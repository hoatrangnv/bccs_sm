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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listImpCmd", request.getSession().getAttribute("listImpCmd"));
%>


    <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    <fieldset class="imFieldset">
        <legend>${imDef:imGetText('MSG.listSearResult')}</legend>
        <display:table id="tblListImpCmd" name="listImpCmd" class="dataTable" pagesize="15" targets="divCreateImpCmdFromPartner" requestURI="anypayPartnerAction!pageNavigatorDestroy.do" >
            <display:column title="${imDef:imGetText('MSG.GOD.073')}" sortable="false" headerClass="tct" style="text-align:center">
                <s:property value="%{#attr.tblListImpCmd_rowNum}"/>
            </display:column>
            <display:column escapeXml="true" title="${imDef:imGetText('MSG.GOD.205')}" property="actionCode"/>
            <display:column title="${imDef:imGetText('MSG.GOD.206')}" property="createDatetime" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${imDef:imGetText('MSG.GOD.184')}" property="fromOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${imDef:imGetText('MSG.partner')}" property="toOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${imDef:imGetText('MSG.reason')}" property="reasonName" sortable="false" headerClass="tct"/>
            
            <display:column escapeXml="true" title="${imDef:imGetText('MSG.GOD.031')}" style="width:100px" property="note" sortable="false" headerClass="tct"/>
            <display:column title="${imDef:imGetText('MSG.GOD.207')}" sortable="false" headerClass="tct">
                <div align="center">
                    <s:if test="#attr.tblListImpCmd.stockTransStatus != 5"> <%-- chi view nhung lenh chua bi huy --%>
                        <s:url action="anypayPartnerAction!viewDetailCommandDestroy" id="URL" encode="true" escapeAmp="false">
                            <s:param name="actionId" value="#attr.tblListImpCmd.actionId"/>
                        </s:url>
                        <sx:a targets="divExpCmdToPartner" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                            ${fn:escapeXml(imDef:imGetText('MSG.detail'))}
                        </sx:a>
                    </s:if>
                    </div>
            </display:column>
        </display:table>
    </fieldset>

<sx:div id="divExpCmdToPartner">
    <jsp:include page="destroyDetailCommand.jsp"/>
</sx:div>
<script>
    destroyImpCmd= function(url){
        var strConfirmMessage  = getUnicodeMsg('<s:text name="MSG.STK.004"/>');
        if(confirm(strConfirmMessage)){
            gotoAction("divCreateImpCmdFromPartner", url  + "&" + token.getTokenParamString(),"searchStockForm");
        }
    }
</script>