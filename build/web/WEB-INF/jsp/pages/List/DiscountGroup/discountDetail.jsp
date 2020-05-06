<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : discountDetail
    Created on : Mar 24, 2009, 9:35:24 AM
    Author     : tamdt1
    Desc       : hien thi thong tin ve discountDetail
--%>

<%@page contentType="text/s" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="sdivDiscountDetail" cssStyle="padding:3px; ">

    <!-- phan hien thi message -->
    <tags:displayResult property="message" id="messageDiscount" type="key"/>

    <!-- phan hien thi thong tin chi tiet CK -->
    <div class="divHasBorder">
        <s:form action="discountGroupAction!addOrEditDiscountDetail" theme="simple" method="post" id="discountForm">
            <s:token/>

            <s:hidden name="discountForm.discountId" id="discountForm.discountId"/>
            <s:hidden name="discountForm.discountGroupId" id="discountForm.discountGroupId"/>

            <!-- trong truong hop cac combobox bi disable phai them cac bien hidden de luu tru cac gia tri nay -->
            <s:if test="!(#request.isAddMode || #request.isEditMode)">
                <s:hidden name="discountForm.type" id="discountForm.type"/>
            </s:if>

            <table class="inputTbl4Col">
                <tr>
<!--                    <td style="width:15%">Loại CK<s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if></td>-->
                    <td style="width:15%"><tags:label key="MSG.LST.031" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/></td>
                    <td class="oddColumn" style="width:35%">
                        <tags:imSelect name="discountForm.type"
                                       id="discountForm.type"
                                       disabled="${fn:escapeXml(!(requestScope.isAddMode || requestScope.isEditMode))}"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="--Select discount type--"
                                       list="1:MSG.LST.032,2:MSG.LST.033"
                                       onchange="changeType()"/>
                    </td>
                    <td style="width:15%"></td>
                    <td></td>
                </tr>
                <tr>
<!--                    <td>Từ số tiền<s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if></td>-->
                    <td><tags:label key="MSG.LST.034" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/></td>
                    <td class="oddColumn">
                        <s:textfield name="discountForm.fromAmount" id="fromAmount" onkeyup="textFieldNF(this)" maxlength="15" cssClass="txtInputFull" cssStyle="text-align:right; " readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
<!--                    <td>Đến số tiền<s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if></td>-->
                    <td><tags:label key="MSG.LST.035" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/></td>
                    <td>
                        <s:textfield name="discountForm.toAmount" id="toAmount" onkeyup="textFieldNF(this)" maxlength="15" cssClass="txtInputFull" cssStyle="text-align:right; " readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
                </tr>
                <tr>
<!--                    <td>Tỷ lệ CK<s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if></td>-->
                    <td><tags:label key="MSG.LST.036" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/></td>
                    <td class="oddColumn">
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td>
                                    <s:textfield name="discountForm.discountRateNumerator" id="discountRateNumerator" maxlength="15" cssClass="txtInputFull" cssStyle="text-align:right; " readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                                </td>
                                <td>
                                    /
                                </td>
                                <td>
                                    <s:textfield name="discountForm.discountRateDenominator" id="discountRateDenominator" maxlength="15" cssClass="txtInputFull" cssStyle="text-align:right; " readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                                </td>
                            </tr>
                        </table>
                    </td>
<!--                    <td>Số tiền CK<s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if></td>-->
                    <td><tags:label key="MSG.LST.037" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/></td>
                    <td>
                        <s:textfield name="discountForm.discountAmount" id="discountAmount" onkeyup="textFieldNF(this)" maxlength="15" cssClass="txtInputFull" cssStyle="text-align:right; " readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
                </tr>
                <tr>
<!--                    <td>Ngày bắt đầu<s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if></td>-->
                    <td><tags:label key="MSG.LST.038" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/></td>
                    <td class="oddColumn">
                        <tags:dateChooser property="discountForm.startDatetime" id="discountForm.startDatetime" styleClass="txtInputFull" readOnly="${fn:escapeXml(!(isAddMode || isEditMode))}"/>
                    </td>
                    <!--                    <td>Ngày kết thúc</td>-->
                    <td><tags:label key="MSG.LST.039"/></td>
                    <td>
                        <tags:dateChooser property="discountForm.endDatetime" id="discountForm.endDatetime" styleClass="txtInputFull" readOnly="${fn:escapeXml(!(isAddMode || isEditMode))}"/>
                    </td>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <s:if test="#request.isAddMode || #request.isEditMode">
            <div align="center" style="vertical-align:top; ">
                <tags:submit id="btnAddOrEditDiscountDetail"
                             targets="sdivDiscountDetail"
                             formId="discountForm"
                             cssStyle="width:80px;"
                             confirm="true"
                             confirmText="MSG.GOD.017"
                             value="MSG.GOD.016"
                             preAction="discountGroupAction!addOrEditDiscountDetail.do"
                             validateFunction="checkValidDataToAddOrEditDiscount()"
                             showLoadingText="true"/>

                <tags:submit id="btnDisplayDiscountDetail"
                             targets="sdivDiscountDetail"
                             formId="discountForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.018"
                             preAction="discountGroupAction!displayDiscountDetail.do"
                             validateFunction="validDataBeforeDisplayDiscountDetail()"
                             showLoadingText="true"/>
            </div>
            <script language="javascript">
                disableTab('sxdivDiscountGroup');
                disableTab('sxdivDiscountGoods');
            </script>
        </s:if>
        <s:else>
            <div align="center" style="vertical-align:top; ">
                <tags:submit id="btnAddDiscountDetail"
                             targets="sdivDiscountDetail"
                             formId="discountForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.019"                             
                             validateFunction = "prepareEdit() "
                             preAction="discountGroupAction!prepareAddDiscountDetail.do"
                             showLoadingText="true"/>

                <!-- chi hien thi sua, xoa trong truong hop da co it nhat 1 phan tu -->
                <s:if test="#attr.discountForm.discountId.compareTo(0L) > 0">
                    <tags:submit id="btnEditDiscountDetail"
                                 targets="sdivDiscountDetail"
                                 formId="discountForm"
                                 cssStyle="width:80px;"
                                 value="MSG.GOD.020"
                                 validateFunction = "prepareEdit() "
                                 preAction="discountGroupAction!prepareEditDiscountDetail.do"
                                 showLoadingText="true"/>

                    <tags:submit id="btnDelDiscountDetail"
                                 targets="sdivDiscountDetail"
                                 formId="discountForm"
                                 cssStyle="width:80px;"
                                 value="MSG.GOD.021"
                                 confirm="true"
                                 confirmText="MSG.GOD.324"
                                 validateFunction = "prepareEdit() "
                                 preAction="discountGroupAction!delDiscountDetail.do"
                                 showLoadingText="true"/>
                </s:if>
                <s:else>
                    <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.020'))}" disabled style="width:80px;">
                    <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))}" disabled style="width:80px;">
                </s:else>
            </div>

            <script language="javascript">
                enableTab('sxdivDiscountGroup');
                enableTab('sxdivDiscountGoods');
            </script>

        </s:else>


    </div>

    <!-- phan hien thi danh chi tiet CK -->
    <div>
        <jsp:include page="listDiscountDetail.jsp"/>
    </div>

</sx:div>


<script language="javascript">

    //dinh danh cho truong tu so tien, den so tien
    textFieldNF($('fromAmount'));
    textFieldNF($('toAmount'));
    textFieldNF($('discountAmount'));

    validDataBeforeDisplayDiscountDetail = function() {
        $('fromAmount').value = "";
        $('toAmount').value = "";
        $('discountRateNumerator').value = "";
        $('discountRateDenominator').value = "";
        $('discountAmount').value = "";

        return true;
    }
    
    //
    changeType = function() {
        var type = $('discountForm.type').value;
        if(type == 2) {
            //truong hop chiet khau tong tien
            $('discountRateNumerator').value = "";
            $('discountRateNumerator').disabled = true;
            $('discountRateDenominator').value = "";
            $('discountRateDenominator').disabled = true;
            $('discountAmount').disabled = false;
        } else {
            //truong hop chiet khau %
            $('discountRateNumerator').disabled = false;
            $('discountRateDenominator').disabled = false;
            $('discountAmount').value = "";
            $('discountAmount').disabled = true;
        }
    }

    //
    checkValidDataToAddOrEditDiscount = function() {

        if(checkRequiredFields_discount() && checkValidFields_discount()) {
            return true;
        } else {
            return false;
        }
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields_discount = function() {
        if(trim($('discountForm.type').value) == "") {
            //chon loai chiet khau
            $('messageDiscount' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.025')"/>';
            $('discountForm.type').focus();
            return false;
        }

        if(trim($('fromAmount').value) == "") {
            //tu so tien
            $('messageDiscount' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.028')"/>';
            $('fromAmount').select();
            $('fromAmount').focus();
            return false;
        }
        
        if(trim($('toAmount').value) == "") {
            //den so tien
            $('messageDiscount' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.029')"/>';
            $('toAmount').select();
            $('toAmount').focus();
            return false;
        }


        if ($('discountForm.type').value == 2) {
            //truong hop chiet khau tong tien
            if(trim($('discountAmount').value) == "") {
                //chon loai chiet khau
                $('messageDiscount' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.026')"/>';
                $('discountAmount').select();
                $('discountAmount').focus();
                return false;
            }

        } else {
            //truong hop chiet khau %
            if(trim($('discountRateNumerator').value) == "") {
                //chon loai chiet khau
                $('messageDiscount' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.027')"/>';
                $('discountRateNumerator').select();
                $('discountRateNumerator').focus();
                return false;
            }
            if(trim($('discountRateDenominator').value) == "") {
                //chon loai chiet khau
                $('messageDiscount' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.027')"/>';
                $('discountRateDenominator').select();
                $('discountRateDenominator').focus();
                return false;
            }
        }

        return true;

    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields_discount = function() {
        var fromAmount = $('fromAmount').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        if(!isInteger(trim(fromAmount))) {
            $('messageDiscount' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.030')"/>';
            $('fromAmount').select();
            $('fromAmount').focus();
            return false;
        }

        var toAmount = $('toAmount').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        if(!isInteger(trim(toAmount))) {
            $('messageDiscount' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.031')"/>';
            $('toAmount').select();
            $('toAmount').focus();
            return false;
        }

        if ($('discountForm.type').value == 2) {
            //truong hop CK tong tien
            var discountAmount = $('discountAmount').value.replace(/\,/g,""); //loai bo dau , trong chuoi
            if(!isInteger(trim(discountAmount))) {
                $('messageDiscount' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.033')"/>';
                $('discountAmount').select();
                $('discountAmount').focus();
                return false;
            }
        } else {
            //ck ty le
            var discountRateNumerator = $('discountRateNumerator').value.replace(/\,/g,"").replace(/\./g,""); //loai bo dau , va dau .
            if(!isInteger(trim(discountRateNumerator))) {
                $('messageDiscount' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.032')"/>';
                $('discountRateNumerator').select();
                $('discountRateNumerator').focus();
                return false;
            }
            var discountRateDenominator = $('discountRateDenominator').value.replace(/\,/g,"").replace(/\./g,""); //
            if(!isInteger(trim(discountRateDenominator))) {
                $('messageDiscount' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.032')"/>';
                $('discountRateDenominator').select();
                $('discountRateDenominator').focus();
                return false;
            }
            var rate = (discountRateNumerator * 1) / (discountRateDenominator * 1);
            if(rate < 0 || rate > 100) {
                $('messageDiscount' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.032')"/>';
                $('discountRateNumerator').select();
                $('discountRateNumerator').focus();
                return false;
            }
        }

        //trim cac truong can thiet
        $('fromAmount').value = trim($('fromAmount').value.replace(/\,/g,""));
        $('toAmount').value = trim($('toAmount').value.replace(/\,/g,""));
        $('discountRateNumerator').value = trim($('discountRateNumerator').value.replace(/\,/g,""));
        $('discountRateDenominator').value = trim($('discountRateDenominator').value.replace(/\,/g,""));
        $('discountAmount').value = trim($('discountAmount').value.replace(/\,/g,""));


        $('fromAmount').value = $('fromAmount').value.replace(",","");
        $('toAmount').value = $('toAmount').value.replace(",","");
        
        return true;
    }

    prepareEdit = function(){
        $('fromAmount').value = trim($('fromAmount').value.replace(/\,/g,""));
        $('toAmount').value = trim($('toAmount').value.replace(/\,/g,""));
        $('fromAmount').value = $('fromAmount').value.replace(",","");
        $('toAmount').value = $('toAmount').value.replace(",","");
        
        return true;
    }


    //
    changeType();

</script>


