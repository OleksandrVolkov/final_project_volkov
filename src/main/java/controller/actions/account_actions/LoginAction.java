package controller.actions.account_actions;

import controller.actions.Action;
import model.Language;
import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.User;
import model.utility.LanguageHandler;
import model.utility.MD5Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoginAction implements Action{

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String username = req.getParameter("login");
        String password = req.getParameter("pas");


        System.out.println(req.getParameter("lang") + "???????");
        String lang = req.getParameter("lang");
        System.out.println("PRE LANG");
        if(lang == null || lang.equals(""))
            lang = "en";
        System.out.println("LANG " + lang);

        String url = "";

        if (username == null || password == null) {
            Map<String, String> authForm = null;
            switch (lang) {
                case "en":
                    System.out.println("EEEEENNNNNN");
                    authForm = LanguageHandler.getHashMapOfAuthorizationForm(Language.ENGLISH);
                    authForm.put("lang", "en");
                    break;
                case "ru":
                    System.out.println("RRRRRUUUUU");
                    authForm = LanguageHandler.getHashMapOfAuthorizationForm(Language.RUSSIAN);
                    authForm.put("lang", "ru");
                    break;
                case "ua":
                    System.out.println("UUUUUAAAAAA");
                    authForm = LanguageHandler.getHashMapOfAuthorizationForm(Language.UKRAINIAN);
                    authForm.put("lang", "ua");
                    break;
                default:
                    break;
            }


            System.out.println(authForm+"________");
            req.setAttribute("language", authForm);
//            resp.sendRedirect("authorization.jsp");
//            req.getRequestDispatcher("authorization.jsp").forward(req, resp);
            url = "";
        } else {
            password = MD5Handler.md5Custom(password);

            UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
            if (userDAO.validateUser(username, password)) {
//                User userBean = userDAO.findUserByUsername(login);
                User user = userDAO.findUserByUsername(username);
                if(user.getRole().equals("client")){
                    req.getSession().setAttribute("LOGGED_USER", username);
                    String loginSession = (String)req.getSession().getAttribute("LOGGED_USER");
                    System.out.println("SESSION FOUND " + loginSession);
//                    resp.sendRedirect("/lang");
                    url = "/lang";
                } else if(user.getRole().equals("manager")){
                    req.getSession().setAttribute("LOGGED_MANAGER", username);
                    String loginSession = (String)req.getSession().getAttribute("LOGGED_MANAGER");
                    System.out.println("SESSION FOUND " + loginSession);
                    System.out.println("MANAGER");
//                    resp.sendRedirect("manager.jsp");
                    url = "/managerserv";
                    System.out.println(url);
                } else if(user.getRole().equals("master")){
                    req.getSession().setAttribute("LOGGED_MASTER", username);
                    String loginSession = (String)req.getSession().getAttribute("LOGGED_MASTER");
                    System.out.println("SESSION FOUND " + loginSession);
                    System.out.println("MASTER");
//                    req.getRequestDispatcher("/master").forward(req, resp);
                    url = "/master";
                    System.out.println(url);
                }

                //                req.getSession().setAttribute("LOGGED_USER", username);
                //                String loginSession = (String)req.getSession().getAttribute("LOGGED_USER");
                ////                if (loggedUser == null) {
                //                    resp.sendRedirect("/login.jsp");
                //                }
                //                System.out.println("SESSION FOUND"+loggedUser);


//                resp.sendRedirect("/lang");
            } else {
//                resp.sendRedirect("reg.jsp");


                //!!!!!!!!!!!!!!!!!
                url = "/lang";
            }
        }
        System.out.println("ONCE MORE " + url);
        return url;
    }
}
