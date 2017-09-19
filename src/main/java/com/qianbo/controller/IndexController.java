package com.qianbo.controller;

import com.qianbo.util.Constants;
import com.qianbo.util.ImageUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public ModelAndView upload( MultipartFile file) throws IOException {
        ModelAndView mav = new ModelAndView();
        //获得文件名
        String originalFilename = file.getOriginalFilename();
//        originalFilename = URLDecoder.decode(o, "UTF-8");
        System.out.println("filename = " + originalFilename);
        //保存原图
        String path = Constants.IMAGE_SAVE_PATH;
        File targetFile = new File(path +"\\"+ originalFilename);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //保存
        file.transferTo(targetFile);
        String img_name = "头像.jpg";
        String img_src = Constants.PREVIEW_PATH + "\\" + img_name;
        imageUtil = new ImageUtil();
        imageUtil.setHandle_success(false);
        imageUtil.setImg_name(img_name);
        imageUtil.setImg_src(img_src);
        mav.addObject(Constants.STATE, Constants.STATE_IMAGE_PROCESSING);
        mav.addObject("handled", false);
        mav.setViewName("index");
        return mav;
    }

    /*
    * 处理成功，返回图片
    * */
    @ResponseBody
    @RequestMapping(value = "/index/checkImgState")
    public String check_img_state() {
        //如果为空，则没有图片上传
        if (imageUtil == null)
            return "";
        imageUtil.setVisit_time(imageUtil.getVisit_time() + 1);
        if (imageUtil.getVisit_time() == ImageUtil.MAX_VISIT) {
            imageUtil.setHandle_success(true);
        }
        System.out.println(imageUtil.getVisit_time());
//        if(imageUtil.getVisit_time() > ImageUtil.MAX_VISIT)
//            return "failed";
        return imageUtil.getHandle_success().toString();
    }

    /*
    * 处理成功，返回图片
    * */
    @RequestMapping(value = "/index/handleSuccess")
    public ModelAndView handleSuccess() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("img_src", imageUtil.getImg_src());
        mav.addObject("img_name", imageUtil.getImg_name());
        System.out.println("img_src = " + imageUtil.getImg_src());
        mav.addObject(Constants.STATE, Constants.STATE_IMAGE_PROCESSED);
        mav.addObject("handled", true);
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping(value = "/index/download")
    public ResponseEntity<byte[]> download(@RequestParam("filename") String filename)
            throws Exception {
        // TODO: 2017/9/18 暂时用本人文件代替
        filename = "头像.jpg";
//        filename = "2234.mpa";
        //下载文件路径
//        String path = request.getSession().getServletContext().getRealPath(Constants.PREVIEW_PATH);
        String path = Constants.IMAGE_DOWNLOAD_PATH;
        File file = new File(path + File.separator + filename);
        HttpHeaders headers = new HttpHeaders();
//        //下载显示的文件名，解决中文名称乱码问题
        String downloadFileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFileName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        System.out.println("downloadfilename = "+ downloadFileName);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }

}