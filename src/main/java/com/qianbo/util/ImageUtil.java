package com.qianbo.util;

public class ImageUtil {

    private String img_src;
    private String img_name_suffix;
    private String img_name_no_suffix ;//无后缀
    private Boolean handle_success = false;//是否处理完成
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

    public Boolean getHandle_success() {
        return handle_success;
    }

    public void setHandle_success(Boolean handle_success) {
        this.handle_success = handle_success;
    }
}
