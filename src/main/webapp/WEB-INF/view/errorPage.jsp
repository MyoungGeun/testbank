<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${statusCode} Error - Page Not Found</title>
  <!-- 부트스트랩 4 CSS -->
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  <!-- 커스텀 CSS -->
  <style>
    /* 페이지 전체를 덮는 스타일 */
    body {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }

    /* 에러 메시지 스타일 */
    .error-message {
      text-align: center;
      margin-bottom: 20px;
    }
  </style>
</head>
<body>
  <div class="container">
    <div class="text-center">
      <h1 class="display-1">${statusCode}</h1>
      <p class="error-message">Page Not Found</p>
      <p>${message}</p>
      <a href="/" class="btn btn-primary">Go to Home Page</a>
    </div>
  </div>
</body>
</html>