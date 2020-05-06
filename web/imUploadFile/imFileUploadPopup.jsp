<%-- 
    Document   : imFileUploadPopup
    Created on : Sep 12, 2009, 1:58:22 PM
    Author     : Doan Thanh 8
--%>

<%--
    Note       : bat popup chon file, upload len server
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://www.davidjc.com/taglibs" prefix="afile"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>


<head>
    <style type="text/css">
        .dojoLayoutContainer{ position: relative; display: block; overflow: hidden; }
        body .dojoAlignTop, body .dojoAlignBottom, body .dojoAlignLeft, body .dojoAlignRight { position: absolute; overflow: hidden; }
        body .dojoAlignClient { position: absolute }
        .dojoAlignClient { overflow: auto; }
    </style>
    <title><tags:label key="MSG.ISN.045"/></title>


    <script src="${contextPath}/imUploadFile/yahooapis_js/yahoo-min.js"></script>
    <script src="${contextPath}/imUploadFile/yahooapis_js/event-min.js"></script>
    <script src="${contextPath}/imUploadFile/yahooapis_js/connection-min.js"></script>

    <afile:head />

</head>

<style>
    body {font-family:"Tahoma";}
    .file_container {width:480px;font-size:11px;border: 1px solid black;margin:0px;border-collapse:collapse;}
    .filelist {font-size:11px;width:100%;margin:0px;border-collapse:collapse;}
    #ajaxFileUploadForm .label {font-size:11px;}
    #ajaxFileUploadForm input {font-size:11px;}
    .filelist tr.odd {background-color:#eef;}
    .filelist tr.even { background-color:#dde;}
    .filelist tr td {padding:4px;}
</style>

<script>
/*
    var callback =
        {
        cache:false,
        success: function(o) {
            document.getElementById('did').innerHTML = o.responseText;
            var clientFileName = document.getElementById('tamdt1_clientFileName').value;
            var clientFileNameId = document.getElementById('tamdt1_clientFileNameId').value;
            var serverFileName = document.getElementById('tamdt1_serverFileName').value;
            var serverFileNameId = document.getElementById('tamdt1_serverFileNameId').value;
            var hasImUploadFileError = document.getElementById('tamdt1_hasImUploadFileError').value;
            if(hasImUploadFileError == 'false') {
                window.opener.refreshFilePath(clientFileNameId, clientFileName, serverFileNameId, serverFileName);
                window.close();
            }
        }
    }
    
    function show_list() {
        var transaction = YAHOO.util.Connect.asyncRequest('GET', '${contextPath}/ImFileUploadDetailsAction.do', callback, null);
    }

    function closeWindowPopup() {
        //var clientFileName = document.getElementById('tamdt1_clientFileName').value;
        //var serverFileName = document.getElementById('tamdt1_serverFileName').value;
        //window.opener.refreshFilePath(clientFileName, serverFileName);
        window.close();
    }
    */

    //
    document.onkeydown = function(e) {
        var evt = (window.event) ? window.event : e;
        var keyId = evt.keyCode;
        if (keyId == 27) {
            //esc -> dong popup
            window.close();
        }
    }

</script>

<body>
<link type="text/css" charset="UTF-8" href="${contextPath}/struts/xhtml/styles.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/site.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/ajaxtags.css" type="text/css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/site_1.css" type="text/css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/displaytag.css" type="text/css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/img/aqua/theme.css" type="text/css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/style.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/custom.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/displaytagex.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/dtree.css'/&gt;" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/anylink.css'/&gt;" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/tpl/css/template.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/tpl/css/form.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/tpl/css/custom.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/extjs/css/ext-all.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/extjs/css/my-ext.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/privateStyle.css" rel="stylesheet">
<link type="text/css" charset="UTF-8" href="${contextPath}/share/css/dialog.css" rel="stylesheet">


<input type="hidden" id="tamdt1_clientFileNameId" value="${fn:escapeXml(param.clientFileNameId)}"/>
<input type="hidden" id="tamdt1_serverFileNameId" value="${fn:escapeXml(param.serverFileNameId)}"/>


<div id="popupBody">
    <table width="100%" border="0" align="center">
        <tbody><tr>
                <td></td>
                <td>
                    <div align="center" style="position: absolute; display: none; border: 1px solid rgb(0, 238, 0); background-color: rgb(238, 255, 238); padding: 2px; margin-top: 8px; width: 300px; font: 12px Arial; color: red;" id="updateSuccess"><h2>Thao tác được thực hiện thành công</h2></div>
                </td>
                <td></td>
            </tr>
        </tbody>
    </table>

    <table width="100%" border="0" align="center">
        <tbody><tr>
                <td></td>
                <td>
                    <div align="center" style="position: absolute; display: none; border: 1px solid rgb(0, 238, 0); background-color: rgb(238, 255, 238); padding: 2px; margin-top: 8px; width: 300px; font: 12px Arial; color: red;" id="msgResultJavaScript">
                        <h2>

                        </h2>
                    </div>
                </td>
                <td></td>
            </tr>
        </tbody>
    </table>

    <div id="content-box">
        <div class="border">
            <div class="padding">
                <div id="toolbar-box">
                    <div class="t">
                        <div class="t">
                            <div class="t"></div>
                        </div>
                    </div>
                    <div class="m">
                        <div id="toolbar" class="toolbar">
                            <table class="toolbar"><tbody><tr>
                                    </tr></tbody></table>
                        </div>
                        <!-- [BEGIN] Header -->
                        <div>
                            <div id="myHeader" class="header" style="float: left;">
                                Upload file dữ liệu
                            </div>
                            <div style="float: right;">
                                <table class="toolbar">
                                    <tbody>
                                        <tr>
                                            <td width="100px" align="left" class="button">
                                                <a onclick="window.close()">
                                                    <span title="Về trang chủ" style="background-image: url(&quot;${contextPath}/share/img/home.png&quot;);">
                                                    </span>
                                                    Về trang chủ
                                                </a>
                                            </td>
                                            <td></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!-- [END] Header-->
                        <div class="clr"></div>
                    </div>
                    <div class="b">
                        <div class="b">
                            <div class="b"></div>
                        </div>
                    </div>
                </div>
                <div class="clr"></div>

                <div id="element-box">
                    <div class="t">
                        <div class="t">
                            <div class="t"></div>
                        </div>
                    </div>
                    <div align="center" class="m">
                        <div class="im-panel">
                            <div class="im-panel-tl">
                                <div class="im-panel-tr">
                                    <div class="im-panel-tc">
                                        <div style="-moz-user-select: none; text-align: left;" class="x-panel-header x-unselectable">
                                            <span class="x-panel-header-text"><tags:label key="MSG.ISN.045"/></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="x-panel-bwrap">
                                <div class="x-panel-ml">
                                    <div class="x-panel-mr">
                                        <div class="x-panel-mc">

                                            <!-- phan hien thi message -->
                                            <div>

                                                <div align="center" class="txtError" id="message">

                                                </div>

                                            </div>

                                            <!-- phan tim kiem thong tin (loc danh sach) -->
                                            <div class="divHasBorder">
                                                <div style="" id="ajaxfileupload">
                                                    <div id="fileuploadForm">
                                                        <form enctype="multipart/form-data" method="post" action="ImFileUploadAction.do?serverFileName=" onsubmit="return false" id="ajaxFileUploadForm">
                                                            <table class="inputTbl3Col">
                                                                <tbody>
                                                                    <tr>
                                                                        <td style="width:25%; "><tags:label key="MSG.ISN.046"/></td>
                                                                        <td class="oddColumn" style="width:60%; ">
                                                                            <input type="file" id="upload" accept="*/*" value="" size="60" name="upload">
                                                                        </td>
                                                                        <td style="text-align: right;">
                                                                            <input type="submit" onclick="return davidjc.AjaxFileUpload.initialise(undefined, show_list);" value="Upload" style="width:80px;" id="ajaxFileUploadForm_0">
                                                                        </td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </form>
                                                    </div>
                                                    <div id="fileuploadProgress" style="display: none;">
                                                        <span id="uploadFilename">Initialising, please wait.....</span>
                                                        <div id="progress-bar">
                                                            <div id="progress-bgrd" style="width: 0px;"></div>
                                                        </div>
                                                        <div id="progress-text"></div>
                                                        <br>
                                                    </div>

                                                </div>


                                            </div>

                                            <!-- phan hien thi danh sach cac dich vu ban hang-->
                                            <div>
                                                <fieldset class="imFieldset">
                                                    <legend><tags:label key="MSG.ISN.047"/> </legend>
                                                    <div style="height: 180px; overflow: auto;" id="did">
                                                        <jsp:include page="imFileUploadList.jsp"/>
                                                    </div>
                                                </fieldset>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="x-panel-bl">
                                    <div class="x-panel-br">
                                        <div class="x-panel-bc">
                                            <div class="x-panel-footer">
                                                <div class="x-panel-btns x-panel-btns-right">
                                                    <div style="width: auto;" class="x-panel-fbar x-small-editor x-toolbar-layout-ct">
                                                    </div>
                                                    <!--div class="x-clear"/-->
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="b">
                        <div class="b">
                            <div class="b"></div>
                        </div>
                    </div>
                </div>
                <noscript>Must be enable JavaScript</noscript>
                <div class="clr"></div>
            </div>
            <div class="clr"></div>
        </div>
    </div>
    <div id="border-bottom">
        <div>
            <div>
            </div>
        </div>
    </div>
    <div id="footer">

        <div style="width:100%; text-align:center; padding-top:10px; ">

        </div>
        <p class="copyright"></p>
        <div align="center">
<!--            Phát triển bởi Trung tâm IT - Công ty Viễn thông Viettel-->
        Developed by IT Center-Viettel Telecom
        </div>
        <p></p>
    </div>
</div>


</body>

