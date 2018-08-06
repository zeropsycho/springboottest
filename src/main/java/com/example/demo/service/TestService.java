package com.example.demo.service;

import com.example.demo.entity.Test;
import com.github.pagehelper.PageInfo;

public interface TestService {

    PageInfo<Test> getMessage(String str, Integer pageNum, Integer pageSize);
}
