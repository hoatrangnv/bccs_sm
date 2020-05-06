<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : StaffExportStockToShop
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("contextPath", request.getContextPath());

%>


<s:form action="StaffExportStockToShop" theme="simple" enctype="multipart/form-data"  method="POST" id="exportStockForm">
<s:token/>


    
    <s:hidden name="exportStockForm.shopExpType" value="2" id="exportStockForm.shopExpType"/>
    <tags:ExportStockCmd shopRecType="staff_exp_to_shop" type="note" subForm="exportStockForm"/>
    <s:hidden name="exportStockForm.canPrint"/>
</s:form>

<div id="inputGoods">
    <jsp:include page="../../Common/Goods.jsp"/>
</div>
<%--script>
    var hrefViewStockDetail =document.getElementById("hrefViewStockDetail");
    if(hrefViewStockDetail){
        hrefViewStockDetail.href='javascript:viewStockDetail("${contextPath}/ViewStockDetailAction!prepareViewStockDetail.do?ownerType=2&ownerId=<s:property escapeJavaScript="true"  value="#session.userToken.getUserID()"/>")'
    }

</script--%>
<div style="width:100%" align="center">
    <%--sx:submit parseContent="true" executeScripts="true"
               formId="exportStockForm" loadingText="loading..." showLoadingText="true"
               targets="bodyContent"  value="Xuất kho" errorNotifyTopics="errorAction"  beforeNotifyTopics="ExportAction/expStock"/>

    <sx:submit parseContent="true" executeScripts="true" formId="exportStockForm"
               loadingText="loading..." showLoadingText="true" targets="bodyContent"  value="In phiếu xuất"
               errorNotifyTopics="errorAction"
               beforeNotifyTopics="ExportAction/printExpNote"/>

    <sx:submit parseContent="true" executeScripts="true" formId="exportStockForm"
               loadingText="loading..." showLoadingText="true" targets="bodyContent"  value="   Reset   "
               errorNotifyTopics="errorAction"
               beforeNotifyTopics="ExportAction/clearForm"/--%>
    <s:if test="exportStockForm.canPrint==1">
        <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.200'))}" disabled/>
        <tags:submit formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="MSG.print.export.note"
                     preAction="StaffExportStockToShop!printExpNote.do?type=toShop"/>
    </s:if>
    <s:else>
        <tags:submit confirm="true" confirmText="MSG.confirm.export.store"  formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="MSG.expStock"
                     validateFunction="validateForm1()"
                     preAction="StaffExportStockToShop!staffExpStock.do"/>
        <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.201'))}" disabled/>
    </s:else>

    <tags:submit formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="MSG.reset"
                 preAction="StaffExportStockToShop!clearForm.do?type=toShop"/>
    <br/>
    <s:if test="exportStockForm.exportUrl !=null && exportStockForm.exportUrl!=''">
        <script>
            window.open('${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
        </script>
        <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
    </s:if>
    <tags:displayResult id="resultCreateExpCmdClient" property="resultCreateExp" propertyValue="resultCreateExpParams" type="key"/>
    <tags:displayResultList property="lstError" type="key"/>
</div>

<script>
    validateForm1=function(){
        return validateForm("resultCreateExpCmdClient");
    }
    /*
    dojo.event.topic.subscribe("ExportAction/expStock", function(event, widget){
        if(!validateForm("resultCreateExpCmdClient")){
            event.cancel=true;
        }
        widget.href = "StaffExportStockToShop!staffExpStock.do";
    });
    dojo.event.topic.subscribe("ExportAction/printExpNote", function(event, widget){
        widget.href = "StaffExportStockToShop!printExpNote.do?type=toShop";
    });
    dojo.event.topic.subscribe("ExportAction/clearForm", function(event, widget){
        widget.href = "StaffExportStockToShop!clearForm.do?type=toShop";
    });
     */
</script>
