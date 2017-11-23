<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    pageContext.setAttribute("basePath", request.getContextPath()+"/") ;
%>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>我要投诉</title>
    <script type="text/javascript" charset="utf-8" src="${basePath }js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath }js/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${basePath }js/ueditor/lang/zh-cn/zh-cn.js"></script>

    <script>
   		//富文本编辑器
   		window.UEDITOR_HOME_URL = "${basePath }js/ueditor/";
    	var ue = UE.getEditor('editor');
    	
    	//根据部门查询该部门下的用户列表(被投诉人是哪个部门)
    	function doSelectDept(){
    		//1、获取部门(option:selected==获取被选中的部门)
    		var dept = $("#toCompDept option:selected").val();
    		
    		if(dept != ""){
	    		//2、根据部门查询列表
	    		$.ajax({
	    			/*方法1:(没有使用框架) "${basePath}sys/home_getUserJson:.action" */
	    			/*方法2:(使用struts框架) "${basePath}sys/home_getUserJson:.action" */
	    			url:"${basePath}sys/home_getUserJson2.action",
	    			data:{"dept":dept},
	    			type:"post",
	    			dataType:"json",//返回数据类型
	    			success: function(data){
	    				//2.1、将用户列表设置到被投诉人下拉框中
	    				if(data != null && data != "" && data != undefined){
	    					//如果查询成功
	    					if("success" == data.msg){
	    						//切换被投诉人部门后,清空被投诉人姓名
	    						var toCompName = $("#toCompName");
	    						toCompName.empty();
	    						//遍历列表(集合): (data.userList: 迭代的参数；；function(index, user): 函数)
	    						$.each(data.userList, function(index, user){
	    							toCompName.append("<option value='" + user.name + "'>" + user.name + "</option>");
	    						});
	    					} else {alert("获取被投诉人列表失败！");}
	    				} else {
	    					alert("获取被投诉人列表失败！");
	    				}
	    			},
	    			error:function(){alert("获取被投诉人列表失败！");}
	    		});
    		} else {
    			//清空被投诉人列表下拉框
    			$("#toCompName").empty();
    		}
    	}
    	
    	//提交表单，保存投诉信息(Ajax)
    	function doSubmit(){
    		//1、提交表单并保存投诉信息
			$.ajax({
				//提交到系统投诉的添加方法
				url:"${basePath}sys/home_complainAdd.action",
				data:$("#form").serialize(),   //将整个表单数据提交  
				type:"post",
				async:false,                   //把异步改为同步
				success:function(msg){
					if("success" == msg){
						//2、提示用户投诉成功
						alert("投诉成功！");
						//3、刷新父窗口
						window.opener.parent.location.reload(true);
						//4、关闭当前窗口
						window.close();
					}else{
						alert("投诉失败！");
					}
					
				},
				error:function(){
					alert("投诉失败！");
				}
			
			});
			
    	
    	
    	}
    	
    	
    </script>
    
</head>
<body>
<form id="form" name="form" action="" method="post" enctype="multipart/form-data">
    <div class="vp_d_1">
        <div style="width:1%;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
        <div class="vp_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>工作主页</strong>&nbsp;-&nbsp;我要投诉</div></div>
    <div class="tableH2">我要投诉</div>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="250px">投诉标题：</td>
            <td><s:textfield name="comp.compTitle"/></td>
        </tr>
        <tr>
            <td class="tdBg">被投诉人部门：</td>
            <td><s:select id="toCompDept" name="comp.toCompDept" list="#{'':'请选择','部门A':'部门A','部门B':'部门B' }" onchange="doSelectDept()"/></td>
        </tr>
        <tr>
            <td class="tdBg">被投诉人姓名：</td>
            <td>
            	<select id="toCompName" name="comp.toCompName">
					            	
            	</select>
            </td>
        </tr>
        <tr>
            <td class="tdBg">投诉内容：</td>
            <td><s:textarea id="editor" name="comp.compContent" cssStyle="width:90%;height:160px;" /></td>
        </tr>
        <tr>
            <td class="tdBg">是否匿名投诉：</td>
            <!-- 默认匿名投诉 -->
            <td><s:radio name="comp.isNm" list="#{'false':'非匿名投诉','true':'匿名投诉' }" value="true"/></td>
        </tr>
       
    </table>
	
	<!-- 隐藏投诉人单位(值从session中取出来) -->
	<s:hidden name="comp.compCompany" value="%{#session.SYS_USER.dept}"></s:hidden>
	<!-- 隐藏投诉人姓名 -->
	<s:hidden name="comp.compName" value="%{#session.SYS_USER.name}"></s:hidden>
	<!-- 隐藏投诉人手机 -->
	<s:hidden name="comp.compMobile" value="%{#session.SYS_USER.mobile}"></s:hidden>
	
    <div class="tc mt20">
        <input type="button" class="btnB2" value="保存" onclick="doSubmit()"/>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:window.close()" class="btnB2" value="关闭" />
    </div>
    </div></div>
    <div style="width:1%;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
    </div>
</form>
</body>
</html>