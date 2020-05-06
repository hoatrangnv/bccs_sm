<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listExpNoteToPartner
    Created on : Sep 3, 2010, 3:34:10 AM
    Author     : Doan Thanh 8
    Desc       : danh sach cac phieu xuat kho cho doi tac
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
        <display:table id="tblListExpNote" name="listExpNote" class="dataTable" pagesize="15" targets="searchArea" requestURI="ExportStockUnderlyingAction!searchExpTrans.do" cellpadding="1" cellspacing="1">
            <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="text-align:center">
                <s:property escapeJavaScript="true"  value="%{#attr.tblListExpNote_rowNum}"/>
            </display:column>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.205'))}" property="id.actionCode"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.206'))}" property="createDatetime" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.184'))}" property="fromOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.185'))}" property="toOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.158'))}" property="reasonName" sortable="false" headerClass="tct"/>
            
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" sortable="false" headerClass="tct">
            <s:if test="#attr.tblListExpNote.stockTransStatus ==3">
                    <s:text name="MSG.GOD.193"/>
                </s:if>
                <s:elseif test="#attr.tblListExpNote.stockTransStatus ==4">
                    <s:text name="MSG.GOD.192"/>
                </s:elseif>
                <s:elseif test="#attr.tblListExpNote.stockTransStatus ==5">
                    <s:text name="MSG.GOD.196"/>
                </s:elseif>
                <s:elseif test="#attr.tblListExpNote.stockTransStatus ==6">
                    <s:text name="MSG.GOD.197"/>
                </s:elseif>
                <s:elseif test="#attr.tblListExpNote.stockTransStatus ==1">
                    <s:text name="MSG.GOD.190"/>
                </s:elseif>
                 <s:elseif test="#attr.tblListExpNote.stockTransStatus ==2">
                    <s:text name="MSG.GOD.191"/>
                </s:elseif>
                <s:else>
                    <s:property escapeJavaScript="true"  value="#attr.tblListExpNote.stockTransStatus"/> - Undefined
                </s:else>

                <%--s:if test="#attr.trans.stockTransStatus ==3">${fn:escapeXml(imDef:imGetText('MSG.GOD.193'))}</s:if>
                <s:if test="#attr.trans.stockTransStatus ==4">${fn:escapeXml(imDef:imGetText('MSG.GOD.192'))}</s:if>
                <s:if test="#attr.trans.stockTransStatus ==5">${fn:escapeXml(imDef:imGetText('MSG.GOD.196'))}</s:if>
                <s:if test="#attr.trans.stockTransStatus ==6">${fn:escapeXml(imDef:imGetText('MSG.GOD.197'))}</s:if--%>
            </display:column>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.031'))}" style="width:200px" property="note" sortable="false" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.207'))}" sortable="false" headerClass="tct">
                <div align="center">
                    <s:if test="#attr.tblListExpNote.stockTransStatus ==2"> <!-- chi nhung giao dịch đã lập phiếu mới được xuất kho -->
                        <s:url action="expToPartnerAction!prepareExpStockFromNote" id="URL" encode="true" escapeAmp="false">
                            <s:param name="actionId" value="#attr.tblListExpNote.id.actionId"/>
                        </s:url>
                        <sx:a targets="divExpNoteToPartner" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                            ${fn:escapeXml(imDef:imGetText('MSG.GOD.200'))}
                        </sx:a>
                    </s:if>
                    <s:if test="#attr.tblListExpNote.stockTransStatus <3"> <!-- chi huy nhung phieu chua xuat kho -->
                        <s:url action="expToPartnerAction!destroyExpNote" id="URL" encode="true" escapeAmp="false">
                            <s:param name="actionId" value="#attr.tblListExpNote.id.actionId"/>
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

<script>
    destroyExpNote= function(url){
        var strConfirmMessage  = getUnicodeMsg('<s:text name="MSG.STK.004"/>');
        if(confirm(strConfirmMessage)){
            gotoAction("divExpNoteToPartner", url + "&" + token.getTokenParamString(),"searchStockForm");
        }
    }
</script>
