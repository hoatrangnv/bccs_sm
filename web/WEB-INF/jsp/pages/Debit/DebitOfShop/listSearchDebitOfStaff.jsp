<%-- 
    Document   : listSearchDebitOfStaff
    Created on : May 29, 2013, 7:43:05 PM
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

<%
    request.setAttribute("lstDebitStaff", request.getAttribute("lstDebitStaff"));
%>

<fieldset class="imFieldset">
    <legend>${imDef:imGetText('TITLE.STOCK.CONFIGURATION.LIMITS.004')}</legend>
    <div id="goodList" style="width:100%; vertical-align:top;height:300px " >
        <div style="height:270px;overflow:auto">
            <tags:displayResult id="displayResultMsg" property="returnMsg" type="key" propertyValue="returnMsgParam"/>
            <display:table targets="listSearchDebitOfStaffDIV" id="requestDebit" name="lstDebitStaff" class="dataTable" cellpadding="1" cellspacing="1" requestURI="searchDebitOfStaffAction!pageNavigator.do" pagesize="10">
                <display:column escapeXml="false" title="STT" sortable="false" headerClass="tct">
                    <div align="center" style="vertical-align:middle">${requestDebit_rowNum}</div>
                </display:column>
                <display:column escapeXml="true" property="staffCode" title="${imDef:imGetText('lbl.ma_don_vi')}" sortable="false" style="text-align:left" headerClass="tct"/>
                <s:if test="#attr.requestDebit.ownerType == 2">
                    <display:column escapeXml="true" property="debitTypeName" title="${imDef:imGetText('lbl.han_muc_kho')}" sortable="false" style="text-align:left;" headerClass="tct"/>
                </s:if>
                <s:else>
                    <display:column format="{0,number,#,###.0000}" property="debitType" title="${imDef:imGetText('lbl.muc_han_muc')}" sortable="false" style="text-align:left;" headerClass="tct"/>
                </s:else>
                
                <display:column escapeXml="true" property="debitDayName" title="${imDef:imGetText('lbl.loai_ngay_ap_dung')}" sortable="false" style="text-align:left" headerClass="tct"/>
                <s:if test="#attr.requestDebit.ownerType == 2">
                    <display:column format="{0,number,#,###.0000}" property="debitType" title="${imDef:imGetText('lbl.han.muc.toi.da')}" sortable="false" style="text-align:left;" headerClass="tct"/>
                </s:if>
                <display:column format="{0,number,#,###.0000}" property="currentAmount" title="${imDef:imGetText('lbl.gia_tri_ton_kho')}" sortable="false" style="text-align:left;" headerClass="tct"/>
                <s:else>
                    <display:column escapeXml="true" property="stockTypeName" title="${imDef:imGetText('lbl.loai_mat_hang')}" sortable="false" style="text-align:left" headerClass="tct"/>
                    <display:column title="${imDef:imGetText('label.request.debit.type')}" sortable="false" style="text-align:left" headerClass="tct">
                        <s:if test="#attr.requestDebit.requestDebitType == 1">
                            <tags:label key="lbl.quantity" />
                        </s:if>
                        <s:else>
                            <tags:label key="lbl.total" />
                        </s:else>
                    </display:column>
                </s:else>
                
            </display:table>
        </div>
    </div>
</fieldset>

