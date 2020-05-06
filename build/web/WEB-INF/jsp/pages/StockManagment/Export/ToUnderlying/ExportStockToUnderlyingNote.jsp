<%--
    Document   : ExportStockToUnderlyingNote
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<s:form method="POST" id="exportStockForm" action="ExportStockUnderlyingAction" theme="simple">
<s:token/>

    <s:hidden name="exportStockForm.depositStatus"/>
    <s:hidden name="exportStockForm.payStatus"/>
    <s:hidden name="exportStockForm.drawStatus"/>
    
    <s:hidden name="exportStockForm.shopExpType" value="1"/>

    <tags:SearchTrans form="exportStockForm" target="searchArea" type="exp" action="ExportStockUnderlyingAction!searchExpTrans.do"/>
    <sx:div id="searchArea" theme="simple">        
        <jsp:include page="ListSearchExpCmd.jsp"/>        
        <jsp:include page="CreateExpNote.jsp"/>
    </sx:div>

</s:form>
