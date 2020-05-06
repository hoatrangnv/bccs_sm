<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 
    Document   : searchRangeSo
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tuannv1
--%>

<%--
    Note: tim kiem thong tin ve dai so
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("path", request.getContextPath()+ "/filterIsdnAction!getRulesTree.do?stockTypeId=" + request.getParameter("stockTypeId"));
        request.setAttribute("stockType",  request.getParameter("stockTypeId"));
%>

<s:if test="#request.stockType !=null && #request.stockType!=''">
<tags:treeAjax
    actionParamName="nodeId"
    getNodeAction="${fn:escapeXml(path)}"
    rootId="0_"
    rootTitle=" ${fn:escapeXml(imDef:imGetText('MSG.ISN.040'))}"
    rootLink=""
    containerId="container"
    containerTextId="holder"
    loadingText="Processing..."
    treeId="tree"
    target="treeArea"
    hasCheckbox="true"
    cbName="filterIsdnForm.ruleSelected"
    isExpandedLevel1="true"
    lazyLoad="false"/>
</s:if>
