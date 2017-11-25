package iFobs;

import com.sun.jna.Structure;
import org.openqa.selenium.By;
import static iFobs.TestScenario.driver;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by truni on 11.10.2017.
 */

public class Validation {

    //*******************************************//Маленькие методы//********************************************//
    //Валидация(поиск) создания документа
    public static void documentVal(){
        driver.findElement(By.xpath("//*[contains(text(),'Операция ввода документа выполнена успешно')]"));
    }

    //Валидация(поиск) создания сообщения
    public static void messageVal(){
        driver.findElement(By.xpath("//*[contains(text(),'Операция создания сообщения выполнена успешно')]"));
    }

    //Валидация(поиск) изменения документа
    public static void documentChangeVal(){
        driver.findElement(By.xpath("//*[contains(text(),'Операция изменения документа выполнена успешно')]"));
    }

    //Валидация, поиск через ID
    public void validationByID(String id, String expectedValue){
        String assertText = driver.findElement(By.id(id)).getAttribute("value");
        assertThat(assertText, is(expectedValue));
    }

    //Валидация, поиск через ID (выпадающее меню)
    public void validationByIDdropDown(String id, String expectedValue){
        String testValue = driver.findElement(By.id(id)).getAttribute("value");
        String assertText = driver.findElement(By.xpath("//*[@value='"+testValue+"']")).getText();
        assertThat(assertText, containsString(expectedValue));
    }

    //Валидация, поиск через Class
    public void validationByClass(String className, String expectedValue) {
        String assertText = driver.findElement(By.className(className)).getText();
        assertThat(assertText, is(expectedValue));
    }

    //Валидация, поиск через Name
    public void validationByName(String name, String expectedValue) {
        String assertText = driver.findElement(By.name(name)).getAttribute("value");
        assertThat(assertText, is(expectedValue));
    }

    //Валидация, поиск через XPath
    public void validationByXPath(String xpath, String expectedValue) {
        String assertText = driver.findElement(By.xpath(xpath)).getAttribute("value");
        assertThat(assertText, is(expectedValue));
    }

    //Валидация, поиск через XPath (часть текста)
    public void validationPartbyXPath(String xpath, String expectedValue) {
        String assertText = driver.findElement(By.xpath(".//*[contains(text(),'"+xpath+"')]")).getAttribute("selected value");
        assertThat(assertText, containsString (expectedValue));
    }

    //Валидация подписей
    public void validationSigns(String UAHsign1, String UAHsign2){
        validationByID("sign_user1", UAHsign1);
        validationByID("sign_user2", UAHsign2);
    }


    //*******************************************//Большие методы//********************************************//

    //Валидация гривневого документа
    public void validationDocUAH (String UAHsumma, String UAHAccA, String mfoB, String UAHAccB,String UAHdescr,
                                  String UAHid, String UAHpurpose, String UAHsign1, String UAHsign2){
        validationByID("summaVISUALi",UAHsumma);
        validationByIDdropDown("p_account_no", UAHAccA);
        validationByID("r_bank_id_control", mfoB);
        validationByID("r_account_no", UAHAccB);
        validationByID("r_descr", UAHdescr);
        validationByID("r_idcode", UAHid);
        validationByID("paymentpurpose", UAHpurpose);
        validationSigns(UAHsign1, UAHsign2);
    }

    //Валидация сообщения
    public void validationMassage(String branchid, String topicMessage,String textMessage, String UAHsign1, String UAHsign2){
        validationByID("BRANCHID", branchid);
        validationByName("SUBJECT", topicMessage);
        validationByName("MSGTEXT", textMessage);
        validationSigns(UAHsign1, UAHsign2);
    }

    //Валидация документа в валюте
    public void validationDocCUR(String CURcurrency, String CURsumma, String CURaccA,String CURaccB, String CURdescr, String CURid, String CURpurpose, String CURsign1, String CURsign2){
        validationByIDdropDown("currency_select", CURcurrency);
        validationByID("summaVISUALi", CURsumma);
        validationByIDdropDown("p_account_no", CURaccA);
        validationByID("r_account_no", CURaccB);
        validationByID("r_descr", CURdescr);
        validationByID("r_idcode", CURid);
        validationByID("paymentpurpose", CURpurpose);
        validationSigns(CURsign1 , CURsign2);
    }

    //Валидация SWIFT
    public void validationSWIFT(String Swiftcurrency, String SWIFTsumma, String SWIFTreason, String SWIFTdeatail,String SWIFTaccWinSTFMT,
                                String SWIFTaccWinSTBic, String SWIFTaccountNo, String SWIFTnameAdr,String SWIFT50currency,
                                String SWIFT50account, String SWIFTpayerNameAdr,String SWIFT71currency, String SWIFT71account,
                                String SWIFTsign1, String SWIFTsign2){
        validationByIDdropDown("CURRENCYID", Swiftcurrency);
        validationByID("AMOUNTVISUALi", SWIFTsumma);
        validationByID("PAYREASON", SWIFTreason);
        validationByID("DETAILSOFPAYMENT", SWIFTdeatail);
        validationByIDdropDown("ACCWINSTFMT", SWIFTaccWinSTFMT);
        validationByID("ACCWINSTBIC", SWIFTaccWinSTBic);
        validationByID("ACCOUNTNO", SWIFTaccountNo);
        validationByID("NAMEADDRESS", SWIFTnameAdr);
        validationByIDdropDown("field50curr", SWIFT50currency);
        validationByIDdropDown("FXACCOUNTID", SWIFT50account);
        validationByID("PAYERNAMEADDR", SWIFTpayerNameAdr);
        validationByIDdropDown("comcurr", SWIFT71currency);
        validationByIDdropDown("FEEACCOUNTID", SWIFT71account);
        validationSigns(SWIFTsign1 , SWIFTsign2);

    }

    //Валидация документа на покупку валюты
    public void validationDocBUY(String BUYsumma,String BUYcurrency,String BUYaccount, String BUYfeeRate, String BUYaccountForEnlistment, String BUYsign1, String BUYsign2){
        validationByID("AMOUNTVISUALi", BUYsumma);
        validationByIDdropDown("CURRENCYID", BUYcurrency);
        validationByIDdropDown("FXACCOUNTID", BUYaccount);
        validationByID("FEERATE", BUYfeeRate);
        validationByIDdropDown("NCACCOUNTID", BUYaccountForEnlistment);
        validationSigns(BUYsign1, BUYsign2);
    }

    //Валидация документа на продажу валюты
    public void validationDocSELL(String SELLsumma, String SELLcurrency, String SELLaccount, String SELLfeeRate, String SELLaccountForEnlistment, String SELLsign1, String SELLsign2){
        validationByID("AMOUNTVISUALi", SELLsumma);
        validationByIDdropDown("CURRENCYID", SELLcurrency);
        validationByIDdropDown("FXACCOUNTID", SELLaccount);
        validationByID("FEERATE", SELLfeeRate);
        validationByID("ACCOUNTNO", SELLaccountForEnlistment);
        validationSigns(SELLsign1, SELLsign2);
    }

    //Валидация документа на конверсию валюты
    public void validationDocCONV(String CONVpurpose, String CONVminMaxRate, String CONVsellAmt, String CONVcurrencySell, String CONVaccountSell,
                                  String CONVcurrencyBuy, String CONVaccountBuy, String CONVaccountFee, String CONVfeePercent, String CONVsign1, String CONVsign2){
        validationByID("PURPOSE", CONVpurpose);
        validationByID("MAXMINRATEVISUALi", CONVminMaxRate);
        validationByID("SELLAMOUNTVISUALi", CONVsellAmt);
        validationByIDdropDown("SELLCURRENCYID", CONVcurrencySell);
        validationByIDdropDown("SELLACCOUNTID", CONVaccountSell);
        validationByIDdropDown("BUYCURRENCYID", CONVcurrencyBuy);
        validationByIDdropDown("BUYACCOUNTID", CONVaccountBuy);
        validationByIDdropDown("FEEACCOUNTID", CONVaccountFee);
        validationByID("FEEPERCENT", CONVfeePercent);
        validationSigns(CONVsign1, CONVsign2);
    }


}
