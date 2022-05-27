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

public class Quoting_ProposalSetup extends SfdcDriver{
	LoginSFDC login;
	SfdcLibrary sfdcLib;
	AccountManagementSamplePage account;
	AccountAndTerritoriesPage accountAndTerr = new AccountAndTerritoriesPage(commonLib);
	OpportunitiesPage oppor = new OpportunitiesPage(commonLib);
	Quote_ProposalSetupPage quote = new Quote_ProposalSetupPage(commonLib);

	String workSheet = "Quote_ProposalSetup";
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
					commonLib.log(LogStatus.INFO,
							testCaseID + "!W1! - 164745 - Quote Page Layout - Sales RM");
					login.loginToSFDC(workBook, 2, 2);
					accountAndTerr.clickAppFromHeader("Opportunities");
					commonLib.getScreenshot();
					
					oppor.selectOpportunityByNameOnOpporPage("Test_Automation_Opportunity");
					quote.clickOnQuoteNumberUnderOpportunityPage();
					commonLib.assertThat(commonLib.isDisplayed("SF_Quote_Header_Label_XPATH"), "Quote Record page is displayed ", "Quote Record page is not displayed");
					
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

	
}
