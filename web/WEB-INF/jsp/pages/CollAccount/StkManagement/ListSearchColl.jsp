<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%--
    Document   : ListChangeInfoStaff
    Created on : 
    Author     : Vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%
    String pageId = request.getParameter("pageId");
    if (request.getAttribute("listColl") == null) {
        request.setAttribute("listColl", request.getSession().getAttribute("listColl" + pageId));
    }
    request.setAttribute("contextPath", request.getContextPath());
    request.setAttribute("typeId", request.getSession().getAttribute("typeId" + pageId));
    request.setAttribute("flag", request.getSession().getAttribute("flag" + pageId));
%>

<div>
    <tags:displayResult id="displayResultMsg" property="message" propertyValue="paramMsg" type="key" />
</div>
<div style="width:100%">
    <display:table id="coll" name="listColl" class="dataTable" pagesize="5"
                   targets="searchArea" requestURI="CollAccountManagmentAction!selectPage.do"
                   cellpadding="0" cellspacing="0">
        <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.SIK.147'))}" sortable="false" headerClass="tct" style="width:20px;text-align:center">
            <s:property escapeJavaScript="true"  value="%{#attr.coll_rowNum}"/>
        </display:column>
        <s:if test="#request.typeId == 2">

            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.SIK.092'))}" property="staffCode" style="width:130px;text-align:left"/>

            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.094'))}" property="name" style="width:240px;text-align:left"/>
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.148'))}" property="idNo" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.149'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <div align="center">
                    <s:set var="staffId" scope="request" value="%{#attr.coll.staffId}" />
                    <s:set var="accountId" scope="request" value="%{#attr.coll.accountId}" />

                    <%--<s:url action="CollAccountManagmentAction!clickAccountColl" id="URL" encode="true" escapeAmp="false">
                    <%--<s:param name="staffId" value="#attr.coll.staffId"/>
                    <s:param name="accountId" value="#attr.coll.accountId"/>
                    <s:token/>
                    <s:param name="staffId" value="%{#request.staffId}"/>
                    <s:param name="accountId" value="%{#request.accountId}"/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>
                </s:url>--%>
                    <sx:a targets="showView" onclick="showEdit('%{#request.staffId}','%{#request.accountId}')"
                          href="javascript: void(0);">
                        <s:if test="#attr.coll.status == 1 or #attr.coll.status == 2">
                            <tags:label key="MSG.SIK.149"/>                            
                        </s:if>
                        <s:else>
                            <tags:label key="MSG.SIK.149"/>                            
                        </s:else>
                    </sx:a>
                </div>
            </display:column>
        </s:if>
        <s:else>
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.093'))}" property="shopCode" style="width:130px;text-align:left"/>
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.095'))}" property="name" style="width:240px;text-align:left"/>
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.031'))}" property="address" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.149'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <div align="center">
                    <s:set var="staffId" scope="request" value="%{#attr.coll.staffId}" />
                    <s:set var="accountId" scope="request" value="%{#attr.coll.accountId}" />

                    <%--<s:url action="CollAccountManagmentAction!clickAccountColl" id="URL" encode="true" escapeAmp="false">
                    <%--<s:param name="staffId" value="#attr.coll.staffId"/>
                    <s:param name="accountId" value="#attr.coll.accountId"/>
                    <s:token/>
                    <s:param name="staffId" value="%{#request.staffId}"/>
                    <s:param name="accountId" value="%{#request.accountId}"/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>
                </s:url>--%>
                    <sx:a targets="showView" onclick="showEdit('%{#request.staffId}','%{#request.accountId}')"
                          href="javascript: void(0);">
                        <s:if test="#attr.coll.status == 1 or #attr.coll.status == 2">
                            <tags:label key="MSG.SIK.149"/>                            
                        </s:if>
                        <s:else>
                            <tags:label key="MSG.SIK.149"/>                            
                        </s:else>
                    </sx:a>
                </div>
            </display:column>
        </s:else>

        <display:footer> <tr> <td colspan="11" style="color:green">${fn:escapeXml(imDef:imGetText('MSG.SIK.150'))}<s:property escapeJavaScript="true"  value="%{#request.listColl.size()}" /></td> <tr> </display:footer>
        </display:table>

</div>

<sx:div id="showView" theme="simple">
    <s:if test="#request.flag == '1'">
        <jsp:include page="ShowViewStaffAndAccount.jsp"/>
    </s:if>
</sx:div>


<script type="text/javascript" language="javascript">
    showEdit = function(staffId,accountId)
    {
        gotoAction('showView','CollAccountManagmentAction!clickAccountColl.do?staffId=' + staffId+"&accountId="
            +accountId+"&"+token.getTokenParamString());
    }
    showEdit1 = function(shopId,accountId)
    {
        gotoAction('showView','CollAccountManagmentAction!clickAccountColl.do?shopId=' + shopId+"&accountId="
            +accountId+"&"+token.getTokenParamString());
    }
</script>
