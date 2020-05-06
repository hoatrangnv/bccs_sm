<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : ReportBillsSearchResult
    Created on : 17/04/2009, 10:51:45 AM
    Author     : TungTV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("invoiceList", request.getSession().getAttribute("invoiceList"));
%>

<sx:div id="displayTagFrame">
    <display:table targets="displayTagFrame" name="invoiceList"
                   id="invoice" pagesize="10" class="dataTable"
                   cellpadding="1" cellspacing="1"
                   requestURI="reportBillsAction!pageNavigator.do">
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
            ${fn:escapeXml(invoice_rowNum)}
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.invoice.type'))}" sortable="false" headerClass="sortable">
            <s:if test="#attr.invoice.invoiceType == 1">
                ${fn:escapeXml(imDef:imGetText('msg.invoiceTypeSale'))}
            </s:if>
            <s:elseif test="#attr.invoice.invoiceType == 2">
                ${fn:escapeXml(imDef:imGetText('msg.invoiceTypePayment'))}
            </s:elseif>
            <s:else>
                undefined
            </s:else>
        </display:column>
        <display:column property="serialCode" title="${fn:escapeXml(imDef:imGetText('MSG.bill.sign'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
        <display:column property="blockNo" title="${fn:escapeXml(imDef:imGetText('MSG.bin.book.number'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
        <display:column escapeXml="true"  property="fromInvoice" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.253'))}" sortable="false" headerClass="sortable"/>
        <display:column escapeXml="true"  property="toInvoice" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.254'))}" sortable="false" headerClass="sortable"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.unit.stock'))}" sortable="false" headerClass="sortable">
            <s:property escapeJavaScript="true"  value="#attr.invoice.shopCode"/> - <s:property escapeJavaScript="true"  value="#attr.invoice.shopName"/>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.staff.stock'))}" sortable="false" headerClass="sortable">
            <s:property escapeJavaScript="true"  value="#attr.invoice.staffCode"/> - <s:property escapeJavaScript="true"  value="#attr.invoice.staffName"/>
        </display:column>
        <display:column property="dateAssign" title="${fn:escapeXml(imDef:imGetText('MSG.consign.date'))}" format="{0,date,dd/MM/yyyy}" style="text-align:center" sortable="false" headerClass="sortable"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="sortable">
            <s:if test="#attr.invoice.invoiceListStatus == 1">
                ${fn:escapeXml(imDef:imGetText('msg.invoiceListStatus.available'))}
            </s:if>
            <s:elseif test="#attr.invoice.invoiceListStatus == 2">
                ${fn:escapeXml(imDef:imGetText('msg.invoiceListStatus.notConfirm'))}
            </s:elseif>
            <s:elseif test="#attr.invoice.invoiceListStatus == 4">
                ${fn:escapeXml(imDef:imGetText('msg.invoiceListStatus.complete'))}
            </s:elseif>
            <s:elseif test="#attr.invoice.invoiceListStatus == 5">
                ${fn:escapeXml(imDef:imGetText('msg.invoiceListStatus.waitApproveDestroy'))}
            </s:elseif>
            <s:elseif test="#attr.invoice.invoiceListStatus == 6">
                ${fn:escapeXml(imDef:imGetText('msg.invoiceListStatus.destroy'))}
            </s:elseif>
            <s:else>
                undefined - <s:property escapeJavaScript="true"  value="#attr.invoice.invoiceListStatus"/>
            </s:else>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.view_history'))}" sortable="false" headerClass="tct" style="width:70px; text-align:center; ">
            <s:a href="" cssClass="cursorHand" onclick="aOnclick('%{#attr.invoice.serialCode}', '%{#attr.invoice.blockNo}', '%{#attr.invoice.fromInvoice}', '%{#attr.invoice.toInvoice}')">
                <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/accept.png"
                     title="<s:text name="MSG.generates.view_history" />"
                     alt="<s:text name="MSG.generates.view_history" />"/>
            </s:a>
        </display:column>
    </display:table>


    <br/>

</sx:div >    



<script>
    aOnclick = function(serialNo, blockNo, fromInvoice, toInvoice) {
        openPopup('reportBillsAction!lookUpInvoiceHistory.do?serialNo='+serialNo + '&blockNo='+blockNo + '&fromInvoice='+fromInvoice + '&toInvoice='+toInvoice +'&'+token.getTokenParamString(),1050,550);
    }
</script>
