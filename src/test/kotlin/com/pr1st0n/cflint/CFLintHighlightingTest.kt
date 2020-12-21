package com.pr1st0n.cflint

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class CFLintHighlightingTest : BasePlatformTestCase() {
    private lateinit var myState: CFLintState

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
        myState = CFLintConfiguration.getInstance(project).state
    }

    fun testArgVarCheckerConflict() {
        val state = CFLintState()
        state.setCustomRules(listOf("ARG_VAR_CONFLICT"))
        try {
            CFLintConfiguration.getInstance(project).loadState(state)
            doTest()
        } finally {
            CFLintConfiguration.getInstance(project).loadState(myState)
        }
    }

    fun testArgVarCheckerMixed() {
        val state = CFLintState()
        state.setCustomRules(listOf("ARG_VAR_MIXED"))
        try {
            CFLintConfiguration.getInstance(project).loadState(state)
            doTest()
        } finally {
            CFLintConfiguration.getInstance(project).loadState(myState)
        }
    }

    fun testArrayNewChecker() {
        val state = CFLintState()
        state.setCustomRules(listOf("AVOID_USING_ARRAYNEW"))
        try {
            CFLintConfiguration.getInstance(project).loadState(state)
            doTest()
        } finally {
            CFLintConfiguration.getInstance(project).loadState(myState)
        }
    }

    fun testBuiltInFunctionChecker() {
        val state = CFLintState()
        state.setCustomRules(listOf("AVOID_USING_ISDATE"))
        try {
            CFLintConfiguration.getInstance(project).loadState(state)
            doTest()
        } finally {
            CFLintConfiguration.getInstance(project).loadState(myState)
        }
    }

    fun testDebugAttributeChecker() {
        val state = CFLintState()
        state.setCustomRules(listOf("AVOID_USING_DEBUG_ATTR"))
        try {
            CFLintConfiguration.getInstance(project).loadState(state)
            doTest()
        } finally {
            CFLintConfiguration.getInstance(project).loadState(myState)
        }
    }

    fun testIncludeChecker() {
        val state = CFLintState()
        state.setCustomRules(listOf("AVOID_USING_CFINCLUDE_TAG"))
        try {
            CFLintConfiguration.getInstance(project).loadState(state)
            doTest()
        } finally {
            CFLintConfiguration.getInstance(project).loadState(myState)
        }
    }

    fun testNestedCFOutput() {
        val state = CFLintState()
        state.setCustomRules(listOf("NESTED_CFOUTPUT"))
        try {
            CFLintConfiguration.getInstance(project).loadState(state)
            doTest()
        } finally {
            CFLintConfiguration.getInstance(project).loadState(myState)
        }
    }

    fun testOutputParmMissing() {
        val state = CFLintState()
        state.setCustomRules(listOf("OUTPUT_ATTR"))
        try {
            CFLintConfiguration.getInstance(project).loadState(state)
            doTest()
        } finally {
            CFLintConfiguration.getInstance(project).loadState(myState)
        }
    }

    fun testSelectStarChecker() {
        val state = CFLintState()
        state.setCustomRules(listOf("SQL_SELECT_STAR"))
        try {
            CFLintConfiguration.getInstance(project).loadState(state)
            doTest()
        } finally {
            CFLintConfiguration.getInstance(project).loadState(myState)
        }
    }

    fun testStructNewChecker() {
        val state = CFLintState()
        state.setCustomRules(listOf("AVOID_USING_STRUCTNEW"))
        try {
            CFLintConfiguration.getInstance(project).loadState(state)
            doTest()
        } finally {
            CFLintConfiguration.getInstance(project).loadState(myState)
        }
    }

    fun testVarScoper() {
        val state = CFLintState()
        state.setCustomRules(listOf("MISSING_VAR"))
        try {
            CFLintConfiguration.getInstance(project).loadState(state)
            doTest()
        } finally {
            CFLintConfiguration.getInstance(project).loadState(myState)
        }
    }

    fun testUnusedLocalVar() {
        val state = CFLintState()
        state.setCustomRules(listOf("UNUSED_LOCAL_VARIABLE"))
        try {
            CFLintConfiguration.getInstance(project).loadState(state)
            doTest()
        } finally {
            CFLintConfiguration.getInstance(project).loadState(myState)
        }
    }

    private fun doTest() {
        myFixture.testHighlighting(true, false, true, getTestName(true) + ".cfm")
    }
}
