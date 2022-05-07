package com.fc.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fc.bean.*;
import com.fc.service.ClassService;
import com.fc.service.ResultssService;
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
import java.util.*;

@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ResultssService resultssService;

    @Autowired
    private ClassService classService;

    @Autowired
    private StudentService studentService;


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

    //返回首页
    @GetMapping(value = "/tea/toindex")
    public String teaToIndex(){
        return "redirect:/teamain.html";
    }


    //返回教师信息修改页面
    @GetMapping(value = "/tea/toUpdateMsgPage")
    public String teaToUpdateMsgPage(HttpSession httpSession, Model model)
    {

        Teacher tea= teacherService.selectById((String) httpSession.getAttribute("loginUser"));
        model.addAttribute("tea",tea);
        return "tea/updatetea";
    }

    //更新教师信息操作
    @PutMapping(value = "/tea/updateTeaMsg")
    public String updateTeaMsg(@Valid Teacher teacher, BindingResult bindingResult, Model model, HttpSession httpSession)
    {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        if(allErrors.size()==0)
        {
            Teacher teacherInit=teacherService.selectById((String) httpSession.getAttribute("loginUser"));
            teacher.setTeaId(teacherInit.getTeaId());
            teacher.setTeaName(teacherInit.getTeaName());
            teacher.setTeaSex(teacherInit.getTeaSex());

            teacherService.deleTea(teacherInit.getTeaId());
            teacherService.addTeacherHavePass(teacher);
            return "redirect:/updateTeaSucc.html";
        }
        else
        {
            for (ObjectError error:allErrors)
            {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors",errmsg);
            model.addAttribute("tea",teacher);
            return "tea/updatetea";
        }
    }

    //根据ID查询学生的成绩
    @GetMapping(value = "/tea/selectById/{pn}")
    public String selectResultByStuId(@PathVariable("pn") Integer pn,@Param("stuId") String stuId, Model model)
    {
        PageHelper.startPage(pn, 6);
        List<Resultss> resultsses=resultssService.selectByStuId(stuId);
        PageInfo<Resultss> page = new PageInfo<Resultss>(resultsses, 5);
        List<Classes> classes=classService.getAllClass();
        model.addAttribute("classes",classes);
        model.addAttribute("pageInfo",page);
        model.addAttribute("stuId",stuId);
        return "tea/tearesultlistbystuid";
    }

}
