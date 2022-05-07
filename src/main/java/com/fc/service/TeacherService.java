package com.fc.service;

import com.fc.bean.Teacher;

import java.util.List;


public interface TeacherService {

    Teacher selectById(String teaId);

    int deleTea(String teaId);
}
