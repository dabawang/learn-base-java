package com.ccsu.base.poi;

import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: Xiaolei Zhu
 * @Date: 2018/9/23
 * @Time: 21:06
 * Description:测试导出Excel内容
 * 参考：https://www.cnblogs.com/hanfeihanfei/p/7079210.html
 * 常用组件：
HSSFWorkbook                      excel的文档对象
HSSFSheet                         excel的表单
HSSFRow                           excel的行
HSSFCell                          excel的格子单元
HSSFFont                          excel字体
HSSFDataFormat                    日期格式
HSSFHeader                        sheet头
HSSFFooter                        sheet尾（只有打印的时候才能看到效果）
样式：
HSSFCellStyle                       cell样式
辅助操作包括：
HSSFDateUtil                        日期
HSSFPrintSetup                      打印
HSSFErrorConstants                  错误信息表
 */
public class PoiTest {

    /**
     * 一下一个Excel的文件的组织形式，
     * 一个Excel文件对应于一个workbook(HSSFWorkbook)，
     * 一个workbook可以有多个sheet（HSSFSheet）组成，
     * 一个sheet是由多个row（HSSFRow）组成，
     * 一个row是由多个cell（HSSFCell）组成。
     * @param all
     */
    public static void exportToExcel(List<Integer> all) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建工作表对象并命名
        HSSFSheet sheet = workbook.createSheet("sheet1");
        sheet.setDefaultColumnWidth(20);
        // 创建列表名称样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setWrapText(true);// 设置长文本自动换行
        //headerStyle.setAlignment();//居中
        HSSFFont font = workbook.createFont();
        font.setBold(true);// 字体加粗
        headerStyle.setFont(font);

        // 创建表头，列
        HSSFRow headerRow = sheet.createRow(0);//行索引
        headerRow.setHeightInPoints(25f);

        HSSFCell header1 = headerRow.createCell(0);//0列
        header1.setCellValue("第一列名称");
        header1.setCellStyle(headerStyle);
        HSSFCell header2 = headerRow.createCell(1);//1列
        header2.setCellValue("第二列名称");
        header2.setCellStyle(headerStyle);

        //一般数据样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        for (int i = 0; i < all.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            row.setHeightInPoints(20f);

            HSSFCell cell = row.createCell(0);//参数为：列数index = 0，此处是第一列
            cell.setCellValue(all.get(i));
            cell.setCellStyle(cellStyle);

            HSSFCell cell1 = row.createCell(1);//参数为：列数index = 1，此处是第二列
            cell1.setCellValue(all.get(i));
            cell1.setCellStyle(cellStyle);
        }

        //总述：将HSSFWorkbook中的文件流数，据输出即写到文件d:text.xls
        //声明变量，一般在哪用，就在哪声明，这行代码要是放在方法第一行也不是不可以，但是习惯不好，
        //像这样的小问题，是没人会给你说的，除了我这哎叨叨的大师兄以外。
        OutputStream outputStream = null;
        try {
            File file = new File("/Users/zhuxiaolei/gitProject/learn-base-java/src/main/java/com/ccsu/base/poi/poi.xls");//可能会抛异常：NullPointerException
            outputStream = new FileOutputStream(file);//1.打开资源：输出文件流；2.可能会抛异常：FileNotFoundException
            /* 关于 HSSFWorkbook.write(OutputStream stream) throws IOException {}
             | 方法的原文注释如下：
             |   Method write - write out this workbook to an Outputstream.  Constructs
             |   a new POI POIFSFileSystem, passes in the workbook binary representation  and
             |   writes it out.
             |   @param stream - the java OutputStream you wish to write the XLS to
             |   @exception IOException if anything can't be written.
             */
            //write会自动新建一个xls模板，然后把数据以二进制的形式写到里面，然后再写到输出流中
            workbook.write(outputStream);//可能会抛异常：IOException
        } catch (IOException e) {
            System.out.println(e.getMessage());//异常要处理给人看，要么log，要么...
        } finally {
            //正确关闭文件流的姿势
            try {
                workbook.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        List<Integer> all = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            all.add(i % 3);
        }
        PoiTest.exportToExcel(all);
    }
}
