<%-- 
    Document   : SaleChannelType
    Created on : Jun 13, 2009, 2:31:53 PM
    Author     : Namnx 
--%>

<%@tag description="Hien thi cac thong tin ve SaleChannelTypeForm" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@attribute name="property" description="Ten thuoc tinh cua bean chua doi tuong SaleChannelTypeForm" %>
<%@attribute name="toInputData" type="java.lang.Boolean" rtexprvalue="true" description="Form dung de nhap du lieu hay de hien thi, = true de nhap DL, = false de hien thi" %>

<%
            request.setAttribute("contextPath", request.getContextPath());

            if ((property != null) && (!property.trim().equals(""))) {
                request.setAttribute("property1", property + ".");
            } else {
                request.setAttribute("property1", "");
            }
%>



<table class="inputTbl6Col" style="width:100%" >
    <tr>
        <td class="text" colspan="6" align="center">
            <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="paramMsg" type="key" />
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.chanel.type.name" required="true" /> </td>
        <td class="text">
            <s:textfield name="%{#attr.property1 + 'name'}" id="%{#attr.property1 + 'name'}" maxlength="80" readonly="%{!#attr.toInputData}" cssClass="txtInputFull" maxLength="50"/>
        </td>
        <td class="label"><tags:label key="MSG.generates.status" required="true" /> </td>
        <td class="text">
            <%--<s:select name="%{#attr.property1 + 'status'}" id="%{#attr.property1 + 'status'}"
                      cssClass="txtInputFull" disabled="%{!#attr.toInputData}"
                      list="#{'1':'Hiệu lực',
                              '0':'Không hiệu lực'}"
                      headerKey="" headerValue="--Chọn trạng thái--"/>--%>
            <tags:imSelect name="${requestScope.property1}status" id="${requestScope.property1}status"
                           cssClass="txtInputFull" disabled="${!pageScope.toInputData}"
                           list="1:MES.CHL.129,0:MES.CHL.130"
                           headerKey="" headerValue="COM.CHL.012"/>
        </td>
        <td class="label"><tags:label key="MSG.chanel.group" required="false" /></td>
        <td class="text">
            <%--<s:select name="%{#attr.property1 + 'objectType'}" id="%{#attr.property1 + 'objectType'}"
                      cssClass="txtInputFull" disabled="%{!#attr.toInputData}"
                      list="#{'1':'Shop',
                              '2':'Staff'}"
                      headerKey="" headerValue="--Chọn nhóm kênh--"/>--%>
            <tags:imSelect name="${requestScope.property1}objectType" id="${requestScope.property1}objectType"
                           cssClass="txtInputFull" disabled="${!pageScope.toInputData}"
                           list="1:Shop,2:Staff"
                           headerKey="" headerValue="COM.CHL.013"/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.unit" required="false" /></td>
        <td class="text">
            <%--<s:select name="%{#attr.property1 + 'isVtUnit'}" id="%{#attr.property1 + 'isVtUnit'}"
                      cssClass="txtInputFull" disabled="%{!#attr.toInputData}"
                      list="#{'1':'Thuộc Viettel',
                              '2':'Không thuộc Viettel'}"
                      headerKey="" headerValue="--Chọn đơn vị--"/>--%>
            <tags:imSelect name="${requestScope.property1}isVtUnit" id="${requestScope.property1}isVtUnit"
                           cssClass="txtInputFull" disabled="${!pageScope.toInputData}"
                           list="1:MES.CHL.146,2:MES.CHL.147"
                           headerKey="" headerValue="COM.CHL.014"/>
        </td>
        <td class="label"><tags:label key="MSG.bonus" required="false" /></td>
        <td class="text">
            <%--<s:select name="%{#attr.property1 + 'checkComm'}" id="%{#attr.property1 + 'checkComm'}"
                      cssClass="txtInputFull" disabled="%{!#attr.toInputData}"
                      list="#{'1':'Tính hoa hồng',
                              '2':'Không tính hoa hồng'}"
                      headerKey="" headerValue="--Chọn tính hoa hồng--"/>--%>
            <tags:imSelect name="${requestScope.property1}checkComm" id="${requestScope.property1}checkComm"
                           cssClass="txtInputFull" disabled="${!pageScope.toInputData}"
                           list="1:MES.CHL.148,2:MES.CHL.149"
                           headerKey="" headerValue="COM.CHL.015"/>
        </td>

        <td class="label"><tags:label key="MSG.export.bill.templace" required="false" /> </td>

        <td class="text">
            <s:textfield name="%{#attr.property1 + 'stockReportTemplate'}" id="%{#attr.property1 + 'stockReportTemplate'}" maxlength="50" readonly="%{!#attr.toInputData}" cssClass="txtInputFull"/>
        </td>
        <%--<td>Loại kho thao tác</td>
        <td class="text">
            <s:select name="%{#attr.property1 + 'stockType'}" id="%{#attr.property1 + 'stockType'}"
                      cssClass="txtInputFull" disabled="%{!#attr.toInputData}"
                      list="#{'1':'Shop',
                      '2':'Staff'}"
                      headerKey="" headerValue="--Chọn loại kho thao tác--"/>
        </td>--%>


    </tr>





    <%--<tr>
        <td colspan="6" align="center">
            <font color="red">
                <s:text name="%{#attr.property1 + 'message'}"/>
            </font>
        </td>

    </tr>--%>
</table>