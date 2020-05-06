<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : createInvoice
    Created on : Sep 10, 2009, 5:02:04 PM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="SaleTransInvoiceAction" theme="simple" method="post" id="form">
<s:token/>


    <s:if test="#request.recoverInvoice == true">
        <script type="text/javascript" language="javascript">
            window.close();
        </script>
    </s:if>

    <div style="width:100%">
        <tags:imPanel title="MSG.invoice.info">
            <sx:div id="serialNoArea"  cssStyle="width:100%">
                <jsp:include page="createInvoiceSerialNo.jsp"/>
            </sx:div>
            <table class="inputTbl2Col">
                <%-- Thong tin Hoa don --%>
                <tr>
                    <s:hidden name="form.saleGroup" id="saleGroup" />
                    <s:hidden name="form.shopId" id="shopId" />
                    <s:hidden name="form.staffId" id="staffId" />
                    <s:hidden name="form.invoiceUsedId" id="invoiceUsedId" />
                    <s:hidden name="form.isPopup" id="isPopup" />
                    <s:hidden name="form.invoiceStatus" id="invoiceStatus" />

                    <s:hidden name="form.cancelInvoice" id="cancelInvoice" />
                    <s:hidden name="form.editInvoice" id="editInvoice" />
                    <s:hidden name="form.recoverInvoice" id="recoverInvoice" />
                    <s:hidden name="form.processingInvoice" id="processingInvoice" />

                    <s:hidden name="form.saleTransList" id="saleTransList" />

                    </td>
                </tr>
                <%-- Thong tin Khach hang --%>
                <tr>
                    <td colspan="2">
                        <table class="inputTbl6Col" style="width:100%">
                            <s:if test="form.invoiceUsedId == null || form.invoiceUsedId == ''">
                                <tr>
                                    <s:if test="form.saleGroup == 1">
                                        <td class="label"><tags:label key="MSG.shop"/></td>
                                    </s:if>
                                    <s:else>
                                        <td class="label"><tags:label key="MSG.customer.name"/></td>
                                    </s:else>
                                    <td class="text">
                                        <s:textfield name="form.objName" id="objName" maxlength="200" cssClass="txtInputFull"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.company"/></td>
                                    <td class="text">
                                        <s:textfield name="form.objCompany" id="objCompany" maxlength="100" cssClass="txtInputFull"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.tax.code"/></td>
                                    <td class="text">
                                        <s:textfield name="form.objTin"  id="objTin" maxlength="30" cssClass="txtInputFull"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.account"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    <td class="text">
                                        <s:textfield name="form.objAccount" id="objAccount" maxlength="40" cssClass="txtInputFull"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.address"/></td>
                                    <td colspan="3" class="text">
                                        <s:textfield name="form.objAddress" id="objAddress" maxlength="200" cssClass="txtInputFull"/>
                                    </td>
                                </tr>
                            </s:if>
                            <s:else>
                                <tr>
                                    <s:if test="form.saleGroup == 1">
                                        <td class="label"><tags:label key="MSG.shop"/></td>
                                    </s:if>
                                    <s:else>
                                        <td class="label"><tags:label key="MSG.customer.name"/></td>
                                    </s:else>
                                    <td class="text">
                                        <s:if test="form.processingInvoice == 1">
                                            <s:textfield name="form.objName" id="objName" maxlength="200" cssClass="txtInputFull" readonly="false"/>
                                        </s:if>
                                        <s:else>
                                            <s:textfield name="form.objName" id="objName" maxlength="200" cssClass="txtInputFull" readonly="true"/>
                                        </s:else>

                                    </td>
                                    <td class="label"><tags:label key="MSG.company"/></td>
                                    <td class="text">
                                        <s:if test="form.processingInvoice == 1">
                                            <s:textfield name="form.objCompany" id="objCompany" maxlength="200" cssClass="txtInputFull" readonly="false"/>
                                        </s:if>
                                        <s:else>
                                            <s:textfield name="form.objCompany" id="objCompany" maxlength="200" cssClass="txtInputFull" readonly="true"/>
                                        </s:else>


                                    </td>
                                    <td class="label"><tags:label key="MSG.tax.code"/></td>
                                    <td class="text">
                                        <s:if test="form.processingInvoice == 1">
                                            <s:textfield name="form.objTin"  id="objTin" maxlength="30" cssClass="txtInputFull" readonly="false"/>
                                        </s:if>
                                        <s:else>
                                            <s:textfield name="form.objTin"  id="objTin" maxlength="30" cssClass="txtInputFull" readonly="true"/>
                                        </s:else>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.account"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    <td class="text">
                                        <s:if test="form.processingInvoice == 1">
                                            <s:textfield name="form.objAccount" id="objAccount" maxlength="40" cssClass="txtInputFull" readonly="false"/>
                                        </s:if>
                                        <s:else>
                                            <s:textfield name="form.objAccount" id="objAccount" maxlength="40" cssClass="txtInputFull" readonly="true"/>
                                        </s:else>
                                    </td>
                                    <td class="label"><tags:label key="MSG.address"/></td>
                                    <td colspan="3" class="text">
                                        <s:if test="form.processingInvoice == 1">
                                            <s:textfield name="form.objAddress" id="objAddress" maxlength="200" cssClass="txtInputFull" readonly="false"/>
                                        </s:if>
                                        <s:else>
                                            <s:textfield name="form.objAddress" id="objAddress" maxlength="200" cssClass="txtInputFull" readonly="true"/>
                                        </s:else>
                                    </td>
                                </tr>
                            </s:else>
                            <tr>
                                <!--                                Check view or edit-->
                                <s:if test="form.processingInvoice == 1 || form.invoiceUsedId == null || form.invoiceUsedId == ''">
                                    <c:set var="var_invoice_disabled" scope="page" value="false" />
                                </s:if>
                                <s:else>
                                    <c:set var="var_invoice_disabled" scope="page" value="true" />
                                </s:else>

                                <td class="label"><tags:label key="MSG.amountMoneyNotTax"/></td>
                                <td class="text">
                                    <s:textfield name="form.amountNotTaxMoney" readonly="true" id="amountNotTax" maxlength="200" cssClass="txtInputFull" cssStyle="text-align:right;"/>
                                </td>
                                <td class="label"><tags:label key="MSG.payMethod" required="true"/></td>
                                <td class="text">
                                    <c:set var="var_payMethodId_header" scope="page" value="${fn:escapeXml(imDef:imGetText('MSG.SAE.170'))}" />                                    
                                    <s:select name="form.payMethodId" disabled="%{#attr.var_invoice_disabled}"
                                              id="payMethodId"
                                              cssClass="txtInputFull"
                                              headerKey="" headerValue="%{#attr.var_payMethodId_header}"
                                              list="%{#session.lstPayMethod != null?#session.lstPayMethod:#{}}"
                                              listKey="code" listValue="name"/>

                                    <%--s:if test="form.processingInvoice == 1 || form.invoiceUsedId == null || form.invoiceUsedId == ''">
                                        <s:select name="form.payMethodId"
                                                  id="payMethodId"
                                                  cssClass="txtInputFull"
                                                  headerKey="" headerValue="--Chọn HTTT--"
                                                  list="%{#session.lstPayMethod != null?#session.lstPayMethod:#{}}"
                                                  listKey="code" listValue="name"/>
                                    </s:if>
                                    <s:else>

                                        <s:select name="form.payMethodId" disabled="true"
                                                  id="payMethodId"
                                                  cssClass="txtInputFull"
                                                  headerKey="" headerValue="--Chọn HTTT--"
                                                  list="%{#session.lstPayMethod != null?#session.lstPayMethod:#{}}"
                                                  listKey="code" listValue="name"/>
                                    </s:else--%>
                                </td>
                                <td class="label"><tags:label key="MSG.reasonCreateContract" required="true"/></td>
                                <td class="text">
                                    <c:set var="var_reasonId_header" scope="page" value="${fn:escapeXml(imDef:imGetText('MSG.SAE.013'))}" />
                                    <s:select name="form.reasonId" disabled="%{#attr.var_invoice_disabled}"
                                              id="reasonId"
                                              cssClass="txtInputFull"
                                              headerKey="" headerValue="%{#attr.var_reasonId_header}"
                                              list="%{#session.lstReasonInvoice != null?#session.lstReasonInvoice:#{}}"
                                              listKey="objId" listValue="objName"/>

                                    <%--s:if test="form.processingInvoice == 1 || form.invoiceUsedId == null || form.invoiceUsedId == ''">
                                        <s:select name="form.reasonId"
                                                  id="reasonId"
                                                  cssClass="txtInputFull"
                                                  headerKey="" headerValue="--Chọn lý do--"
                                                  list="%{#session.lstReasonInvoice != null?#session.lstReasonInvoice:#{}}"
                                                  listKey="objId" listValue="objName"/>
                                    </s:if>
                                    <s:else>
                                        <s:select name="form.reasonId" disabled="true"
                                                  id="reasonId"
                                                  cssClass="txtInputFull"
                                                  headerKey="" headerValue="--Chọn lý do--"
                                                  list="%{#session.lstReasonInvoice != null?#session.lstReasonInvoice:#{}}"
                                                  listKey="objId" listValue="objName"/>
                                    </s:else--%>
                                </td>
                            </tr>
                            <tr>
                                <td class="label"><tags:label key="MSG.money.tax"/></td>
                                <td class="text">
                                    <s:textfield name="form.taxMoney" readonly="true" id="tax" maxlength="1000" cssClass="txtInputFull" cssStyle="text-align:right;"/>
                                </td>
                                <td class="label"> <tags:label key="MSG.comment"/></td>
                                <s:if test="form.invoiceUsedId == null || form.invoiceUsedId == ''">
                                    <td colspan="3" rowspan="4"  style="vertical-align:top; height:100%;" class="text">
                                        <s:textarea name="form.note" cssStyle="height:100%; "  id="note" cssClass="txtInputFull"/>
                                    </td>
                                </s:if>
                                <s:else>
                                    <td colspan="3" rowspan="4"  style="vertical-align:top; height:100%;" class="text">
                                        <s:textarea name="form.note" cssStyle="height:100%; "  id="note" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </s:else>
                            </tr>
                            <tr>
                                <td class="label"> <tags:label key="MSG.discount"/></td>
                                <td class="text">
                                    <s:textfield name="form.discountMoney" readonly="true" id="discount" maxlength="1000" cssClass="txtInputFull"  cssStyle="text-align:right;"/>
                                </td>
                                <td></td>

                            </tr>
                            <tr>
                                <td class="label"> <tags:label key="MSG.promotion"/></td>
                                <td class="text">
                                    <s:textfield  name="form.promotionMoney" readonly="true" id="promotion" maxlength="1000" cssClass="txtInputFull"  cssStyle="text-align:right;"/>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="label"> <tags:label key="MSG.totalPriceAfterRate"/>&nbsp;&nbsp;</td>
                                <td class="text">
                                    <s:textfield  name="form.amountTaxMoney" readonly="true" id="amountTax" maxlength="1000" cssClass="txtInputFull"  cssStyle="text-align:right;"/>
                                </td>
                                <td></td>
                            </tr>
                            <%-- combo --%>
                            <s:if test="form.processingInvoice == 2">
                                <tr>
                                    <td class="label"> <tags:label key="MSG.reason.cancel"/></td>
                                    <td>
                                        <!--                                    MSG.SIK.255-->
                                        <c:set var="var_cancelReasonId" scope="page" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.255'))}" />
                                        <%--s:select name="form.cancelReasonId" id="cancelReasonId"
                                                  cssClass="txtInputFull"
                                                  headerKey="" headerValue="--Chọn lý do hủy--"
                                                  list="%{#session.lstReasonDestroy != null?#session.lstReasonDestroy:#{}}"
                                                  listKey="reasonId" listValue="reasonName" theme="simple"/--%>
                                        <s:select name="form.cancelReasonId" id="cancelReasonId"
                                                  cssClass="txtInputFull"
                                                  headerKey="" headerValue="%{#attr.var_cancelReasonId}"
                                                  list="%{#session.lstReasonDestroy != null?#session.lstReasonDestroy:#{}}"
                                                  listKey="reasonId" listValue="reasonName" theme="simple"/>
                                    </td>
                                </tr>
                            </s:if>
                        </table>
                    </td>
                </tr>

                <%-- Button su kien --%>
                <tr>
                    <td colspan="2">
                        <div align="center" style="width:100%">
                            <s:if test="form.isPopup == null || form.isPopup == '' || (form.isPopup*1 != 1)">
                                <s:if test="form.invoiceUsedId == null || form.invoiceUsedId == ''">
                                    <tags:submit targets="bodyContent" formId="form"
                                                 confirm="true" confirmText="MSG.create.invoice"
                                                 showLoadingText="true" cssStyle="width:85px;"
                                                 value="MSG.createBills" preAction="SaleTransInvoiceAction!createInvoice.do"
                                                 validateFunction="checkInputInvoice()"
                                                 />
                                    <!--                                <input type="button" value="In hóa đơn" style="width:85px;" disabled/>-->
                                    <input type="button" value="<s:text name="MSG.printBills"/>" style="width:85px;" disabled/>
                                </s:if>
                                <s:else>
                                    <!--                                <input type="button" value="Lập hóa đơn" style="width:85px; " disabled/>-->
                                     <!--<input type="button" value="<s:property value="getText('MSG.createBills')"/>" style="width:85px; " disabled/>-->
                                    <%--<tags:submit targets="bodyContent" formId="form" cssStyle="width:85px;"--%>
                                    <!--value="MSG.printBills" preAction="SaleTransInvoiceAction!printInvoice.do"-->
                                    <!--showLoadingText="true"-->
                                    <!--/>-->
                                    <!--                                <input type="button" value="Lập hóa đơn" style="width:85px; " disabled/>-->
                                    <input type="button" value="<s:text name="MSG.createBills"/>" style="width:85px; " disabled/>
                                    <s:if test="#request.print == 1">
                                        <tags:submit targets="bodyContent"
                                                     formId="form"
                                                     id="printInvoiceId"
                                                     cssStyle="width:100px;"
                                                     value="MSG.printBills" preAction="SaleTransInvoiceAction!printInvoice.do"
                                                     showLoadingText="true"
                                                     />
                                    </s:if>
                                    <s:else>
                                        <input type="button" value="<s:text name="MSG.printBills"/>" style="width:100px;" disabled/>
                                    </s:else>
                                </s:else>
                                <tags:submit targets="bodyContent" formId="form" cssStyle="width:85px;"
                                             value="MSG.back" preAction="SaleTransInvoiceAction!goBack.do"
                                             showLoadingText="true"
                                             />
                            </s:if>
                            <s:else>
                                <s:if test="form.processingInvoice == 0">
                                    <s:if test="form.invoiceStatus * 1 == 1 && form.editInvoice == true">
                                        <!--                                        <input type="button" value="Sửa HĐ" style="width:85px;" onclick="gotoEditInvoice();"/>-->
                                        <input type="button" value="<s:text name="MSG.SAE.206"/>" style="width:85px;" onclick="gotoEditInvoice();"/>
                                    </s:if>
                                    <s:else>
                                        <!--                                        <input type="button" value="Sửa HĐ" style="width:85px;" disabled/>-->
                                        <input type="button" value="<s:text name="MSG.SAE.206"/>" style="width:85px;" disabled/>
                                    </s:else>
                                    <s:if test="form.invoiceStatus * 1 == 1 && form.cancelInvoice == true">
                                        <!--                                        <input type="button" value="Huỷ HĐ" style="width:85px;" onclick="gotoCancelInvoice();"/>-->
                                        <input type="button" value="<s:text name="MSG.SAE.207"/>" style="width:85px;" onclick="gotoCancelInvoice();"/>
                                    </s:if>
                                    <s:else>
                                        <!--                                        <input type="button" value="Huỷ HĐ" style="width:85px;" disabled/>-->
                                        <input type="button" value="<s:text name="MSG.SAE.207"/>" style="width:85px;" disabled/>
                                    </s:else>

                                    <s:if test="form.invoiceStatus * 1 == 4 && form.recoverInvoice == true">
                                        <!--                                        <input type="button" value="Khôi phục" style="width:85px;" onclick="gotoRecoverInvoice();"/>-->
                                        <input type="button" value="<s:text name="MSG.SAE.208"/>" style="width:85px;" onclick="gotoRecoverInvoice();"/>
                                    </s:if>
                                    <s:else>
                                        <!--                                        <input type="button" value="Khôi phục" style="width:85px;" disabled/>-->
                                        <input type="button" value="<s:text name="MSG.SAE.208"/>" style="width:85px;" disabled/>
                                    </s:else>
                                </s:if>
                                <s:else>
                                    <!--                                    <input type="button" value="Đồng ý" style="width:85px;" onclick="gotoProcessingInvoice();"/>-->
                                    <input type="button" value="<s:text name="MSG.SAE.209"/>" style="width:85px;" onclick="gotoProcessingInvoice();"/>
                                    <!--                                    <input type="button" value="Bỏ qua" style="width:85px;" onclick="gotoBackInvoice();"/>-->
                                    <input type="button" value="<s:text name="MSG.SAE.034"/>" style="width:85px;" onclick="gotoBackInvoice();"/>
                                </s:else>
                            </s:else>
                        </div>
                        <div style="width:100%;" align="center" id="resultArea">
                             <!--tannh20180424 start: : khong cho tai file theo YC TraTV phong TC-->
                             <s:if test="form.exportUrl!=null && form.exportUrl!=''">
                                 <script>
                                      window.printPDF = function() { 
                                            var objFra =  document.getElementById('myFrame'); 
                                            objFra.contentWindow.focus();
                                            objFra.contentWindow.print();
                                        }
                                 </script>
                                 <iframe 
                                     src="${contextPath}<s:property escapeJavaScript="true"  value="form.exportUrl"/>" 
                                     id="myFrame" 
                                     frameborder="0" style="border:0;" 
                                     width="1" height="1">
                                 </iframe>

                                 <div>
                                     <input type="button"  onclick="this.disabled=true;printPDF()" value="Print File One" />
                                 </div>    

                             </s:if>

                             <s:if test="form.exportUrl1!=null && form.exportUrl1!=''">
                                 <script>
                                      window.printPDF1 = function() { 
                                            var objFra =  document.getElementById('myFrame1'); 
                                            objFra.contentWindow.focus();
                                            objFra.contentWindow.print();
                                        }
                                 </script>
                                 <iframe 
                                     src="${contextPath}<s:property escapeJavaScript="true"  value="form.exportUrl1"/>" 
                                     id="myFrame1" 
                                     frameborder="0" style="border:0;" 
                                     width="1" height="1">
                                 </iframe>

                                 <div>
                                     <input type="button"  onclick="this.disabled=true;printPDF1()" value="Print File Two" />
                                 </div>    

                             </s:if>

                             <s:if test="form.exportUrl2!=null && form.exportUrl2!=''">
                                 <script>
                                      window.printPDF2 = function() { 
                                            var objFra =  document.getElementById('myFrame2'); 
                                            objFra.contentWindow.focus();
                                            objFra.contentWindow.print();
                                        }
                                 </script>
                                 <iframe 
                                     src="${contextPath}<s:property escapeJavaScript="true"  value="form.exportUrl2"/>" 
                                     id="myFrame2" 
                                     frameborder="0" style="border:0;" 
                                     width="1" height="1">
                                 </iframe>

                                 <div>
                                     <input type="button"  onclick="this.disabled=true;printPDF2()" value="Print File Three" />
                                 </div>    

                             </s:if>
                           
                        </div>
                    </td>
                </tr>
            </table>
        </tags:imPanel>
    </div>
    <br/>

    <%--Hien thi ket qua tim kiem--%>
    <jsp:include page="invoiceSaleTransList.jsp"/>

</s:form>
<script type="text/javascript" language="javascript">
    //    textFieldNF($('amountNotTax'));
    //    textFieldNF($('tax'));
    //    textFieldNF($('discount'));
    //    textFieldNF($('promotion'));
    //    textFieldNF($('amountTax'));
</script>

<script type="text/javascript" language="javascript">

    var htmlTag = '<[^>]*>';
   
    gotoEditInvoice = function(){
        document.getElementById("form").action="SaleTransInvoiceAction!gotoEditInvoice.do";
        document.getElementById("form").submit();
    }
    
    gotoCancelInvoice = function(){
        document.getElementById("form").action="SaleTransInvoiceAction!gotoCancelInvoice.do";
        document.getElementById("form").submit();
    }
        
    gotoRecoverInvoice = function(){
        //if(!confirm("Bạn có chắc chắn muốn khôi phục hoá đơn không?"))
        if(!confirm(getUnicodeMsg("<s:text name="MSG.SAE.201"/>"))){
            return;
        }        
        document.getElementById("form").action="SaleTransInvoiceAction!recoverInvoice.do";
        document.getElementById("form").submit();
    }
    
    gotoProcessingInvoice = function(){        
        var processingType = $('processingInvoice').value;
        if (processingType == null){
            return;
        }
        if (processingType == 1){
            editInvoice();
        }
        if (processingType * 1 == 2){
            cancelInvoice();
        }
    }
    
    gotoBackInvoice = function(){        
        document.getElementById("form").action="SaleTransInvoiceAction!gotoBackInvoice.do";
        document.getElementById("form").submit();
    }
    
    editInvoice =function(){
        /*
        var tmp = $( 'objName' ).value;
        if (null==tmp || 0==trim(tmp).length){
            $('returnMsgClient').innerHTML = "Bạn chưa nhập tên khách hàng!";
            $( 'objName' ).focus();
            return return;
        }
            
        tmp = $( 'payMethodId' ).value;
        if (null==tmp || 0==trim(tmp).length){
            $('returnMsgClient').innerHTML = "Bạn chưa chọn hình thức thanh toán!";
            $( 'payMethodId' ).focus();
            return return;
        }
        
        tmp = $( 'reasonId' ).value;
        if (null==tmp || 0==trim(tmp).length){
            $('returnMsgClient').innerHTML = "Bạn chưa chọn lý do lập hoá đơn!";
            $( 'reasonId' ).focus();
            return return;
        }
         */
        //if(!confirm("Bạn có chắc chắn muốn sửa hoá đơn không?"))
        if(!confirm(getUnicodeMsg("<s:text name="MSG.SAE.203"/>"))){
            return;
        }
        document.getElementById("form").action="SaleTransInvoiceAction!editInvoice.do?" + token.getTokenParamString();
        document.getElementById("form").submit();
    }
    
    cancelInvoice =function(){        
        if ($('cancelReasonId') != null){
            var tmp = $( 'cancelReasonId' ).value;
            if (trim(tmp) == "" ){
                $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.038"/>';
                //$('returnMsgClient').innerHTML = "Bạn chưa chọn lý do huỷ hoá đơn!";
                $( 'cancelReasonId' ).focus();
                return;
            }
        }
        
        //if(!confirm("Bạn có chắc chắn muốn huỷ hoá đơn không?"))
        if(!confirm(getUnicodeMsg("<s:text name="MSG.SAE.202"/>"))){
            return;
        }
        document.getElementById("form").action="SaleTransInvoiceAction!cancelInvoice.do?" + token.getTokenParamString();
        document.getElementById("form").submit();
    }
    
    recoverInvoice =function(){
    <%--if(!confirm("Bạn có chắc chắn muốn khôi phục hoá đơn không?")){
        return;
    }--%>
            document.getElementById("form").action="SaleTransInvoiceAction!recoverInvoice.do?" + token.getTokenParamString();
            document.getElementById("form").submit();
        }
</script>

<script type="text/javascript" language="javascript">

    checkInputInvoice = function(){
        //invoiceNo
        var tmp = $( 'serialNo' ).value;
        if (null==tmp || 0==trim(tmp).length){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.039"/>';
            //$('returnMsgClient').innerHTML = "Bạn chưa chọn số hoá đơn!";
            $( 'serialNo' ).focus();
            return false;
        }
        //objName
        tmp = $( 'objName' ).value;
        if (null==tmp || 0==trim(tmp).length){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.040"/>';
            //$('returnMsgClient').innerHTML = "Bạn chưa nhập tên khách hàng!";
            $( 'objName' ).focus();
            return false;
        }

        if (tmp.trim().match(htmlTag) != null)

        {   $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.041"/>';
            //$( 'returnMsgClient' ).innerHTML = "Không nên nhập thẻ html ở tên khách hàng !";
            $( 'objName' ).focus();
            return false;
        }
                
        if (200<trim(tmp).length){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.042"/>';
            //$('returnMsgClient').innerHTML = "Tên khách hàng quá dài so với quy định!";
            $( 'objName' ).focus();
            return false;
        }

        tmp = $( 'objCompany' ).value;
        if (100<trim(tmp).length){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.043"/>';
            //$('returnMsgClient').innerHTML = "Tên công ty quá dài so với quy định!";
            $( 'objCompany' ).focus();
            return false;
        }

        tmp = $( 'objAccount' ).value;
        if (40<trim(tmp).length){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.044"/>';
            //$('returnMsgClient').innerHTML = "Số tài khoản quá dài so với quy định!";
            $( 'objAccount' ).focus();
            return false;
        }

        //objAddress
        tmp = $( 'objAddress' ).value;
        if (200<trim(tmp).length){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.045"/>';
            //$('returnMsgClient').innerHTML = "Địa chỉ khách hàng quá dài so với quy định!";
            $( 'objAddress' ).focus();
            return false;
        }

        tmp = $( 'objTin' ).value;
        if (30<trim(tmp).length){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.046"/>';
            //$('returnMsgClient').innerHTML = "Mã số thuế quá dài so với quy định!";
            $( 'objTin' ).focus();
            return false;
        }

        tmp = $( 'note' ).value;
        if (200<trim(tmp).length){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.047"/>';
            //$('returnMsgClient').innerHTML = "Thông tin ghi chú quá dài so với quy định!";
            $( 'note' ).focus();
            return false;
        }
        return true;
    }

    getInvoiceNo = function(){
        var serialNo =document.getElementById("serialNo");
        if (null != serialNo){
            if (0 != serialNo.value.trim().length){
                gotoAction("serialNoArea", 'SaleTransInvoiceAction!getInvoiceNo.do?serialNo=' + serialNo.value.trim());
                return;
            }
        }
        document.getElementById("fromInvoice").value = "";
        document.getElementById("toInvoice").value = "";
        document.getElementById("curInvoice").value = "";
        $( 'invoiceNo' ).innerHTML = "";
        $( 'invoiceListId' ).innerHTML = "";
    }
</script>
