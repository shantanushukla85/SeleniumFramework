package com.stryker.common.modules;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.stryker.salesforceLibrary.SfdcLibrary;
import com.stryker.star.CommonLibrary;

public class LoginSFDC {

	private CommonLibrary commonLib;
	Date date = new Date();
	long ts = date.getTime();
	Timestamp timeStamp = new Timestamp(date.getTime());
	String Subject_Name = "Automation Task " + timeStamp.toString();
	SfdcLibrary sfdcLib = new SfdcLibrary(commonLib);
	ExtentTest parentTest;

	public LoginSFDC(CommonLibrary commonLib) {
		this.commonLib = commonLib;
		this.sfdcLib = new SfdcLibrary(commonLib);
	}

	public void loginToSFDC(String workbookName) {
		try {

			// Fetching Login Testdata
			List<String> loginData = commonLib.read_Test_Data(workbookName, "Users", 1, 1, 2, 3);
			
			commonLib.log(LogStatus.INFO, "Login with user: " + loginData.get(0));
			
			// Login with admin credentials
			sfdcLib.login(loginData.get(0), loginData.get(1));
			commonLib.waitForPageToLoad();
			commonLib.getScreenshot();
			commonLib.log(LogStatus.INFO,
					"Successfully login to " + commonLib.getConfigValue("environment").toUpperCase() + " environment.");
			sfdcLib.switchToLightningVesion();

		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		commonLib.waitForPageToLoad();
	}
	
/////////////////////////////////////////////// NEW METHODS WITH XPATHS ///////////////////////////////////////////////
public void login(String Username, String Password) throws InterruptedException, IOException {
commonLib.syso("About to login User: " + Username + " to the SalesForce Application");
commonLib.syso("Writing User Email.");
commonLib.sendKeys("SF_loginPage_userName_XPATH", Username);
commonLib.syso("User Email Specified successfully");
commonLib.syso("Putting Password and Click on Login button");
commonLib.sendKeys("SF_loginPage_passWord_XPATH", Password);
commonLib.click("SF_loginPage_submitButton_XPATH");
commonLib.syso("Login button clicked");
commonLib.getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
commonLib.implicitWait(60);
commonLib.waitForTitleTimeOut(20);
switchToLightningVesion();
commonLib.waitForPageToLoad();
}

public void switchToLightningVesion() {

	// waitForPageLoad();
	commonLib.implicitWait(90);

	String currentURL = commonLib.getCurrentUrl();
	System.out.println("URL  " + currentURL);
	if (currentURL.contains("documentforce")) {
		for (int i = 0; i < 25; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			// To check page ready state.
			currentURL = commonLib.getCurrentUrl();
			if (!(currentURL.contains("documentforce"))) {
				break;
			}
		}
	}
	if (currentURL.contains("lightning")) {
		commonLib.waitForPresenceOfElementLocated("TC_Userimg_XPATH");
	} else {
		commonLib.click("SF_switchtolightening_label_XPATH");
		commonLib.waitForPresenceOfElementLocated("TC_Userimg_XPATH");
	}
	commonLib.implicitWait(30);
}
	
}
