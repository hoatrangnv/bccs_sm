<%--
    Document   : Chassic
    Created on : Apr 17, 2009, 10:28:30 AM
    Author     : NamNX
--%>
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
            if (request.getAttribute("listChassic") == null) {
                request.setAttribute("listChassic", request.getSession().getAttribute("listChassic"));
            }

            if (request.getAttribute("listEquipment") == null) {
                request.setAttribute("listEquipment", request.getSession().getAttribute("listEquipment"));
            }

            if (request.getAttribute("listSwitch") == null) {
                request.setAttribute("listSwitch", request.getSession().getAttribute("listSwitch"));
            }

            if (request.getAttribute("listDslam") == null) {
                request.setAttribute("listDslam", request.getSession().getAttribute("listDslam"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>



<tags:imPanel title="MSG.INF.041">
    <!-- hien thi MSG -->
    <div>
        <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgParam" type="key" />
    </div>

    <!-- phan thong tin chassic -->
    <s:form action="chassicAction" theme="simple" enctype="multipart/form-data"  method="post" id="chassicForm">
        <s:hidden name="chassicForm.dslamId" id="dslamId"/>

        <s:hidden name="chassicForm.chassicId" id="chassicId"/>

        <table class="inputTbl6Col">
            <%--tr>
                <td class="label"><tags:label key="Dslam" required="false"/> </td>
                <td class="text" colspan="5">
                    <s:if test="chassicForm.chassicId == null || chassicForm.chassicId * 1 < 1">
                        <tags:imSelect name="chassicForm.dslamId" id="dslamId_temp"
                                       cssClass="txtInputFull" disabled="false"
                                       list="listDslam"
                                       headerKey="" headerValue="--Select Dslam--"
                                       listKey="dslamId" listValue="code" />
                    </s:if><s:else>
                        <tags:imSelect name="chassicForm.dslamId" id="dslamId_temp"
                                       cssClass="txtInputFull" disabled="true"
                                       list="listDslam"
                                       headerKey="" headerValue="--Select Dslam--"
                                       listKey="dslamId" listValue="code" />
                    </s:else>
                </td>
            </tr--%>
            <tr>

                <td class="label"><tags:label key="MSG.code"/></td>
                <td class="text">
                    <s:if test="chassicForm.chassicId == null || chassicForm.chassicId * 1 < 1">
                        <s:textfield name="chassicForm.code" id="code" maxlength="30" cssClass="txtInputFull" />
                    </s:if><s:else>
                        <s:textfield name="chassicForm.code" id="code" maxlength="30" cssClass="txtInputFull" readonly="true"/>
                    </s:else>

                </td>

                <td class="label"><tags:label key="meessages.generates.ip_address" required="false"/></td>
                <td class="text">
                    <s:if test="chassicForm.chassicId == null || chassicForm.chassicId * 1 < 1">
                        <s:textfield name="chassicForm.ip" id="ip" maxlength="20" cssClass="txtInputFull" />
                    </s:if><s:else>
                        <s:textfield name="chassicForm.ip" id="ip" maxlength="20" cssClass="txtInputFull" readonly="true"/>
                    </s:else>
                </td>
                <td class="label"><tags:label key="MSG.port.total" required="true"/></td>
                <td class="text">
                    <s:if test="chassicForm.chassicId == null || chassicForm.chassicId * 1 < 1">
                        <s:textfield name="chassicForm.totalPort" id="totalPort" maxlength="5" cssClass="txtInputFull"/>
                    </s:if><s:else>
                        <s:textfield name="chassicForm.totalPort" id="totalPort" maxlength="5" cssClass="txtInputFull" readonly="true"/>
                    </s:else>

                </td>
                <s:hidden id="portBras" name="chassicForm.portBras" value="1"/>
                <%--td class="label"><tags:label key="MSG.port.bras"/></td>
                <td class="text">
                    <s:textfield name="chassicForm.portBras" id="portBras" maxlength="10" cssClass="txtInputFull" disabled="false"/>
                </td--%>
            </tr>
            <tr>

                <td class="label"><tags:label key="MSG.hostname" required="true"/></td>
                <td class="text">
                    <s:textfield name="chassicForm.hostName" id="hostName" maxlength="100" cssClass="txtInputFull" disabled="false"/>
                </td>

                <td class="label"><tags:label key="MSG.chassic.type" required="true"/></td>
                <td class="text">
                    <%--tags:imSelect name="chassicForm.chassicType" id="chassicType"
                                   cssClass="txtInputFull" disabled="false"
                                   list="0:IP,
                                   1:ATM,
                                   2:ECI,
                                   3:NATCOM"                                   
                                   headerKey="" headerValue="--Select Chassic Type--"/--%>
                    
                    <tags:imSelect name="chassicForm.chassicType" id="chassicType"
                                   cssClass="txtInputFull" disabled="false"
                                   list="0:IP"
                                   headerKey="" headerValue="--Select Chassic Type--"/>
                </td>


                <td class="label"><tags:label key="MSG.device" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="chassicForm.equipmentId" id="equipmentId"
                                   cssClass="txtInputFull" disabled="false"
                                   list="listEquipment"
                                   headerKey="" headerValue="MSG.bras.ChooseDevice"
                                   listKey="equipmentId" listValue="name"
                                   />
                    <%--onchange="updateCombo('equipmentId','switchId','%{#request.contextPath}/chassicAction!getSwitch.do');"/>--%>

                </td>



            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.switch" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="chassicForm.switchId" id="switchId"
                                   cssClass="txtInputFull" disabled="false"
                                   list="listSwitch"
                                   headerKey="" headerValue="MSG.chassic.ChooseSwitch"
                                   listKey="switchId" listValue="name"
                                   onchange="changeSwitch(this.value)"/>

                </td>


                <td class="label"><tags:label key="NMS Vlan" required="false"/></td>
                <td class="text">
                    <s:textfield name="chassicForm.nmsVlan" id="nmsVlan" maxlength="1" cssClass="txtInputFull" readonly="true"/>
                </td>
                <td class="label"><tags:label key="MSG.slot.total" required="true"/></td>
                <td class="text">
                    <s:textfield name="chassicForm.totalSlot" id="totalSlot" maxlength="5" cssClass="txtInputFull" disabled="false"/>
                </td>
            </tr>

            <!--            <tr>-->

            <!--
                            <td class="label"><tags_:label key="MSG.thread.total"/></td>
                            <td class="text">
                                <s_:textfield name="chassicForm.channelNumber" id="channelNumber" maxlength="5" cssClass="txtInputFull" disabled="false"/>
                            </td>-->


            <!--                <td class="label"><tags_:label key="MSG.sub_slot"/></td>
                            <td class="text">
                                <s_:textfield name="chassicForm.subSlot" id="subSlot" maxlength="10" cssClass="txtInputFull" disabled="false"/>

                            </td>-->

            <!--            </tr>-->

            <tr>
                <td class="label"><tags:label key="Vlan" required="true"/></td>
                <td class="text">
                    <s:textfield name="chassicForm.vlanStart" id="vlanStart" maxlength="5" cssClass="txtInputFull" disabled="false" />

                </td>


                <td class="label"><tags:label key="MSG.generates.status" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="chassicForm.status" id="status"
                                   cssClass="txtInputFull" disabled="false"
                                   list="1:MSG.active,0: MSG.inactive"
                                   headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseStatus"/>
                </td>
                
                 <td class="label"><tags:label key="Mac address" required="false"/></td>
                <td class="text">
                    <s:textfield name="chassicForm.macAddress" id="macAddress" maxlength="100" cssClass="txtInputFull" disabled="false" />
                </td>
                
                <!--                <td class="label"><tags_:label key="MSG.total.sport.use"/></td>
                                <td  class="text">
                                    <s_:textfield name="chassicForm.usedPort" id="usedPort" maxlength="5" cssClass="txtInputFull" disabled="false"/>

                                </td>

                                <td class="label"><tags_:label key="MSG.total.sport.error"/></td>

                                <td class="text">
                                    <s_:textfield name="chassicForm.invalidPort" id="invalidPort" maxlength="5" cssClass="txtInputFull" disabled="false"/>
                                </td>-->


            </tr>
            <!--            <tr>

                            <td class="label"><tags_:label key="MSG.setup.date" /></td>
                            <td class="text">
                                <tags_:dateChooser id="startupDate" property="chassicForm.startupDate" styleClass="txtInputFull"/>
                            </td>

                            <td class="label"><tags_:label key="MSG.exploitation.date" /></td>
                            <td class="text">
                                <tags_:dateChooser id="setupDate" property="chassicForm.setupDate" styleClass="txtInputFull"/>
                            </td>
                        </tr>-->

            <%--tr>
                <td class="label"><tags:label key="Dslam" required="true"/> </td>
                <td class="text">
                    <tags:imSelect name="chassicForm.localDslamId" id="dslamId"
                                   cssClass="txtInputFull" disabled="false"
                                   list="listDslam"
                                   headerKey="" headerValue="--Choose Dslam--"
                                   listKey="dslamId" listValue="code"
                                   onchange="changeDslam(this.value)"/>

                    <tags:imSearch codeVariable="chassicForm.dslamCode" nameVariable="chassicForm.dslamName"
                                   codeLabel="Code" nameLabel="Name"
                                   searchClassName="com.viettel.im.database.DAO.ChassicDAO"
                                   searchMethodName="getListDslam"
                                   getNameMethod="getListDslamName"
                                   roleList=""/>
                </td>
                <td class="label"><tags:label key="Chassic" required="true"/> </td>
                <td class="text">
                    <tags:imSelect name="chassicForm.chassicId" id="chassicId"
                                   cssClass="txtInputFull" disabled="false"
                                   list="listChassic"
                                   headerKey="" headerValue="--Choose Chassic--"
                                   listKey="chassicId" listValue="code"
                                   onchange="changeChassic(this.value)"/>
                </td>
                <td class="label"><tags:label key="Slot" required="true"/> </td>
                <td class="text">
                    <tags:imSelect name="chassicForm.slotId" id="slotId"
                                   cssClass="txtInputFull" disabled="false"
                                   list="listSlot"
                                   headerKey="" headerValue="--Choose Slot--"
                                   listKey="slotId" listValue="slotPosition"
                                   onchange="changeSlot(this.value)"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="Port" required="true"/> </td>
                <td class="text">
                    <tags:imSelect name="chassicForm.portId" id="portId"
                                   cssClass="txtInputFull" disabled="false"
                                   list="listPort"
                                   headerKey="" headerValue="--Choose Port--"
                                   listKey="portId" listValue="portPosition"/>
                </td>
            </tr--%>
        </table>

        <s:if test="#session.toEditChassic != 1">
            <div align="center" style="vertical-align:top; padding-top:10px;">
                <%--sx:submit  parseContent="true" executeScripts="true"
                            formId="chassicForm" loadingText="Processing..."
                            showLoadingText="true" targets="divDisplayInfo"
                            cssStyle="width:80px;"
                            key="MSG.generates.addNew"  beforeNotifyTopics="chassicAction/saveChassic"/--%>
                <sx:submit  parseContent="true" executeScripts="true"
                            formId="chassicForm" loadingText="Processing..."
                            showLoadingText="true" targets="divDisplayInfo"
                            cssStyle="width:80px;"
                            key="MSG.generates.find"  beforeNotifyTopics="chassicAction/searchChassic"/>
            </div>
        </s:if>

        <s:else>
            <div align="center" style="vertical-align:top; padding-top:10px;">
                <sx:submit parseContent="true" executeScripts="true"
                           formId="chassicForm" loadingText="Processing..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="divDisplayInfo"
                           key="MSG.accept"  beforeNotifyTopics="chassicAction/saveChassic"
                           errorNotifyTopics="errorAction"/>
                <sx:submit parseContent="true" executeScripts="true"
                           formId="chassicForm" loadingText="Processing..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="divDisplayInfo"
                           key="MSG.INF.047"  beforeNotifyTopics="chassicAction/cancel"/>
            </div>
        </s:else>
        <br>
        <s:token/>
    </s:form>
</tags:imPanel>

<div id="listChassic">
    <tags:imPanel title="MSG.chassic.list">
        <jsp:include page="chassicResult.jsp"/>
    </tags:imPanel>
</div>



<script type="text/javascript">

    changeSwitch = function(switchId){
        $('nmsVlan').value = "";
        getInputText("${contextPath}/chassicAction!displayNmsVlan.do?switchId="+switchId+'&'+token.getTokenParamString());
    }

    changeDslam = function(dslamId){
        updateData("${contextPath}/chassicAction!updateListChassic.do?dslamId="+dslamId+'&'+token.getTokenParamString());
    }
    changeChassic = function(chassicId){
        updateData("${contextPath}/chassicAction!updateListSlot.do?chassicId="+chassicId+'&'+token.getTokenParamString());
    }
    changeSlot = function(slotId){
        updateData("${contextPath}/chassicAction!updateListPort.do?slotId="+slotId+'&'+token.getTokenParamString());
    }

    //ham phuc vu viec phan trang


    dojo.event.topic.subscribe("chassicAction/displayDslamCode", function(value, key, text, widget){

        //getInputText("getDslamCode.do?dslamId="+key);
        //event: set event.cancel = true, to cancel request
    });
    dojo.event.topic.subscribe("chassicAction/displaySwitchCode", function(value, key, text, widget){

        getInputText("getSwitchCode.do?switchId="+key+"&"+token.getTokenParamString());
        //event: set event.cancel = true, to cancel request
    });




    //lang nghe, xu ly su kien khi click nut "Tim Kiem"
    dojo.event.topic.subscribe("chassicAction/searchChassic", function(event, widget){
        widget.href = "chassicAction!searchChassic.do?"+token.getTokenParamString();
        //event: set event.cancel = true, to cancel request
    });

    //lang nghe, xu ly su kien khi click nut "Them moi" hoac "Dong y"
    dojo.event.topic.subscribe("chassicAction/saveChassic", function(event, widget){

        if (checkRequiredFields() && checkValidFields()) {

            var type = '${fn:escapeXml(sessionScope.toEditChassic)}';
            var msg =  '<s:text name="MSG.INF.00018"/>';

            if (type != 1){
                msg =  '<s:text name="MSG.INF.00019"/>';
            }

            if (!confirm(getUnicodeMsg(msg))){
                event.cancel = true;
                return;
            }
            widget.href = "chassicAction!saveChassic.do?"+token.getTokenParamString();
        } else {
            event.cancel = true;
        }


        // }

        //setAction(widget,'divDisplayInfo','chassicAction!saveChassic.do');

    });
    //lang nghe, xu ly su kien khi click nut "Huy"
    dojo.event.topic.subscribe("chassicAction/cancel", function(event, widget){

        widget.href = "chassicAction!displayChassic.do?"+token.getTokenParamString();
        //setAction(widget,'divDisplayInfo','chassicAction!displayChassic.do');

        //event: set event.cancel = true, to cancel request
    });

    editChassic = function(chassicId) {
        gotoAction('divDisplayInfo','chassicAction!prepareEditChassic.do?chassicId=' + chassicId+'&'+token.getTokenParamString());
    }
    copyChassic = function(chassicId) {
        gotoAction('divDisplayInfo','chassicAction!copyChassic.do?chassicId=' + chassicId+'&'+token.getTokenParamString());
    }
    delChassic = function(chassicId) {
        gotoAction('divDisplayInfo','chassicAction!deleteChassic.do?chassicId=' + chassicId+'&'+token.getTokenParamString());
    }

    checkRequiredFields = function() {

        if(trim($('equipmentId').value) == ""||trim($('equipmentId').value) == "0" ) {
            $('displayResultMsgClient').innerHTML =  '<s:text name="ERR.INF.066"/>';
            $('equipmentId').select();
            $('equipmentId').focus();
            return false;
        }
        if(trim($('hostName').value) == "") {
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.067"/>';
            $('hostName').select();
            $('hostName').focus();
            return false;
        }
        if(trim($('switchId').value) == ""||trim($('switchId').value) == "0" ) {
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.068"/>';
            $('switchId').select();
            $('switchId').focus();
            return false;
        }
        if(trim($('ip').value) == "") {
            $('displayResultMsgClient').innerHTML ='<s:text name="ERR.INF.069"/>';
            $('ip').select();
            $('ip').focus();
            return false;
        }

        if(trim($('vlanStart').value) == "") {
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.070"/>';
            $('vlanStart').select();
            $('vlanStart').focus();
            return false;
        }

        //        if(trim($('vlanStop').value) == "") {
        //            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.071"/>';
        //            $('vlanStop').select();
        //            $('vlanStop').focus();
        //            return false;
        //        }

        if(trim($('chassicType').value) == "") {
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.072"/>';
            $('chassicType').select();
            $('chassicType').focus();
            return false;
        }

        if(trim($('status').value) == "") {
            $('displayResultMsgClient').innerHTML = '<s:text name="Error. Status must not empty!"/>';
            $('status').select();
            $('status').focus();
            return false;
        }

        return true;
    }

    checkValidFields = function() {
        //        $('code').value=trim($('code').value);
        //        if(isHtmlTagFormat(trim($('code').value))) {
        //            $('displayResultMsgClient').innerHTML = "!!!Lỗi. Trường mã Chassic không được chứa các thẻ HTML";
        //            $('code').select();
        //            $('code').focus();
        //            return false;
        //        }
        //        if(!isValidInput($('code').value, false, false)) {
        //            $('displayResultMsgClient').innerHTML = "!!!Lỗi. Mã chassic chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
        //            $('code').select();
        //            $('code').focus();
        //            return false;
        //        }
        
        $('hostName').value=trim($('hostName').value);
        if(isHtmlTagFormat(trim($('hostName').value))) {
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.073"/>';
            $('hostName').select();
            $('hostName').focus();
            return false;
        }
        if(!isValidInput($('hostName').value, false, false)) {
            $('displayResultMsgClient').innerHTML ='<s:text name="ERR.INF.074"/>';
            $('hostName').select();
            $('hostName').focus();
            return false;
        }

        if(!isInteger((trim($('portBras').value)))&&(!trim($('portBras').value) == "")){
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.075"/>';
            $('portBras').select();
            $('portBras').focus();
            return false;
        }
        $('portBras').value = trim($('portBras').value);

        if(!isInteger(trim($('vlanStart').value))&&(!trim($('vlanStart').value) == "")){
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.076"/>';
            $('vlanStart').select();
            $('vlanStart').focus();
            return false;
        }
        $('vlanStart').value = trim($('vlanStart').value);

        //        if(!isInteger(trim($('vlanStop').value))&&(!trim($('vlanStop').value) == "")){
        //            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.077"/>';
        //            $('vlanStop').select();
        //            $('vlanStop').focus();
        //            return false;
        //        }
        //        $('vlanStop').value = trim($('vlanStop').value);

        if(!isInteger(trim($('nmsVlan').value))&&(!trim($('nmsVlan').value) == "")){
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.078"/>';
            $('nmsVlan').select();
            $('nmsVlan').focus();
            return false;
        }
        $('nmsVlan').value = trim($('nmsVlan').value);

        //        if(!isInteger(trim($('channelNumber').value))&&(!trim($('channelNumber').value) == "")){
        //            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.079"/>';
        //            $('channelNumber').select();
        //            $('channelNumber').focus();
        //            return false;
        //        }
        //        $('channelNumber').value = trim($('channelNumber').value);

        if(!isInteger(trim($('totalSlot').value))&&(!trim($('totalSlot').value) == "")){
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.080"/>';
            $('totalSlot').select();
            $('totalSlot').focus();
            return false;
        }
        $('totalSlot').value = trim($('totalSlot').value);

        if(!isInteger(trim($('totalPort').value))&&(!trim($('totalPort').value) == "")){
            $('displayResultMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.081')"/>';
            $('totalPort').select();
            $('totalPort').focus();
            return false;
        }
        $('totalPort').value = trim($('totalPort').value);

        //        if(!isInteger(trim($('invalidPort').value))&&(!trim($('invalidPort').value) == "")){
        //            //            $('displayResultMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.082')"/>';
        //            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.082"/>';
        //            $('invalidPort').select();
        //            $('invalidPort').focus();
        //            return false;
        //        }
        //        $('invalidPort').value = trim($('invalidPort').value);
        //
        //        if(!isInteger(trim($('usedPort').value))&&(!trim($('usedPort').value) == "")){
        //            //            $('displayResultMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.083')"/>';
        //            $('displayResultMsgClient').innerHTML = '<s:text name=" ERR.INF.083"/>';
        //            $('usedPort').select();
        //            $('usedPort').focus();
        //            return false;
        //        }
        //        $('usedPort').value = trim($('usedPort').value);

        //        if(!isInteger(trim($('subSlot').value))&&(!trim($('subSlot').value) == "")){
        //            //            $('displayResultMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.084')"/>';
        //            $('displayResultMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.084')"/>';
        //            $('subSlot').select();
        //            $('subSlot').focus();
        //            return false;
        //        }
        //        $('subSlot').value = trim($('subSlot').value);
        //
        //        var date1= dojo.widget.byId("startupDate");
        //        var date2=dojo.widget.byId("setupDate");
        //        if(compareDates(date2.domNode.childNodes[1].value,date1.domNode.childNodes[1].value)){
        //            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.085"/>';
        //            return false;
        //
        //        }

        return true;
    }
</script>
