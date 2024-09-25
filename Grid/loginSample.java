package com.Grid;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class loginSample {
	
	public static WebDriver driver;
	
	@SuppressWarnings("deprecation")
	@BeforeMethod
	@Parameters({"browser"})
	public void launch(String browser) throws IOException,InterruptedException, URISyntaxException {
		
		FileReader file=new FileReader("./src//test//resources//config.properties");
		Properties prop=new Properties();
		prop.load(file);

//to run in remote machine using standalone setup in selenium grid
	/*1.open command prompt from the folder where you placed grid software and run below command to
	 * start the gride setup and keep opening the cmd prompt till ypur execution
	 *     java -jar selenium-server-4.15.0.jar standalone
	 *     
	 *     java -jar selenium-server-4.15.0.jar hub
	 *     java -jar selenium-server-4.15.0.jar node --hub http://<hub-ip>:4444
	 */
		//slecting remote or local from comfig.proprties file
		if(prop.getProperty("execution_env").equalsIgnoreCase("remote")) {
			
			DesiredCapabilities cap=new DesiredCapabilities();//in built class in selenium to set env parameters
		
			//set browser parameters
			switch(browser.toLowerCase())
			{
			case "chrome" : cap.setBrowserName("chrome");break; //set browser as chrome using keyword chrome
			case "edge" : cap.setBrowserName("MicrosoftEdge");break; //set browser as chrome using keyword MicrosoftEdge
			default:System.out.println("Invalid browser"); return;
			}
			
			//set os platform
	/*		switch(os.toLowerCase())
			{
			case "windows" : cap.setPlatform(Platform.WIN11);break; //set os platform as windows11
			case "mac" : cap.setPlatform(Platform.MAC);break; //set os platform as macios
			default:System.out.println("Invalid OS platform"); return;
			}
	*/
			
			//launch the browser in remote machine http://10.0.0.39:4444/wd/hub
			
			driver = new RemoteWebDriver(new URL("http://10.0.0.39:4444/wd/hub"),cap);
			Thread.sleep(2000);
		}

		
//to run in local machine as normal		
		if(prop.getProperty("execution_env").equalsIgnoreCase("local")) {
			
			switch(browser.toLowerCase()) 
			{
			
			case"chrome": 	driver=new ChromeDriver(); break;
			case"edge": 	driver=new EdgeDriver(); break;
			default:System.out.println("Invalid browser"); return;//return stment to exit from the testcase,no need to run further
		
			}
			
			}
		
		driver.get("https://demowebshop.tricentis.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			
	}
	
	
	@Test
	public void login() throws InterruptedException{//String name,String password) {
		
		driver.findElement(By.linkText("Log in")).click();
		driver.findElement(By.id("Email")).sendKeys("vijiraja@gmail.com");
		driver.findElement(By.id("Password")).sendKeys("viji123");
        driver.findElement(By.xpath("//input[@value='Log in']")).click();
        Thread.sleep(2000);
		
	}
	
	

	@AfterMethod()
	public void close(){
		
		driver.quit();
	}

/*	
	@DataProvider(name="logindata")
	
	 Object[][] getdata(){
		
		Object[][] data= {{"vijiraja@gmail.com", "viji123"}, 
		                  {"viji@gmail.com", "viji"} 
		                 };
		return data;
		
		
	}
	*/
}
