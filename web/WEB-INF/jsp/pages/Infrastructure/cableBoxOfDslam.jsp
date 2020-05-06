<%-- 
    Document   : cableBoxOfDslam
    Created on : Apr 13, 2009, 8:59:38 AM
    Author     : TheTM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="cableboxAction" id="cableBoxForm" method="post" theme="simple">
<s:token/>


    <tags:imPanel title = "MSG.INF.039">
        <div>
            <tags:displayResult id="displayResultMsgClient" property="message" propertyValue="paramMsg" type="key" />
        </div>

        <tags:CableBoxOfDslam readOnly="false" property="cableBoxForm"/>

        <s:hidden name="cableBoxForm.cableBoxId" id="cableBoxForm.cableBoxId"/>

        <s:if test="#request.dslamId == null ||  #request.dslamId  * 1 <= 0 ">
            <s:hidden name="cableBoxForm.dslamId" id="cableBoxForm.dslamId"/>
        </s:if>
        <s:else>
            <s:hidden name="cableBoxForm.dslamId" id="cableBoxForm.dslamId" value = "%{#request.dslamId}"/>
        </s:else>


        <div align="center" style="padding-bottom:5px;">

            <tags:submit targets="divDisplayInfo" formId="cableBoxForm"
                         showLoadingText="true" cssStyle="width:80px;"
                         value="MSG.find" preAction="cableboxAction!searchCableBoxOfDslam.do"
                         />


            <s:if test="(cableBoxForm == null) || (cableBoxForm.cableBoxId == null) || (cableBoxForm.cableBoxId.compareTo(0L) <= 0)">

                <tags:submit targets="divDisplayInfo" formId="cableBoxForm"
                             showLoadingText="true" cssStyle="width:80px;"
                             confirm="true" confirmText="MSG.INF.CableBox.ConfirmAdd"
                             validateFunction="checkValidate();"
                             value="MSG.GOD.010" preAction="cableboxAction!addNewCableBoxOfDslam.do"

                             />
            </s:if>
            <s:else>
                <tags:submit targets="divDisplayInfo" formId="cableBoxForm"
                             showLoadingText="true" cssStyle="width:80px;"
                             confirm="true" confirmText="MSG.INF.CableBox.ConfirmEdit"
                             value="MSG.generates.edit" preAction="cableboxAction!editCableBoxOfDslam.do"
                             validateFunction="checkValidate();"
                             />
                <tags:submit targets="divDisplayInfo" formId="cableBoxForm"
                             showLoadingText="true" cssStyle="width:80px;"
                             value="MSG.cancel2" preAction="cableboxAction!Cancel.do"
                             />
            </s:else>
        </div>

    </tags:imPanel>
    <br>
    <div id="listcablebox">
        <tags:imPanel title= "MSG.INF.040">
            <jsp:include page="listCableBoxOfDslam.jsp"/>
        </tags:imPanel>
    </div>

</s:form>

<script type="text/javascript">
    pageNavigator = function (ajaxTagId, pageNavigator, pageNumber){
        dojo.widget.byId("updateContent").href = 'cableboxAction!pageNavigator.do?' + pageNavigator + "=" + pageNumber;
        dojo.event.topic.publish('updateContent');
    }
    
    var code=document.getElementById('cableBoxForm.code');
    var name=document.getElementById('cableBoxForm.name');
    var address=document.getElementById('cableBoxForm.address');
    var maxPorts=document.getElementById('cableBoxForm.maxPorts');
    var usedPorts=document.getElementById('cableBoxForm.usedPorts');
    var reservePort=document.getElementById('cableBoxForm.reservedPort');
    var status=document.getElementById('cableBoxForm.status');

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
    <%-- var _myWidget = dojo.widget.byId("dslamCode");
     var textBoardsCode = trim(_myWidget.textInputNode.value);

        if(textBoardsCode == null || textBoardsCode == "") {
            $('displayResultMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.017')" />';
            _myWidget.textInputNode.select();
            _myWidget.textInputNode.focus();
            return false;
        }--%>
                if($('cableBoxForm.status').value ==null || $('cableBoxForm.status').value.trim() == ""){
                    $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.055')" />';
                    $('cableBoxForm.status').select();
                    $('cableBoxForm.status').focus();
                    return false;
                }
                if($('cableBoxForm.maxPorts').value ==null || $('cableBoxForm.maxPorts').value.trim() == ""){
                    $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.056')" />';
                    $('cableBoxForm.maxPorts').select();
                    $('cableBoxForm.maxPorts').focus();
                    return false;
                }

    <%-- if(!isInteger(trim($('cableBoxForm.maxPorts').value))) {
        $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.LST.110')"/>';
        $('cableBoxForm.maxPorts').select();
        $('cableBoxForm.maxPorts').focus();
        return false;
    }--%>
            if($('cableBoxForm.usedPorts').value ==null || $('cableBoxForm.usedPorts').value.trim() == ""){
                $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.057')" />';
                $('cableBoxForm.usedPorts').select();
                $('cableBoxForm.usedPorts').focus();
                return false;
            }
            if($('address').value ==null || $('address').value.trim() == ""){
                $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.058')" />';
                $('address').select();
                $('address').focus();
                return false;
            }

            if(maxPorts.value <=0){
                $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.INF.CableBox.Port1')" />';
                $('cableBoxForm.maxPorts').select();
                $('cableBoxForm.maxPorts').focus();
                return false;

            }

            if(usedPorts.value <=0){
                $( 'displayResultMsgClient' ).innerHTML =  '<s:property escapeJavaScript="true"  value="getError('MSG.INF.CableBox.Port2')" />';
                $('cableBoxForm.usedPorts').select();
                $('cableBoxForm.usedPorts').focus();
                return false;

            }
            if(reservePort.value <=0 && reservePort.value!=null && reservePort.value.trim()!=""){
                $( 'displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.INF.CableBox.Port3')" />';
                $('cableBoxForm.reservedPort').select();
                $('cableBoxForm.reservedPort').focus();
                return false;

            }

            return true;
        }

        deleteCableBoxOfDslam = function(cableBoxId) {
            if(confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.CableBox.ConfirmDelete')" />'))){
                gotoAction('divDisplayInfo','cableboxAction!deleteCableBoxOfDslam.do?cableBoxId=' + cableBoxId+'&'+token.getTokenParamString());
            }
        }

</script>

