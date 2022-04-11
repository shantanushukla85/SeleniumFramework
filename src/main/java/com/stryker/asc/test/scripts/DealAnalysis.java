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

public class DealAnalysis extends SfdcDriver {
	
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

	public DealAnalysis() {

		this.sfdcLib = new SfdcLibrary(commonLib);
		login = new LoginSFDC(commonLib);
		account = new AccountManagementSamplePage(commonLib);
		accountAndTerr = new AccountAndTerritoriesPage(commonLib);
		oppor = new OpportunitiesPage(commonLib);
		quote= new Quote_ProposalSetupPage(commonLib);
	}
	
	@Test(priority = 1)
	public void dealAnalysis_Finance_227824_TC_01() throws Exception {
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
//
//			String spendType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Spend_Type"));
//			String category = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Category"));
//			String constructType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Type"));
//			String constructName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Name"));
//			String price = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Procedure_Price"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {

					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO,
							testCaseID + "Deal analysis - Finance: 164769 - ASP");

					login.loginToSFDC(workBook, 2, 2);
					accountAndTerr.clickAppFromHeader("Accounts");
					account.clickOnAccountNameLink("Test_Automation_Account_New");
					accountAndTerr.clickOnOpportunityNameUnderAccountPage("Test_Automation_Opportunity");
					quote.navigationToQuoteCreationPage();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("Test Description");
					quote.verifyASPPriceValue();
					quote.addQuoteLine("0747031510");
					commonLib.sendKeys_DynamicValue("SF_Quote_Year_XPATH", "Year1", "2011");
					quote.addQuoteLine("5400-052-000W");
					quote.addQuoteLine("0702-040-000");
					//Step 8 is missing as Years columns are non-editable
					quote.clickOnCPQQuoteOperationsButton("Save");
					
					
					//newOppName = oppor.createNewOpportunityWithAccountName("Test_Automation_Account",
						//	"Customer Assesment");

//					accountAndTerr.clickAppFromHeader("Opportunities");
//					oppor.selectOpportunityByNameOnOpporPage("trinity_oppor128");
//					quote.navigationToQuoteCreationPage();
//					quote.switchtoFrameUnderCPQScreen();
//					quote.updateQuoteDescription("Test Description");
//					quote.clickOnAddConstructButton();
//					quote.selectDropdownValueUnderAddToConstruct("Spend Type", spendType);
//					quote.clickOnUpdateQuote();
//					quote.selectDropdownValueUnderAddToConstruct("Category", category);
//					quote.clickOnUpdateQuote();
//					quote.selectDropdownValueUnderAddToConstruct("Construct Type", constructType);
//					// quote.clickOnUpdateQuote();
//					quote.updateAddConstructTextboxField("Procedure Name Search", "");
//					quote.clickOnUpdateQuote();
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
//					commonLib.softAssertThat(
//							commonLib.WaitforPresenceofElement_Dynamic_Xpath("SF_Product_Title_XPATH", "ADD PRODUCTS"),
//							"Procedure and its related product are loaded successfully",
//							"Procedure and its related product are not loaded successfully");
//
//					quote.clickOnAddToQuote();
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
}
