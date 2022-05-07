package com.fc.controller;

import com.fc.bean.*;
import com.fc.service.ClassService;
import com.fc.service.StudentService;
import com.fc.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping(value = "/tea/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, Map<String,Object> map, HttpSession session)
    {
        Teacher tea=teacherService.login(username,password);
        if(tea!=null)
        {
            session.setAttribute("loginUser",username);
            return "redirect:/teamain.html";
        }
        else
        {
            map.put("msg","用户名或密码错误");
            return  "login";
        }
    }
}
