<%-- 
    Document   : downloadDetailSerial
    Created on : Oct 12, 2009, 12:00:37 PM
    Author     : ThanhNC
--%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%
        request.setAttribute("contextPath", request.getContextPath());
//request.setAttribute("lstStockGoods", request.getSession().getAttribute("lstStockGoods"));
%>
<s:if test="#request.reportAccountPath != null">
    <script>
    window.open('${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}','','toolbar=yes,scrollbars=yes');
    </script>
    <a href="${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}">
        <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
    </a>
</s:if>
<%--
<s:if test="goodsForm.exportUrl !=null && goodsForm.exportUrl!=''">
    <script>
        window.open('${contextPath}<s:property escapeJavaScript="true"  value="goodsForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
    </script>
    <div>
        <a href='${contextPath}<s:property escapeJavaScript="true"  value="goodsForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a>
    </div>
</s:if>
--%>
