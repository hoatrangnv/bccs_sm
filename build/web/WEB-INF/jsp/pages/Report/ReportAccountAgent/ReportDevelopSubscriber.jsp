<%-- 
    Document   : ReportDevelopSubscriber
    Created on : Apr 15, 2010, 2:47:51 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
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
    <s:form action="reportDevelopSubscriberAction" theme="simple" method="post" id="reportRevenueForm">
<s:token/>

        <%--<s:hidden name="reportRevenueForm.reportType" id ="reportCheckType" disabled="true"></s:hidden>--%>
        <div class="divHasBorder">
            <table class="inputTbl4Col">
                <tr>
                    <td style="width:10%; "><tags:label key="MSG.branch.code" required="true" /></td>
                    <td style="width:30%; " class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.provinceCode" nameVariable="reportRevenueForm.provinceName"
                                       codeLabel="MSG.branch.code" nameLabel="MSG.branch.name"
                                       searchClassName="com.viettel.im.database.DAO.ReportDevelopSubscriberDAO"
                                       searchMethodName="getListProvince"
                                       elementNeedClearContent="reportRevenueForm.shopCode;reportRevenueForm.shopName;reportRevenueForm.staffManageCode;reportRevenueForm.staffManageName"
                                       roleList="rolef9ProvinceReportDevelopSubscriber"/>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.shop.code" /></td>
                    <td style="width:30%; " >
                        <tags:imSearch codeVariable="reportRevenueForm.shopCode" nameVariable="reportRevenueForm.shopName"
                                       codeLabel="MSG.shop.code" nameLabel="MSG.shop.name"
                                       searchClassName="com.viettel.im.database.DAO.ReportAccountAgentDAO"
                                       searchMethodName="getListShop"
                                       otherParam="reportRevenueForm.provinceCode"
                                       elementNeedClearContent="reportRevenueForm.staffManageCode;reportRevenueForm.staffManageName"
                                       roleList="rolef9ShopReportDevelopSubscriber"/>
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
                                       roleList="rolef9StaffManageMentReportDevelopSubscriber"/>
                    </td>
                    <td ><tags:label key="MSG.account.type" required="true" /></td>
                    <td class="text">
                        <%-- <s:select name="reportRevenueForm.channelTypeId"
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
                                       headerKey="" headerValue="MSG.RET.149"
                                       onchange="changeChannelType()"/>--%>
                        <tags:imSelect name="reportRevenueForm.channelTypeId" id="channelTypeId"
                                       cssClass="txtInputFull" disabled="false"
                                       headerKey="" headerValue="label.option"
                                       list="listChannelType" listKey="channelTypeId" listValue="name"
                                       onchange="changeChannelType()"/>
                    </td>                    
                </tr>
                <tr>
                    <td><tags:label key="MSG.connection.method" /></td>
                    <td class="oddColumn">
                        <%-- <s:select name="reportRevenueForm.methode"
                                   id="methode"
                                   cssClass="txtInputFull"
                                   theme="simple"
                                   disabled="true"
                                   headerKey="" headerValue="--Hình thức đấu nối--"
                                   list="#{'1':'Qua sim đa năng','2':'Qua HT QLKH'}"
                                   />--%>
                        <tags:imSelect name="reportRevenueForm.methode" id="methode"
                                       cssClass="txtInputFull" disabled="true"
                                       list="1:MSG.RET.021,2:MSG.RET.022"
                                       headerKey="" headerValue="MSG.RET.152"/>
                    </td>
                    <td><tags:label key="MSG.type" /></td>
                    <td class="text">
                        <%--<s:select name="reportRevenueForm.isdnType"
                                  id="isdnType"
                                  cssClass="txtInputFull"
                                  theme="simple"
                                  headerKey="" headerValue="--Loại hình--"
                                  list="#{'1':'Trả trước','2':'Trả sau'}"
                                  />--%>
                        <tags:imSelect name="reportRevenueForm.isdnType" id="isdnType"
                                       cssClass="txtInputFull"
                                       list="1:MSG.RET.023,2:MSG.RET.024"
                                       headerKey="" headerValue="MSG.RET.153"/>
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
                     list="#{'1':'Tổng hợp', '2':'Chi tiết'}"/>--%>
            <tags:imRadio name="reportRevenueForm.reportType" id="reportType"
                          list="1:MSG.RET.115,2:MSG.RET.116"/>
        </div>
        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <s:checkbox name="reportRevenueForm.notBilledSaleTrans"/>
            <label>${fn:escapeXml(imDef:imGetText('MSG.not.billed'))}</label>
            <s:checkbox name="reportRevenueForm.billedSaleTrans"/>
            <label>${fn:escapeXml(imDef:imGetText('MSG.billed'))}</label>
            <s:checkbox name="reportRevenueForm.cancelSaleTrans"/>
            <label>${fn:escapeXml(imDef:imGetText('MSG.destroy'))}</label>
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
                     preAction="reportDevelopSubscriberAction!reportSubscriber.do"/>
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
    var channelTypeId = document.getElementById("channelTypeId");
    if (channelTypeId.value =="1"){
        $('methode').disabled = false;        
    }
    else{
        $('methode').disabled = true;        
    }
    clickChange = function(value) {
        $('reportCheckType').value = value;
    }
    changeChannelType = function() {
        var channelTypeId = document.getElementById("channelTypeId");        
        if (channelTypeId.value =="1"){
            $('methode').disabled = false;
            $('methode').value = "";
        }
        else{
            $('methode').disabled = true;
            $('methode').value = "";            
        }
    }
    checkValidFields = function() {
        var channelTypeId = document.getElementById("channelTypeId");
        if (channelTypeId.value =="" && channelTypeId.value ==""){
    <%--$( 'message' ).innerHTML='Bạn phải chọn loại tài khoản';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.008"/>';;
                $('channelTypeId').focus();
                return false;
            }
            var shopCode = document.getElementById("reportRevenueForm.shopCode");
            var i = 0;
            for(i = 1; i < 2; i=i+1) {
                var radioId = "reportType" + i;
                if($(radioId).checked == true) {
                    break;
                }
            }
            if(i == 2){
                if (shopCode.value =="" && shopCode.value ==""){
    <%--$( 'message' ).innerHTML='Bạn phải chọn mã cửa hàng';--%>
                    $( 'message' ).innerHTML='<s:text name="ERR.RET.009"/>';
                    $('reportRevenueForm.shopCode').focus();
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
