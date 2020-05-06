<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : informationDepositDetail.jsp
    Created on : May 7, 2009, 3:24:17 AM
    Author     : haidd
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<script language="javascript">
    divNF = function(divObject) {
        if(divObject != null) {
            //alert(divObject.innerHTML);
            divObject.innerHTML = addSeparatorsNF(divObject.innerHTML, '.', '.', ',');
        }
    }
</script>

<br />
<%--<fieldset style="width:100%;">--%>
    <%--<legend class="transparent">Chi tiết chi trả</legend>--%>
    <tags:imPanel title="MSG.detail.paid">
    <%--<div style="width:100%; height:253px; overflow:auto; border-width:1px; border-style:solid; border-color:silver">--%>
    <div style="width:100%; height:250px; overflow:auto; border-width:1px; border-style:solid; border-color:silver">
        <table class="dataTable">
            <tr>
                <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.010'))}</th>
                <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.062'))}</th>
                <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.063'))}</th>
                <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.064'))}</th>
                <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.056'))}</th>
                <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.065'))}</th>
                <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.054'))}</th>
                <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.029'))}</th>
            </tr>
            <s:if test="depositDetailBeans != null && depositDetailBeans.size > 0">
                <s:iterator value="depositDetailBeans" status="staDepositDetailBeans">
                    <tr>
                        <td class="tct" style="text-align:center; "><s:property escapeJavaScript="true"  value="#staDepositDetailBeans.count"/></td>
                        <td class="tct"><s:date name="createDate" format="dd/MM/yyyy" /></td>
                        <td class="tct"><s:if test="depositType == 1" > ${fn:escapeXml(imDef:imGetText('MSG.DET.066'))}</s:if><s:else> ${fn:escapeXml(imDef:imGetText('MSG.DET.067'))}</s:else></td>
                        <td class="tct"><s:property escapeJavaScript="true"  value="receiptNo"/></td>
                        <td class="tct" style="text-align:right; "><s:property escapeJavaScript="true"  value="quanity"/></td>
                        <input type="hidden" id="hiddenTempPriceId_<s:property escapeJavaScript="true"  value="#staDepositDetailBeans.count"/>" value="<s:property escapeJavaScript="true"  value="priceId"/>"/>
                        <td class="tct" style="text-align:right; ">
                            <div id="tdPriceId_<s:property escapeJavaScript="true"  value="#staDepositDetailBeans.count"/>">
                                <s:property escapeJavaScript="true"  value="price" />
                            </div>
                            <script>
                                divNF($('tdPriceId_<s:property escapeJavaScript="true"  value="#staDepositDetailBeans.count"/>'));
                            </script>
                        </td>
                        <td class="tct" style="text-align:right; ">
                            <div id='<s:property escapeJavaScript="true"  value="#staDepositDetailBeans.index" />'>
                                <s:property escapeJavaScript="true"  value="amount" />
                            </div>
                            <script>
                                divNF($('<s:property escapeJavaScript="true"  value="#staDepositDetailBeans.index" />'));
                            </script>
                        </td>
                        <%--td class="tct" style="text-align:right; ">
                            <s:text name="format.money">
                                <s:param name="value" value="amount"/>
                            </s:text>
                        </td--%>
                        <td class="tct" style="text-align:center; ">
                            <a href="#" onclick="setDataForViewStockModel(<s:property escapeJavaScript="true"  value="#staDepositDetailBeans.count"/>);"> ${fn:escapeXml(imDef:imGetText('MSG.DET.029'))}</a>
                        </td>
                    </tr>
                </s:iterator>
            </s:if>
            <s:else>
                <tr><td colspan="8"><tags:label key="MSG.noData"/></td></tr>
            </s:else>
        </table>
        <s:hidden name="tempStockModelIdDetail" id="tempStockModelIdDetailId" />
        <s:hidden name="tempStockModelNameDetail" id="tempStockModelNameDetailId" />
    </div>
    </tags:imPanel>
<%--</fieldset>--%>
