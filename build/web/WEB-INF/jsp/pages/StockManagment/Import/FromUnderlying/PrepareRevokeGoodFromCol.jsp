<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : PrepareGetGoodFromCTV
    Created on : Oct 21, 2009, 3:24:36 PM
    Author     : LamNV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%request.setAttribute("contextPath", request.getContextPath());%>
<% String title = "Phiếu";%>
<s:form method="POST" id="importStockForm" action="ImportStockUnderlyingAction" theme="simple">
<s:token/>
    
    <!--s:hidden id="importStockForm.toOwnerId" value="%{#attr.importStockForm.toOwnerId}" name="importStockForm.toOwnerId"/-->
    <s:hidden id="importStockForm.toOwnerId" value="%{#attr.importStockForm.toOwnerId}" name="importStockForm.toOwnerId"/>
    <!--s:hidden id="importStockForm.fromOwnerId" value="%{attr.importStockForm.fromOwnerId}" name="importStockForm.fromOwnerId"/-->

    <tags:imPanel title="MSG.note">        
        <div >
            <table class="inputTbl4Col" style="width:100%">
                <tr>
                    <td class="label"><tags:label key="MSG.noteId" required="true"/></td>
                    <td class="text">
                        <s:textfield theme="simple" maxlength="20"   name="importStockForm.noteImpCode" readonly="true" id="importStockForm.noteImpCode" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.importStaff" /></td>
                    <td  class="text">
                        <s:textfield theme="simple" name="importStockForm.toOwnerName" id="importStockForm.toOwnerName" cssClass="txtInputFull" readonly="true"  />
                    </td>
                </tr>
                <tr>
                    <td class="label"> <tags:label key="MSG.importStore"/></td>
                    <td  class="text" colspan="3">
                        <table style="width:100%; border-spacing:0px;">
                            <tr>
                                <td width="30%">
                                    <s:textfield theme="simple" name="importStockForm.toOwnerCode" id="importStockForm.toOwnerCode" cssClass="txtInputFull" readonly="true"/>
                                </td>
                                <td width="70%">
                                    <s:textfield theme="simple" name="importStockForm.toOwnerName" id="importStockForm.toOwnerName" cssClass="txtInputFull" readonly="true"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.ctvStore" required="true"/></td>
                    <td class="text">
                        <table style="width:100%; border-spacing:0px;">
                            <tr>                                
                                <td width="30%">                                    
                                    <input type="hidden" id="importStockForm.fromOwnerType" value="2"/>
                                    <s:textfield cssClass="txtInputFull" onblur="getStaffName()"  theme="simple"
                                                 name="importStockForm.fromOwnerCode" id="importStockForm.fromOwnerCode" />
                                </td>
                                <td width="70%">
                                    <s:textfield cssClass="txtInputFull" name="importStockForm.fromOwnerName"  theme="simple"
                                                 id="importStockForm.fromOwnerName" readonly="%{#attr.readOnly}" />
                                </td>
                            </tr>
                        </table>

                        <%-- id="importStockForm.fromOwnerId" --%>
                        <%--s:select theme="simple"  name="importStockForm.selectFromOwnerCode"
                                  id="importStockForm.fromOwnerId"
                                  cssClass="txtInput"
                                  disabled="%{#attr.disabled}"
                                  headerKey="" headerValue="--Chọn kho cộng tác viên--"
                                  list="%{#request.lstShopExport != null?#request.lstShopExport : #{}}"
                                  listKey="staffId" listValue="name"
                                  onchange="selectFromOwnerId()"/ --%>

                    </td>
                    <td class="label"><tags:label key="MSG.payMethod"/></td>
                    <td class="text">
                        <tags:imSelect name="importStockForm.paymethodeid"
                                  id="importStockForm.paymethodeid"
                                  cssClass="txtInputFull"
                                  theme="simple"
                                  list="2:MSG.ISN.026"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.reason" required="true"/></td>
                    <td clsss="text">
                        <tags:imSelect theme="simple"  name="importStockForm.reasonId"
                                  id="importStockForm.reasonId"
                                  cssClass="txtInputFull"
                                  disabled="${fn:escapeXml(disabled)}"
                                  headerKey="" headerValue="MSG.GOD.147"
                                  list="lstReasonImp"
                                  listKey="reasonId" listValue="reasonName"/>
                    </td>
                    <td class="label"><tags:label key="MSG.added.date"/></td>
                    <td  class="text">
                        <tags:dateChooser readOnly="true"  property="importStockForm.impDate" id="importStockForm.impDate"  styleClass="txtInputFull" />
                    </td>

                </tr>
                <tr>
                    <td class="label"> <tags:label key="MSG.comment"/></td>
                    <td class="text" colspan="3">
                        <s:textfield theme="simple" maxlength="500"  id="importStockForm.note" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </div>
    </tags:imPanel>

</s:form>

    <%--sau khi chon CTV => ma CTV --%>

    <s:hidden name="staff_export_type" id="staff_export_type" value="staff_import_channel"/>
    
<div id="inputGoods">    
    <jsp:include page="../../Common/Goods.jsp"/>
</div>    


<div style="width:100%" align="center"> 
    <s:if test="importStockForm.canPrint==1">
        <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.createNoteRecovery'))}" disabled/>
        <!--tags:submit formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="In phiếu thu"
                     preAction="ImportStockUnderlyingAction!printImpAction.do?type=toStaff"/-->
    </s:if>
    <s:else>
        <tags:submit confirm="true" confirmText="MSG.confirm.create.note.recover.staff"  formId="importStockForm" showLoadingText="true" targets="bodyContent" value="MSG.createNoteRecovery"
                     validateFunction="validateFrom1()"
                     preAction="ImportStockUnderlyingAction!createReceiveNoteCTV.do"/>
        <!--input type="button" value="In phiếu thu" disabled/ -->
    </s:else>
    <tags:submit formId="exportStockForm" showLoadingText="true" targets="bodyContent" value="MSG.reset"
                 preAction="ImportStockUnderlyingAction!clearRevokeCollForm.do"/>
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
    getStaffName=function(){        
        var staffCode=document.getElementById("importStockForm.fromOwnerCode").value;
        var shopCode='<s:property escapeJavaScript="true"  value="#session.userToken.shopCode"/>';
        selectFromOwnerCode(staffCode);
        getInputText("CommonStockAction!getStaffName.do?target=importStockForm.fromOwnerName&staffCode=" + staffCode+"&shopCode="+shopCode);        
    }

    setStaffValue= function(code,name){
        $('importStockForm.fromOwnerCode').value=code;
        $('importStockForm.fromOwnerName').value=name;
        $('importStockForm.reasonId').focus();
        selectFromOwnerCode(code);
    }

    selectFromOwnerCode = function(code) {
        // tutm1 08/03/2012 Review CSRF add token
        gotoAction("inputGoods", "ImportStockUnderlyingAction!selectStockTypeFromOwnerCode.do?staffCode="+code  
            + "&" + token.getTokenParamString(),"importStockForm");
    }

    try{
        $('importStockForm.fromOwnerCode').onkeyup = function( e ) {
            var evt = ( window.event ) ? window.event : e;
            var keyID = evt.keyCode;
            // F9
            if( keyID == 120 ) {
                try {
                    var code=$('importStockForm.fromOwnerCode').value;
                    var name=$('importStockForm.fromOwnerName').value;

                    var shopCode='<s:property escapeJavaScript="true"  value="#session.userToken.shopCode"/>';
                    var staffOwnerId=$('importStockForm.toOwnerId').value;
                    openDialog('CommonStockAction!popupSearchRevokeColl.do?staffCode='+code+"&staffName="+name+"&shopCode="+shopCode +"&staffOwnerId="+staffOwnerId, 700, 750,false);

                } catch( e ) {
                    alert(e.message);
                }
            }
        }
    } catch(e){
    }
 
    validateFrom1 = function() {
        return validateForm2('resultCreateExpCmdClient');
    }
    
    validateForm2 =function(target){
        var formName="importStockForm";
        var noteImpCode = document.getElementById(formName+".noteImpCode");
        var fromOwnerCode = document.getElementById(formName+".fromOwnerCode");
        var paymethodeid = document.getElementById(formName+".paymethodeid");
        var reasonId = document.getElementById(formName+".reasonId");        
        var note = document.getElementById(formName+".note");
        
        if (noteImpCode == null || trim(noteImpCode.value) == '') {
            $(target).innerHTML= '<s:text name="ERR.STK.053"/>';
            //$(target).innerHTML='Chưa nhập mã phiếu nhập';
            noteImpCode.focus();         
            return false;
        }
        
        if (fromOwnerCode == null || trim(fromOwnerCode.value) == '') {
            $(target).innerHTML= '<s:text name="ERR.STK.054"/>';
            //$(target).innerHTML='Chưa chọn kho CTV';
            fromOwnerCode.focus();
            return false;            
        }

        if (paymethodeid ==null || trim(paymethodeid.value)==''){
            $(target).innerHTML= '<s:text name="ERR.STK.055"/>';
            //$(target).innerHTML='Chưa chọn hình thức thanh toán';
            paymethodeid.focus();
            return false;
        }           
        if (reasonId ==null || trim(reasonId.value)==''){
            $(target).innerHTML= '<s:text name="ERR.STK.053"/>';
            $(target).innerHTML='Chưa chọn lý do nhập';
            reasonId.focus();
            return false;
        }           
        
        if (note != null && trim(note.value) != '') {
            note.value=note.value.trim();
            if(isHtmlTagFormat(note.value)){
                $(target).innerHTML= '<s:text name="ERR.STK.012"/>';
                //$(target).innerHTML='Trường ghi chú không được nhập thẻ HTML';
                note.focus();
                return false;
            }
        }
        return true;        
    }
</script>

