package com.chuchu.blog.controller.admin;

import com.chuchu.blog.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-11-15
 **/
@RequestMapping("/admin")
@Controller
public class SettingController {
    /*@Autowired
    private SettingService settingService;*/

    @RequestMapping("setting")
    public String settingPage(){
        return "admin/setting";
    }

}
