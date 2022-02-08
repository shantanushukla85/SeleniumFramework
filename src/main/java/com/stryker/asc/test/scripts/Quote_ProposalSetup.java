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
		 boolean flag=false;
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
			/*
			 * String View_Name =
			 * lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "View_Name"));
			 * String Account_Name =
			 * lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Account_Name"));
			 * String Contact_Type =
			 * lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Contact_Type"));
			 * String Account_To_Be_Searched =
			 * lstRowData.get(commonLib.getColumnNumberFromList(headerRow,
			 * "Account_To_Be_Searched")); String Account_Number =
			 * lstRowData.get(commonLib.getColumnNumberFromList(headerRow,
			 * "Account_Number"));
			 * 
			 * String First_Name="TestFirst"+commonLib.getRandomNumber(4); String
			 * Last_Name="TestLast"+commonLib.getRandomNumber(4);
			 */

			if (Run_Status.equalsIgnoreCase("Yes")) {

				// try {

				commonLib.startTest(testCaseID);

				commonLib.log(LogStatus.INFO,
						testCaseID + "Account Management: Test searching account, adding contact");

				login.loginToSFDC(workBook, 2, 2);

				
				accountAndTerr.clickAppFromHeader("Opportunities");
				oppor.selectOpportunityByNameOnOpporPage("qq");

				 newOppName = oppor.createNewOpportunities("Customer Assesment");

				/*
				 * } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				 * "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				 * finally { commonLib.endTest(); softAssert.assertAll(); }
				 */

			}
		}
	}


}
