package controller;

import model.Language;
import model.dao.ItemDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Item;
import model.utility.LanguageHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "preRequset", urlPatterns = {"/preRequest"})
public class PreRequestServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        req.getRequestDispatcher("request.jsp").forward(req, resp);
    }
}
