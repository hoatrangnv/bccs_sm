<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : CreateNewBills
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     : anhtt update at here TungTV
--%>

<%@page contentType="text/s" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/s4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>



<%
        if (request.getAttribute("invoiceList") == null) {
            request.setAttribute("invoiceList", request.getSession().getAttribute("invoiceList"));
        }
        request.setAttribute("invoiceList", request.getSession().getAttribute("invoiceList"));
%>
<sx:div id="displayTagFrame">
<s:if test="#session.invoiceList != null && #session.invoiceList.size() != 0">
    <tags:imPanel title="MSG.invoice.list">

            <display:table targets="displayTagFrame" name="invoiceList" id="invoice"
                           pagesize="10" class="dataTable" cellpadding="1" cellspacing="1"
                           requestURI="confirmDestroyBillsAction!pageNavigator.do">
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center;">
                ${fn:escapeXml(invoice_rowNum)}
                </display:column>
                <display:column property="serialNo" title="${fn:escapeXml(imDef:imGetText('MSG.bill.sign'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
                <display:column escapeXml="true"  property="blockName" title="${fn:escapeXml(imDef:imGetText('MSG.bin.book.type'))}" sortable="false" headerClass="sortable"/>
                <display:column property="blockNo" title="${fn:escapeXml(imDef:imGetText('MSG.bin.book.number'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
                <display:column property="fromToInvoice" title="${fn:escapeXml(imDef:imGetText('MSG.bill.range'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
                <display:column escapeXml="true"  property="destroyer" title="${fn:escapeXml(imDef:imGetText('MSG.user.cancel'))}" sortable="false" headerClass="sortable"/>
                <display:column escapeXml="true"  property="shopName" title="${fn:escapeXml(imDef:imGetText('MSG.unit.cancel'))}" sortable="false" headerClass="sortable"/>
                <display:column property="dateCreated" title="${fn:escapeXml(imDef:imGetText('MSG.date.cancel'))}" sortable="true" headerClass="sortable">
                    <s:property escapeJavaScript="true"  value="invoice.dateCreated"/>
                </display:column>
                <display:column escapeXml="true"  property="destroyInvoiceReason" title="${fn:escapeXml(imDef:imGetText('MSG.reasonCancel'))}" sortable="false" headerClass="sortable"/>
                <display:column title="<input id = 'checkAllID' type='checkbox' onclick=\"selectCbAll()\">"
                                sortable="false" headerClass="tct" style="width:50px; text-align:center;">
                    <s:checkbox name="form.receivedBill" id="checkBoxID%{#attr.invoice.invoiceDestroyedId}"
                                onclick="checkSelectCbAll();"
                                fieldValue="%{#attr.invoice.invoiceDestroyedId}" theme="simple"/>
                </display:column>
            </display:table>
            <div style="width:100%; padding-top: 10px " align="center" >
                <tags:submit targets="displayTagFrame" formId="form"
                             value="MSG.approve.cancel"
                             preAction="confirmDestroyBillsAction!confirmDestroyed.do"
                             showLoadingText="true" validateFunction="validateDestroy();"/>
            </div>
        </tags:imPanel>
</s:if>
</sx:div>
<script type="text/javascript" language="javascript">
    selectCbAll = function(){
        selectAll("checkAllID", "form.receivedBill", "checkBoxID");
    }
    checkSelectCbAll = function(){
        checkSelectAll("checkAllID", "form.receivedBill", "checkBoxID");
    }

    //cap nhat du lieu vao the divMessage
    document.getElementById( 'displayResultMsgClient' ).innerHTML = '<s:text name="%{#request.returnMsg}"/>';

</script>
