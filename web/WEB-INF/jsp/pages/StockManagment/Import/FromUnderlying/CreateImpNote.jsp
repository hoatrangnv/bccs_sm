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
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<tags:displayResult id="msgExpStock" property="msgExpStock"  type="key"/>
<s:if test="importStockForm.cmdImportCode != null">

    <table style="width:100%">
        <tr>
            <td style="vertical-align:top;">
                <tags:ImportStock subForm="importStockForm"  disabled="true" readOnly="true" viewOnly="true" height="210px" />
            </td>
            <td style="vertical-align:top;">

                <jsp:include page="../../Common/ListGoods.jsp"/>

            </td>
        </tr>
    </table>

    <!-- Trang thai da xuat hang -->
    <s:hidden name="importStockForm.status"/>
    <s:hidden name="importStockForm.canPrint"/>
    <s:if test="importStockForm.status == 1">
        <tags:imPanel title="MSG.info.import.note">
            <table class="inputTbl" style="width:100%" cellpadding="0" cellspacing="4">
                <tr>
                    <td>
                        <tags:label key="MSG.import.bill.code"/>
                    </td>
                    <td>
                        <s:textfield theme="simple" name="importStockForm.noteImpCode" readonly="true" id="noteImpCode" cssClass="txtInput"/>
                    </td>
                    <td>                      
                        <s:if test="importStockForm.canPrint !=null && importStockForm.canPrint==1">
                            <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.203'))}" disabled/>
                            <!-- tamdt1, bo sung doi voi truong hop lap lenh nhap tu nhan vien -->
                            <s:if test="#request.isImpFromStaff == true">
                                <tags:submit formId="importStockForm"
                                             showLoadingText="true"
                                             targets="bodyContent"
                                             value="MSG.printImpNote"
                                             preAction="importStockFromStaffAction!printImpNote_1.do"/>
                            </s:if>
                            <s:else>
                                <tags:submit formId="importStockForm" showLoadingText="true" targets="bodyContent" value="MSG.printImpNote"
                                             preAction="ImportStockUnderlyingAction!printImpNote.do"/>
                            </s:else>
                        </s:if>
                        <s:else>
                            <!-- tamdt1, bo sung doi voi truong hop lap lenh nhap tu nhan vien -->
                            <s:if test="#request.isImpFromStaff == true">
                                <tags:submit confirm="true" confirmText="MSG.confirm.create.imp.note"
                                             formId="importStockForm"
                                             showLoadingText="true"
                                             targets="bodyContent" value="MSG.up.votes.create"
                                             validateFunction="validateForm1()"
                                             preAction="importStockFromStaffAction!createReceiveNote_1.do"/>
                            </s:if>
                            <s:else>
                                <tags:submit confirm="true" confirmText="MSG.confirm.create.imp.note" formId="importStockForm" showLoadingText="true" targets="bodyContent" value="MSG.up.votes.create"
                                             validateFunction="validateForm1()"
                                             preAction="ImportStockUnderlyingAction!createReceiveNote.do"/>
                            </s:else>

                            <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.209'))}" disabled/>
                        </s:else>



                    </td>
                    <td style="width:30%">&nbsp;</td>

                </tr>
                <tr>
                    <td colspan="4" align="center">
                        <s:if test="importStockForm.exportUrl !=null && importStockForm.exportUrl!=''">
                            <script>
                                window.open('${contextPath}<s:property escapeJavaScript="true"  value="importStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                            </script>
                            <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="importStockForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
                        </s:if>
                    </td>
                </tr>
            </table>
        </tags:imPanel>
    </s:if>
    <tags:displayResult id="resultCreateExpCmdClient" property="resultCreateExp" type="key"/>
    <tags:displayResultList property="lstError" type="key"/>
</s:if>

<script>
    validateForm1= function(){
        var noteImpCode =$('noteImpCode');
        if(noteImpCode.value ==null || trim(noteImpCode.value)==''){
            $('resultCreateExpCmdClient').innerHTML= '<s:text name="ERR.STK.051"/>';
            //$('resultCreateExpCmdClient').innerHTML='Mã phiếu nhập không được để trống';
            noteImpCode.focus();
            return false;
        }
        noteImpCode.value=trim(noteImpCode.value);
        if(!isFormalCharacter(noteImpCode.value)){
            $('resultCreateExpCmdClient').innerHTML= '<s:text name="ERR.STK.052"/>';
            //$('resultCreateExpCmdClient').innerHTML='Mã phiếu nhập không được chứa các ký tự đặc biệt';
            noteImpCode.focus();
            return false;
        }
        return true;
    }
</script>

