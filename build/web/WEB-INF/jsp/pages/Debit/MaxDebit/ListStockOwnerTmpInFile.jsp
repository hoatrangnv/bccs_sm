<%-- 
    Document   : ListStockOwnerTmpInFile
               : Danh sách nhân viên và hạn mức kèm theo từ file template
    Created on : Jul 28, 2011, 10:54:33 AM
    Author     : kdvt_phuoctv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    if (request.getAttribute("listStockOwnerTmpBeanInFile") == null) {
        request.setAttribute("listStockOwnerTmpBeanInFile", request.getSession().getAttribute("listStockOwnerTmpBeanInFile"));
    }
%>
<s:if test= "#request.listStockOwnerTmpBeanInFile != null && #request.listStockOwnerTmpBeanInFile.size() > 0">
    <display:table id="lstSOT" name="listStockOwnerTmpBeanInFile" class="dataTable" pagesize="10"
                   targets="divSearchResult" requestURI="stockOwnerTmpAction!pageNavigator.do"
                   cellpadding="0" cellspacing="0">
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" headerClass="tct" style="width:20px;text-align:center">
            <s:property escapeJavaScript="true"  value="%{#attr.lstSOT_rowNum}"/>
        </display:column>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MES.CHL.015'))}" property="shopCode" style="width:100px;text-align:center"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MES.CHL.016'))}" property="shopName" sortable="false" headerClass="tct" style="width:140px;text-align:left"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MES.CHL.058'))}" property="channelTypeName" sortable="false" headerClass="tct" style="width:150px;text-align:left"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MES.CHL.105'))}" property="staffCode" style="width:100px;text-align:left"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MES.CHL.106'))}" property="staffName" style="width:180px;text-align:left"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('label.currentDebit'))}" property="currentDebit" sortable="false" headerClass="tct" style="width:120px;text-align:right"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('label.maxDebit'))}" property="strMaxDebit" sortable="false" headerClass="tct" style="width:100px;text-align:right"/>
        <display:footer>
            <tr>
                <td colspan="11" style="color:green">
                    <s:text name="MSG.totalRecord"/>&nbsp;<s:property escapeJavaScript="true"  value="%{#request.listStockOwnerTmpBeanInFile.size()}" />
                </td>
            </tr>
        </display:footer>
    </display:table>
</s:if>
<s:else>
    <font color='red'>
        <tags:label key="MSG.noData" />
    </font>
</s:else>
