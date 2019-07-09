package db.connection.factory;

import db.connection.Connection;

public interface ConnectionFactory {

    Connection getConnection(String hostName, String keyspaceName);
}
