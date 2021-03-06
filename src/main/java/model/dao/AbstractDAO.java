package model.dao;

import java.sql.*;
import java.util.List;



public abstract class AbstractDAO<T extends Object> {
    protected Connection connection;
//    protected Statement statement;
//    protected PreparedStatement preparedStatement;
//    protected ResultSet resultSet;
    public AbstractDAO(Connection connection){
        this.connection = connection;
    }

    public abstract List<T> findAll();
    public abstract T findEntityById(int id);
    public abstract boolean delete(int id);
    public abstract boolean create(T entity);
    public abstract T update(T entity, int key);
    public void close(Statement st){
        try{
            if(st != null)
                st.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void close(PreparedStatement ps){
        try{
            if(ps != null)
                ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void close(ResultSet rs){
        try{
            if(rs != null)
                rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void clearResources(PreparedStatement ps, Statement st, ResultSet rs){
        try{
            if(ps != null)
                ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(st != null)
                st.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(rs != null)
                rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
