<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : viewPledgeInfo
    Created on : Jan 29, 2010, 3:15:24 PM
    Author     : NamNX
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        if (request.getAttribute("listPledgeInfo") == null) {
            request.setAttribute("listPledgeInfo", request.getSession().getAttribute("listPledgeInfo"));
        }
        request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.FAC.commitment.info">

    <div style="overflow:auto; max-height:350px;">
        <display:table name="listPledgeInfo"
                       id="tblListPledgeInfo" class="dataTable"
                       cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" headerClass="tct" style="width:40px; text-align:center">
            ${fn:escapeXml(tblListPledgeInfo_rowNum)}
            </display:column>
            <display:column property="price" title="${fn:escapeXml(imDef:imGetText('MSG.price'))}" format="{0,number,#,###}" sortable="false" headerClass="tct" />
            <display:column property="pledgeAmount" title="${fn:escapeXml(imDef:imGetText('MSG.FAC.LookUpISDN.Pledge'))}" format="{0,number,#,###}"  sortable="false" headerClass="tct" />
            <display:column escapeXml="true" property="pledgeTime" title="${fn:escapeXml(imDef:imGetText('MSG.PledgeTime'))}" sortable="false" headerClass="tct" style="width:100px; text-align:center; " />
            <display:column escapeXml="true" property="priorPay" title="${fn:escapeXml(imDef:imGetText('MSG.FAC.LookUpISDN.PayMonth'))}"  sortable="false" headerClass="tct" style="width:100px; text-align:center; "/>

        </display:table>
    </div>
</tags:imPanel>
