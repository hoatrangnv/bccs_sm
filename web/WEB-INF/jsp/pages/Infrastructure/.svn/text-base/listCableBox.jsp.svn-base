<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listCableBox
    Created on : May 15, 2009, 5:46:35 PM
    Author     : User one
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        if (request.getAttribute("listcablebox") == null) {
            request.setAttribute("listcablebox", request.getSession().getAttribute("listcablebox"));
        }
        
        request.setAttribute("contextPath", request.getContextPath());
%>

<s:if test="#request.listcablebox != null" >
    <sx:div id="displayTagFrame">
        <display:table pagesize="20"
                       id="tbllistCableBox" name="listcablebox"
                       class="dataTable" cellpadding="1" cellspacing="1"
                       targets="displayTagFrame"
                       requestURI="cableboxAction!pageNavigator.do">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
            ${fn:escapeXml(tbllistCableBox_rowNum)}
            </display:column>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.board.SubCableCode'))}" property="code"  sortable="false" headerClass="sortable"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.board.SubCableName'))}"  sortable="false" headerClass="sortable">
                <s:property escapeJavaScript="true"  value="#attr.tbllistCableBox.name" />
            </display:column>
            <!--display:column title="Loại cáp nhánh" property="type"  sortable="false" headerClass="sortable"/-->
            <!--display:column title="Mã cáp tổng" property="boardscode"  sortable="false" headerClass="sortable"/-->
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.limited.sport'))}" property="maxPorts" style="text-align:right" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.total.sport.use'))}" property="usedPorts" style="text-align:right" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.total.sport.reserve'))}" property="reservedPort" style="text-align:right" sortable="false" headerClass="sortable"/>
            <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.coordinates.Y'))}" property="x" style="text-align:right" sortable="false" headerClass="sortable"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.coordinates.X'))}" property="y" style="text-align:right" sortable="false" headerClass="sortable"/--%>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.address'))}" property="address"  sortable="false" headerClass="sortable"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" headerClass="tct" sortable="false">
                <s:if test="#attr.tbllistCableBox.status == 1"><s:text name="MSG.active"/></s:if>
                <s:else><s:text name="MSG.inactive"/></s:else>
            </display:column>
            
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <s:url action="cableboxAction!prepareEditCableBox" id="URL1">
                    <s:token/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>
                    <s:param name="cableBoxId" value="#attr.tbllistCableBox.cableBoxId"/>
                </s:url>
                <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}"/>
                </sx:a>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <s:url action="cableboxAction!deleteCableBox" id="URL2">
                    <s:token/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>
                    <s:param name="cableBoxId" value="#attr.tbllistCableBox.cableBoxId"/>
                </s:url>
                <sx:a targets="divDisplayInfo" onclick="delcablebox('%{#attr.tbllistCableBox.cableBoxId}')">
                    <img src="${contextPath}/share/img/delete_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}"/>
                </sx:a>            
            </display:column>
        </display:table>
    </sx:div>
</s:if>

