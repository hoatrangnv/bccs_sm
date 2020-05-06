/*
 *
 * tamdt1
 * date 06/03/2009
 * file nay chua tat ca cac ham can thiet cho viec chuyen du lieu giua 2 listbox = js
 *
*/


var selectedList;
var availableList;

//khoi tao cac list, tham so truyen vao: id cua availableList va id cua selectedList
function createListObjects(availableListId, selectedListId){
    availableList = document.getElementById(availableListId);
    selectedList = document.getElementById(selectedListId);
}

//chuyen cac item duoc chon tu selecedList sang avaiableList
function delAttribute(){
    var arrSelectedItems = [];
    for(var indexList = 0; indexList < selectedList.options.length; indexList++){
        if(selectedList.options[indexList].selected){
            arrSelectedItems.push(selectedList.options[indexList]);
        }
    }
    for(var indexArr = 0; indexArr < arrSelectedItems.length; indexArr++){
        availableList.appendChild(arrSelectedItems[indexArr]);
    }
    
    selectNone(selectedList,availableList);
    setSize(availableList,selectedList);
}

//chuyen cac item duoc chon tu avaiableList sang selecedList
function addAttribute(){
    var arrSelectedItems = [];
    for(var indexList = 0; indexList < availableList.options.length; indexList++){
        if(availableList.options[indexList].selected){
            arrSelectedItems.push(availableList.options[indexList]);
        }
    }
    for(var indexArr = 0; indexArr < arrSelectedItems.length; indexArr++){
        selectedList.appendChild(arrSelectedItems[indexArr]);
    }

    selectNone(selectedList,availableList);
    setSize(availableList,selectedList);
}

//chuyen tat ca cac item tu selectedList sang availableList
function addAll(){
    var len = availableList.length -1;
    for(i=len; i>=0; i--){
        selectedList.appendChild(availableList.item(i));
    }
    
    selectNone(selectedList,availableList);
    setSize(selectedList,availableList);

}

//chuyen tat ca cac item tu availableList sang selectedList
function delAll(){
    var len = selectedList.length -1;
    for(i=len; i>=0; i--){
        availableList.appendChild(selectedList.item(i));
    }

    selectNone(selectedList,availableList);
    setSize(selectedList,availableList);

}

function setSize(list1,list2){
    list1.size = getSize(list1);
    list2.size = getSize(list2);
}

function selectNone(list1,list2){
    list1.selectedIndex = -1;
    list2.selectedIndex = -1;
    addIndex = -1;
    selIndex = -1;
}
function getSize(list){
    /* Mozilla ignores whitespace,
       IE doesn't - count the elements
       in the list */
    var len = list.childNodes.length;
    var nsLen = 0;
    //nodeType returns 1 for elements
    for(i=0; i<len; i++){
        if(list.childNodes.item(i).nodeType==1)
            nsLen++;
    }
    if(nsLen<2)
        return 2;
    else
        return nsLen;
}
function tamdt1(){
    //ham test
    alert('tamdt1@viettel.com.vn');
}


