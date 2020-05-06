<%-- 
    Document   : DislayResult
    Created on : Dec 1, 2008, 5:38:05 PM
    Author     : ThanhNC
    Purpose: Hien thi thong bao sau khi thuc hien 1 action 
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@attribute name="property" required="true"%>
<%@attribute name="propertyValue"%>
<%@attribute name="type"%>
<%@attribute name="id" required="true"%>

<%
            String msg = (String) request.getAttribute(property);
            request.setAttribute("msg", msg);
            String displayType = "";
            if (type != null && !"".equals(type)) {
                displayType = type;
            }
            request.setAttribute("displayType", displayType);
%>

<div id="${id}" align="center" class="txtError">
    <s:if test="#request.msg != null && #request.msg !=''">
        <s:text name="%{#request.msg}">
            <%
                        //phan them tham so vao message neu co truyen theo gia tri cua tham so
                        if (propertyValue != null && request.getAttribute(propertyValue) != null) {
                            java.util.List listParamValue = (java.util.List) request.getAttribute(propertyValue); //lay list gia tri cua cac tham so
                            for (int i = 0; i < listParamValue.size(); i++) {
                                String strParamValue = com.viettel.security.util.StringEscapeUtils.escapeHtml(listParamValue.get(i).toString());
                                request.setAttribute("strParamValue", strParamValue);
            %>
            <s:param name="value" value="#request.strParamValue"/>
            <%
                            }
                        }
            %>
        </s:text>
    </s:if>
    <s:else>
        <s:property value="#request.msg"/>
    </s:else>
</div>