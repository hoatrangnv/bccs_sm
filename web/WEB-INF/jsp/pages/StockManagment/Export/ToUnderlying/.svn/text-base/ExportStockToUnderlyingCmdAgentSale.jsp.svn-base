<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ExportStockToUnderlyingCmdAgentSale
    Created on : Nov 25, 2010, 2:14:57 PM
    Author     : vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>

<s:form action="ExportStockUnderlyingAction" theme="simple" enctype="multipart/form-data"  method="POST" id="ExportStockForm">
<s:token/>


    <tags:ExportStockCmdAgentSale subForm="exportStockForm"/>

</s:form>
<div id="inputGoods">
    <jsp:include page="../../Common/GoodsAgentSale.jsp"/>
</div>
<div style="width:100%" align="center">
    <s:if test="exportStockForm.actionId == null">
    <tags:submit id="exp" formId="ExportStockForm" confirm="true" confirmText="MSG.confirm.create.cmd.exp.store" showLoadingText="true" targets="bodyContent"
                 value="MSG.createExportCmd" validateFunction="validateForm1()"
                 preAction="ExportStockUnderlyingAction!createDeliverCmdAgentSale.do"/>
    </s:if>
    <s:else>
         <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.createExportCmd'))}" disabled style="width: 150px;"/>
    </s:else>


    <s:if test="exportStockForm.actionId !=null">
        <tags:submit id="print" formId="ExportStockForm" showLoadingText="true" targets="bodyContent"
                     value="MSG.printExportCmd"  preAction="ExportStockUnderlyingAction!printExpCmdAgentSale.do"/>
    </s:if>
    <s:else>
        <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.199'))}" disabled/>
    </s:else>
    <tags:submit id="reset" formId="ExportStockForm" showLoadingText="false" targets="bodyContent"
                 value="MSG.reset" preAction="ExportStockUnderlyingAction!clearFormAgentSale.do"/>

</div>
<br>
<s:if test="exportStockForm.exportUrl !=null && exportStockForm.exportUrl!=''">
    <script>
        window.open('${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
    </script>
    <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
</s:if>
<tags:displayResult id="resultCreateExpCmdClient" property="resultCreateExp" type="key"/>
<tags:displayResultList property="lstError" type="key"/>
<script>
    validateForm1 =function(){
        return validateForm('resultCreateExpCmdClient')
    }


</script>
