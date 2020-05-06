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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.serial.find.info">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <s:form action="activeCard" theme="simple" method="post" id="activeCardForm">
<s:token/>

        <!-- phan hien thi thong tin can tra cuu -->
        <div class="divHasBorder">
            <table class="inputTbl4Col">
                <tr>
                    <td class="label">
                        <tags:label key="MSG.store.code"/>
                    </td>
                    <td class="text" colspan="2">

                        <tags:imSearch codeVariable="activeCardForm.shopCode" nameVariable="activeCardForm.shopName"
                                       codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       elementNeedClearContent="activeCardForm.staffCode;activeCardForm.staffName"
                                       getNameMethod="getShopName"
                                       roleList="IM_UTIL_ACT_CARD_SHOP"/>
                    </td>
                    <td class="label">&nbsp;</td>

                </tr>
                <tr>
                    <td class="label">
                        <tags:label key="MSG.stock.staff.code"/>
                    </td>
                    <td class="text" colspan="2">

                        <tags:imSearch codeVariable="activeCardForm.staffCode" nameVariable="activeCardForm.staffName"
                                       codeLabel="MSG.stock.staff.code" nameLabel="MSG.staffName"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStaff"
                                       otherParam="activeCardForm.shopCode"
                                       getNameMethod="getStaffName"
                                       roleList="IM_UTIL_ACT_CARD_STAFF"/>

                    </td>
                    <td class="label">&nbsp;</td>

                </tr>
                <tr>

                    <td><tags:label key="MSG.range.serial" /></td>
                    <td class="text" colspan="2">
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:textfield name="activeCardForm.fromSerial" id="activeCardForm.fromSerial" maxlength="25" cssClass="txtInputFull"/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:textfield name="activeCardForm.toSerial" id="activeCardForm.toSerial" maxlength="25" cssClass="txtInputFull" onfocus="focusOnToSerial();"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td class="label">&nbsp;</td>
                </tr>
            </table>


            <!-- phan nut tac vu -->
            <div align="center" style="padding-top:3px;">
                <!--sx:submit parseContent="true" executeScripts="true"
                           formId="activeCardForm" loadingText="Đang thực hiện..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="bodyContent"
                           value="Tìm kiếm"  beforeNotifyTopics="lookupSerialAction/lookupSerial"/-->
                <%--<tags:submit formId="activeCardForm"
                             validateFunction="checkValidFields()"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.generates.find"
                             preAction="activeCard!checkSerial.do"/>--%>
                <tags:submit formId="activeCardForm"
                             validateFunction="checkValidFields()"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.SAE.138"
                             preAction="activeCard!lookupSerial.do"/>

                <tags:submit formId="activeCardForm"
                             validateFunction="checkValidFields()"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.active.card"
                             preAction="activeCard!activeCard.do"/>
            </div>
        </div>
        <!-- Ket qua kich hoat -->
        <s:if test="#attr.errFilePath != null">
            <script type="text/javascript" language="JavaScript">
                window.open('${fn:escapeXml(errFilePath)}','','toolbar=yes,scrollbars=yes');
            </script>
                <a target="blank" href='${fn:escapeXml(errFilePath)}' ><tags:label key="MSG.click.here.to.download" /></a>

        </s:if>

        <s:if test="#attr.tblListCheckCard != null">
            <div>
                <jsp:include page="listCheckCard.jsp"/>
            </div>
        </s:if>
        <!-- ket qua tim kiem -->
        <s:if test="#attr.tblListSerialPair != null">
            <sx:div id="divSearchResult" theme="simple">
                <jsp:include page="listLookupCard.jsp"/>
            </sx:div>
        </s:if>
    </s:form>
</tags:imPanel>

<script language="javascript">

    //focus vao truong dau tien
    $('activeCardForm.fromSerial').select();
    $('activeCardForm.fromSerial').focus();


    //xu ly su kienn focus tren textBox toIsdn
    focusOnToSerial = function () {
        var fromSerial = trim($('activeCardForm.fromSerial').value);
        var toSerial = trim($('activeCardForm.toSerial').value);
        if(toSerial == "") {
            $('activeCardForm.toSerial').value = fromSerial;
        }
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        var fromSerial = trim($('activeCardForm.fromSerial').value);
        var toSerial = trim($('activeCardForm.toSerial').value);

        if(fromSerial == "" ||fromSerial ==null ) {
            $('message').innerHTML = '<s:text name = "ERR.ACTIVE.CARD.0011"/>';
            $('activeCardForm.fromSerial').select();
            $('activeCardForm.fromSerial').focus();
            return false;
        }

        if(toSerial ==null || toSerial == "" ) {
            $('message').innerHTML = '<s:text name = "ERR.ACTIVE.CARD.0011"/>';
            $('activeCardForm.toSerial').select();
            $('activeCardForm.toSerial').focus();
            return false;
        }

        if(fromSerial != "" && !isInteger(trim($('activeCardForm.fromSerial').value))) {
            $('message').innerHTML = '<s:text name = "ERR.FAC.ISDN.001"/>';
            $('activeCardForm.fromSerial').select();
            $('activeCardForm.fromSerial').focus();
            return false;
        }
        if(toSerial != "" && !isInteger(trim($('activeCardForm.toSerial').value))) {
            $('message').innerHTML = '<s:text name = "ERR.FAC.ISDN.002"/>';
            $('activeCardForm.toSerial').select();
            $('activeCardForm.toSerial').focus();
            return false;
        }


        if(fromSerial != "" && toSerial != "" && (fromSerial * 1 > toSerial * 1)) {
            $('message').innerHTML = '<s:text name = "ERR.FAC.ISDN.003"/>';
            $('activeCardForm.fromSerial').select();
            $('activeCardForm.fromSerial').focus();
            return false;
        }

        //trim cac truong can thiet
        $('activeCardForm.fromSerial').value = trim($('activeCardForm.fromSerial').value);
        $('activeCardForm.toSerial').value = trim($('activeCardForm.toSerial').value);
        /*
        var _myWidget = dojo.widget.byId("activeCardForm.shopCode");
        alert('>' + _myWidget.textInputNode.value + '<');
        _myWidget.textInputNode.value = trim(_myWidget.textInputNode.value);
        alert('>' + _myWidget.textInputNode.value + '<');*/


        return true;
    }


    //cap nhat lai bien session current owerType
    /*
    changeOwnerType = function(ownerType) {
        updateData("${contextPath}/lookupSerialAction!changeOwnerType.do?target=activeCardForm.ownerType_hidden&ownerType=" + ownerType);
    }*/
</script>
