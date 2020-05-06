<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listEquipment
    Created on : May 15, 2009, 8:46:23 AM
    Author     : User one
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
            if (request.getAttribute("listEquipment") == null) {
                request.setAttribute("listEquipment", request.getSession().getAttribute("listEquipment"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

    <sx:div id="displayTagFrame">

        <display:table name="listEquipment" id="tblListEquipment" targets="displayTagFrame" pagesize="10" class="dataTable" cellpadding="1" cellspacing="1" requestURI="equipmentAction!pageNavigator.do">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
                ${fn:escapeXml(tblListEquipment_rowNum)}
            </display:column>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.suppliers'))}" property="equipmentVendorName" headerClass="tct" sortable="false"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.device.type'))}" property="equipmentTypeName" headerClass="tct" sortable="false"/>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.device.code'))}" property="code" headerClass="tct" sortable="false" style="width:75px; text-align:left"/>
            
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.device.name'))}" headerClass="tct" sortable="false">
                <s:property escapeJavaScript="true"  value="#attr.tblListEquipment.name"/>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" headerClass="tct" sortable="false" style="width:75px; text-align:left">
                <s:if test="#attr.tblListEquipment.status == 1"><tags:label key="MSG.active" /></s:if>
                <s:if test="#attr.tblListEquipment.status == 0"><tags:label key="MSG.inactive" /></s:if>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.045'))}" headerClass="tct" sortable="false">
                <s:property escapeJavaScript="true"  value="#attr.tblListEquipment.description"/>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}"  sortable="false" headerClass="tct" style="text-align:center">
                <s:url action="equipmentAction!prepareEditEquipment" id="URL1">
                    <s:param name="equipmentId" value="#attr.tblListEquipment.equipmentId"/>
                </s:url>
                <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit" />" alt="<s:text name="MSG.generates.edit" />"/>
                </sx:a>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" headerClass="tct" style="text-align:center">
                <s:bean name="com.viettel.im.common.util.QueryCryptSessionBean" var="vDelDslamAreaId" >                        
                    <s:param name="queryName">equipmentId</s:param>
                    <s:param name="httpRequestWeb" value="request" />
                    <s:param name="parameterId" value="#attr.tblListEquipment.equipmentId" />
                </s:bean>  
                <a id="deleDlsAreaId_<s:property escapeJavaScript="true"  value="#attr.tblListEquipment.equipmentId"/>" onclick="confirmDelete('<s:property escapeJavaScript="true"  value="#vDelDslamAreaId.printParamCrypt()" />')">
                    <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="MSG.generates.delete" />" alt="<s:text name="MSG.generates.delete" />"/>
                </a>                                 
            </display:column>
        </display:table>
    </sx:div>


