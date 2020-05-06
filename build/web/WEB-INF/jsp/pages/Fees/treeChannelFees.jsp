<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%-- 
    Document   : treeChannelFees
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

<%
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("path", request.getContextPath()+ "/collectFeesAction!getShopTree.do?channelTypeId=" + request.getParameter("channelTypeId"));
        request.setAttribute("channelType",  request.getParameter("channelTypeId"));
%>

<!--s:if test="#request.channelType !=null && #request.channelType!=''"-->

<tags:treeAjax
    actionParamName="nodeId"
    getNodeAction="${fn:escapeXml(path)}"
    rootId="0_"
    rootTitle="Tất cả các kênh"
    rootLink=""
    containerId="container"
    containerTextId="holder"
    loadingText="Đang tải dữ liệu..."
    treeId="tree"
    target="treeArea"
    hasCheckbox="true"
    cbName="collectFeesForm.channelSelected"
    isExpandedLevel1="true"
     lazyLoad="false"/>
<!--/s:if-->
