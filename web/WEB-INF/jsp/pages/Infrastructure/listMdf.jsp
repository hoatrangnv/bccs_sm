<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listMdf
    Created on : Jun 3, 2011, 11:12:59 PM
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
    if (request.getAttribute("listMdf") == null) {
        request.setAttribute("listMdf", request.getSession().getAttribute("listMdf"));
    }
    request.setAttribute("contextPath", request.getContextPath());
%>


<sx:div id="displayTagFrame">

    <display:table name="listMdf" id="tblListMdf" targets="displayTagFrame" pagesize="10" class="dataTable" cellpadding="1" cellspacing="1" requestURI="mdfAction!pageNavigator.do">
        <display:column title="No." headerClass="tct" sortable="false" style="width:50px; text-align:center">
            ${fn:escapeXml(tblListMdf_rowNum)}
        </display:column>

        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('mdf.code'))}" property="code" headerClass="tct" sortable="false" style="width:100px; text-align:left"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('mdf.name'))}" property="name" headerClass="tct" sortable="false" style="width:200px; text-align:left" />

        <display:column title="${fn:escapeXml(imDef:imGetText('mdf.status'))}" headerClass="tct" sortable="false" style="width:75px; text-align:left">
            <s:if test="#attr.tblListMdf.status == 1"><s:text name="mdf.active" /> </s:if>
            <s:elseif test="#attr.tblListMdf.status == 0"><s:text name="mdf.inActive" /> </s:elseif>
            <s:else>
                - <s:property escapeJavaScript="true"  value="#attr.tblListMdf.status"/>
            </s:else>
        </display:column>

        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('mdf.province'))}"  property="province"  
                        headerClass="tct" sortable="false" style="width:75px; text-align:left"/>

        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('mdf.address'))}"  property="address"  
                        headerClass="tct" sortable="false" />
        
        <display:column title="${fn:escapeXml(imDef:imGetText('mdf.edit'))}"  sortable="false" headerClass="tct" style="width:75px; text-align:center">
            <s:url action="mdfAction!prepareEditMdf" id="URL1">
                <s:param name="mdfId" value="#attr.tblListMdf.mdfId"/>
            </s:url>
            <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="mdf.edit" />" alt="<s:text name="mdf.edit" />"/>
            </sx:a>
        </display:column>

        <display:column title="${fn:escapeXml(imDef:imGetText('mdf.delete'))}" sortable="false" headerClass="tct" style="width:75px; text-align:center" >
            <sx:a onclick="confirmDelete('%{#attr.tblListMdf.mdfId}')" >
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="mdf.delete" />" alt="<s:text name="mdf.delete"/>" />
            </sx:a>
        </display:column>
    </display:table>
</sx:div>
