package com.trycatch.sendemail;

import android.util.Log;

import com.trycatch.sendemail.vo.UserEmail;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 在此写用途
 *
 * @FileName: com.trycatch.sendemail.ExcelUtil.java
 * @author: guoyoujin
 * @mail: guoyoujin123@gmail.com
 * @date: 2017-07-28 17:40
 * @version: V1.0 <描述当前版本功能>
 */

public class ExcelUtil {
    public static String getCellValue(Cell cell) {
        String value = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_FORMULA: // 公式
            case Cell.CELL_TYPE_NUMERIC: // 数字

                double doubleVal = cell.getNumericCellValue();
                short format = cell.getCellStyle().getDataFormat();
                String formatString = cell.getCellStyle().getDataFormatString();

                if (format == 14 || format == 31 || format == 57 || format == 58 || (format >= 176 && format <= 183)) {
                    // 日期
                    Date date = DateUtil.getJavaDate(doubleVal);
                    value = formatDate(date, dateFmtPattern);
                } else if (format == 20 || format == 32 || (format >= 184 && format <= 187)) {
                    // 时间
                    Date date = DateUtil.getJavaDate(doubleVal);
                    value = formatDate(date, "HH:mm");
                } else {
                    value = String.valueOf(doubleVal);
                }

                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                value = cell.getStringCellValue();

                break;
            case Cell.CELL_TYPE_BLANK: // 空白
                value = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR: // Error，返回错误码
                value = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                value = "";
                break;
        }
        return value;
    }
    protected static final String dateFmtPattern = "yyyy-MM-dd";
    @SuppressWarnings("deprecation")
    private static String formatDate(Date d, String sdf) {
        String value = null;
        if (d.getSeconds() == 0 && d.getMinutes() == 0 && d.getHours() == 0) {
        } else {
        }
        return value;
    }
    
    
    public static Workbook getWorkBook(String filePath){
        Workbook wb = null;
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(ExcelUtil.isExcel2003(filePath)){
                wb = new HSSFWorkbook(is);
            }else if(ExcelUtil.isExcel2007(filePath)){
                wb = new XSSFWorkbook(is);
            }else{
                wb=null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {

            Log.e("IOException", e.toString());
        }
        return wb;
        
    }
    
    public static List<UserEmail> redWorkBookGetData(Workbook wb){
        List<UserEmail> list = new ArrayList<>();
        if(wb==null){
            return null;
        }
        for (int k = 0; k < wb.getNumberOfSheets(); k++) {
            Sheet sheet = wb.getSheetAt(k);
            int rows = sheet.getPhysicalNumberOfRows();
            for (int r = 0; r < rows; r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                int cells = row.getPhysicalNumberOfCells();
                UserEmail userEmail  = new UserEmail();
                for (int c = 0; c < cells; c++) {
                    Cell cell = row.getCell(c);
                    if (cell == null) {
                        continue;
                    }
                    String value = getCellValue(cell);
                    if(c==0){
                        userEmail.setFirstName(value);
                    }
                    if(c==1){
                        userEmail.setLastName(value);
                    }
                    if(c==2){
                        userEmail.setEmail(value);
                    }
                    if(c==3){
                        userEmail.setCountry(value);
                    }
                    if(c==4){
                        userEmail.setAddrerss(value);
                    }
                    if(c==5){
                        userEmail.setSendState(Integer.getInteger(value));
                    }
                }
                list.add(userEmail);
            }
        }
        return list;
    }


    public static List<UserEmail> redFile(String path){
        if(path==null){
            return null;
        }
        List<UserEmail> list = new ArrayList<>();
        Workbook wb = null;
        try {
            InputStream is = new FileInputStream(path);
            if(ExcelUtil.isExcel2003(path)){
                wb = new HSSFWorkbook(is);
            }else if(ExcelUtil.isExcel2007(path)){
                wb = new XSSFWorkbook(is);
            }else{
                wb=null;
            }
            if (wb!=null){
                Log.d("getNumberOfSheets",wb.getNumberOfSheets()+"");
                for (int k = 0; k < wb.getNumberOfSheets(); k++) {
                    Sheet sheet = wb.getSheetAt(k);
                    int rows = sheet.getPhysicalNumberOfRows();
                    for (int r = 0; r < rows; r++) {
                        Row row = sheet.getRow(r);
                        if (row == null) {
                            continue;
                        }
                        int cells = row.getPhysicalNumberOfCells();
                        UserEmail userEmail  = new UserEmail();
                        for (int c = 0; c < cells; c++) {
                            Cell cell = row.getCell(c);
                            if (cell == null) {
                                continue;
                            }
                            String value = getCellValue(cell);
                            if(c==0){
                                userEmail.setFirstName(value);
                            }
                            if(c==1){
                                userEmail.setLastName(value);
                            }
                            if(c==2){
                                userEmail.setEmail(value);
                            }
                            if(c==3){
                                userEmail.setCountry(value);
                            }
                            if(c==4){
                                userEmail.setAddrerss(value);
                            }
                            if(c==5){
                                userEmail.setSendState(Integer.getInteger(value));
                            }
                        }
                        list.add(userEmail);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", e.toString());
        } catch (IOException e) {

            Log.e("IOException", e.toString());
        }
        return list;
    }


    public static boolean isExcel2003(String filePath)
    {

        return filePath.matches("^.+\\.(?i)(xls)$");

    }

    public static boolean isExcel2007(String filePath)
    {

        return filePath.matches("^.+\\.(?i)(xlsx)$");

    }
}
