<%-- 
    Document   : ReportMngStockStaffDebit
    Created on : Aug 21, 2013, 8:26:51 AM
    Author     : thinhph2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="title.invenLimitByChannel">

    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <s:form id="inputForm" method="POST" theme="simple">
        <table class="inputTbl6Col">
            <tr>
                <!-- cua hang/don vi -->
                <td class="label"><tags:label key="MSG.generates.unit_code" required="true"/></td>
                <td class="text">
                    <tags:imSearch codeVariable="inputForm.shopCode"
                                   nameVariable="inputForm.shopName"
                                   codeLabel="MSG.generates.unit_code" 
                                   nameLabel="MSG.unit.name"
                                   searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                   searchMethodName="getListShop"
                                   getNameMethod="getShopName"
                                   elementNeedClearContent="inputForm.staffCode"
                                   />
                </td>
                <!-- nhan vien -->
                <td class="label"><tags:label key="MSG.RET.047"/></td>
                <td class="text">
                    <tags:imSearch codeVariable="inputForm.staffCode"
                                   nameVariable="inputForm.staffName"
                                   codeLabel="MSG.RET.047" 
                                   nameLabel="MSG.RET.048"
                                   searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                   searchMethodName="getListStaff"
                                   getNameMethod="getStaffName"
                                   otherParam="inputForm.shopCode"
                                   />
                </td>   
            </tr>
            <tr>
                <td class="label"><tags:label key="lbl.tu.han.muc"/></td>
                <td class="text">
                    <s:textfield name="inputForm.fromLimit" id="fromLimit" maxlength="20" cssClass="txtInput"/>
                </td>
                <td class="label"><tags:label key="lbl.den.han.muc"/></td>
                <td class="text">
                    <s:textfield name="inputForm.toLimit" id="toLimit" maxlength="20" cssClass="txtInput"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label"><tags:label key="lbl.from_date" required="true" /></td>
                <td style="width:20%; " class="oddColumn">
                    <tags:dateChooser id="fromDate" property="inputForm.fromDate"/>
                </td>--%>
                <td class="label"><tags:label key="MSG.report.type" required="true" /></td>
                <td class="oddColumn">
                    <tags:imSelect name="inputForm.reportType" id="reportType"
                                   cssClass="txtInputFull" disabled="false"
                                   list="1: lbl.report.detail.inven.channel, 2: report.adjust.inven.limit"
                                   headerKey="" headerValue="MSG.report.type1"/>
                </td>
            </tr>
            <%--<tr>
                <td class="label"><tags:label key="lbl.to_date" required="true" /></td>
                <td class="oddColumn">
                    <tags:dateChooser id="toDate" property="inputForm.toDate"/>
                </td>
            </tr>--%>
        </table>
        <center>
            <tags:submit formId="inputForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.report"
                         validateFunction="validateForm();"
                         preAction="reportInvenLimitStaffAction!exportReport.do"/>
        </center>

        <!-- phan link download bao cao -->

        <div>
            <s:if test="#request.reportAccountPath != null">
                <script>
                    window.open('${fn:escapeXml(contextPath)}/download.do?<s:property value="#request.reportAccountPath"/>','','toolbar=yes,scrollbars=yes');
                </script>
                <a href='${fn:escapeXml(contextPath)}/download.do?<s:property value="#request.reportAccountPath"/>' >
                    <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
                </a>
            </s:if>
            
        </div>
    </s:form>

</tags:imPanel>

<script type="text/javascript">
    jQuery("input[id='inputForm.shopCode']").focus();

    validateForm = function() {

        if (jQuery("input[id='inputForm.shopCode']").val() == "") {
            jQuery("#message").html('<s:property value="getText('err.chua.nhap.don.vi')"/>');
            jQuery("input[id='inputForm.shopCode']").focus();
            return false;
        }

        if (jQuery("input[id='inputForm.shopName']").val() == "") {
            jQuery("#message").html('<s:property value="getText('err.chua.nhap.ten.don.vi')"/>');
            jQuery("input[id='inputForm.shopCode']").focus();
            return false;
        }

        if (jQuery("#reportType").val() == "" || (jQuery("#reportType").val() != 1 && jQuery("#reportType").val() != 2)) {
            jQuery("#message").html('<s:property value="getText('err.chua.chon.loai.bao.cao')"/>');
            jQuery("#reportType").focus();
            return false;
        }

        /*var fromMonth = dojo.widget.byId("fromDate").getDate().getMonth();
        var fromYear = dojo.widget.byId("fromDate").getDate().getYear();
        var toMonth = dojo.widget.byId("toDate").getDate().getMonth();
        var toYear = dojo.widget.byId("toDate").getDate().getYear();

        if (jQuery("input[name='dojo.inputForm.fromDate']").val() == "") {
            jQuery("#message").html('<s:property value="getText('ERR.GOD.087')"/>');
            jQuery("input[name='dojo.inputForm.fromDate']").focus();
            return false;
        }

        if (jQuery("input[name='dojo.inputForm.toDate']").val() == "") {
            jQuery("#message").html('<s:property value="getText('err.chua.nhap.ngay.ket.thuc')"/>');
            jQuery("input[name='dojo.inputForm.toDate']").focus();
            return false;
        }

        if (dojo.widget.byId("fromDate").getDate() > dojo.widget.byId("toDate").getDate()) {
            jQuery("#message").html('<s:property value="getText('err.toDate_lessThan_fromDate')"/>');
            jQuery("input[id='fromDate']").focus();
            return false;
        }

        if ((toMonth - fromMonth) > 3 || fromYear != toYear) {
            jQuery("#message").html('<s:property value="getText('err.toDate_to_fromDate_in_oneMonth')"/>');
            jQuery("input[id='fromDate']").focus();
            return false;
        }*/

        return true;
    }
</script>
