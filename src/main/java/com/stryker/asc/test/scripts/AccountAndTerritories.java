package com.stryker.asc.test.scripts;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.stryker.asc.pages.AccountAndTerritoriesPage;
import com.stryker.asc.pages.AccountManagementSamplePage;
import com.stryker.common.modules.LoginSFDC;
import com.stryker.salesforceLibrary.SfdcLibrary;

public class AccountAndTerritories extends com.stryker.driver.SfdcDriver {

	private com.stryker.salesforceLibrary.SfdcLibrary sfdcLib;

	// page declaration
	LoginSFDC login;
	com.stryker.asc.pages.AccountManagementSamplePage account;
	AccountAndTerritoriesPage accountAndTerr = new AccountAndTerritoriesPage(commonLib);
	String workSheet = "scripts";
	boolean exist = false;

	public AccountAndTerritories() {

		this.sfdcLib = new SfdcLibrary(commonLib);
		login = new LoginSFDC(commonLib);
		account = new AccountManagementSamplePage(commonLib);

	}

	@Test(priority = 0)
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

				accountAndTerr.softAssertThat(accountAndTerr.verifyExistance("SF_ASCSales_account_buildCheckBox_XPATH"),
						" exist", " not exist");
				accountAndTerr.softAssertThat(
						accountAndTerr.verifyExistance("SF_ASCSales_account_billingAddressHeader_XPATH"), " exist",
						" not exist");
				accountAndTerr.softAssertThat(
						accountAndTerr.verifyExistance("SF_ASCSales_account_divisionalContact_Tab_XPATH"), " exist",
						" not exist");
				accountAndTerr.softAssertThat(
						accountAndTerr.verifyExistance("SF_ASCSales_account_divisionalAccounts_Tab_XPATH"), " exist",
						" not exist");

				accountAndTerr.scrolldownToElementAndVerifyClickableAndSoftAssert(
						"SF_ASCSales_account_opportunityProduct_XPATH", "span");
				
				commonLib.scroll(0, 600);
				commonLib.scroll(0, -600);
				commonLib.scroll(0, 600);

				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_account_contact_XPATH",
						"contact related list");
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_account_ManagementCompany_XPATH",
						" management company related list");
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_account_AddressInfo_XPATH",
						" Address info related list");				
				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 
			}
		}
	}

	 @Test(priority = 1)
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
				
				accountAndTerr.softAssertThat(
						accountAndTerr.verifyExistance("SF_ASCSales_account_myAccount_notesAndAttachment_XPATH"),
						" exist", " not exist");
	
				accountAndTerr.clickbyjavascript("SF_ASCSales_account_myAccount_notesAndAttachment_menuItems_XPATH");
				accountAndTerr.click("SF_ASCSales_account_myAccount_notesAndAttachment_upload_XPATH");
				Thread.sleep(5000);

				String filepath = System.getProperty("user.dir") + "\\images\\notes.txt";

				Thread.sleep(3000);
				accountAndTerr.UploadFile(filepath);				
				Thread.sleep(5000);
//accountAndTerr.softAssertThat(accountAndTerr.verifytext("SF_ASCSales_account_myAccount_fileUploadmsg_XPATH", "1 of 1 file uploaded ") , " exist", " not exist");
				accountAndTerr.click("SF_ASCSales_account_myAccount_fileUploadDone_XPATH");

				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_spanTagGeneral_Title_XPATH", "ASC Sales");

				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 
			}
		}
	}

	 @Test(priority = 2)
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

				// Thread.sleep(5000);
				commonLib.refreshPage();
				commonLib.waitForPresenceOfElementLocated("SF_ASCSales_accountLinkInHeader_XPATH");
				commonLib.waitForElementToBeClickable("SF_ASCSales_accountLinkInHeader_XPATH");
				// accountAndTerr.click("SF_ASCSales_accountLinkInHeader_XPATH");
				accountAndTerr.clickbyjavascript("SF_ASCSales_accountLinkInHeader_XPATH");
				// accountAndTerr.sendKeys("SF_ASCSales_searchAccountInputBox_XPATH", "flex
				// mapping");
				accountAndTerr.sendKeys("SF_ASCSales_searchAccountInputBox_XPATH", "gemini asc");//

				commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
				Thread.sleep(5000);
				// accountAndTerr.click("SF_ASCSales_firstAccountInSearch_XPATH");
				accountAndTerr.clickWithDynamicValue("SF_ASCSales_firstAccountInSearch_XPATH", "GEMINI ASC");
				commonLib.refreshPage();

				Thread.sleep(8000);
				commonLib.scroll(0, 1100);
				Thread.sleep(4000);

				accountAndTerr.softAssertThat(
						accountAndTerr.verifyExistance("SF_ASCSales_account_myAccount_ascOwner_XPATH"), " exist",
						" not exist");
				accountAndTerr.click("SF_ASCSales_account_myAccount_ascOwner_menuItems_XPATH");
				accountAndTerr.click("SF_ASCSales_account_myAccount_ascOwner_newButton_XPATH");
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_typeOfOwner_XPATH");

				accountAndTerr.verifytext("SF_ASCSales_account_myAccount_newASCOwner_typeOfOwner_item1_XPATH",
						"Surgeon");
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_typeOfOwner_item1_XPATH");

				accountAndTerr.sendKeys("SF_ASCSales_account_myAccount_newASCOwner_ownerPercentage_XPATH", "11");
				// commonLib.KeyPress_Tab();
				commonLib.scrollDownToElement("SF_ASCSales_account_myAccount_newASCOwner_contact_XPATH", "input");

				accountAndTerr.clickbyjavascript("SF_ASCSales_account_myAccount_newASCOwner_contact_XPATH");
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_contact_sample_XPATH");

				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
				// boolean ss =
				// accountAndTerr.verifyElementExist("SF_ASCSales_account_myAccount_newASCOwner_saveError_XPATH");

				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_closeError_XPATH");

				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_clearContact_XPATH");
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_clearAccount_XPATH");
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_ascAccount_sample_XPATH");
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");

				// boolean ss =
				// accountAndTerr.verifyElementExist("SF_ASCSales_account_myAccount_newASCOwner_successCreate_XPATH");

				accountAndTerr.softAssertThat(
						accountAndTerr.verifyExistance("SF_ASCSales_account_myAccount_newASCOwner_successCreate_XPATH"),
						" exist", " not exist");

				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 
			}
		}
	}

	 @Test(priority = 3)
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

					accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

					commonLib.refreshPage();
					commonLib.waitForPresenceOfElementLocated("SF_ASCSales_accountLinkInHeader_XPATH");
					commonLib.waitForElementToBeClickable("SF_ASCSales_accountLinkInHeader_XPATH");
					accountAndTerr.clickbyjavascript("SF_ASCSales_accountLinkInHeader_XPATH");

					accountAndTerr.sendKeys("SF_ASCSales_searchAccountInputBox_XPATH",
							"CRYSTAL CLINIC PLASTIC SURGEONS");//

					commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
					Thread.sleep(5000);
					accountAndTerr.click("SF_ASCSales_AccountInSearch_crystal_XPATH");
					// accountAndTerr.clickWithDynamicValue("SF_ASCSales_firstAccountInSearch_XPATH","CRYSTAL
					// CLINIC PLASTIC SURGEONS");
					accountAndTerr.scrolldownToElementAndVerifyClickableAndSoftAssert(
							"SF_ASCSales_account_opportunityProduct_XPATH", "span");

					// accountAndTerr.verifyExistance("SF_ASCSales_account_opportunityProduct_XPATH");

					commonLib.scroll(0, 600);

					commonLib.scroll(0, -600);

					commonLib.scroll(0, 600);

					accountAndTerr.softAssertThat(accountAndTerr.verifyExistance("SF_ASCSales_account_contact_XPATH"),
							" exist", " not exist");

					accountAndTerr.scrollToElement("SF_ASCSales_account_ManagementCompany_XPATH");
					accountAndTerr.softAssertThat(
							accountAndTerr.verifyExistance("SF_ASCSales_account_ManagementCompany_XPATH"), " exist",
							" not exist");
					accountAndTerr.softAssertThat(
							accountAndTerr.verifyExistance("SF_ASCSales_account_AddressInfo_XPATH"), " exist",
							" not exist");

					commonLib.scroll(0, -600);
					Thread.sleep(4000);
					accountAndTerr.clickbyjavascript("SF_ASCSales_account_divisionalAccounts_Tab_XPATH");

					accountAndTerr.softAssertThat(
							accountAndTerr.verifyExistance(
									"SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH"),
							" exist", " not exist");

					String divisonalAccNAme = commonLib
							.getText("SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH");
					accountAndTerr
							.clickbyjavascript("SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH");
					commonLib.switchWindowWithURL("Setup"); // New method to switch window on the basis of Url.
					commonLib.waitForDynamicTitle("Salesforce", 120);
					boolean result = accountAndTerr.verifytext("SF_ASCSales_account_toGetName_XPATH", divisonalAccNAme);

					accountAndTerr.softAssertThat(result, " exist", " not exist");

					accountAndTerr.tabs(1);

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

	 @Test(priority = 4)
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

				// accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");

				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

				commonLib.refreshPage();
				commonLib.waitForPresenceOfElementLocated("SF_ASCSales_accountLinkInHeader_XPATH");
				commonLib.waitForElementToBeClickable("SF_ASCSales_accountLinkInHeader_XPATH");
				accountAndTerr.clickbyjavascript("SF_ASCSales_accountLinkInHeader_XPATH");

				accountAndTerr.click("SF_ASCSales_account_newButton_XPATH");
				accountAndTerr.click("SF_ASCSales_account_typeManagementType_checkBox_XPATH");
				accountAndTerr.click("SF_ASCSales_nextButton_XPATH");
				accountAndTerr.sendKeys("SF_ASCSales_newAccount_AccNameInput_XPATH", "wwwr");
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");

				boolean result = accountAndTerr.verifytext("SF_ASCSales_account_toGetName_XPATH", "wwwr");

				accountAndTerr.softAssertThat(result, " exist", " not exist");

				accountAndTerr.softAssertThat(
						accountAndTerr.verifyExistance("SF_ASCSales_account_myAccount_ascOwner_XPATH"), " exist",
						" not exist");
				accountAndTerr.click("SF_ASCSales_account_myAccount_ascOwner_menuItems_XPATH");
				accountAndTerr.click("SF_ASCSales_account_myAccount_ascOwner_newButton_XPATH");
				accountAndTerr.softAssertThat(
						accountAndTerr.verifyExistance("SF_ASCSales_account_myAccount_newASCOwner_typeOfOwner_XPATH"),
						" exist", " not exist");
				Thread.sleep(5000);

				accountAndTerr.softAssertThat(accountAndTerr.verifyExistance("SF_ASCSales_labelName_Contact_XPATH"),
						" exist", " not exist");
				accountAndTerr.softAssertThat(accountAndTerr.verifyExistance("SF_ASCSales_labelName_ASCAccount_XPATH"),
						" exist", " not exist");
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_clearAccount_XPATH");
				accountAndTerr.click("SF_ASCSales_newAccountOptinFromDropdown_XPATH");

				accountAndTerr.click("SF_ASCSales_nextButton_XPATH");
				accountAndTerr.sendKeys("SF_ASCSales_newAscAccount_AccNAme_Input_XPATH", "rrr1");

				accountAndTerr.scrollToElement("SF_ASCSales_newAscAccount_billingTypeDrDown_XPATH");
				accountAndTerr.click("SF_ASCSales_newAscAccount_billingTypeDrDown_XPATH");
				accountAndTerr.click("SF_ASCSales_newAscAccount_billingTypeDrDown_option3_XPATH");
				accountAndTerr.clickbyjavascript("SF_ASCSales_newAscAccount_save_XPATH");

				accountAndTerr.softAssertThat(
						accountAndTerr.verifyExistance("SF_ASCSales_newAscAccountAfterCreateInPlaceHolder_XPATH"),
						" exist", " not exist");

				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 

			}
		}
	}

	 @Test(priority = 5)
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

				// accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");

				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");				

				accountAndTerr.clickAppFromHeader("Accounts");
				commonLib.refreshPage();
				accountAndTerr.click("SF_ASCSales_account_newButton_XPATH");
				accountAndTerr.click("SF_ASCSales_account_typeEnterpriseAcc_checkBox_XPATH");
				accountAndTerr.click("SF_ASCSales_nextButton_XPATH");

				int enterpriseID = commonLib.randomInteger(2, 9);
				String EID = String.valueOf(enterpriseID);
				String accName = commonLib.getRandomString("abt");

				accountAndTerr.sendKeys("SF_ASCSales_newAccount_AccNameInput_XPATH", "Test" + accName);
				accountAndTerr.scrollToElementAndClickOrSendKeys("SF_ASCSales_newAccount_enterpriseID_XPATH",
						"sendkeys", EID);
				accountAndTerr.scrollToElementAndClickOrSendKeys("SF_ASCSales_newAccount_postalCode_XPATH", "sendkeys",
						"59001");
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");

				commonLib.waitForPresenceOfElementLocated("SF_ASCSales_GetCreatedAccName_XPATH");
				String accNumCreated = commonLib.getText("SF_ASCSales_GetCreatedAccName_XPATH");

				accountAndTerr.clickAppFromHeader("Home");
				accountAndTerr.clickAppFromHeader("Regions");
				// accountAndTerr.selectAppFromLeftSideIconWaffle("Regions");

				accountAndTerr.click("SF_ASCSales_Region_RockyMountain_XPATH");
				accountAndTerr.click("SF_ASCSales_Region_RelatedTab_XPATH");
				// exist=accountAndTerr.verifyExistance("SF_ASCSales_Region_presenceof_TerrMemAssig_XPATH");
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_Region_presenceof_TerrMemAssig_XPATH",
						"Territory Member Assignments");
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_Region_presenceof_zipcode_XPATH",
						"Zip Code Section");

				// accountAndTerr.click("SF_ASCSales_Region_TerrMemAssig_viewAll_XPATH");
				accountAndTerr.clickbyjavascript("SF_ASCSales_Region_TerrMemAssig_viewAll_XPATH");
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_Region_TerrMemAssig_table_XPATH",
						"TerrMemberAssignment Data Table");
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_Region_TerrMemAssig_ActiveMember_XPATH",
						"Active member");

				accountAndTerr.clickAppFromHeader("Accounts");

				accountAndTerr.sendKeys("SF_ASCSales_searchAccountInputBox_XPATH", accNumCreated);//

				commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
				Thread.sleep(5000);

				accountAndTerr.click("SF_ASCSales_firstAccAfterSearch_XPATH");
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_accountDeatilPage_AccountTeamLink_XPATH",
						"Account Team List");
				accountAndTerr.click("SF_ASCSales_accountDeatilPage_AccountTeamLink_XPATH");

				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 

			}
		}
	}

	 @Test(priority = 6)
	public void AccountAndTerritories_227848_TC_07() throws Exception {

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
				accountAndTerr.clickAppFromHeader("Regions");

				accountAndTerr.click("SF_ASCSales_Region_Desert_XPATH");
				accountAndTerr.click("SF_ASCSales_Region_RelatedTab_XPATH");
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_Region_presenceof_zipcode_XPATH",
						"Zip Code Section");
				accountAndTerr.click("SF_ASCSales_Region_NewButtonForZipCode_XPATH");

				accountAndTerr.sendKeys("SF_ASCSales_Region_zipCodeInput_XPATH", "59001");
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
				accountAndTerr.verifyElementExistWithSoftAssert("accountAndTerr.", " expected error ");
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_closeError_XPATH");

				commonLib.clear("SF_ASCSales_Region_zipCodeInput_XPATH");
				int pincode = accountAndTerr.gen();
				String pin1 = String.valueOf(pincode);
				accountAndTerr.sendKeys("SF_ASCSales_Region_zipCodeInput_XPATH", pin1);
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_toastMessageForSuccess_XPATH",
						"success msg for zip added");

				accountAndTerr.click("SF_ASCSales_Region_NewButtonForZipCode_XPATH");
				commonLib.clear("SF_ASCSales_Region_zipCodeInput_XPATH");
				accountAndTerr.sendKeys("SF_ASCSales_Region_zipCodeInput_XPATH", pin1);
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
				accountAndTerr.verifyElementExistWithSoftAssert("accountAndTerr.", " expected error ");
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_closeError_XPATH");

				commonLib.clear("SF_ASCSales_Region_zipCodeInput_XPATH");
				pincode = accountAndTerr.gen();
				String pin2 = String.valueOf(pincode);
				accountAndTerr.sendKeys("SF_ASCSales_Region_zipCodeInput_XPATH", pin2 + "-4914");
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
				// SF_ASCSales_toastMessageForSuccess_XPATH

				accountAndTerr.clickAppFromHeader("Accounts");
				commonLib.refreshPage();
				accountAndTerr.click("SF_ASCSales_account_newButton_XPATH");

				accountAndTerr.click("SF_ASCSales_account_typeEnterpriseAcc_checkBox_XPATH");
				accountAndTerr.click("SF_ASCSales_nextButton_XPATH");

				accountAndTerr.sendKeys("SF_ASCSales_newAccount_AccNameInput_XPATH", pin1);
				accountAndTerr.scrollToElementAndClickOrSendKeys("SF_ASCSales_newAccount_enterpriseID_XPATH",
						"sendkeys", pin1);
				accountAndTerr.scrollToElementAndClickOrSendKeys("SF_ASCSales_newAccount_postalCode_XPATH", "sendkeys",
						pin1);
				accountAndTerr.sendKeys("SF_ASCSales_newAccount_shippingpostalCode_XPATH", pin1);
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
				commonLib.waitForPresenceOfElementLocated("SF_ASCSales_GetCreatedAccName_XPATH");

				accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");
				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");
				// commonLib.refreshPage();

				/*
				 * commonLib.clickwithoutWait("GlobalSearch_SetupPage_XPATH");
				 * Thread.sleep(3000); commonLib.sendKeys("GlobalSearch_SetupPage_XPATH", pin1);
				 */

				commonLib.refreshPage();
				accountAndTerr.clickbyjavascript("SF_ASCSales_searchAtTopOfPage_XPATH");
				commonLib.sendkeys_by_JavaScript("SF_ASCSales_placeHolderForTopSearch_XPATH", pin1);
				commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
				Thread.sleep(5000);

				boolean exist = accountAndTerr.verifyAccountExistAfterSearchFromTopSearch(pin1);
				commonLib.softAssertThat(exist, "account with newly added zipcode exist",
						"account with newly added zipcode exist");

				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 

			}
		}
	}

	 @Test(priority = 7)
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
				accountAndTerr.clickAppFromHeader("Regions");

				accountAndTerr.click("SF_ASCSales_Region_Desert_XPATH");
				accountAndTerr.click("SF_ASCSales_Region_RelatedTab_XPATH");
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_Region_presenceof_zipcode_XPATH",
						"Zip Code Section");
				//String count = commonLib.getText("SF_ASCSales_Region_terrMemCount_XPATH");

				// accountAndTerr.clickbyjavascript("SF_ASCSales_Region_TerrMemAssig_viewAll_XPATH");
				accountAndTerr.addTerrMemberInRegion("Margaret ASC Sales", "Read");

				// accountAndTerr.verifytext("SF_ASCSales_Region_terrMemCount_XPATH", count+1);
				accountAndTerr.addTerrMemberInRegion("Robert Montgomery", "Edit");

				// accountAndTerr.verifytext("SF_ASCSales_Region_terrMemCount_XPATH", count+2);

				accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");

				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

				commonLib.refreshPage();

				accountAndTerr.clickbyjavascript("SF_ASCSales_searchAtTopOfPage_XPATH");
				commonLib.sendkeys_by_JavaScript("SF_ASCSales_placeHolderForTopSearch_XPATH", "87213");
				commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
				Thread.sleep(5000);

				accountAndTerr.verifyAccountExistAfterSearchFromTopSearch("87213");
				accountAndTerr.click("SF_ASCSales_account_87213_XPATH");
				accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_accountNameEditLink_XPATH",
						"succes message");

				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 

			}
		}
	}

	 @Test(priority = 8)
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
				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");
				commonLib.refreshPage();

				accountAndTerr.clickbyjavascript("SF_ASCSales_accountLinkInHeader_XPATH");
				accountAndTerr.openAccountFromAccountSearchByName("CRYSTAL CLINIC PLASTIC SURGEONS");

				accountAndTerr.clickbyjavascript("SF_ASCSales_account_divisionalAccounts_Tab_XPATH");

				accountAndTerr.verifyElementExistWithSoftAssert(
						"SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH",
						" divisional account name");

				String divisonalAccNAme = commonLib
						.getText("SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH");
				accountAndTerr.clickbyjavascript("SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH");
				commonLib.switchWindowWithURL("Setup"); // New method to switch window on thebasis of Url.
				commonLib.waitForDynamicTitle("Salesforce", 120);
				result = accountAndTerr.verifytext("SF_ASCSales_account_toGetName_XPATH", divisonalAccNAme);
				commonLib.softAssertThat(result, "Navigated to correct account", "not Navigated to correct account");

				accountAndTerr.tabs(1);

				commonLib.scroll(0, 600);
				// accountAndTerr.scroll();
				commonLib.scroll_view("SF_ASCSales_account_contact_XPATH");

				// accountAndTerr.clickbyjavascript("SF_ASCSales_account_contact_menuItems_XPATH");
				accountAndTerr.click("SF_ASCSales_account_contact_showMoreActionButton_XPATH");
				accountAndTerr.click("SF_ASCSales_account_contact_newButon_XPATH");
				accountAndTerr.click("SF_ASCSales_nextButton_XPATH");
				accountAndTerr.sendKeys("SF_ASCSales_account_contact_newPage_firstNameInput_XPATH", "a99");
				accountAndTerr.sendKeys("SF_ASCSales_account_contact_newPage_lastNameInput_XPATH", "sh2");
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");

				result = accountAndTerr.verifyElementExistWithDynamicText("a98 sh1");
				commonLib.softAssertThat(result, "created contact exist", "created contact exist");

				commonLib.scroll(0, -600);
				accountAndTerr.clickbyjavascript("SF_ASCSales_account_divisionalContact_Tab_XPATH");
				commonLib.scroll(0, 600);
				// accountAndTerr.scroll();
				commonLib.scroll_view("SF_ASCSales_account_contact_XPATH");
				result = accountAndTerr.verifyElementExistWithDynamicText("a98 sh1");
				commonLib.softAssertThat(result, "created contact exist", "created contact exist");

				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 

			}
		}
	}

	 @Test(priority = 9)
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

				accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");
				commonLib.refreshPage();

				accountAndTerr.clickbyjavascript("SF_ASCSales_accountLinkInHeader_XPATH");
				accountAndTerr.openAccountFromAccountSearchByName("CRYSTAL CLINIC PLASTIC SURGEONS");

				accountAndTerr.clickbyjavascript("SF_ASCSales_account_divisionalAccounts_Tab_XPATH");

				accountAndTerr.verifyElementExistWithSoftAssert(
						"SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH",
						" divisional account name");

				String divisonalAccNAme = commonLib
						.getText("SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH");
				accountAndTerr.clickbyjavascript("SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH");
				commonLib.switchWindowWithURL("Setup"); // New method to switch window on thebasis of Url.
				commonLib.waitForDynamicTitle("Salesforce", 120);
				result = accountAndTerr.verifytext("SF_ASCSales_account_toGetName_XPATH", divisonalAccNAme);
				commonLib.softAssertThat(result, "Navigated to correct account", "not Navigated to correct account");

				accountAndTerr.tabs(1);

				commonLib.scroll(0, 600);
				// accountAndTerr.scroll();
				commonLib.scroll_view("SF_ASCSales_account_contact_XPATH");

				// accountAndTerr.clickbyjavascript("SF_ASCSales_account_contact_menuItems_XPATH");
				accountAndTerr.click("SF_ASCSales_account_contact_showMoreActionButton_XPATH");
				accountAndTerr.click("SF_ASCSales_account_contact_newButon_XPATH");
				accountAndTerr.click("SF_ASCSales_nextButton_XPATH");
				accountAndTerr.sendKeys("SF_ASCSales_account_contact_newPage_firstNameInput_XPATH", "a99");
				accountAndTerr.sendKeys("SF_ASCSales_account_contact_newPage_lastNameInput_XPATH", "sh2");
				accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");

				result = accountAndTerr.verifyElementExistWithDynamicText("a98 sh1");
				commonLib.softAssertThat(result, "created contact exist", "created contact exist");

				commonLib.scroll(0, -600);
				accountAndTerr.clickbyjavascript("SF_ASCSales_account_divisionalContact_Tab_XPATH");
				commonLib.scroll(0, 600);
				// accountAndTerr.scroll();
				commonLib.scroll_view("SF_ASCSales_account_contact_XPATH");
				result = accountAndTerr.verifyElementExistWithDynamicText("a98 sh1");
				commonLib.softAssertThat(result, "created contact exist", "created contact exist");

				
				  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
				  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
				  finally { commonLib.endTest(); softAssert.assertAll(); }
				 

			}
		}
	}

	 @Test(priority = 10)
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

					accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

					accountAndTerr.clickAppFromHeader("Accounts");
					commonLib.refreshPage();

					accountAndTerr.click("SF_ASCSales_account_newButton_XPATH");
					accountAndTerr.click("SF_ASCSales_account_typeEnterpriseAcc_checkBox_XPATH");
					accountAndTerr.click("SF_ASCSales_nextButton_XPATH");

					//int enterpriseID = commonLib.randomInteger(2, 9);
					//String EID = String.valueOf(enterpriseID);
					//String accName = commonLib.getRandomString("abt");
					String accName1 = "Test" + System.currentTimeMillis();
					accName1 = "EnterAcc" + accName1.substring(accName1.length() - 3);

					accountAndTerr.sendKeys("SF_ASCSales_newAccount_AccNameInput_XPATH", accName1);
					accountAndTerr.scrollToElementAndClickOrSendKeys("SF_ASCSales_newAccount_shippingpostalCode_XPATH",
							"sendkeys", "59001");
					accountAndTerr.scrollToElementAndClickOrSendKeys("SF_ASCSales_newAccount_postalCode_XPATH",
							"sendkeys", "59001");
					accountAndTerr.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");

					commonLib.waitForPresenceOfElementLocated("SF_ASCSales_GetCreatedAccName_XPATH");
					//String accNumCreated = commonLib.getText("SF_ASCSales_GetCreatedAccName_XPATH");

					accountAndTerr.click("SF_ASCSales_accountTeam_viewAll_XPATH");

					accountAndTerr.verifyElementExistWithSoftAssert(
							"SF_ASCSales_accountTeam_roleColumn_financeTM_XPATH", "ASC Finance TM");

					accountAndTerr.clickbyjavascript("SF_ASCSales_accountTeam_teamRoleColOrderBy_XPATH");
					commonLib.waitforInvisibilityOfElement("SF_ASCSales_progressSpinner_XPATH");
					accountAndTerr.clickbyjavascript("SF_ASCSales_accountTeam_teamRoleColOrderBy_XPATH");
					accountAndTerr.verifyElementExistWithSoftAssert(
							"SF_ASCSales_accountTeam_roleColumn_PricingTM_XPATH", "ASC Pricing TM");
					accountAndTerr.click("SF_ASCSales_logOutAsLink_XPATH");

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
