<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listLookupSerial
    Created on : Jun 21, 2009, 11:18:08 AM
    Author     : Nguyen Van Lam
    Desc       : danh sach cac serial
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
%>

<fieldset class="imFieldset">
    <legend>
        <s:text name="MSG.search.result"/>
    </legend>
    <s:if test= "#request.tblListSerialPair != null && #request.tblListSerialPair.size() > 0">
        <div style="width:100%; height:350px; overflow:auto;">
            <display:table name="tblListSerialPair" id="vcRequest"
                           class="dataTable" cellpadding="1" cellspacing="1">
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))} " sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(vcRequest_rowNum)}</display:column>
                <display:column title="<input id ='checkAllItemId' type='checkbox' onclick=\"selectCbItemAll()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                    <s:checkbox name="activeCardForm.lstSelectedItem" id="checkBoxItemId%{#attr.vcRequest.requestId}"
                                theme="simple" fieldValue="%{#attr.vcRequest.requestId}"
                                onclick="checkSelectCbAll();"/>
                </display:column>
                <display:column escapeXml="true" property="userId" title="${fn:escapeXml(imDef:imGetText('MSG.creater'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="fromSerial" title="${fn:escapeXml(imDef:imGetText('MSG.ISN.011'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="toSerial" title="${fn:escapeXml(imDef:imGetText('MSG.ISN.012'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="createTime" title="${fn:escapeXml(imDef:imGetText('MSG.create.date'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="lastProcessTime" title="${fn:escapeXml(imDef:imGetText('MSG.FAC.Card.ProcessDate'))}" sortable="false" headerClass="tct"/>
            </display:table>
        </div>
    </s:if>
    <s:else>
        <font color='red'>
            <tags:label key="MSG.not.found.records" />
        </font>
    </s:else>
</fieldset>
<script language="javascript">
    selectCbItemAll = function(){
        selectAll("checkAllItemId", "activeCardForm.lstSelectedItem", "checkBoxItemId");
    }

    checkSelectCbAll = function(){
        checkSelectAll("checkAllItemId", "activeCardForm.lstSelectedItem", "checkBoxItemId");
    }
</script>


