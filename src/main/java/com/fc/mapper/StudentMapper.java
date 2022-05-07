package com.fc.mapper;

import com.fc.bean.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;



@Mapper
public interface StudentMapper {

    @Select("select * from student where  stu_id=#{stuId} and stu_pass=#{stuPass}")
     Student selectStudentByIdAndPass(@Param("stuId") String stuId, @Param("stuPass") String stuPass);

    @Select("select * from student")
    List<Student> selectAllStudent();

    @Insert("insert into student (stu_id,stu_name,stu_class,stu_sex,stu_tele) values" +
            "(#{stuId},#{stuName},#{stuClass},#{stuSex},#{stuTele})")
     int insertStudent(Student stu);

    @Insert("insert into student  values(#{stuId},#{stuName},#{stuPass},#{stuClass},#{stuSex},#{stuTele})")
    int insertStudentHavaPass(Student stu);

    @Select("select * from student where  stu_id=#{stuId} ")
    Student selectStudentByStuId(String stuId);

    @Delete("DELETE from student where stu_id=#{stuId}")
    int deleteStuById(String StuId);

    @Select(value = "SELECT * from student where stu_class=#{sutClass}")
    List<Student> selectStuByStuclass(String sutClass);

    @Select("select stu_name from student where stu_id=#{stuId} ")
    String selectNameById(String stuId);

    @Select("select stu_id from student where stu_class=#{stuClass}")
    List<String> selectIdByClass(String stuClass);



}
