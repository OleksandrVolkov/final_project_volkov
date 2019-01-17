package model.dao;

import model.dao.connection.ConnectionManager;
import model.entity.Feedback;
import model.entity.Item;
import model.entity.Reject;
import model.entity.Request;
import org.apache.log4j.Logger;

import java.net.CookieHandler;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *  RequestDAO represents a way to access database to the corresponding to the
 *  Request entity table "requests" via JDBC API by SQL server.
 *  It represents the way to access to the value needed and make basic CRUD (create,
 *  read, update, delete) operations and some more added functionality.
 *  Moreover, it gives the opportunity to initialize the entity
 *  objects (Request class) on the side of model which makes it easier to manipulate with the objects
 *  in the application in the object-oriented way.
 *  It extends an abstract AbstractDAO class and therefore overrides some its methods.
 *
 *
 * @author  Oleksandr Volkov
 * @version 1.0
 * @since   2019-01-15
 */

public class RequestDAO extends AbstractDAO<Request>{
//    private UserDAO userDAO;
    private FeedbackDAO feedbackDAO;
    private RejectDAO rejectDAO;
    private static Logger log = Logger.getLogger(RequestDAO.class);

    public RequestDAO(Connection connection) {
        super(connection);
    }

    /**
     * This method is used to find all requests from the corresponding table in the
     * database.
     * @return List of all of the requests available in the table.
     */
     @Override
     public List<Request> findAll() {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.trace("Finding all feedbacks");
//        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        String query = "SELECT * FROM requests;";
        List<Request> requests = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            int i = 0;

            while (resultSet.next()){
                log.trace("Getting values from result set");
                int id = resultSet.getInt("id");
                String text = resultSet.getString("request_text");
                int user_id = resultSet.getInt("user_id");
                int itemId = resultSet.getInt("item_id");

                String status = resultSet.getString("status");
                log.trace("Creating request_actions with id = " + id);
//                request_actions = new Request(id, text, status);
                log.trace("Setting price for the request_actions");
//!!!                request_actions.setPrice(this.getPriceByRequestId(id));
                log.trace("Setting feedback (throughout access to feedbackDAO)");
//!!!                request_actions.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
                log.trace("Adding a request_actions to an array");

                Request request = new Request(id, text, status, user_id, itemId);
//                request_actions.setItem(new ItemDAO(ConnectionManager.getConnection()).findEntityById(itemId));


                requests.add(request);
            }

            for(Request curRequest: requests){
                curRequest.setPrice(this.getPriceByRequestId(curRequest.getId()));
                curRequest.setFeedback(feedbackDAO.getFeedbackByRequestId(curRequest.getId()));
//                curRequest.setItem(new ItemDAO(ConnectionManager.getConnection()).getItemsByRequest(curRequest));
                curRequest.setReject(new RejectDAO(ConnectionManager.getConnection()).getRejectByRequestId(curRequest.getId()));
            }

        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return null;
        } finally {
            close(preparedStatement);
            close(resultSet);
        }



        log.trace("Returning requests");
        return requests;
    }

    /**
     * This method is used to find request_actions by its id in the database.
     * @param id This is the id of the request_actions is needed to find.
     * @return Request It returns the request_actions by the given id.
     */
     @Override
     public Request findEntityById(int id) {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Finding request_actions by id = " + id);
        Request request = null;
        String query = "SELECT * FROM requests WHERE id = ?;";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            log.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                log.trace("Getting values of the result set");
                String text = resultSet.getString("request_text");
                String status = resultSet.getString("status");
                int userId = resultSet.getInt("user_id");
                int itemId = resultSet.getInt("item_id");
                log.trace("Creating request_actions with id = " + id);
                request = new Request(id, text, status, userId, itemId);
                log.trace("Getting price for request_actions");
                request.setPrice(this.getPriceByRequestId(id));
                log.trace("Setting feedback (throughout access to feedbackDAO) by request_actions id = " + id);
                request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
                request.setReject(new RejectDAO(ConnectionManager.getConnection()).getRejectByRequestId(id));
            }
            log.trace("Returning request_actions");
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return null;
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
        return request;
    }


    /**
     * This method is used to delete request_actions by its id.
     * @param id This is id of the request_actions is needed to delete.
     * @return boolean It returns the boolean value depending on whether request_actions was deleted.
     * (by given id)
     */
     @Override
     public boolean delete(int id) {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Deleting request_actions with id = " + id);
        this.deletePrice(id);
        String query = "DELETE FROM requests WHERE id = ?;";
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
//            this.deleteItemsByRequestId(id);
//            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            log.trace("Executing update ");
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            try {
                connection.rollback();
                log.warn("Successfully rolled back changes from the database");
            } catch (SQLException e1) {
                e1.printStackTrace();
                log.warn("Could not rollback updates " + e1.getMessage());
            }
            return false;
        } finally {
            close(preparedStatement);
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    /**
     * This method is used to delete the corresponding to the given confirmed request_actions price
     * @param requestId This is id of the request_actions is needed to delete.
     * @return boolean It returns the boolean value depending on whether request_actions was deleted.
     * (by given id)
     */
    private boolean deletePrice(int requestId) {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Deleting request_actions with request_actions id = " + requestId);

        String query = "DELETE FROM confirmed_requests WHERE request_id = ?;";
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
//            this.deleteItemsByRequestId(id);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, requestId);
            log.trace("Executing update ");
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            try {
                connection.rollback();
                log.warn("Successfully rolled back changes from the database");
            } catch (SQLException e1) {
                e1.printStackTrace();
                log.warn("Could not rollback updates " + e1.getMessage());
            }
            return false;
        } finally {
            close(preparedStatement);
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    /**
     * This method is used to create request_actions by given object of the corresponding class.
     * @param request This is the request_actions which values will be inserted into the table "requests".
     * @return boolean It returns the boolean value depending on whether request_actions was successfully created.
     */
    @Override
    public boolean create(Request request) {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Creating request_actions with status = " + request.getStatus());
        String query = "INSERT INTO requests(request_text, status, user_id, item_id) VALUES(?,?,?,?);";
//        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());

        PreparedStatement preparedStatement = null;
        try{
            connection.setAutoCommit(false);
            log.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Setting values for prepared statement");

            String text = request.getText();
            String status = request.getStatus();
            Integer userId = request.getUserId();
            Integer itemId = request.getItemId();


            preparedStatement.setString(1, text);
            preparedStatement.setString(2, status);
            preparedStatement.setInt(3, userId);
            preparedStatement.setInt(4, itemId);

            System.out.println("USER ID = " + userId);
            System.out.println("ITEM ID = " + itemId);

            log.trace("Executing prepared statement");
            preparedStatement.execute();


            log.trace("Setting id of the request_actions");
//            request_actions.setId(getRequestIndex(request_actions));
            request.setId(this.getLastInsertedRequestIndex());

            System.out.println("REQUEST ID = " + request.getId());
            connection.commit();

        }catch(SQLException e){
            log.warn("Error a bit:(", e);
            try {
                connection.rollback();
                log.warn("Successfully rolled back changes from the database");
            } catch (SQLException e1) {
                e1.printStackTrace();
                log.warn("Could not rollback updates " + e1.getMessage());
            }
            return false;
        } finally {
            close(preparedStatement);
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        log.trace("Request is created");
        return true;
    }



    /**
     * This method is used to get index of the last inserted requests.
     * It is mainly used while creating request_actions so that get its index for
     * future handling in the application.
     * @return Integer It returns the index of the last inserted request_actions.
     */
    public Integer getLastInsertedRequestIndex(){
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
//        String query = "SELECT * FROM requests WHERE request_text = '" + request_actions.getText() + "' AND status = '" + request_actions.getStatus() + "';";
        String query = "SELECT MAX( id ) AS max_id FROM requests;";
        int id = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
        return id;
    }



    /**
     * This method is used to update request value in the database by its key.
     * @param request This is the request_actions which values will be inserted into the table "requests"
     * @param key This is the id which is used to update the corresponding value
     * @return Request It returns given request
     */
    @Override
    public Request update(Request request, int key) {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Updating request with id = " + key);
        String query = "UPDATE requests SET request_text = ?, status = ?, user_id = ?, item_id = ? WHERE id = ?;";

        PreparedStatement preparedStatement = null;
        try {
//            connection.setAutoCommit(false);
            log.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Setting values of the prepared statement");
            preparedStatement.setString(1, request.getText());
            preparedStatement.setString(2, request.getStatus());
            preparedStatement.setInt(3, request.getUserId());
            preparedStatement.setInt(4, request.getItemId());
            preparedStatement.setInt(5, key);
            log.trace("Executing update");
            preparedStatement.executeUpdate();
            log.trace("Checking whether feedback is empty");
            if(request.getFeedback() != null) {
                log.trace("Feedback is available");
                FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
                log.trace("Creating feedback");
                feedbackDAO.create(request.getFeedback());
                log.trace("Feedback is created");
            }
            log.trace("Checking whether price is empty");
            if(request.getPrice() != null) {
                log.trace("Adding price to the request_actions");
                this.addPriceToRequest(request);
            }
            log.trace("Checking whether reject is empty");
            if(request.getReject() != null) {
                System.out.println(request.getReject());
                rejectDAO.create(request.getReject());
                log.trace("Rejection of the request_actions is created");
            }

            log.trace("Returning request_actions");
//            connection.commit();
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
//            try {
//                connection.rollback();
//                log.warn("Successfully rolled back changes from the database");
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//                log.warn("Could not rollback updates " + e1.getMessage());
//            }
            return null;
        } finally {
            close(preparedStatement);
//            try {
//                connection.setAutoCommit(true);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
        }
        return request;
    }


    /**
     * This method is used to find all requests with the limited range (which is needed).
     * @param currentPage This is the page which is at the time at hte paginagion handling
     * @param recordsPerPage This is the id which is used to update the corresponding value
     * @return List of requests It returns the limited range of requests
     */
    public List<Request> findLimitRequests(int currentPage, int recordsPerPage) {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Finding requests with the fixed limit");
        log.trace("Current page: " + currentPage + ", records per page: " + recordsPerPage);
        List<Request> requests = new ArrayList<>();

        int start = currentPage * recordsPerPage - recordsPerPage;
        log.trace("Start value of the limited query is " + start);
        String sql = "SELECT * FROM requests LIMIT ?, ?;";

        log.trace("Creating prepared statement");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            log.trace("Setting values of the prepared statement");
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, recordsPerPage);

                log.trace("Creating result set");
                resultSet =  preparedStatement.executeQuery();
                while (resultSet.next()){
                    log.trace("Getting results from the result set");
                    int id = resultSet.getInt("id");
                    String text = resultSet.getString("request_text");
                    String status = resultSet.getString("status");
                    int userId = resultSet.getInt("user_id");
                    int itemId = resultSet.getInt("item_id");
                    Request request = new Request(id, text, status, userId, itemId);
                    FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
                    log.trace("Setting feedback of the request_actions");
                    request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
                    log.trace("Setting items of the request_actions");
//                    request_actions.setItems(itemDAO.getItemsByRequest(request_actions));
//                    request_actions.setItem(new ItemDAO(ConnectionManager.getConnection()).findEntityById(itemId));
                    log.trace("Setting price of the price");
                    request.setPrice(getPriceByRequestId(id));

                    log.trace("Adding request_actions to the array");
                    requests.add(request);
                }
                log.trace("Returning requests");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
            return requests;
    }


    /**
     * This method is used to find requests with the limited range (which is needed) by 2 statuses.
     * @param currentPage This is the page which is at the time at hte paginagion handling
     * @param recordsPerPage This is the id which is used to update the corresponding value
     * @param firstStatus This is the first searched status
     * @param secondStatus This is the second searched status
     * @return List of requests It returns the limited range of requests by 2 statuses
     */
    public List<Request> findLimitRequests(int currentPage, int recordsPerPage, String firstStatus, String secondStatus){
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Finding requests with the fixed limit and 2 statuses");
        List<Request> requests = new ArrayList<>();
        log.trace("Current page: " + currentPage + ", records per page: " + recordsPerPage);
        log.trace("1st status: " + firstStatus + ", 2nd status: " + secondStatus);

        int start = currentPage * recordsPerPage - recordsPerPage;
        log.trace("Start value of the limited query is " + start);
        String sql = "SELECT * FROM requests WHERE status IN (?,?) LIMIT ?, ?";

        log.trace("Creating prepared statement");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);

            log.trace("Setting values for the prepared statement");
            preparedStatement.setString(1, firstStatus);
            preparedStatement.setString(2, secondStatus);
            preparedStatement.setInt(3, start);
            preparedStatement.setInt(4, recordsPerPage);

            log.trace("Creating result set");
            resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()){
                log.trace("Getting values of the result set");
                int id = resultSet.getInt("id");
                String text = resultSet.getString("request_text");
                String status = resultSet.getString("status");
                int userId = resultSet.getInt("user_id");
                int itemId = resultSet.getInt("item_id");
                Request request = new Request(id, text, status, userId, itemId);
                log.trace("Request is created with id = " + id);
                log.trace("Setting items of the request_actions");
//                request_actions.setItems(new ItemDAO(ConnectionManager.getConnection()).getItemsByRequest(request_actions));
//                request_actions.setItem(new ItemDAO(ConnectionManager.getConnection()).findEntityById(itemId));
                FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
                log.trace("Setting feedback by request_actions id = " + id);
                request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
                log.trace("Setting price by request_actions id = " + id);
                request.setPrice(getPriceByRequestId(id));
                request.setReject(rejectDAO.getRejectByRequestId(id));
                requests.add(request);
                log.trace("Request is added to the array");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
        log.trace("Returning requests");
        return requests;
    }




    /**
     * This method is used to find requests with the limited range (which is needed) by status.
     * @param currentPage This is the page which is at the time at hte paginagion handling
     * @param recordsPerPage This is the id which is used to update the corresponding value
     * @param status This is the searched status
     * @return List of requests It returns the limited range of requests by status
     */
    public List<Request> findLimitRequests(int currentPage, int recordsPerPage, String status){
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Finding requests with the fixed limit and status");
        List<Request> requests = new ArrayList<>();

        log.trace("Current page: " + currentPage + ", records per page: " + recordsPerPage);
        log.trace("RequestStatus: " + status);
//        int start = currentPage * recordsPerPage - recordsPerPage;
        int start = this.getStartIndex(currentPage, recordsPerPage);
        String sql = "SELECT * FROM requests WHERE status IN (?) LIMIT ?, ?";


        log.trace("Creating prepared statement");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            log.trace("Setting values for the prepared statement");
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, recordsPerPage);

            log.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                log.trace("Getting values of the result set");
                int id = resultSet.getInt("id");
                String text = resultSet.getString("request_text");
                int userId = resultSet.getInt("user_id");
                int itemId = resultSet.getInt("item_id");
                Request request = new Request(id, text, status, userId, itemId);
                log.trace("Request is created with id = " + id);
                log.trace("Setting items of the request_actions");
//                request_actions.setItems(new ItemDAO(ConnectionManager.getConnection()).getItemsByRequest(request_actions));
//                request_actions.setItem(new ItemDAO(ConnectionManager.getConnection()).findEntityById(itemId));
                FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
                log.trace("Setting feedback by request_actions id = " + id);
                request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
                log.trace("Setting price by request_actions id = " + id);
                request.setPrice(getPriceByRequestId(id));
                request.setReject(rejectDAO.getRejectByRequestId(id));
                requests.add(request);
                log.trace("Request is added to the array");
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
        log.trace("Returning requests");
        return requests;
    }




    /**
     * This method is used to find requests with the limited range (which is needed) by user id
     * @param currentPage This is the page which is at the time at hte paginagion handling
     * @param recordsPerPage This is the id which is used to update the corresponding value
     * @param userId This is the user id for whom requests are being searched
     * @return List of requests It returns the limited range of requests by user id
     */
    public List<Request> findLimitRequests(int currentPage, int recordsPerPage, int userId){
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        log.info("Finding requests with the fixed limit and status");
        List<Request> requests = new ArrayList<>();

        log.trace("Current page: " + currentPage + ", records per page: " + recordsPerPage);

//        int start = currentPage * recordsPerPage - recordsPerPage;
        int start = this.getStartIndex(currentPage, recordsPerPage);
        String sql = "SELECT * FROM requests WHERE user_id IN (?) LIMIT ?, ?";


        log.trace("Creating prepared statement");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            log.trace("Setting values for the prepared statement");
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, recordsPerPage);

            log.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                log.trace("Getting values of the result set");
                int id = resultSet.getInt("id");
                String text = resultSet.getString("request_text");
//                int userId = resultSet.getInt("user_id");
                String status = resultSet.getString("status");
                int itemId = resultSet.getInt("item_id");
                Request request = new Request(id, text, status, userId, itemId);
                log.trace("Request is created with id = " + id);
                log.trace("Setting items of the request_actions");
//                request_actions.setItems(new ItemDAO(ConnectionManager.getConnection()).getItemsByRequest(request_actions));
//                request_actions.setItem(new ItemDAO(ConnectionManager.getConnection()).findEntityById(itemId));
                FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
                log.trace("Setting feedback by request_actions id = " + id);
                request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
                log.trace("Setting price by request_actions id = " + id);
                request.setPrice(getPriceByRequestId(id));
                request.setReject(rejectDAO.getRejectByRequestId(id));
                requests.add(request);
                log.trace("Request is added to the array");
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
        log.trace("Returning requests");
        return requests;
    }



    /**
     * This method is used to get start index of the limited range of requests
     * @param currentPage This is the page which is at the time at the paginagion handling
     * @param recordsPerPage This is the id which is used to update the corresponding value
     * @return int Start index of the limited range of requests
     */
    public int getStartIndex(int currentPage, int recordsPerPage){
        return currentPage * recordsPerPage - recordsPerPage;
    }


    /**
     * This method is used to get price value in the database by its request_actions id.
     * @param id This is the request_actions id which is used to get the searched price
     * @return Double It returns the price which is found by the requestId.
     */
    public Double getPriceByRequestId(int id){
        log.trace("Getting price by request_actions id = " + id);
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        String query = "SELECT * FROM confirmed_requests WHERE request_id = ?;";
        Double price = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Setting values of the prepared statement");
            preparedStatement.setInt(1, id);
            log.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                log.trace("Getting values of the result set");
                price = resultSet.getDouble("price");
            }
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
        } finally {
            close(preparedStatement);
            close(resultSet);
        }

        log.trace("Returning price of the request_actions with id " + id);
        return price;
    }


    /**
     * This method is used to get request_actions value in the database by its user id.
     * @param userId This is the user id which is used to get the searched request_actions
     * @return Request It returns the request_actions which is found by the userId.
     */
    public Request getRequestByUserId(int userId){
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Finding request_actions by user id = " + userId);
        Request request = null;
        String query = "SELECT * FROM requests WHERE user_id = ?;";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Creating result set");
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                log.trace("Getting values of the result set");
                String text = resultSet.getString("request_text");
                String status = resultSet.getString("status");
                int itemId = resultSet.getInt("item_id");
                int id = resultSet.getInt("id");
                log.trace("Creating request_actions with id = " + id);
                request = new Request(id, text, status, userId, itemId);
//                request_actions.setItem(new ItemDAO(ConnectionManager.getConnection()).findEntityById(itemId));

                log.trace("Getting price for request_actions");
                request.setPrice(this.getPriceByRequestId(id));
                log.trace("Setting feedback (throughout access to feedbackDAO) by request_actions id = " + id);
                request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));

                //!!!!!!!!!!!!!
//                request_actions.setItems(new ItemDAO(ConnectionManager.getConnection()).getItemsByRequest(request_actions));

            }
            log.trace("Returning request_actions");
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return null;
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
        return request;
    }


    /**
     * This method is used to get amount of the requests by the given status available
     * @param status This is the status by which is got amount of requests available
     * @return int It returns the amount of the requests by the given status available
     */
    public int getAmountOfRequestsByStatus(String status) {
        log.trace("Getting amount of requests by status = " + status);
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        String query = "SELECT COUNT(id) AS total  FROM requests WHERE status = ?;";
        log.trace("Creating prepared statement");
        int total = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            log.trace("Setting values to the prepared statement");
            preparedStatement.setString(1, status);
            log.trace("Executing prepared statement");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                log.trace("Getting values of the result set");
                total = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
        log.trace("Returning amount of requests by status = " + status);
        return total;
    }


    /**
     * This method is used to get amount of requests in the database.
     * @return int It returns the amount of requests
     */
    public int getAmountOfRequests() {
        log.trace("Getting amount of requests");
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        String query = "SELECT COUNT(id) AS total FROM requests;";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            int total = 0;
            while (resultSet.next()){
                log.trace("Getting values from the result set");
                total = resultSet.getInt("total");
            }
            log.trace("Returning amount of requests");
            return total;
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
        return 0;
    }


    /**
     * This method is used to get amount of requests in the database by given user id.
     * @return int It returns the amount of requests by given user id
     */
    public int getAmountOfRequestsByUserId(int userId) {
        log.trace("Getting amount of requests");
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        String query = "SELECT COUNT(id) AS total  FROM requests WHERE user_id = ?;";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            log.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            int total = 0;
            while (resultSet.next()){
                log.trace("Getting values from the result set");
                total = resultSet.getInt("total");
            }
            log.trace("Returning amount of requests");
            return total;
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
        return 0;
    }

    /**
     * This method is used to get amount of requests in the database by given 2 statuses.
     * @return int It returns the amount of requests by given 2 statuses
     */
    public int getAmountOfRequestsByTwoStatuses(String firstStatus, String secondStatus) {
        log.info("Getting amount of requests by 2 statuses, 1 = " + firstStatus + ", 2 = " + secondStatus);
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        String query = "SELECT COUNT(id) AS total FROM requests WHERE status IN (?,?);";
        log.trace("Creating prepared statement");
        int total = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            log.trace("Setting prepared statement values");
            preparedStatement.setString(1, firstStatus);
            preparedStatement.setString(2, secondStatus);
            log.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                log.trace("Getting values from the result set");
                total = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
        log.trace("Returning amount of requests");
        return total;
    }


    /**
     * This method is used to add price to request_actions
     * @return boolean It returns the boolean value whether price was successfully added to the request_actions
     */
    public boolean addPriceToRequest(Request request){
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());

        double price = request.getPrice();
        log.info("Adding price = " + price + " to request_actions with request_actions id = " + request.getId());

        String query = "INSERT INTO confirmed_requests(request_id, price) VALUES(?,?);";

        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            log.trace("Adding price to request_actions");
            preparedStatement = connection.prepareStatement(query);

            log.trace("Setting values for prepared statement");
            preparedStatement.setInt(1, request.getId());
            preparedStatement.setDouble(2, price);

            log.trace("Executing prepared statement");
            preparedStatement.execute();
            log.trace("Price is added");
            connection.commit();
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            try {
                connection.rollback();
                log.warn("Successfully rolled back changes from the database");
            } catch (SQLException e1) {
                e1.printStackTrace();
                log.warn("Could not rollback updates " + e1.getMessage());
            }
        } finally {
            close(preparedStatement);
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}













//    public boolean isRequestHasPrice(int requestID){
//        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
//        String query = "SELECT * FROM confirmed_requests WHERE request_id = " + requestID + ";";
//        try {
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(query);
//            while (resultSet.next()){
//                double price = resultSet.getDouble("price");
//                return true;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//    public boolean isRequestHasFeedback(int requestID){
//        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
//        String query = "SELECT * FROM feedbacks WHERE request_id = " + requestID + ";";
//        try {
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(query);
//            System.out.println("Result set: " + resultSet);
////            while (resultSet.next()){
////                int feedbackId = resultSet.getInt("request_id");
////                return true;
////            }
//            if(resultSet.next())
//                return true;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }


//!!!!    public void addReject(Reject reject, int requestId){
//        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
//        reject.setRequestId(requestId);
//        rejectDAO.create(reject);
//        int rejectId = rejectDAO.getRejectIndexByRequestId(requestId);
//        reject.setId(rejectId);
//    }
//    public void addReject(Request request_actions){
//        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
//        request_actions.getReject().setRequestId(request_actions.getId());
//        rejectDAO.create(request_actions.getReject());
//    }



//    public List<Item> getItemsOfRequest(int request_id){
//        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
//        List<Item> items = new ArrayList<>();
//
//        String query = "SELECT * FROM requests_items WHERE request_id = " + request_id;
//        try {
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(query);
//            while (resultSet.next()){
////                int id = resultSet.getInt("id");
////                String name = resultSet.getString("name");
////                String info = resultSet.getString("info");
//
////          !!!!!    items.add(itemDAO.findEntityById(item_id));
//            }
//
//            return items;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
