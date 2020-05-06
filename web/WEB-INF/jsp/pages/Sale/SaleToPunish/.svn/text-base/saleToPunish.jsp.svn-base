<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : SaleToPunish
    Created on : Nov 15, 2010, 10:56:17 AM
    Author     : NamLT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>


<tags:imPanel title="MENU.200003">
    <s:form action="saleToPunishAction" theme="simple" method="post" id="form">
<s:token/>

        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.SAE.002'))}</legend>
            <table class="inputTbl8Col">
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.066" required="true"/></td>
                    <td class="text">
                        <tags:imSelect name="form.channelTypeId"
                                       id="form.channelTypeId"
                                       cssClass="txtInputFull" disabled="false" theme="simple"
                                       list="lstChannelType" listValue="name" listKey="channelTypeId"
                                       headerKey="" headerValue="MSG.SIK.264" />
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.067" required="true"/></td>
                    <td  class="text" colspan="3">
                        <tags:imSearch codeVariable="form.receiverCode" 
                                       nameVariable="form.receiverName"
                                       codeLabel="MSG.SAE.067" nameLabel="MSG.SAE.070"
                                       searchClassName="com.viettel.im.database.DAO.SaleToPunishDAO"
                                       searchMethodName="getListChannel"
                                       getNameMethod="getListChannelName"
                                       otherParam="form.channelTypeId"
                                       />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.010" required="true"/></td>
                    <td class="text">
                        <tags:imSelect name="form.payMethod"
                                       id="form.payMethod"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.014"
                                       list="10:MSG.DET.017" value="10"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.009" required="true"/></td>
                    <td class="text">
                        <tags:imSelect name="form.reasonId"
                                       id="form.reasonId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.013"
                                       list="lstReason"
                                       listKey="reasonId" listValue="reasonName"
                                       onchange=""/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.008" required="true"/></td>
                    <td class="text">
                        <tags:dateChooser property="form.saleTransDate"  id="form.saleTransDate" styleClass="txtInput"   readOnly="true" insertFrame="false"/>
                    </td>
                </tr>
            </table>
        </fieldset>

        <div id="saleToPunishDetailArea">
            <jsp:include page="saleToPunishDetail.jsp"/>
        </div>

        <!-- phan nut tac vu -->
        <div align="center">
            <s:if test="form.saleTransId == null">
                <tags:submit confirm="true" confirmText="MSG.SAE.204"
                             formId="form" cssStyle="width: 120px;"
                             targets="bodyContent"
                             validateFunction="validateToSave()" showLoadingText="true"
                             value="MSG.SAE.064" preAction="saleToPunishAction!save.do"/>            
            </s:if>
            <s:else>
                <input type="button" disabled value="<s:text name = "MSG.SAE.064"/>" style="width:120px;">
            </s:else>                
            <tags:submit 
                formId="form" cssStyle="width: 120px;"
                targets="bodyContent"
                showLoadingText="true"
                value="MSG.reset" preAction="saleToPunishAction!prepareSaleToPunish.do"/>
        </div>

    </s:form>
</tags:imPanel>

<script type="text/javascript" language="javascript">  
    
    $('form.channelTypeId').focus();
    
    validateToSave = function(){            
        
        
        //kiem tra ma loai KPP ko duoc de trong
        if(trim($('form.channelTypeId').value) == "") {
            $('displayResultMsg').innerHTML = '<s:text name="ERR.STAFF.0009"/>';
            $('form.channelTypeId').select();
            $('form.channelTypeId').focus();
            return false;
        }
        //kiem tra ma KPP ko duoc de trong
        if(trim($('form.receiverCode').value) == "") {
            $('displayResultMsg').innerHTML = '<s:text name="E.200054"/>';
            $('form.receiverCode').select();
            $('form.receiverCode').focus();
            return false;
        }
        //kiem tra ma KPP ko duoc de trong
        if(trim($('form.receiverName').value) == "") {
            $('displayResultMsg').innerHTML = '<s:text name="E.200054"/>';
            $('form.receiverCode').select();
            $('form.receiverCode').focus();
            return false;
        }
        
        
        
        var reasonId = document.getElementById("form.reasonId");
        var payMethod = document.getElementById("form.payMethod");
        if(trim(reasonId.value).length == 0){
            $('displayResultMsg').innerHTML='<s:property escapeJavaScript="true"  value="getText('MSG.SAE.060')"/>';
            reasonId.focus();
            return false;
        }
        if(trim(payMethod.value).length == 0){
            $('displayResultMsg').innerHTML='<s:property escapeJavaScript="true"  value="getText('MSG.SAE.061')"/>';
            payMethod.focus();
            return false;
        }
        return true;
    }
</script>

