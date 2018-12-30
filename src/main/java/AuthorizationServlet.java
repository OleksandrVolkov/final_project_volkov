import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

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
        UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
        try {
            if(userDAO.validateUser(login, password)){
                resp.sendRedirect("main.jsp");
            }else {
                resp.sendRedirect("reg.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}



