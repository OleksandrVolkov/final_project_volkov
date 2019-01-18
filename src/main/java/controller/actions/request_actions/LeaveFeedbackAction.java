package controller.actions.request_actions;

import controller.actions.Action;
import model.dao.FeedbackDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Feedback;
import model.utility.DateHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LeaveFeedbackAction implements Action{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String text = req.getParameter("feedback");
        Integer requestId = Integer.parseInt(req.getParameter("cur_request_id"));
        FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        DateHandler dateHandler = new DateHandler();
        feedbackDAO.create(new Feedback(text, dateHandler.getCurrentDate(), requestId));
        String url = "/client";
        return url;
    }
}
