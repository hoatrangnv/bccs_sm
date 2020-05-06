<%-- 
    Document   : createRequestDebitListResult
    Created on : May 14, 2013, 2:03:47 PM
    Author     : ThinhPH2_S
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            String pageId = request.getParameter("pageId");
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("lstRequestDebit", request.getSession().getAttribute("lstRequestDebit" + pageId));
%>

<fieldset class="imFieldset">
    <legend>${imDef:imGetText('lbl.danh_sach_yeu_cau')}</legend>

    <div id="goodList" style="width:100%; vertical-align:top;height:300px " >
        <div style="height:270px;overflow:auto">
            <tags:displayResult id="displayResultMsg" property="returnMsg" type="key" propertyValue="returnMsgParam"/>
            <display:table targets="listDebit" id="requestDebit" name="lstRequestDebit" class="dataTable" cellpadding="1" cellspacing="1" requestURI="createRequestDebitStockShopAction!pageNavigator.do">
                <display:column escapeXml="false" title="STT" sortable="false" headerClass="tct">
                    <div align="center" style="vertical-align:middle">${requestDebit_rowNum}</div>
                </display:column>
                <display:column escapeXml="true" property="shopCode" title="${imDef:imGetText('MSG.shop.code')}" sortable="false" style="text-align:left" headerClass="tct"/>
                <display:column escapeXml="true" property="debitTypeOld" title="${imDef:imGetText('lbl.gia_tri_han_muc_cu')}" sortable="false" style="text-align:right"  headerClass="tct"/>
                <display:column escapeXml="true" property="debitTypeDisplay" title="${imDef:imGetText('lbl.gia_tri_han_muc_moi')}" sortable="false" style="text-align:right"  headerClass="tct"/>
                <display:column escapeXml="true" property="debitDayTypeDisplay" title="${imDef:imGetText('lbl.ngay.ap.dung')}" sortable="false" style="text-align:left" headerClass="tct"/>
                <display:column escapeXml="true" property="stockTypeName" title="${imDef:imGetText('MSG.stock.stock.type')}" sortable="false" style="text-align:left" headerClass="tct"/>
                <display:column escapeXml="false" title="${imDef:imGetText('label.request.debit.type')}" sortable="false" style="text-align:left" headerClass="tct">
                    <s:if test="#attr.requestDebit.requestDebitType == 1"><tags:label key="MSG.GOD.221" /></s:if>
                    <s:else><tags:label key="lbl.total" /></s:else>
                </display:column>
                
                <display:column escapeXml="false" title="${imDef:imGetText('label.request.debit.type.old')}" sortable="false" style="text-align:left" headerClass="tct">
                    <s:if test="#attr.requestDebit.requestDebitTypeOld == 1"><tags:label key="MSG.GOD.221" /></s:if>
                    <s:if test="#attr.requestDebit.requestDebitTypeOld == 2"><tags:label key="lbl.total" /></s:if>
                    <s:if test="#attr.requestDebit.requestDebitTypeOld == 3">--</s:if>
                </display:column>
                
                <display:column escapeXml="false" title="${imDef:imGetText('MSG.delete')}" sortable="false" style="text-align:center" headerClass="tct" >
                    <div align="center" style="vertical-align:middle; width:50px">
                        <%--<s:url action="createRequestDebitStockShopAction!delRequetstDebit" id="URL">
                            <s:param name="requestDetailId" value="#attr.requestDebit.requestDetailId"/>
                            <s:param name="debitDayType" value="#attr.requestDebit.debitDayType"/>
                        </s:url>
                        <sx:a targets="inputDateOfPaymentDiv" href="%{#URL}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                            <img src="${contextPath}/share/img/delete_icon.jpg" title="${imDef:imGetText('MSG.SAE.042')}" alt="${imDef:imGetText('MSG.SAE.042')}"/>
                        </sx:a>--%>
                        <sx:a onclick="deleteRequest('%{#attr.requestDebit.requestDetailId}','%{#attr.requestDebit.debitDayType}')">
                            <img src="${contextPath}/share/img/delete_icon.jpg" title="${imDef:imGetText('MSG.SAE.042')}" alt="${imDef:imGetText('MSG.SAE.042')}"/>
                        </sx:a>
                    </div>
                </display:column>
            </display:table>
        </div>
    </div>
</fieldset>

