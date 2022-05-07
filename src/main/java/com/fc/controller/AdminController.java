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

    //更新学生信息操作从根据班级查找页面发送来的
    @PutMapping(value = "/adm/stubyclass")
    public String updateStubyclass(@Valid Student student,BindingResult bindingResult,Model model,@Param("ininclass") String ininclass)
    {

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        List<Classes> classes = classService.getAllClass();
        if(allErrors.size()==0)
        {
            System.out.println(student);
            studentService.deleStu(student.getStuId());
            studentService.addStudentHavePass(student);
            try {
                return "redirect:/adm/selectByClass/1?className="+URLEncoder.encode(ininclass,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "redirect:/adm/toclassdmin/1";

        }
        else
        {
            for (ObjectError error:allErrors)
            {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors",errmsg);
            model.addAttribute("stu",student);
            model.addAttribute("classes",classes);
            return "adm/updatestubyclass";
        }
    }

    //返回学生添加页面
    @GetMapping(value = "/adm/stuadd")
    public String stutoaddpage(Model model)
    {
        List<Classes> classes = classService.getAllClass();
        model.addAttribute("classes",classes);
        return "adm/addstu";
    }

    //处理学生添加事务
    @PostMapping(value = "/adm/stuAdd")
    public String stuAdd(@Valid Student student, BindingResult bindingResult,Model model)
    {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        List<Classes> classes = classService.getAllClass();
        if(allErrors.size()==0)
        {
            Student studentVail=studentService.selectById(student.getStuId());
            if(studentVail==null)
            {
                studentService.addStudent(student);
                return "redirect:/adm/tostudmin/1";
            }
            else{
                errmsg.add(new MyError("已存在该学号的学生"));
                model.addAttribute("errors",errmsg);
                model.addAttribute("stu",student);
                model.addAttribute("classes",classes);
                return "adm/addstu";
            }
        }
        else {
            for (ObjectError error:allErrors)
            {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors",errmsg);
            model.addAttribute("stu",student);
            model.addAttribute("classes",classes);
            return "adm/addstu";
        }
    }

    //处理删除学生事务
    @DeleteMapping(value = "/adm/stu/{stuId}")
    public String delestu(@PathVariable("stuId") String stuId)
    {
        studentService.deleStu(stuId);
        return "redirect:/adm/tostudmin/1";
    }

    //返回学生修改页面
    @GetMapping(value = "/adm/stu/{stuId}")
    public String updateStuPage(@PathVariable("stuId") String stuId,Model model)
    {
        Student stu=studentService.selectById(stuId);
        List<Classes> classes=classService.getAllClass();
        model.addAttribute("stu",stu);
        model.addAttribute("classes",classes);
        return "adm/updatestu";
    }

    //更新学生信息操作
    @PutMapping(value = "/adm/stu")
    public String updateStu(@Valid Student student,BindingResult bindingResult,Model model)
    {


        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        List<Classes> classes = classService.getAllClass();
        if(allErrors.size()==0)
        {
            System.out.println(student);
            studentService.deleStu(student.getStuId());
            studentService.addStudentHavePass(student);
            return "redirect:/adm/tostudmin/1";
        }
        else
        {
            for (ObjectError error:allErrors)
            {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors",errmsg);
            model.addAttribute("stu",student);
            model.addAttribute("classes",classes);
            return "adm/updatestu";
        }
    }

    //根据ID查询学生
    @GetMapping(value = "/adm/selectById")
    public String selectById(@Param("stuId") String stuId, Model model)
    {
        Student student=studentService.selectById(stuId);
        List<Classes> classes=classService.getAllClass();
        model.addAttribute("classes",classes);
        model.addAttribute("stus",student);
        return "adm/stubyid";
    }

    //根据班级查询学生
    @GetMapping(value = "/adm/selectByClass/{pn}")
    public String selectStuByClass(@PathVariable("pn") Integer pn,@Param("className") String className,Model model)
    {
        PageHelper.startPage(pn, 6);
        List<Student> stus=studentService.seleStuByClassName(className);
        List<Classes> classes=classService.getAllClass();
        PageInfo<Student> page = new PageInfo<Student>(stus, 5);
        model.addAttribute("pageInfo",page);
        model.addAttribute("classes",classes);
        model.addAttribute("className",className);
        return "adm/stubyclass";
    }
}
