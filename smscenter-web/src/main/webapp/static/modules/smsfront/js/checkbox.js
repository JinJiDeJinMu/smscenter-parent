/**
 * 检测是否有被选中
 * 
 * @param {Object}
 *            objNam CheckBox的id属性
 */
function chkCheckBoxChs(objNam) { // 檢測是否有選擇多选框的至少一项
	var obj = document.getElementsByName(objNam); // 獲取多選框數組
	var objLen = obj.length; // 獲取數據長度
	var objYN; // 是否有選擇
	var i;
	objYN = false;
	for (i = 0; i < objLen; i++) {
		if (obj[i].checked == true) {
			objYN = true;
			break;
		}
	}
	return objYN;
}
/**
 * 全选
 * 
 * @param {Object}
 *            a
 */

$(function() {
    $("#checkAll").click(function() {
       if(this.checked){ 
           $("input[name='ids']").each(function(){this.checked=true;}); 
       }else{ 
           $("input[name='ids']").each(function(){this.checked=false;}); 
       } 
  });
});