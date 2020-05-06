<%--
    Document   : Boards
    Created on : Apr 16, 2009, 8:42:28 AM
    Author     : Namnt
--%>

<%@tag description="Hien thi cac thong tin ve BoardsForm" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@attribute name="property" description="Ten thuoc tinh cua bean chua doi tuong BoardsForm" %>
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
<%
            if (request.getAttribute("listProvince") == null) {
                request.setAttribute("listProvince", request.getSession().getAttribute("listProvince"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

<table class="inputTbl6Col">       
    <tr>
        <!--            Mã cáp tổng-->
        <td class="label"> <tags:label key="MSG.board.CableCode" required="true"/> </td>
        <td class="text">
            <s:textfield name="%{#request.property1}code" id="code" maxlength="80" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>

        </td>

        <!--            <td class="label"> Tên cáp tổng  </td>-->
        <td class="label"> <tags:label key="MSG.board.CableName" required="true"/> </td>
        <td class="text">
            <s:textfield name="%{#request.property1}name" id="name" maxlength="80" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>

        <!--            <td class="label"> Mã DSLAM </td>-->
        <td class="text"> <tags:label key="MSG.dslam.code" required="true"/> </td>
        <s:if test="#request.dslamId == null ||  #request.dslamId  * 1 <= 0 ">
            <s:textfield name="%{#request.property1}dslamcode'" id="dslamcode" maxlength="80" cssClass="txtInputFull" disabled="true" />
        </s:if>
        <s:else>
            <s:textfield name="%{#request.property1}dslamcode'" id="dslamcode" maxlength="80" cssClass="txtInputFull" disabled="true" value = "%{#request.dslamCode}" />
        </s:else>
        </td>
    </tr>
    <tr>

<!--            <td class="label">Số Port tối đa <s:if test="#attr.readOnly!='true'">(<font color="red">*</font>)</s:if> </td>-->
        <td class="label"> <tags:label key="MSG.limited.sport" required="true"/>  </td>
        <td class="text">  <s:textfield name="%{#request.property1}maxPorts" id="maxPorts" maxlength="80" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>
<!--            <td class="label">Số Port sử dụng <s:if test="#attr.readOnly!='true'">(<font color="red">*</font>)</s:if> </td>-->

        <%--td class="label"> <tags:label key="MSG.total.sport.use" required="true"/>  </td>
        <td class="text">
            <s:textfield name="%{#request.property1}usedPorts" id="usedPorts" maxlength="80" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td--%>

<!--            <td class="label">Số Port dự trữ <s:if test="#attr.readOnly!='false'"></s:if> </td>-->
        <td class="label"> <tags:label key="MSG.total.sport.reserve" required="true"/>  </td>
        <td class="text">
            <s:textfield name="%{#request.property1}reservedPort" id="reservedPort" maxlength="80" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>

        <td class="label"> <tags:label key="MSG.status" required="true"/> </td>
        <td class="text">
            
            <s:select name="%{#request.property1}status" id="status" disabled="%{#request.toInputData}"
                      cssClass="txtInputFull"
                      list="#{'1':'Active','0':'Inactive'}"
                              headerKey="" headerValue="--Select Status--"/>
<!--                      list="1:MSG.GOD.002, 0:MSG.GOD.003"
                      headerKey="" headerValue="MSG.LST.807"/>-->
            
        </td>

    </tr>
    <tr>

<!--        <td class="label">Tọa độ X <s:if test="#attr.readOnly!='true'"></s:if> </td>-->
        <%--td class="label"> <tags:label key="MSG.coordinates.Y" required="false"/> </td>
        <td class="text">
            <s:textfield name="%{#request.property1}x" id="x" maxlength="80" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>

<!--        <td class="label">Tọa độ Y <s:if test="#attr.readOnly!='true'"></s:if> </td>-->
        <td class="label"> <tags:label key="MSG.coordinates.X" required="false"/> </td>
        <td class="text">
            <s:textfield name="%{#request.property1}y" id="y" maxlength="80" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td--%>

<!--        <td class="label">Trạng thái<s:if test="#attr.readOnly!='true'"> (<font color="red">*</font>)</s:if></td>-->

    </tr>
    <tr>
<!--        <td class="label">Địa chỉ <s:if test="#attr.readOnly!='true'">(<font color="red">*</font>)</s:if></td>-->
        <td class="label"> <tags:label key="MSG.address" required="false"/> </td>
        <td class ="text" colspan="5">
            <s:textarea rows="2" name="%{#request.property1}address" id="address" maxlength="150" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
        </td>
    </tr>
</table>

