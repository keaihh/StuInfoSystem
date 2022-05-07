package com.fc.controller;

import com.fc.service.AdminService;
import com.fc.service.ClassService;
import com.fc.service.StudentService;
import com.fc.service.TeacherService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fc.bean.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ClassService classService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @PostMapping(value = "/adm/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, Map<String,Object> map, HttpSession session)
    {
        Admin adm=adminService.adminLogin(username,password);
        if(adm!=null)
        {
            List<Classes> classes=classService.getAllClass();
            session.setAttribute("loginUser",username);
            return "redirect:/admmain.html";
        }
        else
        {
            map.put("msg","用户名或密码错误");
            return  "login";
        }
    }

    //返回首页
    @GetMapping(value = "/adm/toindex")
    public String toindex(){
        return "redirect:/admmain.html";
    }

    //返回学生管理首页
    @GetMapping(value = "/adm/tostudmin/{pn}")
    public String tostudmin(@PathVariable("pn") Integer pn,Model model)
    {
        PageHelper.startPage(pn, 6);
        List<Student> students=studentService.getAllStudent();
        List<Classes> classes = classService.getAllClass();
        PageInfo<Student> page = new PageInfo<Student>(students, 5);
        model.addAttribute("classes",classes);
        model.addAttribute("pageInfo",page);
        return "forward:/stuadmin.html";
    }
}
