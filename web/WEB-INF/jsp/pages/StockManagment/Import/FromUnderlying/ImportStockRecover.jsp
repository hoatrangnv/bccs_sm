<%-- 
    Document   : ImportStockRecover
    Created on : Sep 28, 2009, 10:45:46 AM
    Author     : User
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<s:form method="POST" id="importStockForm" action="ImportStockUnderlyingAction" theme="simple">
<s:token/>

    <%--<s:hidden name="importStockForm.shopImpType" value="1"/>--%>

    <tags:SearchTransRecover form="importStockForm" target="searchArea" type="imp" action="ImportStockUnderlyingAction!searchImpTrans.do"/>
    <sx:div id="searchArea" theme="simple">
        <jsp:include page="ListSearchImpNote.jsp"/>
        <jsp:include page="ImpStock.jsp"/>
    </sx:div>
</s:form>

