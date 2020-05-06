<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listPayDebitShop
    Created on : May 15, 2009, 8:31:51 AM
    Author     : User one
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%
            if (request.getAttribute("listPayDebitShop") == null) {
                request.setAttribute("listPayDebitShop", request.getSession().getAttribute("listPayDebitShop"));
            }

            request.setAttribute("contextPath", request.getContextPath());
%>

<s:if test="#request.listPayDebitShop != null && #request.listPayDebitShop.size() > 0">
    <sx:div id="displayTagFrame">
        <display:table name="listPayDebitShop" id="tblListPayDebitShop" targets="displayTagFrame" pagesize="10" class="dataTable" cellpadding="1" cellspacing="1" requestURI="PayDebitShopAction!pageNavigator.do">
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.010'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
                ${fn:escapeXml(tblListPayDebitShop_rowNum)}
            </display:column>
            <!--s:hidden name="docDepositForm.bankReceiptId" id="docDepositForm.bankReceiptId"/-->
            <!--display:column title="Mã phiếu đặt cọc" property="depositId" headerClass="tct" sortable="true"/-->
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.032'))}" property="isdn" headerClass="tct" sortable="false"/>
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.033'))}" property="custName" headerClass="tct" sortable="false" style="text-align:left"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.034'))}" property="amount" format="{0}" headerClass="tct" sortable="false" style="text-align:right"/>
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.035'))}" property="name" headerClass="tct" sortable="false" style="text-align:left"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.036'))}" property="createDate" headerClass="tct" sortable="false" format="{0,date,dd/MM/yyyy}" style="text-align:left"/>
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.037'))}" property="createBy" headerClass="tct" sortable="false"/>
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.038'))}" property="telServiceName" headerClass="tct" sortable="false" style="text-align:left"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('L.100019'))}" headerClass="tct" sortable="false" style="text-align:left">
                <s:if test="#attr.tblListPayDebitShop.receiptId == null "> ${fn:escapeXml(imDef:imGetText('MSG.DET.039'))}</s:if>
                <s:else>${fn:escapeXml(imDef:imGetText('MSG.DET.040'))}</s:else>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.DET.041'))}" headerClass="tct" sortable="false" style="width:100px; text-align:center">
                <s:if test="#attr.tblListPayDebitShop.receiptId == null ">
                    <div align="center">
                        <s:if test="#attr.tblListPayDebitShop.staffId == #attr.tblListPayDebitShop.loginStaffId">
                            <s:url action="PayDebitShopAction!viewDepositDetail" id="URL" encode="true" escapeAmp="false">
                                <!-- TuTM1 04/03/2012 Fix ATTT CSRF -->
                                <s:token/>
                                <s:param name="depositId" value="#attr.tblListPayDebitShop.depositId"/>
                                <s:param name="struts.token.name" value="'struts.token'"/>
                                <s:param name="struts.token" value="struts.token"/>
                            </s:url>
                            <sx:a targets="detailArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                                ${fn:escapeXml(imDef:imGetText('MSG.DET.029'))}
                            </sx:a>
                        </s:if>
                    </div>
                </s:if>
                <s:else>
                    <div align="center">
                        <s:url action="PayDebitShopAction!viewDepositDetail" id="URL1" encode="true" escapeAmp="false">
                            <!-- TuTM1 04/03/2012 Fix ATTT CSRF -->
                            <s:token/>
                            <s:param name="depositId" value="#attr.tblListPayDebitShop.depositId"/>
                            <s:param name="struts.token.name" value="'struts.token'"/>
                            <s:param name="struts.token" value="struts.token"/>
                        </s:url>
                        <sx:a targets="detailArea" href="%{#URL1}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                            ${fn:escapeXml(imDef:imGetText('MSG.DET.030'))}
                        </sx:a>
                    </div>
                </s:else>
            </display:column>
        </display:table>
    </sx:div>                
</s:if>
