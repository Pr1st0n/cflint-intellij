package com.github.pr1st0n.cflint

import com.cflint.api.CFLintAPI
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

class CFLintInspection : LocalInspectionTool(), UnfairLocalInspectionTool {
    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor>? {
        if (file !is CfmlFile) return null

        val descriptors = SmartList<ProblemDescriptor>()

        try {
            // TODO: support configuration file
            val api = CFLintAPI()
            val lintResult = api.scan(file.text, file.virtualFile.path)

            for (issues in lintResult.issues.values) {
                ProgressManager.checkCanceled()
                for (issue in issues) {
                    // TODO: implement proper range logic
                    val textRange = TextRange.create(issue.offset, issue.offset + issue.length)
                    val descriptor = manager.createProblemDescriptor(
                            file,
                            textRange,
                            issue.message,
                            ProblemHighlightType.GENERIC_ERROR_OR_WARNING,
                            isOnTheFly,
                            null // TODO: implement quickfixes when possible
                    )
                    descriptors.add(descriptor)
                }
            }
        } catch (err: ProcessCanceledException) {
            // We should not swallow "ProcessCanceledException"
            throw err
        } catch (err: Exception) {
            // Ignore
            // TODO: CFLintAPI() throws NPE on first function run
        }

        return descriptors.toArray(ProblemDescriptor.EMPTY_ARRAY)
    }
}
