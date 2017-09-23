var video = document.getElementById('video');
// Get access to the camera!
if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
    // Not adding `{ audio: true }` since we only want video now
    navigator.mediaDevices.getUserMedia({video: true}).then(function (stream) {
        video.src = window.URL.createObjectURL(stream);
        video.play();
    });
}

// Elements for taking the snapshot
var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');
var video = document.getElementById('video');
var image;

// Trigger photo take
document.getElementById("snap").addEventListener("click", function () {
    context.drawImage(video, 0, 0, 640, 480);
    convertCanvasToImage(canvas);
    // alert(image.src)
});
document.getElementById("save").addEventListener("click", function () {
    $("#file_src").val(image.src);
    $("#form").submit();
});

$("#file").change(function () {
    alert($(this).val())
})

function convertCanvasToImage(canvas) {
    image = new Image();
    image.src = canvas.toDataURL("image/jpeg");
    return image;
}