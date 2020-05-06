<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>



<sx:div id="informationDepositId">
    <%--<fieldset style="width:100%;">--%>
    <%--<legend class="transparent">Thông tin chung</legend>--%>
    <tags:imPanel title="MSG.info.general">
        <%--<div  style="width:100%; height:160px; overflow:auto; border-width:1px; border-style:solid; border-color:silver">--%>
        <div style="width:100%; height:230px; overflow:auto; border-width:1px; border-style:solid; border-color:silver">
            <table class="dataTable">
                <tr>
                    <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.010'))}</th>
                    <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.050'))}</th>
                    <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.051'))}</th>
                    <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.052'))}</th>
                    <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.053'))}</th>
                    <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.054'))}</th>
                    <th class="tct" width="15px">${fn:escapeXml(imDef:imGetText('MSG.DET.029'))} </th>
                </tr>
                <s:if test="expenseBeans != null && expenseBeans.size > 0" >
                    <s:iterator value="expenseBeans" status="staExpenseBeans">
                        <tr>
                            <td class="tct" style="text-align:center; "><s:property escapeJavaScript="true"  value="#staExpenseBeans.count" /></td>
                        <input type="hidden" id="hiddenStocKModelId_<s:property escapeJavaScript="true"  value="#staExpenseBeans.count" />" value="<s:property escapeJavaScript="true"  value="stockModelId" />">
                        <td class="tct"><div id="tdStockModelNameId_<s:property escapeJavaScript="true"  value="#staExpenseBeans.count" />">
                                <s:property escapeJavaScript="true"  value="stockModelName" /></div></td>
                        <td class="tct" style="text-align:right; "><s:property escapeJavaScript="true"  value="totalReceipt" /></td>
                        <td class="tct" style="text-align:right; "><s:if test="totalPay != null"><s:property escapeJavaScript="true"  value="totalPay" /></s:if>
                            <s:else>0</s:else></td>
                        <td class="tct" style="text-align:right; ">
                            <div id="total"></div>
                            <s:property escapeJavaScript="true"  value="deduct" />
                        </td>
                        <%--td class="tct"><s:property escapeJavaScript="true"  value="amount" /></td--%>
                        <td class="tct" style="text-align:right; ">
                            <s:text name="format.money">
                                <s:param name="value" value="amount"/>
                            </s:text>
                        </td>
                        <td class="tct" width="15px" style="text-align:center; ">
                            <s:url action="viewInformationDepositDetailAction!viewDepositDetailExport.do" var="viewInformationDepositDetail">
                                <s:param name="stockModelId" value="stockModelId" />
                                <s:param name="agentCode" value="agentCode" />
                                <s:param name="stockName" value="stockModelName" />
                            </s:url>
                            <sx:a targets="informationDepositDetailId"
                                  href="%{viewInformationDepositDetail}"
                                  loadingText="Processing..."
                                  showLoadingText="true"
                                  cssClass="cursorHand" executeScripts="true"
                                  parseContent="true">
                            </sx:a>
                        </td>

                        </tr>
                    </s:iterator>
                </s:if>
                <s:else>
                    <tr><td colspan="7"><tags:label key="MSG.noData"/> </td></tr>
                </s:else>
            </table>
        </div>
    </tags:imPanel>
    <%--</fieldset>--%>
</sx:div>
















