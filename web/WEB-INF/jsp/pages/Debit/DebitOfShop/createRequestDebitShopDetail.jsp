<%-- 
    Document   : createRequestDebitForEmp
    Created on : May 14, 2013, 3:13:24 PM
    Author     : ThinhPH2_S
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<table style="width:100%;">
    <tr>
        <td style="width:50%; vertical-align:top">
            <sx:div id="addDebit">
                <jsp:include page="createRequestDebitShopQuery.jsp"/>
            </sx:div>
        </td>
        <td style="width:10px"></td>
        <td style="width:50%; vertical-align:top">
            <sx:div id="listDebit">
                <jsp:include page="createRequestDebitShopListResult.jsp"/>
            </sx:div>
        </td>
    </tr>
</table>
