package primeratools;

import PrimeraWrapper.wrapperobjects.AccountDetailsRequest;
import PrimeraWrapper.wrapperobjects.AccountDetailsResponse;
import com.google.gson.Gson;
import java.util.Random;
import java.util.UUID;



public class NIPInterface
{
    
    public String APIKey = "4466FA2C-1886-4366-B014-AD140712BE38";
    
  public String AccountDetailsRequest(String input)
  {
    try
    {
      Gson gson = new Gson();
      
      AccountDetailsRequest request = (AccountDetailsRequest)gson.fromJson(input, AccountDetailsRequest.class);
      
      request.setRequestID(UUID.randomUUID().toString());
      
      RestClient client = new RestClient("http://127.0.0.1:8080/NIPClient/webresources/PrimeraInterface");  //172.16.10.5:
      
      String stringtohash = request.getRequestID() + request.getInstitutionCode(); //+ request.getAmount();
      
      String hash = client.get_SHA_512_Hash(stringtohash, APIKey);
      
      request.setHash(hash);
      
      String payload = gson.toJson(request);
      
      String responsebody = client.ProcessPrimeraRequest(payload, "GetAccountByAccountNo");
      
      AccountDetailsResponse response = (AccountDetailsResponse)gson.fromJson(responsebody, AccountDetailsResponse.class);
      
      return response.getHash()+ '#' + response.getInstitutionCode();
    }
    catch (Exception s)
    {
    
        return s.getMessage();
    }
  }
  
 
  
  
  
  
  public static void main(String[] args)
  {
    Gson gson = new Gson();
//    FundsTransferDCRequest request = new FundsTransferDCRequest();
//    
//    getFIListRequest firequest = new getFIListRequest();
//    
//    String nepayload = gson.toJson(firequest);
    
 //   String firesponse = new NIPInterface().dogetFIList("");
    
//    Random rand = new Random();
//    //NameEnquiryRequest nerequest = new NameEnquiryRequest();
//    nerequest.setAccountNumber("0019291825");
//    nerequest.setChannelCode("1");
//    nerequest.setDestinationInstitutionCode("000013");
//    nerequest.setChannelCode("1");
//    nerequest.setInstitutionCode("070001");
//    String nepayload = gson.toJson(nerequest);
    
    String neresponse = new NIPInterface().AccountDetailsRequest("{\"InstitutionCode\": \"1111\",\"AccountNumber\": \"10995\"}");
    
    //NameEnquiryResponse neresponseobj = (NameEnquiryResponse)gson.fromJson(neresponse, NameEnquiryResponse.class);
    
    String sd ="";
//    request.setNameEnquiryRef(neresponseobj.getNameEnquiryRef());
//    request.setNarration("Inlaks FT Single DC Test ");
//    request.setBeneficiaryAccountNumber(neresponseobj.getAccountNumber());
//    request.setBeneficiaryAccountName(neresponseobj.getAccountName());
//    request.setBeneficiaryBankVerificationNumber(neresponseobj.getBankVerificationNo());
//    request.setBeneficiaryKYCLevel(neresponseobj.getKycLevel());
//    request.setDestinationInstitutionCode(neresponseobj.getDestinationInstitutionCode());
//    request.setOriginatorAccountName("DENNIS MADU");
//    request.setOriginatorAccountNumber("0010011709");
//    request.setOriginatorBankVerificationNumber("08069846565");
//    request.setOriginatorKYCLevel("1");
//    request.setChannelCode("1");
//    request.setInstitutionCode("000013");
//    request.setTransactionLocation("");
//    request.setPaymentReference("FT3533" + rand.nextInt(458588));
//    
//    String payload = gson.toJson(request);
//    String ftresponse = new NIPInterface().doFundsTransferDC(payload);

//    request.setAmount("150.00");
//    request.setNameEnquiryRef("070001180613105630087666370806");
//    request.setNarration("Iledk");
//    request.setBeneficiaryAccountNumber("0019291825");
//    request.setBeneficiaryAccountName("OKUSADA, OLUFEMI OLUMIDE");
//    request.setBeneficiaryBankVerificationNumber("22179818329");
//    request.setBeneficiaryKYCLevel("2");
//    request.setDestinationInstitutionCode("000013");
//    request.setOriginatorAccountName("ISIMHANZE KESTER FRANCIS");
//    request.setOriginatorAccountNumber("0020246245");
//    request.setOriginatorBankVerificationNumber("22282110079");
//    request.setOriginatorKYCLevel("2");
//    request.setChannelCode("1");
//    request.setInstitutionCode("070001");
//    request.setTransactionLocation("");
//    request.setPaymentReference("FT18159600441691");
//    
//    String payload = gson.toJson(request);
//    String ftresponse = new NIPInterface().doFundsTransferDC(payload);
    
//    String payload = gson.toJson("{\"nameEnquiryRef\":\"070001180613105630087666370806\",\"destinationInstitutionCode\":\"000013\",\"beneficiaryAccountName\":\"OKUSADA, OLUFEMI OLUMIDE\",\"beneficiaryAccountNumber\":\"0019291825\",\"beneficiaryBankVerificationNumber\":\"22179818329\",\"beneficiaryKYCLevel\":\"2\",\"originatorAccountName\":\"ISIMHANZE KESTER FRANCIS\",\"originatorAccountNumber\":\"0020246245\",\"originatorBankVerificationNumber\":\"22282110079\",\"originatorKYCLevel\":\"2\",\"transactionLocation\":\"\",\"narration\":\"ledk\",\"InstitutionCode\":\"070001\",\"amount\":\"150.00\"}");
//    String ftresponse = new NIPInterface().doFundsTransferDC("{\"nameEnquiryRef\":\"070001180613105630087666370806\",\"destinationInstitutionCode\":\"000013\",\"beneficiaryAccountName\":\"OKUSADA, OLUFEMI OLUMIDE\",\"beneficiaryAccountNumber\":\"0019291825\",\"beneficiaryBankVerificationNumber\":\"22179818329\",\"beneficiaryKYCLevel\":\"2\",\"originatorAccountName\":\"ISIMHANZE KESTER FRANCIS\",\"originatorAccountNumber\":\"0020246245\",\"originatorBankVerificationNumber\":\"22282110079\",\"originatorKYCLevel\":\"2\",\"transactionLocation\":\"\",\"narration\":\"ledk\",\"InstitutionCode\":\"070001\",\"ChannelCode\":\"1\",\"PaymentReference\":\"FT18159600441691\",\"amount\":\"150.00\"}");
//    
//    FundsTransferDCResponse ftresponseobj = (FundsTransferDCResponse)gson.fromJson(ftresponse, FundsTransferDCResponse.class);
    
//    TransactionStatusQueryRequest tsq = new TransactionStatusQueryRequest();
//    tsq.setChannelCode("1");
//    tsq.setInstitutionCode("070001");
//    //tsq.setNibssSessionID(ftresponseobj.getNibssSessionID());
//    tsq.setNibssSessionID("000013180609072010000124705923");
//    
//    String tspayload = gson.toJson(tsq);
//    
//    String response = new NIPInterface().doTransactionStatusQuery(tspayload);
  }
}
