<%--
    Document   : Card
    Created on : Apr 21, 2009, 3:31:31 PM
    Author     : AnDv 
--%>

<%@tag description="Quản lý thông tin về hạ tầng ASDN/ quản lý Card" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@attribute name="property" description="Ten thuoc tinh cua bean chua doi tuong card" %>
<%@attribute name="toInputData" type="java.lang.Boolean" rtexprvalue="true" description="Form dung de nhap du lieu hay de hien thi, = true de nhap DL, = false de hien thi" %>
<%
            request.setAttribute("contextPath", request.getContextPath());

            if ((property != null) && (!property.trim().equals(""))) {
                request.setAttribute("property1", property + ".");
            } else {
                request.setAttribute("property1", "");
            }

            if (request.getAttribute("lstEquipment") == null) {
                request.setAttribute("lstEquipment", request.getSession().getAttribute("lstEquipment"));
           }
              if (request.getAttribute("lstCardType") == null) {
                request.setAttribute("lstCardType", request.getSession().getAttribute("lstCardType"));
           }

%>

<table class="inputTbl6Col">
    <tr>
        <td class="label"><tags:label key="MSG.card.type" required="true"/>  </td>
        <td class="text">
            <tags:imSelect name= "${pageScope.property}.cardType"
                                  id="cardType"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="MSG.INF.045"
                                  list="lstCardType"
                                  listKey="code" listValue="name"/>

     <%--       <s:select name="%{#attr.property1 + 'cardType'}" id="cardType"
                      cssClass="txtInputFull"  disabled="%{!#attr.toInputData}"
                      list="#request.lstCardType !  = null ? #request.lstCardType : #{}"
                      headerKey="" headerValue="--Chọn loại card--"
                      listKey="code" listValue="name">
            </s:select>--%>
        </td>
        <td class="label"><tags:label key="MSG.card.code" required="true"/> </td>
        <td class="text">
            <s:textfield  name="%{#attr.property1 + 'code'}" id="code" maxlength="30" cssClass="txtInputFull" readonly="%{!#attr.toInputData}"/>
        </td>
        <td class="label"><tags:label key="MSG.card.name" required="true"/> </td>
        <td class="text">
            <s:textfield  name="%{#attr.property1 + 'name'}" id="name" maxlength="50" cssClass="txtInputFull" readonly="%{!#attr.toInputData}"/>
        </td>


    </tr>
    <tr>

        <td class="label"><tags:label key="MSG.device" required="true"/>  </td>
        <td class="text">
            <tags:imSelect name="${pageScope.property}.equipmentId"
                                  id="equipmentId"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="MSG.bras.ChooseDevice"
                                  list="lstEquipment"
                                  listKey="equipmentId" listValue="name"/>
        </td>

        <%--td>Loại Card</td>
        <td>
            <s:textfield  name="%{#attr.property1 + 'cardType'}" id="cardType" maxlength="1" cssClass="txtInput" readonly="%{!#attr.toInputData}"/>
        </td--%>
        <td class="label"><tags:label key="MSG.port.total" /></td>
        <td class="text">
            <s:textfield  name="%{#attr.property1 + 'totalPort'}" id="totalPort" maxlength="5" cssClass="txtInputFull" readonly="%{!#attr.toInputData}"/>
        </td>
        <td class="label"><tags:label key="MSG.generates.status" required="true"/> </td>
        <td class="text">
            <tags:imSelect name="${pageScope.property}.status" id="status"
                              cssClass="txtInputFull" disabled="false"
                              list="1:MSG.GOD.002,0: MSG.GOD.003"
                             
                              headerKey="" headerValue="MSG.GOD.189"/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.discription" /></td>
        <td class="text" colspan="5">
            <s:textarea name="%{#attr.property1 + 'description'}"  id="description" cssClass="txtInputFull"  readonly="%{!#attr.toInputData}"/>
    </tr>

</table>
