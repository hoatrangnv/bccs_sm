<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : switchResult
    Created on : Apr 17, 2009, 10:28:30 AM
    Author     : NamNX
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("listSwitch", request.getSession().getAttribute("listSwitch"));
%>

<sx:div id="listSwitch1">
 <tags:imPanel title="MSG.switches.list">
        
            <sx:div id="displayTagFrame">
                <display:table pagesize="10" targets="listSwitch"
                               id="Switch" name="listSwitch" class="dataTable"
                               requestURI="switchAction!pageNavigator.do"
                               cellpadding="1" cellspacing="1" >
                    <display:column title="No." headerClass="tct" sortable="false" style="width:50px; text-align:center">
                    ${fn:escapeXml(Switch_rowNum)}
                    </display:column>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.device'))}" headerClass="tct" sortable="false">
                        <s:property escapeJavaScript="true"  value="%{#attr.Switch.vendorName + ' - ' + #attr.Switch.equipmentName}"/>
                    </display:column>
                    <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.switch.code'))}" property="code" headerClass="tct" sortable="false"/>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.switch.name'))}"  headerClass="tct" sortable="false">
                        <s:property escapeJavaScript="true"  value="#attr.Switch.name" />
                    </display:column>
                    <display:column escapeXml="true" title="ip" property="ip" headerClass="tct" sortable="false"/>
                    
                    <display:column escapeXml="true" title="bras" property="brasName" headerClass="tct" sortable="false"/>

                    <display:column escapeXml="true" title="nmsVlan" property="nmsVlan" headerClass="tct" sortable="false"/>

                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" headerClass="tct" sortable="false">
                        <s:if test="#attr.Switch.status == 1"><tags:label key="MSG.active" /></s:if>
                        <s:else><tags:label key="MSG.inactive" /></s:else>
                    </display:column>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct"  style="width:50px; text-align:center">
                        <s:url action="switchAction!prepareEditSwitch" id="URL1">
                        <s:token/>
                        <s:param name="struts.token.name" value="'struts.token'"/>
                        <s:param name="struts.token" value="struts.token"/>
                            <s:param name="switchId" value="#attr.Switch.switchId"/>
                        </s:url>
                        <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                            <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit" />" alt="<s:text name="MSG.generates.edit" />"/>
                        </sx:a>
                    </display:column>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" headerClass="tct"  style="width:50px; text-align:center">
                        <s:url action="switchAction!deleteSwitch" id="URL2">
                        <s:token/>
                        <s:param name="struts.token.name" value="'struts.token'"/>
                        <s:param name="struts.token" value="struts.token"/>
                            <s:param name="switchId" value="#attr.Switch.switchId"/>
                        </s:url>
                        <sx:a onclick="confirmDelete('%{#attr.Switch.switchId}')" >
                            <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="MSG.generates.delete" />" alt="<s:text name="MSG.generates.delete" />"/>
                        </sx:a>
                    </display:column>
                </display:table>
            </sx:div >
        
        
    </tags:imPanel>
</sx:div>
