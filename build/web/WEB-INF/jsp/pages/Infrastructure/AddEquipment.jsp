<%-- 
    Document   : AddEquipment
    Created on : May 12, 2009
    Author     : ducdd
--%>

<%--
    Note: danh mục thiết bị
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
            if (request.getAttribute("listEquipment") == null) {
                request.setAttribute("listEquipment", request.getSession().getAttribute("listEquipment"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title = "MSG.devices.info">
    <!--fieldset style="width:100%; background-color: #F5F5F5;"-->
    <!--legend class="transparent">Thông tin thiết bị</legend-->
    <!-- hien thi message -->
    <div>
        <tags:displayResult id="message" property="returnMsg" propertyValue="returnMsgParam" type="key"/>
    </div>

    <!-- phan thong tin ve thiet bi -->
    <s:form action="equipmentAction" id="EquipmentForm"  theme="simple" method="post" >
<s:token/>

        <s:hidden name="EquipmentForm.equipmentId" id="EquipmentForm.equipmentId"/>

        <table class="inputTbl4Col">
            <tr>
                <td class="label"><tags:label key="MSG.suppliers" required="true"/>

                </td>
                <td class="text">
                    <tags:imSelect name="EquipmentForm.equipmentVendorId"
                              id="equipmentVendorId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="MSG.FAC.ChooseSupplier"
                              list="listEquipmentVendorType"
                              listKey="equipmentVendorId" listValue="vendorName"/>
                </td>
                <td class="label"><tags:label key="MSG.device.type" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="EquipmentForm.equipmentType"
                              id="equipmentType"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="MSG.Equipment.ChooseDeviceType"
                              list="listEquipmentType"
                              listKey="code" listValue="name"/>
                </td>
            </tr>
            <tr>

                <td class="label">
                    <tags:label key="MSG.device.code" required="true"/>
                </td>
              
                <td class="text">
                    <s:textfield name="EquipmentForm.code" id="code" maxlength="20" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.device.name" required="true"/></td>
                <td class="text">
                    <s:textfield name="EquipmentForm.name" id="name" maxlength="500" cssClass="txtInputFull"/>
                </td>                
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.status" required="true"/></td>
                <td class="text" colspan="1">
                    <tags:imSelect name="EquipmentForm.status" id="status"
                              cssClass="txtInputFull" disabled="false"
                              list="1:MSG.active,
                                      0:MSG.inactive"
                              headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseStatus"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.discription"/></td>
                <td class="text" colspan="3">
                    <s:textarea  name="EquipmentForm.description"  id="description"  cssClass="txtInputFull"/>
                </td>
            </tr>
        </table>
    </s:form>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-bottom:5px;">
        <s:if test="#session.toEditEquipment != 1">
            <sx:submit parseContent="true" executeScripts="true"
                       formId="EquipmentForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px;" validate="checkValidate()"
                       key="MSG.generates.addNew"  beforeNotifyTopics="equipmentAction/addEquipment"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="EquipmentForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px;" validate="checkValidate()"
                       key="MSG.generates.find"  beforeNotifyTopics="equipmentAction/searchEquipment"/>

        </s:if>
        <s:else>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="EquipmentForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       key="MSG.accept"  beforeNotifyTopics="equipmentAction/editEquipment"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="EquipmentForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       key="MSG.INF.047"  beforeNotifyTopics="equipmentAction/cancel"/>
        </s:else>
    </div>

    <!--/fieldset-->
</tags:imPanel>

<br />

<div id="listEquipment">
    <tags:imPanel title="MSG.devices.list">
        <!--fieldset style="width:100%; background-color: #F5F5F5;"-->
        <!--legend class="transparent">Danh sách thiết bị </legend-->
        <jsp:include page="listEquipment.jsp"/>
        <!--/fieldset-->
    </tags:imPanel>
</div>


<script type="text/javascript">
    $('equipmentVendorId').select();
    $('equipmentVendorId').focus();
    
    //lang nghe, xu ly su kien khi click nut "Tim kiem"
    dojo.event.topic.subscribe("equipmentAction/searchEquipment", function(event, widget){
        widget.href = "equipmentAction!searchEquipment.do";
        //event: set event.cancel = true, to cancel request
    });


    checkValidate = function() {
        var result = checkRequiredFields() && checkValidFields();
        return result;
    }

    //lang nghe, xu ly su kien khi click nut "Đong y"
    dojo.event.topic.subscribe("equipmentAction/editEquipment", function(event, widget){
        if (checkRequiredFields() && checkValidFields()) {

//            if (confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.0001')"/>'))){
            if (confirm(getUnicodeMsg('<s:text name="MSG.INF.0001"/>'))){
                widget.href = "equipmentAction!editEquipment.do?" + token.getTokenParamString();
            }
            else{
                event.cancel = true;
            }

            
        } else {
            event.cancel = true;
        }
    });

    //lang nghe, xu ly su kien khi click nut "Bo qua"
    dojo.event.topic.subscribe("equipmentAction/cancel", function(event, widget){
        widget.href = "equipmentAction!preparePage.do";
    });

    //lang nghe, xu ly su kien khi click nut "Them moi"
    dojo.event.topic.subscribe("equipmentAction/addEquipment", function(event, widget){
        if (checkRequiredFields() && checkValidFields()) {
//            if (confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.0002')"/>'))){
            if (confirm(getUnicodeMsg('<s:text name="MSG.INF.0002"/>'))){
                widget.href = "equipmentAction!addEquipment.do?" + token.getTokenParamString();
            }
            else{
                event.cancel = true;
            }

            
        } else {
            event.cancel = true;
        }


        //event: set event.cancel = true, to cancel request
    });

    //lang nghe, xu ly su kien khi click nut "xoa"
    confirmDelete = function (equipmentId){
//        if(confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.0003')"/>'))){
        if(confirm(getUnicodeMsg('<s:text name="MSG.INF.0003"/>'))){
            gotoAction('bodyContent', 'equipmentAction!deleteEquipment.do?'+equipmentId  + "&" + token.getTokenParamString());
        }
    }
    //Kiem tra thong tin cac truong bat buoc
    checkRequiredFields = function() {
        //truong mã
        if(trim($('code').value) == "") {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.001')"/>';
            $('message').innerHTML = '<s:text name="ERR.INF.001"/>';
            $('code').select();
            $('code').focus();
            return false;
        }
        if(isHtmlTagFormat(trim($('code').value))) {
//            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.002')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.002"/>';
            $('code').select();
            $('code').focus();
            return false;
        }
        $('code').value = trim($('code').value);
        
        //truong tên
        if(trim($('name').value) == "") {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.003')"/>';
            $('message').innerHTML = '<s:text name="ERR.INF.003"/>';
            $('name').select();
            $('name').focus();
            return false;
        }
        if(isHtmlTagFormat(trim($('name').value))) {
//            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.004')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.004"/>';
            $('name').select();
            $('name').focus();
            return false;
        }
        $('name').value = trim($('name').value);

        //trường loại thiết bị
        if(trim($('equipmentType').value) == "") {
//            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.005')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.005"/>';
            $('equipmentType').select();
            $('equipmentType').focus();
            return false;
        }
        //trương nhà cung cấp
        if(trim($('equipmentVendorId').value) == "") {
//            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.014')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.014"/>';
            $('equipmentVendorId').select();
            $('equipmentVendorId').focus();
            return false;
        }
        if(trim($('status').value) == "") {
//            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.006')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.006"/>';
            $('status').select();
            $('status').focus();
            return false;
        }

        return true;
    }
    //kiem tra ma khong duoc chua cac ky tu dac biet
    checkValidFields = function() {
        /*if(isHtmlTagFormat(trim($('name').value))) {
            $('message').innerHTML = "!!!Lỗi. Trường tên thiết bị không được chứa các kí tự đặc biệt";
            $('name').select();
            $('name').focus();
            return false;
        }

            if(!isValidInput(trim($('name').value), false, false)) {
                $('message').innerHTML = "!!!Lỗi. Trường tên thiết bị chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
                $('name').select();
                $('name').focus();
                return false;
            }*/
        /*
        if(isHtmlTagFormat(trim($('description').value))) {
            $('message').innerHTML = "!!!Lỗi. Trường mô tả không được chứa các thẻ HTML";
            $('description').select();
            $('description').focus();
            return false;
        }*/
        var description = trim($('description').value);
        if(description.length > 1000) {
//            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.007')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.007"/>';
            $('description').select();
            $('description').focus();
            return false;
        }

         if(!isFormalCharacter(trim($('code').value))){
            //alert( 'aa');
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.189')" />';
            $('message').innerHTML = '<s:text name="ERR.INF.189" />';
            $('code').select();
            $('code').focus();
            return false;
        }


        return true;
    }

</script>


