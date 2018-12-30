<%--
  Created by IntelliJ IDEA.
  User: volkov_o_o
  Date: 24.12.18
  Time: 12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        * {box-sizing: border-box;}

        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
        }

        .header {
            overflow: hidden;
            background-color: #f1f1f1;
            padding: 20px 10px;
            width: 100%;
        }

        .header a {
            float: left;
            color: black;
            text-align: center;
            padding: 12px;
            text-decoration: none;
            font-size: 18px;
            line-height: 25px;
            border-radius: 4px;
            text-decoration: none;
        }

        .header a.logo {
            font-size: 25px;
            font-weight: bold;
            text-decoration: none;
        }

        .header a.logo:hover{
            font-size: 25px;
            background-color: #f1f1f1;
            color: black;
        }

        .header a:hover {
            background-color: red;
            color: white;
            text-decoration: none;
        }

        .header a.active:hover {
            background-color: red;
            color: white;
            text-decoration: none;
        }

        .header a.active {
            /*background-color: red;*/
            color: black;
            text-decoration: none;
        }

        .header-right {
            float: right;
        }
        .header a {
            text-decoration: none; /* Отменяем подчеркивание у ссылки */
        }

        @media screen and (max-width: 500px) {
            .header a {
                float: none;
                display: block;
                text-align: left;
            }

            .header-right {
                float: none;
            }
        }

        .banner img{
            width:100%;
            height: 65%;
            align-content: center;
        }

        .banner{
            width: 100%;
        }
        a{
            text-decoration: none;
        }

        .description h1{
           margin-left: 20px;
        }
        .description p{
            text-align: center;
            margin: 0;
            position: absolute;
            top: 50%;
            left: 50%;
            margin-right: -50%;
            transform: translate(-50%, -50%)
        }

        .description{
            margin-left: 37%;
            width: 400px;
            height: 200px;
            align-content: center;
            position: relative;
            text-decoration: none;
        }
        .description-head{
            margin-left: 30%;
        }
        .sendRequestButton{
            float: left;
            color: black;
            text-align: center;
            padding: 12px;
            text-decoration: none;
            font-size: 18px;
            line-height: 25px;
            border-radius: 4px;
            text-decoration: none;

            /*background-color: yellow;*/
            background-color: #f1f1f1;
            border: 2px solid black;
            border-radius: 10px;

            margin-top: 115px;
            margin-left: 110px;
        }

        .sendRequestButton:hover{
            /*font-size: 25px;*/
            /*background-color: #f1f1f1;*/
            color: black;
            background-color: red;
            text-decoration: none;
            color: white;
        }
        .description-head a:hover{
            text-decoration: none;
        }










        * {box-sizing: border-box;}

        /* Full-width input fields */
        input[type=text], input[type=password] {
            width: 70%;
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
            /*width: 44.7%;*/
            width: 49%;
        }

        /* Add padding to container elements */
        .container {
            padding: 8px;
        }

        /* The Modal (background) */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: #474e5d;
            padding-top: 10px;
            text-decoration: none;
        }

        /* Modal Content/Box */
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
            border: 1px solid #888;
            width: 80%; /* Could be more or less, depending on screen size */
            text-decoration: none;
        }

        /* Style the horizontal ruler */
        hr {
            border: 1px solid #f1f1f1;
            margin-bottom: 25px;
            width: 100%;
            margin-left: -15px;
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

        textarea {
            outline: none;
            -moz-appearance: none;
            border: 1px solid #999; /* указание этого свойства также удалит обводку в FireFox */
            height:150px;
            width: 700px;
        }
        .textar{
            margin-top:0px;
        }

        /* Change styles for cancel button and signup button on extra small screens */
        @media screen and (max-width: 300px) {
            .cancelbtn, .signupbtn {
                width: 100%;
            }
        }


        .wrongAuthorization a{
            text-decoration: none;
            color: red;
        }
        .wrongAuthorization a:hover{
            text-decoration: none;
            color: red;
        }
        .wrongAuthorization{
            display: none;
            position: absolute;
            margin-top: -35px;
            text-decoration: none;
            color: red;
        }

        .registrationLink{
            text-decoration: none;
            margin-top: -18px;
        }
        .registrationLink a:hover{
            text-decoration: none;
            color: black;
        }
        .registrationLink a{
            text-decoration: none;
            color: black;
        }

        .footer{
            margin-top: 50px;
            height:300px;
            background-color: black;
        }

        .wrongName{
            margin-left:80%;
            margin-top: -50px;
            position: absolute;
        }
        .wrongSurname{
            margin-left:80%;
            margin-top: -50px;
            position: absolute;
        }
        .wrongEmail{
            margin-left:80%;
            margin-top: -50px;
            position: absolute;
        }
        .wrongLogin{
            margin-left:80%;
            margin-top: -50px;
            position: absolute;
        }
        .wrongPassword{
            margin-left:80%;
            margin-top: -50px;
            position: absolute;
        }

        .mod3{
            padding-top:0px;
        }

    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<div class="header">
    <a href="#default" class="logo">AutoService</a>
    <div class="header-right">
        <a class="active" href="#home">Главная</a>
        <a href="#contact">Услуги</a>
        <a onclick="document.getElementById('id02').style.display='block'">Авторизация</a>
        <a href="#about">О нас</a>
        <a href="#contacts">Контакты</a>
    </div>
</div>



<div class="row">
    <div class="col-md-12 banner">
        <img src="static/site_banner.jpg">
    </div>
</div>
<div class="description">
    <div class="description-head"><h2>Описание</h2></div>
    <p>
      Наше агенство предоставляет возможность, оформив заявку, осуществить обслуживание
        вашего автомобиля в полном объеме в кратчайшие сроки по самым оптимальным ценам
    </p>
    <a class="sendRequestButton" onclick="document.getElementById('id01').style.display='block'">Отправить заявку</a>

</div>

<div id="id01" class="modal">
    <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
    <form class="modal-content" action="/request">
        <div class="container">
            <h1>Оставить заявку</h1>
            <p>Пожалуйста, введите необходимые данные для оформления заявкм</p>
            <hr>
            <label for="selectItem"><b>Выбрать услугу</b></label>
            <br>
            <select name="selectItem">
                <option value="kuzov">Ремонт кузова</option>
                <option value="engine">Замена двигателя</option>
                <option value="go">Замена ходовой</option>
                <option value="bus">Замена шин</option>
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
                <button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Отменить</button>
                <button type="submit" class="signupbtn">Отправить заявку</button>
            </div>
        </div>
    </form>
</div>



<div id="id02" class="modal">
    <span onclick="document.getElementById('id02').style.display='none'" class="close" title="Close Modal">&times;</span>
    <form class="modal-content" action="/authorization">
        <div class="container">
            <h1>Авторизация</h1>
            <p>Пожалуйста, введите необходимые данные для авторизации</p>
            <hr>
            <label for="login"><b>Login</b></label>
            <input type="text" placeholder="Login" name="login" required>
            <br>
            <br>
            <label for="pas"><b>Password</b></label>
            <input type="password" placeholder="Password" name="pas" required>
            <br>

            <label>
                <input type="checkbox" checked="checked" name="remember" style="margin-bottom:15px"> Запомнить меня
            </label>
            <br>
            <%--onclick="document.getElementById('id03').style.display='block'"--%>
            <div class="registrationLinkDiv"> <a class="registrationLink" href="reg.jsp">Не зарегистрированы?</a></div>
            <br>
            <br>
            <br>
            <div class="wrongAuthorization"><a>Не верно введенные данные, повторите ввод</a></div>

            <div class="clearfix">
                <button type="button" onclick="document.getElementById('id02').style.display='none'" class="cancelbtn">Отменить</button>
                <button type="submit" class="signupbtn">Отправить заявку</button>
            </div>

</div>
    </form>
</div>

<%--<div id="id03" class="modal mod3">--%>
    <%--<span onclick="document.getElementById('id03').style.display='none'" class="close" title="Close Modal">&times;</span>--%>
    <%--<form class="modal-content" action="/registration">--%>
        <%--<div class="container">--%>
            <%--<h1>Регистрация</h1>--%>
            <%--<p>Пожалуйста, введите необходимые данные для регистрации</p>--%>
            <%--<hr>--%>
            <%--<label for="name"><b>Имя</b></label>--%>
            <%--<input type="text" placeholder="Имя" name="name" required>--%>
            <%--<div class="wrongName">Имя указано неправильно</div>--%>
            <%--<br>--%>
            <%--<label for="surname"><b>Фамилия</b></label>--%>
            <%--<input type="text" placeholder="Фамилия" name="surname" required>--%>
            <%--<div class="wrongSurname">Фамилия указана неправильно</div>--%>
            <%--<br>--%>
            <%--<label for="login"><b>Логин</b></label>--%>
            <%--<input type="text" placeholder="Логин" name="login" required>--%>
            <%--<div class="wrongLogin">Логин указан неправильно</div>--%>
            <%--<div class="busyLogin">Логин уже занят</div>--%>
            <%--<br>--%>
            <%--<label for="pas"><b>Пароль</b></label>--%>
            <%--<input type="password" placeholder="Пароль" name="pas" required>--%>
            <%--<div class="wrongPassword">Логин указан неправильно</div>--%>
            <%--<div class="busyPassword">Логин уже занят</div>--%>
            <%--<br>--%>
            <%--<label for="email"><b>Email</b></label>--%>
            <%--<input type="text" placeholder="Email" name="email" required>--%>
            <%--<div class="wrongEmail">Email указан неправильно</div>--%>
            <%--<div class="busyEmail">Email уже занят</div>--%>

            <%--&lt;%&ndash;<label>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<input type="checkbox" checked="checked" name="remember" style="margin-bottom:15px"> Запомнить меня&ndash;%&gt;--%>
            <%--&lt;%&ndash;</label>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<br>&ndash;%&gt;--%>
            <%--&lt;%&ndash;&ndash;%&gt;--%>
            <%--&lt;%&ndash;<br>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<br>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div class="wrongAuthorization"><a>Не верно введенные данные, повторите ввод</a></div>&ndash;%&gt;--%>

            <%--<div class="clearfix">--%>
                <%--<button type="button" onclick="document.getElementById('id03').style.display='none'" class="cancelbtn">Отменить</button>--%>
                <%--<button type="submit" class="signupbtn">Зарегистрироваться</button>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</form>--%>
<%--</div>--%>


<div class="footer">

</div>


<script>
    // Get the modal
    var modal = document.getElementById('id01');

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
</script>

</body>
</html>









<%--<div class="container-fluid">--%>
    <%--<div class="name row">--%>
        <%--<div class="col-sm-12">--%>
            <%--<a href="#">Ремонтное агенство автомобилей</a>--%>
        <%--</div>--%>
    <%--</div>--%>


    <%--<div class="header-right">--%>
    <%--<a class="active" href="#home">Главная</a>--%>
    <%--<a href="#contact">Услуги</a>--%>
    <%--<a href="#auht">Авторизация</a>--%>
    <%--<a href="#about">О нас</a>--%>
    <%--<a href="#contacts">Контакты</a>--%>
    <%--</div>--%>


    <%--<div class="row">--%>
        <%--<div class="col-4" style="background-color:lavender;">--%>
            <%--Главная--%>
        <%--</div>--%>
        <%--<div class="col-4" style="background-color:lavenderblush;">Услуги</div>--%>
        <%--<div class="col-4" style="background-color:lavender;">Авторизация</div>--%>
        <%--<div class="col-4" style="background-color:lavender;">О нас</div>--%>
        <%--<div class="col-4" style="background-color:lavender;">Контакты</div>--%>
    <%--</div>--%>





