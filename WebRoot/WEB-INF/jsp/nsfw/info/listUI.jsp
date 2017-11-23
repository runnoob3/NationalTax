<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>信息发布管理</title>
	<script type="text/javascript">
		//全选、全反选
		function doSelectAll() {
			// jquery 1.6 前
			//$("input[name=selectedRow]").attr("checked", $("#selAll").is(":checked"));
			//prop jquery 1.6+建议使用
			$("input[name=selectedRow]").prop("checked",
					$("#selAll").is(":checked"));
		}
	
		//新增按钮
		function doAdd() {
			//document.forms[0]：获取第一个表单
			//document.forms[0].action：表单提交路径; basePath:前缀路径
			//跳到info-struts.xml找到相应action,再找到UserAction中addUI()方法,然后返回结果到info-struts.xml,再进行页面转发
			document.forms[0].action = "${basePath}nsfw/info_addUI.action";
			//提交表单
			document.forms[0].submit();
		}
	
		//编辑按钮(根据id编辑)
		function doEdit(id) {
			//跳到info-struts.xml找到相应action,再找到UserAction中editUI()方法
			document.forms[0].action = "${basePath}nsfw/info_editUI.action?info.infoId=" + id;
			//提交表单
			document.forms[0].submit();
		}
	
		//删除按钮(根据id删除)
		function doDelete(id) {
	
			//跳到info-struts.xml找到相应action,再找到UserAction中delete()方法
			document.forms[0].action = "${basePath}nsfw/info_delete.action?info.infoId="+ id;
			//提交表单
			document.forms[0].submit();
		}
	
		//批量删除按钮(根据id删除)
		function doDeleteAll() {
	
			//跳到info-struts.xml找到相应action,再找到UserAction中deleteSelected()方法
			document.forms[0].action = "${basePath}nsfw/info_deleteSelected.action";
			//提交表单
			document.forms[0].submit();
		}
		
		//禁止&&停用按钮
		//ajax异步发布信息，传入信息id&&将要改成的信息状态
		function doPublic(infoId,state){
			//1. 更新信息状态
			$.ajax({
				url:"${basePath}nsfw/info_publicInfo.action",   //地址
				data:{"info.infoId":infoId,"info.state":state},  //数据
				type:"post",     //提交方式
				success:function(msg){
					//2. 如果返回成功，更新状态栏、操作栏的显示值
					if("更新状态成功"==msg){
						if(state==1){  //发布: 说明信息状态已经改成发布，状态栏显示发布，操作栏显示停用
						
							//获取发布/停用td标签，传入信息id:infoId,因为状态的id不唯一，无法指定修改哪一行的状态
							$("#show_"+infoId).html("发布");       //状态栏,#show_infoId:标识id唯一性
							//字符拼接
							$("#operator_"+infoId).html('<a href="javascript:doPublic(\''+infoId+'\',0)">停用</a>');   //操作栏,#operator_infoId:标识id唯一性
						}else{         //停用: 说明信息状态已经改成停用，状态栏显示停用，操作栏显示发布
						
							//获取发布/停用td标签，传入信息id:infoId,因为状态的id不唯一，无法指定修改哪一行的状态
							$("#show_"+infoId).html("停用");       //状态栏,#show_infoId:标识id唯一性
							//字符拼接,操作栏,#operator_infoId:标识id唯一性
							$("#operator_"+infoId).html('<a href="javascript:doPublic(\''+infoId+'\',1)">发布</a>');
							
						}
					}else{
						alert("更新状态失败！");
					}
				},
				
				error:function(){
					alert("更新信息状态失败！");
				}
			
			});
		}
		
		//定义变量，在分页pageNavigator.jsp中引用
		var list_url = "${basePath}nsfw/info_listUI.action";
		
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
                <div class="c_crumbs"><div><b></b><strong>信息发布管理</strong></div> </div>
                <div class="search_art">
                    <li>
                        	信息标题：<s:textfield name="info.title" cssClass="s_text" id="infoTitle"  cssStyle="width:160px;"/>
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
                            <td align="center">信息标题</td>
                            <td width="120" align="center">信息分类</td>
                            <td width="120" align="center">创建人</td>
                            <td width="140" align="center">创建时间</td>
                            <td width="80" align="center">状态</td>
                            <td width="120" align="center">操作</td>
                        </tr>
                        <s:iterator value="pageResult.items" status="st">
                            <tr <s:if test="#st.odd"> bgcolor="f8f8f8" </s:if> >
                                <td align="center"><input type="checkbox" name="selectedRow" value="<s:property value='infoId'/>"/></td>
                                <td align="center"><s:property value="title"/></td>
                                <td align="center">
                                	<s:property value="#infoTypeMap[type]"/>	
                                </td>
                                <td align="center"><s:property value="creator"/></td>
                                <td align="center"><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                                
                                <!-- 状态栏: 传入信息id:infoId,因为状态(state)id经过iterator遍历后不唯一 -->
                                <td id="show_<s:property value='infoId'/>" align="center"><s:property value="state==1?'发布':'停用'"/></td>
                                
                                <td align="center">
                                	
                                	<span  id="operator_<s:property value='infoId'/>">
	                                	<!-- 操作栏：停用&&发布 -->
	                                	<s:if test="state==1">
	                                		<a href="javascript:doPublic('<s:property value='infoId'/>',0)">停用</a>
	                                	</s:if>
	                                	<s:else>
	                                		<a href="javascript:doPublic('<s:property value='infoId'/>',1)">发布</a>
	                                	</s:else>
                                	</span>
                                	
                                    <a href="javascript:doEdit('<s:property value='infoId'/>')">编辑</a>
                                    <a href="javascript:doDelete('<s:property value='infoId'/>')">删除</a>
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