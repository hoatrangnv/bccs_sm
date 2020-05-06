<%-- 
    Document   : saleToPunishDetail
    Created on : Nov 16, 2010, 3:31:36 AM
    Author     : NamLT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<!--<div align ="center">
    <--tags:displayResult id="displayResultMsg" property="returnMsg"  propertyValue="returnMsgValue" type="key"/>
</div>-->
<table style="width:100%;">
    <tr>
        <td style="width:40%;vertical-align:top">
            <sx:div id="inputGood">
                <jsp:include page="saleToPunishInputGood.jsp"/>
            </sx:div>
        </td>
        <td style="width:10px"></td>
        <td style="width:60%; vertical-align:top">
            <sx:div id="goodsList">
                <jsp:include page="saleToPunishGoodsList.jsp"/>
            </sx:div>
        </td>
    </tr>
</table>
