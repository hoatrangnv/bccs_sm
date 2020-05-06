<%-- 
    Document   : infrastructureOverview
    Created on : Jun 15, 2009, 10:31:46 AM
    Author     : NamNX
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<table style="width:100%; background-color:#F5F5F5;">
    <tr>
        <td style="width:250px; vertical-align:top">
            <tags:imPanel title="MSG.items">

                <div align="left" style="width:250px; height:580px; overflow:scroll; margin-left:5px; margin-top:5px;">
                    <tags:treeAjax
                        actionParamName="nodeId"
                        getNodeAction="${contextPath}/chassicAction!getChassicTree.do"
                        rootId="0_"
                        rootTitle="MSG.all.area"
                        rootLink="${contextPath}/chassicAction!listProvince.do"
                        containerId="container111"
                        containerTextId="holder111"
                        loadingText="Loading..."
                        treeId="tree111"
                        target="divDisplayInfo"
                        lazyLoad="false"/>
                </div>
            </tags:imPanel>
        </td>
        <td style="width:10px"></td>
        <td style="vertical-align:top">
            <sx:div id="divDisplayInfo">
                <jsp:include page="listProvince.jsp"/>
            </sx:div>
        </td>
    </tr>
</table>