import com.datastax.driver.core.*;
import constants.EnvConstants;
import db.connection.Connection;
import db.connection.factory.CassandraConnectionFactory;
import db.connection.factory.ConnectionFactory;
import org.apache.http.util.TextUtils;
import org.apache.log4j.Logger;

import java.util.*;

public class ExternalIdDecryption {
    static Logger logger = LoggerFactory.getLoggerInstance(ExternalIdDecryption.class.getName());
    private static  String className= ExternalIdDecryption.class.getSimpleName();

    public static void main(String[] args) throws Exception {

        RequestParams requestParams = prepareRequestParams();
        if (isEnvValidated(requestParams)) {
            logger.debug(String.format("%s:%s: env variables verified",className,"main"));
            List<User> usersList = getUserDataFromDbAsList(requestParams);
            System.out.println(usersList.get(0).getUserId());
        }

    }


    /**
     * this method will prepare RequestParams object
     * while reading constants from env
     *
     * @return RequestParams
     */
    public static RequestParams prepareRequestParams() {
        RequestParams requestParams = new RequestParams();
        requestParams.setBaseUrl(System.getenv(EnvConstants.SUNBIRD_API_BASE_URL));
        requestParams.setApiKey(System.getenv(EnvConstants.SUNBIRD_API_KEY));
        requestParams.setAuthToken(System.getenv(EnvConstants.SUNBIRD_AUTH_TOKEN));
        requestParams.setCassandraHost(System.getenv(EnvConstants.SUNBIRD_CASSANDRA_HOST));
        requestParams.setCassandraKeyspaceName(System.getenv(EnvConstants.SUNBIRD_CASSANDRA_KEYSPACENAME));
        requestParams.setClientId(System.getenv(EnvConstants.SUNBIRD_SSO_CLIENT_ID));
        requestParams.setRealm(System.getenv(EnvConstants.SUNBIRD_SSO_REALM));
        logger.info(String.format("%s:%s: env variable got %s",className,"prepareRequestParams",requestParams.toString()));
        return requestParams;
    }


    /**
     * this method will only check weather no env should be null or empty
     *
     * @param params
     * @return boolean
     */
    public static boolean isInvalidEnv(String params) {
            return TextUtils.isEmpty(params) ? true : false;

    }


    /**
     * this method will validate the env variable.
     * all the variables are mandatory...
     *
     * @param requestParams
     * @return
     */
    public static boolean isEnvValidated(RequestParams requestParams) {

        if (isInvalidEnv(requestParams.getApiKey())) {
            throw new IllegalArgumentException(String.format("Valid %s is required", EnvConstants.SUNBIRD_API_KEY));
        }
        if (isInvalidEnv(requestParams.getBaseUrl())) {
            throw new IllegalArgumentException(String.format("Valid %s is required", EnvConstants.SUNBIRD_API_BASE_URL));
        }
        if (isInvalidEnv(requestParams.getCassandraHost())) {
            throw new IllegalArgumentException(String.format("Valid %s is required", EnvConstants.SUNBIRD_CASSANDRA_HOST));
        }
        if (isInvalidEnv(requestParams.getCassandraKeyspaceName())) {
            throw new IllegalArgumentException(String.format("Valid %s is required", EnvConstants.SUNBIRD_CASSANDRA_KEYSPACENAME));
        }
        if (isInvalidEnv(requestParams.getRealm())) {
            throw new IllegalArgumentException(String.format("Valid %s is required", EnvConstants.SUNBIRD_SSO_REALM));
        }
        if (isInvalidEnv(requestParams.getClientId())) {
            throw new IllegalArgumentException(String.format("Valid %s is required", EnvConstants.SUNBIRD_SSO_CLIENT_ID));
        }
        if (isInvalidEnv(requestParams.getAuthToken())) {
            throw new IllegalArgumentException(String.format("Valid %s is required", EnvConstants.SUNBIRD_AUTH_TOKEN));
        }
        return true;

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
        connection.closeConnection();
        List<User> usersList = CassandraHelper.getUserListFromResultSet(resultSet);
        return usersList;

    }


}
