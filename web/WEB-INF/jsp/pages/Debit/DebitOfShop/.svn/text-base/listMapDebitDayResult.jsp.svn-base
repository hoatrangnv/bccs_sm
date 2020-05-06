<%-- 
    Document   : listMapDebitDayResult.jsp
    Created on : Dec 13, 2013, 1:52:37 PM
    Author     : thinhph2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%
    String pageId = request.getParameter("pageId");
    request.setAttribute("contextPath", request.getContextPath());
    request.setAttribute("listMappingResult", request.getSession().getAttribute("listMappingResult" + pageId));
%>

<fieldset class="imFieldset">
    <legend>${imDef:imGetText('lbl.list_mapping')}</legend>
    <div style="height:270px;overflow:auto">
        <tags:displayResult id="displayResultMsg" property="returnMsg" type="key" propertyValue="returnMsgParam"/>
        <display:table targets="listChannelGroupDIV" id="listMappingResult" name="listMappingResult" class="dataTable" cellpadding="1" cellspacing="1" requestURI="managementChannelGroupAction!pageNavigator.do" pagesize="10">
            <display:column escapeXml="false" title="STT" sortable="false" headerClass="tct">
                <div align="center" style="vertical-align:middle">${listMappingResult_rowNum}</div>
            </display:column>
            <display:column escapeXml="true" property="shopCode" title="${imDef:imGetText('MSG.channelId')}" sortable="false" style="text-align:left" headerClass="tct"/>
            <display:column escapeXml="true" property="shopName" title="${imDef:imGetText('MSG.unit.name')}" sortable="false" style="text-align:left" headerClass="tct"/>
            <display:column escapeXml="true" property="debitDayTypeName" title="${imDef:imGetText('lbl.loai_ngay_ap_dung')}" sortable="false" style="text-align:left" headerClass="tct"/>
            <display:column title="${imDef:imGetText('MSG.generates.status')}" sortable="false" style="text-align:left" headerClass="tct">
                <s:if test="#attr.listMappingResult.status == 1">
                    <tags:label key="MSG.GOD.002" />
                </s:if>
                <s:else>
                    <tags:label key="MSG.GOD.003" />
                </s:else>
            </display:column>
            <display:column title="${imDef:imGetText('lbl.sua')}" sortable="false" style="text-align:center;width:30px" headerClass="tct">
                <input type="radio"  name="chose" id="radio<s:property value='#attr.listMappingResult.id' />" onclick="preUpdate(<s:property value='#attr.listMappingResult.id' />)" />
            </display:column>
        </display:table>
    </div>
</fieldset>
