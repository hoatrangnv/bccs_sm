<%-- 
    Document   : saleCollToRetailDetail
    Created on : Aug 31, 2010, 1:49:11 PM
    Author     : tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<table style="width:100%;">
    <tr>
        <td style="width:40%; vertical-align:top;">
            <sx:div  id="inputGoods" cssStyle="width:100%; height:380px; vertical-align:top">
                <jsp:include page="saleCollToRetailInputGood.jsp"/>
            </sx:div>
        </td>
        <td style="width:10px"></td>
        <td style="width:60%; vertical-align:top">
            <sx:div id="listGoods">
                <jsp:include page="saleCollToRetailGoodsList.jsp">
                    <jsp:param name="editable" value="true"/>
                </jsp:include>
            </sx:div>
        </td>
    </tr>
</table>