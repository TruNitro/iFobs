package iFobs;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import static iFobs.TestScenario.driver;
import static iFobs.TestScenario.robot;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by truni on 11.10.2017.
 */

public class Methods {
    static Methods m = new Methods();
    static Data d = new Data();
    static Validation v = new Validation();


    //*******************************************//Вход и подписание//********************************************//

    //Открытие браузера, запуск iFobs и вход
    public void openAndEnterIFobs(String login, String userPassword, String keyPassword, String folder){
        driver.manage().window().maximize();
        driver.get("https://ifobs-testc.alfabank.kiev.ua/ifobsClient");
        driver.findElement(By.id("secure_login")).click();
        driver.findElement(By.id("userLogin")).sendKeys(login);       // Ввод Логина
        driver.findElement(By.id("password")).sendKeys(userPassword);        // Ввод Пароля
        driver.findElement(By.id("enter_btn")).click();
        driver.findElement(By.id("key_password")).sendKeys(keyPassword);    // Ввод Пароля к секретному ключу
        driver.findElement(By.id("privateKeyFileInput")).sendKeys(folder); //Ввод роботом расположения ключа
        driver.findElement(By.id("enter_btn_iit")).click();
        m.frameTops();
        v.validationByClass("top_login", login);
    }
    //Подпись документа/Отправка без подписи
    public void iitSign(String folder, String keyPassword) {
        if (d.needSign == 1) {
            driver.findElement(By.id("divAppletBtn")).click();
            driver.findElement(By.id("privateKeyFileInput")).sendKeys(folder);
            driver.findElement(By.id("key_password")).sendKeys(keyPassword);//Пароль к ключу
            driver.findElement(By.id("sign_btn")).click();
        }
        if (d.needSign == 0) {
            driver.findElement(By.name("btnSend")).click();
        }

    }
    //Подпись документа/Отправка без подписи (SWIFT)
    public void iitSign2(String folder, String keyPassword) {
        if (d.needSign == 1) {
            driver.findElement(By.id("divAppletBtnSign")).click();
            driver.findElement(By.id("privateKeyFileInput")).sendKeys(folder);
            driver.findElement(By.id("key_password")).sendKeys(keyPassword);//Пароль к ключу
            driver.findElement(By.id("sign_btn")).click();
        }
        if (d.needSign == 0) {
            driver.findElement(By.name("btnSend")).click();
        }

    }



    //*******************************************//Создание документов//********************************************//
    //Создание документа в нац валюте
    public void createDocUAH(String UAHsumma, String UAHaccountA, String UAHmfoB, String UAHaccountB,
                                    String UAHdescrB, String UAHidB, String UAHpurpose, String folder, String passwordKey,
                                    String Sign1, String Sign2){
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-1']/a")).click();
        m.frameTopic();
        driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[1]/div[2]/div[2]/a")).click();
        driver.findElement(By.id("summaVISUALi")).sendKeys(UAHsumma);       //Сумма документа
        driver.findElement(By.id("p_account_no")).click();
        driver.findElement(By.xpath(".//*[contains(text(),'" + UAHaccountA +"')]")).click(); //Номер счета А
        driver.findElement(By.id("r_bank_id_control")).sendKeys(UAHmfoB);  //МФО банка Б
        driver.findElement(By.id("r_account_no")).sendKeys(UAHaccountB);//Счет Б
        driver.findElement(By.id("r_descr")).sendKeys(UAHdescrB);   //Описание получателя
        driver.findElement(By.id("r_idcode")).sendKeys(UAHidB); //ИНН получателя
        driver.findElement(By.id("paymentpurpose")).sendKeys(UAHpurpose);//Назначение платежа
        robot.delay(500);
        m.iitSign(folder, passwordKey);
        m.getDocNoUAH();
        Validation.documentVal();
        v.validationDocUAH(UAHsumma, UAHaccountA, UAHmfoB, UAHaccountB, UAHdescrB, UAHidB, UAHpurpose, Sign1, Sign2);
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-0']/a")).click();
    }

    //Создание сообщения
    public void createMessage(String MessageMfo, String MessageTopic, String MessageText, String folder, String keyPassword, String Sign1, String Sign2){
        m.frameMenu();
        driver.findElement(By.id("link-3")).click();
        m.frameTopic();
        driver.findElement(By.xpath("html/body/table[1]/tbody/tr[2]/td[1]/form/table/tbody/tr/td[1]/input")).click();
        //Работа с выпадающим списком
        Select dropdown = new Select(driver.findElement(By.id("BRANCHID")));
        dropdown.selectByValue(MessageMfo);
        //
        driver.findElement(By.name("SUBJECT")).sendKeys(MessageTopic);//Тема сообщения
        driver.findElement(By.name("MSGTEXT")).sendKeys(MessageText);//Текст сообщения
        m.iitSign(folder, keyPassword);
        Validation.messageVal();
        v.validationMassage(MessageMfo, MessageTopic, MessageText, Sign1, Sign2);
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-0']/a")).click();
    }

    //Создание документа в валюте
    public void createDocCur(String CURcurrency, String CURsumma, String CURaccountA, String CURaccountB,
                                    String CURdescr, String CURidB, String CURpurpose, String folder, String passwordKey , String Sign1, String Sign2){
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-2']/a")).click();
        m.frameTopic();
        driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[1]/div[2]/div[2]/a")).click();
        //Работа с выпадающим списком - выбор валюты
        Select dropdown1 = new Select(driver.findElement(By.id("currency_select")));
        dropdown1.selectByVisibleText(CURcurrency); //Выбор валюты
        //
        driver.findElement(By.id("summaVISUALi")).sendKeys(CURsumma);
        driver.findElement(By.id("p_account_no")).click();
        driver.findElement(By.xpath(".//*[contains(text(),'" + CURaccountA + "')]")).click();
        driver.findElement(By.id("r_account_no")).sendKeys(CURaccountB);
        driver.findElement(By.id("r_descr")).sendKeys(CURdescr);
        driver.findElement(By.id("r_idcode")).sendKeys(CURidB);
        driver.findElement(By.id("paymentpurpose")).sendKeys(CURpurpose);
        m.iitSign2(folder, passwordKey);
        m.getDocNoCUR();
        Validation.documentVal();
        v.validationDocCUR(CURcurrency, CURsumma, CURaccountA, CURaccountB, CURdescr, CURidB, CURpurpose, Sign1 , Sign2);
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-0']/a")).click();
    }

    //Создание SWIFT
    public void createSWIFT(String SWIFTcurrency, String SWIFTsumma, String SWIFTreason,
                                   String SWIFTdeatail, String SWIFTaccWinSTFMT, String SWIFTaccWinSTBic,
                                   String SWIFTaccountNo, String SWIFTnameAdr, String SWIFT50currency,
                                   String SWIFT50account, String SWIFTpayerNameAdr, String SWIFT71currency,
                                   String SWIFT71account, String folder, String keyPassword, String Sign1, String Sign2){
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-2']/a")).click();
        m.frameTopic();
        driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[1]/div[4]/div[2]/a")).click();
        //Работа с выпадающим списком - выбор валюты
        Select dropdown1 = new Select(driver.findElement(By.id("CURRENCYID")));
        dropdown1.selectByVisibleText(SWIFTcurrency); //Выбор валюты
        //
        driver.findElement(By.id("AMOUNTVISUALi")).sendKeys(SWIFTsumma);//Сумма
        driver.findElement(By.name("PAYREASON")).sendKeys(SWIFTreason);//Основание
        driver.findElement(By.name("DETAILSOFPAYMENT")).sendKeys(SWIFTdeatail);
        //Работа с выпадающим списком блока 57
        Select dropdown2 = new Select(driver.findElement(By.id("ACCWINSTFMT")));
        dropdown2.selectByVisibleText(SWIFTaccWinSTFMT); //Выбор типа
        //
        driver.findElement(By.id("ACCWINSTBIC")).sendKeys(SWIFTaccWinSTBic);
        driver.findElement(By.id("ACCOUNTNO")).sendKeys(SWIFTaccountNo);
        driver.findElement(By.id("NAMEADDRESS")).sendKeys(SWIFTnameAdr);
        //Работа с выпадающим списком блока 50
        Select dropdown3 = new Select(driver.findElement(By.id("field50curr")));
        dropdown3.selectByVisibleText(SWIFT50currency); //Выбор типа валюты
        //
        driver.findElement(By.id("FXACCOUNTID")).click();
        driver.findElement(By.xpath(".//*[@id='FXACCOUNTID']//*[contains(text(),'" + SWIFT50account + "')]")).click();
        driver.findElement(By.id("PAYERNAMEADDR")).sendKeys(SWIFTpayerNameAdr);
        //Работа с выпадающим списком блока 71
        Select dropdown4 = new Select(driver.findElement(By.id("comcurr")));
        dropdown4.selectByVisibleText(SWIFT71currency); //Выбор типа валюты
        //
        driver.findElement(By.id("FEEACCOUNTID")).click();
        driver.findElement(By.xpath(".//*[contains(text(),'" + SWIFT71account + "')]")).click();//Выбор счета
        robot.delay(500);
        m.iitSign(folder, keyPassword);
        m.getDocNoSWIFT();
        Validation.documentVal();
        v.validationSWIFT(SWIFTcurrency, SWIFTsumma, SWIFTreason, SWIFTdeatail, SWIFTaccWinSTFMT, SWIFTaccWinSTBic, SWIFTaccountNo,
                SWIFTnameAdr, SWIFT50currency, SWIFT50account, SWIFTpayerNameAdr, SWIFT71currency, SWIFT71account, Sign1, Sign2);
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-0']/a")).click();
    }

    //Создания документа покупка валюты
    public void createBuyCur(String BUYsumma, String BUYcurrency, String BUYaccount, String BUYfeeRate,
                                    String BUYaccountForEnlistment, String folder, String keyPassword, String Sign1, String Sign2){
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-2']/a")).click();
        m.frameTopic();
        driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[1]/div[6]/div[2]/a")).click();
        driver.findElement(By.id("AMOUNTVISUALi")).sendKeys(BUYsumma);
        //Работа с выпадающим списком - выбор валюты
        Select dropdown1 = new Select(driver.findElement(By.id("CURRENCYID")));
        dropdown1.selectByVisibleText(BUYcurrency); //Выбор валюты
        //
        driver.findElement(By.id("FXACCOUNTID")).click();
        driver.findElement(By.xpath(".//*[contains(text(),'" + BUYaccount + "')]")).click();//Вал счет
        driver.findElement(By.id("FEERATE")).sendKeys(BUYfeeRate);
        //robot.mouseWheel(20);
        robot.delay(500);
        driver.findElement(By.id("NCACCOUNTID")).click();
        driver.findElement(By.xpath(".//*[@id='NCACCOUNTID']//*[contains(text(),'" + BUYaccountForEnlistment + "')]")).click();//Счет в гривне для зачисления
        m.iitSign(folder, keyPassword);
        m.getDocNoBUY();
        Validation.documentVal();
        v.validationDocBUY(BUYsumma, BUYcurrency, BUYaccount, BUYfeeRate, BUYaccountForEnlistment, Sign1, Sign2);
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-0']/a")).click();
    }

    //Создания документа продажи валюты
    public void createSellCur(String SELLsumma, String SELLcurrency, String SELLaccount, String SELLfeeRate,
                                     String SELLaccountForEnlistment, String folder, String keyPassword, String Sign1, String Sign2){
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-2']/a")).click();
        m.frameTopic();
        driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[1]/div[8]/div[2]/a")).click();
        driver.findElement(By.id("AMOUNTVISUALi")).sendKeys(SELLsumma);
        //Работа с выпадающим списком - выбор валюты
        Select dropdown1 = new Select(driver.findElement(By.id("CURRENCYID")));
        dropdown1.selectByVisibleText(SELLcurrency); //Выбор валюты
        //
        driver.findElement(By.id("FXACCOUNTID")).click();
        driver.findElement(By.xpath(".//*[@id='FXACCOUNTID']//*[contains(text(),'" + SELLaccount + "')]")).click();
        driver.findElement(By.id("FEERATE")).sendKeys(SELLfeeRate);
        robot.delay(500);
        driver.findElement(By.id("own_acc_choose")).click();
        driver.findElement(By.id("own_acc_list")).click();
        driver.findElement(By.xpath(".//*[@id='own_acc_list']//*[contains(text(),'" + SELLaccountForEnlistment + "')]")).click();
        m.iitSign(folder, keyPassword);
        m.getDocNoSELL();
        Validation.documentVal();
        v.validationDocSELL(SELLsumma, SELLcurrency, SELLaccount, SELLfeeRate, SELLaccountForEnlistment, Sign1, Sign2);
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-0']/a")).click();
    }

    //Создания документа конверсия валюты
    public void createConvCur(String CONVpurpose, String CONVminMaxRate, String CONVsellAmt, String CONVcurrencySell,
                                      String CONVaccountSell, String CONVcurrencyBuy, String CONVaccountBuy, String CONVaccountFee,
                                      String CONVfeePercent, String folder, String keyPassword, String Sign1, String Sign2){
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-2']/a")).click();
        m.frameTopic();
        driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[1]/div[10]/div[2]/a")).click();
        driver.findElement(By.id("PURPOSE")).sendKeys(CONVpurpose);
        driver.findElement(By.id("MAXMINRATEVISUALi")).sendKeys(CONVminMaxRate);
        driver.findElement(By.id("SELLAMOUNTVISUALi")).sendKeys(CONVsellAmt);
        //Работа с выпадающим списком - выбор валюты
        Select dropdown1 = new Select(driver.findElement(By.id("SELLCURRENCYID")));
        dropdown1.selectByVisibleText(CONVcurrencySell); //Выбор валюты
        //
        driver.findElement(By.id("SELLACCOUNTID")).click();
        driver.findElement(By.xpath(".//*[@id='SELLACCOUNTID']//*[contains(text(),'" + CONVaccountSell + "')]")).click();
        //Работа с выпадающим списком - выбор валюты
        Select dropdown2 = new Select(driver.findElement(By.id("BUYCURRENCYID")));
        dropdown2.selectByVisibleText(CONVcurrencyBuy); //Выбор валюты
        //
        driver.findElement(By.id("BUYACCOUNTID")).click();
        driver.findElement(By.xpath(".//*[@id='BUYACCOUNTID']//*[contains(text(),'" + CONVaccountBuy + "')]")).click();
        robot.delay(500);
        driver.findElement(By.id("FEEACCOUNTID")).click();
        driver.findElement(By.xpath(".//*[@id='FEEACCOUNTID']//*[contains(text(),'" + CONVaccountFee + "')]")).click();
        driver.findElement(By.id("FEEPERCENT")).sendKeys(CONVfeePercent);
        m.iitSign(folder, keyPassword);
        m.getDocNoCONV();
        Validation.documentVal();
        v.validationDocCONV(CONVpurpose, CONVminMaxRate, CONVsellAmt, CONVcurrencySell, CONVaccountSell, CONVcurrencyBuy,
                            CONVaccountBuy, CONVaccountFee, CONVfeePercent, Sign1, Sign2);
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-0']/a")).click();
    }


    //*******************************************//Доподписание документов//********************************************//
    //Доподписание документа в нац валюте
    public void signingDocUAH(String docnoUAH, String UAHaccountA, String folder, String keyPassword, String sign1, String sign2, String sign2empty){
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-1']/a")).click();
        m.findId("link-6").click();
        m.frameTopic();
        robot.delay(500);
        driver.findElement(By.xpath(".//*[@name='tf_documentno']")).clear();
        driver.findElement(By.xpath(".//*[@name='tf_documentno']")).sendKeys(docnoUAH);
        driver.findElement(By.xpath("//*[@name='b_documentno']")).click();
        driver.findElement(By.xpath(".//*[contains(text(),'" + UAHaccountA + "')]")).click();
        v.validationByID("docno", docnoUAH);
        v.validationSigns(sign1, sign2empty);
        m.iitSign(folder, keyPassword);
        v.validationSigns(sign1, sign2);
        Validation.documentChangeVal();
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-0']/a")).click();
    }
    //Доподписание документа в валюте
    public void signingDocCUR(String docnoCUR, String CURaccountA, String folder, String keyPassword){
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-2']/a")).click();
        m.findId("link-10").click();
        m.frameTopic();
        robot.delay(500);
        driver.findElement(By.xpath(".//*[@name='tf_documentno']")).clear();
        driver.findElement(By.xpath(".//*[@name='tf_documentno']")).sendKeys(docnoCUR);
        driver.findElement(By.xpath("//*[@name='b_documentno']")).click();
        driver.findElement(By.xpath(".//*[contains(text(),'" + CURaccountA + "')]")).click();
        v.validationByID("docno", docnoCUR);
        m.iitSign2(folder, keyPassword);
        Validation.documentChangeVal();
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-0']/a")).click();
    }
    //Доподписание документа SWIFT
    public void signingDocSWIFT(String docnoSWIFT, String SWIFT50account, String folder, String keyPassword){
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-2']/a")).click();
        m.findId("link-11").click();
        m.frameTopic();
        robot.delay(500);
        driver.findElement(By.xpath(".//*[@name='tf_documentno']")).clear();
        driver.findElement(By.xpath(".//*[@name='tf_documentno']")).sendKeys(docnoSWIFT);
        driver.findElement(By.xpath("//*[@name='b_documentno']")).click();
        driver.findElement(By.xpath(".//*[contains(text(),'" + SWIFT50account + "')]")).click();
        v.validationByID("DOCUMENTNO", docnoSWIFT);
        m.iitSign(folder, keyPassword);
        Validation.documentChangeVal();
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-0']/a")).click();
    }
    //Доподписание документа Покупка валюты
    public void signingDocBUY (String docnoBUY, String BUYaccount, String folder, String keyPassword){
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-2']/a")).click();
        m.findId("link-12").click();
        m.frameTopic();
        robot.delay(500);
        driver.findElement(By.xpath(".//*[@name='tf_orderno']")).clear();
        driver.findElement(By.xpath(".//*[@name='tf_orderno']")).sendKeys(docnoBUY);
        driver.findElement(By.xpath("//*[@name='b_orderno']")).click();
        driver.findElement(By.xpath(".//*[contains(text(),'" + BUYaccount + "')]")).click();
        v.validationByID("ORDERNO", docnoBUY);
        m.iitSign(folder, keyPassword);
        Validation.documentChangeVal();
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-0']/a")).click();
    }
    //Доподписание документа Продажа валюты
    public void signingDocSELL (String docnSELL, String SELLaccount, String folder, String keyPassword){
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-2']/a")).click();
        m.findId("link-13").click();
        m.frameTopic();
        robot.delay(500);
        driver.findElement(By.xpath(".//*[@name='tf_orderno']")).clear();
        driver.findElement(By.xpath(".//*[@name='tf_orderno']")).sendKeys(docnSELL);
        driver.findElement(By.xpath("//*[@name='b_orderno']")).click();
        driver.findElement(By.xpath(".//*[contains(text(),'" + SELLaccount + "')]")).click();
        v.validationByID("ORDERNO", docnSELL);
        m.iitSign(folder, keyPassword);
        Validation.documentChangeVal();
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-0']/a")).click();
    }
    //Доподписание документа Конверсия валюты
    public void signingDocCONV (String docnCONV, String CONVaccountSell, String folder, String keyPassword){
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-2']/a")).click();
        m.findId("link-14").click();
        m.frameTopic();
        robot.delay(500);
        driver.findElement(By.xpath(".//*[@name='tf_documentno']")).clear();
        driver.findElement(By.xpath(".//*[@name='tf_documentno']")).sendKeys(docnCONV);
        driver.findElement(By.xpath("//*[@name='b_documentno']")).click();
        driver.findElement(By.xpath(".//*[contains(text(),'" + CONVaccountSell + "')]")).click();
        v.validationByID("DOCUMENTNO", docnCONV);
        m.iitSign(folder, keyPassword);
        Validation.documentChangeVal();
        m.frameMenu();
        driver.findElement(By.xpath(".//*[@id='item-0']/a")).click();
    }



    //*******************************************//Дополнительные методы//********************************************//
    //Обьявление переменной "Номер документа" для Документа в нац валюте
    public void getDocNoUAH(){
        if (driver.findElement(By.id("sign_user2")).getAttribute("value").equals("")) {
            d.docnoUAH = driver.findElement(By.id("docno")).getAttribute("value").toString();
        }
    }
    //Обьявление переменной "Номер документа" для Документа в валюте
    public void getDocNoCUR(){
        if (driver.findElement(By.id("sign_user2")).getAttribute("value").equals("")) {
            d.docnoCUR = driver.findElement(By.id("docno")).getAttribute("value").toString();
        }
    }
    //Обьявление переменной "Номер документа" для Документа Покупка валюты
    public void getDocNoBUY(){
        if (driver.findElement(By.id("sign_user2")).getAttribute("value").equals("")) {
            d.docnoBUY = driver.findElement(By.id("ORDERNO")).getAttribute("value").toString();
        }
    }
    //Обьявление переменной "Номер документа" для Документа Продажа валюты
    public void getDocNoSELL(){
        if (driver.findElement(By.id("sign_user2")).getAttribute("value").equals("")) {
            d.docnoSELL = driver.findElement(By.id("ORDERNO")).getAttribute("value").toString();
        }
    }
    //Обьявление переменной "Номер документа" для SWIFT
    public void getDocNoSWIFT(){
        if (driver.findElement(By.id("sign_user2")).getAttribute("value").equals("")) {
            d.docnoSWIFT = driver.findElement(By.id("DOCUMENTNO")).getAttribute("value").toString();
        }
    }
    //Обьявление переменной "Номер документа" для Документа Конверсии валюты
    public void getDocNoCONV(){
        if (driver.findElement(By.id("sign_user2")).getAttribute("value").equals("")) {
            d.docnoCONV = driver.findElement(By.id("DOCUMENTNO")).getAttribute("value").toString();
        }
    }
    //Формирование Выписка
    public void statement(){
        //driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        m.frameMenu();
        m.findId("link-1").click();
        m.frameTopic();
        m.frameRepAccList();
        m.findName("uah_accounts").click();
        m.findName("curr_accounts").click();
        m.findXPath(".//*[@value = '8']").click();
        m.findId("calend1").clear();
        m.findId("calend1").sendKeys("02.06.2017");
        m.findId("calend2").clear();
        m.findId("calend2").sendKeys("02.10.2017");
        m.findName("reportBtn").click();
        if(isElementPresent("mm_b_ok") == true){
            m.findId("mm_b_ok").click();
            m.findName("uah_accounts").click();
            m.findName("curr_accounts").click();
            m.findName("reportBtn").click();
        }
        m.frameButtonsArea();
        //robot.delay(3000);
        m.findId("backBtn").click();
        m.frameTopic();
    }
    //Коррекное закрытие системы
    public void closeIFobs(){
        driver.switchTo().defaultContent();
        driver.switchTo().frame("tops");
        driver.findElement(By.xpath(".//*[@onclick = 'doLogout();']")).click();
        robot.delay(5000);
    }
    //*******************************************//Оптимизация-сокращение//********************************************//
    //Find ID
    public WebElement findId(String id) {
        return driver.findElement(By.id(id));
    }
    //Find XPath
    public WebElement findXPath (String xPath) {
        return driver.findElement(By.xpath(xPath));
    }
    //Find Name
    public WebElement findName (String name) {
        return driver.findElement(By.name(name));

    }
    //Frame "tops"
    public void frameTops(){
        driver.switchTo().defaultContent();
        driver.switchTo().frame("tops");
    }
    //Frame "menu"
    public void frameMenu(){
        driver.switchTo().defaultContent();
        driver.switchTo().frame("menu");
    }
    //Frame "topic"
    public void frameTopic() {
        driver.switchTo().defaultContent();
        driver.switchTo().frame("topic");
    }
    //Frame "topic - repacclist"
    public void frameRepAccList() {
        driver.switchTo().frame("repacclist");
    }
    //Frame "topic - buttonsarea"
    public void frameButtonsArea() {
        driver.switchTo().defaultContent();
        driver.switchTo().frame("topic");
        driver.switchTo().frame("buttonsarea");
    }
    //Есть ли елемент на странице
    public boolean isElementPresent(String errorID){
        try {
            m.findId(errorID);
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }












}