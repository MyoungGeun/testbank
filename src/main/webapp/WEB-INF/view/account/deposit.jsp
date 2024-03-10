<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header.jsp -->
<%@ include file="/WEB-INF/view/layout/header.jsp"%>

<div class="col-sm-8">
	<h2>입금 요청(인증)</h2>
	<h5>Bank App 오신 걸 환영합니다.</h5>
	<form action="/account/deposit" method="post">
		<div class="form-group">
			<label for="amount">입금 금액:</label> <input type="text" name="amount" class="form-control" placeholder="Enter amount" id="amount" value="1000">
		</div>
		<div class="form-group">
			<label for="dAccountNumber">입금 계좌번호:</label> <input type="text" name="dAccountNumber" class="form-control" placeholder="입금 계좌번호 입력"
				id="dAccountNumber" value="1111">
		</div>
		<div class="text-right">
			<button type="submit" class="btn btn-primary">입금</button>
		</div>
	</form>
</div>
</div>
</div>

<!-- footer.jsp -->
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>
