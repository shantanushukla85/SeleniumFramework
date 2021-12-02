package com.stryker.driver;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.stryker.star.CommonLibrary;

public class SfdcDriver {

	public CommonLibrary commonLib;
	public static final String packageName = "config";
	public static final String locatorFileName = "LocatorFileName";
	public static final String ReportFileName = "MedicaServices_Automation_Report";
	public SoftAssert softAssert;
	static ExtentReports extent;
	// public static WebDriver driver;

	public SfdcDriver() {
		commonLib = new CommonLibrary();
		extent = commonLib.initializeReports(ReportFileName);
		// commonLib.setExtent(extent);
	}

	@BeforeSuite
	public void startExtent() {
		//commonLib.initializeReports(ReportFileName);
	}

	@BeforeMethod
	public void setup() throws InterruptedException, IOException {
		commonLib.setExtent(extent);
		commonLib.setConfigurationFile(packageName);
		final String workSheet = commonLib.getConfigValue("SfdcDataSheet");
		softAssert = commonLib.initSoftAssert();
		commonLib.setWorkSheet(workSheet);
		commonLib.setLocatorFileName(locatorFileName);
		commonLib.launchBrowser();
		commonLib.launchURL(commonLib.getConfigValue("siteUrl"));
		commonLib.setWait(120);
		if (commonLib.waitForElementTimeOut("SF_loginPage_userName_XPATH", 60)) {
			System.out.println("Login Page opened");
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	@AfterTest
	public void CloseSetup() throws InterruptedException {
		// commonLib.quitDriver();
		// commonLib.closeExtent();
	}

	@AfterMethod
	public void CloseSetupAfterMethod() throws IOException {
		commonLib.quitDriver();
//		commonLib.closeTestCaseExecution();

	}

	@AfterSuite
	public void Close() throws InterruptedException {
		//commonLib.quitDriver();
		// commonLib.closeExtent();
	}

}
