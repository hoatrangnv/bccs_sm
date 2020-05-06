<%@page import="com.viettel.security.util.StringEscapeUtils"%>

<%-- 
    Document   : SelectBillDepartment
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     : TungTV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>

<%
        request.setAttribute("contextPath", request.getContextPath());

        Long crrInvoiceNo = (Long) session.getAttribute("crrInvoiceNo");
%>
<s:form action="splitBillInfoAction!splitBillComplete.do" method="POST" styleId="SplitBillForm" theme="simple">
<s:token/>

    <fieldset style="width:95%; padding:10px 10px 10px 10px">
        <legend class="transparent">Thông tin tách dải hóa đơn </legend>
        <table class="inputTbl">
            <tr>
                <td >
                   <tags:label key="MSG.bill.sign"/>
                </td>
                <td >
                    <s:textfield name= "#session.billManagerViewHelper.serialNo" id="billSerial" cssClass="txtInput" readonly="true"/>
                </td>
                <td >
                    <tags:label key="MSG.bill.range"/>
                </td>
                <td >
                    <s:textfield name= "#session.billManagerViewHelper.fromToInvoiceNo" id="billSerial" cssClass="txtInput" readonly="true"/>
                </td>
            </tr>
            <!-- Giao hoa don -->
            <s:if test="#session.billManagerViewHelper.interfaceType == 1">
                <tr>
                    <td >
                        <tags:label key="MSG.invoice.number.from" required="true"/>
                    </td>
                    <td >
                        <s:textfield name="formSplit.billSplitStartNumber" value = "%{#session.billManagerViewHelper.billSplitStartNumber}" id="billStartNumber" cssClass="txtInput" readonly="true"/>
                    </td>
                    <td >
                        <tags:label key="MSG.invoice.number.to" required="true"/>
                    </td>
                    <td >
                        <s:textfield name="formSplit.billSplitEndNumber" value = "%{#session.billManagerViewHelper.billSplitEndNumber}" id="billEndNumber" cssClass="txtInput"/>
                    </td>
                </tr>
            </s:if>
            <s:else>
                <tr>
                    <td >
                        <tags:label key="MSG.recover.invoice.number.from" required="true"/>
                    </td>
                    <td >
                        <s:textfield name="formSplit.billSplitStartNumber" value = "%{#session.billManagerViewHelper.billSplitStartNumber}" id="billStartNumber" cssClass="txtInput" />
                    </td>
                    <td >
                        <tags:label key="MSG.recover.invoice.number.to"/>
                    </td>
                    <td >
                        <s:textfield name="formSplit.billSplitEndNumber" value="%{#session.billManagerViewHelper.billSplitEndNumber}" id="billEndNumber" cssClass="txtInput" readonly="true"/>
                    </td>
                </tr>
            </s:else>
            <tr>
                <td colspan="4" align="center">
                    <tags:displayResult id="displayResultMsgClient" property="returnMsg" type="key" />
                </td>
            </tr>
        </table>
        <br/>
        <div style="width:100%" align="center">
            <%--<s:submit value="Cập nhật" onclick="return isValidate();"/>--%>
            <s:submit key="MSG.update" value="Cập nhật" onclick="return isValidate();"/>
        </div>
    </fieldset>
</s:form>

<!--<script type="text/javascript" language="javascript">

//    var regExp1 = /^[0-9]+$/;
//
//    var currentInvoice = <%=StringEscapeUtils.escapeHtml(crrInvoiceNo)%>;
//    //alert(currentInvoice);
//    //var currentInvoice = 123;
//
//    isValidate = function(){
//        if($('billStartNumber').value.trim() == ""){
//            $( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập số h�
-->
