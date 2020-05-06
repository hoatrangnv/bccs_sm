<%-- 
    Document   : receiptBillsInfo
    Created on : Jun 9, 2009, 2:28:05 PM
    Author     : nhocrep
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%--<fieldset style="width:95%; padding:10px 10px 10px 10px">
    <legend clazss="transparent">Thông tin phiếu thu</legend>--%>
<tags:imPanel title="MSG.info.receipt">
    <table class="inputTbl4Col">
        <tr>
            <td colspan="4">
                <tags:displayResult id="displayResultMsgClientB" property="returnMsgB" type="key" />
            </td>
        </tr>
        <tr>
            <td class="label"><tags:label key="MSG.receipt.code" required="true"/></td>
            <td class="text">
                <s:textfield name="formCodeIdMapped" id="formCodeIdMapped" maxlength="30" theme="simple" cssClass="txtInput"/>
            </td>
            <td class="label"><tags:label key="MSG.reason" required="true"/></td>
            <td class="text">
                <s:hidden name="tempReasonName" id="tempReasonNameId" />
                <s:hidden name="reasonNameId" id="reasonId" />
                <s:textfield name="reasonName"  id="reasonName" theme="simple" readonly="true" maxlength="80" cssClass="txtInput"/>
            </td>
        </tr>
        <tr>
            <td class="label"><tags:label key="MSG.shop.name" required="true"/></td>
            <td class="text">
                <s:hidden name="agentNameId" id="agentId" />
                <s:textfield name="agentName"  id="agentNameImportId" theme="simple" maxlength="80"  readonly="true" cssClass="txtInput"/>
            </td>

            <td class="label"><tags:label key="MSG.user.paid" required="true"/></td>
            <td class="text">
                <s:textfield name="userPay" id="userPayId" size="25" maxlength="50" theme="simple" cssClass="txtInput" />
            </td>
        </tr>
        <tr>
            <td class="label"><tags:label key="MSG.moneyTotal" required="true"/></td>
            <td class="text">
                <s:hidden name="total" id="hiddenTotalId" />
                <s:textfield name="tempTotal" readOnly="true" id="totalId" theme="simple" cssClass="txtInput" />
            </td>
            <td class="label"><tags:label key="MSG.payMethod" required="true"/></td>
            <td class="text">
                <tags:imSelect name="paramId" id="paramId"
                          cssClass="txtInput" theme="simple"
                          list="10:MSG.DET.017"
                          headerKey="" headerValue="MSG.DET.018"/>
            </td>
        </tr>
    </table>
</tags:imPanel>
<br/>
<div align="center">
    <table>
        <tr>
            <td>
                <%--sx:submit parseContent="true" executeScripts="true"
                           cssStyle="width:80px;" id = "bttSaveInformation"
                           formId="payDepositFormId" loadingText="Processing..."
                           targets="bodyContent"
                           key="MSG.createNote"  beforeNotifyTopics="payDepositFormId/saveInformationOrderAction"/--%>

                <tags:submit targets="bodyContent" formId="payDepositFormId"
                             cssStyle="width:80px;" id="bttSaveInformation"
                             validateFunction="checkBeforeSave();"
                             confirm="true" confirmText="MSG.confirm.create.receipt"
                             value="MSG.createNote"
                             preAction="saveInformationOrderAction!saveInformationOrderAction.do"
                             showLoadingText="true"/>

            </td>
            <td>
                <tags:VTsx_submit parseContent="true" executeScripts="true"
                           id = "bttCancelInformation"
                           formId="payDepositFormId" loadingText="Processing..."
                           targets="bodyContent"
                           value="L.200001" beforeNotifyTopics="payDepositFormId/cancelInformationOrderAction"/>

                <%--<tags:submit targets="bodyContent" formId="payDepositFormId" cssStyle="width:80px;" id="bttCancelInformation"
                             validateFunction="checkBeforeCancel();"
                             confirm="true" confirmText="MSG.confirm.create.receipt"
                             value="MSG.destroy.note" preAction="saveInformationOrderAction!cancelInformationOrderAction.do"
                             showLoadingText="true"/>--%>
            </td>
            <td>
                <tags:submit targets="linkDownload" formId="payDepositFormId" cssStyle="width:80px;" id="btnPrintBill"
                             confirm="false" confirmText="MSG.confirm.print.note.recover"
                             value="MSG.printNote" preAction="saveInformationOrderAction!printBill.do"
                             showLoadingText="true"/>
            </td>
        </tr>
    </table>
</div>

<div id="linkDownload" align="center">
    <s:if test="#request.reportStockTransPath != null && #request.reportStockTransPath != ''">
        <jsp:include page="../Debit/linkDownload.jsp"></jsp:include>
    </s:if>

    <%--<s:if test="pathExpFile!=null && pathExpFile!=''">
        <script>
            window.open('${contextPath}<s:property escapeJavaScript="true"  value="pathExpFile"/>','','toolbar=yes,scrollbars=yes');
        </script>
        <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="pathExpFile"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về</a></div>
</s:if>--%>
</div>
