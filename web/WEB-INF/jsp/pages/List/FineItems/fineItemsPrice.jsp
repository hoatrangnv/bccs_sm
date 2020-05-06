<%--
    Document   : fineItemsPrice
    Created on : Sep 14, 2009, 4:18:47 PM
    Author     : TheTM
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>


<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="sdivFinePrice" cssStyle="padding:3px; ">
    <div>
        <tags:displayResult property="message" id="message" type="key"/>
    </div>

    <!-- phan hien thi thong tin ve chiet khau -->
    <div class="divHasBorder">
        <s:form action="finePriceAction" theme="simple" method="post" id="fineItemsPriceForm">
<s:token/>

            <s:hidden name="fineItemsPriceForm.fineItemId" id="fineItemsPriceForm.fineItemId"/>
            <s:hidden name="fineItemsPriceForm.fineItemPriceId" id="fineItemsPriceForm.fineItemPriceId"/>
            <table class="inputTbl6Col">
                <tr>
                    <td class="label">Phí<font color="red">*</font></td>
                    <td class="text">
                        <s:textfield name="fineItemsPriceForm.price" id="fineItemsPriceForm.price" readonly="#session.isEditFinePrice==1" onkeyup="textFieldNF(this)" maxlength="15" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">VAT<font color="red">*</font></td>
                    <td class="text">
                        <s:textfield name="fineItemsPriceForm.vat" id="fineItemsPriceForm.vat" readonly="#session.isEditFinePrice==1" maxlength="15" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">Mô tả</td>
                    <td class="text">
                        <s:textfield name="fineItemsPriceForm.description" id="fineItemsPriceForm.description" maxlength="500" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">Ngày bắt đầu<font color="red">*</font></td>
                    <td class="text">
                        <tags:dateChooser id="fineItemsPriceForm.staDate" property="fineItemsPriceForm.staDate" styleClass="txtInputFull"/>
                    </td>
                    <td class="label">Ngày kết thúc</td>
                    <td class="text">
                        <tags:dateChooser property="fineItemsPriceForm.endDate" styleClass="txtInputFull"/>
                    </td>
                    <td class="label">Trạng thái<font color="red">*</font></td>
                    <td class="text">
                        <s:select name="fineItemsPriceForm.status"
                                  id="fineItemsPriceForm.status"
                                  cssClass="txtInputFull"
                                  list="#{'1':'Hiệu lực', '0':'Không hiệu lực'}"
                                  headerKey="" headerValue="--Chọn trạng thái--"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <s:checkbox name="fineItemsPriceForm.checkStatus"
                                    id="fineItemsPriceForm.checkStatus"
                                    onclick="displayListFineItemPrice();"/>
<%--                        <s:hidden name="fineItemsPriceForm.checkStatus" id="fineItemsPriceForm.checkStatus"/>
--%>                    </td>
                    <td>
                        Hiển thị tất cả
                    </td>
                </tr>
            </table>
            <!-- phan nut tac vu, dong popup -->
            <%-- <div align="center" style="padding-top:5px;">
                 <button style="width:80px;" onclick="addFineItemsPrice()">Đồng ý</button>
                 <button style="width:80px;" onclick="cancelAddFineItemsPrice()">Bỏ qua</button>
             </div>--%>
            <s:if test="#session.isEditFinePrice == 1">
                <div align="center" style="padding-top:5px;">
                    <sx:submit parseContent="true" executeScripts="true"
                               formId="fineItemsPriceForm" loadingText="Đang thực hiện..."
                               cssStyle="width:80px;"
                               showLoadingText="true" targets="sxdivFineItemPrice"
                               value="Đồng ý"  beforeNotifyTopics="finePriceAction/addOrEditFineItemsPrice"/>
                    <sx:submit parseContent="true" executeScripts="true"
                               formId="fineItemsPriceForm" loadingText="Đang thực hiện..."
                               cssStyle="width:80px;"
                               showLoadingText="true" targets="divDisplayInfo"
                               value="Bỏ qua"  beforeNotifyTopics="finePriceAction/cancel"/>
                </div>
            </s:if>
            <s:else>
                <div align="center" style="padding-top:5px;">
                    <sx:submit parseContent="true" executeScripts="true"
                               formId="fineItemsPriceForm" loadingText="Đang thực hiện..."
                               cssStyle="width:80px;"
                               showLoadingText="true" targets="sxdivFineItemPrice"
                               value="Thêm mới"  beforeNotifyTopics="finePriceAction/addOrEditFineItemsPrice"/>
                </div>
            </s:else>

        </s:form>

    </div>


    <%--Danh sach phi ly do phat
    --%>
    <jsp:include page="listFineItemPrice.jsp"/>
</sx:div>



<%--<s:if test="#request.priceMode == 'closePopup'">

    <script language="javascript">
        window.opener.refreshListFineItemPrice();
        window.close();
    </script>

</s:if>--%>

<script language="javascript">
    dojo.event.topic.subscribe("finePriceAction/addOrEditFineItemsPrice", function(event, widget){
<%--        if(checkRequiredFields() && checkValidFields()) {
--%>            widget.href = "finePriceAction!addOrEditFineItemsPrice.do"+"?"+ token.getTokenParamString();
<%-- } else {
     event.cancel = true;
 }--%>
    });
    dojo.event.topic.subscribe("finePriceAction/cancel", function(event, widget){
        widget.href = "finePriceAction!displayFineItems.do?" +  token.getTokenParamString();

    });


    //dinh danh cho truong price
    textFieldNF($('fineItemsPriceForm.price'));


    <%--addFineItemsPrice = function() {
        if(checkRequiredFields() && checkValidFields()) {
            $(fineItemsPriceForm).submit();
        }
    }--%>

        cancelAddFineItemsPrice = function() {
            window.close();
        }

        <%--//ham kiem tra cac truong bat buoc
        checkRequiredFields = function() {
            if(trim($('fineItemsPriceForm.price').value) == "") {
                $('message').innerHTML = "!!!Lỗi. Trường giá không được để trống";
                $('fineItemsPriceForm.price').select();
                $('fineItemsPriceForm.price').focus();
                return false;
            }
            if(trim($('fineItemsPriceForm.vat').value) == "") {
                $('message').innerHTML = "!!!Lỗi. VAT không được để trống ";
                $('fineItemsPriceForm.vat').select();
                $('fineItemsPriceForm.vat').focus();
                return false;
            }
            var staDate = dojo.widget.byId("fineItemsPriceForm.staDate");
            if(trim(staDate.domNode.childNodes[1].value) == "") {
                $('message').innerHTML = "!!!Lỗi. Ngày bắt đầu không được để trống";
                staDate.domNode.childNodes[1].select();
                staDate.domNode.childNodes[1].focus();
                return false;
            }
            if(trim($('fineItemsPriceForm.status').value) == "") {
                $('message').innerHTML = "!!!Lỗi. bạn chưa chọn trạng thái";
                $('fineItemsPriceForm.status').focus();
                return false;
            }
        
            return true;
        }

        //ham kiem tra du lieu cac truong co hop le hay ko
        checkValidFields = function() {
            var price = $('fineItemsPriceForm.price').value.replace(/\,/g,""); //loai bo dau , trong chuoi
            if(!isInteger(trim(price))) {
                $('message').innerHTML = "!!!Lỗi. Phí phải là số nguyên dương";
                $('fineItemsPriceForm.price').select();
                $('fineItemsPriceForm.price').focus();
                return false;
            }
            if(!isInteger(trim($('fineItemsPriceForm.vat').value))) {
                $('message').innerHTML = "!!!Lỗi. VAT phải là số nguyên";
                $('fineItemsPriceForm.vat').select();
                $('fineItemsPriceForm.vat').focus();
                return false;
            }
            if($('fineItemsPriceForm.vat').value < 0 || $('fineItemsPriceForm.vat').value > 100) {
                $('message').innerHTML = "!!!Lỗi. VAT phải nằm trong khoảng từ 0 đến 100";
                $('fineItemsPriceForm.vat').select();
                $('fineItemsPriceForm.vat').focus();
                return false;
            }

            //
            $('fineItemsPriceForm.price').value = trim($('fineItemsPriceForm.price').value);
            $('fineItemsPriceForm.vat').value = trim($('fineItemsPriceForm.vat').value);
            $('fineItemsPriceForm.description').value = trim($('fineItemsPriceForm.description').value);

            return true;
        }
--%>
        // var checkStatus=document.getElementById(fineItemForm.checkStatus);
        displayListFineItemPrice = function() {
            gotoAction('divListFineItemPrices','finePriceAction!displayListFineItemPrice.do?checkStatus='+$('fineItemsPriceForm.checkStatus').checked + '&'+  token.getTokenParamString());

        }



</script>
