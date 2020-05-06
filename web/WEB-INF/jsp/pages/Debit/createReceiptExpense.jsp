<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : createReceiptExpense
    Created on : 
    Author     : 
--%>
 
<%--
    Note: thu tiền đặt cọc tại cửa hàng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>


<%
            if (request.getAttribute("listPayMethod") == null) {
                request.setAttribute("listPayMethod", request.getSession().getAttribute("listPayMethod"));
            }
            if (request.getAttribute("listReason") == null) {
                request.setAttribute("listReason", request.getSession().getAttribute("listReason"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.info.receipt">
<table class="inputTbl4Col">
    <tr>
        <td colspan="4">
            <tags:displayResult id="displayResultMsgClientB" property="returnMsgB" propertyValue="returnMsgValueB" type="key" />
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.receipt.code" required="true"/></td>
        <td class="text">
            <s:hidden  name="payDebitShopForm.depositId" id="depositId"></s:hidden>
            <s:hidden name="payDebitShopForm.receiptId" id="receiptId"></s:hidden>
            <s:hidden name="payDebitShopForm.status" id="status"></s:hidden>
            
            <%--<s:if test="payDebitShopForm.receiptId == ''">--%>
            <%--<s:if test="payDebitShopForm.status * 1 == 0 ">
                <s:textfield name="payDebitShopForm.receiptNo" id="receiptNo" maxlength="40" cssClass="txtInput" theme="simple"/>
            </s:if>
            <s:else>
                <s:textfield name="payDebitShopForm.receiptNo" id="receiptNo" maxlength="40" cssClass="txtInput" readonly="true" theme="simple"/>
            </s:else>--%>
            <%--De he thong tu sinh receiptNo, ko duoc nhap tay--%>
            <s:textfield name="payDebitShopForm.receiptNo" id="receiptNo" maxlength="40" cssClass="txtInput" readonly="true" theme="simple"/>
        </td>
        <td class="label"><tags:label key="MSG.deposit.type"/></td>
        <td class="text">
            <s:textfield name="payDebitShopForm.name" id="name" maxlength="40" cssClass="txtInput" readonly="true"  theme="simple"/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.isdn"/> </td>
        <td class="text">
            <s:textfield name="payDebitShopForm.isdn" id="isdn" maxlength="80" readonly="true" cssClass="txtInput" theme="simple"/>
        </td>
        <td class="label"><tags:label key="MSG.totalDeposit"/></td>
        <td class="text">
            <s:textfield name="payDebitShopForm.amount" id="amount" maxlength="20"  readonly="true" cssClass="txtInput" theme="simple"/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.cusName"/></td>
        <td class="text">
            <s:textfield name="payDebitShopForm.custName" id="custName" maxlength="20" readonly="true" cssClass="txtInput" theme="simple"/>
        </td>
        <td class="label"><tags:label key="MSG.household.address"/></td>
        <td class="text">
            <s:textfield name="payDebitShopForm.address" id="address" maxlength="20" readonly="true" cssClass="txtInput" theme="simple"/>
        </td>
    </tr>
    <tr>
        <td class="label"> <tags:label key="MSG.shop.deposit"/></td>
        <td class="text">
            <s:textfield name="payDebitShopForm.shopName" id="shopName"  readonly="true" cssClass="txtInput" theme="simple"/>
        </td>
        <td class="label"> <tags:label key="MSG.staff.execute"/></td>
        <td class="text">
            <s:textfield name="payDebitShopForm.staffName" id="staffName" maxlength="20" readonly="true" cssClass="txtInput" theme="simple"/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.method.paid" required="true"/></td>
        <td class="text">
            <%--<s:if test="payDebitShopForm.receiptId == ''">--%>
            <s:if test="payDebitShopForm.status * 1 == 0 ">
                <tags:imSelect name="payDebitShopForm.payMethod"
                      id="payMethod"
                      cssClass="txtInput" theme="simple"
                      headerKey="" headerValue="MSG.DET.042"
                      list="listPayMethod"
                      listKey="code" listValue="name"/>
            </s:if>
            <s:else>
                <tags:imSelect name="payDebitShopForm.payMethod"
                      id="payMethod" disabled="true"
                      cssClass="txtInput" theme="simple"
                      headerKey="" headerValue="MSG.DET.042"
                      list="listPayMethod"
                      listKey="code" listValue="name"/>
            </s:else>
        </td>
        <td class="label"><tags:label key="MSG.reason"/></td>
        <td class="text">
             <%--<s:if test="payDebitShopForm.receiptId == ''">--%>
             <s:if test="payDebitShopForm.status * 1 == 0 ">
                <tags:imSelect name="payDebitShopForm.reasonId"
                      id="reasonId"
                      cssClass="txtInput" theme="simple"
                      list="listReason"
                      listKey="reasonId" listValue="reasonName"/>
            </s:if>
            <s:else>
                <tags:imSelect name="payDebitShopForm.reasonId"
                          id="reasonId" disabled="true"
                      cssClass="txtInput" theme="simple"
                      list="listReason"
                      listKey="reasonId" listValue="reasonName"/>
            </s:else>
        </td>
    </tr>
</table>
</tags:imPanel>
<br />
<div align="center">
    <%--<s:if test="payDebitShopForm.receiptId == ''">--%>
    <s:if test="payDebitShopForm.status * 1 == 0 ">
        <tags:submit  id="updateButton" formId="payDebitShopForm" confirm="true" confirmText="MSG.confirm.create.receipt"
                                   showLoadingText="true" targets="detailArea" value="MSG.create.receipt"
                                   cssStyle="width:85px;"
                                   preAction="PayDebitShopAction!addDocDepositShop.do" validateFunction="validateData()"
                                   />
        <input type="button" value=" ${fn:escapeXml(imDef:imGetText('MSG.DET.043'))}" style="width:85px;" disabled/>
    </s:if>
    <s:else>
        <input type="button" value=" ${fn:escapeXml(imDef:imGetText('MSG.DET.044'))}" style="width:85px;" disabled/>
        <%--<input type="button" value=" ${fn:escapeXml(imDef:imGetText('MSG.DET.043'))}" style="width:85px;" disabled/>--%>
        <tags:submit id="printButton" formId="payDebitShopForm" showLoadingText="true" targets="bodyContent"
                     value="MSG.DET.043" cssStyle="width:85px;"
                     preAction="PayDebitShopAction!printDocDepositShop.do"/>
    </s:else>
</div>
<div>
    <s:if test="#request.downloadPath != null">
        <script>
            window.open('${fn:escapeXml(downloadPath)}','','toolbar=yes,scrollbars=yes');
        </script>
        <a href="${fn:escapeXml(downloadPath)}">
            <tags:label key="MSG.GOD.272"/>
        </a>
    </s:if>
</div>

<script>
    textFieldNF($('amount'));

    var _myWidget2 = $('receiptNo');
    if (_myWidget2 != null)
        _myWidget2.focus();
    
</script>


