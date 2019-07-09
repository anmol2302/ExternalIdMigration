package db.connection.factory;

import db.connection.CassandraConnection;
import db.connection.Connection;

public class CassandraConnectionFactory implements ConnectionFactory {
    @Override
    public Connection getConnection(String hostName,String keyspaceName,String port) {
        return new CassandraConnection(hostName, keyspaceName,port);
    }
}
