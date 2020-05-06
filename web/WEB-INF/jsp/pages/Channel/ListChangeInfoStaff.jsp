<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListChangeInfoStaff
    Created on : Jun 16, 2010, 4:42:24 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%
    String pageId = request.getParameter("pageId");
    if (request.getAttribute("listStaff") == null) {
        request.setAttribute("listStaff", request.getSession().getAttribute("listStaff" + pageId));
    }
    request.setAttribute("contextPath", request.getContextPath());
    request.setAttribute("typeId", request.getSession().getAttribute("typeId" + pageId));
    request.setAttribute("flag", request.getSession().getAttribute("flag" + pageId));
    request.setAttribute("changeStatus", request.getSession().getAttribute("changeStatus" + pageId));
    request.setAttribute("changeInfo", request.getSession().getAttribute("changeInfo" + pageId));
    request.setAttribute("lstChannelTypeWallet", request.getSession().getAttribute("lstChannelTypeWallet" + pageId));
    request.setAttribute("listChannelTypeAgree", request.getSession().getAttribute("listChannelTypeAgree" + pageId));

    //request.setAttribute("listProfileState", request.getSession().getAttribute("listProfileState" + pageId));
    //request.setAttribute("listBoardState", request.getSession().getAttribute("listBoardState" + pageId));
    //request.setAttribute("listStaffStatus", request.getSession().getAttribute("listStaffStatus" + pageId));
    //request.setAttribute("listCheckVatStatus", request.getSession().getAttribute("listCheckVatStatus" + pageId));
    //request.setAttribute("listAgentType", request.getSession().getAttribute("listAgentType" + pageId));



    request.setAttribute("listProfileState", request.getSession().getAttribute("LIST_PROFILE_STATE_STATUS"));
    request.setAttribute("listBoardState", request.getSession().getAttribute("LIST_BOARD_STATE_STATUS"));
    request.setAttribute("listCheckVatStatus", request.getSession().getAttribute("LIST_STAFF_CHECK_VAT_STATUS"));
    request.setAttribute("listAgentType", request.getSession().getAttribute("LIST_STAFF_AGENT_TYPE_STATUS"));

    request.setAttribute("listStatusNotCanncel", request.getSession().getAttribute("LIST_CHANNEL_STATUS_NOT_CANCEL_STATUS"));
    //request.setAttribute("listStatus", request.getSession().getAttribute("LIST_CHANNEL_STATUS_SEARCH_STATUS"));

    request.setAttribute("roleUpdateChannel", request.getSession().getAttribute("ROLE_UPDATE_CHANNEL" + pageId));
    request.setAttribute("roleCancelChannel", request.getSession().getAttribute("ROLE_CANCEL_CHANNEL" + pageId));
    request.setAttribute("roleAgreeChannel", request.getSession().getAttribute("ROLE_AGREE_CHANNEL" + pageId));
%>
<div style="width:100%">
    <s:hidden name="AddStaffCodeCTVDBForm.staffId" id="AddStaffCodeCTVDBForm.staffId"/>
    <display:table id="coll" name="listStaff" class="dataTable" pagesize="10"
                   targets="searchArea" requestURI="changeInformationStaffAction!selectPage.do"
                   cellpadding="0" cellspacing="0">
        <display:column  title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" headerClass="tct" style="width:20px;text-align:center">
            <s:property escapeJavaScript="true"  value="%{#attr.coll_rowNum}"/>
        </display:column>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.055'))}" property="staffCode" style="width:130px;text-align:left"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.056'))}" property="name" style="width:240px;text-align:left"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.015'))}" property="shopCode" style="width:130px;text-align:center"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.057'))}" property="idNo" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.058'))}" property="nameChannelType" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.059'))}" property="nameManagement" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.060'))}" property="statusName" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.061'))}" sortable="false" headerClass="tct" style="width:20px;text-align:center">
            <div align="center">
                <s:url action="changeInformationStaffAction!clickStaff" id="URL" encode="true" escapeAmp="false">
                    <s:param name="staffId" value="#attr.coll.staffId"/>                    
                </s:url>
                <sx:a targets="searchArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                    <%--<img src="${contextPath}/share/img/accept.png" title="Cập nhật/Sửa" alt="Sửa"/>--%>
                    <img src="${contextPath}/share/img/accept.png" title="<s:text name="MES.CHL.061"/>"  alt="<s:text name="MES.CHL.062"/>"/>
                </sx:a>
            </div>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.150'))}" sortable="false" headerClass="tct" style="width:80px;text-align:center">
            <div align="center">                
                <s:url action="changeInformationStaffAction!showLogAccountAgent" id="URLView" encode="true" escapeAmp="false">
                    <s:param name="accountId" value="#attr.coll.staffId"/>
                </s:url>
                <a href="javascript: void(0);" onclick="openPopup('<s:property escapeJavaScript="true"  value="#URLView"/>', 800, 700)">
                    ${fn:escapeXml(imDef:imGetText('MES.CHL.138'))}
                </a>
            </div>
        </display:column>
        <display:footer> <tr> <td colspan="11" style="color:green">
                    <s:text name="MSG.totalRecord"/>
                    <s:property escapeJavaScript="true"  value="%{#request.listStaff.size()}" /></td> <tr> </display:footer>
            </display:table>

    <fieldset class="imFieldset">
        <legend>
            <s:text name="MES.CHL.063"/>
            <!--            $_{imDef:imGetText('MES.CHL.063')}-->
        </legend>
        <div>
            <tags:displayResult id="displayResultMsgUpdate" property="messageUpdate" propertyValue="paramMsg" type="key" />
        </div>
        <table class="inputTbl8Col">
            <sx:div id="displayParamDetail">
                <tr>
                    <%--<td class="label">Mã cửa hàng </td>--%>
                    <td class="label"><tags:label key="MES.CHL.015" required="true"/></td>
                    <td class="text" colspan="1">
                        <s:hidden name="AddStaffCodeCTVDBForm.parentShopId" id="parentShopId"/>
                        <s:hidden name="AddStaffCodeCTVDBForm.oldShopId" id="oldShopId"/>
                        <s:hidden name="AddStaffCodeCTVDBForm.oldStaffId" id="oldStaffId"/>
                        <s:hidden name="AddStaffCodeCTVDBForm.oldStaffOwnerId" id="oldStaffOwnerId"/>
                        <tags:imSearch codeVariable="AddStaffCodeCTVDBForm.shopCode"
                                       nameVariable="AddStaffCodeCTVDBForm.shopName"
                                       codeLabel="MES.CHL.015" nameLabel="MES.CHL.016"
                                       readOnly="${fn:escapeXml(sessionScope.isEditShop)}"
                                       elementNeedClearContent="AddStaffCodeCTVDBForm.staffManagementCode;AddStaffCodeCTVDBForm.staffManagementName"
                                       searchClassName="com.viettel.im.database.DAO.ChangeInformationStaffDAO"
                                       searchMethodName="getListShopSameLevel"
                                       getNameMethod="getListShopSameLevel"
                                       roleList="saleTransManagementf9Shop"
                                       />
                    </td>
                    <%--</td>
                    <s:textfield theme="simple" name="AddStaffCodeCTVDBForm.shopCode" id="shopCode" maxlength="14" cssClass="txtInputFull" readonly="true"/>
                    <td class="text" colspan="2">
                        <s:textfield theme="simple" name="AddStaffCodeCTVDBForm.shopName" id="shopName" maxlength="100" cssClass="txtInputFull" readonly="true"/>
                    </td>--%>
                    <%--<td class="label">NVQL (<font color="red">*</font>)</td>--%>
                    <td class="label"><tags:label key="MES.CHL.059" required="true"/></td>
                    <td colspan="3">
                        <%--<tags:imSearch codeVariable="AddStaffCodeCTVDBForm.staffManagementCode" nameVariable="AddStaffCodeCTVDBForm.staffManagementName"
                                       codeLabel="MES.CHL.064" nameLabel="MES.CHL.065"
                                       searchClassName="com.viettel.im.database.DAO.ChangeInformationStaffDAO"
                                       otherParam="shopCode"
                                       readOnly="${fn:escapeXml(requestScope.changeInfo)}"
                                       searchMethodName="getListStaffManage"
                                       getNameMethod="getListStaffManageName"
                                       roleList=""/>--%>
                        <tags:imSearch codeVariable="AddStaffCodeCTVDBForm.staffManagementCode" nameVariable="AddStaffCodeCTVDBForm.staffManagementName"
                                       codeLabel="MES.CHL.064" nameLabel="MES.CHL.065"
                                       searchClassName="com.viettel.im.database.DAO.ChangeInformationStaffDAO"
                                       otherParam="AddStaffCodeCTVDBForm.shopCode"
                                       readOnly="${fn:escapeXml(sessionScope.isEditStaff)}"
                                       searchMethodName="getListStaffManage"
                                       getNameMethod="getListStaffManage"
                                       roleList=""/>
                    </td>
                </tr>
                <tr>
                    <%--<td class="label">Mã ĐB/NVĐB </td>--%>
                    <td class="label"><tags:label key="MES.CHL.027" /></td>
                    <td class="text">
                        <s:textfield name="AddStaffCodeCTVDBForm.staffCode" id="staffCode" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <td class="label"><tags:label key="MES.CHL.027" /></td>
                    <td class="text" colspan="1">
                        <s:textfield name="AddStaffCodeCTVDBForm.staffName" id="staffName" theme="simple" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MES.CHL.082" required="false"/><%--Ngày sinh--%></td>
                    <td class="text">
                        <tags:dateChooser property="AddStaffCodeCTVDBForm.birthday" id="birthday" styleClass="txtInput"  insertFrame="false"/>
                    </td>
                    <td class="label"><tags:label key="MES.CHL.060" required="true"/></td>
                    <td class="text">
                        <s:if test="AddStaffCodeCTVDBForm.status != 0 ">
                            <tags:imSelect name="AddStaffCodeCTVDBForm.status"
                                           id="status"
                                           cssClass="txtInputFull"
                                           theme="simple" headerKey="" headerValue="label.option"
                                           list="listStatusNotCanncel" listKey="code" listValue="name"
                                           />
                        </s:if>
                        <s:else>
                            <s:hidden name="AddStaffCodeCTVDBForm.status"/>
                            <tags:imSelect name="AddStaffCodeCTVDBForm.status"
                                           id="status" disabled="true"
                                           cssClass="txtInputFull"
                                           theme="simple" headerKey="" headerValue="label.option"
                                           list="listStatus" listKey="code" listValue="name"
                                           />
                        </s:else>
                    </td>
                </tr>
                <tr>
                    <%--<td class="label">Số CMT (<font color="red">*</font>)</td>--%>
                    <td class="label"><tags:label key="MES.CHL.057" required="true"/></td>
                    <td class="text">
                        <s:textfield name="AddStaffCodeCTVDBForm.idNo" theme="simple" id="idNo" maxlength="19" cssClass="txtInputFull" />
                    </td>
                    <%--<td class="label">Nơi cấp (<font color="red">*</font>)</td>--%>
                    <td class="label"><tags:label key="MES.CHL.066" required="true"/></td>
                    <td class="text">
                        <s:textfield name="AddStaffCodeCTVDBForm.makeAddress" theme="simple" id="makeAddress" maxlength="100" cssClass="txtInputFull" />
                    </td>
                    <%--<td class="label">Ngày Cấp (<font color="red">*</font>)</td>--%>
                    <td class="label"><tags:label key="MES.CHL.067" required="true"/></td>
                    <td class="text">
                        <tags:dateChooser property="AddStaffCodeCTVDBForm.makeDate"  id="makeDate"  styleClass="txtInput"  insertFrame="false"/>
                    </td>

                    <%--<td class="label"><tags:label key="Regedit date" required="true"/></td>
                    <td class="text">
                        <tags:dateChooser property="AddStaffCodeCTVDBForm.registryDate"  id="registryDate"  styleClass="txtInput"  insertFrame="false"/>
                    </td>--%>

                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.SIK.020" required="true"/></td>
                    <td colspan="3">
                        <tags:imSearch codeVariable="AddStaffCodeCTVDBForm.provinceCode" nameVariable="AddStaffCodeCTVDBForm.provinceName"
                                       codeLabel="MSG.SIK.021" nameLabel="MSG.SIK.022"
                                       searchClassName="com.viettel.im.database.DAO.AccountAnyPayFPTManagementDAO"
                                       elementNeedClearContent="AddStaffCodeCTVDBForm.districtCode;AddStaffCodeCTVDBForm.districtName;AddStaffCodeCTVDBForm.precinctCode;AddStaffCodeCTVDBForm.precinctName"
                                       searchMethodName="getProvinceList"
                                       getNameMethod="getProvinceName" postAction="changeArea()"
                                       roleList=""/>
                    </td>

                    <td class="label"><tags:label key="MSG.SIK.023" required="true"/></td>
                    <td colspan="3">
                        <tags:imSearch codeVariable="AddStaffCodeCTVDBForm.districtCode" nameVariable="AddStaffCodeCTVDBForm.districtName"
                                       codeLabel="MSG.SIK.024" nameLabel="MSG.SIK.025"
                                       searchClassName="com.viettel.im.database.DAO.AccountAnyPayFPTManagementDAO"
                                       searchMethodName="getDistrictList"
                                       elementNeedClearContent="AddStaffCodeCTVDBForm.precinctCode;AddStaffCodeCTVDBForm.precinctName"
                                       otherParam="AddStaffCodeCTVDBForm.provinceCode"
                                       getNameMethod="getDistrictName" postAction="changeArea()"
                                       roleList=""/>
                    </td>
                </tr>
                <tr>
                    <%--<td>Phường/Xã (<font color="red">*</font>)</td>--%>
                    <td class="label"><tags:label key="MSG.SIK.026" required="true"/></td>
                    <td colspan="3">
                        <tags:imSearch codeVariable="AddStaffCodeCTVDBForm.precinctCode" nameVariable="AddStaffCodeCTVDBForm.precinctName"
                                       codeLabel="MSG.SIK.027" nameLabel="MSG.SIK.028"
                                       searchClassName="com.viettel.im.database.DAO.AccountAnyPayFPTManagementDAO"
                                       searchMethodName="getWardList"
                                       otherParam="AddStaffCodeCTVDBForm.districtCode;AddStaffCodeCTVDBForm.provinceCode"
                                       getNameMethod="getWardName" postAction="changeArea()"
                                       roleList=""/>
                    </td>
                    <!-- haint41 11/02/2012 : them thong tin to,doi,so nha -->
                    <td class="label"><tags:label key="MES.CHL.176"/></td>
                    <td>
                        <s:textfield name="AddStaffCodeCTVDBForm.streetBlockName" theme="simple" id="streetBlockName"  maxlength="50" cssClass="txtInputFull" onchange="changeArea()"/>
                    </td>
                    <td class="label"><tags:label key="MES.CHL.177"/></td>
                    <td>
                        <s:textfield name="AddStaffCodeCTVDBForm.streetName" theme="simple" id="streetName" maxlength="50" cssClass="txtInputFull" onchange="changeArea()"/>
                    </td>                    
                </tr>
                <tr>
                    <td class="label"><tags:label key="MES.CHL.178"/></td>
                    <td colspan="3">
                        <s:textfield name="AddStaffCodeCTVDBForm.home" theme="simple" id="home" maxlength="50" cssClass="txtInputFull" onchange="changeArea()"/>
                    </td>
                    <%--<td class="label">Địa chỉ (<font color="red">*</font>)</td>--%>
                    <td class="label"><tags:label key="MES.CHL.070" required="false"/></td>
                    <td class="text" colspan="3">
                        <s:textfield name="AddStaffCodeCTVDBForm.address" theme="simple" id="address" maxlength="100" cssClass="txtInputFull" />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="L.100006" required="false"/><%--Độ rộng mặt bằng--%></td>
                    <td class="text" colspan="1">
                        <s:textfield name="AddStaffCodeCTVDBForm.usedfulWidth" theme="simple" id="usedfulWidth" maxlength="100" cssClass="txtInputFull" />
                    </td>
                    <td class="label"><tags:label key="L.100007" required="false"/><%--Diện tích sử dụng--%></td>
                    <td class="text" colspan="1">
                        <s:textfield name="AddStaffCodeCTVDBForm.surfaceArea" theme="simple" id="surfaceArea" maxlength="100" cssClass="txtInputFull" />
                    </td>
                    <td class="label"><tags:label key="L.100013" required="false"/><%--Tên giao dịch--%></td>
                    <td class="text" colspan="1">
                        <s:textfield name="AddStaffCodeCTVDBForm.tradeName" theme="simple" id="tradeName" maxlength="100" cssClass="txtInputFull" />
                    </td>
                    <td class="label"><tags:label key="L.100014" required="false"/><%--Người liên hệ--%></td>
                    <td class="text" colspan="1">
                        <s:textfield name="AddStaffCodeCTVDBForm.contactName" theme="simple" id="contactName" maxlength="100" cssClass="txtInputFull" />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="label.contact.phone"/><%--Điện thoại liên hệ--%></td>
                    <td class="text">
                        <s:textfield name="AddStaffCodeCTVDBForm.tel" theme="simple" id="tel" maxlength="15" cssClass="txtInputFull" />
                    </td>
                    <td class="label"><tags:label key="label.sign.date.contract" required="false"/><%--Ngày ký hợp đồng--%></td>
                    <td class="text">
                        <tags:dateChooser property="AddStaffCodeCTVDBForm.registryDate" id="registryDate" styleClass="txtInput" insertFrame="false"/>
                    </td>

                    <td class="label"><tags:label key="L.100008" required="false"/></td>
                    <td class="text">
                        <tags:imSelect name="AddStaffCodeCTVDBForm.profileState"
                                       id="profileState"
                                       cssClass="txtInputFull"
                                       theme="simple" headerKey="" headerValue="label.option"
                                       list="listProfileState" listKey="code" listValue="name"
                                       />
                    </td>

                    <td class="label"><tags:label key="L.100010" required="false"/></td>
                    <td class="text">
                        <tags:imSelect name="AddStaffCodeCTVDBForm.boardState"
                                       id="boardState"
                                       cssClass="txtInputFull"
                                       theme="simple" headerKey="" headerValue="label.option"
                                       list="listBoardState" listKey="code" listValue="name"
                                       />

                    </td>
                </tr>
                <tr>
                    <%--<s:if test="#request.enableVatAgentType != null">--%>
                    <td class="label"><tags:label key="label.check.vat" required="false"/><%--Có thuế/không thuế--%></td>
                    <td class="text">
                        <tags:imSelect name="AddStaffCodeCTVDBForm.checkVat"
                                       id="checkVat"
                                       cssClass="txtInputFull"
                                       theme="simple" headerKey="" headerValue="label.option"
                                       list="listCheckVatStatus" listKey="code" listValue="name"
                                       />
                    </td>
                    <td class="label"><tags:label key="label.agent.type" required="false"/><%--Loại đại lý--%></td>
                    <td>
                        <tags:imSelect name="AddStaffCodeCTVDBForm.agentType"
                                       id="agentType"
                                       cssClass="txtInputFull"
                                       theme="simple" headerKey="" headerValue="label.option"
                                       list="listAgentType" listKey="code" listValue="name"
                                       />
                    </td>
                    <%--</s:if>--%>

                    <td class="label"><tags:label key="MSG.note" required="false"/><%--Note--%></td>
                    <td class="text">
                        <s:textfield name="AddStaffCodeCTVDBForm.note" theme="simple" id="note" maxlength="500" cssClass="txtInputFull" />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.Note.BTS" required="true"/></td>
                    <td class="text" colspan="3">
                        <tags:imSearch codeVariable="AddStaffCodeCTVDBForm.btsCode"
                                       nameVariable="AddStaffCodeCTVDBForm.fullAddress"
                                       codeLabel="BTS.Bts_Code" nameLabel="BTS.Bts_Name"
                                       searchClassName="com.viettel.im.database.DAO.ChangeInformationStaffDAO"
                                       otherParam="AddStaffCodeCTVDBForm.provinceCode;AddStaffCodeCTVDBForm.districtCode"                          
                                       searchMethodName="getListBTS"
                                       getNameMethod="getListBTS"
                                       roleList=""/>
                    </td>
                    <td class="label"><label style="color: red">Emola channel</label></td>
                    <td>
                        <tags:imSelect name="AddStaffCodeCTVDBForm.lastUpdateKey" theme="simple"
                                       id="lastUpdateKey"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="label.option"
                                       list="Emola:Emola"/>
                    </td>
                    <td class="label"><tags:label key="MSG.Regist.Point" required="false"/><%--Regist Point--%></td>
                    <td>
                        <tags:imSelect name="AddStaffCodeCTVDBForm.registrationPoint" theme="simple"
                                       id="registrationPoint"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="label.option"
                                       list="RA:Registration Point"/>
                    </td>
                </tr>
                <tr>
                    <s:if test="AddStaffCodeCTVDBForm.channelWallet == null ||  AddStaffCodeCTVDBForm.channelWallet == ''">
                        <%--Chon loai kenh vi dien tu--%>
                        <td class="label"><tags:label key="label.channel.wallet"/></td>
                        <td>
                            <tags:imSelect name="AddStaffCodeCTVDBForm.channelWallet"
                                           id="channelWallet"
                                           cssClass="txtInputFull"
                                           theme="simple" headerKey="" headerValue="label.option"
                                           list="lstChannelTypeWallet" listKey="code" listValue="name"
                                           />
                        </td>
                        <%--So dien thoai vi--%>
                        <td class="label"><tags:label key="label.isdn.wallet"/></td>
                        <td class="text">
                            <s:textfield name="AddStaffCodeCTVDBForm.isdnWallet" theme="simple" id="addStaffCodeCTVDBForm.isdnWallet" maxlength="10" cssClass="txtInputFull"/>
                        </td>
                        <%--Kênh ví cha--%>
                        <td class="label"><tags:label key="label.channel.wallet.parent"/></td>
                        <td class="text" colspan="1">
                            <tags:imSearch codeVariable="AddStaffCodeCTVDBForm.parentCodeWallet"
                                           nameVariable="AddStaffCodeCTVDBForm.parentNameWallet"
                                           codeLabel="MSG.stock.staff.code" nameLabel="MSG.staff.name"
                                           searchClassName="com.viettel.im.database.DAO.ChangeInformationStaffDAO"
                                           otherParam="AddStaffCodeCTVDBForm.shopCode;channelWallet"
                                           searchMethodName="getListStaffManageWallet"
                                           getNameMethod="getListStaffManageWallet"
                                           />
                        </td>
                    </s:if>
                    <s:else>
                        <%--Chon loai kenh vi dien tu--%>
                        <s:if test="#session.ChangeISDNChannelWallet == 1">
                            <td class="label"><tags:label key="label.channel.wallet"/></td>
                            <td>
                                <tags:imSelect name="AddStaffCodeCTVDBForm.channelWallet"
                                               id="channelWallet"
                                               cssClass="txtInputFull"
                                               theme="simple" headerKey="" headerValue="label.option"
                                               list="lstChannelTypeWallet" listKey="code" listValue="name"
                                               />
                            </td>
                        </s:if>
                        <s:else>
                            <td class="label"><tags:label key="label.channel.wallet"/></td>
                            <td>
                                <tags:imSelect name="AddStaffCodeCTVDBForm.channelWallet"
                                               id="channelWallet"
                                               cssClass="txtInputFull"
                                               theme="simple" headerKey="" headerValue="label.option"
                                               list="lstChannelTypeWallet" listKey="code" listValue="name" disabled="true"
                                               />
                            </td>
                        </s:else>
                        <%--So dien thoai vi--%>
                        <s:if test="#session.ChangeISDNChannelWallet == 1">
                            <td class="label"><tags:label key="label.isdn.wallet"/></td>
                            <td class="text">
                                <s:textfield name="AddStaffCodeCTVDBForm.isdnWallet" theme="simple" id="addStaffCodeCTVDBForm.isdnWallet" maxlength="10" cssClass="txtInputFull"/>
                            </td>
                        </s:if>
                        <s:else>
                            <td class="label"><tags:label key="label.isdn.wallet"/></td>
                            <td class="text">
                                <s:textfield name="AddStaffCodeCTVDBForm.isdnWallet" theme="simple" id="addStaffCodeCTVDBForm.isdnWallet" maxlength="15" cssClass="txtInputFull" />
                            </td>
                        </s:else>
                        <%--Kênh ví cha--%>
                        <s:if test="#session.ChangeISDNChannelWallet == 1">
                            <td class="label"><tags:label key="label.channel.wallet.parent"/></td>
                            <td class="text" colspan="1">
                                <tags:imSearch codeVariable="AddStaffCodeCTVDBForm.parentCodeWallet"
                                               nameVariable="AddStaffCodeCTVDBForm.parentNameWallet"
                                               codeLabel="MSG.stock.staff.code" nameLabel="MSG.staff.name"
                                               searchClassName="com.viettel.im.database.DAO.ChangeInformationStaffDAO"
                                               otherParam="AddStaffCodeCTVDBForm.shopCode;channelWallet"
                                               searchMethodName="getListStaffManageWallet"
                                               getNameMethod="getListStaffManageWallet"
                                               />
                            </td>
                        </s:if>
                        <s:else>
                            <td class="label"><tags:label key="label.channel.wallet.parent"/></td>
                            <td class="text" colspan="1">
                                <tags:imSearch codeVariable="AddStaffCodeCTVDBForm.parentCodeWallet"
                                               nameVariable="AddStaffCodeCTVDBForm.parentNameWallet"
                                               codeLabel="MSG.stock.staff.code" nameLabel="MSG.staff.name"
                                               searchClassName="com.viettel.im.database.DAO.ChangeInformationStaffDAO"
                                               otherParam="AddStaffCodeCTVDBForm.shopCode;channelWallet"
                                               searchMethodName="getListStaffManageWallet"
                                               getNameMethod="getListStaffManageWallet" readOnly="true"
                                               />
                            </td>
                        </s:else>    
                    </s:else>
                </tr>
                <!--tannh them voucher code--> 
                <%--   <tr>
                       <td class="label">Voucher Code</td>
                       <td class="text">
                            <s:textfield name="AddStaffCodeCTVDBForm.voucherCode" theme="simple" id="addStaffCodeCTVDBForm.voucherCode" maxlength="15" cssClass="txtInputFull"  />
                       </td>
                  </tr> --%>
                <!--LamNT Upload file of channel-->

                <tr>
                    <td class="label">Convert Channel</td>
                    <td>
                        <tags:imSelect name="AddStaffCodeCTVDBForm.converChanel" theme="simple"
                                       id="idConvertChannel"
                                       cssClass="txtInputFull" disabled="${fn:escapeXml(sessionScope.convertChannelType)}" 
                                       headerKey="" headerValue="label.option"
                                       list="1000801:Fix Door to door Channel,1000487:Point of Sales Channel,10:Door to Door Channel,1000641:Vip Channel,1000541:FBB Channel,204068:Staff movitel sales,1000400:Agent channel,12:Street Sales channel,1000721:Merchant Channel"/>
                    </td>
                </tr>

                <tr>
                    <!--Neu la kenh dac biet thi hien thi-->
                    <%--<s:if test="#session.KenhDacBiet == 1">--%>
                    <s:form action="addStaffCodeCTVDBForm!imageUrl" theme="simple" method="post" id="exportStockForm">
                    <table class="inputTbl8Col" style="width:100% " >
                        <tr>
                            <td  style="width:10%"><tags:label key="MSG.generates.file_data" required="true"/></td>

                            <td style="width:30px">
                                <s:if test="#session.KenhDacBiet == 1">
                                    <s:url id="fileguide" namespace="/" action="myDownloadChangInfoStaff!myDownload" ></s:url>
                                    <s:a href="%{fileguide}">Download</s:a>  
                                </s:if>
                            </td>
                            <td id="tagsImFileUpload" class="text" colspan="7" style="width:230px" >
                                <tags:imFileUpload name="AddStaffCodeCTVDBForm.imageUrl" 
                                                   id="imageUrl" 
                                                   serverFileName="AddStaffCodeCTVDBForm.imageUrl"
                                                   cssStyle="width:500px;"/>
                            </td>
                            <td id="tdTextContract" style="width:20% "><tags:label key="MSG.generates.agree" required="true" /></td>
                            <td id="tdListCheckContract" class="text" colspan="3" style="width:13.5%">
                                <tags:imSelect name="AddStaffCodeCTVDBForm.isChecked" 
                                               id="isChecked"
                                               cssClass="txtInputFull" 
                                               theme="simple" headerKey="" headerValue="label.option"
                                               list="listChannelTypeAgree" listKey="code" listValue="name" 
                                               >  Agree </tags:imSelect>

                                </td>
                            </tr> 
                        </table>
                </s:form>


                <%--</s:if>--%>
                </tr>
            </sx:div>
        </table>

        <div style="height:10px">
        </div>
        <div align="center" style="padding-bottom:5px;">
            <td class="text" colspan="8" align="center" theme="simple">
                <s:if test="AddStaffCodeCTVDBForm.status != 0 ">
                    <!-- disabled="$_{requestScope.changeStatus}"-->
                    <tags:submit id="update" formId="addStaffCodeCTVDBForm"
                                 showLoadingText="true" targets="searchArea" value="MES.CHL.071"
                                 cssStyle="width:60px;"
                                 disabled="${fn:escapeXml(!requestScope.roleUpdateChannel)}"
                                 validateFunction="checkupdate()"
                                 confirm="true" confirmText="MES.CHL.072"
                                 preAction="changeInformationStaffAction!updateInfo.do"
                                 />

                </s:if>
                <s:else>
                    <tags:submit id="update" formId="addStaffCodeCTVDBForm"
                                 showLoadingText="true" targets="searchArea" value="MES.CHL.071"
                                 cssStyle="width:60px;"
                                 disabled="true"
                                 validateFunction="checkupdate()"
                                 confirm="true" confirmText="MES.CHL.072"
                                 preAction="changeInformationStaffAction!updateInfo.do"
                                 />
                </s:else>

            </td>
            <td class="text" colspan="8" align="center" theme="simple">
                <s:if test="AddStaffCodeCTVDBForm.status != 0 ">
                    <!--                    disabled="$_{requestScope.changeStatus}"-->
                    <tags:submit id="cancel" confirm="false" formId="addStaffCodeCTVDBForm"
                                 cssStyle="width:60px;"                             
                                 disabled="${fn:escapeXml(!requestScope.roleUpdateChannel)}"
                                 showLoadingText="true" targets="searchArea" value="MES.CHL.073"
                                 preAction="changeInformationStaffAction!cancel.do"
                                 />
                </s:if>
                <s:else>
                    <tags:submit id="cancel" confirm="false" formId="addStaffCodeCTVDBForm"
                                 cssStyle="width:60px;"                             
                                 disabled="true"
                                 showLoadingText="true" targets="searchArea" value="MES.CHL.073"
                                 preAction="changeInformationStaffAction!cancel.do"
                                 />
                </s:else>
            </td>
            <td class="text" colspan="8" align="center" theme="simple">
                <s:if test="AddStaffCodeCTVDBForm.status != 0 ">
                    <!--                    disabled="$_{requestScope.changeStatus}" -->
                    <tags:submit id="destroy" formId="addStaffCodeCTVDBForm"
                                 cssStyle="width:60px;"
                                 disabled="${fn:escapeXml(!requestScope.roleCancelChannel)}"
                                 confirm="true" confirmText="MSG.destroy.channel"
                                 showLoadingText="true" targets="searchArea" value="MSG.destroy"
                                 preAction="changeInformationStaffAction!destroy.do"
                                 />
                </s:if>
                <s:else>
                    <tags:submit id="destroy" formId="addStaffCodeCTVDBForm"
                                 cssStyle="width:60px;"
                                 disabled="true" 
                                 confirm="true" confirmText="MSG.destroy.channel"
                                 showLoadingText="true" targets="searchArea" value="MSG.destroy"
                                 preAction="changeInformationStaffAction!destroy.do"
                                 />
                </s:else>
            </td>
            <!--LinhNBV start modified on May 21 2018: Hide button Change Isdn Wallet-->
            <%--
           <td class="text" colspan="8" align="center" theme="simple">
              
               <s:if test="#session.ChangeISDNChannelWallet == 1">
                   <tags:submit id="updateIsdn" formId="addStaffCodeCTVDBForm"
                                showLoadingText="true" targets="searchArea" value="MES.Change.ISDN"
                                cssStyle="width:100px;"
                                disabled="false"
                                validateFunction="checkupdateISDN()"
                                confirm="true" confirmText="MSG.Confirm.Change.Phone.Wallet"
                                preAction="changeInformationStaffAction!updateISDNChannelWallet.do"
                                />
               </s:if>
               <s:else>
                   <tags:submit id="updateIsdn" formId="addStaffCodeCTVDBForm"
                                showLoadingText="true" targets="searchArea" value="MES.Change.ISDN"
                                cssStyle="width:100px;"
                                disabled="true"
                                validateFunction="checkupdateISDN()"
                                confirm="true" confirmText="MSG.Confirm.Change.Phone.Wallet"
                                preAction="changeInformationStaffAction!updateISDNChannelWallet.do"
                                />
               </s:else>
           </td>--%>
            <!--LinhNBV end.-->
            <td class="text" colspan="8" align="center" theme="simple">
                <s:if test="#session.ChangeISDNChannelWallet == 1">
                    <tags:submit id="editChannelWallet" formId="addStaffCodeCTVDBForm"
                                 showLoadingText="true" targets="searchArea" value="MES.Change.Channel.Wallet"
                                 cssStyle="width:150px;"
                                 disabled="false"
                                 validateFunction="checkChannelWallet()"
                                 confirm="true" confirmText="MSG.Change.Channel.Wallet"
                                 preAction="changeInformationStaffAction!changeChannelWallet.do"
                                 />
                </s:if>
                <s:else>
                    <tags:submit id="editChannelWallet" formId="addStaffCodeCTVDBForm"
                                 showLoadingText="true" targets="searchArea" value="MES.Change.Channel.Wallet"
                                 cssStyle="width:150px;"
                                 disabled="true"
                                 validateFunction="checkChannelWallet()"
                                 confirm="true" confirmText="MSG.Change.Channel.Wallet"
                                 preAction="changeInformationStaffAction!changeChannelWallet.do"
                                 />
                </s:else>
            </td>  
            <td class="text" colspan="8" align="center" theme="simple">
                <s:if test="AddStaffCodeCTVDBForm.status != 0 ">
                    <tags:submit id="converChanel" formId="addStaffCodeCTVDBForm"
                                 showLoadingText="true" targets="searchArea" value="Convert Channel"
                                 cssStyle="width:100px;"
                                 disabled="${fn:escapeXml(sessionScope.convertChannelType)}"
                                 confirm="true" confirmText=" Are you sure to convert channel ?"
                                 preAction="changeInformationStaffAction!convertChannel.do"
                                 />

                </s:if>
                <s:else>
                    <tags:submit id="converChanel" formId="addStaffCodeCTVDBForm"
                                 showLoadingText="true" targets="searchArea" value="Convert Channel"
                                 cssStyle="width:100px;"
                                 disabled="true"
                                 confirm="true" confirmText=" Are you sure to convert channel ?"
                                 preAction="changeInformationStaffAction!convertChannel.do"
                                 />

                </s:else>

            </td>
        </div>
    </fieldset>

</div>

<script language="javascript">
                    checkupdate = function() {
                        var staffName = document.getElementById("staffName");
                        if (trim(staffName.value).length == 0) {
                            $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.012')"/>';
                            staffName.focus();
                            return false;
                        }
                        if (isHtmlTagFormat(trim(staffName.value))) {
    <%--$('displayResultMsgUpdate').innerHTML="Tên CTV/ĐB không được nhập định dạng thẻ HTML"--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.013')"/>';
                staffName.focus();
                return false;
            }
            if (staffName.value == null || staffName.value == "") {
    <%--$('displayResultMsgUpdate' ).innerHTML='Bạn phải nhập tên CTV/ĐB';--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.012')"/>';
                staffName.focus();
                return false;
            }
            if (staffName.value == 'MÃ CHƯA CẬP NHẬT THÔNG TIN') {
    <%--$('displayResultMsgUpdate' ).innerHTML='Bạn không được để tên CTV/ĐB mặc định';--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.014')"/>';
                staffName.focus();
                return false;
            }
            var staffManagementCode = document.getElementById("AddStaffCodeCTVDBForm.staffManagementCode");
            if (trim(staffManagementCode.value).length == 0) {
    <%--$('displayResultMsgUpdate').innerHTML="Bạn phải chọn mã nhân viên quản lý"--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.015')"/>';
                staffManagementCode.focus();
                return false;
            }
            if (staffManagementCode.value == null || staffManagementCode.value == "") {
    <%--$('displayResultMsgUpdate' ).innerHTML='Bạn phải chọn mã nhân viên quản lý';--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.015')"/>';
                staffManagementCode.focus();
                return false;
            }

            /*check ngày sinh*/
            var birthday = dojo.widget.byId("birthday");
            if (birthday.domNode.childNodes[1].value != null && birthday.domNode.childNodes[1].value != "" && !isDateFormat(birthday.domNode.childNodes[1].value)) {
    <%--$( 'displayResultMsgUpdate' ).innerHTML='Ngày sinh chưa hợp lệ';--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0001')"/>';
                birthday.domNode.childNodes[1].focus();
                return false;
            }
            /*if(birthday.domNode.childNodes[1].value != null && birthday.domNode.childNodes[1].value != "" && years_between(birthday.domNode.childNodes[1].value, GetDateSys()) < 18){
    <%--$('displayResultMsgUpdate').innerHTML='Bạn nhập ngày sinh không hợp lệ (Số tuổi của NV không được nhỏ hơn 18)';--%>
             $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0006')"/>';
             birthday.domNode.childNodes[1].focus();
             return false;
             }*/
            /*check trạng thái*/
            var status = document.getElementById("status");
            if (status.value == null || status.value == "") {
    <%--$('displayResultMsgUpdate' ).innerHTML='Bạn chưa chọn trạng thái.';--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0007')"/>';
                status.focus();
                return false;
            }

            var idNo = document.getElementById("idNo");
            if (trim(idNo.value).length == 0) {
    <%--$('displayResultMsgUpdate').innerHTML="Bạn phải chọn số CMT"--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.016')"/>';
                idNo.focus();
                return false;
            }
            if (isHtmlTagFormat(trim(idNo.value))) {
    <%--$('displayResultMsgUpdate').innerHTML="Số CMT không được nhập định dạng thẻ HTML"--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.017')"/>';
                idNo.focus();
                return false;
            }
            if (idNo.value == null || idNo.value == "") {
    <%--$('displayResultMsgUpdate' ).innerHTML='Bạn phải chọn số CMT';--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.016')"/>';
                idNo.focus();
                return false;
            }
            var makeAddress = document.getElementById("makeAddress");
            if (trim(makeAddress.value).length == 0) {
    <%--$('displayResultMsgUpdate').innerHTML="Bạn phải nhập địa chỉ nơi cấp"--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.018')"/>';
                makeAddress.focus();
                return false;
            }
            if (isHtmlTagFormat(trim(makeAddress.value))) {
    <%--$('displayResultMsgUpdate').innerHTML="Địa chỉ không được nhập định dạng thẻ HTML"--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.019')"/>';
                makeAddress.focus();
                return false;
            }
            if (makeAddress.value == null || makeAddress.value == "") {
    <%--$('displayResultMsgUpdate' ).innerHTML='Bạn phải nhập địa chỉ nơi cấp';--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.018')"/>';
                makeAddress.focus();
                return false;
            }
            var makeDate = dojo.widget.byId("makeDate");
            if (makeDate.domNode.childNodes[1].value.trim() == "") {
    <%--$( 'displayResultMsgUpdate' ).innerHTML='Bạn phải nhập ngày cấp CMT';--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.020')"/>';
                makeDate.domNode.childNodes[1].focus();
                return false;
            }
            if (compareDates(GetDateSys(), makeDate.domNode.childNodes[1].value)) {
    <%--$('displayResultMsgUpdate').innerHTML='Ngày cấp CMT không được lớn hơn ngày hiện tại';--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.021')"/>';
                makeDate.domNode.childNodes[1].focus();
                return false;
            }
            if (!isDateFormat(makeDate.domNode.childNodes[1].value)) {
    <%--$( 'displayResultMsgUpdate' ).innerHTML='Ngày cấp CMT chưa hợp lệ';--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.022')"/>';
                $('makeDate').focus();
                return false;
            }
            //            var isdn = document.getElementById("isdn");
            //            isdn.value=trim(isdn.value);
            //            if(isdn != null && trim(isdn.value).length > 0 && !isInteger( trim(isdn.value) ) ){
            //    <%--$('displayResultMsgUpdate').innerHTML="Trường Isdn phải là số"--%>
            //                $( 'displayResultMsgUpdate' ).innerHTML='<s_:property value="getText('ERR.CHL.023')"/>';
            //                isdn.focus();
            //                return false;
            //            }


    <%--var pricePolicy = document.getElementById("pricePolicy");
    if (pricePolicy.value ==null || pricePolicy.value ==""){
        $('displayResultMsgUpdate' ).innerHTML='Bạn phải chọn chính sách giá';
        pricePolicy.focus();
        return false;
    }
    var discountPolicy = document.getElementById("discountPolicy");
    if (discountPolicy.value ==null || discountPolicy.value ==""){
        $('displayResultMsgUpdate' ).innerHTML='Bạn phải chọn chính sách triết khấu';
        discountPolicy.focus();
        return false;
    }--%>

            var province = document.getElementById("AddStaffCodeCTVDBForm.provinceCode");
            if (trim(province.value).length == 0) {
    <%--$('displayResultMsgUpdate').innerHTML="Bạn chưa nhập tỉnh/thành phố"--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.SIK.006')"/>';
                province.focus();
                return false;
            }
            var district = document.getElementById("AddStaffCodeCTVDBForm.districtCode");
            if (trim(district.value).length == 0) {
    <%--$('displayResultMsgUpdate').innerHTML="Bạn chưa nhập quận/huyện"--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.SIK.007')"/>';
                district.focus();
                return false;
            }
            var precinct = document.getElementById("AddStaffCodeCTVDBForm.precinctCode");
            if (trim(precinct.value).length == 0) {
    <%--$('displayResultMsgUpdate').innerHTML="Bạn chưa nhập phường/xã"--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.SIK.008')"/>';
                precinct.focus();
                return false;
            }

            var address = document.getElementById("address");
            if (trim(address.value).length == 0) {
    <%--$('displayResultMsgUpdate').innerHTML="Bạn phải nhập địa chỉ"--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.024')"/>';
                address.focus();
                return false;
            }
            if (isHtmlTagFormat(trim(address.value))) {
    <%--$('displayResultMsgUpdate').innerHTML="Số CMT không được nhập định dạng thẻ HTML"--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.017')"/>';
                address.focus();
                return false;
            }
            if (address.value == null || address.value == "") {
    <%--$('displayResultMsgUpdate' ).innerHTML='Bạn phải nhập địa chỉ';--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.024')"/>';
                address.focus();
                return false;
            }

            var usedfulWidth = document.getElementById("usedfulWidth");
            if (usedfulWidth.value != null && usedfulWidth.value != "") {
                if (!isNumeric(trim(usedfulWidth.value))) {
    <%--$( 'displayResultMsgUpdate' ).innerHTML='Độ rộng mặt bằng phải là số';--%>
                    $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0002')"/>';
                    $('usedfulWidth').focus();
                    return false;
                }
                if (isHtmlTagFormat(trim(usedfulWidth.value))) {
                    $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.userfuleWidth.html')"/>';
                    $('usedfulWidth').focus();
                    return false;
                }

            }

            var surfaceArea = document.getElementById("surfaceArea");
            if (surfaceArea.value != null && surfaceArea.value != "") {
                if (!isNumeric(trim(usedfulWidth.value))) {
    <%--$( 'displayResultMsgUpdate' ).innerHTML='Diện tích sử dụng phải là số';--%>
                    $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0003')"/>';
                    $('surfaceArea').focus();
                    return false;
                }
                if (isHtmlTagFormat(trim(surfaceArea.value))) {
                    $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.surfaceArea.html')"/>';
                    $('surfaceArea').focus();
                    return false;
                }
            }

            var tel = document.getElementById("tel");
            if (tel.value != null && tel.value != "") {
                if (!isInteger(tel.value)) {
    <%--$( 'displayResultMsgUpdate' ).innerHTML='Số điện thoại liên hệ chỉ được bao gồm các ký tự số';--%>
                    $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0004')"/>';
                    $('tel').focus();
                    return false;
                }
            }
            var isdnWallet = document.getElementById("addStaffCodeCTVDBForm.isdnWallet");
            if (isdnWallet.value != null && isdnWallet.value != "") {
                if (!isInteger(trim(isdnWallet.value))) {
    <%--$( 'displayResultMsgUpdate' ).innerHTML='ISDN ví phải là số';--%>
                    $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.ISDN.WALLET.ISNUMBER')"/>';
                    isdnWallet.focus();
                    return false;
                }
            }

            var registryDate = dojo.widget.byId("registryDate");
            if (registryDate.domNode.childNodes[1].value != null && registryDate.domNode.childNodes[1].value != "" && !isDateFormat(registryDate.domNode.childNodes[1].value)) {
    <%--$( 'displayResultMsgUpdate' ).innerHTML='Ngày ký hợp đồng chưa hợp lệ';--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0005')"/>';
                registryDate.domNode.childNodes[1].focus();
                return false;
            }
            if (registryDate.domNode.childNodes[1].value != null && registryDate.domNode.childNodes[1].value != "" && compareDates(GetDateSys(), registryDate.domNode.childNodes[1].value)) {
    <%--$('displayResultMsgUpdate').innerHTML='Ngày ký hợp đồng không được lớn hơn ngày hiện tại';--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0008')"/>';
                registryDate.domNode.childNodes[1].focus();
                return false;
            }

    <%--var agentType = document.getElementById("agentType");
    if (agentType!=null){
        if(agentType.value == null || agentType.value == ""){
            //$( 'displayResultMsgUpdate' ).innerHTML='Chưa chọn phân loại đại lý';
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0038')"/>';
            agentType.focus();
            return false;
        }
    } --%>

            var note = document.getElementById("note");
            if (note.value != null && note.value != "") {
                if (isHtmlTagFormat(trim(note.value))) {
    <%--$('displayResultMsgUpdate').innerHTML="Mô tả không được nhập định dạng thẻ HTML"--%>
                    $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0039')"/>';
                    note.focus();
                    return false;
                }
            }

            var btsCode = document.getElementById("AddStaffCodeCTVDBForm.btsCode");
            if (trim(btsCode.value).length == 0) {
    <%--$('displayResultMsgUpdate').innerHTML="Bạn chưa nhập BTS "--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.00001')"/>';
                btsCode.focus();
                return false;
            }

            var status = document.getElementById("status");
            if (status.value == 2) {
    <s:if test="#session.checkBalanceWallet == 1">
                confirmText = getUnicodeMsg("<s:property value="getText('Confirm.Cancel.Wallet')"/>");
                if (!confirm(confirmText)) {
                    return false;
                }
    </s:if>
            }
    <%--
    if ('${fn:escapeXml(checkBalanceWallet)}' == '0') {
        alert("ok1");
    }
    --%>




            //trim các trường
            $('AddStaffCodeCTVDBForm.shopCode').value = trim($('AddStaffCodeCTVDBForm.shopCode').value);
            $('AddStaffCodeCTVDBForm.staffManagementCode').value = trim($('AddStaffCodeCTVDBForm.staffManagementCode').value);
            $('staffName').value = trim($('staffName').value);
            $('idNo').value = trim($('idNo').value);
            $('makeAddress').value = trim($('makeAddress').value);
            $('AddStaffCodeCTVDBForm.provinceCode').value = trim($('AddStaffCodeCTVDBForm.provinceCode').value);
            $('AddStaffCodeCTVDBForm.districtCode').value = trim($('AddStaffCodeCTVDBForm.districtCode').value);
            $('AddStaffCodeCTVDBForm.precinctCode').value = trim($('AddStaffCodeCTVDBForm.precinctCode').value);
            $('address').value = trim($('address').value);
            $('streetBlockName').value = trim($('streetBlockName').value);
            $('streetName').value = trim($('streetName').value);
            $('home').value = trim($('home').value);
            $('tradeName').value = trim($('tradeName').value);
            $('contactName').value = trim($('contactName').value);
            $('usedfulWidth').value = trim($('usedfulWidth').value);
            $('surfaceArea').value = trim($('surfaceArea').value);
            $('tel').value = trim($('tel').value);
            $('note').value = trim($('note').value);
            return true;
        }

        checkupdateISDN = function() {
            var isdnWallet = document.getElementById("addStaffCodeCTVDBForm.isdnWallet");
            if (trim(isdnWallet.value).length == 0) {
    <%--$('displayResultMsgUpdate').innerHTML="Bạn phải nhập ISDN Wallet"--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.INPUT.ISDN.WALLET')"/>';
                isdnWallet.focus();
                return false;
            }
            if (!isInteger(trim(isdnWallet.value))) {
    <%--$( 'displayResultMsgUpdate' ).innerHTML='ISDN ví phải là số';--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.ISDN.WALLET.ISNUMBER')"/>';
                isdnWallet.focus();
                return false;
            }
            return true;
        }

        checkChannelWallet = function() {
            var channelWallet = document.getElementById("channelWallet");
            if (trim(channelWallet.value).length == 0) {
    <%--$('displayResultMsgUpdate').innerHTML="Bạn phải nhập Channel Wallet"--%>
                $('displayResultMsgUpdate').innerHTML = '<s:property escapeJavaScript="true"  value="getText('err.input.channelWallet')"/>';
                channelWallet.focus();
                return false;
            }

            return true;
        }

        function GetDateSys() {
            var time = new Date();
            var dd = time.getDate();
            var mm = time.getMonth() + 1;
            var yy = time.getFullYear();
            var temp = '';
            if (dd < 10)
                dd = '0' + dd;
            if (mm < 10)
                mm = '0' + mm;
            temp = dd + "/" + mm + "/" + yy;
            return(temp);
        }

        // Tannh : Check role contract correct
        if (${fn:escapeXml(!requestScope.roleAgreeChannel)}) {
            $('tdListCheckContract').style.display = 'none';
            $('tdTextContract').style.display = 'none';
        }
        var imageUrl = document.getElementById('imageUrl').value;
        var e = document.getElementById("isChecked");
        var valueIsChecked = e.options[e.selectedIndex].value;
        if ((imageUrl && imageUrl != '' && imageUrl.length > 0) && valueIsChecked == 1) {
            document.getElementById('imageUrl_1').disabled = true;
            ;
        }
</script>

