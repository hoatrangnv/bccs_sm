<%--
    Document   : Switch
    Created on : Apr 27, 2009, 10:48:30 AM
    Author     : NamNX
--%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());

            if (request.getAttribute("lstEquipment") == null) {
                request.setAttribute("lstEquipment", request.getSession().getAttribute("lstEquipment"));
            }
%>


<tags:imPanel title="MSG.switches.info">

    <!-- phan hien thi MSG -->
    <div>
        <tags:displayResult id="MSG" property="returnMsg" propertyValue="returnMsgParam" type="key"/>
    </div>

    <!-- phan thong tin switch -->
    <s:form action="switchAction" theme="simple"  method="post" id="switchesForm">
        <s:hidden name="switchesForm.switchId" id="switchesForm.switchId"/>

        <table class="inputTbl6Col">
            <tr>
                <td class="label"><tags:label key="MSG.device" required="true"/> </td>
                <td class="text">
                    <tags:imSelect name="switchesForm.equipmentId"
                                   id="equipmentId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.bras.ChooseDevice"
                                   list="lstEquipment"
                                   listKey="equipmentId" listValue="name"/>


                    <%--s:select name="switchesForm.equipmentId"
                              id="equipmentId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="--Chọn thiết bị--"
                              list="%{#request.lstEquipment != null ? #request.lstEquipment : #{}}"
                              listKey="equipmentId" listValue="%{equipmentVendorName + ' - ' + name}"/--%>

                </td>
                <td class="label"><tags:label key="MSG.switch.code" required="true"/> </td>
                <td class="text">
                    <s:textfield name="switchesForm.code" id="code" maxlength="30" cssClass="txtInputFull" disabled="false"/>
                </td>
                <td class="label"><tags:label key="MSG.switch.name" required="true"/> </td>
                <td class="text">
                    <s:textfield name="switchesForm.name" id="name" maxlength="30" cssClass="txtInputFull" disabled="false"/>
                </td>

            </tr>
            <tr>
                <td class="label"><tags:label key="meessages.generates.ip_address" required="true"/> </td>
                <td class="text">
                    <s:textfield name="switchesForm.ip" id="ip" maxlength="20" cssClass="txtInputFull" disabled="false"/>
                </td>
                <td class="label"><tags:label key="MSG.AP.bras" required="true"/> </td>
                <td class="text">
                    <tags:imSelect name="switchesForm.brasId"
                                   id="brasId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.switch.ChooseBras"
                                   list="lstBras"
                                   listKey="brasId" listValue="name"/>
                    <!--td class="oddColumn">
                    <!--sx:autocompleter name="switchesForm.brasName"
                                      id="brasName"
                                      href="getBrasAuto.do"
                                      loadOnTextChange="true"
                                      loadMinimumCount="1"
                    autoComplete="true"/!-->
                </td>
                <td class="label"><tags:label key="NMS Vlan" required="true"/></td>
                <td class="text">
                    <s:textfield name="switchesForm.nmsVlan" id="nmsVlan" maxlength="5" cssClass="txtInputFull" disabled="false"/>
                </td>

            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.generates.status" required="true"/> </td>
                <td class="text">
                    <s:select name="switchesForm.status" id="status"
                              cssClass="txtInputFull"
                              list="#{'1':'Active','0':'Inactive'}"
                              headerKey="" headerValue="--Select Status--"/>
                </td>
            </tr>
        </table>
        <s:token/>
    </s:form>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-bottom:5px; padding-top:10px;">
        <s:if test="#session.toEditSwitch != 1">
            <tags:submit
                targets="bodyContent" formId="switchesForm"
                showLoadingText="true" cssStyle="width:85px;" validateFunction="checkValidate()"
                value="MSG.generates.addNew" preAction="switchAction!addSwitch.do"
                />
            <tags:submit
                targets="bodyContent" formId="switchesForm"
                showLoadingText="true" cssStyle="width:85px;"
                value="MSG.find" preAction="switchAction!searchSwitch.do"
                />

            <%--<sx:submit parseContent="true" executeScripts="true"
                       formId="switchesForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px;"
                       value="Thêm mới"  beforeNotifyTopics="switchAction/addSwitch"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="switchesForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px;"
                       value="Tìm kiếm"  beforeNotifyTopics="switchAction/searchSwitch"/>--%>

        </s:if>
        <s:else>
            <tags:submit
                targets="bodyContent" formId="switchesForm"
                showLoadingText="true" cssStyle="width:85px;" validateFunction="checkValidate()"
                value="MSG.accept" preAction="switchAction!editSwitch.do"
                />
            <tags:submit
                targets="bodyContent" formId="switchesForm"
                showLoadingText="true" cssStyle="width:85px;"
                value="MSG.INF.047" preAction="switchAction!preparePage.do"
                />

            <%--<sx:submit parseContent="true" executeScripts="true"
                       formId="switchesForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px;"
                       value="Đồng ý"  beforeNotifyTopics="switchAction/editSwitch"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="switchesForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px;"
                       value="Bỏ qua"  beforeNotifyTopics="switchAction/cancel"/>--%>
        </s:else>
    </div>

</tags:imPanel>

<br />

<s:div id="listSwitch">
    <jsp:include page="switchResult.jsp"/>
</s:div>

<script type="text/javascript">

    var htmlTag = '<[^>]*>';

    //focus vao truong dau tien
    $('equipmentId').focus();

    //lang nghe, xu ly su kien khi click nut "Tim Kiem"
    dojo.event.topic.subscribe("switchAction/searchSwitch", function(event, widget){
        widget.href = "switchAction!searchSwitch.do?"+token.getTokenParamString();
    });

    checkValidate = function() {
        var result = checkRequiredFields() && checkValidFields();
        return result;
    }

    //lang nghe, xu ly su kien khi click nut "Dong y"
    dojo.event.topic.subscribe("switchAction/editSwitch", function(event, widget){
        if (checkRequiredFields() && checkValidFields()) {
            //            if (confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.00032')" />'))){
            if (confirm(getUnicodeMsg('<s:text name="MSG.INF.00032" />'))){
                widget.href = "switchAction!editSwitch.do?"+token.getTokenParamString();
            }
            else{
                event.cancel = true;
            }
            
        } else {
            event.cancel = true;
        }
    });

    //lang nghe, xu ly su kien khi click nut "Them moi""
    dojo.event.topic.subscribe("switchAction/addSwitch", function(event, widget){
        if (checkRequiredFields() && checkValidFields()) {
            //            if (confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.00033')" />'))){
            if (confirm(getUnicodeMsg('<s:text name="MSG.INF.00033" />'))){
                widget.href = "switchAction!addSwitch.do?"+token.getTokenParamString();
            }
            else{
                event.cancel = true;
            }            
        } else {
            event.cancel = true;
        }
    });

    //lang nghe, xu ly su kien khi click nut "Huy"
    dojo.event.topic.subscribe("switchAction/cancel", function(event, widget){
        widget.href = "switchAction!preparePage.do?"+token.getTokenParamString();
        //event: set event.cancel = true, to cancel request
    });

    //lang nghe, xu ly su kien khi click nut "Reset"
    confirmDelete = function (switchId){
        //        if(confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.00034')" />'))){
        if(confirm(getUnicodeMsg('<s:text name="MSG.INF.00034" />'))){
            gotoAction('bodyContent', 'switchAction!deleteSwitch.do?switchId='+switchId+'&'+token.getTokenParamString());
        }
    }
    //Kiem tra thong tin cac truong bat buoc
    checkRequiredFields = function() {
        //truong tên
        if(trim($('equipmentId').value) == "") {
            $('MSG').innerHTML = '<s:text name="ERR.INF.117" />';
            $('equipmentId').select();
            $('equipmentId').focus();
            return false;
        }
        //trường mã
        if(trim($('code').value) == "") {
            $('MSG').innerHTML =  '<s:text name="ERR.INF.118" />';
            $('code').select();
            $('code').focus();
            return false;
        }

        if(!isFormalCharacter(trim($('code').value))){
            $('MSG').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.188')" />';
            $('code').select();
            $('code').focus();
            return false;
        }
        if(trim($('name').value) == "") {
            $('MSG').innerHTML =  '<s:text name="ERR.INF.119" />';
            $('name').select();
            $('name').focus();
            return false;
        }


        //trương nhà cung cấp

        if(trim($('ip').value) == "") {
            $('MSG').innerHTML = '<s:text name="ERR.INF.069" />';
            $('ip').select();
            $('ip').focus();
            return false;
        }
        if(trim($('brasId').value) == "") {
            $('MSG').innerHTML = '<s:text name="ERR.INF.121" />';
            $('brasId').select();
            $('brasId').focus();
            return false;
        }

        if(trim($('nmsVlan').value) == "") {
            $('MSG').innerHTML = 'Error. NMS Vlan must not empty!';
            $('nmsVlan').select();
            $('nmsVlan').focus();
            return false;
        }
        if(!isInteger(trim($('nmsVlan').value))&&(!trim($('nmsVlan').value) == "")){
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.078"/>';
            $('nmsVlan').select();
            $('nmsVlan').focus();
            return false;
        }
        $('nmsVlan').value = trim($('nmsVlan').value);

        
        if(trim($('status').value) == "") {
            //            $('MSG').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.122')" />';
            $('MSG').innerHTML = '<s:text name="ERR.INF.122" />';
            $('status').select();
            $('status').focus();
            return false;
        }

        

        return true;
    }
    //kiem tra ma khong duoc chua cac ky tu dac biet
    checkValidFields = function() {
        /*
        if(isHtmlTagFormat(trim($('name').value))) {
            $('MSG').innerHTML = "!!!Lỗi. Trường tên Switch không được chứa các kí tự đặc biệt";
            $('name').select();
            $('name').focus();
            return false;
        }*/
        $('code').value=trim($('code').value);

        if(isHtmlTagFormat(trim($('code').value))) {
            //            $('MSG').innerHTML ='<s:property escapeJavaScript="true"  value="getError('ERR.INF.123')" />';
            $('MSG').innerHTML ='<s:text name="ERR.INF.123" />';
            $('code').select();
            $('code').focus();
            return false;
        }
        /*
            if(!isValidInput(trim($('name').value), false, false)) {
                $('MSG').innerHTML = "!!!Lỗi. Trường tên thiết bị chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
                $('name').select();
                $('name').focus();
                return false;
            }*/

        if(isHtmlTagFormat(trim($('code').value))) {
            //            $('MSG').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.124')" />';
            $('MSG').innerHTML = '<s:text name="ERR.INF.124" />';
            $('code').select();
            $('code').focus();
            return false;
        }

        return true;
    }



</script>
