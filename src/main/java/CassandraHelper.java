import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import constants.DbColumnConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * this is a cassandra helper class used for various utility methods.
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
            User user = new User(row.getString(DbColumnConstants.provider), row.getString(DbColumnConstants.idType), row.getString(DbColumnConstants.externalId), row.getString(DbColumnConstants.originalExternalId), row.getString(DbColumnConstants.originalIdType), row.getString(DbColumnConstants.originalProvider), row.getString(DbColumnConstants.userId));
            userList.add(user);
        }
        return userList;
    }
}
