import com.datastax.driver.core.*;
import constants.EnvConstants;
import db.connection.Connection;
import db.connection.factory.CassandraConnectionFactory;
import db.connection.factory.ConnectionFactory;
import org.apache.log4j.Logger;

import java.util.*;

public class ExternalIdDecryption {
    static Logger logger = LoggerFactory.getLoggerInstance(ExternalIdDecryption.class.getName());
    private static String className = ExternalIdDecryption.class.getSimpleName();


    /**
     * the script begins here...
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        RequestParams requestParams = prepareRequestParams();
        RequestParamValidator.getInstance(requestParams).validate();
        List<User> usersList = getUserDataFromDbAsList(requestParams);
        System.out.println(usersList.get(0).getProvider());
    }


    /**
     * this method will prepare RequestParams object
     * while reading constants from env
     *
     * @return RequestParams
     */
    public static RequestParams prepareRequestParams() {
        RequestParams requestParams = new RequestParams();
        requestParams.setCassandraHost(System.getenv(EnvConstants.SUNBIRD_CASSANDRA_HOST));
        requestParams.setCassandraKeyspaceName(System.getenv(EnvConstants.SUNBIRD_CASSANDRA_KEYSPACENAME));
        requestParams.setCassandraPort(System.getenv(EnvConstants.SUNBIRD_CASSANDRA_PORT));
        requestParams.setSunbirdEncryptionKey(System.getenv(EnvConstants.SUNBIRD_ENCRYPTION_KEY));
        logger.info(String.format("%s:%s: env variable got %s", className, "prepareRequestParams", requestParams.toString()));
        return requestParams;
    }





    /**
     * this method is responsible to fetch the user external id data from cassandra
     * and the table name is usr_external_identity
     * and after fetching will convert the  resultSet to List<User>
     *
     * @param requestParams
     * @return List<User>
     */
    public static List<User> getUserDataFromDbAsList(RequestParams requestParams) {
        ConnectionFactory connectionFactory = new CassandraConnectionFactory();
        Connection connection = connectionFactory.getConnection(requestParams.getCassandraHost(), requestParams.getCassandraKeyspaceName());
        ResultSet resultSet = connection.getResult("select * from usr_external_identity");
        List<User> usersList = CassandraHelper.getUserListFromResultSet(resultSet);
        connection.closeConnection();
        return usersList;
    }

}
