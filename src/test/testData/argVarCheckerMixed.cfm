<cffunction name="testFun" returntype="numeric">
    <cfargument name="testVar" type="numeric" default="0"/>
    <cfreturn <weak_warning descr="Variable testVar should not be referenced in local and argument scope.">testVar</weak_warning>>
</cffunction>