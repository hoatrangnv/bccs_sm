<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : addNewReasonGroupResult
    Created on : May 14, 2009, 11:52:26 AM
    Author     : ThanhNC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        if (request.getAttribute("listReasonGroup") == null) {
            request.setAttribute("listReasonGroup", request.getSession().getAttribute("listReasonGroup"));
        }

        request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="displayTagFrame">
    <display:table targets="displayTagFrame" pagesize="10" id="tblListReasonGroup" name="listReasonGroup" class="dataTable" requestURI="reasonGroupAction!pageReasonGroupNavigator.do" cellpadding="1" cellspacing="1" >
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" style="text-align:center; width:50px; " headerClass="tct" sortable="false">
        ${fn:escapeXml(tblListReasonGroup_rowNum)}
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.reason.code'))}" headerClass="tct">
            <s:property escapeJavaScript="true"  value="#attr.tblListReasonGroup.reasonGroupCode" />
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.LST.020'))}" headerClass="tct">
            <s:property escapeJavaScript="true"  value="#attr.tblListReasonGroup.reasonGroupName" />
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.discription'))}" headerClass="tct">
            <s:property escapeJavaScript="true"  value="#attr.tblListReasonGroup.description" />
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" headerClass="tct" style="width:90px; ">
            <s:if test="#attr.tblListReasonGroup.status == 1">${fn:escapeXml(imDef:imGetText('MSG.active'))}</s:if>
            <s:elseif test="#attr.tblListReasonGroup.status == 0">${fn:escapeXml(imDef:imGetText('MSG.inactive'))}</s:elseif>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.copy'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px;">
            <sx:a onclick="copyReasonGroup('%{#attr.tblListReasonGroup.reasonGroupId}')">
                <img src="${contextPath}/share/img/clone.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.copy'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.copy'))}"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
            <s:url action="reasonGroupAction!prepareEditReasonGroup" id="URL1">
                <s:param name="reasonGroupId" value="#attr.tblListReasonGroup.reasonGroupId"/>
            </s:url>
            <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.delete'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
            <sx:a onclick="delReasonGroup('%{#attr.tblListReasonGroup.reasonGroupId}')">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.delete'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.delete'))}"/>
            </sx:a>
        </display:column>
    </display:table>
</sx:div>

<!-- phan ben duoi la hien thi theo cach thong thuong, phan ben tren la hien thi su dung the s:property -->
<%--sx:div id="displayTagFrame">
    <display:table targets="displayTagFrame" pagesize="10" id="tblListReasonGroup" name="listReasonGroup" class="dataTable" requestURI="reasonGroupAction!pageReasonGroupNavigator.do" cellpadding="1" cellspacing="1" >
        <display:column title="STT" style="text-align:center; width:50px; " headerClass="tct" sortable="false">
            ${fn:escapeXml(tblListReasonGroup_rowNum)}
        </display:column>
        <display:column escapeXml="true"  title="Mã nhóm lý do" property="reasonGroupCode" headerClass="tct"/>
        <display:column escapeXml="true"  title="Tên nhóm lý do" property="reasonGroupName" headerClass="tct"/>
        <display:column escapeXml="true"  title="Mô tả" property="description" headerClass="tct"/>
        <display:column title="Trạng thái" headerClass="tct" style="width:80px; ">
            <s:if test="#attr.tblListReasonGroup.status == 1">Hiệu lực</s:if>
            <s:elseif test="#attr.tblListReasonGroup.status == 2">Hết hiệu lực</s:elseif>
        </display:column>
        <display:column title="Sửa" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
            <s:url action="reasonGroupAction!prepareEditReasonGroup" id="URL1">
                <s:param name="reasonGroupId" value="#attr.tblListReasonGroup.reasonGroupId"/>
            </s:url>
            <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="Sửa" alt="Sửa"/>
            </sx:a>
        </display:column>
        <display:column title="Xoá" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
            <s:url action="reasonGroupAction!deleteReasonGroup" id="URL2">
                <s:param name="reasonGroupId" value="#attr.tblListReasonGroup.reasonGroupId"/>
            </s:url>
            <s:a onclick="delReasonGroup('%{#attr.tblListReasonGroup.reasonGroupId}')">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa" alt="Xóa"/>
            </s:a>
        </display:column>
    </display:table>
</sx:div--%>

