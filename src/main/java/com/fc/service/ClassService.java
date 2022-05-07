package com.fc.service;

import com.fc.bean.Classes;

import java.util.List;


public interface ClassService {

    List<Classes> getAllClass();

    Classes selectById(String classId);

    Classes selectByName(String className);


    int addClass(Classes classes);


}
