package controller.actions.account_actions;

import controller.actions.Action;
import model.entity.Feedback;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExitAction implements Action{
    private static Logger log = Logger.getLogger(ExitAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        log.trace("getting attribute of the logged user");
        req.getSession().setAttribute("LOGGED_USER", null);
        log.trace("getting role of logged user");
        String role = (String) req.getSession().getAttribute("role");
        System.out.println("RRROOOOOOOLLLLLLEEE     " + role);
        log.trace("checking whether the role equals null");
        if(role!=null)
            switch (role){
                case "client":
                    log.trace("client is unlogged");
                    req.getSession().setAttribute("LOGGED_USER", null);
                    break;
                case "manager":
                    log.trace("manager is unlogged");
                    req.getSession().setAttribute("LOGGED_MANAGER", null);
                    break;
                case "master":
                    log.trace("master is unlogged");
                    req.getSession().setAttribute("LOGGED_MASTER", null);
                    break;
                default:
                    log.trace("String is different");
                    break;
            }

            log.trace("returning url value");
        String url = "/lang";
        return url;
    }
}
