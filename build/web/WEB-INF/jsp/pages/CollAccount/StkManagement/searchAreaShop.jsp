<%-- 
    Document   : searchAreaShop
    Created on : Nov 3, 2009, 3:46:13 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="abc">
    <div>
        <tags:displayResult id="displayResultMsg" property="message" propertyValue="paramMsg" type="key" />
    </div>
    <table class="inputTbl8Col" style="width:100%">
        <tr>
            <td class="label">Đơn vị</td>
            <td class="text">
                <s:select name="collAccountManagmentForm.shopId"
                          id="shopId"
                          cssClass="txtInputFull"
                          headerKey="" headerValue="--Chọn đơn vị--"
                          listKey="shopId" listValue="name"
                          list="%{#request.listShop!= null ? #request.listShop : #{} }"
                          onchange="updateCombo('shopId','channelTypeId','CollAccountManagmentAction!getChannelType.do');"
                          />
                <script>$('shopId').focus();</script>
            </td>
            <td class="label">Loại đối tượng</td>
            <td class="text">
                <s:select name="collAccountManagmentForm.channelTypeId"
                          id="channelTypeId"
                          cssClass="txtInputFull"
                          headerKey="" headerValue="--Chọn loại đối tượng--"
                          listKey="staffId" listValue="name"
                          list="%{#request.listChannelType != null ? #request.listChannelType : #{} }"
                          onchange="selectChannelTypeShop()"/>
            </td>
            <td class="label">Đối tượng</td>
            <td class="text">
                <s:select name="collAccountManagmentForm.shopSelect"
                          id="shopSelect"
                          cssClass="txtInputFull"
                          headerKey="" headerValue="--Chọn nhân viên quản lý--"
                          listKey="staffId" listValue="name"
                          list="%{#request.listshopSelect != null ? #request.listshopSelect : #{} }"/>
            </td>
            <td class="label">Trạng thái TK</td>
            <td class="text">
                <s:select name="collAccountManagmentForm.accountStatus"
                          id="accountStatus"
                          cssClass="txtInputFull"
                          headerKey="" headerValue="--Trạng thái tài khoản--"
                          list="#{'1':'Đã kích hoạt','0':'Chưa kích hoạt','2':'Tạm ngưng'}"/>
            </td>
        </tr>
        <div style="height:10px">
        </div>
        <tr>
            <td class="text" colspan="8" align="center" >
                <tags:submit id="searchButton" confirm="false" formId="collAccountManagmentForm"
                             showLoadingText="true" targets="searchArea" value="Tìm kiếm" preAction="CollAccountManagmentAction!searchColl.do"  validateFunction="checkValidFields()"/>
            </td>
        </tr>
        <tr>
            <td colspan="8" class="text" align="center">
                <tags:displayResult id="searchMsgClient" property="searchMsg"/>
            </td>
        </tr>
    </table>
</tags:imPanel>
