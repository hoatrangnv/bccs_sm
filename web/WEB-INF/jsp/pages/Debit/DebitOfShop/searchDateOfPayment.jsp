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

<tags:imPanel title="title.tra_cuu_yeu_cau_han_muc_nv">
    <s:form id="searchDebitRequestForm" action="searchDateOfPaymentAction" theme="simple" method="post">
        <fieldset class="imFieldset">
            <legend>${imDef:imGetText('MSG.find.info')}</legend>
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
                    <td class="label"><tags:label key="MSG.GOD.118" required="true"/></td>
                    <td class="text">
                        <tags:dateChooser property="searchDebitRequestForm.toDate" styleClass="txtInputFull" id="toDate"  />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.094"/></td>
                    <td class="text">
                        <tags:imSelect name="searchDebitRequestForm.status" id="status"
                                       cssClass="txtInputFull" disabled="false"
                                       list="1:lbl.chua_duyet, 2: lbl.da_duyet_tat_ca, 3: MSG.approved, 0: MSG.delete"
                                       headerKey="" headerValue="lbl.trang_thai_duyet"/>
                    </td>
                    <td class="label"><tags:label key="MSG.object.type"/></td>
                    <td class="text">
                        <tags:imSelect name="searchDebitRequestForm.requestObjectType" id="requestObjectType"
                                       cssClass="txtInputFull" disabled="false"
                                       list="1: lbl.han.muc.kho.don.vi, 2: lbl.han.muc.kho.nhan.vien"
                                       headerKey="" headerValue="lbl.trang_thai_duyet"/>
                    </td>
                </tr>
            </table>
            <div align="center">
                <tags:submit targets="bodyContent"
                             formId="searchDebitRequestForm"
                             cssStyle="width:100px;"
                             value="imSearchPopup.btnSearch"
                             showLoadingText="true"
                             preAction="searchDateOfPaymentAction!searchDebitRequest.do"
                             validateFunction="validate()"/>
            </div>
            <div id="listSearchResultDiv">
                <jsp:include page="listSearchResultDateOfPayment.jsp"/>
            </div>

        </fieldset>
    </s:form>
</tags:imPanel>
<script type="text/javascript">
    downloadFile = function(requestId){
        window.open("${contextPath}" + "/searchDateOfPaymentAction!downloadFile.do?searchDebitRequestForm.debitRequestId="+requestId,'','toolbar=no,scrollbars=no');
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
        var strConfirm = getUnicodeMsg('<s:property value="getText('cf.xoa_yeu_cau')"/>');
        if (confirm(strConfirm)) {
        <%--if(confirm("Bạn có chắc chắn muốn xóa yêu cầu này không?")){--%>
                gotoAction('listDebitRequestDiv','searchDateOfPaymentAction!delRequetstDebit.do?requestId=' + requestId);
        }
    }
</script>