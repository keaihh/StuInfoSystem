package com.fc.service;

import com.fc.bean.Rank;
import com.fc.bean.Resultss;

import java.util.List;

public interface ResultssService {

    List<Resultss> getAllResult();

    List<Resultss> selectByStuId(String stuId);

    List<Resultss> selectByStuIdAndResTerm(String loginUser, String resTerm);

    Resultss selectResultByResId(int resId);

    void addResult(Resultss resultss);

    Resultss selectResultByStuIdAndSubName(String stuId, String subName);
}
