<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : Report Bill
    Created on : 17/04/2009, 10:51:45 AM
    Author     :
    desc       : tra cuu hoa don
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
            request.setAttribute("lstStatus", request.getSession().getAttribute("lstStatus"));
%>

<tags:imPanel title="title.reportBills.page">
    <s:form action="reportBillsAction" method="POST" id="form" theme="simple">
        <!-- phan hien thi message -->
        <div>
            <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
        </div>

        <!-- phan hien thi thog tin tim kiem dai hoa don -->
        <div class="divHasBorder">
            <table class="inputTbl4Col">
                <tr>
                    <td style="width:13%"><tags:label key="MSG.stock.type" /></td>
                    <td class="oddColumn" style="width:20%">
                        <tags:imSelect name="form.billDepartmentKind"
                                       id="billDepartmentKind"
                                       cssClass="txtInputFull"
                                       list="1:MSG.RET.106,2:MSG.RET.107"
                                       headerKey="" headerValue="MSG.RET.176"/>
                    </td>
                    <td style="width:13%"><tags:label key="MSG.RET.066" /></td>
                    <td>
                        <tags:imSearch codeVariable="form.billDepartmentName" nameVariable="form.billDepartmentNameB"
                                       codeLabel="MSG.RET.066" nameLabel="MSG.RET.067"
                                       searchClassName="com.viettel.im.database.DAO.InvoiceListReportDAO"
                                       searchMethodName="getListShopOrStaff"
                                       otherParam="billDepartmentKind"
                                       getNameMethod="getNameShopOrStaff"/>


                    </td>
                </tr>
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
                        <tags:imSearch codeVariable="form.billSerial" nameVariable="form.billCategory"
                                       codeLabel="MSG.signBill" nameLabel="MSG.bin.book.type"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListBookType"
                                       getNameMethod="getBookTypeName"
                                       otherParam="form.invoiceType"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.generates.status" /></td>
                    <td class="oddColumn">
                        <%--tags:imSelect name="form.status" theme="simple"
                                       id="status"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.189"
                                       list="lstStatus"
                                       listKey="objCode" listValue="objName"/--%>
                        <tags:imSelect name="form.status" theme="simple"
                                       id="status"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.189"
                                       list="   1:msg.invoiceListStatus.available,
                                       2:msg.invoiceListStatus.notConfirm,
                                       4:msg.invoiceListStatus.complete,
                                       5:msg.invoiceListStatus.waitApproveDestroy,
                                       6:msg.invoiceListStatus.destroy"/>
                    </td>
                    <td><tags:label key="MSG.bill.range"/></td>
                    <td>
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:textfield name="form.billStartNumber" id="form.billStartNumber" maxlength="10" cssClass="txtInputFull"/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:textfield name="form.billEndNumber" id="form.billEndNumber" maxlength="10" cssClass="txtInputFull"/>
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
                             preAction="reportBillsAction!searchBills.do"
                             showLoadingText="true"
                             validateFunction="validateForm()"/>
            </div>

        </div>

        <div>
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.invoice.list'))}</legend>
                <sx:div id="ResultSearch">
                    <jsp:include page="ReportBillsSearchList.jsp"/>
                </sx:div>
            </fieldset>
        </div>
        <s:token/>
    </s:form>
</tags:imPanel>


<script type="text/javascript" language="javascript">

    validateForm = function(){
        //
        //trim cac truong can thiet
        $('form.billSerial').value = trim($('form.billSerial').value);
        $('form.billStartNumber').value = trim($('form.billStartNumber').value);
        $('form.billEndNumber').value = trim($('form.billEndNumber').value);

        var serialCode = trim($('form.billSerial').value);

        if(serialCode != "" && isHtmlTagFormat(serialCode)) {
            //            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.001')"/>';
            $('message').innerHTML= '<s:text name="ERR.BIL.001"/>';
            $('form.billSerial').select();
            $('form.billSerial').focus();
            return false;
        }

        var billStartNumber = trim($('form.billStartNumber').value);
        var billEndNumber = trim($('form.billEndNumber').value);

        //truong dau dai phai la so nguyen duong
        if(billStartNumber != "" && !isInteger(billStartNumber)) {
            //            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.002')"/>';
            $('message').innerHTML= '<s:text name="ERR.BIL.002"/>';
            $('form.billStartNumber').select();
            $('form.billStartNumber').focus();
            return false;
        }

        //truong cuoi dai phai la so nguyen duong
        if(billEndNumber != "" && !isInteger(billEndNumber)) {
            //            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.003')"/>';
            $('message').innerHTML= '<s:text name="ERR.BIL.003"/>';
            $('form.billEndNumber').select();
            $('form.billEndNumber').focus();
            return false;
        }

        if(billStartNumber != ""  && billEndNumber != "" && (billStartNumber * 1 > billEndNumber * 1)){
            //            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.004')"/>';
            $('message').innerHTML= '<s:text name="ERR.BIL.004"/>';
            $('form.billStartNumber').select();
            $('form.billStartNumber').focus();
            return false;
        }

        //kiem tra dieu kien, neu da nhap ma kho thi p chon loai kho
        if(trim($('form.billDepartmentName').value) != "" && trim($('billDepartmentKind').value) == "") {
            //            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.RET.049')"/>';
            $('message').innerHTML= '<s:text name="ERR.RET.049"/>';
            $('billDepartmentKind').focus();
            return false;
        }

        //kiem tra dieu kien, neu da nhap ma kho thi p chon loai kho
        if(trim($('form.billSerial').value) != "" && trim($('form.invoiceType').value) == "") {
            //            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('err.invoiceList.notSelectInvoiceType')"/>';
            $('message').innerHTML= '<s:text name="err.invoiceList.notSelectInvoiceType"/>';
            $('form.invoiceType').focus();
            return false;
        }

        return true;
    }


</script>
