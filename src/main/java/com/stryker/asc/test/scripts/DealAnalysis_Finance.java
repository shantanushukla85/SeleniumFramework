package com.stryker.asc.test.scripts;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.stryker.asc.pages.AccountAndTerritoriesPage;
import com.stryker.asc.pages.AccountManagementSamplePage;
import com.stryker.asc.pages.OpportunitiesPage;
import com.stryker.asc.pages.Quote_ProposalSetupPage;
import com.stryker.common.modules.LoginSFDC;
import com.stryker.driver.SfdcDriver;
import com.stryker.salesforceLibrary.SfdcLibrary;

public class DealAnalysis_Finance extends SfdcDriver {
	
	private SfdcLibrary sfdcLib;

	// page declaration
	LoginSFDC login;
	AccountManagementSamplePage account;
	AccountAndTerritoriesPage accountAndTerr;
	OpportunitiesPage oppor;
	Quote_ProposalSetupPage quote;
	String workSheet = "Deal_Analysis";
	boolean exist = false;
	public String newOppName;

	public DealAnalysis_Finance() {

		this.sfdcLib = new SfdcLibrary(commonLib);
		login = new LoginSFDC(commonLib);
		account = new AccountManagementSamplePage(commonLib);
		accountAndTerr = new AccountAndTerritoriesPage(commonLib);
		oppor = new OpportunitiesPage(commonLib);
		quote= new Quote_ProposalSetupPage(commonLib);
	}
	
	@Test(priority = 1)
	public void dealAnalysis_Finance_263008_TC_01() throws Exception {
		String testCaseID = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println("Test case ID value is " + testCaseID);
		String packageName = AccountAndTerritories.class.getPackage().toString();
		System.out.println("Package name value is " + packageName);
		System.out.println("Worksheet name value is " + workSheet);
		String workBook = sfdcLib.getWorkbookNameWithEnvironment();
		System.out.println("Workbook value is " + workBook);

		Map<Integer, List<String>> mapTestData = commonLib.readAllTestCaseData(workBook, workSheet, testCaseID, 1);
		List<String> headerRow = commonLib.getHeaderRow(workBook, workSheet);

		for (int rowKey : mapTestData.keySet()) {

			commonLib.syso("***** Iteration number: " + rowKey + " (Starts)*****");

			List<String> lstRowData = mapTestData.get(rowKey);
			String Run_Status = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Run"));
			String accountName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Acccount_Name"));
			String opportunityName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Opportunity_Name"));
			String disposablePart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Disposable_Part_Number"));
			String capitalPart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Capital_Part_Number"));
			String servicePart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Service_Part_Number"));
			
			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID + "164769 - ASP Calculation Options");
					login.loginToSFDC(workBook, 3, 3);
					accountAndTerr.clickAppFromHeader("Accounts");
					account.clickOnAccountNameLink(accountName);
					accountAndTerr.clickOnOpportunityNameUnderAccountPage(opportunityName);
                    quote.clickOnCreateQuote();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("Test Description");
					quote.clickOnCPQQuoteOperationsButton("Save");
					quote.addQuoteLine(disposablePart);
					quote.enterYearValueforLineItem("volumeYear1", "2007");									
					quote.addQuoteLine(capitalPart);
					quote.enterYearValueforLineItem("volumeYear2", "2012");
					quote.enterYearValueforLineItem("volumeYear3", "2008");
					quote.enterYearValueforLineItem("volumeYear4", "2009");
					quote.enterYearValueforLineItem("volumeYear5", "2003");
					quote.enterYearValueforLineItem("volumeYear6", "2005");
					quote.enterYearValueforLineItem("volumeYear7", "2011");
					quote.addQuoteLine(servicePart);
					quote.enterYearValueforLineItem("volumeYear2", "2006");
					quote.enterYearValueforLineItem("volumeYear3", "2008");
					quote.enterYearValueforLineItem("volumeYear4", "2009");
					quote.enterYearValueforLineItem("volumeYear5", "2003");
					quote.enterYearValueforLineItem("volumeYear6", "2005");
					quote.enterYearValueforLineItem("volumeYear7", "2011");
					commonLib.getScreenshot();
					commonLib.log(LogStatus.INFO, "All the line items are added with appropriate year values");
					Thread.sleep(1000);
					quote.clickOnCPQQuoteOperationsButton("Save");
					
					quote.clickOnCPQQuoteOperationsButton("Send to ASC Finance");
					String quoteStatusFin=quote.verifyQuoteStatusValue();
					System.out.println("Quote Status Value is :"+quoteStatusFin);
					commonLib.assertThat(quoteStatusFin.equalsIgnoreCase("5. FRP (Financial Review Process Team)"), "Quote Status Value is updated correctly to :"+quoteStatusFin, "Quote Status Value is not updated correctly to :"+quoteStatusFin);
					commonLib.getScreenshot();	
					
					commonLib.KeyPress_pageDown();
					Thread.sleep(2000);					
					quote.navigateToTabUnderQuoteSection("Capital Inputs");
					quote.saveFinancialModel("Capital Inputs","Pricing");
					commonLib.getScreenshot();
					commonLib.log(LogStatus.INFO, "Prices are populated under Capital Inputs section");
					
					quote.navigateToTabUnderQuoteSection("Consumables Input");
					quote.saveFinancialModel("Consumables Input","Pricing");
					commonLib.log(LogStatus.INFO, "Prices are populated under Consumables Inputs section");
					
					quote.navigateToTabUnderQuoteSection("Services Input");
					quote.saveFinancialModel("Services Input","Pricing");
					commonLib.log(LogStatus.INFO, "Prices are populated under Services Inputs section");
					
					commonLib.KeyPress_pageUp();
					commonLib.scroll_view_Dynamic("SF_Quote_Section_Name_XPATH", "Summary Rollup");		
					commonLib.assertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Tab_Name_XPATH", "CAPITAL"), "CAPITAL Section is displayed", "CAPITAL Section is not displayed");
					commonLib.assertThat(commonLib.waitForVisibilityOf("SF_Quote_Capital_Input_XPATH"), "Price is populated under CAPITAL section", "Price is not populated under CAPITAL section");
					commonLib.getScreenshot();
					commonLib.log(LogStatus.PASS, "Price is populated under CAPITAL tab of Summary Rollup section");					

				} catch (Exception e) {
					e.printStackTrace();
					commonLib.log(LogStatus.FAIL,
							"Testcase " + testCaseID + " failed due to following reason(s): " + e);
				} finally {
					commonLib.endTest();
					softAssert.assertAll();
				}

			}
		}
	}
	
	
	@Test(priority = 1)
	public void dealAnalysis_Finance_263010_TC_02() throws Exception {
		String testCaseID = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println("Test case ID value is " + testCaseID);
		String packageName = AccountAndTerritories.class.getPackage().toString();
		System.out.println("Package name value is " + packageName);
		System.out.println("Worksheet name value is " + workSheet);
		String workBook = sfdcLib.getWorkbookNameWithEnvironment();
		System.out.println("Workbook value is " + workBook);

		Map<Integer, List<String>> mapTestData = commonLib.readAllTestCaseData(workBook, workSheet, testCaseID, 1);
		List<String> headerRow = commonLib.getHeaderRow(workBook, workSheet);

		for (int rowKey : mapTestData.keySet()) {

			commonLib.syso("***** Iteration number: " + rowKey + " (Starts)*****");

			List<String> lstRowData = mapTestData.get(rowKey);
			String Run_Status = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Run"));
			String accountName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Acccount_Name"));
			String opportunityName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Opportunity_Name"));
			String disposablePart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Disposable_Part_Number"));
			String capitalPart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Capital_Part_Number"));
			String servicePart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Service_Part_Number"));
			
			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID + "164672 - Workflow: Automatically update costs (COGS) values - Finance User");
					login.loginToSFDC(workBook, 3, 3);
					accountAndTerr.clickAppFromHeader("Accounts");
					account.clickOnAccountNameLink(accountName);
					accountAndTerr.clickOnOpportunityNameUnderAccountPage(opportunityName);
                    quote.clickOnCreateQuote();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("Test Description");
					quote.clickOnCPQQuoteOperationsButton("Save");
					quote.addQuoteLine(disposablePart);
					quote.enterYearValueforLineItem("volumeYear1", "2007");									
					quote.addQuoteLine(capitalPart);
					quote.enterYearValueforLineItem("volumeYear2", "2012");
					quote.enterYearValueforLineItem("volumeYear3", "2008");
					quote.enterYearValueforLineItem("volumeYear4", "2009");
					quote.enterYearValueforLineItem("volumeYear5", "2003");
					quote.enterYearValueforLineItem("volumeYear6", "2005");
					quote.enterYearValueforLineItem("volumeYear7", "2011");
					quote.addQuoteLine(servicePart);
					quote.enterYearValueforLineItem("volumeYear2", "2006");
					quote.enterYearValueforLineItem("volumeYear3", "2008");
					quote.enterYearValueforLineItem("volumeYear4", "2009");
					quote.enterYearValueforLineItem("volumeYear5", "2003");
					quote.enterYearValueforLineItem("volumeYear6", "2005");
					quote.enterYearValueforLineItem("volumeYear7", "2011");
					commonLib.getScreenshot();
					commonLib.log(LogStatus.INFO, "All the line items are added with appropriate year values");
					Thread.sleep(1000);
					quote.clickOnCPQQuoteOperationsButton("Save");					
					commonLib.getScreenshot();
					commonLib.log(LogStatus.INFO, "Quote is saved with no errors");
			
				} catch (Exception e) {
					e.printStackTrace();
					commonLib.log(LogStatus.FAIL,
							"Testcase " + testCaseID + " failed due to following reason(s): " + e);
				} finally {
					commonLib.endTest();
					softAssert.assertAll();
				}

			}
		}
	}
}
