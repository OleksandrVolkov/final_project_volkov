<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css">
</head>
<body class="m-3">
<div class="row col-md-6">
    <table class="table table-striped table-bordered table-sm">
        <tr>
            <th>Text</th>
            <th>Status</th>
            <th>Items</th>
            <th>Cancel</th>
            <th>Confirm</th>
            <th>Feedback</th>
        </tr>


        <c:forEach items="${requests}" var="cur_request">
            <tr>
                <td>${cur_request.text}</td>
                <td>${cur_request.status}</td>
                <td>
                            ${items[cur_request.id].name}
                </td>
                <td>
                    <%--<form method="get" actions="/cancelReq">--%>
                        <c:if test="${cur_request.status eq 'not seen'}">
                            <form method="get" action="rejectionForm.jsp">
                                <input type="hidden" name="currentPage" value="${currentPage}">
                                <input type="hidden" name="recordsPerPage" value="${recordsPerPage}">
                                <input type="hidden" name="request_id" value="${cur_request.id}">
                                <button type="submit" class="cancel" name="cancelButton" value="${cur_request.id}">Отменить</button>
                            </form>
                        </c:if>
                </td>
                <td>
                        <%--<c:if test="${notNeededToConfirm.contains(cur_request.id)}">--%>
                            <%--<form method="get" actions="manager_price.jsp">--%>
                                <%--<input type="hidden" name="currentPage" value="${currentPage}">--%>
                                <%--<input type="hidden" name="recordsPerPage" value="${recordsPerPage}">--%>
                                <%--<input type="hidden" name="request_id" value="${cur_request.id}">--%>
                                <%--<button type="submit" class="accept" name="confirmButton" value="${cur_request.id}">Подтвердить</button>--%>
                            <%--</form>--%>
                        <%--</c:if>--%>
                            <c:if test="${cur_request.price eq null && cur_request.status eq 'not seen'}">
                                <form method="get" action="manager_price.jsp">
                                    <input type="hidden" name="currentPage" value="${currentPage}">
                                    <input type="hidden" name="recordsPerPage" value="${recordsPerPage}">
                                    <input type="hidden" name="request_id" value="${cur_request.id}">
                                    <button type="submit" class="accept" name="confirmButton" value="${cur_request.id}">Подтвердить</button>
                                </form>
                            </c:if>
                </td>

                <td>
                    <c:if test="${cur_request.feedback ne null}">
                        ${cur_request.feedback.text}
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<nav aria-label="Navigation for countries">
    <ul class="pagination">
            <c:if test="${currentPage != 1}">
                <li class="page-item"><a class="page-link"
                                         href="managerserv?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}">Previous</a>
                </li>
            </c:if>

        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <li class="page-item active"><a class="page-link">
                            ${i} <span class="sr-only">(current)</span></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link"
                                             href="managerserv?recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage lt noOfPages}">
            <li class="page-item"><a class="page-link"
                                     href="managerserv?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}">Next</a>
            </li>
        </c:if>
    </ul>
</nav>

<form action="/account" method="get">
    <input type="submit" name="exit_account" value="Exit an account">
    <input type="hidden" name="action" value="logout">
    <c:set var = "role" scope = "session" value = "manager"/>
</form>


<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"></script>

</body>
</html>
