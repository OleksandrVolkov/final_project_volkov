package controller;

import model.Language;
import model.utility.LanguageHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name="lang", urlPatterns = {"/lang"})
public class LanguageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("lang"));
        String lang = req.getParameter("lang");
        if(lang == null)
            lang = "en";
        LanguageHandler languageHandler = new LanguageHandler();
        Map<String, String> mainPage = null;

        switch (lang){
            case "en":
                mainPage = LanguageHandler.getHashMapOfMainPage(Language.ENGLISH);
                mainPage.put("lang", "en");
                break;
            case "ru":
                mainPage = LanguageHandler.getHashMapOfMainPage(Language.RUSSIAN);
                mainPage.put("lang", "ru");
                break;
            case "ua":
                mainPage = LanguageHandler.getHashMapOfMainPage(Language.UKRAINIAN);
                mainPage.put("lang", "ua");
                break;
            default:
                mainPage = LanguageHandler.getHashMapOfMainPage(Language.ENGLISH);
                mainPage.put("lang", "en");
                break;
        }
        System.out.println("**********************************************"+mainPage);
        req.setAttribute("language", mainPage);

        req.getRequestDispatcher("main.jsp").forward(req, resp);
    }
}
