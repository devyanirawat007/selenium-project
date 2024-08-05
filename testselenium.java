//import org.junit.AfterClass;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
//import static org.junit.Assert.*;

public class testselenium {
    static WebDriver driver = new ChromeDriver();

    @BeforeClass
    public static void setup(){
        driver.get(" https://qa.payex.live/#/signin");

    }

    @Test
    public void login() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"txtMobileNo\"]/input")).sendKeys("7889800520");
        driver.findElement(By.xpath("//*[@id=\"txtPIN\"]/input")).sendKeys("2837");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"SubmitBtn\"]")).click();
        Thread.sleep(5000);
    }

    @Test(dependsOnMethods = {"login"})
    public void verifylogin(){
        String currURL = driver.getCurrentUrl();
        System.out.println(currURL);
        Assert.assertTrue("Not validated", currURL.contains("https://qa.payex.live/#/invoices"));
    }

    @Test(dependsOnMethods = {"verifylogin"})
    public void contactus() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"app-pane\"]/ion-menu/ion-content/ion-list/ion-menu-toggle/ion-item[9]")).click();
        Thread.sleep(4000);
    }

    @Test(dependsOnMethods = {"contactus"})
    public void verifyContactus() throws InterruptedException {
        String contactDetails = driver.findElement(By.xpath("//*[@id=\"ion-overlay-2\"]/div/div[2]/contact-us")).getText();
        System.out.println(contactDetails);
        String expectedcontactDetails = "Global PayEX Private Limited\n" +
                "Vasudev Chamber, 4th Floor, B-wing Old Nagardas Road\n" +
                "Andheri(E), Mumbai – 400 069\n" +
                "Give a Missed Call on 022-61403616\n" +
                "OK\n" +
                "Our Terms of services & Privacy Policy.";
        Thread.sleep(1000);
        Assert.assertEquals("Do not match",expectedcontactDetails,contactDetails);
        System.out.println("The contact details are correct");
        driver.findElement(By.xpath("//*[@id=\"okBtn\"]")).click();
        Thread.sleep(4000);
    }

    @Test(dependsOnMethods = {"verifyContactus"})
    public void logout() throws InterruptedException {
        WebElement logoutbtn = driver.findElement(By.xpath("//*[@id=\"app-pane\"]/ion-menu/ion-content/ion-list/ion-menu-toggle/ion-item[12]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);",logoutbtn);
        Thread.sleep(2000);
        logoutbtn.click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"okBtn\"]")).click();
    }

    @Test(dependsOnMethods = {"logout"})
    public void verifylogout() throws InterruptedException {
        //verify the login page URL is displayed
        String signinURL = driver.getCurrentUrl();
        System.out.println(signinURL);
        Assert.assertTrue("Not validated",signinURL.contains("https://qa.payex.live/#/signin"));
        Thread.sleep(4000);
        //verify the user-specific elements are not present
        try{
            WebElement userinput1 = driver.findElement(By.xpath("//*[@id=\"txtMobileNo\"]/input"));
            Assert.assertFalse("Registerd number is not visible", userinput1.isDisplayed());
            WebElement userinput2 = driver.findElement(By.xpath("//*[@id=\"txtPIN\"]/input"));
            Assert.assertFalse("Password not visible",userinput2.isDisplayed());
            Thread.sleep(2000);
        } catch (org.openqa.selenium.NoSuchElementException e){
            System.out.println("User is logged out successfully");
        } catch (AssertionError e){
            System.out.println(e.getMessage());
        }
    }

    @AfterClass
    public static void close(){

        driver.quit();
    }
}
