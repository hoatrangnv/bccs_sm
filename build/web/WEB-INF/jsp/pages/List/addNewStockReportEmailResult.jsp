<%-- 
    Document   : addNewStockReportEmailResult
    Created on : Jun 5, 2013, 10:17:04 AM
    Author     : LeVt1_S
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    if (request.getAttribute("listStockReportEmail") == null) {
        request.setAttribute("listStockReportEmail", request.getSession().getAttribute("listStockReportEmail"));
    }

    request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="displayTagFrame" cssStyle="overflow:scroll;">
    <display:table targets="displayTagFrame" pagesize="10" id="tblListStockReportEmail" name="listStockReportEmail" class="dataTable" requestURI="stockReportEmailAction!pageStockReportEmailNavigator.do" cellpadding="1" cellspacing="1" >
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" style="text-align:center; width:50px; " headerClass="tct" sortable="false">
            ${fn:escapeXml(tblListStockReportEmail_rowNum)}
        </display:column>
        <display:column property="email" escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MES.CHL.128'))}" headerClass="tct">
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.report.type'))}" headerClass="tct">
            <s:if test="#attr.tblListStockReportEmail.status == 1">${fn:escapeXml(imDef:imGetText('MSG.Serial.Stock'))}</s:if>
        </display:column>
        <display:column property="description" escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.discription'))}" headerClass="tct">
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" headerClass="tct" style="width:90px; ">
            <s:if test="#attr.tblListStockReportEmail.status == 1">${fn:escapeXml(imDef:imGetText('MSG.active'))}</s:if>
            <s:elseif test="#attr.tblListStockReportEmail.status == 0">${fn:escapeXml(imDef:imGetText('MSG.inactive'))}</s:elseif>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.copy'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px;">
            <s:set var="id" scope="request" value="%{#attr.tblListStockReportEmail.id}" />
            <%
                String copyStockReportEmailId = String.valueOf(request.getAttribute("id"));
                request.setAttribute("id", copyStockReportEmailId);
            %>
            <sx:a onclick="copyStockReportEmail('%{#request.id}')">
                <img src="${contextPath}/share/img/clone.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.copy'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.copy'))}"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
            <s:set var="id" scope="request" value="%{#attr.tblListStockReportEmail.id}" />
            <%
                String editStockReportEmailId = String.valueOf(request.getAttribute("id"));
                request.setAttribute("id", editStockReportEmailId);
            %>
            <sx:a onclick="editStockReportEmail('%{#request.id}')">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.delete'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
            <s:set var="id" scope="request" value="%{#attr.tblListStockReportEmail.id}" />
            <%
                String delStockReportEmailId = String.valueOf(request.getAttribute("id"));
                request.setAttribute("id", delStockReportEmailId);
            %>
            <sx:a onclick="delStockReportEmail('%{#request.id}')">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.delete'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.delete'))}"/>
            </sx:a>
        </display:column>
    </display:table>
</sx:div>

