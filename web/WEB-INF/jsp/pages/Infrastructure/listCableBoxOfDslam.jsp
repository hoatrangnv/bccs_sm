<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listCableBox
    Created on : May 15, 2009, 5:46:35 PM
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
        request.setAttribute("contextPath", request.getContextPath());
%>

<s:if test="#request.listCableBox != null" >
    <sx:div id="displayTagFrame">
        <display:table pagesize="20"
                       id="tbllistCableBox" name="listCableBox"
                       class="dataTable" cellpadding="1" cellspacing="1"
                       targets="displayTagFrame"
                       requestURI="cableboxAction!pageNavigatorOfDslam.do">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
            ${fn:escapeXml(tbllistCableBox_rowNum)}
            </display:column>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.board.SubCableCode'))}" property="code"  sortable="false" headerClass="sortable"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.board.SubCableName'))}"  sortable="false" headerClass="sortable">
                <s:property escapeJavaScript="true"  value="#attr.tbllistCableBox.name" />
            </display:column>            
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.limited.sport'))}" property="maxPorts" style="text-align:right" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.total.sport.use'))}" property="usedPorts" style="text-align:right" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.total.sport.reserve'))}" property="reservedPort" style="text-align:right" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.coordinates.Y'))}" property="x" style="text-align:right" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.coordinates.X'))}" property="y" style="text-align:right" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.address'))}" property="address"  sortable="false" headerClass="sortable"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" headerClass="tct" sortable="false">
                <s:if test="#attr.tbllistCableBox.status == 1"><s:text name="MSG.active"/></s:if>
                <s:else><s:text name="MSG.inactive"/></s:else>
            </display:column>
            
            <display:column title="Sửa" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <s:url action="cableboxAction!prepareEditCableBoxOfDslam" id="URL1">
                    <s:param name="cableBoxId" value="#attr.tbllistCableBox.cableBoxId"/>
                </s:url>
                <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="Sửa" alt="Sửa"/>
                </sx:a>
            </display:column>
            <display:column title="Xoá" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <sx:a onclick="deleteCableBoxOfDslam('%{#attr.tbllistCableBox.cableBoxId}')">
                    <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa cáp nhánh" alt="Xóa cáp nhánh"/>
                </sx:a>            
            </display:column>
        </display:table>
    </sx:div>
</s:if>

