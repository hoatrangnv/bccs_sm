<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : ExportStockToStaff
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

<s:form action="exportStockToStaffAction" theme="simple" enctype="multipart/form-data"  method="POST" id="exportStockForm">
<s:token/>

    <tags:ExportStockCmd shopRecType="staff" type="note" subForm="exportStockForm"/>
    <s:hidden name="exportStockForm.canPrint"/>
</s:form>

<div id="inputGoods">
    <jsp:include page="../../Common/Goods.jsp"/>
    <%--jsp:param name="inputSerial" value="true"/>
    </jsp:include--%>
</div>
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
                     preAction="exportStockToStaffAction!printExpNote.do?type=toStaff"/>
    </s:if>
    <s:else>
        <tags:submit confirm="true" confirmText="MSG.confirm.export.store"  formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="MSG.expStock"
                     validateFunction="validateForm1()"
                     preAction="exportStockToStaffAction!expStock.do"/>
        <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.201'))}" disabled/>
    </s:else>




    <tags:submit formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="${fn:escapeXml(imDef:imGetText('MSG.reset'))}"
                 preAction="exportStockToStaffAction!clearForm.do?type=toStaff"/>
    <br/>
    <s:if test="exportStockForm.exportUrl !=null && exportStockForm.exportUrl!=''">
        <script>
            window.open('${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
        </script>
        <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
    </s:if>
    <tags:displayResult id="resultCreateExpCmdClient" property="resultCreateExp" type="key"/>
</div>


<script>
    validateForm1 =function(){
        return validateForm("resultCreateExpCmdClient");
    }
    /*
    dojo.event.topic.subscribe("ExportAction/expStock", function(event, widget){
        if(!validateForm("resultCreateExpCmdClient")){
            event.cancel=true;
        }
        widget.href = "exportStockToStaffAction!expStock.do";
    });
    dojo.event.topic.subscribe("ExportAction/printExpNote", function(event, widget){
        widget.href = "exportStockToStaffAction!printExpNote.do?type=toStaff";
    });
    dojo.event.topic.subscribe("ExportAction/clearForm", function(event, widget){
        widget.href = "exportStockToStaffAction!clearForm.do?type=toStaff";
    });
     */
</script>
