<%-- 
    Document   : ReportBalanceDeposit
    Created on : Feb 7, 2012, 3:07:01 PM
    Author     : haint
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="reportBalanceDepositAction" theme="simple" method="post" id="ReportDepositForm">
<s:token/>

    <tags:imPanel title="report.balance.deposit">
        <table class="inputTbl4Col">
            <tr>
                <td class ="label"><tags:label key="MSG.generates.unit_code" required="true" /> </td>
                <td colspan="1" class="text">
                    <tags:imSearch codeVariable="ReportDepositForm.shopCode" nameVariable="ReportDepositForm.shopName"
                                   codeLabel="MSG.RET.032" nameLabel="MSG.RET.033"
                                   searchClassName="com.viettel.im.database.DAO.ReportAccountAgentDAO"
                                   searchMethodName="getListShop"
                                   roleList=""/>

                    <%--<tags:imSearch codeVariable="ReportDepositForm.shopCode" nameVariable="ReportDepositForm.shopName"
                                   codeLabel="Mã chi nhánh" nameLabel="Tên chi nhánh"
                                   searchClassName="com.viettel.im.database.DAO.ReportDepositDAO"
                                   searchMethodName="getListBranch"
                                   getNameMethod="getNameBranch"/>--%>
                </td>

                <td  class="label"><tags:label key="MSG.deposit.type" required="false" /></td>
                <td class="text">
                    <%--                        <s:select name="ReportDepositForm.depositTypeId"
                                                      id="ReportDepositForm.depositTypeId"
                                                      cssClass="txtInputFull"
                                                      list="#{'2':'Đặt cọc Roaming', '3':'Đặt cọc hạn mức'}"
                                                      headerKey="" headerValue="--Tất cả--"/>--%>
                    <div class="text">
                        <tags:imSelect name="ReportDepositForm.depositTypeId"
                                       id="ReportDepositForm.depositTypeId"
                                       cssClass="txtInputFull"
                                       theme="simple"
                                       headerKey="" headerValue="MSG.RET.162"
                                       list="listDepositType"  listKey="depositTypeId" listValue="code"/>
                    </div>
                </td>

            </tr>
            <tr>
                <td  class="label"><tags:label key="MSG.fromDate" required="true" /> </td>
                <td  class="text">
                    <tags:dateChooser property="ReportDepositForm.fromDate" id="fromDate"/>
                </td>
                <td  class="label"><tags:label key="MSG.generates.to_date" required="true" /> </td>
                <td  class="text">
                    <tags:dateChooser property="ReportDepositForm.toDate" id="toDate"/>
                </td>                
            </tr>
            <tr>
                <td class ="label"><tags:label key="MSG.report.type" required="true" /> </td>
                <td colspan="1" class ="text">
                    <fieldset style="width:100%;">
                        <%--<s:radio name="ReportDepositForm.reportDetail" id ="reportDetail" list="#{'1':'Tổng hợp', '2':'Chi tiết', '3':'Chi tiết theo thuê bao'}" onchange="changeReportType(this.value);" />--%>
                        <tags:imRadio name="ReportDepositForm.reportDetail" id="reportDetail"
                                      list="1:MSG.RET.115, 2:MSG.RET.116, 3:MSG.SAE.210"/>
                    </fieldset>
                </td>
                <td colspan="2"></td>                
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <tags:submit formId="ReportDepositForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="bodyContent"
                                 value="MSG.report"
                                 preAction="reportBalanceDepositAction!exportReportBalanceDeposit.do" validateFunction="checkValidFields()"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <tags:displayResult id="displayResultMsgClient" property="displayResultMsgClient" propertyValue="returnMsgValue" type="key"/>
                </td>                
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <s:if test="#request.filePath !=null && #request.filePath!=''">
                        <script>
                             window.open('${contextPath}/download.do?${fn:escapeXml(filePath)}', '', 'toolbar=yes,scrollbars=yes');
                            <%--window.open('${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');--%>
                            <%-- window.open('<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');--%>
                        </script>
                        <div>
                            <a href="${contextPath}/download.do?${fn:escapeXml(filePath)}">
                                <tags:displayResult id="reportRevenueMessage" property="reportRevenueMessage" type="key"/>
                            </a>
                            <%--<a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a>--%>
                            <%--<a href='<s:property escapeJavaScript="true"  value="#request.filePath"/>' ><tags:label key="MSG.clickhere.to.download" required="false" /></a>--%>
                        </div>
                    </s:if>
                </td>
            </tr>
        </table>
    </tags:imPanel>
</s:form>


<script language="javascript">
    
    $('ReportDepositForm.shopCode').focus();

    checkValidFields = function() {

        var i = 1;
        var reportType = 1;

        var i = 1;
        for(i = 1; i < 4; i=i+1) {
            var radioId = "reportDetail" + i;
            if($(radioId).checked == true) {
                reportType = $(radioId).value;
                break;
            }

        }


        if (reportType != 4){
            var shopCode = document.getElementById("ReportDepositForm.shopCode");
            if(shopCode.value==null || shopCode.value.trim()==""){
    <%--$( 'displayResultMsgClient' ).innerHTML = "Chưa nhập mã đơn vị";--%>
                    $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.035"/>';
                    shopCode.focus();
                    return false;
                }

            }else{
                var isdn = document.getElementById("isdn");
                if(isdn.value==null || isdn.value.trim()==""){
    <%--$( 'displayResultMsgClient' ).innerHTML = "Chưa nhập số Isdn";--%>
                    $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.036"/>';
                    isdn.focus();
                    return false;
                }
                 if(!isInteger(trim($('isdn').value))) {
                    //$('displayResultMsgClient').innerHTML = "!!!Lỗi. Số thuê bao phải là số không âm.";
                    $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.DET.069"/>';
                    isdn.focus();
                    return false;
                }

            }
            var dateExported= dojo.widget.byId("fromDate");
            if(dateExported.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo từ ngày';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.002"/>';
                $('fromDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ ngày không hợp lệ';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.003"/>';
                $('fromDate').focus();
                return false;
            }

            var dateExported1 = dojo.widget.byId("toDate");
            if(dateExported1.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo đến ngày';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.004"/>';
                $('toDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported1.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo đến ngày không hợp lệ';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.005"/>';
                $('toDate').focus();
                return false;
            }
            if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ phải nhỏ hơn ngày báo cáo đến ngày';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.006"/>';
                $('fromDate').focus();
                return false;
            }
            return true;
        }
        var i = 1;
        for(i = 1; i < 4; i=i+1) {
            var radioId = "reportDetail" + i;
            if($(radioId).checked == true) {
                changeReportType(i);
                break;
            }

        }
        

</script>

