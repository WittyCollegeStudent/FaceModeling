package com.qianbo.facemodeling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IndexController {
    @RequestMapping(path={"/index"})
    public String index() {
        return "index";
    }
    @RequestMapping(path={"history"})
    public ModelAndView history() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","this is name");
        mav.setViewName("history");
        return mav;
    }
}