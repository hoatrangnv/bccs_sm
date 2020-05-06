<%--
    Document   : invoiceSaleTransList
    Created on : 22/04/2009, 16:43:14 PM
    Author     : tungtv
    Desc       : danh sach giao dich can lap HD

--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>


<sx:div id="resultArea" theme="simple">
    <%--Thong bao ket qua tim kiem--%>
    <sx:div id="returnMsgArea" theme="simple">
        <jsp:include page="returnMsg.jsp"/>
    </sx:div>

    <%--Danh sach hoa don--%>
    <sx:div id="invoiceManagementListArea" theme="simple">
        <s:if test="#request.lstInvoice != null && #request.lstInvoice.size() != 0">
            <jsp:include page="approveInvoiceCreditManagementList.jsp"/>
        </s:if>
        <s:if test="#request.lstInvoiceReceipt != null && #request.lstInvoiceReceipt.size() != 0">
            <jsp:include page="lstInvoiceReceipt.jsp"/>
        </s:if>
    </sx:div>

    <sx:div id="CreditInvoiceArea">
        <jsp:include page="creditInvoiceResult.jsp"/>
    </sx:div>

    <%--Danh sach giao dich--%>
    <sx:div id="invoiceSaleTransSearchlResultArea" theme="simple">
        <s:if test="#request.lstSaleTrans != null && #request.lstSaleTrans.size() != 0">
            <jsp:include page="invoiceSaleTransSearchResult.jsp"/>
        </s:if>
    </sx:div>
</sx:div>
