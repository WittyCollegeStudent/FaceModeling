package com.qianbo.facemodeling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {
    @RequestMapping(path={"/upload"})
    public String upload() {
        return new String("index/upload");
    }
}