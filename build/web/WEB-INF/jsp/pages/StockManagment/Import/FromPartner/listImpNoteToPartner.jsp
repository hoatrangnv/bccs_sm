<%-- 
    Document   : listImpNoteToPartner
    Created on : Jun 27, 2014, 1:48:38 PM
    Author     : thuannx1
--%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="display" uri="VTdisplaytaglib" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>
<%
            if (request.getAttribute("listImpNote") == null) {
                request.setAttribute("listImpNote", request.getSession().getAttribute("listImpNote"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>
<tags:displayResult id="displaydestroymessage" property="displaydestroymessage" type="key"/>
<div>
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.listSearResult'))}</legend>
        <s:token/>
        <display:table id="tbllistImpNote" name="listImpNote" class="dataTable" pagesize="15" targets="divImpNoteToPartner" requestURI="ImpStockFromPartner!pageNavigator.do" cellpadding="1" cellspacing="1">
            <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="text-align:center">
                <s:property escapeJavaScript="true"  value="%{#attr.tbllistImpNote_rowNum}"/>
            </display:column>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.212'))}" property="id.actionCode"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.206'))}" property="createDatetime" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.185'))}" property="toOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.partner'))}" property="fromOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.140'))}" property="reasonName" sortable="false" headerClass="tct"/>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" sortable="false" headerClass="tct">
                <s:if test="#attr.tbllistImpNote.stockTransStatus ==3">
                    <s:text name="MSG.GOD.192"/>
                </s:if>
                <s:elseif test="#attr.tbllistImpNote.stockTransStatus ==5">
                    <s:text name="MSG.GOD.196"/>
                </s:elseif>
                <s:elseif test="#attr.tbllistImpNote.stockTransStatus ==6">
                    <s:text name="MSG.GOD.197"/>
                </s:elseif>
                <s:elseif test="#attr.tbllistImpNote.stockTransStatus ==1">
                    <s:text name="MSG.GOD.190"/>
                </s:elseif>
                <s:elseif test="#attr.tbllistImpNote.stockTransStatus ==2">
                    <s:text name="MSG.GOD.191"/>
                </s:elseif>
                <s:else>
                    <s:property escapeJavaScript="true"  value="#attr.tbllistImpNote.stockTransStatus"/> - Undefined
                </s:else>
            </display:column>

            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.031'))}" style="width:200px" property="note" sortable="false" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.207'))}" sortable="false" headerClass="tct">
                <div align="center">
                    <s:if test="#attr.tbllistImpNote.stockTransStatus ==2"> <!-- chi nhung giao dịch đã lập phiếu mới được xuất kho -->
                        <s:url action="ImpStockFromPartner!prepareImpStockFromNote" id="URL" encode="true" escapeAmp="false">
                            <s:param name="actionId" value="#attr.tbllistImpNote.id.actionId"/>
                            <s:param name="struts.token.name" value="'struts.token'"/>
                            <s:param name="struts.token" value="struts.token"/>
                        </s:url>
                        <sx:a targets="divImpNoteToPartner" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                            ${fn:escapeXml(imDef:imGetText('MSG.GOD.229'))}
                        </sx:a>
                    </s:if>
                    <s:if test="#attr.tbllistImpNote.stockTransStatus <3"> <!-- chi huy nhung phieu chua xuat kho -->
                        <s:url action="ImpStockFromPartner!destroyImpNote" id="URL" encode="true" escapeAmp="false">
                            <s:param name="actionId" value="#attr.tbllistImpNote.id.actionId"/>
                            <s:param name="struts.token.name" value="'struts.token'"/>
                            <s:param name="struts.token" value="struts.token"/>
                        </s:url>
                        <%--sx:a targets="bodyContent" href="%{#URL}" executeScripts="true" parseContent="true">
                            Huỷ giao dịch
                        </sx:a--%>
                        <a onclick="destroyExpNote('<s:property escapeJavaScript="true"  value="#attr.URL"/>')">
                            ${fn:escapeXml(imDef:imGetText('MSG.GOD.204'))}
                        </a>
                    </s:if>
                </div>
            </display:column>
        </display:table>

    </fieldset>
</div>

<script type="text/javascript">
    destroyExpNote= function(url){
        var strConfirmMessage  = getUnicodeMsg('<s:text name="MSG.STK.004"/>');
        if(confirm(strConfirmMessage)){
            gotoAction("divImpNoteToPartner", url,"importStockForm");
        }
    }
</script>
