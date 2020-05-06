<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%--
    Document   : ExportStockToUnderlyingNote
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%request.setAttribute("contextPath", request.getContextPath());%>

<div style="width:100%">
<s:if test="#request.lstSearchStockTrans != null && #request.lstSearchStockTrans.size()>0">
    <tags:displayResult id="resultCreateExpCmdClient" property="resultCreateExp" propertyValue="resultCreateExpParams" type="key"/>
    <tags:displayResultList property="lstError" type="key"/>
    <s:token/>
    <display:table id="trans" name="lstSearchStockTrans" class="dataTable" requestURI="javascript: void(0)" cellpadding="1" cellspacing="1">
        <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct">
            <s:property escapeJavaScript="true"  value="%{#attr.trans_rowNum}"/>
        </display:column>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.205'))}" property="id.actionCode"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.206'))}" property="createDatetime" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.184'))}" property="fromOwnerName" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.185'))}" property="toOwnerName" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.158'))}" property="reasonName" sortable="false" headerClass="tct"/>

        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" property="statusName" sortable="false" headerClass="tct">
            <%--s:if test="#attr.trans.stockTransStatus ==3">
                    <s:text name="MSG.GOD.193"/>
                </s:if>
                <s:elseif test="#attr.trans.stockTransStatus ==4">
                    <s:text name="MSG.GOD.192"/>
                </s:elseif>
                <s:elseif test="#attr.trans.stockTransStatus ==5">
                    <s:text name="MSG.GOD.196"/>
                </s:elseif>
                <s:elseif test="#attr.trans.stockTransStatus ==6">
                    <s:text name="MSG.GOD.197"/>
                </s:elseif>
                <s:elseif test="#attr.trans.stockTransStatus ==1">
                    <s:text name="MSG.GOD.190"/>
                </s:elseif>
                 <s:elseif test="#attr.trans.stockTransStatus ==2">
                    <s:text name="MSG.GOD.191"/>
                </s:elseif>
                <s:else>
                    <s:property escapeJavaScript="true"  value="#attr.trans.stockTransStatus"/> - Undefined
                </s:else--%>

                <%--s:if test="#attr.trans.stockTransStatus ==3">${fn:escapeXml(imDef:imGetText('MSG.GOD.193'))}</s:if>
                <s:if test="#attr.trans.stockTransStatus ==4">${fn:escapeXml(imDef:imGetText('MSG.GOD.192'))}</s:if>
                <s:if test="#attr.trans.stockTransStatus ==5">${fn:escapeXml(imDef:imGetText('MSG.GOD.196'))}</s:if>
                <s:if test="#attr.trans.stockTransStatus ==6">${fn:escapeXml(imDef:imGetText('MSG.GOD.197'))}</s:if--%>
            </display:column>
                    
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.031'))}" style="width:200px" property="note" sortable="false" headerClass="tct"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.184'))}" sortable="false" headerClass="tct">
            <div align="center">
            <s:if test="#attr.trans.stockTransStatus ==2"> <!-- chi nhung giao dịch đã lap phieu moi duoc nhap kho -->
                <s:url action="ImportStockFromSenior!prepareCreateImpStockFromNote" id="URL" encode="true" escapeAmp="false">
                    <s:param name="actionId" value="#attr.trans.id.actionId"/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>
                </s:url>
                <sx:a targets="searchArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                   ${fn:escapeXml(imDef:imGetText('MSG.GOD.229'))}
<!--                   Nhập kho-->
                </sx:a>            
            </s:if>
            </div>
        </display:column>
    </display:table>

</s:if>
</div>
