<%-- 
    Document   : informationInOrder
    Created on : Apr 29, 2009, 11:50:26 PM
    Author     : haidd
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
%>
<%--<fieldset style="width:95%; padding:10px 10px 10px 10px">
    <legend class="transparent">Thông tin lệnh xuất</legend>--%>
<tags:imPanel title="MSG.info.export.cmd">

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

    

    <%--
    <table class="dataTable">
        <tr>
            <th><div align="center">STT</div></th>
            <th><div align="center">Mã mặt hàng</div></th>
            <th><div align="center">Tên mặt hàng</div></th>
            <th><div align="center">Số lượng</div></th>
            <th><div align="center">Đơn vị tính</div></th>
            <th><div align="center">Đơn giá</div></th>
            <th><div align="center">Thành tiền</div></th>
        </tr>
        <s:iterator value="#request.listStockDetails" status="staStockDetails">
            <tr class="<s:if test="#staStockDetails.odd == true ">odd</s:if><s:else>even</s:else>">
                <input type="hidden" name="mapStockModels['<s:property escapeJavaScript="true"  value="stockModelId"/>']" value="<s:property escapeJavaScript="true"  value="stockModelId"/>">
                <input type="hidden" name="mapStockModelQuanity['<s:property escapeJavaScript="true"  value="stockModelId"/>']" value="<s:property escapeJavaScript="true"  value="quantityRes"/>">
                <td style="text-align:center"><s:property escapeJavaScript="true"  value="#staStockDetails.count" /></td>
                <td><s:property escapeJavaScript="true"  value="nameCode" /></td>
                <td><s:property escapeJavaScript="true"  value="name" /></td>
                <td style="text-align:right">
                    <div id="quatity_<s:property escapeJavaScript="true"  value="#staStockDetails.count"/>"><s:property escapeJavaScript="true"  value="quantityRes" />
                    </div>
                </td>
                <td style="text-align:center">
                    <s:property escapeJavaScript="true"  value="unit" />
                </td>
                <input type="hidden" name="priceValues['<s:property escapeJavaScript="true"  value="stockModelId"/>']" id="priceValueId_<s:property escapeJavaScript="true"  value="#staStockDetails.count"/>">
                <td>
                    <s:set name="pstockModelId" value="stockModelId" />
                    <!--s:select name="listPrices['%{#pstockModelId}']" cssClass="txtInputFull"  theme="simple"
                              id="price_%{#staStockDetails.count}" onchange="selectChange()"
                              list="prices" listKey="priceId" listValue="price"/-->
                    <s:select name="listPrices['%{#pstockModelId}']" cssClass="txtInputFull"  theme="simple"
                              id="priceList" onchange="selectChange()"
                              list="prices" listKey="priceId" listValue="price"/>
                </td>
                <td style="text-align:right">
                    <div id ="totalSingleModel" style="display:none">
                        <s:property escapeJavaScript="true"  value="totalSingleModel" />
                    </div>
                    <div id="totalSingleId_<s:property escapeJavaScript="true"  value="#staStockDetails.count"/>"></div>
                </td>
                <input type="hidden" name="mapTotalSingleModel['<s:property escapeJavaScript="true"  value="stockModelId"/>']" id="totalsingle_<s:property escapeJavaScript="true"  value="#staStockDetails.count"/>" >
                <s:if test="#staStockDetails.last == true ">
                    <input type="hidden" name="totalHiddenRecord" id="totalHiddenRecordId" value="<s:property escapeJavaScript="true"  value="#staStockDetails.count" />" />
                </s:if>
            </tr>
        </s:iterator>
        <!--s:hidden name="reasonNameId" id="reasonNameId" /-->
    </table>--%>

    </tags:imPanel>
</fieldset<%-->--%>

    
<s:hidden name="formCode" id="formCodeId" onchange="mapFormCode()"/>
<s:hidden name="userPayMapped" id="userPayMappedId" />
<s:hidden name="paramIdMapped" id="paramIdMapped" />


<script type="text/javascript">
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
