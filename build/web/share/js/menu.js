// GetElementsByClass Function
function getElementsByClass(className) {
    var all = document.all ? document.all : document.getElementsByTagName('*');
    var elements = new Array();
    for (var i = 0; i < all.length; i++)
        if (all[i].className == className)
            elements[elements.length] = all[i];
    return elements;
}

// Menu Function
function menu(start,hover) {
    var elm = getElementsByClass(start);
    for (i=0; i < elm.length; i++) {
        elm[i].onmouseover = function() {
            this.className = hover;
            this.onmouseout = function() {
                this.className = start;
            }
        }
    }
}

// Call Menu Function on page load
window.onload = function() {
    menu('parent','activeParent');
}

var my_window = null;

//Mo popup full man hinh
function openPopupFull(path) {
    my_window = window.open(path,'',"location=" + (screen.width) + ",status=1, scrollbars=1, titlebar=0, width=" +screen.width + ",height=" + screen.height);
    my_window.focus();
    return false;
}
//Kiểm tra nếu cửa sổ popup chưa đóng lại thì ko cho phép thao tác trên cửa sổ chính
function check() {
    if(my_window && !my_window.closed)
        my_window.focus();
}

function viewStockDetail(contextPath){
    openPopupFull(contextPath+"/CommonStockAction.do?className=StockCommonDAO&methodName=prepareViewStockDetail");
}



function openSmallPopup(id) {
    var warrantyPopupFd = document.getElementById(id);
    warrantyPopupFd.style.display = '';
}
function closeSmallPopup(id) {
    var warrantyPopupFd = document.getElementById(id);
    warrantyPopupFd.style.display = 'none';
}

/*
 * @author: LongH
 * date 30/03/2009
 * mo 1 dialog
 */

