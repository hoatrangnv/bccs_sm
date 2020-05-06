<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--  
    Document   : CreateNewBills
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     :
    Modified   : tamdt1, 21/10/2010
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
            request.setAttribute("invoiceList", request.getSession().getAttribute("invoiceList"));
%>

<tags:imPanel title="title.createNewBills.page">
    <s:form action="createNewBillsAction" method="post" id="createNewBillsForm" theme="simple">

        <!-- phan hien thi message -->
        <div>
            <tags:displayResult id="displayResultMsgClient" property="message" propertyValue="messageParam" type="key" />
        </div>

        <!-- phan hien thi thong tin ve dai hoa don -->
        <div class="divHasBorder">

            <table class="inputTbl4Col">
                <tr>
                    <td><tags:label key="MSG.bill.sign" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="createNewBillsForm.serialCode" nameVariable="createNewBillsForm.blockName"
                                       codeLabel="MSG.signBill" nameLabel="MSG.bin.book.type"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListBookType"
                                       getNameMethod="getBookTypeName"/>
                    </td>
                    <td><tags:label key="MSG.bill.range" required="true"/></td>
                    <td>
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:textfield name="createNewBillsForm.billStartNumber" id="createNewBillsForm.billStartNumber" maxlength="10" cssClass="txtInputFull"/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:textfield name="createNewBillsForm.billEndNumber" id="createNewBillsForm.billEndNumber" maxlength="10" cssClass="txtInputFull"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>


            <div align="center" style="padding-top:3px;">
                <tags:submit formId="createNewBillsForm"
                             showLoadingText="true"
                             validateFunction="validateAdd();"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.generates.addNew"
                             preAction="createNewBillsAction!createNewBill.do"
                             confirm="true"
                             confirmText="MSG.BIL.004"/>

                <tags:submit formId="createNewBillsForm"
                             showLoadingText="true"
                             validateFunction="validateSearch();"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.find"
                             preAction="createNewBillsAction!searchBills.do"/>
            </div>
        </div>

        <!-- phan hien thi danh sach dai hoa don da tao -->
        <div>
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.invoice.list'))}</legend>
                <jsp:include page="CreateNewBillsSearchResult.jsp"/>
            </fieldset>
        </div>
        <div class="divHasBorder" style="text-align: right; margin-top: 3px;">
            <s:if test="#session.invoiceList != null && #session.invoiceList.size() != 0">
                <tags:submit formId="createNewBillsForm"
                             targets="bodyContent"
                             value="MSG.generates.delete"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             preAction="createNewBillsAction!deleteBills.do"
                             validateFunction="validateDelete();"/>
            </s:if>
            <s:else>
                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" style="width:80px;" disabled />
            </s:else>
        </div>
        
                <s:token/>
    </s:form>
</tags:imPanel>

<script type="text/javascript" language="javascript">

    //focus vao truong dau tien
    $('createNewBillsForm.serialCode').select();
    $('createNewBillsForm.serialCode').focus();

    //
    isCheckBoxChecked = function(){
        if(document.getElementById('invoice') == null){
            return false;
        }
        var i = 0;
        var tbl = $( 'invoice' );
        var inputs = [];
        //var chkNum = 0;
        inputs = tbl.getElementsByTagName( "input" );
        for( i = 0; i < inputs.length; i++ ) {
            //if( inputs[i].type == "checkbox" ) chkNum++;
            if( inputs[i].type == "checkbox" && inputs[i].checked == true ) {
                return true;
            }
        }
        return false;
    }

    //
    validateAdd = function(){
        //
        var serialCode = trim($('createNewBillsForm.serialCode').value);
        if(serialCode == "") {
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.015"/>';
            $('createNewBillsForm.serialCode').select();
            $('createNewBillsForm.serialCode').focus();
            return false;
        }

        if(isHtmlTagFormat(serialCode)) {
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.001"/>';
            $('createNewBillsForm.serialCode').select();
            $('createNewBillsForm.serialCode').focus();
            return false;
        }

        var billStartNumber = trim($('createNewBillsForm.billStartNumber').value);
        var billEndNumber = trim($('createNewBillsForm.billEndNumber').value);

        if(billStartNumber == "") {
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.010"/>';
            $('createNewBillsForm.billStartNumber').select();
            $('createNewBillsForm.billStartNumber').focus();
            return false;
        }

        if(billEndNumber == "") {
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.012"/>';
            $('createNewBillsForm.billEndNumber').select();
            $('createNewBillsForm.billEndNumber').focus();
            return false;
        }

        //truong dau dai phai la so nguyen duong
        if(!isInteger(billStartNumber)) {
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.002"/>';
            $('createNewBillsForm.billStartNumber').select();
            $('createNewBillsForm.billStartNumber').focus();
            return false;
        }

        //truong cuoi dai phai la so nguyen duong
        if(!isInteger(billEndNumber)) {
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.003"/>';
            $('createNewBillsForm.billEndNumber').select();
            $('createNewBillsForm.billEndNumber').focus();
            return false;
        }

        if(billStartNumber * 1 > billEndNumber * 1){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.004"/>';
            $('createNewBillsForm.billStartNumber').select();
            $('createNewBillsForm.billStartNumber').focus();
            return false;
        }

        //trim cac truong can thiet
        $('createNewBillsForm.serialCode').value = trim($('createNewBillsForm.serialCode').value);
        $('createNewBillsForm.billStartNumber').value = trim($('createNewBillsForm.billStartNumber').value);
        $('createNewBillsForm.billEndNumber').value = trim($('createNewBillsForm.billEndNumber').value);


        return true;

    }

    //
    validateSearch = function(){
        //
        var serialCode = trim($('createNewBillsForm.serialCode').value);

        if(serialCode != "" && isHtmlTagFormat(serialCode)) {
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.001"/>';
            $('createNewBillsForm.serialCode').select();
            $('createNewBillsForm.serialCode').focus();
            return false;
        }

        var billStartNumber = trim($('createNewBillsForm.billStartNumber').value);
        var billEndNumber = trim($('createNewBillsForm.billEndNumber').value);

        //truong dau dai phai la so nguyen duong
        if(billStartNumber != "" && !isInteger(billStartNumber)) {
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.002"/>';
            $('createNewBillsForm.billStartNumber').select();
            $('createNewBillsForm.billStartNumber').focus();
            return false;
        }

        //truong cuoi dai phai la so nguyen duong
        if(billEndNumber != "" && !isInteger(billEndNumber)) {
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.003"/>';
            $('createNewBillsForm.billEndNumber').select();
            $('createNewBillsForm.billEndNumber').focus();
            return false;
        }

        if(billStartNumber != ""  && billEndNumber != "" && (billStartNumber * 1 > billEndNumber * 1)){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.004"/>';
            $('createNewBillsForm.billStartNumber').select();
            $('createNewBillsForm.billStartNumber').focus();
            return false;
        }

        //trim cac truong can thiet
        $('createNewBillsForm.serialCode').value = trim($('createNewBillsForm.serialCode').value);
        $('createNewBillsForm.billStartNumber').value = trim($('createNewBillsForm.billStartNumber').value);
        $('createNewBillsForm.billEndNumber').value = trim($('createNewBillsForm.billEndNumber').value);

        return true;

    }

    //
    validateDelete = function(){
        if(isCheckBoxChecked() == true){
            //if(!confirm('Bạn có chắc chắn muốn xóa những dải hóa đơn này ?!'))
            if(!confirm(getUnicodeMsg('<s:text name="MSG.BIL.003"/>'))){
                return false;
            }
        }
        else{
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn dải hóa đơn để xóa";
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.021"/>';
            return false;
        }

        return true;
    }

</script>
