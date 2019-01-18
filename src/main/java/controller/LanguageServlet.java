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
        System.out.println(req.getParameter("pageUrl"));
        System.out.println(req.getParameter("lang") + "???????");

        String pageUrl = req.getParameter("pageUrl");
        String lang = req.getParameter("lang");

        if(pageUrl == null || pageUrl.equals(""))
            pageUrl = "main.jsp";

        if(lang == null || lang.equals(""))
            lang = "en";


        System.out.println("LANG " + lang);



        Map<String, String> authForm = LanguageHandler.getHashMapOfValuesByPageUrl(pageUrl, Language.getLanguage(lang));
        authForm.put("lang", lang);


        System.out.println(authForm+"________");
        req.setAttribute("language", authForm);
        req.getRequestDispatcher(pageUrl).forward(req, resp);

    }
}
















//        System.out.println(req.getParameter("lang"));
//        String lang = req.getParameter("lang");
//        if(lang == null)
//            lang = "en";
//        LanguageHandler languageHandler = new LanguageHandler();
//        Map<String, String> mainPage = null;
//
//        switch (lang){
//            case "en":
//                mainPage = LanguageHandler.getHashMapOfMainPage(Language.ENGLISH);
//                mainPage.put("lang", "en");
//                break;
//            case "ru":
//                mainPage = LanguageHandler.getHashMapOfMainPage(Language.RUSSIAN);
//                mainPage.put("lang", "ru");
//                break;
//            case "ua":
//                mainPage = LanguageHandler.getHashMapOfMainPage(Language.UKRAINIAN);
//                mainPage.put("lang", "ua");
//                break;
//            default:
//                mainPage = LanguageHandler.getHashMapOfMainPage(Language.ENGLISH);
//                mainPage.put("lang", "en");
//                break;
//        }
//        System.out.println("**********************************************"+mainPage);
//        req.setAttribute("language", mainPage);
//
//        req.getRequestDispatcher("main.jsp").forward(req, resp);

//        System.out.println(req.getParameter("lang"));
//        System.out.println(req.getParameter("pageUrl"));
//        String pageUrl = req.getParameter("pageUrl");
//        String lang = req.getParameter("lang");
//        if(lang == null)
//            lang = "en";
//        System.out.println(lang);
//        LanguageHandler languageHandler = new LanguageHandler();
//
//        req.getRequestDispatcher(pageUrl).forward(req, resp);






////        Map<String, String> authForm = null;
////        switch (lang) {
////            case "en":
////                System.out.println("EEEEENNNNNN");
////                authForm = LanguageHandler.getHashMapOfAuthorizationForm(Language.ENGLISH);
////                authForm.put("lang", "en");
////                break;
////            case "ru":
////                System.out.println("RRRRRUUUUU");
////                authForm = LanguageHandler.getHashMapOfAuthorizationForm(Language.RUSSIAN);
////                authForm.put("lang", "ru");
////                break;
////            case "ua":
////                System.out.println("UUUUUAAAAAA");
////                authForm = LanguageHandler.getHashMapOfAuthorizationForm(Language.UKRAINIAN);
////                authForm.put("lang", "ua");
////                break;
////            default:
////                break;
////        }
//
//
//
//        System.out.println(authForm+"________");
//                req.setAttribute("language", authForm);
////            resp.sendRedirect("authorization.jsp");
////        req.getRequestDispatcher("authorization.jsp").forward(req, resp);
//                req.getRequestDispatcher(pageUrl).forward(req, resp);
//
//
//
//
//
////        Map<String, String> mainPage = null;









//    System.out.println(req.getParameter("lang"));
//            String lang = req.getParameter("lang");
//            if(lang == null)
//            lang = "en";
//            LanguageHandler languageHandler = new LanguageHandler();
//            Map<String, String> mainPage = null;
//
//        switch (lang){
//        case "en":
//        mainPage = LanguageHandler.getHashMapOfMainPage(Language.ENGLISH);
//        mainPage.put("lang", "en");
//        break;
//        case "ru":
//        mainPage = LanguageHandler.getHashMapOfMainPage(Language.RUSSIAN);
//        mainPage.put("lang", "ru");
//        break;
//        case "ua":
//        mainPage = LanguageHandler.getHashMapOfMainPage(Language.UKRAINIAN);
//        mainPage.put("lang", "ua");
//        break;
//default:
//        mainPage = LanguageHandler.getHashMapOfMainPage(Language.ENGLISH);
//        mainPage.put("lang", "en");
//        break;
//        }
//        System.out.println("**********************************************"+mainPage);
//        req.setAttribute("language", mainPage);
//
//        req.getRequestDispatcher("main.jsp").forward(req, resp);