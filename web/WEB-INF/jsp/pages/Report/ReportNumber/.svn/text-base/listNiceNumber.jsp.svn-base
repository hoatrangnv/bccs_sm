<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listNiceNumber
    Created on : Nov 26, 2009, 2:29:58 PM
    Author     : NamNX
    Purpose : Danh sach so dep
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
        request.setAttribute("listNiceNumber", request.getSession().getAttribute("listNiceNumber"));
%>

<fieldset class="imFieldset">
    <legend>
        ${fn:escapeXml(imDef:imGetText('MSG.RET.057'))}
    <%--    Kết quả tìm kiếm--%>
    </legend>
    <div style="width:100%; height:300px; overflow:auto;">
        <display:table name="listNiceNumber" id="tblListNiceNumber"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="MSG.RET.049" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListNiceNumber_rowNum)}</display:column>
            <display:column escapeXml="true" property="groupFilterRuleName" title="MSG.RET.058" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="filterTypeName" title="MSG.RET.059" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="ruleName" title="MSG.RET.060" sortable="false" headerClass="tct"/>
            <%--display:column property="ruleId" title="Luật" sortable="false" headerClass="tct"/--%>
            <display:column escapeXml="true" property="count" title="MSG.RET.056" sortable="false" headerClass="tct"/>
            <%--display:column property="price" title="Giá tiền" sortable="false" headerClass="tct"/--%>
            <display:column escapeXml="true" property="totalMoney" title="MSG.RET.061" sortable="false" headerClass="tct"/>
        </display:table>
    </div>
</fieldset>
