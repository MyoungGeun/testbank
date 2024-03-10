<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp"%>


<div class="col-sm-8">
	<h2>출금 요청(인증)</h2>
	<h5>Bank App 오신 걸 환영합니다.</h5>
	<form action="/account/withdrawal" method="post" >
	  <div class="form-group">
	    <label for="amount">출금 금액 : </label>
	    <input type="number" name="amount" class="form-control" placeholder="Enter amount" id="amount" value="1000">
	  </div>
	  <div class="form-group">
	    <label for="wAccountNumber">출금 계좌 번호 : </label>
	    <input type="text" name="wAccountNumber" class="form-control" placeholder="Enter account number" id="wAccountNumber" value="1111">
	  </div>
	  <div class="form-group">
	    <label for="pwd">출금 계좌 비밀 번호 :</label>
	    <input type="password" name="wAccountPassword" class="form-control" placeholder="Enter password" id="pwd" value="1234">
	  </div>

	<div class="text-right">
		  <button type="submit" class="btn btn-primary">출금 요청</button>
	</div>	 		

	</form>
</div>
</div>
</div>


<%@ include file="/WEB-INF/view/layout/footer.jsp"%>