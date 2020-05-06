<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : listPricePolicy
    Created on : Sep 29, 2010, 7:55:41 PM
    Author     : Doan Thanh 8
    Desc       : danh sach chinh sach chiet khau
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MSG.LST.805'))}</legend>
    <div style="width:100%; height:300px; overflow:auto;">
        <display:table name="listPricePolicy" id="tblListPricePolicy"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.LST.816'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListPricePolicy_rowNum)}</display:column>
            <display:column escapeXml="true"  property="pricePolicyName" title="${fn:escapeXml(imDef:imGetText('MSG.LST.NPP'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true"  property="defaultPricePolicyName" title="${fn:escapeXml(imDef:imGetText('MSG.LST.DPP'))}" sortable="false" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.LST.806'))}" sortable="false" headerClass="tct">
                <s:if test="#attr.tblListPricePolicy.pricePolicyStatus == 1">
                    ${fn:escapeXml(imDef:imGetText('MSG.LST.808'))}
                </s:if>
                <s:elseif test="#attr.tblListPricePolicy.pricePolicyStatus == 0">
                    ${fn:escapeXml(imDef:imGetText('MSG.LST.809'))}
                </s:elseif>
                <s:else>
                    undified
                </s:else>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.LST.817'))}" sortable="false" headerClass="tct" style="text-align:center; width:100px;">
                <s:url action="pricePolicyAction!prepareEditPricePolicy" id="URL1">
                    <s:token/>
                    <s:param name="pricePolicyForm.pricePolicyId" value="#attr.tblListPricePolicy.pricePolicyId"/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>


                </s:url>
                <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.LST.812'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.LST.812'))}"/>
                </sx:a>

                &nbsp;|&nbsp;

                <s:url action="pricePolicyAction!delPricePolicy" id="URL2" escapeAmp="false">
                    <s:token/>
                    <s:param name="pricePolicyForm.pricePolicyId" value="#attr.tblListPricePolicy.pricePolicyId"/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>
                </s:url>
                <a class="cursorHand" onclick="delPricePolicy('bodyContent','<s:property escapeJavaScript="true"  value="#attr.URL2"/>')">
                    <img src="${contextPath}/share/img/delete_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.LST.813'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.LST.813'))}"/>
                </a>
            </display:column>
        </display:table>
    </div>
</fieldset>

<script language="javascript">
    //
    delPricePolicy=function(target, action){
        var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.LST.818')"/>');
        if(confirm(strConfirm)){
            gotoAction(target, action);
        }
    }

</script>
