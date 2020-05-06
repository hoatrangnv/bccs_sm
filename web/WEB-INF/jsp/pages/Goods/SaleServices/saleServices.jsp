<%-- 
    Document   : saleServices
    Created on : Mar 13, 2009, 4:42:09 PM
    Author     : tamdt1
    Desc       : hien thi thong tin ve saleService
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

<sx:div id="divSaleServices" cssStyle="padding:3px; ">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult id="message" property="message" type="key"/>
    </div>

    <!-- phan hien thi thong tin ve dich vu -->
    <div class="divHasBorder">
        <s:form theme="simple" action="saleServicesAction.do" id="saleServicesForm" method="post">
            <s:token/>

            <s:hidden name="saleServicesForm.saleServicesId" id="saleServicesForm.saleServicesId"/>
            <!-- TruongNQ5 20140725 R6237 ID ban ghi cua 2 chi tieu -->
            <s:hidden name="saleServicesForm.serviceIndex" id="serviceIndex"/>
            <s:hidden name="saleServicesForm.serviceQualityIndex" id="serviceQualityIndex"/>
            <!-- trong truong hop cac combobox bi disable phai them cac bien hidden de luu tru cac gia tri nay -->
            <s:if test="!(#request.isAddMode || #request.isEditMode)">
                <s:hidden name="saleServicesPriceForm.pricePolicy" id="saleServicesPriceForm.pricePolicy"/>
                <s:hidden name="saleServicesPriceForm.status" id="saleServicesPriceForm.status"/>
            </s:if>

            <table class="inputTbl4Col">
                <tr>
                    <td style="width:15%;">
                        <tags:label key="MSG.GOD.093" required="${(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"/>
                        <!--                        093Dịch vụ VT-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn" style="width:35%;">
                        <tags:imSelect name="saleServicesForm.telecomServicesId"
                                       id="saleServicesForm.telecomServicesId"
                                       cssClass="txtInputFull"
                                       disabled="${!(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"
                                       list="listTelecomServices"
                                       listKey="telecomServiceId" listValue="telServiceName"
                                       headerKey="" headerValue="MSG.GOD.302"/>
                    </td>
                    <td style="width:15%;">
                        <tags:label key="MSG.GOD.094" required="${(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"/>
                        <!--                        094Mã dịch vụ-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td>
                        <s:textfield name="saleServicesForm.code" id="saleServicesForm.code"
                                     maxlength="50" cssClass="txtInputFull"
                                     readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <tags:label key="MSG.GOD.095" required="${(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"/>
                        <!--                        095Tên dịch vụ-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn">
                        <s:textfield name="saleServicesForm.name" id="saleServicesForm.name"
                                     maxlength="100" cssClass="txtInputFull"
                                     readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>


                    <td>
                        <tags:label key="MSG.GOD.328" required="${(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"/>
                        <!--                        095Tên dịch vụ-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn">
                        <s:textfield name="saleServicesForm.accountingModelCode" id="saleServicesForm.accountingModelCode"
                                     maxlength="100" cssClass="txtInputFull"
                                     readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
                </tr>
                <tr>

                    <td>
                        <tags:label key="MSG.GOD.329" required="${(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"/>
                        <!--                        095Tên dịch vụ-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn">
                        <s:textfield name="saleServicesForm.accountingModelName" id="saleServicesForm.accountingModelName"
                                     maxlength="100" cssClass="txtInputFull"
                                     readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>



                    <td>
                        <tags:label key="MSG.GOD.013" required="${(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"/>
                        <!--                        013Trạng thái-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td>
                        <tags:imSelect name="saleServicesForm.status"
                                       id="saleServicesForm.status"
                                       cssClass="txtInputFull"
                                       disabled="${!(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"
                                       list="1:MSG.GOD.002, 0:MSG.GOD.003"/>

                        <%--s:select name="saleServicesForm.status"
                                  id="saleServicesForm.status"
                                  cssClass="txtInputFull"
                                  disabled="%{!(#request.isAddMode || #request.isEditMode)}"
                                  list="#{'1':'Hoạt động', '0':'Không hoạt động'}"/--%>
                    </td>
                </tr>
                <tr>
                    <td>
                        <tags:label key="MSG.GOD.031"/>
                        <!--                        031Ghi chú-->
                    </td>
                    <td colspan="3">
                        <s:textfield name="saleServicesForm.notes" id="saleServicesForm.notes"
                                     maxlength="450" cssClass="txtInputFull"
                                     readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
                </tr>
                <!--TruongNQ5 201407 R6237 Tiêu chí doanh thu-->
                <tr>
                    <td style="width:13%; ">
                        <tags:label key="rvn.service.003" /><!--Tiêu chí doanh thu-->
                    </td>
                    <td class="oddColumn" style="width:26%; ">
                        <%--<tags:imSelect name="saleServicesForm.rvnServiceId"
                                       id="saleServicesForm.rvnServiceId"
                                       cssClass="txtInputFull"
                                       disabled="${!(requestScope.isAddMode || requestScope.isEditMode)}"
                                       list="listRvnService"
                                       listKey="id" listValue="name"
                                       headerKey="" headerValue="rvn.service.001"/>--%>
                        <s:if test="(#request.isAddMode == true || #request.isEditMode == true)">
                            <s:select name="saleServicesForm.rvnServiceId"
                                      id="saleServicesForm.rvnServiceId"
                                      disabled="false"
                                      cssClass="txtInputFull"
                                      headerKey="" headerValue="%{getText('rvn.service.001')}"
                                      list="%{#request.listRvnService != null ? #request.listRvnService :#{}}"
                                      listKey="id" listValue="name"/>
                        </s:if> 
                        <s:else>
                            <s:select name="saleServicesForm.rvnServiceId"
                                      id="saleServicesForm.rvnServiceId"
                                      disabled="true"
                                      cssClass="txtInputFull"
                                      headerKey="" headerValue="%{getText('rvn.service.001')}"
                                      list="%{#request.listRvnService != null ? #request.listRvnService :#{}}"
                                      listKey="id" listValue="name"/>    
                        </s:else>
                    </td>
                    <td style="width:13%; ">
                        <tags:label key="rvn.service.004" /><!--Tiêu chí doanh thu-->
                    </td>
                    <td class="oddColumn" style="width:35%; ">
                        <%--<tags:imSelect name="saleServicesForm.rvnServiceQualityId"
                                       id="saleServicesForm.rvnServiceQualityId"
                                       cssClass="txtInputFull"
                                       disabled="${!(requestScope.isAddMode || requestScope.isEditMode)}"
                                       list="listRvnServiceQuality"
                                       listKey="id" listValue="name"
                                       headerKey="" headerValue="rvn.service.002"/>--%>
                        <s:if test="(#request.isAddMode == true || #request.isEditMode == true)">
                            <s:select name="saleServicesForm.rvnServiceQualityId"
                                      id="saleServicesForm.rvnServiceQualityId"
                                      disabled="false"
                                      cssClass="txtInputFull"
                                      headerKey="" headerValue="%{getText('rvn.service.002')}"
                                      list="%{#request.listRvnServiceQuality != null ? #request.listRvnServiceQuality :#{}}"
                                      listKey="id" listValue="name"/>
                        </s:if> 
                        <s:else>
                            <s:select name="saleServicesForm.rvnServiceQualityId"
                                      id="saleServicesForm.rvnServiceQualityId"
                                      disabled="true"
                                      cssClass="txtInputFull"
                                      headerKey="" headerValue="%{getText('rvn.service.002')}"
                                      list="%{#request.listRvnServiceQuality != null ? #request.listRvnServiceQuality :#{}}"
                                      listKey="id" listValue="name"/>    
                        </s:else>
                    </td>
                </tr>
                <!--End TruongNQ5 201407 R6237 Tiêu chí doanh thu-->
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <s:if test="#request.isAddMode || #request.isEditMode">
            <div align="center" style="vertical-align:top">
                <sx:submit parseContent="true" executeScripts="true"
                           formId="saleServicesForm" loadingText="Processing..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="divSaleServices"
                           key="MSG.GOD.016"  beforeNotifyTopics="saleServicesAction/addOrEditSaleServices"/>

                <sx:submit parseContent="true" executeScripts="true"
                           formId="saleServicesForm" loadingText="Processing..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="divDisplayInfo"
                           key="MSG.GOD.018"  beforeNotifyTopics="saleServicesAction/cancelAddSaleServices"/>
            </div>
            <script language="javscript">
                disableTab('sxdivSaleServicesPrice');
                disableTab('sxdivSaleServicesModel');
            </script>
        </s:if>
        <s:else>
            <div align="center" style="vertical-align:top">
                <sx:submit parseContent="true" executeScripts="true"
                           formId="saleServicesForm" loadingText="Processing..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="divSaleServices"
                           key="MSG.GOD.019"  beforeNotifyTopics="saleServicesAction/prepareAddSaleServices"/>
                <s:if test="saleServicesForm.saleServicesId.compareTo(0L) > 0">
                    <sx:submit parseContent="true" executeScripts="true"
                               formId="saleServicesForm" loadingText="Processing..."
                               cssStyle="width:80px;"
                               showLoadingText="true" targets="divSaleServices"
                               key="MSG.GOD.020"  beforeNotifyTopics="saleServicesAction/prepareEditSaleServices"/>
                    <sx:submit parseContent="true" executeScripts="true"
                               formId="saleServicesForm" loadingText="Processing..."
                               cssStyle="width:80px;"
                               showLoadingText="true" targets="divSaleServices"
                               key="MSG.GOD.021"  beforeNotifyTopics="saleServicesAction/delSaleServices"/>
                </s:if>
                <s:else>
                    <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.020'))}" disabled style="width:80px;">
                    <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))}" disabled style="width:80px;">

                    <script language="javscript">
                        disableTab('sxdivSaleServicesPrice');
                        disableTab('sxdivSaleServicesModel');
                    </script>

                </s:else>

            </div>
            <script language="javscript">
                enableTab('sxdivSaleServicesPrice');
                enableTab('sxdivSaleServicesModel');
            </script>
        </s:else>
    </div>

    <!-- danh sach dich vu ban hang -->
    <div>
        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.075'))}
                <!--                Danh sách dịch vụ bán hàng-->
            </legend>
            <br /><br /><br /><br /><br />
        </fieldset>
    </div>

</sx:div>

<script language="javascript">
    //lang nghe, xu ly su kien khi click nut "Chap nhan"
    dojo.event.topic.subscribe("saleServicesAction/addOrEditSaleServices", function(event, widget) {
        // tutm1 13/03/2012 trim ma dich vu truoc khi validate
        saleServiceCode = $('saleServicesForm.code');
        if (saleServiceCode != null && saleServiceCode.value.trim() != null)
            saleServiceCode.value = saleServiceCode.value.trim();

        if (checkRequiredFields_SaleServices() && checkValidFields_SaleServices() && checkRequiredRVNService()) {
//            alert("1");
            widget.href = "saleServicesAction!addOrEditSaleServices.do?" + token.getTokenParamString();
        } else {
//            alert("2");
            event.cancel = true;
        }
    });


    //lang nghe, xu ly su kien khi click nut "Bo qua"
    dojo.event.topic.subscribe("saleServicesAction/cancelAddSaleServices", function(event, widget) {
        widget.href = "saleServicesAction!displaySaleServices.do";
    });
    //TruongNQ5 20140725 Check RVNServiceOutputId
    checkRequiredRVNService = function() {
        if (trim($('saleServicesForm.rvnServiceId').value) == "" && trim($('saleServicesForm.rvnServiceQualityId').value) == "") {
            $('message').innerHTML = '<s:property value="getText('err.rvn.service')"/>';//Bạn phải chọn một trong hai chỉ tiêu danh thu hoặc sản lượng.
            $('saleServicesForm.rvnServiceId').select();
            $('saleServicesForm.rvnServiceId').focus();
            return false;
        }
        return true;
    }
    //End TruongNQ5

    //ham kiem tra cac truong bat buoc
    checkRequiredFields_SaleServices = function() {
//        alert("checkRequiredFields");
        //truong ma dich vu
        if (trim($('saleServicesForm.code').value) == "") {
//            $('message').innerHTML = '!!!Lỗi. Trường mã dịch vụ không được để trống';
            $('message').innerHTML = '<s:text name="MSG.GOD.096"/>';
            $('saleServicesForm.code').select();
            $('saleServicesForm.code').focus();
            return false;
        }
        if (trim($('saleServicesForm.name').value) == "") {
//            $('message').innerHTML = '!!!Lỗi. Trường tên dịch vụ không được để trống';
            $('message').innerHTML = '<s:text name="MSG.GOD.097"/>';
            $('saleServicesForm.name').select();
            $('saleServicesForm.name').focus();
            return false;
        }
        if (trim($('saleServicesForm.telecomServicesId').value) == "") {
//            $('message').innerHTML= '!!!Lỗi. Chọn dịch vụ viễn thông';
            $('message').innerHTML = '<s:text name="MSG.GOD.098"/>';
            $('saleServicesForm.telecomServicesId').focus();
            return false;
        }
        return true;
    }

    //kiem tra ma khong duoc chua cac ky tu dac biet
    checkValidFields_SaleServices = function() {
//    alert("checkValidFields");
        if (!isValidInput($('saleServicesForm.code').value, false, false)) {
//            $('message').innerHTML = '!!!Lỗi. Trường mã dịch vụ chỉ được chứa các ký tự A-Z, a-z, 0-9, _';
            $('message').innerHTML = '<s:text name="MSG.GOD.099"/>';
            $('saleServicesForm.code').select();
            $('saleServicesForm.code').focus();
            return false;
        }

        if (isHtmlTagFormat(trim($('saleServicesForm.name').value))) {
//            $('message').innerHTML="!!!Lỗi. Tên dịch vụ không được chứa các thẻ HTML";
            $('message').innerHTML = '<s:text name="MSG.GOD.100"/>';
            $('saleServicesForm.name').select();
            $('saleServicesForm.name').focus();
            return false;
        }

        if (isHtmlTagFormat(trim($('saleServicesForm.notes').value))) {
//            $('message').innerHTML="!!!Lỗi. Trường ghi chú không được chứa các thẻ HTML";
            $('message').innerHTML = '<s:text name="MSG.GOD.101"/>';
            $('saleServicesForm.notes').select();
            $('saleServicesForm.notes').focus();
            return false;
        }

        if ($('saleServicesForm.notes').value.length > 450) {
//            $('message').innerHTML="!!!Lỗi. Trường ghi chú quá dài";
            $('message').innerHTML = '<s:text name="MSG.GOD.102"/>';
            $('saleServicesForm.notes').select();
            $('saleServicesForm.notes').focus();
            return false;
        }


        if (isHtmlTagFormat(trim($('saleServicesForm.accountingModelCode').value))) {
//            $('message').innerHTML="!!!Lỗi. Trường ghi chú không được chứa các thẻ HTML";
            $('message').innerHTML = '<s:text name="MSG.GOD.099"/>';
            $('saleServicesForm.accountingModelCode').select();
            $('saleServicesForm.accountingModelCode').focus();
            return false;
        }

        if (isHtmlTagFormat(trim($('saleServicesForm.accountingModelName').value))) {
//            $('message').innerHTML="!!!Lỗi. Trường ghi chú không được chứa các thẻ HTML";
            $('message').innerHTML = '<s:text name="MSG.GOD.100"/>';
            $('saleServicesForm.accountingModelName').select();
            $('saleServicesForm.accountingModelName').focus();
            return false;
        }


        //trim cac truong can thiet
        $('saleServicesForm.code').value = trim($('saleServicesForm.code').value);
        $('saleServicesForm.name').value = trim($('saleServicesForm.name').value);
        $('saleServicesForm.notes').value = trim($('saleServicesForm.notes').value);


        return true;
    }

    //lang nghe, xu ly su kien khi click nut "Them"
    dojo.event.topic.subscribe("saleServicesAction/prepareAddSaleServices", function(event, widget) {
        widget.href = "saleServicesAction!prepareAddSaleServices.do";
    });

    //lang nghe, xu ly su kien khi click nut "Sua"
    dojo.event.topic.subscribe("saleServicesAction/prepareEditSaleServices", function(event, widget) {
        widget.href = "saleServicesAction!prepareEditSaleServices.do";
    });

    //lang nghe, xu ly su kien khi click nut "Xoa"
    dojo.event.topic.subscribe("saleServicesAction/delSaleServices", function(event, widget) {
//        if (confirm('Bạn có chắc chắn muốn xóa dịch vụ bán hàng này không?')) {
        var strConfirm = getUnicodeMsg('<s:text name="MSG.GOD.112"/>')
        if (confirm(strConfirm)) {
            widget.href = "saleServicesAction!delSaleServices.do?" + token.getTokenParamString();
        } else {
            event.cancel = true;
        }

    });

    //ham xu ly su kien onclick cua nut "Them" (them gia dich vu)
    /*
     prepareAddSaleServicesPrice = function() {
     openDialog("${contextPath}/saleServicesAction!prepareAddSaleServicesPrice.do", 750, 700, true);
     //openPopup("${contextPath}/saleServicesAction!prepareAddSaleServicesPrice.do", 750, 700);
     }
     
     //ham xu ly su kien onclick cua nut "Them" (them loai mat hang vao dich vu)
     prepareAddSaleServicesModel = function() {
     openDialog("${contextPath}/saleServicesAction!prepareAddSaleServicesModel.do", 750, 700, true);
     }
     
     //ham xu ly su kien khi dong cua so popup sau khi them saleServicesModel
     refreshListSaleServicesModel = function() {
     gotoAction('divSaleServicesModelList','saleServicesAction!refreshListSaleServicesModel.do');
     }
     
     //ham xu ly su kien khi dong cua so popup
     refreshPriceList = function() {
     gotoAction('divListSaleServicesPrice','saleServicesAction!refreshPriceList.do');
     }
     
     //xu ly su kien onchange cua combobox
     dojo.event.topic.subscribe("select/stockModelId",  function(value, key, text){
     notifyEvent('stockModelId',key,'priceId','select2','${contextPath}/saleServicesAction!getComboboxSource.do');
     });
     
     //lang nghe, xu ly su kien khi click nut "Them" (them saleServiceModel)
     dojo.event.topic.subscribe("saleServicesAction/addSaleServicesModel", function(event, widget){
     var stockTypeId = $('stockTypeId').value;
     var stockModelId = "";
     var priceId = "";
     if(dojo.widget.byId("stockModelId").selectedResult != null) {
     stockModelId = dojo.widget.byId("stockModelId").selectedResult[1];
     }
     if(dojo.widget.byId("priceId").selectedResult != null) {
     priceId = dojo.widget.byId("priceId").selectedResult[1];
     }
     if(stockTypeId != "" && stockModelId != "" && priceId != null) {
     //setAction(widget,'divSaleServicesModelList','saleServicesModelAction!addSaleServicesDetail.do')
     widget.href = "saleServicesAction!addSaleServicesDetail.do";
     } else {
     alert('Các trường bắt buộc không được để trống');
     event.cancel = true;
     }
     
     });
     */
</script>



