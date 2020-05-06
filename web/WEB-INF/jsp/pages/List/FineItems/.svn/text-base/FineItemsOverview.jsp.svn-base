<%--
    Document   : FineItemsOverview
    Created on : Sep 11, 2009, 4:21:43 PM
    Author     : TheTM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>
<style>
    .divSMOLeft {
        width: 265px;
        float: left;
        border: 1px #99BBE8 inset;
        padding: 3px;
    }
    .divSMORight {
        margin-left: 275px;
        border: 1px #99BBE8 inset;
        padding: 3px;
    }
    .clearStyle {
        clear:both;
    }
</style>
<tags:imPanel title="MSG.LST.016">
    <div>
        <div class="divSMOLeft">
            <fieldset class="imFieldset">
                <legend><tags:label key="MSG.list.reason.fine" /></legend>
                <div align="left" style="height:575px; width:250px; overflow:auto; margin-left:3px; margin-top:3px; ">
                    <tags:treeAjax
                        actionParamName="nodeId"
                        getNodeAction="${contextPath}/finePriceAction!getTelecomServiceTree.do"
                        rootId="0_"
                        rootTitle="MSG.all.service"
                        rootLink="${contextPath}/finePriceAction!listTelecomService.do"
                        containerId="container111"
                        containerTextId="holder111"
                        loadingText="Loading..."
                        treeId="tree111"
                        target="divDisplayInfo"
                        lazyLoad="false"/>
                </div>
            </fieldset>
        </div>
        <div class="divSMORight">
            <div align="left" style="height:600px; overflow:auto;">
                <sx:div id="divDisplayInfo">
                    <jsp:include page="listFineItem.jsp"/>
                </sx:div>
            </div>
        </div>
        <div class="clearStyle"></div>
    </div>
</tags:imPanel>

