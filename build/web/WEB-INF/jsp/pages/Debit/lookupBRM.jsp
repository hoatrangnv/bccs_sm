<%-- 
    Document   : lookupBRM
    Created on : Nov 1, 2010, 11:56:57 AM
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

<tags:imPanel title="title.lookupBRM.page">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi thong tin can tra cuu, them moi -->
    <div class="divHasBorder">
        <s:form action="lookupBRMAction" theme="simple" method="post" id="bankReceiptExternalForm">
<s:token/>

            <s:hidden name="bankReceiptExternalForm.bankReceiptExternalId" id="bankReceiptExternalForm.bankReceiptExternalId"/>
            <table class="inputTbl4Col">
                <tr>
                    <td class="label"><tags:label key="Loại GNT"/></td>
                    <td class="text">
                        <tags:imSelect name="bankReceiptExternalForm.bankReceiptType"
                                       id="bankReceiptExternalForm.bankReceiptType"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="--Chọn loại GNT--"
                                       list="1:GNT cửa hàng, 2:GNT ngân hàng"/>
                    </td>
                    <!-- ma don vi -->
                    <td class="label"><tags:label key="MSG.DET.069"/></td>
                    <td class="text" colspan="1">
                        <tags:imSearch codeVariable="bankReceiptExternalForm.ownerCode"
                                       nameVariable="bankReceiptExternalForm.ownerName"
                                       codeLabel="MSG.SAE.067" nameLabel="MSG.SAE.070"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       elementNeedClearContent="bankReceiptExternalForm.ownerName"
                                       getNameMethod="getNameShop"/>
                    </td>
                </tr>
                <tr>
                    <!--Ngan hang-->
                    <td class="label"><tags:label key="MSG.bank.code"/></td>
                    <td class="text" colspan="1">
                        <tags:imSearch codeVariable="bankReceiptExternalForm.bankCode" nameVariable="bankReceiptExternalForm.bankName"
                                       codeLabel="MSG.bank.code" nameLabel="MSG.bank.name"
                                       searchClassName="com.viettel.im.database.DAO.BankAccountDAO"
                                       searchMethodName="getListBank"
                                       elementNeedClearContent="bankReceiptExternalForm.bankName"
                                       getNameMethod="getBankName"/>
                    </td>

                    <!--Nhom chuyen thu-->
                    <td class="label"><tags:label key="MSG.LST.819"/></td>
                    <td class="text" colspan="1">
                        <tags:imSearch codeVariable="bankReceiptExternalForm.bankAccountGroupCode" nameVariable="bankReceiptExternalForm.bankAccountGroupName"
                                       codeLabel="MSG.LST.819" nameLabel="MSG.LST.820"
                                       searchClassName="com.viettel.im.database.DAO.BankAccountDAO"
                                       searchMethodName="getListBankAccountGroup"
                                       elementNeedClearContent="bankAccountForm.bankAccountGroupName"
                                       getNameMethod="getBankAccountGroupName"/>
                    </td>                   
                </tr>
                <tr>
                    <!-- ma giay nop tien -->
                    <td class="label"><tags:label key="MSG.DET.073"/></td>
                    <td class="text" colspan="1">
                        <s:textfield name="bankReceiptExternalForm.bankReceiptCode" id="bankReceiptExternalForm.bankReceiptCode" maxlength="50" cssClass="txtInputFull"/>
                    </td>

                    <!--Trang thai-->
                    <td><tags:label key="MSG.generates.status" required="false"/></td>
                    <td>
                        <tags:imSelect name="bankReceiptExternalForm.status"
                                       id="status"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.189"
                                       list="1:MSG.DET.089, 2:MSG.DET.090, 3:MSG.DET.091, 4:MSG.DET.092"/>
                    </td>
                </tr>
                <tr>
                    <!--Tu ngay nop tien -->
                    <td class="label"><tags:label key="MSG.DET.093"/></td>
                    <td class="text" colspan="1">
                        <tags:dateChooser property="bankReceiptExternalForm.bankReceiptDate"
                                          id="bankReceiptExternalForm.bankReceiptDate"
                                          styleClass="txtInputFull"/>
                    </td>
                    <!--Den ngay nop tien -->
                    <td class="label"><tags:label key="MSG.DET.094"/></td>
                    <td class="text" colspan="1">
                        <tags:dateChooser property="bankReceiptExternalForm.bankReceiptToDate"
                                          id="bankReceiptExternalForm.bankReceiptToDate"
                                          styleClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <div align="center" style="padding-top:3px;">
            <tags:submit formId="bankReceiptExternalForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.DET.082"
                         preAction="lookupBRMAction!lookupBRM.do"/>

        </div>
    </div>

    <!-- phan hien thi danh sach giay nop tien -->
    <div>
        <jsp:include page="listLookupBRM.jsp"/>
    </div>

</tags:imPanel>

