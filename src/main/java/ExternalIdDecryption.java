import constants.EnvConstants;
import db.connection.factory.CassandraConnectionFactory;
import db.connection.factory.ConnectionFactory;
import org.apache.log4j.Logger;

public class ExternalIdDecryption {
  static Logger logger = LoggerFactory.getLoggerInstance(ExternalIdDecryption.class.getName());
  private static String className = ExternalIdDecryption.class.getSimpleName();

  /**
   * the Code begins here...
   *
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {

    try {
      RequestParams requestParams = prepareRequestParams();
      RequestParamValidator.getInstance(requestParams).validate();
      ConnectionFactory connectionFactory = new CassandraConnectionFactory();
      DataProcessor dataProcessor = DataProcessor.getInstance(connectionFactory, requestParams);
      dataProcessor.startProcessingExternalIds();
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /**
   * this method will prepare RequestParams object while reading constants from env
   *
   * @return RequestParams
   */
  private static RequestParams prepareRequestParams() {
    RequestParams requestParams = new RequestParams();
    requestParams.setCassandraHost(System.getenv(EnvConstants.SUNBIRD_CASSANDRA_HOST));
    requestParams.setCassandraKeyspaceName(
        System.getenv(EnvConstants.SUNBIRD_CASSANDRA_KEYSPACENAME));
    requestParams.setCassandraPort(System.getenv(EnvConstants.SUNBIRD_CASSANDRA_PORT));
    requestParams.setSunbirdEncryptionKey(System.getenv(EnvConstants.SUNBIRD_ENCRYPTION_KEY));
    logger.info(
        String.format(
            "%s:%s:env variable got %s",
            className, "prepareRequestParams", requestParams.toString()));
    return requestParams;
  }
}
