<%-- 
    Document   : returnMsg
    Created on : Aug 24, 2009, 8:53:25 AM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
<s:if test="#request.reportStockTransPath != null">
    <script>
        <%-- 
        window.open('${contextPath}/${fn:escapeXml(reportStockTransPath)}','','toolbar=yes,scrollbars=yes');
        --%>
            window.open('${contextPath}${fn:escapeXml(reportStockTransPath)}','','toolbar=yes,scrollbars=yes');
    </script>
    <%--
    <a href="${contextPath}/${fn:escapeXml(reportStockTransPath)}">
    --%>
    <a href="${contextPath}${fn:escapeXml(reportStockTransPath)}">
        <tags:displayResult id="reportStockTransMessage" property="reportStockTransMessage" propertyValue="reportStockTransMessageValue" type="key"/>
    </a>
</s:if>
<%--s:elseif test="form.exportUrl!=null && form.exportUrl!=''">
    <script>
        window.open('${contextPath}<s:property escapeJavaScript="true"  value="form.exportUrl"/>','','toolbar=yes,scrollbars=yes');
    </script>
    <a href='${contextPath}<s:property escapeJavaScript="true"  value="form.exportUrl"/>' >click download file</a>
</s:elseif--%>
<s:else>
    <div>
        <tags:displayResult id="displayResultMsgClient" property="reportStockTransMessage" propertyValue="reportStockTransMessageValue" type="key"/>
    </div>
</s:else>
<s:if test="#request.print == 0">
    <script>
        var printInvoiceId = $('printInvoiceId');
        if (printInvoiceId != null) {
            printInvoiceId.disabled = true;
        }
    </script>
</s:if>
