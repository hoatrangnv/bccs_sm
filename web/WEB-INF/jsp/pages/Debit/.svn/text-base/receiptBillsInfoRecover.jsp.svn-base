<%-- 
    Document   : receiptBillsInfoRecover
    Created on : Sep 29, 2009, 6:11:19 PM
    Author     : User
--%>
 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%--<fieldset style="width:95%; padding:10px 10px 10px 10px">
    <legend clazss="transparent">Thông tin phiếu chi</legend>--%>
<tags:imPanel title="MSG.info.spent">

    <table class="inputTbl4Col">
        <tr>
            <td colspan="4">
                <tags:displayResult id="displayResultMsgClientB" property="returnMsgB" type="key" />
            </td>
        </tr>
        <tr>
            <td class="label"><tags:label key="MSG.spent.code" required="true"/></td>
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

            <td class="label"><tags:label key="MSG.user.receive" required="true"/></td>
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
    <%--</fieldset>--%>
</tags:imPanel>
<br/>
<div align="center">
    <table>
        <tr>
            <td>
                <sx:submit parseContent="true" executeScripts="true"
                           cssStyle="width:85px;" id = "bttSaveInformation"
                           formId="payDepositFormId" loadingText="Processing..."
                           targets="bodyContent"
                           key="MSG.DET.019"  beforeNotifyTopics="payDepositFormId/saveInformationOrderAction"/>

                <%--<tags:submit targets="bodyContent" formId="payDepositFormId"
                             cssStyle="width:80px;" value="MSG.createNote"
                             preAction=""
                             />--%>
            </td>
            <td>
                <sx:submit parseContent="true" executeScripts="true"
                           cssStyle="width:85px;" id = "bttCancelInformation"
                           formId="payDepositFormId" loadingText="Processing..."
                           targets="bodyContent"
                           key="MSG.DET.020"  beforeNotifyTopics="payDepositFormId/cancelInformationOrderAction"/>

                <%--<tags:submit targets="bodyContent" formId="payDepositFormId"
                             cssStyle="width:80px;" value="MSG.createNote"
                             preAction=""
                             />--%>
            </td>
            <td>
                <tags:submit targets="linkDownload" formId="payDepositFormId" cssStyle="width:80px;" id="btnPrintBill"
                             confirm="false" confirmText="Bạn có chắc chắn muốn In phiếu thu?"
                             value="In phiếu" preAction="saveInformationOrderAction!printBillRecover.do"
                             showLoadingText="true"/>
            </td>>
        </tr>
    </table>
</div>
