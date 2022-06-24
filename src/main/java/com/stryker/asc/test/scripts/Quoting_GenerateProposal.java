package com.stryker.asc.test.scripts;

import java.io.File;
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

public class Quoting_GenerateProposal extends SfdcDriver {
	LoginSFDC login;
	SfdcLibrary sfdcLib;
	AccountManagementSamplePage account;
	AccountAndTerritoriesPage accountAndTerr = new AccountAndTerritoriesPage(commonLib);
	OpportunitiesPage oppor = new OpportunitiesPage(commonLib);
	Quote_ProposalSetupPage quote = new Quote_ProposalSetupPage(commonLib);
	// String pdfFile = System.getProperty("user.dir") + "\\MyFiles\\TestPDF.pdf";
	String pdfFile = System.getProperty("user.dir") + File.separator + "MyFiles" + File.separator + "TestPDF.pdf";

	String workSheet = "Quote_Setup";
	boolean exist = false;
	public String newOppName;

	public Quoting_GenerateProposal() {
		this.sfdcLib = new SfdcLibrary(commonLib);
		login = new LoginSFDC(commonLib);
		account = new AccountManagementSamplePage(commonLib);
	}

	@Test(priority = 1)
	public void quote_GenerateProposal_263028_TC_01() throws Exception {
		String testCaseID = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println("Test case ID value is " + testCaseID);
		String packageName = AccountAndTerritories.class.getPackage().toString();
		System.out.println("Package name value is " + packageName);
		System.out.println("Worksheet name value is " + workSheet);
		String workBook = sfdcLib.getWorkbookNameWithEnvironment();
		System.out.println("Workbook value is " + workBook);
		System.out.println("PDF File Path is: " + pdfFile);

		Map<Integer, List<String>> mapTestData = commonLib.readAllTestCaseData(workBook, workSheet, testCaseID, 1);
		List<String> headerRow = commonLib.getHeaderRow(workBook, workSheet);

		for (int rowKey : mapTestData.keySet()) {
			commonLib.syso("***** Iteration number: " + rowKey + " (Starts)*****");
			List<String> lstRowData = mapTestData.get(rowKey);
			String Run_Status = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Run"));
			String accountName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Account_Name"));
			String disposablePart = lstRowData
					.get(commonLib.getColumnNumberFromList(headerRow, "Disposable_Part_Number"));
			String capitalPart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Capital_Part_Number"));
			String servicePart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Service_Part_Number"));
			String templateName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Template_Name"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO, testCaseID + "164791 - Output - Proposal Service agreement");
					login.loginToSFDC(workBook, 3, 3);
					accountAndTerr.clickAppFromHeader("Accounts");
					account.clickOnAccountNameLink(accountName);
					accountAndTerr.clickAppFromHeader("Opportunities");
					newOppName = oppor.createNewOpportunityWithAccountName(accountName, "Data Aggregation");
					quote.clickOnCreateQuote();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("TestDescription");
					quote.clickOnCPQQuoteOperationsButton("Save");

					quote.addQuoteLine(disposablePart);
					quote.enterYearValueforLineItem("volumeYear1", "2007");
					Thread.sleep(2000);
					quote.addQuoteLine(capitalPart);
					quote.enterYearValueforLineItem("volumeYear2", "2012");
					Thread.sleep(2000);
					quote.addQuoteLine(servicePart);
					quote.enterYearValueforLineItem("volumeYear2", "2006");
					quote.enterYearValueforLineItem("volumeYear3", "2008");
					quote.enterYearValueforLineItem("volumeYear4", "2009");
					quote.enterYearValueforLineItem("volumeYear5", "2003");
					quote.enterYearValueforLineItem("volumeYear6", "2005");
					quote.enterYearValueforLineItem("volumeYear7", "2011");
					commonLib.getScreenshot();
					commonLib.log(LogStatus.INFO, "All the line items are added with appropriate year values");
					Thread.sleep(1000);
					quote.clickOnCPQQuoteOperationsButton("Save");

					quote.clickOnCPQQuoteOperationsButton("Send to ASC Finance");
					String quoteStatusFin = quote.verifyQuoteStatusValue();
					commonLib.assertThat(quoteStatusFin.equalsIgnoreCase("5. FRP (Financial Review Process Team)"),
							"Quote Status Value is updated correctly to :" + quoteStatusFin,
							"Quote Status Value is not updated correctly to :" + quoteStatusFin);
					commonLib.getScreenshot();

					quote.clickQuoteTransactionLink("Financing Options");
					quote.selectFlexInfoReviewCheckox();
					quote.clickOnCPQQuoteOperationsButton("Deal Confirmed - Prep Proposal");
					quote.clickQuoteTransactionLink("Proposal");
					quote.selectProposalTemplateValue(templateName);
					quote.selectRadioButtonUnderProposal("Additional Attachments?");
					sfdcLib.uploadFileDirectly("SF_Quote_Select_File_XPATH", pdfFile);
					Thread.sleep(2000);
					sfdcLib.UploadFile(pdfFile);
					Thread.sleep(2000);
					quote.performPrintProposal();
					commonLib.getScreenshot();
					commonLib.assertThat(
							commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Proposal_PDF_XPATH", "TestPDF.pdf"),
							"Proposal file is generated", "Proposal file is not generated");
					commonLib.assertThat(sfdcLib.getNumberOfWindows() == 2, "Proposal file is printed",
							"Proposal file is not printed");
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
	public void quote_GenerateProposal_263029_TC_02() throws Exception {
		String testCaseID = new Object() {
		}.getClass().getEnclosingMethod().getName();
		System.out.println("Test case ID value is " + testCaseID);
		String packageName = AccountAndTerritories.class.getPackage().toString();
		System.out.println("Package name value is " + packageName);
		System.out.println("Worksheet name value is " + workSheet);
		String workBook = sfdcLib.getWorkbookNameWithEnvironment();
		System.out.println("Workbook value is " + workBook);
		String pdfFile = System.getProperty("user.dir") + "\\MyFiles\\TestPDF.pdf";
		// String pdfFile = System.getProperty("user.dir") + File.separator + "MyFiles"
		// + File.separator + "TestPDF.pdf";

		Map<Integer, List<String>> mapTestData = commonLib.readAllTestCaseData(workBook, workSheet, testCaseID, 1);
		List<String> headerRow = commonLib.getHeaderRow(workBook, workSheet);

		for (int rowKey : mapTestData.keySet()) {
			commonLib.syso("***** Iteration number: " + rowKey + " (Starts)*****");
			List<String> lstRowData = mapTestData.get(rowKey);
			String Run_Status = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Run"));
			String accountName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Account_Name"));
			String disposablePart = lstRowData
					.get(commonLib.getColumnNumberFromList(headerRow, "Disposable_Part_Number"));
			String capitalPart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Capital_Part_Number"));
			String servicePart = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Service_Part_Number"));
			String templateName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Template_Name"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO,
							testCaseID + "!W1! - 177448\\164788 - Output - Sending final proposal - Finance");
					login.loginToSFDC(workBook, 3, 3);
					accountAndTerr.clickAppFromHeader("Accounts");
					account.clickOnAccountNameLink(accountName);
					accountAndTerr.clickAppFromHeader("Opportunities");
					newOppName = oppor.createNewOpportunityWithAccountName(accountName, "Data Aggregation");
					quote.clickOnCreateQuote();
					quote.switchtoFrameUnderCPQScreen();
					quote.updateQuoteDescription("TestDescription");
					quote.clickOnCPQQuoteOperationsButton("Save");

					quote.addQuoteLine(disposablePart);
					quote.enterYearValueforLineItem("volumeYear1", "2007");
					Thread.sleep(2000);
					quote.addQuoteLine(capitalPart);
					quote.enterYearValueforLineItem("volumeYear2", "2012");
					Thread.sleep(2000);
					quote.addQuoteLine(servicePart);
					quote.enterYearValueforLineItem("volumeYear2", "2006");
					quote.enterYearValueforLineItem("volumeYear3", "2008");
					quote.enterYearValueforLineItem("volumeYear4", "2009");
					quote.enterYearValueforLineItem("volumeYear5", "2003");
					quote.enterYearValueforLineItem("volumeYear6", "2005");
					quote.enterYearValueforLineItem("volumeYear7", "2011");
					commonLib.getScreenshot();
					commonLib.log(LogStatus.INFO, "All the line items are added with appropriate year values");
					Thread.sleep(1000);
					quote.clickOnCPQQuoteOperationsButton("Save");

					quote.clickOnCPQQuoteOperationsButton("Send to ASC Finance");
					commonLib.getScreenshot();

					quote.clickQuoteTransactionLink("Financing Options");
					quote.selectFlexInfoReviewCheckox();
					quote.clickOnCPQQuoteOperationsButton("Deal Confirmed - Prep Proposal");
					quote.clickQuoteTransactionLink("Proposal");
					quote.selectProposalTemplateValue(templateName);
					quote.selectRadioButtonUnderProposal("Show ASC Capital Price");
					quote.selectRadioButtonUnderProposal("Show Total Capital Price");
					quote.selectRadioButtonUnderProposal("Show Stryker’s ASC Discount vs Avg Selling Price");
					quote.selectRadioButtonUnderProposal("Additional Attachments?");
					sfdcLib.uploadFileDirectly("SF_Quote_Select_File_XPATH", pdfFile);
					Thread.sleep(2000);
					commonLib.assertThat(
							commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Proposal_PDF_XPATH", "TestPDF.pdf"),
							"Proposal file is generated", "Proposal file is not generated");

					sfdcLib.UploadFile(pdfFile);
					Thread.sleep(2000);
					quote.performPrintProposal();
					Thread.sleep(3000);
					quote.attachPDFToOpporunity();
					commonLib.getScreenshot();
					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");
					Thread.sleep(5000);
					quote.verifyProposalPDFGenerationUnderQuotesPage();
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
	public void quote_GenerateProposal_263436_TC_03() throws Exception {
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
			String accountName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Account_Name"));
			String opporName = lstRowData
					.get(commonLib.getColumnNumberFromList(headerRow, "Opportunity_Name"));
			String quoteNumber = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Quote_Number"));
			String templateName = lstRowData.get(commonLib.getColumnNumberFromList(headerRow, "Template_Name"));

			if (Run_Status.equalsIgnoreCase("Yes")) {

				try {
					commonLib.startTest(testCaseID);
					commonLib.log(LogStatus.INFO,
							testCaseID + "!W1! - 177448\\164788  - Output - Sending final proposal - Sales RM");
					login.loginToSFDC(workBook, 2, 2);
					accountAndTerr.clickAppFromHeader("Accounts");
					account.clickOnAccountNameLink(accountName);
					sfdcLib.einsteinSearch("Opportunities", opporName);
					Thread.sleep(5000);
					quote.clickOnQuoteNumber(quoteNumber);
					quote.clickOnEditQuoteButton();
					quote.switchtoFrameUnderCPQScreen();
					quote.clickQuoteTransactionLink("Proposal");
					quote.selectProposalTemplateValue(templateName);
					
//					/*Uncomment below part if below radio button are not checked for the Quote under Proposal Tab*/
					
//					quote.selectRadioButtonUnderProposal("Show ASC Capital Price");
//					quote.selectRadioButtonUnderProposal("Show Total Capital Price");
//					quote.selectRadioButtonUnderProposal("Show Stryker’s ASC Discount vs Avg Selling Price");
//					quote.selectRadioButtonUnderProposal("Additional Attachments?");
//					sfdcLib.uploadFileDirectly("SF_Quote_Select_File_XPATH", pdfFile);
//					Thread.sleep(2000);
//					commonLib.assertThat(
//							commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Proposal_PDF_XPATH", "TestPDF.pdf"),
//							"Proposal file is generated", "Proposal file is not generated");//
//					sfdcLib.UploadFile(pdfFile);
					
					Thread.sleep(2000);
					quote.performPrintProposal();
					Thread.sleep(3000);
					quote.attachPDFToOpporunity();
					commonLib.getScreenshot();
					quote.clickOnCPQQuoteOperationsButton("Return to Opportunity");
					Thread.sleep(5000);
					quote.verifyProposalPDFGenerationUnderQuotesPage();

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
