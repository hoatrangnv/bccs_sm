<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Copyright YYYY Viettel Telecom. All rights reserved.
    VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
    Document   : ListStockOwnerTmp
               : Danh sách nhân viên (có thông tin hạn mức)
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
<%
    if (request.getAttribute("listStockOwnerTmpBean") == null) {
        request.setAttribute("listStockOwnerTmpBean", request.getSession().getAttribute("listStockOwnerTmpBean"));
    }
%>
<s:if test= "#request.listStockOwnerTmpBean != null && #request.listStockOwnerTmpBean.size() > 0">
    <display:table id="lstSOT" name="listStockOwnerTmpBean" class="dataTable" pagesize="10"
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
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('label.currentDebit'))}" property="strCurrentDebit" sortable="false" headerClass="tct" style="width:120px;text-align:right"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('label.maxDebit'))}" property="strMaxDebit" sortable="false" headerClass="tct" style="width:100px;text-align:right"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.LST.812'))}" sortable="false" headerClass="tct" style="width:30px;text-align:center">
            <div align="center">
                <s:url action="stockOwnerTmpAction!editStockOwnerTmp" id="URL" encode="true" escapeAmp="false">
                    <s:param name="stockId" value="#attr.lstSOT.stockId"/>
                </s:url>
                <sx:a targets="bodyContent" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                    <img src="${contextPath}/share/img/accept.png" title="<s:text name="MES.CHL.062"/>" alt="<s:text name="MES.CHL.062"/>"/>
                </sx:a>
            </div>
        </display:column>
        <display:footer>
            <tr>
                <td colspan="11" style="color:green">
                    <s:text name="MSG.totalRecord"/>&nbsp;<s:property escapeJavaScript="true"  value="%{#request.listStockOwnerTmpBean.size()}" />
                </td>
            </tr>
        </display:footer>
    </display:table>
</s:if>
<s:else>
    <font color='red'>
        <tags:label key="MSG.not.found.records" />
    </font>
</s:else>
