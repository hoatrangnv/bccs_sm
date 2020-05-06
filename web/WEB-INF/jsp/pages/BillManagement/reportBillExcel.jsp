<%-- 
    Document   : reportBillExcel
    Created on : Sep 17, 2009, 11:52:19 AM
    Author     : MrSun
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>


<%
        request.setAttribute("contextPath", request.getContextPath());

%>

<s:if test="#session.invoiceList != null && #session.invoiceList.size() != 0">
    <table class="inputTbl" style="width:100%" align="center">
        <tr>
            <td align="center">
                <s:if test="form.pathFile!=null && form.resultMsg!=''">
                    <font color="red"><s:property escapeJavaScript="true"  value="form.resultMsg"/></font>
                        
                </s:if>
            </td>
        </tr>
        <tr>
            <td align="center">
                <s:if test="form.pathFile!=null && form.pathFile!=''">
                    <script>
                        window.open('${contextPath}<s:property escapeJavaScript="true"  value="form.pathFile"/>','','toolbar=yes,scrollbars=yes');
                    </script>
                    <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="form.pathFile"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
                </s:if>
            </td>
        </tr>
    </table>
</s:if>
