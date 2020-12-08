<cffunction name="unusedLocalVar">
    <cfset var <weak_warning descr="Local variable isUsed is not used in function unusedLocalVar. Consider removing it.">isUsed</weak_warning> = false>
</cffunction>