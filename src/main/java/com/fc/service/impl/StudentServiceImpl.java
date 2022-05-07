package com.fc.service.impl;

import com.fc.bean.Student;
import com.fc.mapper.StudentMapper;
import com.fc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Override
    public Student login(String stuId, String stuPass)
    {
        return studentMapper.selectStudentByIdAndPass(stuId,stuPass);
    }

    @Override
    public List<Student> getAllStudent() {
        return studentMapper.selectAllStudent();
    }

    @Override
    public Student selectById(String stuId) {
        return studentMapper.selectStudentByStuId(stuId);
    }

}
