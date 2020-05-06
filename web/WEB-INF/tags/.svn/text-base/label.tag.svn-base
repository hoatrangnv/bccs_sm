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
<%@taglib prefix="imDef" uri="imDef" %>

<%@attribute name="key" required="false"%>
<%@attribute name="value"%>
<%@attribute name="cssClass"%>
<%@attribute name="required"%>


<%
            com.viettel.database.DAO.BaseDAOAction baseDAOAction = new com.viettel.database.DAO.BaseDAOAction();
            request.setAttribute("tagValue", baseDAOAction.getText(key));
%>

<s:div cssClass="%{#attr.cssClass}" theme="simple">
    <s:if test="#attr.key != null && !#attr.key.trim().equals('')">
<!--        <s_:text  name="%{#attr.key}"></s_:text>-->
       <s:property value="%{#request.tagValue}"/>
        <s:if test="#attr.required != null && !#attr.required.trim().equals('') && !#attr.required.trim().equals('false') "><font color="red"> (*)</font></s:if>
    </s:if>
    <s:else>
        <s:property value="%{#attr.value}"/>
    </s:else>
</s:div>



