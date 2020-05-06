<%-- 
    Document   : ShowAccountBalance
    Created on : Nov 12, 2009, 8:31:20 AM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>

<%
            request.setAttribute("contextPath", request.getContextPath());
            String flag = (String) request.getSession().getAttribute("flag");
            String pageId = request.getParameter("pageId");
            request.setAttribute("typeZTE", request.getSession().getAttribute("typeZTE" + pageId));
%>

<s:if test="#request.typeZTE == true ">
    <sx:tabbedpanel id="tabContainer">
        <sx:div key="MSG.SIK.247" id="accountColl">
            <tags:imPanel title="MSG.SIK.070">
                <sx:div id="accountCreate" theme="simple">
                    <jsp:include page="AddAccountColl.jsp"/>
                </sx:div>
            </tags:imPanel>
        </sx:div>

        <sx:div key="MSG.SIK.247" id="anyPay">
            <tags:imPanel title="MSG.SIK.247">
                <sx:div id="accountCreate1" theme="simple">
                    <jsp:include page="accountAnyPayManagement.jsp"/>
                </sx:div>
            </tags:imPanel>
        </sx:div>
    </sx:tabbedpanel>
</s:if>

<s:else>
    <sx:tabbedpanel id="tabContainer">
        <sx:div key="MSG.SIK.247" id="accountColl">
            <sx:div id="accountCreate" theme="simple">
                <jsp:include page="AddAccountColl.jsp"/>
            </sx:div>
        </sx:div>
        <sx:div key="MSG.SIK.248" id="anyPayFPT">
            <tags:imPanel title="MSG.SIK.248">
                <sx:div id="accountCreate1FPT" theme="simple">
                    <jsp:include page="AccountAnyPayFPTManagement.jsp"/>
                </sx:div>
            </tags:imPanel>
        </sx:div>
    </sx:tabbedpanel>

</s:else>