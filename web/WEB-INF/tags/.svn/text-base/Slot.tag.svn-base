<%--
    Document   : Slot
    Created on : Apr 21, 2009, 3:31:31 PM
    Author     : AnDv
--%>

<%@tag description="Quản lý thông tin về hạ tầng ASDN/ quản lý Slot" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@attribute name="property" description="Ten thuoc tinh cua bean chua doi tuong dslamForm" %>
<%@attribute name="toInputData" type="java.lang.Boolean" rtexprvalue="true" description="Form dung de nhap du lieu hay de hien thi, = true de nhap DL, = false de hien thi" %>
<%
        request.setAttribute("contextPath", request.getContextPath());

        if ((property != null) && (!property.trim().equals(""))) {
            request.setAttribute("property1", property + ".");
        } else {
            request.setAttribute("property1", "");
        }
%>

    <div>
        <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgParam" type="key" />
    </div>
    <table class="inputTbl6Col">
        <tr>
           
            <td class="label"><tags:label key="MSG.slot.type" required="true"/></td>
            <td class="text">
                <s:select  name="%{#attr.property1 + 'slotType'}" id="slotType"
                           cssClass="txtInputFull" disabled="%{!#attr.toInputData}"
                           list="#request.lstSlotType != null? #request.lstSlotType :#{}"
                           headerKey="" headerValue="---Select Slot Type---"
                           onchange="changeSlotType(this.value)"
                           listKey="code" listValue="name">
                </s:select>
            </td>
            <td class="label"><tags:label key="MSG.position.slot" required="true"/></td>
            <td class="text">
                <s:textfield  name="%{#attr.property1 + 'slotPosition'}" id="slotPosition" maxlength="5" cssClass="txtInputFull" readonly="%{!#attr.toInputData}"/>
            </td>

            
            <td class="label"><tags:label key="MSG.status" required="true"/></td>
            <td class="text">
                <s:select name="%{#attr.property1 + 'status'}" id="status"
                          cssClass="txtInputFull" disabled="%{!#attr.toInputData}"
                          list="#request.lstSlotStatus != null? #request.lstSlotStatus :#{}"
                              headerKey="" headerValue="--Select Slot Status--"
                              listKey="code" listValue="name"/>
            </td>
        </tr>
        <tr>
             <td class="label"><tags:label key="MSG.card" required="true"/></td>
            <td class="text">
                <s:select  name="%{#attr.property1 + 'cardId'}" id="cardId"
                           cssClass="txtInputFull" disabled="%{!#attr.toInputData}"
                           list="#request.lstCard != null? #request.lstCard :#{}"
                           headerKey="" headerValue="---Select Card---"
                           onchange="changeCard(this.value)"
                           listKey="cardId" listValue="code">
                </s:select>
            </td>

            <td class="label"><tags:label key="MSG.port.total" required="true"/></td>
            <td class="text">
<!--                <s_:textfield  name="%{#attr.property1 + 'maxPort'}" id="maxPort" cssClass="txtInputFull" readonly="%{!#attr.toInputData}" maxLength="5"/>-->
                <s:textfield  name="%{#attr.property1 + 'maxPort'}" id="maxPort" cssClass="txtInputFull" readonly="true" maxLength="5"/>
            </td>

            <td class="label"><tags:label key="MSG.position.first.port" required="true"/></td>
            <td class="text">
                <s:textfield  name="%{#attr.property1 + 'staPortPosition'}" id="staPortPosition" maxlength="1" cssClass="txtInputFull" readonly="%{!#attr.toInputData}"/>
            </td>
        </tr>
        <%--tr>
            <td class="label"><tags:label key="MSG.total.sport.use" /></td>
            <td class="text">
                <s:textfield  name="%{#attr.property1 + 'usedPort'}" id="usedPort" cssClass="txtInputFull" readonly="%{!#attr.toInputData}" maxLength="5"/>
            </td>
            <td class="label"><tags:label key="MSG.space.of.sport" /></td>
            <td class="text">
                <s:textfield  name="%{#attr.property1 + 'freePort'}" id="freePort" maxlength="5"cssClass="txtInputFull" readonly="%{!#attr.toInputData}"/>
            </td>
            <td class="label"><tags:label key="MSG.total.sport.wrong" /></td>
            <td class="text">
                <s:textfield name="%{#attr.property1 + 'invalidPort'}" id="invaildPort"cssClass="txtInputFull" readonly="%{!#attr.toInputData}" maxLength="5"/>
            </td>
        </tr>
        <tr>
            <td class="label"><tags:label key="MSG.vlan_start" /></td>
            <td class="text">
                <s:textfield  name="%{#attr.property1 + 'vlanStart'}" id="vlanStart" maxlength="5"cssClass="txtInputFull" readonly="%{!#attr.toInputData}"/>
            </td>
            <td class="label"><tags:label key="MSG.vlan_stop" /></td>
            <td class="text">
                <s:textfield name="%{#attr.property1 + 'vlanStop'}" id="vlanStop"cssClass="txtInputFull" readonly="%{!#attr.toInputData}" maxLength="5"/>
            </td>

            <td class="label"><tags:label key="MSG.setup.date" /></td>
            <td class="text">
                <tags:dateChooser property="${property1}cardInstalledDate"  styleClass="txtInputFull" id="cardInstalledDate" />
            </td>

        </tr--%>
    </table>
