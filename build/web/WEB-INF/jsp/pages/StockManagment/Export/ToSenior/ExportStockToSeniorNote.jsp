<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ExportStockToSeniorNote
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
<s:form action="ExportStockSeniorAction" theme="simple" enctype="multipart/form-data"  method="POST" id="exportStockForm">
<s:token/>

    <s:hidden name="exportStockForm.canPrint"/>
    <tags:ExportStockCmd  type="note" subForm="exportStockForm" shopRecType="shop_exp_to_senior"/>
</s:form>

<div id="inputGoods">
    <jsp:include page="../../Common/Goods.jsp"/>
</div>
<div style="width:100%" align="center">
    <%--sx:submit parseContent="true" executeScripts="true"
               formId="exportStockForm" loadingText="loading..." showLoadingText="true"
               targets="bodyContent"  value="Lập phiếu xuất"  beforeNotifyTopics="ExportAction/createDeliverNote"/>

    <sx:submit parseContent="true" executeScripts="true" formId="exportStockForm"
               loadingText="loading..." showLoadingText="true" targets="bodyContent"  value="In phiếu xuất"
               errorNotifyTopics="errorAction"
               beforeNotifyTopics="ExportStockForm/printExpNote"/>

    <sx:submit parseContent="true" executeScripts="true" formId="exportStockForm"
               loadingText="loading..." showLoadingText="true" targets="bodyContent"  value="   Reset   "
               errorNotifyTopics="errorAction"
               beforeNotifyTopics="ExportStockForm/clearForm"/--%>
    <s:if test="exportStockForm.canPrint!=null && exportStockForm.canPrint==1">
        <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.211'))}" disabled/>
        <tags:submit formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="MSG.print.export.note"
                     preAction="ExportStockSeniorAction!printExpNote.do"/>
    </s:if>
    <s:else>
        <tags:submit formId="exportStockForm" confirm="true" confirmText="MSG.confirm.create.note" showLoadingText="true" targets="bodyContent" value="MSG.createExportNote"
                     validateFunction="validateForm1()"
                     preAction="ExportStockSeniorAction!createDeliverNote.do"/>
        <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.201'))}" disabled/>
    </s:else>


    <tags:submit formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="MSG.reset"
                 preAction="ExportStockSeniorAction!clearForm.do"/>
</div>
<br>
<s:if test="exportStockForm.exportUrl !=null && exportStockForm.exportUrl!=''">
    <script>
        window.open('${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
    </script>
    <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>' >
<tags:label key="MSG.download2.file.here"/></a></div>
</s:if>
<tags:displayResult id="resultCreateExpCmdClient" property="resultCreateExp" propertyValue="resultCreateExpParams" type="key"/>
<tags:displayResultList property="lstError" type="key"/>
<script>
    validateForm1= function(){
        return validateForm('resultCreateExpCmdClient');
    }
    /*
    dojo.event.topic.subscribe("ExportAction/createDeliverNote", function(event, widget){
        if(!validateForm('resultCreateExpCmdClient')){
            event.cancel = true;
        }
        widget.href = "ExportStockSeniorAction!createDeliverNote.do";

    });
    dojo.event.topic.subscribe("ExportStockForm/printExpNote", function(event, widget){
        widget.href = "ExportStockSeniorAction!printExpNote.do";
    });
    dojo.event.topic.subscribe("ExportStockForm/clearForm", function(event, widget){
        widget.href = "ExportStockSeniorAction!clearForm.do";
    });
     */
</script>
