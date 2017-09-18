package com.qianbo.util;

public class ImageUtil {

    private String img_src;
    private String img_name;
    private Boolean handle_success;//是否处理完成
    private Integer visit_time = 0;//访问次数
    public static final Integer MAX_VISIT = 3;

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

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public Boolean getHandle_success() {
        return handle_success;
    }

    public void setHandle_success(Boolean handle_success) {
        this.handle_success = handle_success;
    }
}
