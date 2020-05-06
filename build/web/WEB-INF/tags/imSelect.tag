<%-- 
    Document   : imSelect
    Created on : Aug 5, 2010, 6:52:44 PM
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
<%@attribute name="list" rtexprvalue="true"%>
<%@attribute name="headerKey" rtexprvalue="true"%>
<%@attribute name="headerValue" rtexprvalue="true"%>
<%@attribute name="listKey" rtexprvalue="true"%>
<%@attribute name="listValue" rtexprvalue="true"%>
<%@attribute name="onchange" rtexprvalue="true"%>
<%@attribute name="ondblclick" rtexprvalue="true"%>
<%@attribute name="theme" rtexprvalue="true"%>
<%@attribute name="value" rtexprvalue="true"%>
<%@attribute name="multiple" rtexprvalue="true"%>
<%@attribute name="size" rtexprvalue="true"%>



<script>
    noAction = function(){
        return true;
    }
</script>
<%

            if (onchange == null || onchange.trim().equals("")) {
                onchange = "noAction()";
            }
            if (headerKey == null || headerKey.trim().equals("")) {
                headerKey = "";
            }
            if (headerValue == null || headerValue.trim().equals("")) {
                headerValue = "";
            }
%>

<!-- neu truong hop disable, bo sung them bien hidden de giu gia tri khi post form -->
<s:if test="#attr.disabled != null && #attr.disabled == 'true' ">
    <s:hidden name="%{#attr.name}" id="%{#attr.id}_1"/>
</s:if>

<jsp:useBean id="tamdt1Test" class="java.util.HashMap" type="java.util.Map" scope="page"/>
<s:if test="#attr.listKey != null && #attr.listKey != '' && #attr.listValue != null && #attr.listValue != ''">
    <!-- truong hop su dung list object-->


    <c:forEach var="tmmpList" items="${imDef:imGetList(request, list)}" varStatus="status">
        <c:set var="tmpListKey" scope="page" value="${imDef:getObjectPropertyValue(tmmpList, listKey)}" />
        <c:set var="tmpListValue" scope="page" value="${imDef:getObjectPropertyValue(tmmpList, listValue)}" />
        <c:set target="${tamdt1Test}" property="${tmpListKey}" value="${tmpListValue}"/>
    </c:forEach>

    <c:set var="tmpHeaderValue" scope="page" value="${imDef:imGetText(headerValue)}" />

    <s:if test="#attr.tmpHeaderValue != null && #attr.tmpHeaderValue != '' ">
        <!--        Co gia tri header-->
        <s:if test="#attr.value != null && #attr.value != '' ">
            <!--            Co gia tri value-->
            <s:select name="%{#attr.name}" id="%{#attr.id}" theme="%{#attr.theme}" value="%{#attr.value}"
                      cssClass="%{#attr.cssClass}"
                      cssStyle="%{#attr.cssStyle}"
                      disabled="%{#attr.disabled}"
                      multiple="%{#attr.multiple}"
                      size="%{#attr.size}"
                      list="%{#attr.tamdt1Test!=null ? #attr.tamdt1Test : #{}}"
                      headerKey="%{#attr.headerKey}" headerValue="%{#attr.tmpHeaderValue}"
                      onchange="%{#attr.onchange}"
                      ondblclick="%{#attr.ondblclick}"
                      />
        </s:if>
        <s:else>
            <!--            Khong co gia tri value-->
            <s:select name="%{#attr.name}" id="%{#attr.id}" theme="%{#attr.theme}"
                      cssClass="%{#attr.cssClass}"
                      cssStyle="%{#attr.cssStyle}"
                      disabled="%{#attr.disabled}"
                      multiple="%{#attr.multiple}"
                      size="%{#attr.size}"
                      list="%{#attr.tamdt1Test!=null ? #attr.tamdt1Test : #{}}"
                      headerKey="%{#attr.headerKey}" headerValue="%{#attr.tmpHeaderValue}"
                      onchange="%{#attr.onchange}"
                      ondblclick="%{#attr.ondblclick}"
                      />
        </s:else>
    </s:if>
    <s:else>
        <!--        Khong co gia tri header-->
        <s:if test="#attr.value != null && #attr.value != '' ">
            <s:select name="%{#attr.name}" id="%{#attr.id}" theme="%{#attr.theme}" value="%{#attr.value}"
                      cssClass="%{#attr.cssClass}"
                      cssStyle="%{#attr.cssStyle}"
                      disabled="%{#attr.disabled}"
                      multiple="%{#attr.multiple}"
                      size="%{#attr.size}"
                      list="%{#attr.tamdt1Test!=null ? #attr.tamdt1Test : #{}}"
                      onchange="%{#attr.onchange}"
                      ondblclick="%{#attr.ondblclick}"
                      />
        </s:if><s:else>
            <s:select name="%{#attr.name}" id="%{#attr.id}" theme="%{#attr.theme}"
                      cssClass="%{#attr.cssClass}"
                      cssStyle="%{#attr.cssStyle}"
                      disabled="%{#attr.disabled}"
                      multiple="%{#attr.multiple}"
                      size="%{#attr.size}"
                      list="%{#attr.tamdt1Test!=null ? #attr.tamdt1Test : #{}}"
                      onchange="%{#attr.onchange}"
                      ondblclick="%{#attr.ondblclick}"
                      />
        </s:else>
    </s:else>

</s:if>
<s:else>

    <%

                com.viettel.database.DAO.BaseDAOAction baseDAOAction = new com.viettel.database.DAO.BaseDAOAction();

                java.util.List<com.viettel.im.common.imObject> tagList = new java.util.ArrayList<com.viettel.im.common.imObject>();
                com.viettel.im.common.imObject imObject = new com.viettel.im.common.imObject();
                String[] arr = list.split(",");
                for (int idx = 0; idx < arr.length; idx++) {
                    imObject = new com.viettel.im.common.imObject();

                    imObject.setKey(arr[idx].split(":")[0].trim());
                    imObject.setValue(arr[idx].split(":")[1].trim());
                    try {
                        //imObject.setValue(baseDAOAction.getText(imObject.getValue().toString()));
                        imObject.setValue(baseDAOAction.getText(imObject.getValue().toString()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    tagList.add(imObject);
                }

                request.setAttribute("tagList", tagList);

                try {
                    //request.setAttribute("tagHeaderValue", baseDAOAction.getText(headerValue));
                    String headerValueTemp = "";
                    if (headerValue != null && !headerValue.trim().equals("")) {
                        headerValueTemp = baseDAOAction.getText(headerValue);
                    }
                    request.setAttribute("tagHeaderValue", headerValueTemp);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
    %>


    <!-- truong hop su dung list truc tiep -->
    <%--c:forEach var="tmpList_out" items="${fn:split(pageScope['list'], ',')}">
        <c:forEach var="tmpList" items="${fn:split(pageScope['tmpList_out'], ':')}" varStatus="status">
            <c:choose>
                <c:when test="${status.index % 2 == 0}">
                    <c:set var="tmpListKey" scope="page" value="${imDef:imGetText(fn:trim(tmpList))}" />
                </c:when>
                <c:otherwise>
                    <c:set var="tmpListValue" scope="page" value="${imDef:imGetText(fn:trim(tmpList))}" />
                    <c:set target="${tamdt1Test}" property="${tmpListKey}" value="${tmpListValue}"/>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:forEach>

    <c:set var="tmpHeaderValue" scope="page" value="${imDef:imGetText(headerValue)}" />

    <s:if test="#attr.tmpHeaderValue != null && #attr.tmpHeaderValue != '' "--%>

    <s:if test="#request.tagHeaderValue != null && #request.tagHeaderValue != '' ">
        <!--        Co gia tri header-->
        <s:if test="#attr.value != null && #attr.value != '' ">
            <!--            Co gia tri value-->
            <%--s:select name="%{#attr.name}" id="%{#attr.id}" theme="%{#attr.theme}" value="%{#attr.value}"
                      cssClass="%{#attr.cssClass}"
                      cssStyle="%{#attr.cssStyle}"
                      disabled="%{#attr.disabled}"
                      multiple="%{#attr.multiple}"
                      size="%{#attr.size}"
                      list="%{#attr.tamdt1Test!=null ? #attr.tamdt1Test : #{}}"
                      headerKey="%{#attr.headerKey}" headerValue="%{#attr.tmpHeaderValue}"
                      onchange="%{#attr.onchange}"
                      ondblclick="%{#attr.ondblclick}"
                      /--%>

            <s:select name="%{#attr.name}" id="%{#attr.id}" theme="%{#attr.theme}" value="%{#attr.value}"
                      cssClass="%{#attr.cssClass}"
                      cssStyle="%{#attr.cssStyle}"
                      disabled="%{#attr.disabled}"
                      multiple="%{#attr.multiple}"
                      size="%{#attr.size}"
                      list="#request.tagList != null? #request.tagList :#{}"
                      headerKey="%{#attr.headerKey}" headerValue="%{#request.tagHeaderValue}"
                      listKey = "key" listValue="value"
                      onchange="%{#attr.onchange}"
                      ondblclick="%{#attr.ondblclick}"
                      />

        </s:if><s:else>
            <!--            Khong co gia tri value-->
            <%--s:select name="%{#attr.name}" id="%{#attr.id}" theme="%{#attr.theme}"
                      cssClass="%{#attr.cssClass}"
                      cssStyle="%{#attr.cssStyle}"
                      disabled="%{#attr.disabled}"
                      multiple="%{#attr.multiple}"
                      size="%{#attr.size}"
                      list="%{#attr.tamdt1Test!=null ? #attr.tamdt1Test : #{}}"
                      headerKey="%{#attr.headerKey}" headerValue="%{#attr.tmpHeaderValue}"
                      onchange="%{#attr.onchange}"
                      ondblclick="%{#attr.ondblclick}"
                      /--%>
            <s:select name="%{#attr.name}" id="%{#attr.id}" theme="%{#attr.theme}"
                      cssClass="%{#attr.cssClass}"
                      cssStyle="%{#attr.cssStyle}"
                      disabled="%{#attr.disabled}"
                      multiple="%{#attr.multiple}"
                      size="%{#attr.size}"
                      list="#request.tagList != null? #request.tagList :#{}"
                      headerKey="%{#attr.headerKey}" headerValue="%{#request.tagHeaderValue}"
                      listKey = "key" listValue="value"
                      onchange="%{#attr.onchange}"
                      ondblclick="%{#attr.ondblclick}"
                      />
        </s:else>

    </s:if>
    <s:else>
        <!--        Khong co gia tri header-->
        <s:if test="#attr.value != null && #attr.value != '' ">
            <!--            Co gia tri value-->
            <%--s:select name="%{#attr.name}" id="%{#attr.id}" theme="%{#attr.theme}" value="%{#attr.value}"
                      cssClass="%{#attr.cssClass}"
                      cssStyle="%{#attr.cssStyle}"
                      disabled="%{#attr.disabled}"
                      multiple="%{#attr.multiple}"
                      size="%{#attr.size}"
                      list="%{#attr.tamdt1Test!=null ? #attr.tamdt1Test : #{}}"
                      onchange="%{#attr.onchange}"
                      ondblclick="%{#attr.ondblclick}"
                      /--%>

            <s:select name="%{#attr.name}" id="%{#attr.id}" theme="%{#attr.theme}" value="%{#attr.value}"
                      cssClass="%{#attr.cssClass}"
                      cssStyle="%{#attr.cssStyle}"
                      disabled="%{#attr.disabled}"
                      multiple="%{#attr.multiple}"
                      size="%{#attr.size}"
                      list="#request.tagList != null? #request.tagList :#{}"
                      listKey = "key" listValue="value"
                      onchange="%{#attr.onchange}"
                      ondblclick="%{#attr.ondblclick}"
                      />
        </s:if><s:else>
            <!--            Khong co gia tri value-->
            <%--s:select name="%{#attr.name}" id="%{#attr.id}" theme="%{#attr.theme}"
                      cssClass="%{#attr.cssClass}"
                      cssStyle="%{#attr.cssStyle}"
                      disabled="%{#attr.disabled}"
                      multiple="%{#attr.multiple}"
                      size="%{#attr.size}"
                      list="%{#attr.tamdt1Test!=null ? #attr.tamdt1Test : #{}}"
                      onchange="%{#attr.onchange}"
                      ondblclick="%{#attr.ondblclick}"
                      /--%>
            <s:select name="%{#attr.name}" id="%{#attr.id}" theme="%{#attr.theme}"
                      cssClass="%{#attr.cssClass}"
                      cssStyle="%{#attr.cssStyle}"
                      disabled="%{#attr.disabled}"
                      multiple="%{#attr.multiple}"
                      size="%{#attr.size}"
                      list="#request.tagList != null? #request.tagList :#{}"
                      listKey = "key" listValue="value"
                      onchange="%{#attr.onchange}"
                      ondblclick="%{#attr.ondblclick}"
                      />
        </s:else>
    </s:else>

</s:else>
