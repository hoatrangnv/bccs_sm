<%-- 
    Document   : CableBox
    Created on : Apr 16, 2009, 11:08:05 AM
    Author     : TheTm
--%>

<%@tag description="Hien thi cac thong tin ve cap nhanh " pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@attribute name="property" description="Ten thuoc tinh cua bean chua doi tuong CableBoxForm" %>
<%@attribute name="readOnly"%>

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
                <td class="label">Mã cáp nhánh <s:if test="#attr.readOnly!='true'">(<font color="red">*</font>)</s:if></td>
                <td class="text">
                    <s:textfield name="%{#request.property1}code" id="%{#request.property1}code" maxlength="100" readonly="%{#request.readOnly}" cssClass="txtInputFull"/>
                </td>
                <td class="label">Tên cáp nhánh <s:if test="#attr.readOnly!='true'">(<font color="red">*</font>)</s:if></td>
                <td class="text">
                    <s:textfield name="%{#request.property1}name" id="%{#request.property1}name" maxlength="100" readonly="%{#request.readOnly}" cssClass="txtInputFull"/>
                </td>

                <td class="label"> Mã Dslam</td>
                <td class="text">
                    <s:if test="#request.dslamId == null ||  #request.dslamId  * 1 <= 0 ">
                        <s:textfield name="%{#request.property1}dslamcode" id="%{#request.property1}dslamcode" maxlength="80" cssClass="txtInputFull" readonly="true"/>
                    </s:if>
                    <s:else>
                        <s:textfield name="%{#request.property1}dslamcode" id="%{#request.property1}dslamcode" maxlength="80" cssClass="txtInputFull" readonly="true" value = "%{#request.dslamCode}"/>
                    </s:else>
                </td>
            </tr>           
            <tr>
                <td class="label">Số Port tối đa <s:if test="#attr.readOnly!='true'">(<font color="red">*</font>)</s:if></td>
                <td class="text">
                    <s:textfield name="%{#request.property1}maxPorts" id="%{#request.property1}maxPorts" maxlength="100" readonly="%{#request.readOnly}" cssClass="txtInputFull"/>
                </td>
                <td class="label">Số Port sử dụng <s:if test="#attr.readOnly!='true'">(<font color="red">*</font>)</s:if></td>
                <td class="text">
                    <s:textfield name="%{#request.property1}usedPorts" id="%{#request.property1}usedPorts" maxlength="100" readonly="%{#request.readOnly}" cssClass="txtInputFull"/>
                </td>
                <td class="label">Số Port dự trữ<s:if test="#attr.readOnly!='true'"></s:if></td>
                <td class="text">
                    <s:textfield name="%{#request.property1}reservedPort" id="%{#request.property1}reservedPort" maxlength="100" readonly="%{#request.readOnly}" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td class="label">Tọa độ X<s:if test="#attr.readOnly!='true'"></s:if></td>
                <td class="text">
                    <s:textfield name="%{#request.property1}x" id="%{#request.property1}x" maxlength="100" readonly="%{#request.readOnly}" cssClass="txtInputFull"/>
                </td>
                <td class="label">Tọa độ Y<s:if test="#attr.readOnly!='true'"></s:if></td>
                <td class="text">
                    <s:textfield name="%{#request.property1+'y'}" id="%{#request.property1+'y'}" maxlength="100" readonly="%{#request.readOnly}" cssClass="txtInputFull"/>
                </td>

                <td class="label">Trạng thái <s:if test="#attr.readOnly!='true'">(<font color="red">*</font>)</s:if></td>
                <td class="text">
                    <s:select name="%{#request.property1}status" id="%{#request.property1}status" disabled="%{#request.readOnly}"
                              cssClass="txtInputFull"
                              list="#{'1':'Hiệu lực','0':'Không hiệu lực'}"
                              headerKey="" headerValue="--Chọn trạng thái--"/>
                </td>                
            </tr>
            <tr>
                <td class="label">Địa chỉ <s:if test="#attr.readOnly!='true'">(<font color="red">*</font>)</s:if></td>
                <td class ="text" colspan="5">
                     <s:textarea rows="2" name="%{#request.property1}address" id="address" maxlength="150" readonly="%{#request.readOnly}" cssClass="txtInputFull"/>
                </td>
            </tr>
            
        </table> 
