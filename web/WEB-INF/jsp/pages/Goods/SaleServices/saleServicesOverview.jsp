<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : saleServicesOverview
    Created on : Mar 12, 2009, 2:35:19 PM
    Author     : tamdt1
--%>

<%--
    Note: man hinh tong quat ve cac dich vu hang hoa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
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

<tags:imPanel title="MSG.GOD.114">
    <div>
        <div class="divSSOLeft">
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.115'))}
<!--                    Danh sách DVBH-->
                </legend>
                <div align="left" style="height:575px; width:250px; overflow:auto; margin-left:3px; margin-top:3px; ">
                    <tags:treeAjax
                        actionParamName="nodeId"
                        getNodeAction="${contextPath}/saleServicesAction!getSaleServicesTree.do"
                        rootId="0_"
                        rootTitle="${fn:escapeXml(imDef:imGetText('MSG.GOD.116'))}"
                        rootLink="${contextPath}/saleServicesAction!searchSaleServices.do"
                        containerId="container111"
                        containerTextId="holder111"
                        loadingText="Loading..."
                        treeId="tree111"
                        target="divDisplayInfo"
                        lazyLoad="false"/>
                </div>
            </fieldset>
        </div>
        <div class="divSSORight">
            <div align="left" style="height:600px; overflow:auto;">
                <sx:div id="divDisplayInfo">
                    <jsp:include page="searchSaleServices.jsp"/>
                </sx:div>
            </div>
        </div>
        <div class="clearStyle"></div>
    </div>
</tags:imPanel>

