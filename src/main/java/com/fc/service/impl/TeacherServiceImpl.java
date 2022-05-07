package com.fc.service.impl;

import com.fc.bean.Teacher;
import com.fc.mapper.TeacherMapper;
import com.fc.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TeacherServiceImpl  implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher selectById(String teaId) {
        return teacherMapper.selectTeacherByStuId(teaId);
    }

    @Override
    public int deleTea(String teaId) {
        return teacherMapper.deleteTeaById(teaId);
    }

}
