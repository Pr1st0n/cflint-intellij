package com.pr1st0n.cflint

import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.ui.components.panels.HorizontalLayout
import com.intellij.ui.components.panels.VerticalLayout
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingConstants

class CFLintConfigurable : SearchableConfigurable {
    companion object {
        private const val SETTINGS_CFML_CFLINT = "reference.plugin.settings.project.settings.cfml.cflint"
    }

    override fun createComponent(): JComponent {
        val panel = JPanel(VerticalLayout(1, SwingConstants.LEFT))
        val row = JPanel(HorizontalLayout(1, SwingConstants.CENTER))
        val enableCheckbox = JCheckBox("Enable")

        row.add(enableCheckbox)
        panel.add(row)

        reset()

        return panel
    }

    override fun isModified(): Boolean {
        return false
    }

    override fun apply() {
        val state = CFLintInspection.STATE

        state.setEnabled(true)
    }

    override fun getDisplayName(): String {
        return CFLintBundle.message("plugin.name")
    }

    override fun getId(): String {
        return SETTINGS_CFML_CFLINT
    }
}
