<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : ImpStock.jsp
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
    Description: Lap phieu nhap kho tu cap tren
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<tags:displayResult id="msgExpStock" property="msgExpStock"  type="key"/>

<s:hidden name="importStockForm.canPrint"/>
<s:if test="exportStockForm.cmdExportCode != null">
    <table style="width:100%">
        <tr>
            <td style="vertical-align:top;">
                <tags:ExportStockCmd subForm="exportStockForm" readOnly="true" disabled="true" viewOnly="true" type="note" height="210px"/>
            </td>
            <td style="vertical-align:top;">
                <jsp:include page="../../Common/ListGoods.jsp"/>
            </td>
        </tr>
    </table>
    <!-- Trang thai da xuat hang -->
    <s:if test = "exportStockForm.status == 3">
        <tags:ImportStock type="note" subForm="importStockForm"/>
        <div style="width:100%" align="center">
            <s:if test="importStockForm.canPrint!=null && importStockForm.canPrint==1">
                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.208'))}" disabled/>
                <tags:submit formId="importStockForm" showLoadingText="true" targets="bodyContent"
                             value="MSG.printImpNote"
                             preAction="ImportStockFromSenior!printImpNote.do"/>
            </s:if>
            <s:else>

                <tags:submit confirm="true" confirmText="MSG.confirm.create.note.import.store" formId="importStockForm" showLoadingText="true" targets="bodyContent"
                             validateFunction="validateForm1()" value="MSG.createImportNote"
                             preAction="ImportStockFromSenior!createImpNote.do"/>
                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.209'))}" disabled/>
            </s:else>
            
        </div>
    </s:if>
    <div align="center" style="width:100%">
        <tags:displayResult id="resultCreateExpCmdClient" property="resultCreateExp" propertyValue="resultCreateExpParams" type="key"/>
        <tags:displayResultList property="lstError" type="key"/>
        <s:if test="importStockForm.exportUrl !=null && importStockForm.exportUrl!=''">
            <script>
                window.open('${contextPath}<s:property escapeJavaScript="true"  value="importStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
            </script>
            <div>
                <a href='${contextPath}<s:property escapeJavaScript="true"  value="importStockForm.exportUrl"/>'
                    <tags:label key="MSG.download2.file.here"/>
                </a>
            </div>

        </s:if>
    </div>
</s:if>

<script>
    validateForm1 = function(){
        return validateForm("resultCreateExpCmdClient");
    }
   
</script>
