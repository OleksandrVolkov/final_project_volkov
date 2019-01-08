package model.dao;

import model.dao.connection.ConnectionManager;
import model.entity.Feedback;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO extends AbstractDAO<Feedback> {
    private UserDAO userDAO;
    private RequestDAO requestDAO;

    public FeedbackDAO(Connection connection){
        super(connection);
    }

    @Override
    public List<Feedback> findAll() {
        userDAO = new UserDAO(connection);
        requestDAO = new RequestDAO(connection);
        String query = "SELECT * FROM feedbacks;";
        List<Feedback> feedbacks = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String text = resultSet.getString("feedback_text");
                String date = resultSet.getString("feedback_date");
                int requestId = resultSet.getInt("request_id");
                feedbacks.add(new Feedback(id, text, date, requestId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


        return feedbacks;
    }

    @Override
    public Feedback findEntityById(int id) {
        userDAO = new UserDAO(connection);
        requestDAO = new RequestDAO(connection);
        Feedback feedback = null;
        String query = "SELECT * FROM feedbacks WHERE id = " + id + ";";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String text = resultSet.getString("feedback_text");
                String date = resultSet.getString("feedback_date");
                int request_id = resultSet.getInt("request_id");
                feedback = new Feedback(id, text, date, request_id);
            }

            return feedback;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        userDAO = new UserDAO(connection);
        requestDAO = new RequestDAO(connection);
        String query = "DELETE FROM feedbacks WHERE id = " + id + ";";
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
    public boolean create(Feedback feedback) {
        userDAO = new UserDAO(connection);
        requestDAO = new RequestDAO(connection);
        String query = "INSERT INTO feedbacks(feedback_text, feedback_date, request_id) VALUES(?,?,?);";
        try{
            preparedStatement = connection.prepareStatement(query);
            String text = feedback.getText();
            String date = feedback.getDate();
            int requestId = feedback.getRequestId();

            preparedStatement.setString(1, text);
            preparedStatement.setString(2, date);
            preparedStatement.setInt(3, requestId);
            preparedStatement.execute();

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }

        feedback.setId(getFeedbackIndex(feedback));
        return true;
    }

    //Отзыв и текст не могут быть здесь одинаковыми - пофиксить!!!!!!!!!!!!!!!
    public Integer getFeedbackIndex(Feedback feedback){
        //  String query = "SELECT * FROM items WHERE name = " + name + ";";
        String query = "SELECT * FROM feedbacks WHERE feedback_text = '" + feedback.getText() + "' AND feedback_date = '" + feedback.getDate() + "';";

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

    @Override
    public Feedback update(Feedback feedback, int key) {
        userDAO = new UserDAO(connection);
        requestDAO = new RequestDAO(connection);
        String query = "UPDATE feedbacks SET feedback_text = ?, feedback_date = ?, request_id = ? WHERE id = "+key+";";

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, feedback.getText());
            preparedStatement.setString(2, feedback.getDate());
            preparedStatement.setInt(3, feedback.getRequestId());
//            preparedStatement.setInt(3, feedback.getUser().getId());
//            preparedStatement.setInt(4, feedback.getRequest().getId());

            preparedStatement.executeUpdate();
            return feedback;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Feedback getFeedbackByRequestId(int requestId){
        String query = "SELECT * FROM feedbacks WHERE request_id = " + requestId + ";";
        Feedback feedback = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            if(resultSet.next()){
                String text = resultSet.getString("feedback_text");
                String date = resultSet.getString("feedback_date");
                feedback = new Feedback(text, date, requestId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedback;
    }
}
