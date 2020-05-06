<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listFilterType
    Created on : Jun 5, 2009, 10:38:32 AM
    Author     : tamdt1
--%>

<%--
    Note: tach tu trang addNewFilterType.jsp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("lstType", request.getSession().getAttribute("lstType"));
%>

<s:if test= "#request.lstType != null && #request.lstType.size() > 0">
    <display:table id="type" name="lstType"
                   requestURI="${contextPath}/filterTypeAction!pageNagivatorFilterType.do"
                   pagesize="10"
                   targets="divListFilterType"
                   class="dataTable" cellpadding="1" cellspacing="1">
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(type_rowNum)}</display:column>
        <%--display:column title="Tên nhóm luật" property="name" sortable="false" headerClass="sortable"/--%>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.rules.of.group.id'))}"  property="filterTypeId"format="{0,number,000000000000000}" style="text-align:left" headerClass="sortable"/>

        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.rules.of.group.name'))}" sortable="false" headerClass="sortable">
            <s:property escapeJavaScript="true"  value="#attr.type.name"/>
        </display:column>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.rules.of.set'))}" property="groupFilterRuleCode" sortable="false" headerClass="sortable"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.discription'))}" sortable="false" headerClass="sortable">
            <s:property escapeJavaScript="true"  value="#attr.type.notes"/>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.copy'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px;">
            <sx:a onclick="copyFilterType('%{#attr.type.filterTypeId}')">
                <img src="${contextPath}/share/img/clone.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.copy')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.copy')" />"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" style="width:50px; text-align:center" headerClass="sortable">
            <s:url action="filterTypeAction!prepareEdit" id="URL1">
                <s:param name="filterTypeId" value="#attr.type.filterTypeId"/>
            </s:url>
            <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')" />"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.delete'))}" sortable="false" style="width:50px; text-align:center" headerClass="sortable">
            <s:url action="filterTypeAction!actionDeleteFilterType" id="URL2">
                <s:token/>
                <s:param name="filterTypeId" value="#attr.type.filterTypeId"/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>


            </s:url>
            <sx:a onclick="return confirmDelete('%{#attr.type.filterTypeId}');">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.generates.delete')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.generates.delete')" />"/>
            </sx:a>
        </display:column>
    </display:table>

    <script language="javascript">
        confirmDelete = function (filterTypeId){
            var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('ERR.LST.020')"/>');
            if (confirm(strConfirm)) {
                gotoAction('bodyContent', 'filterTypeAction!actionDeleteFilterType.do?filterTypeId='+ filterTypeId + '&'+  token.getTokenParamString());
            }
        }
        copyFilterType = function (filterTypeId){
            gotoAction('bodyContent', 'filterTypeAction!prepareCopyFilterType.do?filterTypeId='+ filterTypeId + '&' +  token.getTokenParamString());

        }
    </script>

</s:if>
<s:else>
    <table class="dataTable">
        <tr>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.rules.of.group.name'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.rules.of.set'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.discription'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.delete'))}</th>
        </tr>
        <tr class="odd">
            <td>&nbsp;</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </table>
</s:else>
