<%-- 
    Document   : lookupSerial
    Created on : Jun 21, 2009, 10:02:25 AM
    Author     : tamdt1
    Desc       : nghiep vu tra cuu serial
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.serial.find.info">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi thong tin can tra cuu -->
    <div class="divHasBorder">
        <s:form action="lookupSerialAction" theme="simple" method="post" id="lookupSerialForm">
<s:token/>

            <table class="inputTbl6Col">
                <tr>
                    <td calss="label"> <tags:label key="MSG.GOD.type" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="lookupSerialForm.stockTypeId"
                                       id="lookupSerialForm.stockTypeId"
                                       cssClass="txtInputFull"
                                       list="listStockType" headerKey="" headerValue="--Select Goods Type--"
                                       listKey="stockTypeId" listValue="name"
                                       onchange="changeStockType(this.value)"/>
                    </td>
                    <td calss="label"> <tags:label key="MSG.range.serial" required="true"/></td>
                    <td colspan="3">
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:textfield name="lookupSerialForm.fromSerial" id="lookupSerialForm.fromSerial" maxlength="25" cssClass="txtInputFull"/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:textfield name="lookupSerialForm.toSerial" id="lookupSerialForm.toSerial" maxlength="25" cssClass="txtInputFull" onfocus="focusOnToSerial();"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td calss="label"> <tags:label key="MSG.generates.shop_type"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="lookupSerialForm.ownerType"
                                       id="lookupSerialForm.ownerType"
                                       cssClass="txtInputFull"
                                       list="1:MSG.shop.stock,2:MSG.staff.stock"
                                       headerKey="" headerValue="MSG.FAC.ChooseGroupType"/>
                    </td>
                    <td calss="label"> <tags:label key="MSG.generates.unit_code"/></td>


                    <td colspan="3">
                        <tags:imSearch codeVariable="lookupSerialForm.shopCode" nameVariable="lookupSerialForm.shopName"
                                       codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                       searchClassName="com.viettel.im.database.DAO.LookupSerialDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getNameShop"
                                       otherParam="lookupSerialForm.ownerType"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:13%; "><tags:label key="MSG.generates.goods"/></td>
                    <td class="oddColumn" style="width:20%; ">
                        <tags:imSelect name="lookupSerialForm.stockModelId"
                                       id="lookupSerialForm.stockModelId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.023"
                                       list="listStockModel"
                                       listKey="stockModelId" listValue="name"/>
                    </td>
                    <td style="width:13%; "><tags:label key="MSG.generates.goods.state"/></td>
                    <td class="oddColumn" style="width:20%; ">
                        <tags:imSelect name="lookupSerialForm.stateId"
                                       id="lookupSerialForm.stateId"
                                       cssClass="txtInputFull"
                                       list="3:MSG.GOD.error,1:MSG.GOD.new"
                                       headerKey="" headerValue="MSG.generates.goods.state"/>
                    </td>
                    <td style="width:13%; "><tags:label key="MSG.generates.goods.status"/></td>
                    <td>
                        <tags:imSelect name="lookupSerialForm.status"
                                       id="lookupSerialForm.status"
                                       cssClass="txtInputFull"
                                       list="1:L.200018,0:L.200017,3:L.200020,6:L.200023,14:L.200031,16:L.200033"
                                       headerKey="" headerValue="MSG.generates.goods.status"/>
                    </td>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <div align="center" style="padding-top:3px;">
            <!--sx:submit parseContent="true" executeScripts="true"
                       formId="lookupSerialForm" loadingText="Đang thực hiện..."
                       cssStyle="width:80px;"
                       showLoadingText="true" targets="bodyContent"
                       value="Tìm kiếm"  beforeNotifyTopics="lookupSerialAction/lookupSerial"/-->
            <tags:submit formId="lookupSerialForm"
                         validateFunction="checkValidFields();"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.SAE.138"
                         preAction="lookupSerialAction!lookupSerial.do"/>
        </div>
    </div>
</tags:imPanel>
<!-- ket qua tim kiem -->
<div>
    <jsp:include page="listLookupSerial.jsp"/>
</div>


<script language="javascript">

    //focus vao truong dau tien
    $('lookupSerialForm.fromSerial').select();
    $('lookupSerialForm.fromSerial').focus();


    //xu ly su kienn focus tren textBox toIsdn
    focusOnToSerial = function () {
        var fromSerial = trim($('lookupSerialForm.fromSerial').value);
        var toSerial = trim($('lookupSerialForm.toSerial').value);
        if(toSerial == "") {
            $('lookupSerialForm.toSerial').value = fromSerial;
        }
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {

        var stockTypeId = trim($('lookupSerialForm.stockTypeId').value);
        if (stockTypeId= ""){
            $('message').innerHTML = '<s:text name="Pls, select goods type!"/>';
            $('lookupSerialForm.stockTypeId').select();
            $('lookupSerialForm.stockTypeId').focus();
            return false;
        }

        
        var fromSerial = trim($('lookupSerialForm.fromSerial').value);
        var toSerial = trim($('lookupSerialForm.toSerial').value);

        if(fromSerial != "" && !isInteger(trim($('lookupSerialForm.fromSerial').value))) {
            //            $('message').innerHTML = '<s_:property value="getError('ERR.FAC.ISDN.001')"/>';
            $('message').innerHTML = '<s:text name="ERR.FAC.ISDN.001"/>';
            $('lookupSerialForm.fromSerial').select();
            $('lookupSerialForm.fromSerial').focus();
            return false;
        }
        if(toSerial != "" && !isInteger(trim($('lookupSerialForm.toSerial').value))) {
            //            $('message').innerHTML = '<s_:property value="getError('ERR.FAC.ISDN.002')"/>';
            $('message').innerHTML = '<s:text name="ERR.FAC.ISDN.002"/>';
            $('lookupSerialForm.toSerial').select();
            $('lookupSerialForm.toSerial').focus();
            return false;
        }

        if(fromSerial == "") {
            //            $('message').innerHTML = '<s_:property value="getError('ERR.STK.028')"/>';
            $('message').innerHTML = '<s:text name="ERR.STK.028"/>';
            $('lookupSerialForm.fromSerial').select();
            $('lookupSerialForm.fromSerial').focus();
            return false;
        }

        if(toSerial == "") {
            //            $('message').innerHTML = '<s_:property value="getError('ERR.STK.029')"/>';
            $('message').innerHTML = '<s:text name="ERR.STK.029"/>';
            $('lookupSerialForm.toSerial').select();
            $('lookupSerialForm.toSerial').focus();
            return false;
        }


        if(fromSerial != "" && toSerial != "" && (fromSerial * 1 > toSerial * 1)) {
            //            $('message').innerHTML = '<s_:property value="getError('ERR.FAC.ISDN.003')"/>';
            $('message').innerHTML = '<s:text name="ERR.FAC.ISDN.003"/>';
            $('lookupSerialForm.fromSerial').select();
            $('lookupSerialForm.fromSerial').focus();
            return false;
        }


        if((toSerial * 1 - fromSerial * 1) > 999) {
            //            $('message').innerHTML = '<s_:property value="getError('ERR.SAE.059')"/>' + ' 1,000 serial';
            $('message').innerHTML = '<s:text name="ERR.SAE.059"/>'  + ' 1,000 serial';
            $('lookupSerialForm.fromSerial').select();
            $('lookupSerialForm.fromSerial').focus();
            return false;
        }

        if(toSerial  == fromSerial ) {
            //            $('message').innerHTML = '<s_:property value="getError('ERR.SAE.149')"/>';
            $('message').innerHTML = '<s:text name="ERR.SAE.149"/>';
            $('lookupSerialForm.fromSerial').select();
            $('lookupSerialForm.fromSerial').focus();
            return false;
        }


        //trim cac truong can thiet
        $('lookupSerialForm.fromSerial').value = trim($('lookupSerialForm.fromSerial').value);
        $('lookupSerialForm.toSerial').value = trim($('lookupSerialForm.toSerial').value);
        /*
        var _myWidget = dojo.widget.byId("lookupSerialForm.shopCode");
        alert('>' + _myWidget.textInputNode.value + '<');
        _myWidget.textInputNode.value = trim(_myWidget.textInputNode.value);
        alert('>' + _myWidget.textInputNode.value + '<');*/


        return true;
    }

    /*
     * cmt de thay the nut sub mit
        //lang nghe, xu ly su kien khi click nut "Tim kiem"
        dojo.event.topic.subscribe("lookupSerialAction/lookupSerial", function(event, widget){
            widget.href = "lookupSerialAction!lookupSerial.do";
        });
     */

    //xu ly su kien sau khi chon 1 ma cua hang/ NV
    dojo.event.topic.subscribe("lookupSerialAction/getShopName", function(value, key, text, widget){
        if (key != undefined) {
            getInputText("lookupSerialAction!getShopName.do?target=lookupSerialForm.shopName&shopCode=" + value);
        } else {
            $('lookupSerialForm.shopName').value = "";
        }
    });

    //cap nhat lai bien session current owerType
    /*
    changeOwnerType = function(ownerType) {
        updateData("${contextPath}/lookupSerialAction!changeOwnerType.do?target=lookupSerialForm.ownerType_hidden&ownerType=" + ownerType);
    }*/

    //cap nhat lai danh sach mat hang
    changeStockType = function(stockTypeId) {
        updateData("${contextPath}/lookupSerialAction!changeStockType.do?target=lookupSerialForm.stockModelId&stockTypeId=" + stockTypeId);
    }

</script>
