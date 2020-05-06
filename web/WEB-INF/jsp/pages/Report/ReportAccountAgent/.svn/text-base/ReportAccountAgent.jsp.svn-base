<%-- 
    Document   : ReportAccountAgent
    Created on : Mar 26, 2010, 9:27:31 AM
    Author     : Vunt
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
            
            
            request.setAttribute("listRequestType", request.getSession().getAttribute("LIST_ACCOUNT_BOOK_REQUEST_TYPE_STATUS"));
            
%>


<tags:imPanel title="MSG.criterion.report.create">

    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi tieu chi bao cao -->
    <s:form action="reportAccountAgentAction" theme="simple" method="post" id="reportRevenueForm">
<s:token/>

        <%--<s:hidden name="reportRevenueForm.reportType" id ="reportCheckType" disabled="true"></s:hidden>--%>
        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td class="label" style="width:15%; "><tags:label key="MSG.shop.code" required="true" /></td>
                    <td style="width:35%; " class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.shopCode" nameVariable="reportRevenueForm.shopName"
                                       codeLabel="MSG.shop.code" nameLabel="MSG.shop.name"
                                       searchClassName="com.viettel.im.database.DAO.ReportAccountAgentDAO"
                                       searchMethodName="getListShop"
                                       elementNeedClearContent="reportRevenueForm.staffManageCode;reportRevenueForm.staffManageName;reportRevenueForm.staffCode;reportRevenueForm.staffName"
                                       roleList="reportAccountAgentf9Shop"/>
                    </td>
                    <td class="label"><tags:label key="MSG.account.type" required="true" /></td>
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
                    <td class="label"><tags:label key="MSG.staff.mn" /></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.staffManageCode" nameVariable="reportRevenueForm.staffManageName"
                                       codeLabel="MSG.staff.manager.code" nameLabel="MSG.staff.manager.name"
                                       searchClassName="com.viettel.im.database.DAO.ReportAccountAgentDAO"
                                       searchMethodName="getListStaffManagement"
                                       otherParam="reportRevenueForm.shopCode;channelTypeId"
                                       getNameMethod="getListStaffManagement"
                                       roleList="reportAccountAgentf9StaffManagement"/>
                    </td>
                    <td class="label"><tags:label key="MSG.account.code" required="true" /></td>
                    <td >
                        <tags:imSearch codeVariable="reportRevenueForm.staffCode" nameVariable="reportRevenueForm.staffName"
                                       codeLabel="MSG.account.code" nameLabel="MSG.account.name"
                                       searchClassName="com.viettel.im.database.DAO.ReportAccountAgentDAO"
                                       searchMethodName="getListStaffOrAgent"
                                       otherParam="reportRevenueForm.shopCode;channelTypeId;reportRevenueForm.staffManageCode"
                                       elementNeedClearContent="reportRevenueForm.ownerCode;reportRevenueForm.ownerName"
                                       roleList="reportAccountAgentf9AccountCode"/>
                    </td>

                </tr>
                <tr>
                    <td class="label" ><tags:label key="MSG.trade.type" required="true" /></td>
                    <td class="oddColumn">
                        <%-- <s:select name="reportRevenueForm.requestTypeId"
                                   id="requestTypeId"
                                   cssClass="txtInputFull"
                                   theme="simple"
                                   headerKey="" headerValue="--Loại giao dịch tài khoản--"
                                   list="#{'1':'Nạp tiền/Rút tiền','2':'Đặt cọc/Thu hồi','3':'Hoàn tiền đặt cọc đấu nối','4':'Phí đấu nối/làm DV qua SĐN/Web',
                                           '5':'Tiền hoa hồng đấu nối','6':'Tiền thưởng kích hoạt','7':'Thanh toán tín dụng','8':'Hoàn tiền khi NVQL lập hóa đơn',
                                           '9':'Hủy phiếu thu chi','12':'Sửa sai hình thức hòa mạng bên CM','13':'Cho vay tín dụng','17':'Tiền thưởng kích hoạt tự động'}"
                                   onchange="changeRequestType()"
                                   />--%>
                        
<!--                        list="1:MSG.RET.001,2:MSG.RET.002,3:MSG.RET.003,4:MSG.RET.004,5:MSG.RET.005,6:MSG.RET.006,7:MSG.RET.007,8:MSG.RET.008,9:MSG.RET.009,12:MSG.RET.010,13:MSG.RET.011,17:MSG.RET.012"-->
                        
                        <tags:imSelect name="reportRevenueForm.requestTypeId" id="requestTypeId"
                                       cssClass="txtInputFull" disabled="false"                                       
                                       headerKey="" headerValue="MSG.RET.148"
                                       list="listRequestType" listKey="code" listValue="name"
                                       onchange="changeRequestType()"/>
                    </td>

                    <td class="label"><tags:label key="MSG.trade.target" required="true" /></td>
                    <td >
                        <%--<s:select name="reportRevenueForm.addGetMoney"
                                  id="addGetMoney"
                                  cssClass="txtInputFull"
                                  disabled="true"
                                  theme="simple"                                  
                                  list="#{'1':'Nạp tiền','-1':'Rút tiền'}"
                                  />--%>
                        <tags:imSelectNoHidden name="reportRevenueForm.addGetMoney" id="addGetMoney"
                                       cssClass="txtInputFull" disabled="true"
                                       list="1:MSG.RET.013,-1:MSG.RET.014"
                                       />

                </tr>
                <tr>
                    <td class="label" colspan="2"><font color="red"><tags:label key="MSG.use.in.advance.report" /></font>
                    </td>
                </tr>
                <tr>
                    <td class="label" style="width:10%; "><tags:label key="MSG.fromDate" required="true" /></td>
                    <td class="label" style="width:20%; " class="oddColumn">
                        <tags:dateChooser property="reportRevenueForm.fromDate" id ="fromDate"/>
                    </td>
                    <td class="label" style="width:10%; "><tags:label key="MSG.generates.to_date" required="true" /></td>
                    <td class="oddColumn">
                        <tags:dateChooser property="reportRevenueForm.toDate" id ="toDate"/>
                    </td>

                </tr>
            </table>
        </div>

        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <%--<s:radio name="reportRevenueForm.reportType" id ="reportType"
                     list="#{'1':'Báo cáo chi tiết theo TK', '2':'Báo cáo thu chi tổng hợp theo CH', '3':'Báo cáo nâng cao','4':'Báo cáo tổng hợp công nợ'}"/>--%>
            <tags:imRadio name="reportRevenueForm.reportType" id="reportType"
                          list="1:MSG.RET.110,2:MSG.RET.111,3:MSG.RET.112,4:MSG.RET.113"/>

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
                     preAction="reportAccountAgentAction!reportAccountAgent.do"/>
    </div>

    <!-- phan link download bao cao -->
    
    <div>
        <s:if test="#request.reportAccountPath != null">
            <script>
                <%--window.open('${contextPath}/${fn:escapeXml(reportAccountPath)}','','toolbar=yes,scrollbars=yes');--%>
                <%--window.open('${fn:escapeXml(reportAccountPath)}','','toolbar=yes,scrollbars=yes');--%>
                window.open('${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}', '', 'toolbar=yes,scrollbars=yes');        
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
    var requestTypeId = document.getElementById("requestTypeId");
    if (requestTypeId.value != null && requestTypeId.value =='1'){
        $('addGetMoney').disabled = false;

    }
    else{
        $('addGetMoney').disabled = true;
    }
    clickChange = function(value) {
        $('reportCheckType').value = value;
    }
    changeRequestType = function(){
        var requestTypeId = document.getElementById("requestTypeId");
        if (requestTypeId.value != null && requestTypeId.value =='1'){
            $('addGetMoney').disabled = false;
            $('addGetMoney').value ='1'

        }
        else{
            $('addGetMoney').disabled = true;
            $('addGetMoney').value ='1'
        }
    }
    checkValidFields = function() {
        var shopCode = document.getElementById("reportRevenueForm.shopCode");
        if (shopCode.value =="" && shopCode.value ==""){
    <%--$( 'message' ).innerHTML='Bạn phải chọn mã cửa hàng';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.009"/>';
                $('reportRevenueForm.shopCode').focus();
                return false;
            }
            var staffCode = document.getElementById("reportRevenueForm.staffCode");
            var requestTypeId = document.getElementById("requestTypeId");
            var channelTypeId = document.getElementById("channelTypeId");
            if (channelTypeId.value =="" && channelTypeId.value ==""){
    <%--$( 'message' ).innerHTML='Bạn phải chọn loại tài khoản';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.008"/>';
                $('channelTypeId').focus();
                return false;
            }
            var i = 0;
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
            else{
                if(i == 3 || i == 4){
                    if (requestTypeId.value =="" && requestTypeId.value ==""){
    <%--$( 'message' ).innerHTML='Chưa chọn loại giao dịch';--%>
                        $( 'message' ).innerHTML='<s:text name="ERR.RET.001"/>';
                        $('requestTypeId').focus();
                        return false;
                    }
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
