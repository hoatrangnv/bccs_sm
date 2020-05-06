<%-- 
    Document   : lookupIsdnExtend
    Created on : Jan 26, 2010, 8:47:45 AM
    Author     : TheTM
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

<tags:imPanel title="MSG.find.isdn.extra.info">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi thong tin can tra cuu -->
    <s:form action="lookupIsdnAction" theme="simple" method="post" id="lookupIsdnForm">
<s:token/>

        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td calss="label"> <tags:label key="MSG.generates.service" /></td>
                    <td class="oddColumn">
                        <tags:imSelect name="lookupIsdnForm.stockTypeId"
                                  id="stockTypeId"
                                  cssClass="txtInputFull"
                                  list="listStockType"
                                  listKey="stockTypeId" listValue="name"
                                  onchange="changeStockType(this.value)"/>
                    </td>
                    <td>
                        <s:checkbox name="lookupIsdnForm.isSearchInFile" id="chkSearchInFile" onclick="changedSearchInFile();"/>
                        <tags:label key="MSG.FAC.File" />
                       
                    </td>
                    <td class="oddColumn">
                        <div id="isdnFile">
                            <tags:imFileUpload name="lookupIsdnForm.clientIsdnFile" id="isdnFile1" cssStyle="width:100%; " serverFileName="lookupIsdnForm.serverIsdnFile" extension="xls"/>
                        </div>
                    </td>
                    <td>
                        <s:checkbox name="lookupIsdnForm.isSearchAdvance" id="chkSearchAdvance" onclick="changedSearchAdvance();"/>
                         <tags:label key="MSG.generates.number.type" />
                        
                    </td>
                    <td>
                        <s:textfield name="lookupIsdnForm.isdnAdvance" id="isdnAdvance" maxlength="25" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:10%;"> <tags:label key="MSG.isdn.stock" /></td>
                    <td class="oddColumn" style="width:30%;">
                        <tags:imSearch codeVariable="lookupIsdnForm.shopCode" nameVariable="lookupIsdnForm.shopName"
                                       codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                       searchClassName="com.viettel.im.database.DAO.LookupIsdnDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getNameShop"/>

                    </td>
                    <td style="width:10%;"><tags:label key="MSG.isdn_type" /></td>
                    <td class="oddColumn" style="width:20%;">
                        <tags:imSelect name="lookupIsdnForm.isdnType"
                                  id="isdnType"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="MSG.chooseIsdnType"
                                  list="3:SpecialISDN, 2:PostPaidISDN, 1:PrepaidISDN"/>
                    </td>
                    <td style="width:10%;"><tags:label key="MSG.isdn.beati.rules" /></td>
                    <td>
                        <tags:imSelect name="lookupIsdnForm.groupFilterRuleCode"
                                  id="groupFilterRuleCode"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="MSG.selectRuleType"
                                  list="listGroupFilter"
                                  listKey="groupFilterRuleCode" listValue="name"
                                  onchange="changeGroupFilterRuleCode(this.value);"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.isdn.area" /></td>
                    <td class="oddColumn">
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:textfield name="lookupIsdnForm.fromIsdn" id="fromIsdn" maxlength="25" cssClass="txtInputFull"/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:textfield name="lookupIsdnForm.toIsdn" id="toIsdn" maxlength="25" cssClass="txtInputFull" onfocus="focusOnToIsdn();"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td><tags:label key="MSG.generates.status" /></td>
                    <td class="oddColumn">
                        <tags:imSelect name="lookupIsdnForm.status" id="status"
                              cssClass="txtInputFull" disabled="false"
                              list="4:KITISDN, 5:LockISDN, 2:UsingISDN, 1:NewISDN, 3:SuspendISDN"
                              headerKey="" headerValue="MSG.GOD.189"/>

                        <%--<tags:imSelect name="lookupIsdnForm.status"
                                  id="status"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn trạng thái--"
                                  list="4:KITISDN, 5:LockISDN, 2:UsingISDN, 1:NewISDN, 3:SuspendISDN"/>--%>
                    </td>
                    <td><tags:label key="MSG.isdn.beati.group.rule" /></td>
                    <td>
                        <tags:imSelect name="lookupIsdnForm.filterTypeId"
                                  id="filterTypeId"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="MSG.chooseFilterType"
                                  list="listFilterType"
                                  listKey="filterTypeId" listValue="name"
                                  onchange="changeFilterType(this.value);"/>
                    </td>
                </tr>
                <tr>

                    <td><tags:label key="MSG.interval.price" /></td>
                    <td class="oddColumn">
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:textfield name="lookupIsdnForm.fromPrice" id="fromPrice" onkeyup="textFieldNF(this)" maxlength="15" cssClass="txtInputFull" cssStyle="text-align:right; "/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:textfield name="lookupIsdnForm.toPrice" id="toPrice" onkeyup="textFieldNF(this)" maxlength="15" cssClass="txtInputFull" cssStyle="text-align:right; "/>
                                </td>
                            </tr>
                        </table>
                    </td>

                    <td><tags:label key="MSG.generates.goods" /></td>
                    <td class="oddColumn">
                        <tags:imSelect name="lookupIsdnForm.stockModelId"
                                  id="stockModelId"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="MSG.GOD.217"
                                  list="listStockModel"
                                  listKey="stockModelId" listValue="name"/>
                    </td>

                    <td><tags:label key="MSG.isdn.beauti.rule" /></td>
                    <td>
                        <tags:imSelect name="lookupIsdnForm.ruleId"
                                  id="rulesId"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="MSG.selectRule"
                                  list="listRule"
                                  listKey="rulesId" listValue="name"/>
                    </td>
                </tr>
                <tr>

                    <td><tags:label key="MSG.generates.result.number" /></td>
                    <td class="oddColumn">
                        <s:textfield name="lookupIsdnForm.count" id="count" maxlength="25" cssClass="txtInputFull" cssStyle="text-align:right; "/>
                    </td>

                    <td><tags:label key="MSG.fromDate" /></td>
                    <td>
                        <tags:dateChooser id="lookupIsdnForm.staDate" property="lookupIsdnForm.staDate" styleClass="txtInputFull"/>
                    </td>
                    <td><tags:label key="MSG.generates.to_date" /></td>
                    <td>
                        <tags:dateChooser id="lookupIsdnForm.endDate" property="lookupIsdnForm.endDate" styleClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </div>

        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <tags:imRadio name="lookupIsdnForm.reportFileType"
                     list="1:ExportExcel, 2:ExportText"/>
        </div>

    </s:form>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:3px;">
        <tags:submit formId="lookupIsdnForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     validateFunction="checkValidFields();"
                     value="MSG.report"
                     preAction="lookupIsdnAction!exportIsdnExtend.do"/>
    </div>

</tags:imPanel>

<s:if test="#session.listLookupIsdn != null && #session.listLookupIsdn.size() > 0">
    <s:if test="lookupIsdnForm.pathExpFile!=null && lookupIsdnForm.pathExpFile!=''">
        <script>
            window.open('${contextPath}<s:property escapeJavaScript="true"  value="lookupIsdnForm.pathExpFile"/>','','toolbar=yes,scrollbars=yes');
        </script>

        <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="lookupIsdnForm.pathExpFile"/>'><tags:label key="MSG.download2.file.here" /></a></div>
    </s:if>
</s:if>

<script language="javascript">

    //focus vao truong dau tien
    $('fromIsdn').focus();
    

    //hien thi textbox price theo dinh dang tien
    textFieldNF($('fromPrice'));
    textFieldNF($('toPrice'));

    //disable cac chuc nang tra cuu theo file va tra cuu theo dang so
    disableImFileUpload("isdnFile1", true);
    $('isdnAdvance').disabled = true;

    //xu ly su kienn focus tren textBox toIsdn
    focusOnToIsdn = function () {
        var fromIsdn = trim($('fromIsdn').value);
        var toIsdn = trim($('toIsdn').value);
        if(toIsdn == "") {
            $('toIsdn').value = fromIsdn;
        }
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        var fromPrice = trim($('fromPrice').value.replace(/\,/g,"")); //loai bo dau , trong chuoi
        var toPrice = trim($('toPrice').value.replace(/\,/g,"")); //loai bo dau , trong chuoi

        if(fromPrice != "" && !isInteger(trim(fromPrice))) {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.FAC.ISDN.005')"/>';
            $('message').innerHTML = '<s:text name="ERR.FAC.ISDN.005"/>';
            $('fromPrice').select();
            $('fromPrice').focus();
            return false;
        }

        if(toPrice != "" && !isInteger(trim(toPrice))) {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.FAC.ISDN.006')"/>';
            $('message').innerHTML = '<s:text name="ERR.FAC.ISDN.006"/>';
            $('toPrice').select();
            $('toPrice').focus();
            return false;
        }

        if(fromPrice != "" && toPrice != "" && (fromPrice * 1 > toPrice * 1)) {
//            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.FAC.ISDN.007')"/>';
            $('message').innerHTML =  '<s:text name="ERR.FAC.ISDN.007"/>';
            $('fromPrice').select();
            $('fromPrice').focus();
            return false;
        }

        var fromIsdn = trim($('fromIsdn').value);
        var toIsdn = trim($('toIsdn').value);

        if(fromIsdn != "" && !isInteger(trim(fromIsdn))) {
//            $('message').innerHTML ='<s:property escapeJavaScript="true"  value="getError('ERR.FAC.ISDN.008')"/>';
            $('message').innerHTML ='<s:text name="ERR.FAC.ISDN.008"/>';
            $('fromIsdn').select();
            $('fromIsdn').focus();
            return false;
        }

        if(toIsdn != "" && !isInteger(trim(toIsdn))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.FAC.ISDN.009')"/>';
            $('toIsdn').select();
            $('toIsdn').focus();
            return false;
        }

        if(fromIsdn != "" && toIsdn != "" && (fromIsdn * 1 > toIsdn * 1)) {
//            $('message').innerHTML ='<s:property escapeJavaScript="true"  value="getError('ERR.FAC.ISDN.010')"/>';
            $('message').innerHTML ='<s:text name="ERR.FAC.ISDN.010"/>';
            $('fromIsdn').select();
            $('fromIsdn').focus();
            return false;
        }

        var chkSearchAdvance = $('chkSearchAdvance');
        if (chkSearchAdvance.checked){
            var isdnAdvance = trim($('isdnAdvance').value);
            if (isdnAdvance == ""){
//                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.FAC.ISDN.011')"/>';
                $('message').innerHTML = '<s:text name="ERR.FAC.ISDN.011"/>';
                $('isdnAdvance').select();
                $('isdnAdvance').focus();
                return false;
            }
        }

        //trim cac truong can thiet
        $('fromPrice').value = trim($('fromPrice').value);
        $('toPrice').value = trim($('toPrice').value);
        $('fromIsdn').value = trim($('fromIsdn').value);
        $('toIsdn').value = trim($('toIsdn').value);
        $('isdnAdvance').value = trim($('isdnAdvance').value);

        return true;
    }

    //xu ly su kien sau khi chon 1 ma cua hang
    dojo.event.topic.subscribe("lookupIsdnAction/getShopName", function(value, key, text, widget){
        if (key != undefined) {
            getInputText("lookupIsdnAction!getShopName.do?target=shopName&shopId=" + key);
        } else {
            $('shopName').value = "";
        }
    });

    //
    getShopName = function(){
        var shopCode=$('lookupIsdnForm.shopCode').value;
        getInputText("lookupIsdnAction!getShopName.do?target=lookupIsdnForm.shopName&shopCode=" + shopCode);

    };

    //cap nhat lai danh sach mat hang, danh sach tap luat
    changeStockType = function(stockTypeId) {
        updateData("${contextPath}/lookupIsdnAction!changeStockType.do?target=stockModelId,groupFilterRuleCode,filterTypeId,rulesId&stockTypeId=" + stockTypeId);
    }

    //xu ly su kien khi GroupFilterRuleCode change
    changeGroupFilterRuleCode = function(groupFilterRuleCode) {
        updateData("${contextPath}/lookupIsdnAction!changeGroupFilterRuleCode.do?target=filterTypeId,rulesId&groupFilterRuleCode=" + groupFilterRuleCode);
    }

    //xu ly su kien khi FilterType change
    changeFilterType = function(filterId) {
        updateData("${contextPath}/lookupIsdnAction!changeFilterType.do?target=rulesId&filterId=" + filterId);
    }

    disableNormalSearchControl = function(bDisable){
        $('lookupIsdnForm.shopCode').disabled = bDisable;
        $('lookupIsdnForm.shopName').disabled = bDisable;
        $('isdnType').disabled = bDisable;
        $('groupFilterRuleCode').disabled = bDisable;
        $('filterTypeId').disabled = bDisable;
        $('rulesId').disabled = bDisable;
        $('fromIsdn').disabled = bDisable;
        $('toIsdn').disabled = bDisable;
        $('status').disabled = bDisable;
        $('fromPrice').disabled = bDisable;
        $('toPrice').disabled = bDisable;
        $('stockModelId').disabled = bDisable;
    }

    //tra cuu mo rong so isdn theo dang %%
    changedSearchAdvance = function() {
        var search = $('chkSearchAdvance');
        if (search.checked){
            //tim kiem theo dang mo rong
            $('isdnAdvance').disabled = false;
            //disable tim kiem theo file
            $('chkSearchInFile').checked = false;
            disableImFileUpload("isdnFile1", true);
        } else {
            //bo tim kiem theo dang mo rong -> dua cac doi tuong ve trang thai binh thuong
            $('isdnAdvance').disabled = true;
        }
    }

    //tra cuu so isdn theo file (tim cac so isdn ton tai trong file Excel)
    changedSearchInFile = function() {
        var search = $('chkSearchInFile');
        if (search.checked){
            //tim kiem theo file
            disableImFileUpload("isdnFile1", false);
            //disable tim kiem theo dang so
            $('chkSearchAdvance').checked = false;
            $('isdnAdvance').disabled = true;
            //disable tat ca cac tieu chi tim kiem don le
            disableNormalSearchControl(true);
        } else {
            //bo tim kiem theo file -> dua cac doi tuong ve trang thai binh thuong
            disableImFileUpload("isdnFile1", true);
            disableNormalSearchControl(false);
        }
    }
    changedSearchInFile();
    changedSearchAdvance();
</script>
