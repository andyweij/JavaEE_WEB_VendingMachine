<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Backend</title>
	<script type="text/javascript">
		
	</script>
</head>
<body>
<%@ include file="BackendMenu.jsp" %>
	<h2 align="center">商品新增</h2>
	<HR>	
	<div style="margin-left:25px;">
	<p style="color:blue;">${sessionScope.createMsg}</p>
	<% session.removeAttribute("createMsg"); %>
	<form action="BackendAction.do?action=addGoods" enctype="multipart/form-data" method="post">
	
<div class="input-group mb-3">
  <div class="input-group-append">
    <span class="input-group-text" id="basic-addon2">商品名稱:</span>
  </div>
  <input type="text" class="form-control" name="goodsName" placeholder="Ex:apple" aria-label="Recipient's username" aria-describedby="basic-addon2">

</div>
<!-- 		<p>
			商品名稱:
			<input type="text" name="goodsName" size="10"/>
		</p>	 -->	
		<p>
			設定價格： 
			<input type="text" name="goodsPrice" size="10"/>
		</p>
		<p>
			上架數量：
			<input type="text" name="goodsQuantity" size="10"/>
		</p>
		<p>
			商品圖片：
			<input type="file" name="goodsImage" />			
		</p>
		<p>
			商品狀態：
			<select name="status">
				<option value="1">上架</option>
				<option value="0">下架</option>				
			</select>			
		</p>
		<p>
			<input type="submit" value="新增">
		</p>
	</form>
	</div>

</body>
</html>