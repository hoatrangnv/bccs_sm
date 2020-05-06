<%-- 
    Document   : addNewStaffCTVDB
    Created on : Mar 13, 2012, 2:42:48 PM
    Author     : kdvt_ductm6
    Desc       : Popup tạo mới mã CTV/ĐBz
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
    String pageId = request.getParameter("pageId");

    if (request.getAttribute("lstChannelTypeCol") == null) {
        request.setAttribute("lstChannelTypeCol", request.getSession().getAttribute("lstChannelTypeCol"));
    }
    request.setAttribute("listStaffStatus", request.getSession().getAttribute("listStaffStatus"));
    request.setAttribute("listBoardState", request.getSession().getAttribute("listBoardState"));
    request.setAttribute("listProfileState", request.getSession().getAttribute("listProfileState"));
    request.setAttribute("lstChannelTypeWallet", request.getSession().getAttribute("lstChannelTypeWallet"));

    request.setAttribute("ROLE_CREATE_CHANNEL", request.getSession().getAttribute("ROLE_UPDATE_CHANNEL" + pageId));


    request.setAttribute("listStatusNotCanncel", request.getSession().getAttribute("LIST_CHANNEL_STATUS_NOT_CANCEL_STATUS"));

%>

<s:form action="changeInformationStaffAction" theme="simple" method="post" id="addStaffCodeCTVDBForm">
    <table class="inputTbl8Col">
        <sx:div id="displayStaff">
            <%--<s:hidden name="addStaffCodeCTVDBForm.staffId" id="addStaffCodeCTVDBForm.staffId"/>
            <s:hidden name="addStaffCodeCTVDBForm.statusChannel" id="addStaffCodeCTVDBForm.statusChannel"/>--%>
            <tr>
                <%--<td class="label">Mã cửa hàng </td>--%>
                <td class="label"><tags:label key="MES.CHL.015" /></td>
                <td  colspan="3" >
                    <!--                    readOnly="$_{sessionScope.isEditShop}"-->
                    <tags:imSearch codeVariable="addStaffCodeCTVDBForm.shopCode"
                                   nameVariable="addStaffCodeCTVDBForm.shopName"
                                   codeLabel="MES.CHL.015" nameLabel="MES.CHL.016"                                   
                                   elementNeedClearContent="addStaffCodeCTVDBForm.staffManagementCode;addStaffCodeCTVDBForm.staffManagementName"
                                   searchClassName="com.viettel.im.database.DAO.ChangeInformationStaffDAO"
                                   searchMethodName="getListShop"
                                   getNameMethod="getListShop"
                                   roleList="f9ShopChangeInfoStaff"/>
                </td>
                <td class="label"><tags:label key="MES.CHL.059" required="true"/></td>
                <%--<td class="label">NVQL (<font color="red">*</font>)</td>--%>
                <td colspan="3">
                    <!--                    readOnly="$_{sessionScope.isEditStaff}"-->
                    <tags:imSearch codeVariable="addStaffCodeCTVDBForm.staffManagementCode"
                                   nameVariable="addStaffCodeCTVDBForm.staffManagementName"
                                   codeLabel="MES.CHL.064" nameLabel="MES.CHL.065"
                                   searchClassName="com.viettel.im.database.DAO.ChangeInformationStaffDAO"
                                   otherParam="addStaffCodeCTVDBForm.shopCode"                                   
                                   searchMethodName="getListStaffManage"
                                   getNameMethod="getListStaffManage"
                                   roleList="f9StaffManagementChangeInfoStaff"/>
                </td>
            </tr>
            <tr>
                <%--<td class="label">Loại kênh</td>--%>
                <td class="label"><tags:label key="label.channel.type" required="true" /><%--Loại kênh--%></td>
                <td colspan="3">
                    <div class="text">
                        <tags:imSelect name="addStaffCodeCTVDBForm.channelType"
                                       id="addStaffCodeCTVDBForm.channelType"
                                       headerKey="" headerValue="label.option" cssClass="txtInputFull"
                                       list="lstChannelTypeCol" listKey="channelTypeId" listValue="name" onchange="changeChannelType()"/>
                    </div>
                </td>

                <%--<td class="label">Ngày sinh</td>--%>
                <td class="label"><tags:label key="MES.CHL.082" required="true"/></td>
                <td class="text">
                    <tags:dateChooser property="addStaffCodeCTVDBForm.birthday" id="birthday"
                                      styleClass="txtInput"  insertFrame="false"/>
                </td>

                <%--<td class="label">Trạng thái</td>--%>
                <td class="label"><tags:label key="MES.CHL.060" required="true"/></td>
                <td class="text">
                    <!--                    list="listStaffStatus" listKey="code" listValue="name"-->
                    <tags:imSelect name="addStaffCodeCTVDBForm.status"
                                   id="status" disabled="false"
                                   cssClass="txtInputFull"
                                   theme="simple" headerKey="" headerValue="label.option"                                   
                                   list="listStatusNotCanncel" listKey="code" listValue="name"
                                   />
                </td>
            </tr>
            <tr>
                <%--Ten kênh --%>
                <td class="label"><tags:label key="MES.CHL.120" required="true"/></td>
                <td class="text" colspan="3">
                    <s:textfield name="addStaffCodeCTVDBForm.staffName" id="staffName"
                                 theme="simple" maxlength="100" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MES.CHL.067" required="true"/></td>
                <td class="text">
                    <tags:dateChooser property="addStaffCodeCTVDBForm.makeDate"  id="makeDate"
                                      styleClass="txtInput"  insertFrame="false"/>
                </td>
            </tr>

            <tr>
                <%--Id no--%>
                <td class="label"><tags:label key="MES.CHL.057" required="true"/></td>
                <td class="text">
                    <s:textfield name="addStaffCodeCTVDBForm.idNo" theme="simple" id="idNo"
                                 maxlength="19" cssClass="txtInputFull" />
                </td>
                <%--Place of issue--%>
                <td class="label"><tags:label key="MES.CHL.066" required="true"/></td>
                <td class="text">
                    <s:textfield name="addStaffCodeCTVDBForm.makeAddress"
                                 theme="simple" id="makeAddress" maxlength="100" cssClass="txtInputFull" />
                </td>
                <%--Province--%>
                <td class="label"><tags:label key="MSG.SIK.020" required="true"/></td>
                <td colspan="3">
                    <tags:imSearch codeVariable="addStaffCodeCTVDBForm.provinceCode" nameVariable="addStaffCodeCTVDBForm.provinceName"
                                   codeLabel="MSG.SIK.021" nameLabel="MSG.SIK.022"
                                   readOnly = "true" 
                                   searchClassName="com.viettel.im.database.DAO.AccountAnyPayFPTManagementDAO"
                                   elementNeedClearContent="addStaffCodeCTVDBForm.districtCode;addStaffCodeCTVDBForm.districtName;addStaffCodeCTVDBForm.precinctCode;addStaffCodeCTVDBForm.precinctName"
                                   searchMethodName="getProvinceList" 
                                   getNameMethod="getProvinceName" postAction="changeArea()"
                                   roleList="" />
                </td>
            </tr>
            <tr>
                <%--District--%>
                <td class="label"><tags:label key="MSG.SIK.023" required="true"/></td>
                <td colspan="3">
                    <tags:imSearch codeVariable="addStaffCodeCTVDBForm.districtCode" nameVariable="addStaffCodeCTVDBForm.districtName"
                                   codeLabel="MSG.SIK.024" nameLabel="MSG.SIK.025"
                                   searchClassName="com.viettel.im.database.DAO.AccountAnyPayFPTManagementDAO"
                                   searchMethodName="getDistrictList"
                                   elementNeedClearContent="addStaffCodeCTVDBForm.precinctCode;addStaffCodeCTVDBForm.precinctName"
                                   otherParam="addStaffCodeCTVDBForm.provinceCode"
                                   getNameMethod="getDistrictName" postAction="changeArea()"
                                   roleList=""/>
                </td>
                <%--Precinct--%>
                <td class="label"><tags:label key="MSG.SIK.026" required="true"/></td>
                <td colspan="3">
                    <tags:imSearch codeVariable="addStaffCodeCTVDBForm.precinctCode" nameVariable="addStaffCodeCTVDBForm.precinctName"
                                   codeLabel="MSG.SIK.027" nameLabel="MSG.SIK.028"
                                   searchClassName="com.viettel.im.database.DAO.AccountAnyPayFPTManagementDAO"
                                   searchMethodName="getWardList"
                                   otherParam="addStaffCodeCTVDBForm.districtCode;addStaffCodeCTVDBForm.provinceCode"
                                   getNameMethod="getWardName" postAction="changeArea()"
                                   roleList=""/>
                </td>
            </tr>
            <tr>
                <%--Address--%>
                <td class="label"><tags:label key="MES.CHL.070" required="false"/></td>
                <td class="text" colspan="3">
                    <s:textfield name="addStaffCodeCTVDBForm.address" theme="simple" id="address" maxlength="350" cssClass="txtInputFull" />
                </td>
                <%--Tổ--%>
                <td class="label"><tags:label key="MES.CHL.176"/></td>
                <td>
                    <s:textfield name="addStaffCodeCTVDBForm.streetBlockName" theme="simple" id="streetBlockName"  maxlength="50" cssClass="txtInputFull" onchange="changeArea()"/>
                </td>
                <%--Đường--%>
                <td class="label"><tags:label key="MES.CHL.177"/></td>
                <td>
                    <s:textfield name="addStaffCodeCTVDBForm.streetName" theme="simple" id="streetName" maxlength="50" cssClass="txtInputFull" onchange="changeArea()"/>
                </td>
            </tr>
            <tr>
                <%--Số nhà--%>
                <td class="label"><tags:label key="MES.CHL.178"/></td>
                <td colspan="3">
                    <s:textfield name="addStaffCodeCTVDBForm.home" theme="simple" id="home" maxlength="50" cssClass="txtInputFull" onchange="changeArea()"/>
                </td>
                <td class="label"><tags:label key="L.100013" required="false"/><%--Tên giao dịch--%></td>
                <td class="text" colspan="1">
                    <s:textfield name="addStaffCodeCTVDBForm.tradeName" theme="simple" id="tradeName" maxlength="100" cssClass="txtInputFull" />
                </td>
                <td class="label"><tags:label key="L.100014" required="false"/><%--Người liên hệ--%></td>
                <td class="text" colspan="1">
                    <s:textfield name="addStaffCodeCTVDBForm.contactName" theme="simple" id="contactName" maxlength="100" cssClass="txtInputFull" />
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="L.100006" required="false"/><%--Độ rộng mặt bằng--%></td>
                <td class="text" colspan="1">
                    <s:textfield name="addStaffCodeCTVDBForm.usedfulWidth" theme="simple" id="usedfulWidth" maxlength="100" cssClass="txtInputFull" />
                </td>
                <td class="label"><tags:label key="L.100007" required="false"/><%--Diện tích sử dụng--%></td>
                <td class="text" colspan="1">
                    <s:textfield name="addStaffCodeCTVDBForm.surfaceArea" theme="simple" id="surfaceArea" maxlength="100" cssClass="txtInputFull" />
                </td>
                <td class="label"><tags:label key="L.100008" required="false"/></td>
                <td class="text">
                    <tags:imSelect name="addStaffCodeCTVDBForm.profileState"
                                   id="profileState"
                                   cssClass="txtInputFull"
                                   theme="simple" headerKey="" headerValue="label.option"
                                   list="listProfileState" listKey="code" listValue="name"
                                   />
                </td>

                <td class="label"><tags:label key="L.100010" required="false"/></td>
                <td class="text">
                    <tags:imSelect name="addStaffCodeCTVDBForm.boardState"
                                   id="boardState"
                                   cssClass="txtInputFull"
                                   theme="simple" headerKey="" headerValue="label.option"
                                   list="listBoardState" listKey="code" listValue="name"
                                   />

                </td>
            </tr>
            <tr>
                <%--Điện thoại liên hệ--%>
                <td class="label"><tags:label key="label.contact.phone" required="true"/></td>
                <td class="text">
                    <s:textfield name="addStaffCodeCTVDBForm.tel" theme="simple" id="tel" maxlength="15" cssClass="txtInputFull" />
                </td>
                <%--Ngày ký hợp đồng--%>
                <td class="label"><tags:label key="label.sign.date.contract" required="false"/></td>
                <td class="text">
                    <tags:dateChooser property="addStaffCodeCTVDBForm.registryDate"
                                      id="registryDate" styleClass="txtInput" insertFrame="false"/>
                </td>
                <td class="label"><tags:label key="MSG.note" required="false"/><%--Note--%></td>
                <td class="text" colspan="3">
                    <s:textfield name="addStaffCodeCTVDBForm.note" theme="simple" id="note"
                                 maxlength="500" cssClass="txtInputFull" />
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.Note.BTS" required="true"/></td>
                <td class="text" colspan="3">
                    <tags:imSearch codeVariable="addStaffCodeCTVDBForm.btsCode"
                                   nameVariable="addStaffCodeCTVDBForm.fullAddress"
                                   codeLabel="BTS.Bts_Code" nameLabel="BTS.Bts_Name"
                                   searchClassName="com.viettel.im.database.DAO.ChangeInformationStaffDAO"
                                   otherParam="addStaffCodeCTVDBForm.provinceCode;addStaffCodeCTVDBForm.districtCode"                          
                                   searchMethodName="getListBTS"
                                   getNameMethod="getListBTS"
                                   roleList=""/>
                </td>
                <td class="label"><label style="color: red">Emola channel</label></td>
                <td>
                    <tags:imSelect name="addStaffCodeCTVDBForm.lastUpdateKey" theme="simple"
                                   id="lastUpdateKey"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="label.option"
                                   list="Emola:Emola
                                   "/>
                </td>
                <td class="label"><tags:label key="MSG.Regist.Point" required="false"/><%--Regist Point--%></td>
                <td>
                    <tags:imSelect name="addStaffCodeCTVDBForm.registrationPoint" theme="simple"
                                   id="registrationPoint"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="label.option"
                                   list="RA:Registration Point"/>
                </td>
            </tr>
            <tr>
                <td  class="label" style="display: none"><tags:label key=""/></td>
                <td  class="text" style="display: none" >
                    <tags:dateChooser property="addStaffCodeCTVDBForm.sysDate" id="sysDate"/>
                </td>
            </tr>
            <tr>
                <td class="text"> 
                    <s:checkbox id="chkWallet" name="addStaffCodeCTVDBForm.chkWallet" onchange="changeChkWallet()"/>
                    <tags:label key="lbl.is.wallet"/>
                </td>
            </tr>

            <tr id ="walletInfo">
                <%--Chon loai kenh vi dien tu--%>
                <s:hidden name="addStaffCodeCTVDBForm.channelWalletHide" id="addStaffCodeCTVDBForm.channelWalletHide"/>
                <td class="label"><tags:label key="label.channel.wallet" required="true"/></td>
                <td>
                    <tags:imSelect name="addStaffCodeCTVDBForm.channelWallet"
                                   id="addStaffCodeCTVDBForm.channelWallet"
                                   cssClass="txtInputFull"
                                   theme="simple" headerKey="" headerValue="label.option"
                                   list="lstChannelTypeWallet" listKey="code" listValue="name" disabled="true"
                                   onchange="changeChannelWallet()"
                                   />
                </td>

                <%--So dien thoai vi--%>
                <td class="label"><tags:label key="label.isdn.wallet" required="true"/></td>
                <td class="text">
                    <s:textfield name="addStaffCodeCTVDBForm.isdnWallet" theme="simple" id="addStaffCodeCTVDBForm.isdnWallet" maxlength="10" cssClass="txtInputFull" />
                </td>

                <%--Kênh ví cha--%>
                <td class="label"><tags:label key="label.channel.wallet.parent"/></td>
                <td class="text" colspan="3">
                    <tags:imSearch codeVariable="addStaffCodeCTVDBForm.parentCodeWallet"
                                   nameVariable="addStaffCodeCTVDBForm.parentNameWallet"
                                   codeLabel="MSG.stock.staff.code" nameLabel="MSG.staff.name"
                                   searchClassName="com.viettel.im.database.DAO.ChangeInformationStaffDAO"
                                   otherParam="addStaffCodeCTVDBForm.shopCode;addStaffCodeCTVDBForm.channelWallet"                                   
                                   searchMethodName="getListStaffManageWallet"
                                   getNameMethod="getListStaffManageWallet"
                                   roleList=""/>
                </td>
            </tr>
        </sx:div>
    </table>

    <div style="height:10px">
    </div>
    <div>
        <tags:displayResult id="displayResultMsgAdd" property="messageAdd"
                            propertyValue="paramMsgAdd" type="key" />
    </div>
    <div align="center" style="padding-bottom:5px;">
        <td class="text" colspan="8" align="center" theme="simple">
            <tags:submit id="add" formId="addStaffCodeCTVDBForm"
                         showLoadingText="false"
                         targets="popupBody" value="MSG.add"
                         cssStyle="width:60px;"
                         disabled="false"
                         validateFunction="checkValidAdd()"
                         confirm="true" confirmText="MES.CHL.072"
                         preAction="changeInformationStaffAction!addStaff.do"
                         />
        </td>
        <td class="text" colspan="8" align="center" theme="simple">
            <tags:submit id="cancel" confirm="false"
                         formId="addStaffCodeCTVDBForm"
                         cssStyle="width:60px;"
                         disabled="false"
                         showLoadingText="false"
                         targets="bodyContent" value="MES.CHL.073"
                         preAction="changeInformationStaffAction!cancelStaff.do"
                         />
        </td>
    </div>
    <s:token/>
</s:form>

<script language="javascript">

    changeChkWallet = function() {
        if (jQuery('#chkWallet').is(':checked')) {
            $('walletInfo').style.display = '';
        } else {
            $('walletInfo').style.display = 'none';
        }
        document.getElementById("addStaffCodeCTVDBForm.channelWallet").value = document.getElementById("addStaffCodeCTVDBForm.channelWalletHide").value
    }
    changeChkWallet();

    changeChannelWallet = function() {
        document.getElementById("addStaffCodeCTVDBForm.parentCodeWallet").value = '';
        document.getElementById("addStaffCodeCTVDBForm.parentNameWallet").value = '';
    }

    changeChannelType = function() {
        var channelTypeId = document.getElementById("addStaffCodeCTVDBForm.channelType").value;
        if (channelTypeId != null && channelTypeId == 1000522) {
            document.getElementById("addStaffCodeCTVDBForm.channelWallet").value = 'SA';
            document.getElementById("addStaffCodeCTVDBForm.channelWalletHide").value = 'SA';
        } else if (channelTypeId != null && channelTypeId == 1000485) {
            document.getElementById("addStaffCodeCTVDBForm.channelWallet").value = 'MA';
            document.getElementById("addStaffCodeCTVDBForm.channelWalletHide").value = 'MA';
        } else {
            document.getElementById("addStaffCodeCTVDBForm.channelWallet").value = 'AG';
            document.getElementById("addStaffCodeCTVDBForm.channelWalletHide").value = 'AG';
        }
    }

    checkValidAdd = function() {
        //Lọai kênh
        var channelType = document.getElementById("addStaffCodeCTVDBForm.channelType");
        if (channelType.value == null || channelType.value == "") {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0009')"/>';
            channelType.focus();
            return false;
        }

        var staffManagementCode = document.getElementById("addStaffCodeCTVDBForm.staffManagementCode");
        if (trim(staffManagementCode.value).length == 0) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.015')"/>';
            staffManagementCode.focus();
            return false;
        }
        if (staffManagementCode.value == null || staffManagementCode.value == "") {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.015')"/>';
            staffManagementCode.focus();
            return false;
        }

        /*check ngày sinh*/
        var birthday = dojo.widget.byId("birthday");
        if (birthday.domNode.childNodes[1].value != null && birthday.domNode.childNodes[1].value != ""
                && !isDateFormat(birthday.domNode.childNodes[1].value)) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0001')"/>';
            birthday.domNode.childNodes[1].focus();
            return false;
        }
        var status = document.getElementById("status");
        if (status.value == null || status.value == "") {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0007')"/>';
            status.focus();
            return false;
        }

        var staffName = document.getElementById("staffName");
        if (trim(staffName.value).length == 0) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.012')"/>';
            staffName.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(staffName.value))) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.013')"/>';
            staffName.focus();
            return false;
        }
        if (staffName.value == null || staffName.value == "") {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.012')"/>';
            staffName.focus();
            return false;
        }

        var makeDate = dojo.widget.byId("makeDate");
        if (makeDate.domNode.childNodes[1].value.trim() == "") {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.020')"/>';
            makeDate.domNode.childNodes[1].focus();
            return false;
        }
        //            if(compareDates(GetDateSys(),makeDate.domNode.childNodes[1].value)){
        //                $( 'displayResultMsgAdd' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.CHL.021')"/>';
        //                makeDate.domNode.childNodes[1].focus();
        //                return false;
        //            }
        if (!isDateFormat(makeDate.domNode.childNodes[1].value)) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.022')"/>';
            $('makeDate').focus();
            return false;
        }

        var idNo = document.getElementById("idNo");
        if (trim(idNo.value).length == 0) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.016')"/>';
            idNo.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(idNo.value))) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.017')"/>';
            idNo.focus();
            return false;
        }
        if (idNo.value == null || idNo.value == "") {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.016')"/>';
            idNo.focus();
            return false;
        }
        if (!isValidInput(trim(idNo.value), false, false)) {
            $('displayResultMsgAdd').innerHTML = "<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0025')"/>";
            idNo.focus();
            return false;
        }
        var makeAddress = document.getElementById("makeAddress");
        if (trim(makeAddress.value).length == 0) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.018')"/>';
            makeAddress.focus();
            return false;
        }

        if (makeAddress.value == null || makeAddress.value == "") {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.018')"/>';
            makeAddress.focus();
            return false;
        }

        if (isHtmlTagFormat(trim(makeAddress.value))) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.019')"/>';
            makeAddress.focus();
            return false;
        }

        var province = document.getElementById("addStaffCodeCTVDBForm.provinceCode");
        if (trim(province.value).length == 0) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.SIK.006')"/>';
            province.focus();
            return false;
        }
        var district = document.getElementById("addStaffCodeCTVDBForm.districtCode");
        if (trim(district.value).length == 0) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.SIK.007')"/>';
            district.focus();
            return false;
        }
        var precinct = document.getElementById("addStaffCodeCTVDBForm.precinctCode");
        if (trim(precinct.value).length == 0) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.SIK.008')"/>';
            precinct.focus();
            return false;
        }

        var address = document.getElementById("address");
        if (trim(address.value).length == 0) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.024')"/>';
            address.focus();
            return false;
        }

        if (address.value == null || address.value == "") {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.024')"/>';
            address.focus();
            return false;
        }


        if (isHtmlTagFormat(trim(address.value))) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.CHL.017')"/>';
            address.focus();
            return false;
        }

        var usedfulWidth = document.getElementById("usedfulWidth");
        if (usedfulWidth.value != null && usedfulWidth.value != "") {
            if (!isNumeric(trim(usedfulWidth.value))) {
                $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0002')"/>';
                $('usedfulWidth').focus();
                return false;
            }
            if (isHtmlTagFormat(trim(usedfulWidth.value))) {
                $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0018')"/>';
                $('usedfulWidth').focus();
                return false;
            }
        }

        var surfaceArea = document.getElementById("surfaceArea");
        if (surfaceArea.value != null && surfaceArea.value != "") {
            if (!isNumeric(trim(surfaceArea.value))) {
                $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0003')"/>';
                $('surfaceArea').focus();
                return false;
            }
            if (isHtmlTagFormat(trim(surfaceArea.value))) {
                $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0019')"/>';
                $('surfaceArea').focus();
                return false;
            }
        }

        var tel = document.getElementById("tel");
        if (tel.value == null || tel.value == "") {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.AGENT.009')"/>';
            $('tel').focus();
            return false;
        }
        if (tel.value != null && tel.value != "") {
            if (!isInteger(trim(tel.value))) {
                $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0004')"/>';
                $('tel').focus();
                return false;
            }
            if (isHtmlTagFormat(trim(tel.value))) {
                $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.SAE.010')"/>';
                $('tel').focus();
                return false;
            }
        }

        var registryDate = dojo.widget.byId("registryDate");
        var sysDate = dojo.widget.byId("sysDate");

        if (registryDate.domNode.childNodes[1].value != null && registryDate.domNode.childNodes[1].value != "" && !isDateFormat(registryDate.domNode.childNodes[1].value)) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0005')"/>';
            registryDate.domNode.childNodes[1].focus();
            return false;
        }
        if (registryDate.domNode.childNodes[1].value != null
                && registryDate.domNode.childNodes[1].value != ""
                && compareDates(sysDate.domNode.childNodes[1].value, registryDate.domNode.childNodes[1].value)) {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0008')"/>';
            registryDate.domNode.childNodes[1].focus();
            return false;
        }
        var note = document.getElementById("note");
        if (note.value != null && note.value != "") {
            if (isHtmlTagFormat(trim(note.value))) {
                $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0039')"/>';
                note.focus();
                return false;
            }
        }
        var btsCode = document.getElementById("addStaffCodeCTVDBForm.btsCode");
        if (btsCode.value == null || btsCode.value == "") {
            $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.00001')"/>';
            btsCode.focus();
            return false;
        }

        //Neu la kenh vi thi bat buoc chon cac thong tin vi
        if (jQuery('#chkWallet').is(':checked')) {
            var channelWallet = document.getElementById("addStaffCodeCTVDBForm.channelWallet");
            if (channelWallet.value == null || channelWallet.value == "") {
                $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('err.input.channelWallet')"/>';
                channelWallet.focus();
                return false;
            }

            var isdnWallet = document.getElementById("addStaffCodeCTVDBForm.isdnWallet");
            if (trim(isdnWallet.value).length == 0) {
                $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('err.input.isdnWallet')"/>';
                isdnWallet.focus();
                return false;
            }
            if (isdnWallet.value == null || isdnWallet.value == "") {
                $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('err.input.isdnWallet')"/>';
                isdnWallet.focus();
                return false;
            }
            if (isdnWallet.value != null && isdnWallet.value != "") {
                if (!isInteger(trim(isdnWallet.value))) {
    <%--$( 'displayResultMsgUpdate' ).innerHTML='ISDN ví phải là số';--%>
                        $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.ISDN.WALLET.ISNUMBER')"/>';
                        isdnWallet.focus();
                        return false;
                    }
                }

//            var parentCodeWallet = document.getElementById("addStaffCodeCTVDBForm.parentCodeWallet");
//            if (channelWallet.value != 'SA' && trim(parentCodeWallet.value).length == 0) {
//                $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('err.input.parentCodeWallet')"/>';
//                parentCodeWallet.focus();
//                return false;
//            }
//            if (channelWallet.value != 'SA' && (parentCodeWallet.value == null || parentCodeWallet.value == "")) {
//                $('displayResultMsgAdd').innerHTML = '<s:property escapeJavaScript="true"  value="getText('err.input.parentCodeWallet')"/>';
//                parentCodeWallet.focus();
//                return false;
//            }


            }

            $('addStaffCodeCTVDBForm.staffManagementCode').value = trim($('addStaffCodeCTVDBForm.staffManagementCode').value);
            $('staffName').value = trim($('staffName').value);
            $('idNo').value = trim($('idNo').value);
            $('makeAddress').value = trim($('makeAddress').value);
            $('addStaffCodeCTVDBForm.provinceCode').value = trim($('addStaffCodeCTVDBForm.provinceCode').value);
            $('addStaffCodeCTVDBForm.districtCode').value = trim($('addStaffCodeCTVDBForm.districtCode').value);
            $('addStaffCodeCTVDBForm.precinctCode').value = trim($('addStaffCodeCTVDBForm.precinctCode').value);
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

        changeArea = function() {
            var provinceCode = trim($('addStaffCodeCTVDBForm.provinceCode').value);
            var districtCode = trim($('addStaffCodeCTVDBForm.districtCode').value);
            var precinctCode = trim($('addStaffCodeCTVDBForm.precinctCode').value);
            var streetBlockName = trim(document.getElementById("streetBlockName").value);
            var streetName = trim(document.getElementById("streetName").value);
            var home = trim(document.getElementById("home").value);
            getInputText('${contextPath}/changeInformationStaffAction!updateAddress.do?target=address&provinceCode=' + provinceCode + '&districtCode=' + districtCode + '&precinctCode=' + precinctCode + '&' + token.getTokenParamString()
                    + '&streetBlockName=' + streetBlockName + '&streetName=' + streetName + '&home=' + home);
        }
</script>
