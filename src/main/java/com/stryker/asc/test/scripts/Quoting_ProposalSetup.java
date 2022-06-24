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

public class Quoting_ProposalSetup extends SfdcDriver {
	LoginSFDC login;
	SfdcLibrary sfdcLib;
	AccountManagementSamplePage account;
	AccountAndTerritoriesPage accountAndTerr = new AccountAndTerritoriesPage(commonLib);
	OpportunitiesPage oppor = new OpportunitiesPage(commonLib);
	Quote_ProposalSetupPage quote = new Quote_ProposalSetupPage(commonLib);

	String workSheet = "Quote_Setup";
	boolean exist = false;
	public String newOppName;

	public Quoting_ProposalSetup() {
		this.sfdcLib = new SfdcLibrary(commonLib);
		login = new LoginSFDC(commonLib);
		account = new AccountManagementSamplePage(commonLib);
	}

	@Test
	public void quote_ProposalSetup_262997_TC_01() throws Exception {
		String testCaseID = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println("Test case ID value is " + testCaseID);
		String packageName = AccountAndTerritories.class.getPackage().toString();
		String workBook = sfdcLib.getWorkbookNameWithEnvironment();

		Map<Integer, List<String>> mapTestData = commonLib.readAllTestCaseData(workBook, workSheet, testCaseID, 1);
		List<String> headerRow = commonLib.getHeaderRow(workBook, workSheet);

		for (int rowKey : mapTestData.keySet()) {
			commonLib.syso("***** Iteration number: " + rowKey + " (Starts)*****");
			List<String> lstRowData = mapTestData.get(rowKey);
			String Run_Status = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Run"));
			if (Run_Status.equalsIgnoreCase("Yes")) {
				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID + "!W1! - 164745 - Quote Page Layout - Sales RM");
					login.loginToSFDC(workBook, 2, 2);
					accountAndTerr.clickAppFromHeader("Opportunities");
					commonLib.getScreenshot();

					oppor.selectOpportunityByNameOnOpporPage("Test_Automation_Opportunity");
					quote.clickOnQuoteNumberUnderOpportunityPage();
					commonLib.assertThat(commonLib.isDisplayed("SF_Quote_Header_Label_XPATH"),
							"Quote Record page is displayed ", "Quote Record page is not displayed");

					quote.verifyQuoteDetailsFieldValues("Quote Number");
					quote.verifyQuoteDetailsFieldValues("Account");
					quote.verifyQuoteDetailsFieldValues("Capital Gross Amount");
					quote.verifyQuoteDetailsFieldValues("Capital Net Amount");
					quote.verifyQuoteDetailsFieldValues("Consumables Gross Amount");
					quote.verifyQuoteDetailsFieldValues("Consumables Net Amount");
					quote.verifyQuoteDetailsFieldValues("Service Gross Amount");
					quote.verifyQuoteDetailsFieldValues("Service Net Amount");
					quote.verifyQuoteDetailsFieldValues("Premium Total");
					quote.verifyQuoteDetailsFieldValues("Rebate Total");
					quote.verifyQuoteDetailsFieldValues("Stage");
					quote.verifyQuoteDetailsFieldValues("Flex Term");
					quote.verifyQuoteDetailsFieldValues("Deferred Payment");
					quote.verifyQuoteDetailsFieldValues("MAKO");
					quote.verifyQuoteDetailsFieldValues("Type of SEA");
					commonLib.verifyPresence_Dynamic_Xpath("SF_Quote_Transaction_Link_XPATH", "Related");
					quote.verifyQuoteDetailsFieldValues("BUs part of the deal");
					quote.verifyQuoteDetailsFieldValues("Deal Type");

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
	public void quote_ProposalSetup_262999_TC_02() throws Exception {
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
			String accountName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Account_Name"));
			String opportunityName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Opportunity_Name"));
			String disposablePart = lstRowData
					.get(commonLib.getColumnNumberFromList(headerRow, "Disposable_Part_Number"));
			String capitalPart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Capital_Part_Number"));
			String servicePart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Service_Part_Number"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID + "164752 - View Overall Deal Details");
					login.loginToSFDC(workBook, 3, 3);
					accountAndTerr.clickAppFromHeader("Accounts");
					account.clickOnAccountNameLink(accountName);
					accountAndTerr.clickOnOpportunityNameUnderAccountPage(opportunityName);
					quote.clickOnCreateQuote();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("Test Description");
					quote.clickOnCPQQuoteOperationsButton("Save");
					quote.addQuoteLine(disposablePart);
					quote.addQuoteLine(capitalPart);
					quote.addQuoteLine(servicePart);
					Thread.sleep(1000);
					commonLib.getScreenshot();
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
					String quoteStatusFin = quote.verifyQuoteStatusValue();
					System.out.println("Quote Status Value is :" + quoteStatusFin);
					commonLib.assertThat(quoteStatusFin.equalsIgnoreCase("5. FRP (Financial Review Process Team)"),
							"Quote Status Value is updated correctly to :" + quoteStatusFin,
							"Quote Status Value is not updated correctly to :" + quoteStatusFin);
					commonLib.getScreenshot();

					commonLib.verifyPresence_Dynamic_Xpath("SF_Quote_Section_Name_XPATH", "Financial Model");
					commonLib.KeyPress_pageDown();
					Thread.sleep(2000);
					quote.navigateToTabUnderQuoteSection("Capital Inputs");
					quote.saveFinancialModel("Capital Inputs", "Pricing");
					commonLib.getScreenshot();
					commonLib.log(LogStatus.INFO, "Prices are populated under Capital Inputs section");

					quote.navigateToTabUnderQuoteSection("Consumables Input");
					quote.saveFinancialModel("Consumables Input", "Pricing");
					commonLib.log(LogStatus.INFO, "Prices are populated under Consumables Inputs section");

					quote.navigateToTabUnderQuoteSection("Services Input");
					quote.saveFinancialModel("Services Input", "Pricing");
					commonLib.log(LogStatus.INFO, "Prices are populated under Services Inputs section");

					commonLib.KeyPress_pageUp();
					commonLib.scroll_view_Dynamic("SF_Quote_Section_Name_XPATH", "Summary Rollup");
					commonLib.assertThat(
							commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Tab_Name_XPATH", "CAPITAL"),
							"CAPITAL Section is displayed", "CAPITAL Section is not displayed");
					commonLib.assertThat(commonLib.waitForVisibilityOf("SF_Quote_Capital_Input_XPATH"),
							"Price is populated under CAPITAL section", "Price is not populated under CAPITAL section");
					commonLib.getScreenshot();
					commonLib.log(LogStatus.PASS, "Price is populated under CAPITAL tab of Summary Rollup section");

					quote.scrolltoSectionNameUnderQuoteScreen("Summary Rollup");
					quote.navigateToTabUnderQuoteSection("SERVICE");
					quote.verifyServiceSummaryValues("Business Unit");
					quote.verifyServiceSummaryValues("Year 1");
					quote.verifyServiceSummaryValues("Year 2");
					quote.verifyServiceSummaryValues("Year 3");
					quote.verifyServiceSummaryValues("Year 4");
					quote.verifyServiceSummaryValues("Year 5");
					quote.verifyServiceSummaryValues("Year 6");
					quote.verifyServiceSummaryValues("Year 7");
					quote.verifyServiceSummaryValues("BU Total");
					commonLib.getScreenshot();

					quote.scrolltoSectionNameUnderQuoteScreen("Summary Rollup");
					quote.navigateToTabUnderQuoteSection("BASE");
					quote.verifyBaseSummaryValues("Business Unit");
					quote.verifyBaseSummaryValues("Year 1");
					quote.verifyBaseSummaryValues("Year 2");
					quote.verifyBaseSummaryValues("Year 3");
					quote.verifyBaseSummaryValues("Year 4");
					quote.verifyBaseSummaryValues("Year 5");
					quote.verifyBaseSummaryValues("Year 6");
					quote.verifyBaseSummaryValues("Year 7");
					commonLib.getScreenshot();

					// Specify the BU Name for the part added
					String attribute1 = quote.verifyBusinessUnitEditableStatusUnderPricingSection("SM");
					String attribute2 = quote.verifyBusinessUnitEditableStatusUnderPricingSection("CORE ENDO");

					if (attribute1.contains("read-only") && attribute2.contains("read-only")) {
						commonLib.log(LogStatus.PASS, "Lines are non editable");
						commonLib.getScreenshot();
					} else {
						commonLib.log(LogStatus.FAIL, "Lines are editable");
						commonLib.getScreenshot();
					}

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
	public void quote_ProposalSetup_263001_TC_03() throws Exception {
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
			String accountName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Account_Name"));
			String csvFile= System.getProperty("user.dir") + "\\testdata\\ASC_CSV_File.csv";	
			// String opportunityName =
			// lstRowData.get(commonLib.getColumnNumberFromList(headerRow,
			// "Opportunity_Name"));
			String disposablePart = lstRowData
					.get(commonLib.getColumnNumberFromList(headerRow, "Disposable_Part_Number"));
			String capitalPart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Capital_Part_Number"));
			String servicePart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Service_Part_Number"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID
							+ "164753 - Tier pricing calculations");
					login.loginToSFDC(workBook, 3, 3);
					accountAndTerr.clickAppFromHeader("Accounts");
					account.clickOnAccountNameLink(accountName);
					accountAndTerr.clickAppFromHeader("Opportunities");
					newOppName = oppor.createNewOpportunityWithAccountName(accountName, "Data Aggregation");
					quote.clickOnCreateQuote();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("TestDescription");
					quote.clickOnCPQQuoteOperationsButton("Save");

					quote.addQuoteLine(disposablePart);
					Thread.sleep(2000);
					quote.addQuoteLine(capitalPart);
					Thread.sleep(2000);
					quote.addQuoteLine(servicePart);
					commonLib.getScreenshot();
					commonLib.log(LogStatus.INFO, "All the line items are added with appropriate year values");
					Thread.sleep(1000);
					
					//Check vaid part number in step number 7
					//quote.addQuoteLine("10367040");
					quote.clickOnCPQQuoteOperationsButton("Save");
                    quote.clikOnUploadCSVFile();
                    sfdcLib.UploadFile(csvFile);
                    quote.clickOnCPQQuoteOperationsButton("Save");

					
//					quote.clickOnCPQQuoteOperationsButton("Send to ASC Finance");
//					String quoteStatusFin = quote.verifyQuoteStatusValue();
//					System.out.println("Quote Status Value is :" + quoteStatusFin);
//					commonLib.assertThat(quoteStatusFin.equalsIgnoreCase("5. FRP (Financial Review Process Team)"),
//							"Quote Status Value is updated correctly to :" + quoteStatusFin,
//							"Quote Status Value is not updated correctly to :" + quoteStatusFin);
//					commonLib.getScreenshot();
//
//					quote.clickQuoteTransactionLink("Other Info");
//					String quotenumber = commonLib.getAttribute("SF_QuoteNumber_Value_XPATH", "value");
//					quote.verifyPresenceOfOperationButton("Return to Opportunity");
//					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");
//					quote.clickOnQuoteNumber(quotenumber);
//					commonLib.softAssertThat(commonLib.waitForVisibilityOf("SF_Quote_Header_Label_XPATH"),
//							"Navigated successfully to Quote Details screen", "Not navigated to Quote Details screen");
//
//					quote.clickOnEditQuoteButton();
//					quote.switchtoFrameUnderCPQScreen();
//					List<String> quoteSection = quote.fetchSecionNamesUnderQuoteScreen();
//					commonLib.assertThat(!quoteSection.contains("Line Items"), "Line Items Section is not displayed",
//							"Line Items Section is displayed");
//					commonLib.assertThat(!quoteSection.contains("Pricing"), "Pricing Section is not displayed",
//							"Pricing  Section is displayed");
//					commonLib.assertThat(!quoteSection.contains("Admin"), "Admin Section is not displayed",
//							"Admin  Section is displayed");
//					commonLib.assertThat(quoteSection.contains("Summary Rollup"), "Summary Rollup Section is displayed",
//							"Summary Rollup Section is not displayed");
//					commonLib.assertThat(quoteSection.contains("Financial Model"),
//							"Financial Model Section is displayed", "Financial Model  Section is not displayed");

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
	public void quote_ProposalSetup_263002_TC_04() throws Exception {
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
			String accountName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Account_Name"));
			// String opportunityName =
			// lstRowData.get(commonLib.getColumnNumberFromList(headerRow,
			// "Opportunity_Name"));
			String disposablePart = lstRowData
					.get(commonLib.getColumnNumberFromList(headerRow, "Disposable_Part_Number"));
			String capitalPart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Capital_Part_Number"));
			String servicePart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Service_Part_Number"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID
							+ "!W1! - 164735/164746/164748 - Opportunity/Quote Stages/Notifications (combine with quote stages) - SALES");
					login.loginToSFDC(workBook, 2, 2);
					accountAndTerr.clickAppFromHeader("Accounts");
					account.clickOnAccountNameLink(accountName);
					accountAndTerr.clickAppFromHeader("Opportunities");
					newOppName = oppor.createNewOpportunityWithAccountName(accountName, "Customer Assesment");
					quote.clickOnCreateQuote();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("TestDescription");
					quote.clickOnCPQQuoteOperationsButton("Save");

					quote.verifyPresenceOfOperationButton("Return to Opportunity");
					quote.verifyPresenceOfOperationButton("Rerun Pricing");
					quote.verifyPresenceOfOperationButton("Version Quote");
					quote.verifyPresenceOfOperationButton("Send to ASC Finance");
					commonLib.getScreenshot();

					quote.verifySectionNameAvailablity("Line Items");
					quote.verifySectionNameAvailablity("Mass Updates");

					List<String> sectionNames = quote.fetchSecionNamesUnderQuoteScreen();
					commonLib.assertThat(!sectionNames.contains("Summary Rollup"),
							"Summary Roll Up Section is not displayed", "Summary Roll Up Section is displayed");
					commonLib.assertThat(!sectionNames.contains("Financial Model"),
							"Financial Model Section is not displayed", "Financial Model  Section is displayed");
					commonLib.assertThat(!sectionNames.contains("Admin"), "Admin Section is not displayed",
							"Admin  Section is displayed");

					String quoteStatus = quote.verifyQuoteStatusValue();
					System.out.println("Quote Status Value is :" + quoteStatus);
					commonLib.assertThat(quoteStatus.equalsIgnoreCase("2. Deal Data Aggregation"),
							"Quote Status Value is updated correctly to :" + quoteStatus,
							"Quote Status Value is not updated correctly to :" + quoteStatus);
					commonLib.getScreenshot();

					quote.addQuoteLine(disposablePart);
					quote.enterYearValueforLineItem("volumeYear1", "2007");
					Thread.sleep(2000);
					quote.addQuoteLine(capitalPart);
					quote.enterYearValueforLineItem("volumeYear2", "2012");
					quote.enterYearValueforLineItem("volumeYear3", "2008");
					quote.enterYearValueforLineItem("volumeYear4", "2009");
					quote.enterYearValueforLineItem("volumeYear5", "2003");
					quote.enterYearValueforLineItem("volumeYear6", "2005");
					quote.enterYearValueforLineItem("volumeYear7", "2011");
					Thread.sleep(2000);
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
					String quoteStatusFin = quote.verifyQuoteStatusValue();
					System.out.println("Quote Status Value is :" + quoteStatusFin);
					commonLib.assertThat(quoteStatusFin.equalsIgnoreCase("5. FRP (Financial Review Process Team)"),
							"Quote Status Value is updated correctly to :" + quoteStatusFin,
							"Quote Status Value is not updated correctly to :" + quoteStatusFin);
					commonLib.getScreenshot();

					quote.clickQuoteTransactionLink("Other Info");
					String quotenumber = commonLib.getAttribute("SF_QuoteNumber_Value_XPATH", "value");
					quote.verifyPresenceOfOperationButton("Return to Opportunity");
					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");
					quote.clickOnQuoteNumber(quotenumber);
					commonLib.softAssertThat(commonLib.waitForVisibilityOf("SF_Quote_Header_Label_XPATH"),
							"Navigated successfully to Quote Details screen", "Not navigated to Quote Details screen");

					quote.clickOnEditQuoteButton();
					quote.switchtoFrameUnderCPQScreen();
					List<String> quoteSection = quote.fetchSecionNamesUnderQuoteScreen();
					commonLib.assertThat(!quoteSection.contains("Line Items"), "Line Items Section is not displayed",
							"Line Items Section is displayed");
					commonLib.assertThat(!quoteSection.contains("Pricing"), "Pricing Section is not displayed",
							"Pricing  Section is displayed");
					commonLib.assertThat(!quoteSection.contains("Admin"), "Admin Section is not displayed",
							"Admin  Section is displayed");
					commonLib.assertThat(quoteSection.contains("Summary Rollup"), "Summary Rollup Section is displayed",
							"Summary Rollup Section is not displayed");
					commonLib.assertThat(quoteSection.contains("Financial Model"),
							"Financial Model Section is displayed", "Financial Model  Section is not displayed");

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
	public void quote_ProposalSetup_263003_TC_05() throws Exception {
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
			String accountName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Account_Name"));
			String disposablePart = lstRowData
					.get(commonLib.getColumnNumberFromList(headerRow, "Disposable_Part_Number"));
			String capitalPart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Capital_Part_Number"));
			String servicePart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Service_Part_Number"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID
							+ "!W1! - 164735/164746/164748 - Opportunity/Quote Stages/Notifications (combine with quote stages) -FINANCE");
					login.loginToSFDC(workBook, 3, 3);
					accountAndTerr.clickAppFromHeader("Accounts");
					account.clickOnAccountNameLink(accountName);
					accountAndTerr.clickAppFromHeader("Opportunities");
					newOppName = oppor.createNewOpportunityWithAccountName(accountName, "Customer Assesment");
					quote.clickOnCreateQuote();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("TestDescription");
					quote.clickOnCPQQuoteOperationsButton("Save");

					quote.verifyPresenceOfOperationButton("Return to Opportunity");
					quote.verifyPresenceOfOperationButton("Rerun Pricing");
					quote.verifyPresenceOfOperationButton("Version Quote");
					quote.verifyPresenceOfOperationButton("Send to ASC Finance");
					commonLib.getScreenshot();

					quote.verifySectionNameAvailablity("Line Items");
					quote.verifySectionNameAvailablity("Mass Updates");

					List<String> sectionNames = quote.fetchSecionNamesUnderQuoteScreen();
					commonLib.assertThat(!sectionNames.contains("Summary Rollup"),
							"Summary Roll Up Section is not displayed", "Summary Roll Up Section is displayed");
					commonLib.assertThat(!sectionNames.contains("Financial Model"),
							"Financial Model Section is not displayed", "Financial Model  Section is displayed");
					commonLib.assertThat(!sectionNames.contains("Admin"), "Admin Section is not displayed",
							"Admin  Section is displayed");

					String quoteStatus = quote.verifyQuoteStatusValue();
					System.out.println("Quote Status Value is :" + quoteStatus);
					commonLib.assertThat(quoteStatus.equalsIgnoreCase("2. Deal Data Aggregation"),
							"Quote Status Value is updated correctly to :" + quoteStatus,
							"Quote Status Value is not updated correctly to :" + quoteStatus);
					commonLib.getScreenshot();

					quote.addQuoteLine(disposablePart);
					quote.enterYearValueforLineItem("volumeYear1", "2007");
					Thread.sleep(2000);
					quote.addQuoteLine(capitalPart);
					quote.enterYearValueforLineItem("volumeYear2", "2012");
					quote.enterYearValueforLineItem("volumeYear3", "2008");
					quote.enterYearValueforLineItem("volumeYear4", "2009");
					quote.enterYearValueforLineItem("volumeYear5", "2003");
					quote.enterYearValueforLineItem("volumeYear6", "2005");
					quote.enterYearValueforLineItem("volumeYear7", "2011");
					Thread.sleep(2000);
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
					String quoteStatusFin = quote.verifyQuoteStatusValue();
					System.out.println("Quote Status Value is :" + quoteStatusFin);
					commonLib.assertThat(quoteStatusFin.equalsIgnoreCase("5. FRP (Financial Review Process Team)"),
							"Quote Status Value is updated correctly to :" + quoteStatusFin,
							"Quote Status Value is not updated correctly to :" + quoteStatusFin);
					commonLib.getScreenshot();
					quote.verifyPresenceOfOperationButton("Return to Opportunity");
					quote.verifyPresenceOfOperationButton("Version Quote");
					quote.verifyPresenceOfOperationButton("Send to ASC Pricing");
					quote.verifyPresenceOfOperationButton("Send to Data Aggregation");
					quote.verifyPresenceOfOperationButton("Deal Confirmed - Prep Proposal");
					quote.verifyPresenceOfOperationButton("Display History");

					quote.clickQuoteTransactionLink("Other Info");
					String quotenumber = commonLib.getAttribute("SF_QuoteNumber_Value_XPATH", "value");
					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");
					Thread.sleep(3000);
					quote.clickOnQuoteNumber(quotenumber);
					commonLib.softAssertThat(commonLib.waitForVisibilityOf("SF_Quote_Header_Label_XPATH"),
							"Navigated successfully to Quote Details screen", "Not navigated to Quote Details screen");

					quote.clickOnEditQuoteButton();
					quote.switchtoFrameUnderCPQScreen();
					List<String> quoteSection = quote.fetchSecionNamesUnderQuoteScreen();
					commonLib.assertThat(!quoteSection.contains("Line Items"), "Line Items Section is not displayed",
							"Line Items Section is displayed");
					commonLib.assertThat(!quoteSection.contains("Admin"), "Admin Section is not displayed",
							"Admin  Section is displayed");

					commonLib.assertThat(quoteSection.contains("Summary Rollup"), "Summary Rollup Section is displayed",
							"Summary Rollup Section is not displayed");
					commonLib.assertThat(quoteSection.contains("Financial Model"),
							"Financial Model Section is displayed", "Financial Model  Section is not displayed");
					commonLib.assertThat(quoteSection.contains("Mass Updates"), "Mass Updates Section is displayed",
							"Mass Updates Section is not displayed");

					quote.clickOnCPQQuoteOperationsButton("Send to ASC Pricing");
					String quoteStatusPrice = quote.verifyQuoteStatusValue();
					commonLib.assertThat(quoteStatusPrice.equalsIgnoreCase("4. Pricing"),
							"Quote Status Value is updated correctly to :" + quoteStatusPrice,
							"Quote Status Value is not updated correctly to :" + quoteStatusPrice);
					commonLib.getScreenshot();
					quote.verifyPresenceOfOperationButton("Return to Opportunity");
					quote.verifyPresenceOfOperationButton("Save");
					quote.verifyPresenceOfOperationButton("Rerun Pricing");
					quote.verifyPresenceOfOperationButton("Version Quote");
					quote.verifyPresenceOfOperationButton("Send to ASC Finance");
					quote.verifyPresenceOfOperationButton("Display History");
					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");
					Thread.sleep(3000);
					quote.clickOnQuoteNumber(quotenumber);
					commonLib.softAssertThat(commonLib.waitForVisibilityOf("SF_Quote_Header_Label_XPATH"),
							"Navigated successfully to Quote Details screen", "Not navigated to Quote Details screen");
					quote.clickOnEditQuoteButton();
					quote.switchtoFrameUnderCPQScreen();
					List<String> quoteSectionPricing = quote.fetchSecionNamesUnderQuoteScreen();
					commonLib.assertThat(quoteSectionPricing.contains("Line Items"), "Line Items Section is displayed",
							"Line Items Section is not displayed");
					commonLib.assertThat(quoteSectionPricing.contains("Mass Updates"),
							"Mass Updates Section is displayed", "Mass Updates Section is not displayed");
					commonLib.assertThat(quoteSectionPricing.contains("Pricing"), "Pricing Section is displayed",
							"Pricing Section is not displayed");
					commonLib.assertThat(!quoteSectionPricing.contains("Summary Rollup"),
							"Summary Rollup Section is not displayed", "Summary Rollup Section is displayed");
					commonLib.assertThat(!quoteSectionPricing.contains("Admin"), "Admin Section is not displayed",
							"Admin  Section is displayed");

					quote.clickOnCPQQuoteOperationsButton("Send to ASC Finance");
					String quoteStatusFinValue = quote.verifyQuoteStatusValue();
					commonLib.assertThat(quoteStatusFinValue.equalsIgnoreCase("5. FRP (Financial Review Process Team)"),
							"Quote Status Value is updated correctly to :" + quoteStatusFinValue,
							"Quote Status Value is not updated correctly to :" + quoteStatusFinValue);
					commonLib.getScreenshot();

					quote.clickQuoteTransactionLink("Financing Options");
					quote.selectFlexInfoReviewCheckox();
					quote.clickOnCPQQuoteOperationsButton("Deal Confirmed - Prep Proposal");
					quote.clickQuoteTransactionLink("Transaction Details");
					String quoteStatusDealValue = quote.verifyQuoteStatusValue();
					commonLib.assertThat(quoteStatusDealValue.equalsIgnoreCase("6. Preparing Proposal"),
							"Quote Status Value is updated correctly to :" + quoteStatusDealValue,
							"Quote Status Value is not updated correctly to :" + quoteStatusDealValue);
					commonLib.getScreenshot();
					quote.verifyPresenceOfOperationButton("Return to Opportunity");
					quote.verifyPresenceOfOperationButton("Send to ASC Finance");
					quote.verifyPresenceOfOperationButton("Print");
					quote.verifyPresenceOfOperationButton("Print Appendix");
					quote.verifyPresenceOfOperationButton("Display History");
					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");
					Thread.sleep(3000);
					quote.clickOnQuoteNumber(quotenumber);
					commonLib.softAssertThat(commonLib.waitForVisibilityOf("SF_Quote_Header_Label_XPATH"),
							"Navigated successfully to Quote Details screen", "Not navigated to Quote Details screen");

					quote.clickOnEditQuoteButton();
					quote.switchtoFrameUnderCPQScreen();
					List<String> quoteSectionFin2 = quote.fetchSecionNamesUnderQuoteScreen();
					commonLib.assertThat(!quoteSectionFin2.contains("Line Items"),
							"Line Items Section is not displayed", "Line Items Section is displayed");
					commonLib.assertThat(!quoteSectionFin2.contains("Mass Updates"),
							"Mass Updates Section is not displayed", "Mass Updates Section is displayed");
					commonLib.assertThat(!quoteSectionFin2.contains("Admin"), "Admin Section is not displayed",
							"Admin Section is displayed");
					commonLib.assertThat(quoteSectionFin2.contains("Summary Rollup"),
							"Summary Rollup Section is displayed", "Summary Rollup Section is not displayed");
					commonLib.assertThat(quoteSectionFin2.contains("Pricing"), "Pricing Section is displayed",
							"Pricing Section is not  displayed");
					commonLib.assertThat(quoteSectionFin2.contains("Financial Model"),
							"Financial Model Section is displayed", "Financial Model Section is not  displayed");

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
	public void quote_ProposalSetup_263004_TC_06() throws Exception {
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
			String accountName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Account_Name"));
			String disposablePart = lstRowData
					.get(commonLib.getColumnNumberFromList(headerRow, "Disposable_Part_Number"));
			String capitalPart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Capital_Part_Number"));
			String servicePart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Service_Part_Number"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID
							+ "!W1! - 164735/164746/164748 - Opportunity/Quote Stages/Notifications (combine with quote stages) -PRICING");
					login.loginToSFDC(workBook, 4, 4);
					accountAndTerr.clickAppFromHeader("Accounts");
					account.clickOnAccountNameLink(accountName);
					accountAndTerr.clickAppFromHeader("Opportunities");
					newOppName = oppor.createNewOpportunityWithAccountName(accountName, "Customer Assesment");
					quote.clickOnCreateQuote();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("TestDescription");
					quote.clickOnCPQQuoteOperationsButton("Save");

					quote.verifyPresenceOfOperationButton("Return to Opportunity");
					quote.verifyPresenceOfOperationButton("Save");
					quote.verifyPresenceOfOperationButton("Rerun Pricing");
					quote.verifyPresenceOfOperationButton("Version Quote");
					quote.verifyPresenceOfOperationButton("Send to ASC Finance");
					quote.verifyPresenceOfOperationButton("Display History");
					commonLib.getScreenshot();

					quote.verifySectionNameAvailablity("Line Items");
					quote.verifySectionNameAvailablity("Mass Updates");

					List<String> sectionNames = quote.fetchSecionNamesUnderQuoteScreen();
					commonLib.assertThat(!sectionNames.contains("Summary Rollup"),
							"Summary Roll Up Section is not displayed", "Summary Roll Up Section is displayed");
					commonLib.assertThat(!sectionNames.contains("Financial Model"),
							"Financial Model Section is not displayed", "Financial Model  Section is displayed");
					commonLib.assertThat(!sectionNames.contains("Admin"), "Admin Section is not displayed",
							"Admin  Section is displayed");

					String quoteStatus = quote.verifyQuoteStatusValue();
					System.out.println("Quote Status Value is :" + quoteStatus);
					commonLib.assertThat(quoteStatus.equalsIgnoreCase("2. Deal Data Aggregation"),
							"Quote Status Value is updated correctly to :" + quoteStatus,
							"Quote Status Value is not updated correctly to :" + quoteStatus);
					commonLib.getScreenshot();

					quote.addQuoteLine(disposablePart);
					quote.enterYearValueforLineItem("volumeYear1", "2007");
					Thread.sleep(2000);
					quote.addQuoteLine(capitalPart);
					quote.enterYearValueforLineItem("volumeYear2", "2012");
					quote.enterYearValueforLineItem("volumeYear3", "2008");
					quote.enterYearValueforLineItem("volumeYear4", "2009");
					quote.enterYearValueforLineItem("volumeYear5", "2003");
					quote.enterYearValueforLineItem("volumeYear6", "2005");
					quote.enterYearValueforLineItem("volumeYear7", "2011");
					Thread.sleep(2000);
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
					String quoteStatusFin = quote.verifyQuoteStatusValue();
					System.out.println("Quote Status Value is :" + quoteStatusFin);
					commonLib.assertThat(quoteStatusFin.equalsIgnoreCase("5. FRP (Financial Review Process Team)"),
							"Quote Status Value is updated correctly to :" + quoteStatusFin,
							"Quote Status Value is not updated correctly to :" + quoteStatusFin);
					commonLib.getScreenshot();

					quote.verifyPresenceOfOperationButton("Return to Opportunity");
					quote.verifyPresenceOfOperationButton("Version Quote");
					quote.verifyPresenceOfOperationButton("Send to ASC Pricing");
					quote.verifyPresenceOfOperationButton("Send to Data Aggregation");

					quote.clickQuoteTransactionLink("Other Info");
					String quotenumber = commonLib.getAttribute("SF_QuoteNumber_Value_XPATH", "value");
					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");
					Thread.sleep(3000);
					quote.clickOnQuoteNumber(quotenumber);
					commonLib.softAssertThat(commonLib.waitForVisibilityOf("SF_Quote_Header_Label_XPATH"),
							"Navigated successfully to Quote Details screen", "Not navigated to Quote Details screen");

					quote.clickOnEditQuoteButton();
					quote.switchtoFrameUnderCPQScreen();
					List<String> quoteSection = quote.fetchSecionNamesUnderQuoteScreen();
					commonLib.assertThat(!quoteSection.contains("Line Items"), "Line Items Section is not displayed",
							"Line Items Section is displayed");
					commonLib.assertThat(!quoteSection.contains("Admin"), "Admin Section is not displayed",
							"Admin  Section is displayed");

					commonLib.assertThat(quoteSection.contains("Summary Rollup"), "Summary Rollup Section is displayed",
							"Summary Rollup Section is not displayed");
					commonLib.assertThat(quoteSection.contains("Pricing"), "Pricing Section is displayed",
							"Pricing Section is not displayed");
					commonLib.assertThat(quoteSection.contains("Financial Model"),
							"Financial Model Section is displayed", "Financial Model  Section is not displayed");

					quote.clickOnCPQQuoteOperationsButton("Send to ASC Pricing");
					String quoteStatusPrice = quote.verifyQuoteStatusValue();
					commonLib.assertThat(quoteStatusPrice.equalsIgnoreCase("4. Pricing"),
							"Quote Status Value is updated correctly to :" + quoteStatusPrice,
							"Quote Status Value is not updated correctly to :" + quoteStatusPrice);
					commonLib.getScreenshot();
					quote.verifyPresenceOfOperationButton("Return to Opportunity");
					quote.verifyPresenceOfOperationButton("Save");
					quote.verifyPresenceOfOperationButton("Rerun Pricing");
					quote.verifyPresenceOfOperationButton("Version Quote");
					quote.verifyPresenceOfOperationButton("Send to ASC Finance");
					quote.verifyPresenceOfOperationButton("Display History");

					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");
					Thread.sleep(3000);
					quote.clickOnQuoteNumber(quotenumber);
					commonLib.softAssertThat(commonLib.waitForVisibilityOf("SF_Quote_Header_Label_XPATH"),
							"Navigated successfully to Quote Details screen", "Not navigated to Quote Details screen");
					quote.clickOnEditQuoteButton();
					quote.switchtoFrameUnderCPQScreen();
					List<String> quoteSectionPricing = quote.fetchSecionNamesUnderQuoteScreen();
					commonLib.assertThat(quoteSectionPricing.contains("Line Items"), "Line Items Section is displayed",
							"Line Items Section is not displayed");
					commonLib.assertThat(quoteSectionPricing.contains("Pricing"), "Pricing Section is displayed",
							"Pricing Section is not displayed");
					commonLib.assertThat(quoteSectionPricing.contains("Financial Model"),
							"Financial Model Section is displayed", "Financial Model Section is not displayed");
					commonLib.assertThat(quoteSectionPricing.contains("Mass Updates"),
							"Mass Updates Section is displayed", "Mass Updates Section is not displayed");

					commonLib.assertThat(!quoteSectionPricing.contains("Summary Rollup"),
							"Summary Rollup Section is not displayed", "Summary Rollup Section is displayed");
					commonLib.assertThat(!quoteSectionPricing.contains("Admin"), "Admin Section is not displayed",
							"Admin  Section is displayed");

					quote.clickOnCPQQuoteOperationsButton("Send to ASC Finance");
					String quoteStatusFinValue = quote.verifyQuoteStatusValue();
					commonLib.assertThat(quoteStatusFinValue.equalsIgnoreCase("5. FRP (Financial Review Process Team)"),
							"Quote Status Value is updated correctly to :" + quoteStatusFinValue,
							"Quote Status Value is not updated correctly to :" + quoteStatusFinValue);
					commonLib.getScreenshot();

					quote.clickQuoteTransactionLink("Financing Options");
					quote.selectFlexInfoReviewCheckox();
					quote.clickOnCPQQuoteOperationsButton("Deal Confirmed - Prep Proposal");
					quote.clickQuoteTransactionLink("Transaction Details");
					String quoteStatusDealValue = quote.verifyQuoteStatusValue();
					commonLib.assertThat(quoteStatusDealValue.equalsIgnoreCase("6. Preparing Proposal"),
							"Quote Status Value is updated correctly to :" + quoteStatusDealValue,
							"Quote Status Value is not updated correctly to :" + quoteStatusDealValue);
					commonLib.getScreenshot();
					quote.verifyPresenceOfOperationButton("Return to Opportunity");
					quote.verifyPresenceOfOperationButton("Send to ASC Finance");
					quote.verifyPresenceOfOperationButton("Print");
					quote.verifyPresenceOfOperationButton("Display History");
					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");
					Thread.sleep(3000);
					quote.clickOnQuoteNumber(quotenumber);
					commonLib.softAssertThat(commonLib.waitForVisibilityOf("SF_Quote_Header_Label_XPATH"),
							"Navigated successfully to Quote Details screen", "Not navigated to Quote Details screen");

					quote.clickOnEditQuoteButton();
					quote.switchtoFrameUnderCPQScreen();
					List<String> quoteSectionFin2 = quote.fetchSecionNamesUnderQuoteScreen();
//					commonLib.assertThat(quoteSectionFin2.contains("Line Items"), "Line Items Section is displayed",
//							"Line Items Section is not displayed");
					commonLib.assertThat(quoteSectionFin2.contains("Summary Rollup"),
							"Summary Rollup Section is displayed", "Summary Rollup Section is not displayed");
					commonLib.assertThat(quoteSectionFin2.contains("Pricing"), "Pricing Section is displayed",
							"Pricing Section is not  displayed");
					commonLib.assertThat(quoteSectionFin2.contains("Financial Model"),
							"Financial Model Section is displayed", "Financial Model Section is not  displayed");
//					commonLib.assertThat(quoteSectionFin2.contains("Mass Updates"), "Mass Updates Section is displayed",
//							"Mass Updates Section is not displayed");
					commonLib.assertThat(!quoteSectionFin2.contains("Admin"), "Admin Section is not displayed",
							"Admin Section is displayed");

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
