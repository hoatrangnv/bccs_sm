<%-- 
    Document   : port : Insert/Update/Delete/List port in database

    Created on : Apr 10, 2009, 3:30:50 PM
    Author     : ANHLT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
            if (request.getAttribute("lstPort") == null) {
                request.setAttribute("lstPort", request.getSession().getAttribute("lstPort"));
            }
            if (request.getAttribute("lstPortStatus") == null) {
                request.setAttribute("lstPortStatus", request.getSession().getAttribute("lstPortStatus"));
            }

            if (request.getAttribute("lstPort") == null) {
                request.setAttribute("lstPort", request.getSession().getAttribute("lstPort"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>
<s:form action="portAction" theme="simple"  enctype="multipart/form-data" method="POST" id="portForm">
<s:token/>

    <s:hidden name="portForm.portId" id="portForm.portId"/>
    <s:hidden name="portForm.slotId" id="portForm.slotId"/>

    <tags:imPanel title="MSG.port.PortInfo">
        <div>
            <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgParam" type="key" />
        </div>

        <table class="inputTbl4Col">
            <tr>
                <td class="label"><tags:label key="MSG.position.port" required="true"/></td>
                <td class="text">
                    <s:if test="#session.toEditPort != 1">
                        <s:textfield name="portForm.portPosition" id="portPosition" maxlength="5" cssClass="txtInputFull"/>
                    </s:if><s:else>
                        <s:textfield name="portForm.portPosition" id="portPosition" maxlength="5" readonly="true" cssClass="txtInputFull"/>
                    </s:else>
                </td>
                <%--td class="label"> <tags:label key="MSG.vlan_id"/></td>
                <td class="text">
                    <s:textfield name="portForm.vlanId" id="vlanId" maxlength="5" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr--%>
                <td  class="label"><tags:label key="MSG.generates.status" required="true"/></td>
                <td class="text">
                    <s:select name="portForm.status" id="status"
                              cssClass="txtInputFull" disabled="false"
                              list="#request.lstPortStatus != null? #request.lstPortStatus :#{}"
                              headerKey="" headerValue="--Select Port Status--"
                              listKey="code" listValue="name"/>
                </td>
                <%--td  class="label"><tags:label key="MSG.create.date" required="true"/></td>
                <td class="text">
                    <tags:dateChooser id="createDate" property="portForm.createDate" styleClass="txtInputFull"/>
                </td--%>
            </tr>

        </table>
    </tags:imPanel>

    <div align="center" style="vertical-align:top; padding-top:10px;">
        <s:if test="#session.toEditPort != 1">
            <%--sx:submit  parseContent="true" executeScripts="true"
                        formId="portForm" loadingText="Procesing..."
                        cssStyle="width:80px"
                        showLoadingText="true" targets="divDisplayInfo"
                        key="MSG.generates.addNew"  beforeNotifyTopics="portForm/savePort"/--%>
            <sx:submit  parseContent="true" executeScripts="true"
                        formId="portForm" loadingText="Procesing..."
                        cssStyle="width:80px"
                        showLoadingText="true" targets="divDisplayInfo"
                        key="MSG.find"  beforeNotifyTopics="portForm/searchPort"/>


            <%--tags:submit targets="divDisplayInfo" formId="portForm"
                         confirm="true" confirmText="MSG.INF.00022"
                         showLoadingText="true" cssStyle="width:85px;"
                         value="MSG.create.port" preAction="portAction!genPort.do"
                         /--%>
        </s:if>
        <s:else>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="portForm" loadingText="Procesing..."
                       showLoadingText="true" targets="divDisplayInfo"
                       cssStyle="width:80px"
                       key="MSG.accept"  beforeNotifyTopics="portForm/savePort"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="portForm" loadingText="Procesing..."
                       showLoadingText="true" targets="divDisplayInfo"
                       cssStyle="width:80px"
                       key="MSG.GOD.018"  beforeNotifyTopics="portForm/cancel"/>

        </s:else>
    </div>

    <s:div id="lstPort">
        <tags:imPanel title="MSG.port.list">
            <jsp:include page="portResult.jsp"/>
        </tags:imPanel>
    </s:div>

</s:form>

<script type="text/javascript">
    //ham phuc vu viec phan trang
    pageNavigator = function (ajaxTagId, pageNavigator, pageNumber)
    {
        dojo.widget.byId("updateContent").href = 'portAction!pageNavigator.do?' + pageNavigator + "=" + pageNumber;
        dojo.event.topic.publish('updateContent');
    }
    //lang nghe, xu ly su kien khi click nut "Them doi tac moi"
    dojo.event.topic.subscribe("portForm/savePort", function(event, widget){
        if (checkRequiredFields() && checkValidFields()) {
            var type = '${fn:escapeXml(sessionScope.toEditPort)}';
            if (type != 1){
                //                       msg = '<s:property escapeJavaScript="true"  value="getError('MSG.INF.00024')"/>';
                msg = '<s:text name="MSG.INF.00024"/>';
            }

            else {
                //                       msg = '<s:property escapeJavaScript="true"  value="getError('MSG.INF.00023')"/>';
                msg = '<s:text name="MSG.INF.00023"/>';
            }

            if (!confirm(getUnicodeMsg(msg))){
                event.cancel = true;
                return;
            }


            widget.href = "portAction!savePort.do?"+token.getTokenParamString();
        } else {
            event.cancel = true;
        }


        //event: set event.cancel = true, to cancel request
    });

    //lang nghe, xu ly su kien khi click nut "Tim kiem"
    dojo.event.topic.subscribe("portForm/searchPort", function(event, widget){
        widget.href = "portAction!searchPort.do";
        //event: set event.cancel = true, to cancel request
    });
    //lang nghe, xu ly su kien khi click nut "Đong y"
    dojo.event.topic.subscribe("portAction/editPort", function(event, widget){

        if (checkRequiredFields() && checkValidFields()) {
            widget.href = "portAction!editPort.do?"+token.getTokenParamString();
        } else {
            event.cancel = true;
        }

    });
    //lang nghe, xu ly su kien khi click nut "bo qua"
    dojo.event.topic.subscribe("portForm/cancel", function(event, widget){
        widget.href = "portAction!displayPort.do";
        //event: set event.cancel = true, to cancel request
    });
    delPort = function(portId) {
        if(confirm(getUnicodeMsg('<s:text name="MSG.INF.00025"/>'))){
            gotoAction('divDisplayInfo','portAction!deletePort.do?portId=' + portId+'&'+token.getTokenParamString());
        }
    }
    checkRequiredFields = function() {

        if(trim($('portPosition').value) == ""){
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.098"/>';
            $('portPosition').select();
            $('portPosition').focus();
            return false;
        }
        $('portPosition').value  = trim($('portPosition').value);

        //        if(trim($('vlanId').value) == "") {
        //            $('displayResultMsgClient').innerHTML = "!!!Lỗi. Vlan Id không được để trống";
        //            $('vlanId').select();
        //            $('vlanId').focus();
        //            return false;
        //        }
        //               $('vlanId').value  = trim($('vlanId').value);

        if(trim($('status').value) == "") {
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.099"/>';
            $('status').select();
            $('status').focus();
            return false;
        }

        //               var createDate= dojo.widget.byId("createDate");
        //               if(createDate.domNode.childNodes[1].value.trim() == "" ||
        //                   !isDateFormat(createDate.domNode.childNodes[1].value)){
        //                   $( 'displayResultMsgClient' ).innerHTML='<s_:text name="ERR.INF.100"/>';
        //                   createDate.domNode.childNodes[1].focus();
        //                   return false;
        //               }

        return true;
    }
    checkValidFields = function() {
        if(!isInteger((trim($('portPosition').value)))&&(!trim($('portPosition').value) == "")){
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.101"/>';
            $('portPosition').select();
            $('portPosition').focus();
            return false;
        }
        $('portPosition').value  = trim($('portPosition').value);

        //               if(!isInteger((trim($('vlanId').value)))&&(!trim($('vlanId').value) == "")){
        //                   $('displayResultMsgClient').innerHTML = '<s_:text name="ERR.INF.102"/>';
        //                   $('vlanId').select();
        //                   $('vlanId').focus();
        //                   return false;
        //               }
        //               $('vlanId').value  = trim($('vlanId').value);

        return true;
    }
</script>
