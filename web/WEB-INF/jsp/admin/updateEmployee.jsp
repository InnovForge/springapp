<%-- 
    Document   : addUser
    Created on : Feb 6, 2015, 8:51:31 AM
    Author     : KunPC
--%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<core:set var="contextPath" value="${pageContext.request.contextPath}"/>
<tiles:insertDefinition name="layouts">
    <tiles:putAttribute name="body">
        <div class="content">
            <div class="module">
                <div class="module-head">
                    <h3>Update Employee</h3>
                </div>

                <div class="module-body">
                    <form:form method="POST" action="update.html" commandName="employee" cssClass="form-horizontal row-fluid">
                        <!--<form class="form-horizontal row-fluid">-->
                          <div class="control-group">
                            <label class="control-label" for="basicinput">Employee ID</label>
                            <div class="controls">
                                <form:input type="number" class="span8" path="idEmployee" id="basicinput"  placeholder="Type something here..."/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="basicinput">Employee Number</label>
                            <div class="controls">
                                <form:input type="number" class="span8" path="employeeNumber" id="basicinput" placeholder="Type something here..."/>
                            </div>
                        </div>
                      
                        <div class="control-group">
                            <label class="control-label" for="basicinput">Last Name</label>
                            <div class="controls">
                                <form:input class="span8" path="lastName" id="basicinput" placeholder="Type something here..."/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="basicinput">First Name</label>
                            <div class="controls">
                                <form:input class="span8" path="firstName" id="basicinput" placeholder="Type something here..."/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="basicinput">SSN</label>
                            <div class="controls">
                                <form:input class="span8" path="ssn" id="basicinput" placeholder="Type something here..."/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="basicinput">Pay_Rate</label>
                            <div class="controls">
                                <form:input class="span8" path="payRate" id="basicinput" placeholder="Type something here..."/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="basicinput">PayRates_id</label>
                            <div class="controls">
                                <form:input class="span8" path="payRatesId" id="basicinput" placeholder="Type something here..."/>
                            </div>
                        </div>
                            <div class="control-group">
                            <label class="control-label" for="basicinput">Vacation_Days</label>
                            <div class="controls">
                                <form:input class="span8" path="vacationDays" id="basicinput" placeholder="Type something here..."/>
                            </div>
                        </div>
                            <div class="control-group">
                            <label class="control-label" for="basicinput">Paid_To_Date</label>
                            <div class="controls">
                                <form:input max="99" step="1" class="span8" path="paidToDate" id="basicinput" placeholder="Type something here..."/>
                            </div>
                        </div>
                            <div class="control-group">
                            <label class="control-label" for="basicinput">Paid_Last_Year</label>
                            <div class="controls">
                                <form:input max="99" step="1" class="span8" path="paidLastYear" id="basicinput" placeholder="Type something here..."/>
                            </div>
                        </div>
<!--                        <div class="control-group">
                            <label class="control-label">Active</label>
                            <div class="controls">
                                <label class="checkbox inline">
                                    <input type="checkbox" name="active" value="1">
                                </label>
                            </div>
                        </div>-->
                        <div class="control-group">
                            <div class="controls">
                                <button type="submit" class="btn">Submit Form</button>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div><!--/.content-->
    </tiles:putAttribute>
</tiles:insertDefinition>