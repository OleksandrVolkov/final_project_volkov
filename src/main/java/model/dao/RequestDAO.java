package model.dao;

import model.dao.connection.ConnectionManager;
import model.entity.Feedback;
import model.entity.Item;
import model.entity.Request;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO extends AbstractDAO<Request>{
//    private UserDAO userDAO;
    private FeedbackDAO feedbackDAO;

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
                int feedback_id = resultSet.getInt("feedback_id");
                Feedback feedback = feedbackDAO.findEntityById(feedback_id);
//                User user = userDAO.findEntityById(user_id);

                requests.add(new Request(id, text, status, feedback));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


        return requests;
    }

    @Override
    public Request findEntityById(int id) {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        Request request = null;
        String query = "SELECT * FROM requests WHERE id = " + id + ";";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String text = resultSet.getString("request_text");
//                int user_id = resultSet.getInt("user_id");
                String status = resultSet.getString("status");
                int feedback_id = resultSet.getInt("feedback_id");
                Feedback feedback = feedbackDAO.findEntityById(feedback_id);
//
//                User user = userDAO.findEntityById(user_id);
                request = new Request(id, text, status, feedback);
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
        String query = null;
        if(isFeedbackEmpty(request))
            query = "INSERT INTO requests(request_text, status) VALUES(?,?);";
        else
            query = "INSERT INTO requests(request_text, status, feedback_id) VALUES(?,?,?);";
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());

        try{
            preparedStatement = connection.prepareStatement(query);
            String text = request.getText();
            int feedback_id = -1;

            if(!isFeedbackEmpty(request))
                feedback_id = request.getFeedback().getId();
            String status = request.getStatus();

            preparedStatement.setString(1, text);
            preparedStatement.setString(2, status);

            if(!isFeedbackEmpty(request))
                preparedStatement.setInt(3, feedback_id);

            preparedStatement.execute();


        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        request.setId(getRequestIndex(request));
        return true;
    }

    public Integer getRequestIndex(Request request){
        String query = "SELECT * FROM requests WHERE request_text = '" + request.getText() + "' AND status = '" + request.getStatus() + "';";
        System.out.println(query);
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

    public boolean isFeedbackEmpty(Request request){
        return request.getFeedback() == null;
    }

    @Override
    public Request update(Request request, int key) {
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        String query = null;
        if(!isFeedbackEmpty(request))
            query = "UPDATE requests SET request_text = ?, status = ?, feedback_id = ? WHERE id = "+key+";";
        else
            query = "UPDATE requests SET request_text = ?, status = ? WHERE id = "+key+";";
        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, request.getText());
//            preparedStatement.setInt(2, request.getUser().getId());
            preparedStatement.setString(2, request.getStatus());


            if(!isFeedbackEmpty(request)) {
                feedbackDAO.create(request.getFeedback());
                preparedStatement.setInt(3, request.getFeedback().getId());
            }

            preparedStatement.executeUpdate();
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

//                int user_id = resultSet.getInt("user_id");
//                User user = userDAO.findEntityById(user_id);

                int feedback_id = resultSet.getInt("feedback_id");
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                requests.add(new Request(id, text, status, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


        return requests;

    }

    public List<Item> getItemsOfRequest(int request_id){
        feedbackDAO = new FeedbackDAO(ConnectionManager.getConnection());
        List<Item> items = new ArrayList<>();

        String query = "SELECT * FROM requests_items WHERE request_id = " + request_id;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                String info = resultSet.getString("info");

//          !!!!!    items.add(itemDAO.findEntityById(item_id));
            }

            return items;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Request> findLimitRequests(int currentPage, int recordsPerPage) throws SQLException {
        List<Request> requests = new ArrayList<>();

        int start = currentPage * recordsPerPage - recordsPerPage;
        String sql = "SELECT * FROM requests LIMIT ?, ?";


            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, recordsPerPage);

            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String text = resultSet.getString("request_text");
                String status = resultSet.getString("status");
                int feedback_id = resultSet.getInt("feedback_id");

//                Feedback feedback = feedbackDAO.findEntityB--yId(feedback_id);

                requests.add(new Request(id, text, status));
            }
            return requests;
    }

    public int getAmountOfRequests() throws SQLException {
        String query = "SELECT COUNT(id) AS total FROM requests;";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        int total = 0;
        while (resultSet.next()){
            total = resultSet.getInt("total");
        }
        return total;
    }
}
