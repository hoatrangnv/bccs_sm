<%-- 
    Document   : saleToRetailDetailV3
    Created on : Dec 26, 2012, 3:29:12 PM
    Author     : trungdh3_s
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<div align ="center">
<tags:displayResult id="displayResultMsg" property="returnMsg"  propertyValue="returnMsgValue" type="key"/>
</div>
<table style="width:100%;">
    <tr>
        <td style="width:40%;vertical-align:top">
            <sx:div id="inputGoodsDiv">
                <jsp:include page="inputGoodsV3.jsp"/>
            </sx:div>
        </td>
        <td style="width:10px"></td>
        <td style="width:60%; vertical-align:top">
            <sx:div id="listGoodsDiv">
                <jsp:include page="listGoodsV3.jsp"/>
            </sx:div>
        </td>
    </tr>
</table>
