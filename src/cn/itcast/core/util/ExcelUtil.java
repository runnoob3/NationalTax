package cn.itcast.core.util;

import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.itcast.nsfw.user.entity.User;

public class ExcelUtil {
		
	    /**
	     * 导出用户列表到Excel的工具类:public static(加static调用就不需要实例化)
	     * @param userlist        (用户列表)
	     * @param outputStream    (输出流)
	     */
		public static void exportExcel(List<User> userlist,ServletOutputStream outputStream) {
			
			try {
				//1、创建工作簿
				HSSFWorkbook workbook=new HSSFWorkbook();
				
				//1.1、创建合并单元格对象,合并第一行第一列到第五列
				CellRangeAddress cellRangeAddress= new CellRangeAddress(0, 0, 0, 4);
				
				//1.2、头标题样式
				//样式由工作簿创建，调用createCellStyle()方法，所以传进workbook让工作簿创建它，设置头标题字体大小为16
				HSSFCellStyle style1=createCellStyle(workbook,(short) 16);
				
				/*
				抽取出去作为公共类
				HSSFCellStyle style=workbook.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);              //设置样式，水平居中:ALIGN_CENTER
				style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);   //设置样式，垂直居中:VERTICAL_CENTER
				//1.2.1  创建字体
				HSSFFont font=workbook.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);      //设置加粗字体
				font.setFontHeightInPoints((short) 16);            //设置字体大小
				//1.2.2  加载字体到样式中
				style.setFont(font);
				*/
				
				//1.3、列标题样式
				//样式由工作簿创建，调用createCellStyle()方法，所以传进workbook让工作簿创建它，设置头标题字体大小为13
				HSSFCellStyle style2=createCellStyle(workbook,(short) 13);
				
				//2、创建工作表
				HSSFSheet sheet=workbook.createSheet("用户列表");    //指定工作表名为: 用户列表
				//2.1、加载合并单元格对象
				sheet.addMergedRegion(cellRangeAddress);
				//设置默认列宽(设置Excel表的单元格列宽为15)
				sheet.setDefaultColumnWidth(15);
				
				//3、创建行
				//3.1、创建头标题行；并且设置头标题
				HSSFRow row1=sheet.createRow(0);          //创建第1行,index从0开始
				//创建单元格,因为头标题已经合并，只能创建第1列
				HSSFCell cell1=row1.createCell(0);         //创建第1列,index从0开始
				//加载单元格样式(头标题:style1)
				cell1.setCellStyle(style1);
				//设置单元格的值
				cell1.setCellValue("用户列表");              //单元格显示"用户列表"
				
				
				//3.2、创建列标题行；并且设置列标题
				HSSFRow row2=sheet.createRow(1);          //创建第2行,index从0开始
				//创建数组保存列标题
				String[] title={"用户名","账号","所属部门","性别","电子邮箱"};
				//迭代
				for(int i=0;i<title.length;i++){
					//创建单元格,因为头标题已经合并，只能创建第1列
					HSSFCell cell2=row2.createCell(i);        //创建第i列,index从0开始
					//加载单元格样式(头标题:style2)
					cell2.setCellStyle(style2);
					//设置单元格的值
					cell2.setCellValue(title[i]);               //第i列显示"用户列表"
				}
				
				//4、操作单元格；将用户列表写入excel
				if(userlist !=null){
					
					//迭代用户列表
					for(int j=0;j<userlist.size();j++){
						//创建数据行,前面两行是标题行，已经创建，从第三行(j+2)开始
						HSSFRow row=sheet.createRow(j+2);
						
						//创建单元格:第1列:用户名
						HSSFCell cell11=row.createCell(0);
						//设置单元格的值(获取第几个，获取用户名)
						cell11.setCellValue(userlist.get(j).getName());
						
						//创建单元格:第2列:账号
						HSSFCell cell12=row.createCell(1);
						cell12.setCellValue(userlist.get(j).getAccount());
						
						//创建单元格:第3列:所属部门
						HSSFCell cell13=row.createCell(2);
						cell13.setCellValue(userlist.get(j).getDept());
						
						//创建单元格:第4列:性别
						HSSFCell cell14=row.createCell(3);
						cell14.setCellValue(userlist.get(j).isGender()?"男":"女");
						
						//创建单元格:第5列:电子邮箱
						HSSFCell cell15=row.createCell(4);
						cell15.setCellValue(userlist.get(j).getEmail());
					}
				}
				//5、输出
				workbook.write(outputStream);
				workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		/**
		 * 创建单元格样式公共类:将样式抽取出来,多个的时候方便调用
		 * @param workbook   工作簿
		 * @param fontSize   字体大小
		 * @return
		 */
		private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook,short fontSize) {

			HSSFCellStyle style=workbook.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);              //设置样式，水平居中:ALIGN_CENTER
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);   //设置样式，垂直居中:VERTICAL_CENTER
			//1.2.1  创建字体
			HSSFFont font=workbook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);      //设置加粗字体
			font.setFontHeightInPoints(fontSize);            //设置字体大小
			//1.2.2  加载字体到样式中
			style.setFont(font);
			
			//返回样式
			return style;
		}
}
