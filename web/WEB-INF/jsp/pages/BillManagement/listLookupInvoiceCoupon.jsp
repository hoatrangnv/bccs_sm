<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listRetrieveInvoiceCoupon
    Created on : Sep 9, 2010, 8:26:05 AM
    Author     : tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<fieldset class="imFieldset">
    <legend>Danh sách cuống hoá đơn</legend>
    <display:table name="lstInvoiceCoupon" id="invoice"
                   class="dataTable" cellpadding="1" cellspacing="1">
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" headerClass="tct" style="width:40px; text-align:center">
            ${fn:escapeXml(invoice_rowNum)}
        </display:column>
        <display:column escapeXml="true" property="serialNo" title="${fn:escapeXml(imDef:imGetText('MSG.bill.sign'))}" sortable="false" headerClass="sortable" style=" text-align:center"/>
        <display:column escapeXml="true" property="fromInvoice" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.253'))}" sortable="false" headerClass="sortable" style="width:150px; text-align:right"/>
        <display:column escapeXml="true" property="toInvoice" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.254'))}" sortable="false" headerClass="sortable" style="width:150px; text-align:right"/>
        <display:column escapeXml="true" property="quantity" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.019'))}" sortable="false" headerClass="sortable" style="width:100px; text-align:right"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.094'))}" sortable="false" headerClass="sortable" style="width:200px; text-align:center">
            <s:if test="#attr.invoice.status == 1">
                <s:text name="MSG.DET.040"/>
            </s:if>
            <s:elseif test="#attr.invoice.status == 0">
                <s:text name="MSG.GOD.196"/>
            </s:elseif>
            <s:else>-</s:else>
        </display:column>
    </display:table>
</fieldset>
