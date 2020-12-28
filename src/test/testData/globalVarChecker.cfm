<cffunction name="testFun">
    <cfset <warning descr="Identifier this is global. Referencing in a CFC or function should be avoided.">this</warning>.testVar = 0/>
</cffunction>
