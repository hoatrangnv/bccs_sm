<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listIsdnFilterRules
    Created on : May 5, 2009, 4:48:32 PM
    Author     : tamdt1
    Desc       : danh sach cac isdnFilterRule
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
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("lstRule", request.getSession().getAttribute("listRules"));
%>

<!-- danh sach cac luat -->
<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MSG.rule.type.list'))}</legend>
    <display:table id="rule" name="lstRule" class="dataTable"
                   cellpadding="1" cellspacing="1"
                   requestURI="${contextPath}/isdnFilterRulesAction!pageNavigator.do"
                   pagesize="10" targets="divListIsdnFilterRules">
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px;">
            ${fn:escapeXml(rule_rowNum)}
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.rules.id'))}" sortable="false" property="rulesId" format="{0,number,000000}" style="text-align:left" headerClass="sortable"/>

        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.rules.name'))}" sortable="false" headerClass="sortable">
            <s:property escapeJavaScript="true"  value="#attr.rule.name"/>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.ruleType'))}" sortable="false" headerClass="sortable">
            <s:property escapeJavaScript="true"  value="#attr.rule.filterTypeName"/>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.price'))}" property="price" format="{0,number,#,###}" style="text-align:right" sortable="false" headerClass="sortable"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.masks'))}" sortable="false" headerClass="sortable">
            <s:property escapeJavaScript="true"  value="#attr.rule.maskMapping"/>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.conditionFilter'))}" sortable="false" headerClass="sortable">
            <s:property escapeJavaScript="true"  value="#attr.rule.condition"/>
        </display:column>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.priority.level'))}" property="ruleOrder" style="text-align:right" sortable="false" headerClass="sortable"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.copy'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px;">
            <sx:a onclick="copyRules('%{#attr.rule.rulesId}')">
                <img src="${contextPath}/share/img/clone.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.copy')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.copy')" />"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px;">
            <sx:a onclick="prepareEditRules('%{#attr.rule.rulesId}')">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')" />"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.delete'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px;">
            <sx:a onclick="delRules('%{#attr.rule.rulesId}')">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.generates.delete')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.generates.delete')" />"/>
            </sx:a>
        </display:column>
    </display:table>
</fieldset>



<script language="javascript">
    //
    prepareEditRules = function(rulesId) {
        gotoAction('bodyContent', "${contextPath}/isdnFilterRulesAction!prepareEditIsdnFilterRules.do?selectedRulesId=" + rulesId+"&iscopy=0"+"&"+ token.getTokenParamString());
    }
    copyRules = function(rulesId) {
        gotoAction('bodyContent', "${contextPath}/isdnFilterRulesAction!prepareEditIsdnFilterRules.do?selectedRulesId=" + rulesId+"&iscopy=1"+"&"+ token.getTokenParamString());
    }
    //
    delRules = function(rulesId) {
        var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('ERR.LST.037')"/>');
        if (confirm(strConfirm)) {
            gotoAction('bodyContent', "${contextPath}/isdnFilterRulesAction!delIsdnFilterRules.do?selectedRulesId=" + rulesId+"&"+ token.getTokenParamString());
        }
    }
</script>
