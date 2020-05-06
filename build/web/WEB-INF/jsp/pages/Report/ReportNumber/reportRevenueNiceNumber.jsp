<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : reportRevenueNiceNumberNiceNumber
    Created on : Jan 18, 2010, 3:14:07 PM
    Author     : NamNX
--%>
<%--
    Note:   bao cao doanh thu
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.criterion.report.create">

    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi tieu chi bao cao -->
    <s:form action="reportRevenueNiceNumberAction" theme="simple" method="post" id="reportRevenueNiceNumberForm">
<s:token/>

        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td style="width:10%; "><tags:label key="MSG.shop.code" required="true" /></td>
                    <td style="width:30%; " class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueNiceNumberForm.shopCode" nameVariable="reportRevenueNiceNumberForm.shopName"
                                       codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       elementNeedClearContent="reportRevenueNiceNumberForm.staffCode;reportRevenueNiceNumberForm.staffName"
                                       getNameMethod="getShopName"/>

                    </td>
                    <td style="width:10%; "><tags:label key="MSG.fromDate" required="true" /></td>
                    <td style="width:20%; " class="oddColumn">
                        <tags:dateChooser property="reportRevenueNiceNumberForm.fromDate"/>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.generates.to_date" required="true" /></td>
                    <td class="oddColumn">
                        <tags:dateChooser property="reportRevenueNiceNumberForm.toDate"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.staff.code" /></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueNiceNumberForm.staffCode" nameVariable="reportRevenueNiceNumberForm.staffName"
                                       codeLabel="MSG.RET.047" nameLabel="MSG.RET.048"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStaff"
                                       otherParam="reportRevenueNiceNumberForm.shopCode"
                                       getNameMethod="getStaffName"/>
                    </td>
                    <td><tags:label key="MSG.sale.service" /></td>
                    <td>
                        <tags:imSearch codeVariable="reportRevenueNiceNumberForm.saleServiceCode" nameVariable="reportRevenueNiceNumberForm.saleServiceName"
                                       codeLabel="MSG.RET.080" nameLabel="MSG.RET.081"
                                       searchClassName="com.viettel.im.database.DAO.ReportRevenueNiceNumberDAO"
                                       searchMethodName="getListSaleServices"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="6"><s:checkbox name="reportRevenueNiceNumberForm.hasMoney"/>
                    <label>${fn:escapeXml(imDef:imGetText('MSG.get.money'))}</label>
                    <s:checkbox name="reportRevenueNiceNumberForm.noMoney"/>
                    <label>${fn:escapeXml(imDef:imGetText('MSG.charge'))}</label>
                    </td>
                </tr>
            </table>
        </div>



    </s:form>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="reportRevenueNiceNumberForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MSG.report"
                     preAction="reportRevenueNiceNumberAction!reportRevenueNiceNumber.do"/>
    </div>

    <!-- phan link download bao cao -->
    <div>
        <s:if test="reportRevenueNiceNumberForm.pathExpFile!=null && reportRevenueNiceNumberForm.pathExpFile!=''">
            <script>
                <%--window.open('${contextPath}<s:property escapeJavaScript="true"  value="reportRevenueNiceNumberForm.pathExpFile"/>','','toolbar=yes,scrollbars=yes');--%>
                    window.open('<s:property escapeJavaScript="true"  value="reportRevenueNiceNumberForm.pathExpFile"/>','','toolbar=yes,scrollbars=yes');
            </script>
            <div>
                <%--<a href='${contextPath}<s:property escapeJavaScript="true"  value="reportRevenueNiceNumberForm.pathExpFile"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về</a>--%>
                <a href='<s:property escapeJavaScript="true"  value="reportRevenueNiceNumberForm.pathExpFile"/>'><tags:label key="MSG.clickhere.to.download" /></a>
            </div>
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
</script>
