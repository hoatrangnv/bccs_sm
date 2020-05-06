<%-- 
    Document   : SearchStaff
    Created on : Jun 16, 2010, 4:31:57 PM
    Author     : User
    Edited by  : ductm6@viettel.com.vn
    Edited on  : 13/03/2012
    Desc       : Thêm mới tính năng Tạo mã kênh

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
    String pageId = request.getParameter("pageId");
    request.setAttribute("typeId", request.getSession().getAttribute("typeId" + pageId));
    /*tutm1 29/02/12 bo sung danh sach kenh & trang thai nhan vien */
    request.setAttribute("listChannelType", request.getSession().getAttribute("listChannelType" + pageId));
    request.setAttribute("listChannelStatusSearch", request.getSession().getAttribute("listChannelStatusSearch" + pageId));
    
    
    request.setAttribute("listStatusNotCanncel", request.getSession().getAttribute("LIST_CHANNEL_STATUS_NOT_CANCEL_STATUS"));
    request.setAttribute("listStatus", request.getSession().getAttribute("listChannelStatusSearch" + pageId));
    
    request.setAttribute("roleUpdateChannel", request.getSession().getAttribute("ROLE_UPDATE_CHANNEL" + pageId));
    
    
%>



<div>
    <tags:displayResult id="displayResultMsg" property="message" propertyValue="paramMsg" type="key" />
</div>
<table class="inputTbl6Col">
    <tr>
        <%--<td style="width:10%; ">Mã cửa hàng<font color="red">*</font></td>--%>
        <td class="label" style="width:10%" ><tags:label key="MES.CHL.015" required="true" /></td>
        <td style="width:50%; " >
            <tags:imSearch codeVariable="addStaffCodeCTVDBForm.shopCodeSearch" nameVariable="addStaffCodeCTVDBForm.shopNameSearch"
                           codeLabel="MES.CHL.015" nameLabel="MES.CHL.016"
                           searchClassName="com.viettel.im.database.DAO.ChangeInformationStaffDAO"
                           elementNeedClearContent="addStaffCodeCTVDBForm.staffMagCodeSearch;addStaffCodeCTVDBForm.staffMagNameSearch"
                           searchMethodName="getListShop"
                           roleList="f9ShopChangeInfoStaff"/>
        </td>
        <%--<td style="width:10%">Đối tượng </td>--%>
        <td class="label" style="width:10%" ><tags:label key="MES.CHL.058" /></td>
        <td style="width:25%">
            <%--<s:select name="addStaffCodeCTVDBForm.searchPointOfSale"
                      id="searchPointOfSale"
                      cssClass="txtInputFull"
                      theme="simple"
                      headerKey="" headerValue="--Chọn đối tượng--"
                      list="#{'1':'Điểm bán','2':'NVĐB'}"
                      />--%>

            <!--            <tags_:imSelect name="addStaffCodeCTVDBForm.searchPointOfSale"
                                       id="searchPointOfSale"
                                       cssClass="txtInputFull"
                                       theme="simple"
                                       headerKey="" headerValue="COM.CHL.002"
                                       list="1:MES.CHL.084,2:MES.CHL.085,3:MES.CHL.152,4:MES.CHL.153"
                                       />-->

            <tags:imSelect name="addStaffCodeCTVDBForm.searchPointOfSale"
                           id="searchPointOfSale"
                           cssClass="txtInputFull"
                           theme="simple"
                           headerKey="" headerValue="label.option"
                           list="listChannelType" listKey="channelTypeId" listValue="name"
                           />
        </td>
    </tr>

    <tr>
        <%--<td >NV quản lý</td>--%>
        <td class="label"><tags:label key="MES.CHL.087" /></td>
        <td >
            <tags:imSearch codeVariable="addStaffCodeCTVDBForm.staffMagCodeSearch" nameVariable="addStaffCodeCTVDBForm.staffMagNameSearch"
                           codeLabel="MES.CHL.088" nameLabel="MES.CHL.089"
                           searchClassName="com.viettel.im.database.DAO.CollAccountManagmentDAO"
                           searchMethodName="getListStaffManage"
                           otherParam="addStaffCodeCTVDBForm.shopCodeSearch"
                           getNameMethod="getNameStaff"
                           roleList="f9StaffManagementChangeInfoStaff"/>
        </td>
        <%--<td >Mã ĐB/NVĐB </td>--%>
        <td class="label"><tags:label key="MES.CHL.027" /></td>
        <td >
            <s:textfield name="addStaffCodeCTVDBForm.ownercode" id="ownercode" maxlength="100" cssClass="txtInputFull"/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.object.type" /></td>
        <td >
            <tags:imSelect name="addStaffCodeCTVDBForm.objectType" id="objectType"
                           cssClass="txtInputFull"
                           theme="simple"
                           headerKey="" headerValue="empty.code.object.type.select"
                           list="1:empty.code.object.type.empty,2:empty.code.object.type.update.info"
                           />
        </td>
        <td class="label"><tags:label key="MSG.status" required="false"/></td>
        <td class="text" colspan="1">
            <%--
            <tags:imSelect name="addStaffCodeCTVDBForm.status" id="status"
                           cssClass="txtInputFull" disabled="false"
                           list="1:label.staff.status.active,
                           0:label.staff.status.cancel,
                           2:label.staff.status.lock"
                           headerKey="" headerValue="MSG.LST.807"/>
            --%>
            <tags:imSelect name="addStaffCodeCTVDBForm.statusSearch"
                           id="statusSearch"
                           cssClass="txtInputFull"
                           theme="simple"
                           headerKey="" headerValue="label.option"
                           list="listStatus" listKey="code" listValue="name"
            />
        </td>
    </tr>
    <tr>
        <td colspan="4" align="center">
            <tags:submit formId="addStaffCodeCTVDBForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="searchArea"
                         value="MES.CHL.086"
                         validateFunction="checkValidFields()"
                         preAction="changeInformationStaffAction!findStaff.do"/>
            <!--DUCTM6_20120313-->
                <%--<tags:submit formId="addStaffCodeCTVDBForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="searchArea"
                         value="MSG.add"
                         validateFunction="checkValidAdd()"
                         preAction="changeInformationStaffAction!prepareAddStaff.do"/>--%>
                <input type="button" value="<s:text name="MSG.add" />" style="width:100px;"
                       onclick="prepareAddStaff()">
        </td>
    </tr>
</table>