<%-- 
    Document   : accountBalance
    Created on : Jan 19, 2012, 5:33:24 PM
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
<%@page  import="com.viettel.im.common.util.ResourceBundleUtils"%>
<%@taglib prefix="imDef" uri="imDef" %>


<%
    String pageId = request.getParameter("pageId");

    request.setAttribute("roleReceiveDepositStk", request.getSession().getAttribute("ROLE_RECEIVE_DEPOSIT" + pageId));
    request.setAttribute("rolePayDepositStk", request.getSession().getAttribute("ROLE_PAY_DEPOSIT" + pageId));
    request.setAttribute("roleActiveBalance", request.getSession().getAttribute("ROLE_ACTIVE_ACCOUNT_BALANCE" + pageId));
    request.setAttribute("roleUpdateBalance", request.getSession().getAttribute("ROLE_UPDATE_ACCOUNT_BALANCE" + pageId));
    request.setAttribute("roleInActiveBalance", request.getSession().getAttribute("ROLE_INACTIVE_ACCOUNT_BALANCE" + pageId));

    request.setAttribute("listStatusNotCanncel", request.getSession().getAttribute("LIST_CHANNEL_STATUS_NOT_CANCEL_STATUS"));
    request.setAttribute("listStatus", request.getSession().getAttribute("LIST_CHANNEL_STATUS_STATUS"));

%>

<s:hidden name="form.vfSimtk.accountBalance.accountId"/>
<s:hidden name="form.vfSimtk.accountBalance.balanceId" id="form.vfSimtk.accountBalance.balanceId" />

<s:if test="form.vfSimtk.accountBalance.balanceId != null && form.vfSimtk.accountBalance.status != 0 && form.vfSimtk.accountBalance.status != 4 ">
    <c:set var="isUpdateBalance" scope="page" value="true" />
</s:if>
<s:else>
    <c:set var="isUpdateBalance" scope="page" value="false" />
</s:else>


<table class="inputTbl6Col" style="width:100%">
    <tr>            
        <td class="label"><tags:label key="MSG.SIK.072" required="true"/></td>
        <td class="text">
            <tags:dateChooser property="form.vfSimtk.accountBalance.startDate"  id="form.vfSimtk.accountBalance.startDate" styleClass="txtInputFull"  insertFrame="false"
                              readOnly="true"/>
        </td>
        <td class="label"><tags:label key="MSG.SIK.073"/></td>
        <td class="text">
            <tags:dateChooser property="form.vfSimtk.accountBalance.endDate"  id="form.vfSimtk.accountBalance.endDate" styleClass="txtInputFull"  insertFrame="false"
                              readOnly="${fn:escapeXml(!pageScope.isUpdateBalance)}"/>
        </td>
        <td class="label"><tags:label key="MSG.SIK.074" required="true"/></td>
        <td class="text">
            <s:if test="form.vfSimtk.accountBalance.balanceId != null && form.vfSimtk.accountBalance.status != 0 && form.vfSimtk.accountBalance.status != 4 ">
                <tags:imSelect name="form.vfSimtk.accountBalance.status" id="form.vfSimtk.accountBalance.status"
                               cssClass="txtInputFull"
                               theme="simple"
                               disabled="${fn:escapeXml(!pageScope.isUpdateBalance)}"
                               headerKey="" headerValue="MSG.SIK.252"
                               list="listStatusNotCanncel" listKey="code" listValue="name"
                               />
            </s:if>
            <s:else>
                <tags:imSelect name="form.vfSimtk.accountBalance.status" id="form.vfSimtk.accountBalance.status"
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
        <td class="label"><tags:label key="MSG.SIK.079"/></td>
        <td class="text">
            <s:textfield name="form.vfSimtk.accountBalance.realBalanceDisplay" id="form.vfSimtk.accountBalance.realBalanceDisplay" cssStyle ="text-align:right" theme="simple" maxlength="10"  cssClass="txtInputFull"
                         readonly = "true" />
        </td>
        <td class="label"><tags:label key="L.200006"/></td>
        <td class="text">
            <s:textfield name="form.vfSimtk.accountBalance.realDebitDisplay" id="form.vfSimtk.accountBalance.realDebitDisplay" cssStyle ="text-align:right" theme="simple" maxlength="10"  cssClass="txtInputFull"
                         readonly = "true" />
        </td>
        <td class="label"><tags:label key="MSG.SIK.080"/></td>
        <td class="text">
            <s:textfield name="form.vfSimtk.accountBalance.realCreditDisplay" id="form.vfSimtk.accountBalance.realCreditDisplay" cssStyle ="text-align:right" theme="simple" maxlength="10"  cssClass="txtInputFull"
                         readonly = "true" />
        </td>
    </tr>
</table>
<br/>
<div align="center" style="padding-bottom:5px;">
    <!--    truong hop kich hoat moi hoac kich hoat lai-->
    <s:if test="form.vfSimtk.accountBalance == null || form.vfSimtk.accountBalance.balanceId == null || form.vfSimtk.accountBalance.status == 0  || form.vfSimtk.accountBalance.status == 4 ">
        <!--        neu chua kich hoat tai khoan-->
        <s:if test="form.vfSimtk.accountBalance == null || form.vfSimtk.balanceId == null ">
            <tags:submit  formId="form"
                          cssStyle="width:120px;"
                          showLoadingText="true" confirm="true" confirmText="C.200002"
                          targets="simtkArea"
                          value="MSG.SIK.053" disabled="${fn:escapeXml(!requestScope.roleActiveBalance)}"
                          preAction="simtkManagmentAction!activeAccountBalance.do"
                          />                
        </s:if>
        <!--        neu la bi khoa do vi pham quy dinh kinh doanh-->
        <s:elseif test="form.vfSimtk.accountBalance.status == 4 ">
            <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.009')"/>"style="width:120px;" disabled/>
            <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.010')"/>"style="width:120px;" disabled/>
            <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.088')"/>"style="width:120px;" disabled/>
            <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.088')"/>"style="width:120px;" disabled/>
            <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.089')"/>"style="width:120px;" disabled/>
            <s:url action="CollAccountManagmentAction!prepareViewAccountDetail" id="viewAccountBookURL" encode="true" escapeAmp="false">
                <s:token/>
                <s:param name="accountId" value="form.vfSimtk.accountBalance.accountId"/>
                <s:param name="balanceId" value="form.vfSimtk.accountBalance.balanceId"/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>
            </s:url>
            <input type="button" onclick="viewAccountBook('<s:property escapeJavaScript="true"  value="#attr.viewAccountBookURL"/>')" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.090')"/>"
                   style="width: 120px;"/>
        </s:elseif>
        <s:else>
            <tags:submit  formId="form"
                          cssStyle="width:120px;"
                          showLoadingText="true" confirm="true" confirmText="C.200003"
                          targets="simtkArea"
                          value="MSG.SIK.270" disabled="${fn:escapeXml(!requestScope.roleActiveBalance)}"
                          preAction="simtkManagmentAction!reActiveAccountBalance.do"
                          />
            <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.061')"/>" style="width:120px;" onclick="viewLog()"
            </s:else>
        </s:if>


        <!--                       nghiep vu tai khoan thanh toan-->
        <s:else>
            <tags:submit  targets="simtkArea" formId="form"
                          showLoadingText="true" cssStyle="width:120px;"
                          confirm="true" confirmText="C.200004"
                          value="MSG.SIK.009" disabled="${fn:escapeXml(!requestScope.roleUpdateBalance)}"
                          validateFunction="validateUpdateAccountBalance()"
                          preAction="simtkManagmentAction!updateAccountBalance.do"
                          />
            <tags:submit formId="form"
                         cssStyle="width:120px;"
                         showLoadingText="true" confirm="true" confirmText="C.200005"
                         targets="simtkArea"
                         value="MSG.SIK.010" disabled="${fn:escapeXml(!requestScope.roleInActiveBalance)}"
                         preAction="simtkManagmentAction!inActiveAccountBalance.do"
                         />

            <s:if test="#request.rolePayDepositStk == true">
                <s:url action="simtkManagmentAction!prepareReceiveDeposit" id="prepareReceiveDepositURL" encode="true" escapeAmp="false">
                    <s:token/>
                    <s:param name="accountId" value="form.vfSimtk.accountBalance.accountId"/>
                    <s:param name="balanceId" value="form.vfSimtk.accountBalance.balanceId"/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>
                </s:url>
                <input type="button" onclick="prepareReceiveDeposit('<s:property escapeJavaScript="true"  value="#attr.prepareReceiveDepositURL"/>','<s:property escapeJavaScript="true"  value="form.vfSimtk.accountBalance.accountId"/>','<s:property escapeJavaScript="true"  value="form.vfSimtk.accountBalance.balanceId"/>')" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.088')"/>"
                style="width: 120px;"/>
        </s:if>
        <s:else>
            <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.088')"/>" disabled/>
        </s:else>
        <s:if test="#request.roleReceiveDepositStk == true">
            <s:url action="simtkManagmentAction!preparePayDeposit" id="preparePayDepositURL" encode="true" escapeAmp="false">
                <s:token/>
                <s:param name="accountId" value="form.vfSimtk.accountBalance.accountId"/>
                <s:param name="balanceId" value="form.vfSimtk.accountBalance.balanceId"/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>
            </s:url>
            <input type="button" onclick="preparePayDeposit('<s:property escapeJavaScript="true"  value="#attr.preparePayDepositURL"/>','<s:property escapeJavaScript="true"  value="form.vfSimtk.accountBalance.accountId"/>','<s:property escapeJavaScript="true"  value="form.vfSimtk.accountBalance.balanceId"/>')" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.089')"/>"
                   style="width: 120px;" />
        </s:if><s:else>
            <input type="button"  value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.089')"/>"
                   style="width: 120px;" disabled />
        </s:else>

        <s:url action="CollAccountManagmentAction!prepareViewAccountDetail" id="viewAccountBookURL" encode="true" escapeAmp="false">
            <s:token/>
            <s:param name="accountId" value="form.vfSimtk.accountBalance.accountId"/>
            <s:param name="balanceId" value="form.vfSimtk.accountBalance.balanceId"/>
            <s:param name="struts.token.name" value="'struts.token'"/>
            <s:param name="struts.token" value="struts.token"/>
        </s:url>
        <input type="button" onclick="viewAccountBook('<s:property escapeJavaScript="true"  value="#attr.viewAccountBookURL"/>')" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.090')"/>"
               style="width: 120px;"/>

    </s:else>
</div>


<script type="text/javascript" language="javascript">
    
    validateUpdateAccountBalance = function(){
        var dateExported= dojo.widget.byId("form.vfSimtk.accountBalance.startDate");
        if(dateExported.domNode.childNodes[1].value.trim() == ""){
            $( 'displayResultMsgSimtk' ).innerHTML='<s:text name="ERR.SIK.024"/>';
            $('form.vfSimtk.accountBalance.startDate').focus();
            return false;
        }
       
        if(!isDateFormat(dateExported.domNode.childNodes[1].value.trim())){
            $( 'displayResultMsgSimtk' ).innerHTML='<s:text name="ERR.SIK.025"/>';
            $('form.vfSimtk.accountBalance.startDate').focus();
            return false;
        }
        
        var dateExported1 = dojo.widget.byId("form.vfSimtk.accountBalance.endDate");
        if(dateExported1.domNode.childNodes[1].value != "" && !isDateFormat(dateExported1.domNode.childNodes[1].value)){
            $( 'displayResultMsgSimtk' ).innerHTML='<s:text name="ERR.SIK.026"/>';
            $('form.vfSimtk.accountBalance.endDate').focus();
            return false;
        }
        if(dateExported1.domNode.childNodes[1].value.trim() != "" && !isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
            $( 'displayResultMsgSimtk' ).innerHTML='<s:text name="ERR.SIK.027"/>';
            $('form.vfSimtk.accountBalance.startDate').focus();
            return false;
        }            
        
        if ($('form.vfSimtk.accountBalance.status').value == ''){
            $( 'displayResultMsgSimtk' ).innerHTML='<s:text name="ERR.SIK.001"/>';
            $('form.vfSimtk.accountBalance.status').focus();
            return false;
        }
        
        return true;
    }
    
    prepareReceiveDeposit = function(path,accountId,balanceId) {      
        openDialog(path, true, true, false);
        var myWindow;
        if(!myWindow || myWindow.closed){
            // TuTM1 13/03/2012 Fix ATTT CSRF
            gotoAction("accountBalance",'simtkManagmentAction!refreshParent.do?'+'accountId=' + accountId + '&balanceId=' 
                + balanceId  + "&" + token.getTokenParamString());
        }
    }
    preparePayDeposit = function(path,accountId,balanceId) {
        openDialog(path, true, true, false);
        var myWindow;
        if(!myWindow || myWindow.closed){
            gotoAction("accountBalance",'simtkManagmentAction!refreshParent.do?'+'accountId=' + accountId + '&balanceId=' 
                + balanceId + "&" + token.getTokenParamString());
        }
    }
        
    viewAccountBook=function(path){
        openPopup(path, 900, 700);
    }
    
    updateParent = function(accountId,balanceId){
        gotoAction("accountBalance",'simtkManagmentAction!refreshParent.do?'+'accountId=' + accountId + '&balanceId=' 
            + balanceId + "&" + token.getTokenParamString());
    }
        
        
        
</script>
