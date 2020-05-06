<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : listAssignIsdnType
    Created on : Nov 23, 2009, 6:23:46 PM
    Author     : Doan Thanh 8
    Desc       : danh sach cac dai isdn can gan loai so moi
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
        request.setAttribute("listStockIsdn", request.getSession().getAttribute("listStockIsdn"));
%>

<!-- hien thi link dowload chi tiet so isdn -->
<div class="txtError" style="text-align:center; padding-bottom:5px;">
    <s:if test="#request.detailIsdnRangePath != null">
        <script>
            autoDownloadFile('${contextPath}/${fn:escapeXml(detailIsdnRangePath)}');
        </script>
        <span class="cursorHand" onclick="autoDownloadFile('${contextPath}/${fn:escapeXml(detailIsdnRangePath)}')">
            <s:text name="%{#request.detailIsdnRangeMessage}">
                <s:param name="value" value="#request.messageParam.get(0)"/>
                <s:param name="value" value="#request.messageParam.get(1)"/>
            </s:text>
        </span>
    </s:if>
</div>

<sx:div id="divListIsdnRange">
    <fieldset class="imFieldset">
        <legend> ${fn:escapeXml(imDef:imGetText('MSG.list.isdn.assign.type'))}</legend>
        <div style="height:350px; overflow:auto; ">
            <display:table id="tblListStockIsdn" name="listStockIsdn"
                           class="dataTable" cellpadding="1" cellspacing="1">
                <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" headerClass="tct" sortable="false" style="text-align:center">
                ${fn:escapeXml(tblListStockIsdn_rowNum)}
                </display:column>
                <display:column escapeXml="true"  property="shopCode" title=" ${fn:escapeXml(imDef:imGetText('MSG.store.code'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true"  property="shopName" title=" ${fn:escapeXml(imDef:imGetText('MSG.storeName'))}" sortable="false" headerClass="tct"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.028'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.tblListStockIsdn.stockTypeId == 1"> ${fn:escapeXml(imDef:imGetText('MSG.mobileNumber'))}</s:if>
                    <s:elseif test="#attr.tblListStockIsdn.stockTypeId == 2"> ${fn:escapeXml(imDef:imGetText('MSG.homephoneNumber'))}</s:elseif>
                    <s:elseif test="#attr.tblListStockIsdn.stockTypeId == 3">${fn:escapeXml(imDef:imGetText('MSG.pstnNumber '))}</s:elseif>
                </display:column>
                <display:column escapeXml="true"  property="fromIsdn" title=" ${fn:escapeXml(imDef:imGetText('MSG.ISN.029'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true"  property="toIsdn" title=" ${fn:escapeXml(imDef:imGetText('MSG.toNumber'))}" sortable="false" headerClass="tct"/>
                <display:column property="realQuantity" title="${fn:escapeXml(imDef:imGetText('MSG.quanlity'))}" format="{0,number,#,###}" style="text-align:right; " sortable="false" headerClass="tct"/>
                <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.isdnType'))} " sortable="false" headerClass="tct">
                    <s:if test="#attr.tblListStockIsdn.isdnType == 1"> ${fn:escapeXml(imDef:imGetText('MSG.ISN.005'))}</s:if>
                    <s:elseif test="#attr.tblListStockIsdn.isdnType == 2"> ${fn:escapeXml(imDef:imGetText('MSG.ISN.004'))}</s:elseif>
                    <s:elseif test="#attr.tblListStockIsdn.isdnType == 3"> ${fn:escapeXml(imDef:imGetText('MSG.ISN.003'))}</s:elseif>
                    <s:elseif test="#attr.tblListStockIsdn.isdnType == null"> ${fn:escapeXml(imDef:imGetText('MSG.ISN.027'))}</s:elseif>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.tblListStockIsdn.status == 1"> ${fn:escapeXml(imDef:imGetText('MSG.newIsdn'))}</s:if>
                    <s:elseif test="#attr.tblListStockIsdn.status == 2"> ${fn:escapeXml(imDef:imGetText('MSG.nowIsdn'))}</s:elseif>
                    <s:elseif test="#attr.tblListStockIsdn.status == 3"> ${fn:escapeXml(imDef:imGetText('MSG.stopIsdn'))}</s:elseif>
                    <s:elseif test="#attr.tblListStockIsdn.status == 4">${fn:escapeXml(imDef:imGetText('MSG.ISN.030'))}</s:elseif>
                </display:column>
                <display:column title="<input id='cbCheckAll' type='checkbox' onclick=\"checkAllCheckbox()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center;">
                    <s:checkbox name="assignIsdnTypeForm.isdnIdList"
                                id="arrSelectedFormId_%{#attr.tblListStockIsdn.assignIsdnTypeFormId}"
                                onclick="cbOnclick();"
                                fieldValue="%{#attr.tblListStockIsdn.assignIsdnTypeFormId}"
                                theme="simple"/>
                </display:column>
                <%--<display:column title="<input id='cbCheckAll' type='checkbox' onclick=\"checkAllCheckbox()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center;">
                    <s:checkbox name="assignIsdnTypeForm.arrSelectedFormId"
                                id="arrSelectedFormId_%{#attr.tblListStockIsdn.assignIsdnTypeFormId}"
                                onclick="cbOnclick();"
                                fieldValue="%{#attr.tblListStockIsdn.assignIsdnTypeFormId}"
                                theme="simple"/>
                </display:column>--%>
            </display:table>
        </div>
    </fieldset>
</sx:div>

<script language="javascript">
    //
    checkAllCheckbox = function(){
        selectAll("cbCheckAll", "assignIsdnTypeForm.isdnIdList", "arrSelectedFormId_");
    }
    //
    cbOnclick = function(){
        checkSelectAll("cbCheckAll", "assignIsdnTypeForm.isdnIdList", "arrSelectedFormId_");
    }
    //
    autoDownloadFile = function(filePath) {
        window.open(filePath,'','toolbar=yes,scrollbars=yes');
    }
</script>
