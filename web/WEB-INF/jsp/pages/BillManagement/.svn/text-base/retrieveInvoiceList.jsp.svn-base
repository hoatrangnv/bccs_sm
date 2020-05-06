<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.recover.invoice.not.use">
    <!-- hien thi message -->
    <div>
        <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
    </div>

    <div style="color:blue; font-size:15px; font-family:Arial,Helvetica,sans-serif; text-align:left;">
        <tags:label key="MSG.notes"/>
    </div>

    <br/>

    <s:form action="retrieveInvoiceListAction" method="post" id="form" theme="simple">
        <!-- phan thong tin tim kiem dai hoa don can thu hoi -->
        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td style="width:10%;"><tags:label key="MSG.invoice.type" required="false"/></td>
                    <td class="oddColumn" style="width:23%;">
                        <tags:imSelect name="form.invoiceType"
                                       id="form.invoiceType"
                                       cssClass="txtInputFull"
                                       list="1:msg.invoiceTypeSale, 2:msg.invoiceTypePayment, 3:msg.receiptTypeSale, 4:msg.receiptTypePayment,
                                             5:msg.expenseTypeSale, 6:msg.expenseTypePayment, 7:msg.voucherTypeSale, 8:msg.voucherTypePayment"
                                       headerKey="" headerValue="msg.invoiceTypeHeader"/>

                    </td>
                    <td style="width:10%;"><tags:label key="MSG.signBill" required="false"/></td>
                    <td class="oddColumn" style="width:23%;">
                        <tags:imSearch codeVariable="form.serialCode" nameVariable="form.blockName"
                                       codeLabel="MSG.signBill" nameLabel="MSG.bin.book.type"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListBookType"
                                       getNameMethod="getBookTypeName"
                                       otherParam="form.invoiceType"/>
                    </td>
                    <td style="width:10%;"><tags:label key="MSG.bill.range"/></td>
                    <td>
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:textfield name="form.fromInvoice" id="form.billStartNumber" maxlength="10" cssClass="txtInputFull"/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:textfield name="form.toInvoice" id="form.billEndNumber" maxlength="10" cssClass="txtInputFull"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.generates.shop_type" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="form.ownerType" id="billDepartmentKind"
                                       list="1:MSG.GOD.261,2:MSG.GOD.262"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.263"/>
                    </td>
                    <td><tags:label key="MSG.generates.unit_code" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="form.ownerCode" nameVariable="form.ownerName"
                                       codeLabel="MSG.generates.unit_code" nameLabel="MSG.unit.name"
                                       searchClassName="com.viettel.im.database.DAO.RetrieveInvoiceListDAO"
                                       searchMethodName="getListObject"
                                       otherParam="billDepartmentKind"
                                       getNameMethod="getObjectName"/>

                    </td>
                    <td><tags:label key="MSG.generates.status"/></td>
                    <td>
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:100%;">
                                    <tags:imSelect name="form.status"
                                                   id="billSituation"
                                                   cssClass="txtInputFull"
                                                   headerKey="" headerValue="MSG.GOD.260"
                                                   list="1:MSG.GOD.255,2:MSG.GOD.256"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <div style="width:100%" align="center">
                <tags:submit targets="bodyContent" formId="form"
                             value="MSG.find"
                             cssStyle="width:80px;"
                             preAction="retrieveInvoiceListAction!searchBills.do"
                             showLoadingText="true"
                             validateFunction="validateSearch()"/>
            </div>
        </div>
        <div style="width:100%" align="center">
            <jsp:include page="retrieveInvoiceListResult.jsp"/>
        </div>
            <s:token/>
    </s:form>
</tags:imPanel>

<script type="text/javascript" language="javascript">

    isCheckBoxChecked = function(){
        if(document.getElementById('invoice') == null){
            return false;
        }
        var i = 0;
        var tbl = $( 'invoice' );
        var inputs = [];
        //var chkNum = 0;
        inputs = tbl.getElementsByTagName( "input" );

        for( i = 0; i < inputs.length-3; i++ ) {
            if(inputs[i].type == "text" && inputs[i+3].type == "checkbox"){
                $(inputs[i].id + 'span').style.display = 'none';
            }
        }
        for( i = 0; i < inputs.length; i++ ) {
            //if( inputs[i].type == "checkbox" ) chkNum++;
            if( inputs[i].type == "checkbox" && inputs[i].checked == true ) {
                return true;
            }
        }
        return false;
    }

    validateSearch = function(){
        //
        var serialCode = trim($('form.serialCode').value);

        if(serialCode != "" && isHtmlTagFormat(serialCode)) {
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.001"/>';
            $('form.serialCode').select();
            $('form.serialCode').focus();
            return false;
        }

        var billStartNumber = trim($('form.billStartNumber').value);
        var billEndNumber = trim($('form.billEndNumber').value);

        //truong dau dai phai la so nguyen duong
        if(billStartNumber != "" && !isInteger(billStartNumber)) {
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.002"/>';
            $('form.billStartNumber').select();
            $('form.billStartNumber').focus();
            return false;
        }

        //truong cuoi dai phai la so nguyen duong
        if(billEndNumber != "" && !isInteger(billEndNumber)) {
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.003"/>';
            $('form.billEndNumber').select();
            $('form.billEndNumber').focus();
            return false;
        }

        if(billStartNumber != ""  && billEndNumber != "" && (billStartNumber * 1 > billEndNumber * 1)){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.004"/>';
            $('form.billStartNumber').select();
            $('form.billStartNumber').focus();
            return false;
        }

        //truong loai don vi va ma don vi khong duoc de trong
        //        if(trim($('billDepartmentKind').value) == ""){
        //            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.007')"/>';
        //            $('billDepartmentKind').select();
        //            $('billDepartmentKind').focus();
        //            return false;
        //        }
        //
        //        if(trim($('form.ownerCode').value) == ""){
        //            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.RET.035')"/>';
        //            $('form.ownerCode').select();
        //            $('form.ownerCode').focus();
        //            return false;
        //        }
        //
        var shopCode = $('form.ownerCode').value;
        if(shopCode != null && shopCode != '' && !isValidInput(trim(shopCode), false, false)) {
            $('displayResultMsgClient').innerHTML= '<s:text name="assignChannelTypeForSerial.error.invalidShopCode"/>';//$('message').innerHTML = "!!!Lỗi. Mã kho lấy số chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
            $('form.ownerCode').select();
            $('form.ownerCode').focus();
            return false;
        }

        if(shopCode != null && trim(shopCode) != ""){
            if (trim($('billDepartmentKind').value) == ""){
                $('displayResultMsgClient').innerHTML= '<s:text name="Lỗi. Chưa chọn loại đối tượng!"/>';
                $('billDepartmentKind').select();
                $('billDepartmentKind').focus();
                return false;
            }
        }

        //trim cac truong can thiet
        $('form.serialCode').value = trim($('form.serialCode').value);
        $('form.billStartNumber').value = trim($('form.billStartNumber').value);
        $('form.billEndNumber').value = trim($('form.billEndNumber').value);

        return true;

    }

    validateRetrieve = function(){
        if(isCheckBoxChecked() == false){
            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.008')"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn dải hóa đơn để giao";
            return false;
        }
        if(isTextBoxChecked() == false){
            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.009')"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Số hóa đơn bạn nhập không hợp lệ";
            return false;
        }

        return true;
    }


</script>
