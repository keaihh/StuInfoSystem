package com.fc.mapper;

import com.fc.bean.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface AdminMapper {

    @Select("select * from admin where  admin_id=#{adminId} and admin_pass=#{adminPass}")
    Admin selectAdminByIdAndPass(@Param("adminId") String adminId, @Param("adminPass") String adminPass);

}
