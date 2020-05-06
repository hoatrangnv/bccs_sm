<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : adjustBankReceipt
    Created on : Oct 28, 2010, 4:46:53 PM
    Author     : Doan Thanh 8
    Desc       : dieu chinh giay nop tien tu ngan hang
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


<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MSG.DET.109'))}</legend>

    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan thong tin ve giay nop tien se duoc dieu chinh -->
    <table style="width: 100%;">
        <tr>
            <td style="width: 40%; vertical-align: top;">
                <div class="divHasBorder" style="height: 250px; overflow:auto;">
                    <s:form action="adjustBankReceiptAction" theme="simple" method="post" id="bankReceiptExternalForm">
<s:token/>

                        <s:hidden name="bankReceiptExternalForm.bankReceiptExternalId" id="bankReceiptExternalForm.bankReceiptExternalId" />

                        <table class="inputTbl2Col">
                            <tr>
                                <!-- ma don vi -->
                                <td style="width:25%;"><tags:label key="MSG.DET.069"/></td>
                                <td>
                                    <tags:imSearch codeVariable="bankReceiptExternalForm.ShopCode"
                                                   nameVariable="bankReceiptExternalForm.shopName"
                                                   codeLabel="MSG.SAE.067" nameLabel="MSG.SAE.070"
                                                   searchClassName="com.viettel.im.database.DAO.DocDepositDAO"
                                                   searchMethodName="getListShopOrStaff"
                                                   getNameMethod="getNameShopOrStaff"
                                                   readOnly="true"/>
                                </td>
                            </tr>
                            <tr>
                                <!-- ngay nop tien -->
                                <td><tags:label key="MSG.DET.070"/></td>
                                <td>
                                    <tags:dateChooser property="bankReceiptExternalForm.bankReceiptDate"
                                                      id="bankReceiptInternalForm.bankReceiptDate"
                                                      styleClass="txtInputFull"
                                                      readOnly="true"/>
                                </td>
                            </tr>
                            <tr>
                                <!-- ngan hang -->
                                <td><tags:label key="MSG.DET.071"/></td>
                                <td>
                                    <tags:imSearch codeVariable="bankReceiptExternalForm.bankCode" nameVariable="bankReceiptExternalForm.bankName"
                                                   codeLabel="MSG.bank.code" nameLabel="MSG.bank.name"
                                                   searchClassName="com.viettel.im.database.DAO.BankAccountDAO"
                                                   searchMethodName="getListBank"
                                                   getNameMethod="getBankName"
                                                   readOnly="true"/>
                                </td>
                            </tr>
                            <tr>
                                <!-- nhom chuyen thu -->
                                <td><tags:label key="MSG.DET.072"/></td>
                                <td>
                                    <tags:imSearch codeVariable="bankReceiptExternalForm.bankAccountGroupCode" nameVariable="bankReceiptExternalForm.bankAccountGroupName"
                                                   codeLabel="MSG.LST.819" nameLabel="MSG.LST.820"
                                                   searchClassName="com.viettel.im.database.DAO.BankAccountDAO"
                                                   searchMethodName="getListBankAccountGroup"
                                                   getNameMethod="getBankAccountGroupName"
                                                   readOnly="true"/>
                                </td>
                            </tr>
                            <tr>
                                <!-- ma giay nop tien -->
                                <td><tags:label key="MSG.DET.073"/></td>
                                <td>
                                    <s:textfield name="bankReceiptExternalForm.bankReceiptCode" id="bankReceiptExternalForm.bankReceiptCode" maxlength="25" cssClass="txtInputFull" readonly="true"/>
                                </td>
                            </tr>
                            <tr>
                                <!-- noi dung -->
                                <td><tags:label key="MSG.DET.076"/></td>
                                <td>
                                    <s:textarea name="bankReceiptExternalForm.content" id="bankReceiptExternalForm.content" maxlength="25" cssClass="txtInputFull" readonly="true"/>
                                </td>
                            </tr>
                            <tr>
                                <!-- so tien goc -->
                                <td><tags:label key="MSG.DET.110"/></td>
                                <td>
                                    <s:textfield name="bankReceiptExternalForm.amount" id="bankReceiptExternalForm.amount" maxlength="25" cssClass="txtInputFull" cssStyle="text-align:right; " readonly="true"/>
                                </td>
                            </tr>
                            <tr>
                                <!-- so tien hien tai -->
                                <td><tags:label key="MSG.DET.111"/></td>
                                <td>
                                    <s:textfield name="bankReceiptExternalForm.amountAfterAdjustment" id="bankReceiptExternalForm.amountAfterAdjustment" maxlength="25" cssClass="txtInputFull" cssStyle="text-align:right; " readonly="true"/>
                                </td>
                            </tr>
                        </table>
                    </s:form>
                </div>
            </td>
            <td style="width: 3px; vertical-align: top;"></td>
            <td style="vertical-align: top;">
                <!-- phan hien thi danh sach cac lan dieu chinh -->
                <div class="divHasBorder" style="height: 250px; overflow:auto;">
                    <display:table id="tblBankReceiptAdjustment" name="listBankReceiptAdjustment"
                                   class="dataTable" cellpadding="1" cellspacing="1" >
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.DET.078'))}" style="text-align:center" headerClass="tct" sortable="false">
                            ${fn:escapeXml(tblBankReceiptAdjustment_rowNum)}
                        </display:column>
                        <display:column property="createdDate" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.112'))}" format="{0,date,dd/MM/yyyy}"/>
                        <display:column escapeXml="true"  property="createdUser" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.113'))}"/>
                        <display:column property="amountBeforeAdjustment" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.114'))}" format="{0,number,#,##0.00}" style="text-align:right; "/>
                        <display:column property="amount" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.115'))}" format="{0,number,#,##0.00}" style="text-align:right; "/>
                        <display:column property="amountAfterAdjustment" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.116'))}" format="{0,number,#,##0.00}" style="text-align:right; "/>
                        <display:column escapeXml="true"  property="content" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.117'))}"/>
                    </display:table>
                </div>
            </td>
        </tr>
    </table>

    <!-- phan thong tin dieu chinh cho giay nop tien -->
    <div class="divHasBorder" style="margin-top: 5px;">
        <s:form action="adjustBankReceiptAction" theme="simple" method="post" id="bankReceiptAdjustmentForm">
<s:token/>

            <s:hidden name="bankReceiptAdjustmentForm.bankReceiptExternalType" id="bankReceiptAdjustmentForm.bankReceiptExternalType"/>
            <s:hidden name="bankReceiptAdjustmentForm.bankReceiptExternalId" id="bankReceiptAdjustmentForm.bankReceiptExternalId"/>
            <s:hidden name="bankReceiptAdjustmentForm.currentAmount" id="bankReceiptAdjustmentForm.currentAmount"/>
            <table class="inputTbl6Col">
                <tr>
                    <!-- kieu dieu chinh -->
                    <td style="width:13%;"><tags:label key="MSG.DET.095" required="true"/></td>
                    <td class="oddColumn" style="width:20%;">
                        <tags:imSelect name="bankReceiptAdjustmentForm.adjustmentType"
                                       id="bankReceiptAdjustmentForm.adjustmentType"
                                       headerKey="1" headerValue="MSG.DET.098"
                                       list="-1:MSG.DET.099"
                                       cssClass="txtInputFull"/>
                    </td>
                    <!-- so luogn tien dieu chinh -->
                    <td style="width:13%;"><tags:label key="MSG.DET.096" required="true"/></td>
                    <td class="oddColumn" style="width:20%;">
                        <s:textfield name="bankReceiptAdjustmentForm.amount" id="bankReceiptAdjustmentForm.amount" maxlength="20" cssClass="txtInputFull" cssStyle="text-align:right;" onkeyup="textFieldNF(this)"/>
                    </td>
                    <!-- noi dung dieu chinh -->
                    <td style="width:13%;"><tags:label key="MSG.DET.118"/></td>
                    <td class="oddColumn" style="width:20%;">
                        <s:textfield name="bankReceiptAdjustmentForm.content" id="bankReceiptAdjustmentForm.content" maxlength="50" cssClass="txtInputFull"/>
                    </td>
                    <td>
                        <!-- phan nut tac vu -->
                        <div align="center" style="padding-top:3px;">
                            <tags:submit formId="bankReceiptAdjustmentForm"
                                         validateFunction="validateData();"
                                         showLoadingText="true"
                                         cssStyle="width:80px;"
                                         targets="divAdjustBankReceipt"
                                         value="MSG.DET.100"
                                         preAction="adjustBankReceiptAction!addBankReceiptAdjustment.do"
                                         confirm="true"
                                         confirmText="MSG.DET.105"/>
                        </div>
                    </td>
                    <td></td>
                </tr>
            </table>
        </s:form>
    </div>

</fieldset>

<script language="javascript">
    //dinh dang tien cho o so tien
    textFieldNF($('bankReceiptExternalForm.amount'));
    textFieldNF($('bankReceiptExternalForm.amountAfterAdjustment'));
    textFieldNF($('bankReceiptAdjustmentForm.amount'));

    validateData = function(){
        //trim cac truong can thiet
        $('bankReceiptAdjustmentForm.amount').value = trim($('bankReceiptAdjustmentForm.amount').value);
        $('bankReceiptAdjustmentForm.content').value = trim($('bankReceiptAdjustmentForm.content').value);

        //truong so tien dieu chinh khogn duoc de trong
        if(trim($('bankReceiptAdjustmentForm.amount').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.DET.083"/>';
            $('bankReceiptAdjustmentForm.amount').select();
            $('bankReceiptAdjustmentForm.amount').focus();
            return false;
        }

        //kiem tra so tien nop
        var amount = $('bankReceiptAdjustmentForm.amount').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        if(!isDouble(trim(amount))) {
            $('message').innerHTML = '<s:text name="ERR.DET.084"/>';
            $('bankReceiptAdjustmentForm.amount').select();
            $('bankReceiptAdjustmentForm.amount').focus();
            return false;
        }

        if(amount * 1 < 0) {
            $('message').innerHTML = '<s:text name="ERR.DET.084"/>';
            $('bankReceiptAdjustmentForm.amount').select();
            $('bankReceiptAdjustmentForm.amount').focus();
            return false;
        }

        //kiem tra neu dieu chinh am thi so tien dieu chinh khong duoc lon hon so tien hien co
        var adjustmentType = $('bankReceiptAdjustmentForm.adjustmentType').value;
        var currentAmount = $('bankReceiptAdjustmentForm.currentAmount').value;

        if((adjustmentType == -1) && (amount * 1 > currentAmount * 1)) {
            $('message').innerHTML = '<s:text name="ERR.DET.095"/>';
            $('bankReceiptAdjustmentForm.amount').select();
            $('bankReceiptAdjustmentForm.amount').focus();
            return false;
        }

        return true;
    }
</script>
