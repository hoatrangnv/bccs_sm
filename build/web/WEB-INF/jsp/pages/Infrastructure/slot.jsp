<%--
    Document   : addSlot
    Author     : AnDv--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
            if (request.getAttribute("lstSlot") == null) {
                request.setAttribute("lstSlot", request.getSession().getAttribute("lstSlot"));
            }

            if (request.getAttribute("lstCard") == null) {
                request.setAttribute("lstCard", request.getSession().getAttribute("lstCard"));
            }
            if (request.getAttribute("lstSlotType") == null) {
                request.setAttribute("lstSlotType", request.getSession().getAttribute("lstSlotType"));
            }

            if (request.getAttribute("lstSlotStatus") == null) {
                request.setAttribute("lstSlotStatus", request.getSession().getAttribute("lstSlotStatus"));
            }

            request.setAttribute("contextPath", request.getContextPath());
%>


<s:form action="slotAction" theme="simple"  method="post" id="slotForm">
<s:token/>

    <tags:imPanel title="MSG.INF.044">
        <s:hidden name="slotForm.slotId" id="slotId"/>
        <s:hidden name="slotForm.chassicId" id="chassicId"/>
        <tags:Slot toInputData="true" property="slotForm"/>
        <%--sx:bind id="updateContent" indicator="overlay" events="onclick" listenTopics="updateContent" targets="bodyContent" separateScripts="true" executeScripts="true"/--%>

        <s:if test="#session.toEditSlot != 1">
            <div align="center" style="vertical-align:top; padding-top:10px;">
                <sx:submit  parseContent="true" executeScripts="true"
                            formId="slotForm" loadingText="Processing..."
                            showLoadingText="true" targets="divDisplayInfo"
                            cssStyle="width:80px;"
                            key="MSG.generates.addNew"  beforeNotifyTopics="slotAction/saveSlot"/>
                <sx:submit  parseContent="true" executeScripts="true"
                            formId="slotForm" loadingText="Processing..."
                            showLoadingText="true" targets="divDisplayInfo"
                            cssStyle="width:80px;"
                            key="MSG.generates.find"  beforeNotifyTopics="slotAction/slotSearch"/>
            </div>
        </s:if>
        <s:else>
            <div align="center" style="vertical-align:top; padding-top:10px;">
                <sx:submit parseContent="true" executeScripts="true"
                           formId="slotForm" loadingText="Processing..."
                           showLoadingText="true" targets="divDisplayInfo"
                           cssStyle="width:80px"
                           key="MSG.accept"  beforeNotifyTopics="slotAction/saveSlot"/>
                <sx:submit parseContent="true" executeScripts="true"
                           formId="slotForm" loadingText="Processing..."
                           showLoadingText="true" targets="divDisplayInfo"
                           cssStyle="width:80px"
                           key="MSG.INF.047"  beforeNotifyTopics="slotAction/cancel"/>
            </div>
        </s:else>
    </tags:imPanel>
</s:form>
<br/>
<%--------------List Slot------%>
<s:div id="lstSlot">

    <jsp:include page="slotResult.jsp"/>

</s:div>

<script type="text/javascript">


changeSlotType = function(slotType){
        updateData("${contextPath}/slotAction!updateListCard.do?cardType="+slotType+"&"+token.getTokenParamString());

    }


    changeCard = function(cardId){
        $('maxPort').value = "";
        updateData("${contextPath}/slotAction!displayMaxPort.do?cardId="+cardId);
        
    }

    //ham phuc vu viec phan trang
    pageNavigator = function (ajaxTagId, pageNavigator, pageNumber){
        dojo.widget.byId("updateContent").href = 'slotAction!pageNavigator.do?' + pageNavigator + "=" + pageNumber;
        dojo.event.topic.publish('updateContent');
    }


    //lang nghe, xu ly su kien khi click nut "Them doi tac moi"
    dojo.event.topic.subscribe("slotAction/saveSlot", function(event, widget){
        if (checkRequiredFields()&&checkValidFields()) {

            var type = '${fn:escapeXml(sessionScope.toEditSlot)}';
            //            var msg = '<s:property escapeJavaScript="true"  value="getError('MSG.INF.00029')" />';
            var msg = '<s:text name="MSG.INF.00029" />';

            if (type != 1){
                //                msg = '<s:property escapeJavaScript="true"  value="getError('MSG.INF.00030')" />';
                msg = '<s:text name="MSG.INF.00030" />';
            }

            if (!confirm(getUnicodeMsg(msg))){
                event.cancel = true;
                return;
            }

            widget.href = "slotAction!saveSlot.do?"+token.getTokenParamString();
        } else {
            event.cancel = true;
        }

    });



    dojo.event.topic.subscribe("slotAction/displayChassicCode", function(value, key, text, widget){

        getInputText("getChassicCode.do?chassicId="+key);
        //event: set event.cancel = true, to cancel request
    });

    //lang nghe, xu ly su kien khi click nut "Tim kiem"
    dojo.event.topic.subscribe("slotAction/slotSearch", function(event, widget){
        widget.href = "slotAction!slotSearch.do";

    });

    //lang nghe, xu ly su kien khi click nut "bo qua"
    dojo.event.topic.subscribe("slotAction/cancel", function(event, widget){
        widget.href = "slotAction!displaySlot.do";
        //event: set event.cancel = true, to cancel request
    });
    delSlot = function(slotId) {
        if(confirm(getUnicodeMsg('<s:text name="MSG.INF.00031" />'))){
            gotoAction('divDisplayInfo','slotAction!deleteSlot.do?slotId=' + slotId+'&'+token.getTokenParamString());
        }
    }
    checkRequiredFields = function() {
        if((trim($('cardId').value) == "")||(trim($('cardId').value) == "0")) {

            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.107" />';
            $('cardId').select();
            $('cardId').focus();
            return false;
        }


        if(trim($('slotType').value) == "") {
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.108" />';
            $('slotType').select();
            $('slotType').focus();
            return false;
        }

        if((trim($('slotPosition').value) == "")) {

            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.109" />';
            $('slotPosition').select();
            $('slotPosition').focus();
            return false;
        }

         if((trim($('staPortPosition').value) == "")) {

            $('displayResultMsgClient').innerHTML = '<s:text name="Error. Start position of port on slot must be input!" />';
            $('staPortPosition').select();
            $('staPortPosition').focus();
            return false;
        }

         if((trim($('maxPort').value) == "")) {

            $('displayResultMsgClient').innerHTML = '<s:text name="Error. Max port must be input! " />';
            $('maxPort').select();
            $('maxPort').focus();
            return false;
        }

        if(trim($('status').value) == "") {
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.110" />';
            $('status').select();
            $('status').focus();
            return false;
        }
        return true;
    }
    checkValidFields = function() {
        if(!isInteger(trim($('slotPosition').value))&&(!trim($('slotPosition').value) == "")){
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.111" />';
            $('slotPosition').select();
            $('slotPosition').focus();
            return false;
        }
        $('slotPosition').value = trim($('slotPosition').value);

        if(!isInteger(trim($('maxPort').value))&&(!trim($('maxPort').value) == "")){
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.112" />';
            $('maxPort').select();
            $('maxPort').focus();
            return false;
        }
        $('maxPort').value = trim($('maxPort').value);

//        if(!isInteger(trim($('usedPort').value))&&(!trim($('usedPort').value) == "")){
//            $('displayResultMsgClient').innerHTML = '<s_:property value="getError('ERR.INF.113')" />';
//            $('usedPort').select();
//            $('usedPort').focus();
//            return false;
//        }
//        $('usedPort').value = trim($('usedPort').value);
//
//        if(!isInteger(trim($('freePort').value))&&(!trim($('freePort').value) == "")){
//            $('displayResultMsgClient').innerHTML = '<s_:text name="ERR.INF.114" />';
//            $('freePort').select();
//            $('freePort').focus();
//            return false;
//        }
//        $('freePort').value = trim($('freePort').value);
//
//        if(!isInteger(trim($('invaildPort').value))&&(!trim($('invaildPort').value) == "")){
//            $('displayResultMsgClient').innerHTML = '<s_:text name="ERR.INF.115" />';
//            $('invaildPort').select();
//            $('invaildPort').focus();
//            return false;
//        }
//        $('invaildPort').value = trim($('invaildPort').value);

        if(!isInteger(trim($('staPortPosition').value))&&(!trim($('staPortPosition').value) == "")){
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.116" />';
            $('staPortPosition').select();
            $('staPortPosition').focus();
            return false;
        }
        $('staPortPosition').value = trim($('staPortPosition').value);
        
//        if(!isInteger(trim($('vlanStart').value))&&(!trim($('vlanStart').value) == "")){
//            $('displayResultMsgClient').innerHTML = '<s_:text name="ERR.INF.076" />';
//            $('vlanStart').select();
//            $('vlanStart').focus();
//            return false;
//        }
//
//        if(!isInteger(trim($('vlanStop').value))&&(!trim($('vlanStop').value) == "")){
//            $('displayResultMsgClient').innerHTML = '<s_:text name="ERR.INF.077" />';
//            $('vlanStop').select();
//            $('vlanStop').focus();
//            return false;
//        }
        

        return true;
    }
</script>



