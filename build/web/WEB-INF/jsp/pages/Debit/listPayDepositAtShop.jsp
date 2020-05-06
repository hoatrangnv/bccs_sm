<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listPayDepositAtShop
    Created on : May 14, 2009, 5:15:39 PM
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
        if (request.getAttribute("listPayDepositAtShop") == null) {
            request.setAttribute("listPayDepositAtShop", request.getSession().getAttribute("listPayDepositAtShop"));
        }
        request.setAttribute("contextPath", request.getContextPath());
%>

<s:if test="#request.listPayDepositAtShop != null && #request.listPayDepositAtShop.size() > 0">
    <sx:div id="displayTagFrame">
        <display:table name="listPayDepositAtShop" id="tblListPayDepositAtShop" targets="displayTagFrame" pagesize="10" class="dataTable" cellpadding="1" cellspacing="1" requestURI="payDepositAtShopAction!pageNavigator.do">
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.010'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
                ${fn:escapeXml(tblListPayDepositAtShop_rowNum)}
            </display:column>
            <!--display:column title="Mã phiếu đặt cọc" property="depositId" headerClass="tct" sortable="true"/-->
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.032'))}" property="isdn" headerClass="tct" sortable="false"/>
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.033'))}" property="custName" headerClass="tct" sortable="false" style="text-align:left"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.034'))}" property="amount" format="{0}" headerClass="tct" sortable="false" style="text-align:right"/>
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.035'))}" property="name" headerClass="tct" sortable="false" style="text-align:left"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.036'))}" property="createDate" headerClass="tct" sortable="false" format="{0,date,dd/MM/yyyy}" style="text-align:left"/>
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.037'))}" property="createBy" headerClass="tct" sortable="false"/>
            <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.038'))}" property="telServiceName" headerClass="tct" sortable="false" style="text-align:left"/>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('L.100019'))}" headerClass="tct" sortable="false" style="text-align:left">
                <%--<s:if test="#attr.tblListPayDepositAtShop.type == \"1\" ">Chưa lập</s:if>
                <s:else>Đã lập</s:else>--%>
                <s:if test="#attr.tblListPayDepositAtShop.status * 1 == 0 ">${fn:escapeXml(imDef:imGetText('MSG.DET.039'))}</s:if>
                <s:else> ${fn:escapeXml(imDef:imGetText('MSG.DET.040'))}</s:else>
            </display:column>
            <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.DET.019'))}" headerClass="tct" sortable="false" style="width:60px; text-align:center">
                <%--<s:if test="#attr.tblListPayDepositAtShop.type == \"0\" ">--%>
                <s:if test="#attr.tblListPayDepositAtShop.status * 1 == 0 ">
                    <div align="center">
                        <%--<s:if test="#attr.tblListPayDepositAtShop.staffId == #attr.tblListPayDepositAtShop.loginStaffId">--%>
                            <s:url action="payDepositAtShopAction!viewDepositDetail" id="URL" encode="true" escapeAmp="false">
                                <!-- TuTM1 04/03/2012 Fix ATTT CSRF -->
                                <s:token/>
                                <s:param name="depositId" value="#attr.tblListPayDepositAtShop.depositId"/>
                                <s:param name="struts.token.name" value="'struts.token'"/>
                                <s:param name="struts.token" value="struts.token"/>
                            </s:url>
                            <sx:a targets="detailArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                                ${fn:escapeXml(imDef:imGetText('MSG.DET.029'))}
                            </sx:a>
                         <%--</s:if>--%>
                    </div>
                </s:if>
                <s:else>
                    <div align="center">
                        <s:url action="payDepositAtShopAction!viewDepositDetail" id="URL1" encode="true" escapeAmp="false">
                            <!-- TuTM1 04/03/2012 Fix ATTT CSRF -->
                            <s:token/>
                            <s:param name="depositId" value="#attr.tblListPayDepositAtShop.depositId"/>
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
<s:else>
    <font>
        <tags:label key="MSG.blank.item"/>
    </font>
</s:else>
