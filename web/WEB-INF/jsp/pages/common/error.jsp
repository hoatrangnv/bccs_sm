<%-- 
    Document   : error
    Created on : Mar 30, 2016, 8:20:44 AM
    Author     : mov_itbl_dinhdc
--%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
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

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <table align="center" width="80%" >
            <tr  >
                <td align="center" class="listtitle" height="320">
                    You do not have access Web
                    <a href="share/logout.jsp" >Login again!</a>
                </td>
            </tr>
        </table>
        
    </body>
</html>
