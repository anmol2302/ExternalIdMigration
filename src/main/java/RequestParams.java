public class RequestParams {

    private String cassandraHost;
    private String cassandraKeyspaceName;
    private String baseUrl;
    private String realm;
    private String clientId;
    private String apiKey;
    private String authToken;

    public RequestParams(String cassandraHost, String cassandraKeyspaceName, String baseUrl, String realm, String clientId, String apiKey, String authToken) {
        this.cassandraHost = cassandraHost;
        this.cassandraKeyspaceName = cassandraKeyspaceName;
        this.baseUrl = baseUrl;
        this.realm = realm;
        this.clientId = clientId;
        this.apiKey = apiKey;
        this.authToken = authToken;
    }

    public RequestParams() {
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getCassandraHost() {
        return cassandraHost;
    }

    public void setCassandraHost(String cassandraHost) {
        this.cassandraHost = cassandraHost;
    }

    public String getCassandraKeyspaceName() {
        return cassandraKeyspaceName;
    }

    public void setCassandraKeyspaceName(String cassandraKeyspaceName) {
        this.cassandraKeyspaceName = cassandraKeyspaceName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "RequestParams{" +
                "cassandraHost='" + cassandraHost + '\'' +
                ", cassandraKeyspaceName='" + cassandraKeyspaceName + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", realm='" + realm + '\'' +
                ", clientId='" + clientId + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}
