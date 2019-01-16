package controller;

import model.dao.FeedbackDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Feedback;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "feedback", urlPatterns = {"/feedback"})
public class FeedbackServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("feedback");
        Integer requestId = Integer.parseInt(req.getParameter("cur_request_id"));
        FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        feedbackDAO.create(new Feedback(text, "2019-06-01", requestId));
        req.getRequestDispatcher("/client").forward(req, resp);
    }
}
