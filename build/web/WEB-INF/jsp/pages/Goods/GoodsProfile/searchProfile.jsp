<%-- 
    Document   : searchProfile
    Created on : Dec 13, 2009, 11:29:31 PM
    Author     : Doan Thanh 8
    Desc       : tim kiem thong tin profile
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<tags:imPanel title="MSG.GOD.074">
<%--<tags:imPanel title="Tìm kiếm thông tin profile">--%>

    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan tim kiem thong tin (loc danh sach) -->
    <div class="divHasBorder">
        <s:form action="profileAction!searchProfile" theme="simple" enctype="multipart/form-data"  method="post" id="profileForm">
<s:token/>

            <s:hidden id="profileForm.stockTypeId" name="profileForm.stockTypeId"/>
            <table class="inputTbl5Col">
                <tr>
                    <td style="width:13%; ">
                        <tags:label key="MSG.GOD.007"/>
                        <!--                            007Mã mặt hàng-->
                    </td>
                    <td class="oddColumn" style="width:20%; ">
                        <s:textfield name="profileForm.stockModelCode" id="profileForm.stockModelCode" cssClass="txtInputFull"/>
                    </td>
                    <td style="width:15%; ">
                        <tags:label key="MSG.GOD.008"/>
                        <!--                            008Tên mặt hàng-->
                    </td>
                    <td class="oddColumn">
                        <s:textfield name="profileForm.stockModelName" id="profileForm.stockModelName" cssClass="txtInputFull"/>
                    </td>
                    <td style="width:85px; ">
                        <div align="right">
                            <sx:submit parseContent="true" executeScripts="true"
                                       formId="profileForm" loadingText="Processing..."
                                       cssStyle="width:80px;"
                                       showLoadingText="true" targets="divListProfiles"
                                       key="MSG.GOD.009"  beforeNotifyTopics="profileAction/searchProfile"/>
                        </div>
                    </td>
                    <td style="width:85px; ">
                        <div align="right">
                            <sx:submit parseContent="true" executeScripts="true"
                                       formId="profileForm" loadingText="Processing..."
                                       cssStyle="width:80px;"
                                       showLoadingText="true" targets="divDisplayInfo"
                                       key="MSG.GOD.010"  beforeNotifyTopics="profileAction/prepareAddSaleServices"/>

                            <!--                                <input type="button" value="Thêm mới" style="width:80px;" onclick="prepareAddSaleServices()">-->
                        </div>
                    </td>
                </tr>
                <%--tr>
                    <td>Loại mặt hàng</td>
                    <td class="oddColumn">
                        <s:select name="profileForm.stockTypeId"
                              id="profileForm.stockTypeId"
                              cssClass="txtInputFull"
                              list="%{#request.listStockTypes != null ? #request.listStockTypes : #{}}"
                              listKey="stockTypeId" listValue="name"
                              headerKey="" headerValue="--Chọn loại mặt hàng--"/>
                    </td>
                    <td>Tên profile</td>
                    <td class="oddColumn">
                        <s:textfield name="profileForm.profileName" id="profileForm.profileName" cssClass="txtInputFull"/>
                    </td>
                </tr--%>
            </table>
        </s:form>
    </div>

    <!-- phan hien thi danh sach cac dich vu ban hang-->
    <sx:div id="divListProfiles">
        <jsp:include page="listProfiles.jsp"/>
    </sx:div>

</tags:imPanel>

<script language="javascript">
    //focus vao truog dau tien
    $('profileForm.stockModelCode').select();
    $('profileForm.stockModelCode').focus();

    //lang nghe, xu ly su kien khi click nut "Tim kiem"
    dojo.event.topic.subscribe("profileAction/searchProfile", function(event, widget){
        widget.href = "profileAction!searchProfile.do";
    });

    dojo.event.topic.subscribe("profileAction/prepareAddSaleServices", function(event, widget){
        widget.href = "profileAction!prepareAddProfile.do";
    });


    //xu ly su kien onclick cua nut "Them" (them saleservices)
    prepareAddSaleServices = function() {
        gotoAction("divDisplayInfo", "${contextPath}/saleServicesAction!prepareAddSaleServices.do");
    }
</script>

