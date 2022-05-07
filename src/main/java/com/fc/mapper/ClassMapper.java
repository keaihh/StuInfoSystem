package com.fc.mapper;

import com.fc.bean.Classes;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ClassMapper {

    @Select("select * from class")
    List<Classes> selectAllClass();

    @Select("select * from class where class_id=#{classId}")
    Classes selectClassById(String classId);


    @Delete("DELETE from class where class_id=#{classId}")
    int delectClassById(String classId);

    @Insert("insert into class values(#{classId},#{className})")
    int insertClass(Classes clas);

    @Select("select * from class where class_name=#{className}")
    Classes selectClassByName(String className);
}
