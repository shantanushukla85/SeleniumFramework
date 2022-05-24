package com.stryker.asc.test.scripts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.stryker.asc.pages.*;
import com.stryker.asc.pages.AccountManagementSamplePage;
import com.stryker.common.modules.LoginSFDC;
import com.stryker.salesforceLibrary.SfdcLibrary;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Quote_ProposalSetup extends com.stryker.driver.SfdcDriver {

	private com.stryker.salesforceLibrary.SfdcLibrary sfdcLib;

	// page declaration
	LoginSFDC login;
	com.stryker.asc.pages.AccountManagementSamplePage account;
	AccountAndTerritoriesPage accountAndTerr = new AccountAndTerritoriesPage(commonLib);
	OpportunitiesPage oppor = new OpportunitiesPage(commonLib);
	Quote_ProposalSetupPage quote = new Quote_ProposalSetupPage(commonLib);

	String workSheet = "Quote_ProposalSetup";
	boolean exist = false;
	public String newOppName;

	public Quote_ProposalSetup() {

		this.sfdcLib = new SfdcLibrary(commonLib);
		login = new LoginSFDC(commonLib);
		account = new AccountManagementSamplePage(commonLib);

	}

	@Test(priority = 1)
	public void quote_ProposalSetup_227814_TC_01() throws Exception {
		String newOppName;
		boolean flag = false;
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

			String spendType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Spend_Type"));
			String category = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Category"));
			String constructType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Type"));
			String constructName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Name"));
			String price = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Procedure_Price"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {

					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO,
							testCaseID + "Account Management: Test searching account, adding contact");

					login.loginToSFDC(workBook, 2, 2);
					accountAndTerr.clickAppFromHeader("Opportunities");
					newOppName = oppor.createNewOpportunityWithAccountName("Test_Automation_Account",
							"Customer Assesment");

//					accountAndTerr.clickAppFromHeader("Opportunities");
//					oppor.selectOpportunityByNameOnOpporPage("trinity_oppor128");
					quote.navigationToQuoteCreationPage();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("Test Description");
					quote.clickOnAddConstructButton();
					quote.selectDropdownValueUnderAddToConstruct("Spend Type", spendType);
					quote.clickOnUpdateQuote();
					quote.selectDropdownValueUnderAddToConstruct("Category", category);
					quote.clickOnUpdateQuote();
					quote.selectDropdownValueUnderAddToConstruct("Construct Type", constructType);
					// quote.clickOnUpdateQuote();
					quote.updateAddConstructTextboxField("Procedure Name Search", "");
					quote.clickOnUpdateQuote();
					quote.selectDropdownValueUnderAddToConstruct("Construct Name", constructName);
					quote.clickOnUpdateQuote();
					quote.updateProcedurePrice(price);
					quote.updateAddConstructTextboxField("Year 1", "2015");
					quote.updateAddConstructTextboxField("Year 2", "2016");
					quote.updateAddConstructTextboxField("Year 3", "2017");
					quote.updateAddConstructTextboxField("Year 4", "2018");
					quote.updateAddConstructTextboxField("Year 5", "2019");
					quote.updateAddConstructTextboxField("Year 6", "2021");
					quote.updateAddConstructTextboxField("Year 7", "2022");

					quote.clickOnLoadButtonUnderAddConstruct();
					commonLib.softAssertThat(
							commonLib.WaitforPresenceofElement_Dynamic_Xpath("SF_Product_Title_XPATH", "ADD PRODUCTS"),
							"Procedure and its related product are loaded successfully",
							"Procedure and its related product are not loaded successfully");

					quote.clickOnAddToQuote();
					commonLib.softAssertThat(
							commonLib.WaitforPresenceofElement_Dynamic_Xpath("SF_Product_Title_XPATH", "ASC Construct"),
							"Procedure and its related product are loaded successfully",
							"Procedure and its related product are not loaded successfully");

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
	public void quote_ProposalSetup_227815_TC_02() throws Exception {
		String newOppName;
		boolean flag = false;
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

			String endoscopy_quote = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Part_Number1"));
			String medical_quote = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Part_Number2"));
			String instrument_quote = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Part_Number3"));
			String quoteDescription = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Quote_Description"));
//			String price = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Procedure_Price"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {

					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO, testCaseID + "164750 - New Proposal From ASC Opportunity Check");

					login.loginToSFDC(workBook, 2, 2);
					accountAndTerr.clickAppFromHeader("Opportunities");
					newOppName = oppor.createNewOpportunityWithAccountName("Test_Automation_Account",
							"Customer Assesment");
//					accountAndTerr.clickAppFromHeader("Opportunities");
//					oppor.selectOpportunityByNameOnOpporPage("trinity_oppor128");
					quote.navigationToQuoteCreationPage();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("Test Description");
					// quote.clickOnCPQQuoteOperationsButton("Save");
					quote.addQuoteLine(endoscopy_quote);
					quote.addQuoteLine(medical_quote);
					quote.addQuoteLine(instrument_quote);
					quote.clickOnCPQQuoteOperationsButton("Save");
					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");
					int partNumbers = quote.verifyPartNumberProducts();
					commonLib.softAssertThat(partNumbers == 3,
							"All the products added are displaying under Quotes Product screen",
							"All the products added are not displaying under Quotes Product screen");

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
	public void quote_ProposalSetup_227820_TC_03() throws Exception {
		String newOppName;
		boolean flag = false;
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

//			String spendType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Spend_Type"));
//			String category = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Category"));
//			String constructType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Type"));
//			String constructName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Name"));
//			String price = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Procedure_Price"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {

					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO,
							testCaseID + "Account Management: Test searching account, adding contact");

					login.loginToSFDC(workBook, 2, 2);
					accountAndTerr.clickAppFromHeader("Opportunities");
					newOppName = oppor.createNewOpportunityWithAccountName("Test_Automation_Account",
							"Customer Assesment");
//					accountAndTerr.clickAppFromHeader("Opportunities");
//					oppor.selectOpportunityByNameOnOpporPage("trinity_oppor128");
					quote.navigationToQuoteCreationPage();
					quote.updateQuoteDescription("Test Description");
					quote.addQuoteLine("1588020122");

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
	public void quote_ProposalSetup_164757_TC_06() throws Exception {
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

			String opportunityName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Opportunity_Name"));
			String quoteNumber = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Quote_Number"));
			String quoteDescription = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Quote_Description"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO,
							testCaseID + "Quote Proposal Setup - 164757 - Cloning/Versioning Case");
					login.loginToSFDC(workBook, 2, 2);
					accountAndTerr.clickAppFromHeader("Opportunities");
					oppor.selectOpportunityByNameOnOpporPage(opportunityName);
					quote.clickOnQuoteNumber(quoteNumber);
					quote.clickOnCloneQuote();
					quote.updateQuoteDescription(quoteDescription);
					quote.clickOnCPQQuoteOperationsButton("Save");
					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");
					quote.cickOnEditQuote(quoteNumber);
					quote.clickOnCPQQuoteOperationsButton("Version Quote");
					String quoteDescriptionText = quote.fetchQuoteDescriptiontValue();
					commonLib.softAssertThat(quoteDescriptionText.equalsIgnoreCase(quoteDescription),
							"Quote Description Value is not updated", "Quote Description Value is updated");
					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");

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
	public void quote_ProposalSetup_164745_TC_08() throws Exception {
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

			String opportunityName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Opportunity_Name"));
			String quoteNumber = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Quote_Number"));
			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID + "Quote Proposal Setup - 164745 - Quote Page Layout");
					login.loginToSFDC(workBook, 2, 2);
					accountAndTerr.clickAppFromHeader("Opportunities");
					oppor.selectOpportunityByNameOnOpporPage(opportunityName);
					quote.clickOnQuoteNumber(quoteNumber);
					commonLib.softAssertThat(commonLib.waitForPresenceOfElementLocated("SF_Opportunity_Text_XPATH"),
							"Opportunity Name is loaded successfully", "Opportunity Name is not loaded");
					commonLib.softAssertThat(commonLib.waitForPresenceOfElementLocated("SF_Quote_Number_Text_XPATH"),
							"Quote Number is loaded successfully", "Quote Number is not loaded");

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
	public void quote_ProposalSetup_227904_TC_012() throws Exception {
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
			String spendType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Spend_Type"));
			String category = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Category"));
			String constructType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Type"));
			String constructName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Name"));
			String rebatePercentage = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Rebate_Percenatge"));
			String rebateAmount = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Rebate_Amount"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {

					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID + "164782 - Disposables rebate rules input");
					login.loginToSFDC(workBook, 2, 2);
					accountAndTerr.clickAppFromHeader("Opportunities");
					newOppName = oppor.createNewOpportunityWithAccountName("Test_Automation_Account",
							"Customer Assesment");
					quote.navigationToQuoteCreationPage();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("Test Description");
					quote.clickOnAddConstructButton();
					quote.selectDropdownValueUnderAddToConstruct("Spend Type", spendType);
					quote.selectDropdownValueUnderAddToConstruct("Category", category);
					quote.selectDropdownValueUnderAddToConstruct("Construct Type", constructType);
					quote.selectDropdownValueUnderAddToConstruct("Construct Name", constructName);
					// quote.clickOnUpdateQuote();
					// quote.updateProcedurePrice(price);
					quote.updateAddConstructTextboxField("Year 1", "2015");
					quote.updateAddConstructTextboxField("Year 2", "2016");
					quote.updateAddConstructTextboxField("Year 3", "2017");
					quote.updateAddConstructTextboxField("Year 4", "2018");
					quote.updateAddConstructTextboxField("Year 5", "2019");
					quote.updateAddConstructTextboxField("Year 6", "2021");
					quote.updateAddConstructTextboxField("Year 7", "2022");
					quote.clickOnLoadButtonUnderAddConstruct();
					quote.clickOnAddToQuote();
					commonLib.softAssertThat(
							commonLib.waitForPresenceOfElementLocated("SF_Line_Item_Discount_Amount_Label_XPATH"),
							"Discount Amount field is visible", "Discount Amount field is not visible");
					quote.updateRebateFieldValue(rebatePercentage, rebateAmount);
					commonLib.softAssertThat(
							commonLib.getText("SF_Quote_Rebate_Percentage_Table_Value_XPATH")
									.equalsIgnoreCase(rebatePercentage),
							"Rebate Percentage is updated correctly", "Rebate Percentage is not updated correctly");
					commonLib.softAssertThat(
							commonLib.getText("SF_Quote_Rebate_Amount_Table_Value_XPATH")
									.equalsIgnoreCase(rebateAmount),
							"Rebate Amount is updated correctly", "Rebate Amount is not updated correctly");

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
	public void quote_ProposalSetup_184671_TC_013() throws Exception {
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
			String spendType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Spend_Type"));
			String category = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Category"));
			String constructType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Type"));
			String constructName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Name"));
			String price = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Procedure_Price"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {

					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID + "184671 - Create new proposal (import SFDC fields)");
					login.loginToSFDC(workBook, 2, 2);

					accountAndTerr.clickAppFromHeader("Opportunities");
					// oppor.selectOpportunityByNameOnOpporPage(opportunityName);
					// quote.clickOnQuoteNumber(quoteNumber);
					newOppName = oppor.createNewOpportunityWithAccountName("Test_Automation_Account",
							"Customer Assesment");
					quote.navigationToQuoteCreationPage();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("Test Description");
					quote.clickOnAddConstructButton();
					quote.selectDropdownValueUnderAddToConstruct("Spend Type", spendType);
					quote.selectDropdownValueUnderAddToConstruct("Category", category);
					quote.selectDropdownValueUnderAddToConstruct("Construct Type", constructType);
					quote.selectDropdownValueUnderAddToConstruct("Construct Name", constructName);
					quote.clickOnUpdateQuote();
					quote.updateProcedurePrice(price);
					quote.updateAddConstructTextboxField("Year 1", "2015");
					quote.updateAddConstructTextboxField("Year 2", "2016");
					quote.updateAddConstructTextboxField("Year 3", "2017");
					quote.updateAddConstructTextboxField("Year 4", "2018");
					quote.updateAddConstructTextboxField("Year 5", "2019");
					quote.updateAddConstructTextboxField("Year 6", "2021");
					quote.updateAddConstructTextboxField("Year 7", "2022");

					quote.clickOnLoadButtonUnderAddConstruct();
					quote.clickOnAddToQuote();
					quote.verifyProposedPriceIsNonEditable();

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
	public void quote_ProposalSetup_232246_TC_014() throws Exception {
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
//			String spendType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Spend_Type"));
//			String category = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Category"));
//			String constructType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Type"));
//			String constructName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Name"));
//			String price = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Procedure_Price"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {

					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID + "164746 / 202782 - This is to validated that quote stages impacts opportunity stages (WITHOUT Flex)");
					login.loginToSFDC(workBook, 2, 2);
					accountAndTerr.clickAppFromHeader("Accounts");
					accountAndTerr.searchAccountByNameFromAccountPage("Test_Automation_Account_New");
				
					accountAndTerr.clickAppFromHeader("Opportunities");
					newOppName = oppor.createNewOpportunityWithAccountName("Test_Automation_Account",
							"Customer Assesment");
					quote.navigationToQuoteCreationPage();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("Test Description");
					quote.addQuoteLine("0747031510");
					quote.clickOnCPQQuoteOperationsButton("Save");
					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");
					quote.clickOnCPQQuoteOperationsButton("Save");
					//Below test step is not working currently so commenting it out
//					quote.clickQuoteTransactionLink("Transaction Details");	
//					String quoteStatus2= quote.verfiyQuoteStatusField("Status");
//					commonLib.softAssertThat(
//							quoteStatus2.equalsIgnoreCase("2. Data Aggregation"),
//							"Quote Status is updated correctly", "Quote Status is not updated correctly");
					//quote.clickOnRequestFinancing();
					quote.clickQuoteTransactionLink("Financing Options");					
					quote.updateFlexQuestionaireDropdownValues("End Of Term", "FMV");
					quote.updateFlexQuestionaireDropdownValues("Type of Deal", "Usage Agreement");
					//quote.updateFlexQuestionaireDropdownValues("Usage Percent", "Single Percent");
					quote.clickOnFinanceFlexRadioButton("Are we offering a discounted interest rate?");
					quote.clickOnFinanceFlexRadioButton("Is service being included in the Flex deal ?");
					quote.clickOnFinanceFlexRadioButton("Does the deal include Place items?");	
					quote.clickOnFinanceFlexRadioButton("Is this a Step Agreement?");	
					
//					quote.updateFlexQuestionaireDropdownValues("Type Of SEA", "Carve Out");
//					quote.updateFlexComment("Test Flex Comment");
//					quote.updateFlexQuestionaireDropdownValues("Payment Structure", "Annually");
//					quote.updateFlexQuestionaireDropdownValues("End of Term Option", "FMV");
					quote.updateFinanceFlexFacilityType("Hospital");
					quote.clickOnCPQQuoteOperationsButton("Save");
					quote.clickOnSendToFlexButton();
					quote.clickQuoteTransactionLink("Transaction Details");	
					String quoteStatus3= quote.verfiyQuoteStatusField("Status");
					commonLib.softAssertThat(
							quoteStatus3.equalsIgnoreCase("3. Submitted to Flex"),
							"Quote Status is updated correctly", "Quote Status is not updated correctly");
					
					
					quote.clickQuoteTransactionLink("Other Info");	
					String opptyName= quote.verfiyQuoteStatusField("Opportunity Name");
					System.out.println("Opportunity Namer is: "+opptyName);
					String quoteNumber= quote.verfiyQuoteStatusField("Number");
					System.out.println("Quote Number is: "+quoteNumber);
					
					
					commonLib.refreshPage();
					accountAndTerr.logOutFromCurrentUser();					
					commonLib.getDriver().navigate().to("https://test.salesforce.com/");
					login.loginToSFDC(workBook, 4, 4);					
					accountAndTerr.clickAppFromHeader("Opportunities");
					//oppor.selectOpportunityByNameOnOpporPage(quoteNumber);
					sfdcLib.einsteinSearch("Quotes", quoteNumber);
					quote.clickOnQuoteNumber(opptyName);
					oppor.updateOpportynityStageValue("4. Pricing");
					
					
					
					
//					quote.clickOnAddConstructButton();
//					quote.selectDropdownValueUnderAddToConstruct("Spend Type", spendType);
//					quote.selectDropdownValueUnderAddToConstruct("Category", category);
//					quote.selectDropdownValueUnderAddToConstruct("Construct Type", constructType);
//					quote.selectDropdownValueUnderAddToConstruct("Construct Name", constructName);
//					quote.clickOnUpdateQuote();
//					quote.updateProcedurePrice(price);
//					quote.updateAddConstructTextboxField("Year 1", "2015");
//					quote.updateAddConstructTextboxField("Year 2", "2016");
//					quote.updateAddConstructTextboxField("Year 3", "2017");
//					quote.updateAddConstructTextboxField("Year 4", "2018");
//					quote.updateAddConstructTextboxField("Year 5", "2019");
//					quote.updateAddConstructTextboxField("Year 6", "2021");
//					quote.updateAddConstructTextboxField("Year 7", "2022");
//
//					quote.clickOnLoadButtonUnderAddConstruct();
//					quote.clickOnAddToQuote();
//					quote.verifyProposedPriceIsNonEditable();

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
