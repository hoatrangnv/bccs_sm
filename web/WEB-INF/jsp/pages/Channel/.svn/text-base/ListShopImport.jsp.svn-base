<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListShopImport
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
            request.setAttribute("listShop", request.getSession().getAttribute("listShop" + pageId));
%>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MES.CHL.083'))}</legend>
    <div style="width:100%; height:200px; overflow:auto;">
        <display:table name="listShop" id="listShop"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(listShop_rowNum)}</display:column>
            <display:column escapeXml="true"  property="parShopCode" title="${fn:escapeXml(imDef:imGetText('Parent Shop Code'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="shopCode" title="${fn:escapeXml(imDef:imGetText('Shop Code'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="name" title="${fn:escapeXml(imDef:imGetText('Shop Name'))}" style="text-align:left" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="province" title="${fn:escapeXml(imDef:imGetText('MES.CHL.081'))}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="tel" title="Tel Number" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="address" title="Address" style="text-align:center"  sortable="false" headerClass="tct"/>
        </display:table>
    </div>
</fieldset>
