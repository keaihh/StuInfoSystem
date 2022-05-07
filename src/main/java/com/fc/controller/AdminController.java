package com.fc.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fc.bean.*;
import com.fc.service.AdminService;
import com.fc.service.ClassService;
import com.fc.service.StudentService;
import com.fc.service.TeacherService;
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

    //返回教师管理首页
    @GetMapping(value = "/adm/toteadmin/{pn}")
    public String toteadmin(@PathVariable("pn") Integer pn,Model model)
    {
        PageHelper.startPage(pn, 6);
        List<Teacher> teachers=teacherService.getAllTeacher();
        PageInfo<Teacher> page = new PageInfo<Teacher>(teachers, 5);
        model.addAttribute("pageInfo",page);
        return "adm/tealist";
    }

    //    @GetMapping(value = "/adm/selectByClass/{pn}")
    //处理删除学生事务从根据班级查找页面发送来的
    @DeleteMapping(value = "/adm/stubyclass/{stuId}")
    public String delestubyclass(@PathVariable("stuId") String stuId)
    {
        Student student = studentService.selectById(stuId);
        studentService.deleStu(stuId);
        try {
            return "redirect:/adm/selectByClass/1?className="+ URLEncoder.encode(student.getStuClass(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:/adm/toclassdmin/1";
    }

    //返回学生修改页面从根据班级查找页面发送来的
    @GetMapping(value = "/adm/stubyclass/{stuId}")
    public String updateStuPagebyclass(@PathVariable("stuId") String stuId,Model model)
    {
        Student stu=studentService.selectById(stuId);
        List<Classes> classes=classService.getAllClass();
        model.addAttribute("stu",stu);
        model.addAttribute("classes",classes);
        model.addAttribute("ininclass",stu.getStuClass());
        return "adm/updatestubyclass";
    }
}
