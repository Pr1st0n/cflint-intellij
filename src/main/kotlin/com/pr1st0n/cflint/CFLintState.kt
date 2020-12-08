package com.pr1st0n.cflint

import com.intellij.ide.util.PropertiesComponent

class CFLintState {
    companion object {
        private const val PREFIX = "CFLint"
        private const val ENABLED = "$PREFIX.enabled"
    }

    fun getEnabled(): Boolean {
        return PropertiesComponent.getInstance().getBoolean(ENABLED)
    }

    fun setEnabled(enabled: Boolean) {
        PropertiesComponent.getInstance().setValue(ENABLED, enabled)
    }
}
