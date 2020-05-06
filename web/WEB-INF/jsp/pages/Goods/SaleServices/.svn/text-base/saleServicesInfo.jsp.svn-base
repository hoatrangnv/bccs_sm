<%-- 
    Document   : saleServicesInfo
    Created on : Nov 2, 2009, 8:45:24 AM
    Author     : Doan Thanh 8
    Desc       : thong tin ve DVBH = thong tin ban than DVBH + thong tin gia DV + thong tin mat hang thuoc DV
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
    <%--sx:div label="Thông tin DVBH" id="sxdivSaleServices" cssStyle="height: 575px; overflow:auto; "--%>
    <sx:div key="MSG.GOD.107" id="sxdivSaleServices" cssStyle="height: 575px; overflow:auto; ">
        <jsp:include page="saleServices.jsp"/>
    </sx:div>
    <sx:div key="MSG.GOD.304" id="sxdivSaleServicesPrice" href="%{#request.contextPath + '/saleServicesAction!displaySaleServicesPrice.do'}" preload="false" refreshOnShow="true" cssStyle="height: 575px; overflow:auto; ">
        <jsp:include page="saleServicesPrice.jsp"/>
    </sx:div>
    <sx:div key="MSG.GOD.305" id="sxdivSaleServicesModel" href="%{#request.contextPath + '/saleServicesAction!displaySaleServicesModel.do'}" preload="false" refreshOnShow="true" cssStyle="height: 575px; overflow:auto; ">
        <jsp:include page="saleServicesModel.jsp"/>
    </sx:div>
</sx:tabbedpanel>

<s:if test="#attr.saleServicesForm.saleServicesId.compareTo(0L) <= 0">
    <script language="javscript">
        enableTab('sxdivSaleServices');
        disableTab('sxdivSaleServicesPrice');
        disableTab('sxdivSaleServicesModel');
    </script>
</s:if>




