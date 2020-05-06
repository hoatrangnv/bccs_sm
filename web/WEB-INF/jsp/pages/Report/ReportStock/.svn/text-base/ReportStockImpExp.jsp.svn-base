<%--
    Document   : ReportStockImpExp.jsp
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%request.setAttribute("contextPath", request.getContextPath());%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
    getShopName=function(){
        $("reportStockImpExpForm.shopName").value="";
        var shopCode=$("reportStockImpExpForm.shopCode").value;
        getInputText("CommonStockAction!getShopName.do?target=reportStockImpExpForm.shopName&shopCode=" + shopCode);
    }
    getStaffName=function(){
        $("reportStockImpExpForm.staffName").value="";
        var staffCode=$("reportStockImpExpForm.staffCode").value;
        var shopCode=$("reportStockImpExpForm.shopCode").value;
        getInputText("CommonStockAction!getStaffName.do?target=reportStockImpExpForm.staffName&staffCode=" + staffCode+"&shopCode="+shopCode);
    }
    setShopValue= function(code,name){
        $('reportStockImpExpForm.shopCode').value=code;
        $('reportStockImpExpForm.shopName').value=name;
        $('reportStockImpExpForm.staffCode').value="";
        $('reportStockImpExpForm.staffName').value="";
        $('reportStockImpExpForm.staffCode').focus();
    }
    setStaffValue= function(code,name){
        $('reportStockImpExpForm.staffCode').value=code;
        $('reportStockImpExpForm.staffName').value=name;
        $('reportStockImpExpForm.telecomServiceId').focus();
    }
    $('reportStockImpExpForm.shopCode').focus();
    try{
        $("reportStockImpExpForm.shopCode").onkeyup = function( e ) {
            var evt = ( window.event ) ? window.event : e;
            var keyID = evt.keyCode;
            // F9
            if( keyID == 120 ) {
                try {
                    var shopCode=$('reportStockImpExpForm.shopCode').value;
                    var shopName=$('reportStockImpExpForm.shopName').value;
                    openDialog('CommonStockAction!popupSearchShop.do?shopCode='+shopCode+"&shopName="+shopName, 700, 750,false)
                }
                catch( e ) {
                    alert(e.message);
                }
            }
        }
    }
    catch(e){
    }
    
    try{
        $("reportStockImpExpForm.staffCode").onkeyup = function( e ) {
            var evt = ( window.event ) ? window.event : e;
            var keyID = evt.keyCode;
            // F9
            if( keyID == 120 ) {
                try {
                    var shopCode=$('reportStockImpExpForm.shopCode').value;
                    var staffCode=$('reportStockImpExpForm.staffCode').value;
                    var staffName=$('reportStockImpExpForm.staffName').value;
                    openDialog('CommonStockAction!popupSearchStaffWidthColl.do?staffCode='+staffCode+"&staffName="+staffName+"&shopCode="+shopCode, 700, 750,false)
                }
                catch( e ) {
                    alert(e.message);
                }
            }
        }
    }
    catch(e){
    }
</script>

<s:form method="POST" id="reportStockImpExpForm" action="ReportStockImpExpAction" theme="simple">
<s:token/>

    <%--vt:ShortCutJSParams  shortcutKey="F9" functionCallback="searchShop()"  styleId="reportStockImpExpForm.shopCode"/--%>
    <tags:imPanel title="MSG.criterion.report.create">
        <table class="inputTbl8Col" border="0" style="width:100%">
            <tr>
                <td class="label"><tags:label key="MSG.store.code" /><font color="red">(F9)</font></td>
                <td colspan="3" class="text">
                    <s:textfield cssClass="txtInputFull" onblur="getShopName()" cssStyle="width:30%;float:left;"
                                 name="reportStockImpExpForm.shopCode" id="reportStockImpExpForm.shopCode" />
                    <s:textfield cssClass="txtInputFull" cssStyle="width:70%;float:right"
                                 name="reportStockImpExpForm.shopName" id="reportStockImpExpForm.shopName"  />
                </td>

                <td class="label"><tags:label key="MSG.staff.code"  /> &nbsp;<font color="red">(F9)</font></td>
                <td colspan="3" class="text">

                    <s:textfield cssClass="txtInputFull" onblur="getStaffName()" cssStyle="width:30%;float:left;"
                                 name="reportStockImpExpForm.staffCode" id="reportStockImpExpForm.staffCode" />
                    <s:textfield cssClass="txtInputFull" cssStyle="width:70%;float:right"
                                 name="reportStockImpExpForm.staffName" id="reportStockImpExpForm.staffName"  />
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.generates.service" /></td>
                <td class="text">
                    <%--<s:select name="reportStockImpExpForm.telecomServiceId"
                              id="reportStockImpExpForm.telecomServiceId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="-- Chọn dịch vụ --"
                              list="%{#request.lstTelecomService != null?#request.lstTelecomService : #{}}"
                              listKey="telecomServiceId" listValue="telServiceName"
                              onchange="updateCombo('reportStockImpExpForm.telecomServiceId','reportStockImpExpForm.stockModelId',
                              '%{#request.contextPath}/ReportStockImpExpAction!selectTelecomService.do')"
                              />--%>
                    <tags:imSelect name="reportStockImpExpForm.telecomServiceId"
                                   id="reportStockImpExpForm.telecomServiceId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.RET.158"
                                   list="lstTelecomService"
                                   listKey="telecomServiceId" listValue="telServiceName"
                                   onchange="updateCombo('reportStockImpExpForm.telecomServiceId','reportStockImpExpForm.stockModelId','${requestScope.contextPath}/ReportStockImpExpAction!selectTelecomService.do')"
                                   />
                </td>
                <td class="label"><tags:label key="MSG.generates.goods" /></td>
                <td class="text">
                    <%-- <s:select name="reportStockImpExpForm.stockModelId"
                               id="reportStockImpExpForm.stockModelId"
                               cssClass="txtInputFull"
                               headerKey="" headerValue="-- Chọn mặt hàng --"
                               list="%{#request.lstStockModel != null?#request.lstStockModel : #{}}"
                               listKey="stockModelId" listValue="name"
                               />--%>
                    <tags:imSelect name="reportStockImpExpForm.stockModelId"
                                   id="reportStockImpExpForm.stockModelId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.RET.159"
                                   list="lstStockModel"
                                   listKey="stockModelId" listValue="name"/>
                </td>
                <td class="label">
                    <tags:label key="MSG.generates.status" />
                </td>
                <td class="text">
                    <%--<s:select name="reportStockImpExpForm.stateId"
                              id="reportStockImpExpForm.stateId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="--Chọn trạng thái hàng --"
                              list="#{'1':'Mới','2':'Cũ'}"/> list="1:MSG.RET.099,2:MSG.RET.100"--%>
                    <tags:imSelect name="reportStockImpExpForm.stateId"
                                   id="reportStockImpExpForm.stateId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.RET.174"
                                   list="1:MSG.RET.108,3:MSG.RET.109"
                                   />
                </td>
                <td class="label">
                    <tags:label key="MSG.report.type" />
                </td>
                <td class="text">
                    <%--<s:select name="reportStockImpExpForm.reportType"
                              id="reportStockImpExpForm.reportType"
                              cssClass="txtInputFull"
                              list="#{'1':'Báo cáo tại CH','2':'Báo cáo chi tiết các giao dịch'}"/>--%>
                     <tags:imSelect name="reportStockImpExpForm.reportType"
                                   id="reportStockImpExpForm.reportType"
                                   cssClass="txtInputFull"                                   
                                   list="1:MSG.RET.101,2:MSG.RET.102"
                                   />
                </td>
            </tr>
            <tr>
                <td class="label">
                    <tags:label key="MSG.fromDate" />
                </td>
                <td class="text">
                    <tags:dateChooser property="reportStockImpExpForm.fromDate" id="reportStockImpExpForm.fromDate" styleClass="txtInputFull"/>
                </td>
                <td class="label">
                    <tags:label key="MSG.generates.to_date" />
                </td>
                <td class="text">
                    <tags:dateChooser property="reportStockImpExpForm.toDate" id="reportStockImpExpForm.toDate" styleClass="txtInputFull"/>
                </td>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="8" width="100%">
                    <fieldset style="width:100%;">
                        <%--<s:radio name="reportStockImpExpForm.reportDetail"
                                 list="#{'0':'Báo cáo tổng hợp &nbsp', '1':'Báo cáo chi tiết'}"/>--%>
                        <tags:imRadio name="reportStockImpExpForm.reportDetail" id="reportDetail"
                          list="0:MSG.RET.121,1:MSG.RET.122"/>
                    </fieldset>
                </td>
            </tr>
            <tr>
                <td colspan="8" width="100%">
                    <fieldset style="width:100%">
                        <%--<s:radio name="reportStockImpExpForm.includeStaff"
                                 list="#{'1':'Bao gồm nhân viên', '0':'Không bao gồm nhân viên'}"/>--%>
                        <tags:imRadio name="reportStockImpExpForm.includeStaff" id="includeStaff"
                          list="1:MSG.RET.146,0:MSG.RET.147"/>
                    </fieldset>
                </td>
            </tr>
            <tr>
                <td colspan="8" align="center" width="100%">
                    <tags:submit id="report" confirm="false" formId="reportStockImpExpForm" showLoadingText="true" validateFunction="validateForm()"
                                 targets="bodyContent" value="MSG.report" preAction="ReportStockImpExpAction!exportStockImpExpReport.do"/>
                </td>
            </tr>
            <tr>
                <td colspan="8" align="center"  width="100%">
                    <tags:displayResult id="resultStockImpExpRpt" property="resultStockImpExpRpt"/>
                </td>
            </tr>
            <tr>
                <td colspan="8" align="center" width="100%">
                    <s:if test="#request.filePath !=null && #request.filePath!=''">
                        <script>
                            window.open('${contextPath}/download.do?${fn:escapeXml(filePath)}', '', 'toolbar=yes,scrollbars=yes');
                            <%--window.open('${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
                                window.open('<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');--%>
                        </script>
                        <div>
                            <a href="${contextPath}/download.do?${fn:escapeXml(filePath)}">
                                <tags:displayResult id="reportStockImportPendingMessage" property="reportStockImportPendingMessage" type="key"/>
                            </a>
                            <%--<a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a>
                            <a href='<s:property escapeJavaScript="true"  value="#request.filePath"/>' ><tags:label key="MSG.clickhere.to.download" /></a>--%>
                        </div>
                    </s:if>
                </td>
            </tr>
        </table>

    </tags:imPanel>

</s:form>

<script>


    validateForm= function(){
        var fromDate= dojo.widget.byId("reportStockImpExpForm.fromDate");
        var toDate= dojo.widget.byId("reportStockImpExpForm.toDate");
        var shopCode = $("reportStockImpExpForm.shopCode");
        if(shopCode.value==null || shopCode.value.trim()==""){
            $('resultStockImpExpRpt').innerHTML="<s:text name="stock.report.impExp.error.noShopCode"/>";
            shopCode.textInputNode.focus();
            return false;
        }
        if(fromDate.domNode.childNodes[1].value ==null || trim(fromDate.domNode.childNodes[1].value)==''){
            $('resultStockImpExpRpt').innerHTML="<s:text name="stock.report.impExp.error.noFromDate"/>";
            fromDate.domNode.childNodes[1].focus();
            return false;
        }
        if(toDate.domNode.childNodes[1].value ==null || trim(toDate.domNode.childNodes[1].value)==''){
            $('resultStockImpExpRpt').innerHTML="<s:text name="stock.report.impExp.error.noToDate"/>";
            toDate.domNode.childNodes[1].focus();
            return false;
        }
        /*
        if(!compareDates(fromDate.domNode.childNodes[1].value,toDate.domNode.childNodes[1].value)){
            $('resultStockImpExpRpt').innerHTML="<s:text name="stock.report.impExp.error.fromDateToDateNotValid"/>";
            fromDate.domNode.childNodes[1].focus();
            return false;
        }
         */

        return true;
    }
    dojo.event.topic.subscribe("reportStockImpExpForm/autoSelectShop", function(value, key, text, widget){
        if(key!=null && value!=null){
            $('reportStockImpExpForm.shopName').value=key;
        }

    });
    dojo.event.topic.subscribe("reportStockImpExpForm/autoSelectStaff", function(value, key, text, widget){
        if(key!=null && value!=null){
            $('reportStockImpExpForm.staffName').value=key;
        }

    });
</script>
