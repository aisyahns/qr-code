package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class QRDecoderPage extends BasePage {
    public QRDecoderPage(WebDriver driver) {
        super(driver);
    }

    private By fileInput = By.id("qrcode");
    private By submitBtn = By.xpath("//button[text() = 'Submit']");
    private By decodeText = By.id("decoded");

    public QRDecoderPage goToUrl(){
        driver.get("https://m28dev.github.io/qrdecoder/");
        return this;
    }
    public QRDecoderPage inputImage(){
        writeText(fileInput, System.getProperty("user.dir") + "\\src\\test\\java\\data\\chart.png");
        return this;
    }

    public QRDecoderPage clickSubmit(){
        click(submitBtn);
        return this;
    }

    public QRDecoderPage validateDecodedAppears(){
        Assert.assertNotEquals(getText(decodeText), "");
        return this;
    }

    public QRDecoderPage validateDecodedSame(String text){
        Assert.assertNotEquals(getText(decodeText), text);
        return this;
    }
}
