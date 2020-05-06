<%-- 
    Document   : createInvoiceSerialNo
    Created on : Sep 11, 2009, 2:09:34 PM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            request.setAttribute("lstSerialNo", request.getSession().getAttribute("lstSerialNo"));

            com.viettel.database.DAO.BaseDAOAction baseDAOAction = new com.viettel.database.DAO.BaseDAOAction();

            request.setAttribute("combo_header_serial_no",baseDAOAction.getText("MSG.SAE.196"));
%>


<table class="inputTbl6Col">
    <tr>
        <td class="label"><tags:label key="MSG.SAE.193"/></td>
        <td class="text">
            <s:textfield name="form.shopName" id="shopName" theme="simple" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
        </td>
        <td class="label"><tags:label key="MSG.SAE.091"/></td>
        <td class="text">
            <s:textfield name="form.staffName"  id="staffName" theme="simple" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
        </td>
        <td class="label"><tags:label key="MSG.SAE.194"/></td>
        <td class="text">
            <tags:dateChooser property="form.createDate"  id="createDate" styleClass="txtInput"  insertFrame="false"
                                          readOnly="true"/>
            <%--s:textfield name="form.createDate" id="createDate" theme="simple" maxlength="1000" readonly="true" cssClass="txtInputFull"/--%>
        </td>
    </tr>
    <s:if test="form.invoiceUsedId == null || form.invoiceUsedId == ''">
        <tr>
            <td class="label"><tags:label key="MSG.SAE.195" required="true"/></td>
            <td class="text">
                <s:select name="form.serialNo" theme="simple"
                               id="serialNo"
                               cssClass="txtInputFull"

                               headerKey="" headerValue="%{#request.combo_header_serial_no}"

                               list="%{#request.lstSerialNo != null?#request.lstSerialNo:#{}}"
                               listKey="serialNo" listValue="serialNo"
                               onchange="getInvoiceNo();"/>
            </td>
            <td class="label"><tags:label key="MSG.SAE.197"/></td>
            <td class="text">
                <s:textfield name="form.fromInvoice" id="fromInvoice" theme="simple" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
            </td>
            <td class="label"><tags:label key="MSG.SAE.198"/></td>
            <td class="text">
                <s:textfield name="form.toInvoice" id="toInvoice" theme="simple" maxlength="1000" readonly="true" cssClass="txtInputFull" />
            </td>
        </tr>
        <tr>
            <td class="label"><tags:label key="MSG.SAE.199"/></td>
            <td class="text">
                <s:textfield name="form.curInvoice" id="curInvoice" theme="simple" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
            </td>
            <td class="label"><tags:label key="MSG.SAE.200"/></td>
            <td colspan="3" class="text">
                <s:hidden name="form.invoiceNo" id="invoiceNo" ></s:hidden>
                <font color="red" size="4px"><s:property escapeJavaScript="true"  value="form.invoiceNo"></s:property></font>
                <s:hidden name="form.invoiceListId" id="invoiceListId" ></s:hidden>
                <s:hidden name="form.invoiceNoEdit" id="invoiceNoEdit" />
            </td>
        </tr>
    </s:if>
    <s:else>
        <tr>
            <td class="label"><tags:label key="MSG.SAE.195" required="true"/></td>
            <td class="text">
                <s:textfield name="form.serialNo" id="serialNo" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
            </td>
            <td class="label"><tags:label key="MSG.SAE.197"/></td>
            <td class="text">
                <s:textfield name="form.fromInvoice" id="fromInvoice" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
            </td>
            <td class="label"><tags:label key="MSG.SAE.198"/></td>
            <td class="text">
                <s:textfield name="form.toInvoice" id="toInvoice" maxlength="1000" readonly="true" cssClass="txtInputFull" />
            </td>
        </tr>
        <tr>            
            <td class="label"><tags:label key="MSG.SAE.200"/>
<!--                Số hóa đơn-->
            </td>
            <td colspan="3" class="label">
                <s:hidden name="form.invoiceNo" id="invoiceNo" ></s:hidden>
                <font color="red" size="4px"><s:property escapeJavaScript="true"  value="form.invoiceNo"></s:property></font>
                <s:hidden name="form.invoiceListId" id="invoiceListId" ></s:hidden>
                <s:hidden name="form.invoiceNoEdit" id="invoiceNoEdit" />
            </td>
        </tr>
    </s:else>
</table>

