<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table align="center" width="80%" >
            <tr>
                <td align="center" >
                    <font color="red"><b>Bạn không có quyền truy cập vào chương trình.
                        <a  href="share/logout.jsp">
                            Đăng nhập lại
                        </a>
                        
                        </b></font>
                    <s:property escapeJavaScript="true"  value="exception.message"/>
                    <s:property escapeJavaScript="true"  value="exceptionStack"/>
                </td>
            </tr>
            
            <tr>
                <td align="center">
                    
                    <s:if test="actionErrors != null && actionErrors.size > 0">
                        <s:actionerror/>
                    </s:if>
                </td>
            </tr>
            <tr>
                <td>
                    <div>
                        <s:property escapeJavaScript="true"  value="%{#exception}"/>
                    </div>
                </td>
            </tr>
        </table>
    
    
        
    </body>
</html>
