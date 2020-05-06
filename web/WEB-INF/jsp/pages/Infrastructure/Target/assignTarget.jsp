
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<div id ="seachStaffTarget">
    <jsp:include page="searchStaff.jsp"></jsp:include>
</div>

<br/>
<div id="staffListTarget">
    <tags:imPanel title="Target.ListStaff">
        <jsp:include page="listStaff.jsp"></jsp:include>
    </tags:imPanel>
</div>

<div id ="editTarget">
    <%--
    <s:if test="#session.AddOrEditTarget == 1">
        <jsp:include page="addNewTarget.jsp"></jsp:include>
    </s:if>
    <s:if test="#session.AddOrEditTarget == 2">
        <jsp:include page="editTarget.jsp"></jsp:include>
    </s:if>
    --%>
</div>

<br/>
<div id ="addTargetByFile">    
    <jsp:include page="addTargetByFile.jsp"></jsp:include>
</div>

