<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListRequestCardNotSale
    Created on : Dec 9, 2015, 10:38:28 AM
    Author     : mov_itbl_dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("listStockCardLost", request.getSession().getAttribute("listStockCardLost"));
%>

<sx:div id="displayTagFrame">
    <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    <display:table targets="displayTagFrame" name="listStockCardLost"
                   id="listStockCardLostId" pagesize="100" class="dataTable"
                   cellpadding="1" cellspacing="1"
                   requestURI="approveStockCardLostAction!pageNavigator.do">
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
            ${fn:escapeXml(listStockCardLostId_rowNum)}
        </display:column>
        <display:column property="serial" title="${fn:escapeXml(imDef:imGetText('MSG.serial.number'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
        <display:column property="createReqDate" title="${fn:escapeXml(imDef:imGetText('msg.active.card.date.create'))}" format="{0,date,dd/MM/yyyy}" style="text-align:center" sortable="false" headerClass="sortable"/>
        <display:column property="lostDate" title="${fn:escapeXml(imDef:imGetText('Lost.card.date'))}" format="{0,date,dd/MM/yyyy}" style="text-align:center" sortable="false" headerClass="sortable"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="sortable">
            <s:if test="#attr.listStockCardLostId.status == 0">
                ${fn:escapeXml(imDef:imGetText('MSG.Not.Approved.Request'))}
            </s:if>
            <s:elseif test="#attr.listStockCardLostId.status == 1">
                ${fn:escapeXml(imDef:imGetText('MSG.Approved.Request'))}
            </s:elseif>
            <s:else>
                undefined - <s:property escapeJavaScript="true"  value="#attr.listStockCardLostId.status"/>
            </s:else>
        </display:column>
        <display:column property="storageCode" title="${fn:escapeXml(imDef:imGetText('MSG.store.infor'))}" escapeXml="true"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.unit.channel'))}" escapeXml="true">
            <s:if test="#attr.listStockCardLostId.ownerType == 1">
                ${fn:escapeXml(imDef:imGetText('MSG.RET.106'))}
            </s:if>
            <s:elseif test="#attr.listStockCardLostId.ownerType == 2">
                ${fn:escapeXml(imDef:imGetText('MSG.RET.107'))}
            </s:elseif>
            <s:else>
                undefined - <s:property escapeJavaScript="true"  value="#attr.listStockCardLostId.ownerType"/>
            </s:else>
        </display:column>
        <display:column title="<input id = 'checkAllId' type='checkbox' onclick=\"selectCbAll()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center">
        <%--<display:column title="${imDef:imGetText('lbl.duyet.yeu.cau')}" sortable="false" headerClass="tct" style="text-align:center;">--%>
                <s:if test="#attr.listStockCardLostId.status == 0">
                    <s:checkbox name="reqCardNotSaleFrom.selectedStockCardLostIds" theme="simple"
                                id="checkBoxID%{#attr.listStockCardLostId.stockCardLostId}"
                                fieldValue="%{#attr.listStockCardLostId.stockCardLostId}"
                                onclick="checkSelectCbAll();"
                                />
                </s:if>
                <s:else>
                    <s:checkbox name="selectedStockCardLostIds" theme="simple"
                                id="selectedStockCardLostIds"
                                disabled="true"
                                />
               </s:else>
        </display:column>          

    </display:table>

    <br/>

    <div align="center" style="padding-bottom:4px; padding-top:4px;">
        <tags:submit
            formId="reqCardNotSaleFrom"
            showLoadingText="true"
            targets="bodyContent"
            cssStyle="width:80px"
            value="MSG.approve"
            preAction="approveStockCardLostAction!approveStockCardLost.do"
            validateFunction="validateBeforeApprove();"
        />
    </div>
</sx:div >

<script>
    checkSelectedList =function(checkboxIds) { 
        var listId = document.getElementsByName(checkboxIds);  
        for (var i = 0; i < listId.length; i++) {            
            if (listId[i].checked == true) {
                return true;
            }
        }        
        return false;
    }

    function validateBeforeApprove(){

        if(!checkSelectedList('reqCardNotSaleFrom.selectedStockCardLostIds')){
            $('message').innerHTML = "<s:property value="getText('Choose.serial')"/>";
            return false;
        }
        confirmText = getUnicodeMsg("<s:property value="getText('cf.duyet_yeu_cau')"/>");
        if (!confirm(confirmText)){
            return false;
        }
        return true;
    }
    
    function approveStockCardLost() {
        return "approveStockCardLostAction!approveStockCardLost.do?" + token.getTokenParamString();
    }
    
    selectCbAll = function(){
        selectAll("checkAllId", "reqCardNotSaleFrom.selectedStockCardLostIds", "checkBoxID");
    }
    
    checkSelectCbAll = function(){
        checkSelectAll("checkAllId", "reqCardNotSaleFrom.selectedStockCardLostIds", "checkBoxID");
    }
</script>
