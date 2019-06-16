<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
      integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>

<c:choose>
<c:when test="${login}">
<head><title>Login Page</title>
    <h1>Login Page</h1>

    <form action="/gallery/panel" method="post">
        First name: <input type="text" name="fname"><br>
        Last name: <input type="text" name="lname"><br>
        <input type="submit" value="Submit">
    </form>

    <br/>
    </c:when>
    <c:otherwise>


    <div class="album py-5 bg-light">
        <div class="container">

            <div class="row">
                <c:forEach items="${pictures}" var="picture">
                    <div class="col-md-4" id="id${picture.value.getIndex()}">
                        <div class="card mb-4 box-shadow">
                            <img class="card-img-top" alt="Thumbnail [100%x225]"
                                 style="height: 250px; width: 100%; display: block;"
                                 src="/gallery/pictures/${picture.value.getIndex()}" data-holder-rendered="true">

                            <div class="card-body">
                                <p class="card-text">${picture.value.getName()}<br>${picture.value.getWidth()}x${picture.value.getHeight()}<br>${picture.value.getSize()}kB<br>${picture.value.getCreated()}
                                </p>
                            </div>
                            <c:if test="${panel}">
                                <div class="d-flex justify-content-between align-items-center">
                                    <button type="button" onclick="deletePicture(${picture.value.getIndex()})"
                                            class="btn btn-sm btn-outline-secondary">Delete
                                    </button>
                                </div>

                            </c:if>

                        </div>
                    </div>

                </c:forEach>


            </div>
        </div>
    </div>
    </c:otherwise>
    </c:choose>

    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <script src="${jstlCss}"></script>
</body>
</html>