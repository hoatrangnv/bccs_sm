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
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<s:if test="importStockForm.cmdImportCode != null && importStockForm.cmdImportCode != '' ">
    <!-- LeVT1 start R499 -->
    <div class="x-panel-mc">
        <br/>
        <table style="width:100%">
        <tr>
            <td>
                <s:checkbox id="importStockForm.chkExport" key="Nh?p kho xu?t di?u chuy?n" name="importStockForm.chkExport"/>
            </td>
        </tr>
    </table>
    </div>
    <!-- LeVT1 end R499 -->
    
    <table style="width:100%">
        <tr>
            <td style="vertical-align:top;">
                <tags:ImportStock subForm="importStockForm" type="note" disabled="true" readOnly="true" viewOnly="true" height="210px"/>
            </td>
            <td style="vertical-align:top;">

                <jsp:include page="../../Common/ListGoods.jsp"/>
                
            </td>
        </tr>
    </table>

    <!-- Trang thai da xuat hang -->
    <s:if test="importStockForm.status == 2 ||importStockForm.canPrint==1 ">
        <div style="width:100%" align="center">
            <s:if test="importStockForm.canPrint!=null && importStockForm.canPrint==1">
                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.229'))}" disabled="false"/>
                <!-- tamdt1, bo sung doi voi truong hop lap lenh nhap tu nhan vien -->
                <s:if test="#request.isImpFromStaff == true">
                    <tags:submit confirm="false" confirmText="MSG.confirm.print.note"
                                 formId="importStockForm"
                                 preAction="importStockFromStaffAction!printImpAction_1.do"
                                 showLoadingText="true"
                                 value="MSG.print.votes"
                                 targets="bodyContent"/>
                </s:if>
                <s:else>
                    <tags:submit confirm="false" confirmText="MSG.confirm.print.note" formId="importStockForm" preAction="ImportStockUnderlyingAction!printImpAction.do" showLoadingText="true"
                             value="MSG.print.votes" targets="bodyContent"/>
                </s:else>
            </s:if>
            <s:else>
                <!-- tamdt1, bo sung doi voi truong hop lap lenh nhap tu nhan vien -->
                <s:if test="#request.isImpFromStaff == true">
                    <tags:submit confirm="true" confirmText="MSG.confirm.import.store"
                                 formId="importStockForm"
                                 preAction="importStockFromStaffAction!impStock_1.do"
                                 showLoadingText="true"
                                 value="MSG.importToStore"
                                 targets="bodyContent"/>
                </s:if>
                <s:else>
                    <tags:submit confirm="true" confirmText="MSG.confirm.import.store" formId="importStockForm" preAction="ImportStockUnderlyingAction!impStock.do" showLoadingText="true"
                             value="MSG.importToStore" targets="bodyContent"/>
                </s:else>
                
                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.210'))}" disabled="false"/>
            </s:else>
        </div>
    </s:if>
</s:if>


<s:if test="importStockForm.exportUrl !=null && importStockForm.exportUrl!=''">
    <script>
        window.open('${contextPath}<s:property escapeJavaScript="true"  value="importStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
    </script>
    <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="importStockForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
</s:if>



