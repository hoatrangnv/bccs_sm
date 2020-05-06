<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listDslam
    Created on : May 15, 2009, 3:00:40 PM
    Author     : User one
    Modified   : tamdt1, 08/10/2010
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="displayTagFrame">
    <div style="width:100%; height:300px; overflow:auto;">
        <s:if test="#request.listDslam != null">

            <display:table id="tblListDslam"  name="listDslam"
                           class="dataTable"  cellpadding="1" cellspacing="1">

                <display:column title="No" headerClass="tct" sortable="false" style="width:50px; text-align:center">
                    ${fn:escapeXml(tblListDslam_rowNum)}
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.dslam_dlu'))}" sortable="false" headerClass="sortable">
                    <s:property escapeJavaScript="true"  value="#attr.tblListDslam.code + ' - ' + #attr.tblListDslam.name"/>
                </display:column>
                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.limited.sport'))}" property="maxPort"  style="width:60px; text-align:right" sortable="false" headerClass="tct" format="{0}" />
                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.total.sport.use'))}" property="usedPort" style="width:60px; text-align:right" sortable="false" headerClass="tct" format="{0}"/>
                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.total.sport.reserve'))}" property="reservedPort" style="width:60px; text-align:right" sortable="false" headerClass="tct" format="{0}"/>
                
                <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.coordinates.Y'))}" sortable="false" property="x" style="width:60px; text-align:right" headerClass="tct" format="{0}"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.coordinates.X'))}" sortable="false" property="y" style="width:60px; text-align:right" headerClass="tct" format="{0}"/--%>

                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.dslam_dlu.ip'))}" sortable="false" property="dslamIp" headerClass="tct"/>
                
                <display:column escapeXml="true" title="Bras" sortable="false" property="brasIp" headerClass="tct" style="width:100px; text-align:left"/>
                
                <display:column escapeXml="true" title="MDF" sortable="false" property="mdfCode" headerClass="tct" style="width:100px; text-align:left"/>

                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" headerClass="tct" sortable="false" style="width:75px; text-align:left">
                    <s:if test="#attr.tblListDslam.status == 1"><tags:label key="MSG.active" /></s:if>
                    <s:else><tags:label key="MSG.inactive" /></s:else>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                    <s:url action="dslamAction!prepareEditDslam" id="URLEdit">
                        <s:param name="dslamId" value="#attr.tblListDslam.dslamId"/>
                    </s:url>
                    <sx:a targets="divDisplayInfo" href="%{#URLEdit}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/edit_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}"/>
                    </sx:a>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" style="width:50px; text-align:center" headerClass="sortable">
                    <s:bean name="com.viettel.im.common.util.QueryCryptSessionBean" var="vDelDslarmId" >                        
                        <s:param name="queryName">dslamId</s:param>
                        <s:param name="httpRequestWeb" value="request" />
                        <s:param name="parameterId" value="#attr.tblListDslam.dslamId" />
                    </s:bean>  
                    <a id="delDslarmIdId_<s:property escapeJavaScript="true"  value="#attr.tblListDslam.dslamId"/>" onclick="confirmDelete('<s:property escapeJavaScript="true"  value="#vDelDslarmId.printParamCrypt()" />')">
                        <img src="${contextPath}/share/img/delete_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}"/>
                    </a>                    
                </display:column>
            </display:table>
        </s:if>
    </div>
</sx:div>

