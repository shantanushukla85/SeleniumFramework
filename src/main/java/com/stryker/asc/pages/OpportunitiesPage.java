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

	/**
	 * SHANTANUS5 openFormToCreateDivisionalOpporFromOppPage void Jun 24, 2022
	 */

	public void openFormToCreateDivisionalOpporFromOppPage() {
		commonLib.scroll_view("SF_ASCSales_oppor_viewMoreLink_XPATH");
		commonLib.scroll_view("SF_ASCSales_account_myAccount_notesAndAttachment_XPATH");
		commonLib.scroll_view("SF_ASCSales_account_oppor_stageHistory_XPATH");
		commonLib.scroll_view("SF_ASCSales_stageHistory_viewAll_XPATH");
		commonLib.scroll_view("SF_ASCSales_oppor_stageHistory_lastModified_XPATH");
		commonLib.scroll_view("SF_ASCSales_oppor_divisonalOpporLabel_XPATH");
		sfdcLib.click("SF_ASCSales_oppor_divisonalOppor_menuItemsButton_XPATH");
		sfdcLib.click("SF_ASCSales_newButtonForMenuItems_XPATH");
	}

	/**
	 * SHANTANUS5 createDivisonalOpportunities
	 * 
	 * @param stage
	 * @return
	 * @throws InterruptedException
	 * @throws AWTException         String Jun 24, 2022
	 */

	public String createDivisonalOpportunities(String stage) throws InterruptedException, AWTException {
		boolean flag = false;
		String accName1 = "Test" + System.currentTimeMillis();
		accName1 = "trinity_oppor" + accName1.substring(accName1.length() - 3);
		commonLib.sendKeys("SF_ASCSales_oppor_createNewPage_OppNameInput_XPATH", accName1);
		String startDate = commonLib.add_Minus_Date(0, "M/dd/yyyy");

		commonLib.sendKeys("SF_ASCSales_account_myAccount_newASCOwner_ascAccount_Input_XPATH",
				"ASC OF TRINITY 1520474");
		WebElement w1 = commonLib.driver.findElement(By.xpath("(//input[@placeholder='Search Accounts...'])[1]"));
		w1.sendKeys(Keys.RETURN);
		sfdcLib.click("SF_ASCSales_oppor_showAllResult_accountSearch_XPATH");
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
		Thread.sleep(5000);
		commonLib.log(LogStatus.INFO, "New opp name is " + accName1);
		commonLib.syso("New opp name is " + accName1);
		return accName1;
	}

	/**
	 * SHANTANUS5 createNewOpportunities
	 * 
	 * @param stage
	 * @return
	 * @throws InterruptedException
	 * @throws AWTException         String Jun 24, 2022
	 */

	public String createNewOpportunities(String stage) throws InterruptedException, AWTException {
		boolean flag = false;
		String accName1 = "Test" + System.currentTimeMillis();
		accName1 = "trinity_oppor" + accName1.substring(accName1.length() - 3);
		commonLib.sendKeys("SF_ASCSales_oppor_createNewPage_OppNameInput_XPATH", accName1);
		String startDate = commonLib.add_Minus_Date(0, "M/dd/yyyy");

		commonLib.sendKeys("SF_ASCSales_oppor_createNewPage_closeDateInput_XPATH", startDate);
		commonLib.click("SF_ASCSales_oppor_createNewPage_stage_XPATH");
		commonLib.clickWithDynamicValue("SF_ASCSales_oppor_createNewPage_stageNAme_dynamicText_XPATH", stage);
		commonLib.scroll_view("SF_ASCSales_oppor_createNewPage_hospitalDrDown_XPATH");
		sfdcLib.click("SF_ASCSales_oppor_createNewPage_hospitalDrDown_XPATH");
		sfdcLib.click("SF_ASCSales_oppor_createForm_hospital_XPATH");
		sfdcLib.click("SF_ASCSales_generalSave_nameSaveEdit_XPATH");

		flag = verifyElementExist("SF_ASCSales_toastMessageForSuccess_XPATH", "new oppourtunity created");
		Thread.sleep(5000);
		commonLib.log(LogStatus.INFO, "New opp name is " + accName1);
		commonLib.syso("New opp name is " + accName1);
		return accName1;
	}

	/**
	 * SHANTANUS5 createNewOpportunityWithAccountName: Method to create New
	 * Opportunity providing New Account Name
	 * 
	 * @param accountName
	 * @param stage
	 * @return
	 * @throws InterruptedException
	 * @throws AWTException         String Jun 24, 2022
	 */
	public String createNewOpportunityWithAccountName(String accountName, String stage)
			throws InterruptedException, AWTException {
		boolean flag;
		commonLib.waitForPresenceOfElementLocated("SF_New_Oppty_Button_XPATH");
		commonLib.click("SF_New_Oppty_Button_XPATH");
		String accName1 = "Test" + System.currentTimeMillis();
		accName1 = "trinity_oppor" + accName1.substring(accName1.length() - 3);
		commonLib.sendKeys("SF_ASCSales_oppor_createNewPage_OppNameInput_XPATH", accName1);
		commonLib.sendKeys("SF_Oppty_Account_Name_XPATH", accountName);
		commonLib.click("SF_Show_Result_SuggestionBox_XPATH");
		commonLib.clickbyjavascriptWithDynamicValue("SF_Account_Name_Link_XPATH", accountName);
		String startDate = commonLib.add_Minus_Date(0, "M/dd/yyyy");

		commonLib.sendKeys("SF_ASCSales_oppor_createNewPage_closeDateInput_XPATH", startDate);
		commonLib.click("SF_ASCSales_oppor_createNewPage_stage_XPATH");
		commonLib.clickWithDynamicValue("SF_ASCSales_oppor_createNewPage_stageNAme_dynamicText_XPATH", stage);

		commonLib.scroll_view("SF_ASCSales_oppor_createNewPage_hospitalDrDown_XPATH");
		sfdcLib.click("SF_ASCSales_oppor_createNewPage_hospitalDrDown_XPATH");

		sfdcLib.click("SF_ASCSales_oppor_createForm_hospital_XPATH");
		sfdcLib.click("SF_ASCSales_generalSave_nameSaveEdit_XPATH");
		flag = verifyElementExist("SF_ASCSales_toastMessageForSuccess_XPATH", "new oppourtunity created");
		Thread.sleep(5000);
		commonLib.log(LogStatus.INFO, "New Opportunity is create with name: " + accName1);
		commonLib.syso("New opp name is " + accName1);
		return accName1;
	}

	/**
	 * SHANTANUS5 addContactForOpportunities
	 * 
	 * @param oppName
	 * @return
	 * @throws InterruptedException boolean Jun 24, 2022
	 */
	public boolean addContactForOpportunities(String oppName) throws InterruptedException {
		commonLib.sendKeys("SF_ASCSales_opporSearchInput_XPATH", oppName);
		commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
		Thread.sleep(5000);
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_AccountInSearch_XPATH", oppName);
		commonLib.log(LogStatus.INFO, "Opp searched to create new contact");
		commonLib.getScreenshot();
		Thread.sleep(6000);
		commonLib.click("SF_ASCSales_oppor_contactRolesMenuItems_XPATH");
		commonLib.clickbyjavascript("SF_ASCSales_oppor_contactRoles_addNew_XPATH");
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_contact_XPATH");

		commonLib.click("SF_ASCSales_addContactRoles_contactBobForGeminiASC_XPATH");
		commonLib.click("SF_ASCSales_addContactRoles_showSelected_XPATH");
		commonLib.sendKeys("SF_ASCSales_oppor_searchContactInput_XPATH", "Bob Smith");
		sfdcLib.click("SF_ASCSales_nextButton_XPATH");
		commonLib.click("SF_ASCSales_oppor_addContact_Save_XPATH");
		boolean flag = commonLib.verifyPresence_Dynamic_Xpath("SF_ASCSales_generalTag_a_identifyBy_text_XPATH",
				"Bob Smith");
		commonLib.log(LogStatus.INFO, "new created contact exist " + flag + " name : Bob Smith");
		commonLib.getScreenshot();
		return flag;

	}

	/**
	 * SHANTANUS5 verifyElementExist
	 * 
	 * @param field
	 * @param msg
	 * @return boolean Jun 24, 2022
	 */

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

	/**
	 * SHANTANUS5 opeNewOrAddFromOpportunityRelatedListMenuItems
	 * 
	 * @return
	 * @throws InterruptedException
	 * @throws IOException          boolean Jun 24, 2022
	 */

	public boolean opeNewOrAddFromOpportunityRelatedListMenuItems() throws InterruptedException, IOException {
		boolean flag = false;
		commonLib.scroll_view("SF_ASCSales_account_flexContractsLAbel_XPATH");
		commonLib.scroll(0, 200);
		Thread.sleep(4000);
		commonLib.click("SF_ASCSales_Account_opporMenuItems_XPATH");
		commonLib.clickbyjavascript("SF_ASCSales_newButtonForMenuItems_XPATH");
		flag = commonLib.isDisplayed("SF_ASCSales_newFormExist_XPATH");
		return flag;
	}

	/**
	 * SHANTANUS5 openewOrAddFromRelatedListMenuItemsForOppor
	 * 
	 * @param relatedItem
	 * @param option
	 * @return boolean Jun 24, 2022
	 */

	public boolean openewOrAddFromRelatedListMenuItemsForOppor(String relatedItem, String option) {
		boolean flag = false;
		commonLib.clickWithDynamicValue("SF_ASCSales_opporRelatedList_menuItemsButton_XPATH", relatedItem);
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_opporRelatedList_menuItemsOptionToSelect_XPATH",
				option);
		flag = commonLib.isDisplayed("SF_ASCSales_newFormExist_XPATH");
		return flag;

	}

	/**
	 * SHANTANUS5 clickele
	 * 
	 * @param xpath void Jun 24, 2022
	 */

	public void clickele(String xpath) {
		commonLib.driver.findElement(By.xpath(xpath)).click();
	}

	/**
	 * SHANTANUS5 selectFromInputSearchForOpportunities
	 * 
	 * @param key1
	 * @param key2 void Jun 24, 2022
	 */

	public void selectFromInputSearchForOpportunities(String key1, String key2) {
		String xpath = "//div[@title='" + key1 + "']/following-sibling::div[@title='" + key2 + "']";

		clickele(xpath);
	}

	/**
	 * SHANTANUS5 clickactivityLinkOfOppor
	 * 
	 * @param activityName void Jun 24, 2022
	 */

	public void clickactivityLinkOfOppor(String activityName) {
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor__activityLink_general_XPATH", activityName);
	}

	/**
	 * SHANTANUS5 clickSpanUsingText
	 * 
	 * @param name void Jun 24, 2022
	 */

	public void clickSpanUsingText(String name) {
		commonLib.clickbyjavascriptWithDynamicValue(Subject_Name, name);
	}

	/**
	 * SHANTANUS5 clickOptionsTabForPartcularAccOrOpp
	 * 
	 * @param tabName void Jun 24, 2022
	 */

	public void clickOptionsTabForPartcularAccOrOpp(String tabName) {
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_click_optionsTabForPartcularAccOrOpp_XPATH", tabName);
	}

	/**
	 * SHANTANUS5 clickEditForTabsItemsforAccOrOppor
	 * 
	 * @param itemName void Jun 24, 2022
	 */

	public void clickEditForTabsItemsforAccOrOppor(String itemName) {
		commonLib.clickWithDynamicValue("SF_ASCSales_clickEditForTabsItems_forAccOrOppor_XPATH", itemName);
		if (itemName == "Stage") {
			sfdcLib.click("SF_ASCSales__oppor_DetailsStage_InputDropdown_XPATH");
		}
	}

	/**
	 * SHANTANUS5 editTabItemsOptions
	 * 
	 * @param tabName
	 * @param itemName
	 * @return boolean Jun 24, 2022
	 */

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

	/**
	 * SHANTANUS5 selectOpportunityByNameOnOpporPage
	 * 
	 * @param opporName
	 * @return String Jun 24, 2022
	 */

	public String selectOpportunityByNameOnOpporPage(String opporName) {
		commonLib.clickbyjavascriptWithDynamicValue("SF_App_Header_Tab_Link_XPATH", opporName);
		commonLib.waitForPresenceOfElementLocated("SF_ASCSales_oppor__activity_SendFollow_link_XPATH");
		return opporName;
	}

	/**
	 * SHANTANUS5 viewAllForOpporLeftSideRelatedListItem
	 * 
	 * @param relatedListItemName void Jun 24, 2022
	 */

	public void viewAllForOpporLeftSideRelatedListItem(String relatedListItemName) {
		commonLib.clickWithDynamicValue("SF_ASCSales_viewAllForLeftSideRelatedListItems_XPATH", relatedListItemName);
	}

	/**
	 * SHANTANUS5 selectTabfirstAndThenClickItemToActionOn
	 * 
	 * @param tabName
	 * @param itemName
	 * @throws InterruptedException
	 * @throws IOException          void Jun 24, 2022
	 */

	public void selectTabfirstAndThenClickItemToActionOn(String tabName, String itemName)
			throws InterruptedException, IOException {
		clickOptionsTabForPartcularAccOrOpp(tabName);
		clickEditForTabsItemsforAccOrOppor(itemName);
	}

	/**
	 * SHANTANUS5 selectStageFromDetailTabByName
	 * 
	 * @param stageName void Jun 24, 2022
	 */

	public void selectStageFromDetailTabByName(String stageName) {
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor_createNewPage_stageNAme_dynamicText_XPATH",
				stageName);

	}

	/**
	 * SHANTANUS5 verifyError_HitASnag_ExistAndCloseWhileFillingForm void Jun 24,
	 * 2022
	 */

	public void verifyError_HitASnag_ExistAndCloseWhileFillingForm() {
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_account_myAccount_newASCOwner_saveError_XPATH"),
				"error present", "error not present");
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_closeError_XPATH");

	}

	/**
	 * SHANTANUS5 selectClosedWonOrLostReasonByMoveSelection
	 * 
	 * @param reasonName void Jun 24, 2022
	 */

	public void selectClosedWonOrLostReasonByMoveSelection(String reasonName) {
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor_stageClosedWon/LostReason_dynamicText_XPATH",
				reasonName);
		if (reasonName == "Closed Won") {
			sfdcLib.click("SF_ASCSales_oppor_moveSelectionToChosen_last_XPATH");
		} else {
			sfdcLib.click("SF_ASCSales_oppor_moveSelectionToChosen_forClosedLost_XPATH");
		}
	}

	/**
	 * SHANTANUS5 createNewDealSummaryOfClosedWonByFillingForm
	 * 
	 * @param deal1
	 * @param deal2
	 * @param deal3
	 * @param oppName void Jun 24, 2022
	 */

	public void createNewDealSummaryOfClosedWonByFillingForm(String deal1, String deal2, String deal3, String oppName) {
		String First_Name = "TestFirst" + commonLib.getRandomNumber(4);
		String Last_Name = "TestLast" + commonLib.getRandomNumber(4);

		sfdcLib.click("SF_ASCSales_oppor_dealSummaryInputOnDetailPage_XPATH");
		sfdcLib.click("SF_ASCSales_oppor_newDealSummaryLink_XPATH");
		sfdcLib.clickWithDynamicValue("SF_ASCSales_oppor_newDealSummary_typeOfCheckBox_XPATH", "Closed Won Reason");
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
		sfdcLib.clickWithDynamicValue("SF_ASCSales_general_divWithTitle_XPATH", oppName);
		String dealBU = "Bone Cement;Trauma";
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
		sfdcLib.click("SF_ASCSales_oppor_newDealForm_Save_XPATH");
	}

	/**
	 * SHANTANUS5 createNewDealSummaryOffClosedLostByFillingForm
	 * 
	 * @param deal1
	 * @param deal2
	 * @param deal3
	 * @param oppName
	 * @throws InterruptedException void Jun 24, 2022
	 */

	public void createNewDealSummaryOffClosedLostByFillingForm(String deal1, String deal2, String deal3, String oppName)
			throws InterruptedException {
		String First_Name = "TestFirst" + commonLib.getRandomNumber(4);
		String Last_Name = "TestLast" + commonLib.getRandomNumber(4);

		sfdcLib.click("SF_ASCSales_oppor_dealSummaryInputOnDetailPage_XPATH");
		sfdcLib.click("SF_ASCSales_oppor_newDealSummaryLink_XPATH");
		sfdcLib.clickWithDynamicValue("SF_ASCSales_oppor_newDealSummary_typeOfCheckBox_XPATH", "Closed Lost Reason");
		sfdcLib.click("SF_ASCSales_nextButton_XPATH");
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_powerBU_XPATH"),
				"power BU element present", "power BU element not present");
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_pullthroughBU_XPATH"),
				"pull through BU element present", "pull through BU element not present");
		commonLib.softAssertThat(
				commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_proposedFinalDealStruct_XPATH"),
				"Proposed Final Deal Structure (Flex) element present",
				"Proposed Final Deal Structure (Flex) element not present");
		commonLib.softAssertThat(
				commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_proposedFinalDealStructnonFlex_XPATH"),
				"Proposed Final Deal Structure (Non-Flex) element present",
				"Proposed Final Deal Structure (Non-Flex) element not present");
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_feedbackPushback_XPATH"),
				"Feedback/pushback and SYK response element present",
				"Feedback/pushback and SYK response element not present");
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_impendingCustomerEvent_XPATH"),
				"Impending Customer Event element present", "Impending Customer Event element not present");
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_navPoliticsASC_XPATH"),
				"Navigating politics of the ASC element present", "Navigating politics of the ASC element not present");
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_compWhoWonBusiness_XPATH"),
				"Competitors who won the business element present",
				"Competitors who won the business element not present");
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_whatToWinDeal_XPATH"),
				"What could we have done differently to win the deal? element present",
				"What could we have done differently to win the deal? element not present");

		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor_newDealSummary_powerBUOptionToSelect_XPATH",
				deal1);
		sfdcLib.click("SF_ASCSales_oppor_moveSelectionToChosen_3_onNewDealSummary_XPATH");

		commonLib.scroll_view("SF_ASCSales_oppor_newDealSummary_labelPullThroughBU_XPATH");
		commonLib.clickbyjavascriptWithDynamicValue(
				"SF_ASCSales_oppor_newDealSummary_pullthroughBUOptionToSelect_XPATH", deal2);
		sfdcLib.click("SF_ASCSales_oppor_moveSelectionToChosen_4_onNewDealSummary_XPATH");

		commonLib.scroll_view("SF_ASCSales_oppor_newDealSummary_label_competitorsWhoWon_XPATH");
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor_newDealSummary_competitorsOptionToSelect_XPATH",
				deal3);
		sfdcLib.click("SF_ASCSales_oppor_moveSelectionToChosen_5_onNewDealSummary_XPATH");

		commonLib.scroll_view("SF_ASCSales_oppor_newDealSummary_opportunityInputBox_XPATH");
		sfdcLib.click("SF_ASCSales_oppor_newDealSummary_opportunityInputBox_XPATH");
		sfdcLib.clickWithDynamicValue("SF_ASCSales_general_divWithTitle_XPATH", oppName);

		String dealBU = "Bone Cement;Trauma";
		commonLib.sendKeys_DynamicValue("SF_ASCSales_oppor_newDealSummary_inputGeneral_XPATH", "Deal BU", dealBU);

		sfdcLib.click("SF_ASCSales_oppor_newDealForm_Save_XPATH");
		accountAndTerr.verifyElementExist("SF_ASCSales_toastMessageForSuccess_XPATH", "Deal summary created");
		Thread.sleep(10000);

	}

	/**
	 * SHANTANUS5 verifyUsers void Jun 24, 2022
	 */

	public void verifyUsers() {

		sfdcLib.click("SF_ASCSales_finaceTM_showmoreActions_XPATH");
		commonLib.verifyElementPresence("SF_ASCSales_finaceTM_edit_XPATH");
		commonLib.verifyElementPresence("SF_ASCSales_finaceTM_delete_XPATH");
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_finaceTM_user_XPATH"),
				"ASC Finance TM is present with Read/Write access", "ASC Finance TM is not");

		sfdcLib.click("SF_ASCSales_pricingTM_showmoreActions_XPATH");
		commonLib.verifyElementPresence("SF_ASCSales_finaceTM_edit_XPATH");
		commonLib.verifyElementPresence("SF_ASCSales_finaceTM_delete_XPATH");
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_pricingTM_user_XPATH"),
				"ASC Pricing TM is present with Read/Write access", "ASC Pricing TM is not");

		sfdcLib.click("SF_ASCSales_marketingTM_showmoreActions_XPATH");
		commonLib.verifyElementPresence("SF_ASCSales_finaceTM_edit_XPATH");
		commonLib.verifyElementPresence("SF_ASCSales_finaceTM_delete_XPATH");
		commonLib.softAssertThat(commonLib.isDisplayed("SF_ASCSales_marketingTM_user_XPATH"),
				"ASC Marketing TM is present with Read/Write access", "ASC Marketing TM is not");
	}

	/**
	 * SHANTANUS5 verifynewDealSummaryclosedReasonList
	 * 
	 * @param expectedOptionList
	 * @return boolean Jun 24, 2022
	 */

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

	/**
	 * SHANTANUS5 getValuesInList
	 * 
	 * @param field
	 * @return List<String> Jun 24, 2022
	 */

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

	/**
	 * SHANTANUS5 searchOpportunityAndFoundStageName
	 * 
	 * @param oppName
	 * @return
	 * @throws InterruptedException String Jun 24, 2022
	 */

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
	}

	/**
	 * SHANTANUS5 viewAllTasksForActivitesOfOppor void Jun 24, 2022
	 */

	public void viewAllTasksForActivitesOfOppor() {
		boolean flag = false;
		clickOptionsTabForPartcularAccOrOpp("Activity");
		flag = commonLib.isDisplayed("SF_ASCSales_oppor_viewAllButton_XPATH");
		if (flag) {
			commonLib.clickbyjavascript("SF_ASCSales_oppor_viewAllButton_XPATH");
		}

	}

	/**
	 * SHANTANUS5 makrkOpporStageAsCompleteAndVerifyStageName
	 * 
	 * @param oppName
	 * @return
	 * @throws InterruptedException String Jun 24, 2022
	 */

	public String makrkOpporStageAsCompleteAndVerifyStageName(String oppName) throws InterruptedException {

		commonLib.clickbyjavascript("SF_ASCSales_oppor_makrStageAsComplete_XPATH");
		accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_toastMessageForSuccess_XPATH", "Stage changed ");
		String currentStage = openOppPageSearchOpportunityAndFoundStage(oppName);
		return currentStage;
	}

	/**
	 * SHANTANUS5 openOppPageSearchOpportunityAndFoundStage
	 * 
	 * @param oppName
	 * @return
	 * @throws InterruptedException String Jun 24, 2022
	 */

	public String openOppPageSearchOpportunityAndFoundStage(String oppName) throws InterruptedException {
		accountAndTerr.clickAppFromHeader("Opportunities");
		String currentStage = searchOpportunityAndFoundStageName(oppName);
		return currentStage;
	}

	/**
	 * SHANTANUS5 markStageAsCurrent
	 * 
	 * @param stageName
	 * @param oppName
	 * @return
	 * @throws InterruptedException String Jun 24, 2022
	 */

	public String markStageAsCurrent(String stageName, String oppName) throws InterruptedException {
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor_Stage_General_XPATH", stageName);
		commonLib.clickbyjavascript("SF_ASCSales_oppor_makrAsCurrentStage_XPATH");
		accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_toastMessageForSuccess_XPATH",
				"Stage changed to" + stageName);
		String currentStage = openOppPageSearchOpportunityAndFoundStage(oppName);
		return currentStage;
	}

	/**
	 * SHANTANUS5 markStageAsComplete
	 * 
	 * @param stageName
	 * @param oppName
	 * @return
	 * @throws InterruptedException String Jun 24, 2022
	 */

	public String markStageAsComplete(String stageName, String oppName) throws InterruptedException {
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_oppor_Stage_General_XPATH", stageName);
		commonLib.clickbyjavascript("SF_ASCSales_oppor_makrStageAsComplete_XPATH");
		accountAndTerr.verifyElementExistWithSoftAssert("SF_ASCSales_toastMessageForSuccess_XPATH",
				"Stage changed to" + stageName);
		String currentStage = openOppPageSearchOpportunityAndFoundStage(oppName);
		return currentStage;
	}

	/**
	 * SHANTANUS5 saveOpporFromDetailsPage void Jun 24, 2022
	 */

	public void saveOpporFromDetailsPage() {
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");

		if (commonLib.isElementExists(("SF_ASCSales_oppor_generalCancelButon_XPATH"))) {
			sfdcLib.click("SF_ASCSales_oppor_generalCancelButon_XPATH");
		}
	}

	/**
	 * SHANTANUS5 CancelAndCloseNewDealSummaryForm void Jun 24, 2022
	 */

	public void CancelAndCloseNewDealSummaryForm() {
		sfdcLib.click("SF_ASCSales_oppor_newDealSummary_cancel_XPATH");
	}

	/**
	 * SHANTANUS5 verifyroleErrorForStageChange
	 * 
	 * @return boolean Jun 24, 2022
	 */

	public boolean verifyroleErrorForStageChange() {
		boolean flag = false;
		flag = commonLib.isDisplayed("SF_ASCSales_opporStageChangeErrorForUserRole_XPATH");
		return flag;
	}

	/**
	 * SHANTANUS5 compare2List
	 * 
	 * @param l1
	 * @param l2 void Jun 24, 2022
	 */

	public void compare2List(List l1, List l2) {
		boolean flag = l1.equals(l2);
		commonLib.softAssertThat(flag, "Pass - Expected list options are present" + l1,
				"Fail - Expected list options are not present: " + l1);

	}

	/**
	 * SHANTANUS5 absenceOfElements
	 * 
	 * @throws InterruptedException void Jun 24, 2022
	 */

	public void absenceOfElements() throws InterruptedException {
		commonLib.assertThat(!commonLib.isDisplayed("Opportunity Currency"), "Field is displayed",
				"Field is not displayewd");
		commonLib.assertThat(!commonLib.isDisplayed("IDN number"), "Field is displayed", "Field is not displayewd");
		commonLib.assertThat(!commonLib.isDisplayed("IDN Affiliation"), "Field is displayed",
				"Field is not displayewd");
		commonLib.assertThat(!commonLib.isDisplayed("Total Annual spend "), "Field is displayed",
				"Field is not displayewd");
		commonLib.assertThat(!commonLib.isDisplayed("Amount"), "Field is displayed", "Field is not displayewd");
		commonLib.assertThat(!commonLib.isDisplayed("Service Sell price"), "Field is displayed",
				"Field is not displayewd");
		commonLib.assertThat(!commonLib.isDisplayed("Number of Rooms"), "Field is displayed",
				"Field is not displayewd");
		commonLib.assertThat(!commonLib.isDisplayed("Type"), "Field is displayed", "Field is not displayewd");
		commonLib.assertThat(!commonLib.isDisplayed("additional information section"), "Field is displayed",
				"Field is not displayewd");
		commonLib.assertThat(!commonLib.isDisplayed("Product quote ID"), "Field is displayed",
				"Field is not displayewd");
		commonLib.assertThat(!commonLib.isDisplayed("Product quote process name "), "Field is displayed",
				"Field is not displayewd");
	}

	/**
	 * SHANTANUS5 newDealSummaryError
	 * 
	 * @return boolean Jun 24, 2022
	 */

	public boolean newDealSummaryError() {
		boolean flag = false;
		flag = commonLib.isDisplayed("SF_ASCSales_oppor_newDealSummary_error_XPATH");
		return flag;
	}

	/**
	 * SHANTANUS5 getAllActivitiesTasksInList
	 * 
	 * @return List<String> Jun 24, 2022
	 */

	public List<String> getAllActivitiesTasksInList() {
		List<String> taskList = getValuesInList("SF_ASCSales_oppor_activityList_XPATH");
		return taskList;
	}

	/**
	 * SHANTANUS5 verifyASCPricingUserExistinOpprTeam
	 * 
	 * @return boolean Jun 24, 2022
	 */

	public boolean verifyASCPricingUserExistinOpprTeam() {
		boolean flag = false;
		sfdcLib.click("SF_ASCSales_accountTeam_memberRoleColOrderBy_XPATH");
		return flag = commonLib.verifyElementPresence("SF_ASCSales_UserPricing_XPATH");
	}

	/**
	 * SHANTANUS5 addNewOpporTeamMember void Jun 24, 2022
	 */

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

	/**
	 * SHANTANUS5 openOpporFromLinkOnTaskPage void Jun 24, 2022
	 */

	public void openOpporFromLinkOnTaskPage() {
		sfdcLib.click("SF_ASCSales_Account_opporLinkOnActivityPAge_XPATH");
	}

	/**
	 * SHANTANUS5 verifyTasksForIntialStageOfOppor void Jun 24, 2022
	 */

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
		flag = accountAndTerr.verifyElementExist("SF_ASCSales_Account_oppor_dueDateDetail_XPATH", "due date exist");

		flag = commonLib.isDisplayed("SF_ASCSales_oppor_closeButtonFor_activityMouseHoverPopUp_XPATH");
		if (flag) {
			sfdcLib.click("SF_ASCSales_oppor_closeButtonFor_activityMouseHoverPopUp_XPATH");
		}
	}

	/**
	 * SHANTANUS5 createNewOpptyFromAccount void Jun 24, 2022
	 */
	public void createNewOpptyFromAccount() {
		commonLib.waitForPresenceOfElementLocated("SF_Opportunites_Expander_Button_XPATH");
		commonLib.click("SF_Opportunites_Expander_Button_XPATH");
		commonLib.waitForVisibilityOf("SF_New_Oppty_Button_XPATH");
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "New Opportunity button is displayed");
		commonLib.click("SF_New_Oppty_Button_XPATH");

	}

	/**
	 * SHANTANUS5 updateOpportynityStageValue
	 * 
	 * @param stageName void Jun 24, 2022
	 */
	public void updateOpportynityStageValue(String stageName) {
		try {
			commonLib.waitForElementToBeClickable_Dynamic("SF_ASCSales_opporTabName_XPATH", "Details");
			commonLib.clickWithDynamicValue("SF_ASCSales_opporTabName_XPATH", "Details");
			commonLib.performHoverandClickDynamic("SF_ASCSales_opporTabName_XPATH", "Details");
			commonLib.log(LogStatus.INFO, "Clicked successfully on Detail Tab");
			Thread.sleep(2000);
			commonLib.waitForElementToBeClickable("SF_ASCSales_opporEditStage_Button_XPATH");
			commonLib.click("SF_ASCSales_opporEditStage_Button_XPATH");
			commonLib.waitForVisibilityOf("SF_ASCSales_Oppty_Save_Button_XPATH");
			commonLib.waitForElementToBeClickable("SF_ASCSales_Stage_Dropdown_Button_XPATH");
			commonLib.click("SF_ASCSales_Stage_Dropdown_Button_XPATH");
			commonLib.waitForElementToBeClickable_Dynamic("SF_ASCSales_Stage_Dropdown_Value_XPATH", stageName);
			commonLib.clickWithDynamicValue("SF_ASCSales_Stage_Dropdown_Value_XPATH", stageName);
			commonLib.log(LogStatus.INFO, "Successfully updated opportunity stage value to: " + stageName);
			commonLib.getScreenshot();
			commonLib.click("SF_ASCSales_Oppty_Save_Button_XPATH");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * SHANTANUS5 clickOnDetailsTab
	 * 
	 * @throws InterruptedException void Jun 24, 2022
	 */
	public void clickOnDetailsTab() throws InterruptedException {
		commonLib.waitForVisibilityOf("SF_ASCSales_oppor_DetailsTab_XPATH");
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "Details Tab is visible under Opportunity screen");
		commonLib.click("SF_ASCSales_oppor_DetailsTab_XPATH");
		commonLib.waitForPageToLoad();
		Thread.sleep(3000);
		commonLib.log(LogStatus.INFO, "Successfully cliked on Details Tab under Opportunity screen");
	}

	/**
	 * SHANTANUS5 fetchFieldNamesUnderOpptyScreen: Method to return list of Field
	 * Available Under Oppty Details Screen
	 * 
	 * @return List<String> Jun 24, 2022
	 */
	@SuppressWarnings("null")
	public List<String> fetchFieldNamesUnderOpptyScreen() {

		List<WebElement> labels = commonLib.findElements("SF_ASCSales_Oppty_Label_Name_XPATH");
		List<String> labelStrings = new ArrayList<String>();

		for (int i = 0; i <= labels.size() - 1; i++) {
			String element = labels.get(i).getText();
			labelStrings.add(element);
		}
		commonLib.getScreenshot();
		return labelStrings;

	}
}
