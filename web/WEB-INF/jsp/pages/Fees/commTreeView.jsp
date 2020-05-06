<%-- 
    Document   : commTreeView
    Created on : Mar 31, 2009, 2:17:16 PM
    Author     : DatPV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>


<fieldset style="width:95%; padding:10px 0px 0px 0px">
    <legend class="transparent">Khoản mục hoa hồng</legend>
    <%--                    <div align="left" style="width:400px; border-width:1px ;border-style:solid ;height:700px; overflow:scroll;"> --%>
    <div align="left" style="width:280px; height:600px; overflow:scroll;">

        <div style="width:600px; margin-left:-1px;">
            <tags:treeAjax
                actionParamName="nodeId"
                getNodeAction="${contextPath}/getCommTree.do"
                rootId="0_"
                rootTitle="Tất cả các khoản mục"
                rootLink="${contextPath}/getCommTree!preparePage.do"
                containerId="container111"
                containerTextId="holder111"
                loadingText="Loading..."
                treeId="tree111"
                target="divDisplayInfo"
                lazyLoad="false"
                />
         </div>
    </div>
</fieldset>


