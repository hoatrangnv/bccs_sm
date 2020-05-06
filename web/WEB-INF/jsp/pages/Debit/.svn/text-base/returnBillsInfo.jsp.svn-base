<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : returnBillsInfo
    Created on : Jun 15, 2009, 6:54:41 PM
    Author     : nhocrep

Lap phieu chi tra tien dat coc dai ly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>



<sx:div id ="returnBillsInfo">
    <s:form action="addDepositDetailAction" id="returnBillsInfoForm" method="post" theme="simple">
<s:token/>

        <%--<fieldset style="width:100%;">--%>
            <%--<legend class="transparent">Thông tin phiếu chi</legend>--%>
            <div style="width:100%; height:292px; overflow:auto; border-width:1px; border-style:solid; border-color:silver">
            <tags:imPanel title="MSG.info.spent">
            <table class="inputTbl2Col">
                <tr>
                    <td colspan="2">
                        <tags:displayResult id="displayResultMsgClient3" property="returnMsg3" propertyValue="returnMsgValue" type="key" />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.spent.code" required="true"/></td>
                    <td class="text">
                        <s:textfield name="formCode" id="formCodeId" maxlength="50" theme="simple" cssClass="txtInput"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.reason" required="true"/></td>
                    <td class="text"><s:select name="reason" list="reasons" listKey="reasonId" listValue="reasonName" theme="simple" cssClass="txtInput" id="idReason"
                                  headerKey="" headerValue="--Chọn lý do--"/></td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.paid.date" required="true"/></td>
                    <td class="text">
                        <tags:dateChooser property="startDate" id="dateId" readOnly="true" />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.moneyTotal" required="true"/></td>
                    <td class="text"><s:textfield name="totalAmount"  size="25" id="totalId" maxlength="30" readonly="true" theme="simple" cssClass="txtInput"/></td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.paid.channel.name" required="true"/></td>
                    <td class="text">
                        <s:textfield name="companyName" readOnly="true" size="25" id="companyNameId" maxlength="20" theme="simple" cssClass="txtInput"/>
                        <s:hidden name="tempCompanyName" id="tempCompanyNameId" cssClass="txtInput" theme="simple" />
                        <s:hidden name="company" id="companyId"  cssStyle="txtInput" theme="simple" />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.paid.person" required="true"/></td>
                    <td class="text"><s:textfield name="userReceipt"  size="25" id="userReceiptId" maxlength="20" theme="simple" cssClass="txtInput"/></td>
                </tr>
                <tr>
                    <td class="label">
                         <tags:label key="MSG.pay.type" required="true"/>
                    </td>
                    <td class="text">
                        <tags:imSelect name="parames" list="paramses" listKey="id.code" listValue="name" theme="simple" cssClass="txtInput" id="idPayMethod"
                                  headerKey="" headerValue="MSG.DET.018"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <sx:submit cssStyle="width:100px;" loadingText="Ðang thực hiện..."
                                    executeScripts="true"
                                   formId="returnBillsInfoForm"
                                   showLoadingText="true" targets="returnBillsInfo"
                                   beforeNotifyTopics="AddDepositCreateReceiptFormId/saveDepositExport"
                                   key="MSG.DET.019"  />
                        

                    </td>
                </tr>
            </table>
            <%--s:url action="searchInformationDepositDetailAction!searchDepositExport.do" var="searchDepositExport" >
                <s:param name="isSaveDeposit" value="1"></s:param>
            </s:url>
            <sx:bind id="searchNowBind" href="%{searchDepositExport}" formId="DepositCreateReceiptFormId"
            listenTopics="searchNow" targets="bodyContent" separateScripts="true" executeScripts="true"/--%>
            </tags:imPanel>
            </div>
        <%--</fieldset>--%>
    </s:form>
</sx:div>

<script language="javascript">

    var isLoadForm = '${fn:escapeXml(isLoadForm)}';
    if(isLoadForm == "1"){
         var _myWidget = dojo.widget.byId("autoAgentId");
         var _agentCode = _myWidget.textInputNode.value.trim();
        //dojo.event.topic.publish('searchNow');
        gotoAction("bodyContent", 'searchInformationDepositDetailAction!searchDepositExport.do?isSaveDeposit=1&agentCodeId=' + _agentCode, 'DepositCreateReceiptFormId');
    }

</script>
