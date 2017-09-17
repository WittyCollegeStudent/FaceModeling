package com.qianbo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class IndexController {

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

    @RequestMapping(path = {"/index/upload", "/history/upload"})
    @ResponseBody
    public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;             // 获得文件：
        String picName = request.getParameter("picName");
        System.out.println("picName = "+picName);
        //解析为多部件文件
        MultipartFile mfile = multipartRequest.getFile(picName);
        //仅仅允许图片格式
        List<String> fileTypes = new ArrayList<String>();
        fileTypes.add("jpg");
        fileTypes.add("jpeg");
        fileTypes.add("bmp");
        fileTypes.add("gif");
        fileTypes.add("png");
        //取得原文件名称
        String orifilename = mfile.getOriginalFilename();
        //获取扩展名
        String extensionName = orifilename.substring(orifilename.lastIndexOf(".") + 1);
        //校验扩展名
        if (fileTypes.contains(extensionName.toLowerCase())) {
            //限制图片的大小
//            if(file.getSize() <= 5 * 1024*1024){
//
//                mfile.transferTo(new File(path));
        }
        System.out.println("request = " + multipartRequest);
//        String filename = select_image.getOriginalFilename();
//        System.out.println("filename = "+filename);
//            InputStream is = select_image.getInputStream();
//        Image image = new Image(img_src);
        mav.setViewName("index");
//        mav.addObject("img",image);
        return mav;
    }
}