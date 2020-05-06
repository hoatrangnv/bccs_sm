<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:form action = "/loginAction.do" onsubmit="return validateForm()">
    <html:hidden property="action" value="login"/>
    <table align="center" width="100%" >
        <tr width="100%">
            <td height="320" valign="middle" width="100%">
                <table width="400px" height="200px" cellspacing="5" align="center"  background="../../../../share/img/bot_dn.gif">                            
                    <tr >
                        <td align="center" colspan="2">
                            &nbsp;
                        </td>                                
                    </tr>
                    <tr bgcolor="">
                        <td class="title" colspan="2">&nbsp
                            
                        </td>
                    </tr>                              
                    <tr>
                        <td class="userName" width="120px">
                            &nbsp
                        </td>
                        <td class="" >                                               
                            <html:text property="userName" onfocus="true" styleId="userName" style="width:185px;"/>
                            <script>
                                document.getElementById('userName').focus(); 
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <td class="password" width="120px">
                            &nbsp
                        </td>
                        <td class="">
                            <html:password property="password" styleId="password" style="width:185px;"/>
                        </td>
                        
                    </tr>
                    <logic:present name="error" scope="request">
                        <logic:equal name="error" scope="request" value="userName">
                            <tr class="label">                                                   
                                <script> 
                                    document.getElementById('userName').focus();
                                </script>
                            </tr>                                                
                        </logic:equal>
                        <logic:equal name="error" scope="request" value="Pass">
                            <tr class="label">                                                  
                                <script> 
                                    document.getElementById('password').focus();
                                </script>
                            </tr>                                                
                        </logic:equal>                                                                                  
                    </logic:present>                   
                    <tr>
                        <td>&nbsp</td>
                        <td align="center">
                            <html:hidden property="className" styleId="className" value="AuthenticateDAO"/>                  
                            <html:hidden property="methodName" styleId="methodName" value="actionLogin"/>
                            <html:submit property="login" value="Đăng nhập" styleClass="btnmain"/>                                              
                        </td>                                                                                                
                        
                    </tr>
                    <tr>
                        <td colspan="2" class="txtError" align="center">&nbsp;
                            <logic:present name="error" scope="request">
                                <logic:equal name="error" scope="request" value="userName">                                   
                                    Sai tên đăng nhập.                                   
                                </logic:equal>
                                <logic:equal name="error" scope="request" value="Pass">                                    
                                    Sai mật khẩu.                                    
                                </logic:equal>
                                <logic:equal name="error" scope="request" value="notEnable">                                 
                                    Người dùng đang bị khoá.
                                </logic:equal>
                            </logic:present>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>            
</html:form>


<script type="text/javascript" language="javascript">    
    function checkLength(obj){
        if(obj.value.length > 50){
            alert("Tên truy cập và Mật khẩu không được quá 50 ký tự.");
            obj.value="";
        }
    }
    
    function validateForm(){
        var username = document.getElementById('userName').value;
        var password = document.getElementById('password').value;
        
        if(username == ""){
            alert("Bạn chưa nhập Tên truy cập.");
            document.getElementById('userName').focus();
            return false;
        }
        
        if(password == ""){
            alert("Bạn chưa nhập Mật khẩu.");
            document.getElementById('password').focus();
            return false;
        }
        
        return true;
    }
    </script>
