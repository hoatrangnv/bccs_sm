<%--
    Document   : searchStockModel
    Created on : Nov 20, 2009, 9:34:40 AM
    Author     : Doan Thanh 8
    Desc       : tim kiem thong tin mat hang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<tags:imPanel title="MSG.GOD.267">
        <!-- phan hien thi message -->
        <div>
            <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
        </div>

        <!-- phan tim kiem thong tin (loc danh sach) -->
        <div class="divHasBorder">
            <s:form action="goodsDefAction!searchStockModel.do" theme="simple" method="post" id="stockModelForm">
<s:token/>

                <s:hidden name="stockModelForm.stockTypeId" id="stockModelForm.stockTypeId"/>
                
                <table class="inputTbl6Col">
                    <tr>
<!--                     <td style="width:13%; ">Label form</td>-->                      
                        <td style="width:15%; ">
                            <tags:label key="MSG.GOD.007"/>
                        </td>
<!--                        <td style="width:13%; ">Mã mặt hàng</td>-->
                        <td class="oddColumn" style="width:25%; ">
                            <s:textfield name="stockModelForm.stockModelCode" id="stockModelForm.stockModelCode" maxlength="50" cssClass="txtInputFull"/>
                        </td>
                        <td style="width:15%; ">
                            <tags:label key="MSG.GOD.008"/>
                        </td>
<!--                        <td style="width:15%; ">Tên mặt hàng</td>-->
                        <td class="oddColumn" style="width:25%; ">
                            <s:textfield name="stockModelForm.name" id="stockModelForm.name" maxlength="100" cssClass="txtInputFull"/>
                        </td>
                        
                        <!-- hiepd kiem tra quan ly theo kenh -->
                        <!-- sử dụng list combo box -->
                        <td  class="text" style="width:20%; ">
                            <tags:imSelect name="stockModelForm.checkStockChannel"
                                       id="stockModelForm.checkStockChannel"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GD.SelectManagementType"
                                       list="listCheckStockChannel"
                                       listKey="idCheckStockChannel" listValue="valueCheckStockChannel"/>
                                       
                        </td>
                     </tr>
                     <tr>
                        <td></td>
                        <td></td>
                        <td>
                            <div align="left">
                                <tags:submit id="btnSearchStockModel"
                                             formId="stockModelForm"
                                             showLoadingText="true"
                                             cssStyle="width:80px;"
                                             targets="divListStockModels"
                                             value="MSG.GOD.009"
                                             preAction="goodsDefAction!searchStockModel.do"/>
                            </div>
                        </td>
                        <td>
                            <div align="left">
                                <tags:submit id="btnPrepareAddStockModel"
                                             formId="stockModelForm"
                                             showLoadingText="true"
                                             cssStyle="width:80px;"
                                             targets="divDisplayInfo"
                                             value="MSG.GOD.010"
                                             preAction="goodsDefAction!prepareAddStockModelFirstTime.do"/>        
                            </div>
                        </td>
                        <td></td>
                    </tr>
                </table>
            </s:form>
        </div>

        <!-- phan hien thi danh sach cac dich vu ban hang-->
        <sx:div id="divListStockModels">
            <jsp:include page="listStockModels.jsp"/>
        </sx:div>

</tags:imPanel>

<script language="javascript">
    //focus vao truog dau tien
    $('stockModelForm.stockModelCode').select();
    $('stockModelForm.stockModelCode').focus();

    //bat fim enter tren 2 textbox
    searchStockModel = function(keyCode) {
        if(keyCode == 13) {
            document.getElementById('btnSearchStockModel').click();
        }
    }

    //lang nghe, xu ly su kien khi click nut "Tim kiem"
    dojo.event.topic.subscribe("goodsDefAction/searchStockModel", function(event, widget){
        widget.href = "goodsDefAction!searchStockModel.do";
    });

    //xu ly su kien onclick cua nut "Them" (them saleservices)
    prepareAddSaleServices = function() {
        gotoAction("divDisplayInfo", "${contextPath}/saleServicesAction!prepareAddSaleServices.do");
    }
</script>
