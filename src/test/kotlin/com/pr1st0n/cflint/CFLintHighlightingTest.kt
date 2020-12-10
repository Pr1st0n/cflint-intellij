package com.pr1st0n.cflint

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class CFLintHighlightingTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String {
        return basePath
    }

    override fun getBasePath(): String {
        return "src/test/testData"
    }

    override fun isCommunity(): Boolean {
        return false
    }

    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        myFixture.enableInspections(CFLintInspection())
    }

    fun testUnusedLocalVar() {
        val defaultState = CFLintConfiguration.getInstance(project).state
        val state = CFLintState()
        state.setCustomRules(listOf("UNUSED_LOCAL_VARIABLE"))
        try {
            CFLintConfiguration.getInstance(project).loadState(state)
            doTest()
        } finally {
            CFLintConfiguration.getInstance(project).loadState(defaultState)
        }
    }

    fun testArgVarChecker() {
        val defaultState = CFLintConfiguration.getInstance(project).state
        val state = CFLintState()
        state.setCustomRules(listOf("ARG_VAR_CONFLICT"))
        try {
            CFLintConfiguration.getInstance(project).loadState(state)
            doTest()
        } finally {
            CFLintConfiguration.getInstance(project).loadState(defaultState)
        }
    }

    private fun doTest() {
        myFixture.testHighlighting(true, false, true, getTestName(true) + ".cfm")
    }
}
