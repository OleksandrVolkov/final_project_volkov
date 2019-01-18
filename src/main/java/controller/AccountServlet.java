package controller;

import controller.actions.account_actions.ExitAction;
import controller.actions.account_actions.LoginAction;
import controller.actions.account_actions.RegisterAction;
import controller.actions.Action;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "account", urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet{
    private Map<String,Action> actionMap = new HashMap<String,Action>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        actionMap.put("logout", new ExitAction());
        actionMap.put("login", new LoginAction());
        actionMap.put("register", new RegisterAction());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        System.out.println("URL :" + url);
        if(url == null || url.equals(""))
            url = "/lang";

        req.getRequestDispatcher(url).forward(req, resp);

    }
}
