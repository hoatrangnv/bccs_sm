<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : listDiscounts
    Created on : Apr 18, 2009, 7:09:12 PM
    Author     : Doan Thanh 8
    Desc       : danh sach cac discount
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

<sx:div id="divListDiscountDetail">
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.326'))}</legend>
        <div style="height:375px; overflow:auto; ">
            <display:table id="tblListDiscountDetail" name="listDiscountDetail" class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListDiscountDetail_rowNum)}</display:column>
                <display:column property="fromAmount" title="${fn:escapeXml(imDef:imGetText('MSG.LST.034'))}" style="text-align:right" format="{0,number,#,###}" sortable="false" headerClass="tct"/>
                <display:column property="toAmount" title="${fn:escapeXml(imDef:imGetText('MSG.LST.035'))}" style="text-align:right" format="{0,number,#,###}" sortable="false" headerClass="tct"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.LST.036'))}" style="text-align:right" sortable="false" headerClass="tct"><s:text name="format.number">
                        <s:param value="#attr.tblListDiscountDetail.discountRateNumerator"></s:param>
                    </s:text>
                    /
                    <s:text name="format.number">
                        <s:param value="#attr.tblListDiscountDetail.discountRateDenominator"></s:param>
                    </s:text>
                </display:column>
                <display:column property="discountAmount" title="${fn:escapeXml(imDef:imGetText('MSG.LST.037'))}" style="text-align:right" format="{0,number,#,###}" sortable="false" headerClass="tct"/>
                <display:column property="startDatetime" title="${fn:escapeXml(imDef:imGetText('MSG.LST.038'))}" format="{0,date,dd/MM/yyyy}" style="text-align:center; width:90px; " sortable="false" headerClass="tct"/>
                <display:column property="endDatetime" title="${fn:escapeXml(imDef:imGetText('MSG.LST.039'))}" format="{0,date,dd/MM/yyyy}" style="text-align:center; width:90px; " sortable="false" headerClass="tct"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.LST.040'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                    <!-- neu row dang duoc chon -> hien thi icon khac voi icon cua cac hang con lai-->
                    <s:if test="#attr.tblListDiscountDetail.discountId == #attr.discountForm.discountId">
                        <img src="${contextPath}/share/img/accept_1.png" title="${fn:escapeXml(imDef:imGetText('MSG.LST.040'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.LST.040'))}"/>
                    </s:if>
                    <s:else>
                        <s:url action="discountGroupAction!displayDiscountDetail" id="urlDisplayDiscountDetail">
                            <s:param name="selectedDiscountId" value="#attr.tblListDiscountDetail.discountId"/>
                        </s:url>
                        <sx:a targets="sdivDiscountDetail" href="%{#urlDisplayDiscountDetail}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                            <img src="${contextPath}/share/img/accept.png" title="${fn:escapeXml(imDef:imGetText('MSG.LST.040'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.LST.040'))}"/>
                        </sx:a>
                    </s:else>
                </display:column>
            </display:table>
        </div>
    </fieldset>
</sx:div>

