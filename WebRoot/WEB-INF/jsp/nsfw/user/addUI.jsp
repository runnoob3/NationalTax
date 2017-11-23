<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>用户管理</title>
    
    <!-- 引入日期组件 -->
    <script type="text/javascript" src="${basePath}/js/datepicker/WdatePicker.js"></script>
    
    <script type="text/javascript">
    	
    	var vResult=false;
    	
    	//ajax效验账号的唯一(1. 当帐号的值发生变化时效验:鼠标离开时)
    	function doVerify(){
    		//1. 获取账号
    		var account=$("#account").val();
    		if(account !=""){
    			//2. 效验    get,post,getJson,ajax
    			$.ajax({
    				url:"${basePath}nsfw/user_verifyAccount.action",   //提交路径
    				data:{"user.account":account},                     //提交的数据类（型：值）
    				type:"post",                //提交方式
    				async:false,                //ajax是异步操作，改为同步才能在调用doVerify()方法效验账号后再执行下面的语句
    				success:function(msg){      //成功后处理方式
    					//如果返回结果不是字符串true,账号已经存在
    					if("true"!=msg){
    						//账号已经存在
    						alert("账号已经存在,请使用其它账号！");
    						
    						//定焦：关闭弹框后跳回原来选中的地方
    						$("#account").focus();
    						//成功状态改为false
    						vResult=false;
    					}else{
    						//成功状态改为true,给下面调用doVerify()方法后调用
    						vResult=true;
    					}
    				}
    			});
    		}
    	}
    	
    	
    	//效验账号的唯一 (2.保存的时候:提交表单)
    	function doSubmit(){
    		//获取用户名
    		var name=$("#name");
    		if(name.val() == ""){
    			alert("用户名不能为空！");
    			//定焦回用户名输入框
    			name.focus();
    			return false;
    		}
    			
    		//获取密码
    		var password=$("#password");
    		if(password.val() == ""){
    			alert("密码不能为空！");
    			//定焦回用户名输入框
    			password.focus();
    			return false;
    		}
    			
    		//账号效验，直接调用上面的doVerify()方法
    		doVerify();
    		//如果true就提交表单
    		if(vResult){
    			//提交表单
    			document.forms[0].submit();
    		}
    	}

    </script>
    
    
</head>
<body class="rightBody">

<form id="form" name="form" action="${basePath}nsfw/user_add.action" method="post" enctype="multipart/form-data">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>用户管理</strong>&nbsp;-&nbsp;新增用户</div></div>
    <div class="tableH2">新增用户</div>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="200px">所属部门：</td>
            <!-- 初始化部门：初始化一个Map集合(要加#号) -->
            <td><s:select name="user.dept" list="#{'部门A':'部门A','部门B':'部门B'}"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">头像：</td>
            <td>
                <input type="file" name="headImg"/>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">用户名：</td>
            <td><s:textfield id="name" name="user.name"/> </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">帐号：</td>
            <!-- id="account":获取账号;;;onchange="doVerify"方法：验证用户的唯一性 -->
            <td><s:textfield id="account" name="user.account" onchange="doVerify()"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">密码：</td>
            <td><s:textfield id="password" name="user.password"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">性别：</td>
            <td><s:radio list="#{'true':'男','false':'女'}" name="user.gender"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">角色：</td>
            <td>
            	<!-- 自动生成多个CheckBox(5个权限的CheckBox);listKey="roleId": 在页面显示的属性,不然会显示整个对象 -->
            	<s:checkboxlist list="rolelist" name="userRoleIds" listKey="roleId" listValue="name"></s:checkboxlist>
            
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">电子邮箱：</td>
            <td><s:textfield name="user.email"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">手机号：</td>
            <td><s:textfield name="user.mobile"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">生日：</td>
            <td><s:textfield id="birthday" name="user.birthday" readonly="true" onfocus="WdatePicker({'skin':'whyGreen','dateFmt':'yyyy-MM-dd'})"/></td>
        </tr>
		<tr>
            <td class="tdBg" width="200px">状态：</td>
            <td><s:radio list="#{'1':'有效','0':'无效'}" name="user.state" value="1"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">备注：</td>
            <td><s:textarea name="user.memo" cols="75" rows="3"/></td>
        </tr>
    </table>
    
    <div class="tc mt20">
    	<!-- 把type="submit"改为type="button",加一个onclick事件  -->
        <input type="button" class="btnB2" value="保存" onclick="doSubmit()"/>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
    </div>
    </div></div></div>
</form>
</body>
</html>