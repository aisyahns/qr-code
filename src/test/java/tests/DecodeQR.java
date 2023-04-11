package tests;

import helper.RandomString;
import org.testng.annotations.Test;
import pages.QRDecoderPage;
import pages.QRGeneratorPage;

import java.io.IOException;

public class DecodeQR extends BaseTest {

    @Test(description = "Success decode QR code")
    public void successDecode() throws IOException {
        String name = RandomString.name();

        //Makes the URL recipe
        String recipe = "MECARD:";
        if (!name.equals("")) recipe = recipe + "N:" + name + ";";
        recipe = recipe + ";";

        QRGeneratorPage qrGeneratorPage = new QRGeneratorPage(driver);
        QRDecoderPage qrDecoderPage = new QRDecoderPage(driver);
        qrGeneratorPage
                .inputName(RandomString.name())
                .clickGenerateBtn();
        //Validate the result
        qrGeneratorPage
                .validateQRAppears()
                .saveQR();
        qrDecoderPage
                .goToUrl()
                .inputImage()
                .clickSubmit()
                .validateDecodedAppears()
                .validateDecodedSame(recipe);
    }
}
