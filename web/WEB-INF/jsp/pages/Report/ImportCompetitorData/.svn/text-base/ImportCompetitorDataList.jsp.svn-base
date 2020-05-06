<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListStaffImport
    Created on : Jul 22, 2010, 2:30:20 PM
    Author     : User
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
            request.setAttribute("listStaffImportFile", request.getSession().getAttribute("listStaffImportFile" + pageId));
%>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MES.CHL.083'))}</legend>
    <div style="width:100%; height:200px; overflow:auto;">
        <display:table name="listStaffImportFile" id="tbllistStaffImportFile"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tbllistStaffImportFile_rowNum)}</display:column>
            <display:column escapeXml="true"  property="staffCode" title="${fn:escapeXml(imDef:imGetText('MES.CHL.078'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="false"  property="collectorDateString" title="${fn:escapeXml(imDef:imGetText('MSG.createDate'))}" style="text-align:center" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="kitMovitel" title="${fn:escapeXml(imDef:imGetText('Kit.Movitel'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="kitMctel" title="${fn:escapeXml(imDef:imGetText('Kit.Mctel'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="kitVodacom" title="${fn:escapeXml(imDef:imGetText('Kit.Vodacom'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="cardMovitel" title="${fn:escapeXml(imDef:imGetText('Card.Movitel'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="cardMctel" title="${fn:escapeXml(imDef:imGetText('Card.Mctel'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="cardVodacom" title="${fn:escapeXml(imDef:imGetText('Card.Vodacom'))}" style="text-align:center" sortable="false" headerClass="tct"/>
        </display:table>
    </div>
</fieldset>
