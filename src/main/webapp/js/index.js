/*
*页面初始化点击"上传"
* */
window.onload = function () {
    var pathname = window.location.pathname;
    //设置action
    if(pathname == "/index" || pathname == "/hisory")
        $("#form").attr("action",pathname + "/upload");
    else
        $("#form").attr("action",pathname);
    //如果有结果返回,则设置图片src
    var src = $("#img_src").val();
    $("#img").attr("src",src);
};

$("#upload_btn").on("click",function () {
    // alert($("#img_src").val())
    $("#form").submit();
});

//选择图片文件后替换原有图片
$("#select_image").change(function () {
    var fileUrl = getFileUrl("select_image");
    var fileName = document.getElementById("select_image").files.item(0).name;
    var $msg = $("#msg");
    var msg_display = $msg[0].style.display;
    // alert(fileUrl)
    $("#img").attr("src",fileUrl);
    //保存图片url
    $("#img_src").val(fileUrl);
    if(msg_display.toString() !== "visible")
        $msg.hide();
    //显示图片名称
    $("#img_name").text(fileName);
});
//点击选择图片按钮，弹出图片选择框
$("#select_btn").on('click', function () {
    var $select = $("#select_image");
    $select.click();
});

function getFileUrl(sourceId) {
    var url;
    if (navigator.userAgent.indexOf("MSIE")>=1) { // IE
        url = document.getElementById(sourceId).value;
    } else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox
        url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
    } else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome
        url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
    }
    return url;
}
