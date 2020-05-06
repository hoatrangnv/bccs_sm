<%-- 
    Document   : fineItemInfo
    Created on : Mar 1, 2010, 5:23:48 PM
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
    <sx:div label="Thông tin lý do phạt" id="sxdivFineItem" cssStyle="height: 575px; overflow:auto; ">
        <jsp:include page="fineItems.jsp"/>
    </sx:div>
    <sx:div label="Phí lý do phạt" id="sxdivFineItemPrice" refreshOnShow="true" cssStyle="height: 575px; overflow:auto; ">
        <jsp:include page="fineItemsPrice.jsp"/>
    </sx:div>

</sx:tabbedpanel>

