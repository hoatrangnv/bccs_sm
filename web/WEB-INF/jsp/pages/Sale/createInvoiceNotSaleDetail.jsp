<%--
    Document   : destroySaleInvoice
    Created on : Mar 11, 2009, 6:49:55 PM
    Author     : TungTV
--%>

<%--
    Note: huy hoa don ban hang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div align="center">
                <jsp:include page="returnMsg.jsp"/>
            </div>
        <br/>
        <table style="width:100%;">
        <tr>
            <td style="width:40%">
                <%--<div id="addGood">--%>
                <sx:div id="inputGood">
                    <jsp:include page="createInvoiceNotSaleInputGood.jsp"/>
                </sx:div>
            </td>
            <td style="width:10px"></td>
            <td style="width:60%">
                <sx:div id="goodsList">
                    <jsp:include page="createInvoiceNotSaleGoodsList.jsp"/>
                </sx:div>
            </td>
        </tr>
        </table>
    </body>
</html>