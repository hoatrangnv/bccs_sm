<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : addSubSingleAnyPayList
    Created on : Oct 18, 2012, 3:21:11 PM
    Author     : os_levt1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            String pageId = request.getParameter("pageId");
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("addSubAnyPayList", request.getSession().getAttribute("addSubAnyPayList" + pageId));
%>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MES.CHL.083'))}</legend>
    <div style="width:100%; height:200px; overflow:auto;">
        <display:table name="addSubAnyPayList" id="tbladdSubAnyPayList"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tbladdSubAnyPayList_rowNum)}</display:column>
            <display:column escapeXml="true" property="collaboratorCode" title="${fn:escapeXml(imDef:imGetText('MSG.chanel.code'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="amount" title="${fn:escapeXml(imDef:imGetText('MSG.amount'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="reason" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.240'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="errorMessage" title="${fn:escapeXml(imDef:imGetText('MSG.assign.status'))}" style="text-align:center" sortable="false" headerClass="tct"/>
        </display:table>
    </div>
</fieldset>
