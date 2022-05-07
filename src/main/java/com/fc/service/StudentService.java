package com.fc.service;

import com.fc.bean.Student;

import java.util.List;

public interface StudentService {
     Student login(String stuId,String stuPass);

     List<Student> getAllStudent();

     Student selectById(String stuId);

    void deleStu(String stuId);

    void addStudentHavePass(Student student);
}
