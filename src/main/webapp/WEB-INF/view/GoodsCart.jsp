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
<a style="font-size: larger;" href="FrontendAction.do?action=searchGoods" class="badge badge-danger">商品頁</a>
	<div class="container">
	
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
  </tbody>
</table>

	</div>


</body>
</html>