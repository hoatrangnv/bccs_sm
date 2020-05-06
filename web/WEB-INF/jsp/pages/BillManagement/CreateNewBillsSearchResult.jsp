<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : CreateNewBills
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     :
    Modified   : tamdt1, 21/10/2010
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("invoiceList", request.getSession().getAttribute("invoiceList"));
%>

<sx:div id="displayTagFrame">
    <display:table name="invoiceList" targets="displayTagFrame"
                   id="invoice" pagesize="10" class="dataTable"
                   cellpadding="1" cellspacing="1"
                   requestURI="createNewBillsAction!pageNavigator.do">
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" headerClass="tct" style="width:40px;text-align:center;">
            ${fn:escapeXml(invoice_rowNum)}
        </display:column>
        <display:column property="serialNo" title="${fn:escapeXml(imDef:imGetText('MSG.bill.sign'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
        <display:column escapeXml="true"  property="blockName" title="${fn:escapeXml(imDef:imGetText('MSG.bin.book.type'))}" sortable="false" headerClass="sortable"/>
        <display:column property="blockNo" title="${fn:escapeXml(imDef:imGetText('MSG.bin.book.number'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
        <display:column property="fromToInvoice" title="${fn:escapeXml(imDef:imGetText('MSG.bill.range'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
        <display:column escapeXml="true"  property="userCreated" title="${fn:escapeXml(imDef:imGetText('MSG.user.added'))}" sortable="false" headerClass="sortable"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="sortable">
            <s:if test ="#attr.invoice.status == 1">
                ${fn:escapeXml(imDef:imGetText('MSG.instock'))}
            </s:if>
        </display:column>
        <display:column property="dateCreated" title="${fn:escapeXml(imDef:imGetText('MSG.importDate'))}" sortable="false" headerClass="sortable"  format="{0,date,dd/MM/yyyy}"/>
        <display:column title="<input id = 'checkAllID' type='checkbox' onclick=\"selectCbAll()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center;">
            <s:checkbox name="createNewBillsForm.deleteBill" id="checkBoxID%{#attr.invoice.invoiceListId}"
                        onclick="checkSelectCbAll();"
                        theme="simple" fieldValue="%{#attr.invoice.invoiceListId}"/>
        </display:column>
    </display:table>
</sx:div >


<script>
    selectCbAll = function(){
        selectAll("checkAllID", "createNewBillsForm.deleteBill", "checkBoxID");
    }
    checkSelectCbAll = function(){
        checkSelectAll("checkAllID", "createNewBillsForm.deleteBill", "checkBoxID");
    }
</script>
