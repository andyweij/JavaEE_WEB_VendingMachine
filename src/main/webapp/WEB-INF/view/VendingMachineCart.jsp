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
			<div class="col">
			<div class="card" style="width: 18rem;">
			  <img class="card-img-top" src="DrinksImage/coffee.jpg" alt="Card image cap">
			  <div class="card-body">
			    <h5 class="card-title">歡迎光臨，${member.customerName}!</h5>
			    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
			  </div>
			   <div class="card-body">
    			<a href="BackendAction.do?action=queryGoods" class="card-link">後臺頁面</a>
    			<a href="LoginAction.do?action=logout" class="card-link">登出</a>
 				 </div>			
			  </div>
			</div>
			
			<div class="col">
			
			
			</div>
		</div>
	</div>
</body>
</html>