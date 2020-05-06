<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : OnOffShopfByFile
    Created on : Sep 24, 2010, 8:26:25 AM
    Author     : Lamnt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@page  import="com.viettel.im.database.BO.UserToken" %>
<%@taglib prefix="imDef" uri="imDef" %>


<%
    UserToken userToken = (UserToken) request.getSession().getAttribute("userToken");
    String shopName = userToken.getShopName();
    Long shopId = userToken.getShopId();
    request.setAttribute("contextPath", request.getContextPath());
    request.setAttribute("shopName", shopName);

%>
<s:form action="channelChangeStaffAction" theme="simple" method="post" id="channelForm">
    <s:token/>


    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="channelForm"
                     showLoadingText="true"
                     cssStyle="width:100px;"
                     targets="bodyContent"
                     value="MSG.report.export"
                     validateFunction="checkValidFile()"
                     preAction="channelChangeStaffAction!reportChannelSaleHandset.do"/>
    </div>

    <tags:displayResult id="resultViewConvertChannelByFile" property="resultViewConvertChannel" propertyValue="messageParam" type="key"/>
    <%--Hien thi ket qua tim kiem--%>
    <sx:div id="detailArea">
        <jsp:include page="getReportChannelConfigSaleHandset.jsp"/>
    </sx:div>

</s:form>
