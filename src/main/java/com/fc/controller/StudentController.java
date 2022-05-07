package com.fc.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fc.bean.*;
import com.fc.service.ClassService;
import com.fc.service.ResultssService;
import com.fc.service.StudentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ResultssService resultssService;
    private ClassService classService;

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

    //返回首页
    @GetMapping(value = "/stu/toindex")
    public String toindex() {
        return "redirect:/stumain.html";
    }

    //返回学生成绩首页
    @GetMapping(value = "/stu/toresdmin/{pn}")
    public String toresdmin(@PathVariable("pn") Integer pn, Model model, HttpSession httpSession) {
        PageHelper.startPage(pn, 9);
        List<Resultss> resultsses = resultssService.selectByStuId((String) httpSession.getAttribute("loginUser"));
        PageInfo<Resultss> page = new PageInfo<Resultss>(resultsses, 5);
        model.addAttribute("pageInfo", page);
        return "stu/resultlist";
    }
}