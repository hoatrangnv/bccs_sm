<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : informationInOrderRecover
    Created on : Sep 29, 2009, 6:05:01 PM
    Author     : Vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
            if (request.getAttribute("listStockDetails") == null) {
                request.setAttribute("listStockDetails", request.getSession().getAttribute("listStockDetails"));
            }
            request.setAttribute("contextPath", request.getContextPath());

            String reasonName = (String) request.getAttribute("reasonName");
            String shopExpName = (String) request.getAttribute("shopExpName");
            Object object = request.getAttribute("paymethodeid");
            Long paymethodeid;
            if (object != null) {
                paymethodeid = (Long) object;
            }


%>

<%--<fieldset style="width:95%; padding:10px 10px 10px 10px">--%>
<%--<legend class="transparent">Thông tin phiếu nhập</legend>--%>
<tags:imPanel title="MSG.info.import.note">
    <table class="dataTable">
        <tr>
            <th><tags:label key="MSG.DET.010"/></th>
            <th><tags:label key="MSG.DET.011"/></th>
            <th><tags:label key="MSG.DET.012"/></th>
            <th><tags:label key="MSG.DET.013"/></th>
            <th><tags:label key="MSG.DET.014"/></th>
            <th><tags:label key="MSG.DET.015"/></th>
            <th><tags:label key="MSG.DET.016"/></th>
        </tr>
        <s:if test="#request.listStockDetails != null and #request.listStockDetails.size > 0">
            <s:iterator value="#request.listStockDetails" status="idStockTransD"  >
                <tr class="<s:if test="#idStockTransD.count % 2 == 0">event</s:if><s:else>odd</s:else>">
                    <s:set id="idStockModelId" name="stockModelId"  />
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
                        <%--<s:bean name="com.viettel.im.client.bean.ConvertCurrencyBean">
                            <s:param name="currency" value="amount" />
                            <s:param name="currency" value="totalSingleModel" />
                            <s:property escapeJavaScript="true"  value="startConvertCurrency()" />
                        </s:bean>--%>

                        <input type="hidden" name="priceValues['<s:property escapeJavaScript="true"  value="stockModelId"/>']" id="priceValues_<s:property escapeJavaScript="true"  value="#idStockTransD.count"/>"value="<s:property escapeJavaScript="true"  value="price" />">


                        <input type="hidden" name="mapTotalSingleModel['<s:property escapeJavaScript="true"  value="stockModelId"/>']" id="totalModelId_<s:property escapeJavaScript="true"  value="#idStockTransD.count"/>" value="<s:property escapeJavaScript="true"  value="amount" />">

                        <%--<input type="hidden" name="mapTotalModel['<s:property escapeJavaScript="true"  value="stockModelId"/>']" id="totalModelId_<s:property escapeJavaScript="true"  value="#idStockTransD.count"/>" value="<s:property escapeJavaScript="true"  value="amount" />">--%>

                    </td>
                    <s:if test="#idStockTransD.last == true ">
                    <input type="hidden" name="tTotal" id="tTotalId" value="<s:property escapeJavaScript="true"  value="#idStockTransD.count" />" />
                </s:if>
            </tr>
        </s:iterator>
    </s:if>
</table>

</tags:imPanel>


<s:hidden name="formCode" id="formCodeId" onchange="mapFormCode()"/>
<s:hidden name="userPayMapped" id="userPayMappedId" />
<s:hidden name="paramIdMapped" id="paramIdMapped" />


<script type="text/javascript">    
    var reasonName = '${fn:escapeXml(reasonName)}';
    var shopExpName = '${fn:escapeXml(shopExpName)}';
    var paymethodeid = '${fn:escapeXml(paymethodeid)}';
    document.getElementById("paramId").value = paymethodeid;
    document.getElementById("reasonName").value = reasonName;
    document.getElementById("agentNameImportId").value = shopExpName;    
    
    mapFormCode = new function(){
        //alert($( 'userPayMappedId' ).value + "," + $( 'paramIdMapped' ).value)
        if($( 'formCodeId' ) != null)
            $( 'formCodeIdMapped' ).value = $( 'formCodeId' ).value;
        if($( 'userPayMappedId' ) != null)
            $( 'userPayId' ).value = $( 'userPayMappedId' ).value;
    <%--if($( 'paramIdMapped' ) != null)
        $( 'paramId' ).value = $( 'paramIdMapped' ).value;--%>
            }

            selectChange_1();
    <%--getAmount_1();--%>

    <%--selectNF($('priceList'));--%>

        function formatCurrency(num) {
            num = num.toString().replace(/\$|\,/g,'');
            if(isNaN(num))
                num = "0";
            sign = (num == (num = Math.abs(num)));
            num = Math.floor(num*100+0.50000000001);
            cents = num%100;
            num = Math.floor(num/100).toString();
            if(cents<10)
                cents = "0" + cents;
            for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
                num = num.substring(0,num.length-(4*i+3))+','+
                num.substring(num.length-(4*i+3));
            //return (((sign)?'':'-') + '$' + num + '.' + cents);
            return (((sign)?'':'-') + num );
        }

        //alert(formatCurrency($('totalSingleModel').innerHTML));
    <%--var total = $('totalSingleModel').innerHTML;
    var selectPrice = document.getElementById("listPrices").selectedIndex;
    var price = document.getElementById("listPrices").options[selectPrice].text.replace(",", "");
    document.getElementById("totalSingleId_" + 1).innerHTML = formatCurrency(total);
    document.getElementById("totalsingle_" + 1).value = total;
    document.getElementById("priceValueId_" + 1).value = price;
    document.getElementById("totalId").value=formatCurrency(total);
    document.getElementById("hiddenTotalId").value=total;
    --%>



</script>
