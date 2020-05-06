<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document    : saleFromExpCommand.jsp
    Created on  :
    Author      :
    Desc        : ban hang cho dai ly tu lenh xuat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<tags:imPanel title="MSG.SAE.083">
    <s:form action="saleFromExpCommandAction" theme="simple" method="post" id="saleFromExpCommandActionId">
<s:token/>

        <!-- phan nhap thong tin tim kiem lenh xuat kho de tao giao dich -->
        <fieldset class="imFieldset">
            <legend>
                <s:property escapeJavaScript="true"  value="getText('MSG.SAE.084')"/>
            </legend>
            <s:hidden name="idShop"/>

            <tags:displayResult id="displayResultMsgSearch" property="returnMsgSearch" type="key" />

            <table class="inputTbl8Col">
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.085" required="true"/></td>
                    <td class="text">
                        <tags:imSearch codeVariable="shopCode" nameVariable="shopName"
                                       codeLabel="MSG.SAE.085" nameLabel="MSG.SAE.086"
                                       searchClassName="com.viettel.im.database.DAO.SaleDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getNameShop"/>
                    </td>                
                    <!--                    <td class="label"><_tags:label key="MSG.SAE.086"/></td>-->
                    <td class="label"><tags:label key="MSG.SAE.126"/></td>
                    <td class="text">
                        <tags:dateChooser property="startDate" styleClass="txtInputFull" />
                    </td>
                    <!--                    <td class="label"><_tags:label key="MSG.SAE.087"/></td>-->
                    <td class="label"><tags:label key="MSG.SAE.127"/></td>
                    <td class="text">
                        <tags:dateChooser property="endDate" styleClass="txtInputFull" />
                    </td>
                    <td class="label" align="left">
                        <tags:submit cssStyle="width:100px;"
                                     formId="saleFromExpCommandActionId" 
                                     showLoadingText="true"
                                     targets="bodyContent"
                                     validateFunction="validateBeforeSearch()"
                                     value="MSG.SAE.088"
                                     preAction="saleFromExpCommandAction!searchSaleAction.do"/>
                    </td>
                </tr>

            </table>

            <div>
                <sx:div id="divDisplayInfo">
                    <table class="dataTable">
                        <tr>
                            <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}</th>
                            <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.089'))}</th>
                            <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.090'))}</th>
                            <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.091'))}</th>
                            <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.092'))}</th>
                            <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.093'))}</th>
                            <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.094'))}</th>
                            <th>${fn:escapeXml(imDef:imGetText('MSG.SAE.095'))}</th>
                        </tr>
                        <s:if test="listStockTrans != null and listStockTrans.size > 0">
                            <s:iterator value="listStockTrans" status="idStockTrans">
                                <tr class="<s:if test="#idStockTrans.count % 2 == 0">even</s:if><s:else>odd</s:else>">
                                    <td align="center"><s:property escapeJavaScript="true"  value="#idStockTrans.count" /></td>
                                    <td><s:property escapeJavaScript="true"  value="actionCode" /></td>
                                    <td><s:date name="createDatetime" format="dd/MM/yyyy" /> </td>
                                    <td><s:property escapeJavaScript="true"  value="userAction" /></td>
                                    <td><s:property escapeJavaScript="true"  value="nameShopExport" /></td>
                                    <td><s:property escapeJavaScript="true"  value="nameShopImport" /></td>
                                    <td><s:if test="payStatus == 0">
                                            <s:property escapeJavaScript="true"  value="getText('MSG.SAE.096')"/>
                                        </s:if><s:else>
                                            <s:property escapeJavaScript="true"  value="getText('MSG.SAE.097')"/>
                                        </s:else></td>
                                    <td align="center">
                                        <s:url action="viewFromExpCommandAction!viewStockTransDetailAction.do" var="viewStockDetail">
                                            <s:param name="stockTransId" value="stockTransId" />
                                            <s:param name="shopIdImport" value="shopIdImport" />
                                        </s:url>
                                        <sx:a targets="viewStockDetailsId" href="%{viewStockDetail}" 
                                              onclick="setStockTransId(%{stockTransId},%{shopIdImport})"
                                              cssClass="cursorHand" executeScripts="true" parseContent="true">
                                            <s:property escapeJavaScript="true"  value="getText('MSG.select')"/>
                                        </sx:a>
                                        <input type="hidden" name="sTransId" id="stockTransIdHTML" />
                                        <input type="hidden" name="sShopId" id="shopIdImportHTML" />
                                    </td>
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
                                <td></td>
                            </tr>
                        </s:else>
                    </table>
                </sx:div>
            </div>
        </fieldset>

        <div>
            <jsp:include page="saleFromExpCommandDetail.jsp" />
        </div>

    </s:form>

</tags:imPanel>




<script language="javascript">
    validateBeforeSearch = function(){
        $('displayResultMsgSearch').innerHTML=""
        if ($('shopCode').value.trim() == ""){
            $('displayResultMsgSearch').innerHTML="Bạn chưa chọn mã đại lý!"
            $('shopCode').focus();
            return false;
        }
        return true;
    }

    setStockTransId = function(id,id2){
        document.getElementById("stockTransIdHTML").value = id;
        document.getElementById("shopIdImportHTML").value = id2;
    }

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

    selectChange =   function (){
        var totalRecord = document.getElementById("tTotalId").value;
        var total = 0 ,totalSingle;
        for(i = 1 ; i <= totalRecord;i++){
            var quatity = jQuery.trim(document.getElementById("quatity_" + i).innerHTML);
            var selectPrice = document.getElementById("price_" + i).selectedIndex;
            var price = document.getElementById("price_" + i).options[selectPrice].text;
            totalSingle = price * quatity;
            total = total + totalSingle;
            document.getElementById("totalPriceId_" + i).innerHTML =formatCurrency(totalSingle);
            document.getElementById("totalModelId_" + i).value = totalSingle;
        }
        document.getElementById("totalTax").innerHTML=formatCurrency(total);
        var totalHiddenDiscount = jQuery('#hiddenTotalRecordDiscountId').val();
        var totalDiscountRateId = 0, totalDiscountAmountId = 0;
        if(totalHiddenDiscount != undefined ){
            for(iCount = 1; iCount <= totalHiddenDiscount;iCount++){
                var discountRateId = jQuery.trim(replaceAll(jQuery('#discountRateId_' + iCount).text(),"%",""));
                if( discountRateId != undefined){
                    totalDiscountRateId = totalDiscountRateId + parseInt(discountRateId) ;
                }
                var discountId = jQuery.trim(jQuery('#hiddenDiscountAmountId_' + iCount).val());
                if(discountId != undefined){
                    totalDiscountAmountId = totalDiscountAmountId + discountId;
                }
            }
        }


        var totalText = jQuery.trim(document.getElementById("totalTextId").innerHTML) ;
        if(totalDiscountRateId > 0){
            totalDiscountRateId = total *(5/100);
        }
        //var totalModel = total -  replaceAll(totalText,",","");
        var totalMoneyDiscount =  totalDiscountRateId + parseFloat(totalDiscountAmountId);
        document.getElementById("totalTextId").innerHTML =formatCurrency(totalMoneyDiscount);
        var totalModel = total -  totalMoneyDiscount;

        document.getElementById("totalModelId").innerHTML ="<b>" + formatCurrency(totalModel) + "</b>"  ;
        document.getElementById("idTotalModels").value = totalModel;
        document.getElementById("discountId").value =replaceAll(totalText,",","");
        //alert("value hidden : " + document.getElementById("idTotalModels").value);
    }

    replaceAll = function (Source,stringToFind,stringToReplace){
        var temp = Source;
        var index = temp.indexOf(stringToFind);
        while(index != -1){
            temp = temp.replace(stringToFind,stringToReplace);
            index = temp.indexOf(stringToFind);
        }
        return temp;
    }


</script>

