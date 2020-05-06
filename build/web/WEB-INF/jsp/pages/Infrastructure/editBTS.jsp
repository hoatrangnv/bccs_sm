<%-- 
    Document   : editBTS
    Created on : Jun 22, 2016, 2:13:27 PM
    Author     : mov_itbl_dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@taglib uri="VTdisplaytaglib" prefix="display"%>


<tags:imPanel title="BTS.BTSPanel.Edit">
    <!-- hien thi message -->
    <div>
        <tags:displayResult id="message" property="returnMsg" propertyValue="returnMsgParam" type="key"/>
    </div>
    <div>
        <s:form action="manageBTSAction" theme="simple" enctype="multipart/form-data"  method="post" id="btsForm" name="btsForm">
            <table class="inputTbl6Col" style="width:100%" align="center" >
                <tr>

                    <td class="label">
                        <tags:label key="BTS.Bts_Code" required="true"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.btsCode" id="btsForm.btsCode" maxlength="40" cssClass="txtInputFull" readonly="true"/>
                    </td>

                    <td class="label">
                        <tags:label key="BTS.Bts_Name" required="true"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.btsName" id="btsForm.btsName" maxlength="150" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="BTS.Status" required="true"/> </td>
                    <td class="text">
                        <tags:imSelect name="btsForm.status" id="btsForm.status"
                                       cssClass="txtInputFull"
                                       list="1:BTS.active,
                                       0:BTS.inactive"
                                       headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseStatus"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">
                        <tags:label key="BTS.Shop_Name" required="true"/>
                    </td>
                    <td class="text">
                        <tags:imSearch codeVariable="btsForm.shopCode" nameVariable="btsForm.shopName"
                                       codeLabel="BTS.shopCode" nameLabel="BTS.shopName"
                                       searchClassName="com.viettel.im.database.DAO.ManageBTSDAO"
                                       searchMethodName="getListShop" elementNeedClearContent="btsForm.staffCode;btsForm.staffName"
                                       getNameMethod="getShopName"/>
                    </td>
                    <td class="label">
                        <tags:label key="BTS.Staff_Name" required="false"/>
                    </td>
                    <td class="text">
                        <tags:imSearch codeVariable="btsForm.staffCode" nameVariable="btsForm.staffName"
                                       codeLabel="MSG.stock.staff.code" nameLabel="MSG.staffName"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStaff"
                                       otherParam="btsForm.shopCode"
                                       getNameMethod="getStaffName"/>
                    </td>
                    <td class="label"><tags:label key="BTS.CreateDate" required="true"/> </td>
                    <td class="text">
                        <tags:dateChooser property="btsForm.createDate" styleClass="200px" id="createDate" readOnly="true"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">
                        <tags:label key="Site" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.site" id="btsForm.site" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label key="BTS.high" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.high" id="btsForm.high" maxlength="10" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label key="BTS.longitude" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.longs" id="btsForm.longs" maxlength="20" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">
                        <tags:label key="BTS.latitude" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.lats" id="btsForm.lats" maxlength="20" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label key="BTS.config" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.config" id="btsForm.config" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label key="BTS.coverType" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.coverType" id="btsForm.coverType" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">
                        <tags:label key="BTS.Cell01Cover" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.cell01" id="btsForm.cell01" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label key="BTS.Cell02Cover" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.cell02" id="btsForm.cell02" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label key="BTS.Cell03Cover" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.cell03" id="btsForm.cell03" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">
                        <tags:label key="BTS.numberOfSubscriber" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.numOfSub" id="btsForm.numOfSub" maxlength="20" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label key="BTS.population" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.population" id="btsForm.population" maxlength="20" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label key="BTS.industry" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.industry" id="btsForm.industry" maxlength="20" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">
                        <tags:label key="BTS.company" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.company" id="btsForm.company" maxlength="20" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label key="BTS.administrative" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.administrative" id="btsForm.administrative" maxlength="20" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
            <s:token/>
        </s:form>
    </div>
    <tags:submit
        targets="bodyContent" formId="btsForm" confirm="true"
        showLoadingText="true" cssStyle="width:85px;"
        value="BTS.Update" preAction="manageBTSAction!editBTS.do"
        validateFunction="checkValidateAddNew()"
        confirmText="BTS.BTSModel.00002"
        />
    <tags:submit
        targets="bodyContent" formId="btsForm"
        showLoadingText="true" cssStyle="width:85px;"
        value="BTS.btn.Cancel" preAction="manageBTSAction!preparePage.do"
        />
</tags:imPanel>


<script type="text/javascript">

    $('btsForm.btsName').focus();
    checkValidateSearch = function(){
        var result = checkValidFieldsSearch();
        return result;
    }
    checkValidateAddNew = function() {
        var result = checkRequiredFields() && checkValideFieldsAdd();
        return result;
    }
    checkRequiredFields = function() {
        if(trim($('btsForm.btsCode').value) == "") {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.00001')" />';
            $('btsForm.btsCode').select();
            $('btsForm.btsCode').focus();
            return false;
        }

        if(trim($('btsForm.btsName').value) == "") {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.00002')" />'
            $('btsForm.btsName').select();
            $('btsForm.btsName').focus();
            return false;
        }

        if(trim($('btsForm.shopCode').value) == "") {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.00003')" />'
            $('btsForm.shopCode').select();
            $('btsForm.shopCode').focus();
            return false;
        }

        if(trim($('btsForm.status').value) == ""){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.00004')" />'
            //$('btsForm.status').select();
            $('btsForm.status').focus();
            return false;
        }
        return true;
    }


    isPositiveNumber = function(inputString){
        var arr = inputString.split('.');
        var regularExpression = /^[\d]+(\.[\d]*)?$/;
        var check = true;
        if(arr[arr.length-1] == ""){
            var check = false;
        }
        return regularExpression.test(inputString) && check;
    }

    checkValidFieldsSearch = function() {

        $('btsForm.btsCode').value=trim($('btsForm.btsCode').value);
        $('btsForm.btsName').value=trim($('btsForm.btsName').value);
        $('btsForm.shopCode').value=trim($('btsForm.shopCode').value);
        $('btsForm.staffCode').value=trim($('btsForm.staffCode').value);
        $('btsForm.btsCode').focus();
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

        // kiem tra ngay bat dau nho hon ngay ket thuc
        var objFromDate = dojo.widget.byId("fromDate").domNode.childNodes[1].value.trim();
        var objToDate = dojo.widget.byId("toDate").domNode.childNodes[1].value.trim();

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
        return true;
    }

    checkValideFieldsAdd = function(){
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

        // Kiem tra site--------------------------------------------------------
        //
        // Site khong duoc chua cac the html
        if(isHtmlTagFormat(trim($('btsForm.site').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.00003601')" />';
            $('btsForm.site').select();
            $('btsForm.site').focus();
            return false;
        }
        // Site khong duoc qua 100 ky tu
        if ( trim($('btsForm.site').value).length  > 100){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000037')" />';
            $('btsForm.site').select();
            $('btsForm.site').focus();
            return false;
        }
        // Site khong duoc chua cac ky tu dac biet
        if(trim($('btsForm.site').value) != "" &&
            !isValidInput(trim($('btsForm.site').value), false, false) ){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.00003602')" />';
            $('btsForm.site').select();
            $('btsForm.site').focus();
            return false;
        }


        // Kiem tra chieu cao cot ----------------------------------------------
        //
        // Chieu cao cot
        if(trim($('btsForm.high').value) != null && trim($('btsForm.high').value) != "" ){
            if(trim($('btsForm.high').value).trim() == 0) {
                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000038')"/>';
                $('btsForm.high').select();
                $('btsForm.high').focus();
                return false;
            }

            // Chieu cao cot phai la so khong am
            if(trim($('btsForm.high').value) != "" && !isPositiveNumber(trim($('btsForm.high').value)) ){
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000039')" />';
                $('btsForm.high').select();
                $('btsForm.high').focus();
                return false;
            }
        }
        // Chieu cao cot khong duoc vuot qua 10 ky tu
        if ( trim($('btsForm.high').value).length  > 10){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000038.01')" />';
            $('btsForm.high').select();
            $('btsForm.high').focus();
            return false;
        }

        // Kiem tra kinh do-----------------------------------------------------
        //
        //  Kinh do khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.longs').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000041')" />';
            $('btsForm.longs').select();
            $('btsForm.longs').focus();
            return false;
        }

        // Kinh do khong duoc vuot qua 100 ky tu
        if ( trim($('btsForm.longs').value).length  > 100){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000041.01')" />';
            $('btsForm.longs').select();
            $('btsForm.longs').focus();
            return false;
        }
        // Kinh do khong dung dinh dang
        if(trim($('btsForm.longs').value) != "" && !isPositiveNumber(trim($('btsForm.longs').value)) ){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000041.02')" />';
            $('btsForm.longs').select();
            $('btsForm.longs').focus();
            return false;
        }

        // Kiem tra vi do-------------------------------------------------------
        //
        //  Vi do khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.lats').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000040')" />';
            $('btsForm.lats').select();
            $('btsForm.lats').focus();
            return false;
        }
        // Vi do do khong duoc vuot qua 100 ky tu
        if ( trim($('btsForm.lats').value).length  > 100){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000040.01')" />';
            $('btsForm.lats').select();
            $('btsForm.lats').focus();
            return false;
        }
        // Vi do khong dung dinh dang
        if(trim($('btsForm.lats').value) != "" && !isPositiveNumber(trim($('btsForm.lats').value)) ){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000040.02')" />';
            $('btsForm.lats').select();
            $('btsForm.lats').focus();
            return false;
        }

        // Kiem tra cau hinh tram-----------------------------------------------
        //
        // Cau hinh tram khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.config').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000042')" />';
            $('btsForm.config').select();
            $('btsForm.config').focus();
            return false;
        }
        // Cau hinh tram khong duoc vuot qua 100 ky tu
        if ( trim($('btsForm.config').value).length  > 100){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000042.01')" />';
            $('btsForm.config').select();
            $('btsForm.config').focus();
            return false;
        }
        // Cau hinh tram khong duoc chua cac ky tu dac biet
        if(trim($('btsForm.config').value) != "" &&
            !isValidInput(trim($('btsForm.config').value), false, false) ){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000042.02')" />';
            $('btsForm.config').select();
            $('btsForm.config').focus();
            return false;
        }

        // Kiem tra loai phu----------------------------------------------------
        //
        // Loai phu khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.coverType').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000043')" />';
            $('btsForm.coverType').select();
            $('btsForm.coverType').focus();
            return false;
        }
        // Loai phu khong duoc vuot qua 20 ky tu
        if ( trim($('btsForm.coverType').value).length  > 20){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000043.01')" />';
            $('btsForm.coverType').select();
            $('btsForm.coverType').focus();
            return false;
        }
        // Loai phu khong duoc chua cac ky tu dac biet
        if(trim($('btsForm.coverType').value) != "" &&
            !isValidInput(trim($('btsForm.coverType').value), false, false) ){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000043.02')" />';
            $('btsForm.coverType').select();
            $('btsForm.coverType').focus();
            return false;
        }
        // Kiem tra vung phu cell 01--------------------------------------------
        //
        // Vung phu cell 01 khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.cell01').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000044')" />';
            $('btsForm.cell01').select();
            $('btsForm.cell01').focus();
            return false;
        }
        // Vung phu cell 01 khong duoc vuot qua 100 ky tu
        if ( trim($('btsForm.cell01').value).length  > 100){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000044.01')" />';
            $('btsForm.cell01').select();
            $('btsForm.cell01').focus();
            return false;
        }
        // Vung phu cell 01 khong duoc chua cac ky tu dac biet
        if(trim($('btsForm.cell01').value) != "" &&
            !isValidInput(trim($('btsForm.cell01').value), false, false) ){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000044.02')" />';
            $('btsForm.cell01').select();
            $('btsForm.cell01').focus();
            return false;
        }

        // Kiem tra vung phu cell 02--------------------------------------------
        //
        // Vung phu cell 02 khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.cell02').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000045')" />';
            $('btsForm.cell02').select();
            $('btsForm.cell02').focus();
            return false;
        }
        // Vung phu cell 02 khong duoc vuot qua 100 ky tu
        if ( trim($('btsForm.cell02').value).length  > 100){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000045.01')" />';
            $('btsForm.cell02').select();
            $('btsForm.cell02').focus();
            return false;
        }
        // Vung phu cell 02 khong duoc chua cac ky tu dac biet
        if(trim($('btsForm.cell02').value) != "" &&
            !isValidInput(trim($('btsForm.cell02').value), false, false) ){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000045.02')" />';
            $('btsForm.cell02').select();
            $('btsForm.cell02').focus();
            return false;
        }


        // Kiem tra vung phu cell 03--------------------------------------------
        //
        // Vung phu cell 03 khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.cell03').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000046')" />';
            $('btsForm.cell03').select();
            $('btsForm.cell03').focus();
            return false;
        }
        // Vung phu cell 03 khong duoc vuot qua 100 ky tu
        if ( trim($('btsForm.cell03').value).length  > 100){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000046.01')" />';
            $('btsForm.cell03').select();
            $('btsForm.cell03').focus();
            return false;
        }
        // Vung phu cell 03 khong duoc chua cac ky tu dac biet
        if(trim($('btsForm.cell03').value) != "" &&
            !isValidInput(trim($('btsForm.cell03').value), false, false) ){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000046.02')" />';
            $('btsForm.cell03').select();
            $('btsForm.cell03').focus();
            return false;
        }


        // Kiem tra so thue bao dap ung-----------------------------------------
        if( trim($('btsForm.numOfSub').value) != null && trim($('btsForm.numOfSub').value) != ""){
            // So thue bao dap ung phai la so nguyen va lon hon 0
            if(!isInteger(trim($('btsForm.numOfSub').value))) {
                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000047')"/>';
                $('btsForm.numOfSub').select();
                $('btsForm.numOfSub').focus();
                return false;
            }
            // So thue bao dap ung phai khac 0
            if(trim($('btsForm.numOfSub').value)==0) {
                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000048')"/>';
                $('btsForm.numOfSub').select();
                $('btsForm.numOfSub').focus();
                return false;
            }
            // So thue bao dap ung qua lon
            if(trim($('btsForm.numOfSub').value).length > 20) {
                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000049')"/>';
                $('btsForm.numOfSub').select();
                $('btsForm.numOfSub').focus();
                return false;
            }
        }


        // Kiem tra dan so------------------------------------------------------
        //
        if( trim($('btsForm.population').value) != null && trim($('btsForm.population').value) != ""){
            // Dan so phai la so nguyen va lon hon 0
            if(!isInteger(trim($('btsForm.population').value))) {
                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000050')"/>';
                $('btsForm.population').select();
                $('btsForm.population').focus();
                return false;
            }
            // Dan so phai khac 0
            if(trim($('btsForm.population').value)==0) {
                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000051')"/>';
                $('btsForm.population').select();
                $('btsForm.population').focus();
                return false;
            }
            // Dan so qua lon
            if(trim($('btsForm.population').value).length > 20) {
                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000052')"/>';
                $('btsForm.population').select();
                $('btsForm.population').focus();
                return false;
            }
        }


        // Kiem tra so khu cong nghiep------------------------------------------
        //
        // So khu cong nghiep khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.industry').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000053')" />';
            $('btsForm.industry').select();
            $('btsForm.industry').focus();
            return false;
        }
        // So khu cong nghiep khong duoc chua cac ky tu dac biet
        if(trim($('btsForm.industry').value) != "" &&
            !isValidInput(trim($('btsForm.industry').value), false, false) ){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000054')" />';
            $('btsForm.industry').select();
            $('btsForm.industry').focus();
            return false;
        }
        // So khu cong nghiep vuot qua 20 ky tu
        if(trim($('btsForm.industry').value).length > 20) {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000054.01')"/>';
            $('btsForm.industry').select();
            $('btsForm.industry').focus();
            return false;
        }

        // Kiem tra so cong ty lon----------------------------------------------
        //
        // So cong ty lon khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.company').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000055')" />';
            $('btsForm.company').select();
            $('btsForm.company').focus();
            return false;
        }
        // So cong ty lon khong duoc chua cac ky tu dac biet
        if(trim($('btsForm.company').value) != "" &&
            !isValidInput(trim($('btsForm.company').value), false, false) ){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000056')" />';
            $('btsForm.company').select();
            $('btsForm.company').focus();
            return false;
        }
        // So cong ty lon vuot qua 20 ky tu
        if(trim($('btsForm.company').value).length > 20) {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000056.01')"/>';
            $('btsForm.company').select();
            $('btsForm.company').focus();
            return false;
        }

        // Kiem tra so don vi hanh chinh----------------------------------------
        //
        // So don vi hanh chinh khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsForm.administrative').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000057')" />';
            $('btsForm.administrative').select();
            $('btsForm.administrative').focus();
            return false;
        }
        // So don vi hanh chinh khong duoc chua cac ky tu dac biet
        if(trim($('btsForm.administrative').value) != "" &&
            !isValidInput(trim($('btsForm.administrative').value), false, false) ){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000058')" />';
            $('btsForm.administrative').select();
            $('btsForm.administrative').focus();
            return false;
        }
        // So cong ty lon vuot qua 20 ky tu
        if(trim($('btsForm.administrative').value).length > 20) {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.BTS.000058.01')"/>';
            $('btsForm.administrative').select();
            $('btsForm.administrative').focus();
            return false;
        }
        return true;
    }
</script>

