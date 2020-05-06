<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : packageGoodsOverview.jsp
    Created on : 17/09/2009
    Author     : ThanhNC
--%>

<%--
    Note: man hinh tong quat ve cac goi hang hoa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>

<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("displayInfo", request.getSession().getAttribute("displayInfo"));
        request.getSession().removeAttribute("displayInfo");
%>
<style>
    .divSSOLeft {
        width: 265px;
        float: left;
        border: 1px #99BBE8 inset;
        padding: 3px;
    }
    .divSSORight {
        margin-left: 275px;
        border: 1px #99BBE8 inset;
        padding: 3px;
    }
    .clearStyle {
        clear:both;
    }
</style>

<tags:imPanel title="MSG.define.package.goods">
    <div>
        <div class="divSSOLeft">
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.ISN.039'))}</legend>
                <div align="left" style="height:575px; width:250px; overflow:auto; margin-left:3px; margin-top:3px; ">
                    <tags:treeAjax
                        actionParamName="nodeId"
                        getNodeAction="${contextPath}/packageGoodsAction!getPackageGoodsTree.do"
                        rootId="0_"
                        rootTitle="${fn:escapeXml(imDef:imGetText('MSG.all.package.goods'))}"
                        rootLink="${contextPath}/packageGoodsAction!searchPackageGoods.do"
                        containerId="container"
                        containerTextId="holder"
                        loadingText="%{getText('MSG.loading')}"
                        treeId="tree"
                        target="divDisplayInfo"
                        lazyLoad="false"/>
                </div>
            </fieldset>
        </div>
        <div class="divSSORight">
            <div align="left" style="height:500px; overflow:auto;">
                <sx:div id="divDisplayInfo">
                    <jsp:include page="listPackageGoods.jsp"/>
                </sx:div>
            </div>
        </div>
        <div class="clearStyle"></div>
    </div>
</tags:imPanel>


<%--
<table style="width:100%; background-color:#F5F5F5;">
<tr>
<td style="width:250px; vertical-align:top">
<tags:imPanel title="Các gói dịch vụ">
<div align="left" style="height:600px; overflow:scroll">
<tags:treeAjax
actionParamName="nodeId"
getNodeAction="${contextPath}/packageGoodsAction!getPackageGoodsTree.do"
rootId="0_"
rootTitle="Tất cả các gói hàng"
rootLink="${contextPath}/packageGoodsAction!searchPackageGoods.do"
containerId="container"
containerTextId="holder"
loadingText="%{getText('MSG.loading')}"
treeId="tree"
target="divDisplayInfo"
lazyLoad="false"/>
</div>
</tags:imPanel>
</td>
<td style="width:1px"></td>
<td style="vertical-align:top">
<sx:div id="divDisplayInfo">
<jsp:include page="listPackageGoods.jsp"/>
</sx:div>
</td>
</tr>
</table>
--%>
