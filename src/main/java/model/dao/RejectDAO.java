package model.dao;

import model.dao.connection.ConnectionManager;
import model.entity.Item;
import model.entity.Reject;
import model.entity.Request;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 *  RejectDAO represents a way to access database to the corresponding to the
 *  Feedback entity table "rejects" via JDBC API by SQL server.
 *  It represents the way to access to the value needed and make basic CRUD (create,
 *  read, update, delete) operations and some more added functionality.
 *  Moreover, it gives the opportunity to initialize the entity
 *  objects (Reject class) on the side of model which makes it easier to manipulate with the objects
 *  in the application in the object-oriented way.
 *  It extends an abstract AbstractDAO class and therefore overrides some its methods.
 *
 *
 * @author  Oleksandr Volkov
 * @version 1.0
 * @since   2019-01-15
 */

public class RejectDAO extends AbstractDAO<Reject>{
    private static Logger log = Logger.getLogger(RejectDAO.class);

    public RejectDAO(Connection connection){
        super(connection);
    }

   /**
    * This method is used to find all rejects from the corresponding table in the
    * database.
    * @return List of all of the rejects available in the table.
    */
    @Override
    public List<Reject> findAll() {
        log.info("Finding all rejects");
        String query = "SELECT * FROM rejects;";
        List<Reject> rejects = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.info("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            log.info("Creating result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                log.trace("Getting values of the result set");
                int id = resultSet.getInt("id");
                String text = resultSet.getString("reject_text");
                int requestId = resultSet.getInt("request_id");
                rejects.add(new Reject(id, text, requestId));
                log.trace("New reject with id = " + id + " is added to the array");
            }
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return null;
        } finally {
            close(preparedStatement);
            close(resultSet);
        }

        log.trace("Returning rejects");
        return rejects;
    }

    /**
     * This method is used to find reject by its id in the database.
     * @param id This is the id of the reject is needed to find.
     * @return Reject It returns the reject by the given id.
     */
    @Override
    public Reject findEntityById(int id) {
        log.info("Finding reject with id = " + id);
        String query = "SELECT * FROM rejects WHERE id = " + id + ";";
        Reject reject = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                log.trace("Getting values from result set");
                String text = resultSet.getString("reject_text");
                int requestId = resultSet.getInt("request_id");
                reject = new Reject(id, text, requestId);
                log.trace("Reject is created");
            }
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return null;
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
        log.trace("Returning reject");
        return reject;
    }


    /**
     * This method is used to delete reject by its id.
     * @param id This is id of the reject is needed to delete.
     * @return boolean It returns the boolean value depending on whether reject was deleted.
     * (by given id)
     */
     @Override
     public boolean delete(int id) {
        log.info("Deleting reject with id = " + id);
        String query = "DELETE FROM rejects WHERE id = ?;";
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            log.trace("Creating statement ");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            log.trace("Executing update");
            preparedStatement.executeUpdate();
            log.trace("Reject is deleted");
            connection.commit();
            return true;
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
    }


    /**
     * This method is used to create reject by given object of the corresponding class.
     * @param reject This is the item which values will be inserted into the table "rejects".
     * @return boolean It returns the boolean value depending on whether reject was successfully created.
     */
    @Override
    public boolean create(Reject reject) {
        log.info("Creating reject with requestId = " + reject.getRequestId());
        String query = "INSERT INTO rejects(reject_text, request_id) VALUES(?, ?);";
        PreparedStatement preparedStatement = null;
        try{
            connection.setAutoCommit(false);
            log.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Setting values for prepared statement");
            String text = reject.getText();
            int requestId = reject.getRequestId();
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, requestId);
            log.trace("Executing prepared statement");
            preparedStatement.execute();
            System.out.println(requestId + "      " + text);
            reject.setId(this.getLastInsertedRejectIndex());
            connection.commit();
        } catch(SQLException e){
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
        log.trace("Reject is created");
        return true;
    }


    /**
     * This method is used to get index of the last inserted reject.
     * It is mainly used while creating reject so that get its index for
     * future handling in the application.
     * @return Integer It returns the index of the last inserted reject.
     */
     public Integer getLastInsertedRejectIndex(){
        String query = "SELECT MAX( id ) AS max_id FROM rejects;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            int id = 0;
            if (resultSet.next())
                id = resultSet.getInt("max_id");
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
    }



    /**
     * This method is used to get reject value in the database by its request_actions id.
     * @param requestId This is the request_actions id which is used to get the searched reject
     * @return Reject It returns the reject whiich is found by the requestId.
     */
    public Reject getRejectByRequestId(int requestId){
        String query = "SELECT * FROM rejects WHERE request_id = ?;";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Reject reject = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, requestId);
            resultSet = preparedStatement.executeQuery();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt("id");
                String text = resultSet.getString("reject_text");
                reject = new Reject(id, text, requestId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
        return reject;
    }

    /**
     * This method is used to update reject value in the database by its key.
     * @param reject This is the reject which values will be inserted into the table "rejects"
     * @param key This is the id which is used to update the corresponding value
     * @return Reject It returns given reject
     */
    @Override
    public Reject update(Reject reject, int key) {
        log.trace("Updating reject with id = " + key);
        String query = "UPDATE rejects SET reject_text = ?, request_id = ? WHERE id = "+key+";";

        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            log.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);

            log.trace("Setting values for prepared statement");
            preparedStatement.setString(1, reject.getText());
            preparedStatement.setInt(2, reject.getRequestId());

            log.trace("Executing update");
            preparedStatement.executeUpdate();
            log.trace("Returning reject");
            connection.commit();
            return reject;
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            try {
                connection.rollback();
                log.warn("Successfully rolled back changes from the database");
            } catch (SQLException e1) {
                e1.printStackTrace();
                log.warn("Could not rollback updates " + e1.getMessage());
            }
            return null;
        } finally {
            close(preparedStatement);
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
