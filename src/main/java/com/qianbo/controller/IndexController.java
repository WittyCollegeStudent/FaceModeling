package com.qianbo.controller;

import com.qianbo.service.ConvertService;
import com.qianbo.util.Constants;
import com.qianbo.util.ImageUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.IOException;


@Controller
public class IndexController {

    private ImageUtil imageUtil = null;

    ConvertService convertService = new ConvertService();

    /*
    * 返回主页面
    * */
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
    * 打开摄像头
    * */
    @RequestMapping(path = {"/openCamera"})
    public String openCamera() {
        return "camera";
    }

    /*
    * 摄像头保存照片
    * @param file_src 图片的src
    * */
    @RequestMapping(path = {"/saveSnap"})
    public ModelAndView saveSnap(String file_src) throws IOException {
        ModelAndView mav = new ModelAndView();
        String path = Constants.IMAGE_SAVE_PATH;
        String fileName = Long.toString(System.currentTimeMillis()) + ".jpg";
        System.out.println("time = " + fileName);
        System.out.println("file_src = " + file_src);
        ImageUtil.generateImage((StringUtils.split(file_src,","))[1],path,fileName);
        //复制图片
        Runtime.getRuntime().exec("xcopy E:\\computer-vision\\human-face\\face-reconstruction\\sources\\"
                + fileName
                + " E:\\computer-vision\\human-face\\FaceModeling\\src\\main\\webapp\\images\\upload\\"
                + " /E /Y /Q");
        System.out.println("src = " + (StringUtils.split(file_src,","))[1]);
        mav.addObject("snap_src_isreceived",true);
        mav.addObject("snap_src",file_src);
        mav.addObject("snap_name_received",fileName);
        mav.setViewName("index");
        return mav;
    }

    /*
    * @param file 传过来的文件，需要转换
    * */
    @RequestMapping(value = "/index/upload")
    public ModelAndView upload( MultipartFile file,Boolean isSnap,String snapName) throws IOException {
        ModelAndView mav = new ModelAndView();
        final String originalFilename;
        //如果是拍摄的照片，因为生成过，就不需要再次生成
        if(isSnap == null || !isSnap){
            //获得文件名
            originalFilename = Long.toString(System.currentTimeMillis()) + ".jpg";
//            originalFilename = file.getOriginalFilename();
            //保存原图
            String path = Constants.IMAGE_SAVE_PATH;
            File targetFile = new File(path +"\\"+ originalFilename);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            //保存
            file.transferTo(targetFile);
        }else {
            originalFilename = snapName;
        }
        //执行脚本
        new Thread(new Runnable() {
            public void run() {
                convertService.convert(imageUtil,Constants.IMAGE_SAVE_PATH,originalFilename);
            }
        }).start();
        imageUtil = new ImageUtil();
        imageUtil.setImg_name_suffix(originalFilename);
        imageUtil.setImg_name_no_suffix(StringUtils.split(originalFilename,".")[0]);
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
        if (imageUtil.getVisit_time() >= ImageUtil.MAX_VISIT) {
            return Constants.STATE_HANDLE_FAILURE;
        }
        System.out.println(imageUtil.getVisit_time());
        return imageUtil.getHandle_success().toString();
    }

    /*
    * 处理成功，返回obj路径和png路径，交给前端处理
    * */
    @RequestMapping(value = "/index/handleSuccess")
    public String handleSuccess() {
        return "redirect:/index/showModel";
    }

    /*
    * 返回模型处理结果
    * */
    @RequestMapping(value = "/index/showModel")
    public ModelAndView showModel() {
        ModelAndView mav = new ModelAndView();
        String pngpath = Constants.IMAGE_DOWNLOAD_PATH + "\\" + imageUtil.getImg_name_no_suffix() + "\\" + imageUtil.getImg_name_no_suffix() + "1.png";
        String objpath = Constants.IMAGE_DOWNLOAD_PATH + "\\" + imageUtil.getImg_name_no_suffix() + "\\" + imageUtil.getImg_name_no_suffix() + ".obj";
        mav.addObject("pngpath", pngpath);
        mav.addObject("objpath", objpath);
        mav.addObject(Constants.STATE, Constants.STATE_IMAGE_PROCESSED);
        mav.addObject("handled", imageUtil.getHandle_success());
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping(value = "/index/download")
    public ResponseEntity<byte[]> download()
            throws Exception {
        String filename_with_no_suffix = imageUtil.getImg_name_no_suffix();
        String downloadFileName = "fbx&obj&png.zip";
        //下载文件路径
        String path = Constants.IMAGE_DOWNLOAD_ABSO_PATH + File.separator + filename_with_no_suffix + File.separator + downloadFileName ;
        File file = new File(path);
        HttpHeaders headers = new HttpHeaders();
        //下载显示的文件名，解决中文名称乱码问题
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFileName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        System.out.println("downloadfilename = "+ downloadFileName);
        System.out.println("path = "+ path);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }

}