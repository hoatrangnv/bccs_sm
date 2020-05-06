<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListStaffImportAP
    Created on : Jul 30, 2010, 3:54:07 PM
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

            <display:column escapeXml="true"  property="shopCode" title="${fn:escapeXml(imDef:imGetText('MES.CHL.080'))}" style="text-align:center" sortable="false" headerClass="tct"/>

            <display:column escapeXml="true"  property="staffCode" title="${fn:escapeXml(imDef:imGetText('MES.CHL.078'))}" style="text-align:center" sortable="false" headerClass="tct"/>            
            <display:column escapeXml="true"  property="staffName" title="${fn:escapeXml(imDef:imGetText('MES.CHL.079'))}" style="text-align:left" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="tradeName" title="${fn:escapeXml(imDef:imGetText('L.100013'))}" style="text-align:left" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="contactName" title="${fn:escapeXml(imDef:imGetText('L.100014'))}" style="text-align:left" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="staffOwnerCode" title="${fn:escapeXml(imDef:imGetText('MES.CHL.087'))}" style="text-align:left" sortable="false" headerClass="tct"/>

            <display:column escapeXml="true"  property="idNo" title="${fn:escapeXml(imDef:imGetText('MES.CHL.057'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column property="birthDate" title="${fn:escapeXml(imDef:imGetText('MES.CHL.082'))}" style="text-align:center" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="idIssuePlace" title="${fn:escapeXml(imDef:imGetText('MES.CHL.066'))}" style="text-align:left" sortable="false" headerClass="tct"/>
            <display:column property="idIssueDate" title="${fn:escapeXml(imDef:imGetText('MES.CHL.067'))}" style="text-align:center" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>

            <display:column escapeXml="true"  property="province" title="${fn:escapeXml(imDef:imGetText('MES.CHL.081'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="district" title="${fn:escapeXml(imDef:imGetText('MSG.SIK.023'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="precinct" title="${fn:escapeXml(imDef:imGetText('MSG.SIK.026'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="address" title="${fn:escapeXml(imDef:imGetText('MSG.SIK.031'))}" style="text-align:center" sortable="false" headerClass="tct"/>

            <display:column escapeXml="true"  property="profileState" title="${fn:escapeXml(imDef:imGetText('L.100008'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="registryDate" title="${fn:escapeXml(imDef:imGetText('L.100015'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="usefulWidth" title="${fn:escapeXml(imDef:imGetText('L.100006'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="surfaceArea" title="${fn:escapeXml(imDef:imGetText('L.100007'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="boardState" title="${fn:escapeXml(imDef:imGetText('L.100010'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="status" title="${fn:escapeXml(imDef:imGetText('MES.CHL.060'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <!-- haint41 11/02/2012 : them thong tin To,Duong,So nha -->
            <display:column escapeXml="true"  property="streetBlockName" title="${fn:escapeXml(imDef:imGetText('MES.CHL.176'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="streetName" title="${fn:escapeXml(imDef:imGetText('MES.CHL.177'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="home" title="${fn:escapeXml(imDef:imGetText('MES.CHL.178'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <!-- end haint41 -->

            <display:column escapeXml="true"  property="channelTypeId" title="${fn:escapeXml(imDef:imGetText('MES.CHL.099'))}" style="text-align:left" sortable="false" headerClass="tct"/>

        </display:table>
    </div>
</fieldset>
