/*
*页面初始化点击"上传"
* */

//如果图片处理未完成，则设置定时器，查询处理状态
var handled = $("#handled").val();
if(handled == "false"){
    var t1 = window.setInterval(getHandleState,1000);
}
// alert($("#handled").val())
window.onload = function () {
    //如果有结果返回,则设置图片src
    var src = $("#img_src_received").val();
    $("#img").attr("src", src);
    //设置图片名称
    setImgNameReceived();
    //如果有消息返回，则设置   返回消息
    var msg = $("#msg_received").val();
    if (msg != null && msg != "")
        $("#msg").text(msg);
    //如果处理成功，则下载按钮可见，并显示fbx
    if($("#handled").val() == "true"){
        $("#download_btn").show();
    }
    else
        $("#download_btn").hide();
    //显示图片
    resetImageState();
};

//通过返回的名称设置图片名
function setImgNameReceived() {
    var img_name_received = $("#img_name_received").val();
    if(img_name_received != null && img_name_received != "")
        $("#img_name").text(img_name_received);
}

function resetImageState() {
    var $img = $("#img");
    var src = $img.attr("src");
    // if(src == null || src == "")
    //     $img.hide();
    // else
    //     $img.show();
    $img.hide();
}

//点击"下载"按钮
// $("#download_btn").on("click",function () {
//    if($("#"))
// });

$("#upload_btn").on("click", function () {
    var select_image = $("#select_image").val();
    if(select_image == null || select_image == ""){
        alert("请选择图片");
        return ;
    }
    $("#form").submit();
});

//查询处理状态
function getHandleState() {
    $.ajax({
        type: "POST",
        // contentType: 'application/json;charset=utf-8', //设置请求头信息
        url: "/index/checkImgState",//你后台请求URL地址
        // dataType: "json",
        // data: null,
        success: function(data){
            //如果处理成功，则删除定时器
            if(data == "true"){
                window.clearInterval(t1);
                window.location.href = "/index/handleSuccess";
                return ;
            }
        }
    });
}

//选择图片文件后替换原有图片
$("#select_image").change(function () {
    var fileUrl = getFileUrl("select_image");
    var fileName = document.getElementById("select_image").files.item(0).name;
    var $msg = $("#msg");
    var msg_display = $msg[0].style.display;
    $("#img").attr("src", fileUrl);
    //保存图片url
    $("#img_src").val(fileUrl);
    if (msg_display.toString() !== "visible")
        $msg.hide();
    //显示图片名称
    $("#img_name").text(fileName);
    resetImageState();
});
//点击选择图片按钮，弹出图片选择框
$("#select_btn").on('click', function () {
    var $select = $("#select_image");
    $select.click();
    $("#handled").val("");
});

function getFileUrl(sourceId) {
    var url;
    if (navigator.userAgent.indexOf("MSIE") >= 1) { // IE
        url = document.getElementById(sourceId).value;
    } else if (navigator.userAgent.indexOf("Firefox") > 0) { // Firefox
        url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
    } else if (navigator.userAgent.indexOf("Chrome") > 0) { // Chrome
        url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
    }
    return url;
}


