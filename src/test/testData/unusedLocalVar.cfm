<cffunction name="testFun">
    <cfset var <weak_warning descr="Local variable testVar is not used in function testFun. Consider removing it.">testVar</weak_warning> = 0>
</cffunction>