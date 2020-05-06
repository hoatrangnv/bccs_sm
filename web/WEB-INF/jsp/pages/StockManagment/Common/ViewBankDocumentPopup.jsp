<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ImportStockCmd
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ page import="com.viettel.im.common.util.ResourceBundleUtils"%>
<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<link type="text/css" charset="UTF-8" href="${contextPath}/struts/xhtml/styles.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/site.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/ajaxtags.css" type="text/css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/site_1.css" type="text/css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/displaytag.css" type="text/css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/img/aqua/theme.css" type="text/css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/style.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/custom.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/displaytagex.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/dtree.css'/&gt;" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/anylink.css'/&gt;" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/tpl/css/template.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/tpl/css/form.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/tpl/css/custom.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/extjs/css/ext-all.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/extjs/css/my-ext.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/privateStyle.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/dialog.css" rel="stylesheet">
<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="orderManagementAction!divideBankDocument" theme="simple"  enctype="multipart/form-data" method="post" id="saleManagmentForm">

    <s:token/>
    <tags:imPanel title="Divide Bank Document">
        You want divide document <tags:label key="${fn:escapeXml(requestScope.tranferCode)}" /> 
        <display:table targets="resultUpdateSale" id="trans" pagesize="800" 
                       class="dataTable" cellpadding="1" cellspacing="1" >
            <tr>
                <td class="label">
                    Number of document 
                </td>
                <td class="text" colspan="2">
                    <input type="hidden" id="saleManagmentForm.bankFather" name="saleManagmentForm.bankFather" value="${fn:escapeXml(requestScope.tranferId)}">   
                    <input type="text" id="number" name="number" value="">   
                    <a href="#" id="filldetails" onclick="addFields()">Fill Details</a>
                </td>
                <td class="label"></td>
            </tr>
            <tr>
                <td colspan="4">
                    <div id="container2"></div>
                </td>
            </tr>
            <tr>
                <td colspan="4">
                </td>
            </tr>
            <tr>
                <td  colspan="4" >
                    <div id="container1">
                        <tags:submit confirm="false" formId="saleManagmentForm" id="abc"
                                     value="Divide Bank Document" targets="resultUpdateSale"
                                     preAction="orderManagementAction!divideBankDocument.do"/>  

                    </div>
                </td>
            </tr>
            <tr>
                <td  colspan="4" align="center">
                    <sx:div id="resultUpdateSale">
                        <jsp:include page="ViewMesslBankDocument.jsp"/>
                    </sx:div>
                </td>
            </tr>
        </display:table>
    </tags:imPanel>
</s:form>

<script>
                        function addFields() {
                            var number = document.getElementById("number").value;
                            var container = document.getElementById("container2");
                            while (container.hasChildNodes()) {
                                container.removeChild(container.lastChild);
                            }
                            for (i = 0; i < number; i++) {
                                container.appendChild(document.createTextNode("Bank Document " + (i + 1) + " have amount: "));
                                var input = document.createElement("input");
                                input.type = "number";
                                input.setAttribute("id", "saleManagmentForm.bankChild")
                                input.setAttribute("name", "saleManagmentForm.bankChild")
                                container.appendChild(input);
                                container.appendChild(document.createElement("br"));
                            }
                        }

</script>
