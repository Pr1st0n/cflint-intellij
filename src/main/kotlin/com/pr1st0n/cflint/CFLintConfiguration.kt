package com.pr1st0n.cflint

import com.cflint.config.CFLintChainedConfig
import com.cflint.config.CFLintConfig
import com.cflint.config.CFLintConfiguration
import com.cflint.config.ConfigUtils
import java.io.File

class CFLintConfiguration {
    fun getConfig(basePath: String?): CFLintConfiguration {
        var config = CFLintChainedConfig(CFLintConfig.createDefault())

        @Suppress("TooGenericExceptionCaught")
        try {
            val baseConfig = File("$basePath/.cflintrc")
            if (baseConfig.exists()) {
                val jsonInputStream = baseConfig.inputStream()
                val retval = ConfigUtils.unmarshalJson(jsonInputStream, CFLintConfig::class.java)
                jsonInputStream.close()
                config = config.createNestedConfig(retval)
            }
        } catch (e: Exception) {
            // Ignore and use default config
        }

        return config
    }
}
