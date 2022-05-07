package com.fc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommnonController {

    @RequestMapping("/logout")
    public String logout(HttpSession session)
    {
        session.invalidate();
        return "redirect:/index.html";
    }

}