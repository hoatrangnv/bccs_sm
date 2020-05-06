<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ExportStockToCollaborator
    Created on : Sep 8, 2009, 10:38:48 AM
    Author     : Vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<s:form action="exportStockToCollaboratorAction" theme="simple" enctype="multipart/form-data"  method="POST" id="exportStockForm">
<s:token/>


    <tags:ExportStockCmd shopRecType="Collaborator" type="note" subForm="exportStockForm"/>

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
                     preAction="exportStockToCollaboratorAction!printExpNote.do?type=toStaff"/>
    </s:if>
    <s:else>
<!--        Xuất kho-->
        <tags:submit confirm="true" confirmText="MSG.confirm.export.store"  formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="MSG.GOD.200"
                     validateFunction="validateForm1()"
                     preAction="exportStockToCollaboratorAction!expStock.do"/>
        <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.201'))}" disabled/>
    </s:else>




    <tags:submit formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="MSG.reset"
                 preAction="exportStockToCollaboratorAction!clearForm.do?type=toStaff"/>
    <br/>
    <s:if test="exportStockForm.exportUrl !=null && exportStockForm.exportUrl!=''">
        <script>
            window.open('${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
        </script>
        <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
    </s:if>
    <tags:displayResult id="resultCreateExpCmdClient" property="resultCreateExp" type="key"/>
    <tags:displayResultList property="lstError" type="key"/>
</div>


<script>
    validateForm1 =function(){
        //return true;
        return validateFormColl("resultCreateExpCmdClient");
        
    }
    /*
    dojo.event.topic.subscribe("ExportAction/expStock", function(event, widget){
        if(!validateForm("resultCreateExpCmdClient")){
            event.cancel=true;
        }
        widget.href = "exportStockToCollaboratorAction!expStock.do";
    });
    dojo.event.topic.subscribe("ExportAction/printExpNote", function(event, widget){
        widget.href = "exportStockToCollaboratorAction!printExpNote.do?type=toStaff";
    });
    dojo.event.topic.subscribe("ExportAction/clearForm", function(event, widget){
        widget.href = "exportStockToCollaboratorAction!clearForm.do?type=toStaff";
    });
     */
</script>
