<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FrontEnd</title>
<style type="text/css">
.page {
	display: inline-block;
	padding-left: 10px;
}
</style>
<script type="text/javascript">
	function senfront(){
		
		if(Session["carGoods"]==null){
			alert("請加入商品");
			return false;
		}else{
			
			document.action.submit();
		}
		
	}
		function addCartGoods(goodsID, buyQuantityIdx){
			
			console.log("goodsID:", goodsID);			
			var buyQuantity = document.getElementsByName("buyQuantity")[buyQuantityIdx].value;
			console.log("buyQuantity:", buyQuantity);
			const formData = new FormData();
			formData.append('action', 'addCartGoods');
			formData.append('goodsID', goodsID);
			formData.append('buyQuantity', buyQuantity);
			// 送出商品加入購物車請求
			const request = new XMLHttpRequest();      /* const(區塊變數宣告))*/
			request.open("POST", "MemberAction.do", true);			
			request.send(formData);
			request.onreadystatechange = function() {
		        if (this.readyState == 4 && this.status == 200) {
		            const response = request.responseText;		            
		            const responseJson = JSON.parse(response);		            
		            alert(JSON.stringify(responseJson, null, 3));
		      };
		   }
		}
		function queryCartGoods(){
			const formData = new FormData();
			formData.append('action', 'queryCartGoods');
			// 送出查詢購物車商品請求
			const request = new XMLHttpRequest();
			request.open("POST", "MemberAction.do");			
			request.send(formData);
			equest.onreadystatechange = function() {
		        if (this.readyState == 4 && this.status == 200) {
		            const response = request.responseText;		            
		            const responseJson = JSON.parse(response);		            
		            alert(JSON.stringify(responseJson, null, 3));
		      };
		   }

		}
		function clearCartGoods(){
			const formData = new FormData();
			formData.append('action', 'clearCartGoods');
			// 送出清空購物車商品請求
			const request = new XMLHttpRequest();
			request.open("POST", "MemberAction.do");			
			request.send(formData);			
		}
	</script>
</head>
<body align="center">
	<table width="1000" height="400" align="center">
		<tr>
			<td colspan="2" align="right">
				<button onclick="queryCartGoods()">購物車商品列表</button>
				<button onclick="clearCartGoods()">清空購物車</button>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				<form action="FrontendAction.do" method="get">
					<input type="hidden" name="action" value="searchGoods" /> <input
						type="hidden" name="pageNo" value="1" /> <input type="text"
						name="searchKeyword" /> <input type="submit" value="商品查詢" />
				</form>
			</td>
		</tr>
		<tr>
			<td width="400" height="200" align="center"><img border="0"
				src="DrinksImage/coffee.jpg" width="200" height="200">
				<h1>歡迎光臨，Tomcat！</h1> 
				<a href="BackendAction.do?action=queryGoods"
				align="left">後臺頁面</a>&nbsp; &nbsp; <a
				href="LoginAction.do?action=logout" align="left">登出</a> <br />
			<br />
				<form action="FrontendAction.do" method="post">
					<input type="hidden" name="action" value="buyGoods" /> 
					<font
						face="微軟正黑體" size="4"> <b>投入:</b> <input type="number"
						name="inputMoney" max="100000" min="0" size="5" value="0">
						<b>元</b> <b><input type="submit" value="送出">
						<br />
						</font>
				</form>
				<div style="border-width: 3px; border-style: dashed; border-color: #FFAC55; padding: 5px; width: 300px;">
					<p style="color: blue;">~~~~~~~ 消費明細 ~~~~~~~</p>
					<p style="margin: 10px;">投入金額：${buygoodsrtn.payprice}</p>
					<p style="margin: 10px;">購買金額：${buygoodsrtn.totalsprice}</p>
					<p style="margin: 10px;">找零金額：${buygoodsrtn.returnprice}</p>
					<p style="margin: 10px;">物品明細:${buygoodsrtn.goodsinf}</p>
				</div>
				</td>			
			<td width="600" height="400">
				<table border="1" style="border-collapse: collapse">
					<tbody>
					<tr>
						<c:forEach items="${pagesearch}" var="pagegoods" varStatus="status">
								<td width="300"> 
								<font face="微軟正黑體" size="5"> 
								<!-- 例如: 可口可樂 30元/罐 -->
								${pagegoods.goodsName}
								</font> 
								<br/> 
								<font face="微軟正黑體" size="4" style="color: gray;">
								<!-- 例如: 可口可樂 30元/罐 -->
								${pagegoods.goodsPrice} 元/罐
								</font> 
								<br /> 
								<!-- 各商品圖片 --> 
								<img border="0" src="DrinksImage/${pagegoods.goodsImageName}" width="150" height="150"> 
									<br /> 
									<font face="微軟正黑體" size="3">
										<input type="hidden" name="goodsID"
										value="${pagegoods.goodsID}"> 購買<input type="number"
										name="buyQuantity" min="0" max="30" size="5" value="0">罐
										<!-- 設定最多不能買大於庫存數量 --> <br>
									<br>
									<button onclick="addCartGoods(${pagegoods.goodsID},${pagesearch.indexOf(pagegoods)})">加入購物車</button>
										<br>											<!--${status.index} 需設varStatus -->
									<p style="color: red;">(庫存 ${pagegoods.goodsQuantity} 罐)</p> <!-- 顯示庫存數量 -->
								</font>
								</td>
								</c:forEach>
							</tr>						
					</tbody>
				</table>
			</td>
			</tr>

		<tr>
			<c:forEach items="${pages}" var="pages">
				<td colspan="2" align="right">
				<c:url value="/FrontendAction.do" var="page">
				<c:param name="action" value="searchGoods"/>
				<c:param name="searchKeyword" value="${ searchKeyword}"/>
				<c:param name="pageNo" value="${pages}"/>
				</c:url>
					<h3 class="page"> <a href="${page}">${pages} </a> </h3>
				</td>
			</c:forEach>
		</tr>

	</table>
</body>
</html>