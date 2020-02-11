package com.winterchen.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

/**
 * Created by zy on 2020/2/10.
 */
@Controller
//@RequestMapping("/security")
public class HelloController {

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }
    @GetMapping("login")
    public String login(@RequestParam(required = false) String error,
                        @RequestParam(required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "error");
        }
        if (logout != null) {
            model.addAttribute("logout", "logout");
        }
        return "login";
    }
    @GetMapping("/common/hello")
    @ResponseBody
    public String common() {
        return "common";
    }
    @GetMapping("/user/hello")
    @ResponseBody
    public String user() {
        return "user";
    }
    @GetMapping("/admin/hello")
    @ResponseBody
    public String admin() {
        return "admin";
    }

    @GetMapping("logout")
    public String logout() {
        return "logout";
    }
}
