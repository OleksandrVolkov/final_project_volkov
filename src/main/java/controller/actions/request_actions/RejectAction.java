package controller.actions.request_actions;

import controller.actions.Action;
import model.RequestStatus;
import model.dao.RequestDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Reject;
import model.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RejectAction implements Action {
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String reason = req.getParameter("reason");
        Integer requestId = Integer.parseInt(req.getParameter("cur_request_id"));
        System.out.println(reason);

        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
        Request request = requestDAO.findEntityById(requestId);
        request.setStatus(RequestStatus.REJECTED.toString());
        request.setReject(new Reject(reason, requestId));

        requestDAO.update(request, request.getId());
        String url = "/managerserv";
        return url;
    }
}

















//        try {
//            req.getRequestDispatcher("/managerserv").forward(req, resp);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }