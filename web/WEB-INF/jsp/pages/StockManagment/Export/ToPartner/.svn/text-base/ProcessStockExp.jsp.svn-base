<%-- 
    Document   : ProcessStockExp.jsp
    Created on : Feb 25, 2010, 2:23:18 PM
    Author     : NamNX
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display" %>

<%
            request.setAttribute("contextPath", request.getContextPath());

%>

<tags:imPanel title="MSG.exp.to.partner">
    <!-- phan hien thi thong tin message -->
    <div style="width:100%">
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
        <tags:displayResultList property="lstError" type="key"/>
    </div>
    <!-- phan hien thi thong tin mat hang can nhap kho -->
    <s:form action="processStockExpAction!exportToPartner" theme="simple" method="post" id="exportStockToPartnerForm">
<s:token/>

            <fieldset class="imFieldsetNoLegend">
                <table class="inputTbl6Col">
                    <tr>
                        <td style="width:15%; "> <tags:label key="MSG.fromStore"/></td>
                        <td class="oddColumn" style="width:25%; ">
                            <tags:imSearch codeVariable="exportStockToPartnerForm.fromOwnerCode" nameVariable="exportStockToPartnerForm.fromOwnerName"
                                           codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                           searchClassName="com.viettel.im.database.DAO.ReportAccountAgentDAO"
                                           searchMethodName="getListShop"
                                           roleList=""/>
                            <%--getNameMethod="getShopName"/>--%>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:15%; "><tags:label key="MSG.expNoteId" required="true"/></td>
                        <td class="oddColumn" style="width:25%; ">
                            <s:textfield name="exportStockToPartnerForm.actionCode" id="actionCode" readOnly="true" cssClass="txtInputFull"/>
                        </td>
                        <td style="width:10%; "><tags:label key="MSG.exportDate" required="true"/></td>
                        <td>
                            <tags:dateChooser property="exportStockToPartnerForm.expDate" id="exportStockToPartnerForm.expDate" styleClass="txtInputFull" readOnly="true"/>
                        </td>
                        <td><tags:label key="MSG.partner" required="true"/></td>
                        <td class="oddColumn">
                            <!--                            --Chọn đối tác---->
                            <tags:imSelect name="exportStockToPartnerForm.partnerId"
                                           id="partnerId"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="MSG.GOD.215"
                                           list="listPartners"
                                           listKey="partnerId" listValue="partnerName"/>
                        </td>
                    </tr>                    
                    <tr>
                        <td><tags:label key="MSG.stock.stock.type" required="true"/></td>
                        <td class="oddColumn">
                            <!--                            --Chọn loại mặt hàng---->
                            <tags:imSelect name="exportStockToPartnerForm.stockTypeId"
                                           id="stockTypeId"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="MSG.GOD.216"
                                           list="listStockTypes"
                                           listKey="stockTypeId" listValue="name"
                                           onchange="changeStockType(this.value)"/>
                        </td>                        
                        <td><tags:label key="MSG.generates.goods" required="true"/></td>
                        <td class="oddColumn">
                            <!--                            --Chọn mặt hàng---->
                            <tags:imSelect name="exportStockToPartnerForm.stockModelId"
                                           id="stockModelId"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="MSG.GOD.217"
                                           list="listStockModels"
                                           listKey="stockModelId" listValue="name"
                                           onchange="changeStockModel(this.value)"/>
                        </td>
                        <td class="label"><tags:label key="MSG.generates.status" required="true"/></td>
                        <td class="oddColumn">
                            <!--                            --Chọn trạng thái---->
                            <tags:imSelect name="exportStockToPartnerForm.status" id="status"
                                           cssClass="txtInputFull" disabled="false"
                                           list="1:MSG.GOD.242,3:MSG.GOD.243"
                                           headerKey="" headerValue="MSG.GOD.189"/>
                        </td>
                    </tr>
                    <tr>
                        <td  class="label" style="width:10%"><tags:label key="MSG.generates.file_data" required="true"/></td>
                        <td class="text" colspan="7" style="width:90%">
                            <tags:imFileUpload name="exportStockToPartnerForm.clientFileName" id="exportStockToPartnerForm.clientFileName" serverFileName="exportStockToPartnerForm.serverFileName" cssStyle="width:500px;" extension="xls"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6" align="center">
                            <tags:submit formId="exportStockToPartnerForm"
                                         showLoadingText="true"
                                         cssStyle="width:80px;"
                                         targets="bodyContent"
                                         value="MSG.export"
                                         validateFunction="checkValidate()"
                                         confirm="true"
                                         confirmText="Bạn chắc chắn muốn xuất cân kho"
                                         preAction="processStockExpAction!exportToPartner.do"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </s:form>
    </tags:imPanel>
    <script language="javascript">

        $('actionCode').focus();

        checkValidate = function() {
            var result = checkRequiredFields();
            if(result) {
                //startProgressBar();
            }
            return result;
        }

 
        //xu ly su kien khi thay doi loai mat hang
        changeStockType = function(stockTypeId) {
            updateData("${contextPath}/importFromPartnerAction!getComboboxSource.do?target=stockModelId,profilePattern&stockTypeId=" + stockTypeId);
        }

        //xu ly su kien khi thay doi mat  hang
        changeStockModel = function(stockModelId) {
            updateData("${contextPath}/importFromPartnerAction!updateProfilePattern.do?target=profilePattern&stockModelId=" + stockModelId);
        }

        //ham kiem tra cac truong bat buoc
        checkRequiredFields = function() {

            if(trim($('actionCode').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.019"/>';
                //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn xuất mã phiếu xuất";
                $('actionCode').select();
                $('actionCode').focus();
                return false;
            }

            if(trim($('actionCode').value).length > 20) {
                $('message').innerHTML= '<s:text name="ERR.STK.020"/>';
                //$('message').innerHTML = "!!!Lỗi. Mã phiếu xuất không được lớn hơn 20 ký tự!";
                $('actionCode').select();
                $('actionCode').focus();
                return false;
            }
            $('actionCode').value = trim($('actionCode').value);

            if(isHtmlTagFormat($('actionCode').value)){
                $('message').innerHTML= '<s:text name="ERR.STK.021"/>';
                //$( 'message' ).innerHTML = "Không nên nhập thẻ html vào mã phiếu xuất";
                $('actionCode').select();
                return;
            }


            var impDate = dojo.widget.byId("exportStockToPartnerForm.expDate");
            var strImpDate = impDate.domNode.childNodes[1].value;
            if(trim(strImpDate) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.022"/>';
                //$('message').innerHTML = "!!!Lỗi. Ngày xuất không được để trống";
                impDate.domNode.childNodes[1].select();
                impDate.domNode.childNodes[1].focus();
                return false;
            }


            if(trim($('partnerId').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.023"/>';
                //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn đối tác";
                $('partnerId').focus();
                return false;
            }

            if(trim($('stockTypeId').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.024"/>';
                //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn loại hàng hàng hoá";
                $('stockTypeId').focus();
                return false;
            }
            if(trim($('stockModelId').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.025"/>';
                //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn hàng hoá";
                $('stockModelId').focus();
                return false;
            }

            if(trim($('status').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.026"/>';
                //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn trạng thái";
                $('status').select();
                $('status').focus();
                return false;
            }
           
            //nhap hang theo file -> ten file ko duoc de trong
            if($('exportStockToPartnerForm.clientFileName').value == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.027"/>';
                //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn file dữ liệu";
                $('exportStockToPartnerForm.clientFileName').focus();
                return false;
            }

            return true;
        }

    
    </script>
