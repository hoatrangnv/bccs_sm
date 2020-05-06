<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listBoards
    Created on : May 15, 2009, 5:06:51 PM
    Author     : User one
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        if (request.getAttribute("listBoards") == null) {
            request.setAttribute("listBoards", request.getSession().getAttribute("listBoards"));
        }
        request.setAttribute("contextPath", request.getContextPath());
%>

    <sx:div id="displayTagFrame">
        <display:table pagesize="20" id="tbllistBoards"
                       targets="displayTagFrame" name="listBoards"
                       class="dataTable"
                       requestURI="BoardsAction!pageNavigator.do"
                       cellpadding="1" cellspacing="1" >
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
            ${fn:escapeXml(tbllistBoards_rowNum)}
            </display:column>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.board.CableCode'))}" property="code" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.board.CableName'))}"  headerClass="tct" >
                <s:property escapeJavaScript="true"  value="#attr.tbllistBoards.name"/>
            </display:column>
            <!--display:column title="Mã DSLAM " property="dslamcode" headerClass="tct" sortable="false"/-->
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.board.CableAddress'))}" property="address" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.limited.sport'))}" property="maxPorts" style="text-align:right" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.total.sport.use'))}" property="usedPorts" style="text-align:right" headerClass="tct"/>
            <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.coordinates.Y'))}" property="x" style="text-align:right" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.coordinates.X'))}" property="y" style="text-align:right" headerClass="tct"/--%>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.total.sport.reserve'))}" property="reservedPort" style="text-align:right" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" headerClass="tct" sortable="false">
                <s:if test="#attr.tbllistBoards.status == 1"><s:text name="MSG.active"/></s:if>
                <s:else><s:text name="MSG.inactive"/></s:else>
            </display:column>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <s:url action="BoardsAction!prepareEditBoards" id="URL1">
                    <s:param name="boardId" value="#attr.tbllistBoards.boardId"/>
                </s:url>
                <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}"/>
                </sx:a>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <s:url action="BoardsAction!deleteBoards" id="URL2">
                    <s:token/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>
                    <s:param name="boardId" value="#attr.tbllistBoards.boardId"/>
                </s:url>
                <sx:a targets="divDisplayInfo" onclick="delBoard('%{#attr.tbllistBoards.boardId}')">
                    <img src="${contextPath}/share/img/delete_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}"/>
                </sx:a>
            </display:column>
            
        </display:table>
    </sx:div>
