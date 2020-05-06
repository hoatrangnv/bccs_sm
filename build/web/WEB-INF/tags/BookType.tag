<%-- 
    Document   : BookType
    Created on : Aug 17, 2009, 8:58:40 AM
    Author     : Vunt 
--%>

<%@tag description="Hien thi cac thong tin ve BookType" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@attribute name="property" description="Ten thuoc tinh cua bean chua doi tuong BookType" %>
<%@attribute name="readOnly" %>
<%@attribute name="toInputData" type="java.lang.Boolean" rtexprvalue="true" description="Form dung de nhap du lieu hay de hien thi, = true de nhap DL, = false de hien thi" %>

<%
        request.setAttribute("contextPath", request.getContextPath());

        if ((property != null) && (!property.trim().equals(""))) {
            request.setAttribute("property1", property + ".");
        } else {
            request.setAttribute("property1", "");
        }
%>



<table class="inputTbl6Col">
    <tr>
        <td><tags:label key="MSG.bill.serial.number" required="true" /></td>
        <td class="oddColumn">
            <s:textfield name="%{#request.property1}serialcode" id="%{#request.property1}serialcode" maxlength="20" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td><tags:label key="MSG.real.serial.number" /></td>
        <td class="oddColumn">
            <s:textfield name="%{#request.property1}serialreal" id="%{#request.property1}serialreal" maxlength="20" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>        

        <td><tags:label key="MSG.bill.type.name" required="true" /></td>
        <td>
            <s:textfield name="%{#request.property1}blockname" id="%{#request.property1}blockname" maxlength="50" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>
    </tr>
    <tr>
        <td><tags:label key="MSG.type" required="true" /></td>
        <td class="oddColumn">
            <tags:imSelect name="${property1}type"
                           id="${property1}type"
                           cssClass="txtInputFull"
                           list="1:MSG.book, 2:MSG.bin"
                           headerKey="" headerValue="MSG.LST.007"/>
        </td>
        <td><tags:label key="MSG.bill.template" required="true" /></td>
        <td class="oddColumn">
            <s:textfield name="%{#request.property1}book" id="%{#request.property1}book" maxlength="2" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td><tags:label key="MSG.pager.number.in.book" required="true" /></td>
        <td>
            <s:textfield name="%{#attr.property1 + 'numinvoice'}" id="%{#request.property1}numinvoice" maxlength="10" cssClass="txtInputFull" readonly="%{#request.toInputData}"/>
        </td>
    </tr>
    <tr>
        <td><tags:label key="MSG.papers.in.book" required="true" /></td>
        <td class="oddColumn">
            <s:textfield name="%{#request.property1}lengthname" id="%{#request.property1}lengthname" maxlength="2" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td><tags:label key="MSG.bill.number.length" required="true" /></td>
        <td class="oddColumn">
            <s:textfield name="%{#request.property1}lengthinvoice" id="%{#request.property1}lengthinvoice" maxlength="2" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td><tags:label key="MSG.bill.number.wide" required="false" /></td>
        <td>
            <s:textfield name="%{#request.property1}pagewith" id="%{#request.property1}pagewith" maxlength="10" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>
    </tr>
    <tr>
        <td><tags:label key="MSG.bill.number.hight" required="false" /></td>
        <td class="oddColumn">
            <s:textfield name="%{#request.property1}pageheight" id="%{#request.property1}pageheight" maxlength="10" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td><tags:label key="MSG.line.space" required="false" /></td>
        <td class="oddColumn">
            <s:textfield name="%{#request.property1}rowspacing" id="%{#request.property1}rowspacing" maxlength="10" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td><tags:label key="MSG.line.max" required="false" /></td>
        <td>
            <s:textfield name="%{#request.property1}maxrow" id="%{#request.property1}maxrow" maxlength="10" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>                
    </tr>
    <tr>       
        <td><tags:label key="MSG.generates.status" required="false" /></td>
        <td class="oddColumn">
            <tags:imSelect name="${property1}status"
                           id="${property1}status"
                           cssClass="txtInputFull"
                           headerKey="" headerValue="MSG.GOD.189"
                           list="1:MSG.GOD.002, 0:MSG.GOD.003"/>
        </td>
        <td><tags:label key="MSG.comment" required="false" /></td>
        <td class="oddColumn">
            <s:textfield name="%{#request.property1}decription" id="%{#request.property1}decription" maxlength="500" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td><tags:label key="MSG.invoice.type" required="true" /></td>
        <td>
            <tags:imSelect name="${property1}invoiceType"
                           id="${property1}invoiceType"
                           cssClass="txtInputFull"
                           list="1:msg.invoiceTypeSale, 2:msg.invoiceTypePayment, 3:msg.receiptTypeSale, 4:msg.receiptTypePayment, 
                                 5:msg.expenseTypeSale, 6:msg.expenseTypePayment, 7:msg.voucherTypeSale, 8:msg.voucherTypePayment
                                 , 9:msg.invoiceTypeAdjustment"
                           headerKey="" headerValue="msg.invoiceTypeHeader"/>

        </td>
    </tr>
</table>