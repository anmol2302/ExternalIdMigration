package db.connection.factory;

import db.connection.Connection;


/**
 * @author anmolgupta
 */
public interface ConnectionFactory {


    Connection getConnection(String hostName, String keyspaceName);
}
