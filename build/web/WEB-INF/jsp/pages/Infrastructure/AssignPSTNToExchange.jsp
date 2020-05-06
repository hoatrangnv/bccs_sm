
<%--
    Document   : AssignPSTNToExchange
    Created on : Mar 7, 2009, 3:42:28 PM
    Author     : namnx
--%>
<%-- 
    Note: Gán đầu số PSTN cho Exchange
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<%
            String pageId = request.getParameter("pageId");
            String toEdit = "0";
            if (request.getSession().getAttribute("toEdit" + pageId) != null) {
                toEdit = request.getSession().getAttribute("toEdit" + pageId).toString();
            }
            request.setAttribute("toEdit", toEdit);
            request.setAttribute("contextPath", request.getContextPath());
%>
<s:form action="PSTNExchangeAction" theme="simple" method="POST" id="pstnExchangeForm">
<s:token/>


    <tags:imPanel title ="MSG.assign.pstn.into.exchange.info">
        <div>

            <tags:displayResult id="displayResultMsgClient" property="message" propertyValue="paramMsg" type="key" />

        </div>
        <table class ="inputTbl4Col" >

            <tr>
                <td class="label"><tags:label key="MSG.first.number" required="true"/> </td>
                <td class="text">
                    <s:textfield name="pstnExchangeForm.pstnHeadNumber" id="pstnHeadNumber" maxlength="10" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.switchboard.type" required="true"/> </td>
                <td class="text">
                    <tags:imSelect name="pstnExchangeForm.exchangeType" id="exchangeType"
                              cssClass="txtInputFull"
                              list="lstExchangeType"
                              listKey="code" listValue="name" headerKey="" headerValue="MSG.Equipment.ChooseExchangeType"
                              onchange="updateCombo('exchangeType','exchangeId','${contextPath}/PSTNExchangeAction!getExchange.do');"/>

                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.switchboard.exchange" required="true"/> </td>
                <td class="text">
                    <tags:imSelect name="pstnExchangeForm.exchangeId" id="exchangeId"
                              cssClass="txtInputFull"
                              list="lstExhange"
                              listKey="exchangeId" listValue="name" headerKey="" headerValue="MSG.FAC.ChooseExchange"
                              />

                </td>
                <td class="label"><tags:label key="MSG.province.code" required="true"/> </td>
                <td class="text">
                    <tags:imSearch codeVariable="pstnExchangeForm.provinceCode" nameVariable="pstnExchangeForm.provinceName"
                                   codeLabel="MSG.province.code" nameLabel="MSG.province.name"
                                   searchClassName="com.viettel.im.database.DAO.AssignPstnToExchange"
                                   searchMethodName="getListArea"
                                   getNameMethod="getListAreaName"
                                   roleList=""/>
                </td>
            </tr>                
        </table>

        <div align="center" style="padding-bottom:5px;">
            <s:if test="#request.toEdit != 1">
                <tags:submit
                    targets="bodyContent"
                    formId="pstnExchangeForm"
                    cssStyle="width:120px;"
                    value="MSG.number.assigned"
                    preAction="PSTNExchangeAction!addPSTN.do"
                    validateFunction="checkValidate()"
                    confirm="true" confirmText="MSG.INF.0009"
                    showLoadingText="true"/>
                <tags:submit
                    targets="bodyContent"
                    formId="pstnExchangeForm"
                    cssStyle="width:120px;"
                    value="MSG.find"
                    validateFunction="resetMessage()"
                    preAction="PSTNExchangeAction!searchPSTNExchange.do"                                        
                    showLoadingText="true"/>
            </s:if>
            <s:else>
                <tags:submit
                    targets="bodyContent"
                    formId="pstnExchangeForm"
                    cssStyle="width:120px;"
                    value="MSG.accept"
                    preAction="PSTNExchangeAction!updatePSTN.do"
                    validateFunction="checkValidate()"
                    confirm="true" confirmText="MSG.INF.0009"
                    showLoadingText="true"/>

                <tags:submit targets="bodyContent" formId="pstnExchangeForm"
                     showLoadingText="true" cssStyle="width:120px;"
                     value="MSG.INF.047" preAction="PSTNExchangeAction!preparePage.do"
                     />
                <%--
                <sx:submit  parseContent="true" executeScripts="true"
                            formId="pstnExchangeForm" loadingText="Đang thực hiện..."
                            showLoadingText="true" targets="bodyContent"
                            cssStyle="width:80px"
                            value="Bỏ qua" onclick="formReset()"/>--%>
            </s:else>
        </div>        
    </tags:imPanel>

    <tags:imPanel title = "MSG.pstn.list">
        <sx:div id="lstPSTN">
            <jsp:include page="listPSTNToExchange.jsp"/>
        </sx:div>
    </tags:imPanel>



</s:form>                
<br>


<script type="text/javascript">
    resetMessage = function (){
        $('displayResultMsgClient').innerHTML = "";
        return true;
    }
    formReset = function ()
    {
        document.getElementById("pstnExchangeForm").reset();
    }
    delPstn = function(pstnIsdnExchangeId) {
//        if(confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.0008')"/>'))){
        if(confirm(getUnicodeMsg('<s:text name="MSG.INF.0008"/>'))){
            gotoAction('bodyContent','PSTNExchangeAction!deletePSTN.do?' + pstnIsdnExchangeId + '&' + token.getTokenParamString());
        }
    }
    checkValidate=function(){

        $('displayResultMsgClient').innerHTML = "";
        if($('pstnHeadNumber').value ==null || $('pstnHeadNumber').value.trim() == ""){
//            $('displayResultMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.023')"/>';
            $('displayResultMsgClient').innerHTML = '<s:text name="ERR.INF.023"/>';
            $('pstnHeadNumber').select();
            $('pstnHeadNumber').focus();
            return false;
        }
        $('pstnHeadNumber').value=trim($('pstnHeadNumber').value);
        if($('pstnHeadNumber').value != null && $('pstnHeadNumber').value.trim() !="" && !isNumberFormat($('pstnHeadNumber').value )){
//            $('displayResultMsgClient').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.024')"/>';
            $('displayResultMsgClient').innerHTML =  '<s:text name="ERR.INF.024"/>';
            $('pstnHeadNumber').select();
            $('pstnHeadNumber').focus();
            return false;
        }
        if($('exchangeType').value ==null || $('exchangeType').value.trim() == ""){
//            $('displayResultMsgClient').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.025')"/>';
            $('displayResultMsgClient').innerHTML =  '<s:text name="ERR.INF.025"/>';
            $('exchangeType').select();
            $('exchangeType').focus();
            return false;
        }
        if($('exchangeId').value ==null || $('exchangeId').value.trim() == ""){
//            $('displayResultMsgClient').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.026')"/>';
            $('displayResultMsgClient').innerHTML =  '<s:text name="ERR.INF.026"/>';
            $('exchangeId').select();
            $('exchangeId').focus();
            return false;
        }

        if (trim($('pstnExchangeForm.provinceCode').value) == ""){
//            $('displayResultMsgClient').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.027')"/>';
            $('displayResultMsgClient').innerHTML =  '<s:text name="ERR.INF.027"/>';
            $('pstnExchangeForm.provinceCode').focus();
            $('pstnExchangeForm.provinceCode').select();
            return false;
        }
        
        return true;
    }

</script>
