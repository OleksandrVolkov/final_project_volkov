package controller;

import controller.actions.Action;
import controller.actions.request_actions.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "request", urlPatterns = {"/request"})
public class RequestServlet extends HttpServlet{
    private Map<String,Action> actionMap = new HashMap<String,Action>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        actionMap.put("accept", new AcceptAction());
        actionMap.put("done", new MakeDoneAction());
        actionMap.put("reject", new RejectAction());
        actionMap.put("set", new SetItemsAction());
        actionMap.put("send", new SendAction());
        actionMap.put("feedback", new LeaveFeedbackAction());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(actionMap);
        String actionKey = req.getParameter("action");
        System.out.println("Action key = " + actionKey);
        String url = "";
        Action action = actionMap.get(actionKey);
        try {
            url = action.execute(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("URL REQUEST:" + url);
        if(url == null)
            url = "/lang";

        req.getRequestDispatcher(url).forward(req, resp);
    }
}





























//        String comment = req.getParameter("comment");
//        String itemId = req.getParameter("itemID");
////        String status = "not seen";
//
//        String status = RequestStatus.NOT_SEEN.toString();
//
//
//        Integer id = Integer.parseInt(itemId);
//        ItemDAO itemDAO = new ItemDAO(ConnectionManager.getConnection());
//        Item item = itemDAO.findEntityById(id);
//
//
//        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
//
//        String loginSession = (String)req.getSession().getAttribute("LOGGED_USER");
//
//        if(loginSession != null){
//            UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
//            User user = userDAO.findUserByUsername(loginSession);
//            System.out.println(item);
//
//            Request request_actions = new Request(comment, status, user.getId(), item.getId());
////            request_actions.setItem(item);
//
//            requestDAO.create(request_actions);
//        }
//        resp.sendRedirect("main.jsp");








//@WebServlet("/request_actions")
//public class RequestServlet extends HttpServlet{
//    private Map<String,Action> actionMap = new HashMap<String,Action>();
//
//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        actionMap.put("cancel", new AcceptAction());
//        actionMap.put("done", new MakeDoneAction());
//        actionMap.put("cancel", new RejectAction());
//        actionMap.put("set", new SetItemsAction());
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        this.doPost(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String comment = req.getParameter("comment");
//        String itemId = req.getParameter("itemID");
////        String status = "not seen";
//
//        String status = RequestStatus.NOT_SEEN.toString();
//
//
//        Integer id = Integer.parseInt(itemId);
//        ItemDAO itemDAO = new ItemDAO(ConnectionManager.getConnection());
//        Item item = itemDAO.findEntityById(id);
//
//
//        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
//
//        String loginSession = (String)req.getSession().getAttribute("LOGGED_USER");
//
//        if(loginSession != null){
//            UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
//            User user = userDAO.findUserByUsername(loginSession);
//            System.out.println(item);
//
//            Request request_actions = new Request(comment, status, user.getId(), item.getId());
////            request_actions.setItem(item);
//
//            requestDAO.create(request_actions);
//        }
//        resp.sendRedirect("main.jsp");
//    }
//
//
//    public void create(){
//
//    }
//    public void setItems(HttpServletRequest req, HttpServletResponse resp){
//        //LANGAUGE
//
//        ItemDAO itemDAO = new ItemDAO(ConnectionManager.getConnection());
//        List<Item> items = itemDAO.findAll();
//        req.setAttribute("itemsAvailable", items);
//        try {
//            req.getRequestDispatcher("request_actions.jsp").forward(req, resp);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void accept(HttpServletRequest req, HttpServletResponse resp){
//        String newStatus = "is being seen";
//        String request_id = req.getParameter("cur_request_id");
//        Double price = Double.parseDouble(req.getParameter("price"));
//
//        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
//        Integer requestId = Integer.parseInt(request_id);
//        Request request_actions = requestDAO.findEntityById(requestId);
//        request_actions.setStatus(newStatus);
//        request_actions.setPrice(price);
//        requestDAO.update(request_actions, request_actions.getId());
//
//        try {
//            req.getRequestDispatcher("/managerserv").forward(req, resp);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void cancel(HttpServletRequest req, HttpServletResponse resp){
//        String reason = req.getParameter("reason");
//        Integer requestId = Integer.parseInt(req.getParameter("cur_request_id"));
//        System.out.println(reason);
//
//        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
//        Request request_actions = requestDAO.findEntityById(requestId);
//        request_actions.setStatus("rejected");
//        request_actions.setReject(new Reject(reason, requestId));
//
//        requestDAO.update(request_actions, request_actions.getId());
//
//        try {
//            req.getRequestDispatcher("/managerserv").forward(req, resp);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void makeDone(HttpServletRequest req, HttpServletResponse resp){
////        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
////        Integer id = Integer.parseInt(req.getParameter("cancelButton"));
////        Request request_actions = requestDAO.findEntityById(id);
////        request_actions.setStatus("done");
////        requestDAO.update(request_actions, id);
////        try {
////            req.getRequestDispatcher("/master").forward(req, resp);
////        } catch (ServletException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
//
//
//}

//    public void create(){
//
//    }
//    public void setItems(HttpServletRequest req, HttpServletResponse resp){
//        //LANGAUGE
//
//        ItemDAO itemDAO = new ItemDAO(ConnectionManager.getConnection());
//        List<Item> items = itemDAO.findAll();
//        req.setAttribute("itemsAvailable", items);
//        try {
//            req.getRequestDispatcher("request_actions.jsp").forward(req, resp);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void accept(HttpServletRequest req, HttpServletResponse resp){
//        String newStatus = "is being seen";
//        String request_id = req.getParameter("cur_request_id");
//        Double price = Double.parseDouble(req.getParameter("price"));
//
//        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
//        Integer requestId = Integer.parseInt(request_id);
//        Request request_actions = requestDAO.findEntityById(requestId);
//        request_actions.setStatus(newStatus);
//        request_actions.setPrice(price);
//        requestDAO.update(request_actions, request_actions.getId());
//
//        try {
//            req.getRequestDispatcher("/managerserv").forward(req, resp);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void cancel(HttpServletRequest req, HttpServletResponse resp){
//        String reason = req.getParameter("reason");
//        Integer requestId = Integer.parseInt(req.getParameter("cur_request_id"));
//        System.out.println(reason);
//
//        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
//        Request request_actions = requestDAO.findEntityById(requestId);
//        request_actions.setStatus("rejected");
//        request_actions.setReject(new Reject(reason, requestId));
//
//        requestDAO.update(request_actions, request_actions.getId());
//
//        try {
//            req.getRequestDispatcher("/managerserv").forward(req, resp);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void makeDone(HttpServletRequest req, HttpServletResponse resp){
////        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
////        Integer id = Integer.parseInt(req.getParameter("cancelButton"));
////        Request request_actions = requestDAO.findEntityById(id);
////        request_actions.setStatus("done");
////        requestDAO.update(request_actions, id);
////        try {
////            req.getRequestDispatcher("/master").forward(req, resp);
////        } catch (ServletException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }