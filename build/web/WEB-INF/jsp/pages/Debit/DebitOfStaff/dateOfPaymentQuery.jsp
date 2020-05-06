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
                <td class="label"><tags:label key="MSG.RET.050" required="true"/></td>
                <td colspan="3" class="text">
                    <tags:imSearch codeVariable="createRequestDebitForm.shopCode" nameVariable="createRequestDebitForm.shopName"
                                   codeLabel="MSG.SAE.109" nameLabel="MSG.SAE.110"
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListShop"
                                   elementNeedClearContent="createRequestDebitForm.staffCode;createRequestDebitForm.staffName"
                                   getNameMethod="getNameShop"
                                   roleList="saleTransManagementf9Shop"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.GOD.262" required="true"/></td>
                <td colspan="3" class="text">
                    <tags:imSearch codeVariable="createRequestDebitForm.staffCode" nameVariable="createRequestDebitForm.staffName"
                                   codeLabel="MSG.SAE.111" nameLabel="MSG.SAE.112"
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListStaff"
                                   otherParam="createRequestDebitForm.shopCode"
                                   getNameMethod="getNameStaff"
                                   roleList="saleTransManagementf9Staff"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="lbl.han_muc_kho" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="createRequestDebitForm.debitType"
                                   id="debitType"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="lbl.select_object"
                                   listValue="name" listKey="code"
                                   list="listDebitType" theme="simple"
                                   onchange="getListDebitDay();"/>
                </td>
                <td class="label"><tags:label key="lbl.ngay.ap.dung" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="createRequestDebitForm.debitDayType"
                                   id="debitDayType"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="lbl.loai_ngay_AD"
                                   listValue="name" listKey="code"
                                   theme="simple"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MES.package.goods.003"/></td>
                <td class="text" colspan="3">
                    <s:textfield name="createRequestDebitForm.description" id="description" cssClass="txtInputFull" maxlength="200" theme="simple" />
                </td>
            </tr>
        </table>
        <div align="center">
            <tags:submit preAction="dateOfPaymentAction!addRequestDebit.do"
                         formId="createRequestDebitForm"
                         targets="inputDateOfPaymentDiv"
                         cssStyle="width:80px"
                         value="TITLE.STOCK.CONFIGURATION.LIMITS.001"
                         validateFunction="validate();"
                         />
        </div>
    </div>
</fieldset>
<script type="text/javascript">
    validate = function(){
        var debitType = $('debitType');        
        var debitDayType = $('debitDayType');

        if(jQuery('#createRequestDebitForm\\.shopCode').val() == ""){
            $('displayResultMsg').innerHTML= '<s:property value="getText('err.loi_chua_nhap_cua_hang')"/>';
            jQuery('#createRequestDebitForm\\.shopCode').focus();
            return false;
        }

        if(jQuery('#createRequestDebitForm\\.staffCode').val() == ""){
            $('displayResultMsg').innerHTML= '<s:property value="getText('err.chua.chon.lai.may')"/>';
            jQuery('#createRequestDebitForm\\.staffCode').focus();
            return false;
        }
        
        if(debitType.value == null || debitType.value == ""){
            $('displayResultMsg').innerHTML= '<s:property value="getText('err.chua.chon.loai.han.muc')"/>';
            debitType.focus();
            return false;
        }
        if(debitDayType.value == null || debitDayType.value == ""){
            $('displayResultMsg').innerHTML= '<s:property value="getText('err.loi_chua_chon_ngay_ap_dung')"/>';
            debitDayType.focus();
            return false;
        }
        return true;
    }

    getListDebitDay = function(){
        var debitType = document.getElementById("debitType").value.trim();
        updateData('${contextPath}/dateOfPaymentAction!getListDebitDayType.do?debitType='+ debitType);
    }
</script>