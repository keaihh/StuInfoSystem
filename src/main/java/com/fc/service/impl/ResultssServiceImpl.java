package com.fc.service.impl;
import com.fc.bean.Resultss;
import com.fc.mapper.ResultMapper;
import com.fc.service.ResultssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ResultssServiceImpl  implements ResultssService {

    @Autowired
    private ResultMapper resultMapper;
    @Override
    public List<Resultss> selectByStuId(String stuId) {
        return resultMapper.selectResultByStuId(stuId);
    }
}
