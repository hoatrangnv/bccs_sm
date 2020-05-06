<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : discountGroupOverview
    Created on : Mar 24, 2009, 9:35:52 AM
    Author     : tamdt1
--%>

<%--
    Note: man hinh tong quat ve cac nhom chiet khau
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

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
<tags:imPanel title="MSG.GOD.280">
    <div>
        <div class="divSMOLeft">
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.281'))}</legend>
                <div align="left" style="height:575px; width:250px; overflow:auto; margin-left:3px; margin-top:3px; ">
                    <tags:treeAjax
                        actionParamName="nodeId"
                        getNodeAction="${contextPath}/getDiscountTree.do"
                        rootId="0_"
                        rootTitle=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.282'))}"
                        rootLink="${contextPath}/discountGroupAction!searchDiscountGroup.do"
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
                    <jsp:include page="searchDiscountGroup.jsp"/>
                </sx:div>
            </div>
        </div>
        <div class="clearStyle"></div>
    </div>
</tags:imPanel>
