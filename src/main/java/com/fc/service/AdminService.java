package com.fc.service;

import com.fc.bean.Admin;


public interface AdminService  {
    Admin adminLogin(String AdminId,String AdminPass);
}
