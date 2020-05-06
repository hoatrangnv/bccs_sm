<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : discountGoods
    Created on : Mar 24, 2009, 9:30:50 AM
    Author     : tamdt1
    Desc       : hien thi thong tin ve discountGoods (stockModel)
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

<sx:div id="sdivDiscountGoods" cssStyle="padding:3px; ">

    <!-- phan hien thi message -->
    <div>
        <tags:displayResult property="message" id="messageParam" type="key"/>
    </div>

    <!-- phan hien thi thong tin mat hang trong nhom CK -->
    <div class="divHasBorder">
        <s:form action="discountGroupAction!addOrEditDiscountGoods1" theme="simple" method="post" id="discountGoodsForm">
<s:token/>

            <s:hidden name="discountModelMapForm.discountModelMapId" id="discountModelMapForm.discountModelMapId"/>
            <s:hidden name="discountModelMapForm.discountGroupId" id="discountModelMapForm.discountGroupId"/>
            <s:hidden name="discountModelMapForm.status" id="discountModelMapForm.status"/>

            <table style="width:100%" class="inputTbl4Col">
                <tr>
<!--                    <td style="width:15%">Loại mặt hàng<s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if></td>-->
                    <td style="width:15%"><tags:label key="MSG.LST.041" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/></td>
                    <td class="oddColumn" style="width:25%">
                        <tags:imSelect name="discountModelMapForm.stockTypeId"
                                       id="stockTypeId"
                                       disabled="${fn:escapeXml(!(requestScope.isAddMode || requestScope.isEditMode))}"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.216"
                                       list="listStockType"
                                       listKey="stockTypeId" listValue="name"/>
                    </td>
<!--                    <td style="width:15%">Mặt hàng<s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if></td>-->
                    <td style="width:15%"><tags:label key="MSG.LST.042" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/></td>
                    <td>
                        <tags:imSearch codeVariable="discountModelMapForm.stockModelCode" nameVariable="discountModelMapForm.stockModelName"
                                       codeLabel="MSG.GOD.007" nameLabel="MSG.GOD.008"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName"
                                       otherParam="stockTypeId"
                                       readOnly="${fn:escapeXml(!(isAddMode || isEditMode))}"/>

                    </td>
                    <%--<td style="width:10%">Trạng thái<s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if></td>
                    <td>
                        <s:select name="discountModelMapForm.status"
                                  id="discountModelMapForm.status"
                                  disabled="%{!(#request.isAddMode || #request.isEditMode)}"
                                  cssClass="txtInputFull"
                                  list="#{'1':'Hiệu lực', '0':'Không hiệu lực'}"
                                  headerKey="" headerValue="--Chọn trạng thái--"/>
                    </td>--%>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <s:if test="#request.isAddMode || #request.isEditMode">
            <div align="center" style="vertical-align:top; ">
                <tags:submit id="btnAddOrEditDiscountGoods"
                             targets="sdivDiscountGoods"
                             formId="discountGoodsForm"
                             cssStyle="width:80px;"
                             confirm="true"
                             confirmText="MSG.GOD.017"
                             value="MSG.GOD.016"
                             preAction="discountGroupAction!addOrEditDiscountGoods.do"
                             showLoadingText="true"/>

                <tags:submit id="btnDisplayDiscountGoods"
                             targets="sdivDiscountGoods"
                             formId="discountGoodsForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.018"
                             preAction="discountGroupAction!displayDiscountGoods.do"
                             showLoadingText="true"/>
            </div>
            <script language="javascript">
                disableTab('sxdivDiscountGroup');
                disableTab('sxdivDiscountDetail');
            </script>
        </s:if>
        <s:else>
            <div align="center" style="vertical-align:top; ">
                <tags:submit id="btnAddDiscountGoods"
                             targets="sdivDiscountGoods"
                             formId="discountGoodsForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.019"
                             preAction="discountGroupAction!prepareAddDiscountGoods.do"
                             showLoadingText="true"/>

                <!-- chi hien thi sua, xoa trong truong hop da co it nhat 1 phan tu -->
                <s:if test="#attr.discountModelMapForm.discountModelMapId.compareTo(0L) > 0">
                    <tags:submit id="btnEditDiscountGoods"
                                 targets="sdivDiscountGoods"
                                 formId="discountGoodsForm"
                                 cssStyle="width:80px;"
                                 value="MSG.GOD.020"
                                 preAction="discountGroupAction!prepareEditDiscountGoods.do"
                                 showLoadingText="true"/>

                    <tags:submit id="btnDelDiscountGoods"
                                 targets="sdivDiscountGoods"
                                 formId="discountGoodsForm"
                                 cssStyle="width:80px;"
                                 value="MSG.GOD.021"
                                 confirm="true"
                                 confirmText="MSG.GOD.325"
                                 preAction="discountGroupAction!delDiscountGoods.do"
                                 showLoadingText="true"/>
                </s:if>
                <s:else>
                    <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.020'))}" disabled style="width:80px;">
                    <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))}" disabled style="width:80px;">
                </s:else>
            </div>

            <script language="javascript">
                enableTab('sxdivDiscountGroup');
                enableTab('sxdivDiscountDetail');
            </script>

        </s:else>

    </div>

    <!-- danh sach cac mat hang thuoc nhom chiet khau -->
    <div>
        <jsp:include page="listDiscountGoods.jsp"/>
    </div>

</sx:div>

