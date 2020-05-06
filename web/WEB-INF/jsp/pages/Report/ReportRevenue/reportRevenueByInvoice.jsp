<%-- 
    Document   : reportRevenueByInvoice
    Created on : Aug 3, 2009, 11:30:12 AM
    Author     : tamdt1
--%>

<%--
    Note:   bao cao doanh thu theo hoa don
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
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
    <s:form action="reportRevenueByInvoiceAction" theme="simple" method="post" id="reportRevenueByInvoiceForm">
        <s:token/>

        <table class="inputTbl8Col">
            <tr>
                <td>
                    <fieldset style="width:100%; background-color:#F5F5F5;">
                        <table class="inputTbl8Col">                            
                            <tr>
                                <td><tags:label key="MSG.transaction.stock.code" /></td>
                                <td colspan="2">
                                    <tags:imSearch codeVariable="reportRevenueByInvoiceForm.shopCode" nameVariable="reportRevenueByInvoiceForm.shopName"
                                                   codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                                   searchClassName="com.viettel.im.database.DAO.ReportRevenueByInvoiceDAO"
                                                   searchMethodName="getListShop"
                                                   elementNeedClearContent="reportRevenueByInvoiceForm.staffCode;reportRevenueByInvoiceForm.staffName"
                                                   roleList="reportRevenueByInvoicef9Shop"/>  
                                </td>    
                                <td class="label"><tags:label key="MSG.fromDate" required="true" /></td>
                                <td class="text">
                                    <tags:dateChooser property="reportRevenueByInvoiceForm.fromDate"/>
                                </td>
                                <td class="label"><tags:label key="MSG.generates.to_date" required="true" /></td>
                                <td class="text">
                                    <tags:dateChooser property="reportRevenueByInvoiceForm.toDate"/>
                                </td>

                            </tr>
                            <tr>
                                <td><tags:label key="MSG.staff.code" /></td>
                                <td colspan="2">
                                    <tags:imSearch codeVariable="reportRevenueByInvoiceForm.staffCode" nameVariable="reportRevenueByInvoiceForm.staffName"
                                                   codeLabel="MSG.RET.047" nameLabel="MSG.RET.048s"
                                                   searchClassName="com.viettel.im.database.DAO.ReportRevenueByInvoiceDAO"
                                                   searchMethodName="getListStaff"
                                                   otherParam="reportRevenueByInvoiceForm.shopCode"
                                                   roleList="reportRevenueByInvoicef9Staff"/>  
                                </td>  
                                <td class="label"><tags:label key="MSG.generates.service" /></td>
                                <td class="text">
                                    <%--<s:select name="reportRevenueByInvoiceForm.telecomServiceId"
                                              id="telecomServiceId"
                                              cssClass="txtInputFull"
                                              headerKey="" headerValue="--Chọn dịch vụ--"
                                              list="%{#request.listTelecomService!=null ? #request.listTelecomService : #{}}"
                                              listKey="telecomServiceId" listValue="telServiceName"/>--%>
                                    <tags:imSelect name="reportRevenueByInvoiceForm.telecomServiceId"
                                                   id="telecomServiceId"
                                                   cssClass="txtInputFull"
                                                   headerKey="" headerValue="MSG.RET.171"
                                                   list="listTelecomService"
                                                   listKey="telecomServiceId" listValue="telServiceName"/>
                                </td>
                                <td class="label"><tags:label key="MSG.method" /></td>
                                <td class="text">
                                    <%--<s:select name="reportRevenueByInvoiceForm.payMoney"
                                              id="payMoney"
                                              cssClass="txtInputFull"
                                              headerKey="" headerValue="--Chọn hình thức--"
                                              list="#{'1':'Thu tiền', '0':'Không thu tiền'}"/>--%>
                                    <tags:imSelect name="reportRevenueByInvoiceForm.payMoney" id="payMoney"
                                                   cssClass="txtInputFull"
                                                   headerKey="" headerValue="MSG.RET.173"
                                                   list="1:MSG.RET.096,0:MSG.RET.097"
                                                   />
                                </td>
                            </tr>
                            <tr>
                                <td><tags:label key="Report Simple"/></td>
                                <td colspan="1">
                                    <div class="divHasBorder" style="margin-top:10px; padding:3px;">
                                        <s:checkbox name="reportRevenueForm.reportSimple"/>
                                    </div>
                                </td>
                            </tr>

                        </table>
                    </fieldset>
                </td>
            </tr>
            <%--tr>
                <td>
                    <fieldset style="width:100%; background-color:#F5F5F5;">
                        <legend>Trạng thái hóa đơn</legend>
                        <table class="inputTbl">
                            <tr>
                                <td>
                                    <s:checkbox name="reportRevenueByInvoiceForm.usedInvoice"/>
                                    <label>Hóa đơn đã được tạo</label>
                                    <s:checkbox name="reportRevenueByInvoiceForm.destroyedInvoice"/>
                                    <label>Hóa đơn đã bị hủy</label>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </td>
            </tr--%>
        </table>


    </s:form>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="reportRevenueByInvoiceForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MSG.report"
                     preAction="reportRevenueByInvoiceAction!reportRevenueByInvoice.do"/>
    </div>

    <!-- phan link download bao cao -->
    <%--
    <div>
        <s:if test="#request.reportRevenuePath != null">
            <script>
                    window.open('${fn:escapeXml(reportRevenuePath)}','','toolbar=yes,scrollbars=yes');
            </script>

            <a href="${fn:escapeXml(reportRevenuePath)}">
                <tags:displayResult id="reportRevenueMessage" property="reportRevenueMessage" type="key"/>
            </a>
        </s:if>
    </div>
    --%>
    <div>
        <s:if test="#request.reportRevenuePath != null">
            <script>
                    window.open('${contextPath}/download.do?${fn:escapeXml(reportRevenuePath)}', '', 'toolbar=yes,scrollbars=yes');
            </script>

            <a href="${contextPath}/download.do?${fn:escapeXml(reportRevenuePath)}">
                <tags:displayResult id="reportRevenueMessage" property="reportRevenueMessage" type="key"/>
            </a>
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
    
        //xu ly su kien sau khi chon 1 ma cua hang
        dojo.event.topic.subscribe("reportRevenueByInvoiceAction/getShopName", function(value, key, text, widget){
            $('shopName').value = "";
            $('staffName').value = "";
            var _myWidget = dojo.widget.byId("staffCode");
            _myWidget.textInputNode.value = "";
        
            if (key != undefined) {
                getInputText("reportRevenueByInvoiceAction!getShopName.do?target=shopName&shopId=" + key);
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

        //cap nhat lai danh sach mat hang, danh sach tap luat
        changeStockType = function(stockTypeId) {
            updateData("${contextPath}/reportRevenueAction!changeStockType.do?target=stockModelId&stockTypeId=" + stockTypeId);
        }
    
</script>
