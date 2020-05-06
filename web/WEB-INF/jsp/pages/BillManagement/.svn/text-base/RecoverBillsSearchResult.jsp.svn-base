<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : SelectBillDepartment
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     : Anhtt update TungTV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("invoiceList", request.getSession().getAttribute("invoiceList"));
%>

<s:if test="#session.invoiceList != null && #session.invoiceList.size() != 0" >
    <sx:div id="displayTagFrame">
    <tags:imPanel title="MSG.invoice.list">
        <display:table targets="displayTagFrame" name="invoiceList" id="invoice"
                       pagesize="10" class="dataTable" cellpadding="1"
                       cellspacing="1" requestURI="recoverBillsAction!pageNavigator.do" >

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center;">
            ${fn:escapeXml(invoice_rowNum)}
            </display:column>
            <display:column property="serialNo" title="${fn:escapeXml(imDef:imGetText('MSG.bill.sign'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
            <display:column escapeXml="true"  property="blockName" title="${fn:escapeXml(imDef:imGetText('Loại T/Q'))}" sortable="false" headerClass="sortable"/>
            <display:column property="blockNo" title="${fn:escapeXml(imDef:imGetText('MSG.bin.book.number'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
            <display:column escapeXml="true"  property="invoiceListOrInvoice" title="${fn:escapeXml(imDef:imGetText('MSG.bill.range.bill'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="billOwnerName" title="${fn:escapeXml(imDef:imGetText('MSG.unit.used'))}" sortable="false" headerClass="sortable"/>
            <display:column title="<input id = 'checkAllID' type='checkbox' onclick=\"selectCbAll()\">"
                            sortable="false" headerClass="tct" style="width:50px; text-align:center;">
                <s:checkbox name="form.receivedBill" id="checkBoxID%{#attr.invoice.invoiceDestroyedId}"
                            onclick="checkSelectCbAll();"
                            fieldValue="%{#attr.invoice.invoiceDestroyedId}" theme="simple"/>
            </display:column>
        </display:table>
    </tags:imPanel>
    <div style="width:100%; padding-top: 10px " align="center">
        <tags:submit targets="bodyContent" formId="frm"
                     value="MSG.recovery"
                     cssStyle="width:80px;"
                     preAction="recoverBillsAction!recoverBillComplete.do"
                     showLoadingText="true" validateFunction="validateRecover();"/>
    </div>
    </sx:div>
</s:if>

<script type="text/javascript" language="javascript">
     selectCbAll = function(){
        selectAll("checkAllID", "form.receivedBill", "checkBoxID");
    }
    checkSelectCbAll = function(){
        checkSelectAll("checkAllID", "form.receivedBill", "checkBoxID");
    }
</script>
