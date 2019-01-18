package controller.actions.account_actions;

import controller.actions.Action;
import model.Language;
import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.User;
import model.utility.LanguageHandler;
import model.utility.MD5Handler;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoginAction implements Action{
    private static Logger log = Logger.getLogger(LoginAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        log.trace("Getting login and password values");
        String username = req.getParameter("login");
        String password = req.getParameter("pas");
        log.debug("Username: " + username);

//        System.out.println(req.getParameter("lang") + "???????");
        log.debug("Language short name" + req.getParameter("lang"));
        String lang = req.getParameter("lang");
        if(lang == null || lang.equals(""))
            lang = "en";
        log.debug("Language short name" + lang);

        String url = "";
        log.trace("checking whether values equal to null");
        if (username == null || password == null) {
            Map<String, String> authForm = null;
            switch (lang) {
                case "en":
                    log.trace("English is reached");
                    authForm = LanguageHandler.getHashMapOfAuthorizationForm(Language.ENGLISH);
                    authForm.put("lang", "en");
                    break;
                case "ru":
                    log.trace("Russian is reached");
                    authForm = LanguageHandler.getHashMapOfAuthorizationForm(Language.RUSSIAN);
                    authForm.put("lang", "ru");
                    break;
                case "ua":
                    log.trace("Ukrainian is reached");
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
