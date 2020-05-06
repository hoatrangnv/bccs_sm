<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
            if (request.getAttribute("invoiceList") == null) {
                request.setAttribute("invoiceList", request.getSession().getAttribute("invoiceList"));
            }
            request.setAttribute("contextPath", request.getContextPath());
            String selectedValue = (String) request.getAttribute("selectedValue");
%>

<tags:imPanel title="MSG.bill.info">
    <s:form action="assignInvoiceListAction" method="post" id="form" theme="simple">

        <!-- phan hien thi message -->
        <div>
            <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
        </div>

        <!-- phan hien thi thong tin ve dai hoa don -->
        <div class="divHasBorder">
            <table class="inputTbl4Col">
                <tr>
                    <td><tags:label key="MSG.invoice.type"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="form.invoiceType"
                                       id="form.invoiceType"
                                       cssClass="txtInputFull"
                                       list="1:msg.invoiceTypeSale, 2:msg.invoiceTypePayment, 3:msg.receiptTypeSale, 4:msg.receiptTypePayment,
                                       5:msg.expenseTypeSale, 6:msg.expenseTypePayment, 7:msg.voucherTypeSale, 8:msg.voucherTypePayment"
                                       headerKey="" headerValue="msg.invoiceTypeHeader"/>

                    </td>
                    <td><tags:label key="MSG.bill.sign"/></td>
                    <td>
                        <tags:imSearch codeVariable="form.serialCode" nameVariable="form.blockName"
                                       codeLabel="MSG.signBill" nameLabel="MSG.bin.book.type"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListBookType"
                                       getNameMethod="getBookTypeName"
                                       otherParam="form.invoiceType"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.generates.status"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="form.status"
                                       id="billSituation"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.260"
                                       list="1:MSG.GOD.255,2:MSG.GOD.256"/>
                    </td>
                    <td><tags:label key="MSG.bill.range"/></td>
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
            </table>

            <div style="width:100%" align="center">
                <tags:submit targets="bodyContent"
                             formId="form"
                             value="MSG.find"
                             cssStyle="width:80px;"
                             preAction="assignInvoiceListAction!searchBills.do"
                             showLoadingText="true"
                             validateFunction="validateSearch();"/>
            </div>
        </div>

        <!-- phan hien thi danh sach dai hoa don da tao -->
        <div>
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.invoice.list'))}</legend>
                <jsp:include page="assignInvoiceListResult.jsp"/>
            </fieldset>
        </div>


        <!-- phan hien thi thong tin kho nhan -->
        <s:if test="#request.invoiceList != null && #request.invoiceList.size() != 0">
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.choise.unit.need.deliver'))}</legend>
                <table class="inputTbl6Col">
                    <tr>
                        <td><tags:label key="MSG.unit.type" required="true"/></td>
                        <td class="oddColumn">
                            <tags:imSelect name="form.ownerType"
                                           id="billDepartmentKind"
                                           cssClass="txtInputFull"
                                           disabled="false"
                                           list="1:MSG.GOD.261,2:MSG.GOD.262"
                                           headerKey="" headerValue="MSG.GOD.263"/>
                        </td>
                        <td><tags:label key="MSG.generates.unit_code" required="true" /></td>
                        <td class="oddColumn">
                            <tags:imSearch codeVariable="form.ownerCode" nameVariable="form.ownerName"
                                           codeLabel="MSG.generates.unit_code" nameLabel="MSG.unit.name"
                                           searchClassName="com.viettel.im.database.DAO.RetrieveInvoiceListDAO"
                                           searchMethodName="getListObject"
                                           otherParam="billDepartmentKind"
                                           getNameMethod="getObjectName"/>
                        </td>
                        <td style="text-align: right;">
                            <tags:submit targets="bodyContent"
                                         formId="form"
                                         value="MSG.consign.bill"
                                         preAction="assignInvoiceListAction!assignBill.do"
                                         showLoadingText="true"
                                         validateFunction="validateAssign();"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </s:if>
        <s:token/>
    </s:form>

</tags:imPanel>

<script type="text/javascript" language="javascript">
    var selected = '${fn:escapeXml(selectedValue)}';
    if(selected != null && selected.trim() != ""){
        alert("1");
        $( 'billSituation' ).value = selected;
    }

    isCheckBoxChecked = function(){
        if(document.getElementById('invoice') == null){
            return false;
        }
        var i = 0;
        var tbl = $( 'invoice' );
        var inputs = [];
        inputs = tbl.getElementsByTagName( "input" );
        for( i = 2; i < inputs.length; i++ ) {
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
            //            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.001')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.001"/>';
            $('createNewBillsForm.serialCode').select();
            $('createNewBillsForm.serialCode').focus();
            return false;
        }

        var billStartNumber = trim($('form.billStartNumber').value);
        var billEndNumber = trim($('form.billEndNumber').value);

        //truong dau dai phai la so nguyen duong
        if(billStartNumber != "" && !isInteger(billStartNumber)) {
            //            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.002')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.002"/>';
            $('form.billStartNumber').select();
            $('form.billStartNumber').focus();
            return false;
        }

        //truong cuoi dai phai la so nguyen duong
        if(billEndNumber != "" && !isInteger(billEndNumber)) {
            //            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.003')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.003"/>';
            $('form.billEndNumber').select();
            $('form.billEndNumber').focus();
            return false;
        }

        if(billStartNumber != ""  && billEndNumber != "" && (billStartNumber * 1 > billEndNumber * 1)){
            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.004')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.004"/>';
            $('form.billStartNumber').select();
            $('form.billStartNumber').focus();
            return false;
        }

        //trim cac truong can thiet
        $('form.serialCode').value = trim($('form.serialCode').value);
        $('form.billStartNumber').value = trim($('form.billStartNumber').value);
        $('form.billEndNumber').value = trim($('form.billEndNumber').value);

        return true;
    }
        
    validateAssign = function(){
        if($('billDepartmentKind').value.trim() == ""){
            //            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.007')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.007"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn loại đơn vị";
            $( 'billDepartmentKind' ).focus();
            return false;
        }

        if($('form.ownerCode').value.trim() == ""){
            //            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.007')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.007"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn loại đơn vị";
            $( 'form.ownerCode' ).focus();
            return false;
        }
        
        if(isCheckBoxChecked() == false){
            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.008')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.008"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn dải hóa đơn để giao";
            return false;
        }
        return true;                
    }
      
</script>
