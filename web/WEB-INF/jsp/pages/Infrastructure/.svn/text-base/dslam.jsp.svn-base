<%--
    Document   : dslam
    Created on : Mar 24, 2009, 2:33:35 PM
    Author     : andv
    Modified   : tamdt1, 29/09/2010
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.dslam_dlu.info">
    <div>
        <tags:displayResult id="message" property="returnMsg" propertyValue="returnMsgParam" type="key"/>
    </div>

    <div class="divHasBorder">
        <s:form action="dslamAction" theme="simple" method="POST" id="dslamForm">
<s:token/>

            <s:hidden name="dslamForm.dslamId" id="dslamId"/>
            <%--s:hidden name="dslamForm.productId" id="productId"/--%>

            <s:hidden id="province_1" name="dslamForm.province" />

            <table class="inputTbl6Col">
                <%--tr>
                    <td class ="label"><tags:label key="MSG.generates.province" required="true"/></td>
                    <td class ="text">
                        <tags:imSelect name="dslamForm.province"
                                       id="province"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseProvince"
                                       list="listProvince"
                                       listKey="province" listValue="name"
                                       disabled="true"
                                       onchange="changeProvince(this.value)"/>

                    </td>
                    <td class ="label"><tags:label key="MSG.district" required="true"/> </td>
                    <td class ="text">
                        <tags:imSelect name="dslamForm.district"
                                       id="district"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="Chọn quận/huyện"
                                       list="listDistrict"
                                       listKey="district" listValue="name"
                                       onchange="changeDistrict(this.value)"/>
                    </td>
                    <td class ="label"><tags:label key="MSG.village" required="true"/> </td>
                    <td class ="text">
                        <tags:imSelect name="dslamForm.precinct"
                                       id="precinct"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="Chọn phường/xã"
                                       list="listPrecinct"
                                       listKey="precinct" listValue="name"/>
                    </td>

                    <!--                    <td class ="label"><_tags:label key="MSG.coordinates.X" /></td>
                                        <td class ="text">
                                            <s_:textfield name="dslamForm.x" id="x" maxlength="10" cssClass="txtInputFull"/>
                                        </td>
                                        <td class ="label"><_tags:label key="MSG.coordinates.Y" /></td>
                                        <td class ="text">
                                            <s_:textfield name="dslamForm.y"id="y" maxlength="10" cssClass="txtInputFull"/>
                                        </td>-->

                </tr--%>

                <tr>
                    <td class ="label"><tags:label key="MSG.dslam_dlu.code" required="true"/></td>
                    <td class ="text">
                        <s:textfield name="dslamForm.code" id="code" maxlength="40" cssClass="txtInputFull"/>
                    </td>
                    <td class ="label"><tags:label key="MSG.dslam_dlu.name" required="true"/></td>
                    <td class ="text" colspan="3">
                        <s:textfield name="dslamForm.name" id="name" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>                    
                    <td class ="label"><tags:label key="MSG.service.type" required="true"/></td>
                    <td class ="text">
                        <tags:imSelect name="dslamForm.productId"
                                       id="productId"
                                       cssClass="txtInputFull" disabled="true"
                                       list="1:MSG.INF.101, 6:MSG.INF.102"
                                       headerKey="" headerValue="MSG.GOD.166"
                                       onchange="changeProductId(this.value)"/>
                    </td>
                    <td class ="label"><tags:label key="MSG.dslam.ip"/></td>
                    <td class ="text">
                        <s:textfield name="dslamForm.dslamIp" id="dslamIp" maxlength="15" cssClass="txtInputFull"/>
                    </td>
                    <td class ="label">Bras</td>
                    <td>
                        <tags:imSelect name="dslamForm.brasId"
                                       id="brasId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="--Select Bras--"
                                       list="listBras"
                                       listKey="brasId" listValue="code"/>
                        <!--                        <s_:textfield name="dslamForm.brasIp" id="brasIp" maxlength="15" cssClass="txtInputFull"/>-->
                    </td>
                </tr>
                <tr>
                    <td class ="label"><tags:label key="MSG.limited.sport" required="true"/></td>
                    <td class ="text">
                        <s:textfield name="dslamForm.maxPort" id="maxPort" maxlength="10" cssClass="txtInputFull"/>
                    </td>
                    <!--                    <td class ="label"><_tags:label key="MSG.total.sport.used" /></td>
                                        <td class ="text">
                                            <s_:textfield name="dslamForm.usedPort" id="usedPort" maxlength="10" cssClass="txtInputFull"/>
                                        </td>-->
                    <td class ="label"><tags:label key="MSG.total.sport.reserve" required="true"/></td>
                    <td class ="text">
                        <s:textfield name="dslamForm.reservedPort" id="reservedPort" maxlength="10" cssClass="txtInputFull"/>
                    </td>
                    <td class ="label"><tags:label key="MSG.generates.status" required="true"/> </td>
                    <td class ="text">
                        <tags:imSelect name="dslamForm.status" id="status"
                                       cssClass="txtInputFull"
                                       list="1:MSG.GOD.002,0: MSG.GOD.284 "
                                       listKey="abc" listValue=""
                                       headerKey="" headerValue="MSG.GOD.189"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:10%; "><tags:label key="MDF" required="true"/></td>
                    <td style="width:30%; " class="oddColumn" colspan="5">
                        <tags:imSearch codeVariable="dslamForm.mdfCode" nameVariable="dslamForm.mdfName"
                                       codeLabel="MDF Code" nameLabel="MDF Name"
                                       otherParam="province_1"
                                       searchClassName="com.viettel.im.database.DAO.DslamDAO"
                                       searchMethodName="getListMdf"
                                       getNameMethod="getMdfName"/>
                    </td>
                </tr>
                <tr>
                    <td class ="label"><tags:label key="MSG.address" required="false"/></td>
                    <td colspan="5">
                        <s:textarea rows="2"  name="dslamForm.address" id="address" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>

        </s:form>

        <div align="center" style="padding-bottom:5px;">
            <s:if test="(dslamForm.dslamId == null) || ((dslamForm.dslamId*1) <= 0) ">
                <s:if test="dslamForm.province != null && dslamForm.province != ''">
                    <tags:submit targets="divDisplayInfo"
                                 formId="dslamForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 validateFunction="checkRequiredFields()&& checkValidFields();"
                                 value="MSG.generates.addNew"
                                 confirm="true"
                                 confirmText="Do you want to create new DSLAM/SU?"
                                 preAction="dslamAction!addNewDslam.do"/>
                </s:if>
                <s:else>
                    <input type="button" value="<s:text name = "MSG.generates.addNew"/>" style="width:80px;" disabled/>
                </s:else>

                <tags:submit targets="divDisplayInfo"
                             formId="dslamForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             value="MSG.find"
                             preAction="dslamAction!searchDslam.do"/>

                <input type="button" value="<s:text name = "MSG.SIK.102"/>" style="width:80px;" onclick="resetForm();"/>

            </s:if>
            <s:else>
                <tags:submit targets="divDisplayInfo"
                             formId="dslamForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             validateFunction="checkRequiredFields()&& checkValidFields();"
                             value="MSG.accept"
                             confirm="true"
                             confirmText="Do you want to edit this DSLAM/SU?"
                             preAction="dslamAction!editDslam.do"
                             />

                <tags:submit targets="divDisplayInfo"
                             formId="dslamForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             value="MSG.cancel2"
                             preAction="dslamAction!getListDslam.do"/>
            </s:else>
        </div>
    </div>

    <div id="listDslamArea">
        <fieldset class="imFieldset">
            <legend><s:text name="MSG.dslam_du.list"/></legend>
            <jsp:include page="listDslam.jsp"/>
        </fieldset>
    </div>

</tags:imPanel>

<script type="text/javascript">
    $('code').select();
    $('code').focus();
    if($('productId').value=="1"){
        $('dslamIp').disabled=false;
        $('brasId').disabled=false;
    }
    else{
        $('dslamIp').value="";
        $('brasId').value="";
        $('dslamIp').disabled=true;
        $('brasId').disabled=true;
    }

    //ham reset form
    resetForm = function() {
        $('code').value = "";
        $('name').value = "";
        $('dslamIp').value = "";
        $('brasId').value = "";
        $('maxPort').value = "";
        //        $('usedPort').value = "";
        $('reservedPort').value = "";
        //        $('x').value = "";
        //        $('y').value = "";
        $('status').value = "";
        $('address').value = "";

        $('code').focus();
    }


    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        
        
        if(trim($('code').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.INF.086"/>';
            $('code').select();
            $('code').focus();
            return false;
        }
        
        //truong ma tap luat
        if(trim($('name').value) == "") {
            $('message').innerHTML =  '<s:text name="ERR.INF.087"/>';
            $('name').select();
            $('name').focus();
            return false;
        }
        //        if(trim($('province').value) == "") {
        //            $('message').innerHTML =  '<s:text name="ERR.INF.088"/>';
        //            $('province').select();
        //            $('province').focus();
        //            return false;
        //        }



        if(trim($('productId').value) == "1") {
            if(trim($('dslamIp').value) == "") {
                $('message').innerHTML = '<s:text name="Error. DslamIp must not empty!"/>';
                $('dslamIp').select();
                $('dslamIp').focus();
                return false;
            }
            if(trim($('brasId').value) == "") {
                $('message').innerHTML = '<s:text name="Error. Bras must not empty!"/>';
                $('brasId').select();
                $('brasId').focus();
                return false;
            }
        }
        
        if(trim($('maxPort').value) == "") {
            $('message').innerHTML = '<s:text name="Error. Max port must not empty!"/>';
            $('maxPort').select();
            $('maxPort').focus();
            return false;
        }
        if(trim($('reservedPort').value) == "") {
            $('message').innerHTML = '<s:text name="Error. Reserved port must not empty!"/>';
            $('reservedPort').select();
            $('reservedPort').focus();
            return false;
        }
        
        if(trim($('status').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.INF.089"/>';
            $('status').select();
            $('status').focus();
            return false;
        }


        
        if(trim($('dslamForm.mdfCode').value) == "") {
            $('message').innerHTML = '<s:text name="warning.mdf.code.null"/>';
            $('dslamForm.mdfCode').select();
            $('dslamForm.mdfCode').focus();
            return false;
        }

        
        //        if(trim($('address').value) == "") {
        //            $('message').innerHTML =  '<s:text name="ERR.INF.090"/>';
        //            $('address').select();
        //            $('address').focus();
        //            return false;
        //        }

        return true;
    }
    //kiem tra ma khong duoc chua cac ky tu dac biet
    checkValidFields = function() {
        $('code').value=trim($('code').value);
        /*
        if(!isValidInput($('code').value, false, false)) {
            $('message').innerHTML = "!!!Lỗi. Mã Dslam chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
            $('code').select();
            $('code').focus();
            return false;
        }
         */
        if(isHtmlTagFormat(trim($('code').value))) {
            $('message').innerHTML =  '<s:text name="ERR.INF.091"/>';
            $('code').select();
            $('code').focus();
            return false;
        }
        /*
        if(isHtmlTagFormat(trim($('name').value))) {
            $('message').innerHTML = "!!!Lỗi. Trường tên DSLAM/DLU không được chứa các thẻ HTML";
            $('name').select();
            $('name').focus();
            return false;
        }*/


        if(!isInteger((trim($('maxPort').value)))&&(!trim($('maxPort').value) == "")){
            $('message').innerHTML =  '<s:text name="ERR.INF.092"/>';
            $('maxPort').select();
            $('maxPort').focus();
            return false;
        }
        //        if(!isInteger(trim($('usedPort').value))&&(!trim($('usedPort').value) == "")){
        //            $('message').innerHTML =  '<s:text name="ERR.INF.093"/>';
        //            $('usedPort').select();
        //            $('usedPort').focus();
        //            return false;
        //        }
        if(!isInteger(trim($('reservedPort').value))&&(!trim($('reservedPort').value) == "")){
            $('message').innerHTML =  '<s:text name="ERR.INF.094"/>';
            $('reservedPort').select();
            $('reservedPort').focus();
            return false;
        }
        //        if(!isInteger(trim($('x').value))&&(!trim($('x').value) == "")){
        //            $('message').innerHTML =  '<s:text name="ERR.INF.095"/>';
        //            $('x').select();
        //            $('x').focus();
        //            return false;
        //        }
        //        if(!isInteger(trim($('y').value))&&(!trim($('y').value) == "")){
        //            $('message').innerHTML =  '<s:text name="ERR.INF.096"/>';
        //            $('y').select();
        //            $('y').focus();
        //            return false;
        //        }
        /*
        if(isHtmlTagFormat(trim($('address').value))) {
            $('message').innerHTML = "!!!Lỗi. Trường địa chỉ DSLAM/DLU không được chứa các thẻ HTML";
            $('address').select();
            $('address').focus();
            return false;
        }*/
    
        //        var address = trim($('address').value);
        //        if(address.length > 150) {
        //            $('message').innerHTML =  '<s:text name="ERR.INF.097"/>';
        //            $('address').select();
        //            $('address').focus();
        //            return false;
        //        }


        return true;
    }

    //lang nghe, xu ly su liem khi click nut them moi
    dojo.event.topic.subscribe("dslamAction/addNewDslam", function(event, widget){

        $('message').innerHTML =  "";

        if (checkRequiredFields() && checkValidFields()) {
            var strConfirm = getUnicodeMsg('<s:text name="MSG.confirm.boards.003"/>');
            if (confirm(strConfirm)) {
                widget.href = "dslamAction!addNewDslam.do?"+token.getTokenParamString();
            }else{
                event.cancel = true;
            }
            
        } else {
            event.cancel = true;
        }
    });

    //lang nghe, xu ly su kien khi click nut "Dong y"
    dojo.event.topic.subscribe("dslamAction/editDslam", function(event, widget){

        if (checkRequiredFields() && checkValidFields()) {
            var strConfirm = getUnicodeMsg('<s:text name="Do you want to edit current DSLAM/SU?"/>');
            if (confirm(strConfirm)) {
                widget.href = "dslamAction!editDslam.do?"+token.getTokenParamString();
            }else{
                event.cancel = true;
            }
            
        } else {
            event.cancel = true;
        }
    });



    //lang nghe, xu ly su kien khi click nut "Tim kiem"
    dojo.event.topic.subscribe("dslamAction/dslamSearch", function(event, widget){
        $('message').innerHTML =  "";
        widget.href = "dslamAction!searchDslam.do";
    });

    //lang nghe, xu ly su kien khi click nut "bo qua"
    dojo.event.topic.subscribe("dslamAction/cancel", function(event, widget){
        $('message').innerHTML =  "";
        widget.href = "dslamAction!getListDslam.do";
        //$('dslamId').value='0';
    });

    //lang nghe, xu ly su kien khi click nut "Reset"
    dojo.event.topic.subscribe("dslamAction/reset", function(event, widget){
        $('message').innerHTML =  "";
        $('name').value="";
        $('code').value="";
        $('brasId').value="";
        $('maxPort').value="";
        //        $('usedPort').value="";
        $('reservedPort').value="";
        //        $('province').value="";

        $('shopId').value="";
        //        $('x').value="";
        //        $('y').value="";
        $('productId').value="";
        $('address').value="";
        $('dslamIp').value="";
        event.cancel = true;
    });
    /*
    dojo.event.topic.subscribe("dslamAction/displayProvinceName", function(value, key, text, widget){
        $('provinceName').value = key;
        updateData('getListTechShop.do?target=shopId&province='+ value);
        //updateData('${contextPath}/processPSTNAction!getDluCombobox.do?target=dslamId,form.stockPstnDistrict&location='+ location);
    });*/
    //    changeProvince = function(province) {
    //        updateData("${contextPath}/dslamAction!updateDistrictList.do?target=district&province=" + province);
    //    }
    //    changeDistrict = function(district) {
    //        updateData("${contextPath}/dslamAction!updatePrecinctList.do?target=precinct&district=" + district);
    //    }
    //
    //    changeProductId=function(productId){
    //
    //        if(productId=='1'){
    //            $('dslamIp').disabled=false;
    //            $('brasIp').disabled=false;
    //
    //        }
    //        else{
    //            $('dslamIp').value="";
    //            $('brasIp').value="";
    //            $('dslamIp').disabled=true;
    //            $('brasIp').disabled=true;
    //
    //        }
    //    }


    confirmDelete = function (dslamId){
        $('message').innerHTML =  "";
        var strConfirm = getUnicodeMsg('<s:text name="MSG.INF.00021"/>');
        if(confirm(strConfirm)){
            gotoAction('divDisplayInfo', 'dslamAction!actionDeleteDslam.do?'+dslamId + '&' + token.getTokenParamString());
        }
    }
</script>
