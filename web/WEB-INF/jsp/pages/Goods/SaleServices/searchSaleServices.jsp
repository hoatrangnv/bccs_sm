<%-- 
    Document   : searchSaleServices
    Created on : Nov 2, 2009, 6:38:33 PM
    Author     : Doan Thanh 8
    Purpose    : tim kiem dich vu ban hang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<tags:imPanel title="MSG.GOD.124">
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan tim kiem thong tin (loc danh sach) -->
    <div class="divHasBorder">
        <s:form action="saleServicesAction" theme="simple" method="post" id="saleServicesForm">
<s:token/>

            <table class="inputTbl6Col">
                <tr>
                    <td style="width:10%; ">
                        <tags:label key="MSG.GOD.094"/>
                        <!--                            Mã dịch vụ094-->
                    </td>
                    <td class="oddColumn" style="width:25%; ">
                        <s:textfield name="saleServicesForm.code" id="saleServicesForm.code" maxlength="50" cssClass="txtInputFull"/>
                    </td>
                    <td style="width:10%; ">
                        <tags:label key="MSG.GOD.095"/>
                        <!--                            Tên dịch vụ-->
                    </td>
                    <td class="oddColumn">
                        <s:textfield name="saleServicesForm.name" id="saleServicesForm.name" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <!--td>Ghi chú</td>
                    <td class="oddColumn">
                    <s:textfield name="saleServicesForm.notes" id="saleServicesForm.notes" maxlength="450" cssClass="txtInputFull"/>
                </td-->
                    <td style="width:85px; ">
                        <div align="right">
                            <sx:submit id="btnSearchSaleServices" parseContent="true" executeScripts="true"
                                       formId="saleServicesForm" loadingText="Processing..."
                                       cssStyle="width:80px;"
                                       showLoadingText="true" targets="divDisplayInfo"
                                       key="MSG.GOD.009"  beforeNotifyTopics="saleServicesOverviewAction/searchSaleServices"/>
                        </div>
                    </td>
                    <td style="width:85px; ">
                        <div align="right">
                            <tags:submit id="btnPrepareAddSaleServices"
                                         formId="saleServicesForm"
                                         showLoadingText="true"
                                         cssStyle="width:80px;"
                                         targets="divDisplayInfo"
                                         value="MSG.GOD.010"
                                         preAction="saleServicesAction!prepareAddSaleServicesFirstTime.do"/>
                        </div>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>

    <!-- phan hien thi danh sach cac dich vu ban hang-->
    <div>
        <jsp:include page="listSaleServices.jsp"/>
    </div>

</tags:imPanel>

<script language="javascript">
    //lang nghe, xu ly su kien khi click nut "Tim kiem"
    dojo.event.topic.subscribe("saleServicesOverviewAction/searchSaleServices", function(event, widget){
        widget.href = "saleServicesAction!searchSaleServices.do";
    });

    //xu ly su kien onclick cua nut "Them" (them saleservices)
    prepareAddSaleServices = function() {
        gotoAction("divDisplayInfo", "${contextPath}/saleServicesAction!prepareAddSaleServices.do");
    }
</script>
