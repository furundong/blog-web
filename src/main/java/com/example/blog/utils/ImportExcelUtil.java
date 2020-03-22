package com.example.blog.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaofeng.he on 2018/06/22
 * poi 实现 excel 导入 工具类
 */
public class ImportExcelUtil {

    //正则表达式 用于匹配属性的第一个字母
    private static final String REGEX = "[a-zA-Z]";

    /**
     * Excel数据转 list
     * @param originUrl Excel表的所在路径
     * @param datePattern 日期格式 如"yyyy-MM-dd hh:mm:ss"
     * @param clazz 要返回的对象集合的类型
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> importExcel(String originUrl, String datePattern,Class<T> clazz) throws IOException {
        // 判断文件是否存在
        File file = new File(originUrl);
        if (!file.exists()) {
            throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
        }
        InputStream fis = new FileInputStream(file);

        return doImportExcel(fis, datePattern, clazz);
    }

    /**
     * Excel数据转 list
     * @param inputStream Excel文件输入流
     * @param datePattern 日期格式 如"yyyy-MM-dd hh:mm:ss"
     * @param clazz 要返回的对象集合的类型
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> importExcel(InputStream inputStream, String datePattern,Class<T> clazz) throws IOException {
        return doImportExcel(inputStream, datePattern,clazz);
    }

    /**
     * 真正实现
     *
     * @param inputStream Excel表的所在路径
     * @param datePattern 日期格式 如"yyyy-MM-dd hh:mm:ss"
     * @param clazz 要返回的对象集合的类型
     * @param <T>
     * @return
     * @throws IOException
     */
    private static <T> List<T> doImportExcel(InputStream inputStream, String datePattern, Class<T> clazz) throws IOException {
        Workbook wb;
        Sheet sheet;
        Row filedsRow = null;
        List<Row> rowList = new ArrayList<>();
        try {
            // 去读Excel
            // HSSFWorkbook wb = new HSSFWorkbook(fis);
            // 使用workbook 支持2003/2007
            wb = WorkbookFactory.create(inputStream);
            sheet = wb.getSheetAt(0);
            // 获取最后行号
            int lastRowNum = sheet.getLastRowNum();

            int rowLength = lastRowNum;

            // 获取属性列字段
            filedsRow = sheet.getRow(0);
            // 循环读取
            Row row;
            for (int i = 1; i <= rowLength; i++) {
                row = sheet.getRow(i);
                rowList.add(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        return returnObjectList(datePattern, filedsRow, rowList, clazz);
    }

    /**
     * 功能:返回指定的对象集合
     */
    private static <T>List<T> returnObjectList(String datePattern, Row filedsRow, List<Row> rowList,Class<T> clazz) {
        List<T> objectList=new ArrayList<>();
        try {
            T obj;
            String attribute;
            String value;

            for (Row row : rowList) {
                obj = clazz.newInstance();
                for (int j = 0; j < filedsRow.getLastCellNum(); j++) {
                    attribute = getCellValue(filedsRow.getCell(j));
                    if (!attribute.equals("")) {
                        value = getCellValue(row.getCell(j));
                        setAttrributeValue(obj, attribute, value, datePattern);
                    }
                }
                objectList.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectList;
    }

    /**
     * 功能:获取单元格的值
     */
    private static String getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    result = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    result = cell.getNumericCellValue();
                    break;
                case BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case FORMULA:
                    result = cell.getCellFormula();
                    break;
                case ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case BLANK:
                    break;
                default:
                    break;
            }
        }
        return result.toString();
    }

    /**
     * 功能:给指定对象的指定属性赋值
     */
    private static void setAttrributeValue(Object obj,String attribute,String value, String datePattern) {
        //得到该属性的set方法名
        String method_name = convertToMethodName(attribute,obj.getClass(),true);
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            /**
             * 因为这里只是调用bean中属性的set方法，属性名称不能重复
             * 所以set方法也不会重复，所以就直接用方法名称去锁定一个方法
             * （注：在java中，锁定一个方法的条件是方法名及参数）
             * 如果这里不要id，那么你可以在if里面判断，是setId，就不要走了。
             */
            if(method.getName().equalsIgnoreCase("setId")){
                continue;
            }
            if(method.getName().equalsIgnoreCase(method_name)) {
                Class<?>[] parameterC = method.getParameterTypes();
                try {
                    /**如果是(整型,浮点型,布尔型,字节型,时间类型),
                     * 按照各自的规则把value值转换成各自的类型
                     * 否则一律按类型强制转换(比如:String类型)
                     */
                    if(parameterC[0] == int.class || parameterC[0]==java.lang.Integer.class) {
                        if(value != null && value.length() > 0) {
                            value = value.substring(0, value.lastIndexOf("."));
                            method.invoke(obj,Integer.valueOf(value));
                        }

                        break;
                    } else if(parameterC[0] == long.class || parameterC[0]==java.lang.Long.class) {
                        if(value != null && value.length() > 0) {
                            value = value.substring(0, value.lastIndexOf("."));
                            method.invoke(obj,Long.valueOf(value));
                        }
                        break;
                    } else if(parameterC[0] == float.class || parameterC[0]==java.lang.Float.class) {
                        if(value != null && value.length() > 0) {
                            method.invoke(obj, Float.valueOf(value));
                        }
                        break;
                    } else if(parameterC[0] == double.class || parameterC[0]==java.lang.Double.class) {
                        if(value != null && value.length() > 0) {
                            method.invoke(obj, Double.valueOf(value));
                        }
                        break;
                    } else if(parameterC[0] == byte.class || parameterC[0]==java.lang.Byte.class) {
                        if(value != null && value.length() > 0) {
                            method.invoke(obj, Byte.valueOf(value));
                        }
                        break;
                    } else if(parameterC[0] == boolean.class|| parameterC[0]==java.lang.Boolean.class) {
                        if (value != null && value.length() > 0) {
                            method.invoke(obj, Boolean.valueOf(value));
                        }
                        break;
                    } else if(parameterC[0] == java.util.Date.class) {
                        if(value != null && value.length() > 0) {
                            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
                            Date date=null;
                            try {
                                date=sdf.parse(value);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            method.invoke(obj,date);
                        }

                        break;
                    }else if(parameterC[0] == java.sql.Timestamp.class) {
                        if(value != null && value.length() > 0) {
                            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
                            Date date=null;
                            try {
                                date=sdf.parse(value);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            method.invoke(obj,new Timestamp(date.getTime()));
                        }

                        break;
                    } else if(parameterC[0] == java.math.BigDecimal.class) {
                        if (value != null && value.length() > 0) {
                            method.invoke(obj, new BigDecimal(value));
                        }
                        break;
                        //string
                    } else {
                        if (value != null && value.length() > 0) {
                            method.invoke(obj,parameterC[0].cast(value));
                        }
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 功能:根据属性生成对应的set/get方法
     */
    private static String convertToMethodName(String attribute,Class<?> objClass,boolean isSet) {
        /** 通过正则表达式来匹配第一个字符 **/
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(attribute);
        StringBuilder sb = new StringBuilder();
        /** 如果是set方法名称 **/
        if(isSet) {
            sb.append("set");
        } else {
            /** get方法名称 **/
            try {
                Field attributeField = objClass.getDeclaredField(attribute);
                /** 如果类型为boolean **/
                if(attributeField.getType() == boolean.class||attributeField.getType() == Boolean.class) {
                    sb.append("is");
                } else {
                    sb.append("get");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /** 针对以下划线开头的属性 **/
        if(attribute.charAt(0)!='_' && m.find()) {
            sb.append(m.replaceFirst(m.group().toUpperCase()));
        } else {
            sb.append(attribute);
        }
        return sb.toString();
    }

}