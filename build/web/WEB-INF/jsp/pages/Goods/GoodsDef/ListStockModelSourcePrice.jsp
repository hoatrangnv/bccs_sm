<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Copyright YYYY Viettel Telecom. All rights reserved.
    VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.

    Document   : ListStockModelSourcePrice
    Created on : Aug 13, 2011, 9:01:40 AM
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
            if (request.getAttribute("listSMSP") == null) {
                request.setAttribute("listSMSP", request.getSession().getAttribute("listSMSP"));
            }
%>
<s:if test= "#request.listSMSP != null && #request.listSMSP.size() > 0">
    <display:table id="lstSMSP" name="listSMSP" class="dataTable" pagesize="10"
                   targets="divSearchResult" requestURI="stockModelSourcePriceAction!pageNavigator.do"
                   cellpadding="0" cellspacing="0">
        <display:column  title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" headerClass="tct" style="width:40px;text-align:center">
            <s:property escapeJavaScript="true"  value="%{#attr.lstSMSP_rowNum}"/>
        </display:column>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.stockModelId'))}" property="stockModelCode" style="width:150px;text-align:left"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.stockModelName'))}" property="name" sortable="false" headerClass="tct" style="width:180px;text-align:left"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.type'))}" property="stockTypeName" sortable="false" headerClass="tct" style="width:150px;text-align:left"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.generates.service'))}" property="telecomServiceName" style="width:100px;text-align:left"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('label.sourcePrice'))}" property="sourcePrice" sortable="false" headerClass="tct" style="width:100px;text-align:right"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.LST.812'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
            <div align="center">
                <s:url action="stockModelSourcePriceAction!editSMSP" id="URL" encode="true" escapeAmp="false">
                    <s:token/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>
                    <s:param name="stockModelId" value="#attr.lstSMSP.stockModelId"/>
                </s:url>
                <sx:a targets="bodyContent" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                    <img src="${contextPath}/share/img/accept.png" title="<s:text name="MES.CHL.062"/>" alt="<s:text name="MES.CHL.062"/>"/>
                </sx:a>
            </div>
        </display:column>
        <display:footer>
            <tr>
                <td colspan="11" style="color:green">
                    <s:text name="MSG.totalRecord"/>&nbsp;<s:property escapeJavaScript="true"  value="%{#request.listSMSP.size()}" />
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
