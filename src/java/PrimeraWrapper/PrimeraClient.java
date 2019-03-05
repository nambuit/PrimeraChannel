/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrimeraWrapper;

import PrimeraWrapper.wrapperobjects.AccountDetailsRequest;
import PrimeraWrapper.wrapperobjects.AccountDetailsResponse;
import com.google.gson.Gson;
import java.util.ArrayList;
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
import org.jboss.logging.Param;


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
    
    
private String Ofsuser;
private String Ofspass;
private String ImageBase;
private String output;

    /**
     * Creates a new instance of PrimeraClient
     */
    public PrimeraClient(){
    try
        {
            t24 =   new T24Interface_Service().getT24InterfacePort();
            
           
            
          
            
        } catch (Exception e)
        {
           System.out.println(e.getMessage());
        } 
    }
   
    /**
     * Retrieves representation of an instance of primera.service.GenericResource
     * @param payload
     * @return an instance of java.lang.String
     */
    @POST
    @Path("GetAccountByAccountNo")
    @Produces(MediaType.TEXT_PLAIN)
    public String GetAccountByAccountNo (String payload){
      
        Gson gson = new Gson();
        //List<AccountDetails> alldetails = new ArrayList<>();
        AccountDetailsResponse response = new AccountDetailsResponse();
        //RestClient client = new RestClient ();
        
        
    try{
        

    AccountDetailsRequest request = (AccountDetailsRequest) gson.fromJson(payload, AccountDetailsRequest.class);
    
    AccountDetails t24response = t24.getAccountByAccountNo(request.getInstitutionCode(),request.getAccountNumber()); //wrapper to get from
  
    response.setAccountName(t24response.getAccountName());
    response.setAccountNumber(t24response.getAccountNumber());
    response.setAccountOfficer(t24response.getAccountOfficer());
    response.setCurrency(t24response.getCurrency());
    response.setCustomerName(t24response.getCurrency());
    response.setInstitutionCode(t24response.getInstitutionCode());
    response.setIsSuccessful(t24response.isIsSuccessful());
    response.setOnlineActualBalance(t24response.getOnlineActualBalance());
    
    String output = gson.toJson(t24response);
    System.out.println(output);  
    } 
    catch (Exception e){
        System.out.println(e.getMessage());
        String sd ="";
    }
    
    return output;
   }
}
   
   
   
    
