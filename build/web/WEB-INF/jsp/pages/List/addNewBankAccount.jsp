<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : addNewBankAccount
    Created on : Aug 24, 2009, 2:42:25 PM
    Author     : 
    Desc       : danh muc tai khoan ngan hang
    Modified   : tamdt1
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listBankAccount", request.getSession().getAttribute("listBankAccount"));
%>

<tags:imPanel title="title.addNewBankAccount.page">
    <!-- hien thi message -->
    <div>
        <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="paramMsg" type="key" />
    </div>

<!--    otherParam = "BASE"-->
    <div class="divHasBorder">
        <!-- phan thong tin bankAccount -->
        <s:form action="bankAccountAction" theme="simple" enctype="multipart/form-data"  method="post" id="bankAccountForm">
<s:token/>

            <s:hidden name="bankAccountForm.bankAccountId" id="bankAccountId"/>
            <table class="inputTbl4Col">
                <tr>
                    <td><tags:label key="MSG.bank.code" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="bankAccountForm.bankCode" nameVariable="bankAccountForm.bankName"
                                       codeLabel="MSG.bank.code" nameLabel="MSG.bank.name"
                                       searchClassName="com.viettel.im.database.DAO.BankAccountDAO"
                                       searchMethodName="getListBank"
                                       
                                       elementNeedClearContent="bankAccountForm.bankName"
                                       getNameMethod="getBankName"/>
                    </td>
                    <td><tags:label key="MSG.LST.819" required="true"/></td>
                    <td>
                        <tags:imSearch codeVariable="bankAccountForm.bankAccountGroupCode" nameVariable="bankAccountForm.bankAccountGroupName"
                                       codeLabel="MSG.LST.819" nameLabel="MSG.LST.820"
                                       searchClassName="com.viettel.im.database.DAO.BankAccountDAO"
                                       searchMethodName="getListBankAccountGroup"
                                       elementNeedClearContent="bankAccountForm.bankAccountGroupName"
                                       getNameMethod="getBankAccountGroupName"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.account.number" required="true"/></td>
                    <td class="oddColumn">
                        <s:textfield name="bankAccountForm.accountNo" id="accountNo" maxlength="50" style="text-align:right" cssClass="txtInputFull" disabled="false"/>
                    </td>
                    <td><tags:label key="MSG.generates.status" required="true"/></td>
                    <td>
                        <tags:imSelect name="bankAccountForm.status"
                                       id="status"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.189"
                                       list="1:MSG.GOD.002, 0:MSG.GOD.003"/>
                    </td>
                </tr>
            </table>
        </s:form>



        <!-- phan nut tac vu -->
        <div align="center" style="padding-top:10px; padding-bottom: 5px;">
            <s:if test="#attr.bankAccountForm.bankAccountId != null &&  #attr.bankAccountForm.bankAccountId.compareTo(0L) > 0">

                <tags:submit targets="bodyContent" formId="bankAccountForm"
                             validateFunction="checkRaV();"
                             cssStyle="width:80px;" value="MSG.accept"
                             showLoadingText="true"
                             preAction="bankAccountAction!saveBankAccount.do"/>

                <tags:submit targets="bodyContent" formId="bankAccountForm"
                             cssStyle="width:80px;" value="MSG.cancel2"
                             showLoadingText="true"
                             preAction="bankAccountAction!preparePage.do"/>
            </s:if>
            <s:else>
                <tags:submit targets="bodyContent" formId="bankAccountForm"
                             validateFunction="checkRaV();"
                             cssStyle="width:80px;" value="MSG.generates.addNew"
                             showLoadingText="true"
                             preAction="bankAccountAction!saveBankAccount.do"/>

                <tags:submit targets="bodyContent" formId="bankAccountForm"
                             cssStyle="width:80px;" value="MSG.find"
                             validateFunction="checkValidFields();"
                             showLoadingText="true"
                             preAction="bankAccountAction!searchBankAccount.do"/>
            </s:else>
        </div>

    </div>

    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.bank.account.list'))}</legend>
        <sx:div id="listBankAccount">
            <jsp:include page="addNewBankAccountResult.jsp"/>
        </sx:div>
    </fieldset>
    

</tags:imPanel>

<script type="text/javascript">
    //focus vao truong dau tien
    $('bankAccountForm.bankCode').focus();


    //cac ham phuc vu list
    editBankAccount = function(bankAccountId) {
        gotoAction('bodyContent','bankAccountAction!prepareEditBankAccount.do?bankAccountId=' + bankAccountId+ '&' + token.getTokenParamString());
    }

    delBankAccount = function(bankAccountId) {
//        var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('ERR.LST.055')"/>');
        var strConfirm = getUnicodeMsg('<s:text name="ERR.LST.055"/>');
        if (confirm(strConfirm)) {
            gotoAction('bodyContent','bankAccountAction!deleteBankAccount.do?bankAccountId=' + bankAccountId+ '&' + token.getTokenParamString());
        }
    }


    copyBankAccount = function(bankAccountId) {
        gotoAction('bodyContent','bankAccountAction!prepareCopyBankAccount.do?bankAccountId=' + bankAccountId+ '&' + token.getTokenParamString());
    }

    //ham kiem tra tinh hop le
    checkRaV=function(){
        return (checkRequiredFields() && checkValidFields());
    }

    checkRequiredFields = function() {
        if($('bankAccountForm.bankCode').value ==null || $('bankAccountForm.bankCode').value.trim() == ""){
//            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.LST.056')"/>';
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.056"/>';
            $('bankAccountForm.bankCode').select();
            $('bankAccountForm.bankCode').focus();
            return false;
        }
        if($('bankAccountForm.bankAccountGroupCode').value ==null || $('bankAccountForm.bankAccountGroupCode').value.trim() == ""){
//            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.LST.821')"/>';
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="MSG.LST.821"/>';
            $('bankAccountForm.bankAccountGroupCode').select();
            $('bankAccountForm.bankAccountGroupCode').focus();
            return false;
        }
        if($('accountNo').value ==null || $('accountNo').value.trim() == ""){
//            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.LST.058')"/>';
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.058"/>';
            $('accountNo').select();
            $('accountNo').focus();
            return false;
        }
        if(trim($('status').value) == "") {
//            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.STK.026')"/>';
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.STK.026"/>';
            $('status').select();
            $('status').focus();
            return false;
        }

        return true;
    }

    checkValidFields = function() {
        if(isHtmlTagFormat(trim($('bankAccountForm.bankCode').value))) {
//            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.LST.059')"/>';
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.059"/>';
            $('bankAccountForm.bankCode').select();
            $('bankAccountForm.bankCode').focus();
            return false;
        }
        if(trim($('bankAccountForm.bankCode').value).length > 10) {
//            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.LST.062')"/>';
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.062"/>';
            $('bankAccountForm.bankCode').select();
            $('bankAccountForm.bankCode').focus();

            return false;
        }
        if(isHtmlTagFormat(trim($('bankAccountForm.bankAccountGroupCode').value))) {
//            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.LST.822')"/>';
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="MSG.LST.822"/>';
            $('bankAccountForm.bankAccountGroupCode').select();
            $('bankAccountForm.bankAccountGroupCode').focus();
            return false;
        }
        
        if(!isInteger((trim($('accountNo').value)))&&(!trim($('accountNo').value) == "")){
//            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.LST.060')"/>';
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.060"/>';
            $('accountNo').select();
            $('accountNo').focus();
            return false;
        }
        if(trim($('accountNo').value).length > 15) {
//            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.LST.061')"/>';
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.061"/>';
            $('accountNo').select();
            $('accountNo').focus();

            return false;
        }

        return true;
    }
</script>
