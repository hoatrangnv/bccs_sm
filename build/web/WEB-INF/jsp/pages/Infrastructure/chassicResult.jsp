<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
    Document   : Chassic
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
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%        //request.setAttribute("listChassic", request.getSession().getAttribute("listChassic"));

%>

<sx:div id="displayTagFrame">
    <display:table pagesize="12" targets="divDisplayInfo" id="tblListChassic" name="listChassic" class="dataTable" requestURI="chassicAction!pageNavigator.do" cellpadding="1" cellspacing="1" >
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListChassic_rowNum)}</display:column>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.device.name'))}" property="equipmentName" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.code'))}" property="code" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.hostname'))}" property="hostName" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('meessages.generates.ip_address'))}" property="ip" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.switch'))}" property="switchName" headerClass="tct"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.chassic.type'))}"  headerClass="tct">
            <s:if test="#attr.tblListChassic.chassicType == 0"><s:text name="IP"/></s:if>
            <s:elseif test="#attr.tblListChassic.chassicType == 1"><s:text name="ATM"/></s:elseif>
            <s:elseif test="#attr.tblListChassic.chassicType == 2"><s:text name="ECI"/></s:elseif>
            <s:else>
                <s:property escapeJavaScript="true"  value="#attr.tblListChassic.chassicType"/> - Undefine
            </s:else>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" headerClass="tct" >
            <s:if test="#attr.tblListChassic.status == 1"><s:text name="MSG.active"/></s:if>
            <s:elseif test="#attr.tblListChassic.status == 0"><s:text name="MSG.inactive"/></s:elseif>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
            <s:url action="chassicAction!prepareEditChassic" id="URL1">
                <s:token/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>
                <s:param name="chassicId" value="#attr.tblListChassic.chassicId"/>
            </s:url>
            <sx:a onclick="editChassic('%{#attr.tblListChassic.chassicId}')" >
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit" />" alt="<s:text name="MSG.generates.edit" />"/>
            </sx:a>
            <%--<sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/edit_icon.jpg" title="Sửa" alt="Sửa"/>
                    </sx:a>--%>
        </display:column>
        <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.copy'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
            <s:url action="chassicAction!copyChassic" id="URL4">
                <s:param name="chassicId" value="#attr.tblListChassic.chassicId"/>
            </s:url>
            <sx:a onclick="copyChassic('%{#attr.tblListChassic.chassicId}')" >
                <img src="${contextPath}/share/img/clone.jpg" title="<s:text name="MSG.copy" />" alt="<s:text name="MSG.copy" />"/>
            </sx:a>

        </display:column--%>
        <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
            <s:url action="chassicAction!deleteChassic" id="URL2">
                <s:param name="chassicId" value="#attr.tblListChassic.chassicId"/>
            </s:url>
            <sx:a onclick="delChassic('%{#attr.tblListChassic.chassicId}')" >
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="MSG.generates.delete" />" alt="<s:text name="MSG.generates.delete" />"/>
            </sx:a>
        </display:column--%>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.slot.list'))}" sortable="false" style="width:50px;text-align:center" headerClass="tct">
            <s:url action="slotAction!displaySlot" id="URL3">
                <s:token/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>
                <s:param name="selectedChassicId" value="#attr.tblListChassic.chassicId"/>
            </s:url>
            <sx:a targets="divDisplayInfo" href="%{#URL3}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/accept.png" title="<s:text name="MSG.slot.list" />" alt="<s:text name="MSG.slot.list" />"/>
            </sx:a>
        </display:column>
    </display:table>
</sx:div >
