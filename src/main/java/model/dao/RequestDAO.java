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

public class RequestDAO extends AbstractDAO<Request>{
//    private UserDAO userDAO;
    private FeedbackDAO feedbackDAO;
    private RejectDAO rejectDAO;
    private static Logger log = Logger.getLogger(RequestDAO.class);

    public RequestDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Request> findAll() {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.trace("Finding all feedbacks");
//        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        String query = "SELECT * FROM requests;";
        List<Request> requests = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            statement = connection.createStatement();
            log.trace("Creating result set");
            resultSet = statement.executeQuery(query);
            int i = 0;

            while (resultSet.next()){
                log.trace("Getting values from result set");
                int id = resultSet.getInt("id");
                String text = resultSet.getString("request_text");
                int user_id = resultSet.getInt("user_id");
                String status = resultSet.getString("status");
                log.trace("Creating request with id = " + id);
//                request = new Request(id, text, status);
                log.trace("Setting price for the request");
//!!!                request.setPrice(this.getPriceByRequestId(id));
                log.trace("Setting feedback (throughout access to feedbackDAO)");
//!!!                request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
                log.trace("Adding a request to an array");



                requests.add(new Request(id, text, status, user_id));
            }

            for(Request curRequest: requests){
                curRequest.setPrice(this.getPriceByRequestId(curRequest.getId()));
                curRequest.setFeedback(feedbackDAO.getFeedbackByRequestId(curRequest.getId()));
                curRequest.setItems(new ItemDAO(ConnectionManager.getConnection()).getItemsByRequest(curRequest));
                curRequest.setReject(new RejectDAO(ConnectionManager.getConnection()).getRejectByRequestId(curRequest.getId()));
            }

        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return null;
        } finally {
            close(statement);
            close(resultSet);
        }



        log.trace("Returning requests");
        return requests;
    }

//    public List<Request> findByStatus(String status) throws SQLException {
//        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
//        List<Request> requests = new ArrayList<>();
//
//        String query = "SELECT * FROM requests WHERE status = ?;";
//
//
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, status);
////            statement = connection.createStatement();
////            resultSet = statement.executeQuery(query);
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()){
//                int id = resultSet.getInt("id");
//                String text = resultSet.getString("request_text");
////                int feedback_id = resultSet.getInt("feedback_id");
//// !!!!               Feedback feedback = feedbackDAO.findEntityById(feedback_id);
//
//
//                requests.add(new Request(id, text, status));
//            }
//
//            return requests;
//    }

    @Override
    public Request findEntityById(int id) {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Finding request by id = " + id);
// !!!!!       feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        Request request = null;
        String query = "SELECT * FROM requests WHERE id = " + id + ";";

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            statement = connection.createStatement();
            log.trace("Creating result set");
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                log.trace("Getting values of the result set");
                String text = resultSet.getString("request_text");
                String status = resultSet.getString("status");
                int user_id = resultSet.getInt("user_id");

//                FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
                log.trace("Creating request with id = " + id);
                request = new Request(id, text, status, user_id);
                log.trace("Getting price for request");
                request.setPrice(this.getPriceByRequestId(id));
                log.trace("Setting feedback (throughout access to feedbackDAO) by request id = " + id);
                request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
                //!!!!!!!!!!!!!
                request.setItems(new ItemDAO(ConnectionManager.getConnection()).getItemsByRequest(request));
                request.setReject(new RejectDAO(ConnectionManager.getConnection()).getRejectByRequestId(id));


//                request.setReject(new RejectDAO(ConnectionManager.getConnection()).findEntityById());
            }
            log.trace("Returning request");
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return null;
        } finally {
            close(statement);
            close(resultSet);
        }
        return request;
    }

    @Override
    public boolean delete(int id) {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Deleting request with id = " + id);

        String query = "DELETE FROM requests WHERE id = " + id + ";";
        Statement statement = null;
        try {
            this.deleteItemsByRequestId(id);
//            statement = connection.createStatement();
            statement = connection.createStatement();
            log.trace("Executing update ");
            statement.executeUpdate(query);
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return false;
        } finally {
            close(statement);
        }
        return true;
    }


    private boolean deleteItemsByRequestId(int requestId){
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Deleting items by request with request id = " + requestId);



        String query = "DELETE FROM requests_items WHERE request_id = " + requestId + ";";
        Statement statement = null;
        try {
//            statement = connection.createStatement();
            log.trace("Creating statement");
            statement = connection.createStatement();
            log.trace("Executing update ");
            statement.executeUpdate(query);
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return false;
        } finally {
            close(statement);
        }
        return true;
    }


    @Override
    public boolean create(Request request) {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Creating request with status = " + request.getStatus());
        String query = "INSERT INTO requests(request_text, status, user_id) VALUES(?,?,?);";
//        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());

        PreparedStatement preparedStatement = null;
        try{
            log.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Setting values for prepared statement");

            String text = request.getText();
            String status = request.getStatus();
            int userId = request.getUserId();
            System.out.println("!!!!!!!!!" + text + status + userId);
            preparedStatement.setString(1, text);
            preparedStatement.setString(2, status);
            preparedStatement.setInt(3, userId);

            log.trace("Executing prepared statement");
            preparedStatement.execute();

            log.trace("Setting id of the request");
//            request.setId(getRequestIndex(request));
            request.setId(this.getLastInsertedRequestIndex());

//            this.createRequestsWithItems(request);
            if(!request.getItems().isEmpty()) {
                log.trace("Creating requests with items");
                new RequestDAO(ConnectionManager.getConnection()).createRequestsWithItems(request);
            }
        }catch(SQLException e){
            log.warn("Error a bit:(", e);
            return false;
        } finally {
            close(preparedStatement);
        }
        log.trace("Request is created");
        return true;
    }

    public Integer getLastInsertedRequestIndex(){
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
//        String query = "SELECT * FROM requests WHERE request_text = '" + request.getText() + "' AND status = '" + request.getStatus() + "';";
        String query = "SELECT MAX( id ) AS max_id FROM requests;";
        int id = 0;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                id = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(statement);
            close(resultSet);
        }
        return id;
    }

//    public boolean isFeedbackEmpty(Request request){
//        return request.getFeedback() == null;
//    }

    @Override
    public Request update(Request request, int key) {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Updating request with id = " + key);
//        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
//        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        String query = "UPDATE requests SET request_text = ?, status = ?, user_id = ? WHERE id = "+key+";";

        PreparedStatement preparedStatement = null;
        try {
            log.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Setting values of the prepared statement");
            preparedStatement.setString(1, request.getText());
            preparedStatement.setString(2, request.getStatus());
            preparedStatement.setInt(3, request.getUserId());
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
                log.trace("Adding price to the request");
                this.addPriceToRequest(request);
            }
            log.trace("Checking whether reject is empty");
            if(request.getReject() != null) {
                log.trace("Creating rejection of the request");
                System.out.println("________________________");
                System.out.println("________________________");
                System.out.println("________________________");
                System.out.println("________________________");
                System.out.println("________________________");
                System.out.println("________________________");
                System.out.println("________________________");

                System.out.println(request.getReject());
                rejectDAO.create(request.getReject());

                System.out.println(this.findEntityById(request.getId()).getReject() + ")))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))");
                log.trace("Rejection of the request is created");
            }

            log.trace("Returning request");
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return null;
        } finally {
            close(preparedStatement);
        }
        return request;
    }


//    public List<Request> getRequestsByStatus(String status){
//        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
//        String query = "SELECT * FROM requests WHERE status = " + status;
//        List<Request> requests = new ArrayList<>();
//
//        try {
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(query);
//            while (resultSet.next()){
//                int id = resultSet.getInt("id");
//                String text = resultSet.getString("request_text");
//
////                int feedback_id = resultSet.getInt("feedback_id");
////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                requests.add(new Request(id, text, status, null));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        return requests;
//    }


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
                    Request request = new Request(id, text, status, userId);
                    ItemDAO itemDAO = new ItemDAO(ConnectionManager.getConnection());
                    FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
                    log.trace("Setting feedback of the request");
                    request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
                    log.trace("Setting items of the request");
                    request.setItems(itemDAO.getItemsByRequest(request));
                    log.trace("Setting price of the price");
                    request.setPrice(getPriceByRequestId(id));

                    log.trace("Adding request to the array");
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
                Request request = new Request(id, text, status, userId);
                log.trace("Request is created with id = " + id);
                log.trace("Setting items of the request");
                request.setItems(new ItemDAO(ConnectionManager.getConnection()).getItemsByRequest(request));
                FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
                log.trace("Setting feedback by request id = " + id);
                request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
                log.trace("Setting price by request id = " + id);
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



    public List<Request> findLimitRequests(int currentPage, int recordsPerPage, String status){
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Finding requests with the fixed limit and status");
        List<Request> requests = new ArrayList<>();

        log.trace("Current page: " + currentPage + ", records per page: " + recordsPerPage);
        log.trace("Status: " + status);
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
                Request request = new Request(id, text, status, userId);
                log.trace("Request is created with id = " + id);
                log.trace("Setting items of the request");
                request.setItems(new ItemDAO(ConnectionManager.getConnection()).getItemsByRequest(request));
                FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
                log.trace("Setting feedback by request id = " + id);
                request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
                log.trace("Setting price by request id = " + id);
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
                Request request = new Request(id, text, status, userId);
                log.trace("Request is created with id = " + id);
                log.trace("Setting items of the request");
                request.setItems(new ItemDAO(ConnectionManager.getConnection()).getItemsByRequest(request));
                FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
                log.trace("Setting feedback by request id = " + id);
                request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
                log.trace("Setting price by request id = " + id);
                request.setPrice(getPriceByRequestId(id));
                request.setReject(rejectDAO.getRejectByRequestId(id));
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("________________________________________________________");
                System.out.println("REJECT IN LIMIT                                "+request.getReject());
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




    public int getStartIndex(int currentPage, int recordsPerPage){
        return currentPage * recordsPerPage - recordsPerPage;
    }

    public Double getPriceByRequestId(int id){
        log.trace("Getting price by request id = " + id);
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
//        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
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

        log.trace("Returning price of the request with id " + id);
        return price;
    }

    public Request getRequestByUserId(int user_id){
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        log.info("Finding request by user id = " + user_id);
        Request request = null;
        String query = "SELECT * FROM requests WHERE user_id = " + user_id + ";";

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            statement = connection.createStatement();
            log.trace("Creating result set");
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                log.trace("Getting values of the result set");
                String text = resultSet.getString("request_text");
                String status = resultSet.getString("status");
                int id = resultSet.getInt("id");
                log.trace("Creating request with id = " + id);
                request = new Request(id, text, status, user_id);
                log.trace("Getting price for request");
                request.setPrice(this.getPriceByRequestId(id));
                log.trace("Setting feedback (throughout access to feedbackDAO) by request id = " + id);
                request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));

                //!!!!!!!!!!!!!
                request.setItems(new ItemDAO(ConnectionManager.getConnection()).getItemsByRequest(request));
            }
            log.trace("Returning request");
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return null;
        } finally {
            close(statement);
            close(resultSet);
        }
        return request;
    }





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


    public int getAmountOfRequests() {
        log.trace("Getting amount of requests");
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        String query = "SELECT COUNT(id) AS total FROM requests;";

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            statement = connection.createStatement();
            log.trace("Creating result set");
            resultSet = statement.executeQuery(query);
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
            close(statement);
            close(resultSet);
        }
        return 0;
    }



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

    public boolean createRequestsWithItems(Request request) {
//        List<Item> items = request.getItems();
        log.info("Creating requests with items");
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        String query = "INSERT INTO requests_items(request_id, item_id) VALUES(?,?);";
//        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());

        PreparedStatement preparedStatement = null;
        try {
            for(int i = 0; i < request.getItems().size(); i++) {
                    log.trace("Creating prepared statement");
                    preparedStatement = connection.prepareStatement(query);
                    log.trace("Setting values for prepared statement");
                    preparedStatement.setInt(1, request.getId());
                    preparedStatement.setInt(2, request.getItems().get(i).getId());
                    log.trace("Executing prepared statement");
                    preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            close(preparedStatement);
        }
        return true;
    }

    public boolean addPriceToRequest(Request request){
//        if(!request.getStatus().equals("not seen"))
//            return false;
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());

        double price = request.getPrice();
        log.info("Adding price = " + price + " to request with request id = " + request.getId());

        String query = "INSERT INTO confirmed_requests(request_id, price) VALUES(?,?);";
//        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());

        PreparedStatement preparedStatement = null;
        try {
            log.trace("Adding price to request");
            preparedStatement = connection.prepareStatement(query);

            log.trace("Setting values for prepared statement");
            preparedStatement.setInt(1, request.getId());
            preparedStatement.setDouble(2, price);

            log.trace("Executing prepared statement");
            preparedStatement.execute();
            log.trace("Price is added");
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
        } finally {
            close(preparedStatement);
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
//    public void addReject(Request request){
//        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
//        request.getReject().setRequestId(request.getId());
//        rejectDAO.create(request.getReject());
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
