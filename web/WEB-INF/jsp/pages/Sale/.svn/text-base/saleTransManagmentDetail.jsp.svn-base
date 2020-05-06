<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : saleTransManagmentDetail.jsp
    Created on : 19/06/2009
    Author     : ThanhNC
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<div style="width:100%">
    <tags:displayResult id="resultViewSaleDetailClient" property="resultViewSaleDetail" type="key"/>

    <table style="width:100%">
        <tr>
            <td style="width:40%;vertical-align:top">
                <s:if test="saleManagmentForm.transDate!= null">
                    <fieldset class="imFieldset">
                        <legend><s:property escapeJavaScript="true"  value="getText('MSG.SAE.148')"/></legend>
                        <div style="width:100%;overflow:auto">
                            <table class="inputTbl4Col">
                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.123"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.custName" theme="simple" id="custName" cssClass="txtInputFull" readonly="true"/>
                                        <s:hidden name="saleManagmentForm.saleTransCode" id="saleTransCode" />
                                        <s:hidden name="saleManagmentForm.transStatus" id="transStatus" />
                                    </td>
                                    <td class="label"><tags:label key="MSG.SAE.149"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.telNumber" id="telNumber" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.006"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.company" id="company" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.SAE.150"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.tin" id="tin" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.125"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.contractNo" id="contractNo" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.SAE.151"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.isdn" id="isdn" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.099"/></td>
                                    <td colspan="3" class="text">
                                        <s:textfield name="saleManagmentForm.address" id="address" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label" ><tags:label key="MSG.SAE.020"/></td>
                                    <td colspan="3" class="text">
                                        <s:textarea name="saleManagmentForm.note" rows="2" id="note" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.044"/></td>
                                    <td>
<!--                                        <_s:textfield name="saleManagmentForm.amountNotTaxMoney" cssStyle="text-align:right"  id="amountNotTax" theme="simple" cssClass="txtInputFull" readonly="true"/>-->
                                        <s:textfield name="saleManagmentForm.amountNotTaxMoney" cssStyle="text-align:right"  id="amountNotTaxMoney" theme="simple" cssClass="txtInputFull" readonly="true"/>
<!--                                        <_s:property value="saleManagmentForm.amountNotTax"/>-->
                                    </td>
                                    <td class="label"><tags:label key="MSG.SAE.045"/></td>
                                    <td>
<!--                                        <_s:textfield name="saleManagmentForm.amountTaxMoney" id="amountTax" cssStyle="text-align:right"  theme="simple" cssClass="txtInputFull" readonly="true"/>-->
                                        <s:textfield name="saleManagmentForm.amountTaxMoney" id="amountTaxMoney" cssStyle="text-align:right"  theme="simple" cssClass="txtInputFull" readonly="true"/>
<!--                                        <_s:property value="saleManagmentForm.amountTax"/>-->
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.152"/></td>
                                    <td>
<!--                                        <_s:textfield name="saleManagmentForm.amountPromotionMoney" cssStyle="text-align:right"  id="amountPromotion" cssClass="txtInputFull" theme="simple" readonly="true"/>-->
                                        <s:textfield name="saleManagmentForm.amountPromotionMoney" cssStyle="text-align:right"  id="amountPromotionMoney" cssClass="txtInputFull" theme="simple" readonly="true"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.SAE.081"/></td>
                                    <td>                                        
<!--                                        <_s:textfield name="saleManagmentForm.amountDiscountMoney" cssStyle="text-align:right"  id="amountDiscount" cssClass="txtInputFull" theme="simple" readonly="true"/>-->
                                        <s:textfield name="saleManagmentForm.amountDiscountMoney" cssStyle="text-align:right"  id="amountDiscountMoney" cssClass="txtInputFull" theme="simple" readonly="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.145"/></td>
                                    <td colspan="3">
<!--                                        <_s:textfield name="saleManagmentForm.totalMoney" id="total" cssStyle="text-align:right"  cssClass="txtInputFull" theme="simple" readonly="true"/>-->
                                        <s:textfield name="saleManagmentForm.totalMoney" id="totalMoney" cssStyle="text-align:right"  cssClass="txtInputFull" theme="simple" readonly="true"/>
<!--                                        <_s:property value="saleManagmentForm.total"/>-->
                                    </td>
                                </tr>

                                <tr>
                                    <td class="label"><tags:label key="MSG.SAE.153"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.payMethod" id="payMethod" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.SAE.154"/></td>
                                    <td class="text">
                                        <s:textfield name="saleManagmentForm.referenceNo" id="referenceNo" theme="simple" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </fieldset>
                </s:if>

            </td>
            <td style="width:60%;vertical-align:top">
                <s:if test="#request.lstSaleTransDetail != null && #request.lstSaleTransDetail.size() != 0">
                    <fieldset class="imFieldset">
                        <legend><s:property escapeJavaScript="true"  value="getText('MSG.SAE.155')"/></legend>
                        <div style="width:100%;height: 245px; overflow:auto">
                            <%int idx = 1;%>
                            <display:table name="lstSaleTransDetail" id="trans"  class="dataTable" >
                                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" headerClass="tct">
                                    <div align="center">
                                        <s:if test="#attr.trans.levels!=2">
                                            <%=StringEscapeUtils.escapeHtml(idx)%>
                                            <%idx++;%>
                                        </s:if>
                                    </div>
                                </display:column>
                                <display:column escapeXml="true" property="stockModelCode" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.074'))}" sortable="false" headerClass="sortable"/>
                                <display:column escapeXml="true" property="name" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.036'))}" sortable="false" headerClass="sortable"/>
                                
                                <display:column property="price" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.039'))}"  format="{0,number,#,###.00}"  style="text-align:right" sortable="false" headerClass="sortable"/>
                                
                                <display:column property="quantity" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.038'))}"  format="{0,number,#,###}" style="text-align:right"  sortable="false" headerClass="sortable"/>
                                
                                <display:column property="amount" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.145'))}" format="{0,number,#,###.00}" style="text-align:right" sortable="false" headerClass="sortable"/>
                                
                                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.156'))}" sortable="false" headerClass="tct" style="width:70px">
                                    <s:if test="#attr.trans.checkSerial==1">
                                        <div align="center">
                                            <s:url action="saleManagmentAction!viewTransDetailSerial" id="URL" encode="true" escapeAmp="false">
                                                <s:param name="saleTransDetailId" value="#attr.trans.saleTransDetailId"/>
                                            </s:url>
                                            <a href="javascript:void(0)" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URL"/>',800,700)">
                                                ${fn:escapeXml(imDef:imGetText('MSG.SAE.156'))}
                                            </a>
                                        </div>
                                    </s:if>
                                </display:column>
                                <display:footer> <tr> <td colspan="13" style="color:green">${fn:escapeXml(imDef:imGetText('MSG.SAE.147'))}:<s:property escapeJavaScript="true"  value="%{#request.lstSaleTransDetail.size()}" /></td> <tr> </display:footer>
                                </display:table>
                        </div>
                    </fieldset>

                </s:if>

            </td>
        </tr>
       
        <s:hidden name="saleManagmentForm.invoiceId" />
        <s:if test="saleManagmentForm.transDate!= null">
            <tr>
                <td colspan="2" align="center">
                    <!--Khong cho phep huy giao dich da lap hoa don-->
                    <s:hidden id="saleManagmentForm.saleTransType" name="saleManagmentForm.saleTransType"/>
                    <s:hidden id="saleManagmentForm.cancelTrans" name="saleManagmentForm.cancelTrans"/>
                    <s:if test="saleManagmentForm.transStatus !=null && saleManagmentForm.transStatus==2 && saleManagmentForm.cancelTrans==true" >
                        <s:if test="saleManagmentForm.saleTransType == 4 ">
                            <s:hidden name="saleManagmentForm.backGood" value="true" theme="simple" id="saleManagmentForm.backGood"/>
                        </s:if>

                        <tags:submit showLoadingText="true"
                                     formId="saleManagmentForm"
                                     value="MSG.SAE.157"
                                     targets="InvoicePrinterArea"
                                     confirm="true" 
                                     confirmText="MSG.SAE.205"
                                     disabled="${fn:escapeXml(!requestScope.checkDestroySaleTransAnyPay)}"
                                     preAction="saleManagmentAction!cancelTrans.do"/>
                    </s:if>

                </td>
            </tr>
        </s:if>
    </table>
    <sx:div id="InvoicePrinterArea">
        <jsp:include page="printInvoiceResult.jsp"/>
    </sx:div>
</div>

<script>
//    textFieldNF($('amountNotTax'));
//    textFieldNF($('amountTax'));
//    textFieldNF($('amountPromotion'));
//    textFieldNF($('amountDiscount'));
//    textFieldNF($('total'));
</script>


