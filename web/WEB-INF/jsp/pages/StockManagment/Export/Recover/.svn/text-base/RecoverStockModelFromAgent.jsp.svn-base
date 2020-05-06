<%-- 
    Document   : RecoverStockModelFromAgent
    Created on : Sep 23, 2009, 4:25:22 PM
    Author     : Vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<s:form action="RecoverStockModelFromAgentAction" theme="simple" enctype="multipart/form-data"  method="POST" id="exportStockForm">
<s:token/>

    <tags:RecoverStockModel shopRecType="Collaborator" type="note" subForm="exportStockForm"/>
    <s:hidden name="exportStockForm.canPrint"/>
</s:form>

<div id="inputGoods">
    <jsp:include page="../../Common/GoodsRecover.jsp"/>
    <%--jsp:param name="inputSerial" value="true"/>
    </jsp:include--%>
</div>
<div style="width:100%" align="center"> 
    <s:if test="exportStockForm.canPrint==1">
        <input type="button" value='<s:text name="MSG.GOD.208"/>' disabled/>
        <tags:submit formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="MSG.printImpNote"
                     preAction="RecoverStockModelFromAgentAction!printExpNote.do?type=toStaff"/>
    </s:if>
    <s:else>
        <tags:submit confirm="true" confirmText="MSG.confirm.import.store"  
                     formId="exportStockForm"
                     showLoadingText="true"
                     targets="bodyContent" value="MSG.GOD.208"
                     validateFunction="validateForm1()"
                     preAction="RecoverStockModelFromAgentAction!createRecoverNote.do"/>
        <input type="button" value='<s:text name="MSG.GOD.209"/>' disabled/>
    </s:else>
    <tags:submit formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="MSG.reset"
                 preAction="RecoverStockModelFromAgentAction!clearForm.do?type=toStaff"/>
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

    validateForm1 =function(){
        return validateForm("resultCreateExpCmdClient");
    }
    /*
    dojo.event.topic.subscribe("ExportAction/expStock", function(event, widget){
        if(!validateForm("resultCreateExpCmdClient")){
            event.cancel=true;
        }
        widget.href = "RecoverStockModelFromAgentAction!expStock.do";
    });
    dojo.event.topic.subscribe("ExportAction/printExpNote", function(event, widget){
        widget.href = "RecoverStockModelFromAgentAction!printExpNote.do?type=toStaff";
    });
    dojo.event.topic.subscribe("ExportAction/clearForm", function(event, widget){
        widget.href = "RecoverStockModelFromAgentAction!clearForm.do?type=toStaff";
    });
     */
</script>
