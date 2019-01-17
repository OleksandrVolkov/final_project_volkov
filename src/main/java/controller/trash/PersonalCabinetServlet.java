package controller.trash;

import model.dao.RequestDAO;
import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Request;
import model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "personalCab", urlPatterns = {"/personalCab"})
public class PersonalCabinetServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//            String loginSession = (String)req.getSession().getAttribute("LOGGED_USER");
//            if(loginSession != null){
//                System.out.println("SESSION FOUND " + loginSession);
////                if(req.getParameter("currentPage") == null)
//                    req.setAttribute("currentPage", 1);
//
//                req.setAttribute("recordsPerPage", 5);
//                RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
//                UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
//                List<Request> requests = requestDAO.findAll();
//                System.out.println("?????" + requests);
//
//                List<Request> temp = new ArrayList<>();
//
//                for(Request request_actions : requests){
//                    User user = userDAO.findEntityById(request_actions.getUserId());
//                    if(user != null)
//                        if(user.getLogin().equals(loginSession))
//                            temp.add(request_actions);
//                }
//
//
////                req.setAttribute("requests", requestDAO.findAll());
//                req.setAttribute("requests", temp);
//
//
//                req.getRequestDispatcher("personalCabinet2.jsp").forward(req, resp);
////                resp.sendRedirect("/lang");
//            } else {
//                resp.sendRedirect("/lang");
//            }

    }

    private static List<Request> getLimitedRequestsByUserID(int currentPage, int recordsPerPage, int userId){
        return new RequestDAO(ConnectionManager.getConnection()).findLimitRequests(currentPage, recordsPerPage, userId);
    }
}
