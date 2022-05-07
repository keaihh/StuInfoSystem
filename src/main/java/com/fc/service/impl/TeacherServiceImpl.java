package com.fc.service.impl;

import com.fc.bean.Teacher;
import com.fc.mapper.TeacherMapper;
import com.fc.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl  implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher login(String teaId, String teaPass) {
        return teacherMapper.selectTeacherByIdAndPass(teaId,teaPass);
    }

    @Override
    public List<Teacher> getAllTeacher() {
        return teacherMapper.selectAllTeacher();
    }


    @Override
    public Teacher selectById(String teaId) {
        return teacherMapper.selectTeacherByStuId(teaId);
    }

    @Override
    public void addTeacher(Teacher teacher) {

    }

    @Override
    public void deleTea(String teaId) {

    }

    @Override
    public void addTeacherHavePass(Teacher teacher) {

    }
}
