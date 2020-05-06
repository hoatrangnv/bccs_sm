<%-- 
    Document   : createInvoiceNoSaleDetailInputItem
    Created on : Aug 7, 2009, 10:11:18 AM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<fieldset style="width:100%; height:300px; vertical-align:top">
    <legend class="transparent"> <tags:label key="MSG.imp.goods"/></legend>
    <table class="inputTbl">
        <tr>
            <td style="width:5%;"></td>
            <td style="width:20%;"></td>
            <td style="width:30%;"></td>
            <td style="width:20%;"></td>
            <td style="width:20%;"></td>
            <td style="width:5%;"></td>
        </tr>
        <tr>
            <td style="width:5%;"></td>
            <td style="width:20%;"><tags:label key="MSG.stockModelId"/></td>
            <td style="width:30%;">
                <s:textfield name="saleForm.itemCode" id="itemCode" maxlength="10" cssClass="txtInput" theme="simple"/>
            </td>
            <td style="width:20%;"><tags:label key="MSG.unit.channel" required="true"/></td>
            <td style="width:20%;">
                <s:textfield name="saleForm.itemUnit" id="itemUnit" maxlength="10" cssClass="txtInput" theme="simple"/>
            </td>
            <td style="width:5%;"></td>
        </tr>
        
        <tr>
            <td style="width:5%;"></td>
            <td><tags:label key="MSG.stockModelName" required="true"/></td>
            <td colspan="5">
                <s:textfield name="saleForm.itemName" id="itemName" maxlength="44" cssClass="txtInput" theme="simple"/>
                <s:hidden name="saleForm.itemId" id="itemId"/>
            </td>
        </tr>
        <tr>
            <td style="width:5%;"></td>
            <td><tags:label key="MSG.price" required="true"/></td>
            <td>
                <s:textfield name="saleForm.itemPriceString" id="itemPriceString" maxlength="10" cssClass="txtInput" theme="simple"/>
            </td>
            <td><tags:label key="MSG.quantity" required="true"/></td>
            <td>
                <s:textfield name="saleForm.itemQtyString" id="itemQtyString" maxlength="10" cssClass="txtInput" theme="simple"/>
            </td>
            <td style="width:5%;"></td>
        </tr>        
        <tr>
            <td style="width:5%;"></td>
            <td> <tags:label key="MSG.comment"/></td>
            <td colspan="5">
                <s:textarea name="saleForm.itemNote" id="itemNote" cssClass="txtInput" theme="simple" rows="1"/>
            </td>
        </tr>
        <tr>
            <td></td>
        </tr>
        <tr>
            <td colspan="6">
                <div align="center">
                    <s:if test="#session.createInvoiceNoSaleActionActionType != 'EDIT'">
                        <sx:submit parseContent="true" executeScripts="true"
                                   formId="saleForm" loadingText="Đang thực hiện..."
                                   targets="invoiceDetail"
                                   key="MSG.add.goods"  beforeNotifyTopics="searchSaleTransAction/addItem" errorNotifyTopics="errorAction"/>
                    </s:if>
                    <s:else>
                        <sx:submit parseContent="true" executeScripts="true"
                                   targets="invoiceDetail" formId="saleForm" key="MSG.edit.goods" beforeNotifyTopics="createInvoiceNoSaleAction/updateItem"
                                   errorNotifyTopics="errorAction" />
                        <sx:submit parseContent="true" executeScripts="true"
                                   targets="invoiceDetailInputItem" formId="saleForm" key="MSG.cancel2" beforeNotifyTopics="createInvoiceNoSaleAction/cancelItem"
                                   errorNotifyTopics="errorAction" />
                    </s:else>
                </div>
            </td>
    </tr>
    </table>
</fieldset>

            