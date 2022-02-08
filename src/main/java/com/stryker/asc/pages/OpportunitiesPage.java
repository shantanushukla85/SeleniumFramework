package com.stryker.asc.pages;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.stryker.salesforceLibrary.SfdcLibrary;
import com.stryker.star.CommonLibrary;

/**
 * @author anshumans
 *
 */
/**
 * @author anshumans
 *
 */
public class OpportunitiesPage {

	private CommonLibrary commonLib;
	Date date = new Date();
	long ts = date.getTime();
	Timestamp timeStamp = new Timestamp(date.getTime());
	String Subject_Name = "Automation Task " + timeStamp.toString();
	SfdcLibrary sfdcLib = new SfdcLibrary(commonLib);
	ExtentTest parentTest;
	AccountAndTerritoriesPage accountAndTerr = new AccountAndTerritoriesPage(commonLib);

	public OpportunitiesPage(CommonLibrary commonLib) {
		this.commonLib = commonLib;
		this.sfdcLib = new SfdcLibrary(commonLib);
		this.accountAndTerr = new AccountAndTerritoriesPage(commonLib);
	}

	public void openFormToCreateDivisionalOpporFromOppPage() {
		commonLib.scroll_view("SF_ASCSales_oppor_viewMoreLink_XPATH");
		commonLib.scroll_view("SF_ASCSales_account_myAccount_notesAndAttachment_XPATH");
		commonLib.scroll_view("SF_ASCSales_account_oppor_stageHistory_XPATH");
		commonLib.scroll_view("SF_ASCSales_stageHistory_viewAll_XPATH");
		// commonLib.scroll(0, 200);
		commonLib.scroll_view("SF_ASCSales_oppor_stageHistory_lastModified_XPATH");
		commonLib.scroll_view("SF_ASCSales_oppor_divisonalOpporLabel_XPATH");
		sfdcLib.click("SF_ASCSales_oppor_divisonalOppor_menuItemsButton_XPATH");
		sfdcLib.click("SF_ASCSales_newButtonForMenuItems_XPATH");
	}

	public String createDivisonalOpportunities(String stage) throws InterruptedException, AWTException {
		boolean flag = false;
		String accName1 = "Test" + System.currentTimeMillis();
		accName1 = "trinity_oppor" + accName1.substring(accName1.length() - 3);
		commonLib.sendKeys("SF_ASCSales_oppor_createNewPage_OppNameInput_XPATH", accName1);
		// sfdcLib.click("SF_ASCSales_oppor_createNewPage_searchAcc_XPATH");
		// sfdcLib.click("span", "title", "GEMINI ASC");
		String startDate = commonLib.add_Minus_Date(0, "M/dd/yyyy");

		commonLib.sendKeys("SF_ASCSales_account_myAccount_newASCOwner_ascAccount_Input_XPATH",
				"ASC OF TRINITY 1520474");
		WebElement w1 = commonLib.driver.findElement(By.xpath("(//input[@placeholder='Search Accounts...'])[1]"));
		w1.sendKeys(Keys.RETURN);
		// commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
		sfdcLib.click("SF_ASCSales_oppor_showAllResult_accountSearch_XPATH");
		// commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
		Thread.sleep(5000);

		sfdcLib.click("SF_ASCSales_oppor_divOpprPAge_firstSearchFromAcc_XPATH");
		commonLib.sendKeys("SF_ASCSales_oppor_createNewPage_closeDateInput_XPATH", startDate);
		commonLib.click("SF_ASCSales_oppor_createNewPage_stage_XPATH");
		commonLib.clickWithDynamicValue("SF_ASCSales_oppor_createNewPage_stageNAme_dynamicText_XPATH", stage);

		commonLib.scroll_view("SF_ASCSales_oppor_createNewPage_hospitalDrDown_XPATH");
		sfdcLib.click("SF_ASCSales_oppor_createNewPage_hospitalDrDown_XPATH");

		sfdcLib.click("SF_ASCSales_oppor_createForm_hospital_XPATH");
		sfdcLib.click("SF_ASCSales_generalSave_nameSaveEdit_XPATH");

		flag = verifyElementExist("SF_ASCSales_toastMessageForSuccess_XPATH", "new oppourtunity created");
		/*
		 * if (flag) {
		 * 
		 * String xpath = "//lightning-formatted-text[text()='" + accName1 + "']"; //
		 * flag= commonLib.isDisplayed(xpath); flag =
		 * commonLib.driver.findElement(By.xpath(xpath)).isDisplayed(); if (!flag) {
		 * commonLib.reportUtil.log(LogStatus.FAIL, "new Opp not created");
		 * commonLib.getScreenshot(); } else { commonLib.reportUtil.log(LogStatus.PASS,
		 * "new Opp  created"); commonLib.getScreenshot(); } }
		 */
		Thread.sleep(5000);
		commonLib.log(LogStatus.INFO, "New opp name is " + accName1);
		commonLib.syso("New opp name is " + accName1);
		return accName1;
	}
	
	public String createNewOpportunities(String stage) throws InterruptedException, AWTException {
		boolean flag = false;
		String accName1 = "Test" + System.currentTimeMillis();
		accName1 = "trinity_oppor" + accName1.substring(accName1.length() - 3);
		commonLib.sendKeys("SF_ASCSales_oppor_createNewPage_OppNameInput_XPATH", accName1);
		// sfdcLib.click("SF_ASCSales_oppor_createNewPage_searchAcc_XPATH");
		// sfdcLib.click("span", "title", "GEMINI ASC");
		String startDate = commonLib.add_Minus_Date(0, "M/dd/yyyy");

		commonLib.sendKeys("SF_ASCSales_oppor_createNewPage_closeDateInput_XPATH", startDate);
		commonLib.click("SF_ASCSales_oppor_createNewPage_stage_XPATH");
		commonLib.clickWithDynamicValue("SF_ASCSales_oppor_createNewPage_stageNAme_dynamicText_XPATH", stage);

		commonLib.scroll_view("SF_ASCSales_oppor_createNewPage_hospitalDrDown_XPATH");
		sfdcLib.click("SF_ASCSales_oppor_createNewPage_hospitalDrDown_XPATH");

		sfdcLib.click("SF_ASCSales_oppor_createForm_hospital_XPATH");
		sfdcLib.click("SF_ASCSales_generalSave_nameSaveEdit_XPATH");

		flag = verifyElementExist("SF_ASCSales_toastMessageForSuccess_XPATH", "new oppourtunity created");
		/*
		 * if (flag) {
		 * 
		 * String xpath = "//lightning-formatted-text[text()='" + accName1 + "']"; //
		 * flag= commonLib.isDisplayed(xpath); flag =
		 * commonLib.driver.findElement(By.xpath(xpath)).isDisplayed(); if (!flag) {
		 * commonLib.reportUtil.log(LogStatus.FAIL, "new Opp not created");
		 * commonLib.getScreenshot(); } else { commonLib.reportUtil.log(LogStatus.PASS,
		 * "new Opp  created"); commonLib.getScreenshot(); } }
		 */
		Thread.sleep(5000);
		commonLib.log(LogStatus.INFO, "New opp name is " + accName1);
		commonLib.syso("New opp name is " + accName1);
		return accName1;
	}


	public boolean addContactForOpportunities(String oppName) throws InterruptedException {
		// accountAndTerr.sendKeys("SF_ASCSales_opporSearchInput_XPATH", oppName);
		commonLib.sendKeys("SF_ASCSales_opporSearchInput_XPATH", oppName);
		commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
		Thread.sleep(5000);
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_AccountInSearch_XPATH", oppName);
		commonLib.log(LogStatus.INFO, "Opp searched to create new contact");
		commonLib.getScreenshot();

		// accountAndTerr.verifyElementExist("//span[@title='Contact Roles']", "exist");
		Thread.sleep(6000);
		commonLib.click("SF_ASCSales_oppor_contactRolesMenuItems_XPATH");
		commonLib.clickbyjavascript("SF_ASCSales_oppor_contactRoles_addNew_XPATH");

		// sfdcLib.click("input", "placeholder", "Search Contacts...");
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_contact_XPATH");

		commonLib.click("SF_ASCSales_addContactRoles_contactBobForGeminiASC_XPATH");
		commonLib.click("SF_ASCSales_addContactRoles_showSelected_XPATH");
		commonLib.sendKeys("SF_ASCSales_oppor_searchContactInput_XPATH", "Bob Smith");

		// sfdcLib.click("SF_ASCSales_oppor_createNewContactPage_firstCheckBox_XPATH");
		// sfdcLib.click("span", "text", "Next");
		sfdcLib.click("SF_ASCSales_nextButton_XPATH");
		commonLib.click("SF_ASCSales_oppor_addContact_Save_XPATH");
		boolean flag = commonLib.verifyPresence_Dynamic_Xpath("SF_ASCSales_generalTag_a_identifyBy_text_XPATH",
				"Bob Smith");
		commonLib.log(LogStatus.INFO, "new created contact exist " + flag + " name : Bob Smith");
		commonLib.getScreenshot();
		return flag;

	}

	public boolean verifyElementExist(String field, String msg) {
		boolean isPresent = false;
		if (commonLib.isDisplayed(field)) {
			commonLib.log(LogStatus.INFO, field + " found on page");
			return isPresent = true;

		} else {
			commonLib.log(LogStatus.INFO, field + " not found on page");
		}
		return isPresent;

	}

	public boolean opeNewOrAddFromOpportunityRelatedListMenuItems() throws InterruptedException, IOException {
		boolean flag = false;
		// commonLib.scroll(0, 200);
		commonLib.scroll_view("SF_ASCSales_account_flexContractsLAbel_XPATH");
		commonLib.scroll(0, 200);
		// commonLib.scroll_view("SF_ASCSales_account_ligalNameLabel_XPATH");
		Thread.sleep(4000);
		// commonLib.clickbyjavascript("SF_ASCSales_account_viewAllForOppor_XPATH");
		commonLib.click("SF_ASCSales_Account_opporMenuItems_XPATH");
		// Thread.sleep(4000);
		// co//mmonLib.performHover("SF_ASCSales_account_contact_newButon_XPATH");
		commonLib.clickbyjavascript("SF_ASCSales_newButtonForMenuItems_XPATH");
		// ommonLib.sendke
		// div text New title
		// sfdcLib.click("SF_ASCSales_oppor_vieAllPage_newButton_XPATH");
		flag = commonLib.isDisplayed("SF_ASCSales_newFormExist_XPATH");
		return flag;
	}

	public boolean openewOrAddFromRelatedListMenuItemsForOppor(String relatedItem, String option) {
		boolean flag = false;
		commonLib.clickWithDynamicValue("SF_ASCSales_opporRelatedList_menuItemsButton_XPATH", relatedItem);
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_opporRelatedList_menuItemsOptionToSelect_XPATH",
				option);

		/*
		 * sfdcLib.click("SF_ASCSales_Account_opporMenuItems_XPATH");
		 * sfdcLib.clickbyjavascript("SF_ASCSales_account_contact_newButon_XPATH"
		 * );
		 */
		flag = commonLib.isDisplayed("SF_ASCSales_newFormExist_XPATH");
		return flag;

	}

	public void clickele(String xpath) {
		commonLib.driver.findElement(By.xpath(xpath)).click();
	}

	public void selectFromInputSearchForOpportunities(String key1, String key2) {
		String xpath = "//div[@title='" + key1 + "']/following-sibling::div[@title='" + key2 + "']";

		clickele(xpath);
	}

	public void clickactivityLinkOfOppor(String activityName) {
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor__activityLink_general_XPATH", activityName);
	}

	public void clickSpanUsingText(String name) {
		commonLib.clickbyjavascriptWithDynamicValue(Subject_Name, name);
	}

	public void clickOptionsTabForPartcularAccOrOpp(String tabName) {

		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_click_optionsTabForPartcularAccOrOpp_XPATH", tabName);

	}

	public void clickEditForTabsItemsforAccOrOppor(String itemName) {
		commonLib.clickWithDynamicValue("SF_ASCSales_clickEditForTabsItems_forAccOrOppor_XPATH", itemName);
		if (itemName == "Stage") {
			sfdcLib.click("SF_ASCSales__oppor_DetailsStage_InputDropdown_XPATH");
		}
	}

	public boolean editTabItemsOptions(String tabName, String itemName) {
		boolean flag = false;
		String text = "Test" + System.currentTimeMillis();
		text = "EnterAcc" + text.substring(text.length() - 3);
		String textToEnter = "ChangeValue" + text;
		clickOptionsTabForPartcularAccOrOpp(tabName);
		clickEditForTabsItemsforAccOrOppor(itemName);
		commonLib.clear("SF_ASCSales_textAreaAfterEdit_forTabsItem_XPATH");
		commonLib.sendKeys("SF_ASCSales_textAreaAfterEdit_forTabsItem_XPATH", textToEnter);
		commonLib.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
		String xpath = "//lightning-formatted-text[@data-output-element-id='output-field' and text()='" + textToEnter
				+ "']";
		flag = commonLib.driver.findElement(By.xpath(xpath)).isDisplayed();
		return flag;
	}

	public void selectOpportunityByNameOnOpporPage(String opporName) {
		commonLib.clickbyjavascriptWithDynamicValue("SF_App_Header_Tab_Link_XPATH", opporName);
		commonLib.waitForPresenceOfElementLocated("SF_ASCSales_oppor__activity_SendFollow_link_XPATH");

	}

	public void viewAllForOpporLeftSideRelatedListItem(String relatedListItemName) {
		commonLib.clickWithDynamicValue("SF_ASCSales_viewAllForLeftSideRelatedListItems_XPATH", relatedListItemName);
	}

	public void selectTabfirstAndThenClickItemToActionOn(String tabName, String itemName)
			throws InterruptedException, IOException {
		clickOptionsTabForPartcularAccOrOpp(tabName);

		clickEditForTabsItemsforAccOrOppor(itemName);

	}

	public void selectStageFromDetailTabByName(String stageName) {
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor_createNewPage_stageNAme_dynamicText_XPATH",
				stageName);

	}

	public void verifyError_HitASnag_ExistAndCloseWhileFillingForm() {
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_account_myAccount_newASCOwner_saveError_XPATH"),
				"error present", "error not present");
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_closeError_XPATH");

	}

	public void selectClosedWonOrLostReasonByMoveSelection(String reasonName) {
		// sfdcLib.click("SF_ASCSales_oppor_stageClosedWon/LostReason_dynamicText_XPATH");
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor_stageClosedWon/LostReason_dynamicText_XPATH",
				reasonName);
		if (reasonName == "Closed Won") {
			sfdcLib.click("SF_ASCSales_oppor_moveSelectionToChosen_last_XPATH");
		} else {
			sfdcLib.click("SF_ASCSales_oppor_moveSelectionToChosen_forClosedLost_XPATH");
		}
	}

	public void createNewDealSummaryOfClosedWonByFillingForm(String deal1, String deal2, String deal3) {
		String First_Name = "TestFirst" + commonLib.getRandomNumber(4);
		String Last_Name = "TestLast" + commonLib.getRandomNumber(4);

		sfdcLib.click("SF_ASCSales_oppor_dealSummaryInputOnDetailPage_XPATH");
		sfdcLib.click("SF_ASCSales_oppor_newDealSummaryLink_XPATH");
		sfdcLib.clickWithDynamicValue("SF_ASCSales_oppor_newDealSummary_typeOfCheckBox_XPATH", "Closed Won");
		sfdcLib.click("SF_ASCSales_nextButton_XPATH");
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_powerBU_XPATH"),
				"power BU element present", "power BU element not present");
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_pullthroughBU_XPATH"),
				"pull through BU element present", "pull through BU element not present");

		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor_newDealSummary_powerBUOptionToSelect_XPATH",
				deal1);
		sfdcLib.click("SF_ASCSales_oppor_moveSelectionToChosen_3_onNewDealSummary_XPATH");
		commonLib.clickbyjavascriptWithDynamicValue(
				"SF_ASCSales_oppor_newDealSummary_pullthroughBUOptionToSelect_XPATH", deal2);
		sfdcLib.click("SF_ASCSales_oppor_moveSelectionToChosen_4_onNewDealSummary_XPATH");
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor_newDealSummary_LeftOutBUsOptionToSelect_XPATH",
				deal3);
		sfdcLib.click("SF_ASCSales_oppor_moveSelectionToChosen_5_onNewDealSummary_XPATH");
		commonLib.scroll_view("SF_ASCSales_oppor_newDealSummary_input_investorGrp_XPATH");
		commonLib.sendKeys("SF_ASCSales_oppor_newDealSummary_input_investorGrp_XPATH", "inv1");
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_general_InputPlaceHolder_XPATH",
				"Search Opportunities...");
		sfdcLib.click("SF_ASCSales_oppor_newDealSummary_opportunityInputBox_XPATH");
		String dealBU = "Joint Replacement;Mako;Trauma;Trauma 2;Trauma 3;Trauma 4;Trauma 5;Endoscopy;Sports Medicine;Communications;Orthopaedic Instruments;Surgical Technologies;Ear, Nose & Throat;Interventional Spine;Neurosurgical;Medical;Spine";
		commonLib.sendKeys_DynamicValue("SF_ASCSales_oppor_newDealSummary_inputGeneral_XPATH", "Deal BU", dealBU);
		commonLib.sendKeys_DynamicValue("SF_ASCSales_oppor_newDealSummary_inputGeneral_XPATH", "First Name",
				First_Name);
		commonLib.sendKeys_DynamicValue("SF_ASCSales_oppor_newDealSummary_inputGeneral_XPATH", "Last Name", Last_Name);
		commonLib.sendKeys_DynamicValue("SF_ASCSales_oppor_newDealSummary_inputGeneral_XPATH", "Email",
				"anshuman.sharma@stryker.com");
		commonLib.sendKeys_DynamicValue("SF_ASCSales_oppor_newDealSummary_inputGeneral_XPATH", "Resource First Name",
				First_Name);
		commonLib.sendKeys_DynamicValue("SF_ASCSales_oppor_newDealSummary_inputGeneral_XPATH", "Resource Last Name",
				Last_Name);
		commonLib.sendKeys_DynamicValue("SF_ASCSales_oppor_newDealSummary_inputGeneral_XPATH", "Resource Email",
				"anshuman.sharma@stryker.com");
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
	}

	public void createNewDealSummaryOffClosedLostByFillingForm(String deal1, String deal2, String deal3, String oppName)
			throws InterruptedException {
		String First_Name = "TestFirst" + commonLib.getRandomNumber(4);
		String Last_Name = "TestLast" + commonLib.getRandomNumber(4);

		sfdcLib.click("SF_ASCSales_oppor_dealSummaryInputOnDetailPage_XPATH");
		sfdcLib.click("SF_ASCSales_oppor_newDealSummaryLink_XPATH");
		sfdcLib.clickWithDynamicValue("SF_ASCSales_oppor_newDealSummary_typeOfCheckBox_XPATH",
				"Closed Lost Reason");
		sfdcLib.click("SF_ASCSales_nextButton_XPATH");
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_powerBU_XPATH"),
				"power BU element present", "power BU element not present");
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_pullthroughBU_XPATH"),
				"pull through BU element present", "pull through BU element not present");

		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor_newDealSummary_powerBUOptionToSelect_XPATH",
				deal1);
		sfdcLib.click("SF_ASCSales_oppor_moveSelectionToChosen_3_onNewDealSummary_XPATH");

		// commonLib.scroll_view("SF_ASCSales_oppor_newDealSummary_opportunityInputBox_XPATH");
		commonLib.scroll_view("SF_ASCSales_oppor_newDealSummary_labelPullThroughBU_XPATH");
		commonLib.clickbyjavascriptWithDynamicValue(
				"SF_ASCSales_oppor_newDealSummary_pullthroughBUOptionToSelect_XPATH", deal2);
		sfdcLib.click("SF_ASCSales_oppor_moveSelectionToChosen_4_onNewDealSummary_XPATH");

		commonLib.scroll_view("SF_ASCSales_oppor_newDealSummary_label_competitorsWhoWon_XPATH");
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor_newDealSummary_competitorsOptionToSelect_XPATH",
				deal3);
		sfdcLib.click("SF_ASCSales_oppor_moveSelectionToChosen_5_onNewDealSummary_XPATH");

		commonLib.scroll_view("SF_ASCSales_oppor_newDealSummary_opportunityInputBox_XPATH");
		// commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_general_InputPlaceHolder_XPATH",
		// "Search Opportunities...");
		sfdcLib.click("SF_ASCSales_oppor_newDealSummary_opportunityInputBox_XPATH");
		sfdcLib.clickWithDynamicValue("SF_ASCSales_general_divWithTitle_XPATH", oppName);

		String dealBU = "Joint Replacement;Mako";
		commonLib.sendKeys_DynamicValue("SF_ASCSales_oppor_newDealSummary_inputGeneral_XPATH", "Deal BU", dealBU);

		sfdcLib.click("SF_ASCSales_oppor_newDealForm_Save_XPATH");
		accountAndTerr.verifyElementExist("SF_ASCSales_toastMessageForSuccess_XPATH", "Deal sumary created");
		Thread.sleep(10000);

	}

	public boolean verifynewDealSummaryclosedReasonList(List<String> expectedOptionList) {
		boolean flag;
		List<WebElement> actualHeaderList = commonLib
				.findElements("SF_ASCSales_oppor_newDealSummary_closedReasonList_XPATH");
		List<String> actualHeaderListValue = new ArrayList<String>();

		for (WebElement el : actualHeaderList) {
			String data = el.getText().trim();
			actualHeaderListValue.add(data);
		}

		flag = actualHeaderListValue.containsAll(expectedOptionList);
		commonLib.softAssertThat(flag, "Pass - Expected list options are present" + actualHeaderListValue,
				"Fail - Expected list options are not present: " + actualHeaderListValue);

		commonLib.getScreenshot();

		return flag;
	}

	public List<String> getValuesInList(String field) {
		boolean flag;
		List<WebElement> actualHeaderList = commonLib.findElements(field);
		List<String> actualHeaderListValue = new ArrayList<String>();

		for (WebElement el : actualHeaderList) {
			String data = el.getText().trim();
			actualHeaderListValue.add(data);
		}

		commonLib.log(LogStatus.INFO, "current activity list is " + actualHeaderListValue);

		return actualHeaderListValue;
	}

	public String searchOpportunityAndFoundStageName(String oppName) throws InterruptedException {
		commonLib.sendKeys("SF_ASCSales_searchOpporInputBox_XPATH", oppName);
		commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
		Thread.sleep(5000);
		String xpath1 = "(//a[@title='" + oppName + "'])[last()]/ancestor::tr/td[4]/span/span";
		String xpath2 = "(//a[@title='" + oppName + "'])[last()]";
		String stageName = commonLib.driver.findElement(By.xpath(xpath1)).getText();
		commonLib.log(LogStatus.INFO, "Curent Stage found as" + stageName);
		commonLib.driver.findElement(By.xpath(xpath2)).click();
		return stageName;
		// clickWithDynamicValue("SF_ASCSales_firstAccountInSearch_XPATH", accName);
		// commonLib.refreshPage();
	}

	public void viewAllTasksForActivitesOfOppor() {
		boolean flag = false;
		clickOptionsTabForPartcularAccOrOpp("Activity");
		flag = commonLib.isDisplayed("SF_ASCSales_oppor_viewAllButton_XPATH");
		if (flag) {
			sfdcLib.click("SF_ASCSales_oppor_viewAllButton_XPATH");
		}

	}

	public String makrkOpporStageAsCompleteAndVerifyStageName(String oppName) throws InterruptedException {

		commonLib.clickbyjavascript("SF_ASCSales_oppor_makrStageAsComplete_XPATH");
		accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_toastMessageForSuccess_XPATH", "Stage changed ");

		String currentStage = openOppPageSearchOpportunityAndFoundStage(oppName);
		return currentStage;
	}

	public String openOppPageSearchOpportunityAndFoundStage(String oppName) throws InterruptedException {
		accountAndTerr.clickAppFromHeader("Opportunities");
		String currentStage = searchOpportunityAndFoundStageName(oppName);
		return currentStage;
	}

	public String markStageAsCurrent(String stageName, String oppName) throws InterruptedException {
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor_Stage_General_XPATH", stageName);
		commonLib.clickbyjavascript("SF_ASCSales_oppor_makrAsCurrentStage_XPATH");
		accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_toastMessageForSuccess_XPATH",
				"Stage changed to" + stageName);
		String currentStage = openOppPageSearchOpportunityAndFoundStage(oppName);
		return currentStage;
	}

	public String markStageAsComplete(String stageName, String oppName) throws InterruptedException {
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor_Stage_General_XPATH", stageName);
		commonLib.clickbyjavascript("SF_ASCSales_oppor_makrStageAsComplete_XPATH");
		accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_toastMessageForSuccess_XPATH",
				"Stage changed to" + stageName);
		String currentStage = openOppPageSearchOpportunityAndFoundStage(oppName);
		return currentStage;
	}

	public void saveOpporFromDetailsPage() {
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");

		if (commonLib.isElementExists(("SF_ASCSales_oppor_generalCancelButon_XPATH"))) {
			sfdcLib.click("SF_ASCSales_oppor_generalCancelButon_XPATH");
		}
	}

	public void CancelAndCloseNewDealSummaryForm() {
		sfdcLib.click("SF_ASCSales_oppor_newDealSummary_cancel_XPATH");
	}

	public boolean verifyroleErrorForStageChange() {
		boolean flag = false;
		flag = commonLib.isDisplayed("SF_ASCSales_opporStageChangeErrorForUserRole_XPATH");
		return flag;
	}

	public void compare2List(List l1, List l2) {
		boolean flag = l1.equals(l2);
		commonLib.softAssertThat(flag, "Pass - Expected list options are present" + l1,
				"Fail - Expected list options are not present: " + l1);

	}

	public boolean newDealSummaryError() {
		boolean flag = false;
		flag = commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_error_XPATH");
		return flag;
	}

	public List<String> getAllActivitiesTasksInList() {
		List<String> taskList = getValuesInList("SF_ASCSales_oppor_activityList_XPATH");
		return taskList;
	}

	public boolean verifyASCPricingUserExistinOpprTeam() {
		boolean flag = false;
		sfdcLib.click("SF_ASCSales_accountTeam_memberRoleColOrderBy_XPATH");
		sfdcLib.click("SF_ASCSales_accountTeam_memberRoleColOrderBy_XPATH");
		return flag = commonLib.verifyElementPresence("SF_ASCSales_UserPricing_XPATH");
	}

	public void addNewOpporTeamMember() {
		boolean flag = false;
		openewOrAddFromRelatedListMenuItemsForOppor("Opportunity Team", "Add Opportunity Team Members");
		commonLib.clickWithDynamicValue("SF_ASCSales_oppor_addOppTeamMem_buttonGeneral_XPATH", "Edit User: Item 1");

		commonLib.sendKeys("SF_ASCSales_oppor_addOppTem_SearchPeople_XPATH", "Paige Bossle");
		selectFromInputSearchForOpportunities("Paige Bossley", "Sales Account Manager");
		// commonLib.click("SF_ASCSales_oppor_addOppTem_SearchPeople_XPATH");
		commonLib.clickWithDynamicValue("SF_ASCSales_oppor_addOppTeamMem_buttonGeneral_XPATH",
				"Edit Team Role: Item 1");
		commonLib.click("SF_ASCSales_oppor_addOppTeamMem_roleNone_XPATH");
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor_addOppTeamMem_teamRoleAccEx_XPATH",
				"Account Executive");
		commonLib.click("SF_ASCSales_oppor_addOppTeamMem_saveButton_XPATH");
		flag = accountAndTerr.verifyElementExist("SF_ASCSales_toastMessageForSuccess_XPATH",
				"Opportunity team member added with success");
	}

	public void openOpporFromLinkOnTaskPage() {
		sfdcLib.click("SF_ASCSales_Account_opporLinkOnActivityPAge_XPATH");
	}

	public void verifyTasksForIntialStageOfOppor() {
		boolean flag = false;
		commonLib.waitForPresenceOfElementLocated("SF_ASCSales_oppor__activity_SendFollow_link_XPATH");
		commonLib.waitForElementToBeClickable("SF_ASCSales_oppor__activity_SendFollow_link_XPATH");
		commonLib.clickbyjavascript("SF_ASCSales_oppor__activity_SendFollow_link_XPATH");
		flag = accountAndTerr.verifyText("SF_ASCSales_Account_oppor_subjectDetail_XPATH",
				"Send follow up note to customer with action items");
		flag = accountAndTerr.verifyElementExist("SF_ASCSales_Account_oppor_dueDateDetail_XPATH", "due date exist");

		openOpporFromLinkOnTaskPage();

		commonLib.clickbyjavascript("SF_ASCSales_oppor__activity_buildOppor_link_XPATH");
		// flag =
		// accountAndTerr.verifytext("SF_ASCSales_Account_oppor_subjectDetail_XPATH","Build
		// opportunity team");
		flag = accountAndTerr.verifyElementExist("SF_ASCSales_Account_oppor_dueDateDetail_XPATH", "due date exist");

		flag = commonLib.isDisplayed("SF_ASCSales_oppor_closeButtonFor_activityMouseHoverPopUp_XPATH");
		if (flag) {
			sfdcLib.click("SF_ASCSales_oppor_closeButtonFor_activityMouseHoverPopUp_XPATH");
		}
	}
}
