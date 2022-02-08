package com.stryker.asc.test.scripts;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.stryker.asc.pages.AccountAndTerritoriesPage;
import com.stryker.asc.pages.AccountManagementSamplePage;
import com.stryker.asc.pages.OpportunitiesPage;
import com.stryker.common.modules.LoginSFDC;
import com.stryker.salesforceLibrary.SfdcLibrary;

public class AccountAndTerritories extends com.stryker.driver.SfdcDriver {

	private com.stryker.salesforceLibrary.SfdcLibrary sfdcLib;

	// page declaration
	LoginSFDC login;
	com.stryker.asc.pages.AccountManagementSamplePage account;
	AccountAndTerritoriesPage accountAndTerr = new AccountAndTerritoriesPage(commonLib);
	OpportunitiesPage oppor = new OpportunitiesPage(commonLib);
	String workSheet = "scripts";
	boolean exist = false;
public String newOppName;
	public AccountAndTerritories() {

		this.sfdcLib = new SfdcLibrary(commonLib);
		login = new LoginSFDC(commonLib);
		account = new AccountManagementSamplePage(commonLib);

	}

	//@Test(priority = 0)
	public void AccountAndTerritories_227835_TC_01() throws Exception {

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

				 try {

				commonLib.startTest(testCaseID);

				commonLib.log(LogStatus.INFO,
						testCaseID + "Account Management: Test searching account, adding contact");

				login.loginToSFDC(workBook);

				accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");

				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

				accountAndTerr.clickAppFromHeader("Accounts");
				commonLib.refreshPage();

				accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");

			accountAndTerr.accountDetailsPageVerification();

			commonLib.scroll_view("SF_ASCSales_account_opportunityProduct_XPATH");
				
				commonLib.scroll(0, 600);
				commonLib.scroll(0, -600);
				commonLib.scroll(0, 600);

				accountAndTerr.verifyElementExist("SF_ASCSales_account_contact_XPATH",
						"contact related list");
				
				accountAndTerr.verifyElementExist("SF_ASCSales_account_ManagementCompany_XPATH",
						" management company related list");
				accountAndTerr.verifyElementExist("SF_ASCSales_account_AddressInfo_XPATH",
						" Address info related list");				
				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 
			}
		}
	}

	 //@Test(priority = 1)
	public void AccountAndTerritories_227836_TC_02() throws Exception {

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

			 try {

				commonLib.startTest(testCaseID);

				commonLib.log(LogStatus.INFO,
						testCaseID + "Account Management: Test searching account, adding contact");

				login.loginToSFDC(workBook);
				accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");

				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

				accountAndTerr.clickAppFromHeader("Accounts");
				commonLib.refreshPage();

				//accountAndTerr.click("SF_ASCSales_account_searchView_XPATH");
				//accountAndTerr.click("SF_ASCSales_account_myAccount_XPATH");
				
				accountAndTerr.searchAccountByNameFromAccountPage("ADVENTHEALTH NORTH PINELLAS");				
				Thread.sleep(7000);
				commonLib.scroll(0, 600);
				Thread.sleep(5000);
				
				commonLib.softAssertThat(
						accountAndTerr.verifyElementExistByWait("SF_ASCSales_account_myAccount_notesAndAttachment_XPATH", null),
						" exist", " not exist");
	
				sfdcLib.click("SF_ASCSales_account_myAccount_notesAndAttachment_menuItems_XPATH");
				sfdcLib.click("SF_ASCSales_account_myAccount_notesAndAttachment_upload_XPATH");
				Thread.sleep(5000);

				String filepath = System.getProperty("user.dir") + "\\images\\notes.txt";

				Thread.sleep(3000);
				accountAndTerr.UploadFile(filepath);				
				Thread.sleep(5000);
//commonLib..softAssertThat(accountAndTerr.verifytext("SF_ASCSales_account_myAccount_fileUploadmsg_XPATH", "1 of 1 file uploaded ") , " exist", " not exist");
				sfdcLib.click("SF_ASCSales_account_myAccount_fileUploadDone_XPATH");

				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_spanTagGeneral_Title_XPATH", "ASC Sales");

				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 
			}
		}
	}

	 //@Test(priority = 2)
	public void AccountAndTerritories_227837_TC_03() throws Exception {

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

				 try {

				commonLib.startTest(testCaseID);

				commonLib.log(LogStatus.INFO,
						testCaseID + "Account Management: Test searching account, adding contact");

				login.loginToSFDC(workBook);

				accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");

				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

				accountAndTerr.clickAppFromHeader("Accounts");
				commonLib.refreshPage();

				accountAndTerr.searchAccountByNameFromAccountPage("Gemini ASC");			
				commonLib.refreshPage();
				Thread.sleep(8000);
				commonLib.scroll(0, 1100);
				Thread.sleep(4000);

				commonLib.softAssertThat(
						accountAndTerr.verifyElementExistByWait("SF_ASCSales_account_myAccount_ascOwner_XPATH", null), " exist",
						" not exist");
				
				accountAndTerr.openNewAscOwnerForm();
				
				boolean flag=accountAndTerr.addNewASCOwner();
				commonLib.softAssertThat(flag," exist", " not exist");

				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 
			}
		}
	}

	 //@Test(priority = 4)
	public void AccountAndTerritories_227838_TC_04() throws Exception {

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

				try {

					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO,
							testCaseID + "Account Management: Test searching account, adding contact");

					login.loginToSFDC(workBook);

					accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");

					//accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");
					
					accountAndTerr.clickAppFromHeader("Accounts");
					commonLib.refreshPage();

					accountAndTerr.searchAccountByNameFromAccountPage("CRYSTAL CLINIC PLASTIC SURGEONS");	

					commonLib.scroll_view("SF_ASCSales_account_opportunityProduct_XPATH");

					// accountAndTerr.verifyExistance("SF_ASCSales_account_opportunityProduct_XPATH");

					accountAndTerr.verifyRelatedListForAccount();
					accountAndTerr.clickOptionsTabForPartcularAccOrOpp("Divisional Accounts");;

					accountAndTerr.verifyElementExistWithSoftAssert(
									"SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH", "divisonal account details");

					boolean flag=accountAndTerr.openDivisionalAccFromDivAccHeadPageAndVerify();
					commonLib.softAssertThat(flag, " divisional acc page found", "divisional acc page not found");

					accountAndTerr.switchTodesiredTabFromOpenBrowserTab(1);

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

	 //@Test(priority = 5)
	public void AccountAndTerritories_227839_TC_05() throws Exception {

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

				 try {

				commonLib.startTest(testCaseID);

				commonLib.log(LogStatus.INFO,
						testCaseID + "Account Management: Test searching account, adding contact");

				login.loginToSFDC(workBook);				

				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");				
				accountAndTerr.clickAppFromHeader("Accounts");
				commonLib.refreshPage();

				accountAndTerr.searchAccountByNameFromAccountPage("CRYSTAL CLINIC PLASTIC SURGEONS");

				accountAndTerr.createNewManagemenTypeAccount();

				accountAndTerr.verifyManagementTypeAccForAScOwnerAndAccount();
				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 

			}
		}
	}

	 //@Test(priority = 5)
	public void AccountAndTerritories_227847_TC_06() throws Exception {

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

				try {

				commonLib.startTest(testCaseID);

				commonLib.log(LogStatus.INFO,
						testCaseID + "Account Management: Test searching account, adding contact");

				login.loginToSFDC(workBook);

				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");				

				accountAndTerr.clickAppFromHeader("Accounts");
				commonLib.refreshPage();
				
				String accNumCreated = accountAndTerr.createNewEnterpriseAccByAdmin();

				accountAndTerr.clickAppFromHeader("Home");				
				
				boolean flag=accountAndTerr.openRegionAndRelatdTab("Rocky Mountain");				
				commonLib.softAssertThat(flag, "zipcode page found", "zip code page not found");
				
				// exist=accountAndTerr.verifyExistance("SF_ASCSales_Region_presenceof_TerrMemAssig_XPATH");
				accountAndTerr.verifyRegionDetailPage();

				
				accountAndTerr.verifyActiveTerrMemberForRegion();

				accountAndTerr.clickAppFromHeader("Accounts");
				accountAndTerr.searchAccountFromopSearchAndOpen(accNumCreated);
				
				
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_accountDeatilPage_AccountTeamLink_XPATH",
						"Account Team List");
				sfdcLib.click("SF_ASCSales_accountDeatilPage_AccountTeamLink_XPATH");

				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 

			}
		}
	}

	 //@Test(priority = 7)
	public void AccountAndTerritories_227848_TC_07() throws Exception {
        boolean flag;
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

				 try {

				commonLib.startTest(testCaseID);

				commonLib.log(LogStatus.INFO,
						testCaseID + "Account Management: Test searching account, adding contact");

				login.loginToSFDC(workBook);

				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");
				
				flag=accountAndTerr.openRegionAndRelatdTab("Desert");
				commonLib.softAssertThat(flag, "zipcode page found", "zip code page not found");
								
				accountAndTerr.addZipCode("59001");				
				accountAndTerr.verifyElementExistWithSoftAssert("accountAndTerr.", " expected error ");
				accountAndTerr.closePinCodeAddError();
				
				int pincode = accountAndTerr.genRandomNumber();
				String pin1 = String.valueOf(pincode);
				
				accountAndTerr.editPinCodeAddForm(pin1);
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_toastMessageForSuccess_XPATH",
						"success msg for zip added");

				accountAndTerr.addZipCode(pin1);
				accountAndTerr.verifyElementExistWithSoftAssert("accountAndTerr.", " expected error ");
				sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_closeError_XPATH");

				
				pincode = accountAndTerr.genRandomNumber();
				String pin2 = String.valueOf(pincode);
				pin2=pin2+"-4914";
				accountAndTerr.editPinCodeAddForm(pin1);
			
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_toastMessageForSuccess_XPATH",
						"success msg for zip added");
				
				
				accountAndTerr.createEnterpriseAccountUsingPinCode(pin1);

				accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");
				
				accountAndTerr.searchAccountFromopSearchAndOpen(pin1);

				boolean exist = accountAndTerr.verifyAccountExistAfterSearchFromTopSearch(pin1);
				commonLib.softAssertThat(exist, "account with newly added zipcode exist",
						"account with newly added zipcode exist");

				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 

			}
		}
	}

	 @Test(priority = 8)
	public void AccountAndTerritories_227849_TC_08() throws Exception {

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

				 try {

				commonLib.startTest(testCaseID);

				commonLib.log(LogStatus.INFO,
						testCaseID + "Account Management: Test searching account, adding contact");

				login.loginToSFDC(workBook);

				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");
				
				accountAndTerr.openRegionAndRelatdTab("Desert");
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_Region_presenceof_zipcode_XPATH",
						"Zip Code Section");
				//String count = commonLib.getText("SF_ASCSales_Region_terrMemCount_XPATH");

				// accountAndTerr.clickbyjavascript("SF_ASCSales_Region_TerrMemAssig_viewAll_XPATH");
				accountAndTerr.addTerrMemberInRegion("Margaret ASC Pricing", "Read");

				// accountAndTerr.verifytext("SF_ASCSales_Region_terrMemCount_XPATH", count+1);
				accountAndTerr.addTerrMemberInRegion("Robert Montgomery", "Edit");

				// accountAndTerr.verifytext("SF_ASCSales_Region_terrMemCount_XPATH", count+2);

				accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Pricing");

				//accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

				commonLib.refreshPage();

				accountAndTerr.searchAccountFromopSearchAndOpen("87213");
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_accountNameEditLink_XPATH",
						"succes message");

				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 

			}
		}
	}

	 //@Test(priority = 9)
	public void AccountAndTerritories_227855_TC_09() throws Exception {
		boolean result;
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

				 try {

					 commonLib.startTest(testCaseID);

						commonLib.log(LogStatus.INFO,
								testCaseID + "Account Management: Test searching account, adding contact");

						login.loginToSFDC(workBook);
						accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");

						boolean flag = accountAndTerr.clickAppFromHeader("Accounts");
						commonLib.softAssertThat(flag, "opportunities page opened", "opportunities page not opened");

						accountAndTerr.searchAccountByNameFromAccountPage("CRYSTAL CLINIC PLASTIC SURGEONS");

						accountAndTerr.clickOptionsTabForPartcularAccOrOpp("Divisional Accounts");

						accountAndTerr.verifyElementExistWithSoftAssert(
								"SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH",
								" divisional account name");

						accountAndTerr.openDivisonalAccountAndVerifyCorrectPage();

						accountAndTerr.switchTodesiredTabFromOpenBrowserTab(1);

						String nameUsed = accountAndTerr.createNewContactForAccount();
						result = accountAndTerr.verify_element_spanWithText_Exist(nameUsed);

						commonLib.softAssertThat(result, "created contact exist :" + nameUsed,
								"created contact not  exist :" + nameUsed);
						accountAndTerr.clickOptionsTabForPartcularAccOrOpp("Divisional Contacts");

						accountAndTerr.accountPageScrollDownToContact();
						result = accountAndTerr.verify_element_spanWithText_Exist(nameUsed);
						commonLib.softAssertThat(result, "created contact exist :+nameUsed",
								"created contact not exist: " + nameUsed);


				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 

			}
		}
	}

	 //@Test(priority = 10)
	public void AccountAndTerritories_227856_TC_10() throws Exception {
		boolean result;
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

				 try {

				commonLib.startTest(testCaseID);

				commonLib.log(LogStatus.INFO,
						testCaseID + "Account Management: Test searching account, adding contact");

				login.loginToSFDC(workBook);
				accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");

				boolean flag = accountAndTerr.clickAppFromHeader("Accounts");
				commonLib.softAssertThat(flag, "opportunities page opened", "opportunities page not opened");

				accountAndTerr.searchAccountByNameFromAccountPage("CRYSTAL CLINIC PLASTIC SURGEONS");

				accountAndTerr.clickOptionsTabForPartcularAccOrOpp("Divisional Accounts");

				accountAndTerr.verifyElementExistWithSoftAssert(
						"SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH",
						" divisional account name");

				accountAndTerr.openDivisonalAccountAndVerifyCorrectPage();

				accountAndTerr.switchTodesiredTabFromOpenBrowserTab(1);

				String nameUsed = accountAndTerr.createNewContactForAccount();
				result = accountAndTerr.verify_element_spanWithText_Exist(nameUsed);

				commonLib.softAssertThat(result, "created contact exist :" + nameUsed,
						"created contact not  exist :" + nameUsed);
				accountAndTerr.clickOptionsTabForPartcularAccOrOpp("Divisional Contacts");

				accountAndTerr.accountPageScrollDownToContact();
				result = accountAndTerr.verify_element_spanWithText_Exist(nameUsed);
				commonLib.softAssertThat(result, "created contact exist :+nameUsed",
						"created contact not exist: " + nameUsed);

				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 

			}
		}
	}

	 //@Test(priority = 11)
	public void AccountAndTerritories_227858_TC_11() throws Exception {

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

				try {

					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO,
							testCaseID + "Account Management: Test searching account, adding contact");

					login.loginToSFDC(workBook);

					accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");

					accountAndTerr.clickAppFromHeader("Accounts");
					commonLib.refreshPage();

					String accName= accountAndTerr.createNewEnterpriseAccByASCSalesRep();
					
					accountAndTerr.verifyPricingAndFinanceUserInAccountTeam();
					
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
	 
	 //@Test(priority = 12)
		public void AccountAndTerritories_227896_TC_12() throws Exception {

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

					//try {

						commonLib.startTest(testCaseID);

						commonLib.log(LogStatus.INFO,
								testCaseID + "Account Management: Test searching account, adding contact");

						login.loginToSFDC(workBook);

						accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");
					
						accountAndTerr.clickAppFromHeader("Accounts");								
						
						accountAndTerr.searchAccountByNameFromAccountPage("GEMINI ASC");
						
						accountAndTerr.openOpporFromAccountPageDesiredList("Testing 235273");	
						
						accountAndTerr.createNewActivityForOppAndVerify();
						
						accountAndTerr.clickAppFromHeader("Opportunities");
						
						oppor.searchOpportunityAndFoundStageName("Testing 235273");
											
						//accountAndTerr.clickeleSpanWithTextDynamic("Testing 235273");				
						accountAndTerr.createNewActivityForOppAndVerify();

						/*
						 * } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
						 * "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
						 * finally { commonLib.endTest(); softAssert.assertAll(); }
						 */

				}
			}
		}
	 
	


}
