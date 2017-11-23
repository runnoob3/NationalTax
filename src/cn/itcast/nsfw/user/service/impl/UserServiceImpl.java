package cn.itcast.nsfw.user.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import cn.itcast.core.exception.ServiceException;
import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.core.util.ExcelUtil;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.user.dao.UserDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.entity.UserRoleId;
import cn.itcast.nsfw.user.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	
	/*
	 * 1. 注入userDao调用,抽取service后不用这种方法
	 * @Resource
	 * private UserDao userDao;  创建UserDao对象调用方法
	 */
	
	
	/**
	 * 2. 通过set()方法注入userDao
	 * @param infoDao
	 */
	private UserDao userDao;
	
	@Resource
	public void setUserDao(UserDao userDao) {
		//注入的同时把userDao设置给BaseDao
		super.setBaseDao(userDao);
		
		this.userDao = userDao;
	}
	
	//基础的增删改查(出了重写的方法)都继承公共接口BaseService<T>
	
	
	//删除的方法
	public void delete(Serializable id) {
		
		userDao.delete(id);
		//删除用户对应的权限
		userDao.deleteUserRoleByUserId(id);
	}
	
	
	//导出用户列表的方法
	public void exportExcel(List<User> userlist,ServletOutputStream outputStream) {
		
		//调用工具类的exportExcel()方法，已经抽取到工具类中
		ExcelUtil.exportExcel(userlist, outputStream);
	}
	
	
	//导入用户列表的方法
	public void importExcel(File userExcel, String userExcelFileName) {
		
		try {
			//创建文件输入流对象，获取文件路径
			FileInputStream fileInputStream=new FileInputStream(userExcel);
			
			//标识是否是03版本的Excel文件
			boolean is03Excel=userExcelFileName.matches("^.+\\.(?i)(xls)$");
			
			//1、读取工作簿,用Workbook接口(HSSFWorkbook 和 XSSFWorkbook公共接口)接收，判断Excel是什么版本
			Workbook workbook=is03Excel?new HSSFWorkbook(fileInputStream):new XSSFWorkbook(fileInputStream);
			
			//2、读取工作表,用Sheet接口接收(HSSFSheet和 XSSFSheet公共接口)
			Sheet sheet=workbook.getSheetAt(0);      //索引: 读取第一个工作表 (Hello world)
			
			//3、读取行,用Row接口接收(HSSFRow 和 XSSFRow公共接口)
			//判断行数是否大于2
			if(sheet.getPhysicalNumberOfRows()>0){
				
				User user=null;   //创建user对象设置值
				
				//k=2,从第三行开始
				for(int k=2;k<sheet.getPhysicalNumberOfRows();k++){
					//4.1、读取行
					Row row=sheet.getRow(k);                //读取第k行(前面两行的标题不需要读取),index从0开始
					user=new User();
					//4.2、获取单元格
					//(1).用户名
					Cell cell0=row.getCell(0);                    //读取第1列(index从0开始)
					user.setName(cell0.getStringCellValue());     //获取值，设置值        
					//(2).账号
					Cell cell1=row.getCell(1);                    //读取第2列(index从0开始)
					user.setAccount(cell1.getStringCellValue());     //获取值，设置值        
					//(3).所属部门
					Cell cell2=row.getCell(2);                    //读取第3列(index从0开始)
					user.setDept(cell2.getStringCellValue());     //获取值，设置值        
					//(4).性别
					Cell cell3=row.getCell(3);                                //读取第4列(index从0开始)
					//布尔值，如果true就是男，false就是女
					user.setGender(cell3.getStringCellValue().equals("男"));   //获取值，设置值        
					//(5).手机号
					String mobile="";
					Cell cell4=row.getCell(4);                    //读取第5列(index从0开始)
					try {
						//字符串: "158..."
						mobile=cell4.getStringCellValue();
					} catch (Exception e) {
						//数字: 158...
						double dMobile=cell4.getNumericCellValue();
						//转为字符串
						mobile=BigDecimal.valueOf(dMobile).toString();
					}
					//设置mobile
					user.setMobile(mobile);
					
					//(6).邮箱
					Cell cell5=row.getCell(5);                        //读取第6列(index从0开始)
					user.setEmail(cell5.getStringCellValue());       //获取值，设置值        
					//(7).生日
					Cell cell6=row.getCell(6);                         //读取第7列(index从0开始)
					if(cell6.getDateCellValue() !=null){
						user.setBirthday(cell6.getDateCellValue());    //获取值，设置值 
					}
					
					//(8).设置默认密码为:123456
					user.setPassword("123456");
					//(9).设置用户状态为有效
					user.setState(User.USER_STATE_VALID);
					
					//5、保存用户
					save(user);
				}
			}
			//关闭资源
			workbook.close();
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	//根据账号和用户id查询用户，返回用户列表
	public List<User> findUserByAccountAndId(String id, String account) {
		
		return userDao.findUserByAccountAndId(id, account);
	}

	//保存用户的同时保存角色的方法(保存一个id的集合)
	public void saveUserAndRole(User user, String... roleIds) {
		
		//1.保存用户
		save(user);
		//2.保存用户对应的角色
		if(roleIds !=null){
			
			for(String roleId:roleIds){
				//保存角色id,保存在中间表:用户角色表;;new UserRoleId():主键
				userDao.saveUserRole(new UserRole(new UserRoleId(new Role(roleId),user.getId())));
			}
		}
	}

	//更新用户的同时更新角色的方法
	public void updateUserAndRole(User user, String... roleIds) {
		
		//1.根据用户删除该用户的所有角色(清除历史记录)
		userDao.deleteUserRoleByUserId(user.getId());
		
		//2.更新用户
		update(user);
		//3.保存用户对应的角色
		if(roleIds !=null){
			for(String roleId:roleIds){
			//保存角色id,拨错在中间表:用户角色表;;new UserRoleId():主键
				userDao.saveUserRole(new UserRole(new UserRoleId(new Role(roleId),user.getId())));
			}
		}
		
	}

	//根据用户id获取该用户对应的所有用户角色
	public List<UserRole> getUserRolesByUserId(String id) {
		
		return userDao.getUserRolesByUserId(id);
	}

	
	//根据用户的账号和密码查询用户列表--登录
	public List<User> findUserByAccountAndPassword(String account,String password) {
		
		return userDao.findUserByAccountAndPassword(account, password);
	}
	
	
}
