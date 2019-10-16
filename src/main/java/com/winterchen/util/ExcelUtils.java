package com.winterchen.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:POI解析Excel获取所有数据(支持xls/xlsx)
 * @author zhjun 2015-11-5
 *
 */
@SuppressWarnings("deprecation")
public class ExcelUtils {
	InputStream ins = null;
	Workbook wb = null;
	List<Object[]> dataList = new ArrayList<Object[]>(100);
	
    public static void CreateBorder(CellStyle cellStyle) {
        if (cellStyle != null) {
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
        }
    }
    
    public static Cell setCellValue(Row row, int i, String value) {
		// TODO Auto-generated method stub
		Cell cell = row.createCell(i);
		cell.setCellValue(value);
		
		return cell;
	}
	
    public static Cell setCellValue(Row row, int i, int value) {
		// TODO Auto-generated method stub
		Cell cell = row.createCell(i);
		cell.setCellValue(value);
		
		return cell;
	}
	
	/**
	 * 通过流读取
	 * @param ins
	 */
	public ExcelUtils(InputStream ins){
		try{
			wb = WorkbookFactory.create(ins);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(InvalidFormatException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			if(ins != null){
				try{
					ins.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 通过文件读取
	 * @param file
	 */
	public ExcelUtils(File file){
		try{
			ins = new FileInputStream(file);
			wb = WorkbookFactory.create(ins);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(InvalidFormatException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			if(ins != null){
				try{
					ins.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 通过文件路径读取
	 * @param path
	 */
	public ExcelUtils(String path){
		try{
			ins = new FileInputStream(path);
			wb = WorkbookFactory.create(ins);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(InvalidFormatException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			if(ins != null){
				try{
					ins.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
  
	/**
	 * 取Excel所有数据，包含header
	 * @return  List<Object[]>
	 */
	public List<Object[]> getAllData(int sheetIndex){
		int columnNum = 0;
		Sheet sheet = wb.getSheetAt(sheetIndex);
		if(sheet.getRow(0)!=null){
			columnNum = sheet.getRow(0).getLastCellNum()-sheet.getRow(0).getFirstCellNum();
		}
		if(columnNum>0){
			for(Row row:sheet){
				Object[] singleRow = new Object[columnNum];
				int n = 0;
				
				for(int i=0;i<columnNum;i++){
					Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
					singleRow[n] = getCellValue(cell);					
					n++;
				}

				//在后续方法中校验
				if(singleRow[0] == null || StringUtils.isBlank(singleRow[0].toString())){
					
				}else{
					dataList.add(singleRow);
				}

				
			}
		}
		return dataList;
	}  
	
	public List<Object[]> getAllData(int sheetIndex,int firstRowNum){
		int columnNum = 0;
		Sheet sheet = wb.getSheetAt(sheetIndex);
		if(sheet.getRow(0)!=null){
			columnNum = sheet.getRow(firstRowNum).getLastCellNum();
		}
		if(columnNum>0){
			for(Row row:sheet){
				Object[] singleRow = new Object[columnNum];
				int n = 0;
				for(int i=0;i<columnNum;i++){
					Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
					singleRow[n] = getCellValue(cell);					
					n++;
				} 
				if("".equals(singleRow[0])){continue;}//如果第一行为空，跳过
				dataList.add(singleRow);
			}
		}
		return dataList;
	}  
	
	
	/**
	 * 获取列的数据信息
	 * @param cell
	 * @return
	 */
	private Object getCellValue(Cell cell){
		Object cellValue = null;
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

		switch(cell.getCellType()){
			case Cell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = Boolean.toString(cell.getBooleanCellValue());
				break;
				//数值
			case Cell.CELL_TYPE_NUMERIC:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String temp = cell.getStringCellValue();
				//判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
				if(temp.indexOf(".")>-1){
					cellValue = String.valueOf(new Double(temp)).trim();
				}else{
					cellValue = temp.trim();
				}
				break;
		   case Cell.CELL_TYPE_STRING:
			   cellValue = cell.getStringCellValue().trim();
			   break;
		   case Cell.CELL_TYPE_ERROR:
			   cellValue = "";
			   break;  
		   case Cell.CELL_TYPE_FORMULA:
			   CellValue cv = evaluator.evaluate(cell);
			   
			   if (cv.getCellType()== Cell.CELL_TYPE_STRING) {
				   cellValue = cv.getStringValue();
			   }
			   else if(cv.getCellType()== Cell.CELL_TYPE_NUMERIC){
				   cellValue = cv.getNumberValue();
			   }
			   else{
				   cellValue = cell.getCellFormula();
			   }
			   
			   break;  
		   default:
		     cellValue = "";
		}
		
		return cellValue;
	}
	
	
	/**
	 * 返回Excel最大行index值，实际行数要加1
	 * @return
	 */
	public int getRowNum(int sheetIndex){
		Sheet sheet = wb.getSheetAt(sheetIndex);
		return sheet.getLastRowNum();
	}
  
	/**
	 * 返回数据的列数
	 * @param sheetIndex
	 * @return
	 */
	public int getColumnNum(int sheetIndex){
		Sheet sheet = wb.getSheetAt(sheetIndex);
		Row row = sheet.getRow(0);
		if(row!=null&&row.getLastCellNum()>0){
			return row.getLastCellNum();
		}
		return 0;
	}
  
	/**
	 * 获取某一行数据
	 * @param rowIndex 计数从0开始，rowIndex为0代表header行
	 * @return
	 */
	public Object[] getRowData(int sheetIndex,int rowIndex){
		Object[] dataArray = null;
		if(rowIndex > this.getColumnNum(sheetIndex)){
			return dataArray;
		}else{
//			dataArray = new Object[this.getColumnNum(sheetIndex)];
			return this.dataList.get(rowIndex);
		}
	}

  
	/**
	 * 获取某一列数据
	 * @param colIndex
	 * @return
	 */
	public Object[] getColumnData(int sheetIndex,int colIndex){
		Object[] dataArray = null;
		if(colIndex>this.getColumnNum(sheetIndex)){
			return dataArray;
		}else{   
			if(this.dataList!=null&&this.dataList.size()>0){
				dataArray = new Object[this.getRowNum(sheetIndex)+1];
				int index = 0;
				for(Object[] rowData:dataList){
					if(rowData!=null){
						dataArray[index] = rowData[colIndex];
						index++;
					}
				}
			}
		}
		
		return dataArray;
	}
	
	public static void main(String[] args){
    	ExcelUtils re = new ExcelUtils("D:\\fpb.xlsx");
    	
    	List<Object[]> objList = re.getAllData(0);
    	
    	for(int i=0; i < objList.size(); i++){
    		String result = "";
    		for(int j = 0; j < objList.get(i).length; j++){
    			result += objList.get(i)[j] + "_";
    		}
    		
    		System.out.println(result);
    	}
	}
	
    /*
     * 导出数据
     * */
	public static void export(String fileName,String title,String[] rowName,List<Object[]>  dataList,HttpServletResponse response) throws Exception{
        try{
            HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet(title);                     // 创建工作表
            
            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);
            HSSFCell cellTiltle = rowm.createCell(0);
            
            //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
            HSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);//获取列头样式对象
            HSSFCellStyle style = getStyle(workbook);                    //单元格样式对象
            
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length-1)));
            cellTiltle.setCellStyle(columnTopStyle);
            cellTiltle.setCellValue(title);
            
            // 定义所需列数
            int columnNum = rowName.length;
            HSSFRow rowRowName = sheet.createRow(2);                // 在索引2的位置创建行(最顶端的行开始的第二行)
            
            // 将列头设置到sheet的单元格中
            for(int n=0;n<columnNum;n++){
                HSSFCell cellRowName = rowRowName.createCell(n);                //创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);                //设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text);                                    //设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle);                        //设置列头单元格样式
            }
            
            //将查询出的数据设置到sheet对应的单元格中
            for(int i=0;i<dataList.size();i++){
                
                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i+3);//创建所需的行数
                
                for(int j=0; j<obj.length; j++){
                    HSSFCell cell = null;   //设置单元格的数据类型
                    if(!"".equals(obj[j]) && obj[j] != null){
                    	if(obj[j] instanceof Integer){
                    		cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
	                        cell.setCellValue(Integer.parseInt(obj[j].toString()));                        //设置单元格的值
	                    }else if(obj[j] instanceof BigDecimal){
	                    	cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
	                        cell.setCellValue(new BigDecimal(obj[j].toString()).doubleValue());
	                    }else if(obj[j] instanceof Double){
	                    	cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
	                        cell.setCellValue(new BigDecimal(obj[j].toString()).doubleValue());
	                    }else if(obj[j] instanceof Float){
	                    	cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
	                        cell.setCellValue(new BigDecimal(obj[j].toString()).doubleValue());
	                    }else if(obj[j] instanceof Long){
	                    	cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
	                        cell.setCellValue(new BigDecimal(obj[j].toString()).doubleValue());
	                    }else{
	                    	cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
	                        cell.setCellValue(obj[j].toString());  
	                    }
                    }else{
                    	cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                    	cell.setCellValue("");
                    }
                    cell.setCellStyle(style);                                    //设置单元格样式
                }
            }
            //让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    //当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }

                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        	try{
	                            int length = currentCell.getStringCellValue().getBytes().length;
	                            if (columnWidth < length) {
	                                columnWidth = length;
	                            }
                        	}catch(Exception e){
                        		
                        	}
                        }
                    }
                }
                if(colNum == 0){
                	if(columnWidth < 255){
                		sheet.setColumnWidth(colNum, (columnWidth-2) * 256);
                	}else{
                		sheet.setColumnWidth(colNum, 255 * 256);
                	}
                }else{
                	if(columnWidth < 251){
                		sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
                	}else{
                		sheet.setColumnWidth(colNum, 255 * 256);
                	}
                }
            }
            
            if(workbook !=null){
                try
                {
                    fileName = fileName + ".xls";
                    String headStr = "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"";
                    response.setContentType("APPLICATION/OCTET-STREAM");
                    response.setHeader("Content-Disposition", headStr);
                    OutputStream out = response.getOutputStream();
                    workbook.write(out);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
	

	/*
     * 导出数据
     * */
	public static void export(String fileName,String[] rowName,List<Object[]>  dataList,HttpServletResponse response) throws Exception{
        try{
            HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet("sheet1");                     // 创建工作表
            
            // 产生表格标题行
            
            //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
            HSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);//获取列头样式对象
            HSSFCellStyle style = getStyle(workbook);                    //单元格样式对象
            
            // 定义所需列数
            int columnNum = rowName.length;
            HSSFRow rowRowName = sheet.createRow(0);                // 在索引3的位置创建行(最顶端的行开始的第二行)
            
            // 将列头设置到sheet的单元格中
            for(int n=0;n<columnNum;n++){
                HSSFCell cellRowName = rowRowName.createCell(n);                //创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);                //设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text);                                    //设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle);                        //设置列头单元格样式
            }
            
            //将查询出的数据设置到sheet对应的单元格中
            for(int i=0;i<dataList.size();i++){
                
                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i+1);//创建所需的行数
                
                for(int j=0; j<obj.length; j++){
                    HSSFCell cell = null;   //设置单元格的数据类型
                    if(!"".equals(obj[j]) && obj[j] != null){
                    	if(obj[j] instanceof Integer){
                    		cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
	                        cell.setCellValue(Integer.parseInt(obj[j].toString()));                        //设置单元格的值
	                    }else if(obj[j] instanceof BigDecimal){
	                    	cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
	                        cell.setCellValue(new BigDecimal(obj[j].toString()).doubleValue());
	                    }else if(obj[j] instanceof Double){
	                    	cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
	                        cell.setCellValue(new BigDecimal(obj[j].toString()).doubleValue());
	                    }else if(obj[j] instanceof Float){
	                    	cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
	                        cell.setCellValue(new BigDecimal(obj[j].toString()).doubleValue());
	                    }else if(obj[j] instanceof Long){
	                    	cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
	                        cell.setCellValue(new BigDecimal(obj[j].toString()).doubleValue());
	                    }else{
	                    	cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
	                        cell.setCellValue(obj[j].toString());  
	                    }
                    }else{
                    	cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                    	cell.setCellValue("");
                    }
                    cell.setCellStyle(style);                                    //设置单元格样式
                }
            }
            //让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    //当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }

                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        	try{
	                            int length = currentCell.getStringCellValue().getBytes().length;
	                            if (columnWidth < length) {
	                                columnWidth = length;
	                            }
                        	}catch(Exception e){
                        		
                        	}
                        }
                    }
                }
                if(colNum == 0){
                	if(columnWidth < 255){
                		sheet.setColumnWidth(colNum, (columnWidth-2) * 256);
                	}else{
                		sheet.setColumnWidth(colNum, 255 * 256);
                	}
                }else{
                	if(columnWidth < 251){
                		sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
                	}else{
                		sheet.setColumnWidth(colNum, 255 * 256);
                	}
                }
            }
            
            if(workbook !=null){
                try{
                    fileName = fileName + ".xls";
                    String headStr = "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"";
                    response.setContentType("APPLICATION/OCTET-STREAM");
                    response.setHeader("Content-Disposition", headStr);
                    OutputStream out = response.getOutputStream();
                    workbook.write(out);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
	 /*
     * 创建excel
     * */
	public static void createExcel(List<Object[]>  dataList,String path) throws Exception{
        try{
            HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet("sheet1");                     // 创建工作表
            
            //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
            HSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);//获取列头样式对象
            HSSFCellStyle style = getStyle(workbook);                    //单元格样式对象
          
            
            // 定义所需列数
            int columnNum = dataList.get(0).length;
            HSSFRow rowRowName = sheet.createRow(0);                // 在索引2的位置创建行(最顶端的行开始的第二行)
            
            // 将列头设置到sheet的单元格中
            for(int n=0;n<columnNum;n++){
                HSSFCell cellRowName = rowRowName.createCell(n);                //创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);                //设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(dataList.get(0)[n].toString());
                cellRowName.setCellValue(text);                                    //设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle);                        //设置列头单元格样式
            }
            
            //将查询出的数据设置到sheet对应的单元格中
            for(int i=1;i<dataList.size();i++){
                
                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i);//创建所需的行数
                
                for(int j=0; j<obj.length; j++){
                    HSSFCell cell = null;   //设置单元格的数据类型
                    cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                    if(!"".equals(obj[j]) && obj[j] != null){
                        cell.setCellValue(obj[j].toString());                        //设置单元格的值
                    }else{
                    	cell.setCellValue("");
                    }
                    cell.setCellStyle(style);                                    //设置单元格样式
                }
            }
            //让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    //当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }

                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        	try{
	                            int length = currentCell.getStringCellValue().getBytes().length;
	                            if (columnWidth < length) {
	                                columnWidth = length;
	                            }
                        	}catch(Exception e){
                        		
                        	}
                        }
                    }
                }
                if(colNum == 0){
                    sheet.setColumnWidth(colNum, (columnWidth-2) * 256);
                }else{
                    sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
                }
            }
            
            if(workbook !=null){
                try{
                	
                	FileOutputStream output = new FileOutputStream(path);
                	workbook.write(output);
                    workbook.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
	
    /* 
     * 列头单元格样式
     */    
      public static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
          
            // 设置字体
          HSSFFont font = workbook.createFont();
          //设置字体大小
          font.setFontHeightInPoints((short)11);
          //字体加粗
          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
          //设置字体名字 
          font.setFontName("Courier New");
          //设置样式; 
          HSSFCellStyle style = workbook.createCellStyle();
          //设置底边框; 
          style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
          //设置底边框颜色;  
          style.setBottomBorderColor(HSSFColor.BLACK.index);
          //设置左边框;   
          style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
          //设置左边框颜色; 
          style.setLeftBorderColor(HSSFColor.BLACK.index);
          //设置右边框; 
          style.setBorderRight(HSSFCellStyle.BORDER_THIN);
          //设置右边框颜色; 
          style.setRightBorderColor(HSSFColor.BLACK.index);
          //设置顶边框; 
          style.setBorderTop(HSSFCellStyle.BORDER_THIN);
          //设置顶边框颜色;  
          style.setTopBorderColor(HSSFColor.BLACK.index);
          //在样式用应用设置的字体;  
          style.setFont(font);
          //设置自动换行; 
          style.setWrapText(false);
          //设置水平对齐的样式为居中对齐;  
          style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
          //设置垂直对齐的样式为居中对齐; 
          style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
          
          return style;
          
      }
      
      /*  
     * 列数据信息单元格样式
     */  
      public static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
            // 设置字体
            HSSFFont font = workbook.createFont();
            //设置字体大小
            //font.setFontHeightInPoints((short)10);
            //字体加粗
            //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            //设置字体名字 
            font.setFontName("Courier New");
            //设置样式; 
            HSSFCellStyle style = workbook.createCellStyle();
            //设置底边框; 
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            //设置底边框颜色;  
            style.setBottomBorderColor(HSSFColor.BLACK.index);
            //设置左边框;   
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            //设置左边框颜色; 
            style.setLeftBorderColor(HSSFColor.BLACK.index);
            //设置右边框; 
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            //设置右边框颜色; 
            style.setRightBorderColor(HSSFColor.BLACK.index);
            //设置顶边框; 
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            //设置顶边框颜色;  
            style.setTopBorderColor(HSSFColor.BLACK.index);
            //在样式用应用设置的字体;  
            style.setFont(font);
            //设置自动换行; 
            style.setWrapText(false);
            //设置水平对齐的样式为居中对齐;  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //设置垂直对齐的样式为居中对齐; 
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
           
            return style;
      
      }
}
