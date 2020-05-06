<%-- 
    Document   : ReasonGroup
    Created on : Apr 10, 2009, 11:13:13 AM
    Author     : NamNX
--%>

<%@tag description="Hien thi cac thong tin ve ReasonGroupForm" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@attribute name="property" description="Ten thuoc tinh cua bean chua doi tuong ReasonGroupForm" %>
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
        <td class="label"><tags:label key="MSG.reason.code" required="true" /> </td>
        <td class="text">
            <s:textfield name="%{#attr.property1 + 'reasonGroupCode'}" id="%{#attr.property1 + 'reasonGroupCode'}" maxlength="20" cssClass="txtInputFull"/>
        </td>
        <td class="label"><tags:label key="MSG.LST.020" required="true" /></td>
        <td class="text">
            <s:textfield name="%{#attr.property1 + 'reasonGroupName'}" id="%{#attr.property1 + 'reasonGroupName'}" maxlength="80" readonly="%{!#attr.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td class="label"><tags:label key="MSG.generates.status" required="true" /></td>
        <td class="text">
            <%--<s:select name="%{#attr.property1 + 'status'}" id="%{#attr.property1 + 'status'}"
                      cssClass="txtInputFull" disabled="%{!#attr.toInputData}"
                      list="#{'1':'Hiệu lực',
                      '0':'Không hiệu lực'}"
                      headerKey="" headerValue="--Chọn trạng thái--"/>--%>
            <tags:imSelect name="${property1}status" id="${property1}status"
                           cssClass="txtInputFull" disabled="false"
                           listKey="abc" listValue=""
                           headerKey="" headerValue="MSG.GOD.189"
                           list="1:MSG.GOD.002, 0:MSG.GOD.003"/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.discription" /></td>
        <td class="text" colspan="5" >
            <s:textarea name="%{#attr.property1 + 'description'}" id="%{#attr.property1 + 'description'}" readonly="%{!#attr.toInputData}" cssClass="txtInputFull"/>
        </td>
    </tr>
</table>
