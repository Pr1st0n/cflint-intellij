<cffunction name="argVarChecker">
    <cfargument name="testVar">
    <cfset var <error descr="Variable testVar should not be declared in both local and argument scopes.">testVar</error> = "">
</cffunction>