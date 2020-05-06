<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

<tags:displayResult id="resultPrintInvoiceClient" property="resultPrintInvoice"/>
<!--tannh20180424 start: : khong cho tai file theo YC TraTV phong TC-->
<s:if test="#request.invoicePrintPath!=null && #request.invoicePrintPath !='' ">

    <script>
        window.printPDF = function() {
            var objFra = document.getElementById('myFrame');
            objFra.contentWindow.focus();
            objFra.contentWindow.print();
        }
    </script>
    <iframe 
        src="${contextPath}<s:property escapeJavaScript="true"  value="#request.invoicePrintPath"/>" 
        id="myFrame" 
        frameborder="0" style="border:0;" 
        width="1" height="1">
    </iframe>  

    <div>
        <input type="button" id="btPrint" onclick="printPDF();" value="Print" />
    </div>
</s:if>