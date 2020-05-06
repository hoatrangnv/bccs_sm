<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : listBras
    Created on : May 19, 2011
    Author     : User one
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
            if (request.getAttribute("listStaffSearch") == null) {
                request.setAttribute("listStaffSearch", request.getSession().getAttribute("listStaffSearch"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="displayTagFrame">
    <display:table pagesize="10" id="tbllistStaffSearch"  name="listStaffSearch" class="dataTable" targets="displayTagFrame" requestURI="manageBTSGroupAction!pageNavigatorListStaff.do" cellpadding="1" cellspacing="1" >
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
            ${fn:escapeXml(tbllistStaffSearch_rowNum)}
        </display:column>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('STAFF_CODE'))}" property="staffCode" sortable="false" headerClass="tct" />
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('STAFF_NAME'))}" property="staffName" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('TARGET.YEAR'))}" property="monthAssgin" sortable="false" headerClass="tct"/>
        <display:column escapeXml="false" title="${fn:escapeXml(imDef:imGetText('BTS.Status'))}" headerClass="tct" sortable="false">
            <s:if test="#attr.tbllistStaffSearch.status == 1"><tags:label key="BTS.active" /></s:if>
            <s:else><tags:label key="BTS.inactive" /></s:else>
        </display:column>               
        <display:column escapeXml="false" title="${fn:escapeXml(imDef:imGetText('BTS.CreateDate'))}" format="{0,date,dd/MM/yyyy}" property="createDate" headerClass="tct" />

        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
            <s:url action="manageBTSGroupAction!editTarget" id="URL1">
                <s:param name="targetStaffMonthId" value="#attr.tbllistStaffSearch.targetStaffMonthId"/>
            </s:url>
            <sx:a targets="editTarget" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getText('MSG.generates.edit')"/>" alt="<s:property escapeJavaScript="true"  value="getText('MSG.generates.edit')"/>"/>
            </sx:a>
        </display:column>
        <s:token/>
    </display:table>
</sx:div>


<script language="javascript">
    //Sửa   
</script>
