package com.example.demo.service;

import javax.servlet.http.HttpServletResponse;

/**
 * 表格导入导出
 */
public interface ExcelService {

    void importExcel();

    void exportExcel(HttpServletResponse httpServletResponse);
}
