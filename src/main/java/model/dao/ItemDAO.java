package model.dao;

import model.entity.Item;
import model.entity.Request;
import org.apache.log4j.Logger;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 *  ItemDAO represents a way to access database to the corresponding to the
 *  Feedback entity table "items" via JDBC API by SQL server.
 *  It represents the way to access to the value needed and make basic CRUD (create,
 *  read, update, delete) operations and some more added functionality.
 *  Moreover, it gives the opportunity to initialize the entity
 *  objects (Item class) on the side of model which makes it easier to manipulate with the objects
 *  in the application in the object-oriented way.
 *  It extends an abstract AbstractDAO class and therefore overrides some its methods.
 *
 *
 * @author  Oleksandr Volkov
 * @version 1.0
 * @since   2019-01-15
 */

public class ItemDAO extends AbstractDAO<Item>{
    private static Logger log = Logger.getLogger(ItemDAO.class);

    public ItemDAO(Connection connection){
        super(connection);
    }


    /**
     * This method is used to find all items from the corresponding table in the
     * database.
     * @return List of all of the items available in the table.
     */
    @Override
    public List<Item> findAll() {
        log.info("Finding all objects");
        String query = "SELECT * FROM items;";
        List<Item> items = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
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
            close(preparedStatement);
            close(resultSet);
        }
        log.trace("Returning items");
        return items;
    }


    /**
     * This method is used to find item by its id in the database.
     * @param id This is id of the item is needed to find.
     * @return Item It returns the item by the given id.
     */
    @Override
    public Item findEntityById(int id) {
        log.info("Finding item by id = " + id);
        String query = "SELECT * FROM items WHERE id = ?;";
        Item item = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Creating result set");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
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
            close(preparedStatement);
            close(resultSet);
        }
        log.trace("Returning item with id = " + id);
        return item;
    }


    /**
     * This method is used to delete item by its id.
     * @param id This is id of the item is needed to delete.
     * @return boolean It returns the boolean value depending on whether item was deleted.
     * (by given id)
     */
    @Override
    public boolean delete(int id) {
        log.info("Deleting item with id = " + id);
        String query = "DELETE FROM items WHERE id = ?;";
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(true);
            log.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            log.trace("Item is deleted");
            connection.commit();
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
        return true;
    }


    /**
     * This method is used to create item by given object of the corresponding class.
     * @param item This is the item which values will be inserted into the table "items".
     * @return boolean It returns the boolean value depending on whether item was successfully created.
     */
    @Override
    public boolean create(Item item) {
//        log.info("Creating item with id = " + item.getId());
        String query = "INSERT INTO items(name, info) VALUES(?,?);";
        String name, info;
        PreparedStatement preparedStatement = null;
        try{
            connection.setAutoCommit(false);
            log.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);

            name = item.getName();
            info = item.getInfo();

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, info);

            log.trace("Creating prepared statement");
            preparedStatement.execute();
            log.trace("Item is created");
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

        log.trace("Setting id for just created item for the object");
//        item.setId(getItemIndexByName(name));
        item.setId(this.getLastInsertedItemIndex());

        log.trace("Item is created");
        return true;
    }


    /**
     * This method is used to get index of the last inserted item.
     * It is mainly used while creating item so that get its index for
     * future handling in the application.
     * @return Integer It returns the index of the last inserted item.
     */
    public Integer getLastInsertedItemIndex(){
//        log.info("Getting index by name = " + name);
//        String query = "SELECT * FROM items WHERE name = '" + name + "';";
        String query = "SELECT MAX( id ) AS max_id FROM items;";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Creating result set");
            resultSet = preparedStatement.executeQuery();
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
            close(preparedStatement);
            close(resultSet);
        }
    }

    /**
     * This method is used to update item value in the database by its key.
     * @param item This is the item which values will be inserted into the table "items"
     * @param key This is the id which is used to update the corresponding value
     * @return Feedback It returns given feedback
     */
    @Override
    public Item update(Item item, int key) {
        log.info("Updating item with id = " + key);
        String query = "UPDATE items SET name = ?, info = ? WHERE id = ?;";

        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            log.trace("Creating prepared statement");
            preparedStatement = connection.prepareStatement(query);
            log.trace("Setting values of the item with id = " + key);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getInfo());
            preparedStatement.setInt(3, key);
            log.trace("Executing update");
            preparedStatement.executeUpdate();
            log.trace("Item is updated, returning item");
            connection.commit();
            return item;
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
