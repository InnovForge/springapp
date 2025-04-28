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
                    <h3>Delete Employee</h3>
                </div>
                <div class="module-body">
                    <form:form method="POST" action="delete.html" commandName="employee" cssClass="form-horizontal row-fluid">
                        <!--<form class="form-horizontal row-fluid">-->
                          <div class="control-group">
                            <label class="control-label" for="basicinput">Employee ID</label>
                            <div class="controls">
                                <form:input type="number" class="span8" path="idEmployee" id="basicinput"  placeholder="Type something here..."/>
                            </div>
                        </div>
                      
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