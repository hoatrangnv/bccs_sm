<%--
    Document   : stockModelOverview
    Created on : Sep 23, 2009, 3:04:11 PM
    Author     : Doan Thanh 8
    Desc       : thong tin mat hang (bao gom thong tin mat hang + thong tin gia + thong tin dat coc)
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
    <sx:div key="MSG.GOD.103" id="sxdivStockModel" cssStyle="height: 575px; overflow:auto; ">
        <jsp:include page="stockModel.jsp"/>
    </sx:div>
    <sx:div key="MSG.priceGoods" id="sxdivStockPrice" href="%{#request.contextPath + '/goodsDefAction!displayPrice.do'}" preload="false" refreshOnShow="true" cssStyle="height: 575px; overflow:auto; ">
        <jsp:include page="stockPrice.jsp"/>
    </sx:div>
    <sx:div key="MSG.GOD.269" id="sxdivStockDeposit" href="%{#request.contextPath + '/goodsDefAction!displayStockDeposit.do'}" preload="false" refreshOnShow="true" cssStyle="height: 575px; overflow:auto; ">
        <jsp:include page="stockDeposit.jsp"/>
    </sx:div>
</sx:tabbedpanel>

<s:if test="#attr.stockModelForm.stockModelId.compareTo(0L) <= 0">
    <script language="javscript">
        enableTab('sxdivStockModel');
        disableTab('sxdivStockPrice');
        disableTab('sxdivStockDeposit');
    </script>
</s:if>
