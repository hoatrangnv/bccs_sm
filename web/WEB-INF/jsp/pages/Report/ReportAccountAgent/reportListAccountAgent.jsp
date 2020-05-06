<%-- 
    Document   : reportListAccountAgent
    Created on : Apr 17, 2010, 10:34:26 AM
    Author     : User
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
    <s:form action="reportListAccountAgentAction" theme="simple" method="post" id="reportRevenueForm">
<s:token/>

        <%--<s:hidden name="reportRevenueForm.reportType" id ="reportCheckType" disabled="true"></s:hidden>--%>
        <div class="divHasBorder">
            <table class="inputTbl4Col">
                <tr>
                    <td style="width:10%; "><tags:label key="MSG.branch.code" required="true" /></td>
                    <td style="width:30%; " class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.provinceCode" nameVariable="reportRevenueForm.provinceName"
                                       codeLabel="MSG.branch.code" nameLabel="MSG.branch.name"
                                       searchClassName="com.viettel.im.database.DAO.ReportListAccountAgentDAO"
                                       searchMethodName="getListProvince"
                                       elementNeedClearContent="reportRevenueForm.shopCode;reportRevenueForm.shopName"
                                       roleList="rolef9ProvinceReportListAccountAgent"/>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.shop.code" /></td>
                    <td style="width:30%; " >
                        <tags:imSearch codeVariable="reportRevenueForm.shopCode" nameVariable="reportRevenueForm.shopName"
                                       codeLabel="MSG.shop.code" nameLabel="MSG.shop.name"
                                       searchClassName="com.viettel.im.database.DAO.ReportListAccountAgentDAO"
                                       searchMethodName="getListShop"
                                       otherParam="reportRevenueForm.provinceCode"                                       
                                       roleList="rolef9ShopReportListAccountAgent"/>
                    </td>
                </tr>
                <tr>                    
                    <td class="label"><tags:label key="MSG.account.type" required="true" /></td>
                    <td class="text">
                        <%--<s:select name="reportRevenueForm.channelTypeId"
                                  id="channelTypeId"
                                  cssClass="txtInputFull"
                                  theme="simple"
                                  headerKey="" headerValue="--Loại tài khoản--"
                                  list="#{'1':'Đại lý','2':'Cộng tác viên','3':'Điểm bán'}"
                                  onchange="changeChannelType()"
                                  />
                        <tags:imSelect name="reportRevenueForm.channelTypeId" id="channelTypeId"
                                       cssClass="txtInputFull" disabled="false"
                                       list="1:MSG.RET.015,2:MSG.RET.016,3:MSG.RET.017"
                                       headerKey="" headerValue="MSG.RET.149"/>--%>
                        <tags:imSelect name="reportRevenueForm.channelTypeId"
                                       id="channelTypeId"
                                       cssClass="txtInputFull"
                                       theme="simple"
                                       headerKey="" headerValue="label.option"
                                       list="listChannelType" listKey="channelTypeId" listValue="name"/>
                    </td>
                    <td class="label"><tags:label key="MSG.account.stk.status" /></td>
                    <td class="text">
                        <%--<s:select name="reportRevenueForm.statusSTK"
                                  id="statusSTK"
                                  cssClass="txtInputFull"
                                  theme="simple"
                                  headerKey="" headerValue="--Trạng thái tài khoản STK--"
                                  list="#{'1':'Hoạt động','0':'Ngưng hoạt động','2':'Hủy','-1':'Chưa tạo TK'}"
                                  />--%>
                        <tags:imSelect name="reportRevenueForm.statusSTK" id="statusSTK"
                                       cssClass="txtInputFull" disabled="false"
                                       list="1:MSG.RET.025,0:MSG.RET.026,2:MSG.RET.027,-1:MSG.RET.184"
                                       headerKey="" headerValue="MSG.RET.154"/>
                    </td>
                </tr>
                <tr>                    
                    <td class="label"><tags:label key="MSG.account.balance.status" /></td>
                    <td class="text">
                        <%-- <s:select name="reportRevenueForm.statusAcountBalance"
                                   id="statusAcountBalance"
                                   cssClass="txtInputFull"
                                   theme="simple"
                                   headerKey="" headerValue="--Trạng thái tài khoản TKTT--"
                                   list="#{'1':'Hoạt động','2':'Ngưng hoạt động','3':'Hủy','-1':'Chưa tạo TK'}"
                                   />--%>
                        <tags:imSelect name="reportRevenueForm.statusAcountBalance" id="statusAcountBalance"
                                       cssClass="txtInputFull" disabled="false"
                                       list="1:MSG.RET.025,2:MSG.RET.026,3:MSG.RET.027,-1:MSG.RET.184"
                                       headerKey="" headerValue="MSG.RET.155"/>
                    </td>
                    <td class="label"><tags:label key="MSG.account.anypay.status" /></td>
                    <td class="text">
                        <%--<s:select name="reportRevenueForm.statusAnyPay"
                                  id="statusAnyPay"
                                  cssClass="txtInputFull"
                                  theme="simple"
                                  headerKey="" headerValue="--Trạng thái tài khoản TK AnyPay--"
                                  list="#{'1':'Hoạt động','0':'Ngưng hoạt động','9':'Hủy','-1':'Chưa tạo TK'}"
                                  />--%>
                        <tags:imSelect name="reportRevenueForm.statusAnyPay" id="statusAnyPay"
                                       cssClass="txtInputFull" disabled="false"
                                       list="1:MSG.RET.025,0:MSG.RET.026,9:MSG.RET.027,-1:MSG.RET.184"
                                       headerKey="" headerValue="MSG.RET.156"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.ctv_db_dl.status.code" /></td>
                    <td class="text">
                        <%--<s:select name="reportRevenueForm.statusOwner"
                                  id="statusOwner"
                                  cssClass="txtInputFull"
                                  theme="simple"
                                  headerKey="" headerValue="--Trạng thái mã ĐB/NVĐB--"
                                  list="#{'1':'Hoạt động','0':'Ngưng hoạt động'}"
                                  />--%>
                        <tags:imSelect name="reportRevenueForm.statusOwner" id="statusOwner"
                                       cssClass="txtInputFull" disabled="false"
                                       list="1:MSG.RET.025,0:MSG.RET.026"
                                       headerKey="" headerValue="MSG.RET.157"/>

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
                     value="MSG.report"
                     validateFunction="checkValidFields()"
                     preAction="reportListAccountAgentAction!reportListAccountAgent.do"/>
    </div>

    <!-- phan link download bao cao -->
    <div>
        <s:if test="#request.reportAccountPath != null">
            <script>
                window.open('${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}', '', 'toolbar=yes,scrollbars=yes');
                <%--window.open('${contextPath}/${fn:escapeXml(reportAccountPath)}','','toolbar=yes,scrollbars=yes');--%>
                <%--window.open('${fn:escapeXml(reportAccountPath)}','','toolbar=yes,scrollbars=yes');--%>
            </script>

             <a href="${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}">
                <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
             </a>
            <%--<a href="${contextPath}/${fn:escapeXml(reportAccountPath)}">
                <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
            </a>--%>
            <%--
            <a href="${fn:escapeXml(reportAccountPath)}">
                <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
            </a>
            --%>
        </s:if>
    </div>

</tags:imPanel>

<script language="javascript">    
    clickChange = function(value) {
        $('reportCheckType').value = value;
    }    
    checkValidFields = function() {
        if (trim($('reportRevenueForm.provinceCode').value) == ""){
    <%--$( 'message' ).innerHTML='Lỗi chưa chọn mã chi nhánh!';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.011"/>';
                $('reportRevenueForm.provinceCode').focus();
                return false;
            }
    <%--var channelTypeId = document.getElementById("channelTypeId");
    if (channelTypeId.value =="" && channelTypeId.value ==""){
        $( 'message' ).innerHTML='Bạn phải chọn loại tài khoản';
        $('channelTypeId').focus();
        return false;
    }        --%>
            return true;
        }
</script>
