package model.dao;

import model.entity.Item;
import model.entity.Request;
import org.apache.log4j.Logger;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends AbstractDAO<Item>{
    private static Logger log = Logger.getLogger(ItemDAO.class);

    public ItemDAO(Connection connection){
        super(connection);
    }

    @Override
    public List<Item> findAll() {
        log.info("Finding all objects");
        String query = "SELECT * FROM items;";
        List<Item> items = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            statement = connection.createStatement();
            log.trace("Creating result set");
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                log.trace("Getting values from result set");
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String info = resultSet.getString("info");

                items.add(new Item(id, name, info));
                log.trace("Item with id = " + id);
            }
        } catch (SQLException e) {
            log.warn("Error a bit :(", e);
            return null;
        } finally {
            close(statement);
            close(resultSet);
        }
        log.trace("Returning items");
        return items;
    }

    @Override
    public Item findEntityById(int id) {
        log.info("Finding item by id = " + id);
        String query = "SELECT * FROM items WHERE id = " + id + ";";
        Item item = null;

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            statement = connection.createStatement();
            log.trace("Creating result set");
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                log.trace("Getting values from result set");
                String name = resultSet.getString("name");
                String info = resultSet.getString("info");

                item = new Item(id, name, info);
                log.trace("Item with id = " + id + " is found");
            }
        } catch (SQLException e) {
            log.warn("Error a bot:(", e);
            return null;
        } finally {
            close(statement);
            close(resultSet);
        }
        log.trace("Returning item with id = " + id);
        return item;
    }

    @Override
    public boolean delete(int id) {
        log.info("Deleting item with id = " + id);
        String query = "DELETE FROM items WHERE id = " + id + ";";
        Statement statement = null;
        try {
            log.trace("Creating statement");
            statement = connection.createStatement();
            statement.executeUpdate(query);
            log.trace("Item is deleted");
            return true;
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return false;
        } finally {
            close(statement);
        }
    }

    @Override
    public boolean create(Item item) {
//        log.info("Creating item with id = " + item.getId());
        String query = "INSERT INTO items(name, info) VALUES(?,?);";
        String name, info;
        PreparedStatement preparedStatement = null;
        try{
            log.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);
            name = item.getName();
            info = item.getInfo();

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, info);

            log.trace("Creating prepared statement");
            preparedStatement.execute();
            log.trace("Item is created");
        } catch(SQLException e){
            log.warn("Error a bit:(", e);
            return false;
        } finally {
            close(preparedStatement);
        }

        log.trace("Setting id for just created item for the object");
//        item.setId(getItemIndexByName(name));
        item.setId(this.getLastInsertedItemIndex());

        log.trace("Item is created");
        return true;
    }

    public Integer getLastInsertedItemIndex(){
//        log.info("Getting index by name = " + name);
//        String query = "SELECT * FROM items WHERE name = '" + name + "';";
        String query = "SELECT MAX( id ) AS max_id FROM items;";

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            statement = connection.prepareStatement(query);
            log.trace("Creating result set");
            resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                log.trace("getting values of the result set");
                id = resultSet.getInt("max_id");
            }
            log.trace("Returning index of item: " + id);
            return id;
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return null;
        } finally {
            close(statement);
            close(resultSet);
        }
    }

    @Override
    public Item update(Item item, int key) {
        log.info("Updating item with id = " + key);
        String query = "UPDATE items SET name = ?, info = ? WHERE id = " + key + ";";

        PreparedStatement preparedStatement = null;
        try {
            log.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Setting values of the item with id = " + key);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getInfo());
            log.trace("Executing update");
            preparedStatement.executeUpdate();
            log.trace("Item is updated, returning item");
            return item;
        } catch (SQLException e) {
            log.warn("Error a bit:(", e);
            return null;
        } finally {
            close(preparedStatement);
        }
    }

    public List<Item> getItemsByRequest(Request request){
        String query = "SELECT * FROM requests_items WHERE request_id = '"+request.getId()+"';";
        List<Item> items = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();

        ResultSet resultSet = null;
        Statement statement = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("item_id");
                ids.add(id);
            }
            for(int i = 0; i < ids.size(); i++){
                items.add(this.findEntityById(ids.get(i)));
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(statement);
            close(resultSet);
        }
    }
}
