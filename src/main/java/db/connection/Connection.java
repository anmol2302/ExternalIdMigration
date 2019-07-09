package db.connection;


import com.datastax.driver.core.ResultSet;

public interface Connection {


    public ResultSet getResult(String query);

    public void closeConnection();
}
