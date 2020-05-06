<%-- 
    Document   : createRequestDebitQueryForm
    Created on : May 14, 2013, 2:04:05 PM
    Author     : ThinhPH2_S
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<fieldset class="imFieldset">
    <legend>${imDef:imGetText('MSG.SAE.015')}</legend>
    <div id="addgooddetail" style="width:100%; vertical-align:top;height:300px " >
        <table class="inputTbl3Col" >
            <tr>
                <td class="label"><tags:label key="MES.CHL.142" required="true"/></td>
                <td colspan="3" class="text">
                    <tags:imSearch codeVariable="createRequestDebitForm.shopCode" nameVariable="createRequestDebitForm.shopName"
                                   codeLabel="MSG.SAE.109" nameLabel="MSG.SAE.110"
                                   searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                   searchMethodName="getListShopIsVTUnit"
                                   getNameMethod="getShopName"
                                   />
                </td>
            </tr>            
            <tr>
                <td class="label"><tags:label key="label.maxDebit" required="true"/></td>
                <td class="text">
                    <s:textfield name="createRequestDebitForm.debitType" id="debitType" cssStyle="text-align:right" cssClass="txtInputFull" maxlength="18" theme="simple" />
                </td>
                <td class="label"><tags:label key="lbl.ngay.ap.dung" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="createRequestDebitForm.debitDayType"
                                   id="debitDayType"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="lbl.loai_ngay_AD"
                                   list="listDebitDayType"
                                   listValue="name" listKey="code"
                                   theme="simple"/>
                </td>
            </tr>  
            <tr>
                <td class="label"><tags:label key="MSG.stock.stock.type" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="createRequestDebitForm.stockTypeId"
                                   id="stockTypeId" theme="simple"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="label.stockType.txt"
                                   list="lstStockTypeId"
                                   listKey="stockTypeId" listValue="name"
                                   />
                </td>
                <td class="label"><tags:label key="label.request.debit.type" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="createRequestDebitForm.requestDebitType"
                                   id="requestDebitType" theme="simple"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="label.request.debit.type.combo"
                                   list="1:MSG.GOD.221, 2:lbl.total"
                                   />
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.STK.030"/></td>
                <td class="text" colspan="3">
                    <s:textfield name="createRequestDebitForm.description" id="description" cssClass="txtInputFull" maxlength="200" theme="simple" />
                </td>
            </tr>
        </table>
        <div align="center">
            <tags:submit preAction="createRequestDebitStockShopAction!addRequestDebit.do"
                         formId="createRequestDebitForm"
                         targets="inputDateOfPaymentDiv"
                         cssStyle="width:80px"
                         value="BTS.btn.AddNew"
                         validateFunction="validate();"
                         />
        </div>
    </div>
</fieldset>
<script type="text/javascript">
    validate = function() {
        var debitType = $('debitType');
        var debitDayType = $('debitDayType');

        if (jQuery('#createRequestDebitForm\\.shopCode').val() == "") {
//            $('displayResultMsg').innerHTML= 'Lỗi!!!. Chưa nhập Cừa hàng';
            jQuery('#displayResultMsg').html('<s:property value="getText('err.loi_chua_nhap_cua_hang')"/>');
            jQuery('#createRequestDebitForm\\.shopCode').focus();
            return false;
        }

        if (debitType.value == null || debitType.value == "") {
            $('displayResultMsg').innerHTML = '<s:property value="getText('err.loi_chua_nhap_han_muc')"/>';
            debitType.focus();
            return false;
        }

        if (!isNumberFormat(debitType.value.trim())) {
            $('displayResultMsg').innerHTML = '<s:property value="getText('err.loi_han_muc_nhap_not_int')"/>';
            debitDayType.focus();
            return false;
        }
        if (jQuery("#debitType").val() < 1) {
            $('displayResultMsg').innerHTML = '<s:property value="getText('err.loi_han_muc_nhap_phai_lon_hon_0')"/>';
            debitDayType.focus();
            return false;
        }
        if (debitDayType.value == null || debitDayType.value == "") {
            $('displayResultMsg').innerHTML = '<s:property value="getText('err.loi_chua_chon_ngay_ap_dung')"/>';
            debitDayType.focus();
            return false;
        }

        if (jQuery("#stockTypeId").val() == "") {
            jQuery("#displayResultMsg").html('<s:property value="getText('lbl.chua_chon_loai_mat_hang')"/>');
            jQuery("#stockTypeId").focus();
            return false;
        }
        
        if (jQuery("#requestDebitType").val() == "") {
            jQuery("#displayResultMsg").html('<s:property value="getText('lbl.chua_chon_loai_yeu_cau')"/>');
            jQuery("#requestDebitType").focus();
            return false;
        }
        return true;
    }

</script>