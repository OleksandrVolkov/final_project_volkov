package model.dao;

import model.dao.connection.ConnectionManager;
import model.entity.Item;
import model.entity.Reject;
import model.entity.Request;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RejectDAO extends AbstractDAO<Reject>{
    private static Logger log = Logger.getLogger(RejectDAO.class);

    public RejectDAO(Connection connection){
        super(connection);
    }

    @Override
    public List<Reject> findAll() {
        log.info("Finding all rejects");
        String query = "SELECT * FROM rejects;";
        List<Reject> rejects = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            log.info("Creating statement");
            statement = connection.createStatement();
            log.info("Creating result set");
            resultSet = statement.executeQuery(query);
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
            close(statement);
            close(resultSet);
        }

        log.trace("Returning rejects");
        return rejects;
    }

    @Override
    public Reject findEntityById(int id) {
        log.info("Finding reject with id = " + id);
        String query = "SELECT * FROM rejects WHERE id = " + id + ";";
        Reject reject = null;

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            statement = connection.createStatement();
            log.trace("Creating result set");
            resultSet = statement.executeQuery(query);
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
            close(statement);
            close(resultSet);
        }
        log.trace("Returning reject");
        return reject;
    }

    @Override
    public boolean delete(int id) {
        log.info("Deleting reject with id = " + id);
        String query = "DELETE FROM rejects WHERE id = " + id + ";";
        Statement statement = null;
        try {
            log.trace("Creating statement ");
            statement = connection.createStatement();
            log.trace("Executing update");
            statement.executeUpdate(query);
            log.trace("Reject is deleted");
            return true;
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return false;
        } finally {
            close(statement);
        }
    }

    @Override
    public boolean create(Reject reject) {
        log.info("Creating reject with requestId = " + reject.getRequestId());
        String query = "INSERT INTO rejects(reject_text, request_id) VALUES(?, ?);";
        PreparedStatement preparedStatement = null;
        try{
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
        } catch(SQLException e){
            log.warn("Error a bit:(", e);
            return false;
        } finally {
            close(preparedStatement);
        }
        log.trace("Reject is created");
        return true;
    }


    public Integer getLastInsertedRejectIndex(){
        String query = "SELECT MAX( id ) AS max_id FROM rejects;";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery(query);
            int id = 0;
            if (resultSet.next())
                id = resultSet.getInt("max_id");
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(statement);
            close(resultSet);
        }
    }




    public Reject getRejectByRequestId(int requestId){
        String query = "SELECT * FROM rejects WHERE request_id = '" + requestId + "';";

        Statement statement = null;
        ResultSet resultSet = null;
        Reject reject = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery(query);
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt("id");
                String text = resultSet.getString("reject_text");
                reject = new Reject(id, text, requestId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement);
            close(resultSet);
        }
        return reject;
    }

    @Override
    public Reject update(Reject reject, int key) {
        log.trace("Updating reject with id = " + key);
        String query = "UPDATE rejects SET reject_text = ?, request_id = ? WHERE id = "+key+";";

        PreparedStatement preparedStatement = null;
        try {
            log.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);

            log.trace("Setting values for prepared statement");
            preparedStatement.setString(1, reject.getText());
            preparedStatement.setInt(2, reject.getRequestId());

            log.trace("Executing update");
            preparedStatement.executeUpdate();
            log.trace("Returning reject");
            return reject;
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return null;
        } finally {
            close(preparedStatement);
        }
    }


}
