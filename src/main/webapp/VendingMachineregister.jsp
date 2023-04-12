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
<script>
// Example starter JavaScript for disabling form submissions if there are invalid fields
(function() {
  'use strict';
  window.addEventListener('load', function() {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.getElementsByClassName('needs-validation');
    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function(form) {
      form.addEventListener('submit', function(event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  }, false);
})();
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container">
<div class="col-md-4 offset-md-4 mb-3">
<c:if test="${not empty requestScope.loginMsg}">
		系統回應：<p style="color:blue;">${requestScope.loginMsg}</p>
</c:if>
</div>
<form action="LoginAction.do" method="post" class="needs-validation" novalidate>
<input type="hidden" name="action" value="register"/>
  <div class="form-row">
     <div class="col-md-4 offset-md-4 mb-3">
    <label for="exampleInputId">User Name</label>
    <input type="text" class="form-control" id="exampleInputId" aria-describedby="emailHelp" placeholder="Enter ID" name="newid" required maxlength="10">
    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
	<div class="valid-feedback">欄位驗証通過</div>
	<div class="invalid-feedback">欄位驗証失敗</div>
  </div>
  </div>
<div class="form-row">
   <div class="col-md-4 offset-md-4 mb-3"> 
    <label for="exampleInputPassword1">Password</label>
    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" name="newpwd" required>
	<div class="valid-feedback">欄位驗証通過</div>
	<div class="invalid-feedback">欄位驗証失敗</div>
  </div>
</div>
<div class="form-row">
   <div class="col-md-4 offset-md-4 mb-4">
    <label for="exampleInputname">UserName</label>
    <input type="text" class="form-control" id="exampleInputname" placeholder="使用者名稱" name="newname" required >
    <div class="valid-feedback">欄位驗証通過</div>
	<div class="invalid-feedback">欄位驗証失敗</div>
  </div>
   </div>
   <div class="row">
   <div class="col-md-3 offset-md-4 mb-3">
  <button type="submit" class="btn btn-primary">Submit</button>
   </div>
   <div class="col-md-3 mb-3">
   <h3><a href="${WEB_PATH}/VendingMachineLogin.jsp" class="badge badge-dark">登入頁</a></h3>
   </div>
   </div>
</form>

</div>

</body>
</html>