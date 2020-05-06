<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listBras
    Created on : May 16, 2009, 5:00:13 PM
    Author     : User one
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
        if (request.getAttribute("brasList") == null) {
            request.setAttribute("brasList", request.getSession().getAttribute("brasList"));
        }
        request.setAttribute("contextPath", request.getContextPath());
%>

    <sx:div id="displayTagFrame">
        <display:table pagesize="10" id="tblBrasList" name="brasList" class="dataTable" targets="displayTagFrame" requestURI="brasAction!pageNavigator.do" cellpadding="1" cellspacing="1" >
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
            ${fn:escapeXml(tblBrasList_rowNum)}
            </display:column>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.bras.code'))}" property="code" sortable="false" headerClass="tct" />
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.bras.name'))}" property="name" sortable="false" headerClass="tct" >
                <s:property escapeJavaScript="true"  value="#attr.tblBrasList.name"/>
            </display:column>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.ip'))}" property="ip" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.AAA_IP'))}" property="aaaIp" sortable="false" headerClass="tct" />
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.slot'))}" property="slot" sortable="false" headerClass="tct" />
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.port'))}" property="port" sortable="false" headerClass="tct" />
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.device'))}" property="equipmentName" headerClass="tct" />
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" headerClass="tct" sortable="false">
                <s:if test="#attr.tblBrasList.status == 1"><tags:label key="MSG.active" /></s:if>
                <s:else><tags:label key="MSG.inactive" /></s:else>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.discription'))}" property="description" headerClass="tct" >
                <s:property escapeJavaScript="true"  value="#attr.tblBrasList.description"/>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                <s:url action="brasAction!prepareEditBras" id="URL1">
                <s:token/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>
                    <s:param name="brasId" value="#attr.tblBrasList.brasId"/>
                </s:url>
                <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit" />" alt="<s:text name="MSG.generates.edit" />"/>
                </sx:a>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                <s:url action="brasAction!deleteBras" id="URL2">
                <s:token/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>
                    <s:param name="brasId" value="#attr.tblBrasList.brasId"/>
                </s:url>
               <sx:a onclick="confirmDelete('%{#attr.tblBrasList.brasId}')" >
                        <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="MSG.generates.delete" />" alt="<s:text name="MSG.generates.delete" />"/>
                    </sx:a>
            </display:column>
        </display:table>
    </sx:div>
