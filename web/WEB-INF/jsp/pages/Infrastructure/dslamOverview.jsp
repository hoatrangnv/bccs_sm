<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : dslamOverview
    Created on : Jun 15, 2009, 10:31:46 AM
    Author     : TrongLV
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


<tags:imPanel title="L.100002">
    <div>
        <div class="divSSOLeft">
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.items'))}</legend>
                <div align="left" style="height:575px; width:250px; overflow:auto; margin-left:3px; margin-top:3px; ">
                    <tags:treeAjax actionParamName="nodeId"
                                   getNodeAction="${contextPath}/dslamAction!getListDslamTree.do"
                                   rootId="9_"
                                   rootTitle="MSG.all.dslam_dlu"
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
<!--                    <.jsp:include page="dslam.jsp"/>-->
                </sx:div>
            </div>
        </div>
        <div class="clearStyle"></div>
    </div>
</tags:imPanel>

