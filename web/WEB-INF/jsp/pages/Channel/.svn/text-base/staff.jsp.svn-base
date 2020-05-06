<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : staff
    Created on : Apr 20, 2009, 2:34:48 PM
    Author     : Doan Thanh 8
--%>

<%--
    Note: hien thi thong tin ve nhan vien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>
<div id="check">
    <tags:imPanel title="MES.CHL.136" >
        <!-- phan hien thi message -->
        <div id="idDivForm">
            <div>
                <tags:displayResult id="message" property="message" type="key"/>
            </div>

            <!-- phan thong tin ve staff -->
            <s:form action="channelAction!addOrEditStaff" theme="simple" enctype="multipart/form-data"  method="post" id="staffForm">
                <s:token/>

                <s:hidden name="staffForm.staffId" id="staffForm.staffId"/>
                <s:hidden name="staffForm.shopId" id="staffForm.shopId"/>

                <table class="inputTbl">
                    <tr>
                        <%--<td>Mã nhân viên (<font color="red">*</font>)</td>--%>
                        <%--lamnt change staff code to User--%>
                        <%--<td ><tags:label key="MES.CHL.105" required="true"/></td>--%>
                        <td ><tags:label key="LABLE.USER" required="true"/></td>
                        <%--end--%>
                        <td class="oddColumn">
                            <s:if test="staffForm.staffId == null || staffForm.staffId *1 <= 0 ">
                                <s:textfield name="staffForm.staffCode" id="shopForm.staffCode" maxlength="1000" cssClass="txtInputFull" required="true"/>
                            </s:if>
                            <s:else>
                                <s:textfield name="staffForm.staffCode" id="shopForm.staffCode" maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                            </s:else>
                        </td>
                        <%--<td>Tên nhân viên (<font color="red">*</font>)</td>--%>
                        <td ><tags:label key="MES.CHL.106" required="true"/></td>
                        <td>
                            <s:textfield name="staffForm.name" id="staffForm.name" maxlength="1000" cssClass="txtInputFull" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <%--<td>Ngày sinh (<font color="red">*</font>)</td>--%>
                        <td ><tags:label key="MES.CHL.082" required="true"/></td>
                        <td class="oddColumn">
                            <tags:dateChooser property="staffForm.birthday" styleClass="txtInputFull" id="birthday"/>
                        </td>
                        <%--<td>Số CMT (<font color="red">*</font>)</td>--%>
                        <td ><tags:label key="MES.CHL.057" required="true"/></td>
                        <td>
                            <s:textfield name="staffForm.idNo" id="staffForm.idNo" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                        </td>
                    </tr>
                    <tr>
                        <%--<td>Ngày cấp (<font color="red">*</font>)</td>--%>
                        <td ><tags:label key="MES.CHL.067" required="true"/></td>
                        <td class="oddColumn">
                            <tags:dateChooser property="staffForm.idIssueDate" styleClass="txtInputFull" id="idIssueDate"/>
                        </td>
                        <%--<td>Nơi cấp (<font color="red">*</font>)</td>--%>
                        <td ><tags:label key="MES.CHL.066" required="true"/></td>
                        <td>
                            <s:textfield name="staffForm.idIssuePlace" id="staffForm.idIssuePlace" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                        </td>
                    </tr>
                    <tr>
                        <%--<td>Điện thoại</td>--%>
                        <td ><tags:label key="MES.CHL.095"  required="true"/></td>
                        <td class="oddColumn">
                            <s:textfield name="staffForm.tel" id="staffForm.tel" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                        </td>
                        <%--<td>Fax</td>--%>
                        <%--lamnt 14032017 --%>
                        <%--<td ><tags:label key="MES.CHL.096" /></td>
                        <td>
                            <s:textfield name="shopForm.fax" id="shopForm.fax" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                        </td>--%>
                        <%--end--%>
                        <%--<td>Emola number</td>--%>
                        <%--lamnt 150317--%>
                        <%--LinhNBV start modified on May 23 2015:Fixed change eMola number.  --%>
                        <s:if test="staffForm.isdnWallet != null">
                            <td ><tags:label key="LABLE.EmolaNumber"/></td>
                            <td>
                                <s:textfield name="staffForm.isdnWallet" id="staffForm.isdnWallet" maxlength="9" cssClass="txtInputFull" readonly="true"/>
                            </td>
                        </s:if>
                        <s:else>
                            <td ><tags:label key="LABLE.EmolaNumber"/></td>
                            <td>
                                <s:textfield name="staffForm.isdnWallet" id="staffForm.isdnWallet" maxlength="9" cssClass="txtInputFull" readonly="#request.readonly"/>
                            </td>
                        </s:else>
                        <%--end--%>
                    </tr>
                    <tr>
                        <%--<td>Địa chỉ</td>--%>
                        <%--<td ><tags:label key="MES.CHL.070" /></td>--%>
                        <td ><tags:label key="LABLE.EMAIL" /></td>
                        <td class="oddColumn">
                            <s:textfield name="staffForm.address" id="staffForm.address" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                        </td>
                        <%--<td>HR id (<font color="red">*</font>)</td>--%>
                        <%--lamnt 150317--%>
                        <td ><tags:label key="LABLE.HRID" required="true"/></td>
                        <td>
                            <s:textfield name="staffForm.hrId" id="staffForm.hrId" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                        </td>
                        <%--end--%>
                    </tr>
                    <tr>
                        <%--<td>Loại nhân viên (<font color="red">*</font>)</td>--%>
                        <td ><tags:label key="MES.CHL.137" required="true"/></td>
                        <td class="oddColumn">
                            <%--<s:select name="staffForm.channelTypeId"
                                      id="staffForm.channelTypeId"
                                      cssClass="txtInputFull"
                                      headerKey="" headerValue="--Chọn loại nhân viên--"
                                      list="%{#request.listChannelType != null ? #request.listChannelType :#{}}"
                                      listKey="channelTypeId" listValue="name"/>--%>
                            <tags:imSelect name="staffForm.channelTypeId"
                                           id="staffForm.channelTypeId"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="COM.CHL.010"
                                           list="listChannelType"
                                           listKey="channelTypeId" listValue="name"/>
                        </td>
                        <%--<td>Chức vụ (<font color="red">*</font>)</td>--%>
                        <td ><tags:label key="MES.CHL.127" required="true"/></td>
                        <td>
                            <%--<s:select name="staffForm.type"
                                      id="staffForm.type"
                                      cssClass="txtInputFull"
                                      headerKey="" headerValue="--Chọn chức vụ--"
                                      list="%{#request.listStaffType != null ? #request.listStaffType : #{}}"
                                      listKey="id.code" listValue="name"/>--%>
                            <tags:imSelect name="staffForm.type"
                                           id="staffForm.type"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="COM.CHL.011"
                                           list="listStaffType"
                                           listKey="code" listValue="name"/>
                        </td>
                    </tr>
                    <%--<s:if test="#request.roleLimitCreater == 'BR_LIMIT_CREATER'">
                        <tr style="display:none;">
                            <td colspan="4" style="color: red"><s:text name="staffForm.noteLimit" /></td>
                        </tr>
                        <tr style="display:none;">
                            <td >Limit Money</td>
                            <td class="oddColumn">
                                <s:textfield name="staffForm.limitMoney" id="staffForm.limitMoney" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                            <td >Limit Day</td>
                            <td>
                                <s:textfield name="staffForm.limitDay" id="staffForm.limitDay" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                            </td>
                        </tr>
                        <tr style="display:none;">
                            <td >Creater Limit Money</td>
                            <td class="oddColumn">
                                <s:textfield name="staffForm.limitCreateUser" id="staffForm.limitCreateUser" maxlength="1000" cssClass="txtInputFull" readonly="true" />
                            <td >Create Time Limit </td>
                            <td class="oddColumn">
                                <tags:dateChooser property="staffForm.limitCreateTime" styleClass="txtInputFull" id="staffForm.limitCreateTime"    />
                            </td>
                        </tr>
                    </s:if>
                    <s:elseif test="#request.roleLimitAccept == 'BR_LIMIT_ACCEPT'">
                        <tr style="display:none;">
                            <td >Limit Money</td>
                            <td class="oddColumn">
                                <s:textfield name="staffForm.limitMoney" id="staffForm.limitMoney" maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                            <td >Limit Day</td>
                            <td>
                                <s:textfield name="staffForm.limitDay" id="staffForm.limitDay" maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                            </td>
                        </tr>
                        <tr style="display:none;">
                            <td >Creater Limit Money</td>
                            <td class="oddColumn">
                                <s:textfield name="staffForm.limitCreateUser" id="staffForm.limitCreateUser" maxlength="1000" cssClass="txtInputFull" readonly="true" />
                            <td >Create Time Limit </td>
                            <td class="oddColumn">
                                <tags:dateChooser property="staffForm.limitCreateTime" styleClass="txtInputFull" id="staffForm.limitCreateTime"   />
                            </td>
                        </tr>
                    </s:elseif> 
                    <s:else>
                    </s:else> 
                    <s:if test="staffForm.limitApproveUser == null || staffForm.limitApprovetUser == '' ">    
                        <tr style="display:none;">
                            <td >Status Accept Limit</td>
                            <td class="oddColumn">
                                <s:textfield  value="Not Approve Limit" maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                            </td>
                            <s:if test="#request.roleSpecialLimit == 'TRUE'"> 
                                <td >End Time Limit </td>
                                <td class="oddColumn">
                                    <tags:dateChooser property="staffForm.limitEndTime" styleClass="txtInputFull" id="staffForm.limitEndTime"  />
                                </td>
                            </s:if>
                        </tr>
                    </s:if>
                    <s:else>
                        <tr style="display:none;">
                            <td >Status Accept Limit</td>
                            <td class="oddColumn">
                                <s:textfield  value="Approve Limit" maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                            </td>
                            <s:if test="#request.roleSpecialLimit == 'TRUE'"> 
                                <td >End Time Limit </td>
                                <td class="oddColumn">
                                    <tags:dateChooser property="staffForm.limitEndTime" styleClass="txtInputFull" id="staffForm.limitEndTime"  />
                                </td>
                            </s:if>
                        </tr>
                    </s:else>
                    <s:if test="#request.roleSpecialLimit == 'TRUE'"> 
                        <tr id="trImpByFile" style="display:none;">
                            <td class="label" ><tags:label key="MES.CHL.005" required="true" /></td>
                            <td  class="text" >
                                <tags:imFileUpload
                                    name="staffForm.clientFileName"
                                    id="staffForm.clientFileName" serverFileName="staffForm.serverFileName" extension="pdf"/>
                            </td>
                        </tr>
                    </s:if>

                    <%-- <tr>
                         <td class="label"><tags:label key="MES.CHL.015" required="true"/></td>
                         <td class="oddColumn" colspan="3">
                                 <tags:imSearch codeVariable="staffForm.shopCode"
                                                nameVariable="staffForm.shopName"
                                                codeLabel="MES.CHL.015" nameLabel="MES.CHL.016"
                                                elementNeedClearContent="staffForm.staffManagementCode;staffForm.staffManagementName"
                                                searchClassName="com.viettel.im.database.DAO.ChangeInformationStaffDAO"
                                                searchMethodName="getListShopSameLevel"
                                                getNameMethod="getListShopSameLevel"
                                                roleList="saleTransManagementf9Shop"/>
                         </td>
                     </tr>
                     
                     <tr>
                         <td class="label"><tags:label key="MES.CHL.059" required="true"/></td>
                         <td class="oddColumn" colspan="3">
                             <tags:imSearch codeVariable="staffForm.staffManagementCode" nameVariable="staffForm.staffManagementName"
                                            codeLabel="MES.CHL.064" nameLabel="MES.CHL.065"
                                            searchClassName="com.viettel.im.database.DAO.ChangeInformationStaffDAO"
                                            otherParam="staffForm.shopCode"
                                            searchMethodName="getListStaffManage"
                                            getNameMethod="getListStaffManage"
                                            roleList=""/>
                         </td>
                     </tr>--%>
                </table>
            </s:form>

            <!-- phan nut tac vu, dong popup -->
            <div align="center" style="padding-top:20px; padding-bottom:5px;">
                <%--<button style="width:80px;" onclick="addStaff()">Đồng ý</button>--%>
                <%--<s:if test="#request.roleLimitCreater == 'BR_LIMIT_CREATER'">--%>  
                <input type="button" onclick="addStaff();"  value="${fn:escapeXml(imDef:imGetText('MES.CHL.097'))}"/>
                <%--</s:if>--%>
                <%--<s:else>--%>
                    <!--<input type="button" onclick="addStaffNormal();"  value="${fn:escapeXml(imDef:imGetText('MES.CHL.097'))}"/>--> 
                <%--</s:else>--%> 

                <%--<button style="width:80px;" onclick="cancelAddStaff()">Bỏ qua</button>--%>
                <input type="button" onclick="cancelAddStaff();"  value="${fn:escapeXml(imDef:imGetText('MES.CHL.073'))}"/>
            </div>
            <%--<s:if test="#request.roleLimitAccept == 'BR_LIMIT_ACCEPT' && ( staffForm.limitMoney !=null && staffForm.limitMoney !='' )">
                <div align="center" style="padding-top:20px; " >
                    <tags:submit id="accept" formId="staffForm" showLoadingText="false" targets="minwidth" 
                                 value="Accept Limit" preAction="channelAction!acceptLimitStaff.do" cssStyle="width: 100px; "/>
                    <tags:submit id="notAccept" formId="staffForm" showLoadingText="false" targets="minwidth"
                                 value="Not Accept Limit" preAction="channelAction!notAcceptLimitStaff.do" cssStyle="width: 100px; "/>
                </div>    
            </s:if>--%>
        </div>   
    </tags:imPanel>
</div>


<s:if test="#request.staffMode == 'closePopup'">

    <script language="javascript">
                        window.opener.refreshListStaff();
                        window.close();
    </script>

</s:if>

<script language="javascript">
    addStaff = function() {
//        if($('staffForm.limitDay') != null && $('staffForm.limitMoney') != null ){

//        if (!isInteger(trim($('staffForm.limitDay').value)) || trim($('staffForm.limitDay').value) == "") {
//            $('message').innerHTML = 'Value must be an integer!';
//            $('staffForm.limitDay').select();
//            $('staffForm.limitDay').focus();
//            return false;
//        }
//        if (!isInteger(trim($('staffForm.limitMoney').value)) || trim($('staffForm.limitMoney').value) == "") {
//            $('message').innerHTML = 'Value must be an integer!';
//            $('staffForm.limitMoney').select();
//            $('staffForm.limitMoney').focus();
//            return false;
//        }
//        }    


        $('staffForm').submit();
        /*
         if(checkRequiredFields() && checkValidFields()) {
         $(priceForm).submit();
         }*/
    }
    addStaffNormal = function() {

        $('staffForm').submit();
        /*
         if(checkRequiredFields() && checkValidFields()) {
         $(priceForm).submit();
         }*/
    }

    cancelAddStaff = function() {
        window.close();
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        if (trim($('priceForm.price').value) == "") {
    <%--$('message').innerHTML = "Giá mặt hàng không được để trống";--%>
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.CHL.028')"/>';
                $('priceForm.price').select();
                $('priceForm.price').focus();
                return false;
            }
            if (trim($('priceForm.vat').value) == "") {
    <%--$('message').innerHTML = "VAT không được để trống";--%>
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.CHL.029')"/>';
                $('priceForm.vat').select();
                $('priceForm.vat').focus();
                return false;
            }
            /*
             if(trim($('saleServicesPriceForm.staDate').value) == "") {
             alert('Ngày bắt đầu không được để trống');
             $('saleServicesPriceForm.staDate').select();
             $('saleServicesPriceForm.staDate').focus();
             return false;
             }
             */
            if (trim($('priceForm.pricePolicy').value) == "") {
    <%--$('message').innerHTML = "Chọn Chính sách giá";--%>
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.CHL.040')"/>';
                $('priceForm.pricePolicy').focus();
                return false;
            }
            if (trim($('priceForm.status').value) == "") {
    <%--$('message').innerHTML = "Chọn trạng thái";--%>
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.CHL.031')"/>';
                $('priceForm.status').focus();
                return false;
            }

            return true;
        }

        //ham kiem tra du lieu cac truong co hop le hay ko
        checkValidFields = function() {
            if (!isInteger(trim($('priceForm.price').value))) {
    <%--$('message').innerHTML = "Giá dịch vụ phải là số không âm";--%>
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.CHL.032')"/>';
                $('priceForm.price').select();
                $('priceForm.price').focus();
                return false;
            }
            if (!isInteger(trim($('priceForm.vat').value))) {
    <%--$('message').innerHTML = "VAT phải là số không âm";--%>
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.CHL.033')"/>';
                $('priceForm.vat').select();
                $('priceForm.vat').focus();
                return false;
            }
            if ($('priceForm.vat').value < 0 || $('priceForm.vat').value > 100) {
    <%--$('message').innerHTML = "VAT phải là số không âm nằm trong khoảng 0 đến 100";--%>
                $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.CHL.034')"/>';
                $('priceForm.vat').select();
                $('priceForm.vat').focus();
                return false;
            }

            return true;
        }
        priceChange = function(id) {
            //alert('ukie');
            var tmp = $(id).value;
            tmp = tmp.replace(/\,/g, "");
            var tmp1 = addSeparatorsNF(tmp, '.', '.', ',');
            $(id).value = tmp1;
        }
        textFieldNF($('priceForm.price'));
</script>
