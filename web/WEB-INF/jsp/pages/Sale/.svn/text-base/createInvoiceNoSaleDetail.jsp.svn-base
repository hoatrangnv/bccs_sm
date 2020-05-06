<%-- 
    Document   : createInvoiceNoSaleDetail
    Created on : Aug 7, 2009, 9:14:43 AM
    Author     : MrSun
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

<table width="100%">
    <tr>
        <td width="40%">
            <sx:div id="invoiceDetailInputItem">
                <jsp:include page="createInvoiceNoSaleDetailInputItem.jsp"/>
            </sx:div>
        </td>
        <td style="width:10px"></td>
        <td width="60%">
            <sx:div id="invoiceDetailItemList">
                <jsp:include page="createInvoiceNoSaleDetailItemList.jsp"/>
            </sx:div>
        </td>
    </tr>
</table>

