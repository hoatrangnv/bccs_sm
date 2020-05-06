<%-- 
    Document   : invoiceUsedDetail
    Created on : Apr 7, 2009, 10:33:01 AM
    Author     : TungTV
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


        <fieldset style="width:95%; padding:10px 10px 10px 10px">
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
        </fieldset>

    <br/>
    <jsp:include page="invoiceDestroySaleTransDetail.jsp"/>
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
