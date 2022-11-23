package com.nopcommerce.users;


import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Level_01_Repeat_Yourself {
	WebDriver driver;
	WebDriverWait expliciWait;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String homePageUrl = "https://demo.nopcommerce.com/";
	String emailAddress;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {// MAC
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver.exe");	
		}else {// Window
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");	
		}
		
		driver = new FirefoxDriver();
		expliciWait = new WebDriverWait(driver, 15);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("homePageUrl");
		
		emailAddress = "johnterry" + getRandomNumber() + "@gmail.net";
	}
	@Test
	public void Level_01_Register() {
		// Click to register link
		driver.findElement(By.cssSelector("a.ico-register")).click();
		
		// Enter/submit value
		driver.findElement(By.cssSelector("input#gender-male")).click();
		driver.findElement(By.cssSelector("input#FirstName")).sendKeys("John");
		driver.findElement(By.cssSelector("input#LastName")).sendKeys("Terry");
		
		new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthDay"))).selectByVisibleText("15");
		new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthMonth"))).selectByVisibleText("8");
		new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthYear"))).selectByVisibleText("1986");
	
		driver.findElement(By.cssSelector("input#Email")).sendKeys(emailAddress);
		driver.findElement(By.cssSelector("input#Company")).sendKeys("Marvel Studio");
		driver.findElement(By.cssSelector("input#Password")).sendKeys("123456");
		driver.findElement(By.cssSelector("input#ConfirmPassword")).sendKeys("");
		
		// Click to register button
		driver.findElement(By.cssSelector("button#register-button")).click();
		
		// Verify register seccess
		Assert.assertEquals(driver.findElement(By.cssSelector("div.result")).getText(), "Your registration completed");
	
		// Click to Logout link
		driver.findElement(By.cssSelector("a.ico-logout")).click();
		
		// Verify navigate to Home Page success
		expliciWait.until(ExpectedConditions.urlToBe(homePageUrl));
		Assert.assertEquals(driver.getCurrentUrl(), homePageUrl);
	}
	@Test
	public void Level_02_Login() {
		// Click to Login Link
		driver.findElement(By.cssSelector("a.ico-login")).click();
		
		driver.findElement(By.cssSelector("input#Email")).sendKeys(emailAddress);
		driver.findElement(By.cssSelector("input#Password")).sendKeys("123456");
		driver.findElement(By.cssSelector("button.login-button")).click();
		
		// verify My Account link is Displayed
		Assert.assertTrue(driver.findElement(By.cssSelector("a.ico-account")).isDisplayed());
		
		// Click My Account link 
		driver.findElement(By.cssSelector("a.ico-account")).click();
		
		// Verify 
		Assert.assertTrue(driver.findElement(By.cssSelector("input#gender-male")).isSelected());
		Assert.assertEquals(driver.findElement(By.cssSelector("input#FirstName")).getAttribute("value"), "John");
		Assert.assertEquals(driver.findElement(By.cssSelector("input#LastName")).getAttribute("value"), "Terry");
		
		Assert.assertEquals(new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthDay"))).getFirstSelectedOption().getText(), "15");
		Assert.assertEquals(new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthMonth"))).getFirstSelectedOption().getText(), "August");
		Assert.assertEquals(new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthYear"))).getFirstSelectedOption().getText(), "1986");
	
		Assert.assertEquals(driver.findElement(By.cssSelector("input#Email")).getAttribute("value"), emailAddress);
		Assert.assertEquals(driver.findElement(By.cssSelector("input#Company")).getAttribute("value"), "Marvel Studio");
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public int getRandomNumber() {
		return new Random().nextInt(99999);
	}
}
