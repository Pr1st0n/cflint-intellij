package com.pr1st0n.cflint

import com.intellij.openapi.components.service
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.project.Project
import com.intellij.ui.components.panels.HorizontalLayout
import com.intellij.ui.components.panels.VerticalLayout
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingConstants

class CFLintConfigurable(private val myProject: Project) : SearchableConfigurable {
    private var myLintEnabled = JCheckBox("Enable")

    override fun createComponent(): JComponent {
        val panel = JPanel(VerticalLayout(1, SwingConstants.LEFT))
        val row = JPanel(HorizontalLayout(1, SwingConstants.CENTER))

        row.add(this.myLintEnabled)
        panel.add(row)

        reset()

        return panel
    }

    override fun isModified(): Boolean {
        val projectService = myProject.service<CFLintConfigurationService>()
        val originalState = projectService.state
        val currentState = CFLintState()

        currentState.setEnabled(this.myLintEnabled.isSelected)

        return currentState != originalState
    }

    override fun apply() {
        val projectService = myProject.service<CFLintConfigurationService>()
        val state = CFLintState()
        state.setEnabled(this.myLintEnabled.isSelected)
        state.setCustomRules(emptyList())
        projectService.loadState(state)
    }

    override fun reset() {
        val projectService = myProject.service<CFLintConfigurationService>()
        val state = projectService.state
        this.myLintEnabled.isSelected = state.getEnabled()
    }

    override fun getDisplayName(): String {
        return CFLintBundle.message("plugin.name")
    }

    override fun getId(): String {
        return this.helpTopic
    }

    override fun getHelpTopic(): String {
        return "reference.plugin.settings.project.settings.cfml.cflint"
    }
}
