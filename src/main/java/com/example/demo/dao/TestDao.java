package com.example.demo.dao;

import com.example.demo.entity.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestDao {

    String getMessage(String string);

    List<Test> selectByName(@Param("name") String string, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
}
