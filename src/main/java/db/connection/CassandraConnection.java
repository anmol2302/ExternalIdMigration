package db.connection;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import java.util.Map;

public class CassandraConnection implements Connection {


    private static Cluster cluster;
    private static Session session;
    private String keyspaceName;
    private String host;

    public CassandraConnection(String host, String keyspaceName) {
        this.keyspaceName = keyspaceName;
        this.host = host;
        initializeConnection();

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

    @Override
    public boolean deleteRecord(Map<String, String> compositeKeyMap) {
        Delete delete = QueryBuilder.delete().from(keyspaceName, "usr_external_identity");
        Delete.Where deleteWhere = delete.where();
        compositeKeyMap
                .entrySet()
                .stream()
                .forEach(
                        x -> {
                            Clause clause = QueryBuilder.eq(x.getKey(), x.getValue());
                            deleteWhere.and(clause);
                        });
       ResultSet resultSet= session.execute(delete);
       return resultSet.wasApplied();
    }

    /**
     * this method will initialize the cassandra connection
     *
     */
    public void initializeConnection() {
        cluster = Cluster.builder().addContactPoint(host).build();
        session = cluster.connect(keyspaceName);
        session.execute("USE ".concat(keyspaceName));
    }
}
