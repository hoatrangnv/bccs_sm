<%--
    Document   : saleToPromotionInputUpdateGoods
    Created on : Jul 25, 2009, 10:24:08 PM
    Author     : User
    Desc       : nhap chi tiet mat hang cho GD ban hang KM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>


<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<table style="width:100%;">
    <tr>
        <td style="width:40%;vertical-align:top">
            <sx:div id="addGood">
                <jsp:include page="declareSaleToPromotionGood.jsp"/>
            </sx:div>
        </td>
        <td style="width:10px;"></td>
        <td style="width:60%;vertical-align:top">
            <sx:div id="goodList">
                <jsp:include page="saleToPromotionGoodsResult.jsp"/>
            </sx:div>
        </td>
    </tr>
</table>
