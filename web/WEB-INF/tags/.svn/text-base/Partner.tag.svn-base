<%--
    Document   : Partner
    Created on : Mar 19, 2009, 8:42:28 AM
    Author     : Namnx 
--%>

<%@tag description="Hien thi cac thong tin ve PartnerForm" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@attribute name="property" description="Ten thuoc tinh cua bean chua doi tuong PartnerForm" %>
<%@attribute name="toInputData" type="java.lang.Boolean" rtexprvalue="true" description="Form dung de nhap du lieu hay de hien thi, = true de nhap DL, = false de hien thi" %>

<%
            request.setAttribute("contextPath", request.getContextPath());

            if ((property != null) && (!property.trim().equals(""))) {
                request.setAttribute("property1", property + ".");
            } else {
                request.setAttribute("property1", "");
            }
%>


<%--table class="inputTbl6Col" style="width:100%" >
    <tr>
        <td colspan="6" align="center">
            <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="paramMsg" type="key" />
        </td>
    </tr>
    <tr>
        <td class="label">Mã đối tác<font color="red">*</font></td>
        <td class="text">
            <s:textfield name="%{#attr.property1 + 'partnerCode'}" id="%{#attr.property1 + 'partnerCode'}" maxlength="80" readonly="%{!#attr.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td class="label">Tên đối tác<font color="red">*</font></td>
        <td class="text">
            <s:textfield name="%{#attr.property1 + 'partnerName'}" id="%{#attr.property1 + 'partnerName'}" maxlength="80" readonly="%{!#attr.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td class="label">Loại đối tác<font color="red">*</font></td>
        <td>
            <s:select name="%{#attr.property1 + 'partnerType'}" id="%{#attr.property1 + 'partnerType'}"
                      cssClass="txtInputFull" disabled="%{!#attr.toInputData}"
                      list="#{'1':'Bán hàng',
                      '2':'Vendor',
                      '3':'Vận tải'}"
                      headerKey="" headerValue="--Chọn loại đối tác--"/>
        </td>
    </tr>
    <tr>
        <td class="label">Địa chỉ</td>
        <td class="text">
            <s:textfield name="%{#attr.property1 + 'address'}" id="%{#attr.property1 + 'address'}" maxlength="500" readonly="%{!#attr.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td class="label">Điện thoại</td>
        <td class="text">
            <s:textfield name="%{#attr.property1 + 'phone'}" id="%{#attr.property1 + 'phone'}" maxlength="20" readonly="%{!#attr.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td class="label">Fax</td>
        <td class="text">
            <s:textfield name="%{#attr.property1 + 'fax'}" id="%{#attr.property1 + 'fax'}" maxlength="20" readonly="%{!#attr.toInputData}" cssClass="txtInputFull"/>
        </td>
    </tr>
    <tr>
        <td class="label">Trạng thái<font color="red">*</font></td>
        <td class="text">
            <s:select name="%{#attr.property1 + 'status'}" id="%{#attr.property1 + 'status'}"
                      cssClass="txtInputFull" disabled="%{!#attr.toInputData}"
                      list="#{'1':'Hiệu lực',
                      '0':'Không hiệu lực'}"
                      headerKey="" headerValue="--Chọn trạng thái--"/>
        </td>
        <td class="label">Ngày bắt đầu</td>
        <td class="text">
            <tags:dateChooser property="${property1}staDate" id = "${property1}staDate" styleClass="txtInputFull" />
        </td>
        <td class="label">Ngày kết thúc</td>
        <td class="text">
            <tags:dateChooser property="${property1}endDate" id = "${property1}endDate" styleClass="txtInputFull" />
        </td>
    </tr>
</table--%>


<table class="inputTbl6Col" style="width:100%" >
    <tr>
        <td colspan="6" align="center">
            <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="paramMsg" type="key" />
        </td>
    </tr>
    <tr>
        <td class="label">
            <tags:label key="MSG.partner.code" required="true"/>
        </td>
        <td class="text">
            <s:textfield name="%{#attr.property1 + 'partnerCode'}" id="%{#attr.property1 + 'partnerCode'}" maxlength="80" readonly="%{!#attr.toInputData}" cssClass="txtInputFull"/>
            <%--s:textfield name="%{#request.property1 + 'partnerCode'}" id="%{#request.property1 + 'partnerCode'}" maxlength="80" readonly="%{!#attr.toInputData}" cssClass="txtInputFull"/--%>
        </td>
        <td class="label">
            <tags:label key="MSG.partner.name" required="true"/>
        </td>
        <td class="text">
            <s:textfield name="%{#attr.property1 + 'partnerName'}" id="%{#attr.property1 + 'partnerName'}" maxlength="80" readonly="%{!#attr.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td class="label">
            <tags:label key="MSG.partner.type" required="true"/>
        </td>
        <td>
            <tags:imSelect name="${property1}partnerType" id="${property1}partnerType"
                              cssClass="txtInputFull" disabled="false"
                              list="1:MSG.LST.003, 2:MSG.LST.004 ,3:MSG.LST.005"
                              listKey="abc" listValue=""
                              headerKey="" headerValue="MSG.LST.002"/>
        </td>
    </tr>
    <tr>
        <td class="label">
            <tags:label key="MSG.address"/>
        </td>
        <td class="text">
            <s:textfield name="%{#attr.property1 + 'address'}" id="%{#attr.property1 + 'address'}" maxlength="500" readonly="%{!#attr.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td class="label">
            <tags:label key="MSG.phone.number"/>
        </td>
        <td class="text">
            <s:textfield name="%{#attr.property1 + 'phone'}" id="%{#attr.property1 + 'phone'}" maxlength="20" readonly="%{!#attr.toInputData}" cssClass="txtInputFull"/>
        </td>
        <td class="label">
            <tags:label key="MSG.fax"/>
        </td>
        <td class="text">
            <s:textfield name="%{#attr.property1 + 'fax'}" id="%{#attr.property1 + 'fax'}" maxlength="20" readonly="%{!#attr.toInputData}" cssClass="txtInputFull"/>
        </td>
    </tr>
    <tr>
        <td class="label">
            <tags:label key="MSG.status" required="true"/>
        </td>
        <td class="text">
            <tags:imSelect name="${property1}status" id="${property}.status"
                              cssClass="txtInputFull" disabled="false"
                              list="1:MSG.GOD.002, 0:MSG.GOD.003"
                              listKey="abc" listValue=""
                              headerKey="" headerValue="MSG.GOD.189"/>

        </td>
        <td class="label">
            <tags:label key="MSG.date.start"/>
        </td>
        <td class="text">
            <tags:dateChooser property="${property1}staDate" id = "${property1}staDate" styleClass="txtInputFull" />
        </td>
        <td class="label">
            <tags:label key="MSG.date.end"/>
        </td>
        <td class="text">
            <tags:dateChooser property="${property1}endDate" id = "${property1}endDate" styleClass="txtInputFull" />
        </td>
    </tr>
</table>

