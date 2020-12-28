<cfquery name="testQuery">
    SELECT top #<warning descr="<cfquery> should use <cfqueryparam/> for variable 'this.count'.">this.count</warning>#
    FROM testTable
</cfquery>
