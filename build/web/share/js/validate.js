//---- ham kiem tra xem mot obj co la date ko



// DucDD - Start

/*Author: DucDD
 * Date created: 18/04/2009
 * Purpose: check validate theo regex
*/
isMoneyFormat = function (value){
    var regExpMoney = /^[0-9\,]+$/;
    var result = value.match(regExpMoney);
    if(result == null)
        return false;
    else
        return true;
}
isNumberFormat = function (value){
    var regExpNumber = /^[0-9]+$/;
    var result = value.match(regExpNumber);
    if(result == null)
        return false;
    else
        return true;
}
isHtmlTagFormat = function (value){
    var reDoubleTag = /<.+>.*<\/.+>/;
    var reSingleTag = /<.+\/?>?/;
    var result = value.match(reDoubleTag);
    var result2 = value.match(reSingleTag);
    if(result != null || result2 != null)
        return true;
    else
        return false;
}
isLinkFormat = function (value){ 
    var regExpLink = '(((file|gopher|news|nntp|telnet|http|ftp|https|ftps|sftp)://)|(www\.))+(([a-zA-Z0-9\._-]+\.[a-zA-Z]{2,6})|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(/[a-zA-Z0-9\&amp;%_\./-~-]*)?';
    var result = value.match(regExpLink);
    if(result == null)
        return false;
    else
        return true;
}

isFormalCharacter = function(value){     
    var regExp = /^[a-zA-Z0-9\-\_]+$/;
    var result = value.match(regExp);
    if(result == null)
        return false;
    else
        return true;
}

//DucDD - End


function isDateFormat(value) 

{ 
    
    var date=value.substr(0,10);                
    
    
    
    // Regular expression used to check if date is in correct format 
    
    var pattern = new RegExp(/^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/); 
    
    
    
    // kiem tra date
    
    if(date.match(pattern)){ 
        
        var date_array = date.split('/');            
        
        var day = date_array[0]; 
        
        
        
        // Attention! Javascript consider months in the range 0 - 11 
        
        var month = date_array[1] - 1; 
        
        var year = date_array[2]; 
        
        
        
        // This instruction will create a date object 
        
        source_date = new Date(year,month,day); 
        
        
        
        if(year != source_date.getFullYear()) 
            
        { 
            
            return false; 
            
        } 
        
        
        
        if(month != source_date.getMonth()) 
            
        { 
            
            return false; 
            
        } 
        
        
        
        if(day != source_date.getDate()) 
            
        { 
            
            return false; 
            
        } 
        
    }else {
    
        return false;
    
    }



    // kiem tra time

    if(value.length>10){
    
        var time=value.substr(11);
    
        if(time.length ==8){
        
            var hour=time.substr(0,2);
        
            var minute=time.substr(3,2);
        
            var second=time.substr(6);
        
        
        
            if(parseInt(hour) > 23 && parseInt(hour) < 0){
            
                return false;
            
            }
        
            if(parseInt(minute) > 60 && parseInt(minute) < 0){
            
                return false;
            
            }
        
            if(parseInt(second) > 60 && parseInt(second) < 0){
            
                return false;
            
            }
        
        }else{
    
            return false;
    
        }

    }



    return true;

}



function isNum(inpString) {
    
    return /^[-+]?\d+(\d+)?$/.test(inpString);
    
}



function isInteger(s)
{   
    var value = trim(s);
    if (value.length == 0){
        return false;
    }
    var i;
    
    for (i = 0; i < value.length; i++)
        
    {   
        
            // Check that current character is number.
        
            var c = value.charAt(i);
        
            if (((c < "0") || (c > "9"))) return false;
        
        }
    
    // All characters are numbers.
    
    return true;
    
}



function validateDate(obj){
    
    var checkString = obj.value;
    
    checkString = trim(checkString);
    
    if (checkString != ""){
        
        if (!isDateFormat(checkString)){
            
            alert ('Ngày tháng không hợp lệ!');
            
            obj.value = "";
            
            obj.focus();
            
        }
        
    }
    
}



function validatePositiveNumber(obj){
    var checkString = obj.value;
    checkString = trim(checkString);
    if (checkString != ""){
        if (!isNum(checkString)){
            //alert ('Dữ liệu kiểu số không hợp lệ!');
            obj.value = "";
            
            obj.focus();
            
        }
        else if (!checkNumber(obj)){                                
            obj.value = "";
            obj.focus();
        }
        
        
        
    }
    
}

//ham so sanh xem date1 co nho hon date2 ko? date 
//pattern = "dd/MM/yyyy HH:mm:ss"

function compareDates(date1,date2){ 
    
    var arrayDate1 = date1.split("/");
    
    var arrayDate2 = date2.split("/");
    
    var arrayTime1 = date1.split(":");
    var arrayTime2 = date2.split(":");
    
    var nam1   = parseFloat(arrayDate1[2]);
    
    var nam2   = parseFloat(arrayDate2[2]);
    
    var thang1 = parseFloat(arrayDate1[1]);
    
    var thang2 = parseFloat(arrayDate2[1]);
    
    var ngay1  = parseFloat(arrayDate1[0]);
    
    var ngay2  = parseFloat(arrayDate2[0]);
    
    
    var h1 = parseFloat(arrayTime1[0]);
    var h2 = parseFloat(arrayTime2[0]);
    
    var p1 = parseFloat(arrayTime1[1]);
    var p2 = parseFloat(arrayTime2[1]);
    
    var s1 = parseFloat(arrayTime1[2]);
    var s2 = parseFloat(arrayTime2[2]);
    
    
    if(nam1 < nam2){
        return true;
    }
    if(nam1 == nam2){
        if(thang1 < thang2){
            return true;
        }
        if(thang1 == thang2){
            if(ngay1 < ngay2){
                return true;
            }
            if( ngay1 == ngay2) {
                if( h1 < h2){
                    return true;
                }
                if( p1 < p2){
                    return true;
                }
                if( s1 < s2){
                    return true;
                }
            }
        }
    }
    return false;
    
}



function confirmDelete()

{
    
    var agree=confirm("Bạn có chắc chắn muốn xóa không?");
    
    if (agree)
        
        return true;
    
    else
        
        return false;
    
}





function checkNumber(obj){
    
    if(!isInteger(obj.value)){
        
        alert("Dữ liệu phải là số nguyên dương");
        
        obj.value="0";
        
        return false;
        
    }else{
    
        return true;
    
    }

}





function checkHour(obj){
    
    if(!checkNumber(obj)){
        
        return false;
        
    }
    
    if(parseInt(obj.value) > 24 || parseInt(obj.value) < 0){
        
        alert("Giá trị giờ phải lớn hơn 0 và nhỏ hơn 24");
        
        obj.value="0";
        
        return false;
        
    }else{
    
        return true;
    
    }

}





function checkMinute(obj){
    
    if(!checkNumber(obj)){
        
        return false;
        
    }
    
    if(parseInt(obj.value) > 60 || parseInt(obj.value) < 0){
        
        alert("Giá trị phút phải lớn hơn 0 và nhỏ hơn 60");
        
        obj.value="0";
        
        return false;
        
    }else{
    
        return true;
    
    }

}



function days_between(date1, date2) {
    
    var arrayDate1 = date1.split("/");
    
    var arrayDate2 = date2.split("/");
    
    
    
    var startYear   = parseFloat(arrayDate1[2]);
    
    var endYear   = parseFloat(arrayDate2[2]);
    
    var startMonth = parseFloat(arrayDate1[1]);
    
    var endMonth = parseFloat(arrayDate2[1]);
    
    var startDay  = parseFloat(arrayDate1[0]);
    
    var endDay  = parseFloat(arrayDate2[0]);
    
    
    
    var start = new Date(startYear,startMonth-1,startDay);
    
    var stop  = new Date(endYear,endMonth-1,endDay);
    
    var diff  = (stop - start) / (1000 * 60 * 60 * 24);
    
    return diff;
    
}

function years_between(fromDate, toDate) {
    var arrayDateFrom = fromDate.split("/");
    var arrayDateTo = toDate.split("/");
    var fromYear   = parseFloat(arrayDateFrom[2]);
    var toYear   = parseFloat(arrayDateTo[2]);
    var diff  = toYear - fromYear;

    return diff;
}

// add by dinhvv
function is_valid_email(email){   
    return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(email);
}

function is_empty(str){          
    if((trim(str)).length == 0){
        return true;
    }          
    return false;
}


function trim(stringToTrim) {
    return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function ltrim(stringToTrim) {
    return stringToTrim.replace(/^\s+/,"");
}
function rtrim(stringToTrim) {
    return stringToTrim.replace(/\s+$/,"");
}

function validateYear(year){
    
    if( is_empty(year.value)){
        alert('Hãy chọn năm thanh toán');
        year.focus();
        return false;
    }    
    if(year.value*1 != year.value) {
        alert('Hãy nhập năm là số');
        year.focus();
        return false;
    }            
    var d = new Date();
    var currentYear = d.getFullYear();
    
    if(parseInt(year.value) > currentYear){
        alert('Hãy nhập năm nhỏ hơn năm hiện tại');
        year.focus();
        return false;
    }       
    return true;
}

/** Date */
function checkDate(elem, formatMsg, notExitMsg){
    
    var dateReg = /^(\d{2})(\/)(\d{2})(\/)(\d{4})$/;
    //return dateReg.test(strTime.trim());
    if(elem != null && trim(elem.value).match(dateReg)){
        if (isDateTime(elem)) {
            return true;
        } else {
            alert(notExitMsg);
            elem.focus();
            return false;
        }
    }else{
        alert(formatMsg);
        elem.focus();
        return false;
    }
}

function isDateTime(elem) {    
    var date_array = elem.value.split('/');
    var day = date_array[0];
    var month = date_array[1] - 1; // Attention! Javascript consider months in the range 0 - 11
    var year = date_array[2];

    // This instruction will create a date object
    var vDate = new Date(year,month,day);

    if(year != vDate.getFullYear()
        || month != vDate.getMonth()
        || day != vDate.getDate())
        {
        return false;
    } else {
        return true;
    }
}

function checkFormatDate(val) {
    var dateReg = /^(\d{2})(\/)(\d{2})(\/)(\d{4})$/;
    //return dateReg.test(strTime.trim());
    if(val.match(dateReg)){
        return true;
    } else {
        return false;
    }
}

function checkExistDate(val) {
    var date_array = val.split('/');
    var year = date_array[0];
    var month = date_array[1] - 1; // Attention! Javascript consider months in the range 0 - 11
    var day = date_array[2];

    // This instruction will create a date object
    var vDate = new Date(year,month,day);

    if(year != vDate.getFullYear()
        || month != vDate.getMonth()
        || day != vDate.getDate())
        {
        return false;
    } else {
        return true;
    }
}

function compareDateForCurrentTime(elemFromDate, msg)  {
    var currentTime = new Date();
    var month = currentTime.getMonth() + 1;
    var day = currentTime.getDate();
    var year = currentTime.getFullYear();
    
    if(month.toString().length == 1){
        month = "0" + month;
    }    
    if(day.toString().length == 1){
        day  = "0" + day;
    }
    var dateTo = new Date(day + "/" + month + "/" + year);
    currentTime = dateTo.getTime();
    var dateFrom = Date.parse(elemFromDate.value);    
    if(dateFrom < currentTime)   {
        alert(msg);
        return false;
    } else {
        return true;
    }

}

function compareDate(elemFromDate, elemToDate, msg) {
    // This instruction will create a date object    
    var dateFrom = Date.parse(elemFromDate.value);
    var dateTo = Date.parse(elemToDate.value);    
    if(dateFrom > dateTo)
    {
        alert(msg);
        return false;
    } else {
        return true;
    }
}

function byteCount(str){
    var count = 0;
    for (i=0; i<str.length; i++){
        n = escape(str.charAt(i));
        if (n.length < 4) {
            count++;        //ky tu 1byte
        } else {
            count+=2;      //ky tu 2 byte
        }
    }
    return count;
}
// end dinhvv
//Begin BinhBM
function doCollapse(item) {
    obj=document.getElementById(item);
    col=document.getElementById("x" + item);
    if (obj.style.display=="none") {
        obj.style.display="block";
        col.innerHTML="[-]";
    } else {
        obj.style.display="none";
        col.innerHTML="[+]";
    }
}
//End BinhBM


//tamdt1 - start
//ham kiem tra so dien thoai, so fax
//  dau vao: chuoi ky tu can kiem tra
//  dau ra : true neu dung dinh dang sdt, false neu khong dung
function isPhoneNumber(str){
    var i = 0;
    var strNumber = '0123456789';
    for(i = 0; i < str.length; i++){
        if(strNumber.indexOf(str[i]) == -1){
            return false;
        }
    }
    return true;
}


//ham kiem tra 1 chuoi chi duoc phep chua cac ky tu tu A-Z, a-z, 0-9, _
//dau vao:  - chuoi can kiem tra
//          - chuoi rong co hop le hay ko
//          - chuoi co duoc chua ky tu ' ' (space) khong
function isValidInput() {
    var sText = "";
    var bEmptyAllowed = true;
    var bSpaceAllowed = true;

    switch( arguments.length ) {
        case 1:
            sText = arguments[0];
            break;
        case 2:
            sText = arguments[0];
            bEmptyAllowed = arguments[1];
            break;
        case 3:
            sText = arguments[0];
            bEmptyAllowed = arguments[1];
            bSpaceAllowed = arguments[2];
            break;
    }

    if(bEmptyAllowed) {
        if(bSpaceAllowed) {
            var re = /^[a-zA-Z0-9_\s]*$/;
        } else {
            var re = /^[a-zA-Z0-9_]*/;
        }
    } else {
        if(bSpaceAllowed)
            var re = /^[a-zA-Z0-9_\s]+$/;
        else
            var re = /^[a-zA-Z0-9_]+$/;
    }

    return re.test( sText );
}

//ham kiem tra 1 chuoi chi duoc phep chua cac ky tu tu A-Z, a-z
//dau vao:  - chuoi can kiem tra
//          - chuoi rong co hop le hay ko
//          - chuoi co duoc chua ky tu ' ' (space) khong
function stringContainsOnlyLetters() {
    var sText = "";
    var bEmptyAllowed = true;
    var bSpaceAllowed = true;

    switch( arguments.length ) {
        case 1:
            sText = arguments[0];
            break;
        case 2:
            sText = arguments[0];
            bEmptyAllowed = arguments[1];
            break;
        case 3:
            sText = arguments[0];
            bEmptyAllowed = arguments[1];
            bSpaceAllowed = arguments[2];
            break;
    }

    if(bEmptyAllowed) {
        if(bSpaceAllowed) {
            var re = /^[a-zA-Z\s]*$/;
        } else {
            var re = /^[a-zA-Z]*/;
        }
    } else {
        if(bSpaceAllowed)
            var re = /^[a-zA-Z\s]+$/;
        else
            var re = /^[a-zA-Z]+$/;
    }

    return re.test( sText );
}

//ham kiem tra 1 chuoi co chua cac ky tu dac biet khong
//dau vao:  - chuoi can kiem tra
//dau ra :  - true neu chuoi co chua cac ky tu dac biet
//          - false neu chuoi khong chua cac ky tu dac biet
function checkingSpecialCharacter() {
    var sText = "";
    
    switch( arguments.length ) {
        case 1:
            sText = arguments[0];
            break;
        case 2:
            
            break;
        case 3:
            
            break;
    }
    
    var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?~_";
    for (var i = 0; i < sText.length; i++) {
        if (iChars.indexOf(sText.charAt(i)) != -1) {
            return true;
        }
    }
    
    return false;
}


//tamdt1 - end
function setADSLAddress(){
    setAddress('adslForm.province','adslForm.district','adslForm.precinct','adslForm.deployAddress');
}
function setPSTNAddress(){
    setAddress('pstnForm.province','pstnForm.district','pstnForm.precinct','pstnForm.deployAddress');
}
function setLeasedLineAddress(){
    setAddress('leaseLineForm.province','leaseLineForm.district','leaseLineForm.precinct','leaseLineForm.deployAddress');
}
function setContractAddress(){
    setAddress('contractForm.contractProvince','contractForm.contractDistrict','contractForm.contractPrecinct','contractForm.payFullAddress');
}

//ThanhNC
//Goi y nhap dia chi khi chon tinh, quan huyen
function setAddress(pro, dist,pre,add,to,duongpho,sonha)
{
    var provinceList = document.getElementById(pro);    
    var province;
    for(var i = 0; i < provinceList.options.length; i++) {
        if (provinceList.options[i].selected == true){
            if (i > 0){
                province = provinceList.options[i].text;
            }else{
                province = "";
            }
            break;
        }
    }

    var districtList = document.getElementById(dist);
    var district;
    for(var i = 0; i < districtList.options.length; i++) {
        if (districtList.options[i].selected == true){
            if (i > 0){
                district = districtList.options[i].text;
            }else{
                district = "";
            }
            break;
        }
            
    //document.getElementById('liveAddress').value = provinceList.options[i].text;
    }

    var precinctList = document.getElementById(pre);
    var precinct;
    for(var i = 0; i < precinctList.options.length; i++) {
        if (precinctList.options[i].selected == true){
            if (i > 0){
                precinct = precinctList.options[i].text;
            }else{
                precinct = "";
            }
            break;
        }
            
    //document.getElementById('liveAddress').value = provinceList.options[i].text;
    }
    var sTo="";
    if(to!=null){
        sTo =document.getElementById(to).value;
        sTo=sTo +", " ;
    }
    var sDuongpho="";
    if(duongpho!=null){
        sDuongpho =document.getElementById(duongpho).value;
        sDuongpho=sDuongpho+", ";
    }
    var sSonha="";
    if(sonha!=null){
        sSonha =document.getElementById(sonha).value;
        sSonha=sSonha+", ";
    }
    if (province == '' || district == '' || precinct == ''){
        document.getElementById(add).value = '';
    }else{
        document.getElementById(add).value = sSonha+ sTo+sDuongpho + precinct + ", " + district + ", " + province;
    }


    return true;
}


//TrongLV
//So sanh Date1 & Date2
//FORMAT = VN: DD/MM/YYYY, #VN: MM/DD/YYYY
//return Date1<=Date2?true:false;
function isCompareDate(Date1,Date2, DateFormat){
    var arrayDate1 = Date1.split("/");
    var arrayDate2 = Date2.split("/");
    var indexDay = 0;
    var indexMonth = 1;

    if (DateFormat != "VN"){
        indexDay = 1;
        indexMonth = 0;
    }

    var nam1   = parseFloat(arrayDate1[2]);

    var nam2   = parseFloat(arrayDate2[2]);

    var thang1 = parseFloat(arrayDate1[indexMonth]);

    var thang2 = parseFloat(arrayDate2[indexMonth]);

    var ngay1  = parseFloat(arrayDate1[indexDay]);

    var ngay2  = parseFloat(arrayDate2[indexDay]);
    
    if(nam1 < nam2){
        return true;
    }
    if(nam1 == nam2){
        if(thang1 < thang2){
            return true;
        }
        if(thang1 == thang2){
            if(ngay1 <= ngay2){
                return true;
            }           
        }
    }
    return false;

}

/*
 * Author: TheTM
 * Date created: 1/11/2010
 * Purpose: Ham kiem tra 1 so co dang Double hay khong
 */

function isNumeric(val) {
    var validChars = '0123456789.';

    for(var i = 0; i < val.length; i++) {
        if(validChars.indexOf(val.charAt(i)) == -1)
            return false;
    }

    //TrongLV bo sung check truong hop khong phai kieu so
    if (val.indexOf(".") != val.toString().lastIndexOf(".")){
        return false;
    }
    if(val.toString().lastIndexOf(".") == val.length - 1){
        return false;
    }
    if (val * 1 <= 0){
        return false;
    }


    return true;
}

/*
 * author   : TamDT1
 * date     : 03/11/2010
 * desc     : kiem tra 1 chuoi co p kieu double khong
 * 
 */
function isDouble(val) {
    var validChars = '0123456789.-';

    //kiem tra chi duoc phep chua 1 trong cac ky tu tren
    for(var i = 0; i < val.length; i++) {
        if(validChars.indexOf(val.charAt(i)) == -1)
            return false;
    }

    //kiem tra doi voi truong hop co nhieu hon 1 dau cham
    if (val.indexOf(".") != val.toString().lastIndexOf(".")){
        return false;
    }

    //kiem ra doi voi truogn hop co nhieu hon 1 dau -
    if(val.indexOf("-") >= 0 && val.indexOf("-") != 0) {
        return false;
    }

    return true;
}

function ajaxAction() {
    var areaId, action, param, afterCallback, errorCallback, retType, showLoading;
    var _url;

    switch( arguments.length ) {
        case 2:
            areaId = arguments[0];
            action = arguments[1];
            param = [];
            afterCallback = null;
            errorCallback = null;
            retType = "text/html";
            showLoading = true;
            break;
        case 3:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = null;
            errorCallback = null;
            retType = "text/html";
            showLoading = true;
            break;
        case 4:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = null;
            retType = "text/html";
            showLoading = true;
            break;
        case 5:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = arguments[4];
            retType = "text/html";
            showLoading = true;
            break;
        case 6:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = arguments[4];
            retType = arguments[5];
            showLoading = true;
            break;
        case 7:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = arguments[4];
            retType = arguments[5];
            showLoading = arguments[6];
            break;
    }

    _url = action;
    if( param.length > 0 ) {
        _url += "?" + param.join( "&" );
    }

    if( showLoading ) {
        initProgress();
    }

    dojo.io.bind({
        useCache:false,
        preventCache:true,
        url:_url,
        error: function( type, data, evt ) {
            if( showLoading ) {
                resetProgress();
            }
            
            // [ Display error info
            var str = "<div style='color:red;font-weight:bold;'>Error in Ajax: </div>";
            for( var prop in data ) {
                str += prop + " = " + data[prop] + "<br/>";
            }
            alert(str );
            // ]
            if( errorCallback != null || errorCallback != undefined ) {
                errorCallback( areaId, data );
            }
        },
        handler: function( type, data, e ) {
            if( showLoading ) {
                resetProgress();
            }

            if( areaId != null || areaId != undefined ) {
                byId( areaId ).innerHTML = data;

                var xmlParser = new dojo.xml.Parse();
                var node = dojo.byId( areaId );
                var frag  = xmlParser.parseElement( node, null, true );
                dojo.widget.getParser().createSubComponents( frag, dojo.widget.byId( areaId ) );

                try {
                    var strut = new struts.widget.Bind();
                    strut._executeScripts( strut.parse( data ).scripts );
                } catch( e ) {
                    alert( "Custom error: " + e.message );
                }
            }

            

            if( afterCallback != null || afterCallback != undefined ) {
                afterCallback( areaId, data );
            }
        },
        mimetype: retType
    });

    // [ inner functions
    function byId( id ) {
        return document.getElementById( id );
    }
// ] inner functions
}

function ajaxPostAction() {
    var areaId, action, param, afterCallback, errorCallback, retType, showLoading;
    var _url;

    switch( arguments.length ) {
        case 2:
            areaId = arguments[0];
            action = arguments[1];
            param = [];
            afterCallback = null;
            errorCallback = null;
            retType = "text/html";
            showLoading = true;
            break;
        case 3:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = null;
            errorCallback = null;
            retType = "text/html";
            showLoading = true;
            break;
        case 4:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = null;
            retType = "text/html";
            showLoading = true;
            break;
        case 5:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = arguments[4];
            retType = "text/html";
            showLoading = true;
            break;
        case 6:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = arguments[4];
            retType = arguments[5];
            showLoading = true;
            break;
        case 7:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = arguments[4];
            retType = arguments[5];
            showLoading = arguments[6];
            break;
    }

    _url = action;
    var cntObj = {};
    var i, tmpArr, code = "";
    if( param.length > 0 ) {
        for( i = 0; i < param.length; i++ ) {
            tmpArr = param[i].split( "=" );
            code += "cntObj[\"" + tmpArr[0] + "\"] = \"" + tmpArr[1] + "\";";
        }

        if( code.length > 0 ) {
            eval( code );
        }
    }

    if( showLoading ) {
        initProgress();
    }

    dojo.io.bind({
        useCache:false,
        preventCache:true,
        url:_url,
        method: "post",
        content: cntObj,
        error: function( type, data, evt ) {
            if( showLoading ) {
                resetProgress();
            }

            // [ Display error info
            var str = "<div style='color:red;font-weight:bold;'>Error in Ajax @ LongH: </div>";
            for( var prop in data ) {
                str += prop + " = " + data[prop] + "<br/>";
            }
            alert( str );
            // ]
            if( errorCallback != null || errorCallback != undefined ) {
                errorCallback( areaId, data );
            }
        },
        handler: function( type, data, e ) {
            if( showLoading ) {
                resetProgress();
            }

            if( areaId != null || areaId != undefined ) {
                byId( areaId ).innerHTML = data;

                var xmlParser = new dojo.xml.Parse();
                var node = dojo.byId( areaId );
                var frag  = xmlParser.parseElement( node, null, true );
                dojo.widget.getParser().createSubComponents( frag, dojo.widget.byId( areaId ) );

                try {
                    var strut = new struts.widget.Bind();
                    strut._executeScripts( strut.parse( data ).scripts );
                } catch( e ) {
                    alert("Custom error: " + e.message );
                }
            }



            if( afterCallback != null || afterCallback != undefined ) {
                afterCallback( areaId, data );
            }
        },
        mimetype: retType
    });

    // [ inner functions
    function byId( id ) {
        return document.getElementById( id );
    }
// ] inner functions
}