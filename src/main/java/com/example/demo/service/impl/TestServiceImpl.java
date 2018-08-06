package com.example.demo.service.impl;

import com.example.demo.dao.TestDao;
import com.example.demo.entity.Test;
import com.example.demo.service.TestService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZERO
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;

    @Override
    public PageInfo<Test> getMessage(String string, Integer pageNum, Integer pageSize) {
        // 自动分页处理
        /*PageHelper.startPage(1, 2, true);
        List<Test> testList = testDao.selectByName(string, pageNum, pageSize);
        PageInfo<Test> pageInfo = new PageInfo<>(testList);*/

        // 手动分页处理
        return null;
    }
}
