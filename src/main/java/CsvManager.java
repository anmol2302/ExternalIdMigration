import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

public class CsvManager {


    RequestParams params;
    FileWriter fw;
    HttpClient client;
    BufferedReader br;
    static Logger logger = LoggerFactory.getLoggerInstance(CsvManager.class.getName());


    public CsvManager(RequestParams params) throws IOException {
        this.params = params;
        fw = new FileWriter("failedRecords.txt");
        client = new HttpClient(params);
    }

    public void processFile() {
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = getReaderObject(null);
            if (br != null) {
                while ((line = br.readLine()) != null) {
                    if (line.length() != 0) {
                        String[] values = line.split(cvsSplitBy);
                        if (validateUserObject(null)) {
                              //  performUserUpdateRequest(values[0], values[1], values[2], values[3]);
                            System.out.println("validated");

                        } else {
                            logger.error("No valid record  found for this " + values[0] + " skipping record");
                            continue;
                        }

                    } else {
                        logger.error("No record Found  in row ");
                    }
                }
            }
        }catch (IllegalArgumentException ex){
            logger.error(String.format("%s:%s:skipping record for userId because %s",this.getClass().getSimpleName(),"processFile",ex.getMessage()));

        }
        catch (Exception e) {
            logger.error("CsvManager:processFile: error in processCsv: " + e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                    fw.close();
                    logger.info("Connection Closed");
                } catch (Exception e) {
                    logger.error("error in closing connection " + e.getMessage());
                }
            }
        }


    }

    public BufferedReader getReaderObject(String pathToInputFile) {
        try {
            br = new BufferedReader(new FileReader(pathToInputFile));
            br.readLine();        // escaping headers in file headers
            return br;

        } catch (FileNotFoundException e) {
            logger.error("file does not exists " + pathToInputFile);
            return null;

        } catch (IOException e) {
            logger.error("Exception occurred " + e.getMessage());
            return null;
        }
    }


    public void performUserUpdateRequest(String userName, String userId, String treasuryId, String channel) {
        try {
            String url = params.getBaseUrl().concat("/api/user/v1/update");
            String authToken = client.generateAuthToken(userName);
            logger.info("The auth token generated is " + authToken);
            Map<String, Object> reqMap = prepareUserUpdateRequest(userId, treasuryId, channel);
            if (StringUtils.isNotBlank(authToken)) {
                Map<String, Object> respMap = client.post(reqMap, url, authToken);
                int statusCode = (Integer) respMap.get("statusCode");
                if (statusCode != 200) {
                    writeFailedRecordToFile(userName, userId, (String) respMap.get("errMsg"));
                } else if (statusCode == 200) {
                    logger.info("User successfully updated with userName: " + userName);
                }
            } else {
                logger.error("CsvManager: performUserUpdateRequest: Record with userName " + userName + " Cant be processed");
            }

        } catch (Exception e) {
            logger.error("CsvManager: performUserUpdateRequest: Some Error Occurred in CsvManger:performUserUpdateRequest   " + e);
        }

    }


    public Map<String, Object> prepareUserUpdateRequest(String userId, String treasuryId, String channel) {

        Map<String, Object> reqMap = new HashMap<String, Object>();
        HashMap<String, Object> request = new HashMap<String, Object>();
        request.put("userId", userId);
        List<Map<String, Object>> externalIdList = new ArrayList<Map<String, Object>>();
        Map<String, Object> externaslIdMap = new HashMap<String, Object>();
        externaslIdMap.put("id", treasuryId);
        externaslIdMap.put("idType", channel);
        externaslIdMap.put("provider", channel);
        externalIdList.add(externaslIdMap);
        request.put("externalIds", externalIdList);
        reqMap.put("request", request);
        logger.info("CsvManager:prepareUserUpdateRequest: userRequest is " + Collections.singletonList(reqMap.toString()));
        return reqMap;
    }

    public void writeFailedRecordToFile(String userName, String userId, String errMsg) {
        try {
            String query = "Failed to Update Record with userName " + userName + " and userId" + userId + " and error Message is " + errMsg;
            logger.info("Writing failed record " + query);
            fw.write(query + "\n");
        } catch (Exception e) {
            logger.error("CsvManager: writeFailedRecordToFile: Something went wrong in writing file");
        }
    }

    public boolean validateUserObject(User user) {


        boolean isUserValid=true;
        if (user.getIdType() == null) {
            isUserValid=false;
        }
        if (user.getUserId() == null) {
            isUserValid=false;

        }
        if (user.getExternalId() == null) {
            isUserValid=false;

        }
        if (user.getOriginalexternalid() == null) {
            isUserValid=false;

        }
        if (user.getOriginalidtype() == null) {
            isUserValid=false;

        }
        if (user.getProvider() == null) {
            isUserValid=false;

        }
        if (user.getOriginalprovider() == null) {
            isUserValid=false;

        }
        return true;
    }


}
