package com.qianbo.util;

import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageUtil {

    private String img_src;
    private String img_name_suffix;
    private String img_name_no_suffix ;//无后缀
    private String handle_success = Constants.STATE_HANDLE_PROCESSING;//是否处理完成
    private Integer visit_time = 0;//访问次数
    public static final Integer MAX_VISIT = 60;

    public Integer getVisit_time() {
        return visit_time;
    }

    public void setVisit_time(Integer visit_time) {
        this.visit_time = visit_time;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }

    public String getImg_name_suffix() {
        return img_name_suffix;
    }

    public void setImg_name_suffix(String img_name_suffix) {
        this.img_name_suffix = img_name_suffix;
    }

    public String getImg_name_no_suffix() {
        return img_name_no_suffix;
    }

    public void setImg_name_no_suffix(String img_name_no_suffix) {
        this.img_name_no_suffix = img_name_no_suffix;
    }

    public String getHandle_success() {
        return handle_success;
    }

    public void setHandle_success(String handle_success) {
        this.handle_success = handle_success;
    }

    /*
    * 从base64还原图片
    * */
    public static boolean generateImage(String imgStr , String path, String fileName)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            String imgFilePath = path + "\\" + fileName;//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
