package controller.trash;

import model.dao.RequestDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="doneReq", urlPatterns = {"/doneReq"})
public class DoneRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        changeStatus(req, "done");
//        req.getRequestDispatcher("/master").forward(req, resp);
    }

    public void changeStatus(HttpServletRequest req, String status){
        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
        Integer id = Integer.parseInt(req.getParameter("cancelButton"));
        Request request = requestDAO.findEntityById(id);
        request.setStatus(status);
        requestDAO.update(request, id);
    }
}

