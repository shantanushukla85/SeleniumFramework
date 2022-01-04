package com.stryker.asc.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

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

	/**
	 * anshumans verifyExistance
	 * 
	 * @param element
	 * @return boolean Dec 21, 2021
	 */
	public boolean verifyExistance(String element) {

		if (commonLib.waitForPresenceOfElementLocated(element)) {
			return true;
		}
		return false;
	}

	/**
	 * anshumans scrollAndClickElement
	 * 
	 * @param element
	 * @param type    void Dec 20, 2021
	 * @throws InterruptedException
	 */
	public void scrolldownToElementAndVerifyClickableAndSoftAssert(String element, String type)
			throws InterruptedException {
		boolean exist = false;
		for (int i = 0; i < 5; i++) {

			commonLib.scroll(0, 200);
			Thread.sleep(3000);
			if (commonLib.waitForPresenceOfElementLocated(element)) {
				exist = true;
				break;
			}

		}

		commonLib.softAssertThat(exist, element + " exist", element + " not exist");
	}

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
	verifyElementExistWithSoftAssert
	@param element
	@param elementName
	@return
	boolean
	Jan 4, 2022
	*/
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

	/**anshumans
	verifyElementExistWithDynamicText
	@param dynamicText
	@return
	boolean
	Jan 4, 2022
	*/
	public boolean verifyElementExistWithDynamicText(String dynamicText) {
		boolean isPresent = false;
		String xpath = "//span[text()=' " + dynamicText + " ']";
		isPresent = commonLib.driver.findElements(By.xpath(xpath)).size() > 0;
		return isPresent;

	}

	/**anshumans
	verifytext
	@param element
	@param actualtext
	@return
	boolean
	Jan 4, 2022
	*/
	public boolean verifytext(String element, String actualtext) {
		boolean compareTextResult = false;
		String expectedtext = commonLib.getText(element);
		commonLib.syso(expectedtext);
		compareTextResult = commonLib.verifyText(actualtext, expectedtext, element);
		commonLib.getScreenshot();
		return compareTextResult;

	}

	/**anshumans
	tabs
	@param i
	void
	Jan 4, 2022
	*/
	public void tabs(int i) {
		ArrayList<String> tabs = null;
		tabs = new ArrayList<String>(commonLib.driver.getWindowHandles());
		commonLib.driver.switchTo().window(tabs.get(i));
	}

	/**anshumans
	scrollToElement
	@param Element
	void
	Jan 4, 2022
	*/
	public void scrollToElement(String Element) {

		JavascriptExecutor js = (JavascriptExecutor) commonLib.driver;

		// Scrolling down the page till the element is found
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		js.executeScript("arguments[0].scrollIntoView();", commonLib.findElement(Element));
	}

	/**anshumans
	scrollToElementAndClickOrSendKeys
	@param Element
	@param operation
	@param sendkeys1
	void
	Jan 4, 2022
	*/
	public void scrollToElementAndClickOrSendKeys(String Element, String operation, String sendkeys1) {

		JavascriptExecutor js = (JavascriptExecutor) commonLib.driver;

		// Scrolling down the page till the element is found
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		js.executeScript("arguments[0].scrollIntoView();", commonLib.findElement(Element));
		if (operation == "click") {
			commonLib.click(Element);
			commonLib.log(LogStatus.INFO, Element + "clicked");
			commonLib.getScreenshot();
		}
		if (operation == "sendkeys") {
			commonLib.sendKeys(Element, sendkeys1);
			commonLib.log(LogStatus.INFO, Element + "send key sucess" + "value = " + sendkeys1);
			commonLib.getScreenshot();
		}

	}

	/**anshumans
	click
	@param field
	void
	Jan 4, 2022
	*/
	public void click(String field) {
		
		commonLib.click(field);
		commonLib.log(LogStatus.INFO, field + " clicked");
		commonLib.getScreenshot();

	}

	/**anshumans
	sendKeys
	@param field
	@param inputText
	void
	Jan 4, 2022
	*/
	public void sendKeys(String field, String inputText) {
		commonLib.sendKeys(field, inputText);
		commonLib.log(LogStatus.INFO, "value entererd in  " + field + " =" + inputText);
		commonLib.getScreenshot();
	}

	/**anshumans
	clickbyjavascript
	@param field
	void
	Jan 4, 2022
	*/
	public void clickbyjavascript(String field) {
		commonLib.waitForPresenceOfElementLocated(field);
		commonLib.waitForElementToBeClickable(field);
		commonLib.clickbyjavascript(field);
		commonLib.log(LogStatus.INFO, field + "clicked");
		commonLib.getScreenshot();

	}

	/**anshumans
	clickWithDynamicValue
	@param field
	@param key
	void
	Jan 4, 2022
	*/
	public void clickWithDynamicValue(String field, String key) {
		commonLib.clickWithDynamicValue(field, key);
		commonLib.log(LogStatus.INFO, field + "clicked");
		commonLib.getScreenshot();
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

	/**anshumans
	selectAppFromLeftSideIconWaffle
	@param appName
	@throws InterruptedException
	void
	Jan 4, 2022
	*/
	public void selectAppFromLeftSideIconWaffle(String appName) throws InterruptedException {
		String xpath = "//p[@class='slds-truncate']/b[text()='" + appName + "']";
		clickbyjavascript("SF_ASCSales_appLink_XPATH");
		Thread.sleep(2000);
		commonLib.sendkeys_by_JavaScript("SF_ASCSales_appSearchInput_XPATH", appName);
		// sendKeys("SF_ASCSales_appSearchInput_XPATH", appName);
		commonLib.driver.findElement(By.xpath(xpath)).click();
		// click("SF_ASCSales_selectAppFromSearch_XPATH");
		commonLib.waitForPresenceOfElementLocated("SF_ASCSales_imgASCLightApp_XPATH");
	}

	/**anshumans
	softAssertThat
	@param status
	@param passMsg
	@param failMsg
	void
	Jan 4, 2022
	*/
	public void softAssertThat(boolean status, String passMsg, String failMsg) {

		commonLib.softAssertThat(status, passMsg, failMsg);
		commonLib.getScreenshot();

	}

	/**anshumans
	clickAppFromHeader
	@param app
	void
	Jan 4, 2022
	*/
	public void clickAppFromHeader(String app) {
		commonLib.refreshPage();
		String xpath = "//a[@title='" + app + "']";
		commonLib.waitForPresenceOfElementLocated("SF_ASCSales_accountLinkInHeader_XPATH");
		commonLib.waitForElementToBeClickable("SF_ASCSales_accountLinkInHeader_XPATH");
		JavascriptExecutor js = (JavascriptExecutor) commonLib.driver;
		js.executeScript("arguments[0].click();", commonLib.driver.findElement(By.xpath(xpath)));

	}

	/**anshumans
	gen
	@return
	int
	Jan 4, 2022
	*/
	public int gen() {
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
		click("SF_ASCSales_Region_NewButtonForTerrMem_XPATH");
		click("SF_ASCSales_Region_newTerrMemPage_User_XPATH");
		sendKeys("SF_ASCSales_Region_newTerrMemPage_User_XPATH", userName);
		String xpath = "//span[contains(@class,'slds-listbox')]/lightning-base-combobox-formatted-text[@title='"
				+ userName + "']";
		// commonLib.driver.findElement(By.xpath(xpath)).click();
		commonLib.driver.findElement(By.xpath(xpath)).click();
		click("SF_ASCSales_Region_newTerrMemPage_Role_XPATH");
		click("SF_ASCSales_Region_newTerrMemPage_Role_salesRep_XPATH");
		click("SF_ASCSales_Region_newTerrMemPage_AccPermission_XPATH");

		// click("SF_ASCSales_Region_newTerrMemPage_Perm_Edit_XPATH");
		commonLib.driver.findElement(By.xpath("//span[@title='" + permission + "']")).click();
		click("SF_ASCSales_account_myAccount_newASCOwner_saveButton_XPATH");
		verifyElementExistWithSoftAssert("SF_ASCSales_toastMessageForSuccess_XPATH", "succes message");
		
	}

	/**anshumans
	openAccountFromAccountSearchByName
	@param accName
	@throws InterruptedException
	void
	Jan 4, 2022
	*/
	public void openAccountFromAccountSearchByName(String accName) throws InterruptedException {
		sendKeys("SF_ASCSales_searchAccountInputBox_XPATH", accName);//

		commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
		Thread.sleep(5000);
		// accountAndTerr.click("SF_ASCSales_firstAccountInSearch_XPATH");
		clickWithDynamicValue("SF_ASCSales_firstAccountInSearch_XPATH", accName);
		commonLib.refreshPage();
	}

	

	/**anshumans
	searchAccountByNameFromAccountPage
	@param accNAme
	@throws InterruptedException
	void
	Jan 4, 2022
	*/
	public void searchAccountByNameFromAccountPage(String accNAme) throws InterruptedException {
		sendKeys("SF_ASCSales_searchAccountInputBox_XPATH", accNAme);
		commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
		Thread.sleep(5000);
		commonLib.clickbyjavascriptWithDynamicValue("SF_ASCSales_AccountInSearch_XPATH", accNAme);
	}

}
