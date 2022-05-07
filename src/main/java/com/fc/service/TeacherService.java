package com.fc.service;

import com.fc.bean.Teacher;

public interface TeacherService {
    Teacher login(String teaId, String teaPass);
    Teacher selectById(String teaId);

    void deleTea(String teaId);

    void addTeacherHavePass(Teacher teacher);
}
