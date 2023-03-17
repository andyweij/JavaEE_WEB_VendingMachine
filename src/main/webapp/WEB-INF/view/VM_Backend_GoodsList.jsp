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
<body>
	<%@ include file="BackendMenu.jsp"%>
	<br />
	<br />
	<HR>
	<h2>商品列表</h2>
	<br />
	<div style="margin-left: 25px;">
	<form action="BackendAction.do" method="get" style="width: 600px ;">
	<input type="hidden" name="action" value="goodsSearch"/>
	<div style="border: dashed; border-color:orange;">
	<table>
	<tr>
	<td>商品編號</td>
	<td>商品名稱(不區分大小寫)</td>
	</tr>
	<tr>
	<td class="classTD"><input type="text" name="goodsID" id="goodsNo" value="" ></td>
	<td class="classTD"><input type="text" name="goodsName" id="goodsName" value="" ></td>
</tr>
<tr>
	<td >商品最低價格</td>
	<td >商品最高價格</td>
	<td >價格排序</td>
</tr>
<tr>
	<td class="classTD"><input type="text" name="priceMin" id="priceMin" value="" ></td>
	<td class="classTD"><input type="text" name="priceMax" id="priceMax" value="" ></td>
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
	<td class="classTD"><input type="text" name="stockQuantity" id="stockQuantity" value="" ></td>
	<td >
	<select id="goodstatus" name="goodstatus">
	<option value="">All</option>
	<option value="1">上架</option>
	<option value="0">下架</option>
	</select>
	</td>
	<td><input type="submit" value="查詢"></td>
</tr>	
	</table>
	</div>
	</form>
	</div>
	<br/>
	<div style="margin-left: 25px;">
		<table border="1">
			<tbody>
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
			</tbody>
		</table>
		<%-- 
<tr>
		<td colspan="2" align="right">
					<c:url value="/BackendAction.do" var="page">
						<c:param name="action" value="goodsSearch" />
						<c:param name="goodsID" value="${goods.goodsID}"/>
						<c:param name="goodsName" value="${goods.goodsName}"/>
						<c:param name="priceMax" value="${goods.priceMax}"/>
						<c:param name="priceMin" value="${goods.priceMin}"/>
						<c:param name="priceOrder" value="${goods.priceOrder}"/>
						<c:param name="stockQuantity" value="${goods.stockQuantity}"/>
						<c:param name="goodstatus" value="${goods.goodstatus}"/>
						<c:param name="pageNo" value="${pages.curPage}" />
					</c:url>				
					<c:forEach var="i" begin="1" end="5"> 
					<h3 class="page"><a href="${page}" >${i }</a></h3>
					</c:forEach>			
				</td>
</tr>
--%>
	</div>
</body>
</html>