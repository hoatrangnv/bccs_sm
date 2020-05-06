<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : goodsProfileOverview
    Created on : Apr 2, 2009, 8:19:22 AM
    Author     : tamdt1
    Desc       : man hinh tong quat ve cac goodsProfile
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
    .divGPOLeft {
        width: 265px;
        float: left;
        border: 1px #99BBE8 inset;
        padding: 3px;
    }
    .divGPORight {
        margin-left: 275px;
        border: 1px #99BBE8 inset;
        padding: 3px;
    }
    .clearStyle {
        clear:both;
    }
</style>

<!-- man hinh tong quat, chia thanh 2 vung: ben trai la tree va ben phai la thong tin mat hang -->
<%--<tags:imPanel title="Tạo profile mặt hàng">--%>
<tags:imPanel title="MSG.GOD.067">
    <div>
        <div class="divGPOLeft">
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.068'))}
<!--                    Danh sách profile-->
                </legend>
                <div align="left" style="height:575px; width:250px; overflow:auto; margin-left:3px; margin-top:3px; ">
                    <tags:treeAjax
                        actionParamName="nodeId"
                        getNodeAction="${contextPath}/profileAction!getGoodsProfileTree.do"
                        rootId="0_"
                        rootTitle="${fn:escapeXml(imDef:imGetText('MSG.GOD.040'))}"
                        rootLink="${contextPath}/profileAction!displayAllProfile.do"
                        containerId="container111"
                        containerTextId="holder111"
                        loadingText="Loading..."
                        treeId="tree111"
                        target="divDisplayInfo"
                        lazyLoad="false"/>
                </div>
            </fieldset>
        </div>
        <div class="divGPORight">
            <div align="left" style="height:600px; overflow:auto;">
                <%--sx:div id="divDisplayInfo" href="profileAction!listStockTypes.do"
                    showLoadingText="true" loadingText="Đang tải dữ liệu..."/--%>
                <sx:div id="divDisplayInfo">
                    <jsp:include page="searchProfile.jsp"/>
                </sx:div>
            </div>
        </div>
        <div class="clearStyle"></div>
    </div>
</tags:imPanel>

