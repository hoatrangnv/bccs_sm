<%-- 
    Document   : treeAjax
    Created on : Apr 2, 2009, 9:53:36 PM
    Author     : thanhnv
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="treeId" required="true"%>
<%@attribute name="rootTitle" required="true"%>
<%@attribute name="rootId" required="true"%>
<%@attribute name="rootLink"%>
<%@attribute name="getNodeAction" required="true"%>
<%@attribute name="actionParamName" required="true"%>
<%@attribute name="containerId" required="true"%>
<%@attribute name="containerTextId" required="true"%>
<%@attribute name="loadingText" required="true"%>
<%@attribute name="target" required="true"%>
<%@attribute name="lazyLoad" %>
<%@attribute name="hasCheckbox" %>
<%@attribute name="cbName" %>
<%@attribute name="isExpandedLevel1" %>


<%
            com.viettel.database.DAO.BaseDAOAction baseDAOAction = new com.viettel.database.DAO.BaseDAOAction();
            rootTitle = baseDAOAction.getText(rootTitle);
            request.setAttribute("tagRootTitle",rootTitle);
            
%>
<sx:div parseContent="true" id="%{#attr.containerId}" separateScripts="true" executeScripts="true">
    <span id="${fn:escapeXml(containerTextId)}"
          style="background-color:#F00; color:#FFF;">
        ${fn:escapeXml(loadingText)}
    </span>
</sx:div>
<s:if test="#attr.hasCheckbox != null">
    <script type="text/javascript">
        builtTree(
        '${fn:escapeXml(rootId)}',
        "${fn:escapeXml(tagRootTitle)}",
        '${fn:escapeXml(rootLink)}',
        '${fn:escapeXml(treeId)}',
        '${fn:escapeXml(getNodeAction)}', '${fn:escapeXml(actionParamName)}',
        '${fn:escapeXml(containerId)}',
        '${fn:escapeXml(containerTextId)}',
        <%="'" + request.getContextPath() + "'"%>,
            '${fn:escapeXml(target)}',
        ${fn:escapeXml(lazyLoad)},
            true,
            '${fn:escapeXml(cbName)}',
            '${fn:escapeXml(isExpandedLevel1)}');
    </script>
</s:if>
<s:else>
    <s:if test="#attr.lazyLoad != null">
        <script type="text/javascript">
            builtTree(
            '${fn:escapeXml(rootId)}',
            "${fn:escapeXml(tagRootTitle)}",
            '${fn:escapeXml(rootLink)}',
            '${fn:escapeXml(treeId)}',
            '${fn:escapeXml(getNodeAction)}', '${fn:escapeXml(actionParamName)}',
            '${fn:escapeXml(containerId)}',
            '${fn:escapeXml(containerTextId)}',
            <%="'" + request.getContextPath() + "'"%>,
            '${fn:escapeXml(target)}',
            ${fn:escapeXml(lazyLoad)},
                false,
                '',
                '${fn:escapeXml(isExpandedLevel1)}');

        </script>
    </s:if>
    <s:else>
        <script type="text/javascript">
            builtTree(
            '${fn:escapeXml(rootId)}',
            "${fn:escapeXml(tagRootTitle)}",
            '${fn:escapeXml(rootLink)}',
            '${fn:escapeXml(treeId)}',
            '${fn:escapeXml(getNodeAction)}', '${fn:escapeXml(actionParamName)}',
            '${fn:escapeXml(containerId)}',
            '${fn:escapeXml(containerTextId)}',
            <%="'" + request.getContextPath() + "'"%>,
            '${fn:escapeXml(target)}',
            true,
            false,
            '${fn:escapeXml(cbName)}',
            '${fn:escapeXml(isExpandedLevel1)}' );
        </script>
    </s:else>
</s:else>