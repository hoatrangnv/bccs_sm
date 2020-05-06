<%-- 
    Document   : ReportGetGoodCTV
    Created on : Nov 9, 2009, 11:13:46 AM
    Author     : LamNV
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%request.setAttribute("contextPath", request.getContextPath());%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<s:form method="POST" id="reportGetGoodCTVForm" action="reportGetGoodCTVAction" theme="simple">
<s:token/>

    <tags:imPanel title="MSG.RET.034">
        <table class="inputTbl8Col" border="0" style="width:100%">
            <tr>
                <%--<td class="label">Mã cửa hàng</td>--%>
                <td class="label"><tags:label key="MSG.RET.028"/></td>
                <td>
                    <s:textfield id="shopId" name="reportGetGoodCTVForm.shopId" value="%{#attr.reportGetGoodCTVForm.shopId}"/>
                </td>
                <%--<td class="label">Mã kho</td>--%>
                <td class="label"><tags:label key="MSG.RET.066"/></td>
                <td>
                    <s:textfield id="staffId" name="reportGetGoodCTVForm.staffId" value="%{#attr.reportGetGoodCTVForm.staffId}"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Từ ngày</td>--%>
                <td class="label"><tags:label key="MSG.RET.036"/></td>
                <td class="text">
                    <tags:dateChooser property="reportGetGoodCTVForm.fromDate" id="fromDate" styleClass="txtInputFull"/>
                </td>
                <%--<td class="label">
                    Đến ngày
                </td>--%>
                <td class="label"><tags:label key="MSG.RET.037"/></td>
                <td class="text">
                    <tags:dateChooser property="reportGetGoodCTVForm.toDate" id="toDate" styleClass="txtInputFull"/>
                </td>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="8" align="center" width="100%">
                    <!--button onclick="viewTransList()">Xem giao dịch</button-->
                    <tags:submit id="report" confirm="false" formId="reportGetGoodCTVForm" showLoadingText="true" validateFunction="validateForm()"
                                 targets="displayLstTrans" value="MSG.RET.098" preAction="reportGetGoodCTVAction!viewReport.do"/>
                </td>
            </tr>            
            <tr></tr>
            <tr>
                <td colspan="8" align="left" width="100%">
                    <div id="displayLstTrans">
                    </div>  
                </td>
            </tr>
        </table>
    </tags:imPanel>  



</s:form>
<script>
    validateForm = function() {
        return true;
    }
</script>
