<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : agentPayDepositInfo
    Created on : Apr 8, 2009, 9:23:03 AM
--%>

<%--Chi tra tien dat coc cho dai ly--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<script language="javascript">
    divNF = function(divObject) {
        if(divObject != null) {
            divObject.innerHTML = addSeparatorsNF(divObject.innerHTML, '.', '.', ',');
        }
    }
</script>

<sx:div id="addDepositStockModelId">
    <s:form action="addDepositDetailAction" id="addDepositDetailActionFromId" method="post" theme="simple">
<s:token/>

        <%--<fieldset style="width:100%;">--%>
        <%--<legend class="transparent">Chi trả đặt cọc cho đại lý</legend>--%>
        <div style="width:100%; height:313px; overflow:auto; border-width:1px; border-style:solid; border-color:silver">
            <tags:imPanel title="MSG.deposit.for.agent">
                <table class="inputTbl2Col">
                    <!--tr><td colspan="2"><font color="red"><s:property escapeJavaScript="true"  value="message" /></font></td></tr-->
                    <tr>
                        <td colspan="2">
                            <tags:displayResult id="displayResultMsgClient2" property="returnMsg2" propertyValue="returnMsgValue" type="key" />
                        </td>
                    </tr>
                    <tr>
                        <td class="label"><tags:label key="MSG.generates.goods"/></td>
                        <td class="text">
                            <s:textfield name="tempStockModel" id="tempidStockModel" cssClass="txtInput" theme="simple" readonly="true"/>
                            <s:hidden  name="stockModelId" id="idStockModel" theme="simple" />
                            <s:hidden  name="stockModelName" id="stockNameId" theme="simple" />
                        </td>
                    </tr>
                    <tr>
                        <td class="label"><tags:label key="MSG.price.money"/></td>
                        <td class="text">
                            <div id="console">
                                <s:textfield name="tempPrice" id="tempIdPrice" cssClass="txtInput" theme="simple" readonly="true"/>
                            </div>
                            <s:hidden  name="price" id="tempPriceId" theme="simple" />
                            <s:hidden  name="tempPriceName" id="tempPriceNameId" theme="simple" />
                        </td>
                    </tr>
                    <tr>

                        <td class="label"><tags:label key="MSG.quantity"/></td>
                        <td class="text">
                            <s:textfield name="quantity" id="quantity" size="25" maxlength="20" cssClass="txtInput" theme="simple"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <s:if test="checkEdit == true" >
                                <sx:submit cssStyle="width:100px;" loadingText="Processing..."
                                           formId="addDepositDetailActionFromId" executeScripts="true"
                                           showLoadingText="true" targets="addDepositStockModelId"
                                           beforeNotifyTopics="AddDepositCreateReceiptFormId/editDepositExport"
                                           key="MSG.update"  />
                                <%--<tags:submit targets="bodyContent" formId="addDepositDetailActionFromId"
                                             cssStyle="width:80px;" value="MSG.update"
                                             preAction="addDepositDetailAction!editDepositDetailExport.do"
                                             />--%>
                                <sx:submit cssStyle="width:100px;" loadingText="Processing..."
                                           formId="addDepositDetailActionFromId" executeScripts="true"
                                           showLoadingText="true" targets="addDepositStockModelId"
                                           beforeNotifyTopics="AddDepositCreateReceiptFormId/cancelDepositExport"
                                           key="MSG.destroy"  />
                                <%--<tags:submit targets="bodyContent" formId="addDepositDetailActionFromId"
                                             cssStyle="width:80px;" value="MSG.destroy"
                                             preAction="addDepositDetailAction!cancelDepositDetailExport.do"
                                             />--%>
                            </s:if>
                            <s:else>
                                <sx:submit cssStyle="width:100px;" loadingText="Processing..."
                                           formId="addDepositDetailActionFromId" executeScripts="true"
                                           showLoadingText="true" targets="addDepositStockModelId"
                                           beforeNotifyTopics="AddDepositCreateReceiptFormId/addDepositExport"
                                           value="MSG.add"  />
                                <%--<tags:submit targets="bodyContent" formId="addDepositDetailActionFromId"
                                             cssStyle="width:80px;" value="MSG.add"
                                             preAction="addDepositDetailAction!addDepositDetailExport.do"
                                             />--%>
                            </s:else>
                        </td>
                    </tr>
                </table>
                <br/>
                <s:if test="listStockModelBeans != null && listStockModelBeans.size > 0">
                    <table class="dataTable">
                        <tr>
                            <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.010'))}</th>
                            <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.050'))}</th>
                            <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.055'))}</th>
                            <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.056'))}</th>
                            <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.054'))}</th>
                            <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.057'))}</th>
                            <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.058'))}</th>
                        </tr>
                        <s:iterator value="listStockModelBeans" status="staListStockModelBeans">
                            <input type="hidden" name="mapStockModelsId['<s:property escapeJavaScript="true"  value="stockModelId"/>']" value="<s:property escapeJavaScript="true"  value="stockModelId"/>">
                            <input type="hidden" name="mapStockModelNames['<s:property escapeJavaScript="true"  value="stockModelId"/>']" value="<s:property escapeJavaScript="true"  value="stockModelName"/>">
                            <input type="hidden" name="mapStockModelPricesId['<s:property escapeJavaScript="true"  value="stockModelId"/>']" value="<s:property escapeJavaScript="true"  value="priceId"/>">
                            <input type="hidden" name="mapStockModelPrices['<s:property escapeJavaScript="true"  value="stockModelId"/>']" value="<s:property escapeJavaScript="true"  value="price"/>">
                            <input type="hidden" name="mapStockModelQuanitys['<s:property escapeJavaScript="true"  value="stockModelId"/>']" value="<s:property escapeJavaScript="true"  value="quantity"/>">
                            <tr>
                                <td class="tct"><s:property escapeJavaScript="true"  value="#staListStockModelBeans.count" /></td>
                                <td class="tct"><s:property escapeJavaScript="true"  value="stockModelName"/></td>
                                <td class="tct" style="text-align:right; ">
                                    <div id='price_<s:property escapeJavaScript="true"  value="stockModelId" />'>
                                        <s:property escapeJavaScript="true"  value="price"/>
                                    </div>
                                    <script>
                                        divNF($('price_<s:property escapeJavaScript="true"  value="stockModelId" />'));
                                    </script>
                                </td>
                                <td class="tct" style="text-align:right; "><s:property escapeJavaScript="true"  value="quantity"/></td>
                                <td class="tct" style="text-align:right; ">
                                    <div id='total_<s:property escapeJavaScript="true"  value="stockModelId" />'>
                                        <s:property escapeJavaScript="true"  value="total"/>
                                    </div>
                                    <script>
                                        divNF($('total_<s:property escapeJavaScript="true"  value="stockModelId" />'));
                                    </script>
                                </td>
                                <td class="tct" style="text-align:center; ">
                                    <s:url action="addDepositDetailAction!viewEditDepositDetailExport.do" var="viewEditInformationDepositDetail">
                                        <s:param name="stockModelId" value="stockModelId" />
                                    </s:url>
                                    <sx:a targets="addDepositStockModelId" href="%{viewEditInformationDepositDetail}" loadingText="Đang thực hiện..."
                                          showLoadingText="true" cssClass="cursorHand" executeScripts="true" parseContent="true">Sửa
                                    </sx:a>
                                </td>
                                <td class="tct" style="text-align: center; ">
                                    <s:url action="addDepositDetailAction!deleteDepositDetailExport.do" var="deleteInformationDepositDetail">
                                        <s:param name="stockModelId" value="stockModelId" />
                                    </s:url>
                                    <sx:a targets="addDepositStockModelId" href="%{deleteInformationDepositDetail}" loadingText="Đang thực hiện..."
                                          showLoadingText="true" cssClass="cursorHand" executeScripts="true" parseContent="true">Xoá
                                    </sx:a>
                                </td>
                            </tr>
                        </s:iterator>
                    </table>
                </s:if>
                <s:else>
                    <table class="dataTable">
                        <tr>
                            <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.010'))}</th>
                            <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.050'))}</th>
                            <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.055'))}</th>
                            <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.056'))}</th>
                            <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.054'))}</th>
                            <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.057'))}</th>
                            <th class="tct"> ${fn:escapeXml(imDef:imGetText('MSG.DET.058'))}</th>
                        </tr>
                        <tr><td colspan="7"> ${fn:escapeXml(imDef:imGetText('MSG.DET.059'))}</td></tr>
                    </table>
                </s:else>
                <s:hidden name="tempCompanyName" id="tempCompanyNameId" cssClass="txtInput" theme="simple" />
                <s:hidden name="company" id="companyId"  cssStyle="txtInput" theme="simple" />
            </tags:imPanel>
        </div>
        <%--</fieldset>--%>
    </s:form>
    <br/>
    <jsp:include page="returnBillsInfo.jsp"/>

</sx:div>
<script>
    dojo.event.topic.subscribe("AddDepositCreateReceiptFormId/addDepositExport", function(event, widget){
        //alert('Tên đơn vị nộp: ' + $('tempCompanyNameId').value + 'Mã:' + $('companyId').value);
        if (!validateQuanlity()){
            event.cancel = true;
            return;
        }
        // TuTM1 04/03/2012 Fix ATTT CSRF
        widget.href = "addDepositDetailAction!addDepositDetailExport.do" + "?" + token.getTokenParamString();
    });
    
    validateQuanlity = function(){
        if ($('idStockModel').value.trim() == ""){
            $('displayResultMsgClient2').innerHTML= '<s:text name="ERR.DET.030"/>';
            //$( 'displayResultMsgClient2' ).innerHTML = "Bạn phải chọn hàng hoá!";
    <%--event.cancel = true;--%>
                $('idStockModel').focus();
                return false;
            }
            if ($('quantity').value.trim() == ""){
                $('displayResultMsgClient2').innerHTML= '<s:text name="ERR.DET.049"/>';
                //$( 'displayResultMsgClient2' ).innerHTML = "Bạn phải nhập số lượng!";
    <%--event.cancel = true;--%>
                $('quantity').focus();
                return false;
            }
            if ($('quantity').value.trim().match(htmlTag) != null)
            {   $('displayResultMsgClient2').innerHTML= '<s:text name="ERR.DET.050"/>';
                //$( 'displayResultMsgClient2' ).innerHTML = "Không nên nhập thẻ html ở số lượng!";
                $('quantity').focus();
    <%--event.cancel = true;--%>
                return false;
            }
            if ($('quantity').value.trim().length>0 && !isNumberFormat($('quantity').value.trim()))
            {   $('displayResultMsgClient2').innerHTML= '<s:text name="ERR.DET.051"/>';
                //$( 'displayResultMsgClient2' ).innerHTML = "Số lượng không đúng định dạng!";
                $('quantity').focus();
                //event.cancel = true;
                return false;
            }
            if ($('quantity').value.trim().length>0 && $('quantity').value.trim() * 1 <=0)
            {   $('displayResultMsgClient2').innerHTML= '<s:text name="ERR.DET.052"/>';
                //$( 'displayResultMsgClient2' ).innerHTML = "Số lượng phải >0!";
                //event.cancel = true;
                $('quantity').focus();
                return false;
            }

            return true;
        }

        dojo.event.topic.subscribe("AddDepositCreateReceiptFormId/editDepositExport", function(event, widget){
            if (!validateQuanlity()){
                event.cancel = true;
                return;
            }
            // TuTM1 04/03/2012 Fix ATTT CSRF
            widget.href = "addDepositDetailAction!editDepositDetailExport.do" + "?" + token.getTokenParamString();
        });
        dojo.event.topic.subscribe("AddDepositCreateReceiptFormId/cancelDepositExport", function(event, widget){
            widget.href = "addDepositDetailAction!cancelDepositDetailExport.do"+ "?" + token.getTokenParamString();
        });
        dojo.event.topic.subscribe("AddDepositCreateReceiptFormId/saveDepositExport", function(event, widget){
            if (!validateDepositExport()){
                event.cancel = true;
                return;
            }
            //if (!confirm("Bạn có chắc chắn muốn tạo phiếu chi?"))
            if (!confirm("'<s:text name="MSG.DET.004"/>'")){
                event.cancel = true;
                return;
            }
            widget.href = "createAgentDepositRecieptAction!saveDepositExport.do" + "?" + token.getTokenParamString();
        });

        validateDepositExport = function(){
            if ($('formCodeId').value.trim() == ""){
                $('displayResultMsgClient3').innerHTML= '<s:text name="ERR.DET.031"/>';
                //$( 'displayResultMsgClient3' ).innerHTML = "Bạn chưa nhập mã phiếu chi!";
                $('formCodeId').focus();
                return false;
            }

            var htmlTag = '<[^>]*>';
            if ($('formCodeId').value.trim().match(htmlTag) != null)
            {   $('displayResultMsgClient3').innerHTML= '<s:text name="ERR.DET.032"/>';
                //$( 'displayResultMsgClient3' ).innerHTML = "Không nên nhập thẻ html ở mã phiếu chi!";
                $('formCodeId').focus();
                return false;
            }

            if ($('idReason').value.trim() == ""){
                $('displayResultMsgClient3').innerHTML= '<s:text name="ERR.DET.033"/>';
                //$( 'displayResultMsgClient3' ).innerHTML = "Bạn chưa lý do nộp!";
                $('idReason').focus();
                return false;
            }

            var dateExported= dojo.widget.byId("dateId");
            if(dateExported.domNode.childNodes[1].value.trim() == ""){
                $('displayResultMsgClient3').innerHTML= '<s:text name="ERR.DET.034"/>';
                //$( 'displayResultMsgClient3' ).innerHTML = "Bạn chưa nhập ngày nộp!";
                dateExported.domNode.childNodes[1].focus();
                return false;
            }
            if(dateExported.domNode.childNodes[1].value.trim() != "" &&
                !isDateFormat(dateExported.domNode.childNodes[1].value)){
                $('displayResultMsgClient3').innerHTML= '<s:text name="ERR.DET.035"/>';
                //$( 'displayResultMsgClient' ).innerHTML='Ngày nộp không hợp lệ!';
                dateExported.domNode.childNodes[1].focus();
                event.cancel = true;
                return;
            }

            if ($('userReceiptId').value.trim() == ""){
                $('displayResultMsgClient3').innerHTML= '<s:text name="ERR.DET.036"/>';
                //$( 'displayResultMsgClient3' ).innerHTML = "Bạn chưa nhập tên người nộp!";
                $('userReceiptId').focus();
                return false;
            }
            if ($('userReceiptId').value.trim().match(htmlTag) != null)
            {
                $('displayResultMsgClient3').innerHTML= '<s:text name="ERR.DET.037"/>';
                //$( 'displayResultMsgClient3' ).innerHTML = "Không nên nhập thẻ html ở tên người nộp!";
                $('userReceiptId').focus();
                return false;
            }

            if ($('idPayMethod').value.trim() == ""){
                $('displayResultMsgClient3').innerHTML= '<s:text name="ERR.DET.038"/>';
                //$( 'displayResultMsgClient3' ).innerHTML = "Bạn chưa chọn HTTT!";
                $('idPayMethod').focus();
                return false;
            }
        
            return true;
        }

        setPriceName = function(){
            var priceId = document.getElementById("priceId").selectedIndex;
            var prices = document.getElementById("priceId").options[priceId].text;
            document.getElementById("tempPriceNameId").value = prices;
        }

        //setDataForDepositDetailId = function(id){
        //      document.getElementById("tempStockModelIdDetailId").value = document.getElementById("hiddenStocKModelId_" + id).value;//tempStockModelIdDetail
        //    document.getElementById("tempStockModelNameDetailId").value =jQuery.trim(document.getElementById("tdStockModelNameId_" + id).innerHTML);//tempStockModelNameDetail
        //}

        setDataForViewStockModel = function(id){
            document.getElementById("tempidStockModel").value =document.getElementById("tempStockModelNameDetailId").value;
            document.getElementById("idStockModel").value = document.getElementById("tempStockModelIdDetailId").value;
            document.getElementById("stockNameId").value = document.getElementById("tempStockModelNameDetailId").value;
            document.getElementById("tempPriceId").value = document.getElementById("hiddenTempPriceId_" + id).value;//tempStockModelIdDetail
            document.getElementById("tempPriceNameId").value =trim(document.getElementById("tdPriceId_" + id).innerHTML.toString().replace(",", ""));//tempStockModelNameDetail
            document.getElementById("tempIdPrice").value = trim(document.getElementById("tdPriceId_" + id).innerHTML.toString().replace(",", ""));//tempStockModelNameDetail

        }



        fetchUserByJQuery = function(){
            var selectPrice = document.getElementById("idStockModel").selectedIndex;
            var price = document.getElementById("idStockModel").options[selectPrice].text;
            document.getElementById("stockNameId").value = price;
            jQuery.getJSON("AjaxRetrievePrice.do?stockModelId="  + jQuery('#idStockModel').val(), function(data) {
                toFinalConsole(data);
            });
        }

        toFinalConsole = function(jsonObject){
            var console = document.getElementById('console');
            if (console!=null){
                removeAllChildren ( console );
                var select = null,option = null;
                var lists = jsonObject.list['com.viettel.im.database.BO.Price'];
                select = document.createElement("select");
                select.setAttribute("id", "priceId");
                select.setAttribute("name", "price");
                select.setAttribute("onclick", "setPriceName();")

                option = document.createElement("option");
                option.setAttribute("value", "");
                option.appendChild(document.createTextNode("--Chọn giá--"));
                select.appendChild(option);
                if(lists != null ){
                    var listCount = lists.length;
                    if(listCount > 0){
                        for ( var index = 0;  index < listCount; index++ ) {
                            var list = lists[index];
                            option = document.createElement("option");
                            option.setAttribute("value", list['priceId']);
                            option.appendChild(document.createTextNode(list['price']));
                            select.appendChild(option);
                        }
                    }else{
                        option = document.createElement("option");
                        option.setAttribute("value", lists.priceId);
                        option.appendChild(document.createTextNode(lists.price));
                        select.appendChild(option);
                    }
                }
                console.appendChild(select);
            }
        }

        removeAllChildren= function(node){
            var childCount = node.childNodes.length;
            for ( var count = 1; count <= childCount; count++) {
                node.removeChild ( node.childNodes[0] );
            }
        }

</script>




