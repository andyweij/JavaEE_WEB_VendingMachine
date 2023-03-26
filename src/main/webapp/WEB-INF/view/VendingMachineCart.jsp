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
<script src="${JS_PATH}/jquery-3.2.1.min.js"></script>
<script src="${JS_PATH}/popper.min.js"></script>
<script src="${JS_PATH}/bootstrap.min.js"></script>
<script src="${JS_PATH}/jquery-1.11.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
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
<body>
	<div class="container">
	
		<div class="col" align="right" >
	<br/>	
	<button onclick="queryCartGoods()">購物車商品列表</button>
	<button onclick="clearCartGoods()">清空購物車</button>
	<br/><br/>
				<form action="FrontendAction.do" method="get">
					<input type="hidden" name="action" value="searchGoods" /> 
					<input type="hidden" name="pageNo" value="1" /> 
					<input type="text" name="searchKeyword" value="${pages.searchKeyword}"/> <input type="submit" value="商品查詢" />
				</form>
		</div>
			<div class="row">
				<div class="col" align="center">
					<form action="FrontendAction.do" method="post">
					<input type="hidden" name="action" value="buyGoods" />
					<div class="card" style="width: 18rem;">
					  <img class="card-img-top" src="DrinksImage/coffee.jpg" alt="Card image cap">
					  <div class="card-body">		  
					    <h5 class="card-title" align="center">歡迎光臨，${member.customerName}!</h5>
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text">投入 $</span>
									</div>
									<input type="text" class="form-control" name="inputMoney" max="100000" min="0"
										aria-label="Amount (to the nearest dollar)">
									<div class="input-group-append">
										<span class="input-group-text">元</span>
									</div>
								</div>
								<h3>${frontMsg}</h3>
								<% session.removeAttribute("frontMsg"); %>
							</div>			
					   <div class="card-body"  >
					   	<input class="btn btn-secondary" type="submit" value="送出">&emsp;
		    			<a href="BackendAction.do?action=queryGoods" class="card-link">後臺頁面</a>
		    			<a href="LoginAction.do?action=logout" class="card-link">登出</a>
		 				 </div>			
					  </div>
					  </form>
					<div class="card border-dark mb-3" style="max-width: 18rem;">
					  <div class="card-header" align="center" >~~~~~~~ 消費明細 ~~~~~~~</div>
					  <div class="card-body text-dark">
					    <p class="card-text">投入金額：${buyRtn.payprice}</p>
					    <p class="card-text">找零金額：${buyRtn.returnprice}</p>
					    <p class="card-text">投入金額：${buyRtn.payprice}</p>
					    <p class="card-text" align="center">物品明細:</p>
					    <c:forEach items="${buyRtn.shoppingCartGoods}" var="goodsinfo" >
					    	<p>商品名稱:${goodsinfo.goodsName}</p>
							<p>商品金額:${goodsinfo.goodsPrice}</p>
							<p>商品數量:${goodsinfo.buyQuantity}</p>
						</c:forEach>
						 <% session.removeAttribute("buyRtn"); %>
					  </div>
					 
					</div>					
			</div>
			
				<div class="col">
					<div class="card-group">
						<c:forEach items="${goodsList}" var="good" varStatus="status"
							end="2">
							<div class="card">
								<h5 class="card-title">${good.goodsName}</h5>
								<p class="card-text">${good.goodsPrice}元/罐</p>
								<img class="card-img-top"
									src="DrinksImage/${good.goodsImageName}" alt="Card image cap">
	
								<div class="card-body">
	
									<input type="hidden" name="goodsID" value="${good.goodsID}">
									購買<input type="number" name="buyQuantity" min="0" max="30"
										size="5" value="0">罐<br>
									<button
										onclick="addCartGoods(${good.goodsID},${goodsList.indexOf(good)})">加入購物車</button>
									<p style="color: red;">(庫存 ${good.goodsQuantity} 罐)</p>
								</div>
	
							</div>
						</c:forEach>
					</div>
		<div class="card-group">
					<c:forEach items="${goodsList}" var="good" varStatus="status" begin="3">
						<div class="card">
							<h5 class="card-title">${good.goodsName}</h5>
							<p class="card-text">${good.goodsPrice}元/罐</p>
							<img class="card-img-top"
								src="DrinksImage/${good.goodsImageName}" alt="Card image cap">

							<div class="card-body">

								<input type="hidden" name="goodsID" value="${good.goodsID}">
								購買<input type="number" name="buyQuantity" min="0" max="30"
									size="5" value="0">罐<br>
								<button
									onclick="addCartGoods(${good.goodsID},${goodsList.indexOf(good)})">加入購物車</button>
								<p style="color: red;">(庫存 ${good.goodsQuantity} 罐)</p>
							</div>

						</div>
					</c:forEach>
				</div>			
			</div>
		</div>
		<div class="col" align="right" >
		<nav  aria-label="Page navigation example">
  			<ul  class="pagination" >  			
					<c:url value="/FrontendAction.do" var="page">
						<c:param name="action" value="searchGoods" />
						<c:param name="searchKeyword" value="${pages.searchKeyword}"/>
						<c:param name="pageNo" value="" />
					</c:url>
					<c:if test="${pages.curPage>1}">
					<li class="page-item"><a class="page-link" href="${page}${pages.curPage-1}">Previous</a></li>
					</c:if>
					<c:forEach  items="${pages.pageNo}" var="pageNo">

					<li class="page-item"><a class="page-link" href="${page}${pageNo}">${pageNo}</a></li>

					</c:forEach>
					<c:if test="${pages.curPage<pages.totalPages}">
				<li class="page-item"><a class="page-link" href="${page}${pages.curPage+1}">Next</a></li>
			</c:if>
			</ul>
		</nav>
		</div>
	</div>
</body>
</html>