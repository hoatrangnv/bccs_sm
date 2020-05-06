<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listDiscountGoods
    Created on : Apr 19, 2009, 11:25:46 AM
    Author     : Doan Thanh 8
    Desc       : danh sach cac mat hang thuoc nhom chiet khau
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>


<sx:div id="divListDiscountGoods">
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.327'))}</legend>
        <div style="height:375px; overflow:auto; ">
            <display:table id="tblListDiscountGoods" name="listDiscountGoods" class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                    ${fn:escapeXml(tblListDiscountGoods_rowNum)}
                </display:column>
                <display:column escapeXml="true"  property="stockModelCode" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.007'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true"  property="stockModelName" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.008'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true"  property="stockTypeName" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.027'))}" sortable="false" headerClass="tct"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.tblListDiscountGoods.status.equals(1L)">
                        ${fn:escapeXml(imDef:imGetText('MSG.GOD.002'))}
                    </s:if>
                    <s:else>
                        ${fn:escapeXml(imDef:imGetText('MSG.GOD.003'))}
                    </s:else>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.LST.040'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                    <!-- neu row dang duoc chon -> hien thi icon khac voi icon cua cac hang con lai-->
                    <s:if test="#attr.tblListDiscountGoods.discountModelMapId == #attr.discountModelMapForm.discountModelMapId">
                        <img src="${contextPath}/share/img/accept_1.png" title="${fn:escapeXml(imDef:imGetText('MSG.LST.040'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.LST.040'))}"/>
                    </s:if>
                    <s:else>
                        <s:url action="discountGroupAction!displayDiscountGoods" id="urlDisplayDiscountModelMap">
                            <s:param name="selectedDiscountGoodsId" value="#attr.tblListDiscountGoods.discountModelMapId"/>
                        </s:url>
                        <sx:a targets="sdivDiscountGoods" href="%{#urlDisplayDiscountModelMap}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                            <img src="${contextPath}/share/img/accept.png" title="${fn:escapeXml(imDef:imGetText('MSG.LST.040'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.LST.040'))}"/>
                        </sx:a>
                    </s:else>
                </display:column>
            </display:table>
        </div>
    </fieldset>
</sx:div>

