<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listLookupBRM
    Created on : Nov 1, 2010, 11:57:22 AM
    Author     : Thetm
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
                       pagesize="10" targets="displayTagFrame" requestURI="lookupBRMAction!pageNavigatorBRM.do"
                       class="dataTable" cellpadding="1" cellspacing="1" >

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.DET.078'))}" style="text-align:center" headerClass="tct" sortable="false">
                ${fn:escapeXml(tblListBankReceiptExternal_rowNum)}
            </display:column>
            <display:column property="bankReceiptDate" headerClass="tct" format="{0,date,dd/MM/yyyy}" style="text-align:center;" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.070'))}"/>            
            <display:column escapeXml="true"  property="bankReceiptCode" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.073'))}"/>
            <display:column headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('Loại GNT'))}">

                <s:if test="#attr.tblListBankReceiptExternal.bankReceiptType==1">
                    ${fn:escapeXml(imDef:imGetText('GNT cửa hàng'))}
                </s:if>
                <s:elseif test="#attr.tblListBankReceiptExternal.bankReceiptType==2">
                    ${fn:escapeXml(imDef:imGetText('GNT ngân hàng'))}
                </s:elseif>
                <s:else>
                    Undefined - <s:property escapeJavaScript="true"  value="#attr.tblListBankReceiptExternal.bankReceiptType"/>
                </s:else>
            </display:column>
<<<<<<< .mine
            <display:column escapeXml="true"  property="bankName" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.071'))}"/>
            <display:column escapeXml="true"  property="bagName" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.072'))}"/>
            <display:column escapeXml="true"  property="accountNo" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('Số TK'))}"/>
            <display:column property="amount" headerClass="tct" sortable="false" style="text-align:right" format="{0,number,#,###.###}" title="${fn:escapeXml(imDef:imGetText('MSG.DET.074'))}"/>
            <display:column escapeXml="true"  property="content" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.076'))}"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" headerClass="tct" sortable="false" style="width:75px; text-align:left">
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
                    Undefined - <s:property escapeJavaScript="true"  value="#attr.tblListBankReceiptExternal.status"/>
                </s:else>
            </display:column>
            <display:column escapeXml="true"  property="shopFullName" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.069'))}"/>
            <display:column escapeXml="true"  property="staffFullName" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('Người thực hiện'))}"/>
            <display:column title="Hình thức nhập" headerClass="tct" sortable="false" style="width:75px; text-align:left">
                <s:if test="#attr.tblListBankReceiptExternal.inputType==1">
                    1 - Nhập tay
                </s:if>
                <s:elseif test="#attr.tblListBankReceiptExternal.inputType==2">
                    2- Import
                </s:elseif>                
                <s:else>
                    Undefined
                </s:else>
            </display:column>
        </display:table>

    </fieldset>
</sx:div>

