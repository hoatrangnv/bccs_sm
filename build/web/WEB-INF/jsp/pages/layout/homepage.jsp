<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%
            if (request.getAttribute("listWarnings") == null) {
                request.setAttribute("listWarnings", request.getSession().getAttribute("listWarnings"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>
<%@ taglib uri="/WEB-INF/config/vsa-defs.tld" prefix="vp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<table style="text-align:left; width:100%;">
    <s:iterator value="#request.listWarnings" var="warningDetails" status="StatuswarningDetails">
        <s:if test="#attr.warningDetails.total != 0">
            <tr>
                <td style="width:400px;" align="left">
                    <a onclick="actionAbc('<s:property escapeJavaScript="true"  value="#attr.warningDetails.linkStep"/>')">
                        <s:property escapeJavaScript="true"  value="#attr.warningDetails.nameWarning" />
                    </a>
                </td>
            </tr>

        </s:if>
    </s:iterator>
</table>

<div style="height:7px"></div>

<h3 align="left" style="font-size:20px;color:black; background-color:#D0DEF0">
    <div style="width: 100%; text-align: center; padding-top: 30px; padding-bottom: 30px;">
        <img src="${contextPath}/share/img/saleHome.jpg" title="" alt=""/>
    </div>
</h3>


<script>
    
    var browser=navigator.userAgent;
    var b_version=navigator.appVersion;
    var version=parseFloat(b_version);
    <%--
    if(browser.indexOf("Firefox")<=0){
        alert(getUnicodeMsg("Hệ thống bán hàng hiện tại chỉ tương thích với trình duyệt firefox. "));
        window.close();
    }
    --%>
    actionAbc = function(url){
        gotoAction("bodyContent",url);
    }
</script>




