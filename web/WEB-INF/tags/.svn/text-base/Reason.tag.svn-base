<%--
    Document   : Reason
    Created on : Feb 17, 2009, 4:54:52 PM
    Author     : namnx
--%>

<%--
    Note:  Tag hien thi thong tin cua ReasonForm
    Dau vao:    Ten thuoc tinh cua bean chua doi tuong ReasonForm can hien thi thong tin
                toInputData = true: dung de nhap DL, = false: chi co kha nang readonly
    Dau ra:     hien thi thong tin ve ReasonForm
                cac Id duoc dat theo cau truc tenBienReasonForm_tenThuocTinh
--%>

<%@tag description="Hien thi cac thong tin ve ReasonForm" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@attribute name="property" description="Ten thuoc tinh cua bean chua doi tuong ReasonForm" %>
<%@attribute name="toInputData" type="java.lang.Boolean" rtexprvalue="true" description="Form dung de nhap du lieu hay de hien thi, = true de nhap DL, = false de hien thi" %>

<%
        request.setAttribute("contextPath", request.getContextPath());

        if ((property != null) && (!property.trim().equals(""))) {
            request.setAttribute("property1", property + ".");
        } else {
            request.setAttribute("property1", "");
        }

        request.setAttribute("listReasonGroup",request.getSession().getAttribute("listReasonGroup"));
%>

<table class="inputTbl6Col">
    <tr>
        <td class="label"><tags:label key="MSG.reason.code" required="true" /> </td>
        <td class="text">
            <s:textfield name="%{#attr.property1 + 'reasonCode'}" id="%{#attr.property1 + 'reasonCode'}" maxlength="20" cssClass="txtInputFull"/>
        </td>
        <td class="label"><tags:label key="MSG.LST.022" required="true" /> </td>
        <td class="text">
            <s:textfield name="%{#attr.property1 + 'reasonName'}" id="%{#attr.property1 + 'reasonName'}" maxlength="80" readonly="%{!#attr.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td class="label"><tags:label key="MSG.group.reason" required="true" /> </td>
        <td class="text">
            <%--<s:select name="%{#attr.property1 + 'reasonType'}" id="%{#attr.property1 + 'reasonType'}"
                      cssClass="txtInputFull" disabled="%{!#attr.toInputData}"
                      list="%{#session.listReasonGroup!=null ? #session.listReasonGroup : {} }"
                      listKey="reasonGroupCode" listValue="reasonGroupName"
                      headerKey="" headerValue="--Chọn nhóm lý do--"/>--%>
            
            <tags:imSelect name="${property1}reasonType" id="${property1}reasonType"
                           cssClass="txtInputFull" disabled="${!toInputData}"
                           headerKey="" headerValue="MSG.LST.024"
                           list="listReasonGroup"
                           listKey="reasonGroupCode" listValue="reasonGroupName"/>

        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.generates.status" required="true" /></td>
        <td class="text">
            <%--<s:select name="%{#attr.property1 + 'reasonStatus'}" id="%{#attr.property1 + 'reasonStatus'}"
                      cssClass="txtInputFull" disabled="%{!#attr.toInputData}"
                      list="#{'1':'Hiệu lực',
                      '0':'Không hiệu lực'}"
                      headerKey="" headerValue="--Chọn trạng thái--"/>--%>

            <tags:imSelect name="${property1}reasonStatus" id="${property1}reasonStatus"
                      cssClass="txtInputFull" disabled="${!toInputData}"
                      list="1:MSG.active,
                      0:MSG.inactive"
                      headerKey="" headerValue="MSG.GOD.189"/>

        </td>
        <td class="label"><tags:label key="MSG.discription" /></td>
        <td class="text" colspan="3">
            <s:textfield name="%{#attr.property1 + 'reasonDescription'}" id="%{#attr.property1 + 'reasonDescription'}" readonly="%{!#attr.toInputData}" maxlength="200" cssClass="txtInputFull"/>
        </td>
    </tr>
</table>
