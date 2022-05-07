package com.fc.service;

import com.fc.bean.Resultss;

import java.util.List;

public interface ResultssService {

    List<Resultss> selectByStuId(String stuId);

}
