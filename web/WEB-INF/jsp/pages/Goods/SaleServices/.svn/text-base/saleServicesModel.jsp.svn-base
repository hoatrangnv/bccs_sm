<%-- 
    Document   : saleServicesModel
    Created on : Jun 8, 2009, 10:28:01 AM
    Author     : tamdt1
    Desc       : them 1 loai mat hang moi vao dich vu ban hang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="divSaleServicesModel" cssStyle="padding:3px; ">
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="messageSaleServicesModel" property="message" type="key"/>
    </div>

    <!-- phan thong tin loai mat hang -->
    <div class="divHasBorder">
        <s:form theme="simple" action="saleServicesAction!addOrEditSaleServicesModel.do" id="saleServicesModelForm" method="post">
<s:token/>

            <s:hidden name="saleServicesModelForm.saleServicesModelId" id="saleServicesModelForm.saleServicesModelId"/>
            <s:hidden name="saleServicesModelForm.saleServicesId" id="saleServicesModelForm.saleServicesId"/>

            <s:hidden name="saleServicesForm.shopId" id="saleServicesForm.shopId"/>

            <table class="inputTbl6Col">
                <tr>
                    <td style="width:15%; ">
                        <tags:label key="MSG.GOD.027" required="${fn:escapeXml(requestScope.isAddMode)}"/>
                        <!--                        Loại mặt hàng-->
                    </td>
                    <td class="oddColumn" style="width:25%; ">
                        <tags:imSelect name="saleServicesModelForm.stockTypeId"
                                       id="stockTypeId"
                                       cssClass="txtInputFull"
                                       disabled="${!fn:escapeXml(requestScope.isAddMode)}"
                                       headerKey="" headerValue="MSG.GOD.216"
                                       list="listStockTypes"
                                       listKey="stockTypeId" listValue="name"/>

                    </td>
                    <td rowspan="5" style="vertical-align:top">
                        <table style="width:100%; ">
                            <tr>
                                <td>
                                    <tags:label key="MSG.GOD.108"/>
                                    <!--                                    Kho chức năng-->
                                </td>
                                <td>
                                    <sx:autocompleter name="saleServicesForm.shopCode"
                                                      id="saleServicesForm.shopCode"
                                                      cssClass="txtInputFull"
                                                      disabled="%{!(#request.isAddMode || #request.isEditMode)}"
                                                      href="saleServicesAction!getDataForShopAutocompleter.do"
                                                      loadOnTextChange="true"
                                                      loadMinimumCount="1"
                                                      valueNotifyTopics="saleServicesAction/updateShopNameTextbox"/>
                                </td>
                                <td>
                                    <s:textfield name="saleServicesForm.shopName" id="saleServicesForm.shopName" maxlength="100" readonly="true" cssClass="txtInputFull"/>
                                </td>
                                <td align="center" style="width:15%; vertical-align:middle; text-align:right">
                                    <!--                                    <input type="button" value="Thêm" onclick="addShopId()" style="width:60px">-->
                                    <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.019'))}" onclick="addShopId()" style="width:60px">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" rowspan="4">
                                    <tags:imSelect name="saleServicesModelForm.arrSelectedShopId"
                                                   id="saleServicesModelForm.arrSelectedShopId"
                                                   cssStyle="width:100%; height:90px;"
                                                   multiple="true" size="8" headerKey="" headerValue=""
                                                   disabled="${!(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"
                                                   list="listSelectedShopId"
                                                   listKey="shopId" listValue="shopCodeAndName"
                                                   ondblclick="deleteShopId()"/>
                                </td>
                                <td align="center" style="width:15%; vertical-align:middle; text-align:right">
                                    <!--                                    <input type="button" value="Xóa" onclick="deleteShopId()" style="width:60px">-->
                                    <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))}" onclick="deleteShopId()" style="width:60px">
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <tags:label key="MSG.GOD.081" required="${fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode)}"/>
                        <!--                        Kiểu trừ kho081-->
                    </td>
                    <td class="oddColumn">
                        <tags:imSelect name="saleServicesModelForm.updateStock"
                                       id="saleServicesModelForm.updateStock"
                                       cssClass="txtInputFull"
                                       disabled="${!(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"
                                       headerKey="" headerValue="MSG.GOD.306"
                                       list="0:MSG.GOD.307, 1:MSG.GOD.308"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <tags:label key="MSG.GOD.031"/>
                        <!--                        Ghi chú-->
                    </td>
                    <td class="oddColumn">
                        <s:textfield name="saleServicesModelForm.notes" id="saleServicesModelForm.notes"
                                     maxlength="500" cssClass="txtInputFull"
                                     readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="oddColumn">
                        <s:checkbox name="saleServicesModelForm.checkShopStock" 
                                    id="saleServicesModelForm.checkShopStock"
                                    disabled="%{!(#request.isAddMode || #request.isEditMode)}"/>
                        ${fn:escapeXml(imDef:imGetText('MSG.GOD.109'))}
                        <!--                        Lấy hàng từ kho đơn vị-->
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="oddColumn">
                        <s:checkbox name="saleServicesModelForm.checkStaffStock" 
                                    id="saleServicesModelForm.checkStaffStock"
                                    disabled="%{!(#request.isAddMode || #request.isEditMode)}"/>
                        ${fn:escapeXml(imDef:imGetText('MSG.GOD.110'))}
                        <!--                        Lấy hàng từ kho nhân viên-->
                    </td>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <s:if test="#request.isAddMode || #request.isEditMode">
            <div align="center" style="vertical-align:top">
                <sx:submit parseContent="true" executeScripts="true"
                           formId="saleServicesModelForm" loadingText="Processing..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="divSaleServicesModel"
                           key="MSG.GOD.016"  beforeNotifyTopics="saleServicesAction/addOrEditSaleServicesModel"/>
                <sx:submit parseContent="true" executeScripts="true"
                           formId="saleServicesModelForm" loadingText="Processing..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="divSaleServicesModel"
                           key="MSG.GOD.018" beforeNotifyTopics="saleServicesAction/cancelAddSaleServicesModel"/>
            </div>
            <script language="javscript">
                disableTab('sxdivSaleServices');
                disableTab('sxdivSaleServicesPrice');
            </script>
        </s:if>
        <s:else>
            <div align="center" style="vertical-align:top">
                <sx:submit parseContent="true" executeScripts="true"
                           formId="saleServicesModelForm" loadingText="Processing..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="divSaleServicesModel"
                           key="MSG.GOD.019"  beforeNotifyTopics="saleServicesAction/prepareAddSaleServicesModel"/>
                <s:if test="saleServicesModelForm.saleServicesModelId.compareTo(0L) > 0">
                    <sx:submit parseContent="true" executeScripts="true"
                               formId="saleServicesModelForm" loadingText="Processing..."
                               cssStyle="width:80px;"
                               showLoadingText="true" targets="divSaleServicesModel"
                               key="MSG.GOD.020"  beforeNotifyTopics="saleServicesAction/prepareEditSaleServicesModel"/>
                    <sx:submit parseContent="true" executeScripts="true"
                               formId="saleServicesModelForm" loadingText="Processing..."
                               cssStyle="width:80px;"
                               showLoadingText="true" targets="divSaleServicesModel"
                               key="MSG.GOD.021"  beforeNotifyTopics="saleServicesAction/delSaleServicesModel"/>
                </s:if>
                <s:else>
                    <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.020'))}" disabled style="width:80px;"/>
                    <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))}" disabled style="width:80px;"/>
                    
<!--                    <input type="button" value="Sửa" disabled style="width:80px;"/>
                    <input type="button" value="Xóa" disabled style="width:80px;"/>-->
                </s:else>
            </div>
            <script language="javscript">
                enableTab('sxdivSaleServices');
                enableTab('sxdivSaleServicesPrice');
            </script>
        </s:else>
    </div>

    <!-- danh sach gia dich vu ban hang -->
    <div>
        <jsp:include page="listSaleServicesModels.jsp" />
    </div>

</sx:div>

<script language="javascript">

    //focus vao truong dau tien
    $('stockTypeId').focus();

    //lang nghe, xu ly su kien khi click nut "Dong y"
    dojo.event.topic.subscribe("saleServicesAction/addOrEditSaleServicesModel", function(event, widget){
        if(checkRequiredFields() && checkValidFields()) {
            //chon tat ca cac phan tu trong list truoc khi post
            var cb = $('saleServicesModelForm.arrSelectedShopId');
            for(var i = 0; i < cb.length; i++ ) {
                cb.options[i].selected = true;
            }

                    widget.href = "saleServicesAction!addOrEditSaleServicesModel.do?" + token.getTokenParamString();
        } else {
            event.cancel = true;
        }
    });

    //lang nghe, xu ly su kien khi click nut "Bo qua"
    dojo.event.topic.subscribe("saleServicesAction/cancelAddSaleServicesModel", function(event, widget){
        widget.href = "saleServicesAction!displaySaleServicesModel.do";
    });

    //lang nghe, xu ly su kien khi click nut "Them"
    dojo.event.topic.subscribe("saleServicesAction/prepareAddSaleServicesModel", function(event, widget){
        widget.href = "saleServicesAction!prepareAddSaleServicesModel.do";
    });

    //lang nghe, xu ly su kien khi click nut "Sua"
    dojo.event.topic.subscribe("saleServicesAction/prepareEditSaleServicesModel", function(event, widget){
        widget.href = "saleServicesAction!prepareEditSaleServicesModel.do";
    });

    //lang nghe, xu ly su kien khi click nut "Xoa"
    dojo.event.topic.subscribe("saleServicesAction/delSaleServicesModel", function(event, widget){
//        if (confirm('Bạn có chắc chắn muốn xóa giá DVBH này không?')) {
//        var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.GOD.111')"/>');
        var strConfirm = getUnicodeMsg('<s:text name="MSG.GOD.111"/>');
        if (confirm(strConfirm)) {
            widget.href = "saleServicesAction!delSaleServicesModel.do?" + token.getTokenParamString();
        } else {
            event.cancel = true;
        }

    });

    //ham xu ly su kien khi dong cua so popup sau khi them saleServicesModel
    refreshListSaleServicesModel = function() {
        gotoAction('divListSaleServicesModels','saleServicesAction!refreshListSaleServicesModel.do');
    }

    //them 1 shopId vao danh sach cac shopId duoc chon
    addShopId = function() {
        var shopCode = dojo.widget.byId("saleServicesForm.shopCode").textInputNode.value;
        var shopId = dojo.widget.byId("saleServicesForm.shopCode").domNode.childNodes[1].value;
        var shopName = $("saleServicesForm.shopName").value;
        //var shopId = $("saleServicesForm.shopId").value;

        if(shopId == null || shopId <= 0) {
//            $('messageSaleServicesModel').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.035')"/>';
            $('messageSaleServicesModel').innerHTML = '<s:text name="ERR.GOD.035"/>';
        } else {
            var cb = $('saleServicesModelForm.arrSelectedShopId');

            //kiem tra node da ton tai trong list hay chua
            var ops = cb.options;
            var i;
            var isDuplicate = false;
            for(i = 0; i < ops.length; i++) {
                if(ops[i].value == shopId) {
                    isDuplicate = true;
                    break;
                }
            }

            if(!isDuplicate){
                var firstOp = cb.firstChild;
                var newOp = document.createElement('option');
                newOp.value = shopId;
                newOp.text = shopCode + ' - ' + shopName;
                newOp.innerHTML = shopCode + ' - ' + shopName;

                cb.insertBefore( newOp, firstOp );
            } else {
//                $('messageSaleServicesModel').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.036')"/>';
                $('messageSaleServicesModel').innerHTML = '<s:text name="ERR.GOD.036"/>';
            }
        }
    }

    //loai bo shopId khoi danh sach cac kho duoc chon
    deleteShopId = function() {
        var cb = $('saleServicesModelForm.arrSelectedShopId');
        var needToRemovedOps = [];
        var ops = cb.options;
        var i;
        for(i = 0; i < ops.length; i++) {
            if(ops[i].selected == true) {
                needToRemovedOps.push(ops[i]);
            }
        }

        for(i = 0; i < needToRemovedOps.length; i++) {
            cb.removeChild( needToRemovedOps[i] );
        }
    }

    //
    dojo.event.topic.subscribe("saleServicesAction/updateShopNameTextbox", function(value, key, text, widget){
        if (key != undefined) {
            $('saleServicesForm.shopId').value = key;
            getInputText('${contextPath}/saleServicesAction!updateShopName.do?target=saleServicesForm.shopName&shopId=' + key);
        } else {
            $('saleServicesForm.shopName').value = "";
        }
    });

    //them saleServicesModel moi
    addSaleServicesModel = function() {
        if(checkRequiredFields() && checkValidFields()) {
            //chon tat ca cac phan tu trong list truoc khi post
            var cb = $('saleServicesModelForm.arrSelectedShopId');
            for(var i = 0; i < cb.length; i++ ) {
                cb.options[i].selected = true;
            }

            //submit form
            $('saleServicesModelForm').submit();
        }

    }

    cancelAddSaleServicesModel = function() {
        window.close();
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
//        alert("x");
        if(trim($('stockTypeId').value) == "") {
//            $('message').innerHTML="!!!Lỗi. Chọn loại mặt hàng";
//            $('messageSaleServicesModel').innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.033')"/>';
            $('messageSaleServicesModel').innerHTML = '<s:text name="MSG.GOD.033"/>';
            $('stockTypeId').focus();
            return false;
        }
        if(trim($('saleServicesModelForm.updateStock').value) == "") {
//            $('message').innerHTML="!!!Lỗi. Chọn kiểu trừ kho";
//            $('messageSaleServicesModel').innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.113')"/>';
            $('messageSaleServicesModel').innerHTML = '<s:text name="MSG.GOD.113"/>';
            $('saleServicesModelForm.updateStock').focus();
            return false;
        }

        return true;
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
//    alert("y");
        if(isHtmlTagFormat(trim($('saleServicesModelForm.notes').value))) {
//            $('message').innerHTML="!!!Lỗi. Trường ghi chú không được chứa các thẻ HTML";
//            $('messageSaleServicesModel').innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.101')"/>';
            $('messageSaleServicesModel').innerHTML = '<s:text name="MSG.GOD.101"/>';
            $('saleServicesModelForm.notes').select();
            $('saleServicesModelForm.notes').focus();
            return false;
        }

        if($('saleServicesModelForm.notes').value.length > 50) {
//            $('message').innerHTML="!!!Lỗi. Trường ghi chú quá dài";
//            $('messageSaleServicesModel').innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.102')"/>';
            $('messageSaleServicesModel').innerHTML = '<s:text name="MSG.GOD.102"/>';
            $('saleServicesModelForm.notes').select();
            $('saleServicesModelForm.notes').focus();
            return false;
        }

        return true;
    }

</script>


