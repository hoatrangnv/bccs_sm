<%-- 
    Document   : saleToRetail
    Created on : Feb 24, 2011, 1:57:42 PM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
    request.setAttribute("contextPath", request.getContextPath());

%>

<tags:imPanel title="MSG.SAE.216">
    <!--LinhNBV start modified on May 24 2018: Check limit valid or invalid.-->
    <s:if test="#session.isOverLimit == 0">
        <div  align="center" class="txtError">
            <s:text name="ERR.SAE.158"></s:text>
            </div>
    </s:if>
    <s:else>
        <s:form action="saleToPackageAction" theme="simple" method="post" id="saleTransForm">
            <s:token/>
            <s:hidden name="staff_export_channel" id="staff_export_channel" value="staff_export_channel"/>
            <s:hidden name="saleTransForm.reasonIdVipCustomer" id="saleTransForm.reasonIdVipCustomer" />

            <div align="center">
                <fieldset class="imFieldset">
                    <legend><s:text name="MSG.SAE.002"/></legend>

                    <table class="inputTbl6Col">
                        <tr>
                            <td class="label"><tags:label key="MSG.SAE.005" required="false"/></td>
                            <td class="text">
                                <s:textfield name="saleTransForm.custName" id="saleTransForm.custName" maxlength="100" cssClass="txtInputFull"/>
                            </td>
                            <td class="label"><tags:label key="MSG.SAE.006"/></td>
                            <td class="text">
                                <s:textfield name="saleTransForm.company" id="saleTransForm.company" maxlength="100" cssClass="txtInputFull"/>
                            </td>
                            <td class="label"><tags:label key="MSG.SAE.007"/></td>
                            <td class="text">
                                <s:textfield name="saleTransForm.tin" id="saleTransForm.tin" maxlength="100" cssClass="txtInputFull"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="label"><tags:label key="MSG.SAE.010" required="true"/></td>
                            <td class="text">
                                <tags:imSelect name="saleTransForm.payMethod"
                                               id="saleTransForm.payMethod"
                                               cssClass="txtInputFull"
                                               headerKey="" headerValue="MSG.SAE.014"
                                               list="1:MSG.SAE.071, 99:PayMethod.EMola,2:MSG.SAE.072" 
                                               onchange="checkHideShowAccTrans();"/>
                            </td>
                            <td class="label"><tags:label key="MSG.SAE.009" required="true"/></td>
                            <td class="text">
                                <tags:imSelect name="saleTransForm.reasonId"
                                               id="saleTransForm.reasonId"
                                               cssClass="txtInputFull"
                                               headerKey="" headerValue="MSG.SAE.013"
                                               list="listReason"
                                               listKey="reasonId" listValue="reasonName"
                                               onchange="selectSaleReason();"/>
                            </td>
                            <td class="label"><tags:label key="MSG.SAE.008" required="true"/></td>
                            <td class="text">
                                <tags:dateChooser property="saleTransForm.saleTransDate"  id="saleTransForm.saleTransDate" styleClass="txtInput"   readOnly="true" insertFrame="false"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="label"><tags:label key="MSG.SDT.EMola" required="true"/></td>
                            <td class="text">
                                <s:textfield name="saleTransForm.isdnWallet" id="saleTransForm.isdnWallet" maxlength="10" cssClass="txtInputFull"/>
                            </td>
                        </tr>
                        <tr id="tr.getOTP">   
                            <td class="label"><tags:label key="MSG.SIK.214" required="true"/></td>
                            <td class="text">
                                <s:textfield name="saleTransForm.custMobile" id="saleTransForm.custMobile" maxlength="100" cssClass="txtInputFull"/>
                            </td>
                            <td class="button" colspan="4">
                                <tags:submit confirm="true" confirmText="MSG.SAE.206"
                                             formId="saleTransForm"
                                             targets="getCustOTPDiv"
                                             validateFunction="checkValidateGetOTP()" showLoadingText="true"
                                             value="MSG.SAE.215" preAction="saleToPackageAction!getOTP.do"/>
                            </td>
                        </tr>
                        <tbody style="display:none" id="trOrderCode">

                        </tbody>
                    </table> 
                    <div align="center" id="getCustOTPDiv">
                        <jsp:include page="getCustOTP.jsp"/>
                    </div>
                </fieldset>
            </div>
            <div align="center" id="saleToRetailDetailDiv">            
                <jsp:include page="saleToPackageDetail.jsp"/>
            </div>
            <div align="center" id="btCreateTrans">
                <tags:submit confirm="true" confirmText="MSG.SAE.204"
                             formId="saleTransForm"
                             targets="bodyContent"
                             validateFunction="checkValidateSaleTrans()" showLoadingText="true"
                             value="MSG.SAE.064" preAction="saleToPackageAction!saveSaleTransToRetail.do"/>
            </div>
            <!--         tannh20180115 saleto pakege payment account transfer-->
            <div align="center" id="btCreateOrder" style="margin-top:3px;display: none">
                <tags:submit confirm="true" confirmText="MSG.SAE.314"
                             formId="saleTransForm"
                             targets="bodyContent"
                             validateFunction="checkValidateSaleTrans()" showLoadingText="true"
                             value="MSG.SAE.313" preAction="saleToPackageAction!saveSaleTransToRetail.do"/>
            </div>
        </s:form>
    </s:else>
</tags:imPanel>



<script type="text/javascript" language="javascript">
    //tannh20180115 saleto pakege payment account transfer

    checkHideShowAccTrans = function() {
        var payMethodId = document.getElementById('saleTransForm.payMethod').value;
        if (payMethodId != 2) {
            document.getElementById('trOrderCode').style.display = 'none';
            document.getElementById('btCreateOrder').style.display = 'none';
            document.getElementById('btCreateTrans').style.display = '';
        }
        else {
            document.getElementById('trOrderCode').style.display = '';
            document.getElementById('btCreateOrder').style.display = '';
            document.getElementById('btCreateTrans').style.display = 'none';
        }
    }

    var payMethodId = document.getElementById('saleTransForm.payMethod').value;
    if (payMethodId != 2) {
        document.getElementById('trOrderCode').style.display = 'none';
        document.getElementById('btCreateOrder').style.display = 'none';
        document.getElementById('btCreateTrans').style.display = '';
    } else {
        document.getElementById('trOrderCode').style.display = '';
        document.getElementById('btCreateOrder').style.display = '';
        document.getElementById('btCreateTrans').style.display = 'none';
    }


    checkValidateSaleTrans = function() {
//        if (trim($('saleTransForm.custName').value) == ""){
//            $('displayResultMsg').innerHTML = '<s_:text name="saleRetail.warn.cust"/>';
//            $('saleTransForm.custName').select();
//            $('saleTransForm.custName').focus();
//            return false;
//        }

        if (trim($('saleTransForm.payMethod').value) == "") {
            $('displayResultMsg').innerHTML = '<s:text name="saleRetail.warn.pay"/>';
            $('saleTransForm.payMethod').select();
            $('saleTransForm.payMethod').focus();
            return false;
        } else if (trim($('saleTransForm.payMethod').value) == '99') {
            if (trim($('saleTransForm.isdnWallet').value) == "") {
                $('displayResultMsg').innerHTML = '<s:text name="input.isdn.EMola"/>';
                $('saleTransForm.isdnWallet').select();
                $('saleTransForm.isdnWallet').focus();
                return false;
            }
        }
        if (trim($('saleTransForm.reasonId').value) == "") {
            $('displayResultMsg').innerHTML = '<s:text name="saleRetail.warn.reason"/>';
            $('saleTransForm.reasonId').select();
            $('saleTransForm.reasonId').focus();
            return false;
        }

        var reasonIdVipCustomer = document.getElementById('saleTransForm.reasonIdVipCustomer').value;
        if ($('saleTransForm.reasonId').value == reasonIdVipCustomer) {
            if (trim($('saleTransForm.custMobile').value) == "") {
                $('displayResultMsg').innerHTML = '<s:text name="saleRetail.warn.custMobile"/>';
                $('saleTransForm.custMobile').select();
                $('saleTransForm.custMobile').focus();
                return false;
            }

            if (trim($('saleTransForm.custOTP').value) == "") {
                $('displayResultMsg').innerHTML = '<s:text name="saleRetail.warn.otp"/>';
                $('saleTransForm.custOTP').select();
                $('saleTransForm.custOTP').focus();
                return false;
            }
        }

        return true;
    }

    selectSaleReason = function() {
        var reasonIdVipCustomer = document.getElementById('saleTransForm.reasonIdVipCustomer').value;
        if ($('saleTransForm.reasonId').value != reasonIdVipCustomer) {
            document.getElementById('tr.getOTP').hidden = true;
            document.getElementById('tr.saleTransForm.custOTP').hidden = true;
            document.getElementById('tr.saleTransForm.amountDiscountDisplay').hidden = true;
        } else {
            document.getElementById('tr.getOTP').hidden = false;
            document.getElementById('tr.saleTransForm.custOTP').hidden = false;
            document.getElementById('tr.saleTransForm.amountDiscountDisplay').hidden = false;
        }
        gotoAction("saleToRetailDetailDiv", '${contextPath}/saleToPackageAction!selectReasonSale.do?' + token.getTokenParamString(), "saleTransForm");
    }

    checkValidateGetOTP = function() {
        if (trim($('saleTransForm.custMobile').value) == "") {
            $('displayResultMsg').innerHTML = '<s:text name="saleRetail.warn.custMobile"/>';
            $('saleTransForm.custMobile').select();
            $('saleTransForm.custMobile').focus();
            return false;
        }
        $('displayResultMsg').innerHTML = "";
        return true;
    }

    var reasonIdVipCustomer = document.getElementById('saleTransForm.reasonIdVipCustomer').value;
    if ($('saleTransForm.reasonId').value != reasonIdVipCustomer) {
        document.getElementById('tr.getOTP').hidden = true;
        document.getElementById('tr.saleTransForm.custOTP').hidden = true;
        document.getElementById('tr.saleTransForm.amountDiscountDisplay').hidden = true;
    } else {
        document.getElementById('tr.getOTP').hidden = false;
        document.getElementById('tr.saleTransForm.custOTP').hidden = false;
        document.getElementById('tr.saleTransForm.amountDiscountDisplay').hidden = false;
    }

</script>
