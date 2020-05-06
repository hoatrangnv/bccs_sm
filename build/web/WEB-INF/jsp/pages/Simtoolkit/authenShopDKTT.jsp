<%-- 
    Document   : authenShopDKTT
    Created on : Mar 10, 2016, 10:14:37 AM
    Author     : mov_itbl_dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>

<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<table class="inputTbl2Col" style="width:100%; background-color:#F5F5F5;">
    <tr>
        <td style="width:25px; vertical-align:top">
            <tags:imPanel title="MSG.shop.list.items">
                <div align="left" style="width:250px; height:580px; overflow:scroll; margin-left:5px; margin-top:5px;">
                    <tags:treeAjax
                        actionParamName="nodeId"
                        getNodeAction="${contextPath}/authenShopDKTTAction!getShopTree.do"
                        rootId="0_"
                        rootTitle="MSG.chanels.all"
                        rootLink="${contextPath}/authenShopDKTTAction!listShop.do"
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
                <jsp:include page="listShopDKTT.jsp"/>
            </sx:div>
        </td>
    </tr>
</table>
