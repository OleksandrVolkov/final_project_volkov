<%--
  Created by IntelliJ IDEA.
  User: volkov_o_o
  Date: 01.01.19
  Time: 01:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <style>
        body {font-family: Arial, Helvetica, sans-serif;}
        * {box-sizing: border-box;}

        /* Full-width input fields */
        input[type=text], input[type=password] {
            width: 50%;
            padding: 15px;
            margin: 5px 0 22px 0;
            display: inline-block;
            border: none;
            background: #f1f1f1;
        }

        /* Add a background color when the inputs get focus */
        input[type=text]:focus, input[type=password]:focus {
            background-color: #ddd;
            outline: none;
        }

        /* Set a style for all buttons */
        button {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
            opacity: 0.9;
        }

        button:hover {
            opacity:1;
        }

        /* Extra styles for the cancel button */
        .cancelbtn {
            padding: 14px 20px;
            background-color: #f44336;
        }

        /* Float cancel and signup buttons and add an equal width */
        .cancelbtn, .signupbtn {
            float: left;
            width: 50%;
        }

        /* Add padding to container elements */
        .container {
            padding: 16px;
        }

        /* The Modal (background) */
        .modal {
            /*display: none; !* Hidden by default *!*/
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: #474e5d;
            padding-top: 50px;
        }

        /* Modal Content/Box */
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
            border: 1px solid #888;
            width: 80%; /* Could be more or less, depending on screen size */
        }

        /* Style the horizontal ruler */
        hr {
            border: 1px solid #f1f1f1;
            margin-bottom: 25px;
        }

        /* The Close Button (x) */
        .close {
            position: absolute;
            right: 35px;
            top: 15px;
            font-size: 40px;
            font-weight: bold;
            color: #f1f1f1;
        }

        .close:hover,
        .close:focus {
            color: #f44336;
            cursor: pointer;
        }

        /* Clear floats */
        .clearfix::after {
            content: "";
            clear: both;
            display: table;
        }

        /* Change styles for cancel button and signup button on extra small screens */
        @media screen and (max-width: 300px) {
            .cancelbtn, .signupbtn {
                width: 100%;
            }
        }
















        textarea {
            outline: none;
            -moz-appearance: none;
            border: 1px solid #999; /* указание этого свойства также удалит обводку в FireFox */
            height:150px;
            width: 100%;
        }




        .wrongAuthorization a{
            text-decoration: none;
            color: red;
        }
        .wrongAuthorization a:hover{
            text-decoration: none;
            color: red;
        }

        .registrationLink a:hover{
            text-decoration: none;
            color: black;
        }
        .registrationLink a {
            text-decoration: none;
            color: black;
        }


        .cancelLink{
            text-decoration: none;
            color: white;
        }

        .cancelLink :hover{
            text-decoration: none;
            color: white;
        }


    </style>
</head>
<body>


<div id="id01" class="modal">
    <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
    <form class="modal-content" action="/request">
        <div class="container">
            <h1>Оставить заявку</h1>
            <p>Пожалуйста, введите необходимые данные для оформления заявкм</p>
            <hr>
            <label for="selectItem"><b>Выбрать услугу</b></label>
            <br>
            <select name="itemID">
                <c:forEach items="${itemsAvailable}" var="cur_item">
                  <option value="${cur_item.id}">${cur_item.name}</option>
                </c:forEach>
            </select>
            <br>
            <br>
            <br>
            <label for="comment"><b>Комментарий</b></label>
            <div class="textar">
                <textarea type="text" placeholder="Введите комментарий" name="comment" required></textarea>
            </div>
            <br>


            <label>
                <input type="checkbox" checked="checked" name="remember" style="margin-bottom:15px"> Запомнить меня
            </label>


            <div class="clearfix">
                <a class="cancelLink" href="main.jsp"><button type="button" class="cancelbtn">Отменить</button></a>
                <button type="submit" class="signupbtn">Отправить заявку</button>
            </div>
        </div>
    </form>
</div>

</body>
</html>
