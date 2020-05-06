<%-- 
    Document   : searchBTS
    Created on : Jun 22, 2016, 2:11:13 PM
    Author     : mov_itbl_dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<tags:imPanel title="BTS.BTSPanel.Search">
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
                        <s:textfield name="btsForm.btsCode" id="btsForm.btsCode" maxlength="40" cssClass="txtInputFull"/>
                    </td>

                    <td class="label">
                        <tags:label key="BTS.Bts_Name" required="false"/>
                    </td>
                    <td class="text">
                        <s:textfield name="btsForm.btsName" id="btsForm.btsName" maxlength="150" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="BTS.Status" required="false"/> </td>
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
                        <tags:label key="BTS.Shop_Name" required="false"/>
                    </td>
                    <td class="text">
                        <tags:imSearch codeVariable="btsForm.shopCode" nameVariable="btsForm.shopName"
                                       codeLabel="BTS.shopCode" nameLabel="BTS.shopName"
                                       searchClassName="com.viettel.im.database.DAO.ManageBTSDAO"
                                       searchMethodName="getListShop"
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
                </tr>
                <tr>
                    <td class="label">
                        <tags:label key="BTS.Province" required="false"/>
                    </td>
                    <td class="text">
                        <tags:imSearch codeVariable="btsForm.provinceCode" nameVariable="btsForm.provinceName"
                                       codeLabel="BTS.provinceCode" nameLabel="BTS.provinceName"
                                       searchClassName="com.viettel.im.database.DAO.AssignDslamAreaDAO"
                                       searchMethodName="getListProvince"
                                       elementNeedClearContent = "btsForm.districtCode;btsForm.districtName;btsForm.precinctCode;btsForm.precinctName"
                                       getNameMethod="getListProvinceName"/>
                    </td>
                    <td class="label">
                        <tags:label key="BTS.District" required="false"/>
                    </td>
                    <td class="text">
                        <tags:imSearch codeVariable="btsForm.districtCode" nameVariable="btsForm.districtName"
                                       codeLabel="BTS.districtCode" nameLabel="BTS.districtName"
                                       searchClassName="com.viettel.im.database.DAO.AssignDslamAreaDAO"
                                       searchMethodName="getListDistrict"
                                       getNameMethod="getLisDistrictName"
                                       otherParam="btsForm.provinceCode"
                                       elementNeedClearContent="btsForm.precinctCode;btsForm.precinctName"/>
                    </td>
                    <td class="label">
                        <tags:label key="BTS.Precinct" required="false"/>
                    </td>
                    <td class="text">
                        <tags:imSearch codeVariable="btsForm.precinctCode" nameVariable="btsForm.precinctName"
                                       codeLabel="BTS.precinctCode" nameLabel="BTS.precinctName"
                                       searchClassName="com.viettel.im.database.DAO.AssignDslamAreaDAO"
                                       searchMethodName="getListPrecinct"
                                       getNameMethod="getListPrecinctName"
                                       otherParam="btsForm.provinceCode;btsForm.districtCode"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="BTS.fromDate"/> </td>
                    <td class="text">
                        <tags:dateChooser property="btsForm.fromDate" styleClass="200px" id="fromDate" readOnly="false"/>
                    </td>
                    <td class="label"><tags:label key="BTS.toDate"/> </td>
                    <td class="text">
                        <tags:dateChooser property="btsForm.toDate" styleClass="200px" id="toDate" readOnly="false" />
                    </td>
                </tr>

            </table>
            <s:token/>
        </s:form>
    </div>
    <tags:submit
        targets="bodyContent" formId="btsForm"
        showLoadingText="true" cssStyle="width:85px;"
        value="MSG.find" validateFunction="checkValidateSearch()"
        preAction="manageBTSAction!searchBTS.do"
        />
    <tags:submit
        targets="bodyContent" formId="btsForm"
        showLoadingText="true" cssStyle="width:85px;"
        value="BTS.btn.AddNew" preAction="manageBTSAction!prepareAddNewBTS.do"
        />
</tags:imPanel>

<script type="text/javascript">

    $('btsForm.btsCode').focus();

    checkValidateSearch = function(){
        var result = checkValidFieldsSearch();
        return result;
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

</script>
