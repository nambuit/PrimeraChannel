/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrimeraWrapper;

import PrimeraWrapper.wrapperobjects.AccountDetailsRequest;
import PrimeraWrapper.wrapperobjects.AccountDetailsResponse;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import main.service.AccountDetails;
import main.service.T24Interface;
import main.service.T24Interface_Service;
import primeratools.AppParams;
import primeratools.DBConnector;
import primeratools.NIBBsResponseCodes;


/**
 * REST Web Service
 *
 * @author dogor-Igbosuah
 */

@Path("PrimeraInterface")
public class PrimeraClient {

    @Context
    private UriInfo context;
    T24Interface t24;
    AppParams options;
    NIBBsResponseCodes respcodes;
    private DBConnector db; //why do we use private here.
    String logTable = "InlaksBVNWrapperLog";
    String apikey = "";
    
    
    



    /**
     * Creates a new instance of PrimeraClient
     */
    public PrimeraClient(){
    try
        {
            t24 =   new T24Interface_Service().getT24InterfacePort();
            options = new AppParams();
            db = new DBConnector(options.getDBserver(), options.getDBuser(), options.getDBpass(), "BVNLogs");
            
        } catch (Exception e)
        {
           System.out.println(e.getMessage());
        } 
    }
   
    /**
     * Retrieves representation of an instance of primera.service.GenericResource
     * @param authenticationID
     * @param timeStamp
     * @param payload
     * @param applicationID
     * @return an instance of java.lang.String
     */
    @POST
    @Path("GetAccountByAccountNo")
    @Produces(MediaType.APPLICATION_JSON)
    public String GetAccountByAccountNo (@HeaderParam("authenticationID") String authenticationID, @HeaderParam("timeStamp") String timeStamp,@HeaderParam("applicationID") String applicationID, String payload){
      
        Gson gson = new Gson();
        Connection conn = null;
        AccountDetailsResponse detailresponse = new AccountDetailsResponse();
        
        List<Object> values = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        
        headers.add("requestPayload");
        values.add(payload);

        Date reqdate = new Date();

        headers.add("requestDate");
        values.add(reqdate);
        
        try {
            if (authenticationID == null || timeStamp == null || applicationID == null) {

                respcodes = NIBBsResponseCodes.Invalid_Sender;
                detailresponse.setResponsecode(respcodes.getInlaksCode());
                detailresponse.setResponsedescription(respcodes.getMessage());
                return gson.toJson(detailresponse);
        }
            db = new DBConnector(options.getDBserver(), options.getDBuser(), options.getDBpass(), "BVNLogs");  
            ResultSet rs = db.getData("select * from NIPClients where ApplicationID = '"+applicationID.trim()+"';", conn);
              
              if(rs.next()){
                
              apikey  = rs.getString("APIKey");
              
              String authid = rs.getString("AuthenticationID");
              
             if(!authid.trim().equals(authenticationID.trim())){
                respcodes = NIBBsResponseCodes.Security_violation;
                detailresponse.setResponsecode(respcodes.getInlaksCode());
                detailresponse.setResponsedescription(respcodes.getMessage());
                return gson.toJson(detailresponse);
              }
              
             }
            else{
                
                respcodes = NIBBsResponseCodes.Invalid_Sender;
                detailresponse.setResponsecode(respcodes.getInlaksCode());
                detailresponse.setResponsedescription(respcodes.getMessage());
                return gson.toJson(detailresponse);  // why are we setting detailresponse in to json and returning that.
                
            }
             
   
                 AccountDetailsRequest request = (AccountDetailsRequest) gson.fromJson(payload, AccountDetailsRequest.class);
    
                headers.add("applicationID");
                values.add(applicationID);
                
                 String stringtohash = request.getRequestID()+request.getInstitutionCode();

                 String requesthash = request.getHash();

                String hash = options.get_SHA_512_Hash(stringtohash, apikey);

                headers.add("hash");
                values.add(hash);
                
                
                if (hash.equals(requesthash)) {
                 
                AccountDetails t24response = t24.getAccountByAccountNo(request.getInstitutionCode(),request.getAccountNumber());
                detailresponse.setAccountName(t24response.getAccountName().trim());
                detailresponse.setAccountNumber(t24response.getAccountNumber().trim());
                detailresponse.setAccountOfficer(t24response.getAccountOfficer());
                detailresponse.setCurrency(t24response.getCurrency());
                detailresponse.setCustomerName(t24response.getCustomerName());
                detailresponse.setInstitutionCode(t24response.getInstitutionCode());
                detailresponse.setIsSuccessful(t24response.isIsSuccessful());
                detailresponse.setOnlineActualBalance(t24response.getOnlineActualBalance().trim());
            } else {
                respcodes = NIBBsResponseCodes.Security_violation;
                detailresponse.setResponsecode(respcodes.getInlaksCode());
                detailresponse.setResponsedescription(respcodes.getMessage());

            }

            headers.add("responseCode");
            values.add(detailresponse.getResponsecode());
            headers.add("responseDescription");
            values.add(detailresponse.getResponsedescription());
        } catch (Exception e) {
            respcodes = NIBBsResponseCodes.System_malfunction;
            detailresponse.setResponsecode(respcodes.getInlaksCode());
            detailresponse.setResponsedescription(respcodes.getMessage());
        } finally {
            try {

                headers.add("response");
                values.add(gson.toJson(detailresponse));

                db.insertData(headers, values.toArray(), logTable);
            } catch (Exception s) {

            }
        }

        return gson.toJson(detailresponse);
    
    }
}

    
     //wrapper to get from
  
    
    
   
   
   
    
