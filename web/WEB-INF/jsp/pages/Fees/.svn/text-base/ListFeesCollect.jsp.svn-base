
<%-- 
    Document   : ListFeesCollect
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : Tuannv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>



<%
            request.setAttribute("contextPath", request.getContextPath());

            request.setAttribute("lstChannelFees", request.getSession().getAttribute("lstChannelFees"));
            if (request.getAttribute("money") == null) {
                request.setAttribute("money", request.getSession().getAttribute("money"));
            }
%>


<s:if test="#request.lstChannelFees != null && #request.lstChannelFees.size()>0">
    <tags:imPanel title="Kết quả tổng hợp phí hoa hồng ">        

        <s:if test="collectFeesForm.pathOut!=null && collectFeesForm.pathOut!=''">
            <br>
            <script type="text/javascript" language="JavaScript">
                window.open('${contextPath}<s:property escapeJavaScript="true"  value="collectFeesForm.pathOut"/>','','toolbar=yes,scrollbars=yes');
            </script>
            <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="collectFeesForm.pathOut"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a></div>
            <br>
        </s:if>

        <!-- hien thi ket qua kieu tree-->

        <table width="100%" id="OfferSubTree" class="dataTable" bgcolor="white">
            <thead>
                <tr>
                    <th>Tên kênh</th>
                    <!--s:if test="#request.money != null && #request.money  != 0">
                        <th>Từ ngày </th>
                        <th>Đến ngày </th>     
                    <!--/s:if-->

                    <th>Tổng Số</th>
                    <th>Đúng hạn</th>
                    <th>Quá hạn 1</th>
                    <th>Quá hạn 2</th>
                    <th>Quá hạn 3</th>
                    <s:if test="#request.money != null && #request.money  != 0">
                        <th>Phí hoa hồng</th>
                        <th>Thành tiền</th>
                    </s:if>
                    <th>Chu kỳ tính</th>
                    <th>Ngày tính</th>
                    <th width="18px">&nbsp;</th>
                </tr>
            </thead>

            <tbody style="height:300px; overflow:scroll">

                <s:iterator id="shop" value="#request.lstChannelFees">
                    <tr>
                        <s:set id="shopId" value="%{#attr.shop.shopId}"/>

                        <td onclick="topUpNode('<s:property escapeJavaScript="true"  value='#attr.shopId'/>', '${contextPath}')">
                            <s:set id="expanImgId" value="%{'expan_' + #attr.shopId}"/>
                            <img id="<s:property escapeJavaScript="true"  value='#attr.expanImgId'/>" src="${contextPath}/share/img/treeview/minusbottom.gif" alt="">
                            <s:set id="folderImgId" value="%{'folder_' + #attr.shopId}"/>
                            <img id="<s:property escapeJavaScript="true"  value='#attr.folderImgId'/>" src="${contextPath}/share/img/treeview/folderopen.gif"  alt="">
                            <s:property escapeJavaScript="true"  value="#attr.shop.shopName"/>
                        </td>
                        <!--s:if test="#request.money != null && #request.money != 0">
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>  
                        <!--/s:if-->

                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <s:if test="#request.money != null && #request.money != 0">
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </s:if>

                <script>
                            topUpNode('<s:property escapeJavaScript="true"  value='#attr.shopId'/>', '${contextPath}')
                </script>

            </tr>
            <s:if test="#attr.shop.listItems != null && #attr.shop.listItems.size() > 0">
                <s:iterator id="items" value="#attr.shop.listItems">
                    <tr id="<s:property escapeJavaScript="true"  value='#attr.shop.shopId'/>">
                        <td>
                            <img src="${contextPath}/share/img/treeview/line.gif" alt="">
                            <img src="${contextPath}/share/img/treeview/joinbottom.gif"  alt="">
                            <img src="${contextPath}/share/img/treeview/page.gif"  alt="">
                            <!--s:property value="#attr.items.itemId"/-->
                            <s:property escapeJavaScript="true"  value="#attr.items.itemName"/>
                        </td>
                        <!--s:if test="#request.money != null && #request.money != 0">
                            <td align="center">
                        <!--s:date  name="#attr.items.startDate" format="dd/MM/yyyy"/>
                    </td>
                    <td align="center">
                        <!--s:date name="#attr.items.endDate" format="dd/MM/yyyy"/>
                    </td>
                        <!--/s:if-->
                        <td align="right">
                            <s:property escapeJavaScript="true"  value="#attr.items.quantity" />
                        </td>
                        <td align="right">
                            <s:property escapeJavaScript="true"  value="#attr.items.quantityOntime" />
                        </td>
                        <td align="right">
                            <s:property escapeJavaScript="true"  value="#attr.items.quantityExpired1" />
                        </td>
                        <td align="right">
                            <s:property escapeJavaScript="true"  value="#attr.items.quantityExpired2" />
                        </td>
                        <td align="right">
                            <s:property escapeJavaScript="true"  value="#attr.items.quantityExpired3" />
                        </td>
                        <s:if test="#request.money != null && #request.money != 0">

                            <td style="text-align:right; ">
                                <s:text name="format.money">
                                    <s:param name="value" value="#attr.items.fee"/>
                                </s:text>
                            </td>

                            <td style="text-align:right; ">
                                <s:text name="format.money">
                                    <s:param name="value" value="#attr.items.totalMoney"/>
                                </s:text>
                            </td>
                        </s:if>

                        <td align="center">
                            <s:date name="#attr.items.billCycle" format="dd/MM/yyyy"/>
                        </td>
                        <td align="center">
                            <s:date name="#attr.items.createDate" format="dd/MM/yyyy"/>
                        </td>

                    </tr>
                </s:iterator>
            </s:if>
        </s:iterator>

    </tbody>


</table>
</tags:imPanel>         
</s:if>

<script>
//    Khong dung trong DAO
    dojo.event.topic.subscribe("collectFeesForm/SaveDataFromTmpTable", function(event, widget){
        //widget.href = "collectFeesAction!SaveDataFromTmpTable.do?"+ token.getTokenParamString();
    });
    dojo.event.topic.subscribe("collectFeesForm/exportFilterIsdnResult", function(event, widget){
        var selectedRules =$('selectedRules').value;
        if(selectedRules.length <0)
        {
            alert("Chưa chọn luật để export");
            return;
        }
        widget.href = "filterIsdnAction!exportFilterIsdnResult.do?"+ token.getTokenParamString();
    });
</script>
