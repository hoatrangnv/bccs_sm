<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : assignChannelTypeForSerialList
    Created on : Sep 7, 2010, 9:50:24 AM
    Author     : Doan Thanh 8
    Desc       : danh sach cac dai serial can gan kenh
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
            request.setAttribute("listSerialRange", request.getSession().getAttribute("listSerialRange" + pageId));
%>


<sx:div id="divListSerialRange">
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.STK.033'))}</legend>
        <div style="height:300px; overflow:auto; ">
            <display:table name="listSerialRange" id="tblListSerialRange"
                           class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.STK.034'))}" sortable="false" headerClass="tct" style="text-align:center">
                    ${fn:escapeXml(tblListSerialRange_rowNum)}
                </display:column>
                <s:if test="!#request.isFilePreviewMode">
                    <display:column escapeXml="true" property="fromSerial" title="${fn:escapeXml(imDef:imGetText('MSG.STK.035'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true" property="toSerial" title="${fn:escapeXml(imDef:imGetText('MSG.STK.036'))}" sortable="false" headerClass="tct"/>
                    <display:column property="countSerial" title="${fn:escapeXml(imDef:imGetText('MSG.STK.037'))}" format="{0,number,#,###}" style="text-align:right; " sortable="false" headerClass="tct"/>
                </s:if>
                <s:else>
                    <display:column escapeXml="true" property="fromSerial" title="${fn:escapeXml(imDef:imGetText('MSG.STK.065'))}" sortable="false" headerClass="tct"/>
                </s:else>                
                <display:column escapeXml="true" property="shopCode" title="${fn:escapeXml(imDef:imGetText('MSG.STK.038'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="shopName" title="${fn:escapeXml(imDef:imGetText('MSG.STK.039'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="stockModelCode" title="${fn:escapeXml(imDef:imGetText('MSG.STK.040'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="stockModelName" title="${fn:escapeXml(imDef:imGetText('MSG.STK.041'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true"  property="channelTypeName" title="${fn:escapeXml(imDef:imGetText('MSG.STK.042'))}" sortable="false" headerClass="tct"/>
                <s:if test="!#request.isFilePreviewMode">
                    <display:column title="<input id='cbCheckAll' type='checkbox' onclick=\"checkAllCheckbox()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center;">
                        <s:checkbox name="assignChannelTypeForSerialForm.selectedFormId"
                                    id="arrSelectedFormId_%{#attr.tblListSerialRange.formId}"
                                    onclick="cbOnclick();"
                                    fieldValue="%{#attr.tblListSerialRange.formId}"
                                    theme="simple"/>
                    </display:column>
                </s:if>
                <s:else>
                    <display:column escapeXml="true" property="newChannelTypeName" title="${fn:escapeXml(imDef:imGetText('MSG.STK.063'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true" property="errorMessage" title="${fn:escapeXml(imDef:imGetText('MSG.STK.064'))}" sortable="false" headerClass="tct" style="color:red"/>
                </s:else>
            </display:table>
        </div>
    </fieldset>
</sx:div>


<script language="javascript">
    //
    checkAllCheckbox = function(){
        selectAll("cbCheckAll", "assignChannelTypeForSerialForm.selectedFormId", "arrSelectedFormId_");
    }
    //
    cbOnclick = function(){
        checkSelectAll("cbCheckAll", "assignChannelTypeForSerialForm.selectedFormId", "arrSelectedFormId_");
    }
    //
    autoDownloadFile = function(filePath) {
        window.open(filePath,'','toolbar=yes,scrollbars=yes');
    }
</script>
