<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:url value="/" var="WEB_PATH"/>
<c:url value="/js" var="JS_PATH"/>
<c:url value="/css" var="CSS_PATH"/>
<html>
<head>
<link rel="stylesheet" href="${CSS_PATH}/bootstrap.min.css">
<script src="${JS_PATH}/jquery-3.2.1.min.js"></script>
<script src="${JS_PATH}/popper.min.js"></script>
<script src="${JS_PATH}/bootstrap.min.js"></script>
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>VendingMachineLogin</title>
	<script type="text/javascript">
		
	</script>
</head>
<body>
<div class="container">
<div class="col-md-4 offset-md-4 mb-3">
<c:if test="${not empty requestScope.loginMsg}">
		系統回應：<p style="color:blue;">${requestScope.loginMsg}</p>
	</c:if>
	</div>
<form action="LoginAction.do" method="post">
<input type="hidden" name="action" value="login"/>
  <div class="form-row">
  <div class="col-md-4 offset-md-4 mb-3">
    <label for="exampleInputEmail1">User Name</label>
    <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="ID" name="id">
    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
  </div>
  </div>
  <div class="form-row">
  <div class="col-md-4 offset-md-4 mb-3">
    <label for="exampleInputPassword1">Password</label>
    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" name="pwd">
  </div>
  </div>
     <div class="row">
   <div class="col-md-3 offset-md-4 mb-3">
  <button type="submit" class="btn btn-primary">登入</button>
  </div>
  <div class="col-md-3 mb-3">
  <h3><a href="${WEB_PATH}/VendingMachineregister.jsp" class="badge badge-success">註冊</a></h3>
   </div>
   </div>
</form>

<!--	<c:if test="${not empty requestScope.loginMsg}">
		系統回應：<p style="color:blue;">${requestScope.loginMsg}</p>
	</c:if>
	<form action="LoginAction.do" method="post">
		<input type="hidden" name="action" value="login"/>
	    ID:<input type="text" name="id"/> <br/><br/>
	    PWD:<input type="password" name="pwd"/> <br/><br/>
	    <input type="submit"/>
	</form>
	-->
	</div>
</body>

</html>