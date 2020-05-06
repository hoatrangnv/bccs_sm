<%-- 
    Document   : listDebitType
    Created on : Apr 25, 2013, 8:53:07 AM
    Author     : linhnt28
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<sx:div id="displayTagFrame">
    <%
        if (request.getAttribute("lstDebitType") == null) {
            request.setAttribute("lstDebitType", request.getSession().getAttribute("lstDebitType"));
        }
        request.setAttribute("contextPath", request.getContextPath());
    %>
    <fieldset class="imFieldset">
        <legend><s:property value="getText('TITLE.STOCK.CONFIGURATION.LIMITS.004')"/></legend>
        <s:if test="#session.lstDebitType != null && #session.lstDebitType.size() > 0">
            <display:table id="tblListDebitType" name="lstDebitType"
                           pagesize="10" targets="displayTagFrame"
                           requestURI="debitTypeAction!pageNavigator.do"
                           class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title="${imDef:imGetText('MSG.GOD.073')}" style="text-align:center" headerClass="tct" sortable="false">
                    ${tblListDebitType_rowNum}
                </display:column>
                <display:column title="${imDef:imGetText('MSG.DEBIT.TYPE.001')}" property="debitTypeName" style="text-align:left" headerClass="tct" sortable="false"/>
                <display:column title="${imDef:imGetText('MSG.DEBIT.TYPE.002')}" property="debitDayTypeName" headerClass="tct" sortable="false"/>
                <display:column title="${imDef:imGetText('MSG.DEBIT.TYPE.003')}" property="maxDebit" style="text-align:right" headerClass="tct" sortable="false" format="{0,number}" />
                <display:column escapeXml="true" title="${imDef:imGetText('MSG.LIMITED.06')}" sortable="false" headerClass="sortable">
                    <s:if test="#attr.tblListDebitType.status==1">
                        <s:text name="MSG.GOD.297"/>
                    </s:if>
                    <s:else>
                        <s:text name="MSG.GOD.274"/>
                    </s:else>
                </display:column>
                <display:column title="${imDef:imGetText('MSG.generates.edit')}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                    <s:url action="debitTypeAction!prepareEditDebitType" id="URL1">
                        <s:param name="debitTypeId" value="#attr.tblListDebitType.id"/>
                    </s:url>
                    <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property value="getText('MSG.generates.edit')"/>" alt="<s:property value="getText('MSG.generates.edit')"/>"/>
                    </sx:a>
                </display:column>
                <display:column title="${imDef:imGetText('MSG.delete')}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                    <s:if test="#attr.tblListDebitType.status==1">
                        <sx:a onclick="delDebitType('%{#attr.tblListDebitType.id}')" >
                            <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property value="getText('MSG.delete')"/>" alt="<s:property value="getText('MSG.delete')"/>"/>
                        </sx:a>
                    </s:if>
                </display:column>
                <display:footer> <tr> <td colspan="7" style="color:green"><s:property value="getText('MSG.totalRecord')"/>
                            <s:property escapeJavaScript="true"  value="%{#session.lstDebitType.size()}" /></td> <tr> </display:footer>
                    </display:table>
                </s:if>
                <s:else>
            <font color='red'>
                <s:property value="getText('MSG.blank.item')"/>
            </font>
        </s:else>
    </fieldset>
</sx:div>
