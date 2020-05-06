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
            <display:column escapeXml="true"  property="staffName" title="${fn:escapeXml(imDef:imGetText('MES.CHL.079'))}" style="text-align:left" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="shopCode" title="${fn:escapeXml(imDef:imGetText('MES.CHL.080'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="province" title="${fn:escapeXml(imDef:imGetText('MES.CHL.081'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="idNo" title="${fn:escapeXml(imDef:imGetText('MES.CHL.057'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column property="birthDate" title="${fn:escapeXml(imDef:imGetText('MES.CHL.082'))}" style="text-align:center" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="idIssuePlace" title="${fn:escapeXml(imDef:imGetText('MES.CHL.066'))}" style="text-align:left" sortable="false" headerClass="tct"/>
            <display:column property="idIssueDate" title="${fn:escapeXml(imDef:imGetText('MES.CHL.067'))}" style="text-align:center" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
            <display:column property="tel" title="Tel number" style="text-align:center" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
            <display:column property="address" title="Address" style="text-align:center" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
            <display:column property="type" title="Position" style="text-align:center" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
        </display:table>
    </div>
</fieldset>
