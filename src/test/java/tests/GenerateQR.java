package tests;

import helper.RandomString;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.QRGeneratorPage;

import java.io.UnsupportedEncodingException;

public class GenerateQR extends BaseTest{

    @DataProvider
    public Object[][] dataTest(){
        return new Object[][] {
            { "", "randomCompany", "randomTitle", "randomPhone", "randomEmail", "randomAddress", "randomAddress2", "randomWebsite", "randomMemo", "Name must be at least 1 character." },
            { "aaaa", "randomCompany", "randomTitle", "randomPhone", "randomEmail", "randomAddress", "randomAddress2", "randomWebsite", "randomMemo", "Phone number must be digits only." },
            { "aaaa", "randomCompany", "randomTitle", "1211212121", "randomEmail", "randomAddress", "randomAddress2", "randomWebsite.com", "randomMemo", "Email is not valid." },
            { "aaaa", "randomCompany", "randomTitle", "1211212121", "aaaa@gmail.com", "randomAddress", "randomAddress2", "randomWebsite", "randomMemo", "URL is not valid." },
        };
    };

    @DataProvider
    public Object[][] dataTestPositive(){
        return new Object[][] {
                { RandomString.name(), "", "", "", "", "", "", "", "" },
                { RandomString.name(), RandomString.company(), "", "", "", "", "", "", ""},
                { RandomString.name(), "", "randomTitle", "", "", "", "", "", "" },
                { RandomString.name(), "", "", RandomString.phoneNumber(), "", "", "", "", "" },
                { RandomString.name(), "", "", "", RandomString.email(), "", "", "", ""},
                { RandomString.name(), "", "", "", "", "randomAddress", "", "", ""},
                { RandomString.name(), "", "", "", "", "", "randomAddress2", "", ""},
                { RandomString.name(), "", "", "", "", "", "", RandomString.website(), ""},
                { RandomString.name(), "", "", "", "", "", "", "", "randomMemo"},
                { RandomString.name(), RandomString.company(), "randomTitle", RandomString.phoneNumber(), RandomString.email(), "randomAddress", "randomAddress2", RandomString.website(), "randomMemo"},
        };
    };

    @Test(description = "Failed generate QR code", dataProvider = "dataTest")
    public void failedGenerateQR(String name, String company, String title, String phone, String email, String address,
                                 String address2, String website, String memo, String msg){
        QRGeneratorPage qrGeneratorPage = new QRGeneratorPage(driver);
        qrGeneratorPage
                .inputName(name)
                .inputCompany(company)
                .inputTitle(title)
                .inputPhoneNumber(phone)
                .inputEmail(email)
                .inputAddress(address)
                .inputAddress2(address2)
                .inputWebsite(website)
                .inputMemo(memo)
                .clickGenerateBtn();
        //Validate the result
        qrGeneratorPage
                .validateErrorMsg(msg);
    }

    @Test(description = "Success generate QR code", dataProvider = "dataTestPositive")
    public void successGenerateQR(String name, String company, String title, String phone, String email, String adr,
                                  String adr2, String website, String memo) throws UnsupportedEncodingException {

        //Makes the URL recipe
        String urlOrig = "MECARD:";
        if (!name.equals("")) urlOrig = urlOrig + "N:" + name + ";";
        if (!company.equals("")) urlOrig = urlOrig + "ORG:" + company + ";";
        if (!phone.equals("")) urlOrig = urlOrig + "TEL:" + phone + ";";
        if (!website.equals("")) urlOrig = urlOrig + "URL:" + website + ";";
        if (!email.equals("")) urlOrig = urlOrig + "EMAIL:" + email + ";";
        if (!adr.equals("")) urlOrig = urlOrig + "ADR:" + adr + ";";
        if (!adr2.equals("")) {
            if (urlOrig.contains("ADR:")) urlOrig = urlOrig.substring(0, urlOrig.length() - 1) + " " + adr2 + ";";
            else urlOrig = urlOrig + "ADR:" + adr2 + ";";
        }
        if (!memo.equals("")) urlOrig = urlOrig + "NOTE:" + memo + ";";
        if (!title.equals("")) {
            if (urlOrig.contains("NOTE:")) urlOrig = urlOrig.substring(0, urlOrig.length() - 1) + title + ";";
            else urlOrig = urlOrig + "NOTE:" + title + ";";
        }
        urlOrig = urlOrig + ";";

        QRGeneratorPage qrGeneratorPage = new QRGeneratorPage(driver);
        qrGeneratorPage
                .inputName(name)
                .inputCompany(company)
                .inputTitle(title)
                .inputPhoneNumber(phone)
                .inputEmail(email)
                .inputAddress(adr)
                .inputAddress2(adr2)
                .inputWebsite(website)
                .inputMemo(memo)
                .clickGenerateBtn();
        //Validate the result
        qrGeneratorPage
                .validateQRAppears()
                .validateDownloadLinkAppears()
                .validateUrlFieldAppears()
                .validateRawTextAppears()
                .validateValueOfUrl(urlOrig);
    }

}
