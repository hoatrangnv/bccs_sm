<%-- 
    Document   : ReportStockUnsold
    Created on : Sep 3, 2014, 5:08:01 PM
    Author     : truongnq5
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
request.setAttribute("contextPath", request.getContextPath());
request.setAttribute("listStockType", request.getSession().getAttribute("listStockType"));
%>
<tags:imPanel title="title.reportStockUnsold.page">
    <!-- phan hien thi thong tin message -->
    <div style="width:100%">
        <tags:displayResult id="messageList" property="messageList" propertyValue="messageParam" type="key"/>
    </div>
    <s:form method="POST" id="reportRevenueForm" action="ReportStockUnsoldAction" theme="simple">
        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <%--<td style="width:10%; ">Mã cửa hàng<font color="red">*</font></td>--%>
                    <td style="width:10%; "><tags:label key="MSG.shop.code" required="true"/></td>
                    <td style="width:30%; " >
                        <tags:imSearch codeVariable="reportRevenueForm.shopCode" nameVariable="reportRevenueForm.shopName"
                                       codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShopIsVt"
                                       getNameMethod="getShopName"/>
                    </td>
                    <%-- Số tháng lấy báo cáo tổng hợp--%> 
                    <td style="width:10%; ">
                        <tags:label key="MSG.time.R6505" />
                    </td>
                    <td style="width:30%; ">
                        <tags:imSelect name="reportRevenueForm.month"
                                       id="reportRevenueForm.month"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="R6505.selected.month"
                                       list="1:R6505.1.month,2:R6505.2.month,3:R6505.3.month,4:R6505.4.month,5:R6505.5.month,6:R6505.6.month,7:R6505.7.month,8:R6505.8.month,9:R6505.9.month,10:R6505.10.month,11:R6505.11.month,12:R6505.12.month"
                                       />
                    </td>
                </tr>
                <tr>
                    <%--<td>Loại Mặt hàng</td>--%>
                    <td><tags:label key="MSG.stock.stock.type" /></td>
                    <td>
                        <tags:imSelect name="reportRevenueForm.stockTypeId"
                                       id="stockTypeId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.RET.160"
                                       list="listStockType"
                                       listKey="stockTypeId" listValue="name"
                                       onchange="changeStockType()"/>
                    </td>
                    <%--<td>Mặt hàng</td>--%>
                    <td ><tags:label key="MSG.RET.077"/></td>
                    <td >
                        <tags:imSearch codeVariable="reportRevenueForm.goodsCode" nameVariable="reportRevenueForm.goodsName"
                                       codeLabel="MSG.STK.026" nameLabel="MSG.STK.027"
                                       searchClassName="com.viettel.im.database.DAO.StockModelDAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName"
                                       otherParam="stockTypeId"
                                       />
                    </td>
                </tr>
                <tr>
                    <%--<td>Loại Báo cáo : 1 là chi tiết 2 là tổng hợp</td>--%>
                    <td colspan="6" style="width:100%">
                        <tags:imRadio name="reportRevenueForm.reportType"
                                      id="reportRevenueForm.reportType"
                                      list="1:MSG.report.detail,2:MSG.report.generate"
                                      onchange="radioClick(this.value)"/>
                    </td>
                </tr>
            </table>
        </div>
        
    </s:form>
        <!-- phan nut tac vu -->
        <div align="center" style="padding-top:5px; padding-bottom:5px;">
            <tags:submit formId="reportRevenueForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.RET.040"
                         validateFunction="checkValidFields()"
                         preAction="reportStockUnsoldAction!report.do"/>
        </div>
        <%-- phan link download bao cao 
        <div>
            <s:if test="#request.resultFile != null">
                <script>
                    window.open('${contextPath}/download.do?down=2&<s:property escapeJavaScript="true" value="#request.resultFile"/>', '', 'toolbar=yes,scrollbars=yes');
                </script>
                <a href="${contextPath}/download.do?down=2&<s:property value="#request.resultFile"/>">
                    <tags:displayResult id="returnMsg" property="returnMsg" type="key"/>
                </a>
            </s:if>
        </div>--%>
        <div>
        <s:if test="#request.reportAccountPath != null">
            <script>
                window.open('${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}', '', 'toolbar=yes,scrollbars=yes');
                <%--window.open('${contextPath}/${fn:escapeXml(reportAccountPath)}','','toolbar=yes,scrollbars=yes');--%>
                <%--window.open('${fn:escapeXml(reportAccountPath)}','','toolbar=yes,scrollbars=yes');--%>
            </script>
            <div>
                <a href='<s:property escapeJavaScript="true"  value="#request.reportAccountPath"/>'><tags:label key="MSG.clickhere.to.download" /></a>
            </div>
            <a href="${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}">
                <tags:displayResult id="returnMsg" property="returnMsg" type="key"/>
            </a>
            <%--
            <a href="${fn:escapeXml(reportAccountPath)}">
                <tags:displayResult id="returnMsg" property="returnMsg" type="key"/>
            </a>
            --%>
        </s:if>
    </div>
</tags:imPanel>
<script type="text/javascript">
    //xu ly su kien khi thay doi kieu xuat bao cao
    radioClick = function(value) {
        if (value == 2) {
            document.getElementById("reportRevenueForm.month").disabled = false;
            $('messageList').innerHTML = '';
        } else if (value == 1) {
            document.getElementById("reportRevenueForm.month").value = "";
            document.getElementById("reportRevenueForm.month").disabled = true;
            $('messageList').innerHTML = '';
        }
    }
    checkValidFields = function() {
        var shopCode = document.getElementById("reportRevenueForm.shopCode");
        var month = document.getElementById("reportRevenueForm.month");
        if (shopCode.value == null || shopCode.value == "") {
            $('messageList').innerHTML = '<s:property value="getText('ERR.REPORT.0001')"/>';
            $('reportRevenueForm.shopCode').focus();
            return false;
        }
        if (!document.getElementById("reportRevenueForm.month").disabled) {
            if (month.value == null || month.value == "") {
                $('messageList').innerHTML = '<s:property value="getText('R6505.Error.month')"/>';
                return false;
            }
        }
        return true;
    }
    changeStockType = function() {
        document.getElementById("reportRevenueForm.goodsCode").value = "";
        document.getElementById("reportRevenueForm.goodsName").value = "";
    }
</script>
