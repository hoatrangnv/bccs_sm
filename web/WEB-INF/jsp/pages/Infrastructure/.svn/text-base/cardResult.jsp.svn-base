<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%--
    Document   : card
    Created on : Apr 17, 2009, 10:28:30 AM
    Author     : NamNX
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

            <%

            request.setAttribute("lstCard", request.getSession().getAttribute("lstCard"));

            %>
<sx:div id="lstCard">

<tags:imPanel title="MSG.card.list">


            <sx:div id="displayTagFrame">
                <display:table pagesize="10" targets="displayTagFrame" id="card" name="lstCard" class="dataTable" requestURI="cardAction!pageNavigator.do" cellpadding="1" cellspacing="1" >
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
                        ${fn:escapeXml(card_rowNum)}
                    </display:column>
                    <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.card.type'))}" sortable="false" property="cardTypeName" headerClass="tct"/>
                    <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.code'))}" property="code" sortable="false" headerClass="tct"/>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.name'))}"  sortable="false" headerClass="sortable">
                        <s:property escapeJavaScript="true"  value="#attr.card.name"/>
                    </display:column>
                    <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.device'))}" property="equipmentName" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.port.total'))}" property="totalPort" sortable="false" headerClass="tct"  style="text-align:right"/>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" headerClass="tct" sortable="false" style="width:80px">
                        <s:if test="#attr.card.status == 1"><tags:label key="MSG.active" /></s:if>
                        <s:else><tags:label key="MSG.inactive" /></s:else>
                    </display:column>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.discription'))}" sortable="false" headerClass="tct">
                                            <s:property escapeJavaScript="true"  value="#attr.card.description"/>
                </display:column>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                        <s:url action="cardAction!prepareEditCard" id="URLEdit">
                        <s:token/>
                        <s:param name="struts.token.name" value="'struts.token'"/>
                        <s:param name="struts.token" value="struts.token"/>
                            <s:param name="cardId" value="#attr.card.cardId"/>
                        </s:url>
                        <sx:a targets="bodyContent" href="%{#URLEdit}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                            <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')" />"/>
                        </sx:a>
                    </display:column>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" headerClass="sortable" style="width:50px; text-align:center">
                        <s:url action="cardAction!deleteCard" id="URLDel">
                        <s:token/>
                        <s:param name="struts.token.name" value="'struts.token'"/>
                        <s:param name="struts.token" value="struts.token"/>
                            <s:param name="cardId" value="#attr.card.cardId"/>
                        </s:url>
                        <sx:a onclick="confirmDelete('%{#attr.card.cardId}')" >
                            <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.generates.delete')" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.generates.delete')" />"/>
                        </sx:a>

                    </display:column>
                </display:table>
            </sx:div >
        
    </tags:imPanel>
</sx:div>
