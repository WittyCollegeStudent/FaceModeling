package com.qianbo.controller;

import com.qianbo.util.Constants;
import com.qianbo.util.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;


@Controller
public class IndexController {

    private ImageUtil imageUtil = null;

    @RequestMapping(path = {"/", ""})
    public String redirectIndex() {
        return "redirect:/index";
    }

    @RequestMapping(path = {"/index"})
    public String index() {
        return "index";
    }

    @RequestMapping(path = {"/history"})
    public ModelAndView history() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "this is name");
        mav.setViewName("history");
        return mav;
    }

    /*
    * @param file 传过来的文件，需要转换
    * */
    @RequestMapping(value = "/index/upload")
    public ModelAndView upload(MultipartFile file) throws IOException {
        ModelAndView mav = new ModelAndView();
        //获得文件名
        String originalFilename = file.getOriginalFilename();
        //保存原图
        String path = Constants.IMAGE_SAVE_PATH;
        File targetFile = new File(path, originalFilename);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //保存
        file.transferTo(targetFile);
        String img_name = "头像.jpg";
        String img_src = Constants.PREVIEW_PATH + "\\" + img_name;
        if(imageUtil == null)
            imageUtil = new ImageUtil();
        imageUtil.setHandle_success(false);
        imageUtil.setImg_name(img_name);
        imageUtil.setImg_src(img_src);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        mav.addObject(Constants.STATE, Constants.STATE_IMAGE_PROCESSING);
        mav.setViewName("index");
        return mav;
    }

    /*
    * @param handlesuccess 是否处理成功
    * @param time_handle 处理图片所耗费的时间
    * */
    @RequestMapping(value = "check_image_state")
    public ModelAndView preview() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("img_src", imageUtil.getImg_src());
        mav.addObject("img_name", imageUtil.getImg_name());
        System.out.println("img_src = " + imageUtil.getImg_src());
        if (imageUtil.getHandle_success())
            mav.addObject(Constants.STATE, Constants.STATE_IMAGE_PROCESSED);
        else{
            mav.addObject(Constants.STATE, Constants.STATE_IMAGE_PROCESSING);
            mav.addObject("handling",true);
        }
        mav.setViewName("index");
        return mav;
    }
}