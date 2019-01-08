package model.dao;

import model.dao.connection.ConnectionManager;
import model.entity.Item;
import model.entity.Reject;
import model.entity.Request;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RejectDAO extends AbstractDAO<Reject>{
    public RejectDAO(Connection connection){
        super(connection);
    }

    @Override
    public List<Reject> findAll() {
        String query = "SELECT * FROM rejects;";
        List<Reject> rejects = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String text = resultSet.getString("reject_text");
                int requestId = resultSet.getInt("request_id");
                rejects.add(new Reject(id, text, requestId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return rejects;
    }

    @Override
    public Reject findEntityById(int id) {
        String query = "SELECT * FROM rejects WHERE id = " + id + ";";
        Reject reject = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String text = resultSet.getString("reject_text");
                int requestId = resultSet.getInt("request_id");
                reject = new Reject(id, text, requestId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return reject;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM rejects WHERE id = " + id + ";";
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
    public boolean create(Reject reject) {
        String query = "INSERT INTO rejects(reject_text, request_id) VALUES(?, ?);";
        try{
            preparedStatement = connection.prepareStatement(query);
            String text = reject.getText();
            int requestId = reject.getRequestId();
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, requestId);
            preparedStatement.execute();
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
//        item.setId(getItemIndexByName(name));
        return true;
    }


//    public Integer getRejectIndexByRequestId(int requestId){
//        String query = "SELECT * FROM rejects WHERE request_id = '" + requestId + "';";
//        try {
//            statement = connection.prepareStatement(query);
//            resultSet = statement.executeQuery(query);
//            int id = 0;
//            while (resultSet.next())
//                id = resultSet.getInt("id");
//
//            return id;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public Reject update(Reject reject, int key) {
        String query = "UPDATE rejects SET reject_text = ?, request_id = ? WHERE id = "+key+";";

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, reject.getText());
            preparedStatement.setInt(2, reject.getRequestId());

            preparedStatement.executeUpdate();
            return reject;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
