<%@tag display-name="Stylish Button" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@attribute name="type" type="java.lang.String" required="true"%>
<%@attribute name="ajax" type="java.lang.Boolean" required="true"%>
<%@attribute name="functionType" type="java.lang.String"%>
<%@attribute name="target" type="java.lang.String"%>
<%@attribute name="actionPath" type="java.lang.String"%>
<%@attribute name="formId" type="java.lang.String"%>
<%@attribute name="onClick" type="java.lang.String"%>
<%@attribute name="other" type="java.lang.String"%>
<%@attribute name="getFormAsString" type="java.lang.String"%>
<%@attribute name="onComplete" type="java.lang.String"%>

<%@attribute name="messConfirm" type="java.lang.String"%>
<%@attribute name="id" %>
<%@attribute name="disabled" %>

<c:choose>
    <c:when test="${ajax}" >
        
        <s:if test="#attr.onComplete == null">
            <s:if test="#attr.getFormAsString == null">
                <button   id="${id}" type="${type}" ${functionType} ="${onClick}initProgress();new Ajax.Updater('${target}','${actionPath}&displayAjax=true' + getFormAsString('${formId}'),{onComplete:resetProgress});return false;"
                          class="stylish" ${other} >
                          <span><jsp:doBody/></span>
                </button>
            </s:if>
            <s:else>
                <button   id="${id}" type="${type}" ${functionType}="${onClick}initProgress();new Ajax.Updater('${target}','${actionPath}&displayAjax=true' + ${getFormAsString}('${formId}'),{onComplete:resetProgress});return false;"
                          class="stylish" ${other}>
                          <span><jsp:doBody/></span>
                </button>
            </s:else>
        </s:if>
        
        
        
        <s:if test="#attr.onComplete != null">
            <s:if test="#attr.getFormAsString == null">
                <button   id="${id}" type="${type}" ${functionType}="${onClick}initProgress();new Ajax.Updater('${target}','${actionPath}&displayAjax=true' + getFormAsString('${formId}'),{onComplete:${onComplete}});return false;"
                          class="stylish" ${other}>
                          <span><jsp:doBody/></span>
                </button>
            </s:if>
            <s:else>
                <button   id="${id}" type="${type}" ${functionType}="${onClick}initProgress();new Ajax.Updater('${target}','${actionPath}&displayAjax=true' + ${getFormAsString}('${formId}'),{onComplete:${onComplete}});return false;"
                          class="stylish" ${other}>
                          <span><jsp:doBody/></span>
                </button>
            </s:else>
        </s:if>
        
    </c:when>
    <c:otherwise>
        <button  type="${type}" class="stylish" onclick="${onClick};" ${other} id="${id}">
            <span><jsp:doBody/></span>
        </button>
    </c:otherwise>
</c:choose>

