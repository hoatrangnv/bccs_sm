<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : payFeesReturn
    Created on : Sep 24, 2009, 5:00:22 PM
    Author     : NamNX
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>


<s:if test="#request.reportPayfeesMessage != null">
    <script>
        window.open('${contextPath}/${fn:escapeXml(reportPayfeesPath)}','','toolbar=yes,scrollbars=yes');
    </script>
    <a href="${contextPath}/${fn:escapeXml(reportPayfeesPath)}">
        <tags:displayResult id="reportStockTransMessage" property="reportPayfeesMessage" propertyValue="reportPayfeesMessageValue" type="key"/>
    </a>
</s:if>
<s:else>
    <div>
        <tags:displayResult id="displayResultMsgClient" property="reportPayfeesMessage" propertyValue="reportPayfeesMessageValue" type="key"/>
    </div>
</s:else>
