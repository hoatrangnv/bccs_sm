<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : listResult
    Created on : Apr 19, 2009, 3:33:50 PM
    Author     : Doan Thanh 8
--%>

<%--
    Notes   : danh sach cac shop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listShops", request.getSession().getAttribute("listShops"));
%>

<tags:imPanel title="MSG.shop.list.items">


    <display:table pagesize="20" targets="resultArea" id="tblListShops" name="listShops" class="dataTable" cellpadding="1" cellspacing="1"  requestURI="authenShopDKTTAction!pageNavigator.do">
        <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListShops_rowNum)}</display:column>
        <display:column escapeXml="true"  property="shopCode" title="${fn:escapeXml(imDef:imGetText('MES.CHL.119'))}" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  property="name" title="${fn:escapeXml(imDef:imGetText('MES.CHL.120'))}" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  property="parShopCode" title="${fn:escapeXml(imDef:imGetText('MSG.parentCode'))}" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.Authen.Status'))}" sortable="false" headerClass="tct">
            <s:if test="#attr.tblListShops.authenStatus == 0 || #attr.tblListShops.authenStatus == null">
                ${fn:escapeXml(imDef:imGetText('TYPE.REMOVE.AUTHEN'))}
            </s:if>
            <s:elseif test="#attr.tblListShops.authenStatus == 1">
                ${fn:escapeXml(imDef:imGetText('TYPE.CHECK.AUTHEN'))}
            </s:elseif>
            <s:else>
                undefined - <s:property escapeJavaScript="true"  value="#attr.tblListShops.authenStatus"/>
            </s:else>
        </display:column>        
        <display:column title="<input id = 'checkAllId' type='checkbox' onclick=\"selectCbAll()\"> ${imDef:imGetText('TYPE.CHECK.AUTHEN')} " sortable="false" headerClass="tct" style="width:50px; text-align:center">
                <s:if test="#attr.tblListShops.authenStatus == 0 || #attr.tblListShops.authenStatus == null ">
                    <s:checkbox name="shopForm.selectedShopAuthenIds" theme="simple"
                                id="checkBoxID%{#attr.tblListShops.shopId}"
                                fieldValue="%{#attr.tblListShops.shopId}"
                                onclick="checkSelectCbAll();"
                                />
                </s:if>
                <s:elseif test="#attr.tblListShops.authenStatus == 1 ">
                    <s:checkbox name="selectedShopAuthenIds" theme="simple"
                                id="selectedShopIds"
                                disabled="true"
                                />
                </s:elseif>
                
        </display:column>
        <display:column title="<input id = 'checkAllRemoveId' type='checkbox' onclick=\"selectCbAllRemove()\"> ${imDef:imGetText('TYPE.REMOVE.AUTHEN')} " sortable="false" headerClass="tct" style="width:50px; text-align:center">        
                <s:if test="#attr.tblListShops.authenStatus == 1 || #attr.tblListShops.authenStatus == null ">
                    <s:checkbox name="shopForm.selectedShopRemoveIds" theme="simple"
                                id="checkBoxRemoveID%{#attr.tblListShops.shopId}"
                                fieldValue="%{#attr.tblListShops.shopId}"
                                onclick="checkSelectCbAllRemove();"
                                />
                </s:if>
                <s:elseif test="#attr.tblListShops.authenStatus == 0 ">
                    <s:checkbox name="selectedShopRemoveIds" theme="simple"
                                id="selectedShopIds"
                                disabled="true"
                                />
                </s:elseif>
        </display:column>
        
    </display:table>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-bottom:5px; padding-top:10px;">
        
        <tags:submit formId="shopForm" showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="divDisplayInfo"
                     value="Button.Authen.List"
                     preAction="authenShopDKTTAction!authenShopDKTTByList.do"
                     validateFunction="checkBeforeAuthen();"/>
        
        <tags:submit formId="shopForm" showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="divDisplayInfo"
                     value="Button.Remove.Authen"
                     preAction="authenShopDKTTAction!removeShopDKTTByList.do"
                     validateFunction="checkBeforeRemove();"/>
        
    </div>

</tags:imPanel>

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
    
    checkBeforeAuthen = function() {

            if(!checkSelectedList('shopForm.selectedShopAuthenIds')){
                $('message').innerHTML = "<s:property value="getText('ERR.CHL.002')"/>";
                return false;
            }
            
            confirmText = getUnicodeMsg("<s:property value="getText('cf.duyet_yeu_cau')"/>");
            if (!confirm(confirmText)){
                return false;
            }

            return true;
    }  
    
    checkBeforeRemove = function() {

            if(!checkSelectedList('shopForm.selectedShopRemoveIds')){
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
        selectAll("checkAllId", "shopForm.selectedShopAuthenIds", "checkBoxID");
    }
    
    checkSelectCbAll = function(){
        checkSelectAll("checkAllId", "shopForm.selectedShopAuthenIds", "checkBoxID");
    }
    
    selectCbAllRemove = function(){
        selectAll("checkAllRemoveId", "shopForm.selectedShopRemoveIds", "checkBoxRemoveID");
    }
    
    checkSelectCbAllRemove = function(){
        checkSelectAll("checkAllRemoveId", "shopForm.selectedShopRemoveIds", "checkBoxRemoveID");
    }


</script>
