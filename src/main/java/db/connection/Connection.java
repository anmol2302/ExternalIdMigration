package db.connection;

import com.datastax.driver.core.ResultSet;

import java.util.Map;


/**
 * this is an interface class for connection with db..
 *
 * @author anmolgupta
 */
public interface Connection {


    /**
     * this method will return the resultSet of the queried data
     *
     * @param query
     * @return
     */
    public ResultSet getResult(String query);


    /**
     * this method will be responsible to close the db connection
     */
    public void closeConnection();


    /**
     * this method will be responsible to delete record
     *
     * @param compositeKeysMap
     * @return
     */
    public boolean deleteRecord(Map<String, String> compositeKeysMap);

}
