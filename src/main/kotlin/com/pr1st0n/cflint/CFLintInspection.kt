package com.pr1st0n.cflint

import com.cflint.BugInfo
import com.cflint.api.CFLintAPI
import com.cflint.exception.CFLintConfigurationException
import com.cflint.exception.CFLintScanException
import com.intellij.codeInspection.InspectionManager
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ex.UnfairLocalInspectionTool
import com.intellij.coldFusion.model.files.CfmlFile
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile
import com.intellij.util.SmartList
import com.intellij.workspaceModel.storage.impl.EntityStorageSerializerImpl.Companion.logger

class CFLintInspection : LocalInspectionTool(), UnfairLocalInspectionTool {
    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? {
        val configuration = CFLintConfiguration.getInstance(manager.project)

        if (file !is CfmlFile || !configuration.state.getEnabled()) return null

        val descriptors = SmartList<ProblemDescriptor>()
        val config = configuration.getConfig(manager.project.basePath, false)

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
                        getHighlightType(issue.severity.name),
                        isOnTheFly,
                        null
                    )
                    descriptors.add(descriptor)
                }
            }
        } catch (e: CFLintScanException) {
            logger.error("Unexpected CFLint error during file scan")
        } catch (e: CFLintConfigurationException) {
            logger.error("Invalid CFLint Configuration")
        }

        return descriptors.toArray(ProblemDescriptor.EMPTY_ARRAY)
    }

    private fun getHighlightType(severity: String): ProblemHighlightType {
        return when (severity) {
            "ERROR" -> ProblemHighlightType.GENERIC_ERROR
            "WARNING" -> ProblemHighlightType.WARNING
            else -> ProblemHighlightType.WEAK_WARNING
        }
    }

    @Suppress("ComplexMethod")
    private fun getTextRange(issue: BugInfo): TextRange? {
        @Suppress("MagicNumber")
        val variableOffset = 7
        // Full list of rules: https://github.com/cflint/CFLint/blob/master/RULES.md
        return when (issue.messageCode) {
            "ARG_VAR_CONFLICT" -> {
                val from = issue.offset + variableOffset
                TextRange.create(from, from + issue.variable.length)
            }
            "AVOID_USING_ARRAYNEW" -> {
                TextRange.create(issue.offset, issue.offset + issue.expression.length)
            }
            "AVOID_USING_STRUCTNEW" -> {
                val from = issue.offset + variableOffset
                TextRange.create(from, from + issue.expression.length)
            }
            "COMPONENT_HINT_MISSING" -> {
                val from = issue.offset + 1
                TextRange.create(from, from + "cfcomponent".length)
            }
            "FILE_SHOULD_START_WITH_LOWERCASE" -> {
                null
            }
            "FUNCTION_HINT_MISSING" -> {
                val from = issue.offset + 1
                TextRange.create(from, from + "cffunction".length)
            }
            "MISSING_VAR" -> {
                var from = issue.offset
                if (issue.variable == issue.expression) {
                    from += variableOffset
                }
                TextRange.create(from, from + issue.variable.length)
            }
            "MISSING_SEMI" -> {
                TextRange.create(issue.offset, issue.offset + issue.expression.length)
            }
            "NESTED_CFOUTPUT" -> {
                TextRange.create(issue.offset, issue.offset + issue.expression.length)
            }
            "OUTPUT_ATTR" -> {
                TextRange.create(issue.offset, issue.offset + issue.expression.substringBefore("\n").length)
            }
            "SQL_SELECT_STAR" -> {
                TextRange.create(issue.offset, issue.offset + issue.expression.length)
            }
            "UNUSED_LOCAL_VARIABLE" -> {
                TextRange.create(issue.offset, issue.offset + issue.variable.length)
            }
            else -> {
                TextRange.create(issue.offset, issue.offset + issue.expression.substringBefore("\n").length)
            }
        }
    }
}
