<%-- 
    Document   : assignAreaBTS
    Created on : Jun 22, 2016, 2:14:16 PM
    Author     : mov_itbl_dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<tags:imPanel title="BTS.BTSPanel.AssignArea">
    <!-- hien thi message -->
    <div>
        <tags:displayResult id="message" property="returnMsg" propertyValue="returnMsgParam" type="key"/>
    </div>
    <div>
        <s:form action="manageBTSAction" theme="simple" enctype="multipart/form-data"  method="post" id="btsForm" name="btsForm">
            <table class="inputTbl6Col" style="width:100%" align="center" >
                <tr>

                    <td class="label">
                        <tags:label key="BTS.Bts_Code" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.btsCode" id="btsForm.btsCode" maxlength="40" cssClass="txtInputFull" readonly="true"/>
                    </td>

                    <td class="label">
                        <tags:label key="BTS.Bts_Name" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.btsName" id="btsForm.btsName" maxlength="150" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <td class="label"><tags:label key="BTS.Status" required="false"/> </td>
                    <td class="text">
                        <tags:imSelect name="btsForm.status" id="btsForm.status"
                                       cssClass="txtInputFull"
                                       list="1:BTS.active,
                                       0:BTS.inactive"
                                       disabled="true"
                                       headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseStatus"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">
                        <tags:label key="BTS.Shop_Name" required="false"/>
                    </td>
                    <td class="text">
                        <tags:imSearch codeVariable="btsForm.shopCode" nameVariable="btsForm.shopName"
                                       codeLabel="BTS.shopCode" nameLabel="BTS.shopName"
                                       roleList="abc"
                                       readOnly="true"/>
                    </td>
                    <td class="label">
                        <tags:label key="BTS.Staff_Name" required="false"/>
                    </td>
                    <td class="text">
                        <tags:imSearch codeVariable="btsForm.staffCode" nameVariable="btsForm.staffName"
                                       codeLabel="MSG.stock.staff.code" nameLabel="MSG.staffName"
                                       roleList="abc"
                                       readOnly="true"/>
                    </td>
                </tr>
                <tr>
                    <%-- Tỉnh --%>
                    <td class="label">
                        <tags:label key="BTS.Province" required="true"/>
                    </td>
                    <td class="text">
                        <tags:imSearch codeVariable="btsForm.provinceCode" nameVariable="btsForm.provinceName"
                                       codeLabel="BTS.provinceCode" nameLabel="BTS.provinceName"
                                       searchClassName="com.viettel.im.database.DAO.ManageBTSDAO"
                                       searchMethodName="getUserTokenProvince"
                                       otherParam="btsForm.shopCode" roleList="abc" readOnly="true"
                                       elementNeedClearContent = "btsForm.districtCode;btsForm.districtName;btsForm.precinctCode;btsForm.precinctName;;streetBlockName;streetName;houseNumber"
                                       getNameMethod="getListProvinceName" postAction="changeArea()"/>
                    </td>
                    <%-- Quận/Huyện --%>
                    <td class="label">
                        <tags:label key="BTS.District" required="true"/>
                    </td>
                    <td class="text">
                        <tags:imSearch codeVariable="btsForm.districtCode" nameVariable="btsForm.districtName"
                                       codeLabel="BTS.districtCode" nameLabel="BTS.districtName"
                                       searchClassName="com.viettel.im.database.DAO.AssignDslamAreaDAO"
                                       searchMethodName="getListDistrict"
                                       getNameMethod="getLisDistrictName"
                                       otherParam="btsForm.provinceCode" postAction="changeArea()"
                                       elementNeedClearContent="btsForm.precinctCode;btsForm.precinctName;streetBlockName;streetName;houseNumber"/>
                    </td>
                    <%-- Phường/Xã --%>
                    <td class="label">
                        <tags:label key="BTS.Precinct" required="true"/>
                    </td>
                    <td class="text">
                        <tags:imSearch codeVariable="btsForm.precinctCode" nameVariable="btsForm.precinctName"
                                       codeLabel="BTS.precinctCode" nameLabel="BTS.precinctName"
                                       searchClassName="com.viettel.im.database.DAO.AssignDslamAreaDAO"
                                       searchMethodName="getListPrecinct"
                                       getNameMethod="getListPrecinctName" elementNeedClearContent="streetBlockName;streetName;houseNumber"
                                       otherParam="btsForm.provinceCode;btsForm.districtCode" postAction="changeArea()"/>
                    </td>
                </tr>
                <tr>
                    <%--Tổ--%>
                    <td class="label"><tags:label key="block.street" required="false"/></td>
                    <td colspan="1">
                        <s:textfield name="btsForm.streetBlockName" theme="simple" id="streetBlockName"  maxlength="100" cssClass="txtInputFull" onchange="changeArea()"/>
                    </td>
                    <%--Đường--%>
                    <td class="label"><tags:label key="street" required="false"/></td>
                    <td>
                        <s:textfield name="btsForm.streetName" theme="simple" id="streetName" maxlength="100" cssClass="txtInputFull" onchange="changeArea()"/>
                    </td>

                    <%--Số nhà--%>
                    <td class="label"><tags:label key="houseNumber" required="false"/></td>
                    <td class="text" >
                        <s:textfield name="btsForm.houseNumber" theme="simple" id="houseNumber" maxlength="100" cssClass="txtInputFull" onchange="changeArea()"/>
                    </td>
                </tr>

                <tr>
                    <%--Địa chỉ--%>
                    <td class="label"><tags:label key="MES.CHL.070" required="true"/></td>
                    <td class="text" >
                        <s:textfield name="btsForm.address" theme="simple" id="address" maxlength="500" cssClass="txtInputFull" />
                    </td>
                    <%--Ghi chú--%>
                    <td class="label"><tags:label key="MSG.comment" required="false"/></td>
                    <td class="text">
                        <s:textfield name="btsForm.note" theme="simple" id="note"
                                     maxlength="500" cssClass="txtInputFull" />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="BTS.fromDate"/> </td>
                    <td class="text">
                        <tags:dateChooser property="btsForm.fromDate" styleClass="200px" id="fromDate" readOnly="true"/>
                    </td>
                    <td class="label"><tags:label key="BTS.toDate"/> </td>
                    <td class="text">
                        <tags:dateChooser property="btsForm.toDate" styleClass="200px" id="toDate" readOnly="false" />
                    </td>
                </tr>
                <tr style="display:none">
                    <td class="text">
                        <tags:dateChooser property="btsForm.sysDate" styleClass="200px" id="sysDate" readOnly="true" />
                    </td>
                </tr>

            </table>
            <s:token/>
        </s:form>
    </div>
    <tags:submit
        targets="bodyContent" formId="btsForm" confirm="true"
        showLoadingText="true" cssStyle="width:85px;"
        value="BTS.Update" preAction="manageBTSAction!assignAreaBTS.do"
        validateFunction="checkValidateSearch()"
        confirmText="BTS.BTSModel.00002"
        />
    <tags:submit
        targets="bodyContent" formId="btsForm"
        showLoadingText="true" cssStyle="width:85px;"
        value="BTS.btn.Cancel" preAction="manageBTSAction!preparePage.do"
        />
</tags:imPanel>

<script type="text/javascript">

    $('btsForm.provinceCode').focus();
    checkValidateSearch = function(){
        var result = checkRequiredFields() && checkValidFieldsSearch();
        return result;
    }

    checkRequiredFields = function() {
        if(trim($('btsForm.provinceCode').value) == "") {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000028')" />';
            $('btsForm.provinceCode').select();
            $('btsForm.provinceCode').focus();
            return false;
        }
        if(trim($('btsForm.districtCode').value) == "") {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000069')" />';
            $('btsForm.districtCode').select();
            $('btsForm.districtCode').focus();
            return false;
        }
        if(trim($('btsForm.precinctCode').value) == "") {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000070')" />';
            $('btsForm.precinctCode').select();
            $('btsForm.precinctCode').focus();
            return false;
        }
//        if(trim($('streetBlockName').value) == "") {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000071')" />';
//            $('streetBlockName').select();
//            $('streetBlockName').focus();
//            return false;
//        }
//        if(trim($('streetName').value) == "") {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000072')" />';
//            $('streetName').select();
//            $('streetName').focus();
//            return false;
//        }
//        if(trim($('houseNumber').value) == "") {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000073')" />';
//            $('houseNumber').select();
//            $('houseNumber').focus();
//            return false;
//        }
        if(trim($('address').value) == "") {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000074')" />';
            $('address').select();
            $('address').focus();
            return false;
        }
        return true;
    }

    checkValidFieldsSearch = function() {

        $('btsForm.btsCode').value=trim($('btsForm.btsCode').value);
        $('btsForm.btsName').value=trim($('btsForm.btsName').value);
        $('btsForm.shopCode').value=trim($('btsForm.shopCode').value);
        $('btsForm.staffCode').value=trim($('btsForm.staffCode').value);

        // Kiem tra ma tram BTS-------------------------------------------------
        //
        // -  Ma tram BTS khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.btsCode').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.00005')" />';
            $('btsForm.btsCode').select();
            $('btsForm.btsCode').focus();
            return false;
        }
        // - Ma tram BTS khong duoc chua cac ky tu dac biet
        if(trim($('btsForm.btsCode').value) != "" &&
            !isValidInput(trim($('btsForm.btsCode').value), false, false)){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.00006')" />';
            $('btsForm.btsCode').select();
            $('btsForm.btsCode').focus();
            return false;
        }
        // - Ma tram BTS khong duoc chua cac ky tu trong
        if (trim($('btsForm.btsCode').value).match(" ")){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.00007')" />';
            $('btsForm.btsCode').select();
            $('btsForm.btsCode').focus();
            return false;
        }
        // - Ma tram BTS khong duoc qua 40 ky tu
        if ( trim($('btsForm.btsCode').value).length  > 40){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.00008')" />';
            $('btsForm.btsCode').select();
            $('btsForm.btsCode').focus();
            return false;
        }

        // Kiem tra ten tram BTS------------------------------------------------
        //
        // -  Ten tram BTS khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.btsName').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.00009')" />';
            $('btsForm.btsName').select();
            $('btsForm.btsName').focus();
            return false;
        }
        // - Ten tram BTS khong duoc qua 150 ky tu
        if ( trim($('btsForm.btsName').value).length  > 150){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000011')" />';
            $('btsForm.btsName').select();
            $('btsForm.btsName').focus();
            return false;
        }
        // Kiem tra ma don vi quan ly-------------------------------------------
        //
        // -  Ma don vi quan ly khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.shopCode').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000012')" />';
            $('btsForm.shopCode').select();
            $('btsForm.shopCode').focus();
            return false;
        }
        // - Ma don vi quan ly khong duoc chua cac ky tu dac biet
        if(trim($('btsForm.shopCode').value) != "" &&
            !isValidInput(trim($('btsForm.shopCode').value), false, false) ){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000013')" />';
            $('btsForm.shopCode').select();
            $('btsForm.shopCode').focus();
            return false;
        }
        // - Ma don vi quan ly khong duoc chua cac ky tu trong
        if (trim($('btsForm.shopCode').value).match(" ")){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000014')" />';
            $('btsForm.shopCode').select();
            $('btsForm.shopCode').focus();
            return false;
        }

        // Kiem tra ma user quan ly---------------------------------------------
        //
        // -  Ma user quan ly khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.staffCode').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000015')" />';
            $('btsForm.staffCode').select();
            $('btsForm.staffCode').focus();
            return false;
        }
        // - Ma user quan ly khong duoc chua cac ky tu dac biet
        if(trim($('btsForm.staffCode').value) != "" &&
            !isValidInput(trim($('btsForm.staffCode').value), false, false)){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000016')" />';
            $('btsForm.staffCode').select();
            $('btsForm.staffCode').focus();
            return false;
        }
        // - Ma user quan ly khong duoc chua cac ky tu trong
        if (trim($('btsForm.staffCode').value).match(" ")){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000017')" />';
            $('btsForm.staffCode').select();
            $('btsForm.staffCode').focus();
            return false;
        }


        // Kiem tra ma tinh-----------------------------------------------------
        //
        // -  Ma tinh khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.provinceCode').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000018')" />';
            $('btsForm.provinceCode').select();
            $('btsForm.provinceCode').focus();
            return false;
        }
        // - Ma tinh khong duoc chua cac ky tu dac biet
        if(trim($('btsForm.provinceCode').value) != "" &&
            !isValidInput(trim($('btsForm.provinceCode').value), false, false)){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000019')" />';
            $('btsForm.provinceCode').select();
            $('btsForm.provinceCode').focus();
            return false;
        }
        // - Ma tinh khong duoc chua cac ky tu trong
        if (trim($('btsForm.provinceCode').value).match(" ")){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000020')" />';
            $('btsForm.provinceCode').select();
            $('btsForm.provinceCode').focus();
            return false;
        }


        // Kiem tra ma quan/huyen-----------------------------------------------
        //
        // -  Ma quan/huyen khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.districtCode').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000021')" />';
            $('btsForm.districtCode').select();
            $('btsForm.districtCode').focus();
            return false;
        }
        // - Ma quan/huyen khong duoc chua cac ky tu dac biet
        if(trim($('btsForm.districtCode').value) != "" &&
            !isValidInput(trim($('btsForm.districtCode').value), false, false)){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000022')" />';
            $('btsForm.districtCode').select();
            $('btsForm.districtCode').focus();
            return false;
        }
        // - Ma quan/huyen khong duoc chua cac ky tu trong
        if (trim($('btsForm.districtCode').value).match(" ")){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000023')" />';
            $('btsForm.districtCode').select();
            $('btsForm.districtCode').focus();
            return false;
        }


        // Kiem tra ma phuong/xa------------------------------------------------
        //
        // -  Ma phuong/xa khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.precinctCode').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000024')" />';
            $('btsForm.precinctCode').select();
            $('btsForm.precinctCode').focus();
            return false;
        }
        // - Ma phuong/xa khong duoc chua cac ky tu dac biet
        if(trim($('btsForm.precinctCode').value) != "" &&
            !isValidInput(trim($('btsForm.precinctCode').value), false, false)){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000025')" />';
            $('btsForm.precinctCode').select();
            $('btsForm.precinctCode').focus();
            return false;
        }
        // - Ma phuong/xa khong duoc chua cac ky tu trong
        if (trim($('btsForm.districtCode').value).match(" ")){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000026')" />';
            $('btsForm.precinctCode').select();
            $('btsForm.precinctCode').focus();
            return false;
        }

        // Kiem tra neu tinh = null thi quan huyen phai khac null

        if(trim($('btsForm.provinceCode').value) == "") {
            // huyen va xa deu khac null
            if(trim($('btsForm.districtCode').value) != "" && trim($('btsForm.precinctCode').value) != ""){
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000028')" />';
                $('btsForm.provinceCode').select();
                $('btsForm.provinceCode').focus();
                return false;
            }
            // huyen khac null va xa = null
            if(trim($('btsForm.districtCode').value) != "" && trim($('btsForm.precinctCode').value) == ""){
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000028')" />';
                $('btsForm.provinceCode').select();
                $('btsForm.provinceCode').focus();
                return false;
            }
            // huyen = null va xa khac null
            if(trim($('btsForm.districtCode').value) == "" && trim($('btsForm.precinctCode').value) != ""){
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000029')" />';
                $('btsForm.provinceCode').select();
                $('btsForm.provinceCode').focus();
                return false;
            }
        }
        // Tinh khac null huyen = null nhung xa khac null
        if(trim($('btsForm.provinceCode').value) != "") {
            if(trim($('btsForm.districtCode').value) == "" && trim($('btsForm.precinctCode').value) != ""){
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000030')" />';
                $('btsForm.districtCode').select();
                $('btsForm.districtCode').focus();
                return false;
            }
        }

        var objFromDate = dojo.widget.byId("fromDate").domNode.childNodes[1].value.trim();
        var objToDate = dojo.widget.byId("toDate").domNode.childNodes[1].value.trim();
        var objSysDate = dojo.widget.byId("sysDate").domNode.childNodes[1].value.trim();
        // kiem tra ngay bat dau nho hon ngay ket thuc
        if (objFromDate != "" && objToDate != "") {
            var objFromArray = objFromDate.toString().split('/');
            var objToArray = objToDate.toString().split('/');

            var objFrom = objFromArray[1] + "/" + objFromArray[0] + "/" + objFromArray[2];
            var objTo = objToArray[1] + "/" + objToArray[0] + "/" + objToArray[2];
            var date1 = new Date(objFrom);
            var date2 = new Date(objTo);

            if(date1.getTime() > date2.getTime()) {
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000027')" />';
                dojo.widget.byId("fromDate").domNode.childNodes[1].focus();
                return false;
            }
        }
        // Kiem tra den ngay >= sysdate
        if (objSysDate != "" && objToDate != "") {
            var objSysArray = objSysDate.toString().split('/');
            var objToArray = objToDate.toString().split('/');

            var objFrom = objSysArray[1] + "/" + objSysArray[0] + "/" + objSysArray[2];
            var objTo = objToArray[1] + "/" + objToArray[0] + "/" + objToArray[2];
            var date1 = new Date(objFrom);
            var date2 = new Date(objTo);

            if(date1.getTime() > date2.getTime()) {
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000033')" />';
                dojo.widget.byId("fromDate").domNode.childNodes[1].focus();
                return false;
            }
        }

        // Kiem tra to----------------------------------------------------------
        //
        // -  To khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('streetBlockName').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000059')" />';
            $('streetBlockName').select();
            $('streetBlockName').focus();
            return false;
        }
        // - Ten to vuot qua 100 ky tu
        if ( trim($('streetBlockName').value).length  > 100){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000060')" />';
            $('streetBlockName').select();
            $('streetBlockName').focus();
            return false;
        }
        // - Ten to khong duoc chua cac ky tu dac biet
        if(trim($('streetBlockName').value) != "" &&
            !isValidInput(trim($('streetBlockName').value), true, true)){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000075')" />';
            $('streetBlockName').select();
            $('streetBlockName').focus();
            return false;
        }
        // Kiem tra duong-------------------------------------------------------
        //
        // -  Duong khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('streetName').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000061')" />';
            $('streetName').select();
            $('streetName').focus();
            return false;
        }
        // - Ten duong vuot qua 100 ky tu
        if ( trim($('streetName').value).length  > 100){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000062')" />';
            $('streetName').select();
            $('streetName').focus();
            return false;
        }
        // - Ten duong khong duoc chua cac ky tu dac biet
        if(trim($('streetName').value) != "" &&
            !isValidInput(trim($('streetName').value), true, true)){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000076')" />';
            $('streetName').select();
            $('streetName').focus();
            return false;
        }

        // Kiem tra so nha------------------------------------------------------
        //
        // -  So nha khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('houseNumber').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000063')" />';
            $('houseNumber').select();
            $('houseNumber').focus();
            return false;
        }
        // - So nha vuot qua 100 ky tu
        if ( trim($('houseNumber').value).length  > 100){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000064')" />';
            $('houseNumber').select();
            $('houseNumber').focus();
            return false;
        }
        // - So nha khong duoc chua cac ky tu dac biet
        if(trim($('houseNumber').value) != "" &&
            !isValidInput(trim($('houseNumber').value), true, true)){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000077')" />';
            $('houseNumber').select();
            $('houseNumber').focus();
            return false;
        }
        // Kiem tra dia chi-----------------------------------------------------
        //
        // -  Dia chi khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('address').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000065')" />';
            $('address').select();
            $('address').focus();
            return false;
        }
        // - Dia chi vuot qua 500 ky tu
        if ( trim($('address').value).length  > 500){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000066')" />';
            $('address').select();
            $('address').focus();
            return false;
        }
        // - Dia chi khong duoc chua cac ky tu dac biet
//        var regularExpression = /^[a-zA-Z0-9,.;:-_\s]+$/;
//        if(!regularExpression.test(trim($('address').value))){
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000078')" />';
//            $('address').select();
//            $('address').focus();
//            return false;
//        }

        // Kiem tra ghi chu-----------------------------------------------------
        //
        // -  Ghi chu khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('note').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000067')" />';
            $('note').select();
            $('note').focus();
            return false;
        }

        // - Ghi chu vuot qua 500 ky tu
        if ( trim($('note').value).length  > 500){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000068')" />';
            $('note').select();
            $('note').focus();
            return false;
        }
        //        // - Ghi chu khong duoc chua cac ky tu dac biet
        //        if(trim($('note').value) != "" &&
        //            !isValidInput(trim($('note').value), true, true)){
        //            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000078')" />';
        //            $('note').select();
        //            $('note').focus();
        //            return false;
        //        }

        return true;
    }

    changeArea = function() {
        var provinceCode  = trim($('btsForm.provinceCode').value);
        var districtCode  = trim($('btsForm.districtCode').value);
        var precinctCode  = trim($('btsForm.precinctCode').value);
        var streetBlockName  = trim(document.getElementById("streetBlockName").value);
        var streetName  = trim(document.getElementById("streetName").value);
        var houseNumber  = trim(document.getElementById("houseNumber").value);
        getInputText('${contextPath}/manageBTSAction!updateAddress.do?target=address&provinceCode='+ provinceCode + '&districtCode='+districtCode+'&precinctCode='+precinctCode+'&'+token.getTokenParamString()
            +'&streetBlockName='+streetBlockName+'&streetName='+streetName+'&houseNumber='+houseNumber);
    }
</script>

