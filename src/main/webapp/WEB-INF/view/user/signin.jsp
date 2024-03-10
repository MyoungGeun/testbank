<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header.jsp -->
<%@ include file="/WEB-INF/view/layout/header.jsp"%>

<!-- start of content.jsp(메인 영역)   -->
<div class="col-sm-8">
	<h2>로그인</h2>
	<h5>Bank App 오신 걸 환영합니다.</h5>
	<form action="/user/sign-in" method="post">
		<div class="form-group">
			<label for="username">Username:</label>
			<input type="text" class="form-control" placeholder="Enter username" id="username" name="username" value="길동">
		</div>
		<div class="form-group">
			<label for="pwd">Password: </label>
			<input type="password" class="form-control" placeholder="Enter password" id="pwd" name="password" value="1234">
		</div>

		<div class="text-right">
			<button type="submit" class="btn btn-primary">로그인</button>
		</div>
	</form>
</div>
</div>
</div>

<!-- end of content.jsp(메인 영역)   -->

<!-- footer.jsp -->
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>


