package com.stryker.asc.test.scripts;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.relevantcodes.extentreports.LogStatus;
import com.stryker.asc.pages.AccountAndTerritoriesPage;
import com.stryker.asc.pages.AccountManagementSamplePage;
import com.stryker.asc.pages.OpportunitiesPage;
import com.stryker.asc.pages.Quote_ProposalSetupPage;
import com.stryker.common.modules.LoginSFDC;
import com.stryker.driver.SfdcDriver;
import com.stryker.salesforceLibrary.SfdcLibrary;

public class DealAnalysis_Pricing extends SfdcDriver {

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

	public DealAnalysis_Pricing() {

		this.sfdcLib = new SfdcLibrary(commonLib);
		login = new LoginSFDC(commonLib);
		account = new AccountManagementSamplePage(commonLib);
		accountAndTerr = new AccountAndTerritoriesPage(commonLib);
		oppor = new OpportunitiesPage(commonLib);
		quote = new Quote_ProposalSetupPage(commonLib);
	}

	@Test(priority = 1)
	public void dealAnalysis_Pricing_263023_TC_01() throws Exception {
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
					commonLib.log(LogStatus.INFO, testCaseID + "164780 - Workflow - Contract Pricing Details - Sales User");
					login.loginToSFDC(workBook, 2, 2);
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
					
					quote.expandMassUpdatedSection();
					commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Field_Label_Name_XPATH", "Rebate %"), "Rebate % field is displayed", "Rebate % field is not displayed");
					commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Field_Label_Name_XPATH", "Rebate $"), "Rebate $ field is displayed", "Rebate $ field is not displayed");
					commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Field_Label_Name_XPATH", "Sell or Place"), "Sell or Place field is displayed", "Sell or Place field is not displayed");
					commonLib.getScreenshot();
					commonLib.log(LogStatus.PASS, "Updates available are for Rebates and sell\\place");
				
					quote.clickOnCPQQuoteOperationsButton("Send to ASC Finance");
					String quoteStatus=quote.verifyQuoteStatusValue();
					System.out.println("Quote Status Value is :"+quoteStatus);
					commonLib.assertThat(quoteStatus.equalsIgnoreCase("5. FRP (Financial Review Process Team)"), "Quote Status Value is updated correctly to :"+quoteStatus, "Quote Status Value is not updated correctly to :"+quoteStatus);
					commonLib.getScreenshot();	
				
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
	public void dealAnalysis_Pricing_263024_TC_02() throws Exception {
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
			String idinValue = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "IDIN_Number"));
            String grpoPricelist = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "GPO_Pricelist"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID + "164780 - Workflow - Contract Pricing Details - Sales User");
					login.loginToSFDC(workBook, 4, 4);
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
					
					quote.expandMassUpdatedSection();
					commonLib.getScreenshot();
					commonLib.log(LogStatus.PASS, "Mass Update Section is available");
					commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Field_Label_Name_XPATH", "Rebate %"), "Rebate % field is displayed", "Rebate % field is not displayed");
					commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Field_Label_Name_XPATH", "Rebate $"), "Rebate $ field is displayed", "Rebate $ field is not displayed");
					commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Field_Label_Name_XPATH", "Sell or Place"), "Sell or Place field is displayed", "Sell or Place field is not displayed");
					commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Field_Label_Name_XPATH", "GPO Pricelist"), "GPO Pricelist field is displayed", "GPO Pricelist field is not displayed");
					commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Field_Label_Name_XPATH", "Select IDN"), "Select IDN field is displayed", "Select IDN field is not displayed");
					commonLib.getScreenshot();
					commonLib.log(LogStatus.PASS, "Updates available are for Rebates and sell\\place");
					
					quote.clickOnCPQQuoteOperationsButton("Send to ASC Pricing");
					//Step 12: Mass Section is already expanded
					String quoteStatusPricing=quote.verifyQuoteStatusValue();
					System.out.println("Quote Status Value is :"+quoteStatusPricing);
					commonLib.assertThat(quoteStatusPricing.equalsIgnoreCase("4. Pricing"), "Quote Status Value is updated correctly to :"+quoteStatusPricing, "Quote Status Value is not updated correctly to :"+quoteStatusPricing);
					commonLib.getScreenshot();	
										
					commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Field_Label_Name_XPATH", "GPO Pricelist"), "GPO Pricelist field is displayed", "GPO Pricelist field is not displayed");
					commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Field_Label_Name_XPATH", "Select IDN"), "Select IDN field is displayed", "Select IDN field is not displayed");
				  
					quote.selectIDINValue(idinValue);
					  quote.selectGPOPricelistValue(grpoPricelist);
				    commonLib.getScreenshot();				    
				    quote.clickOnCPQQuoteOperationsButton("Save");
				    
				    quote.clickOnCPQQuoteOperationsButton("Send to ASC Finance");
					String quoteStatusFin=quote.verifyQuoteStatusValue();
					System.out.println("Quote Status Value is :"+quoteStatusFin);
					commonLib.assertThat(quoteStatusFin.equalsIgnoreCase("5. FRP (Financial Review Process Team)"), "Quote Status Value is updated correctly to :"+quoteStatusFin, "Quote Status Value is not updated correctly to :"+quoteStatusFin);
					commonLib.getScreenshot();	
					
					quote.clickOnFinancialModelTabLink("Capital Inputs");					
					quote.saveFinancialModel("Capital Inputs","Pricing");
					commonLib.scroll(550, 0);
					commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Financial_Model_Column_XPATH", "GPO Best"), "GPO Best Column is displaying under Capitals Input tab", "GPO Best Column is not displaying under Capitals Input tab");
				    commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Financial_Model_Column_XPATH", "GPO Tier 1"), "GPO Tier Column is displaying under Capitals Input tab", "GPO Tier Column is not displaying under Capitals Input tab");
					//commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Financial_Model_Column_XPATH", "IDN"), "IDN Column is displaying under Capitals Input tab", "IDN Column is not displaying under Capitals Input tab");
					quote.saveFinancialModel("Capital Inputs","Finance");
					commonLib.assertThat(!quote.fetchColumnHeaderUnderFinancialRollSection().contains("IDN"), "IDN Column is not displaying under Finance View", "IDN Column is displaying under Finance View");
					commonLib.assertThat(!quote.fetchColumnHeaderUnderFinancialRollSection().contains("GPO Best"), "GPO Best Column is not displaying under Finance View", "GPO Best Column is displaying under Finance View");
					
					quote.clickOnFinancialModelTabLink("Consumables Input");
					quote.saveFinancialModel("Consumables Input","Pricing");
					commonLib.scroll(550, 0);
					commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Financial_Model_Column_XPATH", "GPO Best"), "GPO Best Column is displaying under Consumables Input tab", "GPO Best Column is not displaying under Consumables Input tab");
					commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Financial_Model_Column_XPATH", "GPO Tier 1"), "GPO Tier Column is displaying under Consumables Input tab", "GPO Tier Column is not displaying under Consumables Input tab");
					//commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Financial_Model_Column_XPATH", "IDN"), "IDN Column is displaying under Consumables Input tab", "IDN Column is not displaying under Consumables Input tab");
					quote.saveFinancialModel("Consumables Input","Finance");
					commonLib.scroll(550, 0);
					commonLib.assertThat(!quote.fetchColumnHeaderUnderFinancialRollSection().contains("IDN"), "IDN Column is not displaying under Finance View", "IDN Column is displaying under Finance View");
					commonLib.assertThat(!quote.fetchColumnHeaderUnderFinancialRollSection().contains("GPO Best"), "GPO Best Column is not displaying under Finance View", "GPO Best Column is displaying under Finance View");
										
					quote.clickOnFinancialModelTabLink("Services Input");
					quote.saveFinancialModel("Services Input","Pricing");
					commonLib.assertThat(quote.fetchServiceInputValues().equalsIgnoreCase("Pricing"), "Pricing is selected as default view under Services Input tab", "Pricing is not selected as default view under Services Input tab");
															
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
	public void dealAnalysis_Pricing_263025_TC_03() throws Exception {
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
			String idinValue = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "IDIN_Number"));


			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID + "164780 - Workflow - Contract Pricing Details - Sales User");
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
					
					quote.expandMassUpdatedSection();
					commonLib.getScreenshot();
					commonLib.log(LogStatus.PASS, "Mass Update Section is available");
	
					quote.clickOnCPQQuoteOperationsButton("Send to ASC Finance");
					String quoteStatusFin=quote.verifyQuoteStatusValue();
					System.out.println("Quote Status Value is :"+quoteStatusFin);
					commonLib.assertThat(quoteStatusFin.equalsIgnoreCase("5. FRP (Financial Review Process Team)"), "Quote Status Value is updated correctly to :"+quoteStatusFin, "Quote Status Value is not updated correctly to :"+quoteStatusFin);
					commonLib.getScreenshot();	
					
					quote.clickOnCPQQuoteOperationsButton("Send to ASC Pricing");
					String quoteStatusPricing=quote.verifyQuoteStatusValue();
					System.out.println("Quote Status Value is :"+quoteStatusPricing);
					commonLib.assertThat(quoteStatusPricing.equalsIgnoreCase("4. Pricing"), "Quote Status Value is updated correctly to :"+quoteStatusPricing, "Quote Status Value is not updated correctly to :"+quoteStatusPricing);
					commonLib.getScreenshot();	
										
					commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Field_Label_Name_XPATH", "GPO Pricelist"), "GPO Pricelist field is displayed", "GPO Pricelist field is not displayed");
					commonLib.softAssertThat(commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Field_Label_Name_XPATH", "Select IDN"), "Select IDN field is displayed", "Select IDN field is not displayed");
				    quote.selectIDINValue(idinValue);
				    commonLib.getScreenshot();
				    commonLib.log(LogStatus.INFO, "IDIN Value is updated with : "+idinValue);
					
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
