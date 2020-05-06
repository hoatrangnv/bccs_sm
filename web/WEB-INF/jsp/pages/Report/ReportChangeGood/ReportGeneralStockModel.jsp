<%-- 
    Document   : ReportGeneralStockModel
    Created on : Sep 21, 2009, 10:59:35 AM
    Author     : Vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.criterion.report.create">

    <!-- phan hien thi thong tin message -->

    <%--<div>
        <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
    </div>--%>

    <!-- phan hien thi tieu chi bao cao -->
    <s:form action="reportGeneralStockModelAction" theme="simple" method="post" id="ReportChangeGoodForm">
<s:token/>

        <table class="inputTbl6Col">
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td><tags:label key="MSG.generates.unit_code" /></td>
                <td colspan="3">
                    <tags:imSearch codeVariable="ReportChangeGoodForm.shopCode" nameVariable="ReportChangeGoodForm.shopName"
                                   codeLabel="MSG.RET.032" nameLabel="MSG.RET.033"
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListShop"                                   
                                   getNameMethod="getNameShop"
                                   roleList="reportGeneralStockModelf9Shop"/>                         

                </td>                
                <td class="label"><tags:label key="MSG.GOD.027" /></td>
                <td class="text">
                    <%--<s:select name="ReportChangeGoodForm.stockTypeId"
                              id="ReportChangeGoodForm.stockTypeId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="-- Chọn loại mặt hàng --"
                              list="%{#request.lstStockType != null?#request.lstStockType : #{}}"
                              listKey="stockTypeId" listValue="name"
                              onchange="updateCombo('ReportChangeGoodForm.stockTypeId','ReportChangeGoodForm.stockModelId',
                              '%{#request.contextPath}/reportGeneralStockModelAction!selectStockType.do')"
                              />--%>
                    <tags:imSelect name="ReportChangeGoodForm.stockTypeId"
                                   id="ReportChangeGoodForm.stockTypeId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.RET.160"
                                   list="lstStockType"
                                   listKey="stockTypeId" listValue="name"
                                   onchange="updateCombo('ReportChangeGoodForm.stockTypeId','ReportChangeGoodForm.stockModelId','${requestScope.contextPath}/reportGeneralStockModelAction!selectStockType.do')"
                                   />
                </td>
                <td class="label"><tags:label key="MSG.generates.goods" /></td>
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
                <td colspan="8">
                    <fieldset style="width:100%;">
                        <%--<s:radio name="ReportChangeGoodForm.stateId" id="stateId"
                                 list="#{'1':'Báo cáo hàng mới &nbsp;&nbsp;', '3':'Báo cáo hàng hỏng'}"/>--%>
                        <tags:imRadio name="ReportChangeGoodForm.stateId" id="stateId"
                                      list="1:MSG.RET.123,3:MSG.RET.124"/>


                    </fieldset>
                </td>
            </tr>
            <tr>
                <td colspan="8">
                    <fieldset style="width:100%;">
                        <%--<s:radio name="ReportChangeGoodForm.reportType" id="reportType"
                                 list="#{'1':'Báo cáo cấp dưới &nbsp;&nbsp;', '2':'Báo cáo tất cả cấp dưới'}"/>--%>
                        <tags:imRadio name="ReportChangeGoodForm.reportType" id="reportType"
                                      list="1:MSG.RET.125,3:MSG.RET.126"/>
                    </fieldset>
                </td>
            </tr>            
        </table>
                    general
        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <s:checkbox name="ReportChangeGoodForm.channelShop"/>
            <label>${fn:escapeXml(imDef:imGetText('MSG.RET.185'))}</label>            
            
            <s:checkbox name="ReportChangeGoodForm.channelCTV"/>1
            <label>${fn:escapeXml(imDef:imGetText('MSG.RET.186'))}</label>

            <s:checkbox name="ReportChangeGoodForm.channelAgent"/>2
            <label>${fn:escapeXml(imDef:imGetText('MSG.RET.187'))}</label>

        </div>

    </s:form>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="ReportChangeGoodForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MSG.report"
                     preAction="reportGeneralStockModelAction!exportReport.do" validateFunction="checkValidFields()"/>
    </div>

    <!-- phan link download bao cao -->

    <div>
        <tr>
            <td colspan="8" align="center">
                <tags:displayResult id="displayResultMsgClient" property="resultStockImpExpRpt"/>
            </td>
        </tr>
        <tr>
            <td colspan="8" align="center">
                <s:if test="#request.filePath !=null && #request.filePath!=''">
                    <script>
                         window.open('${contextPath}/download.do?${fn:escapeXml(filePath)}', '', 'toolbar=yes,scrollbars=yes');
                        <%--window.open('${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
                            window.open('<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');--%>
                    </script>
                    <a href="${contextPath}/download.do?${fn:escapeXml(filePath)}">
                          <tags:displayResult id="reportRevenueMessage" property="reportRevenueMessage" type="key"/>
                    </a>
                    <%--<div><a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a></div>
                    <div><a href='<s:property escapeJavaScript="true"  value="#request.filePath"/>' ><tags:label key="MSG.clickhere.to.download" /></a></div>--%>
                </s:if>
            </td>
        </tr>
    </div>

</tags:imPanel>
<script language="javascript">
    checkValidFields = function() {
        var stockTypeId = document.getElementById("ReportChangeGoodForm.stockTypeId");
        var stockModelId = document.getElementById("ReportChangeGoodForm.stockModelId");        
        if(stockTypeId.value==null || stockTypeId.value ==""){
    <%--$('displayResultMsgClient').innerHTML="Chưa chọn loại hàng hóa"--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.RET.029')"/>';
                stockTypeId.focus();
                return false;
            }
            if(stockModelId.value==null || stockModelId.value ==""){
    <%--$('displayResultMsgClient').innerHTML="Chưa chọn mặt hàng"--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.RET.030')"/>';
                stockModelId.focus();
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

