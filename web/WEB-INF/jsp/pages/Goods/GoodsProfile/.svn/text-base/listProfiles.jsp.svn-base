<%-- 
    Document   : listProfiles
    Created on : Apr 13, 2009, 9:09:57 AM
    Author     : tamdt1
    Desc       : danh sach cac profile
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<div>
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.068'))}
<!--            Danh sách profile-->
        </legend>
        <display:table id="tblListProfiles" name="listProfiles" class="dataTable"
                       targets="divListProfiles" pagesize="20"
                       requestURI="profileAction!searchProfile.do?profileForm.stockTypeId=${fn:escapeXml(profileForm.stockTypeId)}&profileForm.stockModelCode=${fn:escapeXml(param['profileForm.stockModelCode'])}&profileForm.stockModelName=${fn:escapeXml(param['profileForm.stockModelName'])}"
                       cellpadding="1" cellspacing="1" >
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListProfiles_rowNum)}</display:column>
            <display:column escapeXml="true" property="profileName" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.061'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="linePattern" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.064'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="description" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.119'))}" sortable="false" headerClass="tct"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.generate.choice'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                <s:url action="profileAction!displayProfile" id="URL1">
                    <s:param name="selectedProfileId" value="#attr.tblListProfiles.profileId"/>
                </s:url>
                <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/accept.png"
                         title="<s:text name="MSG.GOD.069"/>" alt="<s:text name="MSG.GOD.070"/>"/>
<!--                         title="Chi tiết thông tin profile" alt="Chi tiết"/>-->
                </sx:a>
            </display:column>
        </display:table>
    </fieldset>
</div>
