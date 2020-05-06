<%-- 
    Document   : reportRevenueBoughtByCollaborator
    Created on : Jun 22, 2010, 4:21:48 PM
    Author     : Doan Thanh 8
    Desc       : bao cao mua hang cua CTV/ DB
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
            <table class="inputTbl4Col">
                <tr>
                    <%--<td style="width:10%; ">Mã cửa hàng<font color="red">*</font></td>--%>
                    <td style="width:10%; "><tags:label key="MSG.shop.code" required="true"/></td>
                    <td style="width:30%; " class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.shopCode" nameVariable="reportRevenueForm.shopName"
                                       codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                       searchClassName="com.viettel.im.database.DAO.ReportRevenueBoughtByCollaboratorDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"
                                       elementNeedClearContent="reportRevenueForm.staffCode;reportRevenueForm.staffName;reportRevenueForm.ownerCode;reportRevenueForm.ownerName"
                                       roleList="reportRevenueShop"/>
                    </td>
                    <td style="width:10%; "><tags:label key="label.channel.type" /><%--Loại kênh--%></td>
                    <td style="width:30%; " class="oddColumn" colspan="3">
                        <tags:imSelect name="reportRevenueForm.channelTypeId" id="reportRevenueForm.channelTypeId" headerKey="" headerValue="label.option" cssClass="txtInputFull"
                                       list="lstChannelTypeCol" listKey="channelTypeId" listValue="name"/>
                    </td>
                </tr>

                <tr>
                    <td ><tags:label key="MES.CHL.064"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.staffManageCode" nameVariable="reportRevenueForm.staffManageName"
                                       codeLabel="MES.CHL.064" nameLabel="MES.CHL.065"
                                       searchClassName="com.viettel.im.database.DAO.ReportRevenueBoughtByCollaboratorDAO"
                                       searchMethodName="getListStaff"
                                       getNameMethod="getStaffName"
                                       otherParam="reportRevenueForm.shopCode"
                                       elementNeedClearContent="reportRevenueForm.ownerCode;reportRevenueForm.ownerName"
                                       roleList="reportRevenueStaff"/>
                    </td>
                    <td ><tags:label key="MSG.DET.008"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.ownerCode" nameVariable="reportRevenueForm.ownerName"
                                       codeLabel="MSG.DET.008" nameLabel="MSG.DET.009"
                                       searchClassName="com.viettel.im.database.DAO.ReportRevenueBoughtByCollaboratorDAO"
                                       searchMethodName="getListOwner"
                                       getNameMethod="getOwnerName"
                                       otherParam="reportRevenueForm.shopCode;reportRevenueForm.staffManageCode"
                                       />
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
                    <td colspan="4">
                        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
                            <tags:imRadio name="reportRevenueForm.reportType" id="reportType"
                                          list="1:MSG.RET.115,2:MSG.RET.116"/>
                        </div>
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
        </div>

                <%--tr>
                 <td ><tags:label key="MSG.RET.077"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="reportRevenueForm.goodsCode" nameVariable="reportRevenueForm.goodsName"
                                       codeLabel="MSG.RET.088" nameLabel="MSG.RET.089"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListGoods"
                                       getNameMethod="getGoodsName"/>
                    </td>
                    <td>Đối tượng</td>
                    <td class="oddColumn">                        
                        <tags:imSelect name="reportRevenueForm.objectType" id="objectType"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.RET.172"
                                       list="2:MSG.RET.016,1:MSG.RET.017"
                                       />
                    </td>
                    <td>Dịch vụ</td>
                    <td>                        
                        <tags:imSelect name="reportRevenueForm.telecomServiceId"
                                       id="telecomServiceId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.RET.171"
                                       list="listTelecomService"
                                       listKey="telecomServiceId" listValue="telServiceName"/>
                    </td>
                </tr--%>



            </table>
        </div>

    </s:form>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="reportRevenueForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MSG.RET.040"
                     preAction="reportRevenueBoughtByCollaboratorAction!reportRevenueBoughtByCollaborator.do"/>
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
    clickChange = function(value) {
        $('reportCheckType').value = value;
    }

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

</script>
