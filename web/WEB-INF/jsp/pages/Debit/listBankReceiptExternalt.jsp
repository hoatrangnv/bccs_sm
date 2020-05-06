<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listBankReceiptExternalt
    Created on : Oct 28, 2010, 11:53:25 AM
    Author     : Ebaymark
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="displayTagFrame">
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.DET.077'))}</legend>
        <display:table id="tblListBankReceiptExternal" name="listBankReceiptExternal"
                       pagesize="10" targets="displayTagFrame" requestURI="createSingleBankReceiptAction!pageNavigator.do"
                       class="dataTable" cellpadding="1" cellspacing="1" >
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.DET.078'))}" style="text-align:center" headerClass="tct" sortable="false">
            ${fn:escapeXml(tblListBankReceiptExternal_rowNum)}
            </display:column>
            <display:column escapeXml="true"  property="ownerCode" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.069'))}"/>
            <display:column escapeXml="true"  property="ownerName" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.088'))}"/>
            <display:column escapeXml="true"  property="bankName" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.071'))}"/>
            <display:column escapeXml="true"  property="bankAccountGroupName" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.072'))}"/>
            <display:column property="bankReceiptDate" headerClass="tct" format="{0,date,dd/MM/yyyy}" style="text-align:center;" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.070'))}"/>
            <display:column escapeXml="true"  property="bankReceiptCode" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.073'))}"/>
            <display:column property="amount" headerClass="tct" sortable="false" style="text-align:right" format="{0,number,#,###.###}" title="${fn:escapeXml(imDef:imGetText('MSG.DET.074'))}"/>
            <display:column escapeXml="true"  property="content" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.076'))}"/>
            <display:column headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}">
                <s:if test="#attr.tblListBankReceiptExternal.status==1">
                    ${fn:escapeXml(imDef:imGetText('MSG.DET.089'))}
                </s:if>
                <s:elseif test="#attr.tblListBankReceiptExternal.status==2">
                    ${fn:escapeXml(imDef:imGetText('MSG.DET.090'))}
                </s:elseif>
                <s:elseif test="#attr.tblListBankReceiptExternal.status==3">
                    ${fn:escapeXml(imDef:imGetText('MSG.DET.091'))}
                </s:elseif>
                <s:elseif test="#attr.tblListBankReceiptExternal.status==4">
                    ${fn:escapeXml(imDef:imGetText('MSG.DET.092'))}
                </s:elseif>
                <s:else>
                    Undefined - <s:property escapeJavaScript="true"  value="#attr.tblBankReceiptInternalForm.status"/>
                </s:else>
            </display:column>
            <%-- tam thoi comment ko cho phep sua, xoa
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <s:if test="#attr.tblListBankReceiptExternal.status == 1">
                    <s:url action="createSingleBankReceiptAction!prepareEditBankReceiptExternal" id="URL1">
                        <s:param name="bankReceiptExternalId" value="#attr.tblListBankReceiptExternal.bankReceiptExternalId"/>
                    </s:url>
                    <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')"/>" alt="<s:property escapeJavaScript="true"  value="getError('MSG.generates.edit')"/>"/>
                    </sx:a>
                </s:if>
                <s:else>
                    <img src="${contextPath}/share/img/edit_icon_1.jpg" title="" alt=""/>
                </s:else>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <s:if test="#attr.tblListBankReceiptExternal.status == 1">
                    <s:url action="createSingleBankReceiptAction!deleteBankReceiptExternal" id="URL2">
                        <s:param name="bankReceiptExternalId" value="#attr.tblListBankReceiptExternal.bankReceiptExternalId"/>
                    </s:url>
                    <sx:a targets="bodyContent" href="%{#URL2}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError('MSG.delete')"/>" alt="<s:property escapeJavaScript="true"  value="getError('MSG.delete')"/>"/>
                    </sx:a>
                </s:if>
                <s:else>
                    <img src="${contextPath}/share/img/delete_icon_1.jpg" title="" alt=""/>
                </s:else>
            </display:column>
            --%>
        </display:table>

    </fieldset>
</sx:div>


