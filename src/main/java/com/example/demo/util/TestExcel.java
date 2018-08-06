package com.example.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.ExcelDefualtEnum;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hpsf.WritingNotSupportedException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 作为测试表格，导出
 * @author ZERO
 * @Date 2018-08-04 14:51
 */
public class TestExcel {
    private static Logger logger = LoggerFactory.getLogger(TestExcel.class.getName());

    /**
     * 使用HSSFWorkdbook形式导出表格
     */
    public static void exportExcel(String title, Map<String, String> map, JSONArray jsonArray,
                                   String datePattern, int colWidth, OutputStream outputStream) {

        if (null != datePattern) {
            ExcelDefualtEnum.DATE_DEFAULAT_FORMAT = datePattern;
        }

        // 声明一个工作簿
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        hssfWorkbook.createInformationProperties();
        hssfWorkbook.getDocumentSummaryInformation().setCompany("*****公司");

        /**
         * 文档属性
         */
        SummaryInformation si = hssfWorkbook.getSummaryInformation();
        // 填加xls文件作者信息
        si.setAuthor("JACK");
        // 填加xls文件创建程序信息
        si.setApplicationName("导出程序");
        // 填加xls文件最后保存者信息
        si.setLastAuthor("最后保存者信息");
        // 填加xls文件作者信息
        si.setComments("JACK is a programmer!");
        // 填加xls文件标题信息
        si.setTitle("POI导出Excel");
        // 填加文件主题信息
        si.setSubject("POI导出Excel");
        si.setCreateDateTime(new Date());

        // 设置列头样式
        HSSFCellStyle titleStylee = hssfWorkbook.createCellStyle();
        titleStylee.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置列头字体样式
        HSSFFont hssfFont = hssfWorkbook.createFont();
        // 字体高度
        hssfFont.setFontHeightInPoints((short) 20);
        // 字体宽度
        hssfFont.setBoldweight((short) 700);
        titleStylee.setFont(hssfFont);

        // 设置列表样式
        HSSFCellStyle headerStyle = hssfWorkbook.createCellStyle();
        // 下边线
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 左边线
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        // 右边线
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 上边线
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置列表字体样式
        HSSFFont headerFont = hssfWorkbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);
        //设置单元格样式
        HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 设置字体
        HSSFFont cellFont = hssfWorkbook.createFont();
        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        cellStyle.setFont(cellFont);
        // 生成一个（带标题）表格
        HSSFSheet sheet = hssfWorkbook.createSheet();
        // 声明画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 注释内容 作者信息
        comment.setAuthor("ZERO");
        // 内容
        comment.setString(new HSSFRichTextString("poi的注释功能！！！"));
        // 设置列宽 // 至少字节数
        int minBytes = colWidth < ExcelDefualtEnum.DEFAULT_COLOUMN_WIDTH ? ExcelDefualtEnum.DEFAULT_COLOUMN_WIDTH : colWidth;
        int[] arrColWidth = new int[map.size()];
        String[] properties = new String[map.size()];
        String[] headers = new String[map.size()];
        int index = 0;
        // 遍历集合数据，产生数据行
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            String fileName = iter.next();
            // 列表值
            properties[index] = fileName;
            // 属性值
            headers[index] = fileName;
            //名字的字节长度
            int bytes = fileName.getBytes().length;
            // 默认值为17，自定义值查过该值时，按最新的值去计算
            arrColWidth[index] = bytes < minBytes ? minBytes : bytes;
            // 以较为精准数据去控制列宽
            sheet.setColumnWidth(index, (int) (arrColWidth[index] + 0.72) * 256);
            index++;
        }
        // 设置每页多少条，超出每页条数，则创建新的sheet
        int rowIndex = 0;
        for (Object obj : jsonArray) {
            if (rowIndex == ExcelDefualtEnum.ROW_DEFAULAT || rowIndex == 0) {
                if (rowIndex != 0) {
                    // 数据超过了每页条数，则创建新的sheet
                    sheet = hssfWorkbook.createSheet();
                }
                // 表头数据
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell(0).setCellValue(title);
                titleRow.getCell(0).setCellStyle(titleStylee);
                // 合并单元格
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, map.size() - 1));
                // 列头 rowIndex= 1
                HSSFRow headerRow = sheet.createRow(1);

                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                    headerRow.getCell(i).setCellStyle(headerStyle);
                }
                // 数据内通过从 rowIndex 2 开始
                rowIndex = 2;
            }

            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(obj);
            HSSFRow dataRow = sheet.createRow(rowIndex);
            // 录入数据
            for (int i = 0; i < properties.length; i++) {
                HSSFCell newCell = dataRow.createCell(i);
                //判断录入的类型
                Object object = jsonObject.get(properties[i]);
                // 接收转换后的值
                String cellValue;
                // 遍历的过程中校验值类型
                if (null == object) {
                    cellValue = "";
                } else if (object instanceof Date) {
                    cellValue = new SimpleDateFormat(ExcelDefualtEnum.DATE_DEFAULAT_FORMAT).format(object);
                } else if (object instanceof Float || object instanceof Double) {
                    cellValue = new BigDecimal(object.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                } else {
                    cellValue = object.toString();
                }
                // 数据值
                newCell.setCellValue(cellValue);
                // 样式值
                newCell.setCellStyle(cellStyle);
            }
            rowIndex++;
        }
        try {
            // 将工作簿对象写入到输出流中
            hssfWorkbook.write(outputStream);
            si.write(outputStream);
            // 关闭输出流、工作簿对象
            outputStream.close();
            hssfWorkbook.close();
        } catch (IOException ex) {
            logger.debug("io流异常：" + ex.getMessage());
        } catch (WritingNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        int count = 1;
        JSONArray ja = new JSONArray();
        for (int i = 0; i < count; i++) {
            Student s = new Student();
            s.setName("POI" + i);
            s.setAge(i);
            s.setBirthday(new Date());
            s.setHeight(i);
            s.setWeight(i);
            s.setSex(i / 2 == 0 ? false : true);
            ja.add(s);
        }
        Map<String, String> headMap = new LinkedHashMap<String, String>();
        headMap.put("name", "姓名1");
        headMap.put("age", "年龄");
        headMap.put("birthday", "生日");
        headMap.put("height", "身高");
        headMap.put("weight", "体重");
        headMap.put("sex", "性别");

        String title = "测试";
        OutputStream outXls = new FileOutputStream("E://a.xls");
        System.out.println("正在导出xls....");
        Date d = new Date();
        TestExcel.exportExcel(title, headMap, ja, null, 0, outXls);
        System.out.println("共" + count + "条数据,执行" + (new Date().getTime() - d.getTime()) + "ms");
        outXls.close();
    }
}
