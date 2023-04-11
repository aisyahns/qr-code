package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class QRGeneratorPage extends BasePage {
    public QRGeneratorPage(WebDriver driver) {
        super(driver);
    }

    private By contentsDropdown = By.xpath("//tr[td[text() = 'Contents']]/td/select");

    //Field if contents == Contact Information
    private By nameField = By.xpath("//tr[td[text() = 'Name']]//input");
    private By companyField = By.xpath("//tr[td[text() = 'Company']]//input");
    private By titleField = By.xpath("//tr[td[text() = 'Title']]//input");
    private By phoneNumberField = By.xpath("//tr[td[text() = 'Phone number']]//input");
    private By emailField = By.xpath("//tr[td[text() = 'Email']]//input");
    private By addressField = By.xpath("//tr[td[text() = 'Address']]//input");
    private By address2Field = By.xpath("//tr[td[text() = 'Address 2']]//input");
    private By websiteField = By.xpath("//tr[td[text() = 'Website']]//input");
    private By memoField = By.xpath("//tr[td[text() = 'Memo']]//input");

    //Field for QR property
    private By encodingDropdown = By.xpath("//tr[td[text() = 'Encoding']]//select");
    private By barcodeSizeDropdown = By.xpath("//tr[td[text() = 'Barcode size']]//select");
    private By errorCorrectionDropdown = By.xpath("//tr[td[text() = 'Error correction']]//select");
    private By charEncodingDropdown = By.xpath("//tr[td[text() = 'Character encoding']]//select");
    private By generateBtn = By.xpath("//button[contains(text(), 'Generate ')]");
    private By errorMessage = By.id("errorMessageID");

    //QR result
    private By qrImage = By.xpath("//div[@id = 'innerresult']/img");
    private By urlTextField = By.id("urlresult");
    private By downloadLink = By.id("downloadlink");
    private By rawTextField = By.id("rawtextresult");

    public QRGeneratorPage inputName(String name){
        clearText(nameField);
        writeText(nameField, name);
        return this;
    }

    public QRGeneratorPage inputCompany(String company){
        clearText(companyField);
        writeText(companyField, company);
        return this;
    }

    public QRGeneratorPage inputTitle(String title){
        clearText(titleField);
        writeText(titleField, title);
        return this;
    }

    public QRGeneratorPage inputPhoneNumber(String phoneNumber){
        clearText(phoneNumberField);
        writeText(phoneNumberField, phoneNumber);
        return this;
    }

    public QRGeneratorPage inputEmail(String email){
        clearText(emailField);
        writeText(emailField, email);
        return this;
    }

    public QRGeneratorPage inputAddress(String address){
        clearText(addressField);
        writeText(addressField, address);
        return this;
    }

    public QRGeneratorPage inputAddress2(String address2){
        clearText(address2Field);
        writeText(address2Field, address2);
        return this;
    }

    public QRGeneratorPage inputWebsite(String website){
        clearText(websiteField);
        writeText(websiteField, website);
        return this;
    }

    public QRGeneratorPage inputMemo(String memo){
        clearText(memoField);
        writeText(memoField, memo);
        return this;
    }

    public QRGeneratorPage clickGenerateBtn(){
        click(generateBtn);
        return this;
    }

    public QRGeneratorPage validateQRAppears(){
        Assert.assertTrue(waitVisibility(qrImage).isDisplayed());
        return this;
    }

    public QRGeneratorPage validateDownloadLinkAppears(){
        Assert.assertTrue(waitVisibility(downloadLink).isDisplayed());
        return this;
    }

    public QRGeneratorPage validateUrlFieldAppears(){
        Assert.assertTrue(waitVisibility(urlTextField).isDisplayed());
        return this;
    }

    public QRGeneratorPage validateRawTextAppears(){
        Assert.assertTrue(waitVisibility(rawTextField).isDisplayed());
        return this;
    }

    public QRGeneratorPage saveQR() throws IOException {
        String imageUrl = waitVisibility(qrImage).getAttribute("src");

        // Download the image using URL and URLConnection classes
        URL url = new URL(imageUrl);
        URLConnection connection = url.openConnection();
        InputStream stream = connection.getInputStream();
        Files.copy(stream, Paths.get(System.getProperty("user.dir") + "\\src\\test\\java\\data\\chart.png"), StandardCopyOption.REPLACE_EXISTING);
        return this;
    }

    public QRGeneratorPage validateErrorMsg(String error){
        Assert.assertEquals(getText(errorMessage), error);
        return this;
    }

    public QRGeneratorPage validateValueOfUrl(String url) throws UnsupportedEncodingException {
        String encodedString = URLEncoder.encode(url, "UTF-8");
        String imageUrl = waitVisibility(qrImage).getAttribute("src");
        Assert.assertTrue(imageUrl.contains(encodedString));
        return this;
    }
}
