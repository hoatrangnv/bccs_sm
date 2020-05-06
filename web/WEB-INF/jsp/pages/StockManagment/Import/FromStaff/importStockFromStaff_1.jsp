<%-- 
    Document   : importStockFromStaff_1
    Created on : Jul 14, 2010, 9:42:23 AM
    Author     : Doan Thanh 8
    Desc       : nhap kho tu nhan vien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<s:form method="POST" id="importStockForm" action="importStockFromStaffAction" theme="simple">
<s:token/>

    <%--<s:hidden name="importStockForm.shopImpType" value="1"/>--%>

    <tags:SearchTrans form="importStockForm"
                      target="searchArea"
                      type="imp"
                      action="importStockFromStaffAction!searchImpTrans_1.do"/>

    <sx:div id="searchArea" theme="simple">
        <jsp:include page="../FromUnderlying/ListSearchImpNote.jsp"/>
        <jsp:include page="../FromUnderlying/ImpStock.jsp"/>
    </sx:div>

</s:form>


