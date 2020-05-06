<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : linkDownload
    Created on : Mar 10, 2010, 11:37:08 AM
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


<%
        request.setAttribute("contextPath", request.getContextPath());
%>



<s:if test="#request.reportStockTransPath != null">
    <script>        
        window.open('${contextPath}${fn:escapeXml(reportStockTransPath)}','','toolbar=yes,scrollbars=yes');
    </script>    
    <a href="${contextPath}${fn:escapeXml(reportStockTransPath)}">
        <tags:displayResult id="reportStockTransMessage" property="reportStockTransMessage" propertyValue="reportStockTransMessageValue" type="key"/>
    </a>
</s:if>
<s:else>
    <div>
        <tags:displayResult id="displayResultMsgClient" property="reportStockTransMessage" propertyValue="reportStockTransMessageValue" type="key"/>
    </div>
</s:else>
