<%-- 
    Document   : reportInvoice
    Created on : Jun 9, 2009, 1:44:46 PM
    Author     : tamdt1
    Desc       : bao cao thanh quyet toan hoa don
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.criterion.report.create">

    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi tieu chi bao cao -->
    <s:form action="reportInvoiceAction" theme="simple" method="post" id="reportInvoiceForm">
<s:token/>

        <div class="divHasBorder">
            <table class="inputTbl4Col">
                <tr>
                    <td><tags:label key="MSG.generates.unit_code" required="true" /></td>
                    <td class="oddColumn" colspan="3">
                        <tags:imSearch codeVariable="reportInvoiceForm.shopCode" nameVariable="reportInvoiceForm.shopName"
                                       codeLabel="MSG.RET.032" nameLabel="MSG.RET.033"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       elementNeedClearContent="reportInvoiceForm.staffCode;reportInvoiceForm.staffName"
                                       getNameMethod="getShopName"/>
                    </td>
                </tr>


                <tr>
                    <td><tags:label key="MSG.staff.code" /></td>
                    <td class="oddColumn" colspan="3">
                        <tags:imSearch codeVariable="reportInvoiceForm.staffCode" nameVariable="reportInvoiceForm.staffName"
                                       codeLabel="MSG.RET.047" nameLabel="MSG.RET.048"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStaff"
                                       otherParam="reportInvoiceForm.shopCode"
                                       getNameMethod="getStaffName"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:10%; "><tags:label key="MSG.fromDate" required="true" /></td>
                    <td style="width:20%; " class="oddColumn">
                        <tags:dateChooser property="reportInvoiceForm.fromDate"/>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.generates.to_date" required="true" /></td>
                    <td style="width:20%;" class="oddColumn">
                        <tags:dateChooser property="reportInvoiceForm.toDate"/>
                    </td>
                    <td></td>
                </tr>
            </table>
        </div>
    </s:form>

    <!-- phan nut tac vu -->
    <div style="margin-top:3px;">
        <tags:submit formId="reportInvoiceForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MSG.report"
                     preAction="reportInvoiceAction!reportInvoice.do"/>
    </div>

    <!-- phan link download bao cao -->
    <div>
        <s:if test="#request.reportInvoicePath != null">
            <script>
                window.open('${contextPath}/download.do?${fn:escapeXml(reportInvoicePath)}', '', 'toolbar=yes,scrollbars=yes');
                <%--
                window.open('${contextPath}/${fn:escapeXml(reportInvoicePath)}','','toolbar=yes,scrollbars=yes');
                
                      window.open('${fn:escapeXml(reportInvoicePath)}','','toolbar=yes,scrollbars=yes');--%>
            </script>
            <%--
              <a href="${contextPath}/${fn:escapeXml(reportInvoicePath)}">
            
            <a href="${fn:escapeXml(reportInvoicePath)}">
                <tags:displayResult id="reportInvoiceMessage" property="reportInvoiceMessage" type="key"/>
            </a>
            --%>
            <a href="${contextPath}/download.do?${fn:escapeXml(reportInvoicePath)}">
                           <tags:displayResult id="reportInvoiceMessage" property="reportInvoiceMessage" type="key"/>
            </a>
        </s:if>
    </div>



</tags:imPanel>

<script language="javascript">

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        var fromPrice = $('fromPrice').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        var toPrice = $('toPrice').value.replace(/\,/g,""); //loai bo dau , trong chuoi

        if(fromPrice != '' && !isInteger(trim(fromPrice))) {
            <%--$('message').innerHTML = "!!!Lỗi. Trường Từ giá phải là số không âm";--%>
            $('message').innerHTML='<s:text name="ERR.RET.043"/>';
            $('fromPrice').select();
            $('fromPrice').focus();
            return false;
        }

        if(toPrice != '' && !isInteger(trim(toPrice))) {
            <%--$('message').innerHTML = "!!!Lỗi. Trường Đến giá phải là số không âm";--%>
            $('message').innerHTML='<s:text name="ERR.RET.044"/>';
            $('toPrice').select();
            $('toPrice').focus();
            return false;
        }

        var fromIsdn = $('fromIsdn').value;
        var toIsdn = $('toIsdn').value;

        if(fromIsdn != '' && !isInteger(trim(fromIsdn))) {
            <%--$('message').innerHTML = "!!!Lỗi. Trường Từ số isdn phải là số không âm";--%>
            $('message').innerHTML='<s:text name="ERR.RET.045"/>';
            $('fromIsdn').select();
            $('fromIsdn').focus();
            return false;
        }

        if(toIsdn != '' && !isInteger(trim(toIsdn))) {
            <%--$('message').innerHTML = "!!!Lỗi. Trường Đến số isdn phải là số không âm";--%>
            $('message').innerHTML='<s:text name="ERR.RET.046"/>';
            $('toIsdn').select();
            $('toIsdn').focus();
            return false;
        }

        //trim cac truong can thiet
        $('fromPrice').value = trim($('fromPrice').value);
        $('toPrice').value = trim($('toPrice').value);

        return true;
    }

    //xu ly su kien sau khi chon 1 ma cua hang
    dojo.event.topic.subscribe("reportInvoiceAction/getShopName", function(value, key, text, widget){
        if (key != undefined) {
            getInputText("reportInvoiceAction!getShopName.do?target=shopName&shopId=" + key);
        } else {
            $('shopName').value = "";
        }
    });

    //xu ly su kien sau khi chon 1 ma nhan vien
    dojo.event.topic.subscribe("reportInvoiceAction/getStaffName", function(value, key, text, widget){
        if (key != undefined) {
            getInputText("reportInvoiceAction!getStaffName.do?target=staffName&staffId=" + key);
        } else {
            $('staffName').value = "";
        }
    });

</script>
