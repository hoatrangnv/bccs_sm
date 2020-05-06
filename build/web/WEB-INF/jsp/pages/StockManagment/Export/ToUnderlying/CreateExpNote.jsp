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

<s:if test="exportStockForm.cmdExportCode != null && exportStockForm.cmdExportCode !='' ">
    <tags:displayResult id="resultCreateExpCmdClient" property="resultCreateExp" type="key"/><br/>
    <tags:displayResultList property="lstError" type="key"/>
    <s:hidden name="exportStockForm.canPrint"/>
    <table style="width:100%">
        <tr>
            <td style="vertical-align:top; width:50%">
                <tags:ExportStockCmd subForm="exportStockForm" readOnly="true" disabled="true" viewOnly="true" height="210px"/>
            </td>
            <td style="vertical-align:top; width:auto;">
                <jsp:include page="../../Common/ListGoods.jsp"/>
            </td>
        </tr>
    </table>
    <!--trang thai lenh ok thi cho phep lap phieu-->
    <s:if test="exportStockForm.returnMsg == 'done'">
        <%--logic:equal name="ExportStockForm" property="returnMsg" value="done"--%>
        
        <tags:imPanel title="MSG.info.export.note">
            <table class="inputTbl" style="width:100%">
                <tr>
                    <td>
                        <tags:label key="MSG.expNoteId"/>
                    </td>
                    <td>
                        <s:textfield theme="simple"  name="exportStockForm.noteExpCode" readonly="true" id="noteExpCode" cssClass="txtInput"/>
                    </td>
                    <td>
                        <%--sx:submit parseContent="true" executeScripts="true"
                                   formId="exportStockForm" loadingText="loading..." showLoadingText="true"
                                   targets="bodyContent"  value="Lập phiếu xuất"
                                   errorNotifyTopics="errorAction"
                                   beforeNotifyTopics="ExportStockForm/createExpNote"/>
                        <sx:submit parseContent="true" executeScripts="true" formId="exportStockForm"
                                   loadingText="loading..." showLoadingText="true" targets="bodyContent"  value="In phiếu xuất"
                                   errorNotifyTopics="errorAction"
                                   beforeNotifyTopics="ExportStockForm/printExpNote"/--%>
                        <s:if test="exportStockForm.canPrint !=null && exportStockForm.canPrint ==1">
                            <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.211'))}" disabled/>
                            <tags:submit id="exp" formId="exportStockForm" showLoadingText="true" targets="bodyContent"
                                         value="MSG.print.export.note"
                                         preAction="ExportStockUnderlyingAction!printExpNote.do"/>
                        </s:if>
                        <s:else>
                            <tags:submit id="exp" confirm="true" confirmText="MSG.confirm.create.export.note" formId="exportStockForm" showLoadingText="true" targets="bodyContent"
                                         value="MSG.createExportNote" validateFunction="validateForm()"
                                         preAction="ExportStockUnderlyingAction!createDeliverNote.do"/>
                            <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.201'))}" disabled/>
                        </s:else>
                    </td>
                    <td style="width:20%">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="4" align="center">
                        <s:if test="exportStockForm.exportUrl !=null && exportStockForm.exportUrl!=''">
                            <script>
                                window.open('${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                            </script>
                            <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a></div>
                        </s:if>
                    </td>
                </tr>
            </table>

        </tags:imPanel>
        <%--/logic:equal--%>
    </s:if>



</s:if>

<script>
    validateForm=function(){
        var noteExpCode= document.getElementById("noteExpCode");
        if(noteExpCode.value ==null || trim(noteExpCode.value)==''){
            //$('resultCreateExpCmdClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.STK.017')"/>';
            $('resultCreateExpCmdClient').innerHTML= '<s:text name="ERR.STK.017"/>';
            //$('resultCreateExpCmdClient').innerHTML='Chưa nhập mã phiếu xuất';
            noteExpCode.focus();
            return false;
        }
        noteExpCode.value=trim(noteExpCode.value);
        if(!isFormalCharacter(noteExpCode.value)){
              //$('resultCreateExpCmdClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.STK.018')"/>';
              $('resultCreateExpCmdClient').innerHTML= '<s:text name="ERR.STK.018"/>';
            //$('resultCreateExpCmdClient').innerHTML='Mã phiếu xuất không được chứa các ký tự đặc biệt';
            noteExpCode.focus();
            return false;
        }
        return true;
    }
<%--
dojo.event.topic.subscribe("ExportStockForm/searchExpCmd", function(event, widget){
widget.href = "ExportStockUnderlyingAction!searchExpCmd.do";
});

dojo.event.topic.subscribe("ExportStockForm/createExpNote", function(event, widget){
widget.href = "ExportStockUnderlyingAction!createDeliverNote.do";
});
dojo.event.topic.subscribe("ExportStockForm/printExpNote", function(event, widget){
widget.href = "ExportStockUnderlyingAction!printExpNote.do";
});

--%>
</script>
