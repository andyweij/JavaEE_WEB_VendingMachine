<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<p style="color:blue;">
	${sessionScope.member.customerName} 先生/小姐您好!
	<a href="LoginAction.do?action=logout" align="left">(登出)</a>
</p>
<br/>
<table border="1" style="border-collapse:collapse;margin-left:25px;">
	<tr>
		<td width="200" height="50" align="center">
			<a href="BackendAction.do?action=queryGoods">商品列表</a>
		</td>
		<td width="200" height="50" align="center">
			<a href="BackendAction.do?action=updateGoodsview">商品維護作業</a>
		</td>
		<td width="200" height="50" align="center">
			<a href="BackendAction.do?action=addGoodsview">商品新增上架</a>
		</td>
			<td width="200" height="50" align="center">
				<a href="BackendAction.do?action=querySalesReport">銷售報表</a>
			</td>
	</tr>
</table>