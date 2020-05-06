<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : addNewBoard
    Created on : Mar 19, 2009, 8:39:06 AM
    Author     : NamNt
    Desc       : them moi cap tong
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


<tags:imPanel title = "MSG.INF.036">
    <!-- hien thi thong tin message -->
    <div>
        <tags:displayResult id="displayResultMsgClient" property="message" propertyValue="paramMsg" type="key" />
    </div>

    <!-- thong tin cap tong -->
    <div class="divHasBorder">
        <s:form action="BoardsAction" theme="simple" enctype="multipart/form-data"  method="post" id="BoardsForm">
<s:token/>

            <s:hidden name="BoardsForm.boardId" id="BoardsForm.boardId"/>
            <s:hidden name="BoardsForm.dslamcode" id="BoardsForm.dslamcode"/>
            <s:hidden name="BoardsForm.province" id="BoardsForm.province"/>

            <s:if test="#request.dslamId == null ||  #request.dslamId  * 1 <= 0 ">
                <s:hidden name="BoardsForm.dslamId" id="BoardsForm.dslamId"/>
            </s:if>
            <s:else>
                <s:hidden name="BoardsForm.dslamId" id="BoardsForm.dslamId" value = "%{#request.dslamId}"/>
            </s:else>

            <tags:Boards toInputData="true" property="BoardsForm"/>

            <div align="center" style="padding-bottom:5px;">
                <%--
                            <tags:submit targets="divDisplayInfo" formId="BoardsForm"
                                     showLoadingText="true" cssStyle="width:80px;"
                                     value="MSG.generates.find" preAction="BoardsAction!searchBoards.do"
                                     />
                --%>

                <s:if test="BoardsForm.boardId == null || BoardsForm.boardId * 1 <= 0 ">
                    <tags:submit targets="divDisplayInfo"
                                 formId="BoardsForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 validateFunction="checkValidate();"
                                 value="MSG.generates.addNew"
                                 preAction="BoardsAction!addBoards.do"/>

                    <tags:submit targets="divDisplayInfo"
                                 formId="BoardsForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 value="MSG.find" 
                                 preAction="BoardsAction!searchBoards.do"/>

                    <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.102'))}" style="width:80px;" onclick="resetForm();"/>

                </s:if>
                <s:else>
                    <tags:submit targets="divDisplayInfo"
                                 formId="BoardsForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 validateFunction="checkValidate();"
                                 value="MSG.accept"
                                 preAction="BoardsAction!editBoards.do"/>

                    <tags:submit targets="divDisplayInfo"
                                 formId="BoardsForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 value="MSG.cancel2" 
                                 preAction="dslamAction!getListBoard.do"/>
                </s:else>
            </div>


        </s:form>
    </div>

    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.INF.037'))}</legend>
        <div id="listBoards">
            <jsp:include page="listBoards.jsp"/>
        </div>
    </fieldset>

</tags:imPanel>

<script type="text/javascript">
    
    //ham reset form
    resetForm = function() {
        $('code').value = "";
        $('name').value = "";
        $('maxPorts').value = "";
        //$('usedPorts').value = "";
        $('reservedPort').value = "";
        //$('x').value = "";
        //$('y').value = "";
        $('status').value = "";
        $('address').value = "";

        $('code').focus();
    }


    checkValidate = function(){

        $('displayResultMsgClient' ).innerHTML = "";

       
        $('code').value=trim($('code').value);

        /*if(!isValidInput($('code').value, false, false)) {
            $('displayResultMsgClient').innerHTML = "!!!Lỗi. Mã cáp tổng chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
            $('code').select();
            $('code').focus();
            return false;
        }*/

        if($('code').value ==null || $('code').value.trim() == ""){
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.INF.016"/>';
            $('code').select();
            $('code').focus();
            return false;
        }

        if($('name').value ==null || $('name').value.trim() == ""){
            $( 'displayResultMsgClient' ).innerHTML ='<s:text name="ERR.INF.018"/>';
            $('name').select();
            $('name').focus();
            return false;
        }/*
        if(checkingSpecialCharacter(trim($('BoardsForm.name').value))) {
            $('displayResultMsgClient').innerHTML = "!!!Lỗi. Tên cáp tổng không được chứa các ký tự đặc biệt";
            $('BoardsForm.name').select();
            $('BoardsForm.name').focus();
            return false;
        }*/
        if($('maxPorts').value ==null || $('maxPorts').value.trim() == ""){
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.INF.019"/>';
            $('maxPorts').select();
            $('maxPorts').focus();
            return false;
        }
        //        if($('reservedPort').value ==null || $('reservedPort').value.trim() == ""){
        //            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="Error. ReservedPort must not empty!"/>';
        //            $('reservedPort').select();
        //            $('reservedPort').focus();
        //            return false;
        //        }
        //        if($('usedPorts').value ==null || $('usedPorts').value.trim() == ""){
        //            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.INF.020"/>';
        //            $('usedPorts').select();
        //            $('usedPorts').focus();
        //            return false;
        //        }
        if($('status').value ==null || $('status').value.trim() == ""){
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.INF.021"/>';
            $('status').select();
            $('status').focus();
            return false;
        }
        //        if($('address').value ==null || $('address').value.trim() == ""){
        //            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.INF.022"/>';
        //            $('address').select();
        //            $('address').focus();
        //            return false;
        //        }



        var boardId = ('BoardsForm.boardId').value;
        var msg = "";
        if (boardId != null && boardId * 1 > 0){
            msg = "Do you want to edit current Terminal Box?";
        }else{
            msg = "Do you want to create new Terminal Box?";
            if(!confirm("<s:text name='MSG.confirm.add'/>")){
                return false;
            }
        }

        if(!confirm(msg)){
            return false;
        }
        
        return true;

    }
    //lang nghe, xu ly su kien khi click nut "Them cap tong moi"
    dojo.event.topic.subscribe("BoardsAction/addBoards", function(event, widget){

        if (!checkValidate()) {
            event.cancel = true;
            return ;
        }

        if(!confirm('Do you want to create new Terminal Box?')){
            event.cancel = true;
            return ;
        }

        widget.href = "BoardsAction!addBoards.do?"+token.getTokenParamString();

    });

    dojo.event.topic.subscribe("BoardsAction/editBoards", function(event, widget){
        if (!checkValidate()) {
            event.cancel = true;
            return ;
        }

        if(!confirm('Do you want to edit current Terminal Box?')){
            event.cancel = true;
            return ;
        }


        widget.href = "BoardsAction!editBoards.do?"+token.getTokenParamString();

    });
    dojo.event.topic.subscribe("BoardsAction/searchBoards", function(event, widget){

        widget.href = "BoardsAction!searchBoards.do";

    });
    dojo.event.topic.subscribe("BoardsAction/cancel", function(event, widget){

        widget.href = "BoardsAction!preparePage.do";

    });
    dojo.event.topic.subscribe("BoardsAction/displayProvinceName", function(value, key, text, widget){
        document.getElementById('provinceName').value = key;
        updateData('getListDslamcode.do?province='+ value);
    });
    dojo.event.topic.subscribe("BoardsAction/displayDSLName", function(value, key, text, widget){
        document.getElementById('BoardsForm.DSLName').value = key;
    });

    delBoard = function(boardId) {
        //        if(confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.0007')"/>'))){
        if(confirm(getUnicodeMsg('<s:text name="MSG.INF.0007"/>'))){
            gotoAction('divDisplayInfo','BoardsAction!deleteBoards.do?boardId=' + boardId+'&'+token.getTokenParamString());
        }
    }
</script>

<%-- var _myWidgetProvince = dojo.widget.byId("province");
        var _myWidgetDslamCode = dojo.widget.byId("dslamcode");
        var textProvince = trim(_myWidgetProvince.textInputNode.value);

        if(!isValidInput(textProvince, false, false)) {
            $('displayResultMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.015')"/>';
            _myWidgetProvince.textInputNode.select();
            _myWidgetProvince.textInputNode.focus();
            return false;
        }

        /*


        var textDslamCode = trim(_myWidgetDslamCode.textInputNode.value);

        if(textDslamCode == null ||textDslamCode == "") {
            $('displayResultMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.017')"/>';
            _myWidgetDslamCode.textInputNode.select();
            _myWidgetDslamCode.textInputNode.focus();
            return false;
        }
--%>
