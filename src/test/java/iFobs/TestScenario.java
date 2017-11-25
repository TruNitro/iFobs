package iFobs;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.awt.*;


/**
 * Created by truni on 20.09.2017.
 */

public class TestScenario {
    static private Methods m;
    static WebDriver driver;
    static Robot robot;
    static Data d = new Data();


    @BeforeClass
    public static void setUp() throws AWTException {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        robot = new Robot();
        m = new Methods();
    }


    //******************************//Две подписи//****************************************//

    //@Test//Запуск браузера и создание всех типов документа и сообщения с двумя подписями
    public void testAllDocs() {

       //Вход на стартовую страницу
       m.openAndEnterIFobs(d.Login, d.PasswordUser, d.PasswordKey, d.Folder);


       //Создание документа в гривне
        int x1 = d.UAHamt;// количество документов
        while (x1 > 0) {
            m.createDocUAH(d.UAHsumma, d.UAHaccountA, d.UAHmfoB, d.UAHaccountB,
                                 d.UAHdescrB, d.UAHidB, d.UAHpurpose, d.Folder,
                                 d.PasswordKey, d.Login, d.Login);
            x1--;
        }


        //Создание сообщения
        int x2 = d.MessageAmt;// количество документов
        while (x2 > 0) {
            m.createMessage(d.MessageMfo, d.MessageTopic, d.MessageText, d.Folder, d.PasswordKey, d.Login, d.Login);
            x2--;
        }


        //Создание документа в валюте
        int x3 = d.CURamt;// количество документов
        while (x3 > 0) {
            m.createDocCur(d.CURcurrency, d.CURsumma, d.CURaccountA, d.CURaccountB,
                                 d.CURdescr, d.CURidB, d.CURpurpose, d.Folder, d.PasswordKey, d.Login ,d.Login );
            x3--;
        }


        //Создание SWIFT
        int x4 = d.SWIFTamt;// количество документов
        while (x4 > 0) {
            m.createSWIFT(d.SWIFTcurrency,d.SWIFTsumma,d.SWIFTreason,d.SWIFTdeatail,d.SWIFTaccWinSTFMT,d.SWIFTaccWinSTBic,
                                d.SWIFTaccountNo,d.SWIFTnameAdr,d.SWIFT50currency,d.SWIFT50account,d.SWIFTpayerNameAdr,
                                d.SWIFT71currency, d.SWIFT71account,d.Folder, d.PasswordKey, d.Login, d.Login);
            x4--;
        }

        //Создания документа покупка валюты
        int x5 = d.BUYamt;// количество документов
        while (x5 > 0) {
            m.createBuyCur(d.BUYsumma,d.BUYcurrency,d.BUYaccount,d.BUYfeeRate,
                                 d.BUYaccountForEnlistment,d.Folder, d.PasswordKey, d.Login, d.Login);
            x5--;
        }


        //Создание документа продажи валюты
        int x6 = d.SELLamt;// количество документов
        while (x6 > 0) {
            m.createSellCur(d.SELLsumma,d.SELLcurrency,d.SELLaccount,d.SELLfeeRate,
                                  d.SELLaccountForEnlistment,d.Folder,d.PasswordKey, d.Login, d.Login);
            x6--;
        }


        //Создание документа конверсии валюты
        int x7 = d.CONVamt;// количество документов
        while (x7 > 0) {
            m.createConvCur(d.CONVpurpose,d.CONVminMaxRate,d.CONVsellAmt,d.CONVcurrencySell,
                                  d.CONVaccountSell,d.CONVcurrencyBuy,d.CONVaccountBuy,d.CONVaccountFee,
                                  d.CONVfeePercent,d.Folder,d.PasswordKey, d.Login, d.Login);
            x7--;
        }


        //Закрытие браузера
        m.closeIFobs();
  }

    //******************************//Первая подпись//*************************************//
    @Test//Создание всех типов докумета и сообщения под пользователем с первой подписью
    public void testAllDocsFirstSign(){

        //Вход на стартовую страницу
        m.openAndEnterIFobs(d.Login1, d.PasswordUser1,
                d.PasswordKey1, d.Folder1);


        //Создание документа в гривне
        int x1 = d.UAHamt;// количество документов
        while (x1 > 0) {
            m.createDocUAH(d.UAHsumma, d.UAHaccountA, d.UAHmfoB, d.UAHaccountB,
                    d.UAHdescrB, d.UAHidB, d.UAHpurpose, d.Folder1, d.PasswordKey1, d.Login1, d.empty);
            x1--;
        }


        //Создание сообщения
        /*int x2 = d.MessageAmt;// количество документов
        while (x2 > 0) {
            m.createMessage(d.MessageMfo, d.MessageTopic, d.MessageText, d.Folder1, d.PasswordKey1);
            x2--;
        }*/


        //Создание документа в валюте
        int x3 = d.CURamt;// количество документов
        while (x3 > 0) {
            m.createDocCur(d.CURcurrency, d.CURsumma, d.CURaccountA, d.CURaccountB,
                    d.CURdescr, d.CURidB, d.CURpurpose, d.Folder1, d.PasswordKey1,d.Login1, d.empty);
            x3--;
        }


        //Создание SWIFT
        int x4 = d.SWIFTamt;// количество документов
        while (x4 > 0) {
            m.createSWIFT(d.SWIFTcurrency,d.SWIFTsumma,d.SWIFTreason,d.SWIFTdeatail,d.SWIFTaccWinSTFMT,d.SWIFTaccWinSTBic,
                    d.SWIFTaccountNo,d.SWIFTnameAdr,d.SWIFT50currency,d.SWIFT50account,d.SWIFTpayerNameAdr,
                    d.SWIFT71currency, d.SWIFT71account,d.Folder1, d.PasswordKey1, d.Login1, d.empty);
            x4--;
        }

        //Создания документа покупка валюты
        int x5 = d.BUYamt;// количество документов
        while (x5 > 0) {
            m.createBuyCur(d.BUYsumma,d.BUYcurrency,d.BUYaccount,d.BUYfeeRate,
                    d.BUYaccountForEnlistment,d.Folder1, d.PasswordKey1, d.Login1, d.empty);
            x5--;
        }


        //Создание документа продажи валюты
        int x6 = d.SELLamt;// количество документов
        while (x6 > 0) {
            m.createSellCur(d.SELLsumma,d.SELLcurrency,d.SELLaccount,d.SELLfeeRate,
                    d.SELLaccountForEnlistment,d.Folder1,d.PasswordKey1, d.Login1, d.empty);
            x6--;
        }


        //Создание документа конверсии валюты
        int x7 = d.CONVamt;// количество документов
        while (x7 > 0) {
            m.createConvCur(d.CONVpurpose,d.CONVminMaxRate,d.CONVsellAmt,d.CONVcurrencySell,
                    d.CONVaccountSell,d.CONVcurrencyBuy,d.CONVaccountBuy,d.CONVaccountFee,
                    d.CONVfeePercent,d.Folder1,d.PasswordKey1, d.Login1, d.empty);
            x7--;
        }


        //Закрытие браузера
        m.closeIFobs();

    }

    //******************************//Вторая подпись//*************************************//
    @Test//Доподписание всех типов документов и сообщения под пользователем с второй подписью
    public void testAllDocsSecondSign() {

        //Вход на стартовую страницу
        robot.delay(5000);
        m.openAndEnterIFobs(d.Login2, d.PasswordUser2,
                d.PasswordKey2, d.Folder2);

        //Доподписание документа в нац валюте
        int x1 = d.UAHamt;// количество документов
        while (x1 > 0) {
            m.signingDocUAH(d.docnoUAH, d.UAHaccountA, d.Folder2, d.PasswordKey2, d.Login1, d.Login2, d.empty);
        x1--;
        }

        //Доподписание документа в валюте
        int x3 = d.CURamt;// количество документов
        while (x3 > 0) {
            m.signingDocCUR(d.docnoCUR, d.CURaccountA, d.Folder2, d.PasswordKey2);
        x3--;
        }

        //Доподписание документа SWIFT
        int x4 = d.SWIFTamt;// количество документов
        while (x4 > 0) {
            m.signingDocSWIFT(d.docnoSWIFT, d.SWIFT71account, d.Folder2, d.PasswordKey2);
        x4--;
        }

        //Доподписание документа Покупка валюты
        int x5 = d.BUYamt;// количество документов
        while (x5 > 0) {
            m.signingDocBUY(d.docnoBUY, d.BUYaccount, d.Folder2, d.PasswordKey2);
        x5--;
        }

        //Доподписание документа Продажа валюты
        int x6 = d.SELLamt;// количество документов
        while (x6 > 0) {
            m.signingDocSELL(d.docnoSELL, d.SELLaccount, d.Folder2, d.PasswordKey2);
        x6--;
        }

        //Доподписание документа Конвертация валюты
        int x7 = d.CONVamt;// количество документов
        while (x7 > 0) {
            m.signingDocCONV(d.docnoCONV, d.CONVaccountSell, d.Folder2, d.PasswordKey2);
        x7--;
        }

    }

    @AfterClass
    public static void Close(){
        driver.quit();
    }

}

