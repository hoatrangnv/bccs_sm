<%-- 
    Document   : SearchStaffAP
    Created on : Jul 30, 2010, 9:38:46 AM
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
            String pageId = request.getParameter("pageId");
            request.setAttribute("typeId", request.getSession().getAttribute("typeId" + pageId));
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
                           roleList="f9ShopChangeInfoStaffAP"/>
        </td>
        <%--<td style="width:10%">Đối tượng </td>--%>
        <td class="label" style="width:10%" ><tags:label key="MES.CHL.058" /></td>
        <td style="width:25%">
 <%--           <s:select name="addStaffCodeCTVDBForm.searchPointOfSale"
                      id="searchPointOfSale"
                      cssClass="txtInputFull"
                      theme="simple"                      
                      list="#{'1':'CTV_AP','2':'CTVDM_AP'}"
                      />--%>
            <tags:imSelect name="addStaffCodeCTVDBForm.searchPointOfSale"
                           id="searchPointOfSale"
                           cssClass="txtInputFull"
                           theme="simple"
                           headerKey="" headerValue="COM.CHL.002"
                           list="1:MES.CHL.018,2:MES.CHL.019"
                           />
        </td>
        <tags:submit formId="addStaffCodeCTVDBForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="searchArea"
                     value="MES.CHL.086"
                     validateFunction="checkValidFields()"
                     preAction="changeInformationStaffAction!findStaffAP.do"/>
    </tr>
    <tr>
        <%--<td >NV quản lý</td>--%>
        <td class="label" ><tags:label key="MES.CHL.087" /></td>
        <td >
            <tags:imSearch codeVariable="addStaffCodeCTVDBForm.staffMagCodeSearch" nameVariable="addStaffCodeCTVDBForm.staffMagNameSearch"
                           codeLabel="Mã nhân viên quản lý" nameLabel="Tên nhân viên quản lý"
                           searchClassName="com.viettel.im.database.DAO.CollAccountManagmentDAO"
                           searchMethodName="getListStaffManage"
                           otherParam="addStaffCodeCTVDBForm.shopCodeSearch"
                           getNameMethod="getNameStaff"
                           roleList="f9StaffManagementChangeInfoStaffAP"/>
        </td>
        <%--<td >Mã CTV_AP </td>--%>
        <td class="label" ><tags:label key="MES.CHL.074" /></td>
        <td >
            <s:textfield name="addStaffCodeCTVDBForm.ownercode" id="ownercode" maxlength="100" cssClass="txtInputFull"/>
        </td>
    </tr>
</table>