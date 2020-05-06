<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ShowViewStaffAndAccount
    Created on : Oct 12, 2009, 8:50:11 AM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            //String flag = (String) request.getSession().getAttribute("flag");
            String pageId = request.getParameter("pageId");
            request.setAttribute("flag", request.getSession().getAttribute("flag" + pageId));
            request.setAttribute("showAccount", request.getSession().getAttribute("showAccount" + pageId));
%>

<sx:div  id="accountAgent">
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.SIK.205'))}</legend>
        <sx:div id="agentDetail" theme="simple">
            <jsp:include page="AddAccountAgent.jsp"/>
        </sx:div>
    </fieldset>
</sx:div>
<div style="height:5px">
</div>
<sx:div id="accountDetail" theme="simple">
    <s:if test="#session.showAccount == '1'">
        <jsp:include page="ShowAccountBalance.jsp"/>
    </s:if>
</sx:div>





