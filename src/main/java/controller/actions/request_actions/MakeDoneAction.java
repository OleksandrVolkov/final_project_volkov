package controller.actions.request_actions;

import controller.actions.Action;
import model.dao.RequestDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MakeDoneAction implements Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
        Integer id = Integer.parseInt(request.getParameter("cancelButton"));
        Request curRequest = requestDAO.findEntityById(id);
        curRequest.setStatus("done");
        System.out.println(curRequest.getStatus()+"########################################################################################################################################");
        requestDAO.update(curRequest, id);

        String url = "/master";
        return url;
    }
}













//        try {
//            request_actions.getRequestDispatcher("/master").forward(request_actions, response);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }