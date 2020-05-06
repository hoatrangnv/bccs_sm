<%--
    Document   : manageBTSHome
    Created on : Jul 23, 2013, 1:44:51 PM
    Author     : thuannx1
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
        <s:form action="manageBTSGroupAction" theme="simple" enctype="multipart/form-data"  method="post" id="targetFromSeach" name="targetFromSeach">
            <table class="inputTbl8Col" style="width:100%" align="center" >
                <tr>
                    <td class="label">
                        <tags:label key="Target.shopCode" required="false"/>
                    </td>
                    <td class="text">
                        <tags:imSearch codeVariable="targetFromSeach.shopCode" nameVariable="targetFromSeach.shopName"
                                       codeLabel="targetFromSeach.shopCode" nameLabel="targetFromSeach.shopName"
                                       searchClassName="com.viettel.im.database.DAO.TargetStaffBtsDAO"
                                       searchMethodName="getListShop" elementNeedClearContent="targetFromSeach.staffCode;targetFromSeach.staffName"
                                       getNameMethod="getShopName"/>
                    </td>
                    <td class="label">
                        <tags:label key="Target.StaffCode" required="false"/>
                    </td>
                    <td class="text">
                        <tags:imSearch codeVariable="targetFromSeach.staffCode" nameVariable="targetFromSeach.staffName"
                                       codeLabel="MSG.stock.staff.code" nameLabel="MSG.staffName"
                                       searchClassName="com.viettel.im.database.DAO.TargetStaffBtsDAO"
                                       searchMethodName="getListStaff"
                                       otherParam="targetFromSeach.shopCode"
                                       getNameMethod="getStaffName"/>
                    </td>


                    <td class="label"><tags:label key="TARGET.YEAR" required="false"/></td>

                    <td class="text">
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:select name="targetFromSeach.yearSelect"
                                              id="targetFromSeach.yearSelect"
                                              cssClass="txtInputFull"
                                              list="%{#request.listYear != null ? #request.listYear :#{}}"
                                              listKey="code" listValue="name"/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:select name="targetFromSeach.monthSelect"
                                              id="targetFromSeach.monthSelect"
                                              cssClass="txtInputFull"
                                              list="%{#request.listMonth != null ? #request.listMonth :#{}}"
                                              listKey="code" listValue="name"/>
                                </td>
                            </tr>
                        </table>
                    </td>

                    <td class="label"><tags:label key="BTS.Status" required="false"/> </td>
                    <td class="text">
                        <tags:imSelect name="targetFromSeach.status" id="targetFromSeach.status"
                                       cssClass="txtInputFull"
                                       list="1:BTS.active,
                                       0:BTS.inactive"
                                       headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseStatus"/>
                    </td>
                </tr>                

            </table>
            <s:token/>
        </s:form>
    </div>
    <tags:submit
        targets="bodyContent" formId="targetFromSeach"
        showLoadingText="true" cssStyle="width:85px;"
        value="MSG.find" validateFunction="checkValidateSearch()"
        preAction="manageBTSGroupAction!searchListStaff.do"
        />
    <tags:submit
        targets="editTarget" formId="targetFromSeach"
        showLoadingText="true" cssStyle="width:85px;"
        value="BTS.btn.AddNewTarGet"
        preAction="manageBTSGroupAction!prepareAddTarget.do"
        />
</tags:imPanel>

<script type="text/javascript">
   

    checkValidateSearch = function(){
        //alert('1');
        return true;
        var result = checkValidFieldsSearch();
        return result;
    }
    
    checkValidFieldsSearch = function() {        

        $('btsGroupForm.btsGroupCode').value=trim($('btsGroupForm.btsGroupCode').value);
        $('btsGroupForm.btsGroupName').value=trim($('btsGroupForm.btsGroupName').value);
        

        // Kiem tra ma tram BTS-------------------------------------------------
        //
        // -  Ma tram BTS khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsGroupForm.btsGroupCode').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00005')" />';
            $('btsGroupForm.btsGroupCode').select();
            $('btsGroupForm.btsGroupCode').focus();
            return false;
        }
        // - Ma tram BTS khong duoc chua cac ky tu dac biet
        if(trim($('btsGroupForm.btsGroupCode').value) != "" &&
            !isValidInput(trim($('btsGroupForm.btsGroupCode').value), false, false)){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00006')" />';
            $('btsGroupForm.btsGroupCode').select();
            $('btsGroupForm.btsGroupCode').focus();
            return false;
        }
        // - Ma tram BTS khong duoc chua cac ky tu trong
        if (trim($('btsGroupForm.btsGroupCode').value).match(" ")){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00007')" />';
            $('btsGroupForm.btsGroupCode').select();
            $('btsGroupForm.btsGroupCode').focus();
            return false;
        }
        // - Ma tram BTS khong duoc qua 40 ky tu
        if ( trim($('btsGroupForm.btsGroupCode').value).length  > 40){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00008')" />';
            $('btsGroupForm.btsGroupCode').select();
            $('btsGroupForm.btsGroupCode').focus();
            return false;
        }

        // Kiem tra ten tram BTS------------------------------------------------
        //
        // -  Ten tram BTS khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsGroupForm.btsGroupName').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00009')" />';
            $('btsGroupForm.btsGroupName').select();
            $('btsGroupForm.btsGroupName').focus();
            return false;
        }
        // - Ten tram BTS khong duoc qua 150 ky tu
        if ( trim($('btsGroupForm.btsGroupName').value).length  > 150){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.000011')" />';
            $('btsGroupForm.btsGroupName').select();
            $('btsGroupForm.btsGroupName').focus();
            return false;
        }        
       
        return true;
    }

    checkValidateSearchAdd = function() {

        $('btsGroupForm.btsGroupCode').value=trim($('btsGroupForm.btsGroupCode').value);
        $('btsGroupForm.btsGroupName').value=trim($('btsGroupForm.btsGroupName').value);


        // Kiem tra ma tram BTS-------------------------------------------------
        //
        // -  Ma tram BTS khong duoc chua cac the HTML
        if(trim($('btsGroupForm.btsGroupCode').value) == ""){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00001')" />';
            $('btsGroupForm.btsGroupCode').select();
            $('btsGroupForm.btsGroupCode').focus();
            return false;
        }
        
        if(isHtmlTagFormat(trim($('btsGroupForm.btsGroupCode').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00005')" />';
            $('btsGroupForm.btsGroupCode').select();
            $('btsGroupForm.btsGroupCode').focus();
            return false;
        }
        // - Ma tram BTS khong duoc chua cac ky tu dac biet    

        if(trim($('btsGroupForm.btsGroupCode').value) != "" &&
            !isValidInput(trim($('btsGroupForm.btsGroupCode').value), false, false)){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00006')" />';
            $('btsGroupForm.btsGroupCode').select();
            $('btsGroupForm.btsGroupCode').focus();
            return false;
        }
        // - Ma tram BTS khong duoc chua cac ky tu trong
        if (trim($('btsGroupForm.btsGroupCode').value).match(" ")){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00007')" />';
            $('btsGroupForm.btsGroupCode').select();
            $('btsGroupForm.btsGroupCode').focus();
            return false;
        }
        // - Ma tram BTS khong duoc qua 40 ky tu
        if ( trim($('btsGroupForm.btsGroupCode').value).length  > 40){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00008')" />';
            $('btsGroupForm.btsGroupCode').select();
            $('btsGroupForm.btsGroupCode').focus();
            return false;
        }

        // Kiem tra ten tram BTS------------------------------------------------
        //
        
        if(trim($('btsGroupForm.btsGroupName').value) == ""){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00002')" />';
            $('btsGroupForm.btsGroupName').select();
            $('btsGroupForm.btsGroupName').focus();
            return false;
        }
        // -  Ten tram BTS khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsGroupForm.btsGroupName').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00009')" />';
            $('btsGroupForm.btsGroupName').select();
            $('btsGroupForm.btsGroupName').focus();
            return false;
        }
        // - Ten tram BTS khong duoc qua 150 ky tu
        if ( trim($('btsGroupForm.btsGroupName').value).length  > 150){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.000011')" />';
            $('btsGroupForm.btsGroupName').select();
            $('btsGroupForm.btsGroupName').focus();
            return false;
        }

        if(trim($('btsGroupForm.status').value) == ""){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00003')" />';
            $('btsGroupForm.status').select();
            $('btsGroupForm.status').focus();
            return false;
        }

        return true;
    }

</script>
