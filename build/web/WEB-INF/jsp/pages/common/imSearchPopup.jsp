<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : imSearchPopup
    Created on : Nov 17, 2009, 10:27:33 AM
    Author     : Doan Thanh 8
    Desc       : bat popup tim kiem thong tin
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
    request.getAttribute("listImSearchBean");
%>

<script>
    var selectedRowIndex = 0;
    
    //var preTime = new Date();
    //var delayTime = 3000; //thoi gian delay truoc khi truy van db 3s

    //kiem tra 1 keyCode da duoc su dung chua
    function checkKeyCodeHasUsed (keyCode) {
        var arrUsedKeyCode = new Array(119, 27, 38, 40, 13); //mang cac key code da duoc su dung
        for (var r = 1; r < arrUsedKeyCode.length; r++) {
            if (arrUsedKeyCode[r] == keyCode) {
                return true;
            }
        }
        return false;
    }


    //
    function imSearch() {
        document.getElementById("imSearchForm").submit();
    }

    //
    function selectIsdnBean(code, name) {
        var codeElementId = $('imSearchForm.codeVariable').value;
        var nameElementId = $('imSearchForm.nameVariable').value;
        window.opener.updateTagValue(codeElementId, nameElementId, code, name);
        window.close();
    }

    //
    function filterDataInTable (term, _id, cellNr, numberChar) {
        var suche = term.value.toLowerCase();
        var table = document.getElementById(_id);
        var cell;
        var ele;
        var bNoRowFound = true;

        //xoa bo dong duoc select truoc do (neu co)
        if(selectedRowIndex > 0) {
            if(selectedRowIndex % 2 == 1) {
            table.rows[selectedRowIndex].style.background='#F2F8FF';
            } else {
                table.rows[selectedRowIndex].style.background='#FFFFFF';
            }
            selectedRowIndex = 0;
        }

        //an cac row khong chua du lieu
        for (var r = 1; r < table.rows.length; r++){
            cell = table.rows[r].cells[cellNr];
            if(cell != undefined) {
                ele = cell.innerHTML.replace(/<[^>]+>/g,"");
                if (ele.toLowerCase().indexOf(suche)>=0 ) {
                    table.rows[r].style.display = '';
                    bNoRowFound = false;
                    if(selectedRowIndex == 0) {
                        selectedRowIndex = r;
                        table.rows[selectedRowIndex].style.background='#6698FF';
                    }
                } else {
                    table.rows[r].style.display = 'none';
                }
            }
        }

        //neu khong co row nao duoc tim thay tren client -> truy van vao DB
        if(bNoRowFound && numberChar != undefined && numberChar > 0) {
            if(suche.length >= numberChar) {
                //LamNV yeu cau cmt 23/02/2010
                //imSearch();
            }
        }
    }

    //
    function searchCodeInTable(value, keyCode) {
        if(keyCode == 13) {
            /*
            //phim enter -> lay du lieu cua dong dau tien -> cho xuong form cha
            var table = document.getElementById("tblListImSearchBean");
            var cell1;
            var ele1;
            var cell2;
            var ele2;
            var bNoRowFound = true;
            
            //lay du lieu dong dau tien chuyen xuong form cha
            for (var r = 1; r < table.rows.length; r++){
                if(table.rows[r].style.display == '') {
                    cell1 = table.rows[r].cells[1];
                    if(cell1 != undefined) {
                        ele1 = cell1.innerHTML.replace(/<[^>]+>/g,"");
                    }
                    cell2 = table.rows[r].cells[2];
                    if(cell2 != undefined) {
                        ele2 = cell2.innerHTML.replace(/<[^>]+>/g,"");
                    }
                    if(ele1 != undefined && ele2 != undefined) {
                        selectIsdnBean(ele1.trim(), ele2.trim());
                    }
                    bNoRowFound = false;
                    break;
                }
            }

            //neu danh sach rong -> tim kiem
            if(bNoRowFound) {
                imSearch();
            }
            */


        } else if (!checkKeyCodeHasUsed(keyCode)) {
            filterDataInTable(value, 'tblListImSearchBean', 1, 3);
        }
    }

    //
    function searchNameInTable(value, keyCode) {
        if(keyCode == 13) {
            /*
            //phim enter -> lay du lieu cua dong dau tien -> cho xuong form cha
            var table = document.getElementById("tblListImSearchBean");
            var cell1;
            var ele1;
            var cell2;
            var ele2;

            //lay du lieu dong dau tien chuyen xuong form cha
            for (var r = 1; r < table.rows.length; r++){
                if(table.rows[r].style.display == '') {
                    cell1 = table.rows[r].cells[1];
                    if(cell1 != undefined) {
                        ele1 = cell1.innerHTML.replace(/<[^>]+>/g,"");
                    }
                    cell2 = table.rows[r].cells[2];
                    if(cell2 != undefined) {
                        ele2 = cell2.innerHTML.replace(/<[^>]+>/g,"");
                    }
                    if(ele1 != undefined && ele2 != undefined) {
                        selectIsdnBean(ele1.trim(), ele2.trim());
                    }
                    break;
                }
            }
            */
        } else if(!checkKeyCodeHasUsed(keyCode)) {
            filterDataInTable(value, 'tblListImSearchBean', 2, 20);
        }
    }

    //
    document.onkeydown = function(e) {
        var evt = (window.event) ? window.event : e;
        var keyId = evt.keyCode;
        if(keyId == 119) {
            //F8 -> focus vao truong dau tien
            $('imSearchForm.code').focus();
        } else if (keyId == 27) {
            //esc -> dong popup
            window.close();
        } else if (keyId == 38) {
            //up
            var divContainerTable = document.getElementById("divImListSearch");
            divContainerTable.focus();

            var table = document.getElementById("tblListImSearchBean");
            var preRowIndex = table.rows.length;

            //lay dong tiep theo
            for (var r = selectedRowIndex - 1; r > 0; r--){
                if(table.rows[r].style.display == '') {
                    preRowIndex = r;
                    break;
                }
            }

            if(selectedRowIndex > preRowIndex) {
                if(selectedRowIndex % 2 == 1) {
                    table.rows[selectedRowIndex].style.background='#F2F8FF';
                } else {
                    table.rows[selectedRowIndex].style.background='#FFFFFF';
                }
                selectedRowIndex = preRowIndex;
                table.rows[selectedRowIndex].style.background='#6698FF';
            }

        } else if (keyId == 40) {
            //down
            var imCode = document.getElementById("imSearchForm.code");
            imCode.blur();
            var divContainerTable = document.getElementById("divImListSearch");
            divContainerTable.focus();

            var table = document.getElementById("tblListImSearchBean");

            var nextRowIndex = -1;
            //lay dong tiep theo
            for (var r = selectedRowIndex + 1; r < table.rows.length; r++){
                if(table.rows[r].style.display == '') {
                    nextRowIndex = r;
                    break;
                }
            }

            if(selectedRowIndex < nextRowIndex) {
                if(selectedRowIndex % 2 == 1) {
                    table.rows[selectedRowIndex].style.background='#F2F8FF';
                } else {
                    table.rows[selectedRowIndex].style.background='#FFFFFF';
                }
                selectedRowIndex = nextRowIndex;
                table.rows[selectedRowIndex].style.background='#6698FF';
            }
        } else if (keyId == 13) {
            var table = document.getElementById("tblListImSearchBean");
            var cell1;
            var ele1;
            var cell2;
            var ele2;

            //lay du lieu chuyen xuong form cha
            if(selectedRowIndex > 0) {
                cell1 = table.rows[selectedRowIndex].cells[1];
                if(cell1 != undefined) {
                    ele1 = cell1.innerHTML.replace(/<[^>]+>/g,"");
                }
                cell2 = table.rows[selectedRowIndex].cells[2];
                if(cell2 != undefined) {
                    ele2 = cell2.innerHTML.replace(/<[^>]+>/g,"");
                }
                if(ele1 != undefined && ele2 != undefined) {
                    selectIsdnBean(ele1.trim(), ele2.trim());
                }
            }
        }
    }

</script>

<tags:imPanel title="imPanel.imSearchPopup.searchInfor">
        <!-- phan hien thi message -->
        <div>
            <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
        </div>

        <!-- phan tim kiem thong tin (loc danh sach) -->
        <div class="divHasBorder">
            <s:form action="ImSearchPopupAction!search.do" theme="simple" method="post" id="imSearchForm">
<s:token/>

                <s:hidden name="imSearchForm.searchClassName" id="imSearchForm.searchClassName"/>
                <s:hidden name="imSearchForm.searchMethodName" id="imSearchForm.searchMethodName"/>
                <s:hidden name="imSearchForm.codeVariable" id="imSearchForm.codeVariable"/>
                <s:hidden name="imSearchForm.nameVariable" id="imSearchForm.nameVariable"/>
                <s:hidden name="imSearchForm.codeLabel" id="imSearchForm.codeLabel"/>
                <s:hidden name="imSearchForm.nameLabel" id="imSearchForm.nameLabel"/>
                <s:hidden name="imSearchForm.otherParam" id="imSearchForm.otherParam"/>
                <s:hidden name="imSearchForm.otherParamValue" id="imSearchForm.otherParamValue"/>
                <input type="hidden"  name="pageId" id="pageId"/> <!--ThanhNC them de luu pageId tren popup-->
                <table class="inputTbl5Col">
                    <tr>
                        <td style="width:10%; ">${fn:escapeXml(imDef:imGetText(imSearchForm.codeLabel))}</td>
                        <td class="oddColumn" style="width:25%; ">
                            <s:textfield name="imSearchForm.code" id="imSearchForm.code" maxlength="50" cssClass="txtInputFull" onkeyup="searchCodeInTable(this, event.keyCode)"/>
                        </td>
                        <td style="width:10%; ">${fn:escapeXml(imDef:imGetText(imSearchForm.nameLabel))}</td>
                        <td class="oddColumn">
                            <s:textfield name="imSearchForm.name" id="imSearchForm.name" maxlength="100" cssClass="txtInputFull" onkeyup="searchNameInTable(this, event.keyCode)"/>
                        </td>
                        <td style="width:85px; text-align:right; ">
                            <input type="button" value="${fn:escapeXml(imDef:imGetText('imSearchPopup.btnSearch'))}" style="width:80px;" onclick="imSearch()">
                        </td>
                    </tr>
                </table>
            </s:form>
        </div>

        <!-- phan hien thi danh sach cac dich vu ban hang-->
        <div>
            <jsp:include page="imSearchList.jsp">
                <jsp:param name="imSearchCodeLabel" value="${fn:escapeXml(imSearchForm.codeLabel)}"/>
                <jsp:param name="imSearchNameLabel" value="${fn:escapeXml(imSearchForm.nameLabel)}"/>
            </jsp:include>
        </div>

        <div class="divHasBorder" style="text-align: right; padding: 3px; margin-top: 3px; font-size: 12px;">
            ${fn:escapeXml(imDef:imGetText('imSearchPopup.lblDisplay'))} ${fn:escapeXml(listImSearchSize)} ${fn:escapeXml(imDef:imGetText('imSearchPopup.lblSearchResult'))}
        </div>

</tags:imPanel>

<script>
    //focus vao truong dau tien
    $('imSearchForm.code').focus();
</script>

