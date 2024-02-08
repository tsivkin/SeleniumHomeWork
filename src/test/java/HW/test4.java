package HW;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import org.assertj.core.api.SoftAssertions;


public class test4 {
    private static WebDriver driver;
    private static final SoftAssertions softAssert = new SoftAssertions();
    String name = "Rick";
    String soName = "Sanchez";
    String fullName = "Rick Sanchez";
    String email = "ricknmorty@gmail.com";
    String mobile = "1234567890";
    String address = "168 High St, Warrenton, VA 20186";
    String gender = "Male";
    String dateBirth = "16 August,1989";
    String subject = "Computer Science";
    String hobbies = "Music";
    String stateCity = "Uttar Pradesh Lucknow";

    @BeforeAll
    public static void setUp() {

        String browser = System.getProperty("browser");

        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }
        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().window().maximize();
    }

    @Test
    public void formTest() {

        //использовать "softAssert" считаю здесь неуместно, поэтому оставил так, далее работаю уже через "softAssert"
        //даже если мое решение спорнов этом моенете с URL, то в любом случае я понял как пользоваться softAssert =)
        Assertions.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/automation-practice-form");
        softAssert.assertThat(driver.getTitle()).isEqualTo("DEMOQA");

        //ввод имени
        WebElement nameInput = driver.findElement(By.cssSelector("#firstName"));
        nameInput.sendKeys(name);

        //ввод фамилии
        WebElement lastNameInput = driver.findElement(By.cssSelector("#lastName"));
        lastNameInput.sendKeys(soName);

        //ввод почты
        WebElement mailInput = driver.findElement(By.cssSelector("#userEmail"));
        mailInput.sendKeys(email);

        //ввод номера телефона
        WebElement numberInput = driver.findElement(By.cssSelector("#userNumber"));
        numberInput.sendKeys(mobile);

        //нажатие радиобаттона
        WebElement radioButton = driver.findElement(By.cssSelector("label[for=gender-radio-1]"));
        radioButton.click();

        //ввод даты рождения на календаре
        WebElement dateOfBirthInput = driver.findElement(By.cssSelector("input[class=form-control]"));
        dateOfBirthInput.click();
        WebElement selectYearDropdown = driver.findElement(By.cssSelector("select[class=react-datepicker__year-select]"));
        Select select2 = new Select(selectYearDropdown);
        select2.selectByVisibleText("1989");
        WebElement selectMonthDropdown = driver.findElement(By.cssSelector("select[class=react-datepicker__month-select]"));
        Select select = new Select(selectMonthDropdown);
        select.selectByIndex(7);
        WebElement dayOfBirthInput = driver.findElement(By.xpath("//div[text()=16]"));
        dayOfBirthInput.click();

        //autocomplite
        WebElement subjectsInput = driver.findElement(By.cssSelector("div[class=subjects-auto-complete__input] input"));
        subjectsInput.click();
        subjectsInput.sendKeys("com");
        driver.findElement(By.xpath("//div[text()='Computer Science']")).click();

        //checkbox
        WebElement checkboxHobbies = driver.findElement(By.cssSelector("label[for=hobbies-checkbox-3]"));
        checkboxHobbies.click();

        //picture
        String patch = new File("src/main/resources/volk.jpg").getAbsolutePath();
        driver.findElement(By.cssSelector("#uploadPicture")).sendKeys(patch);

        //input address
        WebElement addressInput = driver.findElement(By.cssSelector("#currentAddress"));
        addressInput.sendKeys(address);

        //dropboxes
        WebElement dropboxState = driver.findElement(By.cssSelector("#state"));
        dropboxState.click();
        driver.findElement(By.xpath("//div[text()='Uttar Pradesh']")).click();

        WebElement dropboxSity = driver.findElement(By.cssSelector("#city"));
        dropboxSity.click();
        driver.findElement(By.xpath("//div[text()='Lucknow']")).click();

        //submitButtonPress
        WebElement submitButton = driver.findElement(By.cssSelector("#submit"));
        submitButton.click();

        //использовал wait, для того чтоб успело показаться модальное окно
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class=modal-body]")));

        //проверка появления модального окна
        WebElement modalTitle = driver.findElement(By.xpath("//div[text()='Thanks for submitting the form']"));
        String modal = modalTitle.getText();
        softAssert.assertThat(modal).isEqualTo("Thanks for submitting the form");

        //проверка введенных данных
        //проверка введеных имени и фамилии
        WebElement assertName = driver.findElement(By.xpath("//td[text()='Student Name']/following-sibling::td[1]"));
        String nameOutput = assertName.getText();
        softAssert.assertThat(nameOutput).isEqualTo(fullName);


        //проверка введеной почты
        WebElement assertEmail = driver.findElement(By.xpath("//td[text()='Student Email']/following-sibling::td[1]"));
        String emailOutput = assertEmail.getText();
        softAssert.assertThat(emailOutput).isEqualTo(email);

        //проверка введеного пола
        WebElement assertGender = driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td[1]"));
        String genderOutput = assertGender.getText();
        softAssert.assertThat(genderOutput).isEqualTo(gender);

        //проверка введеного номера телефона
        WebElement assertMobile = driver.findElement(By.xpath("//td[text()='Mobile']/following-sibling::td[1]"));
        String mobileOutput = assertMobile.getText();
        softAssert.assertThat(mobileOutput).isEqualTo(mobile);

        //проверка введеной даты рождения
        WebElement assertDateOfBirth = driver.findElement(By.xpath("//td[text()='Date of Birth']/following-sibling::td[1]"));
        String DateBirthOutput = assertDateOfBirth.getText();
        softAssert.assertThat(DateBirthOutput).isEqualTo(dateBirth);

        //проверка введеного предмета
        WebElement assertSubject = driver.findElement(By.xpath("//td[text()='Subjects']/following-sibling::td[1]"));
        String subjectOutput = assertSubject.getText();
        softAssert.assertThat(subjectOutput).isEqualTo(subject);

        //проверка введеного хобби
        WebElement assertHobbies = driver.findElement(By.xpath("//td[text()='Hobbies']/following-sibling::td[1]"));
        String hobbiesOutput = assertHobbies.getText();
        softAssert.assertThat(hobbiesOutput).isEqualTo(hobbies);

        //проверка загруженного изображения
        WebElement assertPicture = driver.findElement(By.xpath("//td[text()='Picture']/following-sibling::td[1]"));
        String pictureName = assertPicture.getText();
        softAssert.assertThat(pictureName).isEqualTo("volk.jpg");

        //проверка введеного адреса
        WebElement assertAddress = driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td[1]"));
        String addressOutput = assertAddress.getText();
        softAssert.assertThat(addressOutput).isEqualTo(address);

        //проверка выбранного штата и города
        WebElement assertStateCity = driver.findElement(By.xpath("//td[text()='State and City']/following-sibling::td[1]"));
        String stateCityOutput = assertStateCity.getText();
        softAssert.assertThat(stateCityOutput).isEqualTo(stateCity);

        softAssert.assertAll();
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
