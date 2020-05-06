<%-- 
    Document   : accountAnyPayManagement
    Created on : Oct 13, 2009, 11:58:50 AM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            Boolean canSale = (Boolean) request.getAttribute("canSale");
%>
<s:hidden name="CollAccountManagmentForm.pForm.pBalanceId" id="pBalanceId"/>
<s:hidden name="CollAccountManagmentForm.pForm.pAccountId" id="pAccountId"/>


<table class="inputTbl6Col">
    <tr>
        <td colspan="6">
            <s:hidden name="CollAccountManagmentForm.pForm.pAgentType" id="pAgentType" />
            <s:hidden name="CollAccountManagmentForm.pForm.pPassword" id="pPassword" />
            <s:hidden name="CollAccountManagmentForm.pForm.pServiceType" id="pServiceType" />
            <s:hidden name="CollAccountManagmentForm.pForm.pIsdn" id="pIsdn" />
            <s:hidden name="CollAccountManagmentForm.pForm.pSerial" id="pSerial" />

            <tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.creater"/></td>
        <td class="text">
            <s:textfield name="CollAccountManagmentForm.pForm.pUserCodeCreate" id="pUserCodeCreate" theme="simple" maxlength="100" cssClass="txtInput"
                         readonly="true"/>
        </td>
        <td class="label"><tags:label key="MSG.SIK.071"/></td>
        <td class="text">
            <tags:dateChooser property="CollAccountManagmentForm.pForm.pDateCreate"  id="pDateCreate"  styleClass="txtInput"  insertFrame="false"
                              readOnly="true"/>
        </td>
        <td class="label"><tags:label key="MSG.SAE.067"/></td>
        <td class="text">
            <s:textfield name="CollAccountManagmentForm.pForm.pOwnerCode" id="pOwnerCode" theme="simple" maxlength="100" cssClass="txtInput"
                         readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.SIK.002" required="true"/></td>
        <td class="text">
            <%--<s:select name="CollAccountManagmentForm.pForm.pInStatus" id="pInStatus" cssClass="txtInput"
                      list="#{'1':'Hiệu lực','2':'Tạm khoá'}"
                      headerKey="" headerValue="--Chọn trạng thái--" disabled="false" theme="simple"
                      />--%>

            <tags:imSelect name="CollAccountManagmentForm.pForm.pInStatus" id="pInStatus"
                           cssClass="txtInput" disabled="false"
                           list="1:MSG.SIK.197,2: MSG.SIK.248 "
                           headerKey="" headerValue="combo.statusHeader"/>
        </td>
        <td class="label"><tags:label key="MSG.SIK.113" required="true"/></td>
        <td class="text">
            <tags:dateChooser property="CollAccountManagmentForm.pForm.pStartDate"  id="pStartDate"  styleClass="txtInput"  insertFrame="false"
                              readOnly="false"/>
        </td>
        <td class="label"><tags:label key="MSG.SIK.114"/></td>
        <td class="text">
            <tags:dateChooser property="CollAccountManagmentForm.pForm.pEndDate"  id="pEndDate"  styleClass="txtInput"  insertFrame="false"
                              readOnly="false"/>
        </td>
        <td>&nbsp;</td>
    </tr>
</table>
<br/>

<div align="center">


    <s:if test="collAccountManagmentForm.pForm.pBalanceId != null && collAccountManagmentForm.pForm.pBalanceId * 1 != 0 ">
        <tags:submit targets="anyPay" formId="collAccountManagmentForm"
                     showLoadingText="true" cssStyle="width:80px;"
                     confirm="true" confirmText="MSG.SIK.011"
                     value="MSG.SIK.009"
                     validateFunction="validateUpdateAccountAnyPayInfo()"
                     preAction="CollAccountManagmentAction!editAccountAnyPay.do"
                     />

        <tags:submit targets="anyPay" formId="collAccountManagmentForm"
                     showLoadingText="true" cssStyle="width:80px;"
                     value="MSG.SIK.249"
                     confirm="true" confirmText="INF.SIK.001"
                     preAction="CollAccountManagmentAction!deleteAccountAnyPay.do"
                     />
        <s:if test="#request.canSale == true">
            <sx:submit cssStyle="width:80px;"
                       key="MSG.SIK.266"
                       onclick="prepareBeforeSaleToAccountAnyPay();"
                       />
        </s:if>
        <s:else>
            <!--            <input type="button" value="<s_:property value="getError('MSG.SIK.266')"/>" style="width:80px;" disabled/>-->
            <input type="button" value="<s:text name = "MSG.SIK.266"/>" style="width:80px;" disabled/>
        </s:else>
        <sx:submit cssStyle="width:80px;"
                   value="TT trên IN"
                   onclick="queryAnyPayAgent();"
                   />
    </s:if>
    <s:else>
        <tags:submit targets="anyPay" formId="collAccountManagmentForm"
                     showLoadingText="true" cssStyle="width:80px;"
                     confirm="true" confirmText="INF.SIK.002"
                     value="MSG.GOD.010"
                     validateFunction="validateUpdateAccountAnyPayInfo()"
                     preAction="CollAccountManagmentAction!addAccountAnyPay.do"
                     />
    </s:else>
</div>
<br/>



<script>
    
    prepareBeforeSaleToAccountAnyPay = function(){
        
        openDialog('SaleToAnyPayAction!prepareBeforeSaleToAccountAnyPay.do?accountAnyPayId='
            +$('pAccountId').value+"&"+ token.getTokenParamString(),800,600,false);
    }

    queryAnyPayAgent = function(){
        // TuTM1 04/03/2012 Fix ATTT CSRF
        openDialog('CollAccountManagmentAction!queryAnyPayAgent.do?accountId='+$('pAccountId').value
        +'&'+token.getTokenParamString(),800,700,false);
    }

    
    validateUpdateAccountAnyPayInfo = function(){

                tmp = $( 'pInStatus' ).value;
                if (null==tmp || 0==trim(tmp).length){
                    //                    $('returnMsgClient').innerHTML= '<s_:property value="getError('ERR.SIK.150')"/>';
                    $('returnMsgClient').innerHTML= '<s:text name = "ERR.SIK.150"/>';
                    //$('returnMsgClient').innerHTML = "!!! Chưa nhập trạng thái";
                    $( 'pInStatus' ).focus();
                    return false;
                }
        
                return true;
            }
    
</script>
