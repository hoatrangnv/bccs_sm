<%-- 
    Document   : searchDebitRequest
    Created on : May 16, 2013, 11:49:25 AM
    Author     : thinhph2_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<tags:imPanel title="title.approvel.debit.staff">
    <s:form id="searchDebitRequestForm" action="approvalDateOfPaymentAction" theme="simple" method="post">
        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.find.info'))}</legend>
            <table class="inputTbl6Col">
                <tr>
                    <td class="label"><tags:label key="lbl.nguoi_yeu_cau"/></td>
                    <td class="text">
                        <s:textfield name="searchDebitRequestForm.userCreate" id="note" theme="simple" cssClass="txtInputFull" maxLength="30"/>
                    </td>
                    <td class="label"><tags:label key="lbl.ma_yeu_cau"/></td>
                    <td class="text">
                        <s:textfield name="searchDebitRequestForm.requestCode" id="note" theme="simple" cssClass="txtInputFull" maxLength="20"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.fromDate" required="true"/></td>
                    <td class="text">
                        <tags:dateChooser property="searchDebitRequestForm.fromDate" styleClass="txtInputFull" id="fromDate"  />
                    </td>
                    <td class="label"><tags:label key="MSG.DET.094" required="true"/></td>
                    <td class="text">
                        <tags:dateChooser property="searchDebitRequestForm.toDate" styleClass="txtInputFull" id="toDate"  />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.generates.GOD.status"/></td>
                    <td class="text">
                        <tags:imSelect name="searchDebitRequestForm.status" id="status"
                                       cssClass="txtInputFull" disabled="false"
                                       list="1:lbl.chua_duyet, 2: lbl.da_duyet_tat_ca, 3: lbl.da_duyet, 0: lbl.da_xoa"
                                       headerKey="" headerValue="lbl.trang_thai_duyet"/>
                    </td>
                </tr>
            </table>
            <div align="center">                
                <tags:submit targets="bodyContent"
                             formId="searchDebitRequestForm"
                             cssStyle="width:100px;"
                             value="MSG.find"
                             showLoadingText="true"
                             preAction="approvalDateOfPaymentAction!searchDebitRequest.do"
                             validateFunction="validate()"/>
            </div>
            <div id="listSearchResultDiv">
                <jsp:include page="listApprovalDateOfPaymentSearchResult.jsp"/>
            </div>            
        </fieldset>
    </s:form>
</tags:imPanel>
<script type="text/javascript">
    downloadFile = function(requestId){
        window.open("${contextPath}" + "/approvalDateOfPaymentAction!downloadFile.do?searchDebitRequestForm.debitRequestId="+requestId,'','toolbar=no,scrollbars=no');
    }

    validateApproval = function(){
        var result = true;
        jQuery('[name=searchDebitRequestForm.listApprovalReject]').each(function(){
            var id = jQuery(this).attr('id').substr(8);
            if(jQuery(this).val()==0){
                if(jQuery('#description'+id).val() == ''){
                    if(jQuery("#allNote").val() == ''){
                        jQuery('#displayResultHMMsg').html('<s:property value="getText('err.loi_chua_nhap_ghi_chu')"/>');
                        jQuery('#description'+id).focus();
                        result = false;
                        return false;
                    }
                    if(checkingSpecialCharacter(jQuery("#allNote").val())){
                        jQuery('#displayResultHMMsg').html('<s:property value="getText('err.ly_do_chua_ki_tu_dac_biet')"/>');
                        jQuery('#description'+id).focus();
                        result = false;
                        return false;
                    }
                    jQuery('#description' + id).val(jQuery("#allNote").val());
                } else {
                    if (checkingSpecialCharacter(jQuery('#description' + id).val())) {
                        jQuery('#displayResultHMMsg').html('<s:property value="getText('err.ly_do_chua_ki_tu_dac_biet')"/>');
                        jQuery('#description' + id).focus();
                        result = false;
                        return false;
                    }
                }
            }
            <%--
            else{
                if (jQuery(jQuery(this).parent().parent().children()[9]).find("input").val() != "0") {
                    if (jQuery('#description' + id).val() == '') {
                        jQuery('#displayResultHMMsg').html('<s:property value="getText('err.chua_nhap_ly_do_thay_doi')"/>');
                        jQuery('#description' + id).focus();
                        result = false;
                        return false;
                    }
                }
            }
           --%> 
        })        
        
        return result;
    }

    validate = function(){
        var fromDate = dojo.widget.byId("fromDate");
        var toDate = dojo.widget.byId("toDate");
        if(dojo.widget.byId('fromDate').getValue() == ""){
            jQuery('#displayResultMsg').html('<s:property value="getText('err.loi_chua_nhap_tu_ngay')"/>');
            jQuery("#fromDate>input")[1].focus();
            return false;
        }
        if(dojo.widget.byId('toDate').getValue() == ""){
            jQuery('#displayResultMsg').html('<s:property value="getText('err.loi_chua_nhap_den_ngay')"/>');
            jQuery("#toDate>input")[1].focus();
            return false;
        }
        if(fromDate.domNode.childNodes[1].value !="" &&  toDate.domNode.childNodes[1].value.trim() != "" && !isCompareDate(fromDate.domNode.childNodes[1].value.trim(),toDate.domNode.childNodes[1].value.trim(),"VN")){
            jQuery('#displayResultMsg').html('<s:property value="getText('alert.tu_ngay_phai_nho_hon_den_ngay')"/>');
            jQuery("#fromDate>input")[1].focus();
            return false;
        }
        
        return true;
    }

    delDebitRequest = function(requestId) {
        var strConfirm = getUnicodeMsg('Bạn có chắc chắn muốn xóa yêu cầu này không?');
        if (confirm(strConfirm)) {
            gotoAction('listDebitRequestDiv','searchDateOfPaymentAction!delRequetstDebit.do?requestId=' + requestId);
        }
    }
    selectCbAllInspectStatus = function(){
        if(jQuery("#checkAllIDInspectStatus").attr("checked")){
            jQuery("[name=searchDebitRequestForm.listApprovalReject][type=select-one]").each(function(){
                jQuery(this).val("2");
            });
        } else {
            jQuery("[name=searchDebitRequestForm.listApprovalReject][type=select-one]").each(function(){
                jQuery(this).val("0");
            });
        }
    }

    checkSelectCbAllInspectStatus = function(){
    var result = true;
        jQuery("[name=searchDebitRequestForm.listApprovalReject][type=select-one]").each(function(){
            if(jQuery(this).val() == "0"){
                result = false;
                return false;
            }
        });
        jQuery("#checkAllIDInspectStatus").attr("checked", result);
    }
</script>