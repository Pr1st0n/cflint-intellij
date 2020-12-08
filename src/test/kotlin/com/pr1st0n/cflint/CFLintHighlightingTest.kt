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

    // UNUSED_LOCAL_VARIABLE
    fun testUnusedLocalVar() {
        doTest()
    }

    // ARG_VAR_CONFLICT
    fun testArgVarChecker() {
        doTest()
    }

    private fun doTest() {
        myFixture.testHighlighting(true, false, true, getTestName(true) + ".cfm")
    }
}
