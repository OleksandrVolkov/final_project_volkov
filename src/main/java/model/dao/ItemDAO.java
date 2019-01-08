package model.dao;

import model.entity.Item;
import model.entity.Request;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends AbstractDAO<Item>{
    public ItemDAO(Connection connection){
        super(connection);
    }

    @Override
    public List<Item> findAll() {
        String query = "SELECT * FROM items;";
        List<Item> items = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String info = resultSet.getString("info");

                items.add(new Item(id, name, info));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return items;
    }

    @Override
    public Item findEntityById(int id) {
        String query = "SELECT * FROM items WHERE id = " + id + ";";
       Item item = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String info = resultSet.getString("info");

                item = new Item(id, name, info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return item;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM items WHERE id = " + id + ";";
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
    public boolean create(Item item) {
        String query = "INSERT INTO items(name, info) VALUES(?,?);";
        String name, info;
        try{
            preparedStatement = connection.prepareStatement(query);
            name = item.getName();
            info = item.getInfo();

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, info);

            preparedStatement.execute();

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }

        item.setId(getItemIndexByName(name));

        return true;
    }

    public Integer getItemIndexByName(String name){
        String query = "SELECT * FROM items WHERE name = '"+name+"';";
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
    public Item update(Item item, int key) {
        String query = "UPDATE items SET name = ?, info = ? WHERE id = "+key+";";

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getInfo());

            preparedStatement.executeUpdate();
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Item> getItemsByRequest(Request request){
        String query = "SELECT * FROM requests_items WHERE request_id = '"+request.getId()+"';";
        List<Item> items = new ArrayList<>();
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("item_id");
                items.add(this.findEntityById(id));
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



}
