<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	//获取当前年份
	Calendar cal = Calendar.getInstance();     //获取当前日期
	int curYear = cal.get(Calendar.YEAR);      //当前年份
	request.setAttribute("curYear", curYear);  //设置当前年份
	List yearList = new ArrayList();  
	//前五年
	for(int i=curYear;i > curYear-5;i--){
		//往集合里面加值
		yearList.add(i);
	}
	
	/* 
	正序 
	for(int i=curYear-4;i <= curYear;i++){
		yearList.add(i);
	} 
	*/
	
	request.setAttribute("yearList", yearList);
	
	
%>


<!DOCTYPE HTML>
<html>
	  <head>
	    	<%@include file="/common/header.jsp"%>
	    	<title>年度投诉统计图</title>
	  </head>
  
	  <!-- 引入fusioncharts.js -->
	  <script type="text/javascript" src="${basePath}js/fusioncharts/fusioncharts.js"></script>
	  
	  <!-- 引入fusioncharts.charts.js -->
	  <script type="text/javascript" src="${basePath}js/fusioncharts/fusioncharts.charts.js"></script>
	  
	  <!-- 引入主题fusioncharts.theme.fint.js -->
	  <script type="text/javascript" src="${basePath}js/fusioncharts/themes/fusioncharts.theme.fint.js"></script>
	  
	  
	  <!-- 初始化(加载值)：从FusionCharts Suite XT的demo中复制 -->
	  <script type="text/javascript">
	  
	  		//加载完dom元素后，立刻执行doAnnualStatistic()方法
	  		$(document).ready(doAnnualStatistic());
	  		
			//根据年份统计投诉数
			function doAnnualStatistic() {
				//1. 获取年份
				var year=$("#year option:selected").val();				
				if(year == "" || year == undefined){
					year = "${curYear}";   //默认当前年份,EL表达式
				}
				
				//2. 根据年份统计(异步：ajax)
				$.ajax({                           
					url:"${basePath}/nsfw/complain_getAnnualStatisticData.action",
					data:{"year":year},
					type:"post",
					dataType:"json",
					success:function(data){
						if(data != null && data != "" && data != undefined){
							//从FusionCharts Suite XT的demo中复制
							var revenueChart = new FusionCharts({
								"type" : "line",
								"renderAt" : "chartContainer",
								"width" : "550",
								"height" : "350",
								"dataFormat" : "json",
								"dataSource" : {
									"chart" : {
										"caption" : year+"年度投诉统计图",
										"xAxisName" : "月  份",
										"yAxisName" : "投诉数",
										"theme" : "fint"
									},
									"data" : data.chartData
								}

							});
							revenueChart.render();
						}else{
							alert("统计投诉失败2！");
						}
					},
					
					error:function(){
						alert("统计投诉失败3！");
					}
					
				});
				
			}
		</script>
	  
	  
	  <body>
		  	<br>
		  		<div style="text-align:center;width: 100%">
		  			<!-- Map集合时无序的，用list集合,页面顶部定义的yearList -->
		    		<s:select id="year" list="#request.yearList" onchange="doAnnualStatistic()"></s:select>
		    	</div>
		    <br>
		    
		    <!-- 图表: 渲染到容器中 -->
		    <div id="chartContainer" style="text-align:center;width: 100%"></div>
	 </body>
</html>
