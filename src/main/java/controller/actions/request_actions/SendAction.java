package controller.actions.request_actions;

import controller.actions.Action;
import model.RequestStatus;
import model.dao.ItemDAO;
import model.dao.RequestDAO;
import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Item;
import model.entity.Request;
import model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String comment = req.getParameter("comment");
        String itemId = req.getParameter("itemID");
//        String status = "not seen";

        String status = RequestStatus.NOT_SEEN.toString();


        Integer id = Integer.parseInt(itemId);
        ItemDAO itemDAO = new ItemDAO(ConnectionManager.getConnection());
        Item item = itemDAO.findEntityById(id);


        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());

        String loginSession = (String)req.getSession().getAttribute("LOGGED_USER");

        if(loginSession != null){
            UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
            User user = userDAO.findUserByUsername(loginSession);
            System.out.println(item);
            Request request = new Request(comment, status, user.getId(), item.getId());

            requestDAO.create(request);
        }

        String url = "/lang";
        return url;
    }
}













//        resp.sendRedirect("main.jsp");