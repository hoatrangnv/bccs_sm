<%-- 
    Document   : ReportImportStockPending
    Created on : Jan 24, 2012, 9:53:22 AM
    Author     : TrongLV
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

<tags:imPanel title="MSG.RET.034">

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
                    <%--<td style="width:10%; ">Mã cửa hàng<font color="red">*</font></td>--%>
                    <td style="width:10%; "><tags:label key="MSG.shop.code" required="true"/></td>
                    <td style="width:30%; " class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.shopCode" nameVariable="reportRevenueForm.shopName"
                                       codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"
                                       elementNeedClearContent="reportRevenueForm.staffCode;reportRevenueForm.staffName;reportRevenueForm.ownerCode;reportRevenueForm.ownerName"
                                       roleList="reportRevenueShop"/>
                    </td>

                    <td><tags:label key="MSG.staff.code" /></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.staffCode" nameVariable="reportRevenueForm.staffName"
                                       codeLabel="MSG.RET.047" nameLabel="MSG.RET.048"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStaff"
                                       getNameMethod="getStaffName"
                                       otherParam="reportRevenueForm.shopCode"
                                       elementNeedClearContent="reportRevenueForm.ownerCode;reportRevenueForm.ownerName"
                                       roleList="reportRevenueStaff"/>
                    </td>
                </tr>

                <tr>
                    <%--<td style="width:10%; ">Từ ngày<font color="red">*</font></td>--%>
                    <td style="width:10%; "><tags:label key="MSG.fromDate" required="true" /></td>
                    <td style="width:20%; " class="oddColumn">
                        <tags:dateChooser property="reportRevenueForm.fromDate"/>
                    </td>
                    <%--<td style="width:10%; ">Đến ngày<font color="red">*</font></td>--%>
                    <td style="width:10%; "><tags:label key="MSG.generates.to_date" required="true" /></td>
                    <td class="oddColumn">
                        <tags:dateChooser property="reportRevenueForm.toDate"/>
                    </td>
                </tr>
                <tr>


                    <%--  <td><tags:label key="MSG.GOD.027" /></td>
                      <td class="oddColumn">
                          <tags:imSelect name="reportRevenueForm.stockTypeId"
                                         id="stockTypeId"
                                         cssClass="txtInputFull"
                                         headerKey="" headerValue="MSG.RET.160"
                                         list="listStockType"
                                         listKey="stockTypeId" listValue="name"/>
                      </td>--%>

                    <%--<td>Mặt hàng</td>--%>
                    <td ><tags:label key="MSG.RET.077"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.goodsCode" nameVariable="reportRevenueForm.goodsName"
                                       codeLabel="MSG.RET.088" nameLabel="MSG.RET.089"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getGoodsName"
                                       getNameMethod="getGoodsName" />
                    </td>
                    <%-- <td>Đối tượng</td>
                     <td class="oddColumn">
                         <s:select name="reportRevenueForm.objectType"
                                   id="objectType"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="--Chọn đối tượng--"
                                   list="#{'2':'Cộng tác viên', '1':'Điểm bán'}"/>
                         <tags:imSelect name="reportRevenueForm.objectType" id="objectType"
                                        cssClass="txtInputFull"
                                        headerKey="" headerValue="MSG.RET.172"
                                        list="2:MSG.RET.016,1:MSG.RET.017"
                                        />
                     </td>--%>



                    <%--  <td>Dịch vụ</td>
                      <td>
                                                  <s:select name="reportRevenueForm.telecomServiceId"
                                                            id="telecomServiceId"
                                                            cssClass="txtInputFull"
                                                            headerKey="" headerValue="--Chọn dịch vụ--"
                                                            list="%{#request.listTelecomService!=null ? #request.listTelecomService : #{}}"
                                                            listKey="telecomServiceId" listValue="telServiceName"/>
                          <tags:imSelect name="reportRevenueForm.telecomServiceId"
                                         id="telecomServiceId"
                                         cssClass="txtInputFull"
                                         headerKey="" headerValue="MSG.RET.171"
                                         list="listTelecomService"
                                         listKey="telecomServiceId" listValue="telServiceName"/>
                      </td>--%>

                </tr>


                <%--  <tr>
                      <td >Loaị báo cáo:</td>
                      <td class="oddColumn">
                                                  <s:select name="reportRevenueForm.telecomServiceId"
                                                            id="telecomServiceId"
                                                            cssClass="txtInputFull"
                                                            headerKey="" headerValue="--Chọn dịch vụ--"
                                                            list="%{#request.listTelecomService!=null ? #request.listTelecomService : #{}}"
                                                            listKey="telecomServiceId" listValue="telServiceName"/>
                          <tags:imSelect name="reportRevenueForm.reportType"
                                         id="telecomServiceId"
                                         cssClass="txtInputFull"
                                         headerKey="" headerValue="MSG.report.ChooseReporType"
                                         list="1:MSG.report.AllAgent, 2:MSG.report.AllAgentOfShop"
                                         />
                      </td>
                  </tr>
                  </tr>--%>
            </table>
        </div>
        <%--Start Dongdv3--%>
        <div class="divHasBorder" align="center" style="margin-top:10px; padding:3px;">
            <%--<s:radio name="reportRevenueForm.groupBySaleTransType"
                     list="#{'1':'Phân nhóm loại giao dịch', '2':'Không phân nhóm loại giao dịch'}"/>--%>
            <table>
                <tr>
                   <td colspan="6"><s:checkbox name="reportRevenueForm.exported"/>
                    <label>${fn:escapeXml(imDef:imGetText('MSG.FAC.Pending'))}</label>
                    <s:checkbox name="reportRevenueForm.notConfirm"/>
                    <label>${fn:escapeXml(imDef:imGetText('L.200040'))}</label>
                    </td>
                </tr>
            </table>
            
            
        </div>
        <%--End Dongdv3--%>
        <div class="divHasBorder" align="center" style="margin-top:10px; padding:3px;">
            <%--<s:radio name="reportRevenueForm.groupBySaleTransType"
                     list="#{'1':'Phân nhóm loại giao dịch', '2':'Không phân nhóm loại giao dịch'}"/>--%>
            <tags:imRadio name="reportRevenueForm.reportType"
                          id="reportRevenueForm.reportType"
                          list="2:MSG.report.detail,1:MSG.report.generate"/>
        </div>

        <div class="divHasBorder" align="center" style="margin-top:10px; padding:3px;">
            <%--<s:radio name="reportRevenueForm.groupBySaleTransType"
                     list="#{'1':'Phân nhóm loại giao dịch', '2':'Không phân nhóm loại giao dịch'}"/>--%>
            <tags:imRadio name="reportRevenueForm.groupType"
                          id="reportRevenueForm.groupType"
                          list="3:MSG.report.all.subordinate,2:MSG.view.all.subordinate,1:MSG.view.current.level"/>
        </div>

    </s:form>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="reportRevenueForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MSG.RET.040"
                     preAction="reportImportStockPending!reportStockImportPending.do"/>
    </div>

    <!-- phan link download bao cao -->
    <div>
        <s:if test="#request.reportStockImportPendingPath != null">
            <script>
                window.open('${contextPath}/download.do?${fn:escapeXml(reportStockImportPendingPath)}', '', 'toolbar=yes,scrollbars=yes');
                <%--window.open('${contextPath}/${fn:escapeXml(reportRevenuePath)}','','toolbar=yes,scrollbars=yes');
                    window.open('${fn:escapeXml(reportStockImportPendingPath)}','','toolbar=yes,scrollbars=yes');--%>
            </script>
            <a href="${contextPath}/download.do?${fn:escapeXml(reportStockImportPendingPath)}">
                <tags:displayResult id="reportStockImportPendingMessage" property="reportStockImportPendingMessage" type="key"/>
            </a>
        </s:if>
    </div>

</tags:imPanel>

<script language="javascript">
    clickChange = function(value) {
        $('reportCheckType').value = value;
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        var fromPrice = $('fromPrice').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        var toPrice = $('toPrice').value.replace(/\,/g,""); //loai bo dau , trong chuoi

        if(fromPrice != '' && !isInteger(trim(fromPrice))) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường Từ giá phải là số không âm";--%>
                $('message').innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.RET.043')"/>';
                $('fromPrice').select();
                $('fromPrice').focus();
                return false;
            }

            if(toPrice != '' && !isInteger(trim(toPrice))) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường Đến giá phải là số không âm";--%>
                $('message').innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.RET.044')"/>';
                $('toPrice').select();
                $('toPrice').focus();
                return false;
            }

            var fromIsdn = $('fromIsdn').value;
            var toIsdn = $('toIsdn').value;

            if(fromIsdn != '' && !isInteger(trim(fromIsdn))) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường Từ số isdn phải là số không âm";--%>
                $('message').innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.RET.045')"/>';
                $('fromIsdn').select();
                $('fromIsdn').focus();
                return false;
            }

            if(toIsdn != '' && !isInteger(trim(toIsdn))) {
    <%--$('message').innerHTML = "!!!Lỗi. Trường Đến số isdn phải là số không âm";--%>
                $('message').innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.RET.046')"/>';
                $('toIsdn').select();
                $('toIsdn').focus();
                return false;
            }

            //trim cac truong can thiet
            $('fromPrice').value = trim($('fromPrice').value);
            $('toPrice').value = trim($('toPrice').value);

            return true;
        }

</script>

