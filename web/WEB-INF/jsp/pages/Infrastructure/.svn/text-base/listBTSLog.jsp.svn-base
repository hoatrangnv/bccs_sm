<%-- 
    Document   : listBTSLog
    Created on : Jun 22, 2016, 3:10:35 PM
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
    if (request.getAttribute("listLog") == null) {
        request.setAttribute("listLog", request.getSession().getAttribute("listLog"));
    }
    request.setAttribute("contextPath", request.getContextPath());
%>
<div>
    <tags:displayResult id="message" property="returnMsg" propertyValue="returnMsgParam" type="key"/>
</div>

<sx:div id="displayTagFrame">
    <input type="hidden"  name="pageId" id="pageId"/>
    <div style="height:250px; overflow:auto; ">
        <display:table pagesize="500" id="tbllistLog"
                       targets="popupBody" name="listLog"
                       class="dataTable"
                       requestURI=""
                       cellpadding="1" cellspacing="1" >
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.147'))}" headerClass="tct" sortable="false" style="width:40px;text-align:center">
                ${fn:escapeXml(tbllistLog_rowNum)}
            </display:column>
            <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.242'))}" property="actionType" style="text-align:left" headerClass="tct"/>
            <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.243'))}" property="description" headerClass="tct" sortable="false"/>
            <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.244'))}" property="actionUser" headerClass="tct"/>
            <display:column escapeXml="false" title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.245'))}" property="actionDate" style="text-align:center" headerClass="tct" format="{0,date,dd/MM/yyyy kk:mm:ss}"/>
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.246'))}" property="actionIp" style="text-align:center" headerClass="tct"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.149'))}" sortable="false" style="text-align:center" headerClass="tct">
                <div align="center" style="vertical-align:middle; width:50px">
                    <s:url action="manageBTSAction!getLogDetail" id="URL">
                        <s:token/>
                        <s:param name="actionId" value="#attr.tbllistLog.actionId"/>
                        <s:param name="struts.token.name" value="'struts.token'"/>
                        <s:param name="struts.token" value="struts.token"/>
                    </s:url>
                    <sx:a targets="logDetail" href="%{#URL}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/accept.png" title="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.149')"/>" alt="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.149')"/>"/>
                    </sx:a>
                </div>
            </display:column>
        </display:table>
    </div>
</sx:div>
<sx:div id="logDetail">
    <div style="height:250px; overflow:auto; ">
        <jsp:include page="../Infrastructure/logBTSDetail.jsp"/>
    </div>
</sx:div>
<script type="text/javascript" language="javascript">

</script>

