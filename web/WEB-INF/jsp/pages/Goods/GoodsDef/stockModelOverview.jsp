<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : stockModelOverview
    Created on : Sep 23, 2009, 3:04:11 PM
    Author     : Doan Thanh 8
    Desc       : man hinh tong quan phan dinh nghia mat hang
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

<!-- man hinh tong quat, chia thanh 2 vung: ben trai la tree va ben phai la thong tin mat hang -->
<tags:imPanel title="MSG.GOD.039">
    <div>
        <div class="divSMOLeft">
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.006'))}
                    <!--                    Danh sách mặt hàng-->
                </legend>
                <div align="left" style="height:575px; width:250px; overflow:auto; margin-left:3px; margin-top:3px; ">
                    <tags:treeAjax
                        actionParamName="nodeId"
                        getNodeAction="${contextPath}/goodsDefAction!getStockModelTree.do"
                        rootId="0_"
                        rootTitle="${fn:escapeXml(imDef:imGetText('MSG.GOD.040'))}"
                        rootLink="${contextPath}/goodsDefAction!displayAllStockModel.do"
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
                    <jsp:include page="searchStockModel.jsp"/>
                </sx:div>
            </div>
        </div>
        <div class="clearStyle"></div>
    </div>
</tags:imPanel>



