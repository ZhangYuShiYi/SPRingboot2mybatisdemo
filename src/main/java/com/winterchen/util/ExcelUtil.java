package com.winterchen.util;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Auther: 李鹏飞
 * @Date: 2018/9/11
 * @Description: Excle 文件导入导出Util(easypoi)
 */
public class ExcelUtil {
	/**
	 * 功能描述：原始导出Excel
	 *
	 * @param list
	 *            导出的实体类
	 * @param sheetName
	 *            sheet表名
	 * @param pojoClass
	 *            映射的实体类
	 * @param fileName
	 * @param response
	 * @return
	 */
	public static void exportExcel(List<?> list, String sheetName, Class<?> pojoClass, String fileName,
			HttpServletResponse response) {
		ExportParams exportParams = new ExportParams();
		exportParams.setSheetName(sheetName);
		downLoadExcel(getWorkBook(list, pojoClass, sheetName), fileName, response);
	}

	/**
	 * 功能描述：获取workbook可以自定义样式
	 *
	 * @param list
	 *            导出的实体类
	 * @param pojoClass
	 *            映射的实体类
	 * @param sheetName
	 *            sheet表名
	 * @return
	 */
	public static Workbook getWorkBook(List<?> list, Class<?> pojoClass, String sheetName) {
		ExportParams exportParams = new ExportParams();
		exportParams.setSheetName(sheetName);
		return ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
	}

	/**
	 * 功能描述：Excel下载
	 *
	 * @param workbook
	 *            Excel对象
	 * @param fileName
	 *            文件名称
	 * @param response
	 * @return
	 */
	public static void downLoadExcel(Workbook workbook, String fileName, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 功能描述：根据接收的Excel文件来导入Excel,并封装成实体类
	 *
	 * @param file
	 *            上传的文件
	 * @param pojoClass
	 *            Excel实体类
	 * @return
	 */
	public static <T> ExcelImportResult<T> importExcel(MultipartFile file, Class<T> pojoClass) {
		if (file == null) {
			return null;
		}
		ExcelImportResult<T> result = null;
		try {
			ImportParams importParams = new ImportParams();
			importParams.setNeedVerfiy(true);
			result = ExcelImportUtil.importExcelMore(file.getInputStream(), pojoClass, importParams);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	private static String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getCellValue(fCell);
				}
			}
		}
		return null;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return
	 */
	private static boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static String getCellValue(Cell cell) {

		if (cell == null)
			return "";

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

			return cell.getStringCellValue();

		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

			return String.valueOf(cell.getBooleanCellValue());

		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

			return cell.getCellFormula();

		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

			return String.valueOf(cell.getNumericCellValue());

		}
		return "";
	}

	/**
	 * 合并单元格获取单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param i
	 * @param cols
	 * @return
	 */
	public static String getValue(Sheet sheet,Row r, int row, int column) {
		String result = null;
		if (isMergedRegion(sheet, row, column)) {
			result = getMergedRegionValue(sheet, row, column);
		} else {
			result = getCellValue(r.getCell(column));
		}
		return result;
	}
	/**
	 * 判断是否为空行
	 * @param row
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isRowEmpty(Row row) {
		if(row == null)
		return true;
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
		}
		return true;
	}
	/**
	 * 拆分合并单元格并赋值
	 * @param sheet
	 */
	@SuppressWarnings("deprecation")
	public static void splitMergedRegion(Sheet sheet) {
		String value = "";
        //遍历sheet中的所有的合并区域
        for (int i = sheet.getNumMergedRegions() - 1; i >= 0; i--) {
            CellRangeAddress region = sheet.getMergedRegion(i);
            Row firstRow = sheet.getRow(region.getFirstRow());
            Cell firstCellOfFirstRow = firstRow.getCell(region.getFirstColumn());
            //如果第一个单元格的是字符串
            if (firstCellOfFirstRow.getCellType() == Cell.CELL_TYPE_STRING) {
                value = firstCellOfFirstRow.getStringCellValue();
            }
            sheet.removeMergedRegion(i);
           //设置第一行的值为，拆分后的每一行的值
            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (region.isInRange(cell.getRowIndex(), cell.getColumnIndex()))
                     {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cell.setCellValue(value);
                    }
                }
            }
    	}
    }
    
    /**
     * @param cell 一个单元格的对象
     * @return 返回该单元格相应的类型的值
     */
    public static String getRightTypeCell(Cell cell){
        cell.setCellType(Cell.CELL_TYPE_STRING);
        return cell.getStringCellValue();
    }
	
}
