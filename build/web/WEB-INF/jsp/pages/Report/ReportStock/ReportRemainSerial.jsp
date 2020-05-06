<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : ReportRemainSerial
    Created on : Sep 1, 2010, 1:42:47 PM
    Author     : NamLT
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page import="com.viettel.im.common.util.ResourceBundleUtils"%>

<%
            request.setAttribute("contextPath", request.getContextPath());
//request.setAttribute("lstStockGoods", request.getSession().getAttribute("lstStockGoods"));
%>

<s:form action="ViewStockDetailAction!viewStockStaffDetail.do"  theme="simple" method="POST" id="goodsForm">
<s:token/>


    <tags:imPanel title="MSG.store.infor">
        <!--s:hidden name="goodsForm.ownerId"/-->
        <s:hidden name="goodsForm.ownerType" id="ownerType"/>
        <s:hidden name="goodsForm.viewType"/>


        <table class="inputTbl6Col" >
            <tr>
                <td colspan="4">
                    <tags:displayResult property="returnMsg" id="returnMsgClient" type="key" />
                </td
            </tr>
            <tr>
                <td class="label">
                    <tags:label key="MSG.store.code" required="true" />
                </td>
                <td class="text" colspan="4" align="center" >

                    <s:hidden theme="simple" name="goodsForm.ownerId" id="goodsForm.ownerId"/>

                    <tags:imSearch codeVariable="goodsForm.shopCode" nameVariable="goodsForm.shopName"
                                   codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                   searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                   searchMethodName="getListShop"
                                   elementNeedClearContent="goodsForm.ownerCode;goodsForm.ownerName"
                                   getNameMethod="getShopName"/>
                    <%--
                    <table width="100%">
                        <tr>
                            <td width="30%">
                                <s:textfield theme="simple" name="goodsForm.ownerCode" onblur="getOwnerName()" id="goodsForm.ownerCode" cssClass="txtInputFull"/>
                            </td>
                            <td width="70%">
                                <s:textfield theme="simple" name="goodsForm.ownerName" id="goodsForm.ownerName" cssClass="txtInputFull"/>
                            </td>
                        </tr>
                    </table>
                    --%>
                </td>
                <td class="label">&nbsp;</td>

            </tr>
            <tr>
                <td class="label">
                    <tags:label key="MSG.stock.staff.code"/>
                </td>
                <td class="text" colspan="4" align="center" >

                    <tags:imSearch codeVariable="goodsForm.ownerCode" nameVariable="goodsForm.ownerName"
                                   codeLabel="MSG.stock.staff.code" nameLabel="MSG.staff.name"
                                   searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                   searchMethodName="getListCTVDB"
                                   otherParam="goodsForm.shopCode"
                                   getNameMethod="getStaffName"/>

                </td>
                <td class="label">&nbsp;</td>

            </tr>

            <tr>
                <td class="label">
                    <tags:label key="MSG.ctv.code"/>
                </td>
                <td class="text" colspan="4" align="center" >

                    <tags:imSearch codeVariable="goodsForm.staffCode" nameVariable="goodsForm.staffName"
                                   codeLabel="MSG.stock.staff.code" nameLabel="MSG.staff.name"
                                   searchClassName="com.viettel.im.database.DAO.ReportRemainSerialDAO"
                                   searchMethodName="getListCTV"
                                   otherParam="goodsForm.ownerCode"
                                   getNameMethod="getListCTV"/>

                </td>
                <td class="label">&nbsp;</td>

            </tr>


            <tr>
                <td class="label">
                    <tags:label key="MSG.generates.goods"/>
                </td>

                <td class="text" colspan="4" align="center">
                    <s:hidden name="goodsForm.stockModelId" id="goodForm.stockModelId"/>
                    <tags:imSearch codeVariable="goodsForm.stockModelCode" nameVariable="goodsForm.stockModelName"
                                   codeLabel="MSG.stockModelId" nameLabel="MSG.stockModelName"
                                   searchClassName="com.viettel.im.database.DAO.ReportRemainSerialDAO"
                                   searchMethodName="getListStockModel" otherParam="ownerType;goodsForm.ownerCode"
                                   getNameMethod="getListStockModel"/>
                </td>
            </tr>
            <tr>
            <div id="dvCheckbox" align="center" style="width:50%; padding-bottom:5px;">
                <td class="label">

                    <s:checkbox id="cbIsStaff" name="goodsForm.isStaff"  />  <label>${fn:escapeXml(imDef:imGetText('MSG.SAE.210'))}</label>

                </td>
                <td class="label">
                    <s:checkbox id="cbIsStaffOwner" name="goodsForm.isStaffOwner" />  <label>${fn:escapeXml(imDef:imGetText('MSG.SAE.211'))}</label>


                </td>

                <td class="label">
                    <s:checkbox id="cbIsShop" name="goodsForm.isShop" />  <label>${fn:escapeXml(imDef:imGetText('MSG.SAE.212'))}</label>


                </td>

                <td class="label" style="display:none">
                    <s:checkbox id="cbIsShopOwner" name="goodsForm.isShopOwner" />  <label>${fn:escapeXml(imDef:imGetText('MSG.SAE.213'))}</label>


                </td>
            </div>

        </tr>





        <tr>
            <td  colspan="4" align="center">
                <sx:div id ="resultDownload">
                    <jsp:include page="../../StockManagment/Common/downloadDetailSerial.jsp"/>
                </sx:div>
            </td>
        </tr>
    </table>

    <div align="center" style="padding-top:5px; padding-bottom:5px;" >



        <tags:submit confirm="false" formId="goodsForm" validateFunction="checkSelected()"
                     showLoadingText="true" value="MSG.exportDetailReport" targets="bodyContent"
                     preAction="reportStockSerialRemain!exportStockStaffDetail.do"/>





    </div>
</tags:imPanel>






</s:form>

<script>

    viewSerial = function (ownerType, ownerId, stockModelId, stateId,quantity,maxView) {
        $('returnMsgClient').innerHTML="";
        if(quantity*1>maxView*1){
            $('returnMsgClient').innerHTML='<s:text name="stock.viewDetailSerial.maxResult"/>';
            return false;
        }
        openDialog("${contextPath}/ViewStockDetailAction!viewDetailSerial.do?ownerType=" + ownerType + "&ownerId=" + ownerId + "&stockModelId=" + stockModelId + "&stateId=" + stateId, 800, 700);
    }

    checkSelected =function() {
        if (document.getElementById('cbIsStaff').checked==false && document.getElementById('cbIsStaffOwner').checked==false
            && document.getElementById('cbIsShop').checked==false ) {
            alert ('Chưa chọn tiêu chí xuất báo cáo');
            return false;
        }

        if ($('goodsForm.ownerCode').value=="" && document.getElementById('cbIsStaff').checked==true && document.getElementById('cbIsStaffOwner').checked==true &&
            document.getElementById('cbIsShop').checked==false )  {
            alert('Bạn phải nhập mã nhân viên để lấy báo cáo');
            return false;
        }

         if((trim($('goodsForm.shopCode').value)== "" )) {
            $('returnMsgClient').innerHTML = '<s:text name="ERR.STK.004"/>';
            $('goodsForm.shopCode').select();
            $('goodsForm.shopCode').focus();
            return false;
        }
        return true;
    }


</script>
