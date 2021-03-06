<%-- 
    Document   : PackageGoodsNewInfo
    Created on : Sep 27, 2010, 10:29:45 AM
    Author     : User
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

<script type="text/javascript">
    //Enable/Disable tabs using JavaScript
    //source: http://struts.apache.org/2.0.9/docs/ajax-and-javascript-recipes.html#AjaxandJavaScriptRecipes-TabbedPanel
    enableTab = function (param) {
        var tabContainer = dojo.widget.byId('tabContainer');
        tabContainer.enableTab(param);
    }

    disableTab = function (param) {
        var tabContainer = dojo.widget.byId('tabContainer');
        tabContainer.disableTab(param);
    }
</script>

<sx:tabbedpanel id="tabContainer" cssStyle="font-size: 13px;">
    <sx:div key="MSG.GOD.293" id="sxdivListPackageGoods" cssStyle="height: 450px; overflow:auto; ">
        <jsp:include page="PackageGoodsNew.jsp"/>        
    </sx:div>
    <sx:div key="MSG.GOD.295" id="sxdivPackageGoodsModelList" cssStyle="height: 450px; overflow:auto; ">
        <jsp:include page="ListPackageGoodsModelsNew.jsp"/>
    </sx:div>
</sx:tabbedpanel>
