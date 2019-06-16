<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Katarzyna Dudek
  Date: 19.05.2018
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <h1>One epicture</h1>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>

<div class="container">

    <div class="row">

        <div class="col-md-4">
            <div class="card mb-4 box-shadow">
                <img class="card-img-top" alt="Thumbnail [100%x225]"
                     style="height: ${picture.value.getHeight()}px; width: ${picture.value.getWidth()}px; display: block;"
                     src="/gallery/pictures/${picture.value.getIndex()}" data-holder-rendered="true">

            </div>
        </div>


    </div>
</div>
</body>
</html>
