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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
            String pageId = request.getParameter("pageId");
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("lstRequestDebit", request.getSession().getAttribute("lstRequestDebit" + pageId));
%>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('lbl.danh_sach_yeu_cau'))}</legend>

    <div id="goodList" style="width:100%; vertical-align:top;height:300px " >
        <div style="height:270px;overflow:auto">
            <tags:displayResult id="displayResultMsg" property="returnMsg" type="key" propertyValue="returnMsgParam"/>
            <display:table targets="listDebit" id="requestDebit" name="lstRequestDebit" class="dataTable" cellpadding="1" cellspacing="1" requestURI="dateOfPaymentAction!pageNavigator.do">
                <display:column escapeXml="false" title="STT" sortable="false" headerClass="tct">
                    <div align="center" style="vertical-align:middle">${requestDebit_rowNum}</div>
                </display:column>
                <display:column escapeXml="true" property="shopCode" title="${imDef:imGetText('MSG.SIK.099')}" sortable="false" style="text-align:left" headerClass="tct"/>
                <display:column escapeXml="true" property="staffCode" title="${imDef:imGetText('MSG.GOD.262')}" sortable="false" style="text-align:left; padding-right:20px" headerClass="tct"/>
                <display:column escapeXml="true" property="debitTypeOld" title="${imDef:imGetText('lbl.gia_tri_han_muc')}" sortable="false" style="text-align:left" headerClass="tct"/>
                <display:column escapeXml="true" property="debitTypeDisplay" title="${imDef:imGetText('lbl.han_muc_kho')}" sortable="false" style="text-align:left" headerClass="tct"/>
                <display:column escapeXml="true" property="debitDayTypeDisplay" title="${imDef:imGetText('lbl.ngay.ap.dung')}" sortable="false" style="text-align:left" headerClass="tct"/>
                <display:column escapeXml="false" title="${imDef:imGetText('lbl.xoa')}" sortable="false" style="text-align:center" headerClass="tct">
                        <div align="center" style="vertical-align:middle; width:50px">
                            <s:url action="dateOfPaymentAction!delRequetstDebit" id="URL">
                                <s:param name="requestDetailId" value="#attr.requestDebit.requestDetailId"/>
                                <s:param name="debitDayType" value="#attr.requestDebit.debitDayType"/>
                            </s:url>
                            <sx:a targets="inputDateOfPaymentDiv" href="%{#URL}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                                <img src="${contextPath}/share/img/delete_icon.jpg" title="${imDef:imGetText('MSG.SAE.042')}" alt="${imDef:imGetText('MSG.SAE.042')}"/>
                            </sx:a>
                        </div>
                    </display:column>
            </display:table>
        </div>
    </div>
</fieldset>

