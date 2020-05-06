<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : retailSale
    Created on : Mar 7, 2009, 5:32:58 PM
    Author     : tamdt1
--%>

<%--
    Note: ban hang le
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>


<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<html:form action="/saleAction.do" styleId="saleFromExpCommand" method="post">
    <html:hidden property="className" styleId="className" value=""/>
    <html:hidden property="methodName"  styleId="methodName" value=""/>

    <!-- phan nhap thong tin chi tiet ve giao dich -->
    <fieldset style="width:100%">
        <legend class="transparent">Thông tin về giao dịch</legend>
        <table class="inputTbl">
            <tr>
                <td>Họ tên người nhận</td>
                <td class="oddColumn">
                    <html:text property="${fn:escapeXml(property1)}saleId" styleId="${fn:escapeXml(property1)}approveDate" styleClass="txtInputFull"/>
                </td>
                <td>Tài khoản</td>
                <td class="oddColumn">
                    <html:text property="${fn:escapeXml(property1)}saleId" styleId="${fn:escapeXml(property1)}approveDate" styleClass="txtInputFull"/>
                </td>
                <td>Mã số thuế</td>
                <td class="oddColumn">
                    <html:text property="${fn:escapeXml(property1)}saleId" styleId="${fn:escapeXml(property1)}approveDate" styleClass="txtInputFull"/>
                </td>
                <td>Địa chỉ</td>
                <td>
                    <html:text property="${fn:escapeXml(property1)}saleId" styleId="${fn:escapeXml(property1)}approveDate" styleClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td>Ngày bán</td>
                <td class="oddColumn">
                    <html:text property="${fn:escapeXml(property1)}saleId" styleId="${fn:escapeXml(property1)}approveDate" styleClass="txtInputFull"/>
                </td>
                <td>Lý do bán</td>
                <td class="oddColumn">
                    <html:select property="${fn:escapeXml(property1)}saleId" styleId="${fn:escapeXml(property1)}reasonId" styleClass="txtInputFull">
                        <html:option value="">--Chọn lý do bán--</html:option>
                        <html:option value="">Bán hàng cho đại lý</html:option>
                        <html:option value="">Bán hàng cho CTV</html:option>
                    </html:select>
                </td>
                <td>Hình thức TT</td>
                <td class="oddColumn">
                    <html:select property="${fn:escapeXml(property1)}saleId" styleId="${fn:escapeXml(property1)}payMethod" styleClass="txtInputFull">
                        <html:option value="">--Chọn hình thức TT--</html:option>
                        <html:option value="">TM</html:option>
                        <html:option value="">CK</html:option>
                        <html:option value="">TM + CK</html:option>
                    </html:select>
                </td>
                <td>Khuyến mại</td>
                <td>
                    <html:select property="${fn:escapeXml(property1)}saleId" styleId="${fn:escapeXml(property1)}payMethod" styleClass="txtInputFull">
                        <html:option value="">--Chọn chương trình KM--</html:option>
                        <html:option value="">Chương trình KM 1</html:option>
                        <html:option value="">Chương trình KM 2</html:option>
                        <html:option value="">Chương trình KM 3</html:option>
                    </html:select>
                </td>
            </tr>
        </table>
    </fieldset>

    <br />

    <!-- phan them hang hoa vao giao dich -->
    <fieldset style="width:100%;">
        <legend class="transparent">Thêm hàng hóa vào giao dịch</legend>
        <table class="inputTbl">
            <tr>
                <td>Loại hàng hóa</td>
                <td class="oddColumn">
                    <html:select property="${fn:escapeXml(property1)}saleId" styleId="${fn:escapeXml(property1)}saleId" styleClass="txtInputFull">
                        <html:option value="">--Chọn loại hàng hóa--</html:option>
                        <html:option value="">Hàng hóa 1</html:option>
                        <html:option value="">Hàng hóa 2</html:option>
                    </html:select>
                </td>
                <td>Tên hàng hóa</td>
                <td class="oddColumn">
                    <html:select property="${fn:escapeXml(property1)}saleId" styleId="${fn:escapeXml(property1)}saleId" styleClass="txtInputFull">
                        <html:option value="">--Chọn hàng hóa--</html:option>
                        <html:option value="">Hàng hóa 1</html:option>
                        <html:option value="">Hàng hóa 2</html:option>
                    </html:select>
                </td>
                <td>Đơn giá</td>
                <td class="oddColumn">
                    <html:select property="${fn:escapeXml(property1)}saleId" styleId="${fn:escapeXml(property1)}saleId" styleClass="txtInputFull">
                        <html:option value="">--Chọn đơn giá--</html:option>
                        <html:option value="">30,000</html:option>
                        <html:option value="">1,080,000</html:option>
                    </html:select>
                </td>
                <td>Số lượng</td>
                <td>
                    <html:text property="${fn:escapeXml(property1)}saleId" styleId="${fn:escapeXml(property1)}saleId" styleClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td colspan="8" align="center">
                    <tags:stylishButton  ajax="false" type="button">
                        Thêm vào danh sách
                    </tags:stylishButton>
                </td>
            </tr>
        </table>
    </fieldset>

    <br />
    <table style="width:100%;">
        <tr>
            <td style="width:60%">
                <fieldset style="width:100%; height:300px">
                    <legend class="transparent">Danh sách hàng hóa</legend>
                    <table class="inputTbl">
                        <tr>
                            <td>
                                <table class="dataTable">
                                    <tr>
                                        <th>STT</th>
                                        <th>Mã hàng hóa</th>
                                        <th>Tên hàng hóa</th>
                                        <th>ĐVT</th>
                                        <th>Số lượng (1)</th>
                                        <th>Đơn giá (2)</th>
                                        <th>Tổng tiền (1 * 2)</th>
                                        <th style="width:50px">Sửa</th>
                                        <th style="width:50px">Xóa</th>
                                    </tr>
                                    <tr class="odd">
                                        <td align="center">1</td>
                                        <td>HH00000001</td>
                                        <td>Hàng hóa 1</td>
                                        <td>Bộ</td>
                                        <td align="right" style="padding-right:20px">
                                            <div id="quatity_1">20</div>
                                        </td>
                                        <td align="right" style="padding-right:20px">
                                            70,000
                                        </td>
                                        <td align="right">1,400,000</td>
                                        <td align="center">
                                            <a>
                                                <img src="${contextPath}/share/img/edit_icon.jpg" title="Sửa" alt="Sửa"/>
                                            </a>
                                        </td>
                                        <td align="center">
                                            <a>
                                                <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa" alt="Xóa"/>
                                            </a>
                                        </td>
                                    </tr>
                                    <tr class="even">
                                        <td align="center">2</td>
                                        <td>HH00000002</td>
                                        <td>Hàng hóa 2</td>
                                        <td>Cái</td>
                                        <td align="right" style="padding-right:20px">200</td>
                                        <td align="right" style="padding-right:20px">
                                            30,000
                                        </td>
                                        <td align="right">6,000,000</td>
                                        <td align="center">
                                            <a>
                                                <img src="${contextPath}/share/img/edit_icon.jpg" title="Sửa" alt="Sửa"/>
                                            </a>
                                        </td>
                                        <td align="center">
                                            <a>
                                                <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa" alt="Xóa"/>
                                            </a>
                                        </td>
                                    </tr>
                                    <tr class="odd">
                                        <td></td>
                                        <td colspan="5"></td>
                                        <td align="right"><hr style="width: 80%; margin-right: 0px;"></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr class="even">
                                        <td></td>
                                        <td colspan="5">Tổng tiền có thuế (3)</td>
                                        <td align="right">7,400,000</td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr class="odd">
                                        <td></td>
                                        <td colspan="5">Tiền khuyến mại (4)</td>
                                        <td align="right">0</td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr class="even">
                                        <td></td>
                                        <td colspan="5">Tiền chiết khấu (5)</td>
                                        <td align="right">0</td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr class="odd">
                                        <td></td>
                                        <td colspan="5"><b>Tổng tiền phải trả (3 - 4 - 5)</b></td>
                                        <td align="right"><b>7,400,000</b></td>
                                        <td></td>
                                        <td></td>
                                    </tr>

                                </table>
                            </td>
                        </tr>
                    </table>
                </fieldset>
            </td>

            <td style="width:10px"></td>

            <td style="vertical-align:top">
                <fieldset style="width:100%; height:300px">
                    <legend class="transparent">Danh sách serial của hàng hóa</legend>
                    <table class="inputTbl">
                        <tr>
                            <td colspan="5">
                                <table class="dataTable">
                                    <tr>
                                        <th>STT</th>
                                        <th>Từ số</th>
                                        <th>Đến số</th>
                                        <th style="width:50px">Xóa</th>
                                    </tr>
                                    <tr class="odd">
                                        <td align="center">1</td>
                                        <td align="right" style="padding-right:20px">
                                            1
                                        </td>
                                        <td align="right" style="padding-right:20px">
                                            10
                                        </td>
                                        <td align="center">
                                            <a>
                                                <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa" alt="Xóa"/>
                                            </a>
                                        </td>
                                    </tr>
                                    <tr class="even">
                                        <td align="center">2</td>
                                        <td align="right" style="padding-right:20px">
                                            151
                                        </td>
                                        <td align="right" style="padding-right:20px">
                                            160
                                        </td>
                                        <td align="center">
                                            <a>
                                                <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa" alt="Xóa"/>
                                            </a>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align:right" colspan="5">
                                Đã có 20/ 20 serial
                            </td>
                        </tr>
                        <tr>
                            <td>Từ</td>
                            <td class="oddColumn">
                                <html:text property="${fn:escapeXml(property1)}saleId" styleId="${fn:escapeXml(property1)}saleId" styleClass="txtInputFull"/>
                            </td>
                            <td>Đến</td>
                            <td class="oddColumn">
                                <html:text property="${fn:escapeXml(property1)}saleId" styleId="${fn:escapeXml(property1)}saleId" styleClass="txtInputFull"/>
                            </td>
                            <td>
                                <tags:stylishButton ajax="false" type="button" other="style='width:100%'">
                                    Thêm
                                </tags:stylishButton>
                            </td>
                        </tr>

                    </table>
                </fieldset>
            </td>
        </tr>
        <tr>
            <td colspan="3" align="center">
                <br />
                <tags:stylishButton  ajax="false" type="button">
                    Lập giao dịch
                </tags:stylishButton>
            </td>
        </tr>
    </table>
</html:form>
