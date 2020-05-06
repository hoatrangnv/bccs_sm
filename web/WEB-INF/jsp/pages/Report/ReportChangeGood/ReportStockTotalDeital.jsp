<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ReportStockTotalDeital
    Created on : Oct 22, 2010, 8:55:17 AM
    Author     : User
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

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listChannelType", request.getSession().getAttribute("listChannelType"));
%>

<tags:imPanel title="MSG.criterion.report.create">

    <!-- phan hien thi thong tin message -->

    <%--<div>
        <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
    </div>--%>

    <!-- phan hien thi tieu chi bao cao -->
    <s:form action="reportGeneralStockModelAction" theme="simple" method="post" id="ReportChangeGoodForm">
<s:token/>

        <div class="divHasBorder" style="margin-top:10px; padding:3px;">


            <table class="inputTbl8Col">
                <tr>
                    <td style="width: 10%;"></td>
                    <td style="width: 15%;"></td>
                    <td style="width: 10%;"></td>
                    <td style="width: 15%;"></td>
                    <td style="width: 10%;"></td>
                    <td style="width: 15%;"></td>
                    <td style="width: 10%;"></td>
                    <td style="width: 15%;"></td>                    
                </tr>
                <tr>
                    <td><tags:label key="MSG.generates.unit_code" /></td>
                    <td colspan="7">
                        <tags:imSearch codeVariable="ReportChangeGoodForm.shopCode" nameVariable="ReportChangeGoodForm.shopName"
                                       codeLabel="MSG.RET.032" nameLabel="MSG.RET.033"
                                       searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getNameShop"
                                       roleList="reportStockTotalDetailf9Shop"/>

                    </td>
                </tr>
            </table>
        </div>
        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <td colspan="8">
                <fieldset style="width:100%;">
                    <%--<s:radio name="ReportChangeGoodForm.stateId" id="stateId"
                             list="#{'1':'Báo cáo hàng mới &nbsp;&nbsp;', '3':'Báo cáo hàng hỏng'}"/>--%>
                    <tags:imRadio name="ReportChangeGoodForm.stateId" id="stateId"
                                  list="1:MSG.RET.123,3:MSG.RET.124"/>


                </fieldset>
            </td>
        </div>
        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <table> <tr><td>
                        <s:checkbox name="reportChangeGoodForm.channelShop"  />
                        <label>${fn:escapeXml(imDef:imGetText('MSG.RET.185'))}</label>
                        <s:checkbox name="reportChangeGoodForm.channelCTV" id="channelCTV"  onchange="changeChannelCTV();"/>
                        <label>${fn:escapeXml(imDef:imGetText('MSG.RET.186'))}</label>
                    </td><td>
                        <div  style="width: 300px" >
                            <s:if test="reportChangeGoodForm.subChannelTypeId == null || reportChangeGoodForm.subChannelTypeId * 1 < 1">
                                <tags:imSelectNoHidden  name="reportChangeGoodForm.subChannelTypeId"
                                                        id="reportChangeGoodForm.subChannelTypeId"
                                                        cssClass="txtInputFull"
                                                        theme="simple" disabled="true"
                                                        headerKey="0" headerValue="label.option"
                                                        list="listChannelType" listKey="channelTypeId" listValue="name" />
                            </s:if><s:else>
                                <tags:imSelectNoHidden  name="reportChangeGoodForm.subChannelTypeId"
                                                        id="reportChangeGoodForm.subChannelTypeId"
                                                        cssClass="txtInputFull"
                                                        theme="simple"
                                                        headerKey="0" headerValue="label.option"
                                                        list="listChannelType" listKey="channelTypeId" listValue="name" />

                            </s:else>

                        </div>
                    </td>
                </tr>
            </table>
            <%--s:checkbox name="reportChangeGoodForm.channelAgent"/>
            <label>${fn:escapeXml(imDef:imGetText('MSG.RET.187'))}</label--%>
        </div>

    </s:form>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="ReportChangeGoodForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MSG.report"
                     preAction="reportGeneralStockModelAction!exportReportStockTotalDetail.do" validateFunction="checkValidFields()"/>
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
        var shopCode = document.getElementById("ReportChangeGoodForm.shopCode");        
        if(shopCode.value==null || shopCode.value ==""){    
            $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.RET.061')"/>';
            shopCode.focus();
            return false;
        }    
        return true;
    }

    changeChannelCTV = function(){
        $('reportChangeGoodForm.subChannelTypeId').disabled = !$('channelCTV').checked;
    }
</script>

