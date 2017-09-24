var video = document.getElementById('video');
/*
* 连接摄像头
* */
if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
    // Not adding `{ audio: true }` since we only want video now
    navigator.mediaDevices.getUserMedia({video: true}).then(function (stream) {
        video.src = window.URL.createObjectURL(stream);
        video.play();
    });
}

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');
var video = document.getElementById('video');
var image;

/*
* 点击拍照
* */
document.getElementById("snap").addEventListener("click", function () {
    context.drawImage(video, 0, 0, 640, 480);
    convertCanvasToImage(canvas);
});

/*
* 点击保存
* */
document.getElementById("save").addEventListener("click", function () {
    $("#file_src").val(image.src);
    $("#form").submit();
});

$("#file").change(function () {
    alert($(this).val())
})

/*
* 将画布转换为图片
* */
function convertCanvasToImage(canvas) {
    image = new Image();
    image.src = canvas.toDataURL("image/jpeg");
    return image;
}