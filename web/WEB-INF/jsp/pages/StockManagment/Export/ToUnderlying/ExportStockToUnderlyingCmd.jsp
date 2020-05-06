<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ExportStockToPartnerCmd
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
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>


<s:form action="ExportStockUnderlyingAction" theme="simple" enctype="multipart/form-data"  method="POST" id="ExportStockForm">
<s:token/>

    <!-- LeVT1 start R499 -->
    <tags:imPanel title="MSG.R499.02">
        <table class="inputTbl4Col"> 
            <tr>
                <td>
                    <s:checkbox id="chkOtherCmd" name="exportStockForm.chkOtherCmd" onchange="changeChkOtherCmd()"/> 
                </td>
                <td>
                    <tags:label key="MSG.R499.01"/>
                </td>

            </tr>
        </table>
    </tags:imPanel>
    <s:if test="exportStockForm.chkOtherCmd==true">
        <tags:ExportStockOtherCmd subForm="exportStockForm" />
    </s:if>
    <s:else>
        <tags:imPanel title="MSG.500.13.Title">
            <tags:displayResult id="resultCreateExpCmdAuto" property="resultCreateExpAuto" type="key"/>
            <table class="inputTbl4Col">
                <tr>
                    <td >
                        <s:if test="exportStockForm.chkExport==true">
                            <s:checkbox id="chkExportId" name="exportStockForm.chkExport" onchange="" disabled="true"/> 
                            <s:hidden name="exportStockForm.chkExport" />
                        </s:if>
                        <s:else>
                            <s:checkbox id="chkExportId" name="exportStockForm.chkExport" onchange="changeChkExport()"/> 
                        </s:else>
                    </td>
                    <td>
                        <tags:label key="MSG_R50013_label"/>
                    </td>

                </tr>
            <tr id ="createExportCmdAutoId">
                <td class="label">
                    <tags:label key="MSG.transaction.code" required="true"/>
                </td>                    
                <td class="text">
                    <s:textfield id="inputExpNoteCodeId" name="exportStockForm.inputExpNoteCode" theme="simple" cssClass="txtInput" size="20"/>                        
                </td>       
                <td class="text">
                    <tags:submit formId="ExportStockForm" showLoadingText="true" targets="bodyContent"
                                 value="MSG.500.13.Submit" validateFunction="createExportCmdAuto()"
                                 preAction="ExportStockUnderlyingAction!createExportCmdAuto.do"/>
                </td>
            </tr>
        </table>

    </tags:imPanel>

    <s:if test="exportStockForm.chkExport==true">
        <tags:ExportStockCmd subForm="exportStockForm" type="command" disabled="true"/>
    </s:if>
    <s:else>
        <tags:ExportStockCmd subForm="exportStockForm" type="command"/>
        </s:else>
    </s:else>
    <!-- LeVT1 end R499 -->

</s:form>
<div id="inputGoods">
    <jsp:include page="../../Common/Goods.jsp"/>
</div>
<div style="width:100%" align="center">

    

    <s:if test="exportStockForm.actionId !=null">
        <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.createExportCmd'))}" disabled style="width: 150px;"/>
        
        <tags:submit id="print" formId="ExportStockForm" showLoadingText="true" targets="bodyContent"
                     value="MSG.printExportCmd"  preAction="ExportStockUnderlyingAction!printExpCmd.do" cssStyle="width: 150px;"/>
    </s:if>
    <s:else>
            <tags:submit id="exp" formId="ExportStockForm" confirm="true" confirmText="MSG.confirm.create.cmd.exp.store" showLoadingText="true" targets="bodyContent"
                 value="MSG.createExportCmd" validateFunction="validateForm1()"
                 preAction="ExportStockUnderlyingAction!createDeliverCmd.do" cssStyle="width: 150px;"/>

        <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.199'))}" disabled style="width: 150px;"/>
    </s:else>
    <tags:submit id="reset" formId="ExportStockForm" showLoadingText="false" targets="bodyContent"
                 value="MSG.reset" preAction="ExportStockUnderlyingAction!clearForm.do" cssStyle="width: 100px; "/>

</div>
<br>
<s:if test="exportStockForm.exportUrl !=null && exportStockForm.exportUrl!=''">
    <script>
        window.open('${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
    </script>
    <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
</s:if>    
<tags:displayResult id="resultCreateExpCmdClient" property="resultCreateExp" propertyValue="resultCreateExpParams" type="key"/>
<tags:displayResultList property="lstError" type="key"/>
<script>
    changeChkExport = function (){           
        if (jQuery('#chkExportId').is(':checked')) { 
            $('createExportCmdAutoId').style.display = '';
        }else{
            $('createExportCmdAutoId').style.display = 'none';
        }
    }
    
    changeChkOtherCmd = function (){           
        if (jQuery('#chkOtherCmd').is(':checked')) { 
            gotoAction("bodyContent", "ExportStockUnderlyingAction!prepareCreateDeliverOtherCmd.do")
        }else{
            gotoAction("bodyContent", "ExportStockUnderlyingAction!prepareCreateDeliverCmd.do")
        }
    }
    
    changeChkExport();
    validateForm1 =function(){
        return validateForm('resultCreateExpCmdClient')
    }
    
    isRequired = function(id){
        if(jQuery.trim(jQuery(id).val()).length > 0){
            return true;
        }else{
            return false;
        }
    }

    createExportCmdAuto =function(){
        $('resultCreateExpCmdAuto').innerHTML= '';
        if(!isRequired('#inputExpNoteCodeId')){
            $('resultCreateExpCmdAuto').innerHTML= '<s:property value="getText('MSG.SIK.199')"/>';
            jQuery('#inputExpNoteCodeId').focus();
            return false;
        }
        return true;
    }

</script>
