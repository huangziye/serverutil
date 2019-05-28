package com.hzy.excel

import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import java.nio.charset.Charset
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Excel 相关工具类
 */
object ExcelUtil {
    /**
     * 导出 excel
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     */
    @JvmOverloads
    fun export(request: HttpServletRequest, response: HttpServletResponse, sheetName: String, fileName: String, title: Array<String>, values: Array<Array<String>>, wb: HSSFWorkbook? = null) {
        var wb = wb
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = HSSFWorkbook()
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        val sheet = wb.createSheet(sheetName)

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        var row = sheet.createRow(0)

        // 第四步，创建单元格，并设置值表头 设置表头居中
        val titleStyle = getStyleWithBorderAndContentCenter(wb)

        //设置标题字体加粗
        val font = wb.createFont()
        font.bold = true
        titleStyle.setFont(font)

        //设置内容居中但不加粗
        val contentStyle = getStyleWithBorderAndContentCenter(wb)


        //创建标题
        for (i in title.indices) {
            //声明列对象
            val cell = row.createCell(i)
            cell.setCellValue(title[i])
            cell.setCellStyle(titleStyle)
        }

        //创建内容
        for (i in values.indices) {
            row = sheet.createRow(i + 1)
            for (j in 0 until values[i].size) {
                //将内容按顺序赋给对应的列对象
                val cell = row.createCell(j)
                cell.setCellStyle(contentStyle)
                cell.setCellValue(values[i][j])
            }
        }

        //响应到客户端
        try {
            this.setResponseHeader(response, fileName)
            val os = response.outputStream
            wb.write(os)
            os.flush()
            os.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //发送响应流方法
    private fun setResponseHeader(response: HttpServletResponse, fileName: String) {
        var fileName = fileName
        try {
            /*try {
                fileName = String(fileName.toByteArray(), "ISO8859-1")
            } catch (e: UnsupportedEncodingException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }*/
            try {
                fileName = String(fileName.toByteArray(), Charset.forName("ISO8859-1"))
            } catch (e: Exception) {
            }
            response.contentType = "application/octet-stream;charset=ISO8859-1"
            response.setHeader("Content-Disposition", "attachment;filename=$fileName")
            response.addHeader("Pargam", "no-cache")
            response.addHeader("Cache-Control", "no-cache")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    /**
     * 获取内容居中且带边框的Style
     */
    private fun getStyleWithBorderAndContentCenter(wb: HSSFWorkbook): HSSFCellStyle {
        val style = wb.createCellStyle()
        style.alignment = HorizontalAlignment.CENTER // 创建一个居中格式
        //设置边框
        style.borderLeft = BorderStyle.THIN
        style.borderTop = BorderStyle.THIN
        style.borderRight = BorderStyle.THIN
        style.borderBottom = BorderStyle.THIN
        style.leftBorderColor = IndexedColors.BLACK.index
        style.topBorderColor = IndexedColors.BLACK.index
        style.rightBorderColor = IndexedColors.BLACK.index
        style.bottomBorderColor = IndexedColors.BLACK.index
        return style
    }

}