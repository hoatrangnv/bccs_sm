<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%--
    Document   : ExportStockToUnderlyingNote
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<s:hidden name="importStockForm.canPrint"/>
<s:if test="exportStockForm.cmdExportCode != null">
    <s:hidden name="exportStockForm.canPrint"/>
    <table style="width:100%">
        <tr>
            <td style="vertical-align:top; width:50%">
                <tags:ExportStockCmd subForm="exportStockForm" shopRecType="staff_exp_to_shop" readOnly="true" disabled="true" viewOnly="true" type="note" height="210px"/>
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
            <%--sx:submit parseContent="true" executeScripts="true"
                       formId="importStockForm" loadingText="loading..." showLoadingText="true"
                       targets="bodyContent"  errorNotifyTopics="errorAction" value="Nhập kho"  beforeNotifyTopics="ImportStockForm/impStockFromStaff"/>
            <sx:submit parseContent="true" executeScripts="true" formId="importStockForm"
                       loadingText="loading..." showLoadingText="true" targets="bodyContent"  value="In phiếu nhập"
                       errorNotifyTopics="errorAction"
                       beforeNotifyTopics="ImportStockForm/printImpNote"/--%>
            <s:if test="importStockForm.canPrint==1">
                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.229'))}" disabled/>
                <tags:submit formId="importStockForm" preAction="importStockFromStaffAction!printImpNote.do?impType=fromStaff" showLoadingText="true"
                             targets="bodyContent" value="MSG.printImpNote"/>
            </s:if>
            <s:else>

                <tags:submit confirm="true" confirmText="MSG.confirm.import.store"  formId="importStockForm" preAction="importStockFromStaffAction!impStockFromStaff.do" showLoadingText="true"
                             validateFunction="validateForm1()" targets="bodyContent" value="MSG.importToStore"/>
                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.209'))}" disabled/>
            </s:else>
        </div>
    </s:if>
    <div align="center" style="width:100%">

        <s:if test="importStockForm.exportUrl !=null && importStockForm.exportUrl!=''">
            <script>
                window.open('${contextPath}<s:property escapeJavaScript="true"  value="importStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
            </script>
            <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="importStockForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>

        </s:if>
    </div>
</s:if>

<script>
    validateForm1 = function(){
        return validateForm("resultCreateExpCmdClient");
    }
</script>

