package com.fc.service.impl;

import com.fc.bean.Admin;
import com.fc.mapper.AdminMapper;
import com.fc.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin adminLogin(String AdminId, String AdminPass)
    {
        return adminMapper.selectAdminByIdAndPass(AdminId,AdminPass);
    }
}
