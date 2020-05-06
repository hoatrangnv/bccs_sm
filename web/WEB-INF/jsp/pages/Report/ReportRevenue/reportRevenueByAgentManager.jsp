<%--/*
* Copyright YYYY Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/--%>

<%-- 
    Created on : 20/07/2011
    Author     : tutm1
    Desc       : bao cao doanh thu theo chuc danh nhan vien quan ly kenh
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
    <s:form action="reportRevenueAction" theme="simple" method="post" id="reportRevenueForm">
<s:token/>

        <s:hidden name="reportRevenueForm.reportType" id ="reportCheckType" disabled="true"></s:hidden>
        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td style="width:10%; "><tags:label key="MSG.shop.code" required="true"/></td>
                    <td style="width:30%; " class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.shopCode"
                                       nameVariable="reportRevenueForm.shopName"
                                       codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShopVT"
                                       getNameMethod="getShopName"
                                       elementNeedClearContent="reportRevenueForm.staffCode;reportRevenueForm.staffName;reportRevenueForm.ownerCode;reportRevenueForm.ownerName"
                                       roleList="reportRevenueShop"/>
                    </td>
                    <td style="width:10%; "><tags:label key="label.channel.type" /><%--Loại kênh--%></td>
                    <td style="width:30%; " class="oddColumn" colspan="3">
                        <tags:imSelect name="reportRevenueForm.channelTypeId" id="reportRevenueForm.channelTypeId" headerKey="" 
                                       headerValue="label.option" cssClass="txtInputFull"
                                       list="lstChannelTypeCol" listKey="channelTypeId" listValue="name"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MES.CHL.064" /></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.staffCode" nameVariable="reportRevenueForm.staffName"
                                       codeLabel="MES.CHL.064" nameLabel="MES.CHL.065"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getAgentManagers"
                                       getNameMethod="getStaffName"
                                       otherParam="reportRevenueForm.shopCode"
                                       elementNeedClearContent=""
                                       roleList="reportRevenueStaff"/>  
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.fromDate" required="true" /></td>
                    <td style="width:20%; " class="oddColumn">
                        <tags:dateChooser property="reportRevenueForm.fromDate"/>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.generates.to_date" required="true" /></td>
                    <td class="oddColumn">
                        <tags:dateChooser property="reportRevenueForm.toDate"/>
                    </td>
                </tr>
            </table>
        </div>

        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <%--<s:radio name="reportRevenueForm.groupType"
                     list="#{'1':'Tổng hợp', '2':'Chi tiết cấp dưới', '3':'Chi tiết nhân viên/CTV/ĐB/ĐL'}"/>--%>
            <tags:imRadio name="reportRevenueForm.groupType"
                          id="groupType"
                          list="1:MSG.report.detail,2:MSG.report.generate"/>
        </div>

    </s:form>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="reportRevenueForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MSG.report"
                     preAction="reportRevenueByAgentManagerAction!reportRevenueByAgentManager.do"/>
    </div>

    <!-- phan link download bao cao -->
    <div>
        <s:if test="#request.reportPath != null">
            <script>
                 window.open('${fn:escapeXml(contextPath)}/download.do?${fn:escapeXml(reportPath)}', '', 'toolbar=yes,scrollbars=yes');
                <%--window.open('${contextPath}/${fn:escapeXml(reportPath)}','','toolbar=yes,scrollbars=yes');--%>
                <%--window.open('${fn:escapeXml(reportPath)}','','toolbar=yes,scrollbars=yes');--%>
            </script>

            <%--
            <a href="${contextPath}/${fn:escapeXml(reportPath)}">
                <tags:displayResult id="reportMessage" property="reportMessage" type="key"/>
            </a>
            --%>
            <a href="${contextPath}/download.do?${fn:escapeXml(reportPath)}">
                <tags:displayResult id="reportMessage" property="reportMessage" type="key"/>
            </a>
            <%--
            <a href="${fn:escapeXml(reportPath)}">
                <tags:displayResult id="reportMessage" property="reportMessage" type="key"/>
            </a>
            --%>
        </s:if>
    </div>
</tags:imPanel>

<script language="javascript">
    clickChange = function(value) {
        $('reportCheckType').value = value;
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {

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
        dojo.event.topic.subscribe("reportRevenueAction/getShopName", function(value, key, text, widget){
            $('shopName').value = "";
            $('staffName').value = "";
            var _myWidget = dojo.widget.byId("staffCode");
            _myWidget.textInputNode.value = "";
        
            if (key != undefined) {
                getInputText("reportRevenueAction!getShopName.do?target=shopName&shopId=" + key);
            } else {
            
            }
        });

        //xu ly su kien sau khi chon 1 ma nhan vien
        dojo.event.topic.subscribe("reportRevenueAction/getStaffName", function(value, key, text, widget){
            $('staffName').value = "";
        
            if (key != undefined) {
                getInputText("reportRevenueAction!getStaffName.do?target=staffName&staffId=" + key);
            } else {
            
            }
        });


</script>
