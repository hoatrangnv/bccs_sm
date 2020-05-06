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
<s:form method="POST" id="importStockForm" action="ImportStockFromSenior" theme="simple">
<s:token/>

    <%--<s:hidden name="importStockForm.shopImpType" value="1"/>--%>

    <tags:SearchTrans form="importStockForm" target="searchArea" type="realImp_from_senior" action="ImportStockFromSenior!searchImpNote.do"/>
    <sx:div id="searchArea" theme="simple">
        <jsp:include page="ListSearchImpNote.jsp"/>
        <jsp:include page="ImpStock.jsp"/>
    </sx:div>

</s:form>





