import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class beoSportLoginForm {

    public static final WebDriver driver = new ChromeDriver();
    WebDriverWait wait =  new WebDriverWait(driver, 10);

    //Environment
    String testEnv = "https://rs.beosport.com/customer/account/login/";

    //Locators
    String loginButton = "send2";
    String errorMessageForEmailField = "advice-required-entry-email";
    String errorMessageForPasswordFieldEmpty = "advice-required-entry-pass";
    String errorMessagePassTooShort = "advice-validate-password-pass";
    String emailField = "email";
    String passwordField = "pass";
    String cookiesConfirmation = "button.button";
    String globalErrorMessage = "li.error-msg";

    //Test data
    String validEmailAddress = "nikolazigiccz@hotmail.com";
    String validPassword = "Test1234";
    String wrongDataPassword = "test9999";
    String wrongDataEmail = "nikolazdravkovic@yahoo.com";
    String wrongDataPasswordTooShort = "test";

    //Expected Result for Error Message
    String expectedErrorMessageEmailEmpty = "Ovo polje je obavezno.";
    String expectedErrorMessagePassEmpty = "Ovo polje je obavezno.";
    String expectedErrorMessagePassTooShort ="Molimo unesite 6 ili više karaktera bez praznih polja na početku i kraju.";

    @Before
    public void openLoginForm() {
        // open Login page
        driver.get(testEnv);
        driver.manage().window().maximize();
        System.out.println("01. Open login form");
    }
    //01. TC.  Submit all required fields empty
    @Test
    public void Test01() throws Exception {
        wait.until(ExpectedConditions.elementToBeClickable(By.id(loginButton)));
        System.out.println("02. Leave all required fields empty");
        driver.findElement(By.id(loginButton)).click();
        System.out.println("03. Click on Login button");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(errorMessageForEmailField)));

        if(driver.findElement(By.id(errorMessageForEmailField)) != null && driver.findElement(By.id(errorMessageForPasswordFieldEmpty))!=null) {

            String actualResultEmailField = driver.findElement(By.id(errorMessageForEmailField)).getText();
            String actualResultPasswordField = driver.findElement(By.id(errorMessageForPasswordFieldEmpty)).getText();

            Assert.assertEquals(expectedErrorMessageEmailEmpty,actualResultEmailField);
            Assert.assertEquals(expectedErrorMessagePassEmpty,actualResultPasswordField);
            System.out.println("04. Verify that user can see proper error message");
        }else {
            throw new Exception("ERROR - Validation message for Email and Password is not present");

        }
    }
    //02. TC. Submit valid data in the Email field and leave empty Password field
    @Test
    public void Test02() throws Exception{
        wait.until(ExpectedConditions.elementToBeClickable(By.id(emailField)));
        driver.findElement(By.id(emailField)).sendKeys(validEmailAddress);
        System.out.println("02. Enter valid data in Email field");
        driver.findElement(By.id(loginButton)).click();
        System.out.println("03. Click on Login button");

        if (driver.findElement(By.id(errorMessageForPasswordFieldEmpty))!=null){
            String actualResultPasswordField = driver.findElement(By.id(errorMessageForPasswordFieldEmpty)).getText();
            Assert.assertEquals(expectedErrorMessagePassEmpty,actualResultPasswordField);
            System.out.println("04. Verify that user can see proper error message");

        }else{

            throw new Exception("ERROR - Validation message for Password is not present.");
        }
    }
    //03. TC. Submit empty Email field and enter valid data in the Password field
    @Test
    public void Test03() throws Exception{
        wait.until(ExpectedConditions.elementToBeClickable(By.id(passwordField)));
        driver.findElement(By.id(passwordField)).sendKeys(validPassword);
        System.out.println("02. Enter valid data in Password field");
        driver.findElement(By.id(loginButton)).click();
        System.out.println("03. Click on Login button");

        if (driver.findElement(By.id(errorMessageForEmailField))!=null){
            String actualResultEmailField = driver.findElement(By.id(errorMessageForEmailField)).getText();
            Assert.assertEquals(expectedErrorMessageEmailEmpty,actualResultEmailField);
            System.out.println("04. Verify that user can see proper error message");

        }else{

            throw new Exception("ERROR - Validation message for Email is not present.");
        }
    }
    //04. TC. Submit valid data in the Email field and wrong data in the Password field
    @Test
    public void Test04() throws Exception{
        wait.until(ExpectedConditions.elementToBeClickable(By.id(emailField)));
        driver.findElement(By.id(emailField)).sendKeys(validEmailAddress);
        System.out.println("02. Enter valid data in Email field");
        driver.findElement(By.id(passwordField)).sendKeys(wrongDataPassword);
        System.out.println("03. Enter wrong data in Password field");
        driver.findElement(By.id(loginButton)).click();
        System.out.println("04. Click on Login button");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(globalErrorMessage)));
        if (driver.findElement(By.cssSelector(globalErrorMessage)) != null) {

        }else{
            throw new Exception("ERROR - Validation global error message is not present.");
        }
    }
    //05. TC. Submit wrong data in the Email field and valid data in the Password field
    @Test
    public void Test05() throws Exception{
        wait.until(ExpectedConditions.elementToBeClickable(By.id(passwordField)));
        driver.findElement(By.id(passwordField)).sendKeys(validPassword);
        System.out.println("02. Enter valid data in Password field");
        driver.findElement(By.id(emailField)).sendKeys(wrongDataEmail);
        System.out.println("03. Enter wrong data in Email field");
        driver.findElement(By.id(loginButton)).click();
        System.out.println("04. Click on Login button");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(globalErrorMessage)));
        if (driver.findElement(By.cssSelector(globalErrorMessage)) != null) {

        }else{
            throw new Exception("ERROR - Validation global error message is not present.");
        }
    }
    //06. TC. Submit valid data in the Email field and less than 6 characters in the Password field
    @Test
    public void Test06() throws Exception{
        wait.until(ExpectedConditions.elementToBeClickable(By.id(passwordField)));
        driver.findElement(By.id(passwordField)).sendKeys(wrongDataPasswordTooShort);
        System.out.println("02. Enter less than 6 characters in Password field");
        driver.findElement(By.id(emailField)).sendKeys(validEmailAddress);
        System.out.println("03. Enter valid data in Email field");
        driver.findElement(By.id(loginButton)).click();
        System.out.println("04. Click on Login button");

        if (driver.findElement(By.id(errorMessagePassTooShort)) != null) {
            String actualResultPasswordField = driver.findElement(By.id(errorMessagePassTooShort)).getText();
            Assert.assertEquals(expectedErrorMessagePassTooShort,actualResultPasswordField);
            System.out.println("04. Verify that user can see proper error message");

        }else{

            throw new Exception("ERROR - Validation message for Password is not present.");
        }
    }
    //07. TC. Submit wrong data in all required fields
    @Test
    public void Test07() throws Exception{
        wait.until(ExpectedConditions.elementToBeClickable(By.id(passwordField)));
        driver.findElement(By.id(passwordField)).sendKeys(wrongDataPassword);
        System.out.println("02. Enter wrong data in Password field");
        driver.findElement(By.id(emailField)).sendKeys(wrongDataEmail);
        System.out.println("03. Enter wrong data in Email field");
        driver.findElement(By.id(loginButton)).click();
        System.out.println("04. Click on Login button");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(globalErrorMessage)));
        if (driver.findElement(By.cssSelector(globalErrorMessage)) != null) {

        }else{
            throw new Exception("ERROR - Validation global error message is not present.");
        }
        driver.close();
    }
}
