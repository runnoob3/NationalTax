package cn.itcast.test;


import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class TestPOIToExcel {
	
	/**
	 * 1. 测试写入2003版本的Excel文件
	 * @throws Exception
	 */
	@Test
	public void testWriteVersion03Excel() throws Exception {
		
		//1、创建工作簿
		HSSFWorkbook workbook=new HSSFWorkbook();
		//2、创建工作表
		HSSFSheet sheet=workbook.createSheet("Hello world");    //指定工作表名为: Hello world
		//3、创建行
		HSSFRow row=sheet.createRow(2);          //创建第三行,index从0开始
		//4、创建单元格
		HSSFCell cell=row.createCell(2);        //创建第三列(index从0开始):第三行第三列
		
		//写入，设置单元格内容
		cell.setCellValue("单元格内容");
		
		//输出到硬盘,指定输出路径+文件名
		FileOutputStream outputStream=new FileOutputStream("G:\\Struts2\\log\\测试POI.xls");
		
		//把Excel输出到具体的地址
		workbook.write(outputStream);
		
		//关闭资源
		workbook.close();
		outputStream.close();
		
	}
	
	
	/**
	 * 2. 测试读取2003版本Excel文件
	 * @throws Exception
	 */
	@Test
	public void testReadVersion03Excel() throws Exception {
		
		//创建输入流对象，获取文件路径
		FileInputStream inputStream=new FileInputStream("G:\\Struts2\\log\\测试POI.xls");
		
		//1、读取工作簿
		HSSFWorkbook workbook=new HSSFWorkbook(inputStream);
		//2、读取工作表
		HSSFSheet sheet=workbook.getSheetAt(0);     //索引: 读取第一个工作表 (Hello world)
		//3、读取行
		HSSFRow row=sheet.getRow(2);             //读取第三行,index从0开始
		//4、读取单元格
		HSSFCell cell=row.getCell(2);            //读取第三列(index从0开始):第三行第三列
		
		//输出,获取单元格内容：cell.getStringCellValue()
		System.out.println("第三行第三列单元格的内容为: "+cell.getStringCellValue());
		
		//关闭资源
		workbook.close();
		inputStream.close();
		
	}
	
	
	/**
	 * 3. 测试写入2007版本的Excel文件
	 * @throws Exception
	 */
	@Test
	public void testWriteVersion07Excel() throws Exception {
		
		//1、创建工作簿
		XSSFWorkbook workbook=new XSSFWorkbook();
		//2、创建工作表
		XSSFSheet sheet=workbook.createSheet("Hello world");    //指定工作表名为: Hello world
		//3、创建行
		XSSFRow row=sheet.createRow(2);          //创建第三行,index从0开始
		//4、创建单元格
		XSSFCell cell=row.createCell(2);        //创建第三列(index从0开始):第三行第三列
		
		//写入，设置单元格内容
		cell.setCellValue("单元格内容");
		
		//输出到硬盘,指定输出路径+文件名
		FileOutputStream outputStream=new FileOutputStream("G:\\Struts2\\log\\测试POI.xlsx");
		
		//把Excel输出到具体的地址
		workbook.write(outputStream);
		
		//关闭资源
		workbook.close();
		outputStream.close();
		
	}
	
	
	/**
	 * 4. 测试读取2007版本Excel文件
	 * @throws Exception
	 */
	@Test
	public void testReadVersion07Excel() throws Exception {
		
		//创建输入流对象，获取文件路径
		FileInputStream inputStream=new FileInputStream("G:\\Struts2\\log\\测试POI.xlsx");
		
		//1、读取工作簿
		XSSFWorkbook workbook=new XSSFWorkbook(inputStream);
		//2、读取工作表
		XSSFSheet sheet=workbook.getSheetAt(0);     //索引: 读取第一个工作表 (Hello world)
		//3、读取行
		XSSFRow row=sheet.getRow(2);             //读取第三行,index从0开始
		//4、读取单元格
		XSSFCell cell=row.getCell(2);            //读取第三列(index从0开始):第三行第三列
		
		//输出,获取单元格内容：cell.getStringCellValue()
		System.out.println("第三行第三列单元格的内容为: "+cell.getStringCellValue());
		
		//关闭资源
		workbook.close();
		inputStream.close();
		
	}
	
	
	/**
	 * 5. 测试同时读取2003和2007版本Excel文件
	 * 	  测试时只需修改测试POI.xls||测试POI.xlsx即可
	 * @throws Exception
	 */
	@Test
	public void testReadVersion03And07Excel() throws Exception {
		
		String fileName="G:\\Struts2\\log\\测试POI.xls";
		
		//判断是否是Excel文件，正则表达式:^：开始; .+:任意的文件名称和路径; \\.:后缀名必须是.xxx; (?i):不区分大小写; (xls):文件后缀; $:结束
		if(fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){  
			
			//创建输入流对象，获取文件路径
			FileInputStream inputStream=new FileInputStream(fileName);
			
			//标识是否是03版本的Excel文件
			boolean is03Excel=fileName.matches("^.+\\.(?i)(xls)$");
			
			//1、读取工作簿,用Workbook接口(HSSFWorkbook 和 XSSFWorkbook公共接口)接收，判断Excel是什么版本
			Workbook workbook=is03Excel?new HSSFWorkbook(inputStream):new XSSFWorkbook(inputStream);
			//2、读取工作表,用Sheet接口接收(HSSFSheet和 XSSFSheet公共接口)
			Sheet sheet=workbook.getSheetAt(0);     //索引: 读取第一个工作表 (Hello world)
			//3、读取行,用Row接口接收(HSSFRow 和 XSSFRow公共接口)
			Row row=sheet.getRow(2);                //读取第三行,index从0开始
			//4、读取单元格,用Cell接口接收(HSSFCell和 XSSFCell公共接口)
			Cell cell=row.getCell(2);               //读取第三列(index从0开始):第三行第三列
			
			//输出,获取单元格内容：cell.getStringCellValue()
			System.out.println("第三行第三列单元格的内容为: "+cell.getStringCellValue());
			
			//关闭资源
			workbook.close();
			inputStream.close();
		}
	}
	
	
	/**
	 * 6. 测试写入Excel文件的样式(合并单元格)
	 * @throws Exception
	 */
	@Test
	public void testExcelStyle() throws Exception {
		
		//1、创建工作簿
		HSSFWorkbook workbook=new HSSFWorkbook();
		//1.1  创建合并单元格对象，合并第三行的第三列到第五列
		CellRangeAddress cellRangeAddress= new CellRangeAddress(2, 2, 2, 4);
		//1.2 创建单元格样式
		HSSFCellStyle style=workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);              //设置样式，水平居中:ALIGN_CENTER
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);   //设置样式，垂直居中:VERTICAL_CENTER
		//1.3  创建字体
		HSSFFont font=workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);      //设置加粗字体
		font.setFontHeightInPoints((short) 16);            //设置字体大小
		//加载字体到样式中
		style.setFont(font);
		
		//单元格背景(黄色，后面要加.index)
		//(1).设置背景填充模式(砖石模式)
		//style.setFillPattern(HSSFCellStyle.DIAMONDS);
		
		//(2).设置背景填充模式(以前景颜色为准)
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//设置填充背景颜色(黄色)
		style.setFillBackgroundColor(HSSFColor.YELLOW.index);
		//设置前景颜色(红色)
		style.setFillForegroundColor(HSSFColor.RED.index);
		
		//2、创建工作表
		HSSFSheet sheet=workbook.createSheet("Hello world");    //指定工作表名为: Hello world
		//2.1 加载合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);
		
		//3、创建行
		HSSFRow row=sheet.createRow(2);          //创建第三行,index从0开始
		//4、创建单元格
		HSSFCell cell=row.createCell(2);         //创建第三列(index从0开始):第三行第三列
		
		//加载样式
		//(1).写入，设置单元格内容
		cell.setCellValue("单元格内容合并");
		//(2).设置单元格样式
		cell.setCellStyle(style);
		
		//输出到硬盘,指定输出路径+文件名
		FileOutputStream outputStream=new FileOutputStream("G:\\Struts2\\log\\测试POI.xls");
		
		//把Excel输出到具体的地址
		workbook.write(outputStream);
		
		//关闭资源
		workbook.close();
		outputStream.close();
		
	}

}
