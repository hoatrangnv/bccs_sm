<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : kitIntegration
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tuannv1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<table style="width:100%; ">
    <tr>
        <td style="width:50%; padding-right:5px; vertical-align:top; ">
            <!-- thong tin ve dai sim -->
            <sx:div id="divListSim">
                <jsp:include page="kitIntegrationSimSearchResult.jsp"/>
            </sx:div>
        </td>
        <td style="padding-left:5px; vertical-align:top; ">
            <!-- thong tin dai so -->
            <sx:div id="divListIsdn">
                <jsp:include page="kitIntegrationIsdnSearchResult.jsp"/>
            </sx:div>

        </td>
    </tr>

    <tr>
        <td colspan="2" style="text-align:center">
            <br />
            <s:form action="kitIntegrationAction" method="POST" id="kitIntegrationForm" theme="simple">
<s:token/>


                <tags:submit formId="kitIntegrationForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.create.file"
                             preAction="kitIntegrationAction!createKitFile.do"/>
            </s:form>
        </td>
    </tr>
    
    <tr>
        <td colspan="2" style="text-align:center">
            <s:if test="#request.kitFilePath != null">
                <a href="${contextPath}/${fn:escapeXml(kitFilePath)}">
                    <tags:displayResult id="kitFileMessage" property="kitFileMessage" type="key"/>
                </a>
            </s:if>
        </td>
    </tr>

</table>


<script type="text/javascript" language="javascript">

</script>
