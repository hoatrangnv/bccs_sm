<%-- 
    Document   : ImportStockFromUnderlyingCmd
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<s:form method="POST" id="importStockForm" action="StaffImportStockFromShop" theme="simple">
<s:token/>

    <%--<s:hidden name="importStockForm.shopImpType" value="2"/>--%>

    <tags:SearchTrans form="importStockForm" target="searchArea" type="staff_imp_from_shop" action="StaffImportStockFromShop!searchImpTrans.do"/>
    <sx:div id="searchArea" theme="simple">
        <jsp:include page="ListStaffSearchExpNote.jsp"/>
        <jsp:include page="StaffImpStock.jsp"/>
    </sx:div>

</s:form>

