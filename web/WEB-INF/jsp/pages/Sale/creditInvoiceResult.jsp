<%-- 
    Document   : printInvoiceResult.jsp
    Created on : 11/06/2009
    Author     : ThanhNC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<tags:displayResult id="resultCreditInvoiceClient" property="resultCreditInvoice" propertyValue="resultCreditInvoiceParam"/>
<%--<s:if test="#request.invoicePrintPath!=null">
    <script>
        window.open('${contextPath}<s:property value="#request.invoicePrintPath"/>','','toolbar=yes,scrollbars=yes');
    </script>
    <div><a href='${contextPath}<s:property value="#request.invoicePrintPath"/>' >${imDef:imGetText('MSG.SAE.158')}</a></div>    
</s:if>
<s:if test="#request.invoicePrintPathAddFile!=null">
    <script>
        window.open('${contextPath}<s:property value="#request.invoicePrintPathAddFile"/>','','toolbar=yes,scrollbars=yes');
    </script>
        <div><a href='${contextPath}<s:property value="#request.invoicePrintPathAddFile"/>' ><tags:label key="MSG.sale.063"/></a></div>
</s:if>--%>