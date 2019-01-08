import model.dao.ItemDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Item;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "preRequset", urlPatterns = {"/preRequest"})
public class PreRequestServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ItemDAO itemDAO = new ItemDAO(ConnectionManager.getConnection());
        List<Item> items = itemDAO.findAll();
        req.setAttribute("itemsAvailable", items);
        req.getRequestDispatcher("request.jsp").forward(req, resp);
    }
}
