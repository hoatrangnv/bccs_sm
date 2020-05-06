<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : depositSlipInternal
    Created on : Oct 27, 2010, 10:29:42 AM
    Author     : Doan Thanh 8
    Desc       : cac nghiep vu them, sua, xoa giay nop tien (noi bo)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="title.depositSlipInternal.page">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi thong tin can tra cuu, them moi -->
    <div class="divHasBorder">
        <s:form action="depositSlipInternalAction" theme="simple" method="post" id="bankReceiptInternalForm">
<s:token/>

            <s:hidden name="bankReceiptInternalForm.bankReceiptInternalId" id="bankReceiptInternalForm.bankReceiptInternalId" />

            <table class="inputTbl4Col">
                <tr>
                    <!-- loai don vi -->
                    <td class="label"><tags:label key="MSG.DET.068" required="true"/></td>
                    <td class="text" >
                        <tags:imSelect name="bankReceiptInternalForm.ownerType"
                                       id="bankReceiptInternalForm.ownerType"
                                       list="2:MSG.GOD.262,1:MSG.GOD.261"
                                       cssClass="txtInputFull"/>
                    </td>
                    <!-- ma don vi -->
                    <td class="label"><tags:label key="MSG.DET.069" required="true"/></td>
                    <td class="text">
                        <tags:imSearch codeVariable="bankReceiptInternalForm.ownerCode"
                                       nameVariable="bankReceiptInternalForm.ownerName"
                                       codeLabel="MSG.SAE.067" nameLabel="MSG.SAE.070"
                                       searchClassName="com.viettel.im.database.DAO.DocDepositDAO"
                                       searchMethodName="getListShopOrStaff"
                                       otherParam="bankReceiptInternalForm.ownerType"
                                       getNameMethod="getNameShopOrStaff"/>
                    </td>
                </tr>
                <tr>
                    <!-- ngan hang -->
                    <td class="label"><tags:label key="MSG.DET.071" required="true"/></td>
                    <td class="text">
                        <tags:imSearch codeVariable="bankReceiptInternalForm.bankCode" nameVariable="bankReceiptInternalForm.bankName"
                                       codeLabel="MSG.bank.code" nameLabel="MSG.bank.name"
                                       searchClassName="com.viettel.im.database.DAO.BankAccountDAO"
                                       searchMethodName="getListBank"
                                       elementNeedClearContent="bankAccountForm.bankName"
                                       getNameMethod="getBankName"/>
                    </td>
                    <!-- nhom chuyen thu -->
                    <td class="label"> <tags:label key="MSG.DET.072" required="true"/></td>
                    <td class="text">
                        <tags:imSearch codeVariable="bankReceiptInternalForm.bankAccountGroupCode" nameVariable="bankReceiptInternalForm.bankAccountGroupName"
                                       codeLabel="MSG.LST.819" nameLabel="MSG.LST.820"
                                       searchClassName="com.viettel.im.database.DAO.BankAccountDAO"
                                       searchMethodName="getListBankAccountGroup"
                                       otherParam = "bankReceiptInternalForm.bankCode"
                                       elementNeedClearContent="bankAccountForm.bankAccountGroupName"
                                       getNameMethod="getBankAccountGroupName"/>
                    </td>                    
                </tr>
                <tr>
                    <!-- ngay nop tien -->
                    <td class="label"><tags:label key="MSG.DET.070" required="true"/></td>
                    <td >
                        <tags:dateChooser property="bankReceiptInternalForm.bankReceiptDate"
                                          id="bankReceiptInternalForm.bankReceiptDate"
                                          styleClass="txtInputFull"/>
                    </td>
                    <!-- ma giay nop tien -->
                    <td class="label"><tags:label key="MSG.DET.073" required="true"/></td>
                    <td class="text">
                        <s:textfield name="bankReceiptInternalForm.bankReceiptCode" id="bankReceiptInternalForm.bankReceiptCode" maxlength="25" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <!-- so tien nop (bang so) -->
                    <td class="label"><tags:label key="MSG.DET.074" required="true"/></td>
                    <td class="text">

                        <s:textfield name="bankReceiptInternalForm.amountInFigureString" id="bankReceiptInternalForm.amountInFigure" maxlength="25" cssClass="txtInputFull" cssStyle="text-align:right; " />
						
						
						
						
						
                    </td>
                    <!-- noi dung -->
                    <td class="label"><tags:label key="MSG.DET.076" required="true"/></td>
                    <td class="text">
                        <s:textfield name="bankReceiptInternalForm.content" id="bankReceiptInternalForm.content" maxlength="75" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <div align="center" style="padding-top:3px;">
            <s:if test="#attr.bankReceiptInternalForm.bankReceiptInternalId != null && #attr.bankReceiptInternalForm.bankReceiptInternalId.compareTo(0L) > 0">
                <tags:submit formId="bankReceiptInternalForm"
                             showLoadingText="true"
                             validateFunction="checkValidDataToAddOrEditDepositSlip();"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.DET.084"
                             preAction="depositSlipInternalAction!addOrEditDepositSlipInternal.do"/>

                <tags:submit formId="bankReceiptInternalForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.DET.085"
                             preAction="depositSlipInternalAction!prepareDepositSlipInternal.do"/>
            </s:if>
            <s:else>
                <tags:submit formId="bankReceiptInternalForm"
                             showLoadingText="true"
                             validateFunction="checkValidDataToSearch();"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.DET.082"
                             preAction="depositSlipInternalAction!searchDepositSlipInternal.do"/>

                <tags:submit formId="bankReceiptInternalForm"
                             showLoadingText="true"
                             validateFunction="checkValidDataToAddOrEditDepositSlip();"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.DET.083"
                             preAction="depositSlipInternalAction!addOrEditDepositSlipInternal.do"/>
            </s:else>
        </div>
    </div>

    <!-- phan hien thi danh sach giay nop tien -->
    <div>
        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.DET.077'))}</legend>
            <sx:div id="divListDepositSlipInternal">
                <jsp:include page="listDepositSlipInternal.jsp"/>
            </sx:div>
        </fieldset>
    </div>

</tags:imPanel>

<script language="javascript">
    //dinh dang tien cho o so tien
    //textFieldNF($('bankReceiptInternalForm.amountInFigure'));

    //focus vao truong dau tien
    $('bankReceiptInternalForm.ownerType').focus();

    //
    checkValidDataToAddOrEditDepositSlip = function() {
        if(checkRequiredFields() && checkValidFields()) {
            return true;
        } else {
            return false;
        }
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        //truong loai don vi
        if(trim($('bankReceiptInternalForm.ownerType').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.DET.070"/>';
            $('bankReceiptInternalForm.ownerType').select();
            $('bankReceiptInternalForm.ownerType').focus();
            return false;
        }
        //truong ma don vi
        if(trim($('bankReceiptInternalForm.ownerCode').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.DET.071"/>';
            $('bankReceiptInternalForm.ownerCode').select();
            $('bankReceiptInternalForm.ownerCode').focus();
            return false;
        }
        //ngay nop tien
        var bankReceiptDate= dojo.widget.byId("bankReceiptInternalForm.bankReceiptDate");
        if(trim(bankReceiptDate.domNode.childNodes[1].value) == ""){
            $('message').innerHTML = '<s:text name="ERR.DET.072"/>';
            bankReceiptDate.domNode.childNodes[1].select();
            bankReceiptDate.domNode.childNodes[1].focus();
            return false;
        }
        //ngan hang
        if(trim($('bankReceiptInternalForm.bankCode').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.DET.073"/>';
            $('bankReceiptInternalForm.bankCode').select();
            $('bankReceiptInternalForm.bankCode').focus();
            return false;
        }
        //nhom chuyen thu
        if(trim($('bankReceiptInternalForm.bankAccountGroupCode').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.DET.074"/>';
            $('bankReceiptInternalForm.bankAccountGroupCode').select();
            $('bankReceiptInternalForm.bankAccountGroupCode').focus();
            return false;
        }
        //ma giay nop tien
        if(trim($('bankReceiptInternalForm.bankReceiptCode').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.DET.075"/>';
            $('bankReceiptInternalForm.bankReceiptCode').select();
            $('bankReceiptInternalForm.bankReceiptCode').focus();
            return false;
        }
        //truong so tien nop (bang so)
        if(trim($('bankReceiptInternalForm.amountInFigure').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.DET.076"/>';
            $('bankReceiptInternalForm.amountInFigure').select();
            $('bankReceiptInternalForm.amountInFigure').focus();
            return false;
        }
        //truong noi dung
        if(trim($('bankReceiptInternalForm.content').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.DET.078"/>';
            $('bankReceiptInternalForm.content').select();
            $('bankReceiptInternalForm.content').focus();
            return false;
        }
        return true;
    }

    //kiem tra tinh hop le cua cac truong
    checkValidFields = function() {
        //trim cac truong can thiet
        $('bankReceiptInternalForm.ownerCode').value = trim($('bankReceiptInternalForm.ownerCode').value);
        $('bankReceiptInternalForm.bankCode').value = trim($('bankReceiptInternalForm.bankCode').value);
        $('bankReceiptInternalForm.bankAccountGroupCode').value = trim($('bankReceiptInternalForm.bankAccountGroupCode').value);
        $('bankReceiptInternalForm.bankReceiptCode').value = trim($('bankReceiptInternalForm.bankReceiptCode').value);
        $('bankReceiptInternalForm.amountInFigure').value = trim($('bankReceiptInternalForm.amountInFigure').value);
        $('bankReceiptInternalForm.content').value = trim($('bankReceiptInternalForm.content').value);

        //ma don vi khong duoc chua cac ky tu dac biet, chi duoc chua cac ky tu A-Z, a-z, 0-9, _
        if(!isValidInput(trim($('bankReceiptInternalForm.ownerCode').value), false, false)) {
            $('message').innerHTML = '<s:text name="ERR.DET.079"/>';
            $('bankReceiptInternalForm.ownerCode').select();
            $('bankReceiptInternalForm.ownerCode').focus();
            return false;
        }
        //kiem tra ma ngan hang
        if(!isValidInput(trim($('bankReceiptInternalForm.bankCode').value), false, false)) {
            $('message').innerHTML = '<s:text name="ERR.DET.093"/>';
            $('bankReceiptInternalForm.bankCode').select();
            $('bankReceiptInternalForm.bankCode').focus();
            return false;
        }
        //kiem tra ma nhom chuyen thu
        if(!isValidInput(trim($('bankReceiptInternalForm.bankAccountGroupCode').value), false, false)) {
            $('message').innerHTML = '<s:text name="ERR.DET.094"/>';
            $('bankReceiptInternalForm.bankAccountGroupCode').select();
            $('bankReceiptInternalForm.bankAccountGroupCode').focus();
            return false;
        }

        //kiem trra cau truc cua ma giay nop tien, theo dinh dang MT_MCH_DV_THANG_NAM


        //kiem tra so tien nop
        var amountInFigure = $('bankReceiptInternalForm.amountInFigure').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        if(!isDouble(trim(amountInFigure))) {
            $('message').innerHTML = '<s:text name="ERR.DET.081"/>';
            $('bankReceiptInternalForm.amountInFigure').select();
            $('bankReceiptInternalForm.amountInFigure').focus();
            return false;
        }

        if (amountInFigure * 1 <= 0) {
            $('message').innerHTML = '<s:text name="ERR.DET.081"/>';
            $('bankReceiptInternalForm.amountInFigure').select();
            $('bankReceiptInternalForm.amountInFigure').focus();
            return false;
        }

		//120423 - TrongLV - xoa dau phan cach phan thap phan
		$('bankReceiptInternalForm.amountInFigure').value = amountInFigure;
		
        return true;
    }

    //kiem tra tinh hop le cua cac truong
    checkValidDataToSearch = function() {
        //trim cac truong can thiet
        $('bankReceiptInternalForm.ownerCode').value = trim($('bankReceiptInternalForm.ownerCode').value);
        $('bankReceiptInternalForm.bankCode').value = trim($('bankReceiptInternalForm.bankCode').value);
        $('bankReceiptInternalForm.bankAccountGroupCode').value = trim($('bankReceiptInternalForm.bankAccountGroupCode').value);
        $('bankReceiptInternalForm.bankReceiptCode').value = trim($('bankReceiptInternalForm.bankReceiptCode').value);
        $('bankReceiptInternalForm.amountInFigure').value = trim($('bankReceiptInternalForm.amountInFigure').value);
        $('bankReceiptInternalForm.content').value = trim($('bankReceiptInternalForm.content').value);

        //ma don vi khong duoc chua cac ky tu dac biet, chi duoc chua cac ky tu A-Z, a-z, 0-9, _
        if($('bankReceiptInternalForm.ownerCode').value != "" && !isValidInput($('bankReceiptInternalForm.ownerCode').value, false, false)) {
            $('message').innerHTML = '<s:text name="ERR.DET.079"/>';
            $('bankReceiptInternalForm.ownerCode').select();
            $('bankReceiptInternalForm.ownerCode').focus();
            return false;
        }
        //kiem tra ma ngan hang
        if($('bankReceiptInternalForm.bankCode').value != "" && !isValidInput($('bankReceiptInternalForm.bankCode').value, false, false)) {
            $('message').innerHTML = '<s:text name="ERR.DET.093"/>';
            $('bankReceiptInternalForm.bankCode').select();
            $('bankReceiptInternalForm.bankCode').focus();
            return false;
        }
        //kiem tra ma nhom chuyen thu
        if($('bankReceiptInternalForm.bankAccountGroupCode').value != "" && !isValidInput($('bankReceiptInternalForm.bankAccountGroupCode').value, false, false)) {
            $('message').innerHTML = '<s:text name="ERR.DET.094"/>';
            $('bankReceiptInternalForm.bankAccountGroupCode').select();
            $('bankReceiptInternalForm.bankAccountGroupCode').focus();
            return false;
        }

        //kiem trra cau truc cua ma giay nop tien, theo dinh dang MT_MCH_DV_THANG_NAM


        //kiem tra so tien nop
        var amountInFigure = $('bankReceiptInternalForm.amountInFigure').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        if(amountInFigure != "" && !isDouble(trim(amountInFigure))) {
            $('message').innerHTML = '<s:text name="ERR.DET.081"/>';
            $('bankReceiptInternalForm.amountInFigure').select();
            $('bankReceiptInternalForm.amountInFigure').focus();
            return false;
        }

        if (amountInFigure != "" && amountInFigure * 1 <= 0) {
            $('message').innerHTML = '<s:text name="ERR.DET.081"/>';
            $('bankReceiptInternalForm.amountInFigure').select();
            $('bankReceiptInternalForm.amountInFigure').focus();
            return false;
        }

        return true;
    }

    
</script>
