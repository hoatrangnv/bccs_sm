<%-- 
    Document   : resultExportSuccess
    Created on : Mar 31, 2015, 4:40:26 PM
    Author     : dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    request.setAttribute("contextPath", request.getContextPath());

%>

<sx:div id="displayTagFrame">
    <table class="frmTbl" width="100%" border="0" style="font-weight:bold">
        <tr>
             <td style="width: 100%;text-align: center" colspan="2">
                <s:if test="#request.reportAccountPath != null">
                <script>    
                    window.open('${contextPath}'+'${fn:escapeXml(reportAccountPath)}','','toolbar=yes,scrollbars=yes');
                </script>
                    <s:property value="#attr.message"></s:property>
                    <div><a id="abc" href="#" onclick="downloadFile('${contextPath}'+'${fn:escapeXml(reportAccountPath)}')" ><tags:label key="MSG.download2.file.here"/></a></div>
                </s:if>
                <s:else >
                    <s:property value="#attr.message"></s:property>
                </s:else>
            </td>
        </tr>
    </table>
</sx:div>

<script type="text/javascript">
    <%--
    downloadFileExcel = function() {
        var filePath = '<s:property value="#attr.filePath" />';
        window.open("${contextPath}/"+filePath, 500, 500);
        return "checkUserAction!downloadDataFromExcel.do";
    }
 
    downloadFileExcel = function() {
        return "checkUserAction!downloadDataFromExcel.do";
    }
   --%>
    downloadFile = function(url) {
        window.open(url, "Download file", "scrollbars=1, titlebar=0");
    }
</script>                
                