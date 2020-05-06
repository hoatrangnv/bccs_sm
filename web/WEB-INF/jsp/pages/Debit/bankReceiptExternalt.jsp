<%-- 
    Document   : bankReceiptExternalt
    Created on : Oct 28, 2010, 11:46:33 AM
    Author     : TheTM
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

<tags:imPanel title="title.bankReceiptExternalt.page">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi thong tin can tra cuu, them moi -->
    <div class="divHasBorder">
        <s:form action="createSingleBankReceiptAction" theme="simple" method="post" id="bankReceiptExternalForm">
<s:token/>

            <s:hidden name="bankReceiptExternalForm.bankReceiptExternalId" id="bankReceiptExternalForm.bankReceiptExternalId"/>
            <table class="inputTbl6Col">
                <tr>
                    <!-- ma don vi -->
                    <td><tags:label key="MSG.DET.069" required="true"/></td>
                    <td>
                        <tags:imSearch codeVariable="bankReceiptExternalForm.ownerCode"
                                       nameVariable="bankReceiptExternalForm.ownerName"
                                       codeLabel="MSG.SAE.067" nameLabel="MSG.SAE.070"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       elementNeedClearContent="bankReceiptExternalForm.ownerName"
                                       getNameMethod="getNameShop"/>
                    </td>

                    <!--Ngan hang-->
                    <td><tags:label key="MSG.bank.code" required="true"/></td>
                    <td>
                        <tags:imSearch codeVariable="bankReceiptExternalForm.bankCode" nameVariable="bankReceiptExternalForm.bankName"
                                       codeLabel="MSG.bank.code" nameLabel="MSG.bank.name"
                                       searchClassName="com.viettel.im.database.DAO.BankAccountDAO"
                                       searchMethodName="getListBank"
                                       elementNeedClearContent="bankReceiptExternalForm.bankName;bankReceiptExternalForm.bankAccountGroupCode;bankReceiptExternalForm.bankAccountGroupName"
                                       getNameMethod="getBankName"/>
                    </td>

                    <!--Nhom chuyen thu-->
                    <td><tags:label key="MSG.LST.819" required="true"/></td>
                    <td>
                        <tags:imSearch codeVariable="bankReceiptExternalForm.bankAccountGroupCode" nameVariable="bankReceiptExternalForm.bankAccountGroupName"
                                       codeLabel="MSG.LST.819" nameLabel="MSG.LST.820"
                                       searchClassName="com.viettel.im.database.DAO.BankAccountDAO"
                                       searchMethodName="getListBankAccountGroup"
                                       otherParam = "bankReceiptExternalForm.bankCode"
                                       elementNeedClearContent="bankAccountForm.bankAccountGroupName"
                                       getNameMethod="getBankAccountGroupName"/>
                    </td>
                </tr>
                <tr>
                    <!-- ngay nop tien -->
                    <td><tags:label key="MSG.DET.070" required="true"/></td>
                    <td>
                        <tags:dateChooser property="bankReceiptExternalForm.bankReceiptDate"
                                          id="bankReceiptExternalForm.bankReceiptDate"
                                          styleClass="txtInputFull"/>
                    </td>

                    <!-- ma giay nop tien -->
                    <td><tags:label key="MSG.DET.073" required="true"/></td>
                    <td>
                        <s:textfield name="bankReceiptExternalForm.bankReceiptCode" id="bankReceiptExternalForm.bankReceiptCode" maxlength="50" cssClass="txtInputFull"/>
                    </td>

                    <!-- so tien nop (bang so) -->
                    <td><tags:label key="MSG.DET.074" required="true"/></td>
                    <td>
                        <s:textfield name="bankReceiptExternalForm.amount" id="bankReceiptExternalForm.amount" maxlength="22" cssClass="txtInputFull" cssStyle="text-align:right; " format="{0,number,#,###.###}" onkeyup="textFieldNF(this)" maxLength="20"/>
                    </td>
                </tr>
                <tr>
                    <!-- noi dung -->
                    <td><tags:label key="MSG.DET.076" required="true"/></td>
                    <td colspan="5">
                        <s:textfield name="bankReceiptExternalForm.content" id="bankReceiptExternalForm.content" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <!--Trang thai-->
                    <%--
                    <td><tags:label key="MSG.generates.status" required="true"/></td>
                    <td>
                        <tags:imSelect name="bankReceiptExternalForm.status"
                                       id="status"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.189"
                                       list="1:MSG.GOD.335, 2:MSG.GOD.332, 3:MSG.GOD.333, 4:MSG.GOD.334"/>
                    </td>
                    --%>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <div align="center" style="padding-top:3px;">
            <s:if test="#attr.bankReceiptExternalForm.bankReceiptExternalId != null && #attr.bankReceiptExternalForm.bankReceiptExternalId.compareTo(0L) > 0">
                <tags:submit formId="bankReceiptExternalForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             validateFunction="checkValidBankReceiptExternal();"
                             value="MSG.DET.084"
                             preAction="createSingleBankReceiptAction!addOrEditBankReceiptExternal.do"/>

                <tags:submit formId="bankReceiptExternalForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.DET.085"
                             preAction="createSingleBankReceiptAction!preparePage.do"/>
            </s:if>
            <s:else>
                <tags:submit formId="bankReceiptExternalForm"
                             showLoadingText="true"
                             validateFunction="checkValidBankReceiptExternal();"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.DET.083"
                             preAction="createSingleBankReceiptAction!addOrEditBankReceiptExternal.do"/>

                <tags:submit formId="bankReceiptExternalForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.DET.082"
                             preAction="createSingleBankReceiptAction!searchBankReceiptExternal.do"/>
            </s:else>
        </div>
    </div>

    <!-- phan hien thi danh sach giay nop tien -->
    <div>
        <jsp:include page="listBankReceiptExternalt.jsp"/>
    </div>

</tags:imPanel>

<script language="javascript">
    //dinh dang tien cho o so tien
    textFieldNF($('bankReceiptExternalForm.amount'));
    $('bankReceiptExternalForm.ownerCode').focus();

    checkValidBankReceiptExternal = function() {
        if(checkRequiredFields() && checkValidFields()) {
            return true;
        } else {
            return false;
        }
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        //truong ma don vi
        if(trim($('bankReceiptExternalForm.ownerCode').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.DET.071"/>';
            $('bankReceiptExternalForm.ownerCode').select();
            $('bankReceiptExternalForm.ownerCode').focus();
            return false;
        }
        //ngan hang
        if(trim($('bankReceiptExternalForm.bankCode').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.DET.073"/>';
            $('bankReceiptExternalForm.bankCode').select();
            $('bankReceiptExternalForm.bankCode').focus();
            return false;
        }
        //nhom chuyen thu
        if(trim($('bankReceiptExternalForm.bankAccountGroupCode').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.DET.074"/>';
            $('bankReceiptExternalForm.bankAccountGroupCode').select();
            $('bankReceiptExternalForm.bankAccountGroupCode').focus();
            return false;
        }
        //Ngay nop tien
        var receiptDate= dojo.widget.byId("bankReceiptExternalForm.bankReceiptDate");
        if( receiptDate.domNode.childNodes[1].value.trim() == ""){
            $('message').innerHTML = '<s:text name="ERR.DET.085"/>';
            receiptDate.domNode.childNodes[1].select();
            receiptDate.domNode.childNodes[1].focus();
            return false;
        }
        //ma giay nop tien
        if(trim($('bankReceiptExternalForm.bankReceiptCode').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.DET.086"/>';
            $('bankReceiptExternalForm.bankReceiptCode').select();
            $('bankReceiptExternalForm.bankReceiptCode').focus();
            return false;
        }
        //truong so tien nop (bang so)
        if(trim($('bankReceiptExternalForm.amount').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.DET.076"/>';
            $('bankReceiptExternalForm.amount').select();
            $('bankReceiptExternalForm.amount').focus();
            return false;
        }
        //truong noi dung
        if(trim($('bankReceiptExternalForm.content').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.DET.078"/>';
            $('bankReceiptExternalForm.content').select();
            $('bankReceiptExternalForm.content').focus();
            return false;
        }
        return true;
    }

    //kiem tra tinh hop le cua cac truong
    checkValidFields = function() {
        
        //Ngay nop tien
        var bankReceiptDate= dojo.widget.byId("bankReceiptExternalForm.bankReceiptDate");
        if(!isDateFormat(bankReceiptDate.domNode.childNodes[1].value)){
            $( 'message' ).innerHTML = '<s:text name="ERR.DET.074"/>';
            $('bankReceiptExternalForm.bankReceiptDate').select();
            $('bankReceiptExternalForm.bankReceiptDate').focus();
            return false;
        }

        //kiem tra so tien nop
        var amount = $('bankReceiptExternalForm.amount').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        if(!isNumeric(trim(amount))) {
            $('message').innerHTML = '<s:text name="ERR.DET.081"/>';
            $('bankReceiptExternalForm.amount').select();
            $('bankReceiptExternalForm.amount').focus();
            return false;
        }
        return true;
    }
</script>
