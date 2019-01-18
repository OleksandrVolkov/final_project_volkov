package model.dao;

import model.dao.connection.ConnectionManager;
import model.entity.Feedback;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



/**
 *  <h1>FeedbackDAO class</h1>
 *  FeedbackDAO represents a way to access database to the corresponding to the
 *  Feedback entity table "feedbacks" via JDBC API by SQL server.
 *  It represents the way to access to the value needed and make basic CRUD (create,
 *  read, update, delete) operations and some more added functionality.
 *  Moreover, it gives the opportunity to initialize the entity
 *  objects(Feedback class) on the side of model which makes it easier to manipulate with the objects
 *  in the application in the object-oriented way.
 *  It extends an abstract AbstractDAO class and therefore overrides some its methods.
 *
 *
 * @author  Oleksandr Volkov
 * @version 1.0
 * @since   2019-01-15
 */


public class FeedbackDAO extends AbstractDAO<Feedback>{
    private UserDAO userDAO;
    private RequestDAO requestDAO;
    private static Logger log = Logger.getLogger(Feedback.class);

//    /**
//     * This constructor is used to initialize the connection via
//     * sending it to the super class AbstractDAO
//     */
    public FeedbackDAO(Connection connection){
        super(connection);
        userDAO = new UserDAO(ConnectionManager.getConnection());
        requestDAO = new RequestDAO(ConnectionManager.getConnection());
    }


    /**
     * This method is used to find all feedbacks from the corresponding table in the
     * database.
     * @return List of all of the feedbacks available in the table.
     */
    @Override
    public List<Feedback> findAll() {
        log.trace("Instantiating UserDAO class and RequestDAO class");
        List<Feedback> feedbacks = new ArrayList<>();
        String query = "SELECT * FROM feedbacks;";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //log.trace("open connection");
            log.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Getting result set from the statement");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                log.trace("Creating a feedback to add in array of feedbacks");
                int id = resultSet.getInt("id");
                String text = resultSet.getString("feedback_text");
                String date = resultSet.getString("feedback_date");
                int requestId = resultSet.getInt("request_id");
                feedbacks.add(new Feedback(id, text, date, requestId));
                log.info("Feedback with ID " + id + " and with the request_actions id " + requestId + " is added to the array");
            }
        } catch (SQLException e) {
            log.trace("Cannot iterate through the result set", e);
        } finally {
            close(preparedStatement);
            close(resultSet);
        }

        log.trace("Returning feedback");
        return feedbacks;
    }


    /**
     * This method is used to find feedback by its id in the database
     * @param id This is id of the feedback is needed
     * @return Feedback It returns the feedback by the given id
     */
    @Override
    public Feedback findEntityById(int id) {
        log.info("finding feedback with id: " + id);
        log.trace("creating userDAO and requestDAO objects");
        Feedback feedback = null;
        String query = "SELECT * FROM feedbacks WHERE id = ?;";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement to the connection");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            log.trace("Getting result set from the statement");
            resultSet = preparedStatement.executeQuery();
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
            close(preparedStatement);
            close(resultSet);
        }
    }

    /**
     * This method is used to delete feedback by its id
     * @param id This is id of the feedback is needed
     * @return boolean It returns the boolean value depending on whether feedback was deleted
     * (by given id)
     */
    @Override
    public boolean delete(int id) {
        log.info("Deleting feedback with id " + id);
        log.info("Instantiating UserDAO and RequestDAO objects");
//        userDAO = new UserDAO(connection);
//        requestDAO = new RequestDAO(connection);

        String query = "DELETE FROM feedbacks WHERE id = ?;";
        PreparedStatement preparedStatement = null;
        try {
            log.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            log.trace("Executing update");
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            log.warn("Can't execute the query", e);
            return false;
        } finally {
            close(preparedStatement);
        }
    }


    /**
     * This method is used to create feedback by given object of the corresponding class
     * @param feedback This is the feedback which values will be inserted into the table "feedbacks"
     * @return boolean It returns the boolean value depending on whether feedback was successfully created
     */
    @Override
    public boolean create(Feedback feedback) {
        log.info("Creating feedback with the request_actions id " + feedback.getRequestId());
        log.trace("Creating userDAO and requestDAO");

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
            log.warn("Can't execute the query", e);
            log.trace("Returning false");
            return false;
        } finally {
            close(preparedStatement);
        }
        log.trace("Setting id to the returned feedback object");
        log.trace("Returning true value");
        return true;
    }


    /**
     * This method is used to get index of the last inserted feedback.
     * It is mainly used while creating feedback so that get its index for
     * future handling in the application.
     * @return Integer It returns the index of the last inserted feedback
     */
    public Integer getLastInsertedFeedbackIndex(){
        log.info("Getting index of the given feedback");
        String query = "SELECT MAX( id ) AS max_id FROM feedbacks;";

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            log.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("max_id");
            }

            log.trace("Returning id");
            return id;
        } catch (SQLException e) {
            log.warn("Can't execute the query", e);
            return null;
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
    }

    /**
     * This method is used to update feedback value in the database by its key.
     * @param feedback This is the feedback which values will be inserted into the table "feedbacks"
     * @param key This is the id which is used to update the corresponding value
     * @return Feedback It returns given feedback
     */
    @Override
    public Feedback update(Feedback feedback, int key) {
        log.info("Updating feedaback with id = " + key);
        log.trace("Creating userDAO and request_actions DAO objects");
        String query = "UPDATE feedbacks SET feedback_text = ?, feedback_date = ?, request_id = ? WHERE id = ?;";

        PreparedStatement preparedStatement = null;
        try {
            log.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, feedback.getText());
            preparedStatement.setString(2, feedback.getDate());
            preparedStatement.setInt(3, feedback.getRequestId());
            preparedStatement.setInt(4, key);

            log.trace("Executing update");
            preparedStatement.executeUpdate();
            log.trace("Returning feedback with id = " + key);
            return feedback;
        } catch (SQLException e) {
            log.warn("Can't execute the query", e);
            return null;
        } finally {
            close(preparedStatement);
        }
    }




    /**
     * This method is used to get feedback value in the database by its request_actions id.
     * @param requestId This is the request_actions id which is used to get the searched feedback
     * @return Feedback It returns the feedback whiich is found by the requestId.
     */
    public Feedback getFeedbackByRequestId(int requestId){
        log.info("Getting feedback by request_actions id = " + requestId);
        String query = "SELECT * FROM feedbacks WHERE request_id = ?;";

        Feedback feedback = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, requestId);
            log.trace("Creating ResultSet");
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                log.trace("Getting values from the ResultSet");
                String text = resultSet.getString("feedback_text");
                String date = resultSet.getString("feedback_date");
                log.trace("Creating returned object");
                feedback = new Feedback(text, date, requestId);
            }
        } catch (SQLException e) {
            log.warn("Can't execute the query", e);
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
        log.trace("Returning feedback with request_actions id = " + requestId);
        return feedback;
    }
}
