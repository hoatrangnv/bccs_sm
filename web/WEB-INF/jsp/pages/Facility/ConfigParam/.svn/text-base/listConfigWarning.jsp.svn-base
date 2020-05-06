<%-- 
    Document   : listConfigWarning
    Created on : Aug 29, 2012, 4:38:37 PM
    Author     : kdvt_tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<sx:div id="displayTagFrame" >
    <display:table  id="lstWarning" name="lstWarning"
                    targets="displayTagFrame" 
                    class="dataTable" cellpadding="1" cellspacing="1" >
        <display:column headerClass="tct" title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}"  sortable="false" style="width:50px; text-align:center">
            ${fn:escapeXml(lstWarning_rowNum)}
        </display:column>        
        <display:column escapeXml="true"  property="toStep" title="${fn:escapeXml(imDef:imGetText('L.100044'))}" sortable="false" headerClass="tct" />
        <display:column escapeXml="true"  property="warnAmountDay" title="${fn:escapeXml(imDef:imGetText('L.100047'))}" sortable="false" headerClass="tct" style="text-align:right; width = 50px;"/>
        <display:column escapeXml="true"  property="lockAmountDay" title="${fn:escapeXml(imDef:imGetText('L.100048'))}" sortable="false" headerClass="tct" style="text-align:right; width = 50px;"/>
        <display:column escapeXml="true"  property="allowUrlType" title="${fn:escapeXml(imDef:imGetText('L.100049'))}" sortable="false" headerClass="tct" />
        <isplay:column escapeXml="true"  property="roleCode" title="${fn:escapeXml(imDef:imGetText('L.100050'))}" sortable="false" headerClass="tct" />
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" sortable="false" headerClass="tct" >
            <s:if test="#attr.lstWarning.status == 1">
                <s:text name="MSG.GOD.002"/>
            </s:if>
            <s:else>
                <s:text name="MSG.GOD.003"/>
            </s:else>
        </display:column>

        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.select'))}" sortable="false" style="text-align:center; width:50px" headerClass="tct">
                <a onclick="selectWarning('<s:property escapeJavaScript="true"  value="#attr.lstWarning.warningId"/>')">
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name = "MSG.select"/>" alt="<s:text name = "MSG.select"/>"/>
                </a>
            </display:column>
        
    </display:table>
</sx:div>

<script type="text/javascript" language="javascript">

    selectWarning = function(warningId){
        gotoAction("bodyContent",'${contextPath}/configWarningAction!selectWarning.do?warningId='
            + warningId + "&" + token.getTokenParamString(), form);
    }   

</script>
