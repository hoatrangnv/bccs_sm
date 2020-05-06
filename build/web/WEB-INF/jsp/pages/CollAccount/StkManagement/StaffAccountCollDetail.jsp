<%-- 
    Document   : StaffAccountCollDetail
    Created on : Oct 10, 2009, 9:29:24 AM
    Author     : Vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>


<s:hidden name="collAccountManagmentForm.collId" id="collAccountManagmentForm.collId"/>
<tags:imPanel>
    <sx:div id="dislayAccountDetail">
        <table class="inputTbl8Col" style="width:100%">
            <tr>
                <s:if test="#session.typeId ==2">
                    <td class="label">Mã CTV</td>
                </s:if>
                <s:else>
                    <td class="label">Mã Đại lý</td>
                </s:else>
                <td class="text">
                    <s:textfield name="collAccountManagmentForm.collCodeAdd" id="collCodeAdd" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="1"/>
                </td>
                <s:if test="#session.typeId ==2">
                    <td class="label">Tên CTV</td>
                </s:if>
                <s:else>
                    <td class="label">Tên Đại lý</td>
                </s:else>                
                <td class="text">
                    <s:textfield name="collAccountManagmentForm.namestaff" id="namestaff" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="1"/>
                </td >
                <td class="label">Mã cửa hàng</td>
                <td class="text">
                    <s:textfield name="collAccountManagmentForm.shopcode" id="shopcode" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="1"/>
                </td>
                <td>Tên cửa hàng</td>
                <td>
                    <s:textfield name="collAccountManagmentForm.nameShopIdStaff" id="nameShopIdStaff" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="1"/>
                </td>
            </tr>

            <tr>
                <s:if test="#session.typeId ==2">
                    <td class="label">Địa chỉ CTV</td>
                </s:if>
                <s:else>
                    <td class="label">Địa chỉ Đại lý</td>
                </s:else>                
                <td class="text">
                    <s:textfield name="collAccountManagmentForm.address" id="address" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="1"/>
                </td>
                <td class="label">Ngày sinh</td>
                <td class="text">
                    <tags:dateChooser property="collAccountManagmentForm.birthday"  id="birthday"  styleClass="txtInput"  insertFrame="false"
                                      readOnly="true"/>
                </td>

                <td class="label">Số CMTND</td>
                <td class="text">
                    <s:textfield name="collAccountManagmentForm.idNo" id="idNo" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="1"/>
                </td>
                <td class="label">Điện thoại</td>
                <td class="text">
                    <s:textfield name="collAccountManagmentForm.tel" id="tel" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="1"/>
                </td>
            </tr>
            <tr>
                <td>Email</td>
                <td>
                    <s:textfield name="collAccountManagmentForm.email" id="email" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="1"/>
                </td>
            </tr>
        </table>
    </sx:div>
</tags:imPanel>



