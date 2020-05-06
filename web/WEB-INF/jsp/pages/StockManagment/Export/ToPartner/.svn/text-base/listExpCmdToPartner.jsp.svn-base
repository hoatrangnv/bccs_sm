<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listExpCmdToPartner
    Created on : Sep 2, 2010, 10:57:30 PM
    Author     : Doan Thanh 8
    Desc       : danh sach cac lenh xuat kho cho doi tac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="display" uri="VTdisplaytaglib" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>

<div>
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.listSearResult'))}</legend>
        <display:table id="tblListExpCmd" name="listExpCmd" class="dataTable" pagesize="15" targets="searchArea" requestURI="ExportStockUnderlyingAction!searchExpTrans.do" >
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="text-align:center">
                <s:property escapeJavaScript="true"  value="%{#attr.tblListExpCmd_rowNum}"/>
            </display:column>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.205'))}" property="id.actionCode"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.206'))}" property="createDatetime" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.184'))}" property="fromOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.185'))}" property="toOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.158'))}" property="reasonName" sortable="false" headerClass="tct"/>
            
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" sortable="false" headerClass="tct">
            <s:if test="#attr.tblListExpCmd.stockTransStatus ==3">
                    <s:text name="MSG.GOD.193"/>
                </s:if>
                <s:elseif test="#attr.tblListExpCmd.stockTransStatus ==4">
                    <s:text name="MSG.GOD.192"/>
                </s:elseif>
                <s:elseif test="#attr.tblListExpCmd.stockTransStatus ==5">
                    <s:text name="MSG.GOD.196"/>
                </s:elseif>
                <s:elseif test="#attr.tblListExpCmd.stockTransStatus ==6">
                    <s:text name="MSG.GOD.197"/>
                </s:elseif>
                <s:elseif test="#attr.tblListExpCmd.stockTransStatus ==1">
                    <s:text name="MSG.GOD.190"/>
                </s:elseif>
                 <s:elseif test="#attr.tblListExpCmd.stockTransStatus ==2">
                    <s:text name="MSG.GOD.191"/>
                </s:elseif>
                <s:else>
                    <s:property escapeJavaScript="true"  value="#attr.tblListExpCmd.stockTransStatus"/> - Undefined
                </s:else>

                <%--s:if test="#attr.trans.stockTransStatus ==3">${fn:escapeXml(imDef:imGetText('MSG.GOD.193'))}</s:if>
                <s:if test="#attr.trans.stockTransStatus ==4">${fn:escapeXml(imDef:imGetText('MSG.GOD.192'))}</s:if>
                <s:if test="#attr.trans.stockTransStatus ==5">${fn:escapeXml(imDef:imGetText('MSG.GOD.196'))}</s:if>
                <s:if test="#attr.trans.stockTransStatus ==6">${fn:escapeXml(imDef:imGetText('MSG.GOD.197'))}</s:if--%>
            </display:column>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.031'))}" style="width:100px" property="note" sortable="false" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.207'))}" sortable="false" headerClass="tct">
                <div align="center">
                    <s:if test="#attr.tblListExpCmd.stockTransStatus ==1"> <%-- chi nhung lenh chua lap phieu moi duoc lap phieu --%>
                        <s:url action="createExpNoteToPartnerAction!prepareCreateNoteFromCmd" id="URL" encode="true" escapeAmp="false">
                            <s:param name="actionId" value="#attr.tblListExpCmd.id.actionId"/>
                        </s:url>
                        <sx:a targets="divCreateExpNoteToPartner" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                            ${fn:escapeXml(imDef:imGetText('MSG.GOD.214'))}
                        </sx:a>
                    </s:if>
                    <s:if test="#attr.tblListExpCmd.stockTransStatus <3"> <%-- chi huy nhung lenh chua xuat kho --%>
                        <s:url action="createExpNoteToPartnerAction!destroyExpCmd" id="URL" encode="true" escapeAmp="false">
                            <s:param name="actionId" value="#attr.tblListExpCmd.id.actionId"/>
                        </s:url>
                        &nbsp;&nbsp;
                        <a onclick="destroyExpCmd('<s:property escapeJavaScript="true"  value="#attr.URL"/>')">
                            ${fn:escapeXml(imDef:imGetText('MSG.GOD.204'))}
                        </a>
                    </div>
                </s:if>
            </display:column>
        </display:table>
    </fieldset>
</div>

<script>
    destroyExpCmd= function(url){
        var strConfirmMessage  = getUnicodeMsg('<s:text name="MSG.STK.004"/>');
        if(confirm(strConfirmMessage)){
            gotoAction("divCreateExpNoteToPartner", url  + "&" + token.getTokenParamString(),"searchStockForm");
        }
    }
</script>
