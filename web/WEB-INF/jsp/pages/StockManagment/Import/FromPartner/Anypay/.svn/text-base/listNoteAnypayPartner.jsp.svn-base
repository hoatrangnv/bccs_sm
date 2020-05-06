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

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listImpNote", request.getSession().getAttribute("listImpNote"));
%>


<div>
    <fieldset class="imFieldset">
        <legend>${imDef:imGetText('MSG.listSearResult')}</legend>
        <display:table id="tblListImpNote" name="listImpNote" class="dataTable" pagesize="15" targets="divImpNoteFromPartner" requestURI="anypayPartnerAction!pageNavigatorNotes.do" cellpadding="1" cellspacing="1">
            <display:column  title="${imDef:imGetText('MSG.noteId')}" sortable="false" headerClass="tct" style="text-align:center">
                <s:property value="%{#attr.tblListImpNote_rowNum}"/>
            </display:column>
            <display:column escapeXml="true" title="${imDef:imGetText('MSG.noteId')}" property="actionCode"/>
            <display:column title="${imDef:imGetText('MSG.GOD.206')}" property="createDatetime" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${imDef:imGetText('MSG.partner')}" property="fromOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${imDef:imGetText('MSG.GOD.185')}" property="toOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${imDef:imGetText('MSG.reasonImport')}" property="reasonName" sortable="false" headerClass="tct"/>
            
            <display:column title="${imDef:imGetText('MSG.GOD.013')}" sortable="false" headerClass="tct">
            <s:if test="#attr.tblListImpNote.stockTransStatus ==3">
                    <s:text name="status.created.imported"/>
                </s:if>
                <s:elseif test="#attr.tblListImpNote.stockTransStatus ==4">
                    <s:text name="MSG.GOD.192"/>
                </s:elseif>
                <s:elseif test="#attr.tblListImpNote.stockTransStatus ==5">
                    <s:text name="status.destroyed"/>
                </s:elseif>
                <s:elseif test="#attr.tblListImpNote.stockTransStatus ==6">
                    <s:text name="MSG.GOD.197"/>
                </s:elseif>
                <s:elseif test="#attr.tblListImpNote.stockTransStatus ==1">
                    <s:text name="status.created.cmd"/>
                </s:elseif>
                 <s:elseif test="#attr.tblListImpNote.stockTransStatus ==2">
                    <s:text name="status.created.make"/>
                </s:elseif>
                <s:else>
                    <s:property value="#attr.tblListImpNote.stockTransStatus"/> - Undefined
                </s:else>

            </display:column>

            <display:column escapeXml="true" title="${imDef:imGetText('MSG.GOD.031')}" style="width:200px" property="note" sortable="false" headerClass="tct"/>
            <display:column title="${imDef:imGetText('MSG.GOD.207')}" sortable="false" headerClass="tct">
                <div align="center">
                    <!-- view phieu -->
                    <%-- ko view trang thai huy & da lap phieu --%>
                        <s:if test="#attr.tblListImpNote.stockTransStatus !=5 && #attr.tblListImpNote.stockTransStatus !=2">
                            <s:url action="anypayPartnerAction!prepareImpStockFromNote" id="URL" encode="true" escapeAmp="false">
                                <s:param name="actionId" value="#attr.tblListImpNote.actionId"/>
                            </s:url>
                            <sx:a targets="divImpFromPartnerFinal" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                                ${imDef:imGetText('MSG.detail')}
                            </sx:a>
                        </s:if>
                    <s:if test="#attr.tblListImpNote.stockTransStatus ==2"> <!-- chi nhung giao dịch đã lập phiếu mới được nhap kho -->
                        <s:url action="anypayPartnerAction!prepareImpStockFromNote" id="URL" encode="true" escapeAmp="false">
                            <s:param name="actionId" value="#attr.tblListImpNote.actionId"/>
                        </s:url>
                        <sx:a targets="divImpFromPartnerFinal" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                            ${imDef:imGetText('MSG.importToStore')}
                        </sx:a>
                    </s:if>
                </div>
            </display:column>
        </display:table>

    </fieldset>
</div>
<sx:div id="divImpFromPartnerFinal">
    <jsp:include page="impFromPartnerFinal.jsp"/>
</sx:div>        

<script>
    destroyImpNote= function(url){
        var strConfirmMessage  = getUnicodeMsg('<s:text name="MSG.STK.004"/>');
        if(confirm(strConfirmMessage)){
            gotoAction("divImpFromPartnerFinal", url + "&" + token.getTokenParamString(),"searchStockForm");
        }
    }
</script>