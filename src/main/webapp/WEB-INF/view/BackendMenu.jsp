<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url value="/" var="WEB_PATH"/>
<c:url value="/js" var="JS_PATH"/>
<c:url value="/css" var="CSS_PATH"/>   
<head>
<link rel="stylesheet" href="${CSS_PATH}/bootstrap.min.css">
<script src="${JS_PATH}/jquery-3.2.1.min.js"></script>
<script src="${JS_PATH}/popper.min.js"></script>
<script src="${JS_PATH}/bootstrap.min.js"></script>    
    </head>
<div  class="container-fluid">
<div class="col">    
<p  style="color:blue;font-size:larger;font-style: italic; ">
	${sessionScope.member.customerName} 先生/小姐您好!
	<a href="LoginAction.do?action=logout" class="badge badge-secondary">(登出)</a>
	</br>
	</p>
	<a style="font-size: larger;" href="FrontendAction.do?action=searchGoods" class="badge badge-danger">商品業面</a>

<br/>
</div>
<ul class="nav nav-tabs">

  <li class="nav-item">
    <a class="nav-link" href="BackendAction.do?action=queryGoods">商品列表</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="BackendAction.do?action=updateGoodsview">商品維護作業</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="BackendAction.do?action=addGoodsview">商品新增上架</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="BackendAction.do?action=querySalesReport">銷售報表</a>
  </li>
</ul>
</div>