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
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.Document
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.util.SmartList

class CFLintInspection : LocalInspectionTool(), UnfairLocalInspectionTool {
    private val logger = Logger.getInstance("CFLint.CFLintInspection")

    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? {
        val configuration = CFLintConfiguration.getInstance(manager.project)

        if (file !is CfmlFile || !configuration.state.getEnabled()) return null

        val descriptors = SmartList<ProblemDescriptor>()
        val config = configuration.getConfig(manager.project.basePath, false)
        val document = PsiDocumentManager.getInstance(manager.project).getDocument(file.containingFile)

        try {
            val api = CFLintAPI(config)
            val lintResult = api.scan(file.text, file.virtualFile.path)

            for (issues in lintResult.issues.values) {
                ProgressManager.checkCanceled()
                for (issue in issues) {
                    val descriptor = manager.createProblemDescriptor(
                        file,
                        getTextRange(issue, document),
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

    private fun getTextRange(issue: BugInfo, document: Document?): TextRange? {
        // Full list of rules: https://github.com/cflint/CFLint/blob/master/RULES.md
        return when (issue.messageCode) {
            "FILE_SHOULD_START_WITH_LOWERCASE" -> {
                // Display in a separate space at the top of the file content
                null
            }
            "MISSING_SEMI" -> {
                // Covered by CFML Support plugin inspection
                val startOffset = issue.offset + 1
                TextRange.create(startOffset, startOffset + 1)
            }
            "UNUSED_LOCAL_VARIABLE" -> {
                TextRange.create(issue.offset, issue.offset + issue.variable.length)
            }
            else -> {
                // Issue line is 1 based, however document line is 0 based
                val documentLine = issue.line - 1
                var startOffset = document?.getLineStartOffset(documentLine) ?: issue.offset
                var endOffset = document?.getLineEndOffset(documentLine) ?: issue.offset + issue.length
                val lineText = document?.getText(TextRange.create(startOffset, endOffset))
                var offsetLength = issue.length
                var indexOfExpression: Int? = null

                if (issue.variable != null && offsetLength > 0) {
                    indexOfExpression = lineText?.indexOf(issue.variable, 0, true)
                } else if (issue.expression != null) {
                    // Use only 1st line in case of multiline expression to find index of it in lineText
                    val expression = issue.expression.substringBefore("\n")
                    indexOfExpression = lineText?.indexOf(expression, 0, true)
                    offsetLength = issue.expression.length
                }

                if (indexOfExpression != null && indexOfExpression > 0) {
                    startOffset += indexOfExpression
                }

                endOffset = startOffset + offsetLength

                if (document != null && endOffset > document.textLength) {
                    endOffset = document.textLength
                }

                TextRange.create(startOffset, endOffset)
            }
        }
    }
}
