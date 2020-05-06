<%-- 
    Document   : createInvoice
    Created on : Sep 10, 2009, 5:02:04 PM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>


<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="SaleTransInvoiceAction" theme="simple" enctype="multipart/form-data"  method="post" id="form">
<s:token/>

    <fieldset style="width:100%">
        <tags:imPanel title="MSG.bill.info">
            <table class="inputTbl2Col">
                <%-- Thong tin Hoa don --%>
                <tr>
                    <td colspan="2">
                        <sx:div id="serialNoArea" cssStyle="width:100%">
                            <jsp:include page="createInvoiceSerialNo.jsp"/>
                        </sx:div>
                    </td>
                </tr>
                <%-- Thong tin Khach hang --%>
                <tr>
                    <td colspan="2">
                        <table class="inputTbl6Col">
                            <s:if test="form.invoiceUsedId == null || form.invoiceUsedId == ''">
                                <tr>
                                    <s:hidden name="form.saleGroup" id="saleGroup" />
                                    <s:hidden name="form.shopId" id="shopId" />
                                    <s:hidden name="form.staffId" id="staffId" />
                                    <s:hidden name="form.invoiceUsedId" id="invoiceUsedId" />
                                    <s:hidden name="form.isPopup" id="isPopup" />
                                    <s:hidden name="form.pageForward" id="pageForward" />
                                    <s:if test="form.saleGroup == 1">
                                        <td class="label"><tags:label key="MSG.shop" required="true"/></td>
                                    </s:if>
                                    <s:elseif test="form.saleGroup == 2">
                                        <td class="label"><tags:label key="MSG.unit.name" required="true"/></td>
                                    </s:elseif>
                                    <s:else>
                                        <td class="label"><tags:label key="MSG.customer.name" required="true"/></td>
                                    </s:else>
                                    <td  class="text">
                                        <s:textfield name="form.objName" id="objName" maxlength="200" cssClass="txtInputFull"/>
                                    </td>
                                    <td class="label"> <tags:label key="MSG.company"/></td>
                                    <td  class="text">
                                        <s:textfield name="form.objCompany" id="objCompany" maxlength="100" cssClass="txtInputFull"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.tax.code"/></td>
                                    <td  class="text">
                                        <s:textfield name="form.objTin"  id="objTin" maxlength="100" cssClass="txtInputFull"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.account"/></td>
                                    <td  class="text">
                                        <s:textfield name="form.objAccount" id="objAccount" maxlength="40" cssClass="txtInputFull"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.address"/></td>
                                    <td colspan="3"  class="text">
                                        <s:textfield name="form.objAddress" id="objAddress" maxlength="200" cssClass="txtInputFull"/>
                                    </td>
                                </tr>
                            </s:if>
                            <s:else>
                                <tr>
                                    <s:hidden name="form.saleGroup" id="saleGroup" />
                                    <s:hidden name="form.shopId" id="shopId" />
                                    <s:hidden name="form.staffId" id="staffId" />
                                    <s:hidden name="form.invoiceUsedId" id="invoiceUsedId" />
                                    <s:hidden name="form.isPopup" id="isPopup" />
                                    <s:hidden name="form.pageForward" id="pageForward" />
                                    <s:if test="form.saleGroup == 1">
                                        <td class="label"><tags:label key="MSG.shop" required="true"/></td>
                                    </s:if>
                                    <s:else>
                                        <td class="label"><tags:label key="MSG.customer.name" required="true"/></td>
                                    </s:else>
                                    <td  class="text">
                                        <s:textfield name="form.objName" id="objName" maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.company"/></td>
                                    <td  class="text">
                                        <s:textfield name="form.objCompany" id="objCompany" maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.tax.code"/></td>
                                    <td  class="text">
                                        <s:textfield name="form.objTin"  id="objTin" maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label"><tags:label key="MSG.account"/></td>
                                    <td  class="text">
                                        <s:textfield name="form.objAccount" id="objAccount" maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.address"/></td>
                                    <td colspan="3" class="text">
                                        <s:textfield name="form.objAddress" id="objAddress" maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </tr>
                            </s:else>

                            <tr>
                                <s:if test="form.invoiceUsedId == null || form.invoiceUsedId == ''">
                                    <td class="label"><tags:label key="MSG.payMethod" required="true"/></td>
                                    <td class="text">
                                        <s:select name="form.payMethodId"
                                                  id="payMethodId"
                                                  cssClass="txtInputFull"
                                                  headerKey="" headerValue="--Chọn HTTT--"
                                                  list="%{#session.lstPayMethod != null?#session.lstPayMethod:#{}}"
                                                  listKey="code" listValue="name"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.comment"/></td>
                                    <td colspan="3" rowspan="2"  style="vertical-align:top; height:100%;">
                                        <s:textarea name="form.note" cssStyle="height:100%; "  id="note" cssClass="txtInputFull"/>
                                    </td>
                                </s:if>
                                <s:else>
                                    <td class="label"><tags:label key="MSG.payMethod" required="true"/></td>
                                    <td class="text">
                                        <s:textfield name="form.payMethodId" id="payMethodId" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                    </td>
                                    <td class="label"><tags:label key="MSG.comment"/></td>
                                    <td colspan="3" rowspan="2"  style="vertical-align:top; height:100%;">
                                        <s:textarea name="form.note" cssStyle="height:100%; "  id="note" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </s:else>
                            </tr>
                            <tr>
                                <s:if test="form.invoiceUsedId == null || form.invoiceUsedId == ''">
                                    <td class="label"><tags:label key="MSG.reasonCreateInvoice" required="true"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    <td class="text">
                                        <s:select name="form.reasonId"
                                                  id="reasonId"
                                                  cssClass="txtInputFull"
                                                  headerKey="" headerValue="--Chọn lý do--"
                                                  list="%{#session.lstReasonInvoice != null?#session.lstReasonInvoice:#{}}"
                                                  listKey="objId" listValue="objName"/>
                                    </td>
                                </s:if>
                                <s:else>
                                    <td class="label"><tags:label key="MSG.reasonCreateInvoice" required="true"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    <td class="text">
                                        <s:textfield name="form.reasonId" id="reasonId" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                    </td>
                                </s:else>
                            </tr>
                        </table>
                    </td>
                </tr>
                <%-- Thong tin Tong tien --%>
                <%--<tr>
                    <td>
                        <table class="inputTbl6Col" >
                            <tr>
                                <s:if test="form.invoiceUsedId == null || form.invoiceUsedId == ''">
                                    <td class="label">HTTT (<font color="red">*</font>)</td>
                                    <td class="text">
                                        <s:select name="form.payMethodId"
                                                  id="payMethodId"
                                                  cssClass="txtInputFull"
                                                  headerKey="" headerValue="--Chọn HTTT--"
                                                  list="%{#session.lstPayMethod != null?#session.lstPayMethod:#{}}"
                                                  listKey="code" listValue="name"/>
                                    </td>
                                    <td class="label">Ghi chú</td>
                                    <td colspan="3" rowspan="2"  style="vertical-align:top; height:100%;">
                                        <s:textarea name="form.note" cssStyle="height:100%; "  id="note" cssClass="txtInputFull"/>
                                    </td>
                                </s:if>
                                <s:else>
                                    <td class="label">HTTT (<font color="red">*</font>)</td>
                                    <td class="text">
                                        <s:textfield name="form.payMethodId" id="payMethodId" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                    </td>
                                    <td class="label">Ghi chú</td>
                                    <td colspan="3" rowspan="2"  style="vertical-align:top; height:100%;">
                                        <s:textarea name="form.note" cssStyle="height:100%; "  id="note" cssClass="txtInputFull" readonly="true"/>
                                    </td>
                                </s:else>
                            </tr>
                            <tr>
                                <s:if test="form.invoiceUsedId == null || form.invoiceUsedId == ''">
                                    <td class="label">Lý do lập HĐ (<font color="red">*</font>)</td>
                                    <td class="text">
                                        <s:select name="form.reasonId"
                                                  id="reasonId"
                                                  cssClass="txtInputFull"
                                                  headerKey="" headerValue="--Chọn lý do--"
                                                  list="%{#session.lstReasonInvoice != null?#session.lstReasonInvoice:#{}}"
                                                  listKey="objId" listValue="objName"/>
                                    </td>
                                </s:if>
                                <s:else>
                                    <td class="label">Lý do lập HĐ (<font color="red">*</font>)</td>
                                    <td class="text">
                                        <s:textfield name="form.reasonId" id="reasonId" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                    </td>
                                </s:else>
                            </tr>
                        </table>

                    </td>
                </tr>    --%>
            </table>
        </tags:imPanel>
    </fieldset>

    <%--Hien thi ket qua tim kiem--%>
    <div id ="createInvoiceNotSaleDetailArea">
        <jsp:include page="createInvoiceNotSaleDetail.jsp"/>
    </div>
    <br/>    
    <%-- Button su kien --%>
    <div align="center" style="width:100%">
        <s:if test="form.invoiceUsedId == null || form.invoiceUsedId == ''">
            <tags:submit targets="bodyContent" formId="form"
                         confirm="true" confirmText="MSG.create.invoice"
                         showLoadingText="true"
                         value="MSG.createBills" preAction="SaleTransInvoiceAction!createInvoiceNotSale.do"
                         validateFunction="checkInputInvoice()"
                         />
            <input type="button" value="In hóa đơn" style="width:85px;" disabled/>
        </s:if>
        <s:else>
            <input type="button" value="Lập hóa đơn" style="width:85px; " disabled/>
            <tags:submit targets="bodyContent" formId="form"
                         value="MSG.printBills" preAction="SaleTransInvoiceAction!printInvoice.do"
                         showLoadingText="true"
                         />
        </s:else>
    </div>
    <br/>
    <div style="width:100%;" align="center">
        <s:if test="form.exportUrl!=null && form.exportUrl!=''">
            <script>
                window.open('${contextPath}<s:property escapeJavaScript="true"  value="form.exportUrl"/>','','toolbar=yes,scrollbars=yes');
            </script>
            <a href='${contextPath}<s:property escapeJavaScript="true"  value="form.exportUrl"/>' ><tags:label key="MSG.click.download.invoice"/></a>
        </s:if>
    </div>
</s:form>

<%--<script type="text/javascript" language="javascript">
    textFieldNF($('amountNotTax'));
    textFieldNF($('tax'));
    textFieldNF($('discount'));
    textFieldNF($('promotion'));
    textFieldNF($('amountTax'));
</script>
--%>

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
        if (100<trim(tmp).length){
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

        tmp = $( 'reasonId' ).value;
        if (200<trim(tmp).length){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.056"/>';
            //$('returnMsgClient').innerHTML = "Bạn chưa chọn lý do lập hoá đơn!";
            $( 'reasonId' ).focus();
            return false;
        }

        tmp = $( 'payMethodId' ).value;
        if (200<trim(tmp).length){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.054"/>';
            //$('returnMsgClient').innerHTML = "Bạn chưa chọn hình thức thanh toán!";
            $( 'payMethodId' ).focus();
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
