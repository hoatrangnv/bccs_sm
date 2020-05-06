<%-- 
    Document   : lookupSerialExact
    Created on : Sep 20, 2010, 4:12:41 PM
    Author     : User
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
        <s:form action="lookupSerialAction" theme="simple" method="post" id="lookupSerialForm" >
<s:token/>

            <table class="inputTbl4Col">
                <tr>
                    <td calss="label"> <tags:label key="MSG.GOD.type" required="true"/></td>
                    <td class="text">
                        <tags:imSelect name="lookupSerialForm.stockTypeId"
                                       id="lookupSerialForm.stockTypeId"
                                       cssClass="txtInputFull"
                                       list="listStockType" headerKey="" headerValue="MSG.selectGoodsTypeChooser"
                                       listKey="stockTypeId" listValue="name"
                                       onchange="changeStockType(this.value)"/>
                    </td>
                    <td calss="label"> <tags:label key="MSG.serial.number" required="true"/></td>
                    <td class="text">
                        <s:textfield name="lookupSerialForm.serial" id="lookupSerialForm.serial" maxlength="25" cssClass="txtInputFull"/>
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
                         preAction="lookupSerialAction!lookupSerialExact.do"/>
        </div>
    </div>
</tags:imPanel>
<!-- ket qua tim kiem -->
<div>
    <jsp:include page="listLookupSerialExact.jsp"/>
</div>


<script language="javascript">

    //focus vao truong dau tien
    $('lookupSerialForm.serial').select();
    $('lookupSerialForm.serial').focus();   

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {

var serial = trim($('lookupSerialForm.serial').value);
        
        var stockTypeId = trim($('lookupSerialForm.stockTypeId').value);
        if (stockTypeId= ""){
            $('message').innerHTML = '<s:text name="Pls, select goods type!"/>';
            $('lookupSerialForm.stockTypeId').select();
            $('lookupSerialForm.stockTypeId').focus();
            return false;
        }

        if(serial != "" && !isInteger(trim($('lookupSerialForm.serial').value))) {
//            $('message').innerHTML = '<s_:property value="getError('ERR.FAC.ISDN.032')"/>';
            $('message').innerHTML = '<s:text name="ERR.FAC.ISDN.032"/>';
            $('lookupSerialForm.serial').select();
            $('lookupSerialForm.serial').focus();
            return false;
        }

        if(serial == "") {
//            $('message').innerHTML = '<s_:property value="getError('ERR.FAC.ISDN.033')"/>';
            $('message').innerHTML = '<s:text name="ERR.FAC.ISDN.033"/>';
            $('lookupSerialForm.serial').select();
            $('lookupSerialForm.serial').focus();
            return false;
        }        
        //trim cac truong can thiet
        $('lookupSerialForm.serial').value = trim($('lookupSerialForm.serial').value);
        
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
