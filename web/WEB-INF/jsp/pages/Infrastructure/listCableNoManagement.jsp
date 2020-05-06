<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listCableNoManagement
    Created on : Jun 9, 2011, 4:50:03 AM
    Author     : MrSun
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
    if (request.getAttribute("listCableNo") == null) {
        request.setAttribute("listCableNo", request.getSession().getAttribute("listCableNo"));
    }
    request.setAttribute("contextPath", request.getContextPath());
%>


<sx:div id="displayTagFrame">

    <display:table name="listCableNo" id="tblListCableNo" targets="displayTagFrame" pagesize="200" class="dataTable" cellpadding="1" cellspacing="1" requestURI="mdfAction!pageNavigator.do">
        <display:column title="No." headerClass="tct" sortable="false" style="width:50px; text-align:center">
            ${fn:escapeXml(tblListCableNo_rowNum)}
        </display:column>

        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('Province'))}" property="provinceCode" headerClass="tct" sortable="false" style="width:100px; text-align:left"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('Mdf'))}" property="mdfCode" headerClass="tct" sortable="false" style="width:200px; text-align:left" />
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('Main box'))}" property="boardCode" headerClass="tct" sortable="false" style="width:200px; text-align:left" />
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('Pai end box'))}" property="cableBoxCode" headerClass="tct" sortable="false" style="width:200px; text-align:left" />
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('Cable no'))}" property="portPosition" headerClass="tct" sortable="false" style="width:200px; text-align:right" />

        <display:column title="${fn:escapeXml(imDef:imGetText('Status'))}" headerClass="tct" sortable="false" style="width:75px; text-align:center">
            
            <s:if test="#attr.tblListCableNo.status == 1">free </s:if>
            <s:elseif test="#attr.tblListCableNo.status == 2">used </s:elseif>
            <s:elseif test="#attr.tblListCableNo.status == 3">locked </s:elseif>
            <s:elseif test="#attr.tblListCableNo.status == 0">damaged </s:elseif>
            <s:else>
                Undefine - <s:property escapeJavaScript="true"  value="#attr.tblListCableNo.status"/> 
            </s:else>
                
                
            <%--s:if test="#attr.tblListCableNo.status == 1"><s:text name="free" /> </s:if>
            <s:elseif test="#attr.tblListCableNo.status == 2"><s:text name="used" /> </s:elseif>
            <s:elseif test="#attr.tblListCableNo.status == 3"><s:text name="locked" /> </s:elseif>
            <s:elseif test="#attr.tblListCableNo.status == 0"><s:text name="damaged" /> </s:elseif>
            <s:else>
                Undefine - <s:property escapeJavaScript="true"  value="#attr.tblListCableNo.status"/> 
            </s:else--%>
        </display:column>
    </display:table>
</sx:div>
