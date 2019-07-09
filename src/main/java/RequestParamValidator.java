import constants.EnvConstants;
import org.apache.http.util.TextUtils;
import org.apache.log4j.Logger;

public class RequestParamValidator {
    static Logger logger = LoggerFactory.getLoggerInstance(ExternalIdDecryption.class.getName());
    RequestParams requestParams = null;

    public RequestParamValidator(RequestParams requestParams) {
        this.requestParams = requestParams;
    }

    public static RequestParamValidator getInstance(RequestParams requestParams) {
        return new RequestParamValidator(requestParams);
    }


    /**
     * this method is used to validate the request Params (env) values...
     */
    public void validate() {
        isEnvValidated(requestParams);
        logger.info(String.format("%s:%s: env variables verified", this.getClass().getSimpleName(), "validate"));

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


        if (isInvalidEnv(requestParams.getCassandraHost())) {
            throw new IllegalArgumentException(String.format("Valid %s is required", EnvConstants.SUNBIRD_CASSANDRA_HOST));
        }
        if (isInvalidEnv(requestParams.getCassandraKeyspaceName())) {
            throw new IllegalArgumentException(String.format("Valid %s is required", EnvConstants.SUNBIRD_CASSANDRA_KEYSPACENAME));
        }
        if (isInvalidEnv(requestParams.getCassandraPort())) {
            throw new IllegalArgumentException(String.format("Valid %s is required", EnvConstants.SUNBIRD_CASSANDRA_PORT));
        }
        if (isInvalidEnv(requestParams.getSunbirdEncryptionKey())) {
            throw new IllegalArgumentException(String.format("Valid %s is required", EnvConstants.SUNBIRD_ENCRYPTION_KEY));
        }

        return true;

    }


}
