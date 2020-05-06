<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : saleTransManagmentResult.jsp
    Created on : 18/06/2009
    Author     : ThanhNC
    Desc       : danh sach giao dich ban hang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<div style="width:100%;" >
    <tags:displayResult id="resultUpdateSaleClient" property="resultUpdateSale" type="key"/>
</div>
