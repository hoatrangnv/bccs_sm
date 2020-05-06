<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : PackageGoodsNewOverview
    Created on : Sep 27, 2010, 8:59:35 AM
    Author     : Vunt
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
            <tags:imPanel title="MSG.ISN.039">                
                <div align="left" style="height:575px; width:250px; overflow:auto; margin-left:3px; margin-top:3px; ">
                    <tags:treeAjax
                        actionParamName="nodeId"
                        getNodeAction="${contextPath}/packageGoodsNewAction!getPackageGoodsTree.do"
                        rootId="0_"
                        rootTitle="${fn:escapeXml(imDef:imGetText('MSG.all.package.goods'))}"
                        rootLink="${contextPath}/packageGoodsNewAction!searchPackageGoods.do"
                        containerId="container"
                        containerTextId="holder"
                        loadingText="%{getText('MSG.loading')}"
                        treeId="tree"
                        target="divDisplayInfo"
                        lazyLoad="false"/>
                </div>
            </tags:imPanel>
        </div>
        <div class="divSSORight">
            <div align="left" style="height:500px; overflow:auto;">
                <sx:div id="divDisplayInfo">
                    <jsp:include page="ListPackageGoodsNew.jsp"/>
                </sx:div>
            </div>
        </div>
        <div class="clearStyle"></div>
    </div>
</tags:imPanel>
