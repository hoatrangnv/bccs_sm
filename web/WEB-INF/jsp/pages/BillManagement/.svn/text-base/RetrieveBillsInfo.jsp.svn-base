

<%-- 
    Document   : SelectBillDepartment
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     : TungTV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<html:form action="/retrieveBillsInfoAction.do" method="POST" styleId="frm">
    <fieldset style="width:95%; padding:10px 10px 10px 10px">
        <legend class="transparent">Thông tin hóa đơn </legend>

        <table style="width:100%" cellpadding="0" cellspacing="4" border="0">
            
            <tr>
                <td class="label8Col">
                   <tags:label key="MSG.reasonRecover"/>
                </td>
                <td class="row8Col">
                    <html:select property="billRecoverReason" styleId="billRecoverReason" styleClass="txtInput">
                        <html:option value="0">--Lý do thu hồi hóa đơn--</html:option>
                        <html:option value="1">Thu hồi hóa đơn không sử dụng</html:option>
                        <html:option value="2">Thu hồi hóa đơn để hủy</html:option>
                    </html:select>
                </td>
                
            </tr>
            
                
        </table>
        
        <div style="width:100%" align="center">
            <tags:stylishButton ajax="false" type="button">Thu hồi hóa đơn</tags:stylishButton>
            <tags:stylishButton ajax="false" type="button">Xóa</tags:stylishButton>
        </div>
        
    </fieldset>
    
    
</html:form>