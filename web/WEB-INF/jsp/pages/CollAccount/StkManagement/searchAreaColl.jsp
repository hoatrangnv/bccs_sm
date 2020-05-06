<%-- 
    Document   : searchAreaColl
    Created on : Nov 3, 2009, 3:39:23 PM
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


<s:if test="#request.typeId ==2">
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
                                   elementNeedClearContent="collAccountManagmentForm.staffManageCode;collAccountManagmentForm.staffManageName"
                                   getNameMethod="getNameShop"
                                   roleList="accountManagementf9Shop"/>
                </td>                
                <td class="label"><tags:label key="MSG.SIK.015" required="true"/></td>
                <td class="text">                    
                    <tags:imSelect name="collAccountManagmentForm.channelTypeId"
                                   id="channelTypeId"
                                   cssClass="txtInputFull"
                                   theme="simple"
                                   headerKey="" headerValue="MSG.SIK.260"
                                   list="lstChannelTypeCol" listKey="channelTypeId" listValue="name"
                                   onchange="selectChannelTypeStaff()"/>
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
                <td class="label"><tags:label key="MSG.SIK.018"/></td>
                <td class="text"  style="width:60%">
                    <s:textfield cssClass="txtInputFull" name="collAccountManagmentForm.isdnSearch" id="isdnSearch" maxLength="15"  theme="simple"/>
                </td>                
                <td colspan="2">&nbsp;</td>
            </tr>
            <tr>
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
                <%--td class="label"><tags:label key="MSG.SIK.074"/></td>
                <td class="text">                    
                    <tags:imSelect name="collAccountManagmentForm.accountStatus"
                                   id="accountStatus"
                                   cssClass="txtInputFull"
                                   theme="simple"
                                   headerKey="" headerValue="MSG.SIK.252"
                                   list="1:Active,0:Locked,2:Cancelled"
                                   />
                </td--%>
            </tr>
            <tr>
                <td class="text" colspan="4" align="center" theme="simple">
                    <tags:submit id="searchButton" confirm="false" formId="collAccountManagmentForm"
                                 showLoadingText="true" targets="searchArea" value="MSG.SIK.115" preAction="CollAccountManagmentAction!searchColl.do"  validateFunction="checkValidFields()"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" class="text" align="center" theme="simple">
                    <tags:displayResult id="searchMsgClient" property="searchMsg"/>
                </td>
            </tr>
        </sx:div>
    </table>
</s:if>
<s:else>
    <s:hidden name="collAccountManagmentForm.typeSearch" id="typeSearch"/>
    <s:hidden name="collAccountManagmentForm.staffManageCode" id="collAccountManagmentForm.staffManageCode"/>
    <s:hidden name="collAccountManagmentForm.staffManageName" id="collAccountManagmentForm.staffManageName"/>
    <div>
        <tags:displayResult id="displayResultMsg" property="message" propertyValue="paramMsg" type="key" />
    </div>
    <table class="inputTbl6Col" style="width:100%">
        <sx:div id="displayShopAcc">
            <tr>
                <td style="width: 10%"></td>
                <td style="width: 17%"></td>
                <td style="width: 20%"></td>
                <td style="width: 20%"></td>
                <td style="width: 10%"></td>
                <td></td>
            </tr>
            <tr>
                <%--<td class="label">Đơn vị </td>--%>
                <td class="label"><tags:label key="MSG.SIK.229"/></td>
                <td colspan="3">
                    <tags:imSearch codeVariable="collAccountManagmentForm.shopcode" nameVariable="collAccountManagmentForm.shopName"
                                   codeLabel="MSG.SIK.099" nameLabel="MSG.SIK.230"
                                   searchClassName="com.viettel.im.database.DAO.CollAccountManagmentDAO"
                                   searchMethodName="getListShop"
                                   elementNeedClearContent="collAccountManagmentForm.shopSelectCode;collAccountManagmentForm.shopSelectName"
                                   getNameMethod="getNameShop"
                                   roleList="accountManagementf9Shop"/>
                </td>

                <td class="label"><tags:label key="MSG.SIK.015" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="collAccountManagmentForm.channelTypeId"
                                   id="channelTypeId"
                                   cssClass="txtInputFull"
                                   theme="simple"
                                   headerKey="" headerValue="MSG.SIK.260"
                                   list="lstChannelTypeCol" listKey="channelTypeId" listValue="name"
                                   onchange="selectChannelTypeStaff()"/>
                </td>                
            </tr>
            <tr>                
                <td class="label"><tags:label key="MSG.SIK.229"/></td>
                <td colspan="3">
                    <tags:imSearch codeVariable="collAccountManagmentForm.shopSelectCode" nameVariable="collAccountManagmentForm.shopSelectName"
                                   codeLabel="MSG.SIK.240" nameLabel="MSG.SIK.241"
                                   searchClassName="com.viettel.im.database.DAO.CollAccountManagmentDAO"
                                   searchMethodName="getListShopSelect"
                                   elementNeedClearContent=""
                                   otherParam="collAccountManagmentForm.shopcode;channelTypeId"
                                   getNameMethod="getNameShopSelect"
                                   roleList=""/>
                </td>
                <%--td class="label"><tags:label key="MSG.SIK.074"/></td>
                <td class="text">                    
                    <tags:imSelect name="collAccountManagmentForm.accountStatus"
                                   id="accountStatus"
                                   cssClass="txtInputFull"
                                   theme="simple"
                                   headerKey="" headerValue="MSG.SIK.252"
                                   list="1:Active,0:Locked,2:Cancelled"
                                   />
                </td--%>
                <td class="label"><tags:label key="MSG.SIK.018"/></td>
                <td class="text"  colspan="1">
                    <s:textfield cssClass="txtInputFull" name="collAccountManagmentForm.isdnSearch" id="isdnSearch" maxLength="12"  theme="simple"/>
                </td>
                 <td colspan="2">&nbsp;</td>
            </tr>            
        </div>
        <tr>
            <td class="text" colspan="8" align="center" theme="simple">
                <tags:submit id="searchButton" confirm="false" formId="collAccountManagmentForm"
                             showLoadingText="true" targets="searchArea" value="MSG.SIK.115" preAction="CollAccountManagmentAction!searchColl.do"  validateFunction="checkValidFields()"/>
            </td>
        </tr>
        <tr>
            <td colspan="8" class="text" align="center" theme="simple">
                <tags:displayResult id="searchMsgClient" property="searchMsg"/>
            </td>
        </tr>
    </table>
</sx:div>
</s:else>





