<%-- 
    Document   : SearchTransRecover
    Created on : Sep 26, 2009, 10:56:10 AM
    Author     : User
--%>

<%@tag description="Hien thi cac thong tin ve CommCountersForm" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@ tag import="java.util.List" %>
<%@ tag import="java.util.ArrayList" %>

<%@attribute name="type"%>
<%@attribute name="form" required="true"%>
<%@attribute name="target" required="true"%>
<%@attribute name="action" required="true"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("lstSerial", request.getSession().getAttribute("lstSerial"));
%>

<fieldset style="width:100%;">
    <legend class="transparent">Tìm kiếm giao dịch</legend>
    <!--Loai action can tim kiem la phieu hay lenh-->
    <s:hidden name="%{#attr.form+'.actionType'}" />
    <s:hidden name="%{#attr.form+'.transType'}" />
    <s:hidden name="%{#attr.form+'.fromOwnerType'}" id="fromOwnerType"/>
    <s:hidden name="%{#attr.form+'.toOwnerType'}" id="toOwnerType"/>
    <table class="inputTbl" style="width:100%">
        <tr>
            <td style="width:15%">Mã giao dịch</td>
            <td style="width:35%">
                <s:textfield name="%{#attr.form+'.actionCode'}" id="actionCode" cssClass="txtInput"/>
                <script>$('actionCode').focus();</script>
            </td>
            <td style="width:15%">Trạng thái</td>
            <td style="width:35%">
                <s:select name="%{#attr.form+'.transStatus'}"
                          id="transStatus"
                          cssClass="txtInput"
                          headerKey="" headerValue="--Chọn trạng thái--"
                          list="#{'1':'Chưa lập phiếu','2':'Đã lập phiếu','3':'Đã nhập kho'}"/>
            </td>
            <!--/s:else-->
        </tr>
        <tr>
            <td>Kho xuất</td>
            <td>
                 <s:select name="%{#attr.form+'.fromOwnerId'}"
                                  id="fromOwnerId"
                                  cssClass="txtInput"
                                  headerKey="" headerValue="--Chọn kho xuất hàng--"
                                  list="%{#request.lstShopExport != null?#request.lstShopExport : #{}}"
                                  listKey="shopId" listValue="name"/>
            </td>
            <td>Kho nhận</td>
            <td>
                <s:textfield name="%{#attr.form+'.toOwnerName'}"  id ="toOwnerName" readonly="true" cssClass="txtInput"/>
                <s:hidden name="%{#attr.form+'.toOwnerId'}" id="toOwnerId"/>
            </td>
        </tr>
        <tr>
            <td>
                Từ ngày
            </td>
            <td>
                <%request.setAttribute("fromDate", form + ".fromDate");%>
                <tags:dateChooser property="${fromDate}" id="fromDate" styleClass="txtInput"/>
            </td>
            <td>
                Đến ngày
            </td>
            <td>
                <%request.setAttribute("toDate", form + ".toDate");%>
                <tags:dateChooser property="${toDate}" id="toDate" styleClass="txtInput"/>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="center">
                <%--sx:submit parseContent="true" executeScripts="true" id="searchButton" formId="%{#attr.form}"
                afterNotifyTopics="StockTrans/conplete"
                           loadingText="Đang xử lý..." showLoadingText="false" targets="%{#attr.target}" value="Tìm kiếm"
                           beforeNotifyTopics="StockTrans/search"  errorNotifyTopics="errorAction"/--%>
                <tags:submit formId="${form}" confirm="false" showLoadingText="true" targets="${target}" value="Tìm kiếm" preAction="${action}"/>
            </td>
        </tr>
    </table>


</fieldset>
<%--
<script>
dojo.event.topic.subscribe("StockTrans/conplete", function(event, widget){
resetProgress();
});
dojo.event.topic.subscribe("StockTrans/search", function(event, widget){
initProgress();
widget.href = '<s:property value="#attr.action"/>';
//event: set event.cancel = true, to cancel request
});
</script>
--%>