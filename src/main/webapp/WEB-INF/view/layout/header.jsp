<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>My Bank</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="/css/common.css">
</head>
<body>
	<div class="m-flex-container">
		<div class="m-container">
			<!-- 코드를 수정해주세요  -->
			<div class="jumbotron text-center banner-img" style="margin-bottom: 0">
				<h1>Tenco Bank</h1>
				<p>JSP, 마이바티스를 활용한 간단한 Bank 시스템 만들어 보기</p>
			</div>

			<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
				<a class="navbar-brand" href="/main-page">HOME</a>
				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="collapsibleNavbar">
					<ul class="navbar-nav">
						<c:choose>
							<c:when test="${principal != null}">
								<li class="nav-item"><a class="nav-link" href="/user/logout">Logout</a></li>
							</c:when>
							<c:otherwise>
								<li class="nav-item"><a class="nav-link" href="/user/sign-in">Sign in</a></li>
								<li class="nav-item"><a class="nav-link" href="/user/sign-up">Sign up</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</nav>

			<div class="container" style="margin-top: 30px">
				<div class="row">
					<div class="col-sm-4">
						<h2>About Me</h2>
						<h5>Photo of me:</h5>
						<!-- 코드를 수정합니다  -->
						<c:choose>
							<c:when test="${principal != null }">
								<img class="m-profile" alt="" src="${principal.setUpUserImage()}">
							</c:when>
							<c:otherwise>
								<div class="m-profile"></div>
							</c:otherwise>
						</c:choose>

						<h3>Some Links</h3>
						<p>계좌 목록,생성,출금,입금,이체 페이지를 사용할 수 있어요</p>
						<ul class="nav nav-pills flex-column text-left">
							<li class="nav-item"><a class="nav-link" href="/account/list">Account List</a></li>
							<li class="nav-item"><a class="nav-link" href="/account/save">Create Account</a></li>
							<li class="nav-item"><a class="nav-link" href="/account/withdrawal">Account Withdrawal</a></li>
							<li class="nav-item"><a class="nav-link" href="/account/deposit">Account deposit</a></li>
							<li class="nav-item"><a class="nav-link" href="/account/transfer">Account transfer</a></li>
							</li>
						</ul>
						<hr class="d-sm-none">
					</div>

					<!-- end of header.jsp   -->