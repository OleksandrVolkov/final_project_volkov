package controller.trash;

import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "exit", urlPatterns = {"/exit"})
public class ExitServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getSession().setAttribute("LOGGED_USER", null);
//        String role = (String) req.getSession().getAttribute("role");
//        if(role!=null)
//            switch (role){
//                case "client":
//                    req.getSession().setAttribute("LOGGED_USER", null);
//                    break;
//                case "manager":
//                    req.getSession().setAttribute("LOGGED_MANAGER", null);
//                    break;
//                case "master":
//                    req.getSession().setAttribute("LOGGED_MASTER", null);
//                    break;
//                default:
//                    break;
//            }
//
//        req.getRequestDispatcher("/lang").forward(req, resp);
    }
}
