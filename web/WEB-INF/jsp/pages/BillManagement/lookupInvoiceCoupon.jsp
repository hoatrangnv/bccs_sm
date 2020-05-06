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

<tags:imPanel title="Thông tin tra cứu cuống hoá đơn">
    <div>
        <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
    </div>
    <s:form action="lookupInvoiceCouponAction" method="post" id="form" theme="simple">
<s:token/>

        <div class="divHasBorder">
            <table class="inputTbl4Col">
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.185" required="true"/></td>
                    <td class="text" colspan="3">
                        <tags:imSearch codeVariable="form.shopCode" nameVariable="form.shopName"
                                       codeLabel="MSG.SAE.185" nameLabel="MSG.SAE.186"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"
                                       elementNeedClearContent="form.staffCode;form.staffName"
                                       roleList="reportRevenueByInvoicef9Shop"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.111"/></td>
                    <td class="text" colspan="3">
                        <tags:imSearch codeVariable="form.staffCode" nameVariable="form.staffName"
                                       codeLabel="MSG.SAE.111" nameLabel="MSG.SAE.112"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStaff"
                                       getNameMethod="getStaffName"
                                       otherParam="form.shopCode"
                                       roleList="reportRevenueByInvoicef9Staff"/>
                    </td>
                </tr>

                <%--tr>
                    <td class="label"><tags:label key="MSG.generates.shop_type" required="true"/></td>
                    <td class="text" >
                        <tags:imSelect name="form.ownerType" id="form.ownerType"
                                       list="2:MSG.GOD.262"
                                       cssClass="txtInputFull"
                                       headerKey="1" headerValue="MSG.SIK.226"/>
                    </td>
                    <td><tags:label key="MSG.generates.unit_code" required="true"/></td>
                    <td>
                        <tags:imSearch codeVariable="form.ownerCode" nameVariable="form.ownerName"
                                       codeLabel="MSG.generates.unit_code" nameLabel="MSG.unit.name"
                                       searchClassName="com.viettel.im.database.DAO.RetrieveInvoiceCouponDAO"
                                       searchMethodName="getListObject"
                                       otherParam="form.ownerType"
                                       getNameMethod="getObjectName"
                                       />
                    </td>
                </tr--%>
                <tr>
                    <td class="label"><tags:label key="MSG.bill.sign" required="false"/></td>
                    <td class="text" colspan="3">
                        <tags:imSearch codeVariable="form.serialNo" nameVariable="form.serialName"
                                       codeLabel="MSG.BIL.006" nameLabel="MSG.BIL.007"
                                       searchClassName="com.viettel.im.database.DAO.RetrieveInvoiceCouponDAO"
                                       searchMethodName="getListSerialNo"
                                       getNameMethod="getSerialName"
                                       />
                    </td>                    
                </tr>
                <tr>
                    <td class="label"> <tags:label key="MSG.bill.number.start"/></td>
                    <td class="text" >
                        <s:textfield name="form.fromInvoice" id="form.fromInvoice" cssClass="txtInputFull" maxlength="10"/>
                    </td>
                    <td class="label"><tags:label key="MSG.bill.number.end"/></td>
                    <td>
                        <s:textfield name="form.toInvoice" id="form.toInvoice" cssClass="txtInputFull" maxlength="10"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.GOD.013"/></td>
                    <td class="text" >
                        <tags:imSelect name="form.status" id="form.status"
                                       list="1:MSG.DET.040, 0:MSG.GOD.196"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.189"/>
                    </td>
                </tr>
            </table>
            <div style="width:100%" align="center">
                <tags:submit targets="bodyContent" formId="form"
                             value="MSG.find"
                             cssStyle="width:80px;"
                             preAction="lookupInvoiceCouponAction!searchInvoiceCoupon.do"
                             showLoadingText="true" validateFunction="validateSearch()"/>
            </div>
        </div>

        <div>
            <jsp:include page="listLookupInvoiceCoupon.jsp"/>
        </div>
    </s:form>
</tags:imPanel>

<script type="text/javascript" language="javascript">
    var regExp1 = '/^[0-9]+$/';
    var htmlTag = '<[^>]*>';

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


        
        if ($( 'form.shopCode' ).value.trim() == ""){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.007"/>';
            $( 'form.shopCode' ).select();
            $( 'form.shopCode' ).focus();
            return false;
        }
     
        return true;
    }

</script>
