<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

		//翻页: 上一页/下一页
		function doGoPage(pageNo){
			//设置pageNo值
			document.getElementById("pageNo").value=pageNo;
			//跳到info-struts.xml找到相应action,再找到UserAction中listUI()方法
			document.forms[0].action=list_url;    //地址不能写死，其它模块还要引用
			//提交表单
			document.forms[0].submit();
		}

</script>
<div class="c_pate" style="margin-top: 5px;">
	<!-- 总条数大于0才显示分页内容 -->
	<s:if test="pageResult.totalCount>0">
		<table width="100%" class="pageDown" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td align="right">总共<s:property value="pageResult.totalCount" />条记录，
					当前第 <s:property value="pageResult.pageNo" /> 页， 共 <s:property
						value="pageResult.totalPageCount" />页 &nbsp;&nbsp; <!-- 限制上一页：当前页号大于1页才有上一页 -->
					<s:if test="pageResult.pageNo>1">
						<a
							href="javascript:doGoPage(<s:property value='pageResult.pageNo-1'/>)">上一页</a>&nbsp;&nbsp;
			                    </s:if> <!-- 显示下一页：当前页号小于最大页才有下一页 --> <s:if
						test="pageResult.pageNo < pageResult.totalPageCount">
						<a
							href="javascript:doGoPage(<s:property value='pageResult.pageNo+1'/>)">下一页</a>
					</s:if> <!-- 回车(Enter):event.keyCode == 13,传pageNo --> 到&nbsp;<input
					id="pageNo" name="pageNo" type="text" style="width: 30px;"
					onkeypress="if(event.keyCode == 13){doGoPage(this.value);}" min="1"
					max="" value="<s:property value='pageResult.pageNo'/>" />
					&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	</s:if>
	<s:else>暂无数据！</s:else>
</div>