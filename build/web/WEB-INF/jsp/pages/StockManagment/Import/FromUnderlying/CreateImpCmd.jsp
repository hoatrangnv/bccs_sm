

<%--
    Document   : ExportStockToUnderlyingNote
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<tags:displayResult id="msgExpStock" property="msgExpStock" type="key"/>
<s:if test="exportStockForm.cmdExportCode != null">
    <table style="width:100%">
        <tr>
            <td style="vertical-align:top;">
                <tags:ExportStockCmd subForm="exportStockForm" shopRecType="shop_exp_to_senior" readOnly="true" disabled="true" viewOnly="true" type="note" height="210px"/>
            </td>
            <td style="vertical-align:top;">

                <jsp:include page="../../Common/ListGoods.jsp"/>

            </td>
        </tr>
    </table>
    <!-- Trang thai da xuat hang -->
    <s:if test="exportStockForm.status == 3">
        <tags:ImportStock subForm="importStockForm"/>
        <s:hidden name="importStockForm.canPrint"/>
        <div style="width:100%" align="center">       
            <s:if test="importStockForm.canPrint!=null && importStockForm.canPrint==1">
                <input type="button" value="<s:text name = "MSG.GOD.231"/>" disabled/>
                <!-- tamdt1, bo sung doi voi truong hop lap lenh nhap tu nhan vien -->
                <s:if test="#request.isImpFromStaff == true">
                    <tags:submit formId="importStockForm"
                                 showLoadingText="true"
                                 targets="bodyContent"
                                 value="MSG.printImportNote"
                                 preAction="importStockFromStaffAction!printImpCmd_1.do"/>
                </s:if>
                <s:else>
                    <tags:submit formId="importStockForm" showLoadingText="true" targets="bodyContent" value="MSG.printImportNote"
                             preAction="ImportStockUnderlyingAction!printImpCmd.do"/>
                </s:else>
            </s:if>
            <s:else>
                <!-- tamdt1, bo sung doi voi truong hop lap lenh nhap tu nhan vien -->
                <s:if test="#request.isImpFromStaff == true">
                    <tags:submit confirm="true"
                                 confirmText="MSG.confirm.create.cmd.import.store"
                                 formId="importStockForm"
                                 showLoadingText="true"
                                 targets="bodyContent"
                                 value="MSG.GOD.231"
                                 validateFunction="validateForm1()"
                                 preAction="importStockFromStaffAction!createImpCmd_1.do"/>
                </s:if>
                <s:else>
                    <tags:submit confirm="true" confirmText="MSG.confirm.create.cmd.import.store" formId="importStockForm" showLoadingText="true" targets="bodyContent" value="MSG.createImportCmd"
                             validateFunction="validateForm1()"
                             preAction="ImportStockUnderlyingAction!createImpCmd.do"/>
                </s:else>

                
                <input type="button" value="<s:text name = "MSG.GOD.232"/>" disabled/>

            </s:else>



        </div>
        <div align="center">
            <s:if test="importStockForm.exportUrl !=null && importStockForm.exportUrl!=''">
                <script>
                    window.open('${contextPath}<s:property escapeJavaScript="true"  value="importStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                </script>
                <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="importStockForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
            </s:if>
        </div>
    </s:if>

    <tags:displayResult id="resultCreateImpCmdClient" property="resultCreateImp" type="key"/>
    <tags:displayResultList property="lstError" type="key"/>

</s:if>

<script>
    //tamdt1, thay the ham validateForm1 ben duoi
    validateForm1= function(){
        var noteImpCode = $('importStockForm.cmdImportCode');
        if(noteImpCode.value ==null || trim(noteImpCode.value)==''){
            $('resultCreateImpCmdClient').innerHTML= '<s:text name="ERR.STK.048"/>';
            //$('resultCreateImpCmdClient').innerHTML='Mã lệnh nhập không được để trống';
            noteImpCode.focus();
            return false;
        }
        noteImpCode.value=trim(noteImpCode.value);
        if(!isFormalCharacter(noteImpCode.value)){
            $('resultCreateImpCmdClient').innerHTML= '<s:text name="ERR.STK.049"/>';
            //$('resultCreateImpCmdClient').innerHTML='Mã lệnh nhập không được chứa các ký tự đặc biệt';
            noteImpCode.focus();
            return false;
        }
        var reasonId = $('importStockForm.reasonId');
        if(reasonId.value ==null || trim(reasonId.value)==''){
            $('resultCreateImpCmdClient').innerHTML= '<s:text name="ERR.STK.050"/>';
            //$('resultCreateImpCmdClient').innerHTML='Chọn lý do nhập';
            reasonId.focus();
            return false;
        }

        return true;
    }



</script>

