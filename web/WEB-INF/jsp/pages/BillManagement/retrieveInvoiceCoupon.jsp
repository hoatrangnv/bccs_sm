<%-- 
    Document   : retrieveInvoiceCoupon
    Created on : Sep 9, 2010, 8:25:37 AM
    Author     : tronglv
--%>

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

<tags:imPanel title="MSG.BIL.005">
    <div>
        <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
    </div>
    <s:form action="retrieveInvoiceCouponAction" method="post" id="form" theme="simple">
        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td style="width:10%;"><tags:label key="MSG.generates.shop_type" required="true"/></td>
                    <td class="oddColumn" style="width:23%;">
                        <tags:imSelect name="form.ownerType" id="form.ownerType"
                                       list="2:MSG.GOD.262"
                                       cssClass="txtInputFull"
                                       headerKey="1" headerValue="MSG.GOD.261"/>
                    </td>
                    <td style="width:10%;"><tags:label key="MSG.generates.unit_code" required="true"/></td>
                    <td class="oddColumn" style="width:23%;">
                        <tags:imSearch codeVariable="form.ownerCode" nameVariable="form.ownerName"
                                       codeLabel="MSG.generates.unit_code" nameLabel="MSG.unit.name"
                                       searchClassName="com.viettel.im.database.DAO.RetrieveInvoiceCouponDAO"
                                       searchMethodName="getListObject"
                                       otherParam="form.ownerType"
                                       getNameMethod="getObjectName"/>
                    </td>
                    <td style="width:10%;"><tags:label key="MSG.GOD.013"/></td>
                    <td>
                        <tags:imSelect name="form.status" id="form.status"
                                       list="1:MSG.DET.040, 0:MSG.GOD.196"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.189"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.bill.sign" required="false"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="form.serialNo" nameVariable="form.serialName"
                                       codeLabel="MSG.BIL.006" nameLabel="MSG.BIL.007"
                                       searchClassName="com.viettel.im.database.DAO.RetrieveInvoiceCouponDAO"
                                       searchMethodName="getListSerialNo"
                                       otherParam="form.ownerType"
                                       getNameMethod="getSerialName"
                                       />
                    </td>
                    <td> <tags:label key="MSG.GOD.253"/></td>
                    <td class="oddColumn">
                        <s:textfield name="form.fromInvoice" id="form.fromInvoice" cssClass="txtInputFull" maxlength="10"/>
                    </td>
                    <td><tags:label key="MSG.GOD.254"/></td>
                    <td>
                        <s:textfield name="form.toInvoice" id="form.toInvoice" cssClass="txtInputFull" maxlength="10"/>
                    </td>
                </tr>
            </table>
            <div style="width:100%" align="center">
                <tags:submit targets="bodyContent" formId="form"
                             value="MSG.find"
                             cssStyle="width:80px;"
                             preAction="retrieveInvoiceCouponAction!searchInvoiceCoupon.do"
                             showLoadingText="true" validateFunction="validateSearch()"/>
            </div>
        </div>

        <div>
            <jsp:include page="listRetrieveInvoiceCoupon.jsp"/>
        </div>
            <s:token/>
    </s:form>
</tags:imPanel>

<script type="text/javascript" language="javascript">
    var regExp1 = '/^[0-9]+$/';
    var htmlTag = '<[^>]*>';

    $('form.ownerType').focus();

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
        $('form.fromInvoice').value = $( 'form.fromInvoice' ).value.trim();
        $('form.toInvoice').value = $( 'form.toInvoice' ).value.trim();
       
        var result1 = trim($('form.fromInvoice').value);
        var result2 = trim($('form.toInvoice').value);

        if ($('form.fromInvoice').value.trim() != "" && !isInteger(result1)){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.002"/>';
            $( 'form.fromInvoice' ).select();
            $( 'form.fromInvoice' ).focus();
            return false;
        }
        if ($('form.toInvoice').value.trim() != "" && !isInteger(result2)){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.003"/>';
            $( 'form.toInvoice' ).select();
            $( 'form.toInvoice' ).focus();
            return false;
        }

        var intResult1 = result1*1;
        var intResult2 = result2*1;

        if(intResult1 != 0 && intResult2 != 0 && (intResult1 > intResult2)){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.004"/>';
            $( 'form.fromInvoice' ).select();
            $( 'form.fromInvoice' ).focus();
            return false;
        }
        
        if ($( 'form.ownerCode' ).value.trim() == ""){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.007"/>';
            $( 'form.ownerCode' ).select();
            $( 'form.ownerCode' ).focus();
            return false;
        }
     
        return true;
    }
        
    validateRetrieve = function(){

        if(isCheckBoxChecked() == false){
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.023"/>';
            return false;
        }
        return true;
    }

</script>