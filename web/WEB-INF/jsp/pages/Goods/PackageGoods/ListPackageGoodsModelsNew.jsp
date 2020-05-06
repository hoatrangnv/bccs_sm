<%-- 
    Document   : ListPackageGoodsModelsNew
    Created on : Sep 27, 2010, 3:07:20 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listPackageGoodsDetail", request.getSession().getAttribute("listPackageGoodsDetail"));
            String pageId = request.getParameter("pageId");
            request.setAttribute("showButton", request.getSession().getAttribute("showButton" + pageId));
%>
<sx:div id="packageGoodsDetail" cssStyle="width:100%">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult id="listPackageGoodsModelMessage" property="listPackageGoodsModelMessage" type="key"/>
    </div>
    <div class="divHasBorder">
        <s:form theme="simple" action="packageGoodsNewAction.do" id="packageGoodsDetailForm" method="post">
<s:token/>

            <s:hidden name="packageGoodsDetailForm.packageGoodsId" id="packageGoodsDetailForm.packageGoodsId"/>
            <s:hidden name="packageGoodsDetailForm.packageGoodsMapId" id="saleServicesModelForm.packageGoodsMapId"/>
            <table style="width:100%" class="inputTbl4Col">
                <tr>
                    <s:if test="#request.isEditGroupMode">
                        <td style="width:20%"><tags:label key="MSG.LST.041" required="true"/></td>
                        <td class="oddColumn" style="width:30%">
                            <tags:imSelect name="packageGoodsDetailForm.stockTypeId"
                                           id="stockTypeId"
                                           disabled="true"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="MSG.GOD.216"
                                           list="listStockType"
                                           listKey="stockTypeId" listValue="name"/>
                        </td>

                    </s:if>
                    <s:else>
                        <td style="width:20%"><tags:label key="MSG.LST.041" required="true"/></td>
                        <td class="oddColumn" style="width:30%">
                            <tags:imSelect name="packageGoodsDetailForm.stockTypeId"
                                           id="stockTypeId"
                                           disabled="${fn:escapeXml(!(requestScope.isAddMode))}"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="MSG.GOD.216"
                                           list="listStockType"
                                           listKey="stockTypeId" listValue="name"/>
                        </td>
                    </s:else>



                    <td> <tags:label key="MSG.groupCode" required="true"/> </td>
                    <td class="oddColumn">
                        <s:textfield name="packageGoodsDetailForm.groupCode" id="groupCode"
                                     maxlength="50" cssClass="txtInputFull"
                                     readonly="!#request.isAddMode"/>
                    </td>
                </tr>
                <tr>
                    <td> <tags:label key="MSG.groupName" required="true"/> </td>
                    <td class="oddColumn">
                        <s:textfield name="packageGoodsDetailForm.groupName" id="groupName"
                                     maxlength="250" cssClass="txtInputFull"
                                     readonly="!#request.isAddMode"/>
                    </td>
                    <td> <tags:label key="MSG.GOD.338"/> </td>
                    <td class="oddColumn">
                        <s:textfield name="packageGoodsDetailForm.note" id="note"
                                     maxlength="250" cssClass="txtInputFull"
                                     readonly="!#request.isAddMode"/>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>
    <br>
    <!-- phan nut tac vu -->
    <s:if test="#request.isAddMode">
        <s:if test="#request.showButton == null || #request.showButton*1 != 0">
            <div align="center" style="vertical-align:top; ">

                <tags:submit id="btnAddOrEditDiscountGoods"
                             targets="packageGoodsDetail"
                             formId="packageGoodsDetailForm"
                             cssStyle="width:80px;"
                             confirm="true"
                             confirmText="MSG.GOD.017"
                             value="MSG.GOD.016"
                             validateFunction="validateAddGoods()"
                             preAction="packageGoodsNewAction!addPackageGoodsMap.do"
                             showLoadingText="true"/>

                <tags:submit id="btnDisplayDiscountGoods"
                             targets="packageGoodsDetail"
                             formId="packageGoodsDetailForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.018"
                             preAction="packageGoodsNewAction!cancelAdd.do"
                             showLoadingText="true"/>
            </div>
            <script language="javascript">
                disableTab('sxdivListPackageGoods');
                enableTab('sxdivPackageGoodsModelList');
            </script>
        </s:if>
        <s:else>
            <script language="javascript">
                enableTab('sxdivListPackageGoods');
                enableTab('sxdivPackageGoodsModelList');
            </script>
        </s:else>
    </s:if>
    <s:else>
        <s:if test="#request.showButton == null || #request.showButton*1 != 0">
            <div align="center" style="vertical-align:top; ">
                <tags:submit id="btnAddDiscountGoods"
                             targets="packageGoodsDetail"
                             formId="packageGoodsDetailForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.019"
                             preAction="packageGoodsNewAction!prepareAddPackageGoodsMap.do"
                             showLoadingText="true"/>
            </div>
            <s:if test="packageGoodsDetailForm.packageGoodsId != null">
                <script language="javascript">
                    enableTab('sxdivListPackageGoods');
                    enableTab('sxdivPackageGoodsModelList');
                </script>
            </s:if>
            <s:else>
                <script language="javascript">
                    enableTab('sxdivListPackageGoods');
                    disableTab('sxdivPackageGoodsModelList');
                </script>
            </s:else>
        </s:if>
    </s:else>
    <s:else>
        <script language="javascript">
            enableTab('sxdivListPackageGoods');
            enableTab('sxdivPackageGoodsModelList');
        </script>
    </s:else>

    <%--<display:table id="tblListPackageGoodsDetail" name="listPackageGoodsDetail" class="dataTable"
                   targets="packageGoodsDetail" cellpadding="1" cellspacing="1">

        <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
            ${fn:escapeXml(tblListPackageGoodsDetail_rowNum)}
        </display:column>
        <display:column escapeXml="true"  property="stockTypeName" title=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.027'))}" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  property="stockModelCode" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.007'))}" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  property="stockModelName" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.008'))}" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  property="decriptions" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.031'))}" sortable="false" headerClass="tct"/>
        <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.020'))}" style="with:10px; text-align:center">
            <s:a onclick="editPackageGoodsDetail('%{#attr.tblListPackageGoodsDetail.packageGoodsDetailId}')">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getText(MSG.GOD.020)"/>" alt="<s:property escapeJavaScript="true"  value="getText(MSG.GOD.020)"/>"/>
            </s:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))} " style="with:10px; text-align:center">
            <s:a onclick="delPackageGoodsDetail('%{#attr.tblListPackageGoodsDetail.packageGoodsDetailId}')">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property escapeJavaScript="true"  value="getText(MSG.GOD.021)"/>" alt="<s:property escapeJavaScript="true"  value="getText(MSG.GOD.021)"/>"/>
            </s:a>
        </display:column>
    </display:table>--%>
    <tags:imPanel title="MSG.ListPackage">
        <!-- phan hien thi thong tin message -->
        <div>
            <tags:displayResult id="listSaleServicesModelMessage" property="listSaleServicesModelMessage" type="key"/>
        </div>
        <div style="overflow: auto">
            <s:if test="#session.listPackageGoodsMap != null && #session.listPackageGoodsMap.size() > 0">
                <table width="100%" id="OfferSubTree" class="dataTable">
                    <thead>
                        <tr>
                            <th style="width:4%; "><tags:label key="MSG.GOD.073"/></th>
                            <th style="width:23%; "><tags:label key="MSG.GOD.080"/></th>
                            <th style="width:10%; "><tags:label key="MSG.groupCode"/></th>
                            <th style="width:16%; "><tags:label key="MSG.groupName"/></th>
                            <th style="width:18%; "><tags:label key="MSG.GOD.045"/></th>
                            <th style="width:18%; "><tags:label key="MSG.requiredPakage"/></th>
                            <th style="width:4%; "><tags:label key="MSG.GOD.339"/></th>
                            <th style="width:3%; "><tags:label key="MSG.GOD.019"/></th>
                            <th style="width:4%; "><tags:label key="MSG.GOD.021"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator id="packageGoodsMap" value="#session.listPackageGoodsMap" status="packageGoodsMapStatus">
                            <tr>
                                <s:set id="topUpNodeParameter" value="%{#attr.packageGoodsMap.packageGoodsMapId + ', ' + #request.contextPath}"/>
                                <td style="text-align:center; ">
                                    <s:property escapeJavaScript="true"  value="%{#packageGoodsMapStatus.index + 1}" />
                                </td>
                                <td class="cursorHand" onclick="topUpNode('<s:property escapeJavaScript="true"  value='#attr.packageGoodsMap.packageGoodsMapId'/>', '${contextPath}')">
                                    <s:set id="expanImgId" value="%{'expan_' + #attr.packageGoodsMap.packageGoodsMapId}"/>
                                    <img id="<s:property escapeJavaScript="true"  value='#attr.expanImgId'/>" src="${contextPath}/share/img/treeview/minusbottom.gif" alt="">
                                    <s:set id="folderImgId" value="%{'folder_' + #attr.packageGoodsMap.packageGoodsMapId}"/>
                                    <img id="<s:property escapeJavaScript="true"  value='#attr.folderImgId'/>" src="${contextPath}/share/img/treeview/folderopen.gif"  alt="">
                                    <s:property escapeJavaScript="true"  value="#attr.packageGoodsMap.stockTypeName"/>
                                </td>
                                <td>
                                    <s:property escapeJavaScript="true"  value="#attr.packageGoodsMap.groupCode"/>
                                </td>
                                <td>
                                    <s:property escapeJavaScript="true"  value="#attr.packageGoodsMap.groupName"/>
                                </td>
                                <td>
                                    <s:property escapeJavaScript="true"  value="#attr.packageGoodsMap.note"/>
                                </td>
                                <td></td>
                                <s:if test="#request.showButton == null || #request.showButton*1 != 0">
                                    <td style="text-align:center; ">
                                        <s:if test="#request.saleServicesMode == 'prepareAddOrEdit'">
                                            <img src="${contextPath}/share/img/edit_icon_1.jpg"
                                                 title="" alt="<s:property escapeJavaScript="true"  value="getText('MSG.GOD.004')"/>"/>
                                            <!--                                         title="" alt="Chọn"/>-->
                                        </s:if>
                                        <s:else>
                                            <img src="${contextPath}/share/img/edit_icon.jpg" onclick="displayPackageGoodsMap(<s:property escapeJavaScript="true"  value="#attr.packageGoodsMap.packageGoodsMapId"/>)"
                                                 title="<s:property escapeJavaScript="true"  value="getText('MSG.GOD.088')"/>" alt="<s:property escapeJavaScript="true"  value="getText('MSG.GOD.004')"/>"/>
                                            <!--                                             title="088Thông tin chi tiết" alt="004Chọn"/>-->
                                        </s:else>
                                    </td>
                                    <td style="text-align:center; ">
                                        <s:if test="#request.saleServicesMode == 'prepareAddOrEdit'">
                                            <img src="${contextPath}/share/img/add_icon_1.png"
                                                 title="<s:property escapeJavaScript="true"  value="getText('MSG.GOD.087')"/>" alt="<s:property escapeJavaScript="true"  value="getText('MSG.GOD.019')"/>"/>
                                            <!--                                         title="" alt="Thêm"/>-->
                                        </s:if>
                                        <s:else>
                                            <s:a href="#" cssClass="cursorHand" onclick="addPackageGoodsDetail('%{#attr.packageGoodsMap.packageGoodsMapId}')">
                                                <img src="${contextPath}/share/img/add_icon.png"
                                                     title="<s:property escapeJavaScript="true"  value="getText('MSG.GOD.089')"/>" alt="<s:property escapeJavaScript="true"  value="getText('MSG.GOD.019')"/>"/>
                                                <!--                                             title="089Thêm mặt hàng vào dịch vụ" alt="Thêm"/>-->
                                            </s:a>
                                        </s:else>
                                    </td>
                                    <td style="text-align:center; ">
                                        <s:if test="#request.saleServicesMode == 'prepareAddOrEdit'">
                                            <img src="${contextPath}/share/img/delete_icon_1.jpg"
                                                 title="" alt="<s:property escapeJavaScript="true"  value="getText('MSG.GOD.021')"/>"/>
                                            <!--                                         title="" alt="Xóa"/>-->
                                        </s:if>
                                        <s:else>
                                            <img src="${contextPath}/share/img/delete_icon.jpg" onclick="delPackageGoodsMap(<s:property escapeJavaScript="true"  value="#attr.packageGoodsMap.packageGoodsMapId"/>)"
                                                 title="<s:property escapeJavaScript="true"  value="getText('MSG.GOD.090')"/>" alt="<s:property escapeJavaScript="true"  value="getText('MSG.GOD.019')"/>"/>
                                            <!--                                             title="Xóa loại mặt hàng khỏi dịch vụ" alt="Xóa"/>-->
                                        </s:else>
                                    </td>
                                </s:if>
                                <s:else>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </s:else>

                            </tr>
                            <s:if test="#attr.packageGoodsMap.listPackageGoodsDetail != null && #attr.packageGoodsMap.listPackageGoodsDetail.size() > 0">
                                <s:iterator id="saleServicesDetail" value="#attr.packageGoodsMap.listPackageGoodsDetail" status="saleServicesDetailStatus">
                                    <tr id="<s:property escapeJavaScript="true"  value='#attr.packageGoodsMap.packageGoodsMapId'/>">
                                        <td></td>
                                        <td colspan="3">
                                            <img src="${contextPath}/share/img/treeview/line.gif" alt="">
                                            <img src="${contextPath}/share/img/treeview/joinbottom.gif"  alt="">
                                            <img src="${contextPath}/share/img/treeview/page.gif"  alt="">
                                            <s:property escapeJavaScript="true"  value="#attr.saleServicesDetail.stockModelName"/>
                                        </td>
                                        <td>${fn:escapeXml(saleServicesDetail.decriptions)}</td>
                                        <td>
                                            <s:if test="#attr.saleServicesDetail.requiredCheck == 0">
                                                <s:property escapeJavaScript="true"  value="getText('MSG.requiredPakage')"/>
                                            </s:if>
                                            <s:else>
                                                <s:property escapeJavaScript="true"  value="getText('MSG.notRequiredPakage')"/>
                                            </s:else>
                                        </td>
                                        <td></td>
                                        <td></td>
                                        <s:if test="#request.showButton == null || #request.showButton*1 != 0">
                                            <td style="text-align:center">
                                                <s:if test="#request.saleServicesMode == 'prepareAddOrEdit'">
                                                    <img src="${contextPath}/share/img/delete_icon_1.jpg"
                                                         title="" alt="<s:property escapeJavaScript="true"  value="getText('MSG.GOD.091')"/>"/>
                                                    <!--                                                 title="" alt="Hủy"/>-->
                                                </s:if>
                                                <s:else>
                                                    <img src="${contextPath}/share/img/delete_icon.jpg" onclick="delPackageGoodsDetail(<s:property escapeJavaScript="true"  value="#attr.packageGoodsMap.packageGoodsMapId"/>, <s:property escapeJavaScript="true"  value="#attr.saleServicesDetail.packageGoodsDetailId"/>)"
                                                         title="<s:property escapeJavaScript="true"  value="getText('MSG.GOD.092')"/>" alt="<s:property escapeJavaScript="true"  value="getText('MSG.GOD.091')"/>"/>
                                                    <!--                                                     title="Hủy mặt hàng khỏi dịch vụ" alt="Hủy"/>-->
                                                </s:else>
                                            </td>
                                        </s:if>
                                        <s:else>
                                            <td></td>
                                        </s:else>
                                    </tr>
                                </s:iterator>
                            </s:if>
                            <!-- doan javascript dong tung hang cua table (giao dien) -->
                        <script>
                            topUpNode('<s:property escapeJavaScript="true"  value='#attr.packageGoodsMap.packageGoodsMapId'/>', '${contextPath}')
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
                            <th style="width:10%; "><tags:label key="MSG.GOD.336"/></th>
                            <th style="width:20%; "><tags:label key="MSG.GOD.337"/></th>
                            <th><tags:label key="MSG.GOD.045"/></th>
                            <th style="width:35px; "><tags:label key="MSG.GOD.339"/></th>
                            <th style="width:35px; "><tags:label key="MSG.GOD.019"/></th>
                            <th style="width:35px; "><tags:label key="MSG.GOD.021"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td colspan="9"></td>
                        </tr>
                    </tbody>
                </table>
            </s:else>
        </div>
    </tags:imPanel>

</sx:div>

<script language="javascript">
    validateAddGoods =function(){
        if(trim($('stockTypeId').value) == "") {
            $('listPackageGoodsModelMessage').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.052')"/>';
            $('stockTypeId').select();
            $('stockTypeId').focus();
            return false;
        }
        if(trim($('groupCode').value) == "") {
            $('listPackageGoodsModelMessage').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.059')"/>';
            $('groupCode').select();
            $('groupCode').focus();
            return false;
        }
        if (isHtmlTagFormat(trim($('groupCode').value))){
            $( 'listPackageGoodsModelMessage' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.GOD.062')"/>';
            $('groupCode').select();
            $('groupCode').focus();
            return false;
        }
        if(trim($('groupName').value) == "") {
            $('listPackageGoodsModelMessage').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.060')"/>';
            $('groupName').select();
            $('groupName').focus();
            return false;
        }
        if (isHtmlTagFormat(trim($('groupName').value))){
            $( 'listPackageGoodsModelMessage' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.GOD.063')"/>';
            $('groupName').select();
            $('groupName').focus();
            return false;
        }
        if (isHtmlTagFormat(trim($('note').value))){
            $( 'listPackageGoodsModelMessage' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.GOD.064')"/>';
            $('note').select();
            $('note').focus();
            return false;
        }
        return true;
    }

    //
    editPackageGoodsDetail = function(id) {
        openDialog("${contextPath}/packageGoodsAction!prepareEditPackageGoodsDetail.do?id=" + id + '&' + token.getTokenParamString(), 750, 700, true);
    }

    //
    <%-- delPackageGoodsDetail = function(packageGoodsDetailId) {
         var confir = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getText('MSG.GOD.291')"/>');
         if(confirm(confir)){
             gotoAction('packageGoodsDetail', '${contextPath}/packageGoodsNewAction!delPackageGoodsDetail.do?packageGoodsDetailId=' + packageGoodsDetailId);
         }
     }--%>

         displayPackageGoodsMap = function(packageGoodsMapId) {
             gotoAction('packageGoodsDetail', '${contextPath}/packageGoodsNewAction!displayPackageGoodsMap.do?packageGoodsMapId=' + packageGoodsMapId + '&' + token.getTokenParamString());
         }

         //
         delPackageGoodsMap = function(packageGoodsMapId) {
             var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getText('MSG.GOD.345')"/>');
             if (confirm(strConfirm)) {
                 //        if (confirm('Bạn có chắc chắn muốn xóa loại mặt hàng này ra khỏi dịch vụ không?')) {
                 gotoAction('packageGoodsDetail', '${contextPath}/packageGoodsNewAction!delPackageGoodsMap.do?packageGoodsMapId=' + packageGoodsMapId + '&' + token.getTokenParamString());
             }
         }


         closeAddPackageGoodsDetail = function(packageGoodsMapId){
             gotoAction('packageGoodsDetail', "${contextPath}/packageGoodsNewAction!closeAddPackageGoodsDetail.do?packageGoodsMapId=" + packageGoodsMapId + '&selectedPackageGoodsId=' + packageGoodsMapId + '&' + token.getTokenParamString());
         }

        
         //ham xu ly su kien khi nhan button them mat hang vao loai mat hang thuoc dich vu
         addPackageGoodsDetail = function(packageGoodsMapId) {
             //openPopup("$_{contextPath}/packageGoodsNewAction!preparePopUpAddPackageGoodsDetail.do?packageGoodsMapId=" + packageGoodsMapId+ '&' + token.getTokenParamString(), 750, 750);
             openDialog("${contextPath}/packageGoodsNewAction!preparePopUpAddPackageGoodsDetail.do?packageGoodsMapId=" + packageGoodsMapId+ '&' + token.getTokenParamString(), 750, 750,true);
         }

//         addPackageGoodsDetail_old = function(packageGoodsMapId) {
//             openDialog("$_{contextPath}/packageGoodsNewAction!preparePopUpAddPackageGoodsDetail.do?packageGoodsMapId=" + packageGoodsMapId + '&' + token.getTokenParamString(), 750, 750, true);
//             var myWindow;
//             //             if(!myWindow || myWindow.closed){
//             //                 alert('fdf');
//             //                 gotoAction("packageGoodsDetail",'$_{contextPath}/packageGoodsNewAction!displayPackageGoodsDetail.do?' + token.getTokenParamString() );
//             //                 alert('$_{contextPath}/packageGoodsNewAction!displayPackageGoodsDetail.do?' + token.getTokenParamString());
//             //             }
//         }
//
//         //
         delPackageGoodsDetail = function(packageGoodsMapId, packageGoodsDetailId) {
             var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getText('C.200006')"/>');
             if (confirm(strConfirm)) {
                 gotoAction('packageGoodsDetail', '${contextPath}/packageGoodsNewAction!delPackageGoodsDetail.do?packageGoodsMapId=' + packageGoodsMapId + "&packageGoodsDetailId=" + packageGoodsDetailId + '&' + token.getTokenParamString());
             }
         }
</script>
