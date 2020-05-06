<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Copyright 2010 Viettel Telecom. All rights reserved.
    VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 

    Document   : listAgentImport
    Created on : Nov 21, 2011, 2:57:42 PM
    Author     : haint
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            String pageId = request.getParameter("pageId");
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listAgentImport", request.getSession().getAttribute("listAgentImport" + pageId));
%>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MES.CHL.083'))}</legend>
    <div style="width:100%; height:200px; overflow:auto;">
        <display:table name="listAgentImport" id="listAgentImport"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(listAgentImport_rowNum)}</display:column>
            <display:column escapeXml="true"  property="parShopCode" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.001'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="shopCode" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.002'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="shopName" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.003'))}" style="text-align:left" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="tradeName" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.004'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="tin" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.019'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="account" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.020'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="bank" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.021'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="contactName" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.005'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="contactTitle" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.022'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="mail" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.023'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="idNo" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.024'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="province" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.006'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="district" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.007'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="precinct" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.008'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="address" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.009'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="profileState" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.010'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="registryDate" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.011'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="usefulWidth" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.012'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="surfaceArea" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.013'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="boardState" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.014'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="status" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.015'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="checkVAT" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.016'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="agentType" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.017'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="telNumber" title="${fn:escapeXml(imDef:imGetText('MES.ADD.AGENT.018'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="streetBlockName" title="${fn:escapeXml(imDef:imGetText('MES.CHL.176'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="streetName" title="${fn:escapeXml(imDef:imGetText('MES.CHL.177'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="home" title="${fn:escapeXml(imDef:imGetText('MES.CHL.178'))}" style="text-align:center"  sortable="false" headerClass="tct"/>
        </display:table>
    </div>
</fieldset>
