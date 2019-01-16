package model.dao;

import model.dao.connection.ConnectionManager;
import model.entity.Feedback;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO extends AbstractDAO<Feedback>{
    private UserDAO userDAO;
    private RequestDAO requestDAO;
    private static Logger log = Logger.getLogger(Feedback.class);


    public FeedbackDAO(Connection connection){
        super(connection);
        userDAO = new UserDAO(ConnectionManager.getConnection());
        requestDAO = new RequestDAO(ConnectionManager.getConnection());
    }

    @Override
    public List<Feedback> findAll() {
        log.trace("Instantiating UserDAO class and RequestDAO class");
        List<Feedback> feedbacks = new ArrayList<>();
        String query = "SELECT * FROM feedbacks;";

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //log.trace("open connection");
            log.trace("Creating statement");
            statement = connection.createStatement();
            log.trace("Getting result set from the statement");
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                log.trace("Creating a feedback to add in array of feedbacks");
                int id = resultSet.getInt("id");
                String text = resultSet.getString("feedback_text");
                String date = resultSet.getString("feedback_date");
                int requestId = resultSet.getInt("request_id");
                feedbacks.add(new Feedback(id, text, date, requestId));
                log.info("Feedback with ID " + id + " and with the request id " + requestId + " is added to the array");
            }
        } catch (SQLException e) {
            log.trace("Cannot iterate through the result set", e);
        } finally {
            close(statement);
            close(resultSet);
        }

        log.trace("Returning feedback");
        return feedbacks;
    }

    @Override
    public Feedback findEntityById(int id) {
        log.info("finding feedback with id: " + id);
        log.trace("creating userDAO and requestDAO objects");
//        userDAO = new UserDAO(connection);
//        requestDAO = new RequestDAO(connection);
        Feedback feedback = null;
        String query = "SELECT * FROM feedbacks WHERE id = " + id + ";";

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement to the connection");
            statement = connection.createStatement();
            log.trace("Getting result set from the statement");
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                 log.trace("Creating feedback to return");
                 String text = resultSet.getString("feedback_text");
                 String date = resultSet.getString("feedback_date");
                 int request_id = resultSet.getInt("request_id");
                 log.info("Feedback with the ID " + id + " and requestId " + request_id + " is created");
                 feedback = new Feedback(id, text, date, request_id);
            }

            return feedback;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(statement);
            close(resultSet);
        }
    }

    @Override
    public boolean delete(int id) {
        log.info("Deleting feedback with id " + id);
        log.info("Instantiating UserDAO and RequestDAO objects");
//        userDAO = new UserDAO(connection);
//        requestDAO = new RequestDAO(connection);
        String query = "DELETE FROM feedbacks WHERE id = " + id + ";";
        Statement statement = null;
        try {
            log.trace("Creating statement");
            statement = connection.createStatement();
            log.trace("Executing update");
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return false;
        } finally {
            close(statement);
        }
    }

    @Override
    public boolean create(Feedback feedback) {
        log.info("Creating feedback with the request id " + feedback.getRequestId());
        log.trace("Creating userDAO and requestDAO");
//        userDAO = new UserDAO(connection);
//        requestDAO = new RequestDAO(connection);

        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO feedbacks(feedback_text, feedback_date, request_id) VALUES(?,?,?);";
        try{
            log.trace("Creating prepared statement ");
            preparedStatement = connection.prepareStatement(query);
            String text = feedback.getText();
            String date = feedback.getDate();
            int requestId = feedback.getRequestId();

            preparedStatement.setString(1, text);
            preparedStatement.setString(2, date);
            preparedStatement.setInt(3, requestId);
            log.trace("Executing prepared statement");
            preparedStatement.execute();
            feedback.setId(this.getLastInsertedFeedbackIndex());
        } catch(SQLException e){
            log.trace("Error a bit:(");
            log.trace("Returning false");
            return false;
        } finally {
            close(preparedStatement);
        }
        log.trace("Setting id to the returned feedback object");
        log.trace("Returning true value");
        return true;
    }

    public Integer getLastInsertedFeedbackIndex(){
        log.info("Getting index of the given feedback");
        //  String query = "SELECT * FROM items WHERE name = " + name + ";";
//        String query = "SELECT * FROM feedbacks WHERE feedback_text = '" + feedback.getText() + "' AND feedback_date = '" + feedback.getDate() + "';";
       String query = "SELECT MAX( id ) AS max_id FROM feedbacks;";

       ResultSet resultSet = null;
       Statement statement = null;
        try {
            log.trace("Creating statement");
            statement = connection.prepareStatement(query);
            log.trace("Creating result set");
            resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("max_id");
            }

            log.trace("Returning id");
            return id;
        } catch (SQLException e) {
            log.warn("Error a bit:( ", e);
            return null;
        } finally {
            close(statement);
            close(resultSet);
        }
    }

    @Override
    public Feedback update(Feedback feedback, int key) {
        log.info("Updating feedaback with id = " + key);
        log.trace("Creating userDAO and request DAO objects");
//        userDAO = new UserDAO(connection);
//        requestDAO = new RequestDAO(connection);
        String query = "UPDATE feedbacks SET feedback_text = ?, feedback_date = ?, request_id = ? WHERE id = "+key+";";

        PreparedStatement preparedStatement = null;
        try {
            log.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, feedback.getText());
            preparedStatement.setString(2, feedback.getDate());
            preparedStatement.setInt(3, feedback.getRequestId());
//            preparedStatement.setInt(3, feedback.getUser().getId());
//            preparedStatement.setInt(4, feedback.getRequest().getId());

            log.trace("Executing update");
            preparedStatement.executeUpdate();
            log.trace("Returning feedback with id = " + key);
            return feedback;
        } catch (SQLException e) {
            log.warn("Error a bit:( ", e);
            return null;
        } finally {
            close(preparedStatement);
        }
    }

    public Feedback getFeedbackByRequestId(int requestId){
        log.info("Getting feedback by request id = " + requestId);
        String query = "SELECT * FROM feedbacks WHERE request_id = " + requestId + ";";

        Feedback feedback = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            statement = connection.createStatement();
            log.trace("Creating ResultSet");
            resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                log.trace("Getting values from the ResultSet");
                String text = resultSet.getString("feedback_text");
                String date = resultSet.getString("feedback_date");
                log.trace("Creating returned object");
                feedback = new Feedback(text, date, requestId);
            }
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
        } finally {
            close(statement);
            close(resultSet);
        }
        log.trace("Returning feedback with request id = " + requestId);
        return feedback;
    }
}
