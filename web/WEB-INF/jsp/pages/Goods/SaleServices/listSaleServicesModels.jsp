<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listSaleServicesModels
    Created on : Mar 15, 2009, 8:37:53 AM
    Author     : tamdt1
    Desc       : hien thi thong tin ve saleServicesModel cua saleService
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="divListSaleServicesModels">
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.077'))}
<!--            Danh sách mặt hàng thuộc dịch vụ bán hàng-->
        </legend>

        <!-- phan hien thi thong tin message -->
        <div>
            <tags:displayResult id="listSaleServicesModelMessage" property="listSaleServicesModelMessage" type="key"/>
        </div>

        <s:if test="#session.listSaleServicesModels != null && #session.listSaleServicesModels.size() > 0">
            <table width="100%" id="OfferSubTree" class="dataTable">
                <thead>
                    <tr>


                        <th style="width:35px; "><tags:label key="MSG.GOD.073"/></th>
                        <th style="width:25%; "><tags:label key="MSG.GOD.080"/></th>
                        <th style="width:10%; "><tags:label key="MSG.GOD.081"/></th>
                        <th style="width:20%; "><tags:label key="MSG.GOD.082"/></th>
                        <th style="width:10%; "><tags:label key="MSG.GOD.083"/></th>
                        <th><tags:label key="MSG.GOD.045"/></th>
                        <th style="width:35px; "><tags:label key="MSG.GOD.004"/></th>
                        <th style="width:35px; "><tags:label key="MSG.GOD.019"/></th>
                        <th style="width:35px; "><tags:label key="MSG.GOD.021"/></th>
<!--                        <th style="width:35px; ">073STT</th>
                        <th style="width:25%; ">080Loại MH/ Mặt hàng</th>
                        <th style="width:10%; ">081Kiểu trừ kho</th>
                        <th style="width:20%; ">082Trừ kho ĐV/ NV/ chức năng</th>
                        <th style="width:10%; ">083Giá</th>
                        <th>045Mô tả</th>
                        <th style="width:35px; ">004Chọn</th>
                        <th style="width:35px; ">019Thêm</th>
                        <th style="width:35px; ">021Xóa</th>-->
                    </tr>
                </thead>
                <tbody>
                    <s:iterator id="saleServicesModel" value="#session.listSaleServicesModels" status="saleServicesModelStatus">
                        <tr>
                            <s:set id="topUpNodeParameter" value="%{#attr.saleServicesModel.saleServicesModelId + ', ' + #request.contextPath}"/>
                            <td style="text-align:center; ">
                                <s:property escapeJavaScript="true"  value="%{#saleServicesModelStatus.index + 1}" />
                            </td>
                            <td class="cursorHand" onclick="topUpNode('<s:property escapeJavaScript="true"  value='#attr.saleServicesModel.saleServicesModelId'/>', '${contextPath}')">
                                <s:set id="expanImgId" value="%{'expan_' + #attr.saleServicesModel.saleServicesModelId}"/>
                                <img id="<s:property escapeJavaScript="true"  value='#attr.expanImgId'/>" src="${contextPath}/share/img/treeview/minusbottom.gif" alt="">
                                <s:set id="folderImgId" value="%{'folder_' + #attr.saleServicesModel.saleServicesModelId}"/>
                                <img id="<s:property escapeJavaScript="true"  value='#attr.folderImgId'/>" src="${contextPath}/share/img/treeview/folderopen.gif"  alt="">
                                <s:property escapeJavaScript="true"  value="#attr.saleServicesModel.stockTypeName"/>
                            </td>
                            <td>
                                <s:if test="#attr.saleServicesModel.updateStock == 0">
                                    <tags:label key="MSG.GOD.078"/>
<!--                                    078Chưa trừ kho ngay-->
                                </s:if>
                                <s:elseif test="#attr.saleServicesModel.updateStock == 1">
                                    <tags:label key="MSG.GOD.079"/>
<!--                                    079Trừ kho ngay-->
                                </s:elseif>
                            </td>
                            <td>
                                <s:if test="#attr.saleServicesModel.checkShopStock == 1L">
                                    <input type="checkbox" checked disabled>
                                </s:if>
                                <s:else>
                                    <input type="checkbox" disabled>
                                </s:else>
                                <s:if test="#attr.saleServicesModel.checkStaffStock == 1L">
                                    <input type="checkbox" checked disabled>
                                </s:if>
                                <s:else>
                                    <input type="checkbox" disabled>
                                </s:else>
                                <s:property escapeJavaScript="true"  value="#attr.saleServicesModel.shopName"/>
                            </td>
                            <td>&nbsp;</td>
                            <td>
                                <s:property escapeJavaScript="true"  value="#attr.saleServicesModel.notes"/>
                            </td>
                            <td style="text-align:center; ">
                                <s:if test="#request.saleServicesMode == 'prepareAddOrEdit'">
                                    <img src="${contextPath}/share/img/edit_icon_1.jpg"
                                         title="" alt="<s:text name="MSG.GOD.004"/>"/>
<!--                                         title="" alt="Chọn"/>-->
                                </s:if>
                                <s:else>
                                    <s:a href="" cssClass="cursorHand" onclick="displaySaleServicesModel('%{#attr.saleServicesModel.saleServicesModelId}')">
                                        <img src="${contextPath}/share/img/edit_icon.jpg"
                                             title="<s:text name="MSG.GOD.088"/>" alt="<s:text name="MSG.GOD.004"/>"/>
<!--                                             title="088Thông tin chi tiết" alt="004Chọn"/>-->
                                    </s:a>
                                </s:else>
                            </td>
                            <td style="text-align:center; ">
                                <s:if test="#request.saleServicesMode == 'prepareAddOrEdit'">
                                    <img src="${contextPath}/share/img/add_icon_1.png"
                                         title="<s:text name="MSG.GOD.087"/>" alt="<s:text name="MSG.GOD.019"/>"/>
<!--                                         title="" alt="Thêm"/>-->
                                </s:if>
                                <s:else>
                                    <s:a href="" cssClass="cursorHand" onclick="addSaleServicesDetail('%{#attr.saleServicesModel.saleServicesModelId}')">
                                        <img src="${contextPath}/share/img/add_icon.png"
                                             title="<s:text name="MSG.GOD.089"/>" alt="<s:text name="MSG.GOD.019"/>"/>
<!--                                             title="089Thêm mặt hàng vào dịch vụ" alt="Thêm"/>-->
                                    </s:a>
                                </s:else>
                            </td>
                            <td style="text-align:center; ">
                                <s:if test="#request.saleServicesMode == 'prepareAddOrEdit'">
                                    <img src="${contextPath}/share/img/delete_icon_1.jpg"
                                         title="" alt="<s:text name="MSG.GOD.021"/>"/>
<!--                                         title="" alt="Xóa"/>-->
                                </s:if>
                                <s:else>
                                    <s:a href="" cssClass="cursorHand" onclick="delSaleServicesModel('%{#attr.saleServicesModel.saleServicesModelId}')">
                                        <img src="${contextPath}/share/img/delete_icon.jpg"
                                             title="<s:text name="MSG.GOD.090"/>" alt="<s:text name="MSG.GOD.019"/>"/>
<!--                                             title="Xóa loại mặt hàng khỏi dịch vụ" alt="Xóa"/>-->
                                    </s:a>
                                </s:else>
                            </td>
                        </tr>
                        <s:if test="#attr.saleServicesModel.listSaleServicesDetail != null && #attr.saleServicesModel.listSaleServicesDetail.size() > 0">
                            <s:iterator id="saleServicesDetail" value="#attr.saleServicesModel.listSaleServicesDetail" status="saleServicesDetailStatus">
                                <tr id="<s:property escapeJavaScript="true"  value='#attr.saleServicesModel.saleServicesModelId'/>">
                                    <td></td>
                                    <td colspan="3">
                                        <img src="${contextPath}/share/img/treeview/line.gif" alt="">
                                        <img src="${contextPath}/share/img/treeview/joinbottom.gif"  alt="">
                                        <img src="${contextPath}/share/img/treeview/page.gif"  alt="">
                                        <s:property escapeJavaScript="true"  value="#attr.saleServicesDetail.stockModelName"/>
                                    </td>
                                    <td style="text-align:right; ">
                                        <s:text name="format.money">
                                            <s:param name="value" value="#attr.saleServicesDetail.price"/>
                                        </s:text>
                                    </td>
                                    <td>
                                        <s:property escapeJavaScript="true"  value="#attr.saleServicesDetail.priceDescription"/>
                                    </td>
                                    <td></td>
                                    <td></td>
                                    <td style="text-align:center">
                                        <s:if test="#request.saleServicesMode == 'prepareAddOrEdit'">
                                            <img src="${contextPath}/share/img/delete_icon_1.jpg"
                                                 title="" alt="<s:text name="MSG.GOD.091"/>"/>
<!--                                                 title="" alt="Hủy"/>-->
                                        </s:if>
                                        <s:else>
                                            <s:a href="" cssClass="cursorHand" onclick="delSaleServicesDetail('%{#attr.saleServicesModel.saleServicesModelId}', '%{#attr.saleServicesDetail.id}')">
                                                <img src="${contextPath}/share/img/delete_icon.jpg"
                                                     title="<s:text name="MSG.GOD.092"/>" alt="<s:text name="MSG.GOD.091"/>"/>
<!--                                                     title="Hủy mặt hàng khỏi dịch vụ" alt="Hủy"/>-->
                                            </s:a>
                                        </s:else>
                                    </td>
                                </tr>
                            </s:iterator>
                        </s:if>

                        <!-- doan javascript dong tung hang cua table (giao dien) -->
                        <script>
                            topUpNode('<s:property escapeJavaScript="true"  value='#attr.saleServicesModel.saleServicesModelId'/>', '${contextPath}')
                        </script>

                    </s:iterator>
                </tbody>
            </table>
        </s:if>
        <s:else>
            <table width="100%" id="OfferSubTree" class="dataTable">
                <thead>
                    <tr>
                        <th style="width:35px; "><tags:label key="MSG.GOD.073"/></th>
                        <th style="width:25%; "><tags:label key="MSG.GOD.080"/></th>
                        <th style="width:10%; "><tags:label key="MSG.GOD.081"/></th>
                        <th style="width:20%; "><tags:label key="MSG.GOD.082"/></th>
                        <th style="width:10%; "><tags:label key="MSG.GOD.083"/></th>
                        <th><tags:label key="MSG.GOD.045"/></th>
                        <th style="width:35px; "><tags:label key="MSG.GOD.004"/></th>
                        <th style="width:35px; "><tags:label key="MSG.GOD.019"/></th>
                        <th style="width:35px; "><tags:label key="MSG.GOD.021"/></th>
                        
<!--                        <th style="width:35px; ">STT</th>
                        <th style="width:25%; ">Loại MH/ Mặt hàng</th>
                        <th style="width:10%; ">Kiểu trừ kho</th>
                        <th style="width:20%; ">Trừ kho ĐV/ NV/ chức năng</th>
                        <th style="width:10%; ">Giá</th>
                        <th>Mô tả</th>
                        <th style="width:35px; ">Chọn</th>
                        <th style="width:35px; ">Thêm</th>
                        <th style="width:35px; ">Xóa</th>-->
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td colspan="9"></td>
                    </tr>
                </tbody>
            </table>
        </s:else>
    </fieldset>
</sx:div>



<script language="javascript">

    //
    displaySaleServicesModel = function(saleServicesModelId) {
        gotoAction('divSaleServicesModel', '${contextPath}/saleServicesAction!displaySaleServicesModel.do?selectedSaleServicesModelId=' + saleServicesModelId);
    }

    //
    delSaleServicesModel = function(saleServicesModelId) {
//        var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.GOD.084')"/>');
        var strConfirm = getUnicodeMsg('<s:text name="MSG.GOD.084"/>');
        if (confirm(strConfirm)) {
//        if (confirm('Bạn có chắc chắn muốn xóa loại mặt hàng này ra khỏi dịch vụ không?')) {
gotoAction('divSaleServicesModel', '${contextPath}/saleServicesAction!delSaleServicesModel.do?saleServicesModelForm.saleServicesModelId=' + saleServicesModelId + '&' + token.getTokenParamString());
        }
    }

    //ham xu ly su kien khi nhan button them mat hang vao loai mat hang thuoc dich vu
    addSaleServicesDetail = function(saleServicesModelId) {
        openDialog("${contextPath}/saleServicesAction!prepareAddSaleServicesDetail.do?saleServicesModelId=" + saleServicesModelId, 750, 550, true);
    }
    
    //
    delSaleServicesDetail = function(saleServicesModelId, saleServicesDetailId) {
//        var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.GOD.085')"/>');
        var strConfirm = getUnicodeMsg('<s:text name="MSG.GOD.085"/>');
        if (confirm(strConfirm)) {
//        if (confirm('Bạn có chắc chắn muốn xóa mặt hàng này ra khỏi dịch vụ không?')) {
gotoAction('divListSaleServicesModels', '${contextPath}/saleServicesAction!delSaleServicesDetail.do?saleServicesModelId=' + saleServicesModelId + "&saleServicesDetailId=" + saleServicesDetailId + "&" + token.getTokenParamString());
        }
    }
</script>

