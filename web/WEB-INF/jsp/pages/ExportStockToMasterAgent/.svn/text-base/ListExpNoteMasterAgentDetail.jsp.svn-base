<%-- 
    Document   : ListExpNoteMasterAgentDetail
    Created on : Sep 13, 2016, 4:18:04 PM
    Author     : mov_itbl_dinhdc
--%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        
        request.setAttribute("listStockTransDetail", request.getSession().getAttribute("listStockTransDetail"));
        request.setAttribute("contextPath", request.getContextPath());
%>

   <sx:div id="displayTagFrame">
            <fieldset style="width:95%; padding:10px 10px 10px 10px">
                <legend class="transparent">${fn:escapeXml(imDef:imGetText('title.ListExpNoteMasterAgentDetail.page'))} </legend>
                <div style="overflow:auto; max-height:350px;">
                    <display:table targets="displayTagFrame" name="listStockTransDetail"
                                   id="listStockTransDetailId" class="dataTable"
                                   cellpadding="1" cellspacing="1">
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" headerClass="tct" style="width:40px; text-align:center" escapeXml="true">
                            ${fn:escapeXml(listStockTransDetailId_rowNum)}
                        </display:column>
                        <display:column property="nameCode" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.074'))}" escapeXml="true"/>
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.unit'))}" escapeXml="true">
                            <s:if test="#attr.listStockTransDetailId.stateId == 1">
                                ${fn:escapeXml(imDef:imGetText('MSG.RET.106'))}
                            </s:if>
                            <s:elseif test="#attr.listStockTransDetailId.stateId == 3">
                                ${fn:escapeXml(imDef:imGetText('MSG.RET.107'))}
                            </s:elseif>
                            <s:else>
                                undefined - <s:property escapeJavaScript="true"  value="#attr.listStockTransDetailId.stateId"/>
                            </s:else>
                        </display:column>
                        <display:column property="quantityRes" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.038'))}" escapeXml="true"/>
                        <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.206'))}" property="createDatetime" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
                        <display:column property="note" title="${fn:escapeXml(imDef:imGetText('note'))}" escapeXml="true"/>
                    </display:table>
                </div>
            </fieldset>
 </sx:div >

