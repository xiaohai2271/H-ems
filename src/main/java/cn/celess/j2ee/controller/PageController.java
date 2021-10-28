package cn.celess.j2ee.controller;

import cn.celess.j2ee.entity.ResponseData;
import cn.celess.j2ee.util.UserContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 2021/10/28
 * TODO:
 *
 * @author 禾几海
 */
@Controller
public class PageController {
    @RequestMapping({"/index", ""})
    public String index() {
        if (UserContext.get() == null){
            return "login";
        }
        return "index";
    }


    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @RequestMapping("/reg")
    public String reg() {
        return "reg";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession(true).removeAttribute("user");
        UserContext.clear();
        return "redirect:login";
    }
}
