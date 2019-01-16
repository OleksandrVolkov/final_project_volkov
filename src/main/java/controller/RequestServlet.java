package controller;

import model.dao.ItemDAO;
import model.dao.RequestDAO;
import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Item;
import model.entity.Request;
import model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/request")
public class RequestServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String comment = req.getParameter("comment");
        String itemId = req.getParameter("itemID");
        String status = "not seen";


        Integer id = Integer.parseInt(itemId);
        ItemDAO itemDAO = new ItemDAO(ConnectionManager.getConnection());
        Item item = itemDAO.findEntityById(id);


        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());

        String loginSession = (String)req.getSession().getAttribute("LOGGED_USER");

        if(loginSession != null){
            UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
            User user = userDAO.findUserByUsername(loginSession);
            System.out.println(item);

            Request request = new Request(comment, status, user.getId());
            request.addItem(item);
            requestDAO.create(request);
        }
        resp.sendRedirect("main.jsp");




    }
}
