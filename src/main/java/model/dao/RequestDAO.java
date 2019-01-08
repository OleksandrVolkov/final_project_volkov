package model.dao;

import model.dao.connection.ConnectionManager;
import model.entity.Feedback;
import model.entity.Item;
import model.entity.Reject;
import model.entity.Request;

import java.net.CookieHandler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO extends AbstractDAO<Request>{
//    private UserDAO userDAO;
    private FeedbackDAO feedbackDAO;
    private RejectDAO rejectDAO;

    public RequestDAO(Connection connection){
        super(connection);
    }

    @Override
    public List<Request> findAll() {

        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        String query = "SELECT * FROM requests;";
        List<Request> requests = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String text = resultSet.getString("request_text");
//                int user_id = resultSet.getInt("user_id");
                String status = resultSet.getString("status");

                Request request = new Request(id, text, status);
                request.setPrice(this.getPriceByRequestId(id));

                FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
                request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


        return requests;
    }



    public List<Request> findByStatus(String status) throws SQLException {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        List<Request> requests = new ArrayList<>();

        String query = "SELECT * FROM requests WHERE status = ?;";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status);
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String text = resultSet.getString("request_text");
//                int feedback_id = resultSet.getInt("feedback_id");
// !!!!               Feedback feedback = feedbackDAO.findEntityById(feedback_id);


                requests.add(new Request(id, text, status));
            }

            return requests;
    }

    @Override
    public Request findEntityById(int id) {
// !!!!!       feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        Request request = null;
        String query = "SELECT * FROM requests WHERE id = " + id + ";";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String text = resultSet.getString("request_text");
                String status = resultSet.getString("status");

                FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
                request = new Request(id, text, status);
                request.setPrice(this.getPriceByRequestId(id));
                request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
            }
            return request;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM requests WHERE id = " + id + ";";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean create(Request request) {

        String query = "INSERT INTO requests(request_text, status) VALUES(?,?);";
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());

        try{
            preparedStatement = connection.prepareStatement(query);
            String text = request.getText();
            String status = request.getStatus();
            preparedStatement.setString(1, text);
            preparedStatement.setString(2, status);

            preparedStatement.execute();

            request.setId(getRequestIndex(request));

//            this.createRequestsWithItems(request);
            if(!request.getItems().isEmpty())
                new RequestDAO(ConnectionManager.getConnection()).createRequestsWithItems(request);

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Integer getRequestIndex(Request request){
        String query = "SELECT * FROM requests WHERE request_text = '" + request.getText() + "' AND status = '" + request.getStatus() + "';";
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public boolean isFeedbackEmpty(Request request){
//        return request.getFeedback() == null;
//    }

    @Override
    public Request update(Request request, int key) {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        rejectDAO = new RejectDAO(ConnectionManager.getConnection());
        String query = "UPDATE requests SET request_text = ?, status = ? WHERE id = "+key+";";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, request.getText());
            preparedStatement.setString(2, request.getStatus());
            preparedStatement.executeUpdate();
            if(request.getFeedback() != null) {
                FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
                feedbackDAO.create(request.getFeedback());
            }
            if(request.getPrice() != null)
                this.addPriceToRequest(request);
            if(request.getReject() != null)
                rejectDAO.create(request.getReject());

            return request;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Request> getRequestsByStatus(String status){
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        String query = "SELECT * FROM requests WHERE status = " + status;
        List<Request> requests = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String text = resultSet.getString("request_text");

//                int feedback_id = resultSet.getInt("feedback_id");
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                requests.add(new Request(id, text, status, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return requests;
    }


    public List<Request> findLimitRequests(int currentPage, int recordsPerPage) throws SQLException {
        List<Request> requests = new ArrayList<>();

        int start = currentPage * recordsPerPage - recordsPerPage;
        String sql = "SELECT * FROM requests LIMIT ?, ?;";


            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, recordsPerPage);

            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String text = resultSet.getString("request_text");
                String status = resultSet.getString("status");
                Request request = new Request(id, text, status);
                ItemDAO itemDAO = new ItemDAO(ConnectionManager.getConnection());
                FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
                request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
                request.setItems(itemDAO.getItemsByRequest(request));
                request.setPrice(getPriceByRequestId(id));

                requests.add(request);
            }
            return requests;
    }

    public List<Request> findLimitRequests(int currentPage, int recordsPerPage, String firstStatus, String secondStatus) throws SQLException {
        List<Request> requests = new ArrayList<>();

        int start = currentPage * recordsPerPage - recordsPerPage;
        String sql = "SELECT * FROM requests WHERE status IN (?,?) LIMIT ?, ?";


        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, firstStatus);
        preparedStatement.setString(2, secondStatus);
        preparedStatement.setInt(3, start);
        preparedStatement.setInt(4, recordsPerPage);

        ResultSet resultSet =  preparedStatement.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String text = resultSet.getString("request_text");
            String status = resultSet.getString("status");
            Request request = new Request(id, text, status);
            request.setItems(new ItemDAO(ConnectionManager.getConnection()).getItemsByRequest(request));
            FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
            request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
            request.setPrice(getPriceByRequestId(id));
            requests.add(request);
        }
        return requests;
    }



    public List<Request> findLimitRequests(int currentPage, int recordsPerPage, String status) throws SQLException {
        List<Request> requests = new ArrayList<>();

        int start = currentPage * recordsPerPage - recordsPerPage;
        String sql = "SELECT * FROM requests WHERE status IN (?) LIMIT ?, ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, status);
        preparedStatement.setInt(2, start);
        preparedStatement.setInt(3, recordsPerPage);

        ResultSet resultSet =  preparedStatement.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String text = resultSet.getString("request_text");
            Request request = new Request(id, text, status);
            request.setItems(new ItemDAO(ConnectionManager.getConnection()).getItemsByRequest(request));
            FeedbackDAO feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
            request.setFeedback(feedbackDAO.getFeedbackByRequestId(id));
            request.setPrice(getPriceByRequestId(id));
            requests.add(request);
        }
        return requests;
    }

    public Double getPriceByRequestId(int id){
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        String query = "SELECT * FROM confirmed_requests WHERE request_id = ?;";
        Double price = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                price = resultSet.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return price;
    }





    public int getAmountOfRequestsByStatus(String status) throws SQLException {
        String query = "SELECT COUNT(id) AS total  FROM requests WHERE status = ?;";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, status);
        resultSet = preparedStatement.executeQuery();
        int total = 0;
        while (resultSet.next()){
            total = resultSet.getInt("total");
        }
        return total;
    }


    public int getAmountOfRequests() {
        String query = "SELECT COUNT(id) AS total FROM requests;";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            int total = 0;
            while (resultSet.next()){
                total = resultSet.getInt("total");
            }

            return total;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getAmountOfRequestsByTwoStatuses(String firstStatus, String secondStatus) throws SQLException {
        String query = "SELECT COUNT(id) AS total FROM requests WHERE status IN (?,?);";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, firstStatus);
        preparedStatement.setString(2, secondStatus);
        resultSet = preparedStatement.executeQuery();
        int total = 0;
        while (resultSet.next()){
            total = resultSet.getInt("total");
        }
        return total;
    }

    public boolean createRequestsWithItems(Request request) throws SQLException {
//        List<Item> items = request.getItems();

        String query = "INSERT INTO requests_items(request_id, item_id) VALUES(?,?);";
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());

        preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, request.getId());
        preparedStatement.setInt(2, request.getItems().get(0).getId());

        preparedStatement.execute();
        return true;
    }

    public boolean addPriceToRequest(Request request){
//        if(!request.getStatus().equals("not seen"))
//            return false;

        double price = request.getPrice();
        String query = "INSERT INTO confirmed_requests(request_id, price) VALUES(?,?);";
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, request.getId());
            preparedStatement.setDouble(2, price);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
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
