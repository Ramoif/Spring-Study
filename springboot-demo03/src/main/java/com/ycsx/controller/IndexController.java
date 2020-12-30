package com.ycsx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*在templates目录下的所有页面只能用Controller来跳转
* 还有模板引擎的支持*/
@Controller
public class IndexController {

    @RequestMapping("/test")
    public String test(Model model){
        model.addAttribute("msg","msg");
        return "test";
    }
}
