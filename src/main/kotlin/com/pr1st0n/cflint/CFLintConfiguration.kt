package com.pr1st0n.cflint

import com.cflint.config.CFLintConfig
import com.cflint.config.CFLintConfiguration
import com.cflint.config.CFLintPluginInfo
import com.cflint.config.ConfigUtils
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import java.io.File

@State(name = "CFLintConfiguration")
class CFLintConfiguration : PersistentStateComponent<CFLintState> {
    private val logger = Logger.getInstance("CFLint.CFLintInspection")
    private var config = CFLintConfig()
    private var initialized = false
    private var myState = CFLintState()

    companion object {
        fun getInstance(project: Project): com.pr1st0n.cflint.CFLintConfiguration {
            return project.getService(com.pr1st0n.cflint.CFLintConfiguration::class.java)
        }
    }

    private fun getConfig(basePath: String?): CFLintConfiguration {
        if (!this.initialized && !setCustomRules() && !setBaseConfig(basePath)) {
            setDefaultConfig()
        }

        return this.config
    }

    fun getConfig(basePath: String?, reload: Boolean): CFLintConfiguration {
        if (reload) {
            this.initialized = false
        }

        return this.getConfig(basePath)
    }

    private fun setBaseConfig(basePath: String?): Boolean {
        val baseConfig = File("$basePath/.cflintrc")

        if (!baseConfig.exists()) {
            return false
        }

        @Suppress("TooGenericExceptionCaught")
        try {
            baseConfig.inputStream().use { jsonInputStream ->
                val retVal = ConfigUtils.unmarshalJson(jsonInputStream, CFLintConfig::class.java)
                setDefaultConfig()
                this.config.includes = retVal.includes
            }
        } catch (e: Exception) {
            logger.error("Invalid CFLint Configuration", e.message)
            return false
        }

        return true
    }

    private fun setCustomRules(): Boolean {
        val rules = this.myState.getCustomRules()

        if (rules.isEmpty()) {
            return false
        }

        setDefaultConfig()

        rules.forEach {
            this.config.addInclude(CFLintPluginInfo.PluginInfoRule.PluginMessage(it))
        }

        return true
    }

    private fun setDefaultConfig() {
        this.config = CFLintConfig.createDefault() as CFLintConfig
        this.initialized = true
    }

    override fun getState(): CFLintState {
        return myState
    }

    override fun loadState(state: CFLintState) {
        myState = state
        initialized = false
    }
}
