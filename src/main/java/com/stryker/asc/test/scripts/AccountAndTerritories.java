package com.stryker.asc.test.scripts;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.stryker.asc.pages.AccountAndTerritoriesPage;
import com.stryker.asc.pages.AccountManagementSamplePage;
import com.stryker.asc.pages.OpportunitiesPage;
import com.stryker.common.modules.LoginSFDC;
import com.stryker.driver.SfdcDriver;
import com.stryker.salesforceLibrary.SfdcLibrary;

public class AccountAndTerritories extends SfdcDriver {

	private com.stryker.salesforceLibrary.SfdcLibrary sfdcLib;

	// page declaration
	LoginSFDC login;
	AccountManagementSamplePage account;
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

				if (Run_Status.equalsIgnoreCase("Yes")) {

					 try {

					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO,
							testCaseID + "Account Management: Test searching account, adding contact");

					/*login.loginToSFDC(workBook);

					accountAndTerr.proxyLoginUsingUser("Margaret ASC Sales", "ASC Sales Rep");*/
					
					login.loginToSFDC(workBook, 2, 2);

					accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

					accountAndTerr.clickAppFromHeader("Accounts");
					commonLib.refreshPage();

					accountAndTerr.searchAccountByNameFromAccountPage("Test_Automation_Account");

				   accountAndTerr.accountDetailsPageVerification();

				   commonLib.scrollDownToElement("SF_ASCSales_account_opportunityProduct_XPATH", "Opportunity Product");
				   
				   accountAndTerr.accountRelatedListVerification();
				 
					  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
					  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
					  finally { commonLib.endTest(); softAssert.assertAll(); }
					 
				}
			}
		}
		
	
	// @Test(priority = 1)
		public void AccountAndTerritories_227837_TC_02() throws Exception {

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

					accountAndTerr.clickAppFromHeader("Accounts");
					commonLib.refreshPage();

					accountAndTerr.searchAccountByNameFromAccountPage("Test_Automation_Account");			
					commonLib.refreshPage();
					accountAndTerr.verifyAccountTeamMember();
					Thread.sleep(8000);
					commonLib.scrollDownToElement("SF_ASCSales_account_opportunityProduct_XPATH", "Opportunity Product");
					Thread.sleep(4000);

					commonLib.softAssertThat(
							accountAndTerr.verifyElementExistByWait("SF_ASCSales_account_myAccount_ascOwner_XPATH", null), " exist",
							" not exist");
					
					accountAndTerr.openNewAscOwnerForm();
					
					boolean flag=accountAndTerr.addNewASCOwner();
					commonLib.softAssertThat(flag," Saved successfully", " Didn't save");
					
					boolean flag2 = commonLib.verifyElementPresence("SF_ASCSales_account_myAccount-ascOwnerRecord_XPATH");
					commonLib.softAssertThat(flag2, "ASC Owner is Visible" , "Not Visible");

					
					  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
					  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
					  finally { commonLib.endTest(); softAssert.assertAll(); }
					 
				}
			}
		}
		
	//@Test(priority = 0)
		public void AccountAndTerritories_227845_TC_03() throws Exception {

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

					accountAndTerr.clickAppFromHeader("Accounts");
					commonLib.refreshPage();
					accountAndTerr.searchAccountByNameFromAccountPage("EnterAcc738");
					
					boolean exist = commonLib.verifyElementPresence("SF_ASCSales_account_myAccount-ascOwner_XPATH");
					commonLib.softAssertThat(exist, "ASC Owner is Visible" , "Not Visible");
					
					accountAndTerr.openNewAscOwnerForm();
					commonLib.click("SF_ASCSales_account_myAccount-CloseascOwnerForm_XPATH");
					
					boolean exist1 = commonLib.verifyElementPresence("SF_ASCSales_account_myAccount-CloseascOwnerForm_XPATH");
					commonLib.softAssertThat(exist1, "Form CLosed" , "Not Closed");
					
					/*accountAndTerr.searchAccountByNameFromAccountPage("Test_Automation_Account");
				   accountAndTerr.accountDetailsPageVerification();

				   commonLib.scrollDownToElement("SF_ASCSales_account_opportunityProduct_XPATH", "Opportunity Product");
				   
				   accountAndTerr.accountRelatedListVerification();*/
				 
					  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
					  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
					  finally { commonLib.endTest(); softAssert.assertAll(); }
					 
				}
			}
		}
		
		
	
	//@Test(priority = 0)
		public void AccountAndTerritories_227847_TC_04() throws Exception {

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

					login.loginToSFDC(workBook);

					accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");				

					accountAndTerr.clickAppFromHeader("Accounts");
					commonLib.refreshPage();
					
					String accNumCreated = accountAndTerr.createNewEnterpriseAccByAdmin();

					accountAndTerr.clickAppFromHeader("Home");				
					
					boolean flag=accountAndTerr.openRegionAndRelatdTab("Gulf Coast");				
					commonLib.softAssertThat(flag, "zipcode page found", "zip code page not found");
				
					accountAndTerr.verifyRegionDetailPage();
					Thread.sleep(3000);

					accountAndTerr.verifyActiveTerrMemberForRegion();

					accountAndTerr.clickAppFromHeader("Accounts");
					accountAndTerr.searchAccountByNameFromAccountPage(accNumCreated);
					
					
					accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_accountDeatilPage_AccountTeamLink_XPATH",
							"Account Team List");
					sfdcLib.click("SF_ASCSales_accountDeatilPage_AccountTeamLink_XPATH");
					accountAndTerr.verifyAccountTeamMemberswithRegions();

					
					  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
					  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
					  finally { commonLib.endTest(); softAssert.assertAll(); }
					 

				}
			}
		}
		
	 //@Test(priority = 7)
		public void AccountAndTerritories_227848_TC_05() throws Exception {
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

					if (Run_Status.equalsIgnoreCase("Yes")) {

						 try {

						commonLib.startTest(testCaseID);

						commonLib.log(LogStatus.INFO,
								testCaseID + "Account Management: Test searching account, adding contact");

						login.loginToSFDC(workBook);

						accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");
						
						flag=accountAndTerr.openRegionAndRelatdTab("Desert");
						commonLib.softAssertThat(flag, "zipcode page found", "zip code page not found");
										
						accountAndTerr.addZipCode("57890");	
						accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_toastMessageForSuccess_XPATH",
								"Success message for saved Zip Code ");
						accountAndTerr.addZipCode("57890");
						accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_errorMessage_XPATH", " Error Message ");
						 }
						
						catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
						"Testcase " + testCaseID + " failed due to following reason(s): " + e); }
						 finally { 
								commonLib.endTest(); softAssert.assertAll(); 
								 }	
					}
				}
			}
		
		
		 @Test(priority = 8)
			public void AccountAndTerritories_227849_TC_06() throws Exception {

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

						login.loginToSFDC(workBook);

						accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");
						
						accountAndTerr.openRegionAndRelatdTab("Desert");
						accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_Region_presenceof_zipcode_XPATH",
								"Zip Code Section");
						String count = commonLib.getText("SF_ASCSales_Region_terrMemCount_XPATH");

						//commonLib.clickbyjavascript("SF_ASCSales_Region_TerrMemAssig_viewAll_XPATH");
						accountAndTerr.addTerrMemberInRegion("Muli ASC Pricing", "Read");

						 accountAndTerr.verifyText("SF_ASCSales_Region_terrMemCount_XPATH", count+1);
						 
						accountAndTerr.addTerrMemberInRegion("Robert Montgomery", "Edit");

						 accountAndTerr.verifyText("SF_ASCSales_Region_terrMemCount_XPATH", count+2);

						accountAndTerr.proxyLoginUsingUser("Muli ASC Pricing", "ASC Sales Pricing");

						//accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");

						commonLib.refreshPage();

						accountAndTerr.searchAccountFromopSearchAndOpen("Test1234");
						accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_accountNameEditLink_XPATH",
								"success message");
						
						commonLib.waitForElementToBeClickable("TC_Userimg_XPATH");
					 	commonLib.performHoverandClick("TC_Userimg_XPATH");
					 	sfdcLib.click("SF_ASCSales_viewProfile_logOut_XPATH");
						
						accountAndTerr.proxyLoginUsingUser("Robert Montgomery", "ASC Sales Pricing");
						
						commonLib.refreshPage();

						accountAndTerr.searchAccountFromopSearchAndOpen("Test1234");
						accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_accountNameEditLink_XPATH",
								"success message");
						

						
						  } catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
						  "Testcase " + testCaseID + " failed due to following reason(s): " + e); }
						  finally { commonLib.endTest(); softAssert.assertAll(); }
						 

					}
				}
			}
		
	//@Test(priority = 11)
		public void AccountAndTerritories_227858_TC_7() throws Exception {

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

						login.loginToSFDC(workBook, 2 , 2);

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
		
		
	//@Test(priority = 7)
		public void AccountAndTerritories_227894_TC_08() throws Exception {
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

				if (Run_Status.equalsIgnoreCase("Yes")) {

					 try {

					commonLib.startTest(testCaseID);

					commonLib.log(LogStatus.INFO,
							testCaseID + "Account Management: Test searching account, adding contact");

					login.loginToSFDC(workBook, 2, 2);

					accountAndTerr.selectAppFromLeftSideIconWaffle("ASC Sales Lightning");
					accountAndTerr.clickAppFromHeader("Accounts");
					commonLib.refreshPage();
					accountAndTerr.searchAccountByNameFromAccountPage("Test_Automation_Account");
					
					accountAndTerr.openNewSupportCaseCDGOForm(); 
					commonLib.refreshPage();
					
					accountAndTerr.switchtoFrameSelectRequestType();
					Thread.sleep(10000);
					accountAndTerr.fillSupportCaseCDGOForm();
					
					commonLib.click("SF_ASCSales_account_myAccount_suppCase_uploadFIle_XPATH");
					Thread.sleep(5000);
					
					String filepath = System.getProperty("user.dir") + "\\images\\notes.txt";
					Thread.sleep(10000);
					accountAndTerr.UploadFile(filepath);	
					
					Thread.sleep(10000);
					commonLib.softAssertThat(accountAndTerr.verifyText("1 of 1 file uploaded", "1 of 1 file uploaded ") , " File Uploaded", " File didn't upload");
					commonLib.getScreenshot();
					commonLib.clickbyjavascript("SF_ASCSales_account_myAccount_fileUploadDone_XPATH");
					Thread.sleep(2000);
					commonLib.clickbyjavascript("SF_ASCSales_account_myAccount_suppCase_NextButton_XPATH");
					Thread.sleep(2000);
					commonLib.clickbyjavascript("SF_ASCSales_account_myAccount_suppCase_NextButton_XPATH");
					Thread.sleep(2000);
					
					commonLib.getScreenshot();
					boolean CDGOexists = commonLib.verifyElementPresence("SF_ASCSales_account_myAccount_suppCase_CDGOCaseNumber_XPATH");
					commonLib.softAssertThat(CDGOexists, " CDGO case created" , "CDGO case not created");
					
					
					String cdgo= accountAndTerr.getCDGOCaseNumber();
					commonLib.clickbyjavascript("SF_ASCSales_account_myAccount_suppCase_NextButton_XPATH");	
					
					accountAndTerr.fillLoxRequest();
					Thread.sleep(2000);
					
					String Lox=accountAndTerr.getLOXCaseNumber();
					commonLib.clickbyjavascript("SF_ASCSales_account_myAccount_loxIntegration_finishButton_XPATH");
					Thread.sleep(8000);
					
					sfdcLib.click("SF_ASCSales_account_myAccount_suppCase_ViewAll_XPATH");
					
					//commonLib.assertThat(commonLib.waitForVisibilityOf(cdgo), "CDGO Case is created successfully", "CDGO case is not created");	
					commonLib.assertThat(cdgo!=null, "CDGO case is visible on Supporst case list", "CDGO case is not visible on Supporst case list");
					commonLib.assertThat(Lox!=null, "LOX case is visible on Supporst case list", "LOX case is not visible on Supporst case list");
					
					 }
					catch (Exception e) { e.printStackTrace(); commonLib.log(LogStatus.FAIL,
							"Testcase " + testCaseID + " failed due to following reason(s): " + e); }
					finally { 
							commonLib.endTest(); softAssert.assertAll(); 
						 }	
					
					 }
				}
				
			}
		
}