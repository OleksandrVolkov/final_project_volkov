package model.utility;

import model.dao.RequestDAO;
import model.dao.connection.ConnectionManager;
import model.entity.Request;

import java.sql.SQLException;
import java.util.List;

public class RequestRowCounter {
    public static int getNumberOfPagesByUserId(int recordsPerPage, int userId){
        return getNumberOfPages(getNumberOfRowsByUserId(userId), recordsPerPage);
    }
    public static int getNumberOfRowsByUserId(int userId){
        return new RequestDAO(ConnectionManager.getConnection()).getAmountOfRequestsByUserId(userId);
    }

    public static int getNumberOfAllRows(){
        return new RequestDAO(ConnectionManager.getConnection()).getAmountOfRequests();
    }

    private static int getNumberOfRowsByStatus(String firstStatus, String secondStatus) throws SQLException {
        return new RequestDAO(ConnectionManager.getConnection()).getAmountOfRequestsByTwoStatuses(firstStatus, secondStatus);
    }
    private static int getNumberOfRowsByStatus(String status) throws SQLException {
        return new RequestDAO(ConnectionManager.getConnection()).getAmountOfRequestsByStatus(status);
    }


    public static int getNumberOfPagesByStatus(int recordsPerPage, String firstStatus, String secondStatus) throws SQLException {
        return getNumberOfPages(getNumberOfRowsByStatus(firstStatus, secondStatus), recordsPerPage);
    }

    public static int getNumberOfPagesByStatus(int recordsPerPage, String status) throws SQLException {
        return getNumberOfPages(getNumberOfRowsByStatus(status), recordsPerPage);
    }

    public static int getNumberOfPages(int recordsPerPage){
        return getNumberOfPages(getNumberOfAllRows(), recordsPerPage);
    }

//    public static int getNumberOfPages(int recordsPerPage, String status){
//        return new RequestDAO(ConnectionManager.getConnection()).getAmountOfRequestsByStatus(status);
////        return getNumberOfPages(recordsPerPage);
//    }

    private static int getNumberOfPages(int rows, int recordsPerPage){
        int nOfPages = rows / recordsPerPage;
        if(nOfPages % recordsPerPage > 0)
            nOfPages++;
        return nOfPages;
    }

    public static List<Request> findRequests(int currentPage, int recordsPerPage){
        return new RequestDAO(ConnectionManager.getConnection()).findLimitRequests(currentPage, recordsPerPage);
    }

}
