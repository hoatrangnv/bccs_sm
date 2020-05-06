<%-- 
    Document   : configWarning
    Created on : Aug 29, 2012, 3:59:13 PM
    Author     : kdvt_tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MENU.200005">
    <div>
        <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="paramMsg" type="key" />
    </div>
    <s:form action="configWarningAction" theme="simple" enctype="multipart/form-data"  method="post" id="form">
        <s:token/>
        <div>
            <table class="inputTbl4Col">
                <tr>
                    <s:hidden name="form.warningId" id="warningId"/>
                    <td class="label"><tags:label key="L.100044" required="false"/></td>
                    <td class="text">
                        <s:textfield name="form.toStep" id="toStep" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <td class="label"><tags:label key="L.100045" required="false"/></td>
                    <td class="text">
                        <s:textfield name="form.fromStep" id="fromStep" cssClass="txtInputFull" readonly="true"/>
                    </td>
                </tr>            
                <tr>
                    <td class="label"><tags:label key="MSG.status" required="true"/></td>
                    <td class="text">
                        <tags:imSelect name="form.status"
                                       id="status"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.189"
                                       list="1:MSG.GOD.002, 0:MSG.GOD.003"/>
                    </td>
                    <td class="label"><tags:label key="L.100046" required="false"/></td>
                    <td class="text">
                        <s:textfield name="form.linkStep" id="linkStep" cssClass="txtInputFull" readonly="true"/>
                    </td>
                </tr>

                <tr>
                    <td class="label"><tags:label key="L.100047" required="true"/></td>
                    <td class="text">
                        <s:textfield name="form.warnAmountDay" id="warnAmountDay" cssClass="txtInputFull" readonly="false" maxlength="5"/>
                    </td>
                    <td class="label"><tags:label key="L.100048" required="true"/></td>
                    <td class="text">
                        <s:textfield name="form.lockAmountDay" id="lockAmountDay" cssClass="txtInputFull" readonly="false" maxlength="5"/>
                    </td>
                </tr> 

                <%--s:hidden name="form.allowUrlType" id="allowUrlType"/>
                <s:hidden name="form.roleCode" id="roleCode"/--%>
                <tr>
                    <td class="label"><tags:label key="L.100049" required="false"/></td>
                    <td class="text">
                        <s:textfield name="form.allowUrlType" id="allowUrlType" cssClass="txtInputFull" readonly="false" maxlength="50"/>
                    </td>
                    <td class="label"><tags:label key="L.100050" required="false"/></td>
                    <td class="text">
                        <s:textfield name="form.roleCode" id="roleCode" cssClass="txtInputFull" readonly="false" maxlength="50"/>
                    </td>
                </tr>
            </table>
        </div>
        <div>
            <s:if test="form.warningId != null">
                <tags:submit targets="bodyContent"
                             formId="form"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             validateFunction="checkValidate();"
                             value="L.100027" confirm="true" confirmText="C.100007"
                             preAction="configWarningAction!edit.do"/>

                <tags:submit targets="bodyContent"
                             formId="form"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             value="L.100028" 
                             preAction="configWarningAction!preparePage.do"/>
            </s:if>
            <s:else>
                <tags:submit targets="bodyContent"
                             formId="form" disabled="true"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             validateFunction="checkValidate();"
                             value="L.100027"
                             preAction="configWarningAction!edit.do"/>

                <tags:submit targets="bodyContent"
                             formId="form" disabled="true"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             value="L.100028" 
                             preAction="configWarningAction!preparePage.do"/>
            </s:else>
        </div>

        <tags:imPanel title="L.100043">
            <jsp:include page="listConfigWarning.jsp"/>
        </tags:imPanel>
    </s:form>        
</tags:imPanel>


<script type="text/javascript" language="javascript">
    checkValidate = function(){
        var warningId = document.getElementById("warningId");
        if (warningId == null){
            return false;
        }
        if (warningId.value ==null || warningId.value ==""){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.100071')"/>';
            return false;
        }
            
        var status = document.getElementById("status");
        if (status == null){
            return false;
        }
        if (status.value ==null || status.value ==""){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.100079')"/>';
            status.focus();
            return false;
        }
        if (!(status.value =="1" || status.value =="0" )){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('EE.100049')"/>';
            status.focus();
            return false;
        }
        
        var warnAmountDay = document.getElementById("warnAmountDay");
        if (warnAmountDay == null){
            return false;
        }
        if (warnAmountDay.value ==null || warnAmountDay.value ==""){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.100074')"/>';
            warnAmountDay.focus();
            return false;
        }
        if(!isNumberFormat(trim(warnAmountDay.value))){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.100075')"/>';
            warnAmountDay.focus();
            return false;
        }
                
        var lockAmountDay = document.getElementById("lockAmountDay");
        if (lockAmountDay == null){
            return false;
        }
        if (lockAmountDay.value ==null || lockAmountDay.value ==""){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.100075')"/>';
            lockAmountDay.focus();
            return false;
        }
        if(!isNumberFormat(trim(lockAmountDay.value))){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.100076')"/>';
            lockAmountDay.focus();
            return false;
        }
        if (trim(lockAmountDay.value) < trim(warnAmountDay.value)){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.100080')"/>';
            warnAmountDay.focus();
            return false;
        }

        var allowUrlType = document.getElementById("allowUrlType");
        if (allowUrlType == null){
            return false;
        }
        if (isHtmlTagFormat(trim(allowUrlType.value))){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.100077')"/>';
            allowUrlType.focus();
            return false;
        }
        if (!checkSpecialCharacter(trim(allowUrlType.value))){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.100077')"/>';
            allowUrlType.focus();
            return false;
        }
        
        var roleCode = document.getElementById("roleCode");
        if (roleCode == null){
            return false;
        }
        if (isHtmlTagFormat(trim(roleCode.value))){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.100078')"/>';
            roleCode.focus();
            return false;
        }
        if (!checkSpecialCharacter(trim(roleCode.value))){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.100078')"/>';
            roleCode.focus();
            return false;
        }
        $( 'displayResultMsgClient' ).innerHTML = "";
        return true;
    }   
    
    
    function checkSpecialCharacter(inputText) {
        var iChars = "*|,\":<>[]{}`\';()@&$#%";
        for (var i = 0; i < inputText.length; i++) {
            if (iChars.indexOf(inputText.charAt(i)) != -1){
                return false;
            }
        }
        return true;
    }


</script>
