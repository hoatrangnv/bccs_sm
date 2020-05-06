<%-- 
    Document   : listSimtkDKTT
    Created on : Mar 11, 2016, 11:26:53 AM
    Author     : mov_itbl_dinhdc
--%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@page  import="com.viettel.im.database.BO.UserToken" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("lstSimtkDKTT", request.getSession().getAttribute("lstSimtkDKTT"));
%>

<sx:div id="displayTagFrame">  
    <div style="width:100%; overflow:auto;">
        <display:table id="lstSimtkDKTTId" name="lstSimtkDKTT" class="dataTable" pagesize="20"
                       targets="searchResultArea" requestURI="simtkDKTTAction!pageNavigator.do"
                       cellpadding="0" cellspacing="0">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                ${fn:escapeXml(lstSimtkDKTTId_rowNum)}
            </display:column>        
            <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.isdn'))}" property="isdn" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.chanel.code'))}" property="ownerCode" style="width:100px;text-align:left"/>
            <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.shop.code'))}" property="shopCode" style="width:75px;text-align:left"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.Authen.Status'))}" sortable="false" headerClass="tct" style="width:75px;text-align:center">
                <s:if test="#attr.lstSimtkDKTTId.authenStatus == 0 || #attr.lstSimtkDKTTId.authenStatus == null">
                    ${fn:escapeXml(imDef:imGetText('TYPE.REMOVE.AUTHEN'))}
                </s:if>
                <s:elseif test="#attr.lstSimtkDKTTId.authenStatus == 1">
                    ${fn:escapeXml(imDef:imGetText('TYPE.CHECK.AUTHEN'))}
                </s:elseif>
                <s:else>
                    undefined - <s:property escapeJavaScript="true"  value="#attr.lstSimtkDKTTId.authenStatus"/>
                </s:else>
            </display:column>    
            <display:column title="<input id = 'checkAllId' type='checkbox' onclick=\"selectCbAll()\"> ${imDef:imGetText('TYPE.CHECK.AUTHEN')} " sortable="false" headerClass="tct" style="width:50px; text-align:center">       
                <s:if test="#attr.lstSimtkDKTTId.authenStatus == 0 || #attr.lstSimtkDKTTId.authenStatus == null ">
                    <s:checkbox name="simtkManagementForm.selectedShopAuthenIds" theme="simple"
                                id="checkBoxID%{#attr.lstSimtkDKTTId.accountId}"
                                fieldValue="%{#attr.lstSimtkDKTTId.accountId}"
                                onclick="checkSelectCbAll();"
                                />
                </s:if>
                <s:else>
                    <s:checkbox name="selectedShopIds" theme="simple"
                                id="selectedShopIds"
                                disabled="true"
                                />
               </s:else>
            </display:column>
            <display:column title="<input id = 'checkAllRemoveId' type='checkbox' onclick=\"selectCbAllRemove()\"> ${imDef:imGetText('TYPE.REMOVE.AUTHEN')} " sortable="false" headerClass="tct" style="width:50px; text-align:center">
                <s:if test="#attr.lstSimtkDKTTId.authenStatus == 1 || #attr.lstSimtkDKTTId.authenStatus == null ">
                    <s:checkbox name="simtkManagementForm.selectedShopRemoveIds" theme="simple"
                                id="checkBoxRemoveID%{#attr.lstSimtkDKTTId.accountId}"
                                fieldValue="%{#attr.lstSimtkDKTTId.accountId}"
                                onclick="checkSelectCbAllRemove();"
                                />
                </s:if>
                <s:else>
                    <s:checkbox name="selectedShopRemoveIds" theme="simple"
                                id="selectedShopRemoveIds"
                                disabled="true"
                                />
               </s:else>
            </display:column>
            
        </display:table>
        
        
        <div align="center" style="padding-bottom:4px; padding-top:4px;">
            <tags:submit
                formId="simtkManagementForm"
                showLoadingText="true"
                targets="bodyContent"
                cssStyle="width:200px"
                value="Button.Authen.ISDN"
                preAction="simtkDKTTAction!authenISDN.do"
                validateFunction="checkBeforeAuthenISDN();"
                />
        
            <tags:submit
                formId="simtkManagementForm"
                showLoadingText="true"
                targets="bodyContent"
                cssStyle="width:200px"
                value="Button.Remove.ISDN"
                preAction="simtkDKTTAction!removeAuthenISDN.do"
                validateFunction="checkBeforeRemoveISDN();"
                />
        </div>
    </div>
</sx:div >        
<script language="javascript">
    
    checkSelectedList =function(checkboxIds) { 
        var listId = document.getElementsByName(checkboxIds);  
        for (var i = 0; i < listId.length; i++) {            
            if (listId[i].checked == true) {
                return true;
            }
        }        
        return false;
    }
    
    checkBeforeAuthenISDN = function() {

            if(!checkSelectedList('simtkManagementForm.selectedShopAuthenIds')){
                $('message').innerHTML = "<s:property value="getText('ERR.CHL.002')"/>";
                return false;
            }
            
            confirmText = getUnicodeMsg("<s:property value="getText('cf.duyet_yeu_cau')"/>");
            if (!confirm(confirmText)){
                return false;
            }

            return true;
    }  
    
    checkBeforeRemoveISDN = function() {

            if(!checkSelectedList('simtkManagementForm.selectedShopRemoveIds')){
                $('message').innerHTML = "<s:property value="getText('ERR.CHL.002')"/>";
                return false;
            }
            
            confirmText = getUnicodeMsg("<s:property value="getText('cf.duyet_yeu_cau')"/>");
            if (!confirm(confirmText)){
                return false;
            }

            return true;
    }  
    
    selectCbAll = function(){
        selectAll("checkAllId", "simtkManagementForm.selectedShopAuthenIds", "checkBoxID");
    }
    
    checkSelectCbAll = function(){
        checkSelectAll("checkAllId", "simtkManagementForm.selectedShopAuthenIds", "checkBoxID");
    }
    
    selectCbAllRemove = function(){
        selectAll("checkAllRemoveId", "simtkManagementForm.selectedShopRemoveIds", "checkBoxRemoveID");
    }
    
    checkSelectCbAllRemove = function(){
        checkSelectAll("checkAllRemoveId", "simtkManagementForm.selectedShopRemoveIds", "checkBoxRemoveID");
    }


</script>
