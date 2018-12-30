import model.dao.RequestDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/request")
public class RequestServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String comment = req.getParameter("comment");
        String item = req.getParameter("selectItem");
        System.out.println(comment);
        System.out.println(item);
        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
        requestDAO.create(new Request(comment, item));

        resp.sendRedirect("main.jsp");
    }
}
