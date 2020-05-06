<%--
    Document   : createStockIsdn
    Created on : Jan 15, 2008, 2:54:01 PM
    Author     : TuanNV
    Purpose    : tao dai so moi
    Modified   : tamdt1
--%>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.create.new.range.isdn">

    <!-- hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- thong tin dai so can tao -->
    <div class="divHasBorder">
        <s:form action="createStockIsdnAction" id="newStockIsdnForm" method="post" theme="simple">
<s:token/>

            <table class="inputTbl6Col">
                <tr>
                    <td style="width:10%;"><tags:label key="MSG.service.type" required="true"/></td>
                    <td class="oddColumn" style="width:23%;">
                        <tags:imSelect name="newStockIsdnForm.serviceType"
                                       id="newStockIsdnForm.serviceType"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.166"
                                       list="listStockType"
                                       listKey="stockTypeId" listValue="name"
                                       onchange="changeServiceType(this.value);"/>
                    </td>
                    <s:if test="#request.listLocation != null && #request.listLocation.size() != 0">
                        <td style="width:10%;"><tags:label key="MSG.place" required="true"/></td>
                        <td class="oddColumn" style="width:23%;">
                            <tags:imSelect name="newStockIsdnForm.location"
                                           id="newStockIsdnForm.location"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="MSG.choosePlace"
                                           list="listLocation"
                                           listKey="areaCode" listValue="fullName"
                                           onchange="changeProvince();"/>
                        </td>
                        <td style="width:13%;"> <tags:label key="MSG.pstn"/></td>
                        <td>
                            <s:textfield name="newStockIsdnForm.stockPstnDistrict" id="newStockIsdnForm.stockPstnDistrict" maxlength="100" cssClass="txtInputFull" readonly="true"/>
                        </td>
                    </s:if>
                    <s:else>
                        <td style="width:10%;"><tags:label key="MSG.place"/></td>
                        <td class="oddColumn" style="width:23%;">
                            <tags:imSelect name="newStockIsdnForm.location"
                                           id="newStockIsdnForm.location"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="MSG.choosePlace"
                                           list="listLocation"
                                           listKey="areaCode" listValue="fullName"
                                           disabled="true"
                                           />
                        </td>
                        <td style="width:13%;"><tags:label key="MSG.pstn"/></td>
                        <td>
                            <s:textfield name="newStockIsdnForm.stockPstnDistrict" id="newStockIsdnForm.stockPstnDistrict" maxlength="100" cssClass="txtInputFull" readonly="true"/>
                        </td>
                    </s:else>
                </tr>
                <tr>
                    <td><tags:label key="MSG.fromIsdn" required="true"/></td>
                    <td class="oddColumn">
                        <s:textfield name="newStockIsdnForm.startStockIsdn" id="newStockIsdnForm.startStockIsdn" maxlength="15"  cssClass="txtInputFull"/>
                    </td>
                    <td><tags:label key="MSG.toIsdn" required="true"/></td>
                    <td class="oddColumn">
                        <s:textfield name="newStockIsdnForm.endStockIsdn" id="newStockIsdnForm.endStockIsdn" maxlength="15" cssClass="txtInputFull"/>
                    </td>
                    <td><tags:label key="MSG.quantity" required="true"/></td>
                    <td>
                        <s:textfield name="newStockIsdnForm.quantity" id="newStockIsdnForm.quantity" maxlength="15" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </s:form>

        <div align="center" style="width:100%; padding-top:5px; padding-bottom:3px;">
            <tags:submit formId="newStockIsdnForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.createListIsdn"
                         validateFunction="checkValidate()"
                         preAction="createStockIsdnAction!createStockIsdn.do"
                         showProgressDiv="true"
                         showProgressClass="com.viettel.im.database.DAO.StockIsdnDAO"
                         showProgressMethod="updateProgressDiv"
                         confirm="true" confirmText="Do you want to create new isdn range?"
                         />

            <!--input type="button" value="$_{imDef:imGetText('MSG.SAE.138')}" disabled style="width:80px;"/-->
        </div>

    </div>

    <div>
        <jsp:include page="createStockIsdnSearchResult.jsp"/>
    </div>

</tags:imPanel>

<script type="text/javascript" language="javascript">

    //focus vao truong dau tien
    $('newStockIsdnForm.serviceType').focus();


//ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        //truong loai dich vu
        if(trim($('newStockIsdnForm.serviceType').value) == "") {
            $('message').innerHTML= '<s:text name="ERR.ISN.001"/>';

            //            $('message').innerHTML = "!!!Lỗi. Chọn loại dịch vụ";
            $('newStockIsdnForm.serviceType').focus();
            return false;
        }

        //truong dia ban
        //    <s_:if test="#request.listLocation != null && #request.listLocation.size() != 0">
        //            if(trim($('newStockIsdnForm.location').value) == "") {
        //                $('message').innerHTML= '<s_:text name="ERR.ISN.002"/>';
        //                //                    $('message').innerHTML = "!!!Lỗi. Chọn địa bàn";
        //                $('newStockIsdnForm.location').focus();
        //                return false;
        //            }
        //    </s_:if>

        //truong so dau dai
        if(trim($('newStockIsdnForm.startStockIsdn').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.ISN.003"/>';

            //                    $('message').innerHTML = "!!!Lỗi. Trường số đầu dải không được để trống";
            $('newStockIsdnForm.startStockIsdn').select();
            $('newStockIsdnForm.startStockIsdn').focus();
            return false;
        }
        //truong so cuoi dai
        if(trim($('newStockIsdnForm.endStockIsdn').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.ISN.004"/>';
            //                    $('message').innerHTML = "!!!Lỗi. Trường số cuối dải không được để trống";
            $('newStockIsdnForm.endStockIsdn').select();
            $('newStockIsdnForm.endStockIsdn').focus();
            return false;
        }
        //truong so luong
        if(trim($('newStockIsdnForm.quantity').value) == "") {
            $('message').innerHTML = '<s:text name="ERR.ISN.005"/>';
            //                    $('message').innerHTML = "!!!Lỗi. Trường số lượng không được để trống";
            $('newStockIsdnForm.quantity').select();
            $('newStockIsdnForm.quantity').focus();
            return false;
        }

        return true;
    }





    //kiem tra tinh hop le cua cac truong
    checkValidFields = function() {
        var startStockIsdn = trim($('newStockIsdnForm.startStockIsdn').value);
        var endStockIsdn = trim($('newStockIsdnForm.endStockIsdn').value);
        var quantity = trim($('newStockIsdnForm.quantity').value.replace(/\,/g,""));

        //truong dau dai phai la so nguyen duong
        if(!isInteger(startStockIsdn)) {
            $('message').innerHTML = '<s:text name="ERR.ISN.006"/>';
            //                    $('message').innerHTML = "!!!Lỗi. Số đầu dải phải là số không âm";
            $('newStockIsdnForm.startStockIsdn').select();
            $('newStockIsdnForm.startStockIsdn').focus();
            return false;
        }
        //truong cuoi dai phai la so nguyen duong
        if(!isInteger(endStockIsdn)) {
            $('message').innerHTML = '<s:text name="ERR.ISN.007"/>';
            //                    $('message').innerHTML = "!!!Lỗi. Số cuối dải phải là số không âm";
            $('newStockIsdnForm.endStockIsdn').select();
            $('newStockIsdnForm.endStockIsdn').focus();
            return false;
        }
        //truong so luong phai la so nguyen duong
        if(!isInteger(quantity)) {
            $('message').innerHTML = '<s:text name="ERR.ISN.008"/>';
            //                    $('message').innerHTML = "!!!Lỗi. Số lượng phải là số không âm";
            $('newStockIsdnForm.quantity').select();
            $('newStockIsdnForm.quantity').focus();
            return false;
        }
        //so dau dai va so cuoi dai phai co chieu dai = nhau
        if(startStockIsdn.length != endStockIsdn.length) {
            $('message').innerHTML = '<s:text name="ERR.ISN.009"/>';
            //                    $('message').innerHTML = "!!!Lỗi. Số đầu dải và số cuối dải phải có chiều dài bằng nhau";
            $('newStockIsdnForm.startStockIsdn').select();
            $('newStockIsdnForm.startStockIsdn').focus();
            return false;
        }
        //so dau dai khong duoc lon hon so cuoi dai
        if(startStockIsdn > endStockIsdn) {
            $('message').innerHTML = '<s:text name="ERR.ISN.010"/>';
            //$('message').innerHTML = "!!!Lỗi. Số đầu dải không được lớn hơn số cuối dải";
            $('newStockIsdnForm.startStockIsdn').select();
            $('newStockIsdnForm.startStockIsdn').focus();
            return false;
        }

        //han che so luong so
    <%--s:if test="#request.listLocation != null &&
#request.listLocation.size() != 0">
            var tmpIsdnHeader =
trim($('newStockIsdnForm.stockPstnDistrict').value);
            var tmpStartStockIsdn =  startStockIsdn;
                if((tmpStartStockIsdn.length < 7) ||
(tmpStartStockIsdn.length > 9)) {
                    $('message').innerHTML = "!!!Lỗi. Số Homephone và
PSTN chỉ được phép chứa 7-9 chữ số";
                    $('newStockIsdnForm.startStockIsdn').select();
                    $('newStockIsdnForm.startStockIsdn').focus();
                    return false;
                }
    </s:if>
    <s:else>
                if((startStockIsdn.length != 9) &&
(startStockIsdn.length != 10)) {
                    $('message').innerHTML = "!!!Lỗi. Số Mobile chỉ
được phép chứa 9-10 chữ số";
                    $('newStockIsdnForm.startStockIsdn').select();
                    $('newStockIsdnForm.startStockIsdn').focus();
                    return false;
                }
    </s:else--%>

            //so cuoi dai - so dau dai + 1 != so luong
            if((endStockIsdn * 1 - startStockIsdn * 1 + 1) != quantity)
{
                $('message').innerHTML = '<s:text name="ERR.ISN.011"/>';
                //                    $('message').innerHTML = "!!!Lỗi. Số lượng số cần tạo không hợp lệ";
                $('newStockIsdnForm.quantity').select();
                $('newStockIsdnForm.quantity').focus();
                return false;
            }

            //so luogn so vuot qua so luong cho phep
            if(quantity * 1 > 2000000) {
                $('message').innrHTML = '<s:text name="ERR.ISN.012"/>';
                //$('message').innerHTML = "!!!Lỗi. Số lượng số cần tạo vượt quá 2,000,000 số";
                $('newStockIsdnForm.quantity').select();
                $('newStockIsdnForm.quantity').focus();
                return false;
            }

            //trim cac truong can thiet
            $('newStockIsdnForm.startStockIsdn').value = startStockIsdn;
            $('newStockIsdnForm.endStockIsdn').value = endStockIsdn;
            $('newStockIsdnForm.quantity').value = quantity;


            return true;
        }


    
    //them dai isdn moi
    checkValidate = function() {
        var result = checkRequiredFields() && checkValidFields();
        if(result) {
            
        }
        return (result);
    }

        changeServiceType = function (serviceType) {
            // TuTM1 04/03/2012 Fix ATTT CSRF
            gotoAction("bodyContent", 'createStockIsdnAction!changeServiceType.do?newStockIsdnForm.serviceType=' 
                + serviceType + "&" + token.getTokenParamString());
        }

        changeProvince = function () {
            var serviceType = document.getElementById("newStockIsdnForm.serviceType").value;
            var location = document.getElementById("newStockIsdnForm.location").value;
            // TuTM1 04/03/2012 Fix ATTT CSRF
            gotoAction("bodyContent", 'createStockIsdnAction!changeProvince.do?newStockIsdnForm.location=' 
                + location + '&newStockIsdnForm.serviceType=' + serviceType + "&" + token.getTokenParamString());
        }

    
</script>

