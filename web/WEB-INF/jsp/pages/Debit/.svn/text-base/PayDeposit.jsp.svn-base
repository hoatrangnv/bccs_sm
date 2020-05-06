<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : PayDeposit
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tuannv
--%>

<%--
    Note: thu tiền đặt cọc từ đại lý
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
            if (request.getAttribute("listCommandExports") == null) {
                request.setAttribute("listCommandExports", request.getSession().getAttribute("listCommandExports"));
            }

            if (request.getAttribute("listStockDetails") == null) {
                request.setAttribute("listStockDetails", request.getSession().getAttribute("listStockDetails"));
            }
            /*if (request.getAttribute("flag") != null) {
            String flag = (String) request.getAttribute("flag");
            }*/

%>


<s:form action="PayDepositAction"  method="post" theme="simple" id="payDepositFormId">
<s:token/>

    <tags:imPanel title="MSG.search.info">
        <tags:imPanel title="MSG.find.export.cmd">
            <table class="inputTbl6Col">
                <tr>
                    <td class="label"><tags:label key="MSG.exportCmdId"/></td>
                    <td class="text">
                        <s:textfield name="stockTransCode" id="stockTransCode" readonly ="false" theme="simple"  maxlength="80" cssClass="txtInputFull"/>
                    </td>
                    <td style="width:10%; "> <tags:label key="MSG.shopId"/></td>
                    <td style="width:30%; " class="oddColumn" colspan="3">
                        <tags:imSearch codeVariable="codeShopId" nameVariable="shopName"
                                       codeLabel="MSG.DET.008" nameLabel="MSG.DET.009"
                                       searchClassName="com.viettel.im.database.DAO.PayDepositDAO"
                                       searchMethodName="getListShop"
                                       />
                    </td>

                    <%--<td class="label">Mã đại lý</td>
                    <td class="text">
                        <s:url id="optionsUrlShop"  action="viewAutoCompleterForShop" method="getAutoCompleterShopAction" />
                        <sx:autocompleter name="codeShopId"
                                          id="autoFormId"
                                          href="%{#optionsUrlShop}"
                                          loadOnTextChange="true"
                                          loadMinimumCount="0"
                                          cssClass="txtInput"
                                          valueNotifyTopics="/valueShop"/>
                    </td>
                    <td  class="label">Tên đại lý</td>
                    <td class="text">
                        <s:textfield name="shopName" id="shopNameId" readonly ="true" theme="simple"  maxlength="80" cssClass="txtInput"/>
                    </td>--%>
                </tr>
                <tr>
                    <td  class="label"> <tags:label key="MSG.order.date.from"/></td>
                    <td class="text">
                        <tags:dateChooser property="startDate" styleClass="txtInputFull" id="fromDateB"  />
                    </td>
                    <td  class="label"> <tags:label key="MSG.order.date.to"/></td>
                    <td class="text">
                        <tags:dateChooser property="endDate" styleClass="txtInputFull" id="toDateB" />
                    </td>
                    <td class="label"> <tags:label key="MSG.receive.bill.status"/></td>
                    <td class="text">
                        <tags:imSelect name="depositStatus" id="depositStatusId"
                                       cssClass="txtInput" disabled="false"
                                       list="0:MSG.GOD.190, 1: MSG.GOD.191 "
                                       headerKey="" headerValue="MSG.DET.007"/>

                        <%--<s:select name="depositStatus" id="depositStatusId"
                                  cssClass="txtInput" theme="simple"
                                  list="#{'0':'Chưa lập phiếu','1':'Đã lập phiếu'}"
                                  headerKey="" headerValue="--Chọn trạng thái--"/>--%>
                    </td>
                </tr>
                <tr>
                    <td colspan="6">
                        <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
                    </td>
                </tr>
            </table>
        </tags:imPanel>
        <br />
        <div align="center">
            <sx:submit parseContent="true" executeScripts="true"
                       cssStyle="width:80px;"
                       formId="payDepositFormId" loadingText="Processing..."
                       targets="bodyContent"
                       key="MSG.find"  beforeNotifyTopics="payDepositFormId/searchCommandExport"/>

            <%--<tags:submit targets="bodyContent" formId="payDepositFormId"
                         cssStyle="width:80px;" value="MSG.find"
                         preAction="PayDepositAction!searchCommandExport.do"
                         />--%>
        </div>
        <%-- Danh mục lệnh xuất --%>
        <div id="listCommandExports">
            <s:if test="#request.listCommandExports != null && #request.listCommandExports.size() > 0">
                <jsp:include page="listPayDeposit.jsp"/>
            </s:if>
        </div>
        <br>
        <div id = "ReceiptBillsInfo" style="display:none">
            <sx:div id="listStockDetails">
                <s:if test="#request.listStockDetails != null && #request.listStockDetails.size() > 0">
                    <jsp:include page="informationInOrder.jsp" />
                </s:if>
            </sx:div>
            <br/>
            <div id = "ReceiptBillsInfoChild">
                <jsp:include page="receiptBillsInfo.jsp" />
            </div>
        </div>
        <script type="text/javascript">
            var flag = '${fn:escapeXml(flag)}';
            if(flag == "1"){
                $( 'ReceiptBillsInfo' ).style.display ="block";
            }
            else{
                $( 'ReceiptBillsInfo' ).style.display ="none";
            }
            
        var _myWidget2 = $('stockTransCode');
        if (_myWidget2 != null)
            _myWidget2.focus();
    

        setDataTicket = function(a,id,stockTransId,depositStatus) {

            $( 'displayResultMsgClient' ).innerHTML = "";
            $( 'displayResultMsgClientB' ).innerHTML = "";

            $( 'ReceiptBillsInfo' ).style.display ="block";
            $( 'bttCancelInformation' ).style.display ="none";
            $( 'bttSaveInformation' ).style.display ="none";
            $( 'btnPrintBill' ).disabled = true;

            var tr = a.parentNode.parentNode;
            var agentNameImport = tr.getElementsByTagName( 'td' )[4].getElementsByTagName( 'span' )[0].innerHTML;
            document.getElementById("agentNameImportId").value= agentNameImport;
            document.getElementById("agentId").value = document.getElementById("tempNameShopImportId_" + id).value;
            document.getElementById("reasonName").value = document.getElementById("tempReasonNameId").value;
            document.getElementById("stockTransIdHTML").value = stockTransId;
            if(depositStatus == "1"){
                $('formCodeIdMapped').readOnly = true;
                $('userPayId').readOnly = true;
                if($('priceList') != null)
                    $('priceList').disabled = true;
                $('paramId').disabled = true;

                $( 'btnPrintBill' ).disabled = false;
                $( 'bttCancelInformation' ).style.display ="block";
            <%--$( 'bttSaveInformation' ).style.display ="none";--%>


            <%--dojo.html.show("bttCancelInformation");
            dojo.html.hide("bttSaveInformation");--%>
                }else{
                    $('formCodeIdMapped').readOnly = false;
                    $('userPayId').readOnly = false;
                    if($('priceList') != null)
                        $('priceList').disabled = false;
                    $('paramId').disabled = false;
                    if($('userPayMappedId') != null)
                        $('userPayMappedId').value = "";
                    if($('paramIdMapped') != null)
                        $('paramIdMapped').value = "";

            <%--$( 'bttCancelInformation' ).style.display ="none";--%>
                    $( 'bttSaveInformation' ).style.display ="block";

            <%--dojo.html.show("bttSaveInformation");
            dojo.html.hide("bttCancelInformation");--%>
                }

            }



            dojo.event.topic.subscribe("/valueShop", function(value, key, text, widget){
                document.getElementById("shopNameId").value = key;
            });

            dojo.event.topic.subscribe("payDepositFormId/searchCommandExport", function(event, widget){
                if($('stockTransCode').value.trim() != "" && isHtmlTagFormat($('stockTransCode').value.trim())){
                    $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.020"/>';
                    //$( 'displayResultMsgClient' ).innerHTML = "Không nên nhập thẻ html vào mã lệnh xuất";
                    $('stockTransCode').select();
                    event.cancel = true;
                    return;
                }
            
            <%--var _myWidget = dojo.widget.byId("autoFormId");
            var textShopCode = _myWidget.textInputNode.value.trim();--%>

                var textShopCode = $('codeShopId').value;
                if(textShopCode != "" && isHtmlTagFormat(textShopCode)){
                    $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.023"/>';
                    //$( 'displayResultMsgClient' ).innerHTML = "Không nên nhập thẻ html vào mã đại lý";
            <%--_myWidget.textInputNode.select();
            event.cancel = true;--%>
                    return;
                }
                var dateExported= dojo.widget.byId("fromDateB");
                if(dateExported.domNode.childNodes[1].value.trim() != "" &&
                    !isDateFormat(dateExported.domNode.childNodes[1].value)){
                    $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.004"/>';
                    //$( 'displayResultMsgClient' ).innerHTML='Ngày nộp từ không hợp lệ';
                    dateExported.domNode.childNodes[1].focus();
                    event.cancel = true;
                    return;
                }
                var dateExported1 = dojo.widget.byId("toDateB");
                if(dateExported1.domNode.childNodes[1].value.trim() != "" &&
                    !isDateFormat(dateExported1.domNode.childNodes[1].value)){
                    $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.005"/>';
                    //$( 'displayResultMsgClient' ).innerHTML='Ngày nộp đến không hợp lệ';
                    dateExported1.domNode.childNodes[1].focus();
                    event.cancel = true;
                    return;
                }
                // TuTM1 04/03/2012 Fix ATTT CSRF
                widget.href = "PayDepositAction!searchCommandExport.do" + "?" + token.getTokenParamString();
            });

            //payDepositFormId/cancelInformationOrderAction
            dojo.event.topic.subscribe("payDepositFormId/cancelInformationOrderAction", function(event, widget){

                if (!confirm(getUnicodeMsg('<s:text name="C.200001"/>'))){
                    event.cancel = true;
                    return;
                }

                widget.href = "saveInformationOrderAction!cancelInformationOrderAction.do" + "?" + token.getTokenParamString();
            });

            checkBeforeCancel = function(){
                var receiptNoFormat = $( 'formCodeIdMapped' ).value;
                if (receiptNoFormat == null){
                    $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.021"/>';
                    //$( 'displayResultMsgClientB' ).innerHTML = "Bạn chưa chọn phiếu thu";
                    $( 'formCodeIdMapped' ).focus();
                    return false;
                }
                return true;
            }

            checkBeforeSave = function(){
                var receiptNoFormat = $( 'formCodeIdMapped' ).value;
                if (receiptNoFormat == null){
                    $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.022"/>';
                    //$( 'displayResultMsgClientB' ).innerHTML = "Bạn chưa nhập mã phiếu thu";
                    $( 'formCodeIdMapped' ).focus();
                    return false;
                }
                receiptNoFormat = receiptNoFormat.trim();
                if(receiptNoFormat == ""){
                    $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.022"/>';
                    //$( 'displayResultMsgClientB' ).innerHTML = "Bạn chưa nhập mã phiếu thu";
                    $( 'formCodeIdMapped' ).focus();
                    return false;
                }
                if(isHtmlTagFormat(receiptNoFormat)){
                    $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.017"/>';
                    //$( 'displayResultMsgClientB' ).innerHTML = "Không nên nhập thẻ html vào mã phiếu thu";
                    $( 'formCodeIdMapped' ).select();
                    return false;
                }
                var userPay = $( 'userPayId' ).value;
                if (userPay == null){
                    $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.024"/>';
                    //$( 'displayResultMsgClientB' ).innerHTML = "Bạn chưa nhập người nộp";
                    $( 'userPayId' ).focus();
                    return false;
                }
                userPay = userPay.trim();
                if(userPay == ""){
                    $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.024"/>';
                    //$( 'displayResultMsgClientB' ).innerHTML = "Bạn chưa nhập người nộp";
                    $( 'userPayId' ).focus();
                    return false;
                }
                if(isHtmlTagFormat(userPay)){
                    $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.025"/>';
                    //$( 'displayResultMsgClientB' ).innerHTML = "Không nên nhập thẻ html vào người nộp";
                    $( 'userPayId' ).select();
                    return false;
                }
                var paramId = $( 'paramId' ).value;
                if(paramId == ""){
                    $('displayResultMsgClientB').innerHTML= '<s:text name="ERR.DET.026"/>';
                    //$( 'displayResultMsgClientB' ).innerHTML = "Bạn chưa chọn hình thức thanh toán";
                    $( 'paramId' ).focus();
                    return false;
                }

                return true;
            }
         
            selectChange = function (){
                $( 'totalSingleModel' ).style.display = "none";
                var totalHiddenRecord = document.getElementById("totalHiddenRecordId").value;
                var total = 0 ,totalSingle;
                for(i = 1 ; i <= totalHiddenRecord;i++){
                    var quatity = document.getElementById("quatity_" + i).innerHTML;
                    var selectPrice = document.getElementById("priceList").selectedIndex;
                    var price = document.getElementById("priceList").options[selectPrice].text;
                    price = price.replace(",", "");
                    totalSingle = price * quatity;
                    total = total + totalSingle;
                    document.getElementById("totalSingleId_" + i).innerHTML =formatCurrency(totalSingle);
                    document.getElementById("totalsingle_" + i).value = totalSingle;
                    document.getElementById("priceValueId_" + i).value = price;
                }
                document.getElementById("totalId").value=formatCurrency(total);
                document.getElementById("hiddenTotalId").value=total;

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

            //Tinh tong tien cac mat hang trong giao dich
            getAmount_1 =   function (){
                var totalRecord = document.getElementById("tTotalId").value;
                var total = 0 ,totalSingle;
                for(i = 1 ; i <= totalRecord;i++){
                    var quatity = document.getElementById("quatity_" + i).innerHTML.trim();
                    var selectPrice = document.getElementById("price_" + i).selectedIndex;
                    var price = document.getElementById("price_" + i).options[selectPrice].text;
                    totalSingle = price * quatity;
                    total = total + totalSingle;
                }

                document.getElementById("totalId").value=formatCurrency(total);
                document.getElementById("hiddenTotalId").value=total;
            }

            //Khi chon lai don gia, tinh lai thanh tien cua mat hang va tong tien cua giao dich
            selectChange_1 =   function (){
                var totalRecord = document.getElementById("tTotalId").value;
                var total = 0 ,totalSingle;
                for(i = 1 ; i <= totalRecord;i++){
                    var quatity = document.getElementById("quatity_" + i).innerHTML.trim();
                    var selectPrice = document.getElementById("price_" + i).selectedIndex;
                    var price = document.getElementById("price_" + i).options[selectPrice].text;
                    totalSingle = price * quatity;
                    total = total + totalSingle;
                    document.getElementById("totalPriceId_" + i).innerHTML =formatCurrency(totalSingle);

                    document.getElementById("priceValues_" + i).value = price;
                
                    document.getElementById("totalModelId_" + i).value = totalSingle;
                }
            
                document.getElementById("totalId").value=formatCurrency(total);
                document.getElementById("hiddenTotalId").value=total;

            <%--document.getElementById("totalTax").innerHTML=formatCurrency(total);
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
            document.getElementById("discountId").value =replaceAll(totalText,",","");--%>
                //alert("value hidden : " + document.getElementById("idTotalModels").value);
            }

        </script>
    </tags:imPanel>
</s:form>


