package controller.actions.account_actions;

import controller.actions.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExitAction implements Action{

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        req.getSession().setAttribute("LOGGED_USER", null);
        String role = (String) req.getSession().getAttribute("role");
        System.out.println("RRROOOOOOOLLLLLLEEE     " + role);
        if(role!=null)
            switch (role){
                case "client":
                    req.getSession().setAttribute("LOGGED_USER", null);
                    break;
                case "manager":
                    req.getSession().setAttribute("LOGGED_MANAGER", null);
                    break;
                case "master":
                    req.getSession().setAttribute("LOGGED_MASTER", null);
                    break;
                default:
                    break;
            }

        String url = "/lang";
        return url;
    }
}
