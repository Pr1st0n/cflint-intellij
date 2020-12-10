package com.pr1st0n.cflint

import com.intellij.openapi.util.Comparing

class CFLintState {
    private var myLintEnabled = true
    private var myCustomRules = emptyList<String>()

    fun getEnabled(): Boolean {
        return myLintEnabled
    }

    fun setEnabled(enabled: Boolean) {
        myLintEnabled = enabled
    }

    fun getCustomRules(): List<String>? {
        return myCustomRules
    }

    fun setCustomRules(rules: List<String>) {
        myCustomRules = rules
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val state: CFLintState = other as CFLintState
        if (myLintEnabled != state.myLintEnabled) return false
        return Comparing.equal(myCustomRules, state.myCustomRules)
    }

    override fun hashCode(): Int {
        var result = myCustomRules.hashCode()
        result = 31 * result + myLintEnabled.hashCode()
        return result
    }
}
