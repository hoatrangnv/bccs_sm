<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : saleFromExpCommandDetail.jsp
    Created on : Apr 25, 2009, 9:24:23 PM
    Author     : haidd
    Desc       :
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<sx:div id="viewStockDetailsId" >    
    <!-- phan nhap thong tin chi tiet ve giao dich -->
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.SAE.002'))}</legend>
        <table class="inputTbl6Col">
            <tr>
                <td class="label"><tags:label key="MSG.SAE.098"/></td>
                <td class="text">
                    <s:textfield name="userId" id="userId" maxlength="100" cssClass="txtInputFull" theme="simple"/>
                </td>
                <td class="label"><tags:label key="MSG.SAE.007"/></td>
                <td class="text">
                    <s:textfield name="taxId" id="taxId" maxlength="15" cssClass="txtInputFull" theme="simple"/>
                </td>
                <td class="label"><tags:label key="MSG.SAE.099"/></td>
                <td class="text">
                    <s:textfield name="addressId" id="addressId" maxlength="100" cssClass="txtInputFull" theme="simple"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.sale.date" required="true"/></td>
                <td class="text">
                    <tags:dateChooser property="sellDate" styleClass="txtInputFull" readOnly="true"/>
                </td>
                <td class="label"><tags:label key="MSG.sale.reason" required="true"/></td>
                <td  class="text">
                    <tags:imSelect name="reasonsId" id="reasonsId"
                                   theme="simple"
                                   cssClass="txtInputFull"
                                   list="listReasons"
                                   listKey="reasonId" listValue="reasonName"
                                   headerKey="" headerValue="MSG.SAE.013"/>
                </td>
                <td class="label"><tags:label key="MSG.payMethod" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="paramesId" id="paramesId"
                                   theme="simple"
                                   cssClass="txtInputFull"
                                   list="1:MSG.SAE.071, 2:MSG.SAE.072"
                                   value="1"
                                   headerKey="" headerValue="MSG.SAE.014"/>
                </td>
            </tr>
        </table>
    </fieldset>


    <tags:displayResult id="displayResultMsg" property="returnMsg" type="key" />

    <!-- phan hien thi danh sach chi tiet cac mat hang trong giao dich -->
    <table style="width:100%;">
        <tr>
            <td style="width:60%">
                <fieldset class="imFieldset">
                    <legend>${fn:escapeXml(imDef:imGetText('MSG.SAE.100'))}</legend>
                    <div style="width:100%; height:300px">
                        <table class="dataTable">
                            <tr>
                                <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}</th>
                                <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.074'))}</th>
                                <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.036'))}</th>
                                <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.037'))}</th>
                                <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.038'))} (1)</th>
                                <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.039'))} (2)</th>
                                <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.040'))} (1 * 2)</th>
                            </tr>
                            <s:if test="listTransDetails != null and listTransDetails.size > 0">
                                <s:iterator value="listTransDetails" status="idStockTransD"  >
                                    <tr class="<s:if test="#idStockTransD.count % 2 == 0"> event </s:if><s:else> odd </s:else>">
                                        
                                        <s:set id="idStockModelId" name="stockModelId"/>
                                        <td align="center"><s:property escapeJavaScript="true"  value="#idStockTransD.count" />
                                            <input type="hidden" name="mapStockModels['<s:property escapeJavaScript="true"  value="stockModelId"/>']" value="<s:property escapeJavaScript="true"  value="stockModelId"/>">
                                        </td>
                                        <td><s:property escapeJavaScript="true"  value="nameCode"/></td>
                                        <td><s:property escapeJavaScript="true"  value="name"/></td>
                                        <td><s:property escapeJavaScript="true"  value="unit"/></td>
                                        <td align="right" style="padding-right:20px">
                                            <input type="hidden" name="mapStockModelQuanity['<s:property escapeJavaScript="true"  value="stockModelId"/>']" value="<s:property escapeJavaScript="true"  value="quantityRes" />" />
                                            <s:iterator value="discounts" status="stadDiscounts">
                                                <input type="hidden" name="mapStockModelDiscount['<s:property escapeJavaScript="true"  value="stockModelId"/>']" value="<s:property escapeJavaScript="true"  />" />
                                            </s:iterator>
                                            <div id="quatity_<s:property escapeJavaScript="true"  value="#idStockTransD.count"/>"><s:property escapeJavaScript="true"  value="quantityRes"/></div>
                                        </td>
                                        <td style="padding-right:10px; padding-left:10px">
                                            <s:set name="vstockModelId" value="stockModelId" />
                                            <s:select name="listPrices['%{#vstockModelId}']" id="price_%{#idStockTransD.count}"  cssClass="txtInputFull"  theme="simple"
                                                      onchange="selectChange()"
                                                      list="prices" listKey="priceId" listValue="price"  />
                                        </td>
                                        <td align="right"><div id="totalPriceId_<s:property escapeJavaScript="true"  value="#idStockTransD.count"/>"></div>
                                            <s:bean name="com.viettel.im.client.bean.ConvertCurrencyBean">
                                                <s:param name="currency" value="amount" />
                                                <s:property escapeJavaScript="true"  value="startConvertCurrency()" />
                                            </s:bean>
                                            <input type="hidden" name="mapTotalModel['<s:property escapeJavaScript="true"  value="stockModelId"/>']" id="totalModelId_<s:property escapeJavaScript="true"  value="#idStockTransD.count"/>" value="<s:property escapeJavaScript="true"  value="amount" />">
                                        </td>

                                        <s:if test="#idStockTransD.last == true ">
                                        <input type="hidden" name="tTotal" id="tTotalId" value="<s:property escapeJavaScript="true"  value="#idStockTransD.count" />" />
                                    </s:if>
                                    </tr>
                                </s:iterator>
                            </s:if>
                            <s:else>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            </s:else>

                            <s:if test="listTransDetails != null and listTransDetails.size > 0 ">
                                <tr class="odd">
                                    <td></td>
                                    <td colspan="5"></td>
                                    <td align="right"><hr style="width: 80%; margin-right: 0px;"></td>
                                </tr>
                                <tr class="even">
                                    <td></td>
                                    <td colspan="5">${fn:escapeXml(imDef:imGetText('MSG.SAE.044'))} (3)</td>
                                    <td align="right" id="totalTax">
                                        <s:bean name="com.viettel.im.client.bean.ConvertCurrencyBean">
                                            <s:param name="currency" value="totalPrice" />
                                            <s:property escapeJavaScript="true"  value="startConvertCurrency()" />
                                        </s:bean>
                                        <input type="hidden" name="totalPrice" id="totalPrice" value="<s:property escapeJavaScript="true"  value="totalPrice" />"/>
                                </tr>
                                <tr class="even">
                                    <td></td>
                                    <td colspan="5">
                                        ${fn:escapeXml(imDef:imGetText('MSG.SAE.045'))} (4)
                                    </td>
                                    <td align="right" id="totalTax">
                                        <s:bean name="com.viettel.im.client.bean.ConvertCurrencyBean">
                                            <s:param name="currency" value="totalTaxMoney" />
                                            <s:property escapeJavaScript="true"  value="startConvertCurrency()" />
                                        </s:bean>
                                        <input type="hidden" name="totalTaxMoney" id="totalTaxMoney" value="<s:property escapeJavaScript="true"  value="totalTaxMoney" />"/>
                                </tr>
                                <tr class="odd">
                                    <td></td>
                                    <td colspan="5">
                                        ${fn:escapeXml(imDef:imGetText('MSG.SAE.101'))}(5)
                                    </td>
                                    <td align="right" id="totalTextId">
                                        <s:bean name="com.viettel.im.client.bean.ConvertCurrencyBean">
                                            <s:param name="currency" value="discountNotTax" />
                                            <s:property escapeJavaScript="true"  value="startConvertCurrency()" />
                                        </s:bean>
<!--                                        <input type="hidden" name="discountNotTax" id="discountNotTax" value="<s:property escapeJavaScript="true"  value="discountNotTax" />"/>-->
                                    </td>
                                </tr>
                                <tr class="even">
                                    <td></td>
                                    <td colspan="5"><b>${fn:escapeXml(imDef:imGetText('MSG.SAE.046'))} (3 + 4)</b></td>
                                    <td align="right" id="totalModelId">
                                        <s:bean name="com.viettel.im.client.bean.ConvertCurrencyBean">
                                            <s:param name="currency" value="totalPriceAfterRate" />
                                            <s:property escapeJavaScript="true"  value="startConvertCurrency()" />
                                        </s:bean>
                                        <input type="hidden" name="totalPriceAfterRate" id="totalPriceAfterRate" value="<s:property escapeJavaScript="true"  value="totalPriceAfterRate" />" />
                                </tr>
                            </s:if>
                        </table>
                    </div>
                </fieldset>
            </td>
            <td style="width:2px"></td>
            <td style="vertical-align:top">
                <fieldset class="imFieldset">
                    <legend>${fn:escapeXml(imDef:imGetText('MSG.SAE.102'))}</legend>
                    <div style="width:100%; height:300px">
                        <table class="dataTable">
                            <tr>
                                <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}</th>
                                <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.103'))}</th>
                                <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.104'))}</th>
                                <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.105'))}</th>
                            </tr>
                            <s:if test="listTransDetails != null and listTransDetails.size > 0 ">
                                <s:if test="listModelMaps != null and listModelMaps.size > 0">
                                    <s:iterator value="listModelMaps" status="idModel" id="idModelMaps" >
                                        <tr class="<s:if test="#idModel.count % 2 == 0">event</s:if><s:else>odd</s:else>">
                                            <td align="center"><s:property escapeJavaScript="true"  value="#idModel.count" /></td>
                                            <td>
                                                <s:property escapeJavaScript="true"  value="name" />
                                            </td>
                                            <!--                                            Bo sung thay doi cua VuNT7
                                                                                        Tai sao VuNT7 bo sung xong lai comment lai nhi?-->
                                            <%--<td align="right" style="padding-right:20px" >
                                              <div id="discountRateId_<s:property escapeJavaScript="true"  value="#idModel.count" />"><s:property escapeJavaScript="true"  value="discountRate" />%</div>
                                          </td>--%>

                                            <td align="right" style="padding-right:20px" >
                                                <div id="discountRateId_<s:property escapeJavaScript="true"  value="#idModel.count" />">
                                                    <s:bean name="com.viettel.im.client.bean.ConvertCurrencyBean">
                                                        <s:param name="currency" value="discountRate" />
                                                        <s:property escapeJavaScript="true"  value="startConvertCurrency()" />
                                                    </s:bean>
                                                    <!--                                                    <.s:property value="discountRate" />-->
                                                    %</div>
                                            </td>
                                            <td align="right" style="padding-right:20px">
                                                <s:bean name="com.viettel.im.client.bean.ConvertCurrencyBean">
                                                    <s:param name="currency" value="discountAmount" />
                                                    <s:property escapeJavaScript="true"  value="startConvertCurrency()" />
                                                </s:bean>                                                
                                            </td>
                                            <s:if test="#idModel.last == true">
                                            <input type="hidden" name="hiddenTotalRecordDiscount" id="hiddenTotalRecordDiscountId" value="<s:property escapeJavaScript="true"  value="#idModel.count" />" />
                                        </s:if>
                                        </tr>
                                    </s:iterator>
                                    <tr class="odd">
                                        <td></td>
                                        <td colspan="2"></td>
                                        <td align="right" colspan="3"><hr style="width: 80%; margin-right: 0px;"></td>
                                    </tr>
                                    <tr class="even">
                                        <td></td>
                                        <td colspan="1">
                                            <s:property escapeJavaScript="true"  value="getText('MSG.SAE.105')"/>
                                        </td>
                                        <td align="right" style="padding-right:20px"  colspan="2">
                                            <s:bean name="com.viettel.im.client.bean.ConvertCurrencyBean">
                                                <s:param name="currency" value="discountNotTax" />
                                                <s:property escapeJavaScript="true"  value="startConvertCurrency()" />
                                            </s:bean>
                                        </td>
                                    </tr>
                                </s:if>
                            </s:if>
                        </table>
                    </div>
                </fieldset>
            </td>
        </tr>
    </table>

    <!-- phan nut tac vu -->
    <div align="center">
        <tags:submit confirm="true" confirmText="MSG.SAE.204"
                     cssStyle="width:100px;"
                     formId="saleFromExpCommandActionId"
                     showLoadingText="true"
                     targets="bodyContent"
                     validateFunction="validate()"
                     value="MSG.SAE.064"
                     preAction="saveFromExpCommandAction!saveCommand.do"/>
    </div>

</sx:div>

<script language="javascript">
    validate = function(){
        var custName = document.getElementById("userId");
        var company = document.getElementById("addressId");
        var tin = document.getElementById("taxId");      
        
        if (trim(custName.value).length >100){
            $('displayResultMsg').innerHTML='<s:text name="ERR.SAE.002"/>';
            custName.focus();
            return false;
        }
        
        if(trim(company.value).length >100){
            $('displayResultMsg').innerHTML='<s:text name="ERR.SAE.012"/>';
            company.focus();
            return false;
        }
        
        if(trim(tin.value).length >100){
            $('displayResultMsg').innerHTML='<s:text name="ERR.SAE.007"/>';
            tin.focus();
            return false;
        }
        
        
        var reasonId = document.getElementById("reasonsId");
        var payMethodId = document.getElementById("paramesId");
        if(trim(reasonId.value).length == 0){
            $('displayResultMsg').innerHTML='<s:text name="ERR.SAE.060"/>';
            reasonId.focus();
            return false;
        }
        if(trim(payMethodId.value).length == 0){
            $('displayResultMsg').innerHTML='<s:text name="ERR.SAE.061"/>';
            payMethodId.focus();
            return false;
        }
        
        return true;
    }
    
</script>
