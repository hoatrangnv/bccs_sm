<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : listResult
    Created on : Apr 19, 2009, 3:33:50 PM
    Author     : Doan Thanh 8
--%>

<%--
    Notes   : danh sach cac shop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listShops", request.getSession().getAttribute("listShops"));
%>

<tags:imPanel title="MSG.sale.channel.list">


    <display:table pagesize="10" targets="resultArea" id="tblListShops" name="listShops" class="dataTable" cellpadding="1" cellspacing="1"  requestURI="channelAction!pageNavigatorShop.do">
        <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListShops_rowNum)}</display:column>
        <display:column escapeXml="true"  property="shopCode" title="${fn:escapeXml(imDef:imGetText('MES.CHL.119'))}" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  property="name" title="${fn:escapeXml(imDef:imGetText('MES.CHL.120'))}" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  property="channelTypeName" title="${fn:escapeXml(imDef:imGetText('MES.CHL.122'))}" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  property="pricePolicyName" title="${fn:escapeXml(imDef:imGetText('MES.CHL.093'))}" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  property="discountPolicyName" title="${fn:escapeXml(imDef:imGetText('MES.CHL.094'))}" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  property="provinceName" title="${fn:escapeXml(imDef:imGetText('MES.CHL.131'))}" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  property="description" title="${fn:escapeXml(imDef:imGetText('MES.CHL.132'))}" sortable="false" headerClass="tct"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.detail'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
            <s:url action="/channelAction!displayShop.do"  id="URL1">
                <s:param name="selectedShopId" value="#attr.tblListShops.shopId"/>
            </s:url>
            <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/accept.png" title="<s:property escapeJavaScript="true"  value="getError(MSG.channel.detail.info)"/>" alt="<s:property escapeJavaScript="true"  value="getError(MSG.detail)" />"/>
            </sx:a>
        </display:column>
    </display:table>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-bottom:5px; padding-top:10px;">
        <%--<input type="button" value="Thêm" style="width:80px;" onclick="prepareAddShop()">--%>
        <tags:submit formId="saleChannelTypeForm" showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="divDisplayInfo"
                     disabled="${fn:escapeXml(!sessionScope.addRootShop)}" 
                     value="MSG.add"
                     preAction="channelAction!prepareAddShop.do"/>
        <%--"--%>
    </div>

</tags:imPanel>

<script language="javascript">

    //xu ly su kien onclick cua nut "Them" (them shop)
    prepareAddShop = function() {
        gotoAction("divDisplayInfo", "${contextPath}/channelAction!prepareAddShop.do?"+  token.getTokenParamString());
    }

</script>
