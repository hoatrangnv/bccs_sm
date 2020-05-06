<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : Port
    Created on : Apr 17, 2009, 10:28:30 AM
    Author     : NamNX
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("lstPort", request.getSession().getAttribute("lstPort"));
%>

<sx:div id="displayTagFrame">
    <display:table pagesize="25" targets="displayTagFrame" id="tblPort" name="lstPort" class="dataTable" requestURI="portAction!pageNavigator.do" cellpadding="1" cellspacing="1" >
        <display:column title="No" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblPort_rowNum)}</display:column>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.position.port'))}" property="portPosition" headerClass="tct" style="text-align:center; width:100px;"/>

        <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.vlan_id'))}" property="vlanId"  style="text-align:left ; width:100px; "/--%>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" property="statusName" headerClass="tct" style="text-align:left">
            <%--s:if test="#attr.tblPort.status == 0">Port hỏng</s:if>
                <s:if test="#attr.tblPort.status == 1">Port free</s:if>
                <s:if test="#attr.tblPort.status == 2">Port đang sử dụng</s:if>
                <s:if test="#attr.tblPort.status == 3">Port tạm giữ</s:if>
                <s:if test="#attr.tblPort.status == 4">Port cho Leased line</s:if--%>
        </display:column>
        <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.create.date'))}"  format="{0,date,dd/MM/yyyy}" property="createDate" headerClass="tct" style="text-align:left width:100px; "/--%>

        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="width:80px;text-align:center">
            <s:url action="portAction!prepareEditPort" id="URL1">
                <s:param name="portId" value="#attr.tblPort.portId"/>
            </s:url>
            <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit)" />" alt="<s:text name="MSG.generates.edit" />"/>
            </sx:a>
        </display:column>
        <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.copy'))}" sortable="false" headerClass="tct" style="width:80px;text-align:center">
            <s:url action="portAction!copyPort" id="URL4">
                <s:param name="portId" value="#attr.tblPort.portId"/>
            </s:url>
            <sx:a targets="divDisplayInfo" href="%{#URL4}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/clone.jpg" title="<s:text name="MSG.copy" />" alt="<s:text name="MSG.copy" />"/>
            </sx:a>
        </display:column--%>
        <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" headerClass="tct" style="width:80px;text-align:center">
            <s:url action="portAction!deletePort" id="URL2">
                <s:param name="portId" value="#attr.tblPort.portId"/>
            </s:url>
            <s:a onclick="delPort('%{#attr.tblPort.portId}')" >
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="MSG.generates.delete" />" alt="<s:text name="MSG.generates.delete" />"/>
            </s:a>
        </display:column--%>
    </display:table>
</sx:div>
