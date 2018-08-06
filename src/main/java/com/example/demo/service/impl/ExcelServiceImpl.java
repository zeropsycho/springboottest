package com.example.demo.service.impl;

import com.example.demo.service.ExcelService;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZERO
 * @Date 2018-08-04
 */
public class ExcelServiceImpl implements ExcelService {


    @Override
    public void importExcel() {

    }

    @Override
    public void exportExcel(HttpServletResponse httpServletResponse) {
        // 标题
        String title;
        //属性列头
        Map<String, Object> map = new HashMap<>();
        // 日期格式
    }
}
