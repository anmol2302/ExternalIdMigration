package db.connection;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

public class CassandraConnection implements Connection {


    private static Cluster cluster;
    private static Session session;

    public CassandraConnection(String host, String keyspaceName) {
        initializeConnection(host, keyspaceName);

    }

    @Override
    public ResultSet getResult(String query) {
        ResultSet resultSet = session.execute(query);
        return resultSet;
    }

    @Override
    public void closeConnection() {
        cluster.close();

    }

    public static void initializeConnection(String host, String keyspaceName) {

        cluster = Cluster.builder().addContactPoint(host).build();
        session = cluster.connect(keyspaceName);
        session.execute("USE ".concat(keyspaceName));
    }
}
