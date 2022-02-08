package com.stryker.asc.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
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
public class AccountAndTerritoriesPage {

	private CommonLibrary commonLib;
	Date date = new Date();
	long ts = date.getTime();
	Timestamp timeStamp = new Timestamp(date.getTime());
	String Subject_Name = "Automation Task " + timeStamp.toString();
	SfdcLibrary sfdcLib = new SfdcLibrary(commonLib);
	ExtentTest parentTest;

	public AccountAndTerritoriesPage(CommonLibrary commonLib) {
		this.commonLib = commonLib;
		this.sfdcLib = new SfdcLibrary(commonLib);
	}

	/////////// Ansh

	

	/**anshumans
	UploadFile
	@param imagePath
	@throws AWTException
	void
	Jan 4, 2022
	*/
	public void UploadFile(String imagePath) throws AWTException {
		// copying File path to Clipboard
		StringSelection str = new StringSelection(imagePath);
		// Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(str, null);
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		robot.delay(250);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(150);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	

	/**anshumans
	verifyElementExistWithDynamicText
	@param dynamicText
	@return
	boolean
	Jan 4, 2022
	*/
	public boolean verify_element_spanWithText_Exist(String dynamicText) {
		boolean isPresent = false;
		String xpath = "//span[text()='" + dynamicText + "']";
		isPresent = commonLib.driver.findElements(By.xpath(xpath)).size() > 0;
		return isPresent;
	}

	/**anshumans
	tabs
	@param i
	void
	Jan 4, 2022
	*/
	public void switchTodesiredTabFromOpenBrowserTab(int i) {
		ArrayList<String> tabs = null;
		tabs = new ArrayList<String>(commonLib.driver.getWindowHandles());
		commonLib.driver.switchTo().window(tabs.get(i));
	}
	

	
	/**anshumans
	proxyLoginUsingUser
	@param user
	@param role
	@throws InterruptedException
	void
	Jan 4, 2022
	*/
	public void proxyLoginUsingUser(String user, String role) throws InterruptedException {
		sfdcLib.click_Setup_FromHomePage_AfterLogin();
		sfdcLib.search_Profile(user);
		commonLib.log(LogStatus.PASS, "Logged in to ASC UAt as " + role);
		commonLib.getScreenshot();
	}
	
	
	  public void proxyLoginUsingUser2(String accName) throws
	  InterruptedException {
		  
	 	  
	  if (commonLib.isDisplayed("SF_ASCSales_setUpUserPage_searchAtTop_XPATH"))
	  {
	  commonLib.sendkeys_by_JavaScript("SF_ASCSales_setUpUserPage_searchAtTop_XPATH",
			  accName); 
	  commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
	  Thread.sleep(5000); 
	  }
	  else {
		  sfdcLib.click("SF_ASCSales_searchAtTopOfPage_XPATH");
			
			  commonLib.sendkeys_by_JavaScript("SF_ASCSales_placeHolderForTopSearch_XPATH",
			  accName); 
			  commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
			  Thread.sleep(5000); 
		  
	  }
	  
	
	  sfdcLib.clickWithDynamicValue("SF_ASCSales_proxyUserNameLink_XPATH", accName);
	  Thread.sleep(5000); 
	  //sfdcLib.click("div", "title", "User Detail");
	  if (commonLib.isDisplayed("SF_ASCSales_setUpUserPage_userDetailButton_XPATH"))
	  {
	  sfdcLib.click("SF_ASCSales_setUpUserPage_userDetailButton_XPATH");
	  }
	  //commonLib.waitforInvisibilityOfElement("SF_ASCSales_loadingStatus_XPATH");
	
	  commonLib.waitforFramenadSwitch("Profile_login_Frame_XPATH");
	  Thread.sleep(3000);
		/*
		 * commonLib.waitForPresenceOfElementLocated("Profile_login_XPATH");
		 * commonLib.waitForElementToBeClickable("Profile_login_XPATH");
		 * 
		 * commonLib.getScreenshot();
		 * commonLib.sfdcLib.clickbyjavascript("Profile_login_XPATH");
		 */
		sfdcLib.click("SF_ASCSales_buttonToLogin_XPATH");
		//commonLib.waitForPageToLoad();
		//Thread.sleep(3000);
		//commonLib.waitforInvisibilityOfElement("SF_ASCSales_loadingStatus_XPATH");
		commonLib.waitForPresenceOfElementLocated("SF_ASCSales_navigationHeader_XPATH");
		//commonLib.waitForPresenceOfElementLocated("SF_Header_StykerLogo_XPATH");
	  }
	 

	/**anshumans
	selectAppFromLeftSideIconWaffle
	@param appName
	@throws InterruptedException
	void
	Jan 4, 2022
	*/
	public void selectAppFromLeftSideIconWaffle(String appName) throws InterruptedException {
		commonLib.refreshPage();
		//String xpath = "//p[@class='slds-truncate']/b[text()='" + appName + "']";		
		sfdcLib.click("SF_ASCSales_appLink_XPATH");
		Thread.sleep(4000);
		commonLib.sendkeys_by_JavaScript("SF_ASCSales_appSearchInput_XPATH", appName);	
		sfdcLib.clickWithDynamicValue("SF_ASC_selectDesiredAppFrom_sldTruncate_XPATH", appName);
		//commonLib.driver.findElement(By.xpath(xpath)).sfdcLib.click();		
		commonLib.waitForPresenceOfElementLocated("SF_ASCSales_imgASCLightApp_XPATH");
	}

	
	/**anshumans
	sfdcLib.clickAppFromHeader
	@param app
	void
	Jan 4, 2022
	 * @throws InterruptedException 
	*/
	public boolean clickAppFromHeader(String app) throws InterruptedException {
		boolean flag= false;
		commonLib.refreshPage();
		String xpath2="//span[contains(@class,'slds-var-p-right_x-small') and text()='" + app + "']";
		commonLib.clickbyjavascriptWithDynamicValue("SF_App_Header_Tab_Link_XPATH", app);
		flag= commonLib.driver.findElement(By.xpath(xpath2)).isDisplayed();
		Thread.sleep(3000);
		commonLib.log(LogStatus.INFO, "Successfully sfdcLib.clicked on app header tab" + app);
		return flag;

	}	
	
	/**anshumans
	gen
	@return
	int
	Jan 4, 2022
	*/
	public int genRandomNumber() {
		Random r = new Random(System.currentTimeMillis());
		return ((1 + r.nextInt(2)) * 40000 + r.nextInt(10000));
	}

	/**anshumans
	verifyAccountExistAfterSearchFromTopSearch
	@param accName
	@return
	boolean
	Jan 4, 2022
	*/
	public boolean verifyAccountExistAfterSearchFromTopSearch(String accName) {
		boolean exist = false;
		commonLib.refreshPage();
		String xpath = "//a[@title='" + accName + "' and @class='displayLabel slds-truncate']";

		if (commonLib.driver.findElement(By.xpath(xpath)).isDisplayed())
			exist = true;
		return exist;

	}

	/**anshumans
	addTerrMemberInRegion
	@param userName
	@param permission
	void
	Jan 4, 2022
	*/
	public void addTerrMemberInRegion(String userName, String permission) {
		sfdcLib.click("SF_ASCSales_Region_NewButtonForTerrMem_XPATH");
		sfdcLib.click("SF_ASCSales_Region_newTerrMemPage_User_XPATH");
		commonLib.sendKeys("SF_ASCSales_Region_newTerrMemPage_User_XPATH", userName);
		String xpath = "//span[contains(@class,'slds-listbox')]/lightning-base-combobox-formatted-text[@title='"
				+ userName + "']";
		// commonLib.driver.findElement(By.xpath(xpath)).sfdcLib.click();
		commonLib.driver.findElement(By.xpath(xpath)).click();
		sfdcLib.click("SF_ASCSales_Region_newTerrMemPage_Role_XPATH");
		sfdcLib.click("SF_ASCSales_Region_newTerrMemPage_Role_salesRep_XPATH");
		sfdcLib.click("SF_ASCSales_Region_newTerrMemPage_AccPermission_XPATH");

		// sfdcLib.click("SF_ASCSales_Region_newTerrMemPage_Perm_Edit_XPATH");
		commonLib.driver.findElement(By.xpath("//span[@title='" + permission + "']")).click();
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
		verifyElementExistWithSoftAssert("SF_ASCSales_toastMessageForSuccess_XPATH", "succes message");
		
	}

	

	/**anshumans
	searchAccountByNameFromAccountPage
	@param accNAme
	@throws InterruptedException
	void
	Jan 4, 2022
	*/
	public void searchAccountByNameFromAccountPage(String accNAme) throws InterruptedException {
		commonLib.sendKeys("SF_ASCSales_searchAccountInputBox_XPATH", accNAme);
		commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
		Thread.sleep(5000);
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_AccountInSearch_XPATH", accNAme);
	}
	
	public void searchAccountByNameFromAccountPage(String accNAme, String accNum) throws InterruptedException {
		commonLib.sendKeys("SF_ASCSales_searchAccountInputBox_XPATH", accNAme+ accNum);
		commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
		Thread.sleep(5000);
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_AccountInSearch_XPATH", accNAme);
	}
	
	public void openNewAscOwnerForm()
	{
		sfdcLib.click("SF_ASCSales_account_myAccount_ascOwner_menuItems_XPATH");
		sfdcLib.click("SF_ASCSales_account_myAccount_ascOwner_newButton_XPATH");
	}
	
	public boolean addNewASCOwner()
	{
		boolean flag;
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_typeOfOwner_XPATH");
		verifyText("SF_ASCSales_account_myAccount_newASCOwner_typeOfOwner_item1_XPATH",
				"Surgeon");
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_typeOfOwner_item1_XPATH");

		commonLib.sendKeys("SF_ASCSales_account_myAccount_newASCOwner_ownerPercentage_XPATH", "11");
		// commonLib.KeyPress_Tab();
		commonLib.scrollDownToElement("SF_ASCSales_account_myAccount_newASCOwner_contact_XPATH", "input");

		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_contact_XPATH");
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_contact_sample_XPATH");

		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
		// boolean ss =
		// accountAndTerr.verifyElementExist("SF_ASCSales_account_myAccount_newASCOwner_saveError_XPATH");

		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_closeError_XPATH");

		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_clearContact_XPATH");
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_clearAccount_XPATH");
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_ascAccount_sample_XPATH");
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
		return flag=verifyElementExistByWait("SF_ASCSales_account_myAccount_newASCOwner_successCreate_XPATH", null);
	}
	
	public String createNewEnterpriseAccByAdmin()
	{
		sfdcLib.click("SF_ASCSales_account_newButton_XPATH");
		sfdcLib.click("SF_ASCSales_account_typeEnterpriseAcc_checkBox_XPATH");
		sfdcLib.click("SF_ASCSales_nextButton_XPATH");

		int enterpriseID = commonLib.randomInteger(2, 9);
		String EID = String.valueOf(enterpriseID);
		String accName = commonLib.getRandomString("abt");

		commonLib.sendKeys("SF_ASCSales_newAccount_AccNameInput_XPATH", "Test" + accName);
		
	
		commonLib.scroll_view("SF_ASCSales_newAccount_enterpriseID_XPATH");
		commonLib.sendKeys("SF_ASCSales_newAccount_enterpriseID_XPATH", EID);
		
		commonLib.scroll_view("SF_ASCSales_newAccount_postalCode_XPATH");
		commonLib.sendKeys("SF_ASCSales_newAccount_postalCode_XPATH", "59001");
		
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");

		commonLib.waitForPresenceOfElementLocated("SF_ASCSales_GetCreatedAccName_XPATH");
		String accNumCreated = commonLib.getText("SF_ASCSales_GetCreatedAccName_XPATH");
		return accNumCreated;
	}
	
	public String createNewEnterpriseAccByASCSalesRep()
	{
		sfdcLib.click("SF_ASCSales_account_newButton_XPATH");
		sfdcLib.click("SF_ASCSales_account_typeEnterpriseAcc_checkBox_XPATH");
		sfdcLib.click("SF_ASCSales_nextButton_XPATH");

		//int enterpriseID = commonLib.randomInteger(2, 9);
		//String EID = String.valueOf(enterpriseID);
		//String accName = commonLib.getRandomString("abt");
		String accName1 = "Test" + System.currentTimeMillis();
		accName1 = "EnterAcc" + accName1.substring(accName1.length() - 3);

		commonLib.sendKeys("SF_ASCSales_newAccount_AccNameInput_XPATH", accName1);
		sfdcLib.click("SF_ASCSales_entPriseAccForm_countryListBox_XPATH");
		sfdcLib.clickWithDynamicValue("SF_ASCSales_general_spanWithTitle_XPATH", "United States");
		
		commonLib.scroll_view("SF_ASCSales_newAccount_shippingpostalCode_XPATH");
		commonLib.sendKeys("SF_ASCSales_newAccount_shippingpostalCode_XPATH", "75001-1234");
		
		commonLib.scroll_view("SF_ASCSales_newAccount_postalCode_XPATH");
		commonLib.sendKeys("SF_ASCSales_newAccount_postalCode_XPATH","75001-1234");
		
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");

		commonLib.waitForPresenceOfElementLocated("SF_ASCSales_GetCreatedAccName_XPATH");
		commonLib.log(LogStatus.INFO, "created enterprise acc by name :" +accName1);
		commonLib.getScreenshot();
		return accName1;
	}
	
	public boolean openRegionAndRelatdTab(String rgnNAme) throws InterruptedException
	{
		boolean flag;
	clickAppFromHeader("Regions");
	commonLib.clickWithDynamicValue("SF_ASCSales_Region_DynamicName_XPATH", rgnNAme);
	sfdcLib.click("SF_ASCSales_Region_RelatedTab_XPATH");
	flag=verifyElementExistWithSoftAssert("SF_ASCSales_Region_presenceof_zipcode_XPATH",
			"Zip Code Section");
	return flag;
	}
	
	public void searchAccountFromTopSearch(String accName) throws InterruptedException
	{
		commonLib.refreshPage();
	//sfdcLib.clickbyjavascript("SF_ASCSales_searchAtTopOfPage_XPATH");
	sfdcLib.click("SF_ASCSales_searchAtTopOfPage_XPATH");
	commonLib.sendkeys_by_JavaScript("SF_ASCSales_placeHolderForTopSearch_XPATH", accName);
	commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
	Thread.sleep(5000);
}
	public String createNewContactForAccount() throws InterruptedException, IOException
	{
		commonLib.scroll_view("SF_ASCSales_account_leftDesirdItems_label_FlexContact_XPATH");
		 commonLib.scroll_view("SF_ASCSales_account_leftDesirdItems_label_Opportunities_XPATH");
commonLib.scroll_view("SF_ASCSales_account_leftDesirdItems_label_OpportunityProduct_XPATH");

		String accName1 = "Test" + System.currentTimeMillis();
		accName1 = "EnterAcc" + accName1.substring(accName1.length() - 3);
	sfdcLib.click("SF_ASCSales_account_contact_showMoreActionButton_XPATH");
	//sfdcLib.click2("SF_ASCSales_account_contact_newButon_XPATH");
	sfdcLib.click("SF_ASCSales_newButtonForMenuItems_XPATH");
	sfdcLib.click("SF_ASCSales_nextButton_XPATH");
	commonLib.sendKeys("SF_ASCSales_account_contact_newPage_firstNameInput_XPATH", "Cont"+accName1);
	commonLib.sendKeys("SF_ASCSales_account_contact_newPage_lastNameInput_XPATH", "lastN");
	sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
	String nameUsed= "Cont"+accName1+ " lastN";
	return nameUsed;
	}
	///////////  5 jan
	public boolean createNewActivityForOppAndVerify()
	{
		boolean flag=false;
	//sfdcLib.clickbyjavascript("span", "text", ", "Add");
	sfdcLib.clickWithDynamicValue("SF_generalEle_spanWithText_XPATH", "Add");
	
	String startDate = commonLib.add_Minus_Year(0, "M/dd/yyyy");
	sfdcLib.click("SF_ASCSales_oppor_newActivity_subject_XPATH");
	
	//sfdcLib.click("span", "text", "Log Activity");
	sfdcLib.clickWithDynamicValue("SF_generalEle_spanWithText_XPATH", "Log Activity");
	
	commonLib.sendKeys("SF_ASCSales_oppor_newActivity_dueDate_XPATH", startDate);
	
	sfdcLib.click("SF_ASCSales_oppor_newActivity_save_XPATH");
	
	flag=verifyElementExist("SF_ASCSales_toastMessageForSuccess_XPATH",
			"new activity created");
		 if(flag)
		 {
				String ele=commonLib.getText("SF_ASCSales_toastMessageForSuccess_XPATH");
				commonLib.log(LogStatus.INFO, ele + " created");
				flag= verifyElementExist("SF_ASCSales_oppor_newActivity_subjectName_XPATH", "new activity");
		 }
	 return flag;
	}
	
	

	
	
	public String createNewManagemenTypeAccount()
	{	
		String accName1 = "Test" + System.currentTimeMillis();
		accName1 = "EnterAcc" + accName1.substring(accName1.length() - 3);
	sfdcLib.click("SF_ASCSales_account_newButton_XPATH");
	sfdcLib.click("SF_ASCSales_account_typeManagementType_checkBox_XPATH");
	sfdcLib.click("SF_ASCSales_nextButton_XPATH");
	commonLib.sendKeys("SF_ASCSales_newAccount_AccNameInput_XPATH", accName1);
	sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
	boolean result = verifyText("SF_ASCSales_account_toGetName_XPATH", accName1);
	commonLib.softAssertThat(result, " exist", " not exist");
	return accName1;
	}

	public void clickAppHeaderTab(String tabName) {
		commonLib.refreshPage();
		commonLib.clickbyjavascriptWithDynamicValue("SF_App_Header_Tab_Link_XPATH", tabName);
		commonLib.log(LogStatus.INFO, "Successfully sfdcLib.clicked on app header tab" + tabName);
	}
	
	public boolean verifyFieldExist(String field, String key) throws InterruptedException {
		boolean flag=false;
		if(key==null)
		{
			flag=commonLib.waitForVisibilityOf(field);
			commonLib.getScreenshot();
			if(flag) {
				flag=commonLib.waitForPresenceOfElementLocated(field);
			}
		}
		
		else {
		flag=commonLib.waitForVisibilityOf_DynamicXpath(field, key);
		commonLib.getScreenshot();
		if(flag) {
			flag=commonLib.WaitforPresenceofElement_Dynamic_Xpath(field, key);
		}
		}
		return flag;
		}
	
	
	/////////////////////
	
	
	
	public boolean verifyElementExist(String field, String msg) {
		boolean isPresent = false;
		if(commonLib.isDisplayed(field))
		{
			commonLib.log(LogStatus.INFO, field + " found on page");
			commonLib.getScreenshot();
		return isPresent= true;
		
		}
		else {
			commonLib.log(LogStatus.INFO, field + " not found on page");
			commonLib.getScreenshot();
		}
		return isPresent;

	}
	
	public boolean verifyElementExistWithSoftAssert(String element, String elementName) {
		boolean exist = false;
		
		if (commonLib.waitForPresenceOfElementLocated(element)) {
			exist = true;
		}
		commonLib.softAssertThat(exist, "Field/Element " + elementName + "exist",
				"Field/Element " + elementName + " does not exist");
		commonLib.getScreenshot();
		return exist;		

	}
	
	
	
	public boolean verifyElementExistByWait(String field, String elementName) {
	   boolean flag;
		if (elementName==null)
		{
			flag=commonLib.waitForPresenceOfElementLocated(field);		
			
		}
		else {
		flag =commonLib.WaitforPresenceofElement_Dynamic_Xpath(field, elementName);
		}
		if(flag) {
			if(elementName==null) {
			commonLib.log(LogStatus.INFO, "Field/Element found on page :" + field);
			}
			else {
				commonLib.log(LogStatus.INFO, "Field/Element found on page :" + elementName);
			}
			commonLib.getScreenshot();
		}
		return flag;

	}
	
	
	

	public boolean verifyText(String strExp, String strAct) {
		commonLib.syso("Verify actual and expected data");
		if (strAct.contains(strExp)) {
			//commonLib.reportUtil.log(LogStatus.PASS, verificationName + ": " + "PASSED");
			commonLib.reportUtil.log(LogStatus.INFO, "Expected Text" + "---> " + strExp);
			commonLib.reportUtil.log(LogStatus.INFO, "Actual Text" + " ---> " + strAct);
			commonLib.getScreenshot();
			return true;
		} else {
			//commonLib.reportUtil.log(LogStatus.FAIL, verificationName + ": " + "FAILED");
			commonLib.reportUtil.log(LogStatus.INFO, "Expected Text" + " ---> " + strExp);
			commonLib.reportUtil.log(LogStatus.INFO, "Actual Text" + " ---> " + strAct);
			commonLib.getScreenshot();
			return false;
			
		}
		
	}
	
	public void clickeleSpanWithTextDynamic(String name) {
	sfdcLib.clickWithDynamicValue("SF_generalEle_spanWithText_XPATH", name);
	commonLib.getScreenshot();
	}
	
	public void clickOptionsTabForPartcularAccOrOpp(String tabName) {
		
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_sfdcLib.click_optionsTabForPartcularAccOrOpp_XPATH", tabName);
		commonLib.getScreenshot();
		
	}
	
	public void openDivisonalAccountAndVerifyCorrectPage()
	{
		String divisonalAccNAme = commonLib
				.getText("SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH");
		sfdcLib.click("SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH");
		commonLib.switchWindowWithURL("Setup"); // New method to switch window on thebasis of Url.
		commonLib.getScreenshot();
		commonLib.waitForDynamicTitle("Salesforce", 120);
		boolean result = verifyText("SF_ASCSales_account_toGetName_XPATH", divisonalAccNAme);
		commonLib.softAssertThat(result, "Navigated to correct account", "not Navigated to correct account");
		commonLib.getScreenshot();
	}
 public void accountPageScrollDownToContact()
 {
	 commonLib.scroll_view("SF_ASCSales_account_leftDesirdItems_label_FlexContact_XPATH");
			 commonLib.scroll_view("SF_ASCSales_account_leftDesirdItems_label_Opportunities_XPATH");
	 commonLib.scroll_view("SF_ASCSales_account_leftDesirdItems_label_OpportunityProduct_XPATH");
	 commonLib.getScreenshot();
		/*
		 * commonLib.scroll_view(
		 * "SF_ASCSales_account_leftDesirdItems_label_Notes&Att_XPATH");
		 * commonLib.scroll_view(
		 * "SF_ASCSales_account_leftDesirdItems_label_Contact_XPATH");
		 */	

 }
 
 public void logOutFromCurrentUser() throws InterruptedException, IOException
 {
 	//accountAndTerr.sfdcLib.click("SF_ASCSales_viewProfileimg_XPATH");
 	commonLib.performHoverandClick("TC_Userimg_XPATH");
 	sfdcLib.click("SF_ASCSales_viewProfile_logOut_XPATH");
 	commonLib.waitForPresenceOfElementLocated("SF_ASCSales_setUpUserImage_XPATH");
 	commonLib.getScreenshot();
 		
 }
 
 public void verifyPricingAndFinanceUserInAccountTeam()
 {
	 sfdcLib.click("SF_ASCSales_accountTeam_viewAll_XPATH");
		verifyElementExistWithSoftAssert(
				"SF_ASCSales_accountTeam_roleColumn_financeTM_XPATH", "ASC Finance TM");

		sfdcLib.click("SF_ASCSales_accountTeam_teamRoleColOrderBy_XPATH");
		commonLib.waitforInvisibilityOfElement("SF_ASCSales_progressSpinner_XPATH");
		sfdcLib.click("SF_ASCSales_accountTeam_teamRoleColOrderBy_XPATH");
		
		verifyElementExistWithSoftAssert("SF_ASCSales_accountTeam_roleColumn_financeTM_XPATH" , "ASC Finance TM");
		verifyElementExistWithSoftAssert(
		"SF_ASCSales_accountTeam_roleColumn_PricingTM_XPATH", "ASC Pricing TM");
		commonLib.getScreenshot();
 }

 public void openOpporFromAccountPageDesiredList(String oppName)
 {
	 commonLib.scroll_view("SF_ASCSales_account_leftDesirdItems_label_FlexContact_XPATH");		
	 sfdcLib.clickWithDynamicValue("SF_generalEle_spanWithText_XPATH",oppName);
		//accountAndTerr.sfdcLib.click("span", "text", "Testing 235273");	
 }

 public void searchAccountFromopSearchAndOpen(String accName) throws InterruptedException
 {
	 searchAccountFromTopSearch(accName);
		verifyAccountExistAfterSearchFromTopSearch(accName);
		sfdcLib.clickWithDynamicValue("SF_ASCSales_aTagGeneral_title_XPATH", accName);
		//sfdcLib.click("SF_ASCSales_account_87213_XPATH");
 }
 
 public void addZipCode(String zipCode)
 {
	sfdcLib.click("SF_ASCSales_Region_NewButtonForZipCode_XPATH");
		commonLib.sendKeys("SF_ASCSales_Region_zipCodeInput_XPATH", zipCode);
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
 }
 
 public void editPinCodeAddForm(String pinCode)
 {
	 commonLib.clear("SF_ASCSales_Region_zipCodeInput_XPATH");
		commonLib.sendKeys("SF_ASCSales_Region_zipCodeInput_XPATH", pinCode);
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
 }
 
 public void createEnterpriseAccountUsingPinCode(String accName) throws InterruptedException
 {
	 clickAppFromHeader("Accounts");
		commonLib.refreshPage();
		sfdcLib.click("SF_ASCSales_account_newButton_XPATH");

		sfdcLib.click("SF_ASCSales_account_typeEnterpriseAcc_checkBox_XPATH");
		sfdcLib.click("SF_ASCSales_nextButton_XPATH");

		commonLib.sendKeys("SF_ASCSales_newAccount_AccNameInput_XPATH", accName);
		
		
		commonLib.scroll_view("SF_ASCSales_newAccount_enterpriseID_XPATH");
		commonLib.sendKeys("SF_ASCSales_newAccount_enterpriseID_XPATH", accName);
		
		commonLib.scroll_view("SF_ASCSales_newAccount_postalCode_XPATH");
		commonLib.sendKeys("SF_ASCSales_newAccount_postalCode_XPATH", accName);
		
		commonLib.sendKeys("SF_ASCSales_newAccount_shippingpostalCode_XPATH", accName);
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
		commonLib.waitForPresenceOfElementLocated("SF_ASCSales_GetCreatedAccName_XPATH");

 }
 public void closePinCodeAddError()
 {
	 sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_closeError_XPATH");
 }
 
 public void verifyRegionDetailPage()
 {
		verifyElementExistWithSoftAssert("SF_ASCSales_Region_presenceof_TerrMemAssig_XPATH",
				"Territory Member Assignments");
		verifyElementExistWithSoftAssert("SF_ASCSales_Region_presenceof_zipcode_XPATH",
				"Zip Code Section");
 }
 
 public void verifyActiveTerrMemberForRegion()
 {
	 sfdcLib.click("SF_ASCSales_Region_TerrMemAssig_viewAll_XPATH");
		
		verifyElementExistWithSoftAssert("SF_ASCSales_Region_TerrMemAssig_table_XPATH",
				"TerrMemberAssignment Data Table");
		verifyElementExistWithSoftAssert("SF_ASCSales_Region_TerrMemAssig_ActiveMember_XPATH",
				"Active member");
 }
 
 public void verifyManagementTypeAccForAScOwnerAndAccount() throws InterruptedException
 {
		verifyElementExistWithSoftAssert("SF_ASCSales_account_myAccount_ascOwner_XPATH", "My account");
		sfdcLib.click("SF_ASCSales_account_myAccount_ascOwner_menuItems_XPATH");
		sfdcLib.click("SF_ASCSales_account_myAccount_ascOwner_newButton_XPATH");
		commonLib.softAssertThat(
				verifyElementExistByWait("SF_ASCSales_account_myAccount_newASCOwner_typeOfOwner_XPATH", null),
				" exist", " not exist");
		Thread.sleep(5000);

		verifyElementExistWithSoftAssert("SF_ASCSales_labelName_Contact_XPATH", "contact label name");
		verifyElementExistWithSoftAssert("SF_ASCSales_labelName_ASCAccount_XPATH", "Asc account label");
		sfdcLib.click("SF_ASCSales_account_myAccount_newASCOwner_clearAccount_XPATH");
		sfdcLib.click("SF_ASCSales_newAccountOptinFromDropdown_XPATH");

		sfdcLib.click("SF_ASCSales_nextButton_XPATH");
		commonLib.sendKeys("SF_ASCSales_newAscAccount_AccNAme_Input_XPATH", "rrr1");

		commonLib.scroll_view("SF_ASCSales_newAscAccount_billingTypeDrDown_XPATH");
		sfdcLib.click("SF_ASCSales_newAscAccount_billingTypeDrDown_XPATH");
		sfdcLib.click("SF_ASCSales_newAscAccount_billingTypeDrDown_option3_XPATH");
		//accountAndTerr.sfdcLib.clickbyjavascript("SF_ASCSales_newAscAccount_save_XPATH");
		sfdcLib.click("SF_ASCSales_newAscAccount_save_XPATH");

		verifyElementExistWithSoftAssert("SF_ASCSales_newAscAccountAfterCreateInPlaceHolder_XPATH", "new ASC account");

		
 }
 
 public boolean openDivisionalAccFromDivAccHeadPageAndVerify()
 {
	 String divisonalAccNAme = commonLib
				.getText("SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH");					
		sfdcLib.click("SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH");			
		
		sfdcLib.click("SF_ASCSales_account_myAccount_divisonalAccountListFirstItem_XPATH");
		
		commonLib.switchWindowWithURL("Setup"); // New method to switch window on the basis of Url.
		commonLib.waitForDynamicTitle("Salesforce", 120);
		boolean result = verifyText("SF_ASCSales_account_toGetName_XPATH", divisonalAccNAme);
		return result;

 }
 
 public void verifyRelatedListForAccount() throws InterruptedException
 {
		commonLib.scroll(0, 600);
		commonLib.scroll(0, -600);
		commonLib.scroll(0, 600);

		verifyElementExistWithSoftAssert("SF_ASCSales_account_contact_XPATH",
				" contact related list");

		commonLib.scroll_view("SF_ASCSales_account_ManagementCompany_XPATH");
		verifyElementExistWithSoftAssert("SF_ASCSales_account_ManagementCompany_XPATH", "management company related list");
		verifyElementExistWithSoftAssert("SF_ASCSales_account_AddressInfo_XPATH", "Address info");

		commonLib.scroll(0, -600);
		Thread.sleep(4000);
		
 }
 
 public void accountDetailsPageVerification()
 {
		commonLib.softAssertThat(verifyElementExistByWait("SF_ASCSales_account_buildCheckBox_XPATH", null),
				" exist", " not exist");
		
		commonLib.softAssertThat(
				verifyElementExistByWait("SF_ASCSales_account_billingAddressHeader_XPATH", null), " exist",
				" not exist");
		commonLib.softAssertThat(
				verifyElementExistByWait("SF_ASCSales_account_divisionalContact_Tab_XPATH", null), " exist",
				" not exist");
		commonLib.softAssertThat(
				verifyElementExistByWait("SF_ASCSales_account_divisionalAccounts_Tab_XPATH", null), " exist",
				" not exist");
 }
}
