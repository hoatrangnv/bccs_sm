<%-- 
    Document   : bras
    Created on : Apr 27, 2009, 4:01:31 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
            if (request.getAttribute("brasList") == null) {
                request.setAttribute("brasList", request.getSession().getAttribute("brasList"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>


<tags:imPanel title="MSG.bras.info">

    <!-- hien thi message -->
    <div>
        <tags:displayResult id="message" property="returnMsg" propertyValue="returnMsgParam" type="key"/>
    </div>

    <!-- thong tin bras -->
    <s:form action="brasAction" theme="simple" enctype="multipart/form-data"  method="post" id="brasForm">
        <s:hidden name="brasForm.brasId" id="brasForm.brasId"/>

        <table class="inputTbl6Col" style="width:100%" >
            <tr>
                <td class="label"><tags:label key="MSG.bras.code" required="true"/> </td>
                <td class="text">
                    <s:textfield name="brasForm.code" id="code" maxlength="30" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.bras.name" required="true"/> </td>
                <td class="text">
                    <s:textfield name="brasForm.name" id="name" maxlength="50" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.device" required="true"/> </td>
                <td class="text">
                    <tags:imSelect name="brasForm.equipmentId"
                                   id="equipmentId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.bras.ChooseDevice"
                                   list="lstEquipment"
                                   listKey="equipmentId" listValue="name"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.ip" required="true"/> </td>
                <td class="text">
                    <s:textfield name="brasForm.ip" id="ip" maxlength="20" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.AAA_IP" /></td>
                <td class="text">
                    <s:textfield name="brasForm.aaaIp" id="aaaIp" maxlength="20" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.generates.status" required="true"/> </td>
                <td class="text">
                    <tags:imSelect name="brasForm.status" id="status"
                                   cssClass="txtInputFull"
                                   list="1:MSG.active,
                                   0:MSG.inactive"
                                   headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseStatus"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.slot" /></td>
                <td class="text">
                    <s:textfield name="brasForm.slot" id="slot" maxlength="10" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.port" /></td>
                <td class="text">
                    <s:textfield name="brasForm.port" id="port" maxlength="10" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.discription" /></td>
                <td colspan="5" class="text">
                    <s:textarea name="brasForm.description" id="description" cols="5" cssClass="txtInputFull"  />
                </td>
            </tr>
        </table>
        <s:token/>
    </s:form>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-bottom:5px;">
        <s:if test="#session.toEditBras != 1">
            <tags:submit
                targets="bodyContent" formId="brasForm"
                showLoadingText="true" cssStyle="width:85px;" validateFunction="checkValidate()"
                value="MSG.generates.addNew" preAction="brasAction!addNewBras.do"
                />
            <tags:submit
                targets="bodyContent" formId="brasForm"
                showLoadingText="true" cssStyle="width:85px;"
                value="MSG.find" preAction="brasAction!searchBras.do"
                />

            <%--<sx:submit  parseContent="true" executeScripts="true"
                        formId="brasForm" loadingText="Đang thực hiện..."
                        showLoadingText="true" targets="bodyContent"
                        cssStyle="width:80"
                        value="Thêm mới"  beforeNotifyTopics="brasAction/addNewBras"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="brasForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80"
                       value="Tìm kiếm"  beforeNotifyTopics="brasAction/searchBras"/>--%>

        </s:if>
        <s:else>
            <tags:submit
                targets="bodyContent" formId="brasForm"
                showLoadingText="true" cssStyle="width:85px;" validateFunction="checkValidate()"
                value="MSG.accept" preAction="brasAction!editBras.do"
                />
            <tags:submit
                targets="bodyContent" formId="brasForm"
                showLoadingText="true" cssStyle="width:85px;"
                value="MSG.INF.047" preAction="brasAction!preparePage.do"
                />

            <%--<sx:submit parseContent="true" executeScripts="true"
                       formId="brasForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       value="Đồng ý"  beforeNotifyTopics="brasAction/editBras"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="brasForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       value="Bỏ qua"  beforeNotifyTopics="brasAction/cancel"/>--%>
        </s:else>
    </div>

</tags:imPanel>


<br />

<div id="brasList">
    <tags:imPanel title="MSG.bras.list">

        <jsp:include page="listBras.jsp"/>
    </tags:imPanel>
</div>


<script type="text/javascript">
    
    //focus vao truong dau tien
    $('code').select();
    $('code').focus();

    //lang nghe, xu ly su kien khi click nut "Tim kiem"
    dojo.event.topic.subscribe("brasAction/searchBras", function(event, widget){
        widget.href = "brasAction!searchBras.do?"+token.getTokenParamString();
        //widget.href = "brasAction!searchBras.do";
    });

    checkValidate = function() {
        var result = checkRequiredFields() && checkValidFields();
        return result;
    }

    //lang nghe, xu ly su kien khi click nut "Them moi"
    dojo.event.topic.subscribe("brasAction/addNewBras", function(event, widget){
        if (checkRequiredFields() && checkValidFields()) {
            if (confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.00012')" />'))){
                widget.href = "brasAction!addNewBras.do?"+token.getTokenParamString();
            }
            else{
                event.cancel = true;
            }
            
        } else {
            event.cancel = true;
        }
    });

    //lang nghe, xu ly su kien khi click nut "Đong y"
    dojo.event.topic.subscribe("brasAction/editBras", function(event, widget){
        if (checkRequiredFields() && checkValidFields()) {
            if (confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.00013')" />'))){
                widget.href = "brasAction!editBras.do?"+token.getTokenParamString();
            }
            else{
                event.cancel = true;
            }

            
        } else {
            event.cancel = true;
        }

    });

    //lang nghe, xu ly su kien khi click nut "bo qua"
    dojo.event.topic.subscribe("brasAction/cancel", function(event, widget){
        widget.href = "brasAction!preparePage.do?"+token.getTokenParamString();
        //widget.href = "brasAction!preparePage.do";
        //event: set event.cancel = true, to cancel request
    });

    confirmDelete = function (brasId){
        if(confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.00014')" />'))){
            gotoAction('bodyContent', 'brasAction!deleteBras.do?brasId='+brasId+'&'+token.getTokenParamString());
        }
    }
    
    checkRequiredFields = function() {
        if(trim($('code').value) == "") {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.035')" />';
            $('code').select();
            $('code').focus();
            return false;
        }

        if(trim($('name').value) == "") {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.036')" />'
            $('name').select();
            $('name').focus();
            return false;
        }
        if(trim($('equipmentId').value) == "") {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.037')" />'
            $('equipmentId').select();
            $('equipmentId').focus();
            return false;
        }
        if(trim($('ip').value) == "") {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.038')" />'
            $('ip').select();
            $('ip').focus();
            return false;
        }        
        if(trim($('status').value) == "") {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.039')" />'
            $('status').select();
            $('status').focus();
            return false;
        }



        return true;
    }
    
    //kiem tra ma khong duoc chua cac ky tu dac biet
    checkValidFields = function() {
        $('code').value=trim($('code').value);
        $('ip').value=trim($('ip').value);
        $('aaaIp').value=trim($('aaaIp').value);

        /* if(!isValidInput(trim($('code').value), false, false)) {
            $('message').innerHTML = "!!!Lỗi. Trường Mã DSLAM chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
            $('code').select();
            $('code').focus();
            return false;
        }*/
        if(isHtmlTagFormat(trim($('code').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.040')" />';
            $('code').select();
            $('code').focus();
            return false;
        }


        if(!isFormalCharacter(trim($('code').value))){
            //alert( 'aa');
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.187')" />';
            $('code').select();
            $('code').focus();
            return false;
        }
        
        if (trim($('code').value).match(" ")){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.041')" />';
            $('code').select();
            $('code').focus();
            return false;
        }

        if(isHtmlTagFormat(trim($('ip').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.042')" />';
            $('ip').select();
            $('ip').focus();
            return false;
        }
        if(isHtmlTagFormat(trim($('aaaIp').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.043')" />';
            $('aaaIp').select();
            $('aaaIp').focus();
            return false;
        }
        var description = trim($('description').value);
        if(description.length > 100) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.044')" />';
            $('description').select();
            $('description').focus();
            return false;
        }


        return true;
    }

</script>
