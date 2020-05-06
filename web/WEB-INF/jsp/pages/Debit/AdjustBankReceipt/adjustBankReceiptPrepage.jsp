<%-- 
    Document   : adjustBankReceiptPrepage
    Created on : Oct 28, 2010, 5:05:28 PM
    Author     : Doan Thanh 8
    Desc       : man hinh khoi tao khi moi vao chuc nang dieu chinh giay nop tien ngan hang
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

<tags:imPanel title="title.adjustBankReceiptPrepage.page">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="messageSearch" id="messageSearch" propertyValue="messageSearchParam" type="key"/>
    </div>

    <!-- phan tra cuu giay thong tin giay nop tien de dieu chinh -->
    <div class="divHasBorder">
        <s:form action="adjustBankReceiptAction" theme="simple" method="post" id="searchBankReceiptExternalForm">
<s:token/>

            <table class="inputTbl6Col">
                <tr>
                    <!-- ma don vi -->
                    <td style="width:13%;"><tags:label key="MSG.DET.069"/></td>
                    <td class="oddColumn" style="width:20%;">
                        <tags:imSearch codeVariable="searchBankReceiptExternalForm.shopCode"
                                       nameVariable="searchBankReceiptExternalForm.shopName"
                                       codeLabel="MSG.SAE.067" nameLabel="MSG.SAE.070"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"/>
                    </td>
                    <!-- ngay nop tien -->
                    <td style="width:13%;"><tags:label key="MSG.DET.147"/></td>
                    <td class="oddColumn" style="width:20%;">
                        <tags:dateChooser property="searchBankReceiptExternalForm.bankReceiptDateFrom"
                                          id="searchBankReceiptExternalForm.bankReceiptDateFrom"
                                          styleClass="txtInputFull"/>
                    </td>
                    <td style="width:13%;"><tags:label key="MSG.DET.148"/></td>
                    <td>
                        <tags:dateChooser property="searchBankReceiptExternalForm.bankReceiptDateTo"
                                          id="searchBankReceiptExternalForm.bankReceiptDateTo"
                                          styleClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <!-- ngan hang -->
                    <td><tags:label key="MSG.DET.071"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="searchBankReceiptExternalForm.bankCode" nameVariable="searchBankReceiptExternalForm.bankName"
                                       codeLabel="MSG.bank.code" nameLabel="MSG.bank.name"
                                       searchClassName="com.viettel.im.database.DAO.BankAccountDAO"
                                       searchMethodName="getListBank"
                                       getNameMethod="getBankName"/>
                    </td>
                    <!-- nhom chuyen thu -->
                    <td><tags:label key="MSG.DET.072"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="searchBankReceiptExternalForm.bankAccountGroupCode" nameVariable="searchBankReceiptExternalForm.bankAccountGroupName"
                                       codeLabel="MSG.LST.819" nameLabel="MSG.LST.820"
                                       searchClassName="com.viettel.im.database.DAO.BankAccountDAO"
                                       searchMethodName="getListBankAccountGroup"
                                       getNameMethod="getBankAccountGroupName"/>
                    </td>
                    <!-- ma giay nop tien -->
                    <td><tags:label key="MSG.DET.073"/></td>
                    <td>
                        <s:textfield name="searchBankReceiptExternalForm.bankReceiptCode" id="searchBankReceiptExternalForm.bankReceiptCode" maxlength="25" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <div align="center" style="padding-top:3px;">
            <tags:submit formId="searchBankReceiptExternalForm"
                         showLoadingText="true"
                         validateFunction="checkValidDataToSearch();"
                         cssStyle="width:80px;"
                         targets="divAdjustBankReceipt"
                         value="MSG.DET.082"
                         preAction="adjustBankReceiptAction!searchBankReceiptExternal.do"/>


        </div>
    </div>

    <!-- phan hien thi danh sach giay nop tien -->
    <div>
        <sx:div id="divAdjustBankReceipt">
            <jsp:include page="listBankReceiptExternal.jsp" />
        </sx:div>
    </div>

</tags:imPanel>


<script>
    //kiem tra tinh hop le cua cac truong
    checkValidDataToSearch = function() {
        //trim cac truong can thiet
        $('searchBankReceiptExternalForm.shopCode').value = trim($('searchBankReceiptExternalForm.shopCode').value);
        $('searchBankReceiptExternalForm.bankCode').value = trim($('searchBankReceiptExternalForm.bankCode').value);
        $('searchBankReceiptExternalForm.bankAccountGroupCode').value = trim($('searchBankReceiptExternalForm.bankAccountGroupCode').value);
        $('searchBankReceiptExternalForm.bankReceiptCode').value = trim($('searchBankReceiptExternalForm.bankReceiptCode').value);


        //ma don vi khong duoc chua cac ky tu dac biet, chi duoc chua cac ky tu A-Z, a-z, 0-9, _
        if($('searchBankReceiptExternalForm.shopCode').value != "" && !isValidInput($('searchBankReceiptExternalForm.shopCode').value, false, false)) {
            $('message').innerHTML = '<s:text name="ERR.DET.079"/>';
            $('searchBankReceiptExternalForm.shopCode').select();
            $('searchBankReceiptExternalForm.shopCode').focus();
            return false;
        }
        //kiem tra ma ngan hang
        if($('searchBankReceiptExternalForm.bankCode').value != "" && !isValidInput($('searchBankReceiptExternalForm.bankCode').value, false, false)) {
            $('message').innerHTML = '<s:text name="ERR.DET.093"/>';
            $('searchBankReceiptExternalForm.bankCode').select();
            $('searchBankReceiptExternalForm.bankCode').focus();
            return false;
        }
        //kiem tra ma nhom chuyen thu
        if($('searchBankReceiptExternalForm.bankAccountGroupCode').value != "" && !isValidInput($('searchBankReceiptExternalForm.bankAccountGroupCode').value, false, false)) {
            $('message').innerHTML = '<s:text name="ERR.DET.094"/>';
            $('searchBankReceiptExternalForm.bankAccountGroupCode').select();
            $('searchBankReceiptExternalForm.bankAccountGroupCode').focus();
            return false;
        }

        return true;
    }
</script>
