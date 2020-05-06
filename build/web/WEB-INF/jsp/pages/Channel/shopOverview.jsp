<%-- 
    Document   : shopOverview
    Created on : Apr 19, 2009, 3:16:48 PM
    Author     : tamdt1
--%>

<%--
    Note: man hinh tong quat ve cac shop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>

<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("displayInfo", request.getSession().getAttribute("displayInfo"));
        request.getSession().removeAttribute("displayInfo");
%>

<table class="inputTbl2Col" style="width:100%; background-color:#F5F5F5;">
    <tr>
        <td style="width:25px; vertical-align:top">
            <tags:imPanel title="MSG.sale.channel.list.items">
                <div align="left" style="width:250px; height:580px; overflow:scroll; margin-left:5px; margin-top:5px;">
                    <tags:treeAjax
                        actionParamName="nodeId"
                        getNodeAction="${contextPath}/channelAction!getShopTree.do"
                        rootId="0_"
                        rootTitle="MSG.chanels.all"
                        rootLink="${contextPath}/channelAction!listShop.do"
                        containerId="container111"
                        containerTextId="holder111"
                        loadingText="Loading..."
                        treeId="tree111"
                        target="divDisplayInfo"
                        lazyLoad="false"/>
                </div>
            </tags:imPanel>
        </td>
        <td style="width:10px"></td>
        <td style="vertical-align:top">
            <sx:div id="divDisplayInfo">
                <jsp:include page="listShops.jsp"/>
            </sx:div>
        </td>
    </tr>
</table>
