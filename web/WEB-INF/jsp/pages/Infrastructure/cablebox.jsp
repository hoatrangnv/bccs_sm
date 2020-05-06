<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : cable_box
    Created on : Apr 13, 2009, 8:59:38 AM
    Author     : TheTM
    Desc       : thong tin ve cap nhanh
    Modified   : tamdt1, 29/09/2010
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title = "MSG.INF.039">
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="displayResultMsgClient" property="message" propertyValue="paramMsg" type="key" />
    </div>

    <!-- thong tin cap nhanh -->
    <div class="divHasBorder">
        <s:form action="cableboxAction" id="cableBoxForm" method="post" theme="simple">
<s:token/>

            <tags:CableBox readOnly="false" property="cableBoxForm"/>
            <s:hidden name="cableBoxForm.cableBoxId" id="cableBoxForm.cableBoxId"/>
            <s:if test="#request.boardId == null ||  #request.boardId  * 1 <= 0 ">
                <s:hidden name="cableBoxForm.boardId" id="cableBoxForm.boardId"/>
            </s:if>
            <s:else>
                <s:hidden name="cableBoxForm.boardId" id="cableBoxForm.boardId" value = "%{#request.boardId}"/>
            </s:else>
        </s:form>
        <div align="center" style="padding-bottom:5px;">
            <s:if test="(cableBoxForm == null) || (cableBoxForm.cableBoxId == null) || (cableBoxForm.cableBoxId.compareTo(0L) <= 0)">
                <tags:submit targets="divDisplayInfo"
                             formId="cableBoxForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             validateFunction="checkValidate();"
                             value="MSG.generates.addNew"
                             preAction="cableboxAction!addNewCableBox.do"/>

                <tags:submit targets="divDisplayInfo"
                             formId="cableBoxForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             value="MSG.find"
                             preAction="cableboxAction!searchCableBox.do"/>
                
                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.102'))}" style="width:80px;" onclick="resetForm();"/>

            </s:if>
            <s:else>
                <tags:submit targets="divDisplayInfo"
                             formId="cableBoxForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             validateFunction="checkValidate();"
                             value="MSG.accept"
                             preAction="cableboxAction!editCableBox.do"/>

                <tags:submit targets="divDisplayInfo"
                             formId="cableBoxForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             value="MSG.cancel2"
                             preAction="dslamAction!getListCableBox.do"/>
            </s:else>
        </div>
    </div>

    <div id="listcablebox">
        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.INF.040'))}</legend>
            <jsp:include page="listCableBox.jsp"/>
        </fieldset>
    </div>


</tags:imPanel>

<script type="text/javascript">
    pageNavigator = function (ajaxTagId, pageNavigator, pageNumber){
        dojo.widget.byId("updateContent").href = 'cableboxAction!pageNavigator.do?' + pageNavigator + "=" + pageNumber;
        dojo.event.topic.publish('updateContent');
    }
    var code=document.getElementById('cableBoxForm.code');
    var name=document.getElementById('cableBoxForm.name');
    var address=document.getElementById('cableBoxForm.address');
    var maxPorts=document.getElementById('cableBoxForm.maxPorts');
//    var usedPorts=document.getElementById('cableBoxForm.usedPorts');
    var status=document.getElementById('cableBoxForm.status');


    //ham reset form
    resetForm = function() {
        $('cableBoxForm.code').value = "";
        $('cableBoxForm.name').value = "";
        $('cableBoxForm.maxPorts').value = "";
//        $('cableBoxForm.usedPorts').value = "";
        $('cableBoxForm.reservedPort').value = "";
//        $('cableBoxForm.x').value = "";
//        $('cableBoxForm.y').value = "";
        $('cableBoxForm.status').value = "";
        $('address').value = "";

        $('cableBoxForm.code').focus();
    }

    checkValidate=function(){

        $('displayResultMsgClient' ).innerHTML = "";
        $('cableBoxForm.code').value=trim($('cableBoxForm.code').value);

        if($('cableBoxForm.code').value ==null || $('cableBoxForm.code').value.trim() == ""){
            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.052')" />';
            $('cableBoxForm.code').select();
            $('cableBoxForm.code').focus();
            return false;
        }
        /*
        if(!isValidInput($('cableBoxForm.code').value, false, false)) {
            $('displayResultMsgClient').innerHTML = "!!!Lỗi. Mã cáp nhánh chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
            $('cableBoxForm.code').select();
            $('cableBoxForm.code').focus();
            return false;
        }*/
        if($('cableBoxForm.name').value ==null || $('cableBoxForm.name').value.trim() == ""){
            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.053')" />';
            $('cableBoxForm.name').select();
            $('cableBoxForm.name').focus();
            return false;
        }
        /*
        if(checkingSpecialCharacter(trim($('cableBoxForm.name').value))) {
            $('displayResultMsgClient').innerHTML = "!!!Lỗi. Tên cáp nhánh không được chứa các ký tự đặc biệt";
            $('cableBoxForm.name').select();
            $('cableBoxForm.name').focus();
            return false;
        }*/
        
        if($('cableBoxForm.maxPorts').value ==null || $('cableBoxForm.maxPorts').value.trim() == ""){
            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.056')" />';
            $('cableBoxForm.maxPorts').select();
            $('cableBoxForm.maxPorts').focus();
            return false;
        }
        if($('cableBoxForm.reservedPort').value ==null || $('cableBoxForm.reservedPort').value.trim() == ""){
            $( 'displayResultMsgClient' ).innerHTML = 'Error. reservedPort must not empty!';
            $('cableBoxForm.reservedPort').select();
            $('cableBoxForm.reservedPort').focus();
            return false;
        }
//        if($('cableBoxForm.usedPorts').value ==null || $('cableBoxForm.usedPorts').value.trim() == ""){
//            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.057')" />';
//            $('cableBoxForm.usedPorts').select();
//            $('cableBoxForm.usedPorts').focus();
//            return false;
//        }
        if($('cableBoxForm.status').value ==null || $('cableBoxForm.status').value.trim() == ""){
            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.055')" />';
            $('cableBoxForm.status').select();
            $('cableBoxForm.status').focus();
            return false;
        }
//        if($('address').value ==null || $('address').value.trim() == ""){
//            $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.058')" />';
//            $('address').select();
//            $('address').focus();
//            return false;
//        }
        return true;
    }

    dojo.event.topic.subscribe("cableboxAction/addNewCableBox", function(event, widget){
        if (!checkValidate()) {
            event.cancel = true;
        }
        widget.href = "cableboxAction!addNewCableBox.do?"+token.getTokenParamString();
    });
    dojo.event.topic.subscribe("cableboxAction/searchCableBox", function(event, widget){

        widget.href = "cableboxAction!searchCableBox.do";

    });
    dojo.event.topic.subscribe("cableboxAction/editCableBox", function(event, widget){
        if (!checkValidate()) {
            event.cancel = true;
        }
        widget.href = "cableboxAction!editCableBox.do?"+token.getTokenParamString();

    });
    dojo.event.topic.subscribe("cableboxAction/cancel", function(event, widget){

        widget.href = "cableboxAction!preparePage.do";

    });
    dojo.event.topic.subscribe("cableboxAction/displayboardsName", function(value, key, text, widget){
        document.getElementById('cableBoxForm.BoardsName').value = key;
    });
    delcablebox = function(cableBoxId) {
        if(confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.Confirm37')" />'))){
            gotoAction('divDisplayInfo','cableboxAction!deleteCableBox.do?cableBoxId=' + cableBoxId + '&' + token.getTokenParamString());
        }
    }
</script>

