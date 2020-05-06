<%-- 
    Document   : addNewCommItemGroups
    Created on : Mar 29, 2009, 9:05:46 PM
    Author     : TheTM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>


<%
        request.setAttribute("contextPath", request.getContextPath());
%>
    
<table style="width:100%;">
    <tr>
        <td style="vertical-align:top; width:280px;">
            <tags:imPanel title="Nhóm khoản mục hoa hồng">            
                <div align="left" style="width:300px; height:600px; overflow:scroll; margin-left:5px; margin-top:5px;">
                    <tags:treeAjax
                        actionParamName="nodeId"
                        getNodeAction="${contextPath}/commItemGroupsAction!getCommItemGroupsTree.do"
                        rootId="0_"
                        rootTitle="Tất cả các nhóm khoản mục"
                        rootLink="${contextPath}/commItemGroupsAction!listCommItemGroups.do"
                        containerId="container111"
                        containerTextId="holder111"
                        loadingText="Loading..."
                        treeId="tree111"
                        target="divDisplayInfo"
                        lazyLoad="false"/>
                </div>
            </tags:imPanel>
        </td>
        <%--<td style="width:10px"></td>--%>
        <td style="vertical-align:top;">
            <div style="width:100%;">
                <sx:div id="divDisplayInfo">
                    <jsp:include page="listCommGroupItems.jsp"/>
                </sx:div>
            </div>
        </td>
    </tr>
</table>


