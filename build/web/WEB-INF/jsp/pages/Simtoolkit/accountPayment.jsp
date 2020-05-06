<%-- 
    Document   : accountPayment
    Created on : Jan 19, 2012, 5:33:48 PM
    Author     : TrongLV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String pageId = request.getParameter("pageId");
    request.setAttribute("roleActivePayment", request.getSession().getAttribute("ROLE_ACTIVE_ACCOUNT_PAYMENT" + pageId));
    request.setAttribute("roleUpdatePayment", request.getSession().getAttribute("ROLE_UPDATE_ACCOUNT_PAYMENT" + pageId));
    request.setAttribute("roleInActivePayment", request.getSession().getAttribute("ROLE_INACTIVE_ACCOUNT_PAYMENT" + pageId));
%>

<s:hidden name="form.vfSimtk.accountPayment.balanceId"/>
<s:hidden name="form.vfSimtk.accountPayment.accountId"/>

<s:if test="form.vfSimtk.accountPayment.balanceId != null && form.vfSimtk.accountPayment.status != 0 && form.vfSimtk.accountPayment.status != 4 ">
    <c:set var="isUpdatePayment" scope="page" value="true" />
</s:if>
<s:else>
    <c:set var="isUpdatePayment" scope="page" value="false" />
</s:else>

<table class="inputTbl8Col" style="width:100%">
    <tr>
        <td class="label"><tags:label key="MSG.SIK.072" required="true"/></td>
        <td class="text">
            <tags:dateChooser property="form.vfSimtk.accountPayment.startDate"  id="form.vfSimtk.accountPayment.startDate" styleClass="txtInputFull"  insertFrame="false"
                              readOnly="true"/>
        </td>
        <td class="label"><tags:label key="MSG.SIK.073"/></td>
        <td class="text">
            <tags:dateChooser property="form.vfSimtk.accountPayment.endDate"  id="form.vfSimtk.accountPayment.endDate" styleClass="txtInputFull"  insertFrame="false"
                              readOnly="${fn:escapeXml(!pageScope.isUpdatePayment)}"/>
        </td>
        <td class="label"><tags:label key="MSG.SIK.074" required="true"/></td>
        <td class="text">

            <s:if test="form.vfSimtk.accountPayment.balanceId != null && form.vfSimtk.accountPayment.status != 0 && form.vfSimtk.accountPayment.status != 4">
                <tags:imSelect name="form.vfSimtk.accountPayment.status" id="form.vfSimtk.accountPayment.status"
                               cssClass="txtInputFull"
                               theme="simple"
                               disabled="${fn:escapeXml(!pageScope.isUpdatePayment)}"
                               headerKey="" headerValue="MSG.SIK.252"
                               list="listStatusNotCanncel" listKey="code" listValue="name"
                               />
            </s:if>
            <s:else>
                <tags:imSelect name="form.vfSimtk.accountPayment.status" id="form.vfSimtk.accountPayment.status"
                               cssClass="txtInputFull"
                               theme="simple"
                               disabled="true"
                               headerKey="" headerValue="MSG.SIK.252"
                               list="listStatus" listKey="code" listValue="name"
                               />
            </s:else>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="L.200009"/></td>
        <td class="text">
            <s:textfield name="form.vfSimtk.accountPayment.currentDebitDisplay" id="form.vfSimtk.accountPayment.currentDebitDisplay" cssStyle ="text-align:right" theme="simple" maxlength="10"  cssClass="txtInputFull"
                         readonly = "true" />
        </td>
        <td class="label"><tags:label key="L.200010"/></td>
        <td class="text">
            <s:textfield name="form.vfSimtk.accountPayment.limitDebitDisplay" id="form.vfSimtk.accountPayment.limitDebitDisplay" cssStyle ="text-align:right" theme="simple" maxlength="10"  cssClass="txtInputFull"
                         readonly = "true" />
        </td>
    </tr>
</table>

<br/>

<div align="center" style="padding-bottom:5px;">   

    <!--    truong hop kich hoat moi hoac kich hoat lai-->
    <s:if test="form.vfSimtk.accountPayment == null || form.vfSimtk.accountPayment.balanceId == null || form.vfSimtk.accountPayment.status == 0 || form.vfSimtk.accountPayment.status == 4">
        <s:if test="form.vfSimtk.accountPayment == null || form.vfSimtk.accountPayment.balanceId == null ">
            <tags:submit  formId="form"
                          cssStyle="width:120px;"
                          showLoadingText="true" confirm="true" confirmText="C.200002"
                          targets="simtkArea"
                          value="MSG.SIK.053" disabled="${fn:escapeXml(!requestScope.roleActivePayment)}"
                          preAction="simtkManagmentAction!activeAccountPayment.do"
                          />                
        </s:if>
        <s:elseif test="form.vfSimtk.accountPayment.status == 4 ">
            <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.009')"/>"style="width:120px;" disabled/>
            <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.010')"/>"style="width:120px;" disabled/>
        </s:elseif>
        <s:else>
            <tags:submit formId="form"
                         cssStyle="width:120px;"
                         showLoadingText="true" confirm="true" confirmText="C.200003"
                         targets="simtkArea"
                         value="MSG.SIK.270" disabled="${fn:escapeXml(!requestScope.roleActivePayment)}"
                         preAction="simtkManagmentAction!reActiveAccountPayment.do"
                         />
        </s:else>
    </s:if>
    <!--                       truong hop tai khoan dang hoat dong -->
    <s:else>
        <tags:submit  targets="simtkArea" formId="form" id="updateAccountPayment"
                      showLoadingText="true" cssStyle="width:120px;"
                      confirm="true" confirmText="C.200004"
                      value="MSG.SIK.009" disabled="${fn:escapeXml(!requestScope.roleUpdatePayment)}"
                      validateFunction="validateUpdateAccountPayment()"
                      preAction="simtkManagmentAction!updateAccountPayment.do"
                      />
        <tags:submit  targets="simtkArea" formId="form" id="inActiveAccountPayment"
                      showLoadingText="true" cssStyle="width:120px;"
                      confirm="true" confirmText="C.200005"                      
                      value="MSG.SIK.010" disabled="${fn:escapeXml(!requestScope.roleInActivePayment)}"
                      preAction="simtkManagmentAction!inActiveAccountPayment.do"
                      />
    </s:else>
</div>


<script language="javascript">
    validateUpdateAccountPayment = function(){
        var dateExported= dojo.widget.byId("form.vfSimtk.accountPayment.startDate");
        if(dateExported.domNode.childNodes[1].value.trim() == ""){
            $( 'displayResultMsgSimtk' ).innerHTML='<s:text name="ERR.SIK.024"/>';
            $('form.vfSimtk.accountPayment.startDate').focus();
            return false;
        }
        
        var dateExported1 = dojo.widget.byId("form.vfSimtk.accountPayment.endDate");
        if(dateExported1.domNode.childNodes[1].value != "" && !isDateFormat(dateExported1.domNode.childNodes[1].value)){
            $( 'displayResultMsgSimtk' ).innerHTML='<s:text name="ERR.SIK.026"/>';
            $('form.vfSimtk.accountPayment.endDate').focus();
            return false;
        }
        if(dateExported1.domNode.childNodes[1].value.trim() != "" && !isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
            $( 'displayResultMsgSimtk' ).innerHTML='<s:text name="ERR.SIK.027"/>';
            $('form.vfSimtk.accountPayment.startDate').focus();
            return false;
        } 
        
         if ($('form.vfSimtk.accountPayment.status').value == ''){
            $( 'displayResultMsgSimtk' ).innerHTML='<s:text name="ERR.SIK.001"/>';
            $('form.vfSimtk.accountPayment.status').focus();
            return false;
        }
        return true;
    }
    
</script>
