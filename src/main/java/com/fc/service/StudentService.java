package com.fc.service;

import com.fc.bean.Student;

import java.util.List;

public interface StudentService {
     Student login(String stuId,String stuPass);

     Student selectById(String stuId);
}
