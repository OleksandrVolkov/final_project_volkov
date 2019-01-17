package controller.actions.account_actions;

import controller.actions.Action;
import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.User;
import model.utility.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterAction implements Action{


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        boolean isValidName = true;
        boolean isValidSurname = true;
        boolean isValidEmail = true;
        boolean isValidLogin = true;
        boolean isValidPassword = true;
        boolean isBusyLogin = true;
        boolean isBusyEmail = true;

        Validator validator = new Validator();
        if(!validator.isValidName(req.getParameter("name")))
            isValidName = false;
        if(!validator.isValidSurname(req.getParameter("surname")))
            isValidSurname = false;
        if(!validator.isValidEmail(req.getParameter("email")))
            isValidEmail = false;
        if(!validator.isValidLogin(req.getParameter("login")))
            isValidLogin = false;
        if(!validator.isValidPassword(req.getParameter("pas")))
            isValidPassword = false;

        UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
        if(userDAO.isEmailTaken(req.getParameter("email")))
            isBusyEmail = false;
        if(userDAO.isUsernameTaken(req.getParameter("login")))
            isBusyLogin = false;


        String url = "";

        if(isValidName == false || isValidSurname == false || isValidLogin == false || isValidEmail == false || isBusyEmail == false || isBusyLogin == false)
//            resp.sendRedirect("reg.jsp?isValidName=" + isValidName + "&isValidSurname=" +
//                    isValidSurname + "&isValidEmail=" + isValidEmail + "&isValidLogin=" +
//                    isValidLogin + "&isValidPassword=" + isValidPassword + "&isBusyLogin=" +
//                    isBusyLogin + "&isBusyEmail=" + isBusyEmail);
            url = "reg.jsp?isValidName=" + isValidName + "&isValidSurname=" +
                    isValidSurname + "&isValidEmail=" + isValidEmail + "&isValidLogin=" +
                    isValidLogin + "&isValidPassword=" + isValidPassword + "&isBusyLogin=" +
                    isBusyLogin + "&isBusyEmail=" + isBusyEmail;
        else {
            User user = new User(req.getParameter("name"), req.getParameter("surname"), req.getParameter("login"),
                    req.getParameter("pas"), req.getParameter("email"), "client");
//            UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
            userDAO.create(user);

            req.getSession().setAttribute("LOGGED_USER", req.getParameter("login"));
            String loginSession = (String) req.getSession().getAttribute("LOGGED_USER");
            System.out.println("SESSION FOUND " + loginSession);
//            resp.sendRedirect("/lang");
            url = "/lang";
        }

        return url;
    }
}
