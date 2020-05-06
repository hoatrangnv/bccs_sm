<%--
    Document   : AccountAnyPayFPTManagement
    Created on : Feb 05, 2010, 11:58:50 AM
    Author     : Vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            Boolean canSale = (Boolean) request.getAttribute("canSale");
            String pageId = request.getParameter("pageId");
            request.setAttribute("roleCreateAccountAnyPay", request.getSession().getAttribute("roleCreateAccountAnyPay" + pageId));
            request.setAttribute("roleUpdateAccountAnyPay", request.getSession().getAttribute("roleUpdateAccountAnyPay" + pageId));
            request.setAttribute("roleDestroyAccountAnyPay", request.getSession().getAttribute("roleDestroyAccountAnyPay" + pageId));
%>
<s:hidden name="CollAccountManagmentForm.checkAccountAnyPayFPT" id="checkAccountAnyPayFPT"/>
<c:set var="roleCreateAccountAnyPay" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleCreateAccountAnyPay, request))}" />
<c:set var="roleUpdateAccountAnyPay" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleUpdateAccountAnyPay, request))}" />
<c:set var="roleDestroyAccountAnyPay" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleDestroyAccountAnyPay, request))}" />

<table class="inputTbl6Col">
    <tr>
        <td colspan="6">
            <tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
        </td>
    </tr>
    <tr>
        <%--<td class="label">Số tiền</td>--%>
        <td class="label"><tags:label key="MSG.SIK.001" /></td>
        <td class="text">
            <s:textfield name="CollAccountManagmentForm.amountAccountAnyPayFPTDisplay" id="amountAccountAnyPayFPT" theme="simple" maxlength="100" cssClass="txtInput"
                         readonly="true"/>
        </td>
        <%--<td class="label">Trạng thái</td>--%>
        <td class="label"><tags:label key="MSG.SIK.002" /></td>
        <s:if test="collAccountManagmentForm.checkAccountAnyPayFPT != null && collAccountManagmentForm.checkAccountAnyPayFPT * 1 == 9 ">
            <td class="text">

                <tags:imSelect name="CollAccountManagmentForm.statusAccountAnyPayFPT" id="statusAccountAnyPayFPT"
                               cssClass="txtInput"
                               disabled="true"
                               theme="simple"
                               list="1:Active,0:Locked,9:Canncelled"
                               headerKey="" headerValue="-- Select status --"/>
            </td>

        </s:if>
        <s:else>            
            <td class="text">

                <tags:imSelect name="CollAccountManagmentForm.statusAccountAnyPayFPT" id="statusAccountAnyPayFPT"
                               cssClass="txtInput" 
                               theme="simple"                               
                               list="1:Active,0:Locked"
                               headerKey="" headerValue="MSG.SIK.252"/>
            </td>
        </s:else>
        <%--<td class="label">Lý do thay đổi (<font color="red">*</font>)</td>--%>
        <td class="label"><tags:label key="MSG.SIK.006" required="true"/></td>
        <td class="text">

            <tags:imSelect name="collAccountManagmentForm.reasonIdAnyPay" id="reasonIdAnyPay"
                           cssClass="txtInputFull"
                           theme="simple"
                           list="1:MSG.SIK.007,0:MSG.SIK.008"
                           />
        </td>
    </tr>

</table>
<br/>

<div align="center">

    <s:if test="collAccountManagmentForm.checkAccountAnyPayFPT != null">
        <s:if test="collAccountManagmentForm.checkAccountAnyPayFPT != null && collAccountManagmentForm.checkAccountAnyPayFPT * 1 != 9 ">


            <tags:submit targets="anyPayFPT" formId="collAccountManagmentForm"
                         showLoadingText="true" cssStyle="width:80px;"
                         confirm="true" confirmText="MSG.SIK.011"
                         value="MSG.SIK.009"
                         disabled="${fn:escapeXml(!pageScope.roleUpdateAccountAnyPay)}"
                         validateFunction="validateUpdateAccountAnyPayInfo()"
                         preAction="CollAccountManagmentAction!updateStatusFPT.do"
                         />
            <tags:submit targets="anyPayFPT" formId="collAccountManagmentForm"
                         showLoadingText="true" cssStyle="width:90px;"
                         confirm="true" confirmText="MSG.SIK.012"
                         value="MSG.SIK.010"
                         disabled="${fn:escapeXml(!pageScope.roleDestroyAccountAnyPay)}"                         
                         preAction="CollAccountManagmentAction!destroyAccountFPT.do"
                         />


        </s:if>
        <s:else>            
        </s:else>        

    </s:if>
    <s:else>

        <tags:submit targets="anyPayFPT" formId="collAccountManagmentForm"
                     showLoadingText="true" cssStyle="width:90px;"
                     confirm="true" confirmText="MSG.SIK.014" validateFunction="checkValidateActiveAnypayAccount();"
                     value="MSG.SIK.013"
                     disabled="${fn:escapeXml(!pageScope.roleCreateAccountAnyPay)}"                     
                     preAction="CollAccountManagmentAction!activeAccountAnyPayFPT.do"
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
        openDialog('CollAccountManagmentAction!queryAnyPayAgent.do?accountId='
            +$('pAccountId').value + "&" + token.getTokenParamString(),800,700,false);
    }


    validateUpdateAccountAnyPayInfo = function(){
        $('returnMsgClient').innerHTML="";

        if (${fn:escapeXml('checkIsdn')}.value != '1'){
            $('returnMsgClient').innerHTML= '<s:text name = "Error. Can not update anypay account without SIM number!"/>';
            return false;
        }

        var statusAccountAnyPayFPT=document.getElementById('statusAccountAnyPayFPT');
        if (statusAccountAnyPayFPT.value ==""){
            //            $('displayResultMsgClient' ).innerHTML='Chưa chọn trạng thái tài khoản';
            //            $('returnMsgClient').innerHTML='<s_:property value="getError('ERR.SIK.001')"/>';
            $('returnMsgClient').innerHTML= '<s:text name = "ERR.SIK.001"/>';
            $('statusAccountAnyPayFPT').focus();
            return false;
        }
        return true;
    }

    checkValidateActiveAnypayAccount = function(){
        $('returnMsgClient').innerHTML="";

        if ($('checkIsdn').value != 1){
            $('returnMsgClient').innerHTML= '<s:text name = "Error. Can not active anypay account without SIM number!"/>';
            return false;
        }

        var statusAccountAnyPayFPT=document.getElementById('statusAccountAnyPayFPT');
        if (statusAccountAnyPayFPT.value ==""){
            //            $('displayResultMsgClient' ).innerHTML='Chưa chọn trạng thái tài khoản';
            //            $('returnMsgClient').innerHTML='<s_:property value="getError('ERR.SIK.001')"/>';
            $('returnMsgClient').innerHTML= '<s:text name = "ERR.SIK.001"/>';
            $('statusAccountAnyPayFPT').focus();
            return false;
        }   
        
        return true;
    
    }


</script>
