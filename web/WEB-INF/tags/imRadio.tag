<%-- 
    Document   : imRadio
    Created on : Aug 6, 2010, 2:57:32 PM
    Author     : Doan Thanh 8
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="imDef" uri="imDef" %>


<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="name" rtexprvalue="true"%>
<%@attribute name="id" rtexprvalue="true"%>
<%@attribute name="cssClass" rtexprvalue="true"%>
<%@attribute name="cssStyle" rtexprvalue="true"%>
<%@attribute name="disabled" rtexprvalue="true"%>
<%@attribute name="theme" rtexprvalue="true"%>
<%@attribute name="list" rtexprvalue="true"%>
<%@attribute name="onchange" rtexprvalue="true"%>

<%-- any content can be specified here e.g.: --%>
<jsp:useBean id="tamdt1Test" class="java.util.HashMap" type="java.util.Map" scope="page"/>
<c:forEach var="tmpList_out" items="${fn:split(pageScope['list'], ',')}">
    <c:forEach var="tmmpList" items="${fn:split(pageScope['tmpList_out'], ':')}" varStatus="status">
        <c:choose>
            <c:when test="${status.index % 2 == 0}">
                <c:set var="tmpListKey" scope="page" value="${imDef:imGetText(fn:trim(tmmpList))}" />
            </c:when>
            <c:otherwise>
                <c:set var="tmpListValue" scope="page" value="${imDef:imGetText(fn:trim(tmmpList))}" />
                <c:set target="${tamdt1Test}" property="${tmpListKey}" value="${tmpListValue}"/>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</c:forEach>

<s:radio name="%{#attr.name}"
         id="%{#attr.id}"
         cssClass="%{#attr.cssClass}"
         cssStyle="%{#attr.cssStyle}"
         disabled="%{#attr.disabled}"
         theme="%{#attr.theme}"
         list="%{#attr.tamdt1Test!=null ? #attr.tamdt1Test : #{}}"
         onchange="%{#attr.onchange}" />

