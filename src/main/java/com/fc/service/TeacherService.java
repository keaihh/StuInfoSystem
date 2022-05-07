package com.fc.service;

import com.fc.bean.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher login(String teaId, String teaPass);

    List<Teacher> getAllTeacher();

    Teacher selectById(String teaId);

    void addTeacher(Teacher teacher);

    void deleTea(String teaId);

    void addTeacherHavePass(Teacher teacher);
}
