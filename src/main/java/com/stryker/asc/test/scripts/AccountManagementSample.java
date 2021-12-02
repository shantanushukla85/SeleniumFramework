package com.stryker.asc.test.scripts;
import java.util.List;
import java.util.Map;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;
import com.stryker.asc.pages.AccountManagementSamplePage;
import com.stryker.common.modules.LoginSFDC;
import com.stryker.salesforceLibrary.SfdcLibrary;


public class AccountManagementSample extends com.stryker.driver.SfdcDriver {
	
	private com.stryker.salesforceLibrary.SfdcLibrary sfdcLib;
	
	// page declaration
	LoginSFDC login;
	com.stryker.asc.pages.AccountManagementSamplePage account;
	
	String workSheet = "AccountManagement";

	public AccountManagementSample() {
		
		this.sfdcLib = new SfdcLibrary(commonLib);
		login = new LoginSFDC(commonLib);
		account = new AccountManagementSamplePage(commonLib);
		
	}

	@Test(priority = 1)
	public void TC01() throws Exception {
		
		String testCaseID = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println("Test case ID value is " + testCaseID);
		String packageName = AccountManagementSample.class.getPackage().toString();
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
			String View_Name = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "View_Name"));
			String Account_Name = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Account_Name"));
			String Contact_Type = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Contact_Type"));
			String Account_To_Be_Searched = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Account_To_Be_Searched"));
			String Account_Number = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Account_Number"));
			String First_Name="TestFirst"+commonLib.getRandomNumber(4);
			String Last_Name="TestLast"+commonLib.getRandomNumber(4);
			
			if (Run_Status.equalsIgnoreCase("Yes")) {
				
				try {
										
					commonLib.startTest(testCaseID);
					
					commonLib.log(LogStatus.INFO, testCaseID+"Account Management: Test searching account, adding contact");				
					
					login.loginToSFDC_Classic(workBook);
					
					commonLib.assertThat(account.verifyFlexLogo(),"Pass: Flex financial logo displayed","Fail: Flex financial logo is not displayed");
					
					sfdcLib.clickTabFromHomePage("Accounts");
					
					account.selectViewFromDropdown(View_Name);
					
					sfdcLib.searchAccount_Classic(Account_Name);
					
					account.mouseHoverContactsLink();
					
					account.clickNewContactButton();
					
					account.selectContactRecordType(Contact_Type);
					
					account.clickOnContinueButton();
					
					account.enterFirstName(First_Name);
					
					account.enterLastName(Last_Name);
					
					account.clickOnSaveBtn();
					
					commonLib.assertThat(account.verifyContactName(First_Name+" "+Last_Name),"Pass: Contact created and saved successfully","Fail: Error creating contact");

					sfdcLib.globalSearch_Classic(Account_To_Be_Searched);
					
					account.clickOnShowFilters();
					
					account.enterAccountNumber(Account_Number);
					
					account.clickOnApplyFilterButton();
					
					commonLib.assertThat(account.verifyAccountNumber(Account_Number),"Pass: Filter applied and account number displayed in the list",
							"Fail: Filter not applied successfully/account number not displayed");
					
					commonLib.endTest();
					
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
	public void TC02() throws Exception {
		
		String testCaseID = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println("Test case ID value is " + testCaseID);
		String packageName = AccountManagementSample.class.getPackage().toString();
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
			String Account_Name = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Account_Name"));
			String Contact_Type = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Contact_Type"));
			String First_Name="TestFirst"+commonLib.getRandomNumber(4);
			String Last_Name="TestLast"+commonLib.getRandomNumber(4);
			
			if (Run_Status.equalsIgnoreCase("Yes")) {
				
				try {
										
					commonLib.startTest(testCaseID);
					
					commonLib.log(LogStatus.INFO, testCaseID+"This test case will verify that internal contacts can be created");				
					
					login.loginToSFDC_Classic(workBook);
					
					sfdcLib.searchAccount_Classic(Account_Name);
					
					account.mouseHoverContactsLink();
					
					account.clickNewContactButton();
					
					account.selectContactRecordType(Contact_Type);
					
					account.clickOnContinueButton();
					
					account.enterFirstName(First_Name);
					
					account.enterLastName(Last_Name);
					
					account.clickOnSaveBtn();
					
					commonLib.assertThat(account.verifyContactName(First_Name+" "+Last_Name),"Pass: Contact "+First_Name+","+Last_Name+" created and saved successfully","Fail: Error creating contact "+First_Name+","+Last_Name);
					
					commonLib.endTest();
					
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
