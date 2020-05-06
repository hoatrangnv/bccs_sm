<%-- 
    Document   : discountGroupInfo.jsp
    Created on : Feb 2, 2010, 2:55:32 PM
    Author     : BSS_ctv_AnDV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>


<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("pageId", request.getParameter("pageId"));
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
    <sx:div key="MSG.LST.025" id="sxdivDiscountGroup" cssStyle="height: 575px; overflow:auto; ">
        <jsp:include page="discountGroup.jsp"/>
    </sx:div>
    <sx:div key="MSG.LST.026" id="sxdivDiscountDetail" href="%{#request.contextPath + '/discountGroupAction!displayDiscountDetail.do?pageId=' + #request.pageId}" preload="false" refreshOnShow="true" cssStyle="height: 575px; overflow:auto; ">
        <jsp:include page="discountDetail.jsp"/>
    </sx:div>
    <sx:div key="MSG.LST.027" id="sxdivDiscountGoods" href="%{#request.contextPath + '/discountGroupAction!displayDiscountGoods.do?pageId=' + #request.pageId}" preload="false" refreshOnShow="true" cssStyle="height: 575px; overflow:auto; ">
        <jsp:include page="discountGoods.jsp"/>
    </sx:div>
</sx:tabbedpanel>

<s:if test="#attr.discountGroupForm.discountGroupId.compareTo(0L) <= 0">
    <script language="javscript">
        enableTab('sxdivDiscountGroup');
        disableTab('sxdivDiscountDetail');
        disableTab('sxdivDiscountGoods');
    </script>
</s:if>
