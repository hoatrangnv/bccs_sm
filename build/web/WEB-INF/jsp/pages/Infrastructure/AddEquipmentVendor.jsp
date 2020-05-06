<%-- 
    Document   : AddEquipmentVendor
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tuannv
--%> 

<%--
    Note: danh mục nhà cung cấp thiết bị
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>


<%
            if (request.getAttribute("listEquipmentVendor") == null) {
                request.setAttribute("listEquipmentVendor", request.getSession().getAttribute("listEquipmentVendor"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title = "MSG.suppliers.info">
    <div>
        <tags:displayResult id="message" property="returnMsg" propertyValue="returnMsgParam" type="key"/>
    </div>
    <s:form action="equipmentVendorAction" id="EquipmentVendorForm"  theme="simple" method="post" >
<s:token/>

        <s:hidden name="EquipmentVendorForm.equipmentVendorId" id="EquipmentVendorForm.equipmentVendorId"/>

        <table class="inputTbl6Col">
            <tr >
                <td class="label"><tags:label key="MSG.suppliers.code" required="true"/> </td>
                <td class="text">
                    <s:textfield name="EquipmentVendorForm.vendorCode" id="vendorCode" maxlength="20" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.suppliers.name" required="true"/> </td>
                <td class="text">
                    <s:textfield name="EquipmentVendorForm.vendorName" id="vendorName" maxlength="500" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.generates.status" required="true"/> </td>
                <td class="text">
                    <tags:imSelect name="EquipmentVendorForm.status" id="status"
                                   cssClass="txtInputFull"
                                   list="1:MSG.active,
                                   0:MSG.inactive"
                                   headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseStatus"/>
                </td>                
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.discription" /></td>
                <td class="text" colspan="5">
                    <s:textfield name="EquipmentVendorForm.description" cssClass="txtInputFull" id="description" maxlength="500" />
                </td>
            </tr>
        </table>
    </s:form>
</tags:imPanel>

<!-- phan nut tac vu -->
<div align="center" style="vertical-align:top; padding-top:10px;">
    <s:if test="#session.toEditEquipmentVendor != 1">
        <tags:submit targets="bodyContent" formId="EquipmentVendorForm"
                     showLoadingText="true" cssStyle="width:80px;" validateFunction="checkValidate()"
                     value="MSG.generates.addNew" preAction="equipmentVendorAction!addEquipmentVendor.do"
                     />
        <tags:submit targets="bodyContent" formId="EquipmentVendorForm"
                     showLoadingText="true" cssStyle="width:80px;"
                     value="MSG.find" preAction="equipmentVendorAction!searchEquipmentVendor.do"
                     />
      
    </s:if>
    <s:else>
        <tags:submit targets="bodyContent" formId="EquipmentVendorForm"
                     showLoadingText="true" cssStyle="width:80px;" validateFunction="checkValidate()"
                     value="MSG.accept" preAction="equipmentVendorAction!editEquipmentVendor.do"
                     />
        <tags:submit targets="bodyContent" formId="EquipmentVendorForm"
                     showLoadingText="true" cssStyle="width:80px;"
                     value="MSG.INF.047" preAction="equipmentVendorAction!preparePage.do"
                     />

    </s:else>
</div>


<br />

<div id="listEquipmentVendor">
    <tags:imPanel title="MSG.suppliers.list">
        <jsp:include page="listEquipmentVendor.jsp"/>
    </tags:imPanel>
</div>

<script type="text/javascript">
    $('vendorName').select();
    $('vendorName').focus();

    //lang nghe, xu ly su kien khi click nut "Tim kiem"
    dojo.event.topic.subscribe("equipmentVendorAction/searchEquipmentVendor", function(event, widget){
        widget.href = "equipmentVendorAction!searchEquipmentVendor.do";
        //event: set event.cancel = true, to cancel request
    });


    checkValidate = function() {
        var result = checkRequiredFields() && checkValidFields();
        return result;
    }   

    //lang nghe, xu ly su kien khi click nut "Bo qua"
    dojo.event.topic.subscribe("equipmentVendorAction/cancel", function(event, widget){
        widget.href = "equipmentVendorAction!preparePage.do";
        //event: set event.cancel = true, to cancel request
    });
    

    //lang nghe, xu ly su kien khi click nut "xoa"
    confirmDelete = function (equipmentVendorId){
//        if(confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.0006')"/>'))){
        if(confirm(getUnicodeMsg('<s:text name="MSG.INF.0006"/>'))){
            gotoAction('bodyContent', 'equipmentVendorAction!deleteEquipment.do?'+equipmentVendorId + '&' + token.getTokenParamString());
        }
    }

    checkRequiredFields = function() {
        //truong tên
        if(trim($('vendorCode').value) == "") {
//            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.008')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.008"/>';
            $('vendorCode').select();
            $('vendorCode').focus();
            return false;
        }
        if(trim($('vendorName').value) == "") {
//            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.009')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.009"/>';
            $('vendorName').select();
            $('vendorName').focus();
            return false;
        }
        if(!isFormalCharacter(trim($('vendorCode').value))){
            //alert( 'aa');
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.190')" />';
            $('message').innerHTML = '<s:text name="ERR.INF.190" />';
            $('vendorCode').select();
            $('vendorCode').focus();
            return false;
        }



        if(trim($('status').value) == "") {
//            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.010')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.010"/>';
            $('status').focus();
            return false;
        }

        $('vendorCode').value=trim($('vendorCode').value);
        $('vendorName').value=trim($('vendorName').value);


        return true;
    }

    //kiem tra ma khong duoc chua cac ky tu dac biet
    checkValidFields = function() {
        if(isHtmlTagFormat(trim($('vendorCode').value))) {
//            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.011')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.011"/>';
            $('vendorCode').select();
            $('vendorCode').focus();
            return false;
        }
        if(isHtmlTagFormat(trim($('vendorName').value))) {
//            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.012')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.012"/>';
            $('vendorName').select();
            $('vendorName').focus();
            return false;
        }
        /*
            if(!isValidInput(trim($('name').value), false, false)) {
                $('message').innerHTML = "!!!Lỗi. Trường tên thiết bị chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
                $('name').select();
                $('name').focus();
                return false;
            }

       if(isHtmlTagFormat(trim($('description').value))) {
            $('message').innerHTML = "!!!Lỗi. Trường mô tả không được chứa các thẻ HTML";
            $('description').select();
            $('description').focus();
            return false;
        }*/
        var description = trim($('description').value);
        if(description.length > 1000) {
//            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.013')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.013"/>';
            $('description').select();
            $('description').focus();
            return false;
        }


        return true;
    }
</script>


