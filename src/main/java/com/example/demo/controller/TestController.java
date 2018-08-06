package com.example.demo.controller;

import com.example.demo.entity.Test;
import com.example.demo.service.TestService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZERO
 */
@RestController
public class TestController {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private TestService testService;

    @PostMapping("/test")
    public PageInfo<Test> testPageInfo(@RequestParam(value = "string", required=false) String string, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize) {
        logger.debug("测试日志输出！！！");
        return testService.getMessage(string, pageNum, pageSize);
    }
}
