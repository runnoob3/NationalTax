<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>角色管理</title>
    <script type="text/javascript">
    
    
  	//全选、全反选
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
		//跳到role-struts.xml找到相应action,再找到UserAction中addUI()方法,然后返回结果到role-struts.xml,再进行页面转发
		document.forms[0].action="${basePath}nsfw/role_addUI.action";
		//提交表单
		document.forms[0].submit();
	}
		
		
	//编辑按钮(根据id编辑)
	function doEdit(id){
		//跳到role-struts.xml找到相应action,再找到UserAction中editUI()方法
		document.forms[0].action="${basePath}nsfw/role_editUI.action?role.roleId="+id;
		//提交表单
		document.forms[0].submit();
	}
		
		
	//删除按钮(根据id删除)
	function doDelete(id){
			
		//跳到role-struts.xml找到相应action,再找到UserAction中delete()方法
		document.forms[0].action="${basePath}nsfw/role_delete.action?role.roleId="+id;
		//提交表单
		document.forms[0].submit();
	}
		
		
	//批量删除按钮(根据id删除)
	function doDeleteAll(){
			
		//跳到role-struts.xml找到相应action,再找到UserAction中deleteSelected()方法
		document.forms[0].action="${basePath}nsfw/role_deleteSelected.action";
		//提交表单
		document.forms[0].submit();
	}
	
	//定义变量，在分页pageNavigator.jsp中引用
	var list_url = "${basePath}nsfw/role_listUI.action";
		
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
<form name="form1" action="" method="post">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>角色管理 </strong></div> </div>
                <div class="search_art">
                    <li>
                        角色名称：<s:textfield name="role.name" cssClass="s_text" id="roleName"  cssStyle="width:160px;"/>
                    </li>
                    <li><input type="button" class="s_button" value="搜 索" onclick="doSearch()"/></li>
                    <li style="float:right;">
                        <input type="button" value="新增" class="s_button" onclick="doAdd()"/>&nbsp;
                        <input type="button" value="删除" class="s_button" onclick="doDeleteAll()"/>&nbsp;
                    </li>
                </div>

                <div class="t_list" style="margin:0px; border:0px none;">
                    <table width="100%" border="0">
                        <tr class="t_tit">
                            <td width="30" align="center"><input type="checkbox" id="selAll" onclick="doSelectAll()" /></td>
                            <td width="120" align="center">角色名称</td>
                            <td align="center">权限</td>
                            <td width="80" align="center">状态</td>
                            <td width="120" align="center">操作</td>
                        </tr>
                       	
                       	<!-- 遍历角色信息 -->
                       	<s:iterator value="pageResult.items" status="st">
                       		<!-- 隔行换色 -->
                            <tr <s:if test="#st.odd">bgcolor="f8f8f8"</s:if>>
                            	<!-- ID批量选择:  checkbox：复选框; value:id -->
                                <td align="center"><input type="checkbox" name="selectedRow" value="<s:property value='roleId'/>"/></td>
                                <!-- 名称 -->
                                <td align="center"> <s:property value='name'/> </td>
                                <!-- 权限 -->
                                <td align="center">
                                		<s:iterator value="rolePrivileges">
                                			<!-- Map集合的取值方式:#privilegeMap[id.code] -->
                                			<!-- 取到角色权限的中文 -->
                                			<s:property value="#privilegeMap[id.code]"/>
                                		</s:iterator>
                                </td>
                                <!-- 状态 -->
                                <td align="center"><s:property value="state==1?'有效':'无效'"/></td>
                                <!-- 操作 -->
                                <td align="center">
                                	<!-- 根据id编辑、删除 ('id')-->
                                    <a href="javascript:doEdit('<s:property value='roleId'/>')">编辑</a>
                                    <a href="javascript:doDelete('<s:property value='roleId'/>')">删除</a>
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