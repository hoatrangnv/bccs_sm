<%--
    Document   : card
    Author     : AnDv--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display" %>

<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            if (request.getAttribute("lstCard") == null) {
                request.setAttribute("lstCard", request.getSession().getAttribute("lstCard"));
            }
            if (request.getAttribute("lstEquipment") == null) {
                request.setAttribute("lstEquipment", request.getSession().getAttribute("lstEquipment"));
            }
            if (request.getAttribute("lstCardType") == null) {
                request.setAttribute("lstCardType", request.getSession().getAttribute("lstCardType"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.card.info">

    <div>
        <tags:displayResult id="message" property="returnMsg" propertyValue="returnMsgParam" type="key"/>
    </div>

    <s:form action="cardAction" theme="simple"  method="post" id="cardForm">
        <s:hidden name="cardForm.cardId" id="cardId"/>

        <tags:Card toInputData="true" property="cardForm"/>
        <sx:bind id="updateContent" indicator="overlay" events="onclick" listenTopics="updateContent" targets="bodyContent" separateScripts="true" executeScripts="true"/>
        <div align="center" style="padding-bottom:5px;">

            <s:if test="#session.toEditCard != 1">
                <tags:submit
                    targets="bodyContent" formId="cardForm"
                    showLoadingText="true" cssStyle="width:85px;" validateFunction="checkValidate();"
                    value="MSG.generates.addNew" preAction="cardAction!addNewCard.do"
                    />
                <tags:submit
                    targets="bodyContent" formId="cardForm"
                    showLoadingText="true" cssStyle="width:85px;"
                    value="MSG.find" preAction="cardAction!cardSearch.do"
                    />

                <%--<sx:submit  parseContent="true" executeScripts="true"
                            formId="cardForm" loadingText="Đang thực hiện..."
                            showLoadingText="true" targets="bodyContent"
                            cssStyle="width:80px;"
                            value="Thêm mới"  beforeNotifyTopics="cardAction/addNewCard"/>
                <sx:submit  parseContent="true" executeScripts="true"
                            formId="cardForm" loadingText="Đang thực hiện..."
                            showLoadingText="true" targets="bodyContent"
                            cssStyle="width:80px;"
                            value="Tìm kiếm"  beforeNotifyTopics="cardAction/cardSearch"/>--%>
            </s:if>
            <s:else>
                <tags:submit
                    targets="bodyContent" formId="cardForm"
                    showLoadingText="true" cssStyle="width:85px;" validateFunction="checkValidate();"
                    value="MSG.accept" preAction="cardAction!editCard.do"
                    />
                <tags:submit
                    targets="bodyContent" formId="cardForm"
                    showLoadingText="true" cssStyle="width:85px;"
                    value="MSG.INF.047" preAction="cardAction!preparePage.do"
                    />

                <%--<sx:submit parseContent="true" executeScripts="true"
                           formId="cardForm" loadingText="Đang thực hiện..."
                           showLoadingText="true" targets="bodyContent"
                           cssStyle="width:80px"
                           value="Đồng ý"  beforeNotifyTopics="cardAction/editCard"/>
                <sx:submit parseContent="true" executeScripts="true"
                           formId="cardForm" loadingText="Đang thực hiện..."
                           showLoadingText="true" targets="bodyContent"
                           cssStyle="width:80px"
                           value="Bỏ qua"  beforeNotifyTopics="cardAction/cancel"/>--%>

            </s:else>
        </div>
        <s:token/>
    </s:form>

</tags:imPanel>
<br/>


<%--------------List Card------%>
<s:div id="lstCard">

    <jsp:include page="cardResult.jsp"/>

</s:div>

<script type="text/javascript">
    $('cardType').select();
    $('cardType').focus();
    //ham phuc vu viec phan trang
    pageNavigator = function (ajaxTagId, pageNavigator, pageNumber){
        dojo.widget.byId("updateContent").href = 'cardAction!pageNavigator.do?' + pageNavigator + "=" + pageNumber;
        dojo.event.topic.publish('updateContent');
    }
    //lang nghe, xu ly su kien khi click nut "Tim kiem"

    dojo.event.topic.subscribe("cardAction/cardSearch", function(event, widget){
        widget.href = "cardAction!cardSearch.do?"+token.getTokenParamString();

    });

    checkValidate = function() {
        var result = checkRequiredFields() && checkValidFields();
        return result;
    }
    //lang nghe, xu ly su kien khi click nut "Them doi tac moi"
    dojo.event.topic.subscribe("cardAction/addNewCard", function(event, widget){

        if (checkRequiredFields() && checkValidFields()) {
            if (confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.00015')" />'))){
                widget.href = "cardAction!addNewCard.do?"+token.getTokenParamString();
            }
            else{
                event.cancel = true;
            }

            
        } else {
            event.cancel = true;
        }


        //event: set event.cancel = true, to cancel request
    });
    dojo.event.topic.subscribe("cardAction/editCard", function(event, widget){

        if (checkRequiredFields() && checkValidFields()) {
            if (confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.00016')" />'))){
                widget.href = "cardAction!editCard.do?"+token.getTokenParamString();
            }
            else{
                event.cancel = true;
            }


        } else {
            event.cancel = true;
        }


        //event: set event.cancel = true, to cancel request
    });


    //lang nghe, xu ly su kien khi click nut "Ðong y"

    //lang nghe, xu ly su kien khi click nut "bo qua"
    dojo.event.topic.subscribe("cardAction/cancel", function(event, widget){
        widget.href = "cardAction!preparePage.do?"+token.getTokenParamString();
        //event: set event.cancel = true, to cancel request
    });

    checkRequiredFields = function() {
        //truong tên
        if(trim($('cardType').value) == "") {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.059')" />';
            $('cardType').select();
            $('cardType').focus();
            return false;
        }
        $('cardType').value = trim($('cardType').value);

        if(trim($('code').value) == "") {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.060')" />';
            $('code').select();
            $('code').focus();
            return false;
        }
        $('code').value = trim($('code').value);

        if(trim($('name').value) == "") {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.061')" />';
            $('name').select();
            $('name').focus();
            return false;
        }
        $('name').value = trim($('name').value);

        //trường thiết bị
        if(trim($('equipmentId').value) == "") {
            $('message').innerHTML ='<s:property escapeJavaScript="true"  value="getError('ERR.INF.062')" />';
            $('equipmentId').select();
            $('equipmentId').focus();
            return false;
        }
        if(trim($('status').value) == "") {
            $('message').innerHTML ='<s:property escapeJavaScript="true"  value="getError('ERR.INF.063')" />';
            $('status').select();
            $('status').focus();
            return false;
        }

        //trương nhà cung cấp
        $('totalPort').value = trim($('totalPort').value);

        return true;
    }
    //kiem tra ma khong duoc chua cac ky tu dac biet
    checkValidFields = function() {
        if(isHtmlTagFormat(trim($('name').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.064')" />';
            $('name').select();
            $('name').focus();
            return false;
        }

     
        
        if(!isValidInput(trim($('name').value), false, false)) {
            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.191')" />';

            $('name').select();
            $('name').focus();
            return false;
        }

        if(!isValidInput(trim($('code').value), false, false)) {
            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.064')" />';

            $('code').select();
            $('code').focus();
            return false;
        }
        $('code').value=trim($('code').value);

        if(isHtmlTagFormat(trim($('code').value))) {
            $('message').innerHTML ='<s:property escapeJavaScript="true"  value="getError('ERR.INF.064')" />';
            $('code').select();
            $('code').focus();
            return false;
        }
        /*
        if(isHtmlTagFormat(trim($('description').value))) {
            $('message').innerHTML = "!!!Lỗi. Trường mô tả không được chứa các thẻ HTML";
            $('description').select();
            $('description').focus();
            return false;
        }*/
        var description = trim($('description').value);
        if(description.length > 100) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.065')" />';
            $('description').select();
            $('description').focus();
            return false;
        }


        return true;
    }


    confirmDelete = function (cardId){
        if(confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.00017')" />'))){
            gotoAction('bodyContent', 'cardAction!deleteCard.do?cardId='+cardId+'&'+token.getTokenParamString());
        }
    }
</script>




