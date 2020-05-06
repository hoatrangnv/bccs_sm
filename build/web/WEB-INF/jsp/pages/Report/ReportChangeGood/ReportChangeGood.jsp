<%-- 
    Document   : ReportChangeGood
    Created on : Aug 22, 2009, 9:15:30 AM
    Author     : Vunts
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
            request.setAttribute("contextPath", request.getContextPath());
%>


<!-- phan hien thi tieu chi bao cao -->
<s:form action="reportChangeGoodAction" theme="simple" method="post" id="ReportChangeGoodForm">
<s:token/>

    <tags:imPanel title="MSG.criterion.report.create">
        <table class="inputTbl8Col">
            <tr>
                <td><tags:label key="MSG.transaction.stock.code" /></td>
                <td colspan="3">
                    <tags:imSearch codeVariable="ReportChangeGoodForm.shopCode" nameVariable="ReportChangeGoodForm.shopName"
                                   codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListShop"                                   
                                   getNameMethod="getNameShop"
                                   roleList="reportChangeGoodf9Shop"/>                         

                </td>  
                <td  class="label"><tags:label key="MSG.generates.service" /></td>
                <td class="text">
                    <%--<s:select name="ReportChangeGoodForm.telecomServiceId"
                              id="ReportChangeGoodForm.telecomServiceId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="-- Chọn dịch vụ --"
                              list="%{#request.lstTelecomService != null?#request.lstTelecomService : #{}}"
                              listKey="telecomServiceId" listValue="telServiceName"
                              onchange="updateCombo('ReportChangeGoodForm.telecomServiceId','ReportChangeGoodForm.stockModelId',
                              '%{#request.contextPath}/reportChangeGoodAction!selectTelecomService.do')"
                              />--%>
                    <tags:imSelect name="ReportChangeGoodForm.telecomServiceId"
                                   id="ReportChangeGoodForm.telecomServiceId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.RET.158"
                                   list="lstTelecomService"
                                   listKey="telecomServiceId" listValue="telServiceName"
                                   onchange="updateCombo('ReportChangeGoodForm.telecomServiceId','ReportChangeGoodForm.stockModelId',
                                   '${requestScope.contextPath}/reportChangeGoodAction!selectTelecomService.do')"
                                   />
                </td>
                <td  class="label"><tags:label key="MSG.generates.goods" /></td>
                <td class="text">
                    <%--<s:select name="ReportChangeGoodForm.stockModelId"
                              id="ReportChangeGoodForm.stockModelId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="-- Chọn mặt hàng --"
                              list="%{#request.lstStockModel != null?#request.lstStockModel : #{}}"
                              listKey="stockModelId" listValue="name"
                              />--%>
                    <tags:imSelect name="ReportChangeGoodForm.stockModelId"
                                   id="ReportChangeGoodForm.stockModelId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.RET.159"
                                   list="lstStockModel"
                                   listKey="stockModelId" listValue="name"/>
                </td>
            </tr>
            <tr>
                <td  class="label"><tags:label key="MSG.fromDate" required="true" /></td>
                <td  class="text">
                    <tags:dateChooser property="reportChangeGoodForm.fromDate" id="fromDate"/>
                </td>
                <td  class="label"><tags:label key="MSG.generates.to_date" required="true" /></td>
                <td  class="text">
                    <tags:dateChooser property="reportChangeGoodForm.toDate" id="toDate"/>
                </td>
                <td colspan="4">&nbsp</td>
            </tr>
            <tr>
                <td colspan="8">
                    <fieldset style="width:100%;">
                        <%--<s:radio name="ReportChangeGoodForm.reportDetail"
                                 list="#{'0':'Báo cáo tổng hợp &nbsp;&nbsp;', '1':'Báo cáo chi tiết'}"/>--%>
                        <tags:imRadio name="ReportChangeGoodForm.reportDetail" id="reportDetail"
                                      list="0:MSG.RET.121,1:MSG.RET.122"/>

                    </fieldset>
                </td>
            </tr>
            <tr>
                <td colspan="8" align="center">
                    <tags:submit formId="ReportChangeGoodForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="bodyContent"
                                 value="MSG.report"
                                 preAction="reportChangeGoodAction!exportReportChangeGoodReport.do" validateFunction="checkValidFields()"/>
                </td>
            </tr>
            <tr>
                <td colspan="8" align="center">
                    <tags:displayResult id="displayResultMsgClient" property="displayResultMsgClient" propertyValue="returnMsgValue" type="key"/>

                </td>

            </tr>
            <tr>
                <td colspan="8" align="center">
                    <s:if test="#request.filePath !=null && #request.filePath!=''">
                        <script>
                            window.open('${contextPath}/download.do?${fn:escapeXml(filePath)}', '', 'toolbar=yes,scrollbars=yes');
                            <%--
                            window.open('${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
                                window.open('<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');                            
                            --%>
                        </script>
                        <a href="${contextPath}/download.do?${fn:escapeXml(filePath)}">
                          <tags:displayResult id="reportRevenueMessage" property="reportRevenueMessage" type="key"/>
                        </a>
                        <%--
                            <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a></div>
                        
                        <div><a href='<s:property escapeJavaScript="true"  value="#request.filePath"/>'><tags:label key="MSG.clickhere.to.download" /></a></div>--%>
                    </s:if>
                </td>
            </tr>
        </table>
    </tags:imPanel>
</s:form>




<script language="javascript">

    checkValidFields = function() {        
        var _myWidget1 = document.getElementById("ReportChangeGoodForm.shopCode");        
        if(_myWidget1.value==null || _myWidget1.value.trim()==""){
            _myWidget1.textInputNode.focus();
    <%--$( 'displayResultMsgClient' ).innerHTML = "Chưa nhập mã cửa hàng/đại lý";--%>
                $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.010"/>';
                return false;
            }
            var dateExported= dojo.widget.byId("fromDate");
            if(dateExported.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo từ ngày';--%>
                $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.002"/>';
                $('fromDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ ngày không hợp lệ';--%>
                $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.003"/>';
                $('fromDate').focus();
                return false;
            }

            var dateExported1 = dojo.widget.byId("toDate");
            if(dateExported1.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo đến ngày';--%>
                $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.004"/>';
                $('toDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported1.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo đến ngày không hợp lệ';--%>
                $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.005"/>';
                $('toDate').focus();
                return false;
            }
            if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ ngày phải nhỏ hơn ngày báo cáo đến ngày';--%>
                $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.006"/>';
                $('fromDate').focus();
                return false;
            }
            return true;
        }

        dojo.event.topic.subscribe("ReportChangeGoodForm/autoSelectShop", function(value, key, text, widget){
            if(key!=null && value!=null){
                $('ReportChangeGoodForm.shopName').value=key;
            }

        });

</script>
