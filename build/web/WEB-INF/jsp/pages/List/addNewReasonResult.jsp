<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : addNewReasonResult
    Created on : May 14, 2009, 11:59:01 AM
    Author     : ThanhNC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        if (request.getAttribute("listReason") == null) {
            request.setAttribute("listReason", request.getSession().getAttribute("listReason"));
        }
        request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="displayTagFrame">
    <display:table id="tblListReason" name="listReason"
                   pagesize="10" targets="displayTagFrame" requestURI="reasonAction!pageReasonNavigator.do"
                   class="dataTable" cellpadding="1" cellspacing="1" >
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" style="text-align:center; width:50px; " headerClass="tct" sortable="false">
        ${fn:escapeXml(tblListReason_rowNum)}
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.reason.code'))}" headerClass="tct" sortable="false">
            <s:property escapeJavaScript="true"  value="#attr.tblListReason.reasonCode"/>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.LST.022'))}" headerClass="tct" sortable="false">
            <s:property escapeJavaScript="true"  value="#attr.tblListReason.reasonName"/>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.LST.023'))}" headerClass="tct" sortable="false">
            <s:property escapeJavaScript="true"  value="#attr.tblListReason.reasonType"/>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.discription'))}" headerClass="tct">
            <s:property escapeJavaScript="true"  value="#attr.tblListReason.reasonDescription"/>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" headerClass="tct" style="width:90px; ">
            <s:if test="#attr.tblListReason.reasonStatus == 1">${fn:escapeXml(imDef:imGetText('MSG.active'))}</s:if>
            <s:elseif test="#attr.tblListReason.reasonStatus == 0">${fn:escapeXml(imDef:imGetText('MSG.inactive'))}</s:elseif>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.copy'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px;">
            <sx:a onclick="copyReason('%{#attr.tblListReason.reasonId}')">
                <img src="${contextPath}/share/img/clone.jpg" title="<s:text name="MSG.copy" />" alt="<s:text name="MSG.copy" />"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px; ">
            <s:url action="reasonAction!prepareEditReason" id="URL1">
                <s:param name="reasonId" value="#attr.tblListReason.reasonId"/>
            </s:url>
            <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit" />" alt="<s:text name="MSG.generates.edit" />"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.delete'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px; ">
            <sx:a onclick="delReason('%{#attr.tblListReason.reasonId}')">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="MSG.generates.delete" />" alt="<s:text name="MSG.generates.delete" />"/>
            </sx:a>
        </display:column>
    </display:table>
</sx:div>


<%--sx:div id="displayTagFrame">
<display:table pagesize="10" targets="displayTagFrame" id="tblListReason" name="listReason" class="dataTable" requestURI="reasonAction!pageReasonNavigator.do" cellpadding="1" cellspacing="1" >
<display:column title="STT" style="text-align:center" headerClass="tct" sortable="false">
${fn:escapeXml(tblListReason_rowNum)}
</display:column>
<display:column escapeXml="true"  title="Mã lý do" property="reasonCode" headerClass="tct" sortable="false"/>
<display:column escapeXml="true"  title="Tên lý do" property="reasonName" headerClass="tct" sortable="false"/>
<display:column escapeXml="true"  title="Loại lý do" property="reasonType" headerClass="tct" sortable="false"/>
<display:column escapeXml="true"  title="Mô tả" property="reasonDescription" headerClass="tct"/>
<display:column title="Trạng thái" headerClass="tct" >
<s:if test="#attr.tblListReason.reasonStatus == 1">Hiệu lực</s:if>
<s:else>Hết hiệu lực</s:else>
</display:column>
<display:column title="Sửa" sortable="false" headerClass="tct" style="text-align:center">
<s:url action="reasonAction!prepareEditReason" id="URL1">
<s:param name="reasonId" value="#attr.tblListReason.reasonId"/>
</s:url>
<sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
<img src="${contextPath}/share/img/edit_icon.jpg" title="Sửa" alt="Sửa"/>
</sx:a>
</display:column>
<display:column title="Xoá" sortable="false" headerClass="tct" style="text-align:center">
<s:url action="reasonAction!deleteReason" id="URL2">
<s:param name="reasonId" value="#attr.tblListReason.reasonId"/>
</s:url>
<s:a onclick="delReason('%{#attr.tblListReason.reasonId}')">
<img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa" alt="Xóa"/>
</s:a>
</display:column>
</display:table>
</sx:div--%>
