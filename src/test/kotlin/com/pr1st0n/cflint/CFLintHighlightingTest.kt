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
        doTest(listOf("ARG_VAR_CONFLICT"))
    }

    fun testArgVarCheckerMixed() {
        doTest(listOf("ARG_VAR_MIXED"))
    }

    fun testArrayNewChecker() {
        doTest(listOf("AVOID_USING_ARRAYNEW"))
    }

    fun testBuiltInFunctionChecker() {
        doTest(listOf("AVOID_USING_ISDATE"))
    }

    fun testComponentHintChecker() {
        doTest(listOf("COMPONENT_HINT_MISSING"))
    }

    fun testDebugAttributeChecker() {
        doTest(listOf("AVOID_USING_DEBUG_ATTR"))
    }

    fun testFunctionTypeCheckerAny() {
        doTest(listOf("FUNCTION_TYPE_ANY"))
    }

    fun testFunctionTypeCheckerMissing() {
        doTest(listOf("FUNCTION_TYPE_MISSING"))
    }

    fun testGlobalVarChecker() {
        doTest(listOf("GLOBAL_VAR"))
    }

    fun testIncludeChecker() {
        doTest(listOf("AVOID_USING_CFINCLUDE_TAG"))
    }

    fun testNestedCFOutput() {
        doTest(listOf("NESTED_CFOUTPUT"))
    }

    fun testOutputParmMissing() {
        doTest(listOf("OUTPUT_ATTR"))
    }

    fun testQueryParamChecker() {
        doTest(listOf("CFQUERYPARAM_REQ"))
    }

    fun testSelectStarChecker() {
        doTest(listOf("SQL_SELECT_STAR"))
    }

    fun testStructKeyChecker() {
        doTest(listOf("UNQUOTED_STRUCT_KEY", "STRUCT_ARRAY_NOTATION"))
    }

    fun testStructNewChecker() {
        doTest(listOf("AVOID_USING_STRUCTNEW"))
    }

    fun testUnusedArgumentChecker() {
        doTest(listOf("UNUSED_METHOD_ARGUMENT"))
    }

    fun testUnusedLocalVar() {
        doTest(listOf("UNUSED_LOCAL_VARIABLE"))
    }

    fun testVarScoper() {
        doTest(listOf("MISSING_VAR"))
    }

    private fun doTest(rules: List<String>) {
        val state = CFLintState()
        state.setCustomRules(rules)
        try {
            CFLintConfiguration.getInstance(project).loadState(state)
            myFixture.testHighlighting(true, false, true, getTestName(true) + ".cfm")
        } finally {
            CFLintConfiguration.getInstance(project).loadState(myState)
        }
    }
}
