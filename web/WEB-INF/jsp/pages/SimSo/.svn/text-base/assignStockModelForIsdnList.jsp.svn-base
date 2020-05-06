<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : assignStockModelForIsdnList
    Created on : May 25, 2009, 3:33:42 PM
    Author     : Doan Thanh 8
--%>

<%--
    danh sach cac dai isdn can gan loai mat hang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            String pageId = request.getParameter("pageId");
            request.setAttribute("listIsdnRange", request.getSession().getAttribute("listIsdnRange" + pageId));
%>


<sx:div id="divListIsdnRange">
    <fieldset class="imFieldset">
        <legend>
            <s:text name="MSG.list.isdn.assign.goods"/>
        </legend>
        <div style="height:450px; overflow:auto; ">
            <display:table name="listIsdnRange" id="tblListIsdnRange"
                           class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" headerClass="tct" style="text-align:center">
                    ${fn:escapeXml(tblListIsdnRange_rowNum)}
                </display:column>
                <display:column escapeXml="true"  property="fromIsdn" title="${fn:escapeXml(imDef:imGetText('MSG.fromIsdn'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true"  property="toIsdn" title="${fn:escapeXml(imDef:imGetText('MSG.toIsdn'))}" sortable="false" headerClass="tct"/>
                <display:column property="countIsdn" title="${fn:escapeXml(imDef:imGetText('MSG.quantity'))}" format="{0,number,#,###}" style="text-align:right; " sortable="false" headerClass="tct"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.016'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.tblListIsdnRange.stockTypeId == 1">${fn:escapeXml(imDef:imGetText('MSG.mobileNumber'))}</s:if>
                    <s:elseif test="#attr.tblListIsdnRange.stockTypeId == 2">${fn:escapeXml(imDef:imGetText('MSG.homephoneNumber'))}</s:elseif>
                    <s:elseif test="#attr.tblListIsdnRange.stockTypeId == 3">${fn:escapeXml(imDef:imGetText('MSG.pstnNumber'))}</s:elseif>
                    <s:else>${fn:escapeXml(imDef:imGetText('MSG.ISN.008'))}</s:else>
                </display:column>
                <display:column escapeXml="true"  property="shopCode" title="${fn:escapeXml(imDef:imGetText('MSG.isdnStoreId'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true"  property="shopName" title="${fn:escapeXml(imDef:imGetText('MSG.storeName'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true"  property="stockModelCode" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.007'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true"  property="stockModelName" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.008'))}" sortable="false" headerClass="tct"/>
                <display:column property="fromPrice" title="${fn:escapeXml(imDef:imGetText('MSG.fromPrice'))}" sortable="false" headerClass="tct" format="{0,number,0,000}" style="text-align: right;"/>
                <display:column property="toPrice" title="${fn:escapeXml(imDef:imGetText('MSG.toPrice'))}" sortable="false" headerClass="tct" format="{0,number,0,000}" style="text-align: right;"/>
                <display:column title="<input id='cbCheckAll' type='checkbox' onclick=\"checkAllCheckbox()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center;">
                    <s:checkbox name="assignStockModelForIsdnForm.selectedFormId"
                                id="arrSelectedFormId_%{#attr.tblListIsdnRange.formId}"
                                onclick="cbOnclick();"
                                fieldValue="%{#attr.tblListIsdnRange.formId}"
                                theme="simple"/>
                </display:column>
            </display:table>
        </div>
    </fieldset>
</sx:div>


<script language="javascript">
    //
    checkAllCheckbox = function(){
        selectAll("cbCheckAll", "assignStockModelForIsdnForm.selectedFormId", "arrSelectedFormId_");
    }
    //
    cbOnclick = function(){
        checkSelectAll("cbCheckAll", "assignStockModelForIsdnForm.selectedFormId", "arrSelectedFormId_");
    }
    //
    autoDownloadFile = function(filePath) {
        window.open(filePath,'','toolbar=yes,scrollbars=yes');
    }
</script>

