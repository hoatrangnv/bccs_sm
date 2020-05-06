<%--
    Document   : invoiceManagementDetail
    Created on : Aug 13, 2009, 8:49:24 AM
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
<s:form action="destroySaleInvoiceAction!prepareDestroySaleInvoice.do" theme="simple" method="post" id="saleForm">
<s:token/>


    <fieldset style="width:95%; padding:10px 0px 10px 10px">
        <legend>Thông tin hóa đơn</legend>
        <table class="inputTbl">
            <%-- Thong tin Hoa don --%>
            <tr>
                <td>
                    <fieldset style="width:100%">
                        <table class="inputTbl">
                            <tr>
                                <td style="width:15%; "></td>
                                <td style="width:25%; "></td>
                                <td style="width:10%; "></td>
                                <td style="width:20%; "></td>
                                <td style="width:15%; "></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>Đơn vị</td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.shopName" id="shopName" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                </td>
                                <td>Người lập</td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.staffName" id="saleForm.staffName" maxlength="1000" readonly="true" cssClass="txtInputFull" theme="simple"/>
                                </td>
                                <td>Ngày lập</td>
                                <td>
                                    <s:textfield name="saleForm.startDate" id="saleForm.startDate" maxlength="1000" readonly="true" cssClass="txtInputFull" theme="simple"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Dải hóa đơn(<font color="red">*</font>)</td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.billSerial" id="saleForm.billSerial" maxlength="1000" readonly="true" cssClass="txtInputFull" theme="simple"/>
                                </td>
                                <td>Từ số hóa đơn</td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.strFromInvoice" id="fromInvoice" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                </td>
                                <td>Đến số hóa đơn</td>
                                <td>
                                    <s:textfield name="saleForm.strToInvoice" id="toInvoice" maxlength="1000" readonly="true" cssClass="txtInputFull" />
                                </td>
                            </tr>
                            <tr>
                                <td>Số HĐ hiện tại</td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.billNum" id="saleForm.billNum" maxlength="1000" readonly="true" cssClass="txtInputFull"/>
                                </td>
                                <td>Số hóa đơn</td>
                                <td colspan="3">
                                    <font color="red" size="4px"><s:label name="saleForm.invoiceNo" id="saleForm.invoiceNo"  ></s:label></font>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </td>
            </tr>
            <%-- Thong tin Khach hang --%>
            <tr>
                <td>
                    <fieldset style="width:100%">
                        <table class="inputTbl">
                            <tr>
                                <td style="width:15%; "></td>
                                <td style="width:25%; "></td>
                                <td style="width:10%; "></td>
                                <td style="width:20%; "></td>
                                <td style="width:15%; "></td>
                                <td></td>
                            </tr>
                            <s:if test="#session.saleTransInvoice.interfaceType == 1">
                                <tr>
                                    <td>Đại lý</td>
                                    <td class="oddColumn">
                                        <s:textfield name="saleForm.agentName" id="agentName" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                    <td>Mã số thuế</td>
                                    <td class="oddColumn">
                                        <s:textfield name="saleForm.tin" id="tin" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Tài khoản</td>
                                    <td class="oddColumn">
                                        <s:textfield name="saleForm.agentAccount" id="account" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                    <td>Địa chỉ </td>
                                    <td  colspan="3">
                                        <s:textfield name="saleForm.address"  id="address" readonly="true"  maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                </tr>
                            </s:if>
                            <s:else>
                                <tr>
                                    <td>Tên khách hàng</td>
                                    <td class="oddColumn">
                                        <s:textfield name="saleForm.custName" id="custName"  readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                    <td>Công ty</td>
                                    <td class="oddColumn">
                                        <s:textfield name="saleForm.company" id="company" readonly="true"  maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                    <td>Mã số thuế</td>
                                    <td>
                                        <s:textfield name="saleForm.tin"  id="tin"  readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Tài khoản</td>
                                    <td class="oddColumn">
                                        <s:textfield name="saleForm.account" id="account"  readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                    <td>Địa chỉ</td>
                                    <td colspan="3">
                                        <s:textfield name="saleForm.address" id="address"  readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                                    </td>
                                </tr>
                            </s:else>
                        </table>
                    </fieldset>
                </td>
            </tr>
            <%-- Thong tin Tong tien --%>
            <tr>
                <td>
                    <fieldset style="width:100%">
                        <table class="inputTbl" >
                            <tr>
                                <td style="width:15%; "></td>
                                <td style="width:25%; "></td>
                                <td style="width:10%; "></td>
                                <td style="width:20%; "></td>
                                <td style="width:15%; "></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>Tiền chưa thuế</td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.amount" readonly="true" id="amount" maxlength="1000" cssClass="txtInputFull" cssStyle="text-align:right;"/>
                                </td>
                                <td>HTTT (<font color="red">*</font>)</td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.payMethod" id="saleForm.payMethod" readonly="true" maxlength="1000" cssClass="txtInputFull" theme="simple"/>
                                </td>
                                <td>Lý do lập HĐ (<font color="red">*</font>)</td>
                                <td >
                                    <s:textfield name="saleForm.reasonName" id="saleForm.reasonName" readonly="true" maxlength="1000" cssClass="txtInputFull" theme="simple"/>
                                </td>

                            </tr>
                            <tr>
                                <td>Tiền thuế</td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.tax" readonly="true" id="tax" maxlength="1000" cssClass="txtInputFull" theme="simple" cssStyle="text-align:right"/>
                                </td>
                                <td>Ghi chú</td>
                                <td colspan="3" rowspan="4"  style="vertical-align:top; height:100%;">
                                    <s:textarea name="saleForm.note" cssStyle="height:100%; "  id="note" cssClass="txtInputFull" readonly="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Chiết khấu</td>
                                <td class="oddColumn">
                                    <s:textfield name="saleForm.discount" readonly="true" id="discount" maxlength="1000" cssClass="txtInputFull"  cssStyle="text-align:right;"/>
                                </td>
                                <td></td>

                            </tr>
                            <tr>
                                <td>Khuyến mãi</td>
                                <td class="oddColumn">
                                    <s:textfield  name="saleForm.promotion" readonly="true" id="promotion" maxlength="1000" cssClass="txtInputFull"  cssStyle="text-align:right;"/>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>Tổng tiền phải trả</td>
                                <td class="oddColumn">
                                    <s:textfield  name="saleForm.amountTax" readonly="true" id="amountTax" maxlength="1000" cssClass="txtInputFull"  cssStyle="text-align:right;"/>
                                </td>
                                <td></td>
                            </tr>
                        </table>
                    </fieldset>
                </td>
            </tr>
        </table>
    </fieldset>



        <%--<fieldset style="width:95%; padding:10px 10px 10px 10px">
                    <legend class="transparent">Thông tin chi tiết về hóa đơn</legend>
                    <s:actionerror/>
                    <table style="width:100%" cellpadding="0" cellspacing="4" border="0">
                        <tr>
                            <td class="label8Col">Người lập</td>
                            <td class="row8Col">
                                <s:textfield name="saleForm.staffName" id="saleForm.staffName" maxlength="1000" readonly="true" cssClass="txtInputFull" theme="simple"/>
                            </td>
                            <td class="label8Col">Ngày lập</td>
                            <td class="row8Col">
                                <s:textfield name="saleForm.startDate" id="saleForm.startDate" maxlength="1000" readonly="true" cssClass="txtInputFull" theme="simple"/>
                            </td>
                        </tr>
                        <tr><td> </td></tr>
                        <tr><td> </td></tr>
                        <tr>
                            <td class="label8Col">Tên khách hàng</td>
                            <td class="row8Col">
                                <s:textfield name="saleForm.custName" id="saleForm.custName" readonly="true" maxlength="1000" cssClass="txtInputFull" theme="simple"/>
                            </td>
                            <td class="label8Col">Công ty</td>
                            <td class="row8Col" colspan="3">
                                <s:textfield name="saleForm.company" id="saleForm.company" readonly="true" maxlength="1000" cssClass="txtInputFull" theme="simple"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="label8Col">Mã số thuế</td>
                            <td class="row8Col">
                                <s:textfield name="saleForm.tin" id="saleForm.tin" readonly="true" maxlength="1000" cssClass="txtInputFull" theme="simple"/>
                            </td>
                            <td class="label8Col">Địa chỉ</td>
                            <td class="row8Col">
                                <s:textfield name="saleForm.address" id="saleForm.address" readonly="true" maxlength="1000" cssClass="txtInputFull" theme="simple"/>
                            </td>
                        </tr>
                        <tr><td> </td></tr>
                        <tr><td> </td></tr>
                        <tr>
                            <td class="label8Col">Ký hiệu hoá đơn</td>
                            <td class="row8Col">
                                <s:textfield name="saleForm.billSerial" id="saleForm.billSerial" maxlength="1000" readonly="true" cssClass="txtInputFull" theme="simple"/>
                            </td>
                            <td class="label8Col">Số HĐ</td>
                            <td class="row8Col">
                                <s:textfield name="saleForm.billNum" id="saleForm.billNum" maxlength="1000" readonly="true" cssClass="txtInputFull" theme="simple"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="label8Col">Chưa thuế</td>
                            <td class="row8Col">
                                <s:textfield name="saleForm.amount" readonly="true" id="amount" maxlength="1000" cssClass="txtInputFull" theme="simple"  cssStyle="text-align:right"/>
                            </td>
                            <td class="label8Col">Tiền thuế</td>
                            <td class="row8Col">
                                <s:textfield name="saleForm.tax" readonly="true" id="tax" maxlength="1000" cssClass="txtInputFull" theme="simple" cssStyle="text-align:right"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="label8Col">Triết khấu</td>
                            <td class="row8Col">
                                <s:textfield name="saleForm.discount" readonly="true" id="discount" maxlength="1000" cssClass="txtInputFull" theme="simple" cssStyle="text-align:right"/>
                            </td>
                            <td class="label8Col">Tổng tiền</td>
                            <td class="row8Col">
                                <s:textfield name="saleForm.amountTax" readonly="true" id="amountTax" maxlength="1000" cssClass="txtInputFull" theme="simple" cssStyle="text-align:right"/>
                            </td>
                        </tr>
                        <tr><td> </td></tr>
                        <tr><td> </td></tr>
                        <tr>
                            <td class="label8Col">HTTT</td>
                            <td class="row8Col">
                                <s:textfield name="saleForm.payMethod" id="saleForm.payMethod" readonly="true" maxlength="1000" cssClass="txtInputFull" theme="simple"/>
                            </td>
                            <td class="label8Col">Lý do</td>
                            <td class="row8Col">
                                <s:textfield name="saleForm.reasonName" id="saleForm.reasonName" readonly="true" maxlength="1000" cssClass="txtInputFull" theme="simple"/>
                            </td>
                        </tr>                                                
                        
                        <tr>
                            <td class="label8Col">Ghi chú</td>
                            <td class="row8Col" colspan="3">
                                <s:textfield name="saleForm.note" readonly="true" id="note" cssClass="txtInputFull" theme="simple" rows="2"/>
                            </td>
                        </tr>
                        <tr><td> </td></tr>
                        <tr><td> </td></tr>
                        <tr><td> </td></tr>
                    </table>                            
                    <div style="width:100%" align="center">
                        <s:submit value="Đóng" onclick="closePopup()"/>
                    </div>
        </fieldset>--%>

    <br/>
    <jsp:include page="invoiceManagementDetailSaleTransList.jsp"/>
    
</s:form>


<script type="text/javascript" language="javascript">
    closePopup = function() {
        window.close();
    }
    textFieldNF($('amount'));
    textFieldNF($('tax'));
    textFieldNF($('amountTax'));
    textFieldNF($('discount'));
    textFieldNF($('promotion'));
</script>
