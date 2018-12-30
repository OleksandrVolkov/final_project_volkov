<%--
  Created by IntelliJ IDEA.
  User: volkov_o_o
  Date: 23.12.18
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
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
        width: 700px;
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

    .wrong{
        margin-left:50%;
        margin-top: -55px;
        position: absolute;
        color: red;
        display: none;
    }

    .wrongName{

    }
    .wrongSurname{

    }
    .wrongEmail{

    }
    .wrongLogin{
    }
    .wrongPassword{

    }

    .mod3{
        padding-top:0px;
    }

    .busyEmail{

    }
    .busyLogin{

    }

</style>
<body>

<%--<h2>Modal Signup Form</h2>--%>

<%--<button onclick="document.getElementById('id01').style.display='block'" style="width:auto;">Sign Up</button>--%>

<%--<div id="id01" class="modal">--%>
    <%--<span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>--%>
    <%--<form class="modal-content" action="/action_page.php">--%>
        <%--<div class="container">--%>
            <%--<h1>Sign Up</h1>--%>
            <%--<p>Please fill in this form to create an account.</p>--%>
            <%--<hr>--%>
            <%--<label for="email"><b>Email</b></label>--%>
            <%--<input type="text" placeholder="Enter Email" name="email" required>--%>

            <%--<label for="psw"><b>Password</b></label>--%>
            <%--<input type="password" placeholder="Enter Password" name="psw" required>--%>

            <%--<label for="psw-repeat"><b>Repeat Password</b></label>--%>
            <%--<input type="password" placeholder="Repeat Password" name="psw-repeat" required>--%>

            <%--<label for="login"><b>Login</b></label>--%>
            <%--<input type="text" placeholder="Login" name="login" required>--%>

            <%--<label for="name"><b>Name</b></label>--%>
            <%--<input type="text" placeholder="Name" name="name" required>--%>

            <%--<label for="surname"><b>Surname</b></label>--%>
            <%--<input type="text" placeholder="Surname" name="surname" required>--%>

            <%--<label>--%>
                <%--<input type="checkbox" checked="checked" name="remember" style="margin-bottom:15px"> Remember me--%>
            <%--</label>--%>

            <%--<p>By creating an account you agree to our <a href="#" style="color:dodgerblue">Terms & Privacy</a>.</p>--%>

            <%--<div class="clearfix">--%>
                <%--<button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>--%>
                <%--<button type="submit" class="signupbtn">Sign Up</button>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</form>--%>
<%--</div>--%>


<div id="id01" class="modal mod3">
    <%--<span onclick="document.getElementById('id03').style.display='none'" class="close" title="Close Modal">&times;</span>--%>
        <a href="main.jsp">
            <span onclick="hide(); return false" class="close" title="Close Modal">&times;</span>
        </a>
    <form class="modal-content" action="/registration">
        <div class="container">
            <h1>Регистрация</h1>
            <p>Пожалуйста, введите необходимые данные для регистрации</p>
            <hr>
            <label for="name"><b>Имя</b></label>
            <input type="text" placeholder="Имя" name="name" required>
            <div class="wrong" id="wrongName">Имя указано неправильно</div>
            <br>
            <label for="surname"><b>Фамилия</b></label>
            <input type="text" placeholder="Фамилия" name="surname" required>
            <div class="wrong" id="wrongSurname">Фамилия указана неправильно</div>
            <br>
            <label for="login"><b>Логин</b></label>
            <input type="text" placeholder="Логин" name="login" required>
            <div class="wrong" id="wrongLogin">Логин указан неправильно</div>
            <div class="wrong" id="busyLogin">Логин уже занят</div>
            <br>
            <label for="pas"><b>Пароль</b></label>
            <input type="password" placeholder="Пароль" name="pas" required>
            <div class="wrong" id="wrongPassword">Пароль указан неправильно</div>
            <br>
            <label for="email"><b>Email</b></label>
            <input type="text" placeholder="Email" name="email" required>
            <div class="wrong" id="wrongEmail">Email указан неправильно</div>
            <div class="wrong" id="busyEmail">Email уже занят</div>

            <%--<label>--%>
            <%--<input type="checkbox" checked="checked" name="remember" style="margin-bottom:15px"> Запомнить меня--%>
            <%--</label>--%>
            <%--<br>--%>
            <%----%>
            <%--<br>--%>
            <%--<br>--%>
            <%--<div class="wrongAuthorization"><a>Не верно введенные данные, повторите ввод</a></div>--%>

            <div class="clearfix">
                <button type="button" onclick="document.getElementById('id03').style.display='none'" class="cancelbtn">Отменить</button>
                <button type="submit" class="signupbtn">Зарегистрироваться</button>
            </div>
        </div>
    </form>
</div>

<script>
    var url = new URL(document.URL);
    var isValidSurname = url.searchParams.get("isValidSurname");
    var isValidaPas = url.searchParams.get("isValidPassword");
    if(url.searchParams.get("isValidPassword") == 'false')
        document.getElementById('wrongPassword').style.display = "block";
    if(url.searchParams.get("isValidLogin") == 'false')
        document.getElementById('wrongLogin').style.display = "block";
    if(url.searchParams.get("isValidName") == 'false')
        document.getElementById('wrongName').style.display = "block";
    if(url.searchParams.get("isValidSurname") == 'false')
        document.getElementById('wrongSurname').style.display = "block";
    if(url.searchParams.get("isValidEmail") == 'false')
        document.getElementById('wrongEmail').style.display = "block";
    if(false)
        document.getElementById('busyLogin').style.display = "block";
    if(false)
        document.getElementById('busyEmail').style.display = "block";


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


