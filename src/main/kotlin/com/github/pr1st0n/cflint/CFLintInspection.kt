package com.github.pr1st0n.cflint

import com.cflint.BugInfo
import com.cflint.api.CFLintAPI
import com.cflint.config.CFLintChainedConfig
import com.cflint.config.CFLintConfig
import com.cflint.config.CFLintConfiguration
import com.cflint.config.ConfigUtils
import com.intellij.codeInspection.InspectionManager
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ex.UnfairLocalInspectionTool
import com.intellij.coldFusion.model.files.CfmlFile
import com.intellij.openapi.progress.ProcessCanceledException
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile
import com.intellij.util.SmartList
import java.io.File

class CFLintInspection : LocalInspectionTool(), UnfairLocalInspectionTool {
    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? {
        if (file !is CfmlFile) return null

        val descriptors = SmartList<ProblemDescriptor>()
        var config = getConfig(manager.project.basePath)

        try {
            val api = CFLintAPI(config)
            val lintResult = api.scan(file.text, file.virtualFile.path)

            for (issues in lintResult.issues.values) {
                ProgressManager.checkCanceled()
                for (issue in issues) {
                    val descriptor = manager.createProblemDescriptor(
                        file,
                        getTextRange(issue),
                        issue.message,
                        getHighlightType(issue.messageCode),
                        isOnTheFly,
                        null // TODO: implement quickfixes when possible
                    )
                    descriptors.add(descriptor)
                }
            }
        } catch (e: ProcessCanceledException) {
            // We should not swallow "ProcessCanceledException"
            throw e
        } catch (e: Exception) {
            // Ignore
            // TODO: CFLintAPI() throws NPE on first function run
        }

        return descriptors.toArray(ProblemDescriptor.EMPTY_ARRAY)
    }

    private fun getConfig(basePath: String?): CFLintConfiguration {
        var config = CFLintChainedConfig(CFLintConfig.createDefault())

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

    private fun getHighlightType(codeMessage: String): ProblemHighlightType {
        return when(codeMessage) {
            "WARNING" -> ProblemHighlightType.WARNING
            "ERROR" -> ProblemHighlightType.ERROR
            else -> ProblemHighlightType.WEAK_WARNING
        }
    }

    private fun getTextRange(issue: BugInfo): TextRange? {
        // TODO: extend range logic
        return when (issue.messageCode) {
            "FUNCTION_HINT_MISSING" -> {
                val from = issue.offset + 1
                TextRange.create(from, from + "cffunction".length)
            }
            "COMPONENT_HINT_MISSING" -> {
                val from = issue.offset + 1
                TextRange.create(from, from + "cfcomponent".length)
            }
            else -> {
                TextRange.create(issue.offset, issue.offset + issue.expression.length)
            }
        }
    }
}
