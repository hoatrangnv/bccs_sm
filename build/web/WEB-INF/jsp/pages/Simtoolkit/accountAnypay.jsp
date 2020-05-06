<%-- 
    Document   : accountAnypay
    Created on : Jan 19, 2012, 5:33:36 PM
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

    request.setAttribute("roleActiveAnypay", request.getSession().getAttribute("ROLE_ACTIVE_ACCOUNT_ANYPAY" + pageId));
    request.setAttribute("roleUpdateAnypay", request.getSession().getAttribute("ROLE_UPDATE_ACCOUNT_ANYPAY" + pageId));
    request.setAttribute("roleInActiveAnypay", request.getSession().getAttribute("ROLE_INACTIVE_ACCOUNT_ANYPAY" + pageId));

%>


<s:hidden name="form.vfSimtk.accountAnypay.balanceId"/>
<s:hidden name="form.vfSimtk.accountAnypay.accountId"/>

<s:if test="form.vfSimtk.accountAnypay.balanceId != null && form.vfSimtk.accountAnypay.status != 0 && form.vfSimtk.accountAnypay.status  != 4 ">
    <c:set var="isUpdateAnypay" scope="page" value="true" />
</s:if>
<s:else>
    <c:set var="isUpdateAnypay" scope="page" value="false" />
</s:else>

<table class="inputTbl8Col" style="width:100%">
    <tr>
        <td class="label"><tags:label key="MSG.SIK.072" required="true"/></td>
        <td class="text">
            <tags:dateChooser property="form.vfSimtk.accountAnypay.startDate"  id="form.vfSimtk.accountAnypay.startDate" styleClass="txtInputFull"  insertFrame="false"
                              readOnly="true"  />
        </td>
        <td class="label"><tags:label key="MSG.SIK.073"/></td>
        <td class="text">
            <tags:dateChooser property="form.vfSimtk.accountAnypay.endDate"  id="form.vfSimtk.accountAnypay.endDate" styleClass="txtInputFull"  insertFrame="false"
                              readOnly="${fn:escapeXml(!pageScope.isUpdateAnypay)}"/>
        </td>
        <td class="label"><tags:label key="MSG.SIK.074" required="true"/></td>
        <td class="text">
            <s:if test="form.vfSimtk.accountAnypay.balanceId != null && form.vfSimtk.accountAnypay.status != 0 && form.vfSimtk.accountAnypay.status != 4 ">
                <tags:imSelect name="form.vfSimtk.accountAnypay.status" id="form.vfSimtk.accountAnypay.status"
                               cssClass="txtInputFull"
                               theme="simple"
                               disabled="${fn:escapeXml(!pageScope.isUpdateAnypay)}"
                               headerKey="" headerValue="MSG.SIK.252"
                               list="listStatusNotCanncel" listKey="code" listValue="name"
                               />
            </s:if>
            <s:else>
                <tags:imSelect name="form.vfSimtk.accountAnypay.status" id="form.vfSimtk.accountAnypay.status"
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
            <s:textfield name="form.vfSimtk.accountAnypay.realBalanceDisplay" id="form.vfSimtk.accountAnypay.realBalanceDisplay" cssStyle ="text-align:right" theme="simple" maxlength="10"  cssClass="txtInputFull"
                         readonly = "true" />
        </td>            
    </tr>
</table>

<br/>

<div align="center" style="padding-bottom:5px;">


    <!--    truong hop kich hoat moi hoac kich hoat lai-->
    <s:if test="form.vfSimtk.accountAnypay == null || form.vfSimtk.accountAnypay.balanceId == null || form.vfSimtk.accountAnypay.status == 0 || form.vfSimtk.accountAnypay.status == 4 ">
        <s:if test="form.vfSimtk.accountAnypay == null || form.vfSimtk.accountAnypay.balanceId == null ">
            <tags:submit  formId="form"
                          cssStyle="width:120px;"
                          showLoadingText="true" confirm="true" confirmText="C.200002"
                          targets="simtkArea"
                          value="MSG.SIK.053" disabled="${fn:escapeXml(!requestScope.roleActiveAnypay)}"
                          preAction="simtkManagmentAction!activeAccountAnypay.do"
                          />                
        </s:if>
        <s:elseif test="form.vfSimtk.accountAnypay.status == 4 ">
            <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.009')"/>"style="width:120px;" disabled/>
            <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.010')"/>"style="width:120px;" disabled/>
        </s:elseif>
        <s:else>
            <tags:submit  formId="form"
                          cssStyle="width:120px;"
                          showLoadingText="true" confirm="true" confirmText="C.200003"
                          targets="simtkArea"
                          value="MSG.SIK.270" disabled="${fn:escapeXml(!requestScope.roleActiveAnypay)}"
                          preAction="simtkManagmentAction!reActiveAccountAnypay.do"
                          />
        </s:else>
    </s:if>
    <!--                       nghiep vu tai khoan thanh toan-->
    <s:else>
        <tags:submit targets="simtkArea" formId="form"
                     showLoadingText="true" cssStyle="width:120px;"
                     confirm="true" confirmText="C.200004"
                     value="MSG.SIK.009" disabled="${fn:escapeXml(!requestScope.roleUpdateAnypay)}"
                     validateFunction="validateUpdateAccountAnypay()"
                     preAction="simtkManagmentAction!updateAccountAnypay.do"
                     />
        <tags:submit  formId="form"
                      cssStyle="width:120px;"
                      showLoadingText="true" confirm="true" confirmText="C.200005"
                      targets="simtkArea"
                      value="MSG.SIK.010" disabled="${fn:escapeXml(!requestScope.roleInActiveAnypay)}"
                      preAction="simtkManagmentAction!inActiveAccountAnypay.do"
                      />
    </s:else>

</div>

<script language="javascript">

    validateUpdateAccountAnypay = function(){
        var dateExported= dojo.widget.byId("form.vfSimtk.accountAnypay.startDate");
        if(dateExported.domNode.childNodes[1].value.trim() == ""){
            $( 'displayResultMsgSimtk' ).innerHTML='<s:text name="ERR.SIK.024"/>';
            $('form.vfSimtk.accountAnypay.startDate').focus();
            return false;
        }
        
         var dateExported1 = dojo.widget.byId("form.vfSimtk.accountAnypay.endDate");
        if(dateExported1.domNode.childNodes[1].value != "" && !isDateFormat(dateExported1.domNode.childNodes[1].value)){
            $( 'displayResultMsgSimtk' ).innerHTML='<s:text name="ERR.SIK.026"/>';
            $('form.vfSimtk.accountAnypay.endDate').focus();
            return false;
        }
        if(dateExported1.domNode.childNodes[1].value.trim() != "" && !isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
            $( 'displayResultMsgSimtk' ).innerHTML='<s:text name="ERR.SIK.027"/>';
            $('form.vfSimtk.accountAnypay.startDate').focus();
            return false;
        } 
        
        
        if ($('form.vfSimtk.accountAnypay.status').value == ''){
            $( 'displayResultMsgSimtk' ).innerHTML='<s:text name="ERR.SIK.001"/>';
            $('form.vfSimtk.accountAnypay.status').focus();
            return false;
        }
        return true;
    }
    
</script>
