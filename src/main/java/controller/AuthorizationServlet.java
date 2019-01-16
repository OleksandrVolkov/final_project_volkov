package controller;

import model.Language;
import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.User;
import model.utility.LanguageHandler;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/authorization")
public class AuthorizationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("pas");


        System.out.println(req.getParameter("lang") + "???????");
        if (login == null || password == null) {

            Map<String, String> authForm = null;
            switch (req.getParameter("lang")) {
                case "en":
                    authForm = LanguageHandler.getHashMapOfAuthorizationForm(Language.ENGLISH);
                    authForm.put("lang", "en");
                    break;
                case "ru":
                    authForm = LanguageHandler.getHashMapOfAuthorizationForm(Language.RUSSIAN);
                    authForm.put("lang", "ru");
                    break;
                case "ua":
                    authForm = LanguageHandler.getHashMapOfAuthorizationForm(Language.UKRAINIAN);
                    authForm.put("lang", "ua");
                    break;
                default:
                    break;
            }


            System.out.println(authForm+"________");
            req.setAttribute("language", authForm);
            resp.sendRedirect("authorization.jsp");
        } else {
            password = md5Custom(password);

            UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
            if (userDAO.validateUser(login, password)) {
//                User userBean = userDAO.findUserByUsername(login);
                req.getSession().setAttribute("LOGGED_USER", login);

                String loginSession = (String)req.getSession().getAttribute("LOGGED_USER");
//                if (loggedUser == null) {
//                    resp.sendRedirect("/login.jsp");
//                }
//                System.out.println("SESSION FOUND"+loggedUser);

                System.out.println("SESSION FOUND " + loginSession);
                resp.sendRedirect("/lang");
            } else {
                resp.sendRedirect("reg.jsp");
            }
        }
    }

    public static String md5Custom(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            // тут можно обработать ошибку
            // возникает она если в передаваемый алгоритм в getInstance(,,,) не существует
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}



