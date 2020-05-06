<%-- 
    Document   : listImpCmdToPartner
    Created on : Jun 26, 2014, 1:35:23 PM
    Author     : thuannx1
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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<tags:displayResult id="displaydestroymessage" property="displaydestroymessage" type="key"/>
<%
            if (request.getAttribute("listImpCmd") == null) {
                request.setAttribute("listImpCmd", request.getSession().getAttribute("listImpCmd"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>
<sx:div id="DisplayListImpCmd">
    <tags:imPanel title="MSG.listSearResult">
        <s:token/>
        <display:table id="tbllistImpCmd" name="listImpCmd" class="dataTable" pagesize="15" targets="DisplayListImpCmd" requestURI="CreateImpNoteStockFromPartnerAction!pageNavigator.do" >
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="text-align:center">
                <s:property escapeJavaScript="true"  value="%{#attr.tbllistImpCmd_rowNum}"/>
            </display:column>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.205'))}" property="id.actionCode"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.206'))}" property="createDatetime" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.185'))}" property="toOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.partner'))}" property="fromOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.reasonImport'))}" property="reasonName" sortable="false" headerClass="tct"/>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" sortable="false" headerClass="tct">
                <s:if test="#attr.tbllistImpCmd.stockTransStatus ==3">
                    <s:text name="MSG.GOD.192"/>
                </s:if>
                <s:elseif test="#attr.tbllistImpCmd.stockTransStatus ==5">
                    <s:text name="MSG.GOD.196"/>
                </s:elseif>
                <s:elseif test="#attr.tbllistImpCmd.stockTransStatus ==6">
                    <s:text name="MSG.GOD.197"/>
                </s:elseif>
                <s:elseif test="#attr.tbllistImpCmd.stockTransStatus ==1">
                    <s:text name="MSG.GOD.190"/>
                </s:elseif>
                <s:elseif test="#attr.tbllistImpCmd.stockTransStatus ==2">
                    <s:text name="MSG.GOD.191"/>
                </s:elseif>
                <s:else>
                    <s:property escapeJavaScript="true"  value="#attr.tbllistImpCmd.stockTransStatus"/> - Undefined
                </s:else>
            </display:column>

            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.031'))}" style="width:100px" property="note" sortable="false" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.207'))}" sortable="false" headerClass="tct">
                <div align="center">
                    <s:if test="#attr.tbllistImpCmd.stockTransStatus ==1"> <%-- chi nhung lenh chua lap phieu moi duoc lap phieu --%>
                        <s:url action="CreateImpNoteStockFromPartnerAction!prepareCreateNoteFromCmd" id="URL" encode="true" escapeAmp="false">
                            <s:param name="actionId" value="#attr.tbllistImpCmd.id.actionId"/>
                            <s:param name="struts.token.name" value="'struts.token'"/>
                            <s:param name="struts.token" value="struts.token"/>
                        </s:url>
                        <sx:a targets="KetQuaVaNut" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                            ${fn:escapeXml(imDef:imGetText('MSG.GOD.214'))}
                        </sx:a>
                    </s:if>
                    <s:if test="#attr.tbllistImpCmd.stockTransStatus <3"> <%-- chi huy nhung lenh chua xuat kho --%>
                        <s:url action="CreateImpNoteStockFromPartnerAction!destroyImpCmd" id="URL" encode="true" escapeAmp="false">
                            <s:param name="actionId" value="#attr.tbllistImpCmd.id.actionId"/>
                            <s:param name="struts.token.name" value="'struts.token'"/>
                            <s:param name="struts.token" value="struts.token"/>
                        </s:url>
                        &nbsp;&nbsp;
                        <a onclick="destroyExpCmd('<s:property escapeJavaScript="true"  value="#attr.URL"/>')">
                            ${fn:escapeXml(imDef:imGetText('MSG.GOD.204'))}
                        </a>
                    </div>
                </s:if>
            </display:column>
        </display:table>
    </tags:imPanel>
</sx:div>

<sx:div id="divCreateImpNoteToPartner">
</sx:div>
<script type="text/javascript">
    destroyExpCmd= function(url){
        var strConfirmMessage  = getUnicodeMsg('<s:text name="MSG.STK.004"/>');
        if(confirm(strConfirmMessage)){
            gotoAction("KetQuaVaNut", url,"importStockForm");
        }
    }
</script>
