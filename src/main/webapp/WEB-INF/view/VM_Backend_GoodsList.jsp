<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
		table{ border-color: red; }
		td { font-weight:bold; }
        .classTD {color: white; background-color: green;}
		#idTD {color: white; background-color: blue;}
	</style>
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BackEnd</title>
<script type="text/javascript">	

</script>
</head>
<%@ include file="BackendMenu.jsp"%>
<body >
<div  class="container-fluid">
	<h2 align="Center">商品列表</h2>
		<HR>
	<form action="BackendAction.do" method="get" style="width: 600px ;">
	<input type="hidden" name="action" value="queryGoods"/>

	<table>
		<tbody>
	<tr>
	<td>商品編號</td>
	<td>商品名稱(不區分大小寫)</td>
	</tr>
	<tr>
	<td class="classTD"><input type="text" name="goodsID" id="goodsNo" value="${pagesearchkey.goodsID}" ></td>
	<td class="classTD"><input type="text" name="goodsName" id="goodsName" value="${pagesearchkey.goodsName}" ></td>
</tr>
<tr>
	<td >商品最低價格</td>
	<td >商品最高價格</td>
	<td >價格排序</td>
</tr>
<tr>
	<td class="classTD"><input type="text" name="priceMin" id="priceMin" value="${pagesearchkey.priceMin}" ></td>
	<td class="classTD"><input type="text" name="priceMax" id="priceMax" value="${pagesearchkey.priceMax}" ></td>
	<td >
	<select id="priceOrder" name="priceOrder">
	<option value="2">無</option>
	<option value="0">價格由高到低</option>
	<option value="1">價格由低到高</option>
	</select>
	</td>
</tr>
<tr>
	<td>商品低於庫存量</td>
	<td>商品狀態</td>
</tr>
<tr>
	<td class="classTD"><input type="text" name="stockQuantity" id="stockQuantity" value="${pagesearchkey.stockQuantity}" ></td>
	<td >
	<select id="goodstatus" name="goodstatus">
	<option value="${pagesearchkey.goodstatus }">All</option>
	<option value="1">上架</option>
	<option value="0">下架</option>
	</select>
	</td>
	<td><input type="submit" value="查詢"></td>	
</tr>	
</tbody>

				<tr height="50" align="center">
					<td width="100"><b>商品編號</b></td>
					<td width="100"><b>商品名稱</b></td>
					<td width="100"><b>商品價格</b></td>
					<td width="100"><b>商品庫存</b></td>
					<td width="100"><b>商品狀態</b></td>
				</tr>			
				<c:forEach items="${goods}" var="goods">
					<tr height="30" align="center">
						<td>${goods.goodsID}</td>
						<td>${goods.goodsName}</td>
						<td>${goods.goodsPrice}</td>
						<td>${goods.goodsQuantity}</td>
						<td>${goods.status}</td>
					</tr>					
				</c:forEach>
	</table>
	<table align="right" style="width: 600px ">
	<tr align="right" style="width: 600px">
		<td colspan="2" align="right" style="width: 500px">		
					<c:url value="/BackendAction.do" var="page">
						<c:param name="action" value="queryGoods" />
						<c:param name="goodsNo" value="${pagesearchkey.goodsID}" />
						<c:param name="goodsName" value="${pagesearchkey.goodsName}" />
						<c:param name="priceMin" value="${pagesearchkey.priceMin}" />
						<c:param name="priceMax" value="${pagesearchkey.priceMax}" />
						<c:param name="priceOrder" value="${pagesearchkey.priceOrder}" />
						<c:param name="goodstatus" value="${pagesearchkey.goodstatus}" />
						<c:param name="pageNo" value="" />
						</c:url>
		<c:if test="${pages.curPage>1}">
					<td class="page-item"><a class="page-link" href="${page}${pages.curPage-1}">Previous</a></td>
					</c:if>
					<c:forEach  items="${pages.pageNo}" var="pageNo">
					<td class="page-item"><a class="page-link" href="${page}${pageNo}">${pageNo}</a></td>
					</c:forEach>
					<c:if test="${pages.curPage<pages.totalPages}">
				<td class="page-item"><a class="page-link" href="${page}${pages.curPage+1}">Next</a></td>
			</c:if>
	</tr>	
	</table>
</form>
</div>
</body>
</html>