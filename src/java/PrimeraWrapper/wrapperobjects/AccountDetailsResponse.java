/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrimeraWrapper.wrapperobjects;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author dogor-Igbosuah
 */

@Getter @Setter
public class AccountDetailsResponse {
 private String AccountName;
 private String AccountNumber;
 private String CustomerName;
 private String Currency;
 private String OnlineActualBalance;
 private String AccountType;
 private String AccountOfficer;
 private String PhoneNo;
 private String InstitutionCode;
 private Boolean IsSuccessful;
 private String Message;
 private String requestID;
 private String hash;
 private String Responsecode;
 private String Responsedescription;
}
