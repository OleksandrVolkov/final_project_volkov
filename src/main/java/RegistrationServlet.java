import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.User;
import model.utility.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isValidName = true;
        boolean isValidSurname = true;
        boolean isValidEmail = true;
        boolean isValidLogin = true;
        boolean isValidPassword = true;

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




        if(isValidName == false || isValidSurname == false || isValidLogin == false || isValidEmail == false)
            resp.sendRedirect("reg.jsp?isValidName=" + isValidName + "&isValidSurname=" +
                    isValidSurname + "&isValidEmail=" + isValidEmail + "&isValidLogin=" +
                    isValidLogin + "&isValidPassword=" + isValidPassword);
        else {
            User user = new User(req.getParameter("name"), req.getParameter("surname"), req.getParameter("login"),
                    req.getParameter("pas"), req.getParameter("email"), "client");
            UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
            userDAO.create(user);
            System.out.println(userDAO.findEntityById(user.getId()));
            resp.sendRedirect("main.jsp");
        }
    }
}
