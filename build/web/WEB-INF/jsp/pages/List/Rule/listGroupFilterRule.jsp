<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listGroupFilterRule
    Created on : Jun 5, 2009, 11:35:11 AM
    Author     : tamdt1
--%>

<%--
    Note: tach tu trang addNewGroupFilterRule.jsp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("lstGroup", request.getSession().getAttribute("lstGroup"));
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:if test="#request.lstGroup !=null && #request.lstGroup.size()>0">
    <display:table id="group" name="lstGroup"
                   class="dataTable"  cellpadding="1" cellspacing="1"
                   requestURI="${contextPath}/groupFilterRuleAction!pageNagivatorGroupFilterRule.do"
                   pagesize="10"
                   targets="divListGroupFilterRule">
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="text-align:center; width:50px; " headerClass="sortable">
            ${fn:escapeXml(group_rowNum)}
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.rules.of.set.id'))}" property="groupFilterRuleId"  format="{0,number,000000000000000}" style="text-align:left" headerClass="sortable"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.rules.of.set.code'))}" property="groupFilterRuleCode" sortable="false" headerClass="sortable"/>
        <%--display:column title="Tên tập luật" property="name" sortable="false" headerClass="sortable" /--%>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.rules.of.set.name'))}" sortable="false" headerClass="sortable">
            <s:property escapeJavaScript="true"  value="#attr.group.name"/>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.resource.type'))}" sortable="false" headerClass="sortable">
            <s:if test="#attr.group.stockTypeId == 1"><tags:label key="MSG.mobileNumber" /></s:if>
            <s:elseif test="#attr.group.stockTypeId == 2"><tags:label key="MSG.homephoneNumber" /></s:elseif>
            <s:elseif test="#attr.group.stockTypeId == 3"><tags:label key="MSG.pstnNumber" /></s:elseif>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.discription'))}" sortable="false" headerClass="sortable">
            <s:property escapeJavaScript="true"  value="#attr.group.notes"/>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.copy'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px;">
            <sx:a onclick="copyGroupFilterRule('%{#attr.group.groupFilterRuleId}')">
                <img src="${contextPath}/share/img/clone.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.copy')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.copy')" />"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px; ">
            <s:url action="groupFilterRuleAction!prepareEdit" id="URL1">
                <s:token/>
                <s:param name="groupFilterRuleId" value="#attr.group.groupFilterRuleId"/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>


            </s:url>
            <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')" />"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.delete'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px; ">
            <s:url action="groupFilterRuleAction!actionDeleteGroupFilterRule" id="URL2">
                <s:token/>
                <s:param name="groupFilterRuleId" value="#attr.group.groupFilterRuleId"/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>


            </s:url>
            <sx:a onclick="return confirmDelete('%{#attr.group.groupFilterRuleId}');">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.generates.delete')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.generates.delete')" />"/>
            </sx:a>
        </display:column>
    </display:table>

    <script language="javascript">
        confirmDelete = function (groupFilterRuleId){
            var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('ERR.LST.043')"/>');
            if (confirm(strConfirm)) {
        <%--if(confirm("Bạn có chắc chắn muốn xóa tập luật này không?")){--%>
                    gotoAction('bodyContent', 'groupFilterRuleAction!actionDeleteGroupFilterRule.do?groupFilterRuleId='+groupFilterRuleId+"&"+ token.getTokenParamString());
                }
            }
            copyGroupFilterRule = function (groupFilterRuleId){
                gotoAction('bodyContent', 'groupFilterRuleAction!prepareCopyGroupFilterRule.do?groupFilterRuleId='+groupFilterRuleId+"&"+ token.getTokenParamString());

            }
    </script>

</s:if>
<s:else>
    <table class="dataTable">
        <tr>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.rules.of.set.code'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.rules.of.set.name'))}</th>
            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.resource.type'))}</th>
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
            <td></td>
        </tr>
    </table>
</s:else>

