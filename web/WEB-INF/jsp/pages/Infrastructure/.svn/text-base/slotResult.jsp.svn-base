<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
    Document   : Slot
    Created on : Apr 17, 2009, 10:28:30 AM
    Author     : NamNX
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("lstSlot", request.getSession().getAttribute("lstSlot"));
%>

<sx:div id="displayTagFrame">
    <tags:imPanel title="MSG.slot.list">
         <%--s:property value="#session.chassicCode"/--%>

        <display:table pagesize="25" targets="displayTagFrame" id="slot" name="lstSlot" class="dataTable" requestURI="slotAction!pageNavigator.do" cellpadding="1" cellspacing="1" >
            <display:column title="No" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(slot_rowNum)}</display:column>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.slot.type'))}" property="slotTypeName" headerClass="tct" style="width:100px; text-align:center" />
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.position.slot'))}" property="slotPosition" headerClass="tct" style="text-align:center; width:80px; "/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" property="statusName" headerClass="tct" style="text-align:left; width:100px;" />
            
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.card'))}" property="cardCode" headerClass="tct" style="width:150px; text-align:left" />
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.position.start.port'))}" property="staPortPosition" headerClass="tct" style="text-align:center; width:80px; "/>
            <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.setup.date'))}"  format="{0,date,dd/MM/yyyy}" property="cardInstalledDate" headerClass="tct" style="text-align:left"/--%>

            
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <s:url action="slotAction!prepareEditSlot" id="URLEdit">
                    <s:param name="slotId" value="#attr.slot.slotId"/>
                </s:url>
                <sx:a targets="divDisplayInfo" href="%{#URLEdit}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit" />" alt="<s:text name="MSG.generates.edit" />"/>
                </sx:a>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.copy'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <s:url action="slotAction!copySlot" id="URL4">
                    <s:param name="slotId" value="#attr.slot.slotId"/>
                </s:url>
                <sx:a targets="divDisplayInfo" href="%{#URL4}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/clone.jpg" title="<s:text name="MSG.copy" />" alt="<s:text name="MSG.copy" />"/>
                </sx:a>

            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <s:url action="slotAction!deleteSlot" id="URLDel">
                    <s:param name="slotId" value="#attr.slot.slotId"/>
                </s:url>
                <s:a href="" onclick="delSlot('%{#attr.slot.slotId}')" >
                    <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="MSG.generates.delete" />" alt="<s:text name="MSG.generates.delete" />"/>
                </s:a>

            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.port.list'))}" sortable="false" style="width:50px;text-align:center" headerClass="tct">
                <s:url action="portAction!displayPort" id="URL3">
                    <s:param name="selectedSlotId" value="#attr.slot.slotId"/>
                </s:url>
                <sx:a targets="divDisplayInfo" href="%{#URL3}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/accept.png" title="<s:text name="MSG.port.list" />" alt="MSG.port.list"/>
                </sx:a>
            </display:column>
        </display:table>

    </tags:imPanel>
</sx:div >
