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
            <s:form action="channelAction!addOrAcceptLimitStaff" theme="simple" enctype="multipart/form-data"  method="post" id="staffForm">
                <s:token/>

                <s:hidden name="staffForm.staffId" id="staffForm.staffId"/>
                <s:hidden name="staffForm.shopId" id="staffForm.shopId"/>

                <table class="inputTbl">
                    <s:if test="#request.roleLimitCreater == 'BR_LIMIT_CREATER' || #request.roleSpecialLimit == 'TRUE'">
                        <tr>
                            <td colspan="4" style="color: red"><s:text name="staffForm.noteLimit" /></td>
                        </tr>
                        <tr>
                            <td >Limit Money</td>
                            <td class="oddColumn">
                                <s:textfield name="staffForm.limitMoney" id="staffForm.limitMoney" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                            <td >Limit Day</td>
                            <td>
                                <s:textfield name="staffForm.limitDay" id="staffForm.limitDay" maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                            </td>
                        </tr>
                        <tr>
                            <td >User created</td>
                            <td class="oddColumn">
                                <s:textfield name="staffForm.limitCreateUser" id="staffForm.limitCreateUser" maxlength="1000" cssClass="txtInputFull" readonly="true" />
                            <td >Create Time Limit </td>
                            <td class="oddColumn">
                                <tags:dateChooser property="staffForm.limitCreateTime" styleClass="txtInputFull" id="staffForm.limitCreateTime"    />
                            </td>
                        </tr>
                    </s:if>
                    <s:elseif test="#request.roleLimitAccept == 'BR_LIMIT_ACCEPT'">
                        <tr>
                            <td >Limit Money</td>
                            <td class="oddColumn">
                                <s:textfield name="staffForm.limitMoney" id="staffForm.limitMoney" maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                            <td >Limit Day</td>
                            <td>
                                <s:textfield name="staffForm.limitDay" id="staffForm.limitDay" maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td >User created</td>
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
                        <tr>
                            <td >Status</td>
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
                        <tr>
                            <td >Status</td>
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
                        <tr id="trImpByFile">
                            <td class="label" ><tags:label key="MES.CHL.005" required="true" /></td>
                            <td  class="text" >
                                <tags:imFileUpload
                                    name="staffForm.clientFileName"
                                    id="staffForm.clientFileName" serverFileName="staffForm.serverFileName" extension="pdf"/>
                            </td>
                        </tr>
                    </s:if>
                </table>
            </s:form>

            <!-- phan nut tac vu, dong popup -->
            <s:if test="#request.roleLimitCreater == 'BR_LIMIT_CREATER' || #request.roleSpecialLimit == 'TRUE'">
                <div align="center" style="padding-top:20px; padding-bottom:5px;">
                    <input type="button" onclick="addStaff();"  value="${fn:escapeXml(imDef:imGetText('MES.CHL.097'))}"/>
                    <input type="button" onclick="cancelAddStaff();"  value="${fn:escapeXml(imDef:imGetText('MES.CHL.073'))}"/>
                </div>
            </s:if>

            <s:if test="#request.roleLimitAccept == 'BR_LIMIT_ACCEPT' && ( staffForm.limitMoney !=null && staffForm.limitMoney !='' )">
                <div align="center" style="padding-top:20px; ">
                    <tags:submit id="accept" formId="staffForm" showLoadingText="false" targets="minwidth" 
                                 value="Accept Limit" preAction="channelAction!acceptLimitStaff.do" cssStyle="width: 100px; "/>
                    <tags:submit id="notAccept" formId="staffForm" showLoadingText="false" targets="minwidth"
                                 value="Not Accept Limit" preAction="channelAction!notAcceptLimitStaff.do" cssStyle="width: 100px; "/>
                </div>    
            </s:if>
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

        if (!isInteger(trim($('staffForm.limitDay').value)) || trim($('staffForm.limitDay').value) == "") {
            $('message').innerHTML = 'Value must be an integer!';
            $('staffForm.limitDay').select();
            $('staffForm.limitDay').focus();
            return false;
        }
        if (!isInteger(trim($('staffForm.limitMoney').value)) || trim($('staffForm.limitMoney').value) == "") {
            $('message').innerHTML = 'Value must be an integer!';
            $('staffForm.limitMoney').select();
            $('staffForm.limitMoney').focus();
            return false;
        }
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
