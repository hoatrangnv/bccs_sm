<%-- 
    Document   : listStockModel
    Created on : Apr 16, 2009, 8:54:55 AM
    Author     : tamdt1
    Desc       : hien thi danh sach cac stockModel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
        request.setAttribute("contextPath", request.getContextPath());
        //request.setAttribute("listStockModels", request.getSession().getAttribute("listStockModels"));
%>

<div>
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.006'))}</legend>
        <display:table id="tblListStockModels" name="listStockModels"
                       class="dataTable"
                       targets="divListStockModels" pagesize="20"
                       requestURI="goodsDefAction!searchStockModel.do?stockModelForm.stockTypeId=${fn:escapeXml(stockModelForm.stockTypeId)}&stockModelForm.stockModelCode=${fn:escapeXml(param['stockModelForm.stockModelCode'])}&stockModelForm.name=${fn:escapeXml(param['stockModelForm.name'])}"
                       cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListStockModels_rowNum)}</display:column>
            <display:column escapeXml="true" property="stockModelCode" title="${fn:escapeXml(imDef:imGetText('MSG.stockModelId'))}" sortable="false" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.008'))}" sortable="false" headerClass="tct">
                <s:property escapeJavaScript="true"  value="#attr.tblListStockModels.name"/>
            </display:column>
            <display:column escapeXml="true" property="telecomServiceName" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.016'))}" sortable="false" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.serial.management'))}" sortable="false" style="text-align:center" headerClass="tct">
                <s:if test="#attr.tblListStockModels.checkSerial == 1">
                    <input type="checkbox" disabled checked>
                </s:if>
                <s:else>
                    <input type="checkbox" disabled>
                </s:else>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.dial1'))}" sortable="false" style="text-align:center"  headerClass="tct">
                <s:if test="#attr.tblListStockModels.checkDial == 1">
                    <input type="checkbox" disabled checked>
                </s:if>
                <s:else>
                    <input type="checkbox" disabled>
                </s:else>
            </display:column>
            <display:column escapeXml="true" property="unitName" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.032'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="notes" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.031'))}" sortable="false" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.047'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                <s:url action="goodsDefAction!displayStockModel" id="URL1">
                    <s:param name="selectedStockModelId" value="#attr.tblListStockModels.stockModelId"/>
                </s:url>
                <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/accept.png" title="<s:property escapeJavaScript="true"  value="getError(MSG.GOD.004)"/>" alt="<s:property escapeJavaScript="true"  value="getError(MSG.GOD.004)"/>"/>
                </sx:a>
            </display:column>
        </display:table>
    </fieldset>
</div>

