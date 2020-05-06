<%-- 
    Document   : addCommItems
    Created on : Mar 10, 2009, 11:31:00 AM
    Author     : DatPV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("listCommItems", request.getSession().getAttribute("listCommItems"));
%>
<s:form action="/getCommTree.do" id="CommissionItemsForm" method="post" theme="simple">
<s:token/>

    <table class="inputTbl">
        <tr>
            <td width="25%">
                <jsp:include page="commTreeView.jsp"/>
            </td>
            <td style="vertical-align:top;">
                <sx:div id="divDisplayInfo" cssStyle="width:100%;">
                    <jsp:include page="listCommGroupItems.jsp"/>
                </sx:div>
            </td>
        </tr>
    </table>
</s:form>
