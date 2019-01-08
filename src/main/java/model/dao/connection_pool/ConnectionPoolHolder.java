package model.dao.connection_pool;

import com.sun.org.apache.xerces.internal.impl.PropertyManager;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;
    private static DataSource getDataSource(){
        if(dataSource == null){
            synchronized (ConnectionPoolHolder.class){
                if(dataSource == null){
//                    BasicDataSource ds = new BasicDataSource();
//                    ds.setUrl(PropertyManager.CONFIG.getString(Config.DB_URL));
//                    ds.setUsername();
                }
            }
        }
        return null;
    }
}
