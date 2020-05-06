<%-- 
    Document   : logBTSDetail
    Created on : Jun 22, 2016, 3:12:09 PM
    Author     : mov_itbl_dinhdc
--%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%
            String pageId = request.getParameter("pageId");
            if (request.getAttribute("listLogDetail") == null) {
                request.setAttribute("listLogDetail", request.getSession().getAttribute("listLogDetail" + pageId));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>
<sx:div id="logDetail">
    <input type="hidden"  name="pageId" id="pageId"/>
    <tags:imPanel title="MSG.SIK.267">
        <display:table pagesize="500" id="tblListDetail"
                       targets="logDetail" name="listLogDetail"
                       class="dataTable"
                       requestURI="CollAccountManagmentAction!showLogDetailNextPage.do"
                       cellpadding="1" cellspacing="1" >
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.147'))}" headerClass="tct" sortable="false" style="width:40px;text-align:center">
                ${fn:escapeXml(tblListDetail_rowNum)}
            </display:column>
            <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.200'))}" property="columnName" style="text-align:center" headerClass="tct"/>
            <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.201'))}" property="oldValue" style="text-align:center" headerClass="tct" sortable="false"/>
            <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.202'))}" property="newValue" style="text-align:center" headerClass="tct"/>
        </display:table>
    </tags:imPanel>
</sx:div>
<script type="text/javascript" language="javascript">

</script>
