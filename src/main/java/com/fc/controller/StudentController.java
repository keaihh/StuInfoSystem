package com.fc.controller;

import com.fc.bean.*;
import com.fc.service.ClassService;
import com.fc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/stu/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, Map<String, Object> map, HttpSession session) {
        Student stu = studentService.login(username, password);
        if (stu != null) {
            session.setAttribute("loginUser", username);
            return "redirect:/stumain.html";
        } else {
            map.put("msg", "用户名或密码错误");
            return "login";
        }
    }
}