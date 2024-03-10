<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header.jsp -->
<%@ include file="/WEB-INF/view/layout/header.jsp"%>

<!-- start of content.jsp(메인 영역)   -->
<div class="col-sm-8">
	<h2>계좌 생성(인증)</h2>
	<h5>Bank App 오신 걸 환영합니다.</h5>
	<form action="/account/save" method="post" >
	  <div class="form-group">
	    <label for="number">Account Number : </label>
	    <input type="text" name="number" class="form-control" placeholder="Enter number" id="number" value="5555">
	  </div>
	  <div class="form-group">
	    <label for="pwd">계좌 비밀번호 :</label>
	    <input type="password" name="password" class="form-control" placeholder="Enter password" id="pwd" value="1234">
	  </div>
	  <div class="form-group">
	    <label for="balance">Deposit Amount :</label>
	    <input type="number" name="balance" class="form-control" placeholder="Enter balance" id="balance" value="2000">
	  </div>

	<div class="text-right">
		  <button type="submit" class="btn btn-primary">계좌 생성</button>
	</div>	 		

	</form>
</div>
</div>
</div>

<!-- end of content.jsp(메인 영역)   -->

<!-- footer.jsp -->
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>


