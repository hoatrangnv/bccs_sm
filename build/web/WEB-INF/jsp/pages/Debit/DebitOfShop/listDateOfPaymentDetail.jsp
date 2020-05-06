<%--
    Document   : listDebitRequestDetail
    Created on : May 16, 2013, 11:49:55 AM
    Author     : thinhph2_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String pageId = request.getParameter("pageId");
    request.setAttribute("contextPath", request.getContextPath());
    request.setAttribute("listDateOfPaymentDetailSearch", request.getSession().getAttribute("listDateOfPaymentDetailSearch"));
%>
<s:if test="#request.listDateOfPaymentDetailSearch.size() > 0 && #request.listDateOfPaymentDetailSearch != null">
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('TITLE.STOCK.CONFIGURATION.LIMITS.004'))}</legend>
        <div id="debitRequestDetailsDiv" style="width:100%; vertical-align:top;height:300px " >
            <div style="height:270px;overflow-y:auto">
                <display:table targets="bodyContent" id="requestDebitDetail" name="listDateOfPaymentDetailSearch" class="dataTable" cellpadding="1" cellspacing="1" requestURI="searchDateOfPaymentAction!pageNavigatorDetail.do" pagesize="100">
                    <display:column escapeXml="false" title="STT" sortable="false" headerClass="tct">
                        <div align="center" style="vertical-align:middle">${requestDebitDetail_rowNum}</div>
                    </display:column>
                    <s:if test="#attr.requestDebitDetail.ownerType == 1">
                        <display:column escapeXml="true" property="shopCodeDebit" title="${imDef:imGetText('lbl.ma_don_vi')}" sortable="false" style="text-align:left" headerClass="tct"/>
                    </s:if>
                    <s:if test="#attr.requestDebitDetail.ownerType == 2">
                        <display:column escapeXml="true" property="shopCode" title="${imDef:imGetText('lbl.ma_don_vi')}" sortable="false" style="text-align:left" headerClass="tct"/>
                        <display:column escapeXml="true" property="staffCode" title="${imDef:imGetText('MES.CHL.078')}" sortable="false" style="text-align:left" headerClass="tct"/>                   
                        <display:column escapeXml="true" property="debitTypeOld" title="${imDef:imGetText('MSG.DEBIT.TYPE.003')}" sortable="false" style="text-align:left" headerClass="tct"/>
                        <display:column escapeXml="true" property="debitType" title="${imDef:imGetText('lbl.han_muc_kho')}" sortable="false" style="text-align:left" headerClass="tct"/>
                    </s:if>
                    <s:if test="#attr.requestDebitDetail.ownerType == 1">
                        <display:column escapeXml="true" property="debitTypeShopOld" title="${imDef:imGetText('lbl.han.muc.cu')}" sortable="false" style="text-align:right" headerClass="tct"/>
                        <display:column escapeXml="true" property="debitTypeShopNew" title="${imDef:imGetText('lbl.muc_han_muc')}" sortable="false" style="text-align:right" headerClass="tct"/>
                        <display:column title="${imDef:imGetText('lbl.loai_yeu_cau')}" sortable="false" style="text-align:left" headerClass="tct">
                            <s:if test="#attr.requestDebitDetail.requestDebitType == 1"><tags:label key="lbl.quantity" /></s:if>
                            <s:elseif test="#attr.requestDebitDetail.requestDebitType == 2"><tags:label key="lbl.total" /></s:elseif>
                        </display:column>
                        <display:column title="${imDef:imGetText('label.request.debit.type.old')}" sortable="false" style="text-align:left" headerClass="tct">
                            <s:if test="#attr.requestDebitDetail.requestDebitTypeOld == 1"><tags:label key="lbl.quantity" /></s:if>
                            <s:if test="#attr.requestDebitDetail.requestDebitTypeOld == 2"><tags:label key="lbl.total" /></s:if>
                            <s:if test="#attr.requestDebitDetail.requestDebitTypeOld == 3">--</s:if>
                        </display:column>
                        <display:column escapeXml="true" property="stockTypeName" title="${imDef:imGetText('lbl.loai_mat_hang')}" sortable="false" style="text-align:left" headerClass="tct"/>
                        <display:column escapeXml="true" property="debitDayTypeOld" title="${imDef:imGetText('lbl.ngay_ad_cu')}" sortable="false" style="text-align:left" headerClass="tct"/>
                    </s:if>
                    <display:column escapeXml="true" property="debitDayType" title="${imDef:imGetText('lbl.ngay_ad_moi')}" sortable="false" style="text-align:left" headerClass="tct"/>

                    <display:column title="${imDef:imGetText('MSG.generates.status')}" sortable="false" style="text-align:left" headerClass="tct">
                        <s:if test="#attr.requestDebitDetail.status == 1"><tags:label key="lbl.chua_duyet" /></s:if>
                        <s:elseif test="#attr.requestDebitDetail.status == 0"><tags:label key="MSG.deny" /></s:elseif>
                        <s:elseif test="#attr.requestDebitDetail.status == 2"><tags:label key="lbl.da_duyet" /></s:elseif>
                    </display:column>
                    <display:column escapeXml="true" property="note" title="${imDef:imGetText('L.100026')}" sortable="false" style="text-align:left" headerClass="tct"/>
                </display:table>
            </div>
        </div>
    </fieldset>
</s:if>
