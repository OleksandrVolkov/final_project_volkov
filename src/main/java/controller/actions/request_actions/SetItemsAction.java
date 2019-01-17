package controller.actions.request_actions;

import controller.actions.Action;
import model.Language;
import model.dao.ItemDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Item;
import model.utility.LanguageHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class SetItemsAction implements Action {
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println(req.getParameter("lang") + ">>>>>>");
        String lang = req.getParameter("lang");
        if(lang == null)
            lang = "en";
//        LanguageHandler languageHandler = new LanguageHandler();
        Map<String, String> requestForm = null;

        switch (lang){
            case "en":
                requestForm = LanguageHandler.getHashMapOfRequestForm(Language.ENGLISH);
                break;
            case "ru":
                requestForm = LanguageHandler.getHashMapOfRequestForm(Language.RUSSIAN);
                break;
            case "ua":
                requestForm = LanguageHandler.getHashMapOfRequestForm(Language.UKRAINIAN);
                break;
            default:
                break;
        }

        System.out.println(requestForm);
        req.setAttribute("language", requestForm);



        ItemDAO itemDAO = new ItemDAO(ConnectionManager.getConnection());
        List<Item> items = itemDAO.findAll();
        req.setAttribute("itemsAvailable", items);
        String url = "request.jsp";
        return url;
    }
}











//        try {
//            req.getRequestDispatcher("request_actions.jsp").forward(req, resp);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }