<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listColaborators
    Created on : Jun 13, 2009, 3:58:52 PM
    Author     : tamdt1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listCollaborators", request.getSession().getAttribute("listCollaborators"));
%>

<display:table id="tblListColaborators" name="listCollaborators"
               class="dataTable" cellpadding="1" cellspacing="1"
               pagesize="5" requestURI="channelAction!pageNavigatorCollaborator.do"
               targets="divListCollaborator">
    <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
        ${fn:escapeXml(tblListColaborators_rowNum)}
    </display:column>
    <display:column escapeXml="true"  property="staffCode" title="${fn:escapeXml(imDef:imGetText('MES.CHL.055'))}" sortable="false" headerClass="tct"/>
    <display:column escapeXml="true"  property="name" title="${fn:escapeXml(imDef:imGetText('MES.CHL.056'))}" sortable="false" headerClass="tct"/>
    <display:column escapeXml="true"  property="nameManagement" title="${fn:escapeXml(imDef:imGetText('MES.CHL.059'))}" sortable="false" headerClass="tct"/>
    <display:column escapeXml="true"  property="nameChannelType" title="${fn:escapeXml(imDef:imGetText('MES.CHL.099'))}" sortable="false" headerClass="tct" style="text-align:center"/>
    <display:column escapeXml="true"  property="address" title="${fn:escapeXml(imDef:imGetText('MES.CHL.070'))}" sortable="false" headerClass="tct"/>
    <display:column escapeXml="true"  property="tel" title="${fn:escapeXml(imDef:imGetText('MES.CHL.095'))}" sortable="false" headerClass="tct"/>
    <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.062'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px">
        <s:a href="" onclick="prepareEditCollaborator(this, '%{#attr.tblListColaborators.staffId}')">
            <%--<img src="${contextPath}/share/img/edit_icon.jpg" title="Sửa thông tin CTV" alt="Sửa"/>--%>
            <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError(MES.CHL.100)"/>"  alt="<s:property escapeJavaScript="true"  value="getError(MES.CHL.062)"/>"/>
        </s:a>
    </display:column>
    <%--display:column title="Xoá" sortable="false" headerClass="tct" style="text-align:center; width:50px">
        <s:a onclick="deleteCollaborator(this, '%{#attr.tblListColaborators.staffId}')">
            <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa thông tin CTV" alt="Xóa"/>
        </s:a>
    </display:column--%>
</display:table>

<script language="javascript">
    //bat popup sua thong tin CTV
    prepareEditCollaborator = function(aObject, staffId) {
        aObject.href = "${contextPath}/channelAction!prepareEditCollaborator.do?selectedStaffId=" + staffId + '&' + token.getTokenParamString();
    }

    //xoa thong tin ve nhan vien
    deleteCollaborator = function(aObject, staffId) {
    <%--if (confirm('Bạn có chắc chắn muốn xóa CTV này không?')) {--%>
            var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MES.CHL.101')"/>');
            if (confirm(strConfirm)){
                aObject.href = "${contextPath}/channelAction!deleteCollaborator.do?selectedStaffId=" + staffId + '&' + token.getTokenParamString() ;
            }
        }

</script>

