import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import constants.DbColumnConstants;

import java.sql.Timestamp;
import java.util.*;


/**
 * this is a cassandra helper class used for various utility methods.
 * * provider, idtype, externalid, createdby, createdon, lastupdatedby, lastupdatedon, originalexternalid, originalidtype, originalprovider, userid
 *
 * @author anmolgupta
 */
public class CassandraHelper {


    /**
     * these methods will be used to convert resultSet into List of User entity Object.
     *
     * @param resultSet
     * @return
     */
    public static List<User> getUserListFromResultSet(ResultSet resultSet) {

        List<User> userList = new ArrayList<>();
        Iterator<Row> iterator = resultSet.iterator();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            User user = new User(row.getString(DbColumnConstants.provider), row.getString(DbColumnConstants.idType), row.getString(DbColumnConstants.externalId), row.getString(DbColumnConstants.createdBy),
                    row.getTimestamp(DbColumnConstants.createdOn), row.getString(DbColumnConstants.lastUpdatedBy), row.getTimestamp(DbColumnConstants.lastUpdatedOn), row.getString(DbColumnConstants.originalExternalId),
                    row.getString(DbColumnConstants.originalIdType), row.getString(DbColumnConstants.originalProvider), row.getString(DbColumnConstants.userId));
            userList.add(user);
        }
        return userList;
    }

    /**
     * this method is used to prepare insertQuery for cassandra db..
     *
     * @param user
     * @return query(String)
     */
    public static String getInsertRecordQuery(User user) {
        return String.format("INSERT INTO usr_external_identity (provider, idtype, externalid, createdby, createdon, lastupdatedby, lastupdatedon, originalexternalid, originalidtype, originalprovider, userid) VALUES('%s','%s', '%s', '%s', %s,'%s',%s,'%s','%s','%s','%s');", user.getProvider(), user.getIdType(), user.getExternalId(), user.getCreatedBy(), getTimeStampFromDate(user.getCreatedOn()), user.getLastUpdatedBy(), getTimeStampFromDate(user.getLastUpdatedOn()), user.getOriginalExternalId(), user.getOriginalIdType(), user.getOriginalProvider(), user.getUserId());
    }


    /**
     * this method is used to get the cql timestamp from date
     * @param date
     * @return
     */
    public static long getTimeStampFromDate(Date date) {
        return date != null ? new Timestamp(date.getTime()).getTime() : new Timestamp(Calendar.getInstance().getTime().getTime()).getTime();
    }


}
