<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listEquipmentVendor
    Created on : May 15, 2009, 9:16:39 AM
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
        if (request.getAttribute("listEquipmentVendor") == null) {
            request.setAttribute("listEquipmentVendor", request.getSession().getAttribute("listEquipmentVendor"));
        }
        request.setAttribute("contextPath", request.getContextPath());
%>

    <sx:div id="displayTagFrame">
        <display:table name="listEquipmentVendor" id="tblListEquipmentVendor" targets="displayTagFrame" pagesize="10" class="dataTable" cellpadding="1" cellspacing="1" requestURI="equipmentVendorAction!pageNavigator.do">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
                ${fn:escapeXml(tblListEquipmentVendor_rowNum)}
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.suppliers.code'))}"  headerClass="tct" sortable="false">
                <s:property escapeJavaScript="true"  value="#attr.tblListEquipmentVendor.vendorCode"/>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.suppliers.name'))}"  headerClass="tct" sortable="false">
                <s:property escapeJavaScript="true"  value="#attr.tblListEquipmentVendor.vendorName"/>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.discription'))}"  headerClass="tct" sortable="false">
                <s:property escapeJavaScript="true"  value="#attr.tblListEquipmentVendor.description"/>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" headerClass="tct" sortable="false">
                <s:if test="#attr.tblListEquipmentVendor.status == 1"><tags:label key="MSG.active" /></s:if>
                <s:else><tags:label key="MSG.inactive" /></s:else>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}"  sortable="false" style="width:50px; text-align:center"  headerClass="tct" >
                <s:url action="equipmentVendorAction!prepareEditEquipmentVendor" id="URL1">
                    <s:param name="equipmentVendorId" value="#attr.tblListEquipmentVendor.equipmentVendorId"/>
                </s:url>
                <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit" />" alt="<s:text name="MSG.generates.edit" />"/>
                </sx:a>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" style="width:50px; text-align:center"  headerClass="tct" >
                <s:bean name="com.viettel.im.common.util.QueryCryptSessionBean" var="vEquVenderId" >                        
                    <s:param name="queryName">equipmentVendorId</s:param>
                    <s:param name="httpRequestWeb" value="request" />
                    <s:param name="parameterId" value="#attr.tblListEquipmentVendor.equipmentVendorId" />
                </s:bean>  
                <a id="deleEquVendorId_<s:property escapeJavaScript="true"  value="#attr.tblListEquipment.equipmentId"/>" onclick="confirmDelete('<s:property escapeJavaScript="true"  value="#vEquVenderId.printParamCrypt()" />')">
                    <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="MSG.generates.delet" />" alt="<s:text name="MSG.generates.delete" />"/>
                </a>                                   
            </display:column>
        </display:table>
    </sx:div>
