/**
 * this class is used to map the csv value into User class object
 * below are the fields used in the table (usr_external_Identity)
 * provider, idtype, externalid, createdby, createdon, lastupdatedby, lastupdatedon, originalexternalid, originalidtype, originalprovider, userid
 * @count 11
 *
 * @author anmolgupta
 */
public class User {

    private String provider;
    private String idType;
    private String externalId;
    private String originalexternalid;
    private String originalidtype;
    private String originalprovider;
    private String userId;

    public User(String provider, String idType, String externalId, String originalexternalid, String originalidtype, String originalprovider, String userId) {
        this.provider = provider;
        this.idType = idType;
        this.externalId = externalId;
        this.originalexternalid = originalexternalid;
        this.originalidtype = originalidtype;
        this.originalprovider = originalprovider;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getOriginalexternalid() {
        return originalexternalid;
    }

    public void setOriginalexternalid(String originalexternalid) {
        this.originalexternalid = originalexternalid;
    }

    public String getOriginalidtype() {
        return originalidtype;
    }

    public void setOriginalidtype(String originalidtype) {
        this.originalidtype = originalidtype;
    }

    public String getOriginalprovider() {
        return originalprovider;
    }

    public void setOriginalprovider(String originalprovider) {
        this.originalprovider = originalprovider;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    @Override
    public String toString() {
        return "User{" +
                "provider='" + provider + '\'' +
                ", idType='" + idType + '\'' +
                ", externalId='" + externalId + '\'' +
                ", originalexternalid='" + originalexternalid + '\'' +
                ", originalidtype='" + originalidtype + '\'' +
                ", originalprovider='" + originalprovider + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
