package com.jesper.mapper;

import com.jesper.model.Course;
import com.jesper.model.Delivery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {

    int deleteByPrimaryKey(String id);
   

    int insert(Course record);

    Course selectByPrimaryKey(String id);

    List<Course> selectAll();

    int updateByPrimaryKey(Course record);

   
    // 课程记录列表
    Integer count(Course course);
    
    List<Course> list(Course course);
}