package com.fc.service;

import com.fc.bean.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher login(String teaId, String teaPass);

    Teacher selectById(String teaId);

}
