<%-- 
    Document   : searchResetLimit
    Created on : Jun 23, 2010, 4:47:31 PM
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

<tags:imPanel title="Thông tin tìm kiếm">
    <s:hidden name="collAccountManagmentForm.typeSearch" id="typeSearch"/>
    <div>
        <tags:displayResult id="displayResultMsg" property="message" propertyValue="paramMsg" type="key" />
    </div>
    <table class="inputTbl4Col" style="width:100%" theme="simple">
        <sx:div id="displayCollAcc">
            <tr>
 
                <td class="label"><tags:label key="MSG.SIK.229"/></td>
                <td class="text" style="width:60%">
                    <tags:imSearch codeVariable="collAccountManagmentForm.shopcode" nameVariable="collAccountManagmentForm.shopName"
                                   codeLabel="MSG.SIK.099" nameLabel="MSG.SIK.230"
                                   searchClassName="com.viettel.im.database.DAO.CollAccountManagmentDAO"
                                   searchMethodName="getListShop"
                                   elementNeedClearContent=""
                                   getNameMethod="getNameShop"
                                   roleList="accountManagementf9Shop"/>
                </td>
      
                <td class="label"><tags:label key="MSG.SIK.015" required="true"/></td>
                <td class="text">
    
                    <tags:imSelect name="collAccountManagmentForm.channelTypeId" id="channelTypeId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.SIK.260"
                                   theme="simple"
                                   list="2:MSG.SIK.232,3:MSG.SIK.233"
                                   onchange="selectChannelTypeStaff()"
                                   />
                </td>

            </tr>
            <tr>

                <td class="label"><tags:label key="MSG.SIK.234"/></td>
                <td  class="text"  style="width:60%">
                    <tags:imSearch codeVariable="collAccountManagmentForm.staffManageCode" nameVariable="collAccountManagmentForm.staffManageName"
                                   codeLabel="MSG.SIK.235" nameLabel="MSG.SIK.236"
                                   searchClassName="com.viettel.im.database.DAO.CollAccountManagmentDAO"
                                   searchMethodName="getListStaffManage"
                                   otherParam="collAccountManagmentForm.shopcode"
                                   getNameMethod="getNameStaff"
                                   roleList="accountManagementf9Staff"/>
                </td>
                <%--<td class="label">Số ISDN</td>--%>
                <td class="label"><tags:label key="MSG.SIK.018"/></td>
                <td class="text"  style="width:60%">
                    <s:textfield cssClass="txtInputFull" name="collAccountManagmentForm.isdnSearch" id="isdnSearch" maxLength="12"  theme="simple"/>
                </td>
                <%--<td class="label">
                    Mã CTV
                </td>
                <td class="text">
                    <s:textfield cssClass="txtInputFull" name="collAccountManagmentForm.collCode" id="collCode" maxLength="50"  theme="simple"/>
                </td>--%>
                <td colspan="2">&nbsp;</td>
            </tr>
            <tr>
                <%--<td class="label">Mã CTV</td>--%>
                <td class="label"><tags:label key="MSG.SIK.092"/></td>
                <td  class="text"  style="width:60%">
                    <tags:imSearch codeVariable="collAccountManagmentForm.collCode" nameVariable="collAccountManagmentForm.collName"
                                   codeLabel="MSG.SIK.237" nameLabel="MSG.SIK.238"
                                   searchClassName="com.viettel.im.database.DAO.CollAccountManagmentDAO"
                                   searchMethodName="getListStaff"
                                   otherParam="collAccountManagmentForm.shopcode;collAccountManagmentForm.staffManageCode;channelTypeId"
                                   getNameMethod="getNameStaff"
                                   roleList=""/>
                </td>
                <%--<td class="label">Trạng thái TK</td>--%>
                <td class="label"><tags:label key="MSG.SIK.074"/></td>
                <td class="text">
       
                    <tags:imSelect name="collAccountManagmentForm.accountStatus" id="accountStatus"
                                   cssClass="txtInputFull"
                                   theme="simple"
                                   headerKey="" headerValue="MSG.SIK.252"
                                   list="1:MSG.SIK.197,0:MSG.SIK.077,2:MSG.SIK.078"
                                   />
                </td>

            </tr>
            <tr>
                <td class="text" colspan="4" align="center" theme="simple">
                    <tags:submit id="searchButton" confirm="false" formId="collAccountManagmentForm"
                                 showLoadingText="true" targets="searchArea" value="Tìm kiếm" preAction="CollAccountManagmentAction!searchAccountReset.do"  validateFunction="checkValidFields()"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" class="text" align="center" theme="simple">
                    <tags:displayResult id="searchMsgClient" property="searchMsg"/>
                </td>
            </tr>
        </sx:div>
    </table>
</tags:imPanel>

