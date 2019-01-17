package model;

import model.dao.FeedbackDAO;
import model.dao.RejectDAO;
import model.dao.RequestDAO;
import model.dao.UserDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Feedback;
import model.entity.Reject;
import model.entity.Request;
import model.entity.User;
import model.utility.LanguageHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Main{
    public static void main(String[] args){
//        RejectDAO rejectDAO = new RejectDAO(ConnectionManager.getConnection());
////        rejectDAO.create(new Reject(2, "TEEEEXT",2));
//
//        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());

//        Reject reject = new Reject("bbbbbb");
//        requestDAO.addReject(reject, 23);
//        System.out.println(reject.getId());


//        System.out.println(requestDAO.findEntityById(rejectDAO.findEntityById(4).getRequestId()));

//        RequestDAO requestDAO = new RequestDAO(ConnectionManager.getConnection());
//        List<Request> requests = requestDAO.findAll();
//        for(Request request_actions: requests)
//            System.out.println(request_actions);

//        UserDAO userDAO = new UserDAO(ConnectionManager.getConnection());
//        List<User> users = userDAO.findAll();
//        for(User user: users){
//            System.out.println(user);
//        }

//        LanguageHandler languageHandler = new LanguageHandler();
////        System.out.println(languageHandler.getMainPageRus());
////        System.out.println(languageHandler.getMainPageUa());
//        System.out.println(languageHandler.getMainPageEn());
//
//        ResourceBundle RB = ResourceBundle.getBundle("app", new Locale(lang));

//        System.out.println(LanguageHandler.getHashMapOfMainPage(Language.ENGLISH));

//        System.out.println(Language.getLanguage("en"));



    }
}
