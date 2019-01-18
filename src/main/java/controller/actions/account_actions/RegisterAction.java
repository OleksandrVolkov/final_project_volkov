package controller.actions.account_actions;

import controller.actions.Action;
import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.User;
import model.utility.Validator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterAction implements Action {


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {


//        boolean isValidName = true;
//        boolean isValidSurname = true;
//        boolean isValidEmail = true;
//        boolean isValidLogin = true;
//        boolean isValidPassword = true;
//        boolean isBusyLogin = true;
//        boolean isBusyEmail = true;

        Validator validator = new Validator();
        String url = "";
        if (req.getParameter("name") == null) {
            url = this.firstLoad(req, resp);
        } else {
            url = this.check(req, resp);
        }


//        if(!validator.isValidName(req.getParameter("name")))
//            isValidName = false;
//        if(!validator.isValidSurname(req.getParameter("surname")))
//            isValidSurname = false;
//        if(!validator.isValidEmail(req.getParameter("email")))
//            isValidEmail = false;
//        if(!validator.isValidLogin(req.getParameter("login")))
//            isValidLogin = false;
//        if(!validator.isValidPassword(req.getParameter("pas")))
//            isValidPassword = false;
//
//        UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
//        if(userDAO.isEmailTaken(req.getParameter("email")))
//            isBusyEmail = false;
//        if(userDAO.isUsernameTaken(req.getParameter("login")))
//            isBusyLogin = false;
//
//
//
//        if(isValidName == false || isValidSurname == false || isValidLogin == false || isValidEmail == false || isBusyEmail == false || isBusyLogin == false) {
////            url = "reg.jsp?isValidName=" + isValidName + "&isValidSurname=" +
////                    isValidSurname + "&isValidEmail=" + isValidEmail + "&isValidLogin=" +
////                    isValidLogin + "&isValidPassword=" + isValidPassword + "&isBusyLogin=" +
////                    isBusyLogin + "&isBusyEmail=" + isBusyEmail;
//            req.setAttribute("isValidName", isValidName);
//            req.setAttribute("isValidSurname", isValidSurname);
//            req.setAttribute("isValidPassword", isValidPassword);
//            req.setAttribute("isBusyLogin", isBusyLogin);
//            req.setAttribute("isValidLogin", isValidLogin);
//            req.setAttribute("isBusyEmail", isBusyEmail);
//            req.setAttribute("isValidEmail", isValidEmail);
//            url = "/lang";
//        }
//        else {
//            User user = new User(req.getParameter("name"), req.getParameter("surname"), req.getParameter("login"),
//                    req.getParameter("pas"), req.getParameter("email"), "client");
//            userDAO.create(user);
//
//            req.getSession().setAttribute("LOGGED_USER", req.getParameter("login"));
//            String loginSession = (String) req.getSession().getAttribute("LOGGED_USER");
//            System.out.println("SESSION FOUND " + loginSession);
//            url = "/lang";
//        }


//        url = "/lang";

        return url;
    }

    public String firstLoad(HttpServletRequest req, HttpServletResponse resp) {
        boolean isValidName = true;
        boolean isValidSurname = true;
        boolean isValidEmail = true;
        boolean isValidLogin = true;
        boolean isValidPassword = true;
        boolean isBusyLogin = true;
        boolean isBusyEmail = true;

        req.setAttribute("isValidName", isValidName);
        req.setAttribute("isValidSurname", isValidSurname);
        req.setAttribute("isValidPassword", isValidPassword);
        req.setAttribute("isBusyLogin", isBusyLogin);
        req.setAttribute("isValidLogin", isValidLogin);
        req.setAttribute("isBusyEmail", isBusyEmail);
        req.setAttribute("isValidEmail", isValidEmail);
        return "/reg.jsp";
    }

    public String check(HttpServletRequest req, HttpServletResponse resp) {
        boolean isValidName = true;
        boolean isValidSurname = true;
        boolean isValidEmail = true;
        boolean isValidLogin = true;
        boolean isValidPassword = true;
        boolean isBusyLogin = true;
        boolean isBusyEmail = true;

        Validator validator = new Validator();
        String url = "";
//        if(req.getParameter("name") == null){
//            this.firstLoad();
//        }


        if (!validator.isValidName(req.getParameter("name")))
            isValidName = false;
        if (!validator.isValidSurname(req.getParameter("surname")))
            isValidSurname = false;
        if (!validator.isValidEmail(req.getParameter("email")))
            isValidEmail = false;
        if (!validator.isValidLogin(req.getParameter("login")))
            isValidLogin = false;
        if (!validator.isValidPassword(req.getParameter("pas")))
            isValidPassword = false;

        UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
        if (userDAO.isEmailTaken(req.getParameter("email")))
            isBusyEmail = false;
        if (userDAO.isUsernameTaken(req.getParameter("login")))
            isBusyLogin = false;


        if (isValidName == false || isValidSurname == false || isValidLogin == false || isValidEmail == false || isBusyEmail == false || isBusyLogin == false) {
//            url = "reg.jsp?isValidName=" + isValidName + "&isValidSurname=" +
//                    isValidSurname + "&isValidEmail=" + isValidEmail + "&isValidLogin=" +
//                    isValidLogin + "&isValidPassword=" + isValidPassword + "&isBusyLogin=" +
//                    isBusyLogin + "&isBusyEmail=" + isBusyEmail;
            req.setAttribute("isValidName", isValidName);
            req.setAttribute("isValidSurname", isValidSurname);
            req.setAttribute("isValidPassword", isValidPassword);
            req.setAttribute("isBusyLogin", isBusyLogin);
            req.setAttribute("isValidLogin", isValidLogin);
            req.setAttribute("isBusyEmail", isBusyEmail);
            req.setAttribute("isValidEmail", isValidEmail);
            url = "/reg.jsp";
        } else {
            User user = new User(req.getParameter("name"), req.getParameter("surname"), req.getParameter("login"),
                    req.getParameter("pas"), req.getParameter("email"), "client");
            userDAO.create(user);

            req.getSession().setAttribute("LOGGED_USER", req.getParameter("login"));
            String loginSession = (String) req.getSession().getAttribute("LOGGED_USER");
            System.out.println("SESSION FOUND " + loginSession);
            url = "/lang";
        }
        return url;
    }
}
