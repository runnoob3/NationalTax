package cn.itcast.core.action;

import cn.itcast.core.page.PageResult;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	
	
	//接收复选框的值，进行批量删除(private改为protected)
	protected String[] selectedRow;
	
	//分页的抽取
	protected PageResult pageResult;  //分页结果
	private int pageNo;             //当前页
	private int pageSize;           //页大小
	
	//定义一个常量保存默认每页的大小
	public static int DEFAULT_PAGE_SIZE=3;
	
	
	//getter()/setter()方法
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	public PageResult getPageResult() {
		return pageResult;
	}
	public void setPageResult(PageResult pageResult) {
		this.pageResult = pageResult;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		//设置页大小
		if(pageSize<1){
			pageSize=DEFAULT_PAGE_SIZE;  //每页显示多少条
		}
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
