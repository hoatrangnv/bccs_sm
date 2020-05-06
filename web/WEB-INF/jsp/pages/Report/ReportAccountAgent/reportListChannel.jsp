<%-- 
    Document   : reportListChannel
    Created on : Jul 19, 2011, 4:28:09 PM
    Author     : kdvt_tronglv
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
                    <td style="width:10%; "><tags:label key="MSG.shop.code" required="true"/><%--Mã cửa hàng--%></td>
                    <td style="width:30%; " >
                        <tags:imSearch codeVariable="reportRevenueForm.shopCode" nameVariable="reportRevenueForm.shopName"
                                       codeLabel="MSG.shop.code" nameLabel="MSG.shop.name"
                                       searchClassName="com.viettel.im.database.DAO.ReportListAccountAgentDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"
                                       roleList=""/>
                    </td>
                    <td style="width:10%; "><tags:label key="label.channel.type" /><%--Loại kênh--%></td>
                    <td style="width:30%; " class="oddColumn">
                        <tags:imSelect name="reportRevenueForm.channelTypeId" id="channelTypeId" headerKey="" 
                                       headerValue="label.option" cssClass="txtInputFull"
                                       list="lstChannelTypeCol" listKey="channelTypeId" listValue="name"
                                       onchange="changeChannelType(this.value)"/>                        
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="label.agent.type" required="false"/><%--Loại đại lý--%></td>
                    <td>
                        <tags:imSelect name="reportRevenueForm.agentType"
                                       id="agentType"
                                       cssClass="txtInputFull"
                                       theme="simple" headerKey="" headerValue="label.option"
                                       list="listAgentType" listKey="code" listValue="name"
                                       />
                    </td>
                    <td><tags:label key="MES.CHL.060" required="false"/><%--Trạng thái--%></td>
                    <td>
                        <tags:imSelect name="reportRevenueForm.statusStaff"
                                       id="statusStaff"
                                       cssClass="txtInputFull"
                                       theme="simple" headerKey="" headerValue="label.option"
                                       list="listStaffStatus" listKey="code" listValue="name"
                                       />
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
                     preAction="reportListAccountAgentAction!reportListChannel.do"/>
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
        if (trim($('reportRevenueForm.shopCode').value) == ""){
    <%--$( 'message' ).innerHTML='Lỗi chưa chọn mã cửa hàng!';--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.RET.033')"/>';
                $('reportRevenueForm.shopCode').focus();
                return false;
            }
            return true;
        }
        
        //xu ly su kien khi thay doi loai mat hang
        changeChannelType = function(channelTypeId) {
            updateData("${contextPath}/reportListAccountAgentAction!getComboboxSource.do?target=agentType&channelTypeId=" + channelTypeId);
        }        
</script>
