<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<head>

	<%@include file="/common/header.jsp"%>
    <title>用户管理</title>
    
    <script type="text/javascript">
    
      	//全选、全反选按钮
		function doSelectAll(){
			// jquery 1.6 前
			//$("input[name=selectedRow]").attr("checked", $("#selAll").is(":checked"));
			//prop jquery 1.6+建议使用
			$("input[name=selectedRow]").prop("checked", $("#selAll").is(":checked"));		
		}
		
		//新增按钮
		function doAdd(){
			//document.forms[0]：获取第一个表单
			//document.forms[0].action：表单提交路径; basePath:前缀路径
			//跳到user-struts.xml找到相应action,再找到UserAction中addUI()方法,然后返回结果到user-struts.xml,再进行页面转发
			document.forms[0].action="${basePath}nsfw/user_addUI.action";
			//提交表单
			document.forms[0].submit();
		}
		
		
		//编辑按钮(根据id编辑)
		function doEdit(id){
			//跳到user-struts.xml找到相应action,再找到UserAction中editUI()方法
			document.forms[0].action="${basePath}nsfw/user_editUI.action?user.id="+id;
			//提交表单
			document.forms[0].submit();
		}
		
		
		//删除按钮(根据id删除)
		function doDelete(id){
			
			//跳到user-struts.xml找到相应action,再找到UserAction中delete()方法
			document.forms[0].action="${basePath}nsfw/user_delete.action?user.id="+id;
			//提交表单
			document.forms[0].submit();
		}
		
		
		//批量删除按钮(根据id删除)
		function doDeleteAll(){
			
			//跳到user-struts.xml找到相应action,再找到UserAction中deleteSelected()方法
			document.forms[0].action="${basePath}nsfw/user_deleteSelected.action";
			//提交表单
			document.forms[0].submit();
		}
		
		
		//导出用户列表
		function doExportExcel(){
			//打开一个新窗口，跳转到的地址(方法);
			window.open("${basePath}nsfw/user_exportExcel.action");
		}
		
		//导入用户列表
		function doImportExcel(){
			//跳到user-struts.xml找到相应action,再找到UserAction中importExcel()方法
			document.forms[0].action="${basePath}nsfw/user_importExcel.action";
			//提交表单
			document.forms[0].submit();
		
		}
		
		//定义变量，在分页pageNavigator.jsp中引用
		var list_url = "${basePath}nsfw/user_listUI.action";
		
		//根据信息标题模糊查询(搜索按钮)
		function doSearch(){
			//点击搜索时重置页号
			$("#pageNo").val(1);
			//跳到info-struts.xml找到相应action,再找到UserAction中listUI()方法
			document.forms[0].action = list_url;
			//提交表单
			document.forms[0].submit();
		}
		
    </script>
</head>
<body class="rightBody">
<form name="form1" action="" method="post" enctype="multipart/form-data">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>用户管理</strong></div> </div>
                <div class="search_art">
                    <li>
                        用户名：<s:textfield name="user.name" cssClass="s_text" id="userName"  cssStyle="width:160px;"/>
                    </li>
                    <li><input type="button" class="s_button" value="搜 索" onclick="doSearch()"/></li>
                    <li style="float:right;">
                        <input type="button" value="新增" class="s_button" onclick="doAdd()"/>&nbsp;
                        <input type="button" value="删除" class="s_button" onclick="doDeleteAll()"/>&nbsp;
                        <input type="button" value="导出" class="s_button" onclick="doExportExcel()"/>&nbsp;
                    	<input name="userExcel" type="file"/>
                        <input type="button" value="导入" class="s_button" onclick="doImportExcel()"/>&nbsp;

                    </li>
                </div>

                <div class="t_list" style="margin:0px; border:0px none;">
                    <table width="100%" border="0">
                        <tr class="t_tit">
                            <td width="30" align="center"><input type="checkbox" id="selAll" onclick="doSelectAll()" /></td>
                            <td width="140" align="center">用户名</td>
                            <td width="140" align="center">帐号</td>
                            <td width="160" align="center">所属部门</td>
                            <td width="80" align="center">性别</td>
                            <td align="center">电子邮箱</td>
                            <td width="100" align="center">操作</td>
                        </tr>
                        
                        
                        <!-- OGNL表达式迭代集合,userlist:在栈顶，所以不需要加#号 -->
                        <s:iterator value="pageResult.items" status="st">
                        	<!-- 隔行换色 -->
                            <tr <s:if test="#st.odd">bgcolor="f8f8f8"</s:if>>
                            	<!-- 批量选择:  checkbox：复选框; value:id -->
                                <td align="center"><input type="checkbox" name="selectedRow" value="<s:property value='id'/>" /></td>
                                <td align="center"><s:property value='name'/></td>
                                <td align="center"><s:property value='account'/></td>
                                <td align="center"><s:property value='dept'/></td>
                                <td align="center"><s:property value="gender?'男':'女'"/></td>
                                <td align="center"><s:property value="email"/></td>
                                <td align="center">
                                <!-- 
                                    <a href="javascript:doEdit(id)">编辑</a>
                                    <a href="javascript:doDelete(id)">删除</a>
                                -->
                                
                                	<!-- 根据id编辑和删除:id是字符串，所以括号里面要加'' -->
                                	<a href="javascript:doEdit('<s:property value='id'/>')">编辑</a>
                                    <a href="javascript:doDelete('<s:property value='id'/>')">删除</a>
                                
                                </td>
                            </tr>
                        </s:iterator>
                    </table>
                </div>
            </div>
    		
    		<!-- 引入分页jsp -->
            <jsp:include page="/common/pageNavigator.jsp"></jsp:include>
    		
        </div>
    </div>
</form>

</body>
</html>