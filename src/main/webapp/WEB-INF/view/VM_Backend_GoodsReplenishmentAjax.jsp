<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BackEndAjax</title>
<script src="${JS_PATH}/jquery-1.11.1.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#goodId").bind("change",function(){
				//# 取id
				var goodsID = $("#goodId option:selected").val();
				
				var goodsParam = {goodsID : goodsID};
			
				if(goodsID != ""){
					$.ajax({
					  url: '${WEB_PATH}BackendAction.do?action=getupdateGoods', // 指定要進行呼叫的位址
					  type: "GET", // 請求方式 POST/GET
					  // data: {id : accountID}, // 傳送至 Server的請求資料(物件型式則為 Key/Value pairs)
					  data: goodsParam,
					  dataType : 'JSON', // Server回傳的資料類型
					  success: function(goodsInfo) { // 請求成功時執行函式
					  	
					  	$("#goodPrice").val(goodsInfo.goodsPrice);//id
					  	$("#goodQuantity").val(goodsInfo.goodsQuantity);
					  	$("#goodstatus").val(goodsInfo.status);
					  },
					  error: function(error) { // 請求發生錯誤時執行函式
					  	alert("Ajax Error!");
					  }
					});
				}else{
				  	$("#goodPrice").val('');
				  	$("#goodsQuantity").val('');
				  	$("#goodstatus").val('1');
				}
			});
		});
	</script>
</head>
<body>
	<%@ include file="BackendMenu.jsp"%>
	<h2 align="Center">商品維護作業</h2>
		<HR>
	<div style="margin-left: 25px;">
		<p style="color: blue;">${sessionScope.updateMsg}</p>
		<% session.removeAttribute("updateMsg"); %>
		<form name="updateGoodsForm" action="BackendAction.do" method="post">
			<input type="hidden" name="action" value="updateGoods" />
			<p>
				<select size="1" id="goodId" name="goodsID" >
					<option value="">----- 請選擇 -----</option>
					<c:forEach items="${goods}" var="goods">
						<option <c:if test="${goods.goodsID eq updategoods.goodsID}">selected</c:if> 
						value="${goods.goodsID}">
							${goods.goodsName}
							</option>
					</c:forEach>
				</select>
			</p>
			<p>
				更改價格： 
			<input type="number" id="goodPrice" name="goodsPrice" size="5" value="${updategoods.goodsPrice}" >
			</p>
			<p>
				補貨數量：
			<input type="number" id="goodQuantity" name="goodsQuantity" size="5" value="${updategoods.goodsQuantity}" >
			</p>
			<p>
				商品狀態： <select id="goodstatus" name="status">

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