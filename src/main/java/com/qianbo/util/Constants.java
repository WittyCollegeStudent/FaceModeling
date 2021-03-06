package com.qianbo.util;

public class Constants {
    public static final String STATE = "msg";
    public static final String STATE_IMAGE_PROCESSING = "正在处理，请等待约半分钟...";
    public static final String STATE_IMAGE_PROCESSED = "处理完成";
    public static final String STATE_IMAGE_FAILED = "处理失败,请重新上传";
    public static final String STATE_HANDLE_SUCCESS = "true";//处理成功
    public static final String STATE_HANDLE_FAILURE = "failed";//处理失败
    public static final String STATE_HANDLE_PROCESSING = "false";//正在处理中
    //上传图片的保存路径
    public static final String IMAGE_SAVE_PATH = "E:\\computer-vision\\human-face\\face-reconstruction\\sources";
    //处理后文件的下载路径
    public static final String IMAGE_DOWNLOAD_PATH = "\\images\\download";
    public static final String IMAGE_DOWNLOAD_ABSO_PATH = "E:\\computer-vision\\human-face\\FaceModeling\\src\\main\\webapp\\images\\download";
}
