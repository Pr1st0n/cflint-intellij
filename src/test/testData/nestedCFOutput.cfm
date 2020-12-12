<cfoutput query="testQuery">
    <cfset var testVar = 0 />
    <error descr="Nested CFOutput, outer CFOutput has @query."><cfoutput>#testVar#</cfoutput></error>
</cfoutput>
