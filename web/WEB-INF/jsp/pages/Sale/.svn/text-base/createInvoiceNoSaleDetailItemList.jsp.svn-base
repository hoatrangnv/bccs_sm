<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : createInvoiceNoSaleDetailItemList
    Created on : Aug 7, 2009, 10:11:38 AM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<fieldset style="width:100%; height:300px">
            <legend class="transparent"><tags:label key="MSG.list.stock.model"/> </legend>
            <table class="inputTbl">                
                <tr>
                    <td>
                        <s:if test="#session.lstItems != null && #session.lstItems.size() != 0">
                            <%
                                request.setAttribute("lstItems", request.getSession().getAttribute("lstItems"));
                            %>
                            <display:table name="lstItems" id="item"  class="dataTable" >
                                <display:column escapeXml="true" title="STT" sortable="false" headerClass="tct">
                                    <div align="center" style="vertical-align:middle">${fn:escapeXml(item_rowNum)}</div>
                                </display:column>
                                <display:column escapeXml="true" property="itemCode" title="Mã MH" sortable="false" style="text-align:left; padding-right:20px" headerClass="tct"/>
                                <display:column escapeXml="true" property="itemName" title="Tên mặt hàng" sortable="false" style="text-align:left; padding-right:20px" headerClass="tct"/>
                                <display:column escapeXml="true" property="itemUnit" title="Đơn vị" sortable="false" style="text-align:center; padding-right:20px" headerClass="tct"/>
                                <display:column escapeXml="true" property="itemPrice" title="Đơn giá" sortable="false" style="text-align:right" headerClass="tct" format="{0}"/>
                                <display:column escapeXml="true" property="itemQty" title="Số lượng" sortable="false" style="text-align:right" headerClass="tct" format="{0}"/>
                                <display:column escapeXml="true" property="itemTotal" title="Thành tiền" sortable="false" style="text-align:right" headerClass="tct" format="{0}"/>                                <

                                <display:column title="Sửa" style="width:70px" sortable="false" headerClass="tct">
                                    <div align="center">
                                        <s:url action="searchSaleTransAction!editItem" id="URL1">
                                            <s:param name="itemId" value="%{#attr.item.itemId}"/>
                                        </s:url>
                                        <sx:a targets="invoiceDetailInputItem" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                                            <img src="${contextPath}/share/img/accept.png" title="Sửa mặt hàng" alt="Sửa"/>
                                        </sx:a>
                                    </div>
                                </display:column>
                                <display:column title="Xóa" sortable="false" headerClass="tct" style="width:70px" >
                                    <div align="center">
                                        <s:url action="searchSaleTransAction!delItem" id="URL">
                                            <s:param name="itemId" value="#attr.item.itemId"/>
                                        </s:url>                                        
                                        <sx:a targets="invoiceDetailItemList" href="%{#URL}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                                            <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa mặt hàng" alt="Xóa"/>
                                        </sx:a>
                                    </div>
                                </display:column>

                            </display:table>
                        </s:if>
                        <s:else>
                            <table class="dataTable">
                                <tr>
                                    <th class="tct">STT</th>
                                    <th class="tct">Mã mặt hàng</th>
                                    <th>Tên mặt hàng</th>
                                    <th class="tct">Đơn vị</th>
                                    <th class="tct">Số lượng</th>
                                    <th class="tct">Đơn giá</th>
                                    <th class="tct">Thành tiền</th>
                                    <th class="tct">Sửa</th>
                                    <th class="tct">Xoá</th>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td  width="70px">&nbsp;</td>
                                    <td  width="70px">&nbsp;</td>
                                </tr>
                            </table>
                        </s:else>
                    </td>
                </tr>
            </table>
            <table class="inputTbl">
                <tr><td></td><td></td><td></td></tr>
                <tr><td></td><td></td><td></td></tr>
                <tr>
                    <td><tags:label key="MSG.sum.money"/></td>
                    <td>
                        <s:textfield name="saleForm.invoiceTotal" theme="simple" cssStyle="text-align:right" id="invoiceTotal" readonly="true" cssClass="txtInputFull"/>
                    </td>
                    <td width="140px"></td>
                </tr>                
            </table>
        </fieldset>


<script type="text/javascript" language="javascript">
    textFieldNF($('invoiceTotal'));
</script>
