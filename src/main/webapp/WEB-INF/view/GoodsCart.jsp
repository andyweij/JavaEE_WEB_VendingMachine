<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:url value="/" var="WEB_PATH"/>
<c:url value="/js" var="JS_PATH"/>
<c:url value="/css" var="CSS_PATH"/>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${CSS_PATH}/bootstrap.min.css">
<script src="${JS_PATH}/jquery-3.2.1.min.js"></script>
<script src="${JS_PATH}/popper.min.js"></script>
<script src="${JS_PATH}/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cart</title>
</head>
<body>
<div class="container">
<div class="row">
<div class="col">
<a style="font-size: larger;" href="FrontendAction.do?action=searchGoods" class="badge badge-danger">商品頁</a>
</div>	
<div class="col-10">	
<table class="table table-striped">
  <thead>
    <tr>
      <th scope="col">Item</th>
      <th scope="col">商品名稱</th>
      <th scope="col">商品價格</th>
      <th scope="col">購買數量</th>
    </tr>
  </thead>
  <tbody>
 
  <c:forEach items="${shoppingCartGoods}" var="goodsList" begin="0" end="${shoppingCartGoods.size()}"  >
    <tr>
      <th scope="row">${shoppingCartGoods.indexOf(goodsList)+1}</th>
      <td>${goodsList.goodsName }</td>
      <td>${goodsList.goodsPrice}</td>
      <td>${goodsList.buyQuantity}</td>
    </tr>
    </c:forEach>
    <td>合計: ${cartGoodsInfo.totalAmount }</td>
  </tbody>

</table>
</div>
</div>
<div class="row">
<div class="col-md-4 offset-md-4 mb-3">
<form action="FrontendAction.do" method="post">
					<input type="hidden" name="action" value="buyGoods" />
					<div class="card" style="width: 18rem;">					  
					  <div class="card-body">		  
									<div class="input-group-prepend">
										<span class="input-group-text">投入 $</span>
									</div>
									<input type="text" class="form-control" name="inputMoney" max="100000" min="0"
										aria-label="Amount (to the nearest dollar)">
									<div class="input-group-append">
										<span class="input-group-text">元</span>
									</div>
									</div>
									<div class="card-body"  >
					   	<input class="btn btn-secondary" type="submit" value="送出">&emsp;
		    			
		    			<a href="LoginAction.do?action=logout" class="card-link">登出</a>
		 				 </div>			
									</div>
									</form>
	</div>
	</div>
	</div>
</body>
</html>