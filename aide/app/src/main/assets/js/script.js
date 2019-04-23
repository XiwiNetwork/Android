
// img 小图片 被点击事件
$('#img').click(function(){
	// 获取img标签 src属性
	var src = $('#img').attr("src");
	// 赋值 div下的img标签属性
	$('#div').find('img').attr("src", src);
	// 显示 遮罩层div
	$('#div').show();
});

// 遮罩层被点击事件
$('#div').click(function(){
	// 隐藏
	$('#div').hide();
});














//alert("ok");



//$(".main-body").css("display", "none");

/*
$(".main-head").Click(function(){
	
	alert("head onClick0");
	
	var style = $('.main-body').style.display;
	
	alert("style: " + style);
	
	if (style == "none") {
		$('.main-body').style.display = "inline";
	} else {
		$('.main-body').style.display = "none";
	}
	
	

});

*/


