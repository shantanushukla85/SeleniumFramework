package com.stryker.driver;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.stryker.salesforceLibrary.SfdcLibrary;
import com.stryker.star.CommonLibrary;


public class SfdcDriver {

	public CommonLibrary commonLib;
	public static final String configFileName = "config";
	public static final String locatorFileName = "LocatorFileName";
	public static final String ReportFileName = "ACS_Automation_Report";
	public SoftAssert softAssert;	
	SfdcLibrary sfdcLib;
	static Map<Integer, List<String>> summaryResult=new HashMap<>();
	static int rowcount=0;
	// public static WebDriver driver;

	public SfdcDriver() {

		commonLib = new CommonLibrary();
		sfdcLib=new SfdcLibrary(commonLib);
		
	}

	@BeforeSuite
	public void startExtent() throws Exception {

		commonLib.setConfigurationFile(configFileName);
		commonLib.verifyPresenceofSummarySheet(commonLib.getConfigValue("Summary_WorkbookName"), commonLib.getConfigValue("Summary_SheetName"));
	
	}
	
	@BeforeMethod
	public void setup(Method name) throws Exception {

		commonLib.setConfigurationFile(configFileName);
		commonLib.initializeReports(name.getName(),ReportFileName+"_"+name.getName());	
		softAssert = commonLib.initSoftAssert();
		commonLib.setLocatorFileName(locatorFileName);
		commonLib.launchBrowser();
		commonLib.launchURL(commonLib.getConfigValue("siteUrl"));
		commonLib.implicitWait(120);
		commonLib.setWait(120);
		if (commonLib.waitForElementTimeOut("SF_loginPage_userName_XPATH", 60)) {

			System.out.println("Login Page opened");
		}

	}

	@AfterMethod
	public void CloseSetupAfterMethod(ITestResult result) throws Exception {

		rowcount=rowcount+1;
		
		List<String> testData=new ArrayList<>();
				
		if(result.getStatus() == ITestResult.SUCCESS)
		{   
			commonLib.syso("TC "+result.getName()+" *****Passed **********");
			testData.add(result.getName());
			testData.add("Pass");	
			summaryResult.put(rowcount, testData);
		}
		else if(result.getStatus() == ITestResult.FAILURE)
		{
			commonLib.syso("TC "+result.getName()+" ******Failed **********");
			testData.add(result.getName());
			testData.add("Fail");		
			summaryResult.put(rowcount, testData);
		}
		else if(result.getStatus() == ITestResult.SKIP ){

			commonLib.syso("TC "+result.getName()+" ******Skipped**********");
			testData.add(result.getName());
			testData.add("Skipped");		
			summaryResult.put(rowcount, testData);
		}
		//commonLib.quitDriver();
		commonLib.closeExtent();
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	@AfterSuite(alwaysRun=true)
	public void Close() throws Exception {
		
		System.out.println("After Suite");
		commonLib.updateResult(commonLib.getConfigValue("Summary_WorkbookName"), commonLib.getConfigValue("Summary_SheetName"), summaryResult);
		//commonLib.quitDriver();
		
	}
	
	
	

}
