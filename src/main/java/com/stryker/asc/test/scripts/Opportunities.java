package com.stryker.asc.test.scripts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.stryker.asc.pages.AccountAndTerritoriesPage;
import com.stryker.asc.pages.OpportunitiesPage;
import com.stryker.asc.pages.AccountManagementSamplePage;
import com.stryker.common.modules.LoginSFDC;
import com.stryker.salesforceLibrary.SfdcLibrary;

public class Opportunities extends com.stryker.driver.SfdcDriver {

	private com.stryker.salesforceLibrary.SfdcLibrary sfdcLib;

	// page declaration
	LoginSFDC login;
	com.stryker.asc.pages.AccountManagementSamplePage account;
	AccountAndTerritoriesPage accountAndTerr = new AccountAndTerritoriesPage(commonLib);
	OpportunitiesPage oppor = new OpportunitiesPage(commonLib);
	String workSheet = "opportunities";
	boolean exist = false;
	public String newOppName;

	public Opportunities() {

		this.sfdcLib = new SfdcLibrary(commonLib);
		login = new LoginSFDC(commonLib);
		account = new AccountManagementSamplePage(commonLib);

	}

// @Test(priority = 1)
	public void Opportunities_227841_TC_01() throws Exception {
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

				login.loginToSFDC(workBook);

				accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");

				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

				accountAndTerr.clickAppFromHeader("Opportunities");

				 newOppName = oppor.createNewOpportunities("Customer Assesment");

				/*
				 * } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				 * "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				 * finally { commonLib.endTest(); softAssert.assertAll(); }
				 */

			}
		}
	}

// @Test(priority = 2)
	public void Opportunities_227843_TC_02() throws Exception {
		String newOppName;
		 boolean flag=false;
		String testCaseID = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println("Test case ID value is " + testCaseID);
		String packageName = Opportunities.class.getPackage().toString();
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

				login.loginToSFDC(workBook);

				accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");

				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

				flag = accountAndTerr.clickAppFromHeader("Opportunities");
				commonLib.softAssertThat(flag, "opportunities page opened", "opportunities page not opened");

				// flag= oppor.addContactForOpportunities(newOppName);
				flag = oppor.addContactForOpportunities("EnterAcc170");
				commonLib.softAssertThat(flag, " new contact created fpr opportunitis",
						" new contact created fpr opportunitis");

				/*
				 * } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				 * "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				 * finally { commonLib.endTest(); softAssert.assertAll(); }
				 */

			}
		}
	}

	// @Test(priority = 2)
	public void Opportunities_227850_TC_03() throws Exception {
		boolean flag = false;
		String newOppName;
		String testCaseID = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println("Test case ID value is " + testCaseID);
		String packageName = Opportunities.class.getPackage().toString();
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

				login.loginToSFDC(workBook);

				accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");
				// accountAndTerr.proxyLoginUsingUser2("Margaret ASC Sales");

				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

				flag = accountAndTerr.clickAppFromHeader("Accounts");
				commonLib.softAssertThat(flag, "opportunities page opened", "opportunities page not opened");

				accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");

				oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();

				newOppName = oppor.createNewOpportunities("Customer Assesment");
				Thread.sleep(5000);

				flag = accountAndTerr.clickAppFromHeader("Opportunities");
				oppor.selectOpportunityByNameOnOpporPage(newOppName);

				oppor.verifyTasksForIntialStageOfOppor();
				
				oppor.openOpporFromLinkOnTaskPage();

				oppor.addNewOpporTeamMember();

				 oppor.markStageAsCurrent("Deal Data Aggregation", newOppName);
				 oppor.clickOptionsTabForPartcularAccOrOpp("Activity");
				 oppor.viewAllTasksForActivitesOfOppor();				
				 oppor.clickactivityLinkOfOppor("Quotes needed within 1 week");
				// flag =
				// accountAndTerr.verifytext("SF_ASCSales_Account_oppor_subjectDetail_XPATH","Quotes
				// needed within 1 week");
				flag = accountAndTerr.verifyElementExist("SF_ASCSales_Account_oppor_dueDateDetail_XPATH",
						"due date exist");

				oppor.openOpporFromLinkOnTaskPage();

				 oppor.markStageAsCurrent("Preparing Proposal", newOppName);
				 oppor.clickOptionsTabForPartcularAccOrOpp("Activity");
				 oppor.viewAllTasksForActivitesOfOppor();
				 oppor.clickactivityLinkOfOppor(
						"Review with internal teams before sending to customer ( 6. Preparing Proposal )");

				// flag =
				// accountAndTerr.verifytext("SF_ASCSales_Account_oppor_subjectDetail_XPATH",
				// "Review with internal teams before sending to customer ( 6. Preparing
				// Proposal )");
				flag = accountAndTerr.verifyElementExist("SF_ASCSales_Account_oppor_dueDateDetail_XPATH",
						"due date exist");

				
				 oppor.markStageAsCurrent("Preparing Legal Document", newOppName);
				 oppor.clickOptionsTabForPartcularAccOrOpp("Activity");
				 oppor.viewAllTasksForActivitesOfOppor();
				oppor.clickactivityLinkOfOppor(
						"Review with internal teams before sending to customer ( 8. Preparing Legal Document )");

				// flag =
				// accountAndTerr.verifytext("SF_ASCSales_Account_oppor_subjectDetail_XPATH",
				// "Review with internal teams before sending to customer ( 8. Preparing Legal
				// Document )");
				flag = accountAndTerr.verifyElementExist("SF_ASCSales_Account_oppor_dueDateDetail_XPATH",
						"due date exist");

				/*
				 * } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				 * "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				 * finally { commonLib.endTest(); softAssert.assertAll(); }
				 */

			}
		}
	}

	//@Test(priority = 2)
	public void Opportunities_227854_TC_04() throws Exception {
		boolean flag = false;
		String newOppName;
		String testCaseID = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println("Test case ID value is " + testCaseID);
		String packageName = Opportunities.class.getPackage().toString();
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

				login.loginToSFDC(workBook);
			
				accountAndTerr.proxyLoginUsingUser2("Margaret ASC Sales");

				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

				flag = accountAndTerr.clickAppFromHeader("Accounts");				
				commonLib.softAssertThat(flag, "Accounts page opened", "Accounts page not opened");

				accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");

				oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();

				newOppName = oppor.createNewOpportunities("Customer Assesment");				

				flag = accountAndTerr.clickAppFromHeader("Opportunities");
				commonLib.softAssertThat(flag, "Pricing user exist with edit right", "Pricing user not exist with edit rights");
				
				oppor.selectOpportunityByNameOnOpporPage(newOppName);				
				oppor.viewAllForOpporLeftSideRelatedListItem("Opportunity Team");
				
				flag=oppor.verifyASCPricingUserExistinOpprTeam();
				commonLib.softAssertThat(flag, "Pricing user exist with edit right", "Pricing user not exist with edit rights");
				
				accountAndTerr.logOutFromCurrentUser();
				accountAndTerr.proxyLoginUsingUser2("Margaret ASC Pricing");
				
				accountAndTerr.clickAppFromHeader("Opportunities");
				oppor.selectOpportunityByNameOnOpporPage(newOppName);
				

				flag=oppor.editTabItemsOptions("Details", "Details");
				commonLib.softAssertThat(flag, "value edited with success by pricing user", "value edited with success by pricing user");

				/*
				 * } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				 * "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				 * finally { commonLib.endTest(); softAssert.assertAll(); }
				 */

			}
		}
	}
	
	//@Test(priority = 2)
	public void Opportunities_227859_TC_05() throws Exception {
		boolean flag = false;
		String newOppName;
		String testCaseID = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println("Test case ID value is " + testCaseID);
		String packageName = Opportunities.class.getPackage().toString();
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

			String proxyUser1 = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Proxy_User1"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				// try {

				commonLib.startTest(testCaseID);

				commonLib.log(LogStatus.INFO,
						testCaseID + "Account Management: Test searching account, adding contact");

				login.loginToSFDC(workBook);

				accountAndTerr.proxyLoginUsingUser2("proxyUser1");
//Margaret ASC Sales

				flag = accountAndTerr.clickAppFromHeader("Accounts");
				commonLib.softAssertThat(flag, "Accounts page opened", "Accounts page not opened");

				accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");

				oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();

				newOppName = oppor.createNewOpportunities("Preparing Proposal");

				flag = accountAndTerr.clickAppFromHeader("Opportunities");

				oppor.selectOpportunityByNameOnOpporPage(newOppName);

				List<String> activitesTaskListbeforeCloseWon = new ArrayList<String>();
				activitesTaskListbeforeCloseWon.add("Owners saw financial value in program"); activitesTaskListbeforeCloseWon.add("Mako Clinical Champion"); activitesTaskListbeforeCloseWon.add("Favorable Implant Pricing"); activitesTaskListbeforeCloseWon.add("Value of Comprehensive Portfolio");

				oppor.selectTabfirstAndThenClickItemToActionOn("Details", "Stage");
				oppor.selectStageFromDetailTabByName("Closed Won");
				
				oppor.verifynewDealSummaryclosedReasonList(activitesTaskListbeforeCloseWon);
				
				oppor.selectClosedWonOrLostReasonByMoveSelection("Owners saw financial value in program");
				
				oppor.saveOpporFromDetailsPage();
				
				oppor.verifyError_HitASnag_ExistAndCloseWhileFillingForm();
				
				oppor.createNewDealSummaryOfClosedWonByFillingForm("Joint Replacement", "Joint Replacement", "Joint Replacement");
				flag=oppor.newDealSummaryError();
				commonLib.softAssertThat(flag,"New deal summary form fill error exist", "New deal summary form fill error does not exit");
				
				oppor.CancelAndCloseNewDealSummaryForm();
				
				oppor.createNewDealSummaryOfClosedWonByFillingForm("Joint Replacement", "MAKO", "Trauma");				
				

				accountAndTerr.clickAppFromHeader("Opportunities");
				String currentStage = oppor.searchOpportunityAndFoundStageName(newOppName);
				flag = accountAndTerr.verifyText("Closed Won", currentStage);
				commonLib.softAssertThat(flag, "Stage is updated to closed Won", "Stage is updated to closed Won");
				
				oppor.viewAllTasksForActivitesOfOppor();
				
				flag=commonLib.verifyPresence_Dynamic_Xpath("SF_ASCSales_oppor__activityLink_general_XPATH", "Assign tasks to internal team (shipping/install timelines) and implementation team");
				commonLib.softAssertThat(flag, "Assign task activity created", "Assign task activity not created");
				

				/*
				 * } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				 * "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				 * finally { commonLib.endTest(); softAssert.assertAll(); }
				 */

			}
		}
	}
	
	//@Test(priority = 2)
	public void Opportunities_227861_TC_06() throws Exception {
		String newOppName;
		 boolean flag=false;
		String testCaseID = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println("Test case ID value is " + testCaseID);
		String packageName = Opportunities.class.getPackage().toString();
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

			String proxyUser1 = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Proxy_User1"));

			if (Run_Status.equalsIgnoreCase("Yes")) {
				// try {
				commonLib.startTest(testCaseID);

				commonLib.log(LogStatus.INFO,
						testCaseID + "Account Management: Test searching account, adding contact");

				login.loginToSFDC(workBook);

				accountAndTerr.proxyLoginUsingUser2(proxyUser1);
//Margaret ASC Sales

				flag = accountAndTerr.clickAppFromHeader("Accounts");
				commonLib.softAssertThat(flag, "Accounts page opened", "Accounts page not opened");

				accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");

				oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();

				newOppName = oppor.createNewOpportunities("Preparing Proposal");

				flag = accountAndTerr.clickAppFromHeader("Opportunities");
//"EnterAcc818
				oppor.selectOpportunityByNameOnOpporPage(newOppName);

				List<String> newDealSummaryCloseReasonList = new ArrayList<String>();
				newDealSummaryCloseReasonList.add("Customer Project Terminated");
				newDealSummaryCloseReasonList.add("Pricing");
				newDealSummaryCloseReasonList.add("Customer Relationship with Competitor");
				newDealSummaryCloseReasonList.add("Products Portfolio");

				oppor.viewAllTasksForActivitesOfOppor();
				List<String> activitesTaskListbeforeCloseLost = oppor.getAllActivitiesTasksInList();

				oppor.selectTabfirstAndThenClickItemToActionOn("Details", "Stage");

				oppor.selectStageFromDetailTabByName("Closed Lost");
				oppor.verifynewDealSummaryclosedReasonList(newDealSummaryCloseReasonList);

				oppor.selectClosedWonOrLostReasonByMoveSelection("Customer Project Terminated");

				oppor.saveOpporFromDetailsPage();

				oppor.verifyError_HitASnag_ExistAndCloseWhileFillingForm();

				oppor.createNewDealSummaryOffClosedLostByFillingForm("Joint Replacement", "Joint Replacement",
						"Arthrex", newOppName);
				commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_error_XPATH"),
						"error exist", "error does not exit");

				oppor.CancelAndCloseNewDealSummaryForm();

				oppor.createNewDealSummaryOffClosedLostByFillingForm("Joint Replacement", "MAKO", "Arthrex",
						newOppName);
				oppor.verifynewDealSummaryclosedReasonList(newDealSummaryCloseReasonList);

				// flag =
				accountAndTerr.verifyElementExist("SF_ASCSales_toastMessageForSuccess_XPATH",
						"new deal summary created");

				accountAndTerr.clickAppFromHeader("Opportunities");
				// String
				// currentStage=commonLib.getText("SF_ASCSales_oppor_getTextForItems_OnDetailPage_XPATH");
				String currentStage = oppor.searchOpportunityAndFoundStageName(newOppName);
				flag = accountAndTerr.verifyText("Closed Lost", currentStage);
				commonLib.softAssertThat(flag, "Stage is updated to closed Lost", "Stage is updated to closed Won");

				oppor.viewAllTasksForActivitesOfOppor();
				List<String> activitesTaskListAfterCloseLost = oppor.getAllActivitiesTasksInList();
				oppor.compare2List(activitesTaskListAfterCloseLost, activitesTaskListbeforeCloseLost);
				

				/*
				 * } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				 * "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				 * finally { commonLib.endTest(); softAssert.assertAll(); }
				 */

			}
		}
	}
	
	//@Test(priority = 2)
		public void Opportunities_227865_TC_07() throws Exception {
			String newOppName;
			 boolean flag=false;
			String testCaseID = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println("Test case ID value is " + testCaseID);
			String packageName = Opportunities.class.getPackage().toString();
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

				String proxyUser1 = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Proxy_User1"));

				if (Run_Status.equalsIgnoreCase("Yes")) {
					 try {
					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO,
							testCaseID + "Account Management: Test searching account, adding contact");

					login.loginToSFDC(workBook);

					accountAndTerr.proxyLoginUsingUser2(proxyUser1);
					// Margaret ASC Sales

					flag = accountAndTerr.clickAppFromHeader("Accounts");
					commonLib.softAssertThat(flag, "Accounts page opened", "Accounts page not opened");

					accountAndTerr.searchAccountByNameFromAccountPage("ASC OF TRINITY", " 235770");
					//accountAndTerr.clickbyjavascript("SF_ASCSales_account_divisionalAccounts_Tab_XPATH");
					oppor.clickOptionsTabForPartcularAccOrOpp("Divisional Account");

					oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();

					newOppName = oppor.createNewOpportunities("Preparing Proposal");// Customer Assesment
					
					accountAndTerr.clickAppFromHeader("Opportunities");
					oppor.searchOpportunityAndFoundStageName(newOppName);

					oppor.openFormToCreateDivisionalOpporFromOppPage();
					String newOppName2 = oppor.createDivisonalOpportunities("Customer Assesment");
					
					accountAndTerr.clickAppFromHeader("Opportunities");
					oppor.searchOpportunityAndFoundStageName(newOppName);
					
					oppor.clickOptionsTabForPartcularAccOrOpp("Divisional Opportunities");
					
					flag = accountAndTerr.verifyFieldExist("ProfileSearchResult_NameLink_XPATH", newOppName2);
					commonLib.softAssertThat(flag, "divisonal opp found under divisonal opp tab",
							"divisonal opp found under divisonal opp tab");
				
					
					  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
					  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
					  finally { commonLib.endTest(); softAssert.assertAll(); }
					 

				}
			}
		}
		
		// @Test(priority = 2)
		public void Opportunities_227897_TC_15() throws Exception {
			String newOppName;
			 boolean flag=false;
			String currentStage;
			String testCaseID = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println("Test case ID value is " + testCaseID);
			String packageName = Opportunities.class.getPackage().toString();
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

					login.loginToSFDC(workBook);
					
					accountAndTerr.proxyLoginUsingUser("Margaret ASC Finance", "User");					

					flag = accountAndTerr.clickAppFromHeader("Accounts");
					commonLib.softAssertThat(flag, "opportunities page opened", "opportunities page not opened");

					accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");

					oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();

					newOppName = oppor.createNewOpportunities("Customer Assesment");			

					flag = accountAndTerr.clickAppFromHeader("Opportunities");
					oppor.searchOpportunityAndFoundStageName(newOppName);

					oppor.markStageAsCurrent("Deal Data Aggregation" ,newOppName);

					 currentStage = oppor.makrkOpporStageAsCompleteAndVerifyStageName(newOppName);
					flag = accountAndTerr.verifyText("Submitted to Flex", currentStage);
					commonLib.softAssertThat(flag, "stage changed to as expected" + currentStage,
							"stage not changed to as expected" + currentStage);

					currentStage = oppor.makrkOpporStageAsCompleteAndVerifyStageName(newOppName);
					flag = accountAndTerr.verifyText("Pricing", currentStage);
					commonLib.softAssertThat(flag, "stage changed to as expected" + currentStage,
							"stage not changed to as expected" + currentStage);

					currentStage = oppor.makrkOpporStageAsCompleteAndVerifyStageName(newOppName);
					flag = accountAndTerr.verifyText("FRP", currentStage);
					commonLib.softAssertThat(flag, "stage changed to as expected" + currentStage,
							"stage not changed to as expected" + currentStage);

					currentStage = oppor.makrkOpporStageAsCompleteAndVerifyStageName(newOppName);
					flag = accountAndTerr.verifyText("Preparing Proposal", currentStage);
					commonLib.softAssertThat(flag, "stage changed to as expected" + currentStage,
							"stage not changed to as expected" + currentStage);

					 currentStage = oppor.makrkOpporStageAsCompleteAndVerifyStageName(newOppName);
					flag = accountAndTerr.verifyText("Proposal Presented", currentStage);
					commonLib.softAssertThat(flag, "stage changed to as expected" + currentStage,
							"stage not changed to as expected" + currentStage);

					 currentStage = oppor.makrkOpporStageAsCompleteAndVerifyStageName(newOppName);
					flag = accountAndTerr.verifyText("Preparing Legal Document", currentStage);
					commonLib.softAssertThat(flag, "stage changed to as expected" + currentStage,
							"stage not changed to as expected" + currentStage);

					 currentStage = oppor.makrkOpporStageAsCompleteAndVerifyStageName(newOppName);
					flag = accountAndTerr.verifyText("Legal Document Presented", currentStage);
					commonLib.softAssertThat(flag, "stage changed to as expected" + currentStage,
							"stage not changed to as expected" + currentStage);

					oppor.selectTabfirstAndThenClickItemToActionOn("Details", "Stage");
					oppor.selectStageFromDetailTabByName("Closed Lost");

					oppor.selectClosedWonOrLostReasonByMoveSelection("Customer Project Terminated");

					oppor.createNewDealSummaryOffClosedLostByFillingForm("Joint Replacement", "Mako", "Arthrex",
							newOppName);
					oppor.saveOpporFromDetailsPage();

					 currentStage = oppor.makrkOpporStageAsCompleteAndVerifyStageName(newOppName);
					flag = accountAndTerr.verifyText("Closed Lost", currentStage);
					commonLib.softAssertThat(flag, "stage changed to as expected" + currentStage,
							"stage not changed to as expected" + currentStage);

					/*
					 * } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
					 * "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
					 * finally { commonLib.endTest(); softAssert.assertAll(); }
					 */

				}
			}
		}
		
		//@Test(priority = 2)
		public void Opportunities_227898_TC_16() throws Exception {
			boolean flag = false;
			String currentStage;
			String newOppName;
			String testCaseID = new Object() {
			}.getClass().getEnclosingMethod().getName();
			System.out.println("Test case ID value is " + testCaseID);
			String packageName = Opportunities.class.getPackage().toString();
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

					login.loginToSFDC(workBook);

					accountAndTerr.proxyLoginUsingUser("Margaret ASC Pricing", "User");

					flag = accountAndTerr.clickAppFromHeader("Accounts");
					commonLib.softAssertThat(flag, "opportunities page opened", "opportunities page not opened");

					accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");

					oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();

					newOppName = oppor.createNewOpportunities("Customer Assesment");

					flag = accountAndTerr.clickAppFromHeader("Opportunities");
					oppor.searchOpportunityAndFoundStageName(newOppName);

					oppor.markStageAsCurrent("Deal Data Aggregation", newOppName);

					currentStage = oppor.makrkOpporStageAsCompleteAndVerifyStageName(newOppName);
					flag = accountAndTerr.verifyText("Submitted to Flex", currentStage);
					commonLib.softAssertThat(flag, "stage changed to as expected" + currentStage,
							"stage not changed to as expected" + currentStage);

					oppor.markStageAsComplete("Submitted to Flex", newOppName);
					
					oppor.markStageAsCurrent("Pricing", newOppName);
					flag = oppor.verifyroleErrorForStageChange();
					commonLib.softAssertThat(flag, "stage not chaged due to user role access",
							"stage not chaged due to user role access");

					oppor.markStageAsCurrent("FRP", newOppName);
					flag = oppor.verifyroleErrorForStageChange();
					commonLib.softAssertThat(flag, "stage not chaged due to user role access",
							"stage not chaged due to user role access");

					oppor.markStageAsCurrent("Preparing Proposal", newOppName);
					flag = oppor.verifyroleErrorForStageChange();
					commonLib.softAssertThat(flag, "stage not chaged due to user role access",
							"stage not chaged due to user role access");

					oppor.markStageAsCurrent("Proposal Presented", newOppName);
					flag = oppor.verifyroleErrorForStageChange();
					commonLib.softAssertThat(flag, "stage not chaged due to user role access",
							"stage not chaged due to user role access");

					oppor.markStageAsCurrent("Preparing Legal Document", newOppName);
					flag = oppor.verifyroleErrorForStageChange();
					commonLib.softAssertThat(flag, "stage not chaged due to user role access",
							"stage not chaged due to user role access");

					oppor.markStageAsCurrent("Legal Document Presented", newOppName);
					flag = oppor.verifyroleErrorForStageChange();
					commonLib.softAssertThat(flag, "stage not chaged due to user role access",
							"stage not chaged due to user role access");

					oppor.selectTabfirstAndThenClickItemToActionOn("Details", "Stage");
					oppor.selectStageFromDetailTabByName("Closed Lost");

					oppor.selectClosedWonOrLostReasonByMoveSelection("Customer Project Terminated");

					sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");

					oppor.verifyError_HitASnag_ExistAndCloseWhileFillingForm();

					/*
					 * } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
					 * "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
					 * finally { commonLib.endTest(); softAssert.assertAll(); }
					 */

				}
			}
		}
		
		@Test(priority = 2)
				public void Opportunities_227899_TC_17() throws Exception {
					boolean flag = false;
					String currentStage;
					String newOppName;
					String testCaseID = new Object() {
					}.getClass().getEnclosingMethod().getName();
					System.out.println("Test case ID value is " + testCaseID);
					String packageName = Opportunities.class.getPackage().toString();
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

							login.loginToSFDC(workBook);

							//accountAndTerr.proxyLoginUsingUser("Margaret ASC Pricing", "User");
							accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");

							flag = accountAndTerr.clickAppFromHeader("Accounts");
							commonLib.softAssertThat(flag, "opportunities page opened", "opportunities page not opened");

							accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");
							 newOppName="trinity_oppor000";
						
							  oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();
							  
							  newOppName = oppor.createNewOpportunities("Customer Assesment");
							  
							  flag = accountAndTerr.clickAppFromHeader("Opportunities");
							  oppor.searchOpportunityAndFoundStageName(newOppName);
							  
							  oppor.markStageAsCurrent("Deal Data Aggregation", newOppName);
							  
							  currentStage= oppor.markStageAsCurrent("Submitted to Flex", newOppName); flag
							  = accountAndTerr.verifyText("Submitted to Flex", currentStage); 
							  commonLib.softAssertThat(flag,"stage changed to as expected" + currentStage, "stage not changed to as expected" );
							  
							  oppor.markStageAsCurrent("Pricing", newOppName);
							  
							  oppor.markStageAsCurrent("FRP", newOppName);
							  
							  oppor.markStageAsCurrent("Preparing Proposal", newOppName);
							  
							  oppor.markStageAsCurrent("Proposal Presented", newOppName);
							  
							  
							  oppor.markStageAsCurrent("Preparing Legal Document", newOppName);
							  
							  
							  oppor.markStageAsCurrent("Legal Document Presented", newOppName);
							  
							  
							  oppor.selectTabfirstAndThenClickItemToActionOn("Details", "Stage");
							  oppor.selectStageFromDetailTabByName("Closed Lost");
							  
							  oppor.
							  selectClosedWonOrLostReasonByMoveSelection("Customer Project Terminated");
							  
							  oppor.createNewDealSummaryOffClosedLostByFillingForm("Joint Replacement",
							  "Mako", "Arthrex", newOppName); oppor.saveOpporFromDetailsPage();
							  
							  currentStage = oppor.openOppPageSearchOpportunityAndFoundStage(newOppName);
							  flag = accountAndTerr.verifyText("Closed Lost", currentStage); 
							  commonLib.softAssertThat(flag,
							  "stage changed to as expected" + currentStage,
							  "stage not changed to as expected" + currentStage);
							 
							
							
							/*
							 * } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
							 * "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
							 * finally { commonLib.endTest(); softAssert.assertAll(); }
							 */

						}
					}
				}
		

}
