package db.connection.factory;

import db.connection.CassandraConnection;
import db.connection.Connection;

public class CassandraConnectionFactory implements ConnectionFactory {
    @Override
    public Connection getConnection(String hostName,String keyspaceName) {
        return new CassandraConnection(hostName, keyspaceName);
    }
}
