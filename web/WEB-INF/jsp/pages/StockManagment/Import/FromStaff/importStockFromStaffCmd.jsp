<%--
    Document   : importStockFromStaffCmd
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
    Modified   : tamdt1, 13/07/2010
    Desc       : lap lenh nhap kho tu nhan vien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form method="POST" id="importStockForm" action="importStockFromStaffAction" theme="simple">
<s:token/>

    <%--<s:hidden name="importStockForm.shopImpType" value="1"/>--%>

    <tags:SearchTrans form="importStockForm"
                      target="searchArea"
                      type="imp_from_exp"
                      action="importStockFromStaffAction!searchImpTrans_1.do"/>

    <sx:div id="searchArea" theme="simple">
        <jsp:include page="../FromUnderlying/ListSearchExpNote.jsp"/>
        <jsp:include page="../FromUnderlying/CreateImpCmd.jsp"/>
    </sx:div>

</s:form>





