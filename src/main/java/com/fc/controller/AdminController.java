package com.fc.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fc.bean.*;
import com.fc.service.ClassService;
import com.fc.service.TeacherService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@Controller
public class AdminController {


    @Autowired
    private ClassService classService;


    @Autowired
    private TeacherService teacherService;



    //根据ID查询教师
    @GetMapping(value = "/adm/selectTeaById")
    public String selectTeaById(@Param("teaId") String teaId, Model model)
    {
        Teacher teacher=teacherService.selectById(teaId);
        model.addAttribute("tea",teacher);
        return "adm/teabyid";
    }

    //处理删除教师事务
    @DeleteMapping(value = "/adm/tea/{teaId}")
    public String deletea(@PathVariable("teaId") String teaId)
    {
        teacherService.deleTea(teaId);
        return "redirect:/adm/toteadmin/1";
    }

    //返回班级管理首页
    @GetMapping(value = "/adm/toclassdmin/{pn}")
    public String toclassdmin(@PathVariable("pn") Integer pn,Model model)
    {
        PageHelper.startPage(pn, 6);
        List<Classes> classes=classService.getAllClass();
        PageInfo<Classes> page = new PageInfo<Classes>(classes, 5);
        model.addAttribute("pageInfo",page);
        return "adm/classlist";
    }

    //返回班级添加页面
    @GetMapping(value = "/adm/classadd")
    public String classToAddPage()
    {
        return "adm/addclass";
    }

    //处理班级添加事务
    @PostMapping(value = "/adm/classAdd")
    public String classAdd(@Valid Classes classes, BindingResult bindingResult,Model model)
    {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        if(allErrors.size()==0)
        {
            if(classService.selectByName(classes.getClassName())==null) {
                Classes classVail = classService.selectById(classes.getClassId());
                if (classVail == null) {
                    classService.addClass(classes);
                    return "redirect:/adm/toclassdmin/1";
                } else {
                    errmsg.add(new MyError("已存在该班级号的班级"));
                    model.addAttribute("errors", errmsg);
                    model.addAttribute("class", classes);
                    return "adm/addclass";
                }
            }
            else
            {
                errmsg.add(new MyError("已存在该班级名字的班级"));
                model.addAttribute("errors", errmsg);
                model.addAttribute("class", classes);
                return "adm/addclass";
            }
        }
        else {
            for (ObjectError error:allErrors)
            {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors",errmsg);
            model.addAttribute("class",classes);
            return "adm/addclass";
        }
    }

}
