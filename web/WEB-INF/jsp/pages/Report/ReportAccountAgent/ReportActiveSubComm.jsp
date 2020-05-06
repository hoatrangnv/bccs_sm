<%-- 
    Document   : 
    Created on : May 21, 2010, 2:20:14 PM
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
    <s:form action="reportActiveSubCommAction" theme="simple" method="post" id="reportRevenueForm">
<s:token/>

        <%--<s:hidden name="reportRevenueForm.reportType" id ="reportCheckType" disabled="true"></s:hidden>--%>
        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td style="width:10%; "><tags:label key="MSG.shop.code" required="true" /></td>
                    <td style="width:40%; " class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.shopCode" nameVariable="reportRevenueForm.shopName"
                                       codeLabel="MSG.shop.code" nameLabel="MSG.shop.name"
                                       searchClassName="com.viettel.im.database.DAO.ReportAccountAgentDAO"
                                       searchMethodName="getListShop"
                                       elementNeedClearContent="reportRevenueForm.staffManageCode;reportRevenueForm.staffManageName;reportRevenueForm.staffCode;reportRevenueForm.staffName"
                                       roleList="rolef9ShopReportActiveSubscriber"/>
                    </td>
                    <td ><tags:label key="MSG.account.type" required="true" /></td>
                    <td >
                        <%--<s:select name="reportRevenueForm.channelTypeId"
                                  id="channelTypeId"
                                  cssClass="txtInputFull"
                                  theme="simple"
                                  headerKey="" headerValue="--Loại tài khoản--"
                                  list="#{'1':'Đại lý','2':'Cộng tác viên','3':'Điểm bán'}"
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

                </tr>
                <tr>
                    <td><tags:label key="MSG.staff.mn" /></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.staffManageCode" nameVariable="reportRevenueForm.staffManageName"
                                       codeLabel="MSG.staff.manager.code" nameLabel="MSG.staff.manager.name"
                                       searchClassName="com.viettel.im.database.DAO.ReportAccountAgentDAO"
                                       searchMethodName="getListStaffManagement"
                                       otherParam="reportRevenueForm.shopCode;channelTypeId"
                                       getNameMethod="getListStaffManagement"
                                       elementNeedClearContent="reportRevenueForm.staffCode;reportRevenueForm.staffName"
                                       roleList="rolef9StaffManageMentReportActiveSubscriber"/>
                    </td>
                    <td><tags:label key="MSG.account.code" required="true" /></td>
                    <td >
                        <tags:imSearch codeVariable="reportRevenueForm.staffCode" nameVariable="reportRevenueForm.staffName"
                                       codeLabel="MSG.account.code" nameLabel="MSG.account.name"
                                       searchClassName="com.viettel.im.database.DAO.ReportAccountAgentDAO"
                                       searchMethodName="getListStaffOrAgent"
                                       otherParam="reportRevenueForm.shopCode;channelTypeId;reportRevenueForm.staffManageCode"
                                       elementNeedClearContent="reportRevenueForm.ownerCode;reportRevenueForm.ownerName"
                                       roleList=""/>
                    </td>

                </tr>
                <tr>
                    <td style="width:10%; "><tags:label key="MSG.fromDate" required="true" /></td>
                    <td style="width:20%; " class="oddColumn">
                        <tags:dateChooser property="reportRevenueForm.fromDate" id ="fromDate"/>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.generates.to_date" required="true" /></td>
                    <td class="oddColumn">
                        <tags:dateChooser property="reportRevenueForm.toDate" id ="toDate"/>
                    </td>

                </tr>
            </table>
        </div>
        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <%--<s:radio name="reportRevenueForm.reportType" id ="reportType"
                     list="#{'1':'Báo cáo chi tiết theo TK', '2':'Báo cáo tổng hợp tại CH'}"/>--%>
            <tags:imRadio name="reportRevenueForm.reportType" id="reportType"
                          list="1:MSG.RET.110,2:MSG.RET.114"/>
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
                     preAction="reportActiveSubCommAction!reportActiveSubComm.do"/>
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
            <%--
            <a href="${contextPath}/${fn:escapeXml(reportAccountPath)}">
                <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
            </a>
            --%>
            <%--
            <a href="${fn:escapeXml(reportAccountPath)}">
                <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
            </a>
            --%>
        </s:if>
    </div>

</tags:imPanel>

<script language="javascript">

    checkValidFields = function() {
        var shopCode = document.getElementById("reportRevenueForm.shopCode");
        if (shopCode.value =="" && shopCode.value ==""){
    <%--$( 'message' ).innerHTML='Bạn phải chọn mã cửa hàng';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.009"/>';
                $('reportRevenueForm.shopCode').focus();
                return false;
            }

            var channelTypeId = document.getElementById("channelTypeId");
            if (channelTypeId.value =="" && channelTypeId.value ==""){
    <%--$( 'message' ).innerHTML='Bạn phải chọn loại tài khoản';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.008"/>';
                $('channelTypeId').focus();
                return false;
            }
            var staffCode = document.getElementById("reportRevenueForm.staffCode");
            for(i = 1; i < 4; i=i+1) {
                var radioId = "reportType" + i;
                if($(radioId).checked == true) {
                    break;
                }
            }
            if(i == 1){
                if (staffCode.value =="" && staffCode.value ==""){
    <%--$( 'message' ).innerHTML='Bạn phải chọn mã tài khoản';--%>
                    $( 'message' ).innerHTML='<s:text name="ERR.RET.007"/>';
                    $('reportRevenueForm.staffCode').focus();
                    return false;
                }
            }
        

            var dateExported= dojo.widget.byId("fromDate");
            if(dateExported.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'message' ).innerHTML='Chưa nhập ngày báo cáo từ ngày';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.002"/>';
                $('fromDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
    <%--$( 'message' ).innerHTML='Ngày báo cáo từ ngày không hợp lệ';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.003"/>';
                $('fromDate').focus();
                return false;
            }

            var dateExported1 = dojo.widget.byId("toDate");
            if(dateExported1.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'message' ).innerHTML='Chưa nhập ngày báo cáo đến ngày';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.004"/>';
                $('toDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported1.domNode.childNodes[1].value)){
    <%--$( 'message' ).innerHTML='Ngày báo cáo đến ngày không hợp lệ';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.005"/>';
                $('toDate').focus();
                return false;
            }

            if( !isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
    <%--$( 'message' ).innerHTML='Ngày báo cáo từ ngày phải nhỏ hơn Ngày báo cáo đến ngày';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.006"/>';
                $('fromDate').focus();

                return false;
            }
            return true;
        }
</script>
