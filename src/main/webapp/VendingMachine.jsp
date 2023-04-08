<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:url value="/" var="WEB_PATH"/>
<c:url value="/js" var="JS_PATH"/>
<c:url value="/css" var="CSS_PATH"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="${CSS_PATH}/bootstrap.min.css">
<script src="${JS_PATH}/jquery-3.2.1.min.js" ></script>
<script src="${JS_PATH}/popper.min.js" ></script>
<script src="${JS_PATH}/bootstrap.min.js" ></script>
<script src="${JS_PATH}/jquery-1.11.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>FrontEnd</title>
<script type="text/javascript">

$(document).ready(function(){
	
	$('#btnID').click(function() {
		$('#showmsg').toggle("slow");
	})
});
		function addCartGoods(goodsID, buyQuantityIdx){					
			var buyQuantity = document.getElementsByName("buyQuantity")[buyQuantityIdx].value;
			if(buyQuantity!=0){
			console.log("goodsID:", goodsID);
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
			}else{
				alert('請輸入購買數量');
			}
		}
		function queryCartGoods(){
			const formData = new FormData();
			formData.append('action', 'queryCartGoods');
			// 送出查詢購物車商品請求
			const request = new XMLHttpRequest();
			request.open("POST", "MemberAction.do");			
			request.send(formData);
			request.onreadystatechange = function() {
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
			alert('購物車已清空');
		}
	
	</script>
</head>
<body align="center">

	<table width="1000" height="400" align="center" >
		<tr>
			<td colspan="2" align="right">
				<button onclick="queryCartGoods()">購物車商品列表</button>
				<button onclick="clearCartGoods()">清空購物車</button>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				<form action="FrontendAction.do" method="get">
					<input type="hidden" name="action" value="searchGoods" /> 
					<input type="hidden" name="pageNo" value="1" /> 
					<input type="text" name="searchKeyword" value="${pages.searchKeyword}"/> <input type="submit" value="商品查詢" />
				</form>
			</td>
		</tr>
		<tr>
			<td width="400" height="200" align="center">
			<img border="0" src="DrinksImage/coffee.jpg" width="200" height="200">
				<h1>歡迎光臨，${member.customerName}!</h1></br> 
				<h3>${frontMsg}</h3>
				<% session.removeAttribute("frontMsg"); %>
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
						</font>
				</form>
					<input  type="button" id="btnID" value="ShowList"/>
				<div id="showmsg" style="border-width: 3px; border-style: dashed; border-color: #FFAC55; padding: 5px; width: 300px;">
					<p style="color: blue;">~~~~~~~ 消費明細 ~~~~~~~</p>
					<p style="margin: 10px;">投入金額：${buyRtn.payprice}</p>
					<p style="margin: 10px;">購買金額：${buyRtn.totalsprice}</p>
					<p style="margin: 10px;">找零金額：${buyRtn.returnprice}</p>
					<c:forEach items="${buyRtn.shoppingCartGoods}" var="goodsinfo" >   
					<p style="margin: 10px;">物品明細:</br>
					商品名稱:${goodsinfo.goodsName}</br>
					商品金額:${goodsinfo.goodsPrice}</br>
					商品數量:${goodsinfo.buyQuantity}</br>
					</p>
					</c:forEach>					
				</div>	
				</td>					
			<td width="600" height="400" align="center">
				<table border="1" style="border-collapse: collapse">
					<tbody>
					<tr>
						<c:forEach items="${goodsList}" var="good" varStatus="status" end="2">
								<td width="300"> 
								<font face="微軟正黑體" size="5"> 
								<!-- 例如: 可口可樂 30元/罐 -->
								${good.goodsName}
								</font> 
								<br/> 
								<font face="微軟正黑體" size="4" style="color: gray;">
								<!-- 例如: 可口可樂 30元/罐 -->
								${good.goodsPrice} 元/罐
								</font> 
								<br /> 
								<!-- 各商品圖片 --> 
								<img border="0" src="DrinksImage/${good.goodsImageName}" width="150" height="150" > 
									<br /> 
									<font face="微軟正黑體" size="3">
										<input type="hidden" name="goodsID"
										value="${good.goodsID}"> 購買<input type="number"
										name="buyQuantity" min="0" max="30" size="5" value="0">罐
										<!-- 設定最多不能買大於庫存數量 --> <br>
									<br>
									<button onclick="addCartGoods(${good.goodsID},${goodsList.indexOf(good)})">加入購物車</button>
										<br>											<!--${status.index} 需設varStatus -->
									<p style="color: red;">(庫存 ${good.goodsQuantity} 罐)</p> <!-- 顯示庫存數量 -->
								</font>
								</td>
								</c:forEach>
							</tr>	
							<tr>
						<c:forEach items="${goodsList}" var="good" varStatus="status" begin="3">
								<td width="300"> 
								<font face="微軟正黑體" size="5"> 
								<!-- 例如: 可口可樂 30元/罐 -->
								${good.goodsName}
								</font> 
								<br/> 
								<font face="微軟正黑體" size="4" style="color: gray;">
								<!-- 例如: 可口可樂 30元/罐 -->
								${good.goodsPrice} 元/罐
								</font> 
								<br /> 
								<!-- 各商品圖片 --> 
								<img border="0" src="DrinksImage/${good.goodsImageName}" width="150" height="150"> 
									<br /> 
									<font face="微軟正黑體" size="3">
										<input type="hidden" name="goodsID"
										value="${good.goodsID}"> 購買<input type="number"
										name="buyQuantity" min="0" max="30" size="5" value="0">罐
										<!-- 設定最多不能買大於庫存數量 --> <br>
									<br>
									<button onclick="addCartGoods(${good.goodsID},${goodsList.indexOf(good)})">加入購物車</button>
										<br>											<!--${status.index} 需設varStatus -->
									<p style="color: red;">(庫存 ${good.goodsQuantity} 罐)</p> <!-- 顯示庫存數量 -->
								</font>
								</td>
								</c:forEach>
							</tr>					
					</tbody>
				</table>
			</td>
			</tr>	
	</table>
	<nav  aria-label="Page navigation example" >
  			<ul  class="pagination" >
  			
					<c:url value="/FrontendAction.do" var="page">
						<c:param name="action" value="searchGoods" />
						<c:param name="searchKeyword" value="${pages.searchKeyword}"/>
						<c:param name="pageNo" value="" />
					</c:url>
					<c:if test="${pages.curPage>1}">
					<li class="page-item"><a class="page-link" href="${page}${pages.curPage-1}">上一頁</a></li>
					</c:if>
					<c:forEach  items="${pages.pageNo}" var="pageNo">
					<li class="page-item"><a class="page-link" href="${page}${pageNo}">${pageNo}</a></li>
					</c:forEach>
					<c:if test="${pages.curPage<pages.totalPages}">
				<li class="page-item"><a class="page-link" href="${page}${pages.curPage+1}">下一頁</a></li>
			</c:if>
			</ul>
</nav>
</body>
</html>
