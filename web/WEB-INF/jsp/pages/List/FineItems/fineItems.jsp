<%--
    Document   : fineItems
    Created on : Sep 14, 2009, 3:32:52 PM
    Author     : TheTM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listFineItemPrices", request.getSession().getAttribute("listFineItemPrices"));
%>

<script>
    //Su kien click nut xem tat ca
    onchangeclick = function() {
        gotoAction('divListFineItemPrices','finePriceAction!onchangclick.do?checkView=' + $('fineItemForm.checkView').checked+ "&fineItemsId=" + $('fineItemForm.fineItemsId').value + '&' +  token.getTokenParamString());

    }
</script>



<s:if test="#request.fineItemMode == 'prepareAddOrEdit'">
    <s:set var="readonly" value="false" scope="request"/>
</s:if>
<s:else>
    <s:set var="readonly" value="true" scope="request"/>
</s:else>

<sx:div id="sdivFineItem" cssStyle="padding:3px; ">
    <div>
        <tags:displayResult property="message" id="message" type="key"/>
    </div>

    <!-- phan hien thi thong tin ve chiet khau -->
    <div class="divHasBorder">
        <!-- phan hien thi thong tin ve ly do phat -->
        <s:form action="finePriceAction" theme="simple" method="post" id="fineItemForm">
<s:token/>

            <s:hidden name="fineItemForm.fineItemsId" id="fineItemForm.fineItemsId"/>
            <%--            <s:hidden name="fineItemForm.telecomServiceId" id="fineItemForm.telecomServiceId"/>
            --%>            <table class="inputTbl6Col">
                <tr>
                    <td class="label">Loại dịch vụ<font color="red">*</font></td>
                    <td class="text">
                        <s:select name="fineItemForm.telecomServiceId"
                                  id="fineItemForm.telecomServiceId"
                                  cssClass="txtInputFull"
                                  list="%{#request.listTelecomService != null ? #request.listTelecomService : #{}}"
                                  listKey="telecomServiceId" listValue="telServiceName"
                                  headerKey="" headerValue="--Chọn loại dịch vụ--"
                                  disabled="#request.readonly"/>

                    <td class="label">Tên lý do phạt<font color="red">*</font></td>
                    <td class="text">
                        <s:textfield name="fineItemForm.fineItemsName" id="fineItemForm.fineItemsName" maxlength="200" cssClass="txtInputFull" readonly="#request.readonly"/>
                    </td>
                    <td class="label">Trạng thái<font color="red">*</font></td>
                    <td class="text">
                        <s:select name="fineItemForm.status"
                                  id="fineItemForm.status"
                                  cssClass="txtInputFull"
                                  list="#{'1':'Hiệu lực', '0':'Không hiệu lực'}"
                                  headerKey="" headerValue="--Chọn trạng thái--"
                                  disabled="#request.readonly"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">Mô tả</td>
                    <td class="text">
                        <s:textfield name="fineItemForm.description" id="fineItemForm.description" maxlength="500" cssClass="txtInputFull" readonly="#request.readonly"/>
                    </td>
                </tr>

            </table>
            <!-- phan nut tac vu -->
            <s:if test="#request.readonly == false">
                <div align="center" style="vertical-align:top; padding-top:10px;">
                    <sx:submit parseContent="true" executeScripts="true"
                               formId="fineItemForm" loadingText="Đang thực hiện..."
                               cssStyle="width:80px;"
                               showLoadingText="true" targets="divDisplayInfo"
                               value="Đồng ý"  beforeNotifyTopics="finePriceAction/addOrEditFineItems"/>
                    <sx:submit parseContent="true" executeScripts="true"
                               formId="fineItemForm" loadingText="Đang thực hiện..."
                               cssStyle="width:80px;"
                               showLoadingText="true" targets="divDisplayInfo"
                               value="Bỏ qua"  beforeNotifyTopics="finePriceAction/displayFineItems"/>
                </div>

                <script language="javascript">
                    disableTab('sxdivFineItemPrice');
                    //lang nghe, xu ly su kien khi click nut "Dong y"
                    dojo.event.topic.subscribe("finePriceAction/addOrEditFineItems", function(event, widget){
                        if(checkRequiredFields()) {
                            widget.href = "finePriceAction!addOrEditFineItems.do?" +  token.getTokenParamString();
                        } else {
                            event.cancel = true;
                        }
                    });
                    //lang nghe, xu ly su kien khi click nut "Bo qua"
                    dojo.event.topic.subscribe("finePriceAction/displayFineItems", function(event, widget){
                        widget.href = "finePriceAction!displayFineItems.do?" +  token.getTokenParamString();

                    });

                    //ham kiem tra cac truong bat buoc
                    checkRequiredFields = function() {
                         if(trim($('fineItemForm.telecomServiceId').value) == "") {
                            $('message').innerHTML = "!!!Lỗi. Trường loại dịch vụ không được để trống";
                            $('fineItemForm.telecomServiceId').select();
                            $('fineItemForm.telecomServiceId').focus();
                            return false;
                        }

                        //truong ten ly do phat
                        if(trim($('fineItemForm.fineItemsName').value) == "") {
                            $('message').innerHTML = "!!!Lỗi. Trường tên lý do phạt không được để trống";
                            $('fineItemForm.fineItemsName').select();
                            $('fineItemForm.fineItemsName').focus();
                            return false;
                        }

                        if(trim($('fineItemForm.status').value) == "") {
                            $('message').innerHTML = "!!!Lỗi. bạn chưa chọn trạng thái";
                            $('fineItemForm.status').select();
                            $('fineItemForm.status').focus();
                            return false;
                        }
                        if (isHtmlTagFormat(trim($('fineItemForm.fineItemsName').value))){
                            $('message').innerHTML="Trường tên lý do phạt không được nhập định dạng thẻ HTML"
                            $('fineItemForm.fineItemsName').select();
                            $('fineItemForm.fineItemsName').focus()
                            return false;
                        }
                        return true;
                    }
                </script>
            </s:if>
            <s:else>
                <div align="center" style="vertical-align:top; padding-top:10px;">
                    <sx:submit parseContent="true" executeScripts="true"
                               formId="fineItemForm" loadingText="Đang thực hiện..."
                               cssStyle="width:80px;"
                               showLoadingText="true" targets="divDisplayInfo"
                               value="Thêm"  beforeNotifyTopics="finePriceAction/prepareAddFineItems"/>
                    <s:if test="#attr.fineItemForm.fineItemsId.compareTo(0L) > 0">
                        <sx:submit parseContent="true" executeScripts="true"
                                   formId="fineItemForm" loadingText="Đang thực hiện..."
                                   cssStyle="width:80px;"
                                   showLoadingText="true" targets="divDisplayInfo"
                                   value="Sửa"  beforeNotifyTopics="finePriceAction/prepareEditFineItems"/>

                        <sx:submit parseContent="true" executeScripts="true"
                                   formId="fineItemForm" loadingText="Đang thực hiện..."
                                   cssStyle="width:80px;"
                                   showLoadingText="true" targets="divDisplayInfo"
                                   value="Xóa"  beforeNotifyTopics="finePriceAction/delFineItems"/>
                        <%--                        <input type="button" value="Thêm phí" style="width:80px;" onclick="prepareAddFineItemPrice()">
                        --%>                    </s:if>
                    <s:else>
                        <input type="button" value="Sửa" disabled style="width:80px;">
                        <input type="button" value="Xóa" disabled style="width:80px;">
                        <script language="javscript">
                            disableTab('sxdivFineItemPrice');
                        </script>
                    </s:else>
                </div>

                <script language="javascript">
                    enableTab('sxdivFineItemPrice');

                    //lang nghe, xu ly su kien khi click nut "Them"
                    dojo.event.topic.subscribe("finePriceAction/prepareAddFineItems", function(event, widget){
                        widget.href = "finePriceAction!prepareAddFineItems.do?" +  token.getTokenParamString() ;

                    });

                    //lang nghe, xu ly su kien khi click nut "Sua"
                    dojo.event.topic.subscribe("finePriceAction/prepareEditFineItems", function(event, widget){
                        widget.href = "finePriceAction!prepareEditFineItems.do?" +  token.getTokenParamString();

                    });

                    //lang nghe, xu ly su kien khi click nut "Xoa"
                    dojo.event.topic.subscribe("finePriceAction/delFineItems", function(event, widget){
                        if (confirm('Bạn có chắc chắn muốn xóa lý do này không?')) {
                            widget.href = "finePriceAction!delFineItems.do?" +  token.getTokenParamString();
                        } else {
                            event.cancel = true;
                        }

                    });

                </script>
            </s:else>
        </s:form>
    </div>
</sx:div>

<script language="javascript">
    //bat popup them price moi
    <%-- prepareAddFineItemPrice = function() {
         //openPopup("${contextPath}/goodsDefAction!prepareAddPrice.do", 750, 700);
         openDialog("${contextPath}/finePriceAction!prepareAddFineItemPrice.do", 750, 700, true);
     }--%>

         //cap nhat lai danh sach gia
         refreshListFineItemPrice = function() {
             gotoAction('divListFineItemPrices','finePriceAction!refreshListFineItemPrice.do?' +  token.getTokenParamString());
         }

</script>
