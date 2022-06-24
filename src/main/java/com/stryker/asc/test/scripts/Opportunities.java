package com.stryker.asc.test.scripts;

import java.util.ArrayList;
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

public class Opportunities extends SfdcDriver {

	private com.stryker.salesforceLibrary.SfdcLibrary sfdcLib;

	// page declaration
	LoginSFDC login;
	AccountManagementSamplePage account;
	AccountAndTerritoriesPage accountAndTerr = new AccountAndTerritoriesPage(commonLib);
	OpportunitiesPage oppor = new OpportunitiesPage(commonLib);
	String workSheet = "Opportunities";
	boolean exist = false;
	public String newOppName;
	Quote_ProposalSetupPage quote = new Quote_ProposalSetupPage(commonLib);

	public Opportunities() {

		this.sfdcLib = new SfdcLibrary(commonLib);
		login = new LoginSFDC(commonLib);
		account = new AccountManagementSamplePage(commonLib);
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test(priority = 1)
	public void Opportunities_227841_TC_01() throws Exception {	
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
			if (Run_Status.equalsIgnoreCase("Yes")) {
				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO,
							testCaseID + "Account Management: Test searching account, adding contact");
					login.loginToSFDC(workBook, 2, 2);
					accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");
					flag = accountAndTerr.clickAppFromHeader("Accounts");
					accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");
					oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();
					newOppName = oppor.createNewOpportunities("Customer Assesment");
					oppor.clickOptionsTabForPartcularAccOrOpp("Details");
					
					commonLib.clickbyjavascript("SF_ASCSales_Oppty_Link_XPATH");
					Thread.sleep(2000);
					oppor.clickOnDetailsTab();
					
					List<String> fieldNames= oppor.fetchFieldNamesUnderOpptyScreen();
					System.out.println(fieldNames);
					commonLib.assertThat(!fieldNames.equals("Opportunity Currency"), "Opportunity Currency field is not availble under Details Screen", "Opportunity Currency field is availble under Details Screen");
					commonLib.assertThat(!fieldNames.equals("IDN Number"), "IDN Number field is not availble under Details Screen", "IDN Number field is availble under Details Screen");
					commonLib.assertThat(!fieldNames.equals("IDN Affiliation"), "IDN Affiliation  field is not availble under Details Screen", "IDN Affiliation field is availble under Details Screen");
					commonLib.assertThat(!fieldNames.equals("Total Annual spend"), "Total Annual spend field is not availble under Details Screen", "Total Annual spend  field is availble under Details Screen");
					commonLib.assertThat(!fieldNames.equals("Service Sell Price"), "Service Sell Price field is not availble under Details Screen", "Service Sell Price field is availble under Details Screen");
					commonLib.assertThat(!fieldNames.equals("Number of Rooms"), "Number of Rooms field is not availble under Details Screen", "Number of Rooms field is availble under Details Screen");
					commonLib.assertThat(!fieldNames.equals("Type"), "Type field is not availble under Details Screen", "Type field is availble under Details Screen");
					commonLib.assertThat(!fieldNames.equals("Product Quote ID"), "Product Quote ID  field is not availble under Details Screen", "Product Quote ID field is availble under Details Screen");
					commonLib.assertThat(!fieldNames.equals("Product Quote Process Name"), "Product Quote Process Name field is not availble under Details Screen", "Product Quote Process Name field is availble under Details Screen");
				
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

	@Test(priority = 2)
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

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {

					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO,
							testCaseID + "Account Management: Test searching account, adding contact");

					login.loginToSFDC(workBook, 2, 2);

					accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

					flag = accountAndTerr.clickAppFromHeader("Accounts");
					// commonLib.softAssertThat(flag, "Accounts page opened", "Accounts page not
					// opened");

					accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");

					oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();

					newOppName = oppor.createNewOpportunities("Customer Assesment");
					Thread.sleep(5000);

					flag = accountAndTerr.clickAppFromHeader("Opportunities");
					oppor.selectOpportunityByNameOnOpporPage(newOppName);

					oppor.verifyTasksForIntialStageOfOppor();
					Thread.sleep(1000);

					oppor.openOpporFromLinkOnTaskPage();

					oppor.addNewOpporTeamMember();

					oppor.markStageAsCurrent("Deal Data Aggregation", newOppName);
					oppor.clickOptionsTabForPartcularAccOrOpp("Activity");
					Thread.sleep(1000);
					oppor.viewAllTasksForActivitesOfOppor();
					Thread.sleep(1000);
					oppor.clickactivityLinkOfOppor("Quotes needed within 1 week");
					
					commonLib.refreshPage();
					flag = accountAndTerr.verifyElementExist("SF_ASCSales_Account_oppor_dueDateDetail_XPATH",
							"due date exist");

					oppor.openOpporFromLinkOnTaskPage();

					oppor.markStageAsCurrent("Preparing Proposal", newOppName);
					oppor.clickOptionsTabForPartcularAccOrOpp("Activity");
					oppor.viewAllTasksForActivitesOfOppor();
					oppor.clickactivityLinkOfOppor(
							"Review with internal teams before sending to customer ( 6. Preparing Proposal )");
				
					commonLib.refreshPage();
					flag = accountAndTerr.verifyElementExist("SF_ASCSales_Account_oppor_dueDateDetail_XPATH",
							"due date exist");

					oppor.markStageAsCurrent("Preparing Legal Document", newOppName);
					oppor.clickOptionsTabForPartcularAccOrOpp("Activity");
					oppor.viewAllTasksForActivitesOfOppor();
					oppor.clickactivityLinkOfOppor(
							"Review with internal teams before sending to customer ( 8. Preparing Legal Document )");
					commonLib.refreshPage();
					flag = accountAndTerr.verifyElementExist("SF_ASCSales_Account_oppor_dueDateDetail_XPATH",
							"due date exist");

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

	@Test(priority = 2)
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

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {

					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO,
							testCaseID + "Account Management: Test searching account, adding contact");

					login.loginToSFDC(workBook, 2, 2);

					accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

					flag = accountAndTerr.clickAppFromHeader("Accounts");

					accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");

					oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();

					newOppName = oppor.createNewOpportunities("Customer Assesment");

					flag = accountAndTerr.clickAppFromHeader("Opportunities");
			
					oppor.selectOpportunityByNameOnOpporPage(newOppName);
					oppor.viewAllForOpporLeftSideRelatedListItem("Opportunity Team");

					// add steps
					oppor.verifyUsers();

					accountAndTerr.logOutFromCurrentUser();

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

	@Test(priority = 2)
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

				try {

					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO,
							testCaseID + "Account Management: Test searching account, adding contact");

					login.loginToSFDC(workBook, 2, 2);

					flag = accountAndTerr.clickAppFromHeader("Accounts");
				
					accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");

					oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();

					newOppName = oppor.createNewOpportunities("Preparing Proposal");

					flag = accountAndTerr.clickAppFromHeader("Opportunities");

					oppor.selectOpportunityByNameOnOpporPage(newOppName);

					List<String> activitesTaskListbeforeCloseWon = new ArrayList<String>();
					activitesTaskListbeforeCloseWon.add("Owners saw financial value in program");
					activitesTaskListbeforeCloseWon.add("Mako Clinical Champion");
					activitesTaskListbeforeCloseWon.add("Favorable Implant Pricing");
					activitesTaskListbeforeCloseWon.add("Value of Comprehensive Portfolio");

					oppor.selectTabfirstAndThenClickItemToActionOn("Details", "Stage");
					oppor.selectStageFromDetailTabByName("Closed Won");

					oppor.verifynewDealSummaryclosedReasonList(activitesTaskListbeforeCloseWon);

					oppor.selectClosedWonOrLostReasonByMoveSelection("Owners saw financial value in program");

					oppor.saveOpporFromDetailsPage();

					oppor.verifyError_HitASnag_ExistAndCloseWhileFillingForm();

					oppor.createNewDealSummaryOfClosedWonByFillingForm("Bone Cement", "Bone Cement", "Bone Cement",
							newOppName);
					flag = oppor.newDealSummaryError();
					commonLib.softAssertThat(flag, "New deal summary form fill error exist",
							"New deal summary form fill error does not exit");

					oppor.CancelAndCloseNewDealSummaryForm();

					oppor.createNewDealSummaryOfClosedWonByFillingForm("Bone Cement", "Trauma", "CMF", newOppName);
					Thread.sleep(2000);
					commonLib.click("SF_ASCSales_generalSave_nameSaveEdit_XPATH");
					Thread.sleep(10000);

					accountAndTerr.clickAppFromHeader("Opportunities");
					String currentStage = oppor.searchOpportunityAndFoundStageName(newOppName);
					flag = accountAndTerr.verifyText("Closed Won", currentStage);
					commonLib.softAssertThat(flag, "Stage is updated to closed Won", "Stage is updated to closed Won");

					oppor.viewAllTasksForActivitesOfOppor();

					flag = commonLib.verifyPresence_Dynamic_Xpath("SF_ASCSales_oppor__activityLink_general_XPATH",
							"Assign tasks to internal team (shipping/install timelines) and implementation team");
					commonLib.softAssertThat(flag, "Assign task activity created", "Assign task activity not created");

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

	@Test(priority = 2)
	public void Opportunities_227861_TC_06() throws Exception {
		String newOppName;
		boolean flag = false;
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

					login.loginToSFDC(workBook, 2, 2);

					flag = accountAndTerr.clickAppFromHeader("Accounts");

					accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");

					oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();

					newOppName = oppor.createNewOpportunities("Preparing Proposal");

					flag = accountAndTerr.clickAppFromHeader("Opportunities");
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

					oppor.createNewDealSummaryOffClosedLostByFillingForm("Bone Cement", "Bone Cement", "Arthrex",
							newOppName);
					commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_error_XPATH"),
							"error exist", "error does not exit");

					oppor.CancelAndCloseNewDealSummaryForm();

					oppor.createNewDealSummaryOffClosedLostByFillingForm("Bone Cement", "Trauma", "Arthrex",
							newOppName);

					commonLib.click("SF_ASCSales_generalSave_nameSaveEdit_XPATH");
					Thread.sleep(10000);

					accountAndTerr.clickAppFromHeader("Opportunities");
					String currentStage = oppor.searchOpportunityAndFoundStageName(newOppName);
					flag = accountAndTerr.verifyText("Closed Lost", currentStage);
					commonLib.softAssertThat(flag, "Stage is updated to closed Lost", "Stage is updated to closed Won");

					oppor.viewAllTasksForActivitesOfOppor();
					List<String> activitesTaskListAfterCloseLost = oppor.getAllActivitiesTasksInList();
					oppor.compare2List(activitesTaskListAfterCloseLost, activitesTaskListbeforeCloseLost);

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

	@Test(priority = 2)
	public void Opportunities_227865_TC_07() throws Exception {
		String newOppName;
		boolean flag = false;
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

					login.loginToSFDC(workBook, 2, 2);

					flag = accountAndTerr.clickAppFromHeader("Accounts");

					accountAndTerr
							.searchAccountByNameFromAccountPage("COMPREHENSIVE OUTPATIENT JOINT AND SPINE INSTITUTE");
					oppor.clickOptionsTabForPartcularAccOrOpp("Divisional Accounts");

					oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();

					newOppName = oppor.createNewOpportunities("Preparing Proposal");// Customer Assessment

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

	@Test(priority = 2)
	public void Opportunities_227897_TC_15() throws Exception {
		String newOppName;
		boolean flag = false;
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

			if (Run_Status.equalsIgnoreCase("Yes")) {
				try {

					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO,
							testCaseID + "Account Management: Test searching account, adding contact");

					login.loginToSFDC(workBook, 3, 3);

					flag = accountAndTerr.clickAppFromHeader("Accounts");
					
					accountAndTerr.searchAccountByNameFromAccountPage("Test_Automation_Finance_Account");

					oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();

					newOppName = oppor.createNewOpportunities("Customer Assesment");

					flag = accountAndTerr.clickAppFromHeader("Opportunities");
					oppor.searchOpportunityAndFoundStageName(newOppName);

					oppor.markStageAsCurrent("Deal Data Aggregation", newOppName);

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

					oppor.createNewDealSummaryOffClosedLostByFillingForm("Bone Cement", "Trauma", "Arthrex",
							newOppName);
					oppor.saveOpporFromDetailsPage();

					currentStage = oppor.makrkOpporStageAsCompleteAndVerifyStageName(newOppName);
					flag = accountAndTerr.verifyText("Closed Lost", currentStage);
					commonLib.softAssertThat(flag, "stage changed to as expected" + currentStage,
							"stage not changed to as expected" + currentStage);

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

	@Test(priority = 2)
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

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {

					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO,
							testCaseID + "Account Management: Test searching account, adding contact");

					login.loginToSFDC(workBook, 4, 4);

					flag = accountAndTerr.clickAppFromHeader("Opportunities");
					newOppName = oppor.selectOpportunityByNameOnOpporPage("Test_Pricing_Opportunity");

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

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {

					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO,
							testCaseID + "Account Management: Test searching account, adding contact");

					login.loginToSFDC(workBook, 2, 2);

					flag = accountAndTerr.clickAppFromHeader("Accounts");
					// commonLib.softAssertThat(flag, "opportunities page opened", "opportunities
					// page not opened");

					accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");
					newOppName = "trinity_oppor000";

					oppor.opeNewOrAddFromOpportunityRelatedListMenuItems();

					newOppName = oppor.createNewOpportunities("Customer Assesment");

					flag = accountAndTerr.clickAppFromHeader("Opportunities");
					oppor.searchOpportunityAndFoundStageName(newOppName);

					oppor.markStageAsCurrent("Deal Data Aggregation", newOppName);

					currentStage = oppor.markStageAsCurrent("Submitted to Flex", newOppName);
					flag = accountAndTerr.verifyText("Submitted to Flex", currentStage);
					commonLib.softAssertThat(flag, "stage changed to as expected" + currentStage,
							"stage not changed to as expected");

					oppor.markStageAsCurrent("Pricing", newOppName);

					oppor.markStageAsCurrent("FRP", newOppName);

					oppor.markStageAsCurrent("Preparing Proposal", newOppName);

					oppor.markStageAsCurrent("Proposal Presented", newOppName);

					oppor.markStageAsCurrent("Preparing Legal Document", newOppName);

					oppor.markStageAsCurrent("Legal Document Presented", newOppName);

					oppor.selectTabfirstAndThenClickItemToActionOn("Details", "Stage");
					oppor.selectStageFromDetailTabByName("Closed Lost");

					oppor.selectClosedWonOrLostReasonByMoveSelection("Customer Project Terminated");

					oppor.createNewDealSummaryOffClosedLostByFillingForm("Bone Cement", "Trauma", "Arthrex",
							newOppName);
					oppor.saveOpporFromDetailsPage();

					currentStage = oppor.openOppPageSearchOpportunityAndFoundStage(newOppName);
					flag = accountAndTerr.verifyText("Closed Lost", currentStage);
					commonLib.softAssertThat(flag, "stage changed to as expected" + currentStage,
							"stage not changed to as expected" + currentStage);

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

	@Test(priority = 2)
	public void Opportunities_227896_TC_10() throws Exception {
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

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {

					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO,
							testCaseID + "Account Management: Test searching account, adding contact");

					login.loginToSFDC(workBook, 3, 3);

					flag = accountAndTerr.clickAppFromHeader("Accounts");
					// commonLib.softAssertThat(flag, "opportunities page opened", "opportunities
					// page not opened");

					accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");

					newOppName = oppor.createNewOpportunities("Customer Assesment");

					flag = accountAndTerr.clickAppFromHeader("Opportunities");
					oppor.searchOpportunityAndFoundStageName(newOppName);

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
	public void Opportunities_262996_TC_10() throws Exception {
//		boolean flag = false;
//		String currentStage;
//		String newOppName;
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
			String accountName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Account_Name"));
			String spendType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Spend_Type"));
			String category = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Category"));
			String constructType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Type"));
			String constructName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Name"));
			String price = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Procedure_Price"));
			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID
							+ "!W1! - 202841 - Workflow: Opportunity Closed CPQ Requirements - Tech - Finance");
					login.loginToSFDC(workBook, 3, 3);
					accountAndTerr.clickAppFromHeader("Accounts");
					account.clickOnAccountNameLink(accountName);
					accountAndTerr.clickAppFromHeader("Opportunities");
					newOppName = oppor.createNewOpportunityWithAccountName(accountName, "Customer Assesment");
					quote.clickOnCreateQuote();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("TestDescription");
					quote.clickOnCPQQuoteOperationsButton("Save");
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

					quote.clickQuoteTransactionLink("Other Info");
					String quotenumber = commonLib.getAttribute("SF_QuoteNumber_Value_XPATH", "value");
					quote.verifyPresenceOfOperationButton("Return to Opportunity");
					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");
					Thread.sleep(5000);
					quote.clickOnQuoteNumber(quotenumber);
					commonLib.softAssertThat(commonLib.waitForVisibilityOf("SF_Quote_Header_Label_XPATH"),
							"Navigated successfully to Quote Details screen", "Not navigated to Quote Details screen");

					String bu = quote.fetchQuoteDetailLabelValue("BUs part of the deal");
					commonLib.softAssertThat(bu.contains("MAKO CAPITAL"),
							"BUs displayed in CPQ Line Item Grid are copied to the BU part of the Deal",
							"BUs displayed in CPQ Line Item Grid are not copied to the BU part of the Deal");
					commonLib.assertThat(quote.verifyMakoSalesCheckboxStatus().equalsIgnoreCase("true"),
							"Includes Mako checkbox is checked", "Includes Mako checkbox is not checked");

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
	public void Opportunities_263383_TC_11() throws Exception {
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
			String accountName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Account_Name"));
			String spendType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Spend_Type"));
			String category = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Category"));
			String constructType = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Type"));
			String constructName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Construct_Name"));
			String price = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Procedure_Price"));
			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO,
							testCaseID + "!W1! - 202841 - Workflow: Opportunity Closed CPQ Requirements - Sales RM");
					login.loginToSFDC(workBook, 2, 2);
					accountAndTerr.clickAppFromHeader("Accounts");
					account.clickOnAccountNameLink(accountName);
					accountAndTerr.clickAppFromHeader("Opportunities");
					newOppName = oppor.createNewOpportunityWithAccountName(accountName, "Customer Assesment");
					quote.clickOnCreateQuote();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("TestDescription");
					quote.clickOnCPQQuoteOperationsButton("Save");
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

					quote.clickQuoteTransactionLink("Other Info");
					String quotenumber = commonLib.getAttribute("SF_QuoteNumber_Value_XPATH", "value");
					quote.verifyPresenceOfOperationButton("Return to Opportunity");
					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");
					Thread.sleep(5000);
					quote.clickOnQuoteNumber(quotenumber);
					commonLib.softAssertThat(commonLib.waitForVisibilityOf("SF_Quote_Header_Label_XPATH"),
							"Navigated successfully to Quote Details screen", "Not navigated to Quote Details screen");

					String bu = quote.fetchQuoteDetailLabelValue("BUs part of the deal");
					commonLib.softAssertThat(bu.contains("MAKO CAPITAL"),
							"BUs displayed in CPQ Line Item Grid are copied to the BU part of the Deal",
							"BUs displayed in CPQ Line Item Grid are not copied to the BU part of the Deal");
					commonLib.assertThat(quote.verifyMakoSalesCheckboxStatus().equalsIgnoreCase("true"),
							"Includes Mako checkbox is checked", "Includes Mako checkbox is not checked");

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
