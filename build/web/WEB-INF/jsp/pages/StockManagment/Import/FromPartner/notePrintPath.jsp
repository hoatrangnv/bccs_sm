<%-- 
    Document   : notePrintPath
    Created on : Jun 26, 2014, 8:37:47 PM
    Author     : thuannx1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tags:displayResult id="resultCreateExpCmdClient" property="resultCreateExp" type="key"/>
<s:if test="#request.notePrintPath !=null && #request.notePrintPath != ''">
    <script type="text/javascript">
        window.open('${contextPath}/${fn:escapeXml(notePrintPath)}','','toolbar=yes,scrollbars=yes');
    </script>
    <div><a href='${contextPath}/${fn:escapeXml(notePrintPath)}' ><tags:label key="MSG.download2.file.here"/></a></div>
</s:if>