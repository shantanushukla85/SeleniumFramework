package com.stryker.salesforceLibrary;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.stryker.asc.test.scripts.Opportunities;
import com.stryker.star.CommonLibrary;

public class SfdcLibrary {
	
	public WebDriver driver;
	public ExtentReports extent;
	public ExtentTest test;
	public CommonLibrary commonLib;
	public XSSFWorkbook workbook;
	public XSSFSheet Sheet;
	public XSSFRow Row;
	public String workbookName;
	public XSSFCell cell;

	public SfdcLibrary(CommonLibrary commonLib) {
		this.commonLib = commonLib;

	}

	public static enum GximProfile {
		LOCAL, REGIONAL, GLOBAL, SPONSOR
	}

/////////////////////////////////////////////// NEW METHODS WITH XPATHS ///////////////////////////////////////////////
	public void login(String Username, String Password) throws InterruptedException, IOException {
		commonLib.syso("About to login User: " + Username + " to the SalesForce Application");
		commonLib.syso("Writing User Email.");
		commonLib.sendKeys("SF_loginPage_userName_XPATH", Username);
		commonLib.syso("User Email Specified successfully");
		commonLib.syso("Putting Password and Click on Login button");
		commonLib.sendKeys("SF_loginPage_passWord_XPATH", Password);
		commonLib.click("SF_loginPage_submitButton_XPATH");
		commonLib.syso("Login button clicked");
		
		//commonLib.waitforInvisibilityOfElement("SF_ASCSales_loadingStatus_XPATH");
		commonLib.waitForPresenceOfElementLocated("SF_ASCSales_navigationHeader_XPATH");
		commonLib.syso("loader disappear");
		/*
		 * commonLib.getDriver().manage().timeouts().pageLoadTimeout(60,
		 * TimeUnit.SECONDS); commonLib.implicitWait(60);
		 * commonLib.waitForTitleTimeOut(20);
		 */
	}

	public String getWorkbookNameWithEnvironment() {
		String dataBook = commonLib.getGlobalConfigurationValue("dataBookPrefix")
				+ commonLib.getConfigValue("environment").toUpperCase();
		workbookName = commonLib.getConfigValue(dataBook);

		if (workbookName == null || workbookName.isEmpty()) {
			commonLib.log(LogStatus.FAIL, "Please check the config Key for " + dataBook);
		}

		return this.workbookName;
	}

	public void closeLightningTab() {
		try {
			commonLib.waitForPageToLoad();
			boolean elementAvail = false;
			do {
				if (elementAvail) {
					WebElement openTabElement = commonLib.findElement("Close_Tabs_XPATH");
					openTabElement.click();
					Thread.sleep(5000);
				}
				elementAvail = commonLib.isElementExists(
						"//*[@class='tabBarItems slds-grid']/descendant::button[contains(@title,'Close') and contains(@class,'slds-button')]");
			} while (elementAvail);
		} catch (Exception e) {
			commonLib.syso("ALL Tabs not closed Successfully");
			commonLib.log(LogStatus.FAIL, "ACTUAL RESULT->" + e);
			commonLib.catchException(e.getMessage(), e);

		}
	}

	public void searchUser_Classic(String username) throws IOException, InterruptedException {
		commonLib.waitForPresenceOfElementLocated("SF_classic_searchBox_XPATH");
		commonLib.clear("SF_classic_searchBox_XPATH");
		commonLib.findElement("SF_classic_searchBox_XPATH").sendKeys(username, Keys.ENTER);
		commonLib.waitForPageToLoad();
		commonLib.waitForPresenceOfElementLocated("TC_classic_UserBlock_XPATH");
		commonLib.getScreenshot();
		commonLib.scroll_view("TC_classic_UserBlock_XPATH");
		commonLib.clickWithDynamicValue("TC_classic_UserBlock_user_XPATH", username);
		commonLib.waitForPageToLoad();
		if (commonLib.waitForPresenceOfElementLocated("TC_classic_UserDetails_follow_XPATH")) {
			Thread.sleep(1000);
			commonLib.clickbyjavascript("TC_classic_UserDetails_follow_Btn_XPATH");
			commonLib.click("TC_classic_follow_UserDetail_XPATH");
			commonLib.waitForPageToLoad();
			commonLib.log(LogStatus.PASS, "PASS - User successfully navigated to the 'User' record.");
			commonLib.getScreenshot();
			commonLib.click("TC_classic_UserDetail_Login_Btn_XPATH");
			commonLib.waitForPageToLoad();
		} else {
			throw new NoSuchElementException("Could not Open User Details page");
		}
	}

	public void globalSearch_Classic(String searchtext) {
		commonLib.waitForPresenceOfElementLocated("SF_classic_searchBox_XPATH");
		commonLib.clear("SF_classic_searchBox_XPATH");
		commonLib.findElement("SF_classic_searchBox_XPATH").sendKeys(searchtext, Keys.ENTER);
		commonLib.log(LogStatus.INFO, "Successfully searched record: " + searchtext);
		commonLib.getScreenshot();
		commonLib.waitForPageToLoad();
	}

	public void searchAccount_Classic(String searchtext) throws IOException, InterruptedException {
		globalSearch_Classic(searchtext);
		commonLib.waitForPresenceOfElementLocated("TC_classic_AccountBlock_XPATH");
		commonLib.scroll_view("TC_classic_AccountBlock_XPATH");
		commonLib.log(LogStatus.INFO, "Successfully navigated to the 'Account' record: " + searchtext);
		commonLib.getScreenshot();
		commonLib.clickWithDynamicValue("TC_classic_AccountBlock_Account_XPATH", searchtext);
	}

	/**
	 * Generic method to click on TOP ROW Button
	 *
	 */
	public void clickTopRowButton_Classic(String buttonName) {
		commonLib.waitForElementToBeClickable_Dynamic("SF_Classic_Page_TopRow_Button_XPATH", buttonName);
		commonLib.clickWithDynamicValue("SF_Classic_Page_TopRow_Button_XPATH", buttonName);
		commonLib.log(LogStatus.INFO, "Successfully clicked button: " + buttonName);
		commonLib.waitForPageToLoad();
	}

	/**
	 * Generic method to click on application TAB header
	 *
	 */
	public void clickHeaderTab_Classic(String tabName) {
		commonLib.waitForElementToBeClickable_Dynamic("SF_Classic_App_Tab_Header_XPATH", tabName);
		commonLib.clickWithDynamicValue("SF_Classic_App_Tab_Header_XPATH", tabName);
		commonLib.waitForPageToLoad();
	}

	public void switchToClassicVersion() throws InterruptedException, IOException {
		Thread.sleep(6000);
		String currentURL = commonLib.getCurrentUrl();
		if (currentURL.contains("lightning")) {
			System.out.println("USer logged in to Lightning version ");
			commonLib.waitForPresenceOfElementLocated("TC_Userimg_XPATH");
			commonLib.performHoverandClick("TC_Userimg_XPATH");
			Thread.sleep(4000);
			commonLib.click("SF_switchtoClassic_XPATH");
			commonLib.getDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			commonLib.implicitWait(20);
			if (commonLib.waitForPresenceOfElementLocated("SF_Classic_AccountTab_XPATH")) {
				System.out.println("Swicthed to Classic Version successfully");
			}
		} else {
			System.out.println("User is in classic version");
			commonLib.waitForPresenceOfElementLocated("SF_switchtolightening_label_XPATH");
		}
		commonLib.implicitWait(30);
	}

	public boolean verifyTableHeadersList(String actualHeaderLocator, List<String> expectedOptionList) {
		boolean flag;
		List<WebElement> actualHeaderList = commonLib.findElements(actualHeaderLocator);

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

	public boolean verfiyDropdownValues(String actualDDLocator, List<String> expectedDDOptionList) {
		boolean flag = false;
		List<String> actualDDList = commonLib.get_All_options(actualDDLocator);

		flag = actualDDList.equals(expectedDDOptionList);
		commonLib.softAssertThat(flag, "Pass - Expected dropdown options are present: " + expectedDDOptionList,
				"Fail - Expected dropdown options are not present: " + expectedDDOptionList);

		commonLib.getScreenshot();

		return flag;
	}

	public String splitUserName(String username, String typeFirstOrLast) {
		String selectedName = null;
		String[] splited = username.split("\\s+");

		if (typeFirstOrLast.equalsIgnoreCase("first")) {
			selectedName = splited[0];
		} else if (typeFirstOrLast.equalsIgnoreCase("last")) {
			selectedName = splited[1];
		} else {
			commonLib.syso("Please select 'First' or 'Last' as your selection");
		}
		return selectedName;
	}

	/////////////////////////////////////////////// OLD METHODS WITH NO XPATHS
	/////////////////////////////////////////////// ///////////////////////////////////////////////

	public void switchToLightningVesion() {

		// waitForPageLoad();
		commonLib.implicitWait(90);

		String currentURL = commonLib.getCurrentUrl();
		System.out.println("URL  " + currentURL);
		if (currentURL.contains("documentforce")) {
			for (int i = 0; i < 25; i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				// To check page ready state.
				currentURL = commonLib.getCurrentUrl();
				if (!(currentURL.contains("documentforce"))) {
					break;
				}
			}
		}
		if (currentURL.contains("lightning")) {
			//commonLib.waitForPresenceOfElementLocated("SF_Header_StykerLogo_XPATH");
		} else {
			commonLib.click("SF_Header_Switch_To_Lightning_XPATH");
			commonLib.waitForPresenceOfElementLocated("SF_Header_StykerLogo_XPATH");
		}
		commonLib.implicitWait(30);
	}

	public void globalSearch(String searchtext) throws IOException, InterruptedException {
		commonLib.switchToDefaultContent();
		Thread.sleep(2000);
		commonLib.waitForElementToBeClickable("GlobalSearch_Homepage_XPATH");
		commonLib.clickbyjavascript("GlobalSearch_Homepage_XPATH");
		Thread.sleep(3000);
		commonLib.clear("Global_Search_XPATH");
		commonLib.click("Global_Search_XPATH");
		Thread.sleep(1000);
		commonLib.sendKeys("Global_Search_XPATH", searchtext);
		Thread.sleep(3000);
		commonLib.log(LogStatus.INFO, "Performing Global search for " + searchtext);
		commonLib.getScreenshot();
		commonLib.WaitforPresenceofElement_Dynamic_Xpath("Search_Result_XPATH", searchtext);
		commonLib.clickWithDynamicValue("Search_Result_XPATH", searchtext);
	}

	// Get system date
	public String getcurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String sysdate = dateFormat.format(date).toString();
		return sysdate;
	}

	public void searchAccount(String AccountName) {
		commonLib.scroll_view("SF_AccountPop_AccountSearch_XPATH");
		commonLib.sendKeys("SF_AccountPop_AccountSearch_XPATH", AccountName);
		commonLib.click("SF_ContactPopUp_SearchAccountLink_XPATH");
		boolean accountResultsPopUP = commonLib.waitForPresenceOfElementLocated("SF_AccountResults_popUP_XPATH");
		if (accountResultsPopUP == true) {
			System.out.print("");
			commonLib.clickWithDynamicValue("SF_AccountSearchPopUp_accountRecordLink_XPATH", AccountName);
		} else {
			System.out.println("Account Search PopUp is not displayed");
		}

	}

	public String getcurrentDate_MM_dd_yyyy() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String sysdate = dateFormat.format(date).toString();
		return sysdate;
	}

	public void SwitchtoFrame(String field) {
		WebElement ele = commonLib.findElement(field);
		WebDriver driver = commonLib.getDriver();
		driver.switchTo().frame(ele);
	}

	public void App_Launcher_Search(String SearchData) throws IOException, InterruptedException {
		commonLib.waitforElement("App_Launcher_XPATH");
		commonLib.click("App_Launcher_XPATH");
		Thread.sleep(3000);
		commonLib.click("App_Launcher_View_All_XPATH");
		Thread.sleep(5000);
		commonLib.sendKeys("Search_launcher_XPATH", SearchData);
		Thread.sleep(2000);
		if (commonLib.WaitforPresenceofElement_Dynamic_Xpath("Seacrh_Result_XPATH", SearchData)) {
			commonLib.clickbyjavascriptWithDynamicValue("Seacrh_Result_XPATH", SearchData);
		} else {
			commonLib.clickWithDynamicValue("Seacrh_Result2_XPATH", SearchData);
		}
	}

	public void maximizeWindow() throws IOException, InterruptedException {

		commonLib.getDriver().manage().window().maximize();
	}

	public boolean verifyCheckbox(String name, String val) throws IOException, InterruptedException {

		WebElement element = commonLib.findElement(name);
		JavascriptExecutor js = (JavascriptExecutor) commonLib.getDriver();
		String value = js.executeScript("return arguments[0].checked", element).toString();
		if (value.equalsIgnoreCase(val)) {
			String status = "Pass";
		} else {
			commonLib.log(LogStatus.FAIL, ":" + "FAILED");
			commonLib.log(LogStatus.INFO, "Expected Event" + " ---> " + "checkbox should be matched with given value");
			commonLib.log(LogStatus.INFO, "Actual Event" + " ---> " + "checkbox not matched with given value");
		}
		return true;
	}

	public String checkAppVersion() throws InterruptedException {
		Thread.sleep(4000);
		String currentURL = commonLib.getCurrentUrl();
		if (currentURL.contains("lightning")) {
			System.out.println("USer logged in to Lightning version ");
			return "Lightning";

		} else {
			System.out.println("User is in classic version");
			commonLib.waitForPresenceOfElementLocated("SF_switchtolightening_label_XPATH");
			return "Classic";
		}

	}

	public void scrollDownUntillElementVisible(String element) {
		boolean elementValue = false;
		elementValue = commonLib.waitForVisibilityOf(element);
		if (elementValue == false) {
			for (int i = 1; i <= 5; i++) {
				JavascriptExecutor js = (JavascriptExecutor) commonLib.getDriver();
				js.executeScript(
						"window.scrollTo(0, Math.max(document.documentElement.scrollHeight, document.body.scrollHeight, document.documentElement.clientHeight));");
				elementValue = commonLib.waitForVisibilityOf(element);
				if (elementValue == true) {
					commonLib.scroll_view(element);
					break;
				}
			}
		}
	}

	public void swicth_to_ChildWindow_by_Id() {
		String Parentwindow = commonLib.getDriver().getWindowHandle();
		Set<String> windows = commonLib.getDriver().getWindowHandles();
		Iterator<String> win = windows.iterator();

		while (win.hasNext()) {
			String child_windows = win.next();
			if (!Parentwindow.equals(child_windows)) {
				commonLib.getDriver().switchTo().window(child_windows);
				System.out.println("Successfully switched to window ");
				break;
			}
		}
	}

	public boolean verifyLoginSuccess() {

		System.out.println("Verifying User's Login");
		commonLib.implicitWait(20);
		try {
			commonLib.waitForPresenceOfElementLocated("NVCC_Lightning_HomePage_header_XPATH");
			boolean NV_Header = commonLib.waitForVisibilityOf("NVCC_Lightning_HomePage_header_XPATH");

			if (NV_Header == true) {
				commonLib.syso("User logged in Successfully");
				return true;
			} else {
				commonLib.syso("Could not Find Home Page , User's login not successful");
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public String getDefaultselectedOption(String Welement) {
		String selValue = null;
		try {
			WebElement element = commonLib.findElement(Welement);
			Select se = new Select(element);
			WebElement option = se.getFirstSelectedOption();
			selValue = option.getText();
			return selValue;
		} catch (Exception e) {
			commonLib.syso("Could not get default selected value");
			commonLib.log(LogStatus.FAIL, "ACTUAL RESULT->" + e);
			return null;
		}
	}

	public boolean verifyDropDownData(String Welement, ArrayList<String> actualList) {

		try {
			WebElement element = commonLib.findElement(Welement);
			Select se = new Select(element);
			List<WebElement> ListElements = se.getOptions();
			List<String> originalList = new ArrayList<String>();

			for (WebElement webElement : ListElements) {
				originalList.add(webElement.getText());
			}
			if (actualList.equals(originalList)) {
				commonLib.syso("Drop down List data matched");
				return true;
			} else {
				commonLib.syso("Drop down List data mismatched");
				return false;
			}
		} catch (Exception e) {
			commonLib.syso("Could not get Drop down values");
			commonLib.log(LogStatus.FAIL, "ACTUAL RESULT->" + e);
			return false;
		}
	}

	public boolean Verify_alphabetical_order_DropDown(String field) {

		boolean ans = false;
		// Select dropdown =new Select();
		List<WebElement> dd = commonLib.getDriver().findElements(By.xpath(commonLib.getLocatorValue(field)));// dropdown.getOptions();
		List<String> actuallist = new ArrayList<String>();
		List<String> checklist = new ArrayList<String>();
		for (WebElement el : dd) {
			String data = el.getText();
			if (!data.equalsIgnoreCase("--None--")) {
				actuallist.add(data);
			}
		}
		checklist = actuallist;
		Collections.sort(checklist);

		if (actuallist.equals(checklist)) {
			ans = true;
		} else {
			ans = false;
		}
		return ans;
	}

	public void uploadFileDirectly(String field, String path) {
		commonLib.findElement(field).sendKeys(path);

	}

	public void Verify_Global_Search(String SearchData) throws InterruptedException, AWTException {
		commonLib.findElement("GlobalSearch_Homepage_XPATH").clear();
		;
		commonLib.syso("Enter Account name in Global search=" + SearchData);
		commonLib.sendKeys("GlobalSearch_Homepage_XPATH", SearchData);
		Thread.sleep(2000);
		commonLib.click("GlobalSearch_Homepage_XPATH");
		commonLib.KeyPress_Enter();
		boolean accounts_Results_Table_Exist = commonLib
				.waitForVisibilityOf_DynamicXpath("Accounts_Results_Table_In_Home_Page_XPATH", SearchData);

		if (accounts_Results_Table_Exist) {
			commonLib.syso(SearchData + "is visible");
		} else {
			commonLib.log(LogStatus.INFO, SearchData + "is not visible");
		}
	}

	public void Search_App_Launcher(String SearchData) throws InterruptedException, AWTException {
		commonLib.click("App_Launcher_XPATH");
		Thread.sleep(2000);
		commonLib.sendKeys("Search_App_Launcher_XPATH", SearchData);
		commonLib.clickWithDynamicValue("Searched_launcher_XPATH", SearchData);
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name : verifyEdit
	 * Description : Verifies that the input field is readonly or not Arguments :
	 * Xpath Return value : boolean Example : sfdcLib.verify_DropDown(Xpath) Author
	 * : archit.sharma@stryker.com
	 * =============================================================================
	 * ===============================================
	 */

	public boolean VerifyEdit(String field) throws InterruptedException {
		Boolean localResult = null;
		commonLib.waitForPresenceOfElementLocated(field);
		commonLib.waitForVisibilityOf(field);
		WebElement readOnly = commonLib.findElement(field);
		if (readOnly.getAttribute("readonly") != null) {
			localResult = readOnly.getAttribute("readonly").equals("true");
		} else if (readOnly.getAttribute("disabled") != null) {
			localResult = readOnly.getAttribute("disabled").equals("true");
		}
		return localResult;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * verify_DropDown Description : Verifies the dropdown list based on attribute
	 * with predefined list Arguments : Xpath, Dropdown List , Attribute Return
	 * value : boolean Example : sfdcLib.verify_DropDown(Xpath, ArrayList<String>
	 * check, "title") Author : archit.sharma@stryker.com
	 * =============================================================================
	 * ===============================================
	 */
	public boolean verify_DropDown(String field, ArrayList<String> check, String attribute) {
		boolean ans = false;
		List<WebElement> dd = commonLib.getDriver().findElements(By.xpath(commonLib.getLocatorValue(field)));// dropdown.getOptions();
		List<String> actuallist = new ArrayList<String>();
		for (WebElement el : dd) {
			String data = el.getAttribute(attribute);
			if (!data.equalsIgnoreCase("--None--")) {
				actuallist.add(data);
			}
		}
		if (actuallist.equals(check)) {
			ans = true;
		} else {
			ans = false;
		}
		return ans;
	}

	// GXIC
	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * open_Given_Tab_In_Home_Page Description : It opens given tab name from home
	 * page Arguments : tabName Return value : NA Example :
	 * sfdcLib.open_Given_Tab_In_Home_Page("Accounts");
	 * sfdcLib.open_Given_Tab_In_Home_Page("Contacts");
	 * sfdcLib.open_Given_Tab_In_Home_Page("Support Cases");
	 * =============================================================================
	 * ===============================================
	 */

	public void open_Given_Tab_In_Home_Page(String tabName) throws IOException, InterruptedException {

		try {
			// commonLib.log(LogStatus.PASS, "Click tab='" + tabName+ "' in Home page");
			commonLib.syso("Click tab='" + tabName + "' in Home page");
			commonLib.clickbyjavascriptWithDynamicValue("All_Tabs_In_HomePage_XPATH", tabName);

			is_Given_Tab_Open_In_Home_Page(tabName);
		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Given tab='" + tabName + "' is not open in Home page");
			e.printStackTrace();
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * is_Given_Tab_Open_In_Home_Page Description : It checks given tab name from
	 * home page is open or not Arguments : tabName Return value : NA Example :
	 * sfdcLib.is_Given_Tab_Open_In_Home_Page("Accounts");
	 * sfdcLib.is_Given_Tab_Open_In_Home_Page("Contacts");
	 * sfdcLib.is_Given_Tab_Open_In_Home_Page("Support Cases");
	 * =============================================================================
	 * ===============================================
	 */
	public void is_Given_Tab_Open_In_Home_Page(String tabName) throws IOException, InterruptedException {
		boolean is_Given_Tab_Open_In_Home_Page;
		is_Given_Tab_Open_In_Home_Page = commonLib
				.waitForVisibilityOf_DynamicXpath("Header_Text_In_Tabs_In_HomePage_XPATH", tabName);

		if (is_Given_Tab_Open_In_Home_Page) {
			// commonLib.log(LogStatus.PASS, "Given tab='" + tabName+ "' is open in Home
			// page");
			commonLib.syso("Given tab='" + tabName + "' is open in Home page");
		} else {
			commonLib.log(LogStatus.FAIL, "Given tab='" + tabName + "' is not open in Home page");
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * search_Profile Description : It searches for the given profile and login
	 * Arguments : String toBeSearched, String xpathOfResult, String
	 * keyForWaitDynamicElement,String keyForClickWithDynamic Return value : NA
	 * Example : search_Profile("IC Local", "GlobalSearch_ProfileName_XPATH",
	 * "IC Local", "IC Local");
	 * =============================================================================
	 * ===============================================
	 */

	/*
	 * public void search_Profile(String toBeSearched, String xpathOfResult, String
	 * keyForWaitDynamicElement, String keyForClickWithDynamic) throws
	 * InterruptedException { try {
	 * commonLib.clickwithoutWait("GlobalSearch_Homepage_XPATH");
	 * commonLib.sendKeys("GlobalSearch_Homepage_XPATH", toBeSearched);
	 * Thread.sleep(5 * 1000);
	 * commonLib.WaitforPresenceofElement_Dynamic_Xpath(xpathOfResult,
	 * keyForWaitDynamicElement); commonLib.clickWithDynamicValue(xpathOfResult,
	 * keyForClickWithDynamic); commonLib.waitUntilUrlContains("ManageUsers");
	 * commonLib.waitForPresenceOfElementLocated("GlobalSearch_Homepage_XPATH");
	 * commonLib.waitForPageToLoad(); // Thread.sleep(10000); driver =
	 * commonLib.getDriver();
	 * commonLib.waitforFramenadSwitch("Profile_login_Frame_XPATH"); int framescnt =
	 * driver.findElements(By.xpath("//iframe")).size(); commonLib.syso("framescnt:"
	 * + framescnt); System.out.println("page load completed"); //
	 * Thread.sleep(10000);
	 * 
	 * // driver.switchTo().frame(0);// added
	 * commonLib.waitForPresenceOfElementLocated("Profile_login_XPATH");
	 * commonLib.clickwithoutWait("Profile_login_XPATH");
	 * commonLib.waitUntilUrlContains("home");
	 * commonLib.waitForPresenceOfElementLocated("GlobalSearch_Homepage_XPATH");
	 * driver.switchTo().defaultContent(); // added new Thread.sleep(5000);
	 * commonLib.refreshPage();// added as some times logi profile is not displayed
	 * by Brahma
	 * commonLib.waitForPresenceOfElementLocated("Profile_loginMessage_XPATH");//
	 * Brahma if (validateProfileLogin("Profile_loginMessage_XPATH",
	 * keyForClickWithDynamic)) { commonLib.log(LogStatus.INFO,
	 * "User is successfull logged in with " + keyForClickWithDynamic); } else {
	 * commonLib.log(LogStatus.FAIL, "User is not Logged in With " +
	 * keyForClickWithDynamic); } } catch (Exception e) { commonLib.syso("No Frame:"
	 * + e.getMessage()); }
	 * 
	 * }
	 */
	public void search_Profile(String toBeSearched) throws InterruptedException {
		try {
			commonLib.clickwithoutWait("GlobalSearch_SetupPage_XPATH");
			Thread.sleep(3000);
			commonLib.sendKeys("GlobalSearch_SetupPage_XPATH", toBeSearched);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("GlobalSearch_ProfileName_XPATH", toBeSearched);
			Thread.sleep(2000);
			commonLib.clickwithoutWait("GlobalSearch_SetupPage_XPATH");
			commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
			// commonLib.KeyPress_Tab();
			KeyPress_Tab_Using_Actions_Class();
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("ProfileSearchResult_NameLink_XPATH", toBeSearched);
			commonLib.clickbyjavascriptWithDynamicValue("ProfileSearchResult_NameLink_XPATH", toBeSearched);
			commonLib.waitForPresenceOfElementLocated("GlobalSearch_SetupPage_XPATH");
			commonLib.waitForPageToLoad();
			Thread.sleep(10000);
			driver = commonLib.getDriver();
			
			commonLib.waitforFramenadSwitch("Profile_login_Frame_XPATH");
			int framescnt = driver.findElements(By.xpath("//iframe")).size();
			commonLib.syso("framescnt:" + framescnt);
			commonLib.syso("Waiting for the Frame to get loaded for Login Button");

			commonLib.syso("framescnt:" + framescnt);
			// driver.switchTo().frame(0);// added
			commonLib.waitForPresenceOfElementLocated("Profile_login_XPATH");
			// Thread.sleep(3*1000);
			commonLib.waitForPresenceOfElementLocated("SearchProfile_PermissionSetButton_Link_XPATH");
			commonLib.waitForElementToBeClickable("SearchProfile_PermissionSetButton_Link_XPATH");
			commonLib.waitForElementToBeClickable("Profile_login_XPATH");
//			commonLib.clickbyjavascript("Home_Page_QuickFind_textbox_XPATH");
			commonLib.getScreenshot();
			commonLib.clickbyjavascript("Profile_login_XPATH");
			commonLib.waitUntilUrlContains("home");
//			commonLib.waitForPresenceOfElementLocated("GlobalSearch_Homepage_XPATH");
			driver.switchTo().defaultContent(); // To switch back to parent window
			// Logic applied as sometimes Logged in user Name does not comes up.
			if (commonLib.findElementPresence("Profile_loginMessage_XPATH") == false) {
				commonLib.refreshPage();
			}
			commonLib.waitForPresenceOfElementLocated("Profile_loginMessage_XPATH");
		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "User is not Logged in With " + toBeSearched);
			e.printStackTrace();
		}

	}
	
	public void KeyPress_Tab_Using_Actions_Class() {
		try {
			Actions action = new Actions(driver);
			action.sendKeys(Keys.TAB).build().perform();
		} catch (Exception e) {
			commonLib.syso("Failed.Not able to press enter using actions class");
		}
	}
	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * search_Profile_GXICM Description : It searches for the given profile and
	 * login Arguments : String tobeSearched ->> Like with IC Local we have to pass
	 * IC Local only rest it will take care Return value : NA Example :
	 * search_Profile_GXICM("IC Local");
	 * =============================================================================
	 * ===============================================
	 */
	public void search_Profile_GXICM(String toBeSearched) throws InterruptedException {
		try {
			commonLib.clickwithoutWait("GlobalSearch_SetupPage_XPATH");
			commonLib.sendKeys("GlobalSearch_SetupPage_XPATH", toBeSearched);
			Thread.sleep(4 * 1000);
			// Logic applied as sometimes suggestions in search text bar does not comes up.
			try {
				if (getElememt_Visibility_Status(commonLib.findElementWithDynamicXPath("GlobalSearch_ProfileName_XPATH",
						toBeSearched)) == false) {
					commonLib.click("GlobalSearch_SetupPage_XPATH");
					Thread.sleep(1000);
				}

			} catch (StaleElementReferenceException e) {
				// TODO: handle exception

				commonLib.click("GlobalSearch_SetupPage_XPATH");
				Thread.sleep(1000);
			}
			// Logic for handling case when it did not click on Profile suggestion
			try {

				commonLib.WaitforPresenceofElement_Dynamic_Xpath("GlobalSearch_ProfileName_XPATH", toBeSearched);
				Thread.sleep(3000);
				commonLib.clickWithDynamicValue("GlobalSearch_ProfileName_XPATH", toBeSearched);

			} catch (StaleElementReferenceException e) {
				Thread.sleep(1000);
				commonLib.clickWithDynamicValue("GlobalSearch_ProfileName_XPATH", toBeSearched);

			}
			// commonLib.waitForVisibilityOf("Profile_UserDetail_Button_XPATH");
			// commonLib.click("Profile_UserDetail_Button_XPATH");

			commonLib.waitUntilUrlContains("Manage Users");
			commonLib.waitForPresenceOfElementLocated("GlobalSearch_SetupPage_XPATH");
			commonLib.waitForPageToLoad();
			// Thread.sleep(10000);
			driver = commonLib.getDriver();
			commonLib.waitforFramenadSwitch("Profile_login_Frame_XPATH");
			int framescnt = driver.findElements(By.xpath("//iframe")).size();
			commonLib.syso("framescnt:" + framescnt);
			commonLib.syso("Waiting for the Frame to get loaded for Login Button");

			commonLib.syso("framescnt:" + framescnt);
			// driver.switchTo().frame(0);// added
			commonLib.waitForPresenceOfElementLocated("Profile_login_XPATH");
			Thread.sleep(3 * 1000);
			commonLib.waitForPresenceOfElementLocated("SearchProfile_PermissionSetButton_Link_XPATH");
			commonLib.waitForElementToBeClickable("SearchProfile_PermissionSetButton_Link_XPATH");
			commonLib.waitForElementToBeClickable("Profile_login_XPATH");
			commonLib.clickbyjavascript("Profile_login_XPATH");
			commonLib.waitUntilUrlContains("home");
			commonLib.waitForPresenceOfElementLocated("GlobalSearch_Homepage_XPATH");
			driver.switchTo().defaultContent(); // To switch back to parent window
			// Logic applied as sometimes Logged in user Name does not comes up.
			if (commonLib.findElementPresence("Profile_loginMessage_XPATH") == false) {
				commonLib.refreshPage();
			}
			commonLib.waitForPresenceOfElementLocated("Profile_loginMessage_XPATH");

			if (validateProfileLogin("Profile_loginMessage_XPATH", toBeSearched)) {

				commonLib.syso("User is successfull logged in with " + toBeSearched);
			} else {
				commonLib.log(LogStatus.FAIL, "User is not Logged in With " + toBeSearched);
			}
		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "User is not Logged in With " + toBeSearched);
			e.printStackTrace();
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * validateProfileLogin Description : It validates profile login Arguments :
	 * String xpathOfLoginMessage, String profileNameToValidate Return value :
	 * true/false Example :
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	// Generic method to validate the profile name after login like we do login with
	// IC local.
	public boolean validateProfileLogin(String xpathOfLoginMessage, String profileNameToValidate) {
		try {
			return commonLib.getText(xpathOfLoginMessage).toUpperCase().contains(profileNameToValidate.toUpperCase());
		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "User is successfullly logged in with the selected profile");
			return false;
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name : logout
	 * Description : It logs out from profile page Arguments : NA Return value : NA
	 * Example : logout();
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void logout() {
		try {

			commonLib.refreshPage();// some times Logout link is not visible, if refresh page,it is visible
			commonLib.waitForPresenceOfElementLocated("Logout_Link_In_Home_Page_XPATH");
			int i = 1;
			while (i < 6) {
				if (!commonLib.isDisplayed("Logout_Link_In_Home_Page_XPATH")) {
					commonLib.refreshPage();// some times Logout link is not visible, if refresh page,it is visible
					commonLib.waitForPresenceOfElementLocated("Logout_Link_In_Home_Page_XPATH");
					i++;
				} else
					break;
			}
			String logout_UserName = commonLib.getText("Logout_Link_In_Home_Page_XPATH");
			int indexno = logout_UserName.indexOf("as");
			logout_UserName = logout_UserName.substring(indexno + 3);

			commonLib.clickbyjavascript("Logout_Link_In_Home_Page_XPATH");
			commonLib.syso("Logout as '" + logout_UserName + "'");

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Logout link is not exist.");
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Navigate_To_Existing_Account Description : It navigates to existing account
	 * Arguments : accountName Return value : NA Example :
	 * Navigate_To_Existing_Account(accountName)
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public void Navigate_To_Existing_Account(String accountName) throws InterruptedException, AWTException {
		commonLib.syso("Enter Account name in Global search=" + accountName);
		commonLib.clear("GlobalSearch_Homepage_XPATH");
		commonLib.sendKeys("GlobalSearch_Homepage_XPATH", accountName);
		Thread.sleep(4000);
		commonLib.click("GlobalSearch_Homepage_XPATH");
		// commonLib.KeyPress_Enter();
		commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();

		boolean accounts_Results_Table_Exist = commonLib
				.waitForVisibilityOf_DynamicXpath("Accounts_Results_Table_In_Home_Page_XPATH", accountName);

		if (accounts_Results_Table_Exist) {
			commonLib.syso("Accounts results table is open");
		} else {
			commonLib.syso("If Search result does not appear,Search again");
			commonLib.syso("Enter Account name in Global search=" + accountName);
			commonLib.clear("GlobalSearch_Homepage_XPATH");
			commonLib.sendKeys("GlobalSearch_Homepage_XPATH", accountName);
			Thread.sleep(1000);
			commonLib.click("GlobalSearch_Homepage_XPATH");
			// commonLib.KeyPress_Enter();
			commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
			accounts_Results_Table_Exist = commonLib
					.waitForVisibilityOf_DynamicXpath("Accounts_Results_Table_In_Home_Page_XPATH", accountName);
		}

		if (accounts_Results_Table_Exist) {
			commonLib.syso("Accounts results table is open");
		} else {
			commonLib.log(LogStatus.FAIL, "Accounts results table is not open");
		}

		commonLib.clickbyjavascriptWithDynamicValue("Accounts_Results_Table_In_Home_Page_XPATH", accountName);

		Thread.sleep(5000);
		// boolean accountName_Exist=
		// commonLib.waitForVisibilityOf_DynamicXpath("AccountName_Text_Header_In_Accounts_Page_XPATH",
		// accountName);
		boolean accountName_Exist = commonLib
				.WaitforPresenceofElement_Dynamic_Xpath("AccountName_Text_Header_In_Accounts_Page_XPATH", accountName);

		if (accountName_Exist) {
			commonLib.syso("Account ='" + accountName + "' page is open");
		} else {
			commonLib.log(LogStatus.FAIL, "Account ='" + accountName + "' page is not open");
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Navigate_To_Existing_Account_WithReload Description : It navigates to
	 * existing account with Reload in between Arguments : accountName Return value
	 * : NA Example : Navigate_To_Existing_Account_WithReload(accountName)
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public void Navigate_To_Existing_Account_WithReload(String accountName) throws InterruptedException, AWTException {
		commonLib.syso("Enter Account name in Global search=" + accountName);
		commonLib.clear("GlobalSearch_Homepage_XPATH");
		commonLib.sendKeys("GlobalSearch_Homepage_XPATH", accountName);
		Thread.sleep(4000);
		commonLib.click("GlobalSearch_Homepage_XPATH");
		// commonLib.KeyPress_Enter();
		commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();

		boolean accounts_Results_Table_Exist = commonLib
				.waitForVisibilityOf_DynamicXpath("Accounts_Results_Table_In_Home_Page_XPATH", accountName);

		if (accounts_Results_Table_Exist) {
			commonLib.syso("Accounts results table is open");
		} else {
			commonLib.syso("If Search result does not appear,Search again");
			commonLib.syso("Enter Account name in Global search=" + accountName);
			commonLib.clear("GlobalSearch_Homepage_XPATH");
			commonLib.sendKeys("GlobalSearch_Homepage_XPATH", accountName);
			Thread.sleep(1000);
			commonLib.click("GlobalSearch_Homepage_XPATH");
			// commonLib.KeyPress_Enter();
			commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
			accounts_Results_Table_Exist = commonLib
					.waitForVisibilityOf_DynamicXpath("Accounts_Results_Table_In_Home_Page_XPATH", accountName);
		}

		if (accounts_Results_Table_Exist) {
			commonLib.syso("Accounts results table is open");
		} else {
			commonLib.log(LogStatus.FAIL, "Accounts results table is not open");
		}

		commonLib.clickbyjavascriptWithDynamicValue("Accounts_Results_Table_In_Home_Page_XPATH", accountName);
		Thread.sleep(5000);
		commonLib.refreshPage();
		boolean accountName_Exist = commonLib
				.waitForVisibilityOf_DynamicXpath("AccountName_Text_Header_In_Accounts_Page_XPATH", accountName);
		Thread.sleep(5000);

		if (accountName_Exist) {
			commonLib.syso("Account ='" + accountName + "' page is open");
		} else {
			commonLib.log(LogStatus.FAIL, "Account ='" + accountName + "' page is not open");
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Open_DueDiligence_Recommendations_Page_Through_Related_List_Quick_Links
	 * Description : It opens DueDiligence Recommendations Page
	 * Through_Related_List_Quick_Links Arguments : NA Return value : NA Example
	 * :Open_DueDiligence_Recommendations_Page_Through_Related_List_Quick_Links()
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public void Open_DueDiligence_Recommendations_Page_Through_Related_List_Quick_Links() {
		commonLib.syso("Click \"Due Diligence Recommendations\" link from section \"Related List Quick Links\"");
		commonLib.click("DueDiligence_Recommendations_Through_Related_List_Quick_Links_In_Accounts_Page_XPATH");
		commonLib.waitForPresenceOfElementLocated("DueDilligenceText_IN_DueDiligenceRecommendations_Page_XPATH");

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Create_New_Account_In_Accounts_Page Description : It creates new account
	 * Arguments : accountName,countryOfOperation,ServicesProvided Return value :
	 * map object Example :
	 * Create_New_Account_In_Accounts_Page(accountName,countryOfOperation,
	 * ServicesProvided);//Generic Method to create Account.
	 * Create_New_Account_In_Accounts_Page("ICACCTest","Hong Kong"
	 * ,"Sales Generation")
	 * =============================================================================
	 * ===============================================
	 */

	public Map<String, String> Create_New_Account_In_Accounts_Page(String accountName, String countryOfOperation,
			String ServicesProvided) throws IOException, InterruptedException {
		Map<String, String> AccountInformation_Map = new HashMap<>();
		commonLib.log(LogStatus.PASS, "Enter Account Name='" + accountName + "' in Accounts page");
		commonLib.sendKeys("AccountName_Textbox_In_New_Account_Page_XPATH", accountName);

		commonLib.click("AccountName_Textbox_In_New_Account_Page_XPATH");

		commonLib.log(LogStatus.PASS, "Select Country Of Operation as =" + countryOfOperation);
		commonLib.click("CountryofOperation_Dropdown_In_New_Account_Page_XPATH");
		commonLib.WaitforPresenceofElement_Dynamic_Xpath("CountryofOperation_Values_Dropdown_In_New_Account_Page_XPATH",
				countryOfOperation);

		commonLib.clickbyjavascriptWithDynamicValue("CountryofOperation_Values_Dropdown_In_New_Account_Page_XPATH",
				countryOfOperation);

		commonLib.scroll_view("MoveselectiontoChosen_Icon_In_SelectServices_Menu_In_XPATH");// updated 'Services
																							// provided' field modifed
																							// as 'Select Services'

		commonLib.log(LogStatus.PASS,
				"Select Service Provided as '" + ServicesProvided + "' and move from Available to Chosen section");

		commonLib.clickWithDynamicValue("SelectServices_Menu_In_New_Account_Page_XPATH", ServicesProvided);
		// updated as 'Services provided' field modifed as 'Select Services'

		commonLib.click("MoveselectiontoChosen_Icon_In_SelectServices_Menu_In_XPATH");// updated as 'Services provided'
																						// field modifed as 'Select
																						// Services'

		commonLib.WaitforPresenceofElement_Dynamic_Xpath(
				"Chosen_Menu_In_SelectServices_Section_In_New_Account_Page_XPATH", ServicesProvided);
		// updated as 'Services provided' field modifed as 'Select Services'

		commonLib.log(LogStatus.PASS, "Click \"Save\" button");
		commonLib.clickwithoutWait("Save_Button_In_New_Account_Page_XPATH");

		commonLib.waitForPresenceOfElementLocated("ActiveRiskRating_Text_In_Accounts_Page_XPATH");

		commonLib.log(LogStatus.PASS, "Capture unique system number and validate the following	");
		String Uniquesystemnumber = commonLib.getText("Uniquesystemnumber_Text_In_Accounts_Page_XPATH");
		commonLib.log(LogStatus.PASS, "Uniquesystemnumber='" + Uniquesystemnumber + "'");

		String AccountOwner = commonLib.getText("AccountOwner_In_Accounts_Page_XPATH");
		commonLib.log(LogStatus.PASS, "AccountOwner='" + AccountOwner + "'");

		String CountryofOperation = commonLib.getText("CountryofOperation_Dropdown_In_Accounts_Page_XPATH");
		commonLib.log(LogStatus.PASS, "CountryofOperation='" + CountryofOperation + "'");

		String ServicesProvided_Text = commonLib.getText("SelectServices_Text_In__Accounts_Page_XPATH");
		// updated as 'Services provided' field modifed as 'Select Services'
		commonLib.log(LogStatus.PASS, "ServicesProvided_Text='" + ServicesProvided_Text + "'");

		String ActiveRiskRating = commonLib.getAttribute("ActiveRiskRating_Text_In_Accounts_Page_XPATH", "value");
		commonLib.log(LogStatus.PASS, "ActiveRiskRating='" + ActiveRiskRating + "'");

		String DefaultRiskRating = commonLib.getAttribute("DefaultRiskRating_Text_In_Accounts_Page_XPATH", "value");
		commonLib.log(LogStatus.PASS, "DefaultRiskRating='" + DefaultRiskRating + "'");

		AccountInformation_Map.put("Uniquesystemnumber", Uniquesystemnumber);
		AccountInformation_Map.put("AccountOwner", AccountOwner);
		AccountInformation_Map.put("CountryofOperation", CountryofOperation);
		AccountInformation_Map.put("ServicesProvided_Text", ServicesProvided_Text);

		AccountInformation_Map.put("ActiveRiskRating", ActiveRiskRating);
		AccountInformation_Map.put("DefaultRiskRating", DefaultRiskRating);

		return AccountInformation_Map;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * open_NewlyCreated_DueDigilence Description : It opens newly created Due
	 * Diligence page Arguments : accountName,countryOfOperation,ServicesProvided
	 * Return value : map object Example :
	 * Create_New_Account_In_Accounts_Page(accountName,countryOfOperation,
	 * ServicesProvided);//Generic Method to create Account.
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public void open_NewlyCreated_DueDigilence(String xpathofDueDigilenceLink, String key) {

		commonLib.clickWithDynamicValue(xpathofDueDigilenceLink, key);
		commonLib.waitForPresenceOfElementLocated("DueDiligenceRecommendationTab_AfterClicking_With_ICLocal_XPATH");
		commonLib.waitForPresenceOfElementLocated("DueDiligenceRecommendationText_AfterClicking_With_ICLocal_XPATH");

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * open_New_Account_In_Accounts_Page Description : It opens new account in
	 * accounts page Arguments : NA Return value : NA Example
	 * :open_New_Account_In_Accounts_Page()
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public void open_New_Account_In_Accounts_Page() throws IOException, InterruptedException {
		commonLib.waitForVisibilityOf_DynamicXpath("New_Button_In_All_Tabs_XPATH", "Accounts");
		commonLib.clickbyjavascriptWithDynamicValue("New_Button_In_All_Tabs_XPATH", "Accounts");

		boolean newAccount_Page_Open = commonLib
				.waitForPresenceOfElementLocated("AccountName_Textbox_In_New_Account_Page_XPATH");

		if (newAccount_Page_Open) {
			commonLib.syso("New Account page  is open in Accounts page");
		} else {
			commonLib.log(LogStatus.FAIL, "New Account page  is not open in Accounts page");
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name : Open Related
	 * Contact from Account Homepage Description : It opens the Related contact Page
	 * from Account Page. Arguments : NA Return value : NA Example
	 * :Open_Related_Contact_FromAccount()
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void Open_Related_Contact_FromAccount() {
		commonLib.log(LogStatus.INFO, "Click \"Related Contacts\" link from section \"Related List Quick Links\"");
		commonLib.click("OpenRelatedContact_AccountPage_XPATH");
		commonLib.waitForPresenceOfElementLocated("NewContactButton_RelatedContacts_AccountPage_XPATH");

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page Description :
	 * It opens the given link text through Related List Quick Links from exsiting
	 * accounts page Arguments : Link_Text_From_RelatedListQuickLinks Return value :
	 * NA Example : sfdcLib.
	 * Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Support Cases"
	 * ); sfdcLib.
	 * Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Account Team"
	 * ); sfdcLib.Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page(
	 * "Activities"); sfdcLib.
	 * Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Related Contacts"
	 * ); sfdcLib.Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page(
	 * "Contracts"); sfdcLib.
	 * Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Channel Margins"
	 * ); sfdcLib.
	 * Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Account History"
	 * ); sfdcLib.
	 * Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Notes & Attachments"
	 * );
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page(
			String Link_Text_From_RelatedListQuickLinks) throws InterruptedException {
		try {
			commonLib.refreshPage();// as it is not clicking
			commonLib.waitForPresenceOfElementLocated("AccountDetailPage_RelatedQuickLinks_ShowAllButton_XPATH");
			Thread.sleep(5000);
			commonLib.syso("Click 'Show All' link through Related List Quick Links");
			commonLib.click("AccountDetailPage_RelatedQuickLinks_ShowAllButton_XPATH");
			Thread.sleep(2000);
			commonLib.waitForPresenceOfElementLocated("AccountDetailPage_RelatedQuickLinks_ShowLessButton_XPATH");
			Thread.sleep(1000);
			commonLib
					.syso("Click '" + Link_Text_From_RelatedListQuickLinks + "' link through Related List Quick Links");
			commonLib.clickbyjavascriptWithDynamicValue("RelatedListQuickLinks_In_Accounts_Page_XPATH",
					Link_Text_From_RelatedListQuickLinks);
			Thread.sleep(2000);

			Boolean is_HeaderTitle_Open = commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"HeaderTitle_For_RelatedListQuickLinks_In_Accounts_XPATH", Link_Text_From_RelatedListQuickLinks);

			if (is_HeaderTitle_Open) {
				commonLib.syso("'" + Link_Text_From_RelatedListQuickLinks
						+ "' page is open through 'Related List Quick Links' in Accounts ");
				// commonLib.getScreenshot();
			} else {
				commonLib.log(LogStatus.FAIL, "'" + Link_Text_From_RelatedListQuickLinks
						+ "' page is not open through 'Related List Quick Links' in Accounts ");
			}
		} catch (Exception e) {

			commonLib.log(LogStatus.FAIL, "'" + Link_Text_From_RelatedListQuickLinks
					+ "' page is not open through 'Related List Quick Links' in Accounts ");
			e.printStackTrace();
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Create_New_Support_Case_From_Accounts_Page Description : It creates new
	 * Support case from existing accounts page Arguments :
	 * Select_Record_Type_value, priority, issueCategory, issueSubCategory, subject,
	 * description, action, dueDate, closeNotes Return value : Returns Support Case
	 * Number Example :
	 * Create_New_Support_Case_From_Accounts_Page("IC Issue","High","Due Diligence"
	 * ,"Applicable law violations","My subject",
	 * "My Issue description","Resolve prior to Due Diligence","7/24/2020");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public String Create_New_Support_Case_From_Accounts_Page(String Select_Record_Type_value, String priority,
			String issueCategory, String issueSubCategory, String subject, String description, String action,
			String dueDate, String closeNotes) throws IOException, InterruptedException {
		String supportCaseNumber = null;
		try {

			Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Support Cases");

			commonLib.syso("Click 'New' button");
			commonLib.click("New_Button_In_Support_Case_Page_XPATH");

			commonLib.syso("Select the 'Select a Record Type'as IC case and click on Next button ");
			commonLib.selectDropdownVisibleText("Selecta_RecordType_Dropdown_In_NewCase_Page_XPATH",
					Select_Record_Type_value);

			commonLib.click("Next_Button_In_New_Case_Page_XPATH");

			Boolean is_NewSupportCase_Open = commonLib
					.waitForPresenceOfElementLocated("SupportCases_Heading_In_Accounts_XPATH");
			if (is_NewSupportCase_Open) {
				commonLib.syso("Pop up form with all the required details for Support case creation should be opened.");
			} else {
				commonLib.log(LogStatus.FAIL,
						"Pop up form with all the required details for Support case creation is not opened.");
			}

			// Validate the Account Name should be prepopulated on the Support case creation
			// screen //???? account name field is not exist

			commonLib.syso("Enter below Mandatory fields: ");
			commonLib.syso("Priority:" + priority);
			commonLib.click("Priority_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickWithDynamicValue("Priority_DropdownMenu_In_NewSupportCase_XPATH", priority);

			commonLib.syso("Issue Category:" + issueCategory);
			commonLib.click("IssuesCategories_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH", issueCategory);

			commonLib.syso("Issue Sub Category:" + issueSubCategory);
			commonLib.click("IssueSubcategory_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH", issueSubCategory);

			commonLib.syso("Subject: " + subject);
			commonLib.sendKeys("Subject_Editbox_In_DescriptionInformation_Section_In_New_Support_Case_Page_XPATH",
					subject);

			commonLib.syso("Description: " + description);
			commonLib.sendKeys("Description_Editbox_In_DescriptionInformation_Section_In_New_Support_Case_Page_XPATH",
					description);

			commonLib.syso("Action:" + action);
			commonLib.click("Action_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickWithDynamicValue("Action_DropdownMenu_In_NewSupportCase_XPATH", action);

			commonLib.syso("Due Date:" + dueDate);
			commonLib.sendKeys("DueDate_Editbox_In_NewSupportCase_XPATH", dueDate);

			commonLib.syso("Close Notes in Remediation:" + closeNotes);
			commonLib.sendKeys("CloseNotes_Editbox_In_NewSupportCase_XPATH", closeNotes);

			commonLib.waitForPresenceOfElementLocated("Save_Button_In_NewSupportCase_Page_XPATH");

			commonLib.syso("Click on Save after filling all the details");
			commonLib.click("Save_Button_In_NewSupportCase_Page_XPATH");

			supportCaseNumber = commonLib.getText("SupportCaseNumber_Text_In_SupportCase_Page_XPATH");
			commonLib.syso("Support Case ID:" + supportCaseNumber);

			commonLib.getScreenshot();
			return supportCaseNumber;
		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Failed.Not able to create New Support Case From Accounts_Page");
			e.printStackTrace();
		}
		return supportCaseNumber;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * open_SupportCases_Tab_From_Home_Page Description : It opens Support Cases
	 * page through tab Arguments : NA Return value : NA Example :
	 * open_SupportCases_Tab_From_Home_Page();
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	// public void open_SupportCases_Tab_From_Home_Page() throws IOException
	// {
	// try {
	// open_Given_Tab_In_Home_Page("Support Cases");
	// }
	// catch (InterruptedException e) {
	// commonLib.log(LogStatus.FAIL, "Failed.Not able to open 'Support Cases' tab
	// from home page");
	// e.printStackTrace();
	// }
	//
	// }

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * navigate_To_Existing_SupportCase_From_Support_Page Description : It navigates
	 * Arguments : supportCaseNo Return value : NA Example :
	 * navigate_To_Existing_SupportCase_From_Support_Page("06424969");
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public void navigate_To_Existing_SupportCase_From_Support_Page(String supportCaseNo)
			throws IOException, AWTException {
		try {
			open_Given_Tab_In_Home_Page("Support Cases");
			commonLib.log(LogStatus.PASS,
					"Type Support Case No='" + supportCaseNo + "' in Search edit box in support cases page");
			commonLib.sendKeys("SupportCases_Searchthislist_Editbox_XPATH", supportCaseNo);

			commonLib.clickwithoutWait("SupportCases_Searchthislist_Editbox_XPATH");
			commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();

			commonLib.waitForPresenceOfElementLocated("supportCases_FirstRow_Search_Results_Table_XPATH");

			boolean supportCases_Results_Table_Exist = commonLib
					.waitForVisibilityOf_DynamicXpath("supportCases_Search_Results_Table_XPATH", supportCaseNo);

			if (supportCases_Results_Table_Exist) {
				commonLib.log(LogStatus.PASS, "Support Cases search results table is open");
				commonLib.getScreenshot();
			} else {
				commonLib.log(LogStatus.FAIL, "Support Cases search results table is not  open");
			}

			commonLib.log(LogStatus.PASS, "Click Support Case No='" + supportCaseNo + "'");
			commonLib.clickWithDynamicValue("supportCases_Search_Results_Table_XPATH", supportCaseNo);
			commonLib.waitForPresenceOfElementLocated("SupportCaseNumber_Text_In_SupportCase_Page_XPATH");

			String Actual_supportCaseNumber = commonLib.getText("SupportCaseNumber_Text_In_SupportCase_Page_XPATH");
			if (supportCaseNo.equals(Actual_supportCaseNumber)) {
				commonLib.log(LogStatus.PASS, "SupportCaseNo ='" + supportCaseNo + "' page is open");
				commonLib.getScreenshot();
			} else {
				commonLib.log(LogStatus.FAIL, "SupportCaseNo ='" + supportCaseNo + "' page is not open");
			}

		} catch (InterruptedException e) {
			commonLib.log(LogStatus.FAIL, "Failed.Not able to navigate to existing support case='" + supportCaseNo
					+ "' from support page.Please check Support case is valid or not");
			e.printStackTrace();
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * ChangeOwner_InSupportDetail Description : It allowed user to change the owner
	 * from given profile name Arguments : Profile to change Return value : NA
	 * Example : ChangeOwner_InSupportDetail("06424969", "IC Global");
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public void ChangeOwner_InSupportDetail(String changeOwner) {
		try {
			// commonLib.scroll_view("SupportCaseDetail_ChangeOwner_Button_XPATH");
			commonLib.performHover("SupportCaseDetail_ChangeOwner_Button_XPATH");// Updated by Brahma 5-Aug-2020
			commonLib.click("SupportCaseDetail_ChangeOwner_Button_XPATH");
			commonLib.waitForPresenceOfElementLocated("SupportCase_ChangeOwner_Textbox_XPATH");
			Thread.sleep(3000);
			commonLib.sendKeys("SupportCase_ChangeOwner_Textbox_XPATH", changeOwner);
			Thread.sleep(6 * 1000);
			commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
			Thread.sleep(5000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("SupportCase_ChangeOwnerLink_XPATH", changeOwner);
			commonLib.clickWithDynamicValue("SupportCase_ChangeOwnerLink_XPATH", changeOwner);
			Thread.sleep(2 * 1000);
			commonLib.waitForPresenceOfElementLocated("SupportCase_ChangeOwner_Button_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.click("SupportCase_ChangeOwner_Button_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.waitForPresenceOfElementLocated("SupportCase_ChangeOwner_SuccessMessage_XPATH");
			if (commonLib.getText("SupportCase_ChangedOwnerField_XPATH", changeOwner) == changeOwner) {
				commonLib.syso("Owner is changed successfully as " + changeOwner);

			}
		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Owner is NOT changed as " + changeOwner);
			e.printStackTrace();
		}

	}
	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * navigate_To_Existing_SupportCase_From_Global_Search(Support Case number)
	 * Description : It allows user to traverse the existing support case through
	 * global search Arguments : Support Case number Return value : NA Example :
	 * navigate_To_Existing_SupportCase_From_Global_Search("06424969");
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public void navigate_To_Existing_SupportCase_From_Global_Search(String supportCaseNo)
			throws AWTException, InterruptedException {

		commonLib.click("GlobalSearch_Homepage_XPATH");
		Thread.sleep(2000);

		commonLib.sendKeys("Support_Case_Search_XPATH", supportCaseNo);

		commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
		boolean support_Results_Table_Exist = commonLib
				.waitForVisibilityOf_DynamicXpath("Support_Results_Table_In_Home_Page_XPATH", supportCaseNo);

		if (support_Results_Table_Exist) {

			commonLib.syso("Support Case result is coming successfully");
		} else {
			commonLib.log(LogStatus.FAIL, "Not able to search the given support Case i.e. :" + supportCaseNo);
		}

		commonLib.clickbyjavascriptWithDynamicValue("Support_Results_Table_In_Home_Page_XPATH", supportCaseNo);

		commonLib.waitForPresenceOfElementLocated("SupportCaseNumber_Text_In_SupportCase_Page_XPATH");

		String Actual_supportCaseNumber = commonLib.getText("SupportCaseNumber_Text_In_SupportCase_Page_XPATH");
		if (supportCaseNo.equals(Actual_supportCaseNumber)) {

			commonLib.syso("SupportCaseNo ='" + supportCaseNo + "' page is open");
			Thread.sleep(8 * 1000);

		} else {
			commonLib.log(LogStatus.FAIL, "SupportCaseNo ='" + supportCaseNo + "' page is not open");
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * changeStatusOf_SupportCase(Status to Set, outlinerReview Status) Description
	 * : It allows user to change the status of support case to the given status
	 * value. Arguments : Status to Set, outlinerReview Status Return value : NA
	 * Example : changeStatusOf_SupportCase("Closed","2A Franchise Mix")
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public void changeStatusOf_SupportCase(String statusToSet, String outlinerReviewStatus) {
		try {
			commonLib.click("SupportCase_EditStatusButton_XPATH");
			commonLib.waitForPresenceOfElementLocated("SupportCase_StatusDropDown_XPATH");
			Thread.sleep(5 * 1000);
			commonLib.scroll(0, 150);
			commonLib.clickbyjavascript("SupportCase_StatusDropDown_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("SupportCase_StatusDropDown_Value_XPATH", statusToSet);
			commonLib.clickWithDynamicValue("SupportCase_StatusDropDown_Value_XPATH", statusToSet);
			Thread.sleep(5 * 1000);
			commonLib.scroll(0, 300);
			commonLib.clickbyjavascript("SupportCase_OutlinerReview_Dropdown_XPATH");
			commonLib.clickWithDynamicValue("SupportCase_OutlinerReview_DropdownValue_XPATH", outlinerReviewStatus);
			Thread.sleep(1000);
			commonLib.clear("SupprtCase_CloseNotes_Editbox_XPATH");
			Thread.sleep(1000);
			commonLib.sendKeys("SupprtCase_CloseNotes_Editbox_XPATH", "Closing Notes for status as " + statusToSet
					+ " OutlinerReview Status as: " + outlinerReviewStatus);
			commonLib.click("Support_Case_Save_XPATH");
			commonLib.waitForPresenceOfElementLocated("SupportCase_EditStatusButton_XPATH");
			commonLib.waitForVisibilityOf("SupportCase_EditStatusButton_XPATH");
			Thread.sleep(8 * 1000);

		} catch (Exception e) {
			commonLib.syso("Not able to change the Status of Support Case");
			e.printStackTrace();
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * whiteListing_Given_ICAccount Description : It whitelists the given account
	 * with given reason and comments Arguments : Return value : Example :
	 * whiteListing_Given_ICAccount("Blacklistemailtesing", "Whitelist Reason",
	 * "Test additional info");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void whiteListing_Given_ICAccount(String accountName, String restrictedPartyListReason,
			String additionalInformation) throws InterruptedException, AWTException {

		try {
			Navigate_To_Existing_Account(accountName);

			commonLib.performHover("AccountsPage_RestrictedPartyListDetails_Section_XPATH");
			commonLib.performHover("Accounts_RestrictedPartyList_EditableImageIcon_XPATH");

			commonLib.log(LogStatus.PASS, "Click 'Edit Restricted Party List' button  ");
			commonLib.click("Accounts_RestrictedPartyList_EditableImageIcon_XPATH");
			Thread.sleep(1000);

			commonLib.log(LogStatus.PASS, "Fill following details in restricted list section: ");
			commonLib.log(LogStatus.PASS, "i.Enable  black list checkbox  ");

			commonLib.press_ArrowDown_Given_No_Of_Times(6);
			commonLib.press_Space_Using_ActionsClass();
			Thread.sleep(2000);

			commonLib.log(LogStatus.PASS, "ii.Select Restricted Party List Reason as '" + restrictedPartyListReason
					+ "' and move from Available to Chosen section");
			commonLib.clickWithDynamicValue("Accounts_RestrictedPartyListReason_Menu_XPATH", restrictedPartyListReason);

			commonLib.click("AccountsPage_MoveselectiontoChosen_Icon_In_RestrictedPartyListReason_Menu_In_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"AccountsPage_Chosen_Menu_In_RestrictedPartyListReason_Section_XPATH", restrictedPartyListReason);

			commonLib.log(LogStatus.PASS, " iii.Fill comments in Additional Information i.e " + additionalInformation);
			commonLib.clear("AccountsPage_AdditionalInformation_Editbox_RestrictedPartyList_Section_XPATH");
			commonLib.sendKeys("AccountsPage_AdditionalInformation_Editbox_RestrictedPartyList_Section_XPATH",
					additionalInformation);

			commonLib.log(LogStatus.PASS, "Click 'Save' button");
			commonLib.clickwithoutWait("Save_Button_In_New_Account_Page_XPATH");

			// Account should be saved with black list checkbox selection, reason, comments

			boolean is_restrictedPartyListReason_Status = commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"AccountsPage_RestrictedPartyListReason_Editbox_XPATH", restrictedPartyListReason);

			if (is_restrictedPartyListReason_Status) {
				commonLib.log(LogStatus.PASS, "restrictedPartyListReason=" + restrictedPartyListReason + " is found");
			} else {
				commonLib.log(LogStatus.FAIL,
						"restrictedPartyListReason='" + restrictedPartyListReason + "' is not found");
			}

			boolean is_additionalInformation_Status = commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"AccountsPage__AdditionalInformation_Editbox_XPATH", additionalInformation);
			if (is_additionalInformation_Status) {
				commonLib.log(LogStatus.PASS, "additionalInformation='" + additionalInformation + "' is found");
			} else {
				commonLib.log(LogStatus.FAIL, "additionalInformation='" + additionalInformation + "' is not found");
			}

			commonLib.getScreenshot();

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Not able to whitelist the given Account='" + accountName + "'");
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Create_New_Support_Case_From_SupportCases_Page Description : It creates new
	 * Support case from existing support cases tab Arguments :
	 * Select_Record_Type_value, priority, issueCategory, issueSubCategory, subject,
	 * description, action, dueDate, closeNotes Return value : Returns Support Case
	 * Number Example : Create_New_Support_Case_From_SupportCases_Page("IC Issue"
	 * ,"High","Due Diligence","Applicable law violations","My subject",
	 * "My Issue description","Resolve prior to Due Diligence","7/24/2020");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public String Create_New_Support_Case_From_SupportCases_Page(String Select_Record_Type_value, String priority,
			String issueCategory, String issueSubCategory, String subject, String description, String action,
			String dueDate, String closeNotes) throws IOException, InterruptedException {
		String supportCaseNumber = null;
		try {
			open_SupportCases_Tab_From_Home_Page();

			// commonLib.log(LogStatus.PASS, "Click 'New' button");
			commonLib.syso("Click 'New' button");
			Thread.sleep(2000);
			commonLib.click("New_Button_In_Support_Case_Page_XPATH");
			Thread.sleep(2000);
			commonLib.syso("Select the 'Select a Record Type'as IC case and click on Next button ");
			Thread.sleep(2000);
			commonLib.selectDropdownVisibleText("Selecta_RecordType_Dropdown_In_NewCase_Page_XPATH",
					Select_Record_Type_value);
			Thread.sleep(2000);
			commonLib.click("Next_Button_In_New_Case_Page_XPATH");

			Boolean is_NewSupportCase_Open = commonLib
					.waitForPresenceOfElementLocated("Priority_Dropdown_In_NewSupportCase_XPATH");
			if (is_NewSupportCase_Open) {
				commonLib.syso("Pop up form with all the required details for Support case creation should be opened.");
			} else {
				commonLib.log(LogStatus.FAIL,
						"Pop up form with all the required details for Support case creation is not opened.");
			}

			// Validate the Account Name should be prepopulated on the Support case creation
			// screen //???? account name field is not exist

			commonLib.syso("Enter below Mandatory fields: ");
			commonLib.syso("Priority :" + priority);
			commonLib.click("Priority_Dropdown_In_NewSupportCase_XPATH");
			;
			commonLib.clickWithDynamicValue("Priority_DropdownMenu_In_NewSupportCase_XPATH", priority);
			commonLib.syso("Issue Category:" + issueCategory);
			commonLib.click("IssuesCategories_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH", issueCategory);
			commonLib.syso("Issue Sub Category:" + issueSubCategory);
			commonLib.click("IssueSubcategory_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH", issueSubCategory);
			commonLib.syso("Subject: " + subject);
			commonLib.sendKeys("Subject_Editbox_In_DescriptionInformation_Section_In_New_Support_Case_Page_XPATH",
					subject);
			commonLib.syso("Description: " + description);
			commonLib.sendKeys("Description_Editbox_In_DescriptionInformation_Section_In_New_Support_Case_Page_XPATH",
					description);
			commonLib.syso("Action:" + action);
			commonLib.click("Action_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickWithDynamicValue("Action_DropdownMenu_In_NewSupportCase_XPATH", action);
			commonLib.syso("Due Date:" + dueDate);
			commonLib.sendKeys("DueDate_Editbox_In_NewSupportCase_XPATH", dueDate);
			commonLib.syso("Close Notes in Remediation:" + closeNotes);
			commonLib.sendKeys("CloseNotes_Editbox_In_NewSupportCase_XPATH", closeNotes);
			commonLib.waitForPresenceOfElementLocated("Save_Button_In_NewSupportCase_Page_XPATH");
			commonLib.syso("Click on Save after filling all the details");
			commonLib.click("Save_Button_In_NewSupportCase_Page_XPATH");
			supportCaseNumber = commonLib.getText("SupportCaseNumber_Text_In_SupportCase_Page_XPATH");
			commonLib.syso("Captured Support Case ID:" + supportCaseNumber);
			Thread.sleep(8 * 1000);
			commonLib.getScreenshot();
			return supportCaseNumber;
		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Failed.Not able to create New Support Case From Support cases page");
			e.printStackTrace();
		}
		return supportCaseNumber;
	}

	// Same as Above just in this we are attaching Account Name with the Support
	// Case
	public String Create_New_Support_Case_From_SupportCases_Page_AccountName(String Select_Record_Type_value,
			String AccountName, String priority, String issueCategory, String issueSubCategory, String subject,
			String description, String action, String dueDate, String closeNotes)
			throws IOException, InterruptedException {
		String supportCaseNumber = null;
		try {
			open_SupportCases_Tab_From_Home_Page();

			// commonLib.log(LogStatus.PASS, "Click 'New' button");
			commonLib.syso("Click 'New' button");
			commonLib.waitForPresenceOfElementLocated("New_Button_In_Support_Case_Page_XPATH");
			List<WebElement> ls = commonLib.findElements("New_Button_In_Support_Case_Page_XPATH");
			for (WebElement webElement : ls) {
				if (webElement.isDisplayed() == true)
					webElement.click();
			}

			commonLib.syso("Select the 'Select a Record Type'as IC case and click on Next button ");
			commonLib.selectDropdownVisibleText("Selecta_RecordType_Dropdown_In_NewCase_Page_XPATH",
					Select_Record_Type_value);

			commonLib.click("Next_Button_In_New_Case_Page_XPATH");

			Boolean is_NewSupportCase_Open = commonLib
					.waitForPresenceOfElementLocated("Priority_Dropdown_In_NewSupportCase_XPATH");
			if (is_NewSupportCase_Open) {
				commonLib.syso("Pop up form with all the required details for Support case creation should be opened.");
			} else {
				commonLib.log(LogStatus.FAIL,
						"Pop up form with all the required details for Support case creation is not opened.");
			}

			// Validate the Account Name should be prepopulated on the Support case creation
			// screen //???? account name field is not exist

			commonLib.syso("Enter below Mandatory fields: ");
			commonLib.syso("Priority :" + priority);

			commonLib.click("Priority_Dropdown_In_NewSupportCase_XPATH");
			;
			commonLib.clickWithDynamicValue("Priority_DropdownMenu_In_NewSupportCase_XPATH", priority);

			commonLib.sendKeys("NewSupportCase_SupportTab_SearchAccount_XPATH", AccountName);
			Thread.sleep(2000);
			commonLib.clickbyjavascriptWithDynamicValue("NewSupportCase_SupportTab_AccountNameSuggestion_XPATH",
					AccountName);

			commonLib.syso("Issue Category:" + issueCategory);
			commonLib.clickbyjavascript("IssuesCategories_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickbyjavascriptWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH",
					issueCategory);

			commonLib.syso("Issue Sub Category:" + issueSubCategory);
			commonLib.clickbyjavascript("IssueSubcategory_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickbyjavascriptWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH",
					issueSubCategory);

			commonLib.syso("Subject: " + subject);
			commonLib.sendKeys("Subject_Editbox_In_DescriptionInformation_Section_In_New_Support_Case_Page_XPATH",
					subject);

			commonLib.syso("Description: " + description);
			commonLib.sendKeys("Description_Editbox_In_DescriptionInformation_Section_In_New_Support_Case_Page_XPATH",
					description);

			commonLib.syso("Action:" + action);
			commonLib.click("Action_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickbyjavascriptWithDynamicValue("Action_DropdownMenu_In_NewSupportCase_XPATH", action);

			commonLib.syso("Due Date:" + dueDate);
			commonLib.sendKeys("DueDate_Editbox_In_NewSupportCase_XPATH", dueDate);

			commonLib.syso("Close Notes in Remediation:" + closeNotes);
			commonLib.sendKeys("CloseNotes_Editbox_In_NewSupportCase_XPATH", closeNotes);

			commonLib.waitForPresenceOfElementLocated("Save_Button_In_NewSupportCase_Page_XPATH");

			commonLib.syso("Click on Save after filling all the details");
			commonLib.click("Save_Button_In_NewSupportCase_Page_XPATH");

			supportCaseNumber = commonLib.getText("SupportCaseNumber_Text_In_SupportCase_Page_XPATH");
			commonLib.syso("Captured Support Case ID:" + supportCaseNumber);
			Thread.sleep(8 * 1000);

			return supportCaseNumber;
		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Failed.Not able to create New Support Case From Support cases page");
			e.printStackTrace();

		}
		return supportCaseNumber;
	}

	// Edit Support Case Method
	public void Edit_SupportCase_FromDetailPage(String issueCategory, String issueSubCategory, String Status,
			String closeNotes) throws IOException, InterruptedException {

		try {

			commonLib.syso("Click 'Edit' button");
			commonLib.waitForPresenceOfElementLocated("SupportCaseDetail_EditButton_UAT_XPATH");
			commonLib.click("SupportCaseDetail_EditButton_UAT_XPATH");
			commonLib.waitForPresenceOfElementLocated("IssuesCategories_Dropdown_In_NewSupportCase_XPATH");
			Thread.sleep(1000);
			commonLib.syso("Issue Category:" + issueCategory);
			commonLib.click("IssuesCategories_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH", issueCategory);
			Thread.sleep(1000);
			commonLib.syso("Issue Sub Category:" + issueSubCategory);
			commonLib.click("IssueSubcategory_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH", issueSubCategory);
			Thread.sleep(1000);
			commonLib.click("IssueStatus_Dropdown_NewSupportCase_XPATH");
			commonLib.syso("Issue Status :" + Status);
			commonLib.clickWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH", Status);
			Thread.sleep(1000);
			commonLib.clear("CloseNotes_Editbox_In_NewSupportCase_XPATH");
			commonLib.syso("Close Notes in Remediation:" + closeNotes);
			commonLib.sendKeys("CloseNotes_Editbox_In_NewSupportCase_XPATH", closeNotes);
			Thread.sleep(1000);
			commonLib.waitForPresenceOfElementLocated("Save_Button_In_NewSupportCase_Page_XPATH");
			commonLib.syso("Click on Save after filling all the details");
			commonLib.click("Save_Button_In_NewSupportCase_Page_XPATH");
			commonLib.waitforInvisibilityOfElement("SupportCaseEdit_HeaderText_XPATH");
			commonLib.waitForVisibilityOf("SupportCaseDetail_EditButton_UAT_XPATH");

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Failed.Not able to Edit Support Case From Support cases page");
			commonLib.getScreenshot();
			e.printStackTrace();
		}

	}

	/*
	 * 
	 * /*
	 * =============================================================================
	 * =============================================== Function Name :
	 * open_SupportCases_Tab_From_Home_Page Description : It opens Support Cases
	 * page through tab Arguments : NA Return value : NA Example :
	 * open_SupportCases_Tab_From_Home_Page();
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void open_SupportCases_Tab_From_Home_Page() throws IOException {
		try {
			open_Given_Tab_In_Home_Page("Cases");
		} catch (InterruptedException e) {
			commonLib.log(LogStatus.FAIL, "Failed.Not able to open 'Support Cases' tab from home page");
			e.printStackTrace();
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * changeStatusOf_SupportCase(Status to Set, outlinerReview Status) Description
	 * : It allows user to change the status of support case to the given status
	 * value. Arguments : Status to Set, outlinerReview Status Return value : NA
	 * Example : changeStatusOf_SupportCase("Closed","2A Franchise Mix")
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	// public void changeStatusOf_SupportCase(String statusToSet, String
	// outlinerReviewStatus)
	// {
	// try {
	// commonLib.click("SupportCase_EditStatusButton_XPATH");
	// commonLib.waitForPresenceOfElementLocated("SupportCase_StatusDropDown_XPATH");
	// Thread.sleep(5*1000);
	// commonLib.scroll(0, 150);
	// commonLib.clickbyjavascript("SupportCase_StatusDropDown_XPATH");
	// commonLib.WaitforPresenceofElement_Dynamic_Xpath("SupportCase_StatusDropDown_Value_XPATH",
	// statusToSet);
	// commonLib.clickWithDynamicValue("SupportCase_StatusDropDown_Value_XPATH",
	// statusToSet);
	// Thread.sleep(5*1000);
	// commonLib.scroll(0,300);
	// commonLib.clickbyjavascript("SupportCase_OutlinerReview_Dropdown_XPATH");
	// commonLib.clickWithDynamicValue("SupportCase_OutlinerReview_DropdownValue_XPATH",
	// outlinerReviewStatus);
	// Thread.sleep(1000);
	// commonLib.clear("SupprtCase_CloseNotes_Editbox_XPATH");
	// Thread.sleep(1000);
	// commonLib.sendKeys("SupprtCase_CloseNotes_Editbox_XPATH", "Closing Notes for
	// status as " + statusToSet + " OutlinerReview Status as: " +
	// outlinerReviewStatus);
	// commonLib.click("Support_Case_Save_XPATH");
	// commonLib.waitForPresenceOfElementLocated("SupportCase_EditStatusButton_XPATH");
	// commonLib.waitForVisibilityOf("SupportCase_EditStatusButton_XPATH");
	// Thread.sleep(8*1000);
	//
	// } catch (Exception e) {
	// commonLib.syso("Not able to change the Status of Support Case");
	// e.printStackTrace();
	// }
	//
	//
	// }

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * restricting_Given_ICAccount Description : It restricts the given account with
	 * given reason and comments Arguments : accountName, restrictedPartyListReason,
	 * additionalInformation ) Return value : Example :
	 * restricting_Given_ICAccount("STNACC2169", "Illegal Activity",
	 * "My Additional information 1");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void restricting_Given_ICAccount(String accountName, String restrictedPartyListReason,
			String additionalInformation) throws InterruptedException, AWTException {

		try {
			Navigate_To_Existing_Account(accountName);

			commonLib.performHover("AccountsPage_RestrictedPartyListDetails_Section_XPATH");
			commonLib.performHover("Accounts_RestrictedPartyList_EditableImageIcon_XPATH");

			commonLib.syso("Click 'Edit Restricted Party List' button  ");
			commonLib.click("Accounts_RestrictedPartyList_EditableImageIcon_XPATH");
			Thread.sleep(1000);

			commonLib.syso("Fill following details in restricted list section: ");
			commonLib.syso("i.Enable  black list checkbox  ");
			commonLib.press_ArrowDown_Given_No_Of_Times(6);
			boolean is_RestrictPartyCheckboxSelected = commonLib
					.is_Checkbox_Selected("Accounts_RestrictedPartyList_Checkbox_XPATH");
			if (is_RestrictPartyCheckboxSelected) {
				commonLib.unSelect_Checkbox("Accounts_RestrictedPartyList_Checkbox_XPATH");
				Thread.sleep(2000);
				commonLib.select_Checkbox("Accounts_RestrictedPartyList_Checkbox_XPATH");
			} else {
				commonLib.select_Checkbox("Accounts_RestrictedPartyList_Checkbox_XPATH");
			}

			Thread.sleep(2000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("Accounts_RestrictedPartyListReason_Menu_XPATH",
					restrictedPartyListReason);
			String[] restrictedPartyListReasonArr = restrictedPartyListReason.split(",");
			// try for multiple values ..Later
			for (String EachVal : restrictedPartyListReasonArr) {
				commonLib.syso("ii.Select Restricted Party List Reason as '" + EachVal
						+ "' and move from Available to Chosen section");
				commonLib.clickWithDynamicValue("Accounts_RestrictedPartyListReason_Menu_XPATH", EachVal);
				Thread.sleep(1000);
				commonLib.click("AccountsPage_MoveselectiontoChosen_Icon_In_RestrictedPartyListReason_Menu_In_XPATH");
				commonLib.WaitforPresenceofElement_Dynamic_Xpath(
						"AccountsPage_Chosen_Menu_In_RestrictedPartyListReason_Section_XPATH", EachVal);
			}
			Thread.sleep(1000);

			commonLib.syso(" iii.Fill comments in Additional Information i.e " + additionalInformation);
			commonLib.clear("AccountsPage_AdditionalInformation_Editbox_RestrictedPartyList_Section_XPATH");
			commonLib.sendKeys("AccountsPage_AdditionalInformation_Editbox_RestrictedPartyList_Section_XPATH",
					additionalInformation);

			commonLib.syso("Click 'Save' button");
			commonLib.clickwithoutWait("Save_Button_In_New_Account_Page_XPATH");

			// Account should be saved with black list checkbox selection, reason, comments

			boolean is_restrictedPartyListReason_Status = commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"AccountsPage_RestrictedPartyListReason_Editbox_XPATH", restrictedPartyListReason);

			if (is_restrictedPartyListReason_Status) {
				commonLib.log(LogStatus.PASS, "restrictedPartyListReason=" + restrictedPartyListReason + " is found");
				commonLib.getScreenshot();
			} else {
				commonLib.log(LogStatus.FAIL,
						"restrictedPartyListReason='" + restrictedPartyListReason + "' is not found");
			}

			Thread.sleep(2000);
			boolean is_additionalInformation_Status = commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"AccountsPage__AdditionalInformation_Editbox_XPATH", additionalInformation);
			if (is_additionalInformation_Status) {
				commonLib.syso("additionalInformation='" + additionalInformation + "' is found");

			} else {
				commonLib.log(LogStatus.FAIL, "additionalInformation='" + additionalInformation + "' is not found");
			}

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Not able to blacklist the given Account='" + accountName + "'");
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * whiteListing_ICAccount Description : It whitelists the given account with
	 * given reason and comments Arguments : Return value : Example :
	 * whiteListing_ICAccount( "Whitelist Reason", "Test additional info");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void whiteListing_ICAccount(String restrictedPartyListReason, String additionalInformation)
			throws InterruptedException, AWTException {
		try {
			commonLib.performHover("AccountsPage_RestrictedPartyListDetails_Section_XPATH");
			commonLib.performHover("Accounts_RestrictedPartyList_EditableImageIcon_XPATH");

			commonLib.syso("Click 'Edit Restricted Party List' button  ");
			commonLib.click("Accounts_RestrictedPartyList_EditableImageIcon_XPATH");
			Thread.sleep(2000);

			commonLib.syso("Fill following details in restricted list section: ");
			commonLib.syso("i.unselect Restricted Party List checkbox  ");
			commonLib.press_ArrowDown_Given_No_Of_Times(6);

			boolean is_RestrictPartyCheckboxSelected = commonLib
					.is_Checkbox_Selected("Accounts_RestrictedPartyList_Checkbox_XPATH");
			if (is_RestrictPartyCheckboxSelected) {
				commonLib.unSelect_Checkbox_Using_JavaScriptExecutor("Accounts_RestrictedPartyList_Checkbox_XPATH");
				commonLib.select_Checkbox_Using_JavaScriptExecutor("Accounts_RestrictedPartyList_Checkbox_XPATH");
				Thread.sleep(2000);
				commonLib.unSelect_Checkbox("Accounts_RestrictedPartyList_Checkbox_XPATH");
				Thread.sleep(2000);
			} else {
				commonLib.select_Checkbox("Accounts_RestrictedPartyList_Checkbox_XPATH");
				Thread.sleep(2000);
				commonLib.unSelect_Checkbox("Accounts_RestrictedPartyList_Checkbox_XPATH");
			}

			// commonLib.press_Space_Using_ActionsClass();

			Thread.sleep(2000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("Accounts_RestrictedPartyListReason_Menu_XPATH",
					restrictedPartyListReason);
			commonLib.syso("ii.Select Restricted Party List Reason as '" + restrictedPartyListReason
					+ "' and move from Available to Chosen section");
			commonLib.clickWithDynamicValue("Accounts_RestrictedPartyListReason_Menu_XPATH", restrictedPartyListReason);

			commonLib
					.performHover("AccountsPage_MoveselectiontoChosen_Icon_In_RestrictedPartyListReason_Menu_In_XPATH");
			commonLib.clickbyjavascript(
					"AccountsPage_MoveselectiontoChosen_Icon_In_RestrictedPartyListReason_Menu_In_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"AccountsPage_Chosen_Menu_In_RestrictedPartyListReason_Section_XPATH", restrictedPartyListReason);

			commonLib.syso(" iii.Fill comments in Additional Information i.e " + additionalInformation);
			commonLib.clear("AccountsPage_AdditionalInformation_Editbox_RestrictedPartyList_Section_XPATH");
			commonLib.sendKeys("AccountsPage_AdditionalInformation_Editbox_RestrictedPartyList_Section_XPATH",
					additionalInformation);

			commonLib.syso("Click 'Save' button");
			commonLib.clickwithoutWait("Save_Button_In_New_Account_Page_XPATH");

			// Account should be saved with black list checkbox selection, reason, comments

			boolean is_restrictedPartyListReason_Status = commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"AccountsPage_RestrictedPartyListReason_Editbox_XPATH", restrictedPartyListReason);

			if (is_restrictedPartyListReason_Status) {
				commonLib.log(LogStatus.PASS, "restrictedPartyListReason=" + restrictedPartyListReason + " is found");
			} else {
				commonLib.log(LogStatus.FAIL,
						"restrictedPartyListReason='" + restrictedPartyListReason + "' is not found");
			}
			Thread.sleep(2000);
			boolean is_additionalInformation_Status = commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"AccountsPage__AdditionalInformation_Editbox_XPATH", additionalInformation);
			if (is_additionalInformation_Status) {
				commonLib.syso("additionalInformation='" + additionalInformation + "' is found");
			} else {
				commonLib.log(LogStatus.FAIL, "additionalInformation='" + additionalInformation + "' is not found");
			}

			commonLib.getScreenshot();

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Not able to whitelist the  Account");
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * getElememt_Visibility_Status Description : This Method is created to handle
	 * the Element not found exception it will return false and will gracefully
	 * handle the exception Arguments : NA Return value : NA Example
	 * :getElememt_Visibility_Status(String xpathofElement)
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public boolean getElememt_Visibility_Status(String xpathOfElement) {

		try {
			if (driver.findElements(By.xpath(xpathOfElement)).size() > 0)
				return true;

		} catch (Exception e) {
			commonLib.syso("Element" + xpathOfElement + " is not visible on the screen");
			return false;
		}
		return false;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * getElememt_Visibility_Status Description : This Method is created to handle
	 * the Element not found exception it will return false and will gracefully
	 * handle the exception Arguments : NA Return value : NA Example
	 * :getElememt_Visibility_Status(Element name)
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public boolean getElememt_Visibility_Status(WebElement element) {

		try {
			if (element.isDisplayed())
				return true;

		} catch (Exception e) {
			commonLib.syso("Element is not visible on the screen, Please recheck");
			return false;
		}
		return true;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * wait_MethodFor_Loading_QuickLinkScreens Description : This method is generic
	 * to validate the status of screen which gets appeared when you click any link
	 * from Related quick links from IC Arguments : text_Of_RelatedQuickLink_Opened
	 * Return value : NA Example :
	 * sfdcLib.wait_MethodFor_Loading_QuickLinkScreens("Contracts") Same way we can
	 * use it for any tab.
	 * =============================================================================
	 * ===============================================
	 */

	public boolean wait_MethodFor_Loading_QuickLinkScreens(String text_Of_RelatedQuickLink_Opened) {
		try {
			if (getElememt_Visibility_Status(commonLib.findElementWithDynamicXPath(
					"RelatedQuickLinksPage_StaticTextForWait_XPATH", text_Of_RelatedQuickLink_Opened))) {
				commonLib.log(LogStatus.PASS,
						"Clicked Quick Link Named: " + text_Of_RelatedQuickLink_Opened + " is opened successfully");
				return true;
			}
		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Click Quick Link " + text_Of_RelatedQuickLink_Opened
					+ " is not opened successfully from the Account Page");
			return false;
		}
		return false;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * getElememt_Visibility_Status Description : This Method is created to handle
	 * the Element not found exception it will return false and will gracefully
	 * handle the exception Arguments : NA Return value : NA Example
	 * :getElememt_Visibility_Status(Element name)
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void create_Contract_From_NewContract_Screen(String AccountName_ToBeVerified_On_ContractPage,
			String ServiceLevel, String Status) throws InterruptedException {
		try {
			commonLib.waitforElement("Contracts_NewContractButton_XPATH");
			commonLib.clickbyjavascript("Contracts_NewContractButton_XPATH");
			Thread.sleep(5 * 1000);
			commonLib.waitForPresenceOfElementLocated("NewContractPage_ContractStartDateInput_XPATH");
			String AccountName_onContractPage = commonLib.getText("NewContract_AccountNumber_XPATH");
			System.out.println(AccountName_onContractPage);
			if (AccountName_onContractPage.equalsIgnoreCase(AccountName_ToBeVerified_On_ContractPage)) {
				commonLib.log(LogStatus.PASS, "Account Name i.e." + AccountName_ToBeVerified_On_ContractPage
						+ " is coming prepopulated correctly on the Contract creation screen");
				commonLib.getScreenshot();
			} else {
				commonLib.log(LogStatus.FAIL, "Account Name is coming Incorrect on Contract creation form Expected->"
						+ AccountName_onContractPage + ". Actual value-> " + AccountName_ToBeVerified_On_ContractPage);
				commonLib.getScreenshot();
			}
			commonLib.clickwithoutWait("NewContractPage_ContractStartDateInput_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("NewContractPage_ContractStartDate_DateSelectionBox_XPATH",
					commonLib.getcurrentDate_AnyFormat("yyyy-MM-dd"));
			commonLib.clickWithDynamicValue("NewContractPage_ContractStartDate_DateSelectionBox_XPATH",
					commonLib.getcurrentDate_AnyFormat("yyyy-MM-dd"));
			Thread.sleep(1000);
			commonLib.clickWithDynamicValue("NewContractPage_ServiceLevel_XPATH", ServiceLevel);
			Thread.sleep(500);
			commonLib.clickWithDynamicValue("NewContract_ServiceLevel_MovetoChoosen_XPATH", ServiceLevel);
			commonLib.clickWithDynamicValue("NewContract_Status_DropDown_SelectValue_XPATH", Status);
			commonLib.clickwithoutWait("Save_Button_In_New_Account_Page_XPATH");

		} catch (Exception e) {
			commonLib.syso("Error:Not able to Create Contract due to Exception as ");
			e.printStackTrace();
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * clickTabFromHomePage Description : This method is created to click on Any tab
	 * from Homepage. You have to pass the tab name which you want to click and it
	 * will generate xpath on that basis of that. Arguments : NA Return value : NA
	 * Example :clickTabFromHomePage(Account)
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public void clickTabFromHomePage(String TabName) throws InterruptedException {
		commonLib.WaitforPresenceofElement_Dynamic_Xpath("Homepage_TabName_XPATH", TabName);
		// commonLib.clickWithDynamicValue("Homepage_TabName_XPATH", TabName);
		commonLib.clickbyjavascriptWithDynamicValue("Homepage_TabName_XPATH", TabName);
		Thread.sleep(5 * 1000);
	}
	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Navigate_To_Existing_Contract Description : This method is basically to
	 * navigate to existing contract in the system. Arguments : NA Return value : NA
	 * Example :Navigate_To_Existing_Contract(00636568)
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public void Navigate_To_Existing_Contract(String contract_Number) throws InterruptedException, AWTException {
		commonLib.syso("Enter Contract Number in Global search=" + contract_Number);
		commonLib.sendKeys("GlobalSearch_Homepage_XPATH", contract_Number);
		Thread.sleep(3000);
		commonLib.KeyPress_Enter();

		boolean contract_table_Result_Exists = commonLib
				.waitForVisibilityOf_DynamicXpath("SearchContract_ResultTable_XPATH", contract_Number);

		if (contract_table_Result_Exists) {

			commonLib.syso("Contract results table is opened");
		} else {
			commonLib.syso("Contract results table is not opened");

		}

		commonLib.clickbyjavascriptWithDynamicValue("SearchContract_ResultTable_XPATH", contract_Number);

		boolean ContractStatus_Exist = commonLib.waitforElement("ContrtactDetail_ContractStatus_XPATH");

		if (ContractStatus_Exist) {
			commonLib.log(LogStatus.PASS, "Contract ='" + contract_Number + "' page is open");
		} else {
			commonLib.log(LogStatus.FAIL, "Contract ='" + contract_Number + "' page is not open");
		}

	}
	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * click_Setup_FromHomePage_AfterLogin Description : This method is designed to
	 * click Service Setup button from Homepage once the user 'Sign in' into the
	 * application . Arguments : NA Return value : NA Example
	 * :click_Setup_FromHomePage_AfterLogin()
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public String click_Setup_FromHomePage_AfterLogin() {
		String currentHandle = "";
		try {
			commonLib.clickwithoutWait("User_settings_Button_XPATH");
			commonLib.waitForElementTimeOut("Service_Setup_XPATH", 20);
			commonLib.clickwithoutWait("Service_Setup_XPATH"); // Clicking on Settings button.
			currentHandle = commonLib.getWindowHandle(); // Saving the Current Handle to switch to Parent Window.
			commonLib.switchWindowWithURL("Setup"); // New method to switch window on the basis of Url.
			commonLib.waitForDynamicTitle("Salesforce", 120);
			commonLib.waitForPresenceOfElementLocated("GlobalSearch_SetupPage_XPATH");
		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, e.getMessage());

		}
		return currentHandle;

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * click_Setup_FromHomePage_AfterLogin Description : This method is designed to
	 * click Service Setup button from Homepage once the user 'Sign in' into the
	 * application . Arguments : NA Return value : NA Example
	 * :click_Setup_FromHomePage_AfterLogin()
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public String click_SetupHome_FromHomePage_AfterLogin() {
		String currentHandle = "";
		try {
			commonLib.clickwithoutWait("User_settings_Button_XPATH");
			commonLib.waitForElementTimeOut("Service_Setup_XPATH", 20);
			commonLib.clickwithoutWait("HomePage_Setup_XPATH"); // Clicking on Settings button.
			currentHandle = commonLib.getWindowHandle(); // Saving the Current Handle to switch to Parent Window.
			commonLib.switchWindowWithURL("SetupOneHome"); // New method to switch window on the basis of Url.
			commonLib.waitForDynamicTitle("Salesforce", 120);
			commonLib.waitForPresenceOfElementLocated("GlobalSearch_Homepage_XPATH");

		} catch (Exception e) {
			commonLib.syso("Not able to Click on Setup button from Homepage");

		}
		return currentHandle;

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * get_ContractNumber_From_ContractListingPage Description : This method is to
	 * get Contract Number from contract listing page. Arguments : NA Return value :
	 * String Example :get_ContractNumber_From_ContractListingPage()
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public String get_ContractNumber_From_ContractListingPage() {
		String contract_Number = "";
		try {
			contract_Number = commonLib.getText("ContractListing_LastContractLink_XPATH");

		} catch (Exception e) {
			commonLib.syso("Error:Contract Listing is not populated correctly");
			e.printStackTrace();
			// TODO: handle exception
		}
		return contract_Number;
	}
	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * createDueDiligenceWithICFinance Description : This method is to create Due
	 * diligence through Finance profile. It returns the newly created Due diligence
	 * number. Arguments : NA Return value : String Example
	 * :createDueDiligenceWithICFinance("Approve", "Approve")
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public String createDueDiligenceWithICFinance(String ICMFinanceRecommendation_Status, String CreditCheckStatus,
			String FinaneRecomendationText) {
		String DueDiligenceRecommendationNumber = "";
		try {
			Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Due Diligence Recommendations");
			commonLib.click("New_Button_In_DueDiligenceRecommendations_Page_XPATH");
			Thread.sleep(5 * 1000);
			commonLib.waitForVisibilityOf("ICMFinanceRecommendation_Dropdown_In_NewDueDiligenceRecommendation_XPATH");
			commonLib.click("ICMFinanceRecommendation_Dropdown_In_NewDueDiligenceRecommendation_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"ICMFinanceRecommendation_Values_Dropdown_In_NewDueDiligenceRecommendation_XPATH",
					ICMFinanceRecommendation_Status);
			commonLib.clickbyjavascriptWithDynamicValue(
					"ICMFinanceRecommendation_Values_Dropdown_In_NewDueDiligenceRecommendation_XPATH",
					ICMFinanceRecommendation_Status);
			commonLib.click("CreditCheckDone_Checkbox_In_NewDueDiligenceRecommendation_Page_XPATH");
			commonLib.click("CreditCheckStatus_Dropdown_In_NewDueDiligenceRecommendation_Page_XPATH");
			if (CreditCheckStatus.equalsIgnoreCase("Approve")) {
				Thread.sleep(1000);
				commonLib.WaitforPresenceofElement_Dynamic_Xpath(
						"CreditCheckStatus_Values_Dropdown_In_NewDueDiligenceRecommendation_Page_XPATH",
						CreditCheckStatus);
				commonLib.clickbyjavascriptWithDynamicValue(
						"CreditCheckStatus_Values_Dropdown_In_NewDueDiligenceRecommendation_Page_XPATH",
						CreditCheckStatus);
				Thread.sleep(2000);

			} else {
				Thread.sleep(1000);
				commonLib.WaitforPresenceofElement_Dynamic_Xpath(
						"CreditCheckStatus_Values_Dropdown_Reject_In_NewDueDiligenceRecommendation_Page_XPATH",
						CreditCheckStatus);
				commonLib.clickbyjavascriptWithDynamicValue(
						"CreditCheckStatus_Values_Dropdown_Reject_In_NewDueDiligenceRecommendation_Page_XPATH",
						CreditCheckStatus);
				Thread.sleep(2000);

			}
			commonLib.sendKeys("Create_Due_Diligence_IC_Finance_Rationale_For_Recommendation_TextArea_XPATH",
					FinaneRecomendationText);
			Thread.sleep(1000);
			commonLib.click("Save_Button_In_NewDueDiligenceRecommendation_Page_XPATH");
			commonLib.waitForVisibilityOf(
					"DueDiligenceRecommendationNumber_From_Table_In_DueDiligenceRecommendation_Page_XPATH_");

			DueDiligenceRecommendationNumber = commonLib
					.getText("DueDiligenceRecommendationNumber_From_Table_In_DueDiligenceRecommendation_Page_XPATH_");

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Due diligence is not completed with Finance module.");
			e.printStackTrace();
		}
		return DueDiligenceRecommendationNumber;

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * createDueDiligenceWithICLocal Description : This method is to create Due
	 * diligence through IC Local profile. It returns the newly created Due
	 * diligence number. Arguments : NA Return value : String Example
	 * :createDueDiligenceWithICLocal("Approve",
	 * "Dummy text for IC Local Rationale")
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void createDueDiligenceWithICLocal(String ICMLocalRecommendation_Status, String textForICLocalRationale) {
		try {

			commonLib.clickwithoutWait("DueDiligenceRecommendation_EditWithICLocal_XPATH");
			Thread.sleep(3 * 1000);
			commonLib.waitForVisibilityOf("DueDiligenceRecommendation_WithICLocalRecommendation_EditDropDown_XPATH");
			commonLib.clickbyjavascript("DueDiligenceRecommendation_WithICLocalRecommendation_EditDropDown_XPATH");
			commonLib.waitForPresenceOfElementLocated(
					"DueDiligenceRecommendation_WithICLocalRecommendation_ApproveFromDropdown_XPATH");
			commonLib.clickWithDynamicValue(
					"DueDiligenceRecommendation_WithICLocalRecommendation_ValueFromDropdown_XPATH",
					ICMLocalRecommendation_Status);
			Thread.sleep(1000);
			commonLib.sendKeys("DueDiligenceRecommendation_WithICLocalRecommendation_LocalRationale_XPATH",
					textForICLocalRationale);
			commonLib.click("DueDiligenceRecommendation_WithICLocalRecommendation_SaveAfterEdit_XPATH");
			commonLib.waitForPresenceOfElementLocated("DueDiligenceRecommendation_EditWithICLocal_XPATH");
			commonLib.syso("Updated Due diligence from IC local with status as:" + ICMLocalRecommendation_Status);

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Due diligence is not completed with IC Local.");
			e.printStackTrace();

		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * createDueDiligenceWithICCompliance Description : This method is to create Due
	 * diligence through IC Compliance profile. It returns the newly created Due
	 * diligence number. Arguments : NA Return value : String Example
	 * :createDueDiligenceWithICLocal("Approve",
	 * "Dummy text for IC Local Rationale")
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public void createDueDiligenceWithICCompliance(String ICMComplianceRemediationStatus,
			String textForICComplianceRationale) {
		try {
			commonLib.clickwithoutWait("DueDiligenceRecommendation_EditWithICCompliance_XPATH");
			Thread.sleep(3 * 1000);
			commonLib.waitForVisibilityOf(
					"DueDiligenceRecommendation_WithICComplianceRecommendation_EditDropDown_XPATH");
			commonLib.click("DueDiligenceRecommendation_WithICComplianceRecommendation_EditDropDown_XPATH");
			Thread.sleep(2000);
			commonLib.clickWithDynamicValue(
					"DueDiligenceRecommendation_WithICComplianceRecommendation_ValueFromDropdown_XPATH",
					ICMComplianceRemediationStatus);
			Thread.sleep(1000);
			commonLib.sendKeys("DueDiligenceRecommendation_WithICComplianceRecommendation_LocalRationale_XPATH",
					textForICComplianceRationale);
			commonLib.click("DueDiligenceRecommendation_WithICLocalRecommendation_SaveAfterEdit_XPATH");
			commonLib.waitForPresenceOfElementLocated("DueDiligenceRecommendation_EditWithICCompliance_XPATH");
			commonLib.syso("Updated Due diligence from IC Compliance with status as:" + ICMComplianceRemediationStatus);

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Due diligence is not completed with IC Local.");
			e.printStackTrace();

		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * createDueDiligenceWithICComplianceFirst Description : This method is to
	 * create Due diligence through IC Compliance profile first name not on the
	 * existing Due diligence. Arguments : NA Return value : String Example
	 * :createDueDiligenceWithICLocal("Approve",
	 * "Dummy text for IC Local Rationale")
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public String createDueDiligenceWithICCompliance_First(String ICMComplianceRemediationStatus,
			String textForICComplianceRationale) {
		String DueDiligenceRecommendationNumber = "";
		try {
			/*
			 * List<WebElement> ls=commonLib.findElements(
			 * "New_Button_In_DueDiligenceRecommendations_Page_XPATH"); for (WebElement
			 * webElement : ls) { if(webElement.isDisplayed()==true) webElement.click();
			 * 
			 * }
			 */
			commonLib.waitForPresenceOfElementLocated("New_Button_In_DueDiligenceRecommendations_Page_XPATH");
			commonLib.click("New_Button_In_DueDiligenceRecommendations_Page_XPATH");
			Thread.sleep(5 * 1000);
			commonLib.waitForVisibilityOf("ICMComplianceRecommendation_DropDown_Button_XPATH");
			commonLib.click("ICMComplianceRecommendation_DropDown_Button_XPATH");
			Thread.sleep(2000);
			commonLib.clickbyjavascriptWithDynamicValue(
					"ICMComplianceRecommendation_Values_Dropdown_In_NewDueDiligenceRecommendation_XPATH",
					ICMComplianceRemediationStatus);
			Thread.sleep(1000);
			commonLib.sendKeys("DueDiligenceICCompliance_TextArea_XPATH", textForICComplianceRationale);
			Thread.sleep(500);
			commonLib.click("Save_Button_In_NewDueDiligenceRecommendation_Page_XPATH");
			commonLib.waitForVisibilityOf(
					"DueDiligenceRecommendationNumber_From_Table_In_DueDiligenceRecommendation_Page_XPATH_");
			DueDiligenceRecommendationNumber = commonLib
					.getText("DueDiligenceRecommendationNumber_From_Table_In_DueDiligenceRecommendation_Page_XPATH_");
			commonLib.syso("Updated Due diligence from IC Compliance with status as:" + ICMComplianceRemediationStatus);

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Due diligence is not completed with IC Local.");
			e.printStackTrace();

		}
		return DueDiligenceRecommendationNumber;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * createDueDiligenceWithICFinance Description : This method is to create Due
	 * diligence through Finance profile. It returns the newly created Due diligence
	 * number. Arguments : NA Return value : String Example
	 * :createDueDiligenceWithICFinance("Approve", "Approve")
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void createDueDiligenceWithICFinance_JRHD(String ICMFinanceRecommendation_Status, String CreditCheckStatus,
			String FinaneRecomendationText) {
		try {

			commonLib.waitForPresenceOfElementLocated("Edit_ICM_Finance_Recommendation_XPATH");
			commonLib.click("Edit_ICM_Finance_Recommendation_XPATH");
			commonLib.waitForVisibilityOf("ICMFinanceRecommendation_DropDown_Button_XPATH");
			commonLib.click("ICMFinanceRecommendation_DropDown_Button_XPATH");
			Thread.sleep(2000);
			commonLib.clickbyjavascriptWithDynamicValue(
					"ICMFinanceRecommendation2_Values_Dropdown_In_NewDueDiligenceRecommendation_XPATH",
					ICMFinanceRecommendation_Status);
			Thread.sleep(1000);
			commonLib.sendKeys("ICMFinanceRecommendation2_TextInputArea_XPATH", FinaneRecomendationText);
			Thread.sleep(500);
			commonLib.clickbyjavascript("CreditCheckDone_Checkbox_In_NewDueDiligenceRecommendation_Page_XPATH");
			commonLib.scrollDownToElement("ICM_Finance_CreditCheck_DropDown_JRHD_XPATH", "Button");
			commonLib.click("ICM_Finance_CreditCheck_DropDown_JRHD_XPATH");
			Thread.sleep(500);
			commonLib.clickWithDynamicValue("ICM_Finance_CreditCheck_DropDown_Value_JRHD_XPATH", CreditCheckStatus);
			Thread.sleep(1000);
			commonLib.click("Save_Button_In_NewDueDiligenceRecommendation_Page_XPATH");
			commonLib.waitForVisibilityOf("Edit_ICM_Finance_Recommendation_XPATH");
			commonLib.syso("Updated Due diligence from IC Finance with status as:" + ICMFinanceRecommendation_Status);

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Due diligence is not completed with IC Finance.");
			e.printStackTrace();

		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * createDueDiligenceWithICMLocal_JRHD Description : This method is to create
	 * Due diligence through ICMLocal profile. Arguments : NA Return value : NA
	 * Example :createDueDiligenceWithICFinance("Approve", "Approve")
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void createDueDiligenceWithICMLocal_JRHD(String ICMLocalRecommendationStatus,
			String ICMLocalRecomendationText) {
		try {

			commonLib.waitForPresenceOfElementLocated("Edit_ICM_Finance_Recommendation_XPATH");
			commonLib.click("Edit_ICM_Finance_Recommendation_XPATH");
			commonLib.waitForVisibilityOf("ICMLocalRecommendation_DropDown_Button_XPATH");
			commonLib.click("ICMLocalRecommendation_DropDown_Button_XPATH");
			Thread.sleep(2000);
			commonLib.clickbyjavascriptWithDynamicValue(
					"ICMLocalRecommendation_Values_Dropdown_In_NewDueDiligenceRecommendation_XPATH",
					ICMLocalRecommendationStatus);
			Thread.sleep(1000);
			commonLib.sendKeys("ICMLocalRecommendation_TextInputArea_XPATH", ICMLocalRecomendationText);
			Thread.sleep(500);
			commonLib.click("Save_Button_In_NewDueDiligenceRecommendation_Page_XPATH");
			commonLib.waitForVisibilityOf("Edit_ICM_Finance_Recommendation_XPATH");
			commonLib.syso("Updated Due diligence from IC Finance with status as:" + ICMLocalRecommendationStatus);

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Due diligence is not completed with IC Local.");
			e.printStackTrace();

		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Create_New_Support_Case_From_RelatedQuickLinks Description : This method is
	 * to create support from Related quick links only. Arguments : NA Return value
	 * : String Example :
	 * Create_New_Support_Case_From_RelatedQuickLinks(Record_Type, priority,
	 * issueCategory, issueSubCategory, subject, description, action, dueDate,
	 * closeNotes);
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public String Create_New_Support_Case_From_RelatedQuickLinks(String Select_Record_Type_value, String priority,
			String issueCategory, String issueSubCategory, String subject, String description, String action,
			String dueDate, String closeNotes) throws IOException, InterruptedException {
		String supportCaseNumber = null;
		try {

			// commonLib.log(LogStatus.PASS, "Click 'New' button");
			commonLib.syso("Click 'New' button");

			commonLib.click("New_Button_In_Support_Case_Page_XPATH");

			commonLib.syso("Select the 'Select a Record Type'as IC case and click on Next button ");
			commonLib.selectDropdownVisibleText("Selecta_RecordType_Dropdown_In_NewCase_Page_XPATH",
					Select_Record_Type_value);

			commonLib.click("Next_Button_In_New_Case_Page_XPATH");

			Boolean is_NewSupportCase_Open = commonLib
					.waitForPresenceOfElementLocated("Priority_Dropdown_In_NewSupportCase_XPATH");
			if (is_NewSupportCase_Open) {
				commonLib.syso("Pop up form with all the required details for Support case creation should be opened.");
			} else {
				commonLib.log(LogStatus.FAIL,
						"Pop up form with all the required details for Support case creation is not opened.");
			}

			// Validate the Account Name should be prepopulated on the Support case creation
			// screen //???? account name field is not exist

			commonLib.syso("Enter below Mandatory fields: ");
			commonLib.syso("Priority :" + priority);
			commonLib.click("Priority_Dropdown_In_NewSupportCase_XPATH");
			;
			commonLib.clickWithDynamicValue("Priority_DropdownMenu_In_NewSupportCase_XPATH", priority);

			commonLib.syso("Issue Category:" + issueCategory);
			commonLib.click("IssuesCategories_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH", issueCategory);

			commonLib.syso("Issue Sub Category:" + issueSubCategory);
			commonLib.click("IssueSubcategory_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH", issueSubCategory);

			commonLib.syso("Subject: " + subject);
			commonLib.sendKeys("Subject_Editbox_In_DescriptionInformation_Section_In_New_Support_Case_Page_XPATH",
					subject);

			commonLib.syso("Description: " + description);
			commonLib.sendKeys("Description_Editbox_In_DescriptionInformation_Section_In_New_Support_Case_Page_XPATH",
					description);

			commonLib.syso("Action:" + action);
			commonLib.click("Action_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickWithDynamicValue("Action_DropdownMenu_In_NewSupportCase_XPATH", action);

			commonLib.syso("Due Date:" + dueDate);
			commonLib.sendKeys("DueDate_Editbox_In_NewSupportCase_XPATH", dueDate);

			commonLib.syso("Close Notes in Remediation:" + closeNotes);
			commonLib.sendKeys("CloseNotes_Editbox_In_NewSupportCase_XPATH", closeNotes);

			commonLib.waitForPresenceOfElementLocated("Save_Button_In_NewSupportCase_Page_XPATH");

			commonLib.syso("Click on Save after filling all the details");
			commonLib.click("Save_Button_In_NewSupportCase_Page_XPATH");

			supportCaseNumber = commonLib.getText("SupportCaseNumber_Text_In_SupportCase_Page_XPATH");
			commonLib.syso("Captured Support Case ID:" + supportCaseNumber);
			Thread.sleep(8 * 1000);
			commonLib.getScreenshot();
			return supportCaseNumber;
		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Failed.Not able to create New Support Case From Support cases page");
			e.printStackTrace();
		}
		return supportCaseNumber;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name : Description :
	 * Arguments : Return value : Example :
	 * add_TeamMembers_In_Acounts_Page("Compliance User","ICM Compliance"
	 * ,"Read/Write");
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public void add_TeamMembers_In_Acounts_Page(String User, String TeamRole, String AccountAccess) {
		try {
			commonLib.log(LogStatus.PASS, "Precondition: Adding User='" + User + "' to Team Role='" + TeamRole + "'");
			Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Account Team");
			commonLib.clickLink("Add Team Members");
			commonLib.waitForPresenceOfElementLocated("AddaccountteammembersPage_UserTable_User_Row_XPATH");

			commonLib.syso("Enter user:" + User);
			commonLib.click("AddaccountteammembersPage_UserTable_User_Row_XPATH");
			Thread.sleep(2000);
			commonLib.waitForPresenceOfElementLocated("AddaccountteammembersPage_UserTable_User_Row1_Editbox_XPATH");
			commonLib.sendKeys("AddaccountteammembersPage_UserTable_User_Row1_Editbox_XPATH", User);
			Thread.sleep(3000);
			commonLib.clickLink(User);
			Thread.sleep(2000);

			commonLib.syso("Enter TeamRole:" + TeamRole);
			commonLib.click("AddaccountteammembersPage_UserTable_TeamRole_Row1_Editbox_XPATH");
			Thread.sleep(2000);
			commonLib.waitForPresenceOfElementLocated(
					"AddaccountteammembersPage_UserTable_TeamRole_Row1_Dropdown_XPATH");

			commonLib.click("AddaccountteammembersPage_UserTable_TeamRole_Row1_Dropdown_XPATH");
			commonLib.send_Text_To_Currently_Focussed_Element_Using_Actions_Class(TeamRole);
			Thread.sleep(1000);
			commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();

			commonLib.syso("Enter AccountAccess:" + AccountAccess);
			commonLib.click("AddaccountteammembersPage_UserTable_AccountAccess_Row1_XPATH");
			Thread.sleep(2000);
			commonLib.waitForPresenceOfElementLocated(
					"AddaccountteammembersPage_UserTable_AccountAccess_Row1_dropdown_XPATH");

			commonLib.press_Space_Using_ActionsClass();
			Thread.sleep(1000);
			commonLib.press_Space_Using_ActionsClass();

			Thread.sleep(2000);
			commonLib.clickLink(AccountAccess);
			Thread.sleep(2000);

			commonLib.clickbyjavascript("AddaccountteammembersPage_UserTable_Save_Button_XPATH");
			Thread.sleep(3000);
			commonLib.waitForVisibilityOf_DynamicXpath("AccountTeamPage_TeamMmeber_Table_XPATH", TeamRole);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * read_All_Test_Data_From_All_Rows_From_Given_ColumnName Description : It reads
	 * all test data from all rows from given column name Arguments : String
	 * fileName , String sheetName , String colName Return value : List object or If
	 * Column name is not in sheet,it logs fail message and returns null or
	 * exception Example : List<String> Record_Type_value_data=sfdcLib.
	 * read_All_Test_Data_From_All_Rows_From_Given_ColumnName(worksheet,
	 * TestData_SheetName,"Record_Type_value");
	 * commonLib.syso("Record_Type_value_data="+Record_Type_value_data);
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public List<String> read_All_Test_Data_From_All_Rows_From_Given_ColumnName(String fileName, String sheetName,
			String colName) {
		String p = System.getProperty("user.dir") + "/testdata/" + fileName;
		List<String> userData = new ArrayList<String>();
		try {
			workbook = new XSSFWorkbook(p);
			Sheet = workbook.getSheet(sheetName);
			Row = Sheet.getRow(0);
			int col_Num = -1;

			// read columns names
			String actualColumnName = "";
			int Colcnt = Row.getLastCellNum();

			for (int i = 0; i < Colcnt; i++) {
				cell = Row.getCell(i);
				if (cell == null) {
					// System.out.println("1.Column Name is blank at Column no=" +(i+1)+ ".Iterate
					// next value="+(i+1));
					continue;
				} else {
					actualColumnName = cell.getStringCellValue().trim();
				}

				if (actualColumnName.equals(colName.trim())) {
					col_Num = i;
					// System.out.println("Expected Column name='" +colName + "' ,Actual
					// ColumnName="+actualColumnName + " is found at Colum No="+(col_Num+1));
					break;
				}
			}

			if (col_Num == -1) {
				commonLib.syso("Failed.Given Column name='" + colName
						+ "' is  not found.Please check Column name in excel file='" + p + "', sheet name='" + sheetName
						+ "'");
				// commonLib.log(LogStatus.FAIL, "Failed.Given Column name='"+ colName+ "' is
				// not found.Please check Column name in excel file='"+p+"', sheet
				// name='"+sheetName+"'");
				return null;
			}

			int toRow = Sheet.getLastRowNum();

			for (int i = 1; i <= toRow; i++) {
				Row = Sheet.getRow(i);
				if (Row == null) {
					continue;
				}

				for (int k = col_Num; k <= col_Num; k++) {
					cell = Row.getCell(k);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					String CellValue = cell.toString();
					// System.out.println("Row="+(i+1) + ",Col no= "+(k+1) +"
					// ,CellValue="+CellValue);
					userData.add(cell.toString());
				}
			}
			workbook.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return userData;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * readAllTestCaseData Description : It reads all test data from all rows for a
	 * particular Testcase Arguments : String fileName , String sheetName , String
	 * testCaseId, int columnTestcaseId Return value : Map object or If Column name
	 * is not in sheet,it logs fail message and returns null or exception Example :
	 * Map<Integer, List<String>> Record_Type_value_data=readAllTestData(String
	 * fileName , String sheetName , String testCaseId, int columnTestcaseId);
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public Map<Integer, List<String>> readAllTestCaseData(String fileName, String sheetName, String testCaseId,
			int columnTestcaseId) {
		String p = System.getProperty("user.dir") + "/testdata/" + fileName;
		Map<Integer, List<String>> userData = new HashMap<Integer, List<String>>();
		try {
			int counter = 0;
			workbook = new XSSFWorkbook(p);
			Sheet = workbook.getSheet(sheetName);
			int rowno = Sheet.getLastRowNum();
			for (int i = 1; i <= rowno; i++) {
				Row = Sheet.getRow(i);
				int lastCell = Row.getLastCellNum();
				String tcId = Row.getCell(columnTestcaseId).getStringCellValue();
				List<String> rowData = new ArrayList<>();
				if (tcId.trim().equals(testCaseId.trim())) {

					for (int j = 0; j < lastCell; j++) {
						if (Row.getCell(j) != null) {
							switch (Row.getCell(j).getCellType()) {

							case Cell.CELL_TYPE_STRING:
								rowData.add(Row.getCell(j).getStringCellValue());
								break;
							case Cell.CELL_TYPE_BLANK:
								rowData.add(Row.getCell(j).getStringCellValue());
								break;
							case Cell.CELL_TYPE_NUMERIC:
								long d = (long) Row.getCell(j).getNumericCellValue();
								String data = String.valueOf(d);
								rowData.add(data);
								break;
							default:
								break;
							}
						} else {
							rowData.add("");
						}
					}

					userData.put(++counter, rowData);
				}
			}

			workbook.close();

		} catch (Exception e) {

			e.printStackTrace();
			commonLib.log(LogStatus.FAIL, e.getMessage());
		}
		if (userData.size() > 0) {

			return userData;

		} else {
			commonLib.log(LogStatus.FAIL, "No Data found for given test case " + testCaseId);
			return userData;
		}

	}

	public Map<Integer, List<String>> readAllTestCaseDataWithRowNum(String fileName, String sheetName,
			String testCaseId, int columnTestcaseId) {
		String p = System.getProperty("user.dir") + "/testdata/" + fileName;
		Map<Integer, List<String>> userData = new HashMap<Integer, List<String>>();
		try {
			workbook = new XSSFWorkbook(p);
			Sheet = workbook.getSheet(sheetName);
			int rowno = Sheet.getLastRowNum();

			for (int i = 1; i <= rowno; i++) {
				Row = Sheet.getRow(i);
				int lastCell = Row.getLastCellNum();
				String tcId = Row.getCell(columnTestcaseId).getStringCellValue();
				List<String> rowData = new ArrayList<>();
				if (tcId.trim().equals(testCaseId.trim())) {

					for (int j = 0; j < lastCell; j++) {
						if (Row.getCell(j) != null) {
							switch (Row.getCell(j).getCellType()) {

							case Cell.CELL_TYPE_STRING:
								rowData.add(Row.getCell(j).getStringCellValue());
								break;
							case Cell.CELL_TYPE_BLANK:
								rowData.add(Row.getCell(j).getStringCellValue());
								break;
							case Cell.CELL_TYPE_NUMERIC:
								long d = (long) Row.getCell(j).getNumericCellValue();
								String data = String.valueOf(d);
								rowData.add(data);
								break;
							default:
								break;
							}
						} else {
							rowData.add("");
						}
					}

					userData.put(i, rowData);
				}
			}

			workbook.close();

		} catch (Exception e) {

			e.printStackTrace();
			commonLib.log(LogStatus.FAIL, e.getMessage());
		}
		if (userData.size() > 0) {

			return userData;

		} else {
			commonLib.log(LogStatus.FAIL, "No Data found for given test case " + testCaseId);
			return userData;
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name : getHeaderRow
	 * Description : It gets the header row of the sheet Arguments : String fileName
	 * , String sheetName Return value : List<String>
	 * =============================================================================
	 * ===============================================
	 */

	public List<String> getHeaderRow(String fileName, String sheetName) throws Exception {
		String p = System.getProperty("user.dir") + "/testdata/" + fileName;
		List<String> rowData = new ArrayList<>();

		workbook = new XSSFWorkbook(p);
		Sheet = workbook.getSheet(sheetName);
		int cellCount = Sheet.getRow(0).getLastCellNum();

		for (int i = 0; i < cellCount; i++) {
			rowData.add(Sheet.getRow(0).getCell(i).getStringCellValue());
		}
		workbook.close();
		return rowData;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name : getHeaderRow
	 * Description : It gets the column index from the list Arguments :List<String>
	 * lstData, String columnName Return value : List<String>
	 * =============================================================================
	 * ===============================================
	 */

	public int getColumnNumberFromList(List<String> lstData, String columnName) throws Exception {

		int count = 0;
		boolean found = false;
		for (int i = 0; i < lstData.size(); i++) {
			if (lstData.get(i).equals(columnName)) {
				found = true;
				count = i;
				return count;
			}
		}
		if (!found) {

			throw new Exception("Error column " + columnName + " not found");

		}

		return count;
	}

	// *******************************************************************************************************
	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * restricting_Contact_In_NewContact_Page Description : It restricts the given
	 * contact with given reason and comments Arguments : restrictedPartyListReason,
	 * additionalInformation ) Return value : Example :
	 * restricting_Contact_In_NewContact_Page("Illegal Activity",
	 * "My Additional information 1");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public Boolean restricting_Contact_In_NewContact_Page(String restrictedPartyListReason,
			String additionalInformation) throws InterruptedException, AWTException {
		Boolean isrestricting_Contact_In_NewAccount_Page = false;
		try {

			// commonLib.performHover("AccountsPage_RestrictedPartyListDetails_Section_XPATH");
			// commonLib.performHover("Accounts_RestrictedPartyList_EditableImageIcon_XPATH");

			commonLib.syso("Fill following details in restricted list section: ");
			commonLib.syso("i.Enable  black list checkbox  ");
			// commonLib.press_ArrowDown_Given_No_Of_Times(6);
			commonLib.select_Checkbox("ContactsPage_RestrictedPartyList_Checkbox_XPATH");
			Thread.sleep(2000);

			// commonLib.WaitforPresenceofElement_Dynamic_Xpath("ContactsPage_RestrictedPartyListReason_Menu_XPATH",
			// restrictedPartyListReason);
			String[] restrictedPartyListReasonArr = restrictedPartyListReason.split(",");
			// try for multiple values ..Later
			for (String EachVal : restrictedPartyListReasonArr) {
				EachVal = EachVal.trim();
				commonLib.syso("ii.Select Restricted Party List Reason as '" + EachVal
						+ "' and move from Available to Chosen section");
				commonLib.clickWithDynamicValue("ContactsPage_RestrictedPartyListReason_Menu_XPATH", EachVal);
				// commonLib.clickbyjavascriptWithDynamicValue("ContactsPage_MoveselectiontoChosen_Icon_In_RestrictedPartyListReason_Menu_In_XPATH",
				// EachVal);
				Thread.sleep(1000);
				commonLib.click("ContactsPage_MoveselectiontoChosen_Icon_In_RestrictedPartyListReason_Menu_In_XPATH");

				commonLib.WaitforPresenceofElement_Dynamic_Xpath(
						"ContactsPage_Chosen_Menu_In_RestrictedPartyListReason_Section_XPATH", EachVal);
			}

			commonLib.syso(" iii.Fill comments in Additional Information i.e " + additionalInformation);
			commonLib.clear("ContactsPage_AdditionalInformation_Editbox_XPATH");
			commonLib.sendKeys("ContactsPage_AdditionalInformation_Editbox_XPATH", additionalInformation);

			commonLib.syso("Click 'Save' button");
			commonLib.clickwithoutWait("ContactsPage_Save_Button_XPATH");
			Thread.sleep(3000);
			// Account should be saved with black list checkbox selection, reason, comments

			boolean is_restrictedPartyListReason_Status = commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"ContactsPage_RestrictedPartyListReason_Editbox_Value_XPATH", restrictedPartyListReason);

			if (is_restrictedPartyListReason_Status) {
				commonLib.log(LogStatus.PASS, "restrictedPartyListReason=" + restrictedPartyListReason + " is found");

				isrestricting_Contact_In_NewAccount_Page = true;
			} else {
				commonLib.log(LogStatus.FAIL,
						"restrictedPartyListReason='" + restrictedPartyListReason + "' is not found");
			}

			boolean is_additionalInformation_Status = commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"ContactsPage_AdditionalInformation_Label_Value_XPATH", additionalInformation);
			if (is_additionalInformation_Status) {
				commonLib.syso("additionalInformation='" + additionalInformation + "' is found");
				isrestricting_Contact_In_NewAccount_Page = true;
			} else {
				commonLib.log(LogStatus.FAIL, "additionalInformation='" + additionalInformation + "' is not found");
				isrestricting_Contact_In_NewAccount_Page = false;
			}
			commonLib.getScreenshot();

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Not able to restrict the contact");
			isrestricting_Contact_In_NewAccount_Page = false;
		}
		return isrestricting_Contact_In_NewAccount_Page;
	}
	// *******************************************************************************************************
	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Navigate_To_Existing_ContactOrPrincipal Description : It navigates to
	 * existing Contact or Principal Arguments : contactName Return value :
	 * true/false Example : Navigate_To_Existing_ContactOrPrincipal(contactName);
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public boolean Navigate_To_Existing_ContactOrPrincipal(String contactName)
			throws InterruptedException, AWTException {
		boolean isNavigate_To_Existing_ContactOrPrincipal = false;
		try {
			commonLib.syso("Enter Contact name in Global search=" + contactName);
			commonLib.sendKeys("GlobalSearch_Homepage_XPATH", contactName);
			Thread.sleep(3000);
			commonLib.click("GlobalSearch_Homepage_XPATH");
			// commonLib.KeyPress_Enter();
			commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
			boolean contacts_Results_Table_Exist = commonLib
					.waitForVisibilityOf_DynamicXpath("HomePage_ContactsSearchResult_Data_Table_XPATH", contactName);
			if (contacts_Results_Table_Exist) {

			} else { // if Search does not happen for the first time
				commonLib.syso("Again enter Contact name in Global search=" + contactName);
				commonLib.clear("GlobalSearch_Homepage_XPATH");
				commonLib.sendKeys("GlobalSearch_Homepage_XPATH", contactName);
				Thread.sleep(3000);
				commonLib.click("GlobalSearch_Homepage_XPATH");
				// commonLib.KeyPress_Enter();
				commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
				contacts_Results_Table_Exist = commonLib.waitForVisibilityOf_DynamicXpath(
						"HomePage_ContactsSearchResult_Data_Table_XPATH", contactName);
			}

			if (contacts_Results_Table_Exist) {
				commonLib.syso("Contacts results table is open");
			} else {
				commonLib.log(LogStatus.FAIL, "Contacts results table is not open");
				return isNavigate_To_Existing_ContactOrPrincipal;
			}

			commonLib.clickbyjavascriptWithDynamicValue("HomePage_ContactsSearchResult_Data_Table_XPATH", contactName);

			boolean contactName_Exist = commonLib
					.waitForVisibilityOf_DynamicXpath("ContactsPage_Contact_Header_Text_XPATH", contactName);

			if (contactName_Exist) {
				commonLib.syso("contactName ='" + contactName + "' page is open");
				isNavigate_To_Existing_ContactOrPrincipal = true;
			} else {
				commonLib.log(LogStatus.FAIL, "contactName ='" + contactName + "' page is not open");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return isNavigate_To_Existing_ContactOrPrincipal;
		}

		return isNavigate_To_Existing_ContactOrPrincipal;

	}

	// *******************************************************************************************************
	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * whiteListing_Contact_In_ContactsPage Description : It whitelist the given
	 * contact with given reason and comments Arguments : restrictedPartyListReason,
	 * additionalInformation Return value : Example :
	 * whiteListing_Contact_In_ContactsPage("Whitelist Reason",
	 * "Test-WhiteLsiting the Contact");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public Boolean whiteListing_Contact_In_ContactsPage(String restrictedPartyListReason, String additionalInformation)
			throws InterruptedException, AWTException {
		Boolean iswhiteListing_Contact_In_ContactsPage = false;
		try {
			commonLib.performHover("Accounts_RestrictedPartyList_EditableImageIcon_XPATH");
			commonLib.click("Accounts_RestrictedPartyList_EditableImageIcon_XPATH");
			Thread.sleep(2000);

			commonLib.syso("i.Disable  Restricted Party List checkbox  ");
			commonLib.press_ArrowDown_Given_No_Of_Times(6);

			boolean is_RestrictPartyCheckboxSelected = commonLib
					.is_Checkbox_Selected("Accounts_RestrictedPartyList_Checkbox_XPATH");
			if (is_RestrictPartyCheckboxSelected) {
				commonLib.unSelect_Checkbox_Using_JavaScriptExecutor("Accounts_RestrictedPartyList_Checkbox_XPATH");
				commonLib.select_Checkbox_Using_JavaScriptExecutor("Accounts_RestrictedPartyList_Checkbox_XPATH");
				Thread.sleep(2000);
				commonLib.unSelect_Checkbox("Accounts_RestrictedPartyList_Checkbox_XPATH");
				Thread.sleep(2000);
			} else {
				commonLib.select_Checkbox("Accounts_RestrictedPartyList_Checkbox_XPATH");
				Thread.sleep(2000);
				commonLib.unSelect_Checkbox("Accounts_RestrictedPartyList_Checkbox_XPATH");
			}

			commonLib.syso("ii.Select Restricted Party List Reason as '" + restrictedPartyListReason
					+ "' and move from Available to Chosen section");
			commonLib.clickWithDynamicValue("Accounts_RestrictedPartyListReason_Menu_XPATH", restrictedPartyListReason);
			Thread.sleep(1000);
			commonLib.click("AccountsPage_MoveselectiontoChosen_Icon_In_RestrictedPartyListReason_Menu_In_XPATH");

			commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"AccountsPage_Chosen_Menu_In_RestrictedPartyListReason_Section_XPATH", restrictedPartyListReason);

			commonLib.syso(" iii.Fill comments in Additional Information i.e " + additionalInformation);
			commonLib.clear("ContactsPage_AdditionalInformation_Editbox_Editing_XPATH");
			commonLib.sendKeys("ContactsPage_AdditionalInformation_Editbox_Editing_XPATH", additionalInformation);

			commonLib.syso("Click 'Save' button");
			commonLib.clickbyjavascript("ContactsPage_Save_Button_XPATH");
			Thread.sleep(3000);

			boolean is_whiteListReason_Status = commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"ContactsPage_RestrictedPartyListReason_Editbox_Value_XPATH", restrictedPartyListReason);

			if (is_whiteListReason_Status) {
				commonLib.log(LogStatus.PASS, "WhiteList Reason=" + restrictedPartyListReason + " is found");
				iswhiteListing_Contact_In_ContactsPage = true;
			} else {
				commonLib.log(LogStatus.FAIL, "WhiteList Reason='" + restrictedPartyListReason + "' is not found");
			}

			boolean is_additionalInformation_Status = commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"ContactsPage_AdditionalInformation_Label_Value_XPATH", additionalInformation);
			if (is_additionalInformation_Status) {
				commonLib.syso("additionalInformation='" + additionalInformation + "' is found");
				iswhiteListing_Contact_In_ContactsPage = true;
			} else {
				commonLib.log(LogStatus.FAIL, "additionalInformation='" + additionalInformation + "' is not found");
				iswhiteListing_Contact_In_ContactsPage = false;
			}

			commonLib.getScreenshot();

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Not able to whitelist the contact");
			iswhiteListing_Contact_In_ContactsPage = false;
		}
		return iswhiteListing_Contact_In_ContactsPage;
	}

	// ******************************************************************************************************
	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * search_For_Account_From_HomePage Description : It searches account from Home
	 * page and verify results table contains given value Arguments : accountName
	 * Return value : true/false Example :
	 * search_For_Account_From_HomePage(accountName)
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public boolean search_For_Account_From_HomePage(String accountName) throws InterruptedException, AWTException {
		boolean accounts_Results_Table_Exist = false;
		String sArr[] = accountName.split(",");
		for (String eachAccountName : sArr) {
			try {
				commonLib.syso("Enter Account name in Global search=" + eachAccountName);
				commonLib.clear("GlobalSearch_Homepage_XPATH");
				commonLib.sendKeys("GlobalSearch_Homepage_XPATH", eachAccountName);
				Thread.sleep(2000);
				commonLib.click("GlobalSearch_Homepage_XPATH");
				// commonLib.KeyPress_Enter();
				commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
				accounts_Results_Table_Exist = commonLib.waitForVisibilityOf_DynamicXpath(
						"HomePage_AccountsSearchResult_Data_Table_XPATH", eachAccountName);
				if (accounts_Results_Table_Exist) {

				} else// if seacrh is not done for the first time, Try again
				{
					commonLib.syso("Again enter Account name in Global search=" + eachAccountName);
					commonLib.clear("GlobalSearch_Homepage_XPATH");
					commonLib.sendKeys("GlobalSearch_Homepage_XPATH", eachAccountName);
					Thread.sleep(2000);
					commonLib.click("GlobalSearch_Homepage_XPATH");
					commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
					accounts_Results_Table_Exist = commonLib.waitForVisibilityOf_DynamicXpath(
							"HomePage_AccountsSearchResult_Data_Table_XPATH", eachAccountName);
				}

				if (accounts_Results_Table_Exist) {
					commonLib.syso("Accounts search results table contains " + eachAccountName);
				} else {
					commonLib.log(LogStatus.FAIL,
							"Failed.Accounts Search results table does not contain " + eachAccountName);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				commonLib.log(LogStatus.FAIL, "Failed.Not able  to search account " + eachAccountName);
				e.printStackTrace();
				// return false;
			}
		}
		return accounts_Results_Table_Exist;

	}

	// ******************************************************************************************************
	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * search_For_ContactOrPrincipal_From_HomePage Description : It searches Contact
	 * from Home page and verify Contacts results table contains given value
	 * Arguments : contactName("ram, rama rao") Return value : true/false Example :
	 * search_For_ContactOrPrincipal_From_HomePage("ram, rama rao");
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public boolean search_For_ContactOrPrincipal_From_HomePage(String contactName)
			throws InterruptedException, AWTException {
		boolean contacts_Results_Table_Exist = false;
		String sArr[] = contactName.split(",");
		for (String eachContactName : sArr) {
			try {
				commonLib.syso("Enter Contact name in Global search=" + eachContactName);
				commonLib.clear("GlobalSearch_Homepage_XPATH");
				commonLib.sendKeys("GlobalSearch_Homepage_XPATH", eachContactName);
				Thread.sleep(3000);
				commonLib.click("GlobalSearch_Homepage_XPATH");
				// commonLib.KeyPress_Enter();
				commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
				contacts_Results_Table_Exist = commonLib.waitForVisibilityOf_DynamicXpath(
						"HomePage_ContactsSearchResult_Data_Table_XPATH", eachContactName);
				commonLib.syso("contacts_Results_Table_Exist=" + contacts_Results_Table_Exist);
				if (contacts_Results_Table_Exist) {

				} else// if search is not done for the first time, Try again
				{
					commonLib.syso("Again enter Contact name in Global search=" + eachContactName);
					commonLib.clear("GlobalSearch_Homepage_XPATH");
					commonLib.sendKeys("GlobalSearch_Homepage_XPATH", eachContactName);
					Thread.sleep(3000);
					commonLib.click("GlobalSearch_Homepage_XPATH");
					// commonLib.KeyPress_Enter();
					commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
					contacts_Results_Table_Exist = commonLib.waitForVisibilityOf_DynamicXpath(
							"HomePage_ContactsSearchResult_Data_Table_XPATH", eachContactName);
					commonLib.syso("2.contacts_Results_Table_Exist=" + contacts_Results_Table_Exist);
				}

				if (contacts_Results_Table_Exist) {
					commonLib.syso("Contacts search results table contains " + eachContactName);
				} else {
					commonLib.log(LogStatus.FAIL,
							"Failed.Contacts Search results table does not contain " + eachContactName);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				commonLib.log(LogStatus.FAIL, "Failed.Not able  to search contact ");
				e.printStackTrace();
				return false;

			}
		}
		return contacts_Results_Table_Exist;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * get_Restricted_Party_AccountName_From_Accounts_Page Description : It gets the
	 * restricted party Account name from accounts page Arguments : NA Return value
	 * : String Example : get_Restricted_Party_AccountName_From_Accounts_Page();
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public String get_Restricted_Party_AccountName_From_Accounts_Page() throws InterruptedException, AWTException {
		try {
			open_Given_Tab_In_Home_Page("Accounts");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String restrictedPartyAccountName = "";
		try {
			restrictedPartyAccountName = commonLib.getAttribute("AccountsPage_RestrictedParty_AccountName_XPATH",
					"title");
			commonLib.syso("restrictedPartyAccountName=" + restrictedPartyAccountName);
			return restrictedPartyAccountName;
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			commonLib.log(LogStatus.FAIL, "Failed to get Restricted_Party_AccountName_From_Accounts_Page ");
			e.printStackTrace();

		}
		return restrictedPartyAccountName;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * verify_ContactsSearchResult_ColumnNames_From_Global_Search_Page Description :
	 * It verifies all column names from contacts searcrh results from global search
	 * Arguments : ContactsSearchResult_ColumnNames Return value : Example : String
	 * ContactsSearchResult_ColumnNames="Name,First Name,Last Name,Passport # / Travel ID,Phone,Email,Contact Type,Restricted Party List,Restricted Party List Date"
	 * ; verify_ContactsSearchResult_ColumnNames_From_Global_Search_Page(
	 * ContactsSearchResult_ColumnNames);
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void verify_ContactsSearchResult_ColumnNames_From_Global_Search_Page(
			String ContactsSearchResult_ColumnNames) {
		String[] contactColumnNamesArr = ContactsSearchResult_ColumnNames.split(",");
		for (String EachColName : contactColumnNamesArr) {
			boolean ColExist = commonLib
					.findElementPresence_Dynamic("HomePage_ContactsSearchResult_Columns_Table_Data_XPATH", EachColName);
			if (ColExist) {
				commonLib.log(LogStatus.PASS,
						"Contacts Search results Table Columns contains given column name=" + EachColName);
			} else {
				commonLib.log(LogStatus.FAIL,
						"Contacts Search results Table Columns does not contain given column name=" + EachColName);
			}
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * verify_Accounts_SearchResult_ColumnNames_From_Global_Search_Page Description
	 * : It verifies all column names from Accounts searcrh results from global
	 * search Arguments : AccountSearchResult_ColumnNames Return value : NA Example
	 * : String
	 * AccountSearchResult_ColumnNames="Account Name,Unique system number,Legal Name,Trade Name,Billing Address,Phone,Restricted Party List,Restricted Party List Date,Tax Identification Number"
	 * ; sfdcLib.verify_Accounts_SearchResult_ColumnNames_From_Global_Search_Page(
	 * AccountSearchResult_ColumnNames);
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void verify_Accounts_SearchResult_ColumnNames_From_Global_Search_Page(
			String AccountSearchResult_ColumnNames) {
		String[] ColumnNamesArr = AccountSearchResult_ColumnNames.split(",");
		for (String EachColName : ColumnNamesArr) {
			boolean ColExist = commonLib
					.findElementPresence_Dynamic("HomePage_AccountsSearchResult_Columns_Table_XPATH", EachColName);
			if (ColExist) {
				commonLib.log(LogStatus.PASS,
						"Accounts Search results Table Columns contains given column name=" + EachColName);
			} else {
				commonLib.log(LogStatus.FAIL,
						"Accounts Search results Table Columns does not contain given column name=" + EachColName);
			}
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * get_Restricted_Party_ContactName_From_Contacts_Page Description : It gets
	 * first restricted party contact name from contacts page Arguments : NA Return
	 * value : String Example :
	 * get_Restricted_Party_ContactName_From_Contacts_Page();
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public String get_Restricted_Party_ContactName_From_Contacts_Page() throws InterruptedException, AWTException {
		try {
			open_Given_Tab_In_Home_Page("Contacts");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String restrictedPartyContacttName = "";
		try {
			restrictedPartyContacttName = commonLib.getAttribute("AccountsPage_RestrictedParty_AccountName_XPATH",
					"title");
			commonLib.syso("restrictedPartyContacttName=" + restrictedPartyContacttName);
			return restrictedPartyContacttName;
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			commonLib.log(LogStatus.FAIL, "Failed to get get_Restricted_Party_ContactName_From_Contacts_Page ");
			e.printStackTrace();

		}
		return restrictedPartyContacttName;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * verify_RestrictedPartyListCheckbox_Is_Exist_In_Accounts_Page Description : It
	 * checks the RestrictedPartyListCheckbox is exist in Accounts_Page Arguments :
	 * NA Return value : NA Example :
	 * verify_RestrictedPartyListCheckbox_Is_Exist_In_Accounts_Page();
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void verify_RestrictedPartyListCheckbox_Is_Exist_In_Accounts_Page() {
		try {
			commonLib.press_ArrowDown_Given_No_Of_Times(7);
			Thread.sleep(2000);
			int cnt = commonLib.findElements("AccountsPage_RestrictedPartyLisCheckboxLabelName_XPATH").size();
			// if(commonLib.waitForVisibilityOf("AccountsPage_RestrictedPartyLisCheckboxLabelName_XPATH",2))
			if (cnt >= 1) {
				commonLib.syso("Restricted Party List checkbox is  displayed.");
				commonLib.getScreenshot();
			}

			else {
				commonLib.log(LogStatus.FAIL, "Restricted Party List checkbox is not displayed.");
			}

		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			commonLib.log(LogStatus.FAIL, "Failed to verify_RestrictedPartyListCheckbox_Is_Exist_In_Accounts_Page ");
			e.printStackTrace();

		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * verify_RestrictedPartyListReason_And_Comments_Are_Exist_In_Accounts_Page
	 * Description : It verifies
	 * RestrictedPartyListReason_And_Comments_Are_Exist_In_Accounts_Page Arguments :
	 * NA Return value : NA Example :
	 * verify_RestrictedPartyListReason_And_Comments_Are_Exist_In_Accounts_Page();
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void verify_RestrictedPartyListReason_And_Comments_Are_Exist_In_Accounts_Page() {
		try {
			commonLib.press_ArrowDown_Given_No_Of_Times(7);
			Thread.sleep(2000);
			// if(commonLib.waitForVisibilityOf("AccountsPage_RestrictedPartyListReason_Label_XPATH",5))
			int cnt = commonLib.findElements("AccountsPage_RestrictedPartyListReason_Label_XPATH").size();
			if (cnt >= 1) {
				commonLib.syso("Restricted Party List Reason is displayed.");
			}

			else {
				commonLib.log(LogStatus.FAIL, "Restricted Party List Reason is not  displayed.");

			}

			cnt = commonLib.findElements("AccountsPage_AdditionalInformation_Label_XPATH").size();
			// if(commonLib.waitForVisibilityOf("AccountsPage_AdditionalInformation_Label_XPATH",1))
			if (cnt >= 1) {
				commonLib.syso("Additional Information field is displayed.");
				commonLib.getScreenshot();
			}

			else {
				commonLib.log(LogStatus.FAIL, "Additional Information field is  not displayed.");

			}

		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			commonLib.log(LogStatus.FAIL,
					"Failed to get verify_RestrictedPartyListReason_And_Comments_Are_Exist_In_Accounts_Page ");
			e.printStackTrace();

		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * verify_RestrictedPartyListReason_And_Comments_Are_Not_Exist_In_Accounts_Page
	 * Description : It verifies
	 * RestrictedPartyListReason_And_Comments_Are_Not_Exist_In_Accounts_Page
	 * Arguments : NA Return value : NA Example :
	 * verify_RestrictedPartyListReason_And_Comments_Are_Not_Exist_In_Accounts_Page(
	 * );
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void verify_RestrictedPartyListReason_And_Comments_Are_Not_Exist_In_Accounts_Page() {
		try {
			//
			// if(commonLib.waitForVisibilityOf("AccountsPage_RestrictedPartyListReason_Label_XPATH",1))
			commonLib.press_ArrowDown_Given_No_Of_Times(7);
			Thread.sleep(2000);
			int cnt = commonLib.findElements("AccountsPage_RestrictedPartyListReason_Label_XPATH").size();
			commonLib.syso("cnt=" + cnt);
			if (cnt >= 1) {
				commonLib.log(LogStatus.FAIL, "Restricted Party List Reason is displayed.");
			}

			else {
				commonLib.syso("Restricted Party List Reason is not  displayed.");
				// commonLib.getScreenshot();
			}

			// if(commonLib.waitForVisibilityOf("AccountsPage_AdditionalInformation_Label_XPATH",1))
			cnt = commonLib.findElements("AccountsPage_AdditionalInformation_Label_XPATH").size();
			if (cnt >= 1) {
				commonLib.log(LogStatus.FAIL, "Additional Information field is displayed.");
			}

			else {
				commonLib.syso("Additional Information field is  not displayed.");
				commonLib.getScreenshot();
			}

		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			commonLib.log(LogStatus.FAIL,
					"Failed to get verify_RestrictedPartyListCheckbox_Is_Exist_In_Accounts_Page ");
			e.printStackTrace();

		}

	}

	// *******************************************************************************************************

	// Removed the below method as it was an exact duplicate of the method with same
	// name.

	// //Method for File Upload

	public void UploadFile(String imagePath) {
		StringSelection stringSelection = new StringSelection(imagePath);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
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

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Create_New_Margin_One_Off_Adjustment(Data for Channel Margin one off)
	 * Description : It allows to create a Channel Margin with all the data Passed
	 * as an arguments of type as One Off Adjustment Arguments : String fileName ,
	 * String sheetName , String colName Return value : List object or If Column
	 * name is not in sheet,it logs fail message and returns null or exception
	 * Example : List<String> Record_Type_value_data=sfdcLib.
	 * read_All_Test_Data_From_All_Rows_From_Given_ColumnName(worksheet,
	 * TestData_SheetName,"Record_Type_value");
	 * commonLib.syso("Record_Type_value_data="+Record_Type_value_data);
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public String Create_New_Margin_One_Off_Adjustment(String AdjustmentTypeValue, String FranchiseValue,
			String ReasonRationaleValue, String CommentsTextValue, String CurrencyValue, String TransactionValueAtCP,
			String TransactionValue, String StrykerGrossAtCP, String TransacationValueinUSD,
			String StrykerGrossMarginAtDP, String DPOffContract) throws IOException, InterruptedException {
		String channelMarginNumber = null;
		try {

			commonLib.waitForPresenceOfElementLocated("ChannelMargin_NewButton_XPATH");
			commonLib.click("ChannelMargin_NewButton_XPATH");
			// commonLib.waitForPresenceOfElementLocated("NewChannelMarginPopUp_OneOffCheckBox_XPATH");
			// commonLib.click("NewChannelMarginPopUp_OneOffCheckBox_XPATH");
			// commonLib.click("NewChannelMarginPopUp_NextButton_XPATH");
			commonLib.waitForPresenceOfElementLocated("NewChannelMargin_OneOffPopUp_HeaderText_XPATH");
			commonLib.click("NewChannelMargin_OneOffPopUp_AdjustMentType_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("NewChannelMargin_OneOffPopUp_AdjustMentType_Value_XPATH",
					AdjustmentTypeValue);
			commonLib.clickWithDynamicValue("NewChannelMargin_OneOffPopUp_AdjustMentType_Value_XPATH",
					AdjustmentTypeValue);
			commonLib.click("NewChannelMargin_OneOffPopUp_Franchise_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("NewChannelMargin_OneOffPopUp_Franchise_Value_XPATH",
					FranchiseValue);
			commonLib.clickWithDynamicValue("NewChannelMargin_OneOffPopUp_Franchise_Value_XPATH", FranchiseValue);
			commonLib.click("NewChannelMargin_OneOffPopUp_Reason_Rationale_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"NewChannelMargin_OneOffPopUp_Reason_Rationale_Value_XPATH", ReasonRationaleValue);
			commonLib.clickWithDynamicValue("NewChannelMargin_OneOffPopUp_Reason_Rationale_Value_XPATH",
					ReasonRationaleValue);
			commonLib.sendKeys("NewChannelMargin_OneOffPopUp_Comments_TextArea_XPATH", CommentsTextValue);
			Thread.sleep(500);
			commonLib.scrollDownToElement("NewChannelMargin_OneOffPopUp_DiscountPerOffContract_TextArea_XPATH",
					"field");
			commonLib.click("NewChannelMargin_OneOffPopUp_CurrencyDropDown_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("NewChannelMargin_OneOffPopUp_CurrencyDropDownValue_XPATH",
					CurrencyValue);
			commonLib.sendKeys("NewChannelMargin_OneOffPopUp_CurrencyDropDown_XPATH", CurrencyValue);
			commonLib.clickWithDynamicValue("NewChannelMargin_OneOffPopUp_CurrencyDropDownValue_XPATH", CurrencyValue);
			Thread.sleep(500);
			commonLib.sendKeys("NewChannelMargin_OneOffPopUp_TransactionContractPrice_TextArea_XPATH",
					TransactionValueAtCP);
			Thread.sleep(500);
			commonLib.sendKeys("NewChannelMargin_OneOffPopUp_TransactionValue_TextArea_XPATH", TransactionValue);
			Thread.sleep(500);
			commonLib.sendKeys("NewChannelMargin_OneOffPopUp_StrykerGrossAtCP_TextArea_XPATH", StrykerGrossAtCP);
			Thread.sleep(500);
			commonLib.sendKeys("NewChannelMargin_OneOffPopUp_Transaction_value_in_USD_TextArea_XPATH",
					TransacationValueinUSD);
			Thread.sleep(500);
			commonLib.sendKeys("NewChannelMargin_OneOffPopUp_StrykerGrossAtDP_TextArea_XPATH", StrykerGrossMarginAtDP);
			Thread.sleep(500);
			commonLib.sendKeys("NewChannelMargin_OneOffPopUp_DiscountPerOffContract_TextArea_XPATH", DPOffContract);
			Thread.sleep(500);
			commonLib.click("OutOfOfficePopUp_SaveButton_XPATH");

			if (commonLib.waitForPresenceOfElementLocated(
					"NewChannelMargin_OneOffPopUp_SaveMessage_ChannelMarginNumber_TextArea_XPATH")) {
				// channelMarginNumber=commonLib.getText("NewChannelMargin_OneOffPopUp_SaveMessage_ChannelMarginNumber_TextArea_XPATH");
				channelMarginNumber = commonLib.getAttribute(
						"NewChannelMargin_OneOffPopUp_SaveMessage_ChannelMarginNumber_Anchor_XPATH", "title");
				commonLib.syso("Channel Margin is created successfully with Number" + channelMarginNumber);
			} else {
				commonLib.syso("Channel Margin is Not created successfully");
			}

		} catch (Exception e) {
			commonLib.syso("Channel Margin is Not created successfully because of this Exception" + e.getMessage());
		}
		return channelMarginNumber;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Create_New_Margin_Current_Business_Updates(Data for Channel Margin Current
	 * business updates arguments) Description : It allows to create a Channel
	 * Margin with all the data Passed as an arguments of type as CUrrent business
	 * updates Arguments : Channel margin data Return value : Channel Margin number
	 * Example :
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public String Create_New_Margin_Current_Business_Updates(String AccountName, String FranchiseValue, String Services,
			String IndirectChannelType, String DealerGM, String AgentCommission, String ICInitiative)
			throws IOException, InterruptedException {
		String channelMarginNumber = null;
		try {

			commonLib.waitForPresenceOfElementLocated("ChannelMargin_NewButton_XPATH");
			commonLib.click("ChannelMargin_NewButton_XPATH");
			// commonLib.waitForPresenceOfElementLocated("NewChannelMarginPopUp_OneOffCheckBox_XPATH");
			// commonLib.click("NewChannelMarginPopUp_CurrentBusinessUpdated_Checkbox_XPATH");
			// commonLib.click("NewChannelMarginPopUp_NextButton_XPATH");
			// commonLib.waitForPresenceOfElementLocated("NewChannelMargin_CurrentBusinessUpdates_HeaderText_XPATH");
			commonLib.sendKeys("NewChannelMargin_CurrentBusinessUpdates_AccountSearchField_XPATH", AccountName);
			Thread.sleep(2000);
			commonLib.clickWithDynamicValue(
					"NewChannelMargin_CurrentBusinessUpdates_AccountSearchField_AccountSuggestions_XPATH", AccountName);
			Thread.sleep(2 * 1000);
			commonLib.click("NewChannelMargin_CurrentBusinessUpdates_Franchise_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"NewChannelMargin_CurrentBusinessUpdates_Franchise_Value_XPATH", FranchiseValue);
			commonLib.clickbyjavascriptWithDynamicValue("NewChannelMargin_CurrentBusinessUpdates_Franchise_Value_XPATH",
					FranchiseValue);
			Thread.sleep(500);
			commonLib.clickWithDynamicValue("NewChannelMargin_ServicesMultiSelect_XPATH", Services);
			Thread.sleep(500);
			commonLib.click("NewChannelMargin_MoveToSelection_XPATH");
			Thread.sleep(500);
			commonLib.scrollDownToElement("NewChannelMargin_MoveToSelection_XPATH", "Button");
			Thread.sleep(500);
			commonLib.click("NewChannelMargin_IndirectChannelType_Dropdown_XPATH");
			commonLib.clickWithDynamicValue("NewChannelMargin_IndirectChannelType_Dropdown_value_XPATH",
					IndirectChannelType);
			Thread.sleep(500);
			commonLib.sendKeys("NewChannelMargin_DealerGrossMargin_XPATH", DealerGM);
			Thread.sleep(500);
			commonLib.sendKeys("NewChannelMargin_AgentCommission_XPATH", AgentCommission);
			Thread.sleep(500);
			commonLib.sendKeys("NewChannelMargin_ICIncentive_XPATH", ICInitiative);
			Thread.sleep(500);

			commonLib.click("ChannelMarginPopUp_SaveButton_XPATH");

			if (commonLib.waitForPresenceOfElementLocated(
					"NewChannelMargin_CurrentBusinessUpdated_SaveMessage_ChannelMarginNumber_TextArea_XPATH")) {
				// channelMarginNumber=commonLib.getText("NewChannelMargin_OneOffPopUp_SaveMessage_ChannelMarginNumber_TextArea_XPATH");
				channelMarginNumber = commonLib
						.getText("NewChannelMargin_CurrentBusinessUpdated_Header_ChannelMarginNumber_XPATH");
				commonLib.syso("Channel Margin is created successfully with Number" + channelMarginNumber);
			} else {
				commonLib.syso("Channel Margin is Not created successfully");
			}

		} catch (Exception e) {
			commonLib.syso("Channel Margin is Not created successfully because of this Exception" + e.getMessage());
		}
		return channelMarginNumber;
	}

	public String Create_New_Margin_MultipleServices_Current_Business_Updates(String AccountName, String FranchiseValue,
			List<String> Services, String IndirectChannelType, String DealerGM, String AgentCommission,
			String ICInitiative) throws IOException, InterruptedException {
		String channelMarginNumber = null;
		try {

			commonLib.waitForPresenceOfElementLocated("ChannelMargin_NewButton_XPATH");
			commonLib.click("ChannelMargin_NewButton_XPATH");
			commonLib.waitForPresenceOfElementLocated("NewChannelMarginPopUp_OneOffCheckBox_XPATH");
			commonLib.click("NewChannelMarginPopUp_CurrentBusinessUpdated_Checkbox_XPATH");
			commonLib.click("NewChannelMarginPopUp_NextButton_XPATH");
			commonLib.waitForPresenceOfElementLocated("NewChannelMargin_CurrentBusinessUpdates_HeaderText_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.sendKeys("NewChannelMargin_SearchAccount_XPATH", AccountName);
			Thread.sleep(2000);
			commonLib.clickWithDynamicValue("NewChannelMargin_AccountNameSuggestion_XPATH", AccountName);
			Thread.sleep(2 * 1000);
			commonLib.click("NewChannelMargin_CurrentBusinessUpdates_Franchise_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"NewChannelMargin_CurrentBusinessUpdates_Franchise_Value_XPATH", FranchiseValue);
			commonLib.clickbyjavascriptWithDynamicValue("NewChannelMargin_CurrentBusinessUpdates_Franchise_Value_XPATH",
					FranchiseValue);
			Thread.sleep(500);
			for (String object : Services) {
				commonLib.clickWithDynamicValue("NewChannelMargin_ServicesMultiSelect_XPATH", object);
				Thread.sleep(500);
				commonLib.click("NewChannelMargin_MoveToSelection_XPATH");
				Thread.sleep(500);
			}
			Thread.sleep(1000);
			commonLib.sendKeys("NewChannelMargin_DealerGrossMargin_XPATH", DealerGM);
			Thread.sleep(500);
			commonLib.click("NewChannelMargin_IndirectChannelType_Dropdown_XPATH");
			Thread.sleep(500);
			commonLib.clickWithDynamicValue("NewChannelMargin_IndirectChannelType_Dropdown_value_XPATH",
					IndirectChannelType);
			Thread.sleep(500);
			commonLib.sendKeys("NewChannelMargin_AgentCommission_XPATH", AgentCommission);
			Thread.sleep(500);
			commonLib.sendKeys("NewChannelMargin_ICIncentive_XPATH", ICInitiative);
			Thread.sleep(500);
			commonLib.click("NewChannelMargin_SaveButton_XPATH");

			if (commonLib.waitForPresenceOfElementLocated(
					"NewChannelMargin_CurrentBusinessUpdated_SaveMessage_ChannelMarginNumber_TextArea_XPATH")) {
				// channelMarginNumber=commonLib.getText("NewChannelMargin_OneOffPopUp_SaveMessage_ChannelMarginNumber_TextArea_XPATH");
				channelMarginNumber = commonLib
						.getText("NewChannelMargin_CurrentBusinessUpdated_Header_ChannelMarginNumber_XPATH");
				commonLib.syso("Channel Margin is created successfully with Number" + channelMarginNumber);
			} else {
				commonLib.syso("Channel Margin is Not created successfully");
			}

		} catch (Exception e) {
			commonLib.syso("Channel Margin is Not created successfully because of this Exception" + e.getMessage());
		}
		return channelMarginNumber;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * navigate_To_Existing_ChannelMargin_From_Global_Search(channelMarginNumber)
	 * Description : It allows user to traverse the existing channelMargin case
	 * through global search Arguments : channel Margin number Return value : NA
	 * Example : navigate_To_Existing_SupportCase_From_Global_Search("FMX-000227");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void navigate_To_Existing_ChannelMargin_From_Global_Search(String channelMarginNumber)
			throws AWTException, InterruptedException {

		try {
			commonLib.waitForElementToBeClickable("GlobalSearch_Homepage_XPATH");
			commonLib.clickbyjavascript("GlobalSearch_Homepage_XPATH");
			Thread.sleep(2000);
			commonLib.clear("Global_Search_XPATH");
			commonLib.sendKeys("Global_Search_XPATH", channelMarginNumber);
			Thread.sleep(2000);

			commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
			boolean channelMargin_Results_Table_Exist = commonLib.waitForVisibilityOf_DynamicXpath(
					"ChannelMargin_Results_Table_In_Home_Page_XPATH", channelMarginNumber);

			if (channelMargin_Results_Table_Exist) {

				commonLib.syso("Channel Margin result is coming successfully");
			} else {
				commonLib.log(LogStatus.FAIL,
						"Not able to search the given Channel margin number i.e. :" + channelMarginNumber);
			}

			commonLib.clickbyjavascriptWithDynamicValue("ChannelMargin_Results_Table_In_Home_Page_XPATH",
					channelMarginNumber);

			commonLib.waitForPresenceOfElementLocated(
					"NewChannelMargin_CurrentBusinessUpdated_Header_ChannelMarginNumber_XPATH");

			String Actual_channelMarginNumber = commonLib
					.getText("NewChannelMargin_CurrentBusinessUpdated_Header_ChannelMarginNumber_XPATH");
			commonLib.syso(channelMarginNumber);
			commonLib.syso(Actual_channelMarginNumber);
			if (channelMarginNumber.equals(Actual_channelMarginNumber)) {

				commonLib.syso("channelMarginNumber ='" + channelMarginNumber + "' page is open");
				Thread.sleep(8 * 1000);

			} else {
				commonLib.syso("channelMarginCase = " + channelMarginNumber + "' page is not open");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Create_New_Contact_FromContactScreen(Contact data) Description : It creates a
	 * new Contact from Contact screen Arguments :String email, String
	 * LastName,String Firstname, String jobTitle, String mobileNumber Return value
	 * : NA Example : Create_New_Contact_FromContactScreen(String email, String
	 * LastName,String Firstname, String jobTitle, String mobileNumber)
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void Create_New_Contact_FromContactScreen(String email, String LastName, String Phone, String Firstname,
			String jobTitle, String mobileNumber) throws IOException, InterruptedException {

		try {
			commonLib.waitForPresenceOfElementLocated("NewContact_Button_Account_XPATH");
			commonLib.click("NewContact_Button_Account_XPATH");
			Thread.sleep(5 * 1000);
			/*
			 * if(commonLib.verifyPresence("NewContact_ICContact_Checkbox_XPATH")) {
			 * commonLib.waitForPresenceOfElementLocated(
			 * "NewContact_ICContact_Checkbox_XPATH");
			 * commonLib.click("NewContact_ICContact_Checkbox_XPATH");
			 * commonLib.click("NewContactPopUp_NextButton_XPATH");
			 * commonLib.waitForPresenceOfElementLocated("NewContactPopUp_EmailInput_XPATH")
			 * ; commonLib.sendKeys("NewContactPopUp_JobTitle_XPATH", jobTitle);
			 * Thread.sleep(500);
			 * 
			 * }
			 */

			commonLib.waitForPresenceOfElementLocated("NewContactPopUp_EmailInput_XPATH");
			commonLib.sendKeys("NewContactPopUp_EmailInput_XPATH", email);
			Thread.sleep(500);
			commonLib.sendKeys("NewContactPopUp_LastNameInput_XPATH", LastName);
			Thread.sleep(500);
			commonLib.sendKeys("NewContactPopUp_FirstName_XPATH", Firstname);
			Thread.sleep(500);
			commonLib.sendKeys("NewContactPopUp_Phone_XPATH", Phone);
			Thread.sleep(500);
			commonLib.sendKeys("NewContactPopUp_Mobile_XPATH", mobileNumber);
			Thread.sleep(500);
			commonLib.click("NewContactPopUp_Save_XPATH");
			commonLib.waitForPresenceOfElementLocated("NewContact_ContactCreationMessage_XPATH");
			commonLib.syso("New Contact is successfully created");

		} catch (Exception e) {
			commonLib.syso("New Contact is not created successfully with Error");
		}

	}

	// *******************************************************************************************************
	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * upload_File_In_SupportCase_Page Description : upload_File_In_SupportCase_Page
	 * Arguments : NA Return value : NA Example : upload_File_In_SupportCase_Page()
	 * 
	 * =============================================================================
	 * ===============================================
	 */

	public void upload_File_In_SupportCase_Page(String fileName) throws InterruptedException {
		String filepath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\stryker\\NV\\data\\images\\"
				+ fileName + ".png";
		try {
			// File Upload
			commonLib.waitForElementToBeClickable("SupportCaseDetail_AddFilesButton_XPATH");
			// commonLib.click("SupportCaseDetail_AddFilesButton_XPATH");
			commonLib.clickbyjavascript("SupportCaseDetail_AddFilesButton_XPATH");
			commonLib.waitForPresenceOfElementLocated("SupportCase_AddFile_Button_XPATH");

			commonLib.clickbyjavascript("SupportCase_AddFile_Button_XPATH");
			commonLib.waitForVisibilityOf("Contract_Upload_Button_XPATH");
			// commonLib.click("Contract_Upload_Button_XPATH");
			Thread.sleep(3 * 1000);
			uploadFileDirectly("Contract_Upload_Button_XPATH", filepath);
			commonLib.waitForElementToBeClickable("Contract_Upload_Done_XPATH");
			commonLib.clickbyjavascript("Contract_Upload_Done_XPATH");
			Thread.sleep(5 * 1000);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			commonLib.log(LogStatus.FAIL, "Failed.upload_File_In_SupportCase_Page.i.e" + filepath);
			e.printStackTrace();
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * create_New_Account_In_Accounts_Page Description : It creates new a/c
	 * Arguments : String accountName,String Region,String SubRegion,String
	 * CountryofOperation Return value : String Example :
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public String create_New_Account_In_Accounts_Page(String accountName, String Region, String SubRegion,
			String CountryofOperation) {

		try {
			open_Given_Tab_In_Home_Page("Accounts");
			open_New_Account_In_Accounts_Page();

			commonLib.syso("Enter Account Name='" + accountName + "' in Accounts page");
			commonLib.sendKeys("AccountName_Textbox_In_New_Account_Page_XPATH", accountName);
			Thread.sleep(1 * 1000);
			commonLib.click("AccountName_Textbox_In_New_Account_Page_XPATH");
			Thread.sleep(1 * 1000);

			commonLib.click("NewAccountPage_Region_Dropdown_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("NewAccountPage_Region_Values_Dropdown_XPATH", Region);
			commonLib.clickbyjavascriptWithDynamicValue("NewAccountPage_Region_Values_Dropdown_XPATH", Region);

			commonLib.click("NewAccountPage_SubRegion_Dropdown_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("NewAccountPage_SubRegion_Values_Dropdown_XPATH",
					SubRegion);
			commonLib.clickbyjavascriptWithDynamicValue("NewAccountPage_SubRegion_Values_Dropdown_XPATH", SubRegion);
			Thread.sleep(1 * 1000);

			commonLib.clickbyjavascript("CountryofOperation_Dropdown_In_New_Account_Page_XPATH");
			Thread.sleep(1 * 1000);

			commonLib.send_Text_To_Currently_Focussed_Element_Using_Actions_Class(CountryofOperation);
			commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();

			commonLib.syso("Click \"Save\" button");
			commonLib.clickwithoutWait("Save_Button_In_New_Account_Page_XPATH");

			boolean accountName_Exist = commonLib
					.waitForVisibilityOf_DynamicXpath("AccountName_Text_Header_In_Accounts_Page_XPATH", accountName);

			if (accountName_Exist) {
				commonLib.syso("Account ='" + accountName + "' created");
				return accountName;
			} else {
				commonLib.log(LogStatus.FAIL, "Account ='" + accountName + "'is not created");
				return "";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * get_Last_ContractNo Description : It gets last Contract number Arguments : NA
	 * Return value : String Example :
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public String get_Last_ContractNumber_From_Contracts_Page() {
		String LastContractNo = null;
		try {
			if (get_Contracts_Count_From_Contracts_Page() >= 1) {
				LastContractNo = commonLib.getText("ContractListing_LastContractLink_XPATH");
				commonLib.syso("Contact number=" + LastContractNo);
				return LastContractNo;
			} else {
				commonLib.log(LogStatus.FAIL, "Failed.Check Table has no rows");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			commonLib.log(LogStatus.FAIL, "Failed.Not able to get Last Contact number");
			e.printStackTrace();
			return LastContractNo;
		}
		return LastContractNo;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * get_Contracts_Count_From_Contracts_Page Description : It gets Contracts count
	 * Arguments : NA Return value : int Example :
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public int get_Contracts_Count_From_Contracts_Page() {
		int cnt = 0;
		try {
			commonLib.refreshPage();
			String str = commonLib.getText("ContractsPage_Table_Rows_Count_XPATH");

			commonLib.syso("str=" + str);
			String numberOnly = str.replaceAll("[^0-9]", "");
			cnt = commonLib.convertStringToInteger(numberOnly);

			commonLib.syso("Contact number Cnt=" + cnt);// 1
			return cnt;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			commonLib.log(LogStatus.FAIL, "Failed.Not able to get_Contracts_Count_From_Contracts_Page");
			e.printStackTrace();
		}
		return cnt;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Assign_ICSponsor_In_Contracts_Page Description : It assigns IC Sponsor in
	 * contracts page Arguments : NA Return value : int Example :
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public boolean Assign_ICSponsor_In_Contracts_Page(String AssignICSponsor) throws IOException {
		boolean is_Assign_ICSponsor_In_Contracts = false;
		try {
			commonLib.performHover("ContractsPage_ICSponsor_ImageIcon_XPATH");
			commonLib.click("ContractsPage_ICSponsor_ImageIcon_XPATH");
			commonLib.waitForPresenceOfElementLocated("ContractsPage_ICSponsorSearchPeople_Edibox_XPATH");

			commonLib.syso("Verify user is able to select the sponsor");
			commonLib.sendKeys("ContractsPage_ICSponsorSearchPeople_Edibox_XPATH", AssignICSponsor);
			Thread.sleep(2000);

			commonLib.clickLink(AssignICSponsor);
			commonLib.click("ContactsPage_Save_Button_XPATH");

			boolean AssignICSponsorexist = commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"ContractsPage_ICSponsor_LabelValueEditbox_XPATH", AssignICSponsor);
			if (AssignICSponsorexist) {
				commonLib.syso("User should able to select the sponsor as" + AssignICSponsor);
				commonLib.getScreenshot();
				return AssignICSponsorexist;
			} else {
				commonLib.log(LogStatus.FAIL, "User is not able to select the sponsor as" + AssignICSponsor);
				return AssignICSponsorexist;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return is_Assign_ICSponsor_In_Contracts;
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name : OpenAReport
	 * Description : It searches for and opens a particular report Arguments :
	 * ReportName, timeToLoad Return value : Boolean Example : OpenAReport(String
	 * ReportName, long timeToLoad); //Generic Method to search and open any report.
	 * Author : simranjeet.singh08@stryker.com
	 * =============================================================================
	 * ===============================================
	 */

	public Boolean OpenAReport(String ReportName, long timeToLoad) {
		Boolean openReport = false;
		try {
			int pageReloadcount = 0;
			open_Given_Tab_In_Home_Page("Reports");
			commonLib.log(LogStatus.INFO, "Opened Reports Tab from home page");
			commonLib.getScreenshot();

			commonLib.waitForPresenceOfElementLocated("Reports_Tab_All_Reports_XPATH");
			commonLib.click("Reports_Tab_All_Reports_XPATH");
			commonLib.getScreenshot();

			while (pageReloadcount < 1) {
				commonLib.waitforElement("Reports_Search_All_Reports_Btn_XPATH");
				commonLib.sendKeys("Reports_Search_All_Reports_Btn_XPATH", ReportName);
				Thread.sleep(5000);
				commonLib.KeyPress_BackSpace();

				if (commonLib.WaitforPresenceofElement_Dynamic_Xpath("Reports_Search_Result_Link_Dynamic_XPATH",
						ReportName)) {
					commonLib.log(LogStatus.PASS,
							"Search results for the report: " + ReportName + " displayed successfully");
					commonLib.getScreenshot();
					pageReloadcount = pageReloadcount + 1;
					openReport = true;
				} else {
					commonLib.log(LogStatus.FAIL, ReportName + " not found in search results");
					if (pageReloadcount == 0) {
						commonLib.refreshPage();
						commonLib.log(LogStatus.INFO, "Reloading the page to search for the report again");
						pageReloadcount = pageReloadcount + 1;
					} else {
						commonLib.log(LogStatus.INFO, "Reloading the page to search for the report again");
						commonLib.getScreenshot();
						// openReport=false;
					}

				}
			}

			commonLib.clickbyjavascriptWithDynamicValue("Reports_Search_Result_Link_Dynamic_XPATH", ReportName);
			commonLib.waitForElementTimeOut("Reports_Display_Page_Iframe_XPATH", 5000);
			commonLib.switchtoFrame("Reports_Display_Page_Iframe_XPATH");

			Thread.sleep(timeToLoad);

			if (commonLib.verifyPresence_Dynamic_Xpath("Reports_Display_Page_Report_Title_XPATH", ReportName)) {
				commonLib.log(LogStatus.PASS, "Report Title: " + ReportName + " displayed successfully");
				commonLib.getScreenshot();
			} else {
				commonLib.log(LogStatus.FAIL, "Report Title: " + ReportName + " not visible or report Page not opened");
				commonLib.getScreenshot();
				openReport = false;
			}

			commonLib.switchToDefaultContent();

		} catch (Exception e) {
			e.printStackTrace();
			commonLib.log(LogStatus.FAIL, "Open report method has failed. See Stack trace: " + " " + e.toString());
		}
		return openReport;

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * verifyReportColumnNames Description : It verifies the presence of all column
	 * names of a partcular report Arguments : ReportName, timeToLoad Return value :
	 * Integer Example :verifyReportColumnNames(List<String> columnNameList);
	 * //Generic method to verify presence of all Column Names in a Report. Author :
	 * simranjeet.singh08@stryker.com
	 * =============================================================================
	 * ===============================================
	 */

	public int verifyReportColumnNames(List<String> columnNameList) {
		int countOfMissingColumns = 0;
		try {
			commonLib.waitForElementTimeOut("Reports_Display_Page_Iframe_XPATH", 3000);
			commonLib.switchtoFrame("Reports_Display_Page_Iframe_XPATH");
			commonLib.getScreenshot();
			for (String columnName : columnNameList) {
				if (commonLib.verifyPresence_Dynamic_Xpath("Reports_Display_Page_Column_Name_Identifier_XPATH",
						columnName)) {
					commonLib.log(LogStatus.PASS, "Column: " + columnName + "  is visible in the Report");
				} else {
					commonLib.log(LogStatus.FAIL, "Column: " + columnName + "  is not visible in the Report");
					countOfMissingColumns = countOfMissingColumns + 1;
				}
			}
			commonLib.switchToDefaultContent();
		} catch (Exception e) {
			e.printStackTrace();
			commonLib.log(LogStatus.FAIL,
					"Verify Column Name method has failed. See Stack trace: " + " " + e.toString());
		}

		return countOfMissingColumns;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * OpenADashboard Description : It searches for and opens a particular report
	 * Arguments : DashboardName, timeToLoad Return value : Boolean Example :
	 * OpenADashboard(String DashboardName, long timeToLoad); //Generic Method to
	 * search and open any dashboard Author : simranjeet.singh08@stryker.com
	 * =============================================================================
	 * ===============================================
	 */

	public Boolean OpenADashboard(String DashboardName, long timeToLoad) {
		Boolean openDashboard = true;
		try {
			open_Given_Tab_In_Home_Page("Dashboards");
			commonLib.log(LogStatus.INFO, "Opened Dashboards Tab from home page");
			commonLib.getScreenshot();

			commonLib.waitForPresenceOfElementLocated("Dashboards_Tab_All_Dashboards_XPATH");
			commonLib.click("Dashboards_Tab_All_Dashboards_XPATH");
			commonLib.getScreenshot();

			commonLib.waitforElement("Dashboards_Search_All_Dashboards_Btn_XPATH");
			commonLib.sendKeys("Dashboards_Search_All_Dashboards_Btn_XPATH", DashboardName);
			if (commonLib.WaitforPresenceofElement_Dynamic_Xpath("Dashboards_Search_Result_Link_Dynamic_XPATH",
					DashboardName)) {
				commonLib.log(LogStatus.PASS,
						"Search results for the dashboard: " + DashboardName + " displayed successfully");
				commonLib.getScreenshot();
			} else {
				commonLib.log(LogStatus.FAIL, DashboardName + " not found in search results");
				commonLib.getScreenshot();
				openDashboard = false;
			}

			commonLib.clickbyjavascriptWithDynamicValue("Dashboards_Search_Result_Link_Dynamic_XPATH", DashboardName);
			commonLib.waitForElementTimeOut("Dashboards_Display_Page_Iframe_XPATH", 5000);
			commonLib.switchtoFrame("Dashboards_Display_Page_Iframe_XPATH");

			Thread.sleep(timeToLoad);

			if (commonLib.verifyPresence_Dynamic_Xpath("Dashboards_Display_Page_Dashboard_Title_XPATH",
					DashboardName)) {
				commonLib.log(LogStatus.PASS, "Dashboard Title: " + DashboardName + " displayed successfully");
				commonLib.getScreenshot();
			} else {
				commonLib.log(LogStatus.FAIL,
						"Dashboard Title: " + DashboardName + " not visible or report Page not opened");
				commonLib.getScreenshot();
				openDashboard = false;
			}

			commonLib.click("Dashboards_Board_Refresh_btn_XPATH");

			Thread.sleep(2000);

			commonLib.switchToDefaultContent();

		} catch (Exception e) {
			e.printStackTrace();
			commonLib.log(LogStatus.FAIL, "Open report method has failed. See Stack trace: " + " " + e.toString());
		}
		return openDashboard;

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * openReportTypeInICM_Finance(Reports Name) Description : This message is used
	 * to open the Reports type given as an arguments Arguments : Report name i.e.
	 * Type of Report you want to open in ICM-Finance Return value : NA Example :
	 * openReportTypeInICM_Finance(String ReportsName) Author :
	 * =============================================================================
	 * ===============================================
	 */
	public void openReportTypeInICM_Finance(String ReportsName) {
		try {
			commonLib.click("ReportsPage_AllFolders_Button_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.waitForPresenceOfElementLocated("ReportsPage_ICMReports_Button_XPATH");
			commonLib.clickbyjavascript("ReportsPage_ICMReports_Button_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.waitForPresenceOfElementLocated("ReportsPage_ICMReports__FinanceReports_Button_XPATH");
			commonLib.clickbyjavascript("ReportsPage_ICMReports__FinanceReports_Button_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"ReportsPage_ICMReports__FinanceReports_ReportType_Button_XPATH", ReportsName);
			commonLib.clickbyjavascriptWithDynamicValue(
					"ReportsPage_ICMReports__FinanceReports_ReportType_Button_XPATH", ReportsName);
			Thread.sleep(5 * 1000);
			commonLib.waitforFramenadSwitch("OpenedReports_Iframe_XPATH");

		} catch (Exception e) {
			commonLib.syso("Not able to open the given reports i.e. " + ReportsName);
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * DueDiligence_ReportsOpening_DaysToCompleteType(Reports Name) Description :
	 * This message is used to open the Reports type given as an arguments Arguments
	 * : Report name i.e. Type of Report you want to open in ICM Due Diligence
	 * Return value : NA Example :
	 * DueDiligence_ReportsOpening_DaysToCompleteType(String ReportsName) Author :
	 * =============================================================================
	 * ===============================================
	 */
	public void DueDiligence_ReportsOpening_DaysToCompleteType(String ReportsName) {
		try {
			commonLib.click("ReportsPage_AllFolders_Button_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.waitForPresenceOfElementLocated("ReportsPage_ICMDashboards_Button_XPATH");
			commonLib.clickbyjavascript("ReportsPage_ICMDashboards_Button_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.waitForPresenceOfElementLocated("ReportsPage_ICMDashboards__TimeTaken_Button_XPATH");
			commonLib.clickbyjavascript("ReportsPage_ICMDashboards__TimeTaken_Button_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.waitforFramenadSwitch("ReportsPage_ICMDashboards__TimeTaken_Button__Iframe_XPATH");
			Thread.sleep(1000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"ReportsPage_ICMDashboards__TimeTaken_Button__DueDilgenceDaystoComplete_XPATH", ReportsName);
			commonLib.clickbyjavascriptWithDynamicValue(
					"ReportsPage_ICMDashboards__TimeTaken_Button__DueDilgenceDaystoComplete_XPATH", ReportsName);
			Thread.sleep(5 * 1000);
			commonLib.switchToDefaultContent();
			Thread.sleep(1000);
			commonLib.waitforFramenadSwitch("OpenedReports_Iframe_XPATH");

		} catch (Exception e) {
			commonLib.syso("Not able to open the given reports i.e. " + ReportsName);
			commonLib.getScreenshot();
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * openReportTypeInICM_DD(Reports Name) Description : This message is used to
	 * open the Reports type given as an arguments Arguments : Report name i.e. Type
	 * of Report you want to open in ICM Due Diligence Return value : NA Example :
	 * openReportTypeInICM_DD(String ReportsName) Author : Ankit Bansal
	 * =============================================================================
	 * ===============================================
	 */
	public void openReportTypeInICM_DD(String ReportsName) {
		try {
			commonLib.click("ReportsPage_AllFolders_Button_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.waitForPresenceOfElementLocated("ReportsPage_ICMReports_Button_XPATH");
			commonLib.clickbyjavascript("ReportsPage_ICMReports_Button_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.waitForPresenceOfElementLocated("ReportsPage_ICMReports__ComplianceReports_Button_XPATH");
			commonLib.clickbyjavascript("ReportsPage_ICMReports__ComplianceReports_Button_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"ReportsPage_ICMReports__FinanceReports_ReportType_Button_XPATH", ReportsName);
			commonLib.clickbyjavascriptWithDynamicValue(
					"ReportsPage_ICMReports__FinanceReports_ReportType_Button_XPATH", ReportsName);
			Thread.sleep(5 * 1000);
			commonLib.waitforFramenadSwitch("OpenedReports_Iframe_XPATH");
			System.out.println("test");

		} catch (Exception e) {
			commonLib.syso("Not able to open the given reports i.e. " + ReportsName);
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * openReportTypeInICM_DD_AvgDaysToComplete(Reports Name) Description : This
	 * message is used to open the Reports type given as an arguments Arguments :
	 * Report name i.e. Type of Report you want to open in ICM Due Diligence Return
	 * value : NA Example : openReportTypeInICM_DD_AvgDaysToComplete(String
	 * ReportsName) Author :
	 * =============================================================================
	 * ===============================================
	 */
	public void openReportTypeInICM_DD_AvgDaysToComplete(String ReportsName) {
		try {
			commonLib.click("ReportsPage_AllFolders_Button_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.waitForPresenceOfElementLocated("ReportsPage_ICMReports_Button_XPATH");
			commonLib.clickbyjavascript("ReportsPage_ICMReports_Button_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.waitForPresenceOfElementLocated("ReportsPage_ICMReports__ComplianceReports_Button_XPATH");
			commonLib.clickbyjavascript("ReportsPage_ICMReports__ComplianceReports_Button_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath(
					"ReportsPage_ICMReports__FinanceReports_ReportType_Button_XPATH", ReportsName);
			commonLib.clickbyjavascriptWithDynamicValue(
					"ReportsPage_ICMReports__FinanceReports_ReportType_Button_XPATH", ReportsName);
			Thread.sleep(5 * 1000);
			commonLib.waitforFramenadSwitch("OpenedReports_Iframe_XPATH");

		} catch (Exception e) {
			commonLib.syso("Not able to open the given reports i.e. " + ReportsName);
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * CreateNewFMV_CostOfService(String Region, String SubRegion, String Country,
	 * String Franchise, String Service, String IndirectChannelType, String cost)
	 * Description : It creats New FMV of Cost of Service Type Arguments : String
	 * Region, String SubRegion, String Country, String Franchise, String Service,
	 * String IndirectChannelType, String cost Return value : String Example :
	 * MVCreate_CostOfService(String Region, String SubRegion, String Country,
	 * String Franchise, String Service, String IndirectChannelType, String cost)
	 * Author :
	 * =============================================================================
	 * ===============================================
	 */
	public String CreateNewFMV_CostOfService(String Region, String SubRegion, String Country, String Franchise,
			String Service, String IndirectChannelType, String cost) {
		String FMVNumber = "";
		try {
			commonLib.click("FMV_NewFMVPopUp_NextButton_XPATH");
			commonLib.waitForPresenceOfElementLocated("FMV_NewFMVPopUp_RegionInput_XPATH");
			commonLib.click("FMV_NewFMVPopUp_RegionInput_XPATH");
			Thread.sleep(500);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_Region_FieldDropDown_Values_XPATH",
					Region);
			commonLib.clickWithDynamicValue("FMV_NewFMVPopUp_Region_FieldDropDown_Values_XPATH", Region);
			Thread.sleep(500);
			commonLib.click("FMV_NewFMVPopUp_SubRegionInput_XPATH");
			Thread.sleep(1000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_SubRegion_FieldDropDown_Values_XPATH",
					SubRegion);
			Thread.sleep(1000);
			commonLib.scroll_view_Dynamic("FMV_NewFMVPopUp_SubRegion_FieldDropDown_Values_XPATH", SubRegion);
			Thread.sleep(1000);
			commonLib.clickWithDynamicValue("FMV_NewFMVPopUp_SubRegion_FieldDropDown_Values_XPATH", SubRegion);
			Thread.sleep(500);
			commonLib.click("FMV_NewFMVPopUp_CountryInput_XPATH");
			Thread.sleep(1000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_Country_FieldDropDown_Values_XPATH",
					Country);
			Thread.sleep(1000);
			commonLib.scroll_view_Dynamic("FMV_NewFMVPopUp_Country_FieldDropDown_Values_XPATH", Country);
			Thread.sleep(1000);
			commonLib.clickbyjavascriptWithDynamicValue("FMV_NewFMVPopUp_Country_FieldDropDown_Values_XPATH", Country);
			Thread.sleep(500);
			commonLib.clickbyjavascript("FMV_NewFMVPopUp_FranchiseInput_XPATH");
			Thread.sleep(1000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Franchise);
			Thread.sleep(1000);
			commonLib.scroll_view_Dynamic("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Franchise);
			Thread.sleep(1000);
			commonLib.clickbyjavascriptWithDynamicValue("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Franchise);
			Thread.sleep(500);
			commonLib.scrollDownToElement("FMV_NewFMVPopUp_FranchiseInput_XPATH", "Input");
			Thread.sleep(500);
			commonLib.clickbyjavascript("FMV_NewFMVPopUp_ServiceInput_XPATH");
			Thread.sleep(1000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Service);
			Thread.sleep(1000);
			commonLib.clickbyjavascriptWithDynamicValue("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Service);
			Thread.sleep(500);
			commonLib.clickbyjavascript("FMV_NewFMVPopUp_IndirectChannelTypeInput_XPATH");
			Thread.sleep(500);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH",
					IndirectChannelType);
			commonLib.scroll_view_Dynamic("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", IndirectChannelType);
			Thread.sleep(500);
			commonLib.clickWithDynamicValue("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", IndirectChannelType);
			Thread.sleep(500);
			commonLib.sendKeys("FMV_NewFMVPopUp_CostInput_XPATH", cost);
			commonLib.click("FMV_NewFMVPopUp_SaveButton_XPATH");
			commonLib.waitForPresenceOfElementLocated("FMV_NewFMVServiceCost_CreatedHeading_XPATH");
			FMVNumber = commonLib.getText("FMV_NewFMVServiceCost_CreatedHeading_XPATH");
			return FMVNumber;

		} catch (Exception e) {
			commonLib.syso("FMV not created because of issue:" + e.getMessage());
			return FMVNumber;
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * CreateNewFMV_RangeName(String Region, String SubRegion, String Country,
	 * String Franchise, String IndirectChannelType, String LowRange, String
	 * highRange) Description : It creates New FMV of Cost of Service Type Arguments
	 * : String Region, String SubRegion, String Country, String Franchise, String
	 * Service, String IndirectChannelType, String cost Return value : String
	 * Example : MVCreate_CostOfService(String Region, String SubRegion, String
	 * Country, String Franchise, String Service, String IndirectChannelType, String
	 * cost) Author :
	 * =============================================================================
	 * ===============================================
	 */
	public String CreateNewFMV_RangeName(String Region, String SubRegion, String Country, String Franchise,
			String IndirectChannelType, String LowRange, String highRange) {
		String FMVNumber = "";
		try {
			commonLib.click("FMV_NewFMVPopUp_MarketRange_CheckBox_XPATH");
			Thread.sleep(500);
			commonLib.click("FMV_NewFMVPopUp_NextButton_XPATH");
			commonLib.waitForPresenceOfElementLocated("FMV_NewFMVPopUp_RegionInput_XPATH");
			commonLib.click("FMV_NewFMVPopUp_RegionInput_XPATH");
			Thread.sleep(500);
			Thread.sleep(500);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_Region_FieldDropDown_Values_XPATH",
					Region);
			commonLib.clickWithDynamicValue("FMV_NewFMVPopUp_Region_FieldDropDown_Values_XPATH", Region);
			Thread.sleep(500);
			commonLib.click("FMV_NewFMVPopUp_SubRegionInput_XPATH");
			Thread.sleep(1000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_SubRegion_FieldDropDown_Values_XPATH",
					SubRegion);
			Thread.sleep(1000);
			commonLib.scroll_view_Dynamic("FMV_NewFMVPopUp_SubRegion_FieldDropDown_Values_XPATH", SubRegion);
			Thread.sleep(1000);
			commonLib.clickWithDynamicValue("FMV_NewFMVPopUp_SubRegion_FieldDropDown_Values_XPATH", SubRegion);
			Thread.sleep(500);
			commonLib.click("FMV_NewFMVPopUp_CountryInput_XPATH");
			Thread.sleep(1000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_Country_FieldDropDown_Values_XPATH",
					Country);
			Thread.sleep(1000);
			commonLib.scroll_view_Dynamic("FMV_NewFMVPopUp_Country_FieldDropDown_Values_XPATH", Country);
			Thread.sleep(1000);
			commonLib.clickbyjavascriptWithDynamicValue("FMV_NewFMVPopUp_Country_FieldDropDown_Values_XPATH", Country);
			Thread.sleep(500);
			commonLib.clickbyjavascript("FMV_NewFMVPopUp_FranchiseInput_XPATH");
			Thread.sleep(1000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Franchise);
			Thread.sleep(1000);
			commonLib.scroll_view_Dynamic("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Franchise);
			Thread.sleep(1000);
			commonLib.clickbyjavascriptWithDynamicValue("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Franchise);
			Thread.sleep(500);
			commonLib.scrollDownToElement("FMV_NewFMVPopUp_FranchiseInput_XPATH", "Input");
			Thread.sleep(500);
			commonLib.clickbyjavascript("FMV_NewFMVPopUp_IndirectChannelTypeInput_XPATH");
			Thread.sleep(500);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH",
					IndirectChannelType);
			commonLib.scroll_view_Dynamic("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", IndirectChannelType);
			Thread.sleep(500);
			commonLib.clickWithDynamicValue("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", IndirectChannelType);
			Thread.sleep(500);
			commonLib.sendKeys("FMV_EditScreen_RangeName_LowRange_XPATH", LowRange);
			Thread.sleep(500);
			commonLib.sendKeys("FMV_EditScreen_RangeName_HighRange_XPATH", highRange);
			commonLib.click("FMV_NewFMVPopUp_SaveButton_XPATH");
			commonLib.waitForPresenceOfElementLocated("FMV_NewFMVServiceCost_CreatedHeading_XPATH");
			FMVNumber = commonLib.getText("FMV_NewFMVServiceCost_CreatedHeading_XPATH");
			return FMVNumber;

		} catch (Exception e) {
			commonLib.syso("FMV not created because of issue:" + e.getMessage());
			return FMVNumber;
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * CreateNewFMV_RangeName(String Region, String SubRegion, String Country,
	 * String Franchise, String IndirectChannelType, String LowRange, String
	 * highRange) Description : It is the same method to create FMV of Range type
	 * but we use this when we firstly create the cost type Arguments : String
	 * Region, String SubRegion, String Country, String Franchise, String Service,
	 * String IndirectChannelType, String cost Return value : String Example :
	 * MVCreate_CostOfService(String Region, String SubRegion, String Country,
	 * String Franchise, String Service, String IndirectChannelType, String cost)
	 * Author :
	 * =============================================================================
	 * ===============================================
	 */
	public String CreateNewFMV_RangeName2(String Region, String SubRegion, String Country, String Franchise,
			String IndirectChannelType, String LowRange, String highRange) {
		String FMVNumber = "";
		try {
			commonLib.click("FMV_NewFMVPopUp_MarketRange_CheckBox_XPATH");
			Thread.sleep(500);
			commonLib.click("FMV_NewFMVPopUp_NextButton_XPATH");
			commonLib.waitForPresenceOfElementLocated("FMV_NewFMVPopUp_RegionInput_XPATH");
			commonLib.refreshPage();
			Thread.sleep(5000);
			commonLib.waitForPresenceOfElementLocated("FMV_NewFMVPopUp_RegionInput_XPATH");
			commonLib.clickbyjavascript("FMV_NewFMVPopUp_RegionInput_XPATH");
			Thread.sleep(500);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Region);
			commonLib.clickWithDynamicValue("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Region);
			Thread.sleep(500);
			commonLib.clickbyjavascript("FMV_NewFMVPopUp_SubRegionInput_XPATH");
			Thread.sleep(500);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", SubRegion);
			commonLib.clickWithDynamicValue("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", SubRegion);
			Thread.sleep(500);
			commonLib.clickbyjavascript("FMV_NewFMVPopUp_CountryInput_XPATH");
			Thread.sleep(500);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Country);
			commonLib.scroll_view_Dynamic("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Country);
			Thread.sleep(500);
			commonLib.clickWithDynamicValue("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Country);
			Thread.sleep(500);
			commonLib.clickbyjavascript("FMV_NewFMVPopUp_FranchiseInput_XPATH");
			Thread.sleep(500);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Franchise);
			commonLib.scroll_view_Dynamic("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Franchise);
			commonLib.clickbyjavascriptWithDynamicValue("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", Franchise);
			Thread.sleep(500);
			commonLib.scrollDownToElement("FMV_NewFMVPopUp_FranchiseInput_XPATH", "Input");
			Thread.sleep(500);
			commonLib.clickbyjavascript("FMV_NewFMVPopUp_IndirectChannelTypeInput_XPATH");
			Thread.sleep(500);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH",
					IndirectChannelType);
			commonLib.scroll_view_Dynamic("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", IndirectChannelType);
			Thread.sleep(500);
			commonLib.clickWithDynamicValue("FMV_NewFMVPopUp_FieldDropDown_Values_XPATH", IndirectChannelType);
			Thread.sleep(500);
			commonLib.sendKeys("FMV_EditScreen_RangeName_LowRange_XPATH", LowRange);
			Thread.sleep(500);
			commonLib.sendKeys("FMV_EditScreen_RangeName_HighRange_XPATH", highRange);
			commonLib.click("FMV_NewFMVPopUp_SaveButton_XPATH");
			commonLib.waitForPresenceOfElementLocated("FMV_NewFMVServiceCost_CreatedHeading_XPATH");
			FMVNumber = commonLib.getText("FMV_NewFMVServiceCost_CreatedHeading_XPATH");
			return FMVNumber;

		} catch (Exception e) {
			commonLib.syso("FMV not created because of issue:" + e.getMessage());
			return FMVNumber;
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * navigate_To_Existing_FMV_From_Global_Search(FMVNumber) Description : It
	 * allows user to traverse the existing FMV case through global search Arguments
	 * : FMV number Return value : NA Example :
	 * navigate_To_Existing_FMV_From_Global_Search("FMX-000227");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void navigate_To_Existing_FMV_From_Global_Search(String FMVNumber)
			throws AWTException, InterruptedException {

		commonLib.click("GlobalSearch_Homepage_XPATH");
		Thread.sleep(2000);

		commonLib.clear("Global_Search_XPATH");
		commonLib.click("Global_Search_XPATH");
		Thread.sleep(1000);
		commonLib.sendKeys("Global_Search_XPATH", FMVNumber);
		Thread.sleep(2000);
		commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
		boolean FMV_Results_Table_Exist = commonLib
				.waitForVisibilityOf_DynamicXpath("FMV_Results_Table_In_Home_Page_XPATH", FMVNumber);

		if (FMV_Results_Table_Exist) {

			commonLib.syso("FMV result is coming successfully");
		} else {
			commonLib.log(LogStatus.FAIL, "Not able to search the given FMV number i.e. :" + FMVNumber);
		}

		commonLib.clickbyjavascriptWithDynamicValue("FMV_Results_Table_In_Home_Page_XPATH", FMVNumber);

		commonLib.waitForPresenceOfElementLocated("FMV_NewFMVServiceCost_CreatedHeading_XPATH");

		String Actual_FMVNumber = commonLib.getText("FMVDetail_HeaderText_FMVName_XPATH");
		commonLib.syso(FMVNumber);
		commonLib.syso(Actual_FMVNumber);
		if (FMVNumber.equals(Actual_FMVNumber)) {

			commonLib.syso("FMV Number ='" + FMVNumber + "' page is open");
			Thread.sleep(8 * 1000);
			commonLib.getScreenshot();

		} else {
			commonLib.syso("FMVNumber = " + FMVNumber + "' page is not open");

		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * navigate_To_Existing_FMV_From_Global_SearchWithReload(FMVNumber) Description
	 * : It allows user to traverse the existing FMV case through global search and
	 * we refresh the screen in this Arguments : FMV number Return value : NA
	 * Example : navigate_To_Existing_FMV_From_Global_Search("FMX-000227");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void navigate_To_Existing_FMV_From_Global_SearchWithReload(String FMVNumber)
			throws AWTException, InterruptedException {

		commonLib.sendKeys("GlobalSearch_Homepage_XPATH", FMVNumber);
		Thread.sleep(2000);

		commonLib.click("GlobalSearch_Homepage_XPATH");
		Thread.sleep(2 * 1000);
		commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
		Thread.sleep(2 * 1000);
		commonLib.refreshPage();
		Thread.sleep(5000);
		commonLib.WaitforPresenceofElement_Dynamic_Xpath("FMV_SearchResult_FMVNumberLink_XPATH", FMVNumber);
		boolean FMV_Results_Table_Exist = commonLib
				.waitForVisibilityOf_DynamicXpath("FMV_Results_Table_In_Home_Page_XPATH", FMVNumber);

		if (FMV_Results_Table_Exist) {

			commonLib.syso("FMV result is coming successfully");
		} else {
			commonLib.log(LogStatus.FAIL, "Not able to search the given FMV number i.e. :" + FMVNumber);
		}

		commonLib.clickbyjavascriptWithDynamicValue("FMV_Results_Table_In_Home_Page_XPATH", FMVNumber);

		commonLib.waitForPresenceOfElementLocated("FMV_NewFMVServiceCost_CreatedHeading_XPATH");

		String Actual_FMVNumber = commonLib.getText("FMVDetail_HeaderText_FMVName_XPATH");
		commonLib.syso(FMVNumber);
		commonLib.syso(Actual_FMVNumber);
		if (FMVNumber.equals(Actual_FMVNumber)) {

			commonLib.syso("FMV Number ='" + FMVNumber + "' page is open");
			Thread.sleep(8 * 1000);
			commonLib.getScreenshot();

		} else {
			commonLib.syso("FMVNumber = " + FMVNumber + "' page is not open");

		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * navigate_To_Existing_Contact_From_Global_Search(contactName) Description : It
	 * allows user to traverse the existing Contact through global search Arguments
	 * : Contact name Return value : NA Example :
	 * navigate_To_Existing_Contact_From_Global_Search("FirstName LastName");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public boolean navigate_To_Existing_Contact_From_Global_Search(String ContactName)
			throws AWTException, InterruptedException {
		boolean status = false;

		try {

			commonLib.sendKeys("GlobalSearch_Homepage_XPATH", ContactName);
			Thread.sleep(2000);

			commonLib.click("GlobalSearch_Homepage_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
			boolean FMV_Results_Table_Exist = commonLib
					.waitForVisibilityOf_DynamicXpath("Contact_Results_Table_In_Home_Page_XPATH", ContactName);

			if (FMV_Results_Table_Exist) {

				commonLib.syso("ContactName result is coming successfully");
			} else {
				commonLib.log(LogStatus.FAIL, "Not able to search the given Contact Name i.e. :" + ContactName);
			}

			commonLib.clickbyjavascriptWithDynamicValue("Contact_Results_Table_In_Home_Page_XPATH", ContactName);

			commonLib.waitForPresenceOfElementLocated("ContactDetail_HeadingText_ContactName_XPATH");

			String Actual_ContactName = commonLib.getText("ContactDetail_HeadingText_ContactName_XPATH");
			commonLib.syso(Actual_ContactName);
			commonLib.syso(ContactName);
			if (ContactName.equals(Actual_ContactName)) {

				commonLib.syso("Contact name ='" + ContactName + "' page is open");
				Thread.sleep(8 * 1000);
				status = true;
				commonLib.getScreenshot();

			} else {
				commonLib.syso("Contact Name = " + ContactName + "' page is not open");

			}

		} catch (Exception e) {
			commonLib.syso("Not able to open Contact screen : " + e.getMessage());
		}
		return status;

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * navigate_To_ContactSearchResult_From_Global_Search(contactName) Description :
	 * It allows user to traverse the existing Contact search result table through
	 * global search Arguments : Contact name Return value : NA Example :
	 * navigate_To_ContactSearchResult_From_Global_Search("FirstName LastName");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public boolean navigate_To_ContactSearchResult_From_Global_Search(String ContactName)
			throws AWTException, InterruptedException {
		boolean status = false;

		try {

			// commonLib.sendKeys("GlobalSearch_Homepage_XPATH", ContactName);
			// Thread.sleep(2000);
			//
			// commonLib.click("GlobalSearch_Homepage_XPATH");
			// Thread.sleep(2*1000);

			commonLib.click("GlobalSearch_Homepage_XPATH");
			Thread.sleep(2000);

			commonLib.clear("Global_Search_XPATH");
			commonLib.click("Global_Search_XPATH");
			Thread.sleep(1000);
			commonLib.sendKeys("Global_Search_XPATH", ContactName);
			Thread.sleep(3000);

			commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
			boolean FMV_Results_Table_Exist = commonLib
					.waitForVisibilityOf_DynamicXpath("Contact_Results_Table_In_Home_Page_XPATH", ContactName);

			if (FMV_Results_Table_Exist) {

				commonLib.syso("ContactName result is coming successfully");
				status = true;
			} else {
				commonLib.syso("Not able to get the Search result for given Contact Name.");
			}

		} catch (Exception e) {
			commonLib.syso("Not able to get the Search result for given Contact Name: " + e.getMessage());
		}
		return status;

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Create_New_Contact_FromContactScreen_WithContactType(Contact data)
	 * Description : It creates a new Contact from Contact screen with Contact type
	 * information as well. Arguments :String email, String LastName,String
	 * Firstname, String jobTitle, String mobileNumber, String contacttype Return
	 * value : NA Example : Create_New_Contact_FromContactScreen(String email,
	 * String LastName,String Firstname, String jobTitle, String mobileNumber,
	 * String cotacttype)
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void Create_New_Contact_FromContactScreen_WithContactType(String email, String LastName, String Phone,
			String Firstname, String jobTitle, String mobileNumber, String ContactType)
			throws IOException, InterruptedException {

		try {
			commonLib.waitForPresenceOfElementLocated("NewContact_Button_Account_XPATH");
			commonLib.click("NewContact_Button_Account_XPATH");
			Thread.sleep(5 * 1000);
			/*
			 * if(commonLib.verifyPresence("NewContact_ICContact_Checkbox_XPATH")) {
			 * commonLib.waitForPresenceOfElementLocated(
			 * "NewContact_ICContact_Checkbox_XPATH");
			 * commonLib.click("NewContact_ICContact_Checkbox_XPATH");
			 * commonLib.click("NewContactPopUp_NextButton_XPATH");
			 * commonLib.waitForPresenceOfElementLocated("NewContactPopUp_EmailInput_XPATH")
			 * ; commonLib.sendKeys("NewContactPopUp_JobTitle_XPATH", jobTitle);
			 * Thread.sleep(500);
			 * 
			 * }
			 */

			commonLib.waitForPresenceOfElementLocated("NewContactPopUp_EmailInput_XPATH");
			commonLib.sendKeys("NewContactPopUp_EmailInput_XPATH", email);
			Thread.sleep(500);
			commonLib.sendKeys("NewContactPopUp_LastNameInput_XPATH", LastName);
			Thread.sleep(500);
			commonLib.sendKeys("NewContactPopUp_FirstName_XPATH", Firstname);
			Thread.sleep(500);
			commonLib.clear("NewContactPopUp_Phone_XPATH");
			Thread.sleep(500);
			commonLib.sendKeys("NewContactPopUp_Phone_XPATH", Phone);
			Thread.sleep(500);
			commonLib.sendKeys("NewContactPopUp_JobTitle_XPATH", jobTitle);
			Thread.sleep(500);
			commonLib.sendKeys("NewContactPopUp_Mobile_XPATH", mobileNumber);
			Thread.sleep(500);
			commonLib.scrollDownToElement("NewContactPopUp_LastNameInput_XPATH", "TextBox");
			Thread.sleep(500);
			commonLib.click("NewContactPopUp_ContactType_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("NewContactPopUp_ContactType_DropDownValue_XPATH",
					ContactType);
			commonLib.clickbyjavascriptWithDynamicValue("NewContactPopUp_ContactType_DropDownValue_XPATH", ContactType);
			commonLib.click("NewContactPopUp_PreferredLanguage_XPATH");
			commonLib.waitForPresenceOfElementLocated("NewContactPopUp_PreferredLanguage_DropDownValue_XPATH");
			commonLib.clickbyjavascript("NewContactPopUp_PreferredLanguage_DropDownValue_XPATH");

			commonLib.click("NewContactPopUp_Save_XPATH");
			commonLib.waitForPresenceOfElementLocated("NewContact_ContactCreationMessage_XPATH");
			Thread.sleep(5 * 1000);
			commonLib.syso("New Contact is successfully created");

		} catch (Exception e) {
			commonLib.syso("New Contact is not created successfully with Error");
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * openReportTypeInICM_Forms_AvgDaysToComplete(Reports Name) Description : This
	 * method is used to open Reports in Forms for any type of reports Arguments :
	 * Report name i.e. Type of Report you want to open in ICM Forms Return value :
	 * NA Example : openReportTypeInICM_Forms_AvgDaysToComplete(String ReportsName)
	 * Author :
	 * =============================================================================
	 * ===============================================
	 */
	public void openReportTypeInICM_Forms_AvgDaysToComplete(String FirstFolder, String SecondFolder, String ThirdFolder,
			String ReportsName) {
		try {
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("ReportsPage_FirstFolder_Button_XPATH", FirstFolder);
			commonLib.clickWithDynamicValue("ReportsPage_FirstFolder_Button_XPATH", FirstFolder);
			Thread.sleep(2 * 1000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("ReportsPage_ICMReports__SecondFolder_Button_XPATH",
					SecondFolder);
			commonLib.clickbyjavascriptWithDynamicValue("ReportsPage_ICMReports__SecondFolder_Button_XPATH",
					SecondFolder);
			Thread.sleep(2 * 1000);
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("ReportsPage_ICMReports__ThirdFolder_Button_XPATH",
					ThirdFolder);
			commonLib.clickbyjavascriptWithDynamicValue("ReportsPage_ICMReports__ThirdFolder_Button_XPATH",
					ThirdFolder);
			Thread.sleep(2 * 1000);
			if (ReportsName.contains("grouped by Region and Country")) {
				commonLib.WaitforPresenceofElement_Dynamic_Xpath("ReportsPage_ICMReports_ReportType1_Button_XPATH",
						ReportsName);
				commonLib.clickbyjavascriptWithDynamicValue("ReportsPage_ICMReports_ReportType1_Button_XPATH",
						ReportsName);
			} else {
				commonLib.WaitforPresenceofElement_Dynamic_Xpath("ReportsPage_ICMReports_ReportType_Button_XPATH",
						ReportsName);
				commonLib.clickbyjavascriptWithDynamicValue("ReportsPage_ICMReports_ReportType_Button_XPATH",
						ReportsName);
			}
			Thread.sleep(5 * 1000);
			commonLib.waitforFramenadSwitch("OpenedReports_Iframe_XPATH");

		} catch (Exception e) {
			commonLib.syso("Not able to open the given reports i.e. " + ReportsName);
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * navigate_To_Existing_Report_From_Global_Search(reportName) Description : It
	 * allows user to traverse the existing Report from the global search Arguments
	 * : Report Name Return value : NA Example :
	 * navigate_To_Existing_Report_From_Global_Search("Report Name");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void navigate_To_Existing_Report_From_Global_Search(String ReportName)
			throws AWTException, InterruptedException {

		// commonLib.sendKeys("GlobalSearch_Homepage_XPATH", ReportName);
		// Thread.sleep(2000);
		// commonLib.click("GlobalSearch_Homepage_XPATH");

		commonLib.waitForElementToBeClickable("GlobalSearch_Homepage_XPATH");
		commonLib.clickbyjavascript("GlobalSearch_Homepage_XPATH");
		Thread.sleep(2000);
		commonLib.clear("Global_Search_XPATH");
		commonLib.sendKeys("Global_Search_XPATH", ReportName);

		Thread.sleep(2 * 1000);
		commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
		boolean Report_Results_Table_Exist = commonLib
				.waitForVisibilityOf_DynamicXpath("Reports_Results_Table_In_Home_Page_XPATH", ReportName);

		if (Report_Results_Table_Exist) {

			commonLib.syso("Report result is coming successfully");
		} else {
			commonLib.log(LogStatus.FAIL, "Not able to search the given Report i.e. :" + ReportName);
		}

		commonLib.clickbyjavascriptWithDynamicValue("Reports_Results_Table_In_Home_Page_XPATH", ReportName);

		commonLib.waitforFramenadSwitch("OpenedReports_Iframe_XPATH");

		commonLib.WaitforPresenceofElement_Dynamic_Xpath("OpenedReport_Header_Text_XPATH", ReportName);

	}
	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Create_New_Account_From_Accounts_Page_Simple Description : It creates new
	 * account Arguments : LegalName, AccountName, RegionName, SubRegionName,
	 * CountryOfOperation Return value : boolean Example :
	 * Create_New_Account_From_Accounts_Page_Simple(accountName,countryOfOperation,
	 * ServicesProvided);//Generic Method to create Account.
	 * Create_New_Account_From_Accounts_Page_Simple("ICACCTest","Hong Kong"
	 * ,"Sales Generation")
	 * =============================================================================
	 * ===============================================
	 */

	public boolean Create_New_Account_From_Accounts_Page_Simple(String legalName, String AccountName, String RegionName,
			String SubRegion, String CountryofOperation) throws IOException, InterruptedException {
		boolean flag = false;
		try {
			open_Given_Tab_In_Home_Page("Accounts");
			commonLib.waitForPresenceOfElementLocated("AccountPageListing_NewAccount_Button_XPATH");
			commonLib.clickbyjavascript("AccountPageListing_NewAccount_Button_XPATH");
			commonLib.waitForPresenceOfElementLocated("AccountPage_NewAccountScreen_LegalName_XPATH");
			commonLib.sendKeys("AccountPage_NewAccountScreen_LegalName_XPATH", legalName);
			Thread.sleep(2000);
			commonLib.sendKeys("AccountPage_NewAccountScreen_AccountNameTextBox_XPATH", AccountName);
			Thread.sleep(500);
			commonLib.click("AccountPage_NewAccountScreen_LegalName_XPATH");
			Thread.sleep(500);
			commonLib.click("AccountPage_NewAccountScreen_RegionDropdown_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("AccountPage_NewAccount_DropDownValues_Selection_XPATH",
					RegionName);
			commonLib.clickbyjavascriptWithDynamicValue("AccountPage_NewAccount_DropDownValues_Selection_XPATH",
					RegionName);
			Thread.sleep(500);
			commonLib.click("AccountPage_NewAccountScreen_SubRegionDropdown_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("AccountPage_NewAccount_DropDownValues_Selection_XPATH",
					SubRegion);
			commonLib.clickbyjavascriptWithDynamicValue("AccountPage_NewAccount_DropDownValues_Selection_XPATH",
					SubRegion);
			Thread.sleep(500);
			commonLib.click("AccountPage_NewAccountScreen_CountryOfOperationDropdown_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("AccountPage_NewAccount_DropDownValues_Selection_XPATH",
					CountryofOperation);
			commonLib.clickbyjavascriptWithDynamicValue("AccountPage_NewAccount_DropDownValues_Selection_XPATH",
					CountryofOperation);
			Thread.sleep(500);
			commonLib.click("AccountPage_NewAccount_SaveButton_XPATH");
			commonLib.WaitforPresenceofElement_Dynamic_Xpath("AccountPage_CreatedAccount_AccountNameHeading_XPATH",
					AccountName);
			if (commonLib.verifyPresence_Dynamic_Xpath("AccountPage_CreatedAccount_AccountNameHeading_XPATH",
					AccountName)) {
				flag = true;
			}
			return flag;

		} catch (Exception e) {
			commonLib.syso("Not able to Create the Given Account i.e. " + AccountName);
			commonLib.getScreenshot();
			return flag;
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * verify_n_ClickViewReport_FromDashboard(reportsName) Description : It verifies
	 * the report from Landing page and click on View report button for that report.
	 * Arguments : ReportsName Return value : boolean Example :
	 * verify_n_ClickViewReport_FromDashboard(reportsname)
	 * =============================================================================
	 * ===============================================
	 */

	public boolean verify_n_ClickViewReport_FromDashboard(String reportsName) {
		boolean flag = false;
		try {
			if (commonLib.verifyPresence_Dynamic_Xpath("HomePage_DashboardTiles_Text_XPATH", reportsName)) {
				commonLib.scroll_view_Dynamic("HomePage_DashboardTiles_Text_XPATH", reportsName);
				commonLib.log(LogStatus.PASS, "Step 2:User Should be able to view the report i.e. " + reportsName
						+ " chart on landing page.");
				commonLib.KeyPress_UpKey();
				commonLib.KeyPress_UpKey();
				commonLib.getScreenshot();
			}
			commonLib.clickbyjavascriptWithDynamicValue("HomePage_ReportsTile_ViewReportButton_XPATH", reportsName);
			commonLib.waitForElementTimeOut("BRM_Report_Iframe_XPATH", 3000);
			Thread.sleep(2000);
			flag = true;

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Not able to Report name i.e." + reportsName);
			commonLib.syso("Not able to Report name i.e." + reportsName);

		}
		return flag;

	}

	public boolean verifyReportColumns(List<String> columnNameList) {
		boolean check = true;
		try {
			commonLib.waitForElementTimeOut("BRM_Report_Iframe_XPATH", 3000);
			commonLib.switchtoFrame("BRM_Report_Iframe_XPATH");
			for (String columnName : columnNameList) {
				if (commonLib.verifyPresence_Dynamic_Xpath("LRP_Column_Check_XPATH", columnName)) {
					commonLib.syso("Column: " + columnName + "  is visible in the Report");
				} else {
					commonLib.log(LogStatus.FAIL, "Column: " + columnName + "  is not visible in the Report");
					check = false;
				}
			}
			commonLib.switchToDefaultContent();
		} catch (Exception e) {
			e.printStackTrace();
			commonLib.log(LogStatus.FAIL, "Verify Columns method has failed. See Stack trace: " + " " + e.toString());
		}
		return check;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Edit_SupportCase_FromDetailPage_WithOutlinerStatus(String
	 * issueCategory,String issueSubCategory,String Status,String
	 * OutlinerStatus,String closeNotes) Description : In this we are editing the
	 * Support case in covering the scenario when we status as closed so Arguments :
	 * ReportsName Return value : boolean Example :
	 * Edit_SupportCase_FromDetailPage_WithOutlinerStatus(String
	 * issueCategory,String issueSubCategory,String Status,String
	 * OutlinerStatus,String closeNotes)
	 * =============================================================================
	 * ===============================================
	 */
	public void Edit_SupportCase_FromDetailPage_WithOutlinerStatus(String issueCategory, String issueSubCategory,
			String Status, String OutlinerStatus, String closeNotes) throws IOException, InterruptedException {

		try {

			commonLib.syso("Click 'Edit' button");
			commonLib.waitForPresenceOfElementLocated("SupportCaseDetail_EditButton_UAT_XPATH");
			commonLib.click("SupportCaseDetail_EditButton_UAT_XPATH");
			commonLib.waitForPresenceOfElementLocated("IssuesCategories_Dropdown_In_NewSupportCase_XPATH");
			Thread.sleep(1000);
			commonLib.syso("Issue Category:" + issueCategory);
			commonLib.click("IssuesCategories_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH", issueCategory);
			Thread.sleep(1000);
			commonLib.syso("Issue Sub Category:" + issueSubCategory);
			commonLib.click("IssueSubcategory_Dropdown_In_NewSupportCase_XPATH");
			commonLib.clickWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH", issueSubCategory);
			Thread.sleep(1000);
			commonLib.click("IssueStatus_Dropdown_NewSupportCase_XPATH");
			commonLib.syso("Issue Status :" + Status);
			commonLib.clickWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH", Status);
			Thread.sleep(1000);
			commonLib.clear("CloseNotes_Editbox_In_NewSupportCase_XPATH");
			commonLib.syso("Close Notes in Remediation:" + closeNotes);
			commonLib.sendKeys("CloseNotes_Editbox_In_NewSupportCase_XPATH", closeNotes);
			Thread.sleep(1000);
			if (OutlinerStatus.length() > 1) {
				commonLib.click("OutlinerStatus_Dropdown_In_EditSupportCase_XPATH");
				commonLib.clickWithDynamicValue("OutlinerCategories_DropdownMenu_In_EditSupportCase_XPATH",
						OutlinerStatus);
			}
			Thread.sleep(1000);
			commonLib.waitForPresenceOfElementLocated("Save_Button_In_NewSupportCase_Page_XPATH");
			commonLib.syso("Click on Save after filling all the details");
			commonLib.click("Save_Button_In_NewSupportCase_Page_XPATH");
			commonLib.waitforInvisibilityOfElement("SupportCaseEdit_HeaderText_XPATH");
			commonLib.waitForVisibilityOf("SupportCaseDetail_EditButton_UAT_XPATH");

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Failed.Not able to Edit Support Case From Support cases page");
			commonLib.getScreenshot();
			e.printStackTrace();
		}

	}
	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * Create_New_Support_Case_From_FromAccountPage(String
	 * Select_Record_Type_value,String priority,String issueCategory,String
	 * issueSubCategory,String subject,String description,String action,String
	 * dueDate,String closeNotes) Description : In this we are creating the support
	 * case from account page. Arguments : ReportsName Return value : boolean
	 * Example : Create_New_Support_Case_From_FromAccountPage(String
	 * Select_Record_Type_value,String priority,String issueCategory,String
	 * issueSubCategory,String subject,String description,String action,String
	 * dueDate,String closeNotes)
	 * =============================================================================
	 * ===============================================
	 */

	public String Create_New_Support_Case_From_FromAccountPage(String Select_Record_Type_value, String priority,
			String Status, String issueCategory, String issueSubCategory, String subject, String description,
			String action, String dueDate, String closeNotes) throws IOException, InterruptedException {
		String supportCaseNumber = null;
		try {

			commonLib.clickbyjavascript("Account_Cases_XPATH");
			Thread.sleep(3000);
			commonLib.clickbyjavascript("AccountDetail_Page_NewSupportCaseButton_XPATH");
			Thread.sleep(2000);
			// commonLib.log(LogStatus.PASS, "Click 'New' button");
			commonLib.syso("Select the 'Select a Record Type'as IC case and click on Next button ");
			commonLib.selectDropdownVisibleText("Selecta_RecordType_Dropdown_In_NewCase_Page_XPATH",
					Select_Record_Type_value);

			commonLib.click("Next_Button_In_New_Case_Page_XPATH");

			Boolean is_NewSupportCase_Open = commonLib
					.waitForPresenceOfElementLocated("Priority_Dropdown_In_NewSupportCase_XPATH");
			if (is_NewSupportCase_Open) {
				commonLib.syso("Pop up form with all the required details for Support case creation should be opened.");
			} else {
				commonLib.log(LogStatus.FAIL,
						"Pop up form with all the required details for Support case creation is not opened.");
			}

			// Validate the Account Name should be prepopulated on the Support case creation
			// screen //???? account name field is not exist

			commonLib.syso("Enter below Mandatory fields: ");
			commonLib.syso("Priority :" + priority);
			Thread.sleep(2000);
			if (priority.length() > 1) {
				commonLib.click("Priority_Dropdown_In_NewSupportCase_XPATH");
				;
				commonLib.clickWithDynamicValue("Priority_DropdownMenu_In_NewSupportCase_XPATH", priority);

			}
			commonLib.syso("Status :" + Status);
			Thread.sleep(2000);
			if (Status.length() > 1) {
				commonLib.click("Status_Dropdown_In_NewSupportCase_XPATH");
				;
				commonLib.clickWithDynamicValue("Status_DropdownMenu_In_NewSupportCase_XPATH", Status);

			}

			commonLib.syso("Issue Category:" + issueCategory);
			if (issueCategory.length() > 1) {
				commonLib.click("IssuesCategories_Dropdown_In_NewSupportCase_XPATH");
				commonLib.clickWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH", issueCategory);

			}
			commonLib.syso("Issue Sub Category:" + issueSubCategory);
			if (issueSubCategory.length() > 1) {
				commonLib.click("IssueSubcategory_Dropdown_In_NewSupportCase_XPATH");
				commonLib.clickWithDynamicValue("IssuesCategories_DropdownMenu_In_NewSupportCase_XPATH",
						issueSubCategory);

			}

			commonLib.syso("Subject: " + subject);
			commonLib.sendKeys("Subject_Editbox_In_DescriptionInformation_Section_In_New_Support_Case_Page_XPATH",
					subject);

			commonLib.syso("Description: " + description);
			commonLib.sendKeys("Description_Editbox_In_DescriptionInformation_Section_In_New_Support_Case_Page_XPATH",
					description);

			commonLib.syso("Action:" + action);
			if (action.length() > 1) {
				commonLib.click("Action_Dropdown_In_NewSupportCase_XPATH");
				commonLib.clickWithDynamicValue("Action_DropdownMenu_In_NewSupportCase_XPATH", action);

			}

			commonLib.syso("Due Date:" + dueDate);
			if (dueDate.length() > 1) {
				commonLib.sendKeys("DueDate_Editbox_In_NewSupportCase_XPATH", dueDate);
			}

			commonLib.syso("Close Notes in Remediation:" + closeNotes);
			if (closeNotes.length() > 1) {
				commonLib.sendKeys("CloseNotes_Editbox_In_NewSupportCase_XPATH", closeNotes);
			}

			commonLib.waitForPresenceOfElementLocated("Save_Button_In_NewSupportCase_Page_XPATH");

			commonLib.syso("Click on Save after filling all the details");
			commonLib.click("Save_Button_In_NewSupportCase_Page_XPATH");

			supportCaseNumber = commonLib.getText("SupportCaseNumber_Text_In_SupportCase_Page_XPATH");
			commonLib.syso("Captured Support Case ID:" + supportCaseNumber);
			Thread.sleep(8 * 1000);

			return supportCaseNumber;
		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "Failed.Not able to create New Support Case From Account Detail page.");
			e.printStackTrace();

		}
		return supportCaseNumber;
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * navigate_To_Existing_ChannelMargin_OneOffAdjustmentType_From_Global_Search(
	 * channelMarginNumber) Description : It allows user to traverse the existing
	 * channelMargin case through global search Arguments : channel Margin number
	 * Return value : NA Example :
	 * navigate_To_Existing_ChannelMargin_OneOffAdjustmentType_From_Global_Search(
	 * "FMX-000227");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void navigate_To_Existing_ChannelMargin_OneOffAdjustmentType_From_Global_Search(String channelMarginNumber)
			throws AWTException, InterruptedException {

		try {
			commonLib.sendKeys("GlobalSearch_Homepage_XPATH", channelMarginNumber);
			Thread.sleep(2000);

			commonLib.click("GlobalSearch_Homepage_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
			boolean channelMargin_Results_Table_Exist = commonLib.waitForVisibilityOf_DynamicXpath(
					"ChannelMargin_Results_Table_In_Home_Page_XPATH", channelMarginNumber);

			if (channelMargin_Results_Table_Exist) {

				commonLib.syso("Channel Margin result is coming successfully");
			} else {
				commonLib.log(LogStatus.FAIL,
						"Not able to search the given Channel margin number i.e. :" + channelMarginNumber);
			}

			commonLib.clickbyjavascriptWithDynamicValue("ChannelMargin_Results_Table_In_Home_Page_XPATH",
					channelMarginNumber);

			commonLib.waitForPresenceOfElementLocated(
					"NewChannelMargin_OneOffAdjustment_Header_ChannelMarginNumber_XPATH");

			String Actual_channelMarginNumber = commonLib
					.getText("NewChannelMargin_OneOffAdjustment_Header_FMXValue_ChannelMarginNumber_XPATH");
			commonLib.syso(channelMarginNumber);
			commonLib.syso(Actual_channelMarginNumber);
			if (channelMarginNumber.equals(Actual_channelMarginNumber)) {

				commonLib.syso("channelMarginNumber ='" + channelMarginNumber + "' page is open");
				Thread.sleep(8 * 1000);

			} else {
				commonLib.syso("channelMarginCase = " + channelMarginNumber + "' page is not open");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * navigate_To_Existing_Contact_From_Global_Search2(contactName) Description :
	 * It allows user to traverse the existing Contact through global search
	 * Arguments : Contact name Return value : NA Example :
	 * navigate_To_Existing_Contact_From_Global_Search2("FirstName LastName");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public boolean navigate_To_Existing_Contact_From_Global_Search2(String ContactName)
			throws AWTException, InterruptedException {
		boolean status = false;

		try {

			commonLib.sendKeys("GlobalSearch_Homepage_XPATH", ContactName);
			Thread.sleep(2000);
			commonLib.click("GlobalSearch_Homepage_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
			boolean FMV_Results_Table_Exist = commonLib
					.waitForVisibilityOf_DynamicXpath("Contact_Results_Table_In_Home_Page_XPATH", ContactName);

			if (FMV_Results_Table_Exist) {

				commonLib.syso("ContactName result is coming successfully");
			} else {
				commonLib.log(LogStatus.FAIL, "Not able to search the given Contact Name i.e. :" + ContactName);
			}

			commonLib.clickbyjavascriptWithDynamicValue("Contact_Results_Table_In_Home_Page_XPATH", ContactName);

			commonLib.WaitforPresenceofElement_Dynamic_Xpath("ContactDetail_HeadingText_ContactName_XPATH",
					ContactName);
			String Actual_ContactName = commonLib.getText("ContactDetail_HeadingText_ContactName_XPATH", ContactName);
			commonLib.syso(Actual_ContactName);
			commonLib.syso(ContactName);
			if (Actual_ContactName.contains(ContactName)) {

				commonLib.syso("Contact name ='" + ContactName + "' page is open");
				Thread.sleep(8 * 1000);
				status = true;
				commonLib.getScreenshot();

			} else {
				commonLib.syso("Contact Name = " + ContactName + "' page is not open");

			}

		} catch (Exception e) {
			commonLib.syso("Not able to open Contact screen : " + e.getMessage());
		}
		return status;

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * navigate_To_Existing_Contact_From_Global_Search2(contactName) Description :
	 * It allows user to traverse the existing Contact through global search
	 * Arguments : Contact name Return value : NA Example :
	 * navigate_To_Existing_Contact_From_Global_Search2("FirstName LastName");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public void changeLanguage_To_English() throws AWTException, InterruptedException {
		try {

			commonLib.click("CommunityPortal_ProfileName_XPATH");
			commonLib.waitForPresenceOfElementLocated("CommunityPortal_ProfileName_MySettings_ChangeToEnglish_XPATH");
			commonLib.click("CommunityPortal_ProfileName_MySettings_ChangeToEnglish_XPATH");
			commonLib.waitForPresenceOfElementLocated(
					"CommunityPortal_ProfileName_MySettings_LanguageDropdown_WithID_XPATH");
			Thread.sleep(2000);
			commonLib.click("CommunityPortal_ProfileName_MySettings_LanguageDropdown_WithID_XPATH");
			Thread.sleep(1000);
			commonLib.clickWithDynamicValue("CommunityPortal_ProfileName_MySettings_LanguageDropdownValue_XPATH",
					"English (US)");
			Thread.sleep(1000);
			commonLib.click("CommunityPortal_ProfileName_MySettings_SaveButton_ChangeToEnglish_XPATH");
			Thread.sleep(8000);
			commonLib.refreshPage();
			commonLib.waitForPresenceOfElementLocated("CommunityPortal_FormsLink_XPATH");

			int i = 0;
			while (i < 3) {
				if (!commonLib.verifyPresence("CommunityPortal_FormsLink_XPATH")) {
					commonLib.refreshPage();
					commonLib.waitForPresenceOfElementLocated("CommunityPortal_FormsLink_XPATH");
					i++;

				} else
					break;
			}
			Thread.sleep(8000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * =============================================================================
	 * =============================================== Function Name :
	 * navigate_To_Existing_IntakeForm_From_Global_Search(FormNumber) Description :
	 * It allows user to traverse the existing Form through global search Arguments
	 * : FormNumber Return value : NA Example :
	 * navigate_To_Existing_IntakeForm_From_Global_Search("FormNumber");
	 * 
	 * =============================================================================
	 * ===============================================
	 */
	public boolean navigate_To_Existing_IntakeForm_From_Global_Search(String FormNumber)
			throws AWTException, InterruptedException {
		boolean status = false;

		try {

			commonLib.sendKeys("GlobalSearch_Homepage_XPATH", FormNumber);
			Thread.sleep(2000);
			commonLib.click("GlobalSearch_Homepage_XPATH");
			Thread.sleep(2 * 1000);
			commonLib.KeyPress_Enter_On_Currently_Focussed_Element_Using_Actions_Class();
			boolean Form_Results_Table_Exist = commonLib
					.waitForVisibilityOf_DynamicXpath("Form_Results_Table_In_Home_Page_XPATH", FormNumber);

			if (Form_Results_Table_Exist) {

				commonLib.syso("Form result is coming successfully");
			} else {
				commonLib.log(LogStatus.FAIL, "Not able to search the given Form Name i.e. :" + FormNumber);
			}

			commonLib.clickbyjavascriptWithDynamicValue("Form_Results_Table_In_Home_Page_XPATH", FormNumber);

			commonLib.WaitforPresenceofElement_Dynamic_Xpath("FormDetail_HeadingText_FormName_XPATH", FormNumber);
			String Actual_FormName = commonLib.getText("FormDetail_HeadingText_FormName_XPATH", FormNumber);
			commonLib.syso(Actual_FormName);
			commonLib.syso(FormNumber);
			if (Actual_FormName.contains(FormNumber)) {

				commonLib.syso("Contact name ='" + FormNumber + "' page is open");
				Thread.sleep(8 * 1000);
				status = true;
				commonLib.getScreenshot();

			} else {
				commonLib.syso("Contact Name = " + FormNumber + "' page is not open");

			}

		} catch (Exception e) {
			commonLib.syso("Not able to open FormNumber : " + e.getMessage());
		}
		return status;

	}

	/*
	 * =============================================================================
	 * =============================================== Function Name : Description :
	 * Arguments : Return value : Example : Author :
	 * =============================================================================
	 * ===============================================
	 */

	// *******************************************************************************************************

	// *******************************************************************************************************
	public String preIntake_MasterForm_Approval(List<String> lstRowData, List<String> headerRow, String workBook,
			String workSheet, String FormNumber, GximProfile gximProfile) throws Exception {

		commonLib.log(LogStatus.INFO, "PreIntake Approval process has been started");
		String Run_Status = lstRowData.get(getColumnNumberFromList(headerRow, "Run"));
		String[] ICUser = lstRowData.get(getColumnNumberFromList(headerRow, "IC User")).split("\\|");
		String UserLicense = lstRowData.get(getColumnNumberFromList(headerRow, "User License"));
		String Profile = lstRowData.get(getColumnNumberFromList(headerRow, "Profile"));
		String LicenseAllocation = lstRowData.get(getColumnNumberFromList(headerRow, "License Allocation"));
		String ApproveOption = lstRowData.get(getColumnNumberFromList(headerRow, "Approve Option"));
		String Comments = lstRowData.get(getColumnNumberFromList(headerRow, "Comments"));
		String InternalComments = lstRowData.get(getColumnNumberFromList(headerRow, "Comments"));
		// String FormStatus = lstRowData.get(sfdcLib.getColumnNumberFromList(headerRow,
		// "IC User"));
		String Country = lstRowData.get(getColumnNumberFromList(headerRow, "Country"));
		String State = lstRowData.get(getColumnNumberFromList(headerRow, "State"));
		String Website = lstRowData.get(getColumnNumberFromList(headerRow, "WebSite"));
		// String StockExchange =
		// lstRowData.get(sfdcLib.getColumnNumberFromList(headerRow, "IC User"));
		String YearEstablished = lstRowData.get(getColumnNumberFromList(headerRow, "Year Established"));
		// String FormNumber = lstRowData.get(getColumnNumberFromList(headerRow, "Form
		// Number"));
		// String Contact = lstRowData.get(sfdcLib.getColumnNumberFromList(headerRow,
		// "Contact"));
		String activeRR = lstRowData.get(getColumnNumberFromList(headerRow, "Active RR"));
		String AccountName = "Entity_Script_" + commonLib.getRandomNumber(4);
		String filepath = System.getProperty("user.dir") + "\\images\\Test Doc.pdf";

		commonLib.launchURL("https://stryker--stage.lightning.force.com/lightning/setup/SetupOneHome/home");

		commonLib.waitForVisibilityOf("//a[text()='Setup Home']");

		approvePreIntakeForm(ICUser[1], ApproveOption, InternalComments, FormNumber);
		Thread.sleep(2000);
		commonLib.waitforElement("Intake_ContactName_XPATH");
		commonLib.clickbyjavascript("Intake_ContactName_XPATH");
		Thread.sleep(2000);
		commonLib.waitForVisibilityOf("Intake_Contact_More_DD_XPATH");
		commonLib.log(LogStatus.PASS, "Step 4: Navigated to Contact.");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		commonLib.waitforElement("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Enable_Customer_XPATH");
		commonLib.switchtoFrame("Intake_Iframe_Contact_XPATH");
		String Username = "Sample" + commonLib.getRandomNumber(4) + "@xyz.com";
		commonLib.clear("Intake_Username_XPATH");
		Thread.sleep(2000);
		commonLib.sendKeys("Intake_Username_XPATH", Username);
		commonLib.clear("Intake_NickName_XPATH");
		commonLib.sendKeys("Intake_NickName_XPATH", "Sam" + commonLib.getRandomNumber(4));
		commonLib.selectDropdownVisibleText("Intake_User_License_XPATH", UserLicense);
		Thread.sleep(1000);
		commonLib.selectDropdownVisibleText("Intake_Profile_XPATH", Profile);
		Thread.sleep(1000);
		commonLib.scrollDownToElement("Intake_Additional_Info_XPATH", "field");
		commonLib.selectDropdownVisibleText("Intake_License_Allocation_XPATH", LicenseAllocation);
		Thread.sleep(1000);
		commonLib.click("Intake_Save_Btn_XPATH");
		// commonLib.alertAccept();
		// commonLib.waitforInvisibilityOfElement("Intake_Save_Btn_XPATH");
		commonLib.log(LogStatus.PASS, "Step 5: Enabled the Customer User");
		commonLib.getScreenshot();
		Thread.sleep(1000);

		App_Launcher_Search("IC Hub");
		Thread.sleep(6000);
		commonLib.waitForPageToLoad();
		globalSearch(FormNumber);
		commonLib.waitforElement("Intake_Release_Form_XPATH");
		commonLib.clickbyjavascript("Intake_Release_Form_XPATH");
		Thread.sleep(3000);
		commonLib.log(LogStatus.PASS, "Step 6: Form is Released to IC");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		logout();
		Thread.sleep(2000);
		App_Launcher_Search("IC Hub");
		Thread.sleep(2000);
		commonLib.waitForPageToLoad();
		globalSearch(FormNumber);
		commonLib.waitforElement("Intake_ContactName_XPATH");
		commonLib.clickbyjavascript("Intake_ContactName_XPATH");
		Thread.sleep(3000);
		try {
			commonLib.waitForElementToBeClickable("Intake_Contact_More_DD_XPATH");
		} catch (AssertionError e) {

		}
		commonLib.clickbyjavascript("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_Login_XPATH");
		commonLib.click("Intake_ICHUB_Community_XPATH");
		Thread.sleep(1000);
		commonLib.log(LogStatus.PASS, "Step 7: Logged in as Community User");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		commonLib.waitForPageToLoad();
		commonLib.waitforElement("Intake_Contact_Form_XPATH");
		commonLib.click("Intake_Contact_Form_XPATH");
		commonLib.waitForPageToLoad();
		commonLib.clickbyjavascript("Intake_List_View_XPATH");
		commonLib.clickbyjavascript("Intake_List_All_XPATH");
		Thread.sleep(6000);
		String MasterForm = commonLib.getText("Intake_Master_Form_XPATH");
		commonLib.clickWithDynamicValue("Intake_Form_List_XPATH", MasterForm);
		commonLib.waitForPageToLoad();
		commonLib.log(LogStatus.PASS, "Step 8: Navigated to master Form");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		commonLib.waitforElement("Intake_Form_Edit_XPATH");
		commonLib.clickbyjavascript("Intake_TnC_Checkbox1_XPATH");
		commonLib.clickbyjavascript("Intake_TnC_Checkbox2_XPATH");
		commonLib.clickbyjavascript("Intake_TnC_Checkbox3_XPATH");
		Thread.sleep(1000);
		commonLib.log(LogStatus.PASS,
				"Step 9: Checkbox for accepting terms and condition is visible. (142282, 141171, 143935)");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		commonLib.click("Intake_Proceed_XPATH");
		commonLib.waitForElementToBeClickable("Intake_Form_Edit_XPATH");
		commonLib.clickbyjavascript("Intake_Form_Edit_XPATH");
		Thread.sleep(2000);
		commonLib.click("Intake_General_Questions_XPATH");
		Thread.sleep(3000);
		commonLib.sendKeys("Intake_Company_Name_XPATH", AccountName);
		String RegNum = commonLib.getRandomNumber(5);
		Thread.sleep(3000);
		commonLib.sendKeys("Intake_Company_Registration_Number_XPATH", RegNum);
		Thread.sleep(2000);
		commonLib.clickbyjavascript("Intake_Country_XPATH");
		commonLib.clickWithDynamicValue("Intake_Country_Data_XPATH", Country);
		commonLib.sendKeys("Intake_Street_XPATH", "test");
		commonLib.sendKeys("Intake_City_XPATH", "test");
		commonLib.click("Intake_State_XPATH");
		commonLib.clickWithDynamicValue("Intake_State_Data_XPATH", State);
		commonLib.sendKeys("Intake_Zip_Postal_XPATH", commonLib.getRandomNumber(4));
		commonLib.scroll_view("Intake_Office_Number_XPATH");
		commonLib.sendKeys("Intake_Office_Number_XPATH", "+91" + commonLib.getRandomNumber(9));
		commonLib.sendKeys("Intake_Website_Address_XPATH", Website);
		Thread.sleep(2000);
		commonLib.sendKeys("Intake_Email_For_Business_Communication_XPATH",
				"Sample" + commonLib.getRandomNumber(4) + "@xyz.com");
		commonLib.log(LogStatus.PASS, "Step 10: Completed Madatory Fields for 'General Questions' Section");

		commonLib.scrollDownToElement("Intake_Contact_Information_XPATH", "Button");
		commonLib.click("Intake_Contact_Information_XPATH");
		Thread.sleep(2000);
		commonLib.waitforElement("Intake_Contact_Verify_XPATH");
		// if(commonLib.getText("Intake_Contact_Verify_XPATH").equalsIgnoreCase(Contact))
		// {
		// commonLib.log(LogStatus.PASS, "Step 11: Contact is same as in Pre-Intake
		// Form");
		// } else {
		// commonLib.log(LogStatus.FAIL, "Step 11: Contact is different from Pre-Intake
		// Form");
		// }
		commonLib.click("Intake_Edit_Contact_XPATH");
		commonLib.waitforElement("Intake_Contact_Type_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_Type_XPATH");
		commonLib.clickWithDynamicValue("Intake_Contact_Type_Data_XPATH", "Main contact");
		commonLib.clickbyjavascript("Intake_Title_XPATH");
		commonLib.clickWithDynamicValue("Intake_Title_Data_XPATH", "Mr.");
		// commonLib.waitforElement("Intake_CheckBox_Training_Required_XPATH");
		// commonLib.clickbyjavascript("Intake_CheckBox_Training_Required_XPATH");
		Thread.sleep(2000);
		commonLib.sendKeys("Intake_Job_Title_XPATH", "Test");
		String Cell = "+91" + commonLib.getRandomNumber(10);
		Thread.sleep(2000);
		commonLib.scrollDownToElement("Intake_Job_Title_XPATH", "Input");
		commonLib.sendKeys("Intake_Contact_Cell_Number_XPATH", Cell);
		Thread.sleep(2000);
		commonLib.click("Intake_Contact_Save_Btn_XPATH");
		commonLib.waitForPresenceOfElementLocated("Intake_Ownership_XPATH");
		commonLib.log(LogStatus.PASS, "Step 11: Filled the Contact Information");
		commonLib.getScreenshot();
		Thread.sleep(4000);
		commonLib.scrollDownToElement("Intake_Ownership_XPATH", "Button");
		commonLib.click("Intake_Ownership_XPATH");
		// commonLib.scrollDownToElement("Intake_Company_Details_XPATH", "Button");
		commonLib.click("Intake_Company_Publicly_XPATH");
		commonLib.clickWithDynamicValue("Intake_Company_Publicly_Data_XPATH", "No");
		Thread.sleep(1000);
		commonLib.click("Intake_Company_Publicly_Add_Row_XPATH");
		Thread.sleep(3000);
		commonLib.waitForPresenceOfElementLocated("Intake_Shareholders_Above_XPATH");
		commonLib.waitForVisibilityOf("Intake_Shareholders_Above_XPATH");
		commonLib.clickbyjavascript("Intake_Shareholders_Above_XPATH");
		Thread.sleep(1000);
		commonLib.click("Intake_Shareholders_Above_Data_XPATH");
		// commonLib.clickWithDynamicValue("Intake_Shareholders_Above_Data_XPATH",
		// "Owner/ Principal/ Shareholder Above 10%");
		Thread.sleep(1000);
		commonLib.clickbyjavascript("Intake_Title_XPATH");
		commonLib.clickWithDynamicValue("Intake_Title_Data_XPATH", "Mr.");
		commonLib.sendKeys("Intake_First_Name_Shareholder_XPATH", "First_" + commonLib.getRandomNumber(4));
		commonLib.sendKeys("Intake_Last_Name_Shareholder_XPATH", "Last_" + commonLib.getRandomNumber(4));
		commonLib.sendKeys("Intake_Email_Shareholder_XPATH", "sample" + commonLib.getRandomNumber(4) + "@xyz.com");
		// commonLib.waitforElement("Intake_CheckBox_Training_Required_XPATH");
		// commonLib.clickbyjavascript("Intake_CheckBox_Training_Required_XPATH");
		commonLib.sendKeys("Intake_Passport_Number_Shareholder_XPATH", commonLib.getRandomNumber(5));
		commonLib.sendKeys("Intake_Passport_Country_Shareholder_XPATH", "Argentina");
		commonLib.scrollDownToElement("Intake_Healthcare_Question_XPATH", "Input");
		commonLib.clickbyjavascript("Intake_Healthcare_Question_XPATH");
		commonLib.clickWithDynamicValue("Intake_Healthcare_Question_Data_XPATH", "No");
		commonLib.scrollDownToElement("Intake_Involvement_Stryker_XPATH", "Input");
		commonLib.clickbyjavascript("Intake_Involvement_Stryker_XPATH");
		commonLib.clickWithDynamicValue("Intake_Involvement_Stryker_Data_XPATH", "No");
		commonLib.click("Intake_Contact_Save_Btn_XPATH");
		Thread.sleep(6000);
		// commonLib.waitforInvisibilityOfElement("Intake_Contact_Save_Btn_XPATH");
		// commonLib.waitForPresenceOfElementLocated("Intake_Contact_Save_Btn_XPATH");
		commonLib.scrollDownToElement("Intake_Company_Publicly_Add_Row_XPATH", "Button");
		Thread.sleep(2000);
		uploadFileDirectly("Intake_Upload_Doc_Owners_XPATH", filepath);
		commonLib.waitforElement("Contract_Upload_Done_XPATH");
		commonLib.click("Contract_Upload_Done_XPATH");
		Thread.sleep(2000);
		commonLib.click("Intake_General_Questions_XPATH");
		Thread.sleep(2000);
		commonLib.scrollDownToElement("Intake_Company_Publicly_Add_Row_XPATH", "Button");
		// commonLib.click("Intake_Stock_Exchange_XPATH");
		// commonLib.clickWithDynamicValue("Intake_Stock_Exchange_Data_XPATH",
		// StockExchange);
		commonLib.clickbyjavascript("Intake_Association_With_Stryker_XPATH");
		commonLib.clickWithDynamicValue("Intake_Association_With_Stryker_Data_XPATH", "No");
		commonLib.log(LogStatus.PASS, "Step 12: Completed Madatory Fields for 'Ownership' Section");

		commonLib.scrollDownToElement("Intake_Company_Details_XPATH", "Button");
		commonLib.click("Intake_Company_Details_XPATH");
		commonLib.scrollDownToElement("Intake_Company_Details_XPATH", "Button");
		Thread.sleep(1000);
		commonLib.sendKeys("Intake_Company_Eshtblished_XPATH", YearEstablished);
		commonLib.click("Intake_Different_Registered_Name_XPATH");
		Thread.sleep(1000);
		commonLib.clickWithDynamicValue("Intake_Different_Registered_Name_Data_XPATH", "No");
		Thread.sleep(1000);
		commonLib.scrollDownToElement("Intake_Employed_XPATH", "Button");
		commonLib.clickbyjavascript("Intake_Employed_XPATH");
		Thread.sleep(2000);
		commonLib.clickWithDynamicValue("Intake_Employed_Data_XPATH", "1-5");
		commonLib.clickbyjavascript("Intake_Work_Related_Stryker_XPATH");
		Thread.sleep(1000);
		commonLib.clickWithDynamicValue("Intake_Work_Related_Stryker_Data_XPATH", "1-5");
		Thread.sleep(2000);
		commonLib.click("Intake_Financial_Terms_JR_Sales_XPATH");
		commonLib.click("Intake_Franchise_Movetoselected1_XPATH");
		Thread.sleep(2000);
		commonLib.click("Intake_Financial_Terms_JR_Invoices_XPATH");
		commonLib.click("Intake_Franchise_Movetoselected1_XPATH");
		Thread.sleep(2000);
		commonLib.click("Intake_Financial_Terms_JR_Owns_XPATH");
		commonLib.click("Intake_Franchise_Movetoselected1_XPATH");
		Thread.sleep(2000);
		commonLib.scrollDownToElement("Intake_Franchise_Movetoselected1_XPATH", "button");
		commonLib.click("Intake_Work_Experience_XPATH");
		commonLib.clickWithDynamicValue("Intake_Work_Experience_Data_XPATH", "1-2");
		Thread.sleep(2000);
		commonLib.click("Intake_Medical_Companies_XPATH");
		commonLib.click("Intake_Franchise_Movetoselected2_XPATH");
		commonLib.scrollDownToElement("Intake_Franchise_Movetoselected2_XPATH", "Button");
		Thread.sleep(2000);
		commonLib.click("Intake_Company_Certifications_XPATH");
		commonLib.clickWithDynamicValue("Intake_Company_Certifications_Data_XPATH", "No");
		commonLib.click("Intake_Intention_Engage_XPATH");
		commonLib.clickWithDynamicValue("Intake_Intention_Engage_Data_XPATH", "No");
		Thread.sleep(2000);
		commonLib.log(LogStatus.PASS, "Step 13: Completed Madatory Fields for 'Company Details' Section");
		commonLib.scrollDownToElement("Intake_Save_For_Later_XPATH", "Button");
		commonLib.click("Intake_Save_For_Later_XPATH");
		commonLib.waitForVisibilityOf("Intake_Form_Edit_XPATH");
		Thread.sleep(2000);
		commonLib.log(LogStatus.PASS, "Step 14: Form Filled and Saved for later.");
		commonLib.getScreenshot();
		Thread.sleep(2000);

		commonLib.waitforElement("Intake_Form_Edit_XPATH");
		commonLib.click("Intake_Form_Edit_XPATH");
		commonLib.waitforElement("Intake_Review_Checkbox_XPATH");
		commonLib.clickbyjavascript("Intake_Review_Checkbox_XPATH");
		Thread.sleep(2000);
		commonLib.waitforElement("Submit_Review_XPATH");
		commonLib.click("Submit_Review_XPATH");
		Thread.sleep(8000);
		// commonLib.waitforInvisibilityOfElement("Submit_Review_XPATH");
		commonLib.waitforElement("Intake_Form_Edit_XPATH");
		commonLib.clickbyjavascript("Intake_Form_Edit_XPATH");
		Thread.sleep(3000);
		if (commonLib.waitForPresenceOfElementLocated("Intake_Approve_Check_XPATH")) {
			commonLib.log(LogStatus.PASS, "Step 15: Master Form is submitted for review");
			commonLib.getScreenshot();
		} else {
			commonLib.log(LogStatus.FAIL, "Step 16: Master Form is not submitted for review");
			commonLib.getScreenshot();
		}
		Thread.sleep(3000);
		commonLib.click("Intake_Close_Window_XPATH");

		commonLib.waitforElement("Intake_Customer_User_XPATH");
		Thread.sleep(3000);
		commonLib.clickbyjavascript("Intake_Customer_User_XPATH");
		commonLib.click("Intake_Customer_User_Logout_XPATH");
		Thread.sleep(2000);

		commonLib.refreshPage();
		Thread.sleep(3000);
		commonLib.waitForPresenceOfElementLocated("User_settings_Button_XPATH");
		click_Setup_FromHomePage_AfterLogin();
		if (gximProfile == GximProfile.LOCAL) {
			search_Profile_GXICM(ICUser[1]);
		} else if (gximProfile == GximProfile.REGIONAL) {
			search_Profile_GXICM(ICUser[5]);
		}
		commonLib.waitForVisibilityOf("Logout_Link_In_Home_Page_XPATH");
		Thread.sleep(2000);
		commonLib.log(LogStatus.PASS, "Step 17: Login to ICM Local.");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		globalSearch(MasterForm);
		Thread.sleep(4000);
		commonLib.click("Intake_Edit_Btn_XPATH");
		commonLib.click("Intake_Approver_Action_XPATH");
		Thread.sleep(2000);
		commonLib.clickWithDynamicValue("Intake_Approver_Option_XPATH", ApproveOption);
		commonLib.sendKeys("Intake_Approve_Comments_XPATH", Comments);
		commonLib.sendKeys("Intake_Approve_Comments_Internal_XPATH", InternalComments);
		Thread.sleep(2000);
		commonLib.waitforElement("Intake_Complete_Review_XPATH");
		commonLib.click("Intake_Complete_Review_XPATH");
		Thread.sleep(7000);
		// commonLib.waitforInvisibilityOfElement("Intake_Complete_Review_XPATH");
		commonLib.waitForVisibilityOf("Intake_Form_Status_XPATH");
		commonLib.waitforElement("Intake_Form_Status_XPATH");
		if (commonLib.getText("Intake_Form_Status_XPATH").equalsIgnoreCase("Approved")) {
			commonLib.log(LogStatus.PASS, "Step 19: Master Form Status is Approved");
			commonLib.getScreenshot();
		} else {
			commonLib.log(LogStatus.FAIL, "Step 19: Master Form Status is not Approved");
			commonLib.getScreenshot();

		}
		commonLib.refreshPage();
		commonLib.log(LogStatus.PASS, "Step 18: Navigated to Master Form.");
		commonLib.getScreenshot();
		Thread.sleep(4000);
		commonLib.waitForElementToBeClickable("Intake_Account_Name_XPATH");
		commonLib.clickbyjavascript("Intake_Account_Name_XPATH");
		Thread.sleep(2000);
		commonLib.waitForElementToBeClickable("Intake_Active_RR_Edit_XPATH");
		Thread.sleep(1000);
		commonLib.click("Intake_Active_RR_Edit_XPATH");
		Thread.sleep(3000);
		commonLib.clickbyjavascript("Intake_Active_RR_DD_XPATH");
		Thread.sleep(1000);
		commonLib.clickWithDynamicValue("Intake_Active_RR_DD_Data_XPATH", activeRR);
		Thread.sleep(2000);
		commonLib.click("Intake_Contact_Save_Btn_XPATH");
		Thread.sleep(4000);
		commonLib.waitForPresenceOfElementLocated("Intake_Active_RR_Check_XPATH");
		if (commonLib.getText("Intake_Active_RR_Check_XPATH").equalsIgnoreCase(activeRR)) {
			commonLib.log(LogStatus.PASS, "Step 20: Active Risk Rating is: " + activeRR);
			commonLib.getScreenshot();
		} else {
			commonLib.log(LogStatus.FAIL, "Step 20: Active Risk Rating is not  " + activeRR);
			commonLib.getScreenshot();
		}
		Thread.sleep(3000);

		return MasterForm;

	}

	public List<String> preIntake_MasterForm_Approval_CreateAllContacts(List<String> lstRowData, List<String> headerRow,
			String workBook, String workSheet, String FormNumber, GximProfile gximProfile) throws Exception {
		List<String> allDetails = new ArrayList<>();

		commonLib.log(LogStatus.INFO, "PreIntake Approval process has been started");
		String Run_Status = lstRowData.get(getColumnNumberFromList(headerRow, "Run"));
		String[] ICUser = lstRowData.get(getColumnNumberFromList(headerRow, "IC User")).split("\\|");
		String UserLicense = lstRowData.get(getColumnNumberFromList(headerRow, "User License"));
		String Profile = lstRowData.get(getColumnNumberFromList(headerRow, "Profile"));
		String LicenseAllocation = lstRowData.get(getColumnNumberFromList(headerRow, "License Allocation"));
		String ApproveOption = lstRowData.get(getColumnNumberFromList(headerRow, "Approve Option"));
		String Comments = lstRowData.get(getColumnNumberFromList(headerRow, "Comments"));
		String InternalComments = lstRowData.get(getColumnNumberFromList(headerRow, "Comments"));
		// String FormStatus = lstRowData.get(sfdcLib.getColumnNumberFromList(headerRow,
		// "IC User"));
		String Country = lstRowData.get(getColumnNumberFromList(headerRow, "Country"));
		String State = lstRowData.get(getColumnNumberFromList(headerRow, "State"));
		String Website = lstRowData.get(getColumnNumberFromList(headerRow, "WebSite"));
		// String StockExchange =
		// lstRowData.get(sfdcLib.getColumnNumberFromList(headerRow, "IC User"));
		String YearEstablished = lstRowData.get(getColumnNumberFromList(headerRow, "Year Established"));
		// String FormNumber = lstRowData.get(getColumnNumberFromList(headerRow, "Form
		// Number"));
		// String Contact = lstRowData.get(sfdcLib.getColumnNumberFromList(headerRow,
		// "Contact"));
		String activeRR = lstRowData.get(getColumnNumberFromList(headerRow, "Active RR"));
		String AccountName = "Entity_Script_" + commonLib.getRandomNumber(4);
		String filepath = System.getProperty("user.dir") + "\\images\\Test Doc.pdf";

		commonLib.launchURL("https://stryker--stage.lightning.force.com/lightning/setup/SetupOneHome/home");

		commonLib.waitForVisibilityOf("//a[text()='Setup Home']");

		approvePreIntakeForm(ICUser[1], ApproveOption, InternalComments, FormNumber);
		Thread.sleep(2000);
		commonLib.waitforElement("Intake_ContactName_XPATH");
		commonLib.clickbyjavascript("Intake_ContactName_XPATH");
		Thread.sleep(2000);
		commonLib.waitForVisibilityOf("Intake_Contact_More_DD_XPATH");
		commonLib.log(LogStatus.PASS, "Step 4: Navigated to Contact.");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		commonLib.waitforElement("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Enable_Customer_XPATH");
		commonLib.switchtoFrame("Intake_Iframe_Contact_XPATH");
		String Username = "Sample" + commonLib.getRandomNumber(4) + "@xyz.com";
		commonLib.clear("Intake_Username_XPATH");
		Thread.sleep(2000);
		commonLib.sendKeys("Intake_Username_XPATH", Username);
		commonLib.clear("Intake_NickName_XPATH");
		commonLib.sendKeys("Intake_NickName_XPATH", "Sam" + commonLib.getRandomNumber(4));
		commonLib.selectDropdownVisibleText("Intake_User_License_XPATH", UserLicense);
		Thread.sleep(1000);
		commonLib.selectDropdownVisibleText("Intake_Profile_XPATH", Profile);
		Thread.sleep(1000);
		commonLib.scrollDownToElement("Intake_Additional_Info_XPATH", "field");
		commonLib.selectDropdownVisibleText("Intake_License_Allocation_XPATH", LicenseAllocation);
		Thread.sleep(1000);
		commonLib.click("Intake_Save_Btn_XPATH");
		// commonLib.alertAccept();
		// commonLib.waitforInvisibilityOfElement("Intake_Save_Btn_XPATH");
		commonLib.log(LogStatus.PASS, "Step 5: Enabled the Customer User");
		commonLib.getScreenshot();
		Thread.sleep(1000);

		App_Launcher_Search("IC Hub");
		Thread.sleep(6000);
		commonLib.waitForPageToLoad();
		globalSearch(FormNumber);
		commonLib.waitforElement("Intake_Release_Form_XPATH");
		commonLib.clickbyjavascript("Intake_Release_Form_XPATH");
		Thread.sleep(3000);
		commonLib.log(LogStatus.PASS, "Step 6: Form is Released to IC");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		logout();
		Thread.sleep(2000);
		App_Launcher_Search("IC Hub");
		Thread.sleep(2000);
		commonLib.waitForPageToLoad();
		globalSearch(FormNumber);
		commonLib.waitforElement("Intake_ContactName_XPATH");
		commonLib.clickbyjavascript("Intake_ContactName_XPATH");
		Thread.sleep(3000);
		try {
			commonLib.waitForElementToBeClickable("Intake_Contact_More_DD_XPATH");
		} catch (AssertionError e) {

		}
		commonLib.clickbyjavascript("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_Login_XPATH");
		commonLib.click("Intake_ICHUB_Community_XPATH");
		Thread.sleep(1000);
		commonLib.log(LogStatus.PASS, "Step 7: Logged in as Community User");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		commonLib.waitForPageToLoad();
		commonLib.waitforElement("Intake_Contact_Form_XPATH");
		commonLib.click("Intake_Contact_Form_XPATH");
		commonLib.waitForPageToLoad();
		commonLib.clickbyjavascript("Intake_List_View_XPATH");
		commonLib.clickbyjavascript("Intake_List_All_XPATH");
		Thread.sleep(6000);
		String MasterForm = commonLib.getText("Intake_Master_Form_XPATH");
		allDetails.add(MasterForm);
		commonLib.clickWithDynamicValue("Intake_Form_List_XPATH", MasterForm);
		commonLib.waitForPageToLoad();
		commonLib.log(LogStatus.PASS, "Step 8: Navigated to master Form");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		commonLib.waitforElement("Intake_Form_Edit_XPATH");
		commonLib.clickbyjavascript("Intake_TnC_Checkbox1_XPATH");
		commonLib.clickbyjavascript("Intake_TnC_Checkbox2_XPATH");
		commonLib.clickbyjavascript("Intake_TnC_Checkbox3_XPATH");
		Thread.sleep(1000);
		commonLib.log(LogStatus.PASS,
				"Step 9: Checkbox for accepting terms and condition is visible. (142282, 141171, 143935)");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		commonLib.click("Intake_Proceed_XPATH");
		commonLib.waitForElementToBeClickable("Intake_Form_Edit_XPATH");
		commonLib.clickbyjavascript("Intake_Form_Edit_XPATH");
		Thread.sleep(2000);
		commonLib.click("Intake_General_Questions_XPATH");
		Thread.sleep(3000);
		commonLib.sendKeys("Intake_Company_Name_XPATH", AccountName);
		String RegNum = commonLib.getRandomNumber(5);
		Thread.sleep(3000);
		commonLib.sendKeys("Intake_Company_Registration_Number_XPATH", RegNum);
		Thread.sleep(2000);
		commonLib.clickbyjavascript("Intake_Country_XPATH");
		commonLib.clickWithDynamicValue("Intake_Country_Data_XPATH", Country);
		commonLib.sendKeys("Intake_Street_XPATH", "test");
		commonLib.sendKeys("Intake_City_XPATH", "test");
		commonLib.click("Intake_State_XPATH");
		commonLib.clickWithDynamicValue("Intake_State_Data_XPATH", State);
		commonLib.sendKeys("Intake_Zip_Postal_XPATH", commonLib.getRandomNumber(4));
		commonLib.scroll_view("Intake_Office_Number_XPATH");
		commonLib.sendKeys("Intake_Office_Number_XPATH", "+91" + commonLib.getRandomNumber(9));
		commonLib.sendKeys("Intake_Website_Address_XPATH", Website);
		Thread.sleep(2000);
		commonLib.sendKeys("Intake_Email_For_Business_Communication_XPATH",
				"Sample" + commonLib.getRandomNumber(4) + "@xyz.com");
		commonLib.log(LogStatus.PASS, "Step 10: Completed Madatory Fields for 'General Questions' Section");

		commonLib.scrollDownToElement("Intake_Contact_Information_XPATH", "Button");
		commonLib.click("Intake_Contact_Information_XPATH");
		Thread.sleep(2000);
		commonLib.waitforElement("Intake_Contact_Verify_XPATH");
		// if(commonLib.getText("Intake_Contact_Verify_XPATH").equalsIgnoreCase(Contact))
		// {
		// commonLib.log(LogStatus.PASS, "Step 11: Contact is same as in Pre-Intake
		// Form");
		// } else {
		// commonLib.log(LogStatus.FAIL, "Step 11: Contact is different from Pre-Intake
		// Form");
		// }
		commonLib.click("Intake_Edit_Contact_XPATH");
		commonLib.waitforElement("Intake_Contact_Type_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_Type_XPATH");
		commonLib.clickWithDynamicValue("Intake_Contact_Type_Data_XPATH", "Main contact");
		commonLib.clickbyjavascript("Intake_Title_XPATH");
		commonLib.clickWithDynamicValue("Intake_Title_Data_XPATH", "Mr.");
		// commonLib.waitforElement("Intake_CheckBox_Training_Required_XPATH");
		// commonLib.clickbyjavascript("Intake_CheckBox_Training_Required_XPATH");
		Thread.sleep(2000);
		commonLib.sendKeys("Intake_Job_Title_XPATH", "Test");
		String Cell = "+91" + commonLib.getRandomNumber(10);
		Thread.sleep(2000);
		commonLib.scrollDownToElement("Intake_Job_Title_XPATH", "Input");
		commonLib.sendKeys("Intake_Contact_Cell_Number_XPATH", Cell);
		Thread.sleep(2000);
		commonLib.click("Intake_Contact_Save_Btn_XPATH");
		commonLib.waitforInvisibilityOfElement("Intake_Contact_Save_Btn_XPATH");

		commonLib.click("Intake_Contact_Add_Row_XPATH");
		commonLib.waitforElement("Intake_Contact_Type_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_Type_XPATH");
		commonLib.clickWithDynamicValue("Intake_Contact_Type_Data_XPATH", "Compliance");
		commonLib.clickbyjavascript("Intake_Title_XPATH");
		commonLib.clickWithDynamicValue("Intake_Title_Data_XPATH", "Mr.");
		String ComplianceFirst = "Compliance" + commonLib.getRandomNumber(3);
		String ComplianceLast = "Contact" + commonLib.getRandomNumber(3);
		String Compliance_Contact = ComplianceFirst + " " + ComplianceLast;
		allDetails.add(Compliance_Contact);
		commonLib.sendKeys("Intake_Contact_FirstName_XPATH", ComplianceFirst);
		Thread.sleep(1000);
		commonLib.sendKeys("Intake_Contact_LastName_XPATH", ComplianceLast);
		commonLib.waitforElement("Intake_CheckBox_Training_Required_XPATH");
		commonLib.clickbyjavascript("Intake_CheckBox_Training_Required_XPATH");
		Thread.sleep(1000);
		commonLib.sendKeys("Intake_Job_Title_XPATH", "Compliance Test");
		Thread.sleep(1000);
		commonLib.sendKeys("Intake_Contact_Email_XPATH", "Contact" + commonLib.getRandomNumber(4) + "@xyz.com");
		commonLib.scrollDownToElement("Intake_Job_Title_XPATH", "Input");
		commonLib.sendKeys("Intake_Contact_Cell_Number_XPATH", Cell);
		commonLib.sendKeys("Intake_Contact_Office_Number_XPATH", commonLib.getRandomMobile());
		Thread.sleep(1000);
		commonLib.click("Intake_Contact_Save_Btn_XPATH");

		commonLib.waitforInvisibilityOfElement("Intake_Contact_Save_Btn_XPATH");
		Thread.sleep(1000);
		commonLib.click("Intake_Contact_Add_Row_XPATH");
		commonLib.waitforElement("Intake_Contact_Type_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_Type_XPATH");
		commonLib.clickWithDynamicValue("Intake_Contact_Type_Data_XPATH", "Finance");
		commonLib.clickbyjavascript("Intake_Title_XPATH");
		commonLib.clickWithDynamicValue("Intake_Title_Data_XPATH", "Mr.");
		String FinanceFirst = "Finance" + commonLib.getRandomNumber(3);
		String FinanceLast = "Contact" + commonLib.getRandomNumber(3);
		String Finance_Contact = FinanceFirst + " " + FinanceLast;
		allDetails.add(Finance_Contact);
		commonLib.sendKeys("Intake_Contact_FirstName_XPATH", FinanceFirst);
		Thread.sleep(1000);
		commonLib.sendKeys("Intake_Contact_LastName_XPATH", FinanceLast);
		commonLib.waitforElement("Intake_CheckBox_Training_Required_XPATH");
		commonLib.clickbyjavascript("Intake_CheckBox_Training_Required_XPATH");
		Thread.sleep(1000);
		commonLib.sendKeys("Intake_Job_Title_XPATH", "Finance Test");
		Thread.sleep(1000);
		commonLib.sendKeys("Intake_Contact_Email_XPATH", "Contact" + commonLib.getRandomNumber(4) + "@xyz.com");
		commonLib.scrollDownToElement("Intake_Job_Title_XPATH", "Input");
		commonLib.sendKeys("Intake_Contact_Cell_Number_XPATH", Cell);
		commonLib.sendKeys("Intake_Contact_Office_Number_XPATH", commonLib.getRandomMobile());
		Thread.sleep(1000);
		commonLib.click("Intake_Contact_Save_Btn_XPATH");

		commonLib.waitforInvisibilityOfElement("Intake_Contact_Save_Btn_XPATH");
		Thread.sleep(2000);
		commonLib.click("Intake_Contact_Add_Row_XPATH");
		commonLib.waitforElement("Intake_Contact_Type_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_Type_XPATH");
		commonLib.clickWithDynamicValue("Intake_Contact_Type_Data_XPATH", "Regulatory/ Quality");
		commonLib.clickbyjavascript("Intake_Title_XPATH");

		commonLib.clickWithDynamicValue("Intake_Title_Data_XPATH", "Mr.");
		String RAQAFirst = "RAQA" + commonLib.getRandomNumber(3);
		String RAQALast = "Contact" + commonLib.getRandomNumber(3);
		String RAQA_Contact = RAQAFirst + " " + RAQALast;
		allDetails.add(RAQA_Contact);
		commonLib.sendKeys("Intake_Contact_FirstName_XPATH", RAQAFirst);
		Thread.sleep(1000);
		commonLib.sendKeys("Intake_Contact_LastName_XPATH", RAQALast);
		commonLib.waitforElement("Intake_CheckBox_Training_Required_XPATH");
		commonLib.clickbyjavascript("Intake_CheckBox_Training_Required_XPATH");
		Thread.sleep(1000);
		commonLib.sendKeys("Intake_Job_Title_XPATH", "RAQA Test");
		Thread.sleep(1000);
		commonLib.sendKeys("Intake_Contact_Email_XPATH", "Contact" + commonLib.getRandomNumber(4) + "@xyz.com");
		commonLib.scrollDownToElement("Intake_Job_Title_XPATH", "Input");
		commonLib.sendKeys("Intake_Contact_Cell_Number_XPATH", Cell);
		commonLib.sendKeys("Intake_Contact_Office_Number_XPATH", commonLib.getRandomMobile());
		Thread.sleep(1000);
		commonLib.click("Intake_Contact_Save_Btn_XPATH");
		commonLib.waitforInvisibilityOfElement("Intake_Contact_Save_Btn_XPATH");
		commonLib.log(LogStatus.PASS, "Step 4: Filled the Contact Information");
		commonLib.getScreenshot();

		commonLib.waitForPresenceOfElementLocated("Intake_Ownership_XPATH");
		commonLib.log(LogStatus.PASS, "Step 11: Filled the Contact Information");
		commonLib.getScreenshot();
		Thread.sleep(4000);
		commonLib.scrollDownToElement("Intake_Ownership_XPATH", "Button");
		commonLib.click("Intake_Ownership_XPATH");
		// commonLib.scrollDownToElement("Intake_Company_Details_XPATH", "Button");
		commonLib.click("Intake_Company_Publicly_XPATH");
		commonLib.clickWithDynamicValue("Intake_Company_Publicly_Data_XPATH", "No");
		Thread.sleep(1000);
		commonLib.click("Intake_Company_Publicly_Add_Row_XPATH");
		Thread.sleep(3000);
		commonLib.waitForPresenceOfElementLocated("Intake_Shareholders_Above_XPATH");
		commonLib.waitForVisibilityOf("Intake_Shareholders_Above_XPATH");
		commonLib.clickbyjavascript("Intake_Shareholders_Above_XPATH");
		Thread.sleep(1000);
		commonLib.click("Intake_Shareholders_Above_Data_XPATH");
		// commonLib.clickWithDynamicValue("Intake_Shareholders_Above_Data_XPATH",
		// "Owner/ Principal/ Shareholder Above 10%");
		Thread.sleep(1000);
		commonLib.clickbyjavascript("Intake_Title_XPATH");
		commonLib.clickWithDynamicValue("Intake_Title_Data_XPATH", "Mr.");
		commonLib.sendKeys("Intake_First_Name_Shareholder_XPATH", "First_" + commonLib.getRandomNumber(4));
		commonLib.sendKeys("Intake_Last_Name_Shareholder_XPATH", "Last_" + commonLib.getRandomNumber(4));
		commonLib.sendKeys("Intake_Email_Shareholder_XPATH", "sample" + commonLib.getRandomNumber(4) + "@xyz.com");
		// commonLib.waitforElement("Intake_CheckBox_Training_Required_XPATH");
		// commonLib.clickbyjavascript("Intake_CheckBox_Training_Required_XPATH");
		commonLib.sendKeys("Intake_Passport_Number_Shareholder_XPATH", commonLib.getRandomNumber(5));
		commonLib.sendKeys("Intake_Passport_Country_Shareholder_XPATH", "Argentina");
		commonLib.scrollDownToElement("Intake_Healthcare_Question_XPATH", "Input");
		commonLib.clickbyjavascript("Intake_Healthcare_Question_XPATH");
		commonLib.clickWithDynamicValue("Intake_Healthcare_Question_Data_XPATH", "No");
		commonLib.scrollDownToElement("Intake_Involvement_Stryker_XPATH", "Input");
		commonLib.clickbyjavascript("Intake_Involvement_Stryker_XPATH");
		commonLib.clickWithDynamicValue("Intake_Involvement_Stryker_Data_XPATH", "No");
		commonLib.click("Intake_Contact_Save_Btn_XPATH");
		Thread.sleep(6000);
		// commonLib.waitforInvisibilityOfElement("Intake_Contact_Save_Btn_XPATH");
		// commonLib.waitForPresenceOfElementLocated("Intake_Contact_Save_Btn_XPATH");
		commonLib.scrollDownToElement("Intake_Company_Publicly_Add_Row_XPATH", "Button");
		Thread.sleep(2000);
		uploadFileDirectly("Intake_Upload_Doc_Owners_XPATH", filepath);
		commonLib.waitforElement("Contract_Upload_Done_XPATH");
		commonLib.click("Contract_Upload_Done_XPATH");
		Thread.sleep(2000);
		commonLib.click("Intake_General_Questions_XPATH");
		Thread.sleep(2000);
		commonLib.scrollDownToElement("Intake_Company_Publicly_Add_Row_XPATH", "Button");
		// commonLib.click("Intake_Stock_Exchange_XPATH");
		// commonLib.clickWithDynamicValue("Intake_Stock_Exchange_Data_XPATH",
		// StockExchange);
		commonLib.clickbyjavascript("Intake_Association_With_Stryker_XPATH");
		commonLib.clickWithDynamicValue("Intake_Association_With_Stryker_Data_XPATH", "No");
		commonLib.log(LogStatus.PASS, "Step 12: Completed Madatory Fields for 'Ownership' Section");

		commonLib.scrollDownToElement("Intake_Company_Details_XPATH", "Button");
		commonLib.click("Intake_Company_Details_XPATH");
		commonLib.scrollDownToElement("Intake_Company_Details_XPATH", "Button");
		Thread.sleep(1000);
		commonLib.sendKeys("Intake_Company_Eshtblished_XPATH", YearEstablished);
		commonLib.click("Intake_Different_Registered_Name_XPATH");
		Thread.sleep(1000);
		commonLib.clickWithDynamicValue("Intake_Different_Registered_Name_Data_XPATH", "No");
		Thread.sleep(1000);
		commonLib.scrollDownToElement("Intake_Employed_XPATH", "Button");
		commonLib.clickbyjavascript("Intake_Employed_XPATH");
		Thread.sleep(2000);
		commonLib.clickWithDynamicValue("Intake_Employed_Data_XPATH", "1-5");
		commonLib.clickbyjavascript("Intake_Work_Related_Stryker_XPATH");
		Thread.sleep(1000);
		commonLib.clickWithDynamicValue("Intake_Work_Related_Stryker_Data_XPATH", "1-5");
		Thread.sleep(2000);
		commonLib.click("Intake_Financial_Terms_JR_Sales_XPATH");
		commonLib.click("Intake_Franchise_Movetoselected1_XPATH");
		Thread.sleep(2000);
		commonLib.click("Intake_Financial_Terms_JR_Invoices_XPATH");
		commonLib.click("Intake_Franchise_Movetoselected1_XPATH");
		Thread.sleep(2000);
		commonLib.click("Intake_Financial_Terms_JR_Owns_XPATH");
		commonLib.click("Intake_Franchise_Movetoselected1_XPATH");
		Thread.sleep(2000);
		commonLib.scrollDownToElement("Intake_Franchise_Movetoselected1_XPATH", "button");
		commonLib.click("Intake_Work_Experience_XPATH");
		commonLib.clickWithDynamicValue("Intake_Work_Experience_Data_XPATH", "1-2");
		Thread.sleep(2000);
		commonLib.click("Intake_Medical_Companies_XPATH");
		commonLib.click("Intake_Franchise_Movetoselected2_XPATH");
		commonLib.scrollDownToElement("Intake_Franchise_Movetoselected2_XPATH", "Button");
		Thread.sleep(2000);
		commonLib.click("Intake_Company_Certifications_XPATH");
		commonLib.clickWithDynamicValue("Intake_Company_Certifications_Data_XPATH", "No");
		commonLib.click("Intake_Intention_Engage_XPATH");
		commonLib.clickWithDynamicValue("Intake_Intention_Engage_Data_XPATH", "No");
		Thread.sleep(2000);
		commonLib.log(LogStatus.PASS, "Step 13: Completed Madatory Fields for 'Company Details' Section");
		commonLib.scrollDownToElement("Intake_Save_For_Later_XPATH", "Button");
		commonLib.click("Intake_Save_For_Later_XPATH");
		commonLib.waitForVisibilityOf("Intake_Form_Edit_XPATH");
		Thread.sleep(2000);
		commonLib.log(LogStatus.PASS, "Step 14: Form Filled and Saved for later.");
		commonLib.getScreenshot();
		Thread.sleep(2000);

		commonLib.waitforElement("Intake_Form_Edit_XPATH");
		commonLib.click("Intake_Form_Edit_XPATH");
		commonLib.waitforElement("Intake_Review_Checkbox_XPATH");
		commonLib.clickbyjavascript("Intake_Review_Checkbox_XPATH");
		Thread.sleep(2000);
		commonLib.waitforElement("Submit_Review_XPATH");
		commonLib.click("Submit_Review_XPATH");
		Thread.sleep(8000);
		// commonLib.waitforInvisibilityOfElement("Submit_Review_XPATH");
		commonLib.waitforElement("Intake_Form_Edit_XPATH");
		commonLib.clickbyjavascript("Intake_Form_Edit_XPATH");
		Thread.sleep(3000);
		if (commonLib.waitForPresenceOfElementLocated("Intake_Approve_Check_XPATH")) {
			commonLib.log(LogStatus.PASS, "Step 15: Master Form is submitted for review");
			commonLib.getScreenshot();
		} else {
			commonLib.log(LogStatus.FAIL, "Step 16: Master Form is not submitted for review");
			commonLib.getScreenshot();
		}
		Thread.sleep(3000);
		commonLib.click("Intake_Close_Window_XPATH");

		commonLib.waitforElement("Intake_Customer_User_XPATH");
		Thread.sleep(3000);
		commonLib.clickbyjavascript("Intake_Customer_User_XPATH");
		commonLib.click("Intake_Customer_User_Logout_XPATH");
		Thread.sleep(2000);

		commonLib.refreshPage();
		Thread.sleep(3000);
		commonLib.waitForPresenceOfElementLocated("User_settings_Button_XPATH");
		click_Setup_FromHomePage_AfterLogin();
		if (gximProfile == GximProfile.LOCAL) {
			search_Profile_GXICM(ICUser[1]);
		} else if (gximProfile == GximProfile.REGIONAL) {
			search_Profile_GXICM(ICUser[5]);
		}
		commonLib.waitForVisibilityOf("Logout_Link_In_Home_Page_XPATH");
		Thread.sleep(2000);
		commonLib.log(LogStatus.PASS, "Step 17: Login to ICM Local.");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		globalSearch(MasterForm);
		commonLib.waitForPresenceOfElementLocated("Intake_Edit_Btn_XPATH");
		commonLib.log(LogStatus.PASS, "Step 18: Navigated to Master Form.");
		commonLib.getScreenshot();
		Thread.sleep(4000);
		commonLib.waitForElementToBeClickable("Intake_Account_Name_XPATH");
		commonLib.clickbyjavascript("Intake_Account_Name_XPATH");
		Thread.sleep(2000);
		commonLib.waitForElementToBeClickable("Intake_Active_RR_Edit_XPATH");
		Thread.sleep(1000);
		commonLib.click("Intake_Active_RR_Edit_XPATH");
		Thread.sleep(3000);
		commonLib.clickbyjavascript("Intake_Active_RR_DD_XPATH");
		Thread.sleep(1000);
		commonLib.clickWithDynamicValue("Intake_Active_RR_DD_Data_XPATH", activeRR);
		Thread.sleep(2000);
		commonLib.click("Intake_Contact_Save_Btn_XPATH");
		Thread.sleep(4000);
		commonLib.waitForPresenceOfElementLocated("Intake_Active_RR_Check_XPATH");
		if (commonLib.getText("Intake_Active_RR_Check_XPATH").equalsIgnoreCase(activeRR)) {
			commonLib.log(LogStatus.PASS, "Step 3: Active Risk Rating is: " + activeRR);
			commonLib.getScreenshot();
		} else {
			commonLib.log(LogStatus.FAIL, "Step 3: Active Risk Rating is not  " + activeRR);
			commonLib.getScreenshot();
		}
		Thread.sleep(3000);
		globalSearch(MasterForm);
		Thread.sleep(4000);
		commonLib.click("Intake_Edit_Btn_XPATH");
		commonLib.click("Intake_Approver_Action_XPATH");
		Thread.sleep(2000);
		commonLib.clickWithDynamicValue("Intake_Approver_Option_XPATH", ApproveOption);
		commonLib.sendKeys("Intake_Approve_Comments_XPATH", Comments);
		commonLib.sendKeys("Intake_Approve_Comments_Internal_XPATH", InternalComments);
		Thread.sleep(2000);
		commonLib.waitforElement("Intake_Complete_Review_XPATH");
		commonLib.clickbyjavascript("Intake_Complete_Review_XPATH");
		Thread.sleep(7000);
		// commonLib.waitforInvisibilityOfElement("Intake_Complete_Review_XPATH");
		commonLib.waitForVisibilityOf("Intake_Form_Status_XPATH");
		commonLib.waitforElement("Intake_Form_Status_XPATH");
		if (commonLib.getText("Intake_Form_Status_XPATH").equalsIgnoreCase("Approved")) {
			commonLib.log(LogStatus.PASS, "Step 19: Master Form Status is Approved");
			commonLib.getScreenshot();
		} else {
			commonLib.log(LogStatus.FAIL, "Step 19: Master Form Status is not Approved");
			commonLib.getScreenshot();
		}

		return allDetails;

	}

	public String preIntake_MasterForm_Submission(List<String> lstRowData, List<String> headerRow, String workBook,
			String workSheet, String FormNumber) throws Exception {

		commonLib.log(LogStatus.INFO, "PreIntake Approval process has been started");
		String Run_Status = lstRowData.get(getColumnNumberFromList(headerRow, "Run"));
		String[] ICUser = lstRowData.get(getColumnNumberFromList(headerRow, "IC User")).split("\\|");
		String UserLicense = lstRowData.get(getColumnNumberFromList(headerRow, "User License"));
		String Profile = lstRowData.get(getColumnNumberFromList(headerRow, "Profile"));
		String LicenseAllocation = lstRowData.get(getColumnNumberFromList(headerRow, "License Allocation"));
		String ApproveOption = lstRowData.get(getColumnNumberFromList(headerRow, "Approve Option"));
		String Comments = lstRowData.get(getColumnNumberFromList(headerRow, "Comments"));
		String InternalComments = lstRowData.get(getColumnNumberFromList(headerRow, "Comments"));
		// String FormStatus = lstRowData.get(sfdcLib.getColumnNumberFromList(headerRow,
		// "IC User"));
		String Country = lstRowData.get(getColumnNumberFromList(headerRow, "Country"));
		String State = lstRowData.get(getColumnNumberFromList(headerRow, "State"));
		String Website = lstRowData.get(getColumnNumberFromList(headerRow, "WebSite"));
		// String StockExchange =
		// lstRowData.get(sfdcLib.getColumnNumberFromList(headerRow, "IC User"));
		String YearEstablished = lstRowData.get(getColumnNumberFromList(headerRow, "Year Established"));
		// String FormNumber = lstRowData.get(getColumnNumberFromList(headerRow, "Form
		// Number"));
		// String Contact = lstRowData.get(sfdcLib.getColumnNumberFromList(headerRow,
		// "Contact"));
		String activeRR = lstRowData.get(getColumnNumberFromList(headerRow, "Active RR"));
		String AccountName = "Entity_Script_" + commonLib.getRandomNumber(4);
		String filepath = System.getProperty("user.dir") + "\\images\\Test Doc.pdf";

		commonLib.launchURL("https://stryker--sit2.lightning.force.com/lightning/setup/SetupOneHome/home");

		commonLib.waitForVisibilityOf("//a[text()='Setup Home']");

		approvePreIntakeForm(ICUser[1], ApproveOption, InternalComments, FormNumber);
		Thread.sleep(2000);
		commonLib.waitforElement("Intake_ContactName_XPATH");
		commonLib.clickbyjavascript("Intake_ContactName_XPATH");
		Thread.sleep(2000);
		commonLib.waitForVisibilityOf("Intake_Contact_More_DD_XPATH");
		commonLib.log(LogStatus.PASS, "Step 4: Navigated to Contact.");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		commonLib.waitforElement("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Enable_Customer_XPATH");
		commonLib.switchtoFrame("Intake_Iframe_Contact_XPATH");
		String Username = "Sample" + commonLib.getRandomNumber(4) + "@xyz.com";
		commonLib.clear("Intake_Username_XPATH");
		Thread.sleep(2000);
		commonLib.sendKeys("Intake_Username_XPATH", Username);
		commonLib.clear("Intake_NickName_XPATH");
		commonLib.sendKeys("Intake_NickName_XPATH", "Sam" + commonLib.getRandomNumber(4));
		commonLib.selectDropdownVisibleText("Intake_User_License_XPATH", UserLicense);
		Thread.sleep(1000);
		commonLib.selectDropdownVisibleText("Intake_Profile_XPATH", Profile);
		Thread.sleep(1000);
		commonLib.scrollDownToElement("Intake_Additional_Info_XPATH", "field");
		commonLib.selectDropdownVisibleText("Intake_License_Allocation_XPATH", LicenseAllocation);
		Thread.sleep(1000);
		commonLib.click("Intake_Save_Btn_XPATH");
		// commonLib.alertAccept();
		// commonLib.waitforInvisibilityOfElement("Intake_Save_Btn_XPATH");
		commonLib.log(LogStatus.PASS, "Step 5: Enabled the Customer User");
		commonLib.getScreenshot();
		Thread.sleep(1000);

		App_Launcher_Search("IC Hub");
		Thread.sleep(6000);
		commonLib.waitForPageToLoad();
		globalSearch(FormNumber);
		commonLib.waitforElement("Intake_Release_Form_XPATH");
		commonLib.clickbyjavascript("Intake_Release_Form_XPATH");
		Thread.sleep(3000);
		commonLib.log(LogStatus.PASS, "Step 6: Form is Released to IC");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		logout();
		Thread.sleep(2000);
		App_Launcher_Search("IC Hub");
		Thread.sleep(2000);
		commonLib.waitForPageToLoad();
		globalSearch(FormNumber);
		commonLib.waitforElement("Intake_ContactName_XPATH");
		commonLib.clickbyjavascript("Intake_ContactName_XPATH");
		Thread.sleep(3000);
		try {
			commonLib.waitForElementToBeClickable("Intake_Contact_More_DD_XPATH");
		} catch (AssertionError e) {

		}
		commonLib.clickbyjavascript("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_Login_XPATH");
		commonLib.click("Intake_ICHUB_Community_XPATH");
		Thread.sleep(1000);
		commonLib.log(LogStatus.PASS, "Step 7: Logged in as Community User");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		commonLib.waitForPageToLoad();
		commonLib.waitforElement("Intake_Contact_Form_XPATH");
		commonLib.click("Intake_Contact_Form_XPATH");
		commonLib.waitForPageToLoad();
		commonLib.clickbyjavascript("Intake_List_View_XPATH");
		commonLib.clickbyjavascript("Intake_List_All_XPATH");
		Thread.sleep(6000);
		String MasterForm = commonLib.getText("Intake_Master_Form_XPATH");
		commonLib.clickWithDynamicValue("Intake_Form_List_XPATH", MasterForm);
		commonLib.waitForPageToLoad();
		commonLib.log(LogStatus.PASS, "Step 8: Navigated to master Form");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		commonLib.waitforElement("Intake_Form_Edit_XPATH");
		commonLib.clickbyjavascript("Intake_TnC_Checkbox1_XPATH");
		commonLib.clickbyjavascript("Intake_TnC_Checkbox2_XPATH");
		commonLib.clickbyjavascript("Intake_TnC_Checkbox3_XPATH");
		Thread.sleep(1000);
		commonLib.log(LogStatus.PASS,
				"Step 9: Checkbox for accepting terms and condition is visible. (142282, 141171, 143935)");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		commonLib.click("Intake_Proceed_XPATH");
		commonLib.waitForElementToBeClickable("Intake_Form_Edit_XPATH");
		commonLib.clickbyjavascript("Intake_Form_Edit_XPATH");
		Thread.sleep(2000);
		commonLib.click("Intake_General_Questions_XPATH");
		Thread.sleep(3000);
		commonLib.sendKeys("Intake_Company_Name_XPATH", AccountName);
		String RegNum = commonLib.getRandomNumber(5);
		Thread.sleep(3000);
		commonLib.sendKeys("Intake_Company_Registration_Number_XPATH", RegNum);
		Thread.sleep(2000);
		commonLib.clickbyjavascript("Intake_Country_XPATH");
		commonLib.clickWithDynamicValue("Intake_Country_Data_XPATH", Country);
		commonLib.sendKeys("Intake_Street_XPATH", "test");
		commonLib.sendKeys("Intake_City_XPATH", "test");
		commonLib.click("Intake_State_XPATH");
		commonLib.clickWithDynamicValue("Intake_State_Data_XPATH", State);
		commonLib.sendKeys("Intake_Zip_Postal_XPATH", commonLib.getRandomNumber(4));
		commonLib.scroll_view("Intake_Office_Number_XPATH");
		commonLib.sendKeys("Intake_Office_Number_XPATH", "+91" + commonLib.getRandomNumber(9));
		commonLib.sendKeys("Intake_Website_Address_XPATH", Website);
		Thread.sleep(2000);
		commonLib.sendKeys("Intake_Email_For_Business_Communication_XPATH",
				"Sample" + commonLib.getRandomNumber(4) + "@xyz.com");
		commonLib.log(LogStatus.PASS, "Step 10: Completed Madatory Fields for 'General Questions' Section");

		commonLib.scrollDownToElement("Intake_Contact_Information_XPATH", "Button");
		commonLib.click("Intake_Contact_Information_XPATH");
		Thread.sleep(2000);
		commonLib.waitforElement("Intake_Contact_Verify_XPATH");
		// if(commonLib.getText("Intake_Contact_Verify_XPATH").equalsIgnoreCase(Contact))
		// {
		// commonLib.log(LogStatus.PASS, "Step 11: Contact is same as in Pre-Intake
		// Form");
		// } else {
		// commonLib.log(LogStatus.FAIL, "Step 11: Contact is different from Pre-Intake
		// Form");
		// }
		commonLib.click("Intake_Edit_Contact_XPATH");
		commonLib.waitforElement("Intake_Contact_Type_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_Type_XPATH");
		commonLib.clickWithDynamicValue("Intake_Contact_Type_Data_XPATH", "Main contact");
		commonLib.clickbyjavascript("Intake_Title_XPATH");
		commonLib.clickWithDynamicValue("Intake_Title_Data_XPATH", "Mr.");
		// commonLib.waitforElement("Intake_CheckBox_Training_Required_XPATH");
		// commonLib.clickbyjavascript("Intake_CheckBox_Training_Required_XPATH");
		Thread.sleep(2000);
		commonLib.sendKeys("Intake_Job_Title_XPATH", "Test");
		String Cell = "+91" + commonLib.getRandomNumber(10);
		Thread.sleep(2000);
		commonLib.scrollDownToElement("Intake_Job_Title_XPATH", "Input");
		commonLib.sendKeys("Intake_Contact_Cell_Number_XPATH", Cell);
		Thread.sleep(2000);
		commonLib.click("Intake_Contact_Save_Btn_XPATH");
		commonLib.waitForPresenceOfElementLocated("Intake_Ownership_XPATH");
		commonLib.log(LogStatus.PASS, "Step 11: Filled the Contact Information");
		commonLib.getScreenshot();
		Thread.sleep(4000);
		commonLib.scrollDownToElement("Intake_Ownership_XPATH", "Button");
		commonLib.click("Intake_Ownership_XPATH");
		// commonLib.scrollDownToElement("Intake_Company_Details_XPATH", "Button");
		commonLib.click("Intake_Company_Publicly_XPATH");
		commonLib.clickWithDynamicValue("Intake_Company_Publicly_Data_XPATH", "No");
		Thread.sleep(1000);
		commonLib.click("Intake_Company_Publicly_Add_Row_XPATH");
		Thread.sleep(3000);
		commonLib.waitForPresenceOfElementLocated("Intake_Shareholders_Above_XPATH");
		commonLib.waitForVisibilityOf("Intake_Shareholders_Above_XPATH");
		commonLib.clickbyjavascript("Intake_Shareholders_Above_XPATH");
		Thread.sleep(1000);
		commonLib.click("Intake_Shareholders_Above_Data_XPATH");
		// commonLib.clickWithDynamicValue("Intake_Shareholders_Above_Data_XPATH",
		// "Owner/ Principal/ Shareholder Above 10%");
		Thread.sleep(1000);
		commonLib.clickbyjavascript("Intake_Title_XPATH");
		commonLib.clickWithDynamicValue("Intake_Title_Data_XPATH", "Mr.");
		commonLib.sendKeys("Intake_First_Name_Shareholder_XPATH", "First_" + commonLib.getRandomNumber(4));
		commonLib.sendKeys("Intake_Last_Name_Shareholder_XPATH", "Last_" + commonLib.getRandomNumber(4));
		commonLib.sendKeys("Intake_Email_Shareholder_XPATH", "sample" + commonLib.getRandomNumber(4) + "@xyz.com");
		// commonLib.waitforElement("Intake_CheckBox_Training_Required_XPATH");
		// commonLib.clickbyjavascript("Intake_CheckBox_Training_Required_XPATH");
		commonLib.sendKeys("Intake_Passport_Number_Shareholder_XPATH", commonLib.getRandomNumber(5));
		commonLib.sendKeys("Intake_Passport_Country_Shareholder_XPATH", "Argentina");
		commonLib.scrollDownToElement("Intake_Healthcare_Question_XPATH", "Input");
		commonLib.clickbyjavascript("Intake_Healthcare_Question_XPATH");
		commonLib.clickWithDynamicValue("Intake_Healthcare_Question_Data_XPATH", "No");
		commonLib.scrollDownToElement("Intake_Involvement_Stryker_XPATH", "Input");
		commonLib.clickbyjavascript("Intake_Involvement_Stryker_XPATH");
		commonLib.clickWithDynamicValue("Intake_Involvement_Stryker_Data_XPATH", "No");
		commonLib.click("Intake_Contact_Save_Btn_XPATH");
		Thread.sleep(6000);
		// commonLib.waitforInvisibilityOfElement("Intake_Contact_Save_Btn_XPATH");
		// commonLib.waitForPresenceOfElementLocated("Intake_Contact_Save_Btn_XPATH");
		commonLib.scrollDownToElement("Intake_Company_Publicly_Add_Row_XPATH", "Button");
		Thread.sleep(2000);
		uploadFileDirectly("Intake_Upload_Doc_Owners_XPATH", filepath);
		commonLib.waitforElement("Contract_Upload_Done_XPATH");
		commonLib.click("Contract_Upload_Done_XPATH");
		Thread.sleep(2000);
		commonLib.click("Intake_General_Questions_XPATH");
		Thread.sleep(2000);
		commonLib.scrollDownToElement("Intake_Company_Publicly_Add_Row_XPATH", "Button");
		// commonLib.click("Intake_Stock_Exchange_XPATH");
		// commonLib.clickWithDynamicValue("Intake_Stock_Exchange_Data_XPATH",
		// StockExchange);
		commonLib.clickbyjavascript("Intake_Association_With_Stryker_XPATH");
		commonLib.clickWithDynamicValue("Intake_Association_With_Stryker_Data_XPATH", "No");
		commonLib.log(LogStatus.PASS, "Step 12: Completed Madatory Fields for 'Ownership' Section");

		commonLib.scrollDownToElement("Intake_Company_Details_XPATH", "Button");
		commonLib.click("Intake_Company_Details_XPATH");
		commonLib.scrollDownToElement("Intake_Company_Details_XPATH", "Button");
		Thread.sleep(1000);
		commonLib.sendKeys("Intake_Company_Eshtblished_XPATH", YearEstablished);
		commonLib.click("Intake_Different_Registered_Name_XPATH");
		Thread.sleep(1000);
		commonLib.clickWithDynamicValue("Intake_Different_Registered_Name_Data_XPATH", "No");
		Thread.sleep(1000);
		commonLib.scrollDownToElement("Intake_Employed_XPATH", "Button");
		commonLib.clickbyjavascript("Intake_Employed_XPATH");
		Thread.sleep(2000);
		commonLib.clickWithDynamicValue("Intake_Employed_Data_XPATH", "1-5");
		commonLib.clickbyjavascript("Intake_Work_Related_Stryker_XPATH");
		Thread.sleep(1000);
		commonLib.clickWithDynamicValue("Intake_Work_Related_Stryker_Data_XPATH", "1-5");
		Thread.sleep(2000);
		commonLib.click("Intake_Financial_Terms_JR_Sales_XPATH");
		commonLib.click("Intake_Franchise_Movetoselected1_XPATH");
		Thread.sleep(2000);
		commonLib.click("Intake_Financial_Terms_JR_Invoices_XPATH");
		commonLib.click("Intake_Franchise_Movetoselected1_XPATH");
		Thread.sleep(2000);
		commonLib.click("Intake_Financial_Terms_JR_Owns_XPATH");
		commonLib.click("Intake_Franchise_Movetoselected1_XPATH");
		Thread.sleep(2000);
		commonLib.scrollDownToElement("Intake_Franchise_Movetoselected1_XPATH", "button");
		commonLib.click("Intake_Work_Experience_XPATH");
		commonLib.clickWithDynamicValue("Intake_Work_Experience_Data_XPATH", "1-2");
		Thread.sleep(2000);
		commonLib.click("Intake_Medical_Companies_XPATH");
		commonLib.click("Intake_Franchise_Movetoselected2_XPATH");
		commonLib.scrollDownToElement("Intake_Franchise_Movetoselected2_XPATH", "Button");
		Thread.sleep(2000);
		commonLib.click("Intake_Company_Certifications_XPATH");
		commonLib.clickWithDynamicValue("Intake_Company_Certifications_Data_XPATH", "No");
		commonLib.click("Intake_Intention_Engage_XPATH");
		commonLib.clickWithDynamicValue("Intake_Intention_Engage_Data_XPATH", "No");
		Thread.sleep(2000);
		commonLib.log(LogStatus.PASS, "Step 13: Completed Madatory Fields for 'Company Details' Section");
		commonLib.scrollDownToElement("Intake_Save_For_Later_XPATH", "Button");
		commonLib.click("Intake_Save_For_Later_XPATH");
		commonLib.waitForVisibilityOf("Intake_Form_Edit_XPATH");
		Thread.sleep(2000);
		commonLib.log(LogStatus.PASS, "Step 14: Form Filled and Saved for later.");
		commonLib.getScreenshot();
		Thread.sleep(2000);

		commonLib.waitforElement("Intake_Form_Edit_XPATH");
		commonLib.click("Intake_Form_Edit_XPATH");
		commonLib.waitforElement("Intake_Review_Checkbox_XPATH");
		commonLib.clickbyjavascript("Intake_Review_Checkbox_XPATH");
		Thread.sleep(2000);
		commonLib.waitforElement("Submit_Review_XPATH");
		commonLib.click("Submit_Review_XPATH");
		Thread.sleep(8000);
		// commonLib.waitforInvisibilityOfElement("Submit_Review_XPATH");
		commonLib.waitforElement("Intake_Form_Edit_XPATH");
		commonLib.clickbyjavascript("Intake_Form_Edit_XPATH");
		Thread.sleep(3000);
		if (commonLib.waitForPresenceOfElementLocated("Intake_Approve_Check_XPATH")) {
			commonLib.log(LogStatus.PASS, "Step 15: Master Form is submitted for review");
			commonLib.getScreenshot();
		} else {
			commonLib.log(LogStatus.FAIL, "Step 16: Master Form is not submitted for review");
			commonLib.getScreenshot();
		}
		Thread.sleep(3000);
		commonLib.click("Intake_Close_Window_XPATH");

		commonLib.waitforElement("Intake_Customer_User_XPATH");
		Thread.sleep(3000);
		commonLib.clickbyjavascript("Intake_Customer_User_XPATH");
		commonLib.click("Intake_Customer_User_Logout_XPATH");
		Thread.sleep(8000);

		return MasterForm;

	}

	private void approvePreIntakeForm(String user, String ApproveOption, String InternalComments, String FormNumber)
			throws InterruptedException, IOException {

		search_Profile_GXICM(user);
		Thread.sleep(2000);
		commonLib.waitForVisibilityOf("Logout_Link_In_Home_Page_XPATH");
		commonLib.log(LogStatus.PASS, "Step 1: Logged in as ICM Local.");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		globalSearch(FormNumber);

		Thread.sleep(2000);
		commonLib.log(LogStatus.PASS, "Step 2: Navigated to Form: " + FormNumber);
		commonLib.getScreenshot();
		Thread.sleep(1000);
		try {
			commonLib.waitForElementToBeClickable("Intake_Edit_Btn_XPATH");
		} catch (AssertionError e) {

		}
		commonLib.clickbyjavascript("Intake_Edit_Btn_XPATH");
		commonLib.click("Intake_Approver_Action_XPATH");
		Thread.sleep(2000);
		commonLib.clickWithDynamicValue("Intake_Approver_Option_XPATH", ApproveOption);
		// commonLib.sendKeys("Intake_Approve_Comments_XPATH", Comments);
		// Thread.sleep(2000);
		commonLib.sendKeys("Intake_Approve_Comments_Internal_XPATH", InternalComments);
		Thread.sleep(5000);
		commonLib.waitforElement("Intake_Complete_Review_XPATH");
		commonLib.click("Intake_Complete_Review_XPATH");
		commonLib.waitForVisibilityOf("Intake_Form_Status2_XPATH");
		commonLib.waitforElement("Intake_Form_Status2_XPATH");
		if (commonLib.getText("Intake_Form_Status2_XPATH").equalsIgnoreCase(ApproveOption)) {
			commonLib.log(LogStatus.PASS, "Step 3: Form Status is Approved. (141170)");
			commonLib.getScreenshot();
		} else {
			commonLib.log(LogStatus.FAIL, "Step 3: Form Status is not Approved");
			commonLib.getScreenshot();
		}
	}

	public void enableContactAsCustomer(String user, String UserLicense, String Profile, String LicenseAllocation)
			throws InterruptedException {

		commonLib.clickbyjavascript("Intake_PreIntake_Account_Name_XPATH");
		Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Related Contacts");
		commonLib.refreshPage();
		commonLib.log(LogStatus.PASS, "Step 12: Navigated to Related Contacts.");
		commonLib.getScreenshot();
		commonLib.WaitforPresenceofElement_Dynamic_Xpath("Intake_Related_Contacts_XPATH", user);
		commonLib.clickWithDynamicValue("Intake_Related_Contacts_XPATH", user);
		commonLib.waitforElement("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Enable_Customer_XPATH");
		commonLib.switchtoFrame("Intake_Iframe_Contact_XPATH");
		String Username1 = "Sample" + commonLib.getRandomNumber(4) + "@xyz.com";
		commonLib.clear("Intake_Username_XPATH");
		Thread.sleep(2000);
		commonLib.sendKeys("Intake_Username_XPATH", Username1);
		commonLib.clear("Intake_NickName_XPATH");
		commonLib.sendKeys("Intake_NickName_XPATH", "Sam" + commonLib.getRandomNumber(4));
		commonLib.selectDropdownVisibleText("Intake_User_License_XPATH", UserLicense);
		Thread.sleep(1000);
		commonLib.selectDropdownVisibleText("Intake_Profile_XPATH", Profile);
		Thread.sleep(1000);
		commonLib.scrollDownToElement("Intake_Additional_Info_XPATH", "field");
		commonLib.selectDropdownVisibleText("Intake_License_Allocation_XPATH", LicenseAllocation);
		Thread.sleep(1000);
		commonLib.click("Intake_Save_Btn_XPATH");
		Thread.sleep(2000);
		// commonLib.alertAccept();
		commonLib.log(LogStatus.PASS, "Step 13: Enabled " + user + " Contact as Customer User");
		commonLib.getScreenshot();
	}

	public void approveRejectPreIntakeForm(String user, String option, String InternalComments, String FormNumber)
			throws InterruptedException, IOException {

		search_Profile_GXICM(user);
		Thread.sleep(2000);
		commonLib.waitForVisibilityOf("Logout_Link_In_Home_Page_XPATH");
		commonLib.log(LogStatus.PASS, "Step 1: Logged in as ICM Local.");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		globalSearch(FormNumber);

		Thread.sleep(2000);
		commonLib.log(LogStatus.PASS, "Step 2: Navigated to Form: " + FormNumber);
		commonLib.getScreenshot();
		Thread.sleep(1000);
		try {
			commonLib.waitForElementToBeClickable("Intake_Edit_Btn_XPATH");
		} catch (AssertionError e) {

		}
		commonLib.clickbyjavascript("Intake_Edit_Btn_XPATH");
		commonLib.click("Intake_Approver_Action_XPATH");
		Thread.sleep(2000);
		commonLib.clickWithDynamicValue("Intake_Approver_Option_XPATH", option);
		// commonLib.sendKeys("Intake_Approve_Comments_XPATH", Comments);
		// Thread.sleep(2000);
		commonLib.sendKeys("Intake_Approve_Comments_Internal_XPATH", InternalComments);
		Thread.sleep(5000);
		commonLib.waitforElement("Intake_Complete_Review_XPATH");
		commonLib.click("Intake_Complete_Review_XPATH");
		commonLib.waitForVisibilityOf("Intake_Form_Status2_XPATH");
		commonLib.waitforElement("Intake_Form_Status2_XPATH");
		if (commonLib.getText("Intake_Form_Status2_XPATH").equalsIgnoreCase(option)) {
			commonLib.log(LogStatus.PASS, "Step 3: Form Status is " + option);
			commonLib.getScreenshot();
		} else {
			commonLib.log(LogStatus.FAIL, "Step 3: Form Status is not " + option);
			commonLib.getScreenshot();
		}

	}

	public void releaseFunctionalForms_ICS() throws InterruptedException {
		commonLib.waitforElement("Intake_Release_Functional_Forms2_XPATH");
		commonLib.click("Intake_Release_Functional_Forms2_XPATH");
		/*
		 * commonLib.waitforInvisibilityOfElement(
		 * "Intake_Release_Functional_Forms2_XPATH");
		 */
		commonLib.log(LogStatus.PASS, "Step 5: Master Form Released to IC");
		commonLib.getScreenshot();

	}

	public boolean verifyAllFunctionalForms(String formNumber) throws IOException, InterruptedException {
		logout();
		commonLib.log(LogStatus.PASS, "Step 6: Logged out from ICM Local.");
		commonLib.getScreenshot();
		Thread.sleep(7000);
		App_Launcher_Search("IC Hub");
		Thread.sleep(3000);
		globalSearch(formNumber);
		commonLib.waitforElement("Intake_ContactName_XPATH");
		commonLib.clickbyjavascript("Intake_ContactName_XPATH");
		Thread.sleep(3000);
		commonLib.waitforElement("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_Login_XPATH");
		commonLib.click("Intake_ICHUB_Community_XPATH");
		commonLib.log(LogStatus.PASS, "Step 7: Login as Customer User (Contact)");
		commonLib.getScreenshot();
		commonLib.waitForPageToLoad();
		commonLib.waitforElement("Intake_Contact_Form_XPATH");
		commonLib.click("Intake_Contact_Form_XPATH");
		commonLib.waitForPageToLoad();
		commonLib.clickbyjavascript("Intake_List_View_XPATH");
		commonLib.clickbyjavascript("Intake_List_Active_Forms_XPATH");
		commonLib.waitForPresenceOfElementLocated("Intake_Compliance_Form_XPATH");
		if (commonLib.waitForElementToBeClickable("Intake_Compliance_Form_XPATH")) {
			commonLib.getScreenshot();
			return true;
		} else {
			commonLib.getScreenshot();
			return false;
		}

	}

	public boolean verifyErrorMessageDuringReleaseForms() {
		try {
			if (commonLib.waitForPresenceOfElementLocated("Intake_Error_Release_Forms2_XPATH")) {
				commonLib.getScreenshot();
				return true;
			} else {
				commonLib.getScreenshot();
				return false;
			}

		} catch (Exception e) {
			return false;
		}

	}

	public boolean verifyFunctionalFormBRQuestionaire() {
		String FinanceForm = commonLib.getText("Intake_Finance_Form_XPATH");
		commonLib.clickWithDynamicValue("Intake_Form_List_XPATH", FinanceForm);
		commonLib.waitForPageToLoad();
		commonLib.log(LogStatus.PASS, "Step 14: Navigated to Finance Form");
		commonLib.getScreenshot();
		commonLib.waitForPageToLoad();
		if (commonLib.waitForPresenceOfElementLocated("Intake_BR_Questionnaire_XPATH")) {
			commonLib.getScreenshot();
			return true;
		} else {

			commonLib.getScreenshot();
			return false;
		}
	}

	public void updatePaymentTerms(String icUser, String formNumber, String paymentTerm)
			throws InterruptedException, IOException {
		Thread.sleep(5000);
		search_Profile_GXICM(icUser);
		commonLib.log(LogStatus.PASS, "Step 14: Login to IC-HUB as " + icUser);
		commonLib.waitForPageToLoad();
		Thread.sleep(2000);
		globalSearch(formNumber);
		commonLib.log(LogStatus.PASS, "Step 15: Navigated to Form: " + formNumber);
		commonLib.getScreenshot();
		commonLib.waitForElementToBeClickable("Intake_Edit_Btn_XPATH");
		commonLib.click("Intake_Edit_Btn_XPATH");
		commonLib.waitForPageToLoad();
		Thread.sleep(3000);
		commonLib.clickbyjavascript("Intake_Contract_Ques2_XPATH");
		Thread.sleep(2000);
		commonLib.click("Intake_Proposed_Payment2_XPATH");
		commonLib.clickWithDynamicValue("Intake_Proposed_Payment_Data_XPATH", paymentTerm);
		Thread.sleep(3000);
		commonLib.click("Intake_Save_For_Later_XPATH");
		commonLib.log(LogStatus.PASS, "Step 16: Payment Terms Updated and Saved.");
		commonLib.getScreenshot();
		Thread.sleep(3000);
		commonLib.waitforElement("PreIntakeEdit_XPATH");
		commonLib.click("PreIntakeEdit_XPATH");
		commonLib.waitforElement("Submit_Review_XPATH");
		commonLib.click("Submit_Review_XPATH");
		commonLib.waitforInvisibilityOfElement("Submit_Review_XPATH");
		commonLib.waitForPageToLoad();
		Thread.sleep(3000);
		logout();
	}

	public boolean verifyPreIntakeFormStatus(String user, String formNumber, String status)
			throws InterruptedException, IOException {
		search_Profile_GXICM(user);
		commonLib.log(LogStatus.PASS, "Step 20: Logged in to IC-HUB as IC Sponsor");
		commonLib.waitForPageToLoad();
		Thread.sleep(2000);
		globalSearch(formNumber);
		commonLib.log(LogStatus.PASS, "Step 21: Navigated to Form: " + formNumber);
		commonLib.getScreenshot();
		commonLib.waitForPresenceOfElementLocated("Intake_Form_Status2_XPATH");
		if (commonLib.getText("Intake_Form_Status2_XPATH").equalsIgnoreCase(status)) {
			commonLib.getScreenshot();
			return true;
		} else {
			commonLib.getScreenshot();
			return false;
		}
	}

	public boolean verifyAllFormCreationAndCompletionDates(String account, String icUser)
			throws InterruptedException, IOException {

		try {

			search_Profile_GXICM(icUser);
			Thread.sleep(2000);
			commonLib.log(LogStatus.PASS, "Step 1: Logged in as " + icUser);
			globalSearch(account);
			commonLib.waitForPageToLoad();
			commonLib.log(LogStatus.PASS, "Step 2: Navigated to Account: " + account);
			commonLib.getScreenshot();
			Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Active Intake Forms");
			// commonLib.waitForElementToBeClickable("Intake_Form_Active_Lookup_XPATH");
			// commonLib.click("Intake_Form_Active_Lookup_XPATH");
			Thread.sleep(2000);
			commonLib.log(LogStatus.PASS, "Step 3: Navigated to Form Active Lookup List View");
			commonLib.getScreenshot();
			commonLib.waitForElementToBeClickable("Intake_Form_Active_PreIntake_XPATH");
			commonLib.click("Intake_Form_Active_PreIntake_XPATH");
			commonLib.waitForPresenceOfElementLocated("Intake_Form_Created_Date_XPATH");
			String Created = commonLib.getText("Intake_Form_Created_Date_XPATH");
			String Completed = commonLib.getText("Intake_Form_Completed_Date_XPATH");
			commonLib.log(LogStatus.PASS,
					"Step 4: PreIntake Form Created Date: " + Created + " and Completed Date: " + Completed);
			commonLib.getScreenshot();
			commonLib.refreshPage();
			commonLib.clickbyjavascript("Intake_PreIntake_Account_Name_XPATH");
			Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Active Intake Forms");
			// commonLib.waitForElementToBeClickable("Intake_Form_Active_Lookup_XPATH");
			// commonLib.click("Intake_Form_Active_Lookup_XPATH");
			Thread.sleep(2000);
			commonLib.waitForElementToBeClickable("Intake_Form_Active_Master_XPATH");
			commonLib.click("Intake_Form_Active_Master_XPATH");
			commonLib.waitForPresenceOfElementLocated("Intake_Form_Created_Date_XPATH");
			Created = commonLib.getText("Intake_Form_Created_Date_XPATH");
			Completed = commonLib.getText("Intake_Form_Completed_Date_XPATH");
			commonLib.log(LogStatus.PASS,
					"Step 5: Master Form Created Date: " + Created + " and Completed Date: " + Completed);
			commonLib.getScreenshot();
			commonLib.refreshPage();
			commonLib.clickbyjavascript("Intake_PreIntake_Account_Name_XPATH");
			Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Active Intake Forms");
			// commonLib.waitForElementToBeClickable("Intake_Form_Active_Lookup_XPATH");
			// commonLib.click("Intake_Form_Active_Lookup_XPATH");
			Thread.sleep(2000);
			commonLib.waitForElementToBeClickable("Intake_Form_Active_RAQA_XPATH");
			commonLib.click("Intake_Form_Active_RAQA_XPATH");
			commonLib.waitForPresenceOfElementLocated("Intake_Form_Created_Date_XPATH");
			Created = commonLib.getText("Intake_Form_Created_Date_XPATH");
			Completed = commonLib.getText("Intake_Form_Completed_Date_XPATH");
			commonLib.log(LogStatus.PASS,
					"Step 6: RAQA Form Created Date: " + Created + " and Completed Date: " + Completed);
			commonLib.getScreenshot();
			commonLib.refreshPage();
			commonLib.clickbyjavascript("Intake_PreIntake_Account_Name_XPATH");
			Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Active Intake Forms");
			// commonLib.waitForElementToBeClickable("Intake_Form_Active_Lookup_XPATH");
			// commonLib.click("Intake_Form_Active_Lookup_XPATH");
			Thread.sleep(2000);
			commonLib.waitForElementToBeClickable("Intake_Form_Active_Compliance_XPATH");
			commonLib.click("Intake_Form_Active_Compliance_XPATH");
			commonLib.waitForPresenceOfElementLocated("Intake_Form_Created_Date_XPATH");
			Created = commonLib.getText("Intake_Form_Created_Date_XPATH");
			Completed = commonLib.getText("Intake_Form_Completed_Date_XPATH");
			commonLib.log(LogStatus.PASS,
					"Step 7: Compliance Form Created Date: " + Created + " and Completed Date: " + Completed);
			commonLib.getScreenshot();
			commonLib.refreshPage();
			commonLib.clickbyjavascript("Intake_PreIntake_Account_Name_XPATH");
			Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Active Intake Forms");
			// commonLib.waitForElementToBeClickable("Intake_Form_Active_Lookup_XPATH");
			// commonLib.click("Intake_Form_Active_Lookup_XPATH");
			Thread.sleep(2000);
			commonLib.waitForElementToBeClickable("Intake_Form_Active_Finance_XPATH");
			commonLib.click("Intake_Form_Active_Finance_XPATH");
			commonLib.waitForPresenceOfElementLocated("Intake_Form_Created_Date_XPATH");
			Created = commonLib.getText("Intake_Form_Created_Date_XPATH");
			Completed = commonLib.getText("Intake_Form_Completed_Date_XPATH");
			commonLib.log(LogStatus.PASS,
					"Step 8: Finance Form Created Date: " + Created + " and Completed Date: " + Completed);
			commonLib.getScreenshot();
			globalSearch(account);
			commonLib.refreshPage();
			Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Active Intake Forms");
			// commonLib.waitForElementToBeClickable("Intake_Form_Active_Lookup_XPATH");
			// commonLib.click("Intake_Form_Active_Lookup_XPATH");
			Thread.sleep(2000);
			commonLib.waitForElementToBeClickable("Intake_Form_Active_BRForm_XPATH");
			commonLib.click("Intake_Form_Active_BRForm_XPATH");
			commonLib.waitForPresenceOfElementLocated("Intake_Form_Created_Date_XPATH");
			Created = commonLib.getText("Intake_Form_Created_Date_XPATH");
			Completed = commonLib.getText("Intake_Form_Completed_Date_XPATH");
			commonLib.log(LogStatus.PASS,
					"Step 9: B&R Form Created Date: " + Created + " and Completed Date: " + Completed);
			commonLib.getScreenshot();
			Thread.sleep(2000);
			logout();
			commonLib.log(LogStatus.PASS, "Step 10: Logged out as " + icUser);

			return true;
		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "verification failed due to: " + e);
			return false;
		}
	}

	public boolean verifyPreIntakeStatusDates(String icUser, String report) throws InterruptedException, IOException {
		try {

			search_Profile_GXICM(icUser);
			Thread.sleep(2000);
			commonLib.log(LogStatus.PASS, "Step 1: Logged in as " + icUser + " User.");
			commonLib.getScreenshot();
			Thread.sleep(1000);
			globalSearch(report);
			Thread.sleep(2000);
			commonLib.log(LogStatus.PASS, "Step 2: Navigated to Report: " + report);
			commonLib.getScreenshot();
			commonLib.waitForPageToLoad();
			commonLib.switchtoFrame("Intake_Report_View_Iframe_XPATH");
			commonLib.waitForPresenceOfElementLocated("Intake_Contract_Number_Report_XPATH");
			commonLib.click("Intake_Contract_Number_Report_XPATH");
			Thread.sleep(3000);
			commonLib.refreshPage();
			Thread.sleep(2000);
			commonLib.waitForPresenceOfElementLocated("Intake_Contract_Start_Date2_XPATH");
			String ContractDate = commonLib.getText("Intake_Contract_Start_Date2_XPATH");
			commonLib.log(LogStatus.PASS, " Step 3.1: Contract Start Date: " + ContractDate);
			Thread.sleep(1000);
			commonLib.navigateBack();
			commonLib.refreshPage();
			Thread.sleep(2000);
			commonLib.waitForPageToLoad();
			commonLib.switchtoFrame("Intake_Report_View_Iframe_XPATH");
			commonLib.waitForPresenceOfElementLocated("Intake_Form_Number_Report_XPATH");
			commonLib.click("Intake_Form_Number_Report_XPATH");
			commonLib.refreshPage();
			Thread.sleep(2000);
			commonLib.waitForPresenceOfElementLocated("Intake_Form_Created_Date_XPATH");
			String CreatedDate = commonLib.getText("Intake_Form_Created_Date_XPATH");
			commonLib.log(LogStatus.PASS, " Step 3.2: Form Created Date: " + CreatedDate);
			// String substr = CreatedDate.substring(0,CreatedDate.indexOf(","));
			logout();
			Thread.sleep(5000);
			return false;

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "verification failed due to: " + e);
			return false;
		}

	}

	public int getBRScore(String formNumber) throws InterruptedException, IOException {

		commonLib.waitForPresenceOfElementLocated("Global_Search_XPATH");
		globalSearch(formNumber);
		commonLib.log(LogStatus.PASS, "Step 2: Navigated to Finance Form:" + formNumber);
		commonLib.getScreenshot();
		Thread.sleep(2000);
		commonLib.waitForPageToLoad();
		commonLib.refreshPage();
		Thread.sleep(2000);
		String Score = commonLib.getText("Intake_BR_Score_Check_XPATH");
		int BRScore = Integer.parseInt(Score);
		return BRScore;
	}

	public boolean verifyDueDeligenceStatus(String icUser, String Report) throws InterruptedException, IOException {
		try {
			search_Profile_GXICM(icUser);
			commonLib.log(LogStatus.PASS, "Step 1: Logged in as " + icUser + " Local.");
			commonLib.getScreenshot();
			open_Given_Tab_In_Home_Page("Reports");
			commonLib.log(LogStatus.PASS, "Step 2: Navigated to Reports Tab.");
			commonLib.getScreenshot();
			Thread.sleep(3000);
			globalSearch(Report);
			Thread.sleep(2000);
			commonLib.log(LogStatus.PASS, "Step 3: Navigated to Report: " + Report);
			commonLib.getScreenshot();
			commonLib.waitForPageToLoad();
			commonLib.switchtoFrame("Intake_Report_View_Iframe_XPATH");
			commonLib.click("Intake_Days_To_Complete_Tab_XPATH");
			Thread.sleep(1000);
			commonLib.click("Intake_Days_To_Complete_Tab_XPATH");
			String CreatedDate = commonLib.getText("Intake_Created_Date_XPATH");
			commonLib.log(LogStatus.PASS, " Step 4.1: Created Date: " + CreatedDate);
			Thread.sleep(1000);
			String CompletionDate = commonLib.getText("Intake_Completion_Date_XPATH");
			commonLib.log(LogStatus.PASS, " Step 4.2: Completion Date: " + CompletionDate);
			Thread.sleep(1000);
			String Daystocomplete = commonLib.getText("Intake_DaysToComplete_Data_XPATH");
			commonLib.log(LogStatus.PASS, " Step 4.3: Days To Complete: " + Daystocomplete);
			commonLib.getScreenshot();
			commonLib.switchToDefaultContent();
			logout();
			Thread.sleep(2000);
			return true;

		} catch (Exception e) {
			commonLib.log(LogStatus.FAIL, "verification failed due to: " + e);
			return false;
		}
	}

	public boolean verifyBRColor(String color) {

		if (commonLib.WaitforPresenceofElement_Dynamic_Xpath("Intake_BR_Color_Code_Check_XPATH", color)) {
			return true;
		} else {
			return false;
		}
	}

	public void submit_ComplianceForm(String FormNumber, String contact) throws Exception {

		App_Launcher_Search("IC Hub");
		commonLib.log(LogStatus.PASS, "Step 1: Naigated to IC Hub.");
		commonLib.getScreenshot();
		globalSearch(FormNumber);
		commonLib.waitForPresenceOfElementLocated("Intake_PreIntake_Account_Name_XPATH");
		commonLib.clickbyjavascript("Intake_PreIntake_Account_Name_XPATH");
		Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Related Contacts");
		commonLib.refreshPage();
		commonLib.WaitforPresenceofElement_Dynamic_Xpath("Intake_Related_Contacts_XPATH", contact);
		commonLib.clickWithDynamicValue("Intake_Related_Contacts_XPATH", contact);
		commonLib.refreshPage();
		Thread.sleep(3000);
		commonLib.waitforElement("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_Login_XPATH");
		commonLib.log(LogStatus.PASS, "Step 2: Login as Customer User (Contact)");
		commonLib.getScreenshot();
		commonLib.waitForPageToLoad();
		commonLib.waitforElement("Intake_Contact_Form_XPATH");
		commonLib.click("Intake_Contact_Form_XPATH");
		commonLib.waitForPageToLoad();
		commonLib.clickbyjavascript("Intake_List_View_XPATH");
		commonLib.clickbyjavascript("Intake_List_Active_Forms_XPATH");
		Thread.sleep(2000);
		String ComplianceForm = commonLib.getText("Intake_Compliance_Form_XPATH");
		String ComplianceFormNumber = ComplianceForm;
		commonLib.clickWithDynamicValue("Intake_Form_List_XPATH", ComplianceForm);
		commonLib.log(LogStatus.PASS, "Step 2: Navigated to Compliance Form: " + ComplianceFormNumber);
		commonLib.getScreenshot();

		commonLib.waitForVisibilityOf("Intake_Form_Edit_XPATH");
		Thread.sleep(1000);
		commonLib.log(LogStatus.PASS, "Step 2: Navigated to Compliance Form: " + ComplianceFormNumber);
		commonLib.getScreenshot();
		Thread.sleep(1000);
		commonLib.waitforElement("Intake_Form_Edit_XPATH");
		commonLib.click("Intake_Form_Edit_XPATH");

		commonLib.scrollDownToElement("Intake_Indirect_Channel_Company_Information_XPATH", "Button");
		commonLib.click("Intake_Indirect_Channel_Company_Information_XPATH");
		commonLib.click("Intake_Denied_Official_License_XPATH");
		commonLib.clickWithDynamicValue("Intake_Denied_Official_License_Data_XPATH", "No");
		commonLib.click("Intake_Convicted_For_Offence_XPATH");
		commonLib.clickWithDynamicValue("Intake_Convicted_For_Offence_Data_XPATH", "No");
		commonLib.click("Intake_Company_Litigation_XPATH");
		commonLib.clickWithDynamicValue("Intake_Company_Litigation_Data_XPATH", "No");
		commonLib.scrollDownToElement("Intake_Company_Banned_XPATH", "Input");
		commonLib.click("Intake_Company_Banned_XPATH");
		commonLib.clickWithDynamicValue("Intake_Company_Banned_Data_XPATH", "No");
		commonLib.scrollDownToElement("Intake_Compliance_Program_XPATH", "Input");
		commonLib.click("Intake_Compliance_Program_XPATH");
		commonLib.clickWithDynamicValue("Intake_Compliance_Program_Data_XPATH", "No");
		commonLib.click("Intake_Conduct_Ethics_XPATH");
		commonLib.clickWithDynamicValue("Intake_Conduct_Ethics_Data_XPATH", "No");
		commonLib.click("Intake_Anti_Corruption_XPATH");
		commonLib.clickWithDynamicValue("Intake_Anti_Corruption_Data_XPATH", "No");
		commonLib.click("Intake_Publicly_Funded_Healthcare_XPATH");
		commonLib.clickWithDynamicValue("Intake_Publicly_Funded_Healthcare_Data_XPATH", "No");
		commonLib.scrollDownToElement("Intake_Directly_To_Patients_XPATH", "Input");
		commonLib.click("Intake_Directly_To_Patients_XPATH");
		commonLib.clickWithDynamicValue("Intake_Directly_To_Patients_Data_XPATH", "No");
		commonLib.click("Intake_Government_Relations_XPATH");
		commonLib.clickWithDynamicValue("Intake_Government_Relations_Data_XPATH", "No");
		commonLib.log(LogStatus.PASS,
				"Step 3: Completed Mandatory fields for 'Indirect Channel Company Information' Section");

		commonLib.scrollDownToElement("Intake_Business_Engage_Activities_XPATH", "Button");
		commonLib.click("Intake_Business_Engage_Activities_XPATH");
		commonLib.click("Intake_Meal_With_HealtCareProfessional_XPATH");
		commonLib.clickWithDynamicValue("Intake_Meal_With_HealtCareProfessional_Data_XPATH", "No");
		commonLib.click("Intake_Gift_To_HealtCareProfessional_XPATH");
		commonLib.clickWithDynamicValue("Intake_Gift_To_HealtCareProfessional_Data_XPATH", "No");
		commonLib.scrollDownToElement("Intake_Training_And_Education_XPATH", "Input");
		commonLib.click("Intake_Training_And_Education_XPATH");
		commonLib.clickWithDynamicValue("Intake_Training_And_Education_Data_XPATH", "No");
		commonLib.click("Intake_Sponsorship_XPATH");
		commonLib.clickWithDynamicValue("Intake_Sponsorship_Data_XPATH", "No");
		commonLib.log(LogStatus.PASS,
				"Step 4: Completed Mandatory fields for 'Do you and your business engage in the following activities' Section");

		commonLib.scrollDownToElement("Intake_Certification_XPATH", "Button");
		commonLib.click("Intake_Certification_XPATH");
		commonLib.click("Intake_Certification_Checkbox_XPATH");
		commonLib.log(LogStatus.PASS, "Step 5: Completed Mandatory fields for 'Certification' Section");

		commonLib.scrollDownToElement("Intake_Certify_XPATH", "Button");
		commonLib.click("Intake_Certify_XPATH");
		commonLib.click("Intake_Employ_Govt_Official_XPATH");
		commonLib.clickWithDynamicValue("Intake_Employ_Govt_Official_Data_XPATH", "I Agree");
		commonLib.scrollDownToElement("Intake_Duplicate_Payment_XPATH", "Input");
		commonLib.click("Intake_Duplicate_Payment_XPATH");
		commonLib.clickWithDynamicValue("Intake_Duplicate_Payment_Data_XPATH", "I Agree");
		commonLib.click("Intake_Violation_Of_Laws_XPATH");
		commonLib.clickWithDynamicValue("Intake_Violation_Of_Laws_Data_XPATH", "I Agree");
		commonLib.scrollDownToElement("Intake_Law_And_Regulation_XPATH", "Input");
		commonLib.click("Intake_Law_And_Regulation_XPATH");
		commonLib.clickWithDynamicValue("Intake_Law_And_Regulation_Data_XPATH", "I Agree");
		commonLib.click("Intake_Employee_Compliance_Training_XPATH");
		commonLib.clickWithDynamicValue("Intake_Employee_Compliance_Training_Data_XPATH", "I Agree");
		commonLib.log(LogStatus.PASS,
				"Step 6: Completed Mandatory fields for 'In addition, we certify the following' Section");

		commonLib.click("Intake_Save_For_Later_XPATH");
		commonLib.waitForVisibilityOf("Intake_Form_Edit_XPATH");
		Thread.sleep(1000);
		commonLib.log(LogStatus.PASS, "Step 7: Form filled and Saved for later");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		commonLib.refreshPage();
		commonLib.waitForPresenceOfElementLocated("Intake_Form_Edit_XPATH");
		commonLib.click("Intake_Form_Edit_XPATH");
		Thread.sleep(4000);
		commonLib.waitForElementToBeClickable("Intake_Review_Checkbox_XPATH");
		commonLib.clickbyjavascript("Intake_Review_Checkbox_XPATH");
		commonLib.click("Submit_Review_XPATH");
		Thread.sleep(3000);
		commonLib.waitforElement("Intake_Form_Edit_XPATH");
		commonLib.refreshPage();
		Thread.sleep(2000);
		commonLib.waitforElement("Form_Status_XPATH");
		if (commonLib.getText("Form_Status_XPATH").equalsIgnoreCase("Submitted")) {
			commonLib.log(LogStatus.PASS, "Step 8: Compliance Form Submitted for Review");
			commonLib.getScreenshot();
		} else {
			commonLib.log(LogStatus.FAIL, "Step 8: Compliance Form is not submitted for review");
			commonLib.getScreenshot();
		}
		// sfdcLib.writeToCell(workBook, workSheet, 1, lstRowData.size(),
		// ComplianceForm);
		// writeToCell(workBook, workSheet, 1, getColumnNumberFromList(headerRow,
		// "Compliance Form"), ComplianceForm);

		Thread.sleep(2000);
		commonLib.waitforElement("Intake_Customer_User_XPATH");
		commonLib.click("Intake_Customer_User_XPATH");
		commonLib.click("Intake_Customer_User_Logout_XPATH");
		commonLib.waitForPageToLoad();
		Thread.sleep(4000);
	}

	public void submit_FinanceForm(String formNumber, String contact) throws Exception {

		String BookKeeping = "We have no process for book keeping";
		String TransactionRecorded = "Once per Year";
		String PercentageofSuppDoc = "We record and retain documents for few transactions (<25%)";
		String UseOfCash = "We do not have cash transactions";
		String LevelOfDetail = "There is no or minimal expense categorization (e.g., all transactions are grouped together) in the ledger";
		String HealthcareProfessional = "Our business transactions with HCPs, Hospitals, and/or Government Officials are not documented in writing";
		String FormStatus = "Submitted";

		String filepath = System.getProperty("user.dir") + "\\images\\Test Doc.pdf";

		App_Launcher_Search("IC Hub");
		commonLib.log(LogStatus.PASS, "Step 1: Naigated to IC Hub.");
		commonLib.getScreenshot();
		globalSearch(formNumber);
		commonLib.waitForPresenceOfElementLocated("Intake_PreIntake_Account_Name_XPATH");
		commonLib.clickbyjavascript("Intake_PreIntake_Account_Name_XPATH");
		Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Related Contacts");
		commonLib.refreshPage();
		commonLib.WaitforPresenceofElement_Dynamic_Xpath("Intake_Related_Contacts_XPATH", contact);
		commonLib.clickWithDynamicValue("Intake_Related_Contacts_XPATH", contact);
		Thread.sleep(3000);
		commonLib.waitforElement("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_Login_XPATH");
		commonLib.log(LogStatus.PASS, "Step 2: Login as Customer User (Contact)");
		commonLib.getScreenshot();
		commonLib.waitForPageToLoad();
		commonLib.waitforElement("Intake_Contact_Form_XPATH");
		commonLib.click("Intake_Contact_Form_XPATH");
		commonLib.waitForPageToLoad();
		commonLib.clickbyjavascript("Intake_List_View_XPATH");
		commonLib.clickbyjavascript("Intake_List_Active_Forms_XPATH");
		Thread.sleep(2000);
		String FinanceForm = commonLib.getText("Intake_Finance_Form_XPATH");
		String FinanceFormNumber = FinanceForm;
		commonLib.clickWithDynamicValue("Intake_Form_List_XPATH", FinanceForm);
		commonLib.log(LogStatus.PASS, "Step 2: Navigated to Finance Form: " + FinanceFormNumber);
		commonLib.getScreenshot();
		commonLib.waitforElement("Intake_Form_Edit_XPATH");
		commonLib.click("Intake_Form_Edit_XPATH");
		Thread.sleep(8000);
		// commonLib.scrollDownToElement("Intake_External_Auditor_XPATH", "Button");
		commonLib.click("Intake_External_Auditor_XPATH");
		commonLib.click("Intake_External_Financial_Audit_XPATH");
		commonLib.clickWithDynamicValue("Intake_External_Financial_Audit_Data_XPATH", "No");

		commonLib.log(LogStatus.PASS, "Step 3: Completed the mandatory fields for 'External Auditor' Section.");
		commonLib.clickbyjavascript("Intake_External_Auditor_XPATH");
		Thread.sleep(1000);

		commonLib.scrollDownToElement("Intake_Credit_References_XPATH", "Button");
		commonLib.click("Intake_Credit_References_XPATH");
		// commonLib.click("Intake_Upload_Evidence_Business_Credit_XPATH");
		uploadFileDirectly("Intake_Upload_Evidence_Business_Credit_XPATH", filepath);
		commonLib.waitforElement("Contract_Upload_Done_XPATH");
		Thread.sleep(5000);
		commonLib.click("Contract_Upload_Done_XPATH");
		commonLib.log(LogStatus.PASS, "Step 4: Completed the mandatory fields for 'Credtit Reference' Section.");
		Thread.sleep(2000);
		commonLib.clickbyjavascript("Intake_Credit_References_XPATH");
		Thread.sleep(1000);
		commonLib.scrollDownToElement("Intake_BR_Questionnaire_XPATH", "Button");
		commonLib.click("Intake_BR_Questionnaire_XPATH");
		commonLib.click("Intake_Book_Keeping_XPATH");
		commonLib.clickWithDynamicValue("Intake_Book_Keeping_Data_XPATH", BookKeeping);
		commonLib.click("Intake_Transaction_Recorded_XPATH");
		commonLib.clickWithDynamicValue("Intake_Transaction_Recorded_Data_XPATH", TransactionRecorded);
		commonLib.click("Intake_Supporting_Documentation_XPATH");
		commonLib.clickWithDynamicValue("Intake_Supporting_Documentation_Data_XPATH", PercentageofSuppDoc);
		commonLib.scrollDownToElement("Intake_Use_Of_Cash_XPATH", "Input");
		commonLib.click("Intake_Use_Of_Cash_XPATH");
		commonLib.clickWithDynamicValue("Intake_Use_Of_Cash_Data_XPATH", UseOfCash);
		commonLib.scrollDownToElement("Intake_Level_Of_Detail_XPATH", "Input");
		commonLib.click("Intake_Level_Of_Detail_XPATH");
		commonLib.clickWithDynamicValue("Intake_Level_Of_Detail_Data_XPATH", LevelOfDetail);
		commonLib.scrollDownToElement("Intake_HealthCare_Professionals_XPATH", "Input");
		commonLib.click("Intake_HealthCare_Professionals_XPATH");
		commonLib.clickWithDynamicValue("Intake_HealthCare_Professionals_Data_XPATH", HealthcareProfessional);
		commonLib.click("Intake_Provide_Documentation_XPATH");
		commonLib.clickWithDynamicValue("Intake_Provide_Documentation_Data_XPATH", "No");
		commonLib.log(LogStatus.PASS, "Step 5: Completed the mandatory fields for 'B&R Questionnaire' Section.");

		commonLib.click("Intake_Save_For_Later_XPATH");
		Thread.sleep(2000);
		commonLib.waitForVisibilityOf("Intake_Form_Edit_XPATH");
		commonLib.log(LogStatus.PASS, "Step 6: Form filled and Saved for later.");
		commonLib.getScreenshot();
		Thread.sleep(3000);
		commonLib.refreshPage();
		commonLib.waitforElement("Intake_Form_Edit_XPATH");
		commonLib.click("Intake_Form_Edit_XPATH");
		Thread.sleep(3000);
		commonLib.waitforElement("Intake_Review_Checkbox_XPATH");
		commonLib.clickbyjavascript("Intake_Review_Checkbox_XPATH");
		Thread.sleep(1000);
		commonLib.clickbyjavascript("Submit_Review_XPATH");
		commonLib.waitForPageToLoad();
		Thread.sleep(11000);
		commonLib.waitForVisibilityOf("Intake_Form_Edit_XPATH");
		if (commonLib.getText("Form_Status_XPATH").equalsIgnoreCase(FormStatus)) {
			commonLib.log(LogStatus.PASS, "Step 7: Finance Form Submitted for Review");
			commonLib.getScreenshot();
		} else {
			commonLib.log(LogStatus.FAIL, "Step 7: Finance Form is not submitted for review");
			commonLib.getScreenshot();
		}
		// sfdcLib.writeToCell(workBook, workSheet, 1, lstRowData.size(), FinanceForm);
		// sfdcLib.writeToCell(workBook, workSheet, 1,
		// sfdcLib.getColumnNumberFromList(headerRow, "Finance Form"), FinanceForm);

		// commonLib.waitforElement("Intake_Customer_User_XPATH");
		commonLib.waitForElementToBeClickable("Intake_Customer_User_XPATH");
		commonLib.clickbyjavascript("Intake_Customer_User_XPATH");
		Thread.sleep(5000);
		commonLib.click("Intake_Customer_User_Logout_XPATH");
		commonLib.waitForPageToLoad();
		Thread.sleep(5000);
	}

	public void submit_RAQAForm(String formNumber, String contact, String Country) throws Exception {

		String filepath = System.getProperty("user.dir") + "\\images\\Test Doc.pdf";

		App_Launcher_Search("IC Hub");
		commonLib.log(LogStatus.PASS, "Step 1: Naigated to IC Hub.");
		commonLib.getScreenshot();
		Thread.sleep(2000);
		globalSearch(formNumber);
		commonLib.waitForPresenceOfElementLocated("Intake_PreIntake_Account_Name_XPATH");
		commonLib.clickbyjavascript("Intake_PreIntake_Account_Name_XPATH");
		Open_Given_Link_Through_RelatedListQuickLinks_In_Accounts_Page("Related Contacts");
		commonLib.refreshPage();
		commonLib.WaitforPresenceofElement_Dynamic_Xpath("Intake_Related_Contacts_XPATH", contact);
		commonLib.clickWithDynamicValue("Intake_Related_Contacts_XPATH", contact);
		Thread.sleep(3000);
		commonLib.waitforElement("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_More_DD_XPATH");
		commonLib.clickbyjavascript("Intake_Contact_Login_XPATH");
		commonLib.log(LogStatus.PASS, "Step 2: Login as Customer User (Contact)");
		commonLib.getScreenshot();
		commonLib.waitForPageToLoad();
		commonLib.waitforElement("Intake_Contact_Form_XPATH");
		commonLib.click("Intake_Contact_Form_XPATH");
		commonLib.waitForPageToLoad();
		commonLib.clickbyjavascript("Intake_List_View_XPATH");
		commonLib.clickbyjavascript("Intake_List_Active_Forms_XPATH");
		Thread.sleep(2000);
		String RAQAForm = commonLib.getText("Intake_RAQA_Form_XPATH");
		String RAQAFormNumber = RAQAForm;
		commonLib.clickWithDynamicValue("Intake_Form_List_XPATH", RAQAForm);
		commonLib.log(LogStatus.PASS, "Step 2: Navigated to RAQA Form: " + RAQAFormNumber);
		commonLib.getScreenshot();

		commonLib.clickbyjavascript("Intake_Form_Edit_XPATH");
		commonLib.clickbyjavascript("Intake_Form_Edit_XPATH");
		Thread.sleep(1000);

		/******* Country Specific ***/

		commonLib.click("Intake_Country_Specific_XPATH");
		// commonLib.click("Intake_Upload_Operating_License_XPATH");
		List<WebElement> uploadButton = commonLib.findElements("Intake_Upload_buttons_country_XPATH");
		System.out.println("upload size " + uploadButton.size());

		for (WebElement webElement : uploadButton) {
			webElement.sendKeys(filepath);
			commonLib.waitforElement("Contract_Upload_Done_XPATH");
			commonLib.click("Contract_Upload_Done_XPATH");
			Thread.sleep(2000);

		}
		Thread.sleep(2000);

		/*
		 * //commonLib.click("Intake_Upload_Local_Sanitary_License_XPATH");
		 * sfdcLib.uploadFileDirectly("Intake_Upload_Local_Sanitary_License_XPATH",
		 * filepath); commonLib.waitforElement("Contract_Upload_Done_XPATH");
		 * commonLib.click("Contract_Upload_Done_XPATH"); Thread.sleep(2000);
		 * 
		 * // commonLib.scrollDownToElement(
		 * "Intake_Upload_Technical_Resopnsible_Certificate_XPATH", "button"); //
		 * commonLib.click("Intake_Upload_Technical_Resopnsible_Certificate_XPATH"); //
		 * sfdcLib.UploadFile(filepath); //
		 * commonLib.waitforElement("Contract_Upload_Done_XPATH"); //
		 * commonLib.click("Contract_Upload_Done_XPATH"); // Thread.sleep(2000);
		 * 
		 * commonLib.scrollDownToElement(
		 * "Intake_Upload_National_Legal_Person_Register_XPATH", "button");
		 * //commonLib.click("Intake_Upload_National_Legal_Person_Register_XPATH");
		 * sfdcLib.uploadFileDirectly(
		 * "Intake_Upload_National_Legal_Person_Register_XPATH", filepath);
		 * commonLib.waitforElement("Contract_Upload_Done_XPATH");
		 * commonLib.click("Contract_Upload_Done_XPATH"); Thread.sleep(2000); //
		 * commonLib.waitForPresenceOfElementLocated(
		 * "Intake_Upload_National_Legal_Person_Register_check_XPATH");
		 * commonLib.scrollDownToElement(
		 * "Intake_Upload_National_Traceability_Sys_XPATH", "button");
		 * //commonLib.click("Intake_Upload_National_Traceability_Sys_XPATH");
		 * sfdcLib.uploadFileDirectly("Intake_Upload_National_Traceability_Sys_XPATH",
		 * filepath); commonLib.waitforElement("Contract_Upload_Done_XPATH");
		 * commonLib.click("Contract_Upload_Done_XPATH"); Thread.sleep(2000);
		 * commonLib.scrollDownToElement("Intake_Upload_GLN_Certificate_XPATH",
		 * "button"); //commonLib.click("Intake_Upload_GLN_Certificate_XPATH");
		 * sfdcLib.uploadFileDirectly("Intake_Upload_GLN_Certificate_XPATH", filepath);
		 * commonLib.waitforElement("Contract_Upload_Done_XPATH");
		 * commonLib.click("Contract_Upload_Done_XPATH"); Thread.sleep(2000);
		 */
		// commonLib.waitForPresenceOfElementLocated("Intake_Upload_GLN_Certificate_check_XPATH");
		commonLib.log(LogStatus.PASS, "Step 3: Completed Mandatory Fields for 'Country Specific' Section.");
		commonLib.clickbyjavascript("Intake_Country_Specific_XPATH");

		Thread.sleep(1000);

		/******* Storage ***/

		commonLib.click("Intake_Storage_Of_Products_XPATH");
		Thread.sleep(1000);
		commonLib.click("Intake_Product_Storage_XPATH");
		commonLib.clickWithDynamicValue("Intake_Product_Storage_Data_XPATH", "No");

		commonLib.click("Intake_Goods_IN_OUT_XPATH");
		commonLib.clickWithDynamicValue("Intake_Goods_IN_OUT_Data_XPATH", "No");

		commonLib.click("Intake_Expiry_Dates_XPATH");
		commonLib.clickWithDynamicValue("Intake_Expiry_Dates_Data_XPATH", "No");

		commonLib.scrollDownToElement("Intake_Segregation_XPATH", "Button");
		commonLib.click("Intake_Segregation_XPATH");
		commonLib.clickWithDynamicValue("Intake_Segregation_Data_XPATH", "No");

		commonLib.scrollDownToElement("Intake_Procedure_XPATH", "Button");
		commonLib.click("Intake_Procedure_XPATH");
		commonLib.clickWithDynamicValue("Intake_Procedure_Data_XPATH", "No");

		commonLib.click("Intake_Stock_Check_XPATH");
		commonLib.clickWithDynamicValue("Intake_Stock_Check_Data_XPATH", "No");

		commonLib.scrollDownToElement("Intake_Temperature_XPATH", "Button");
		commonLib.click("Intake_Temperature_XPATH");
		commonLib.clickWithDynamicValue("Intake_Temperature_Data_XPATH", "No");

		commonLib.click("Intake_Temperature_Limit_XPATH");
		commonLib.clickWithDynamicValue("Intake_Temperature_Limit_Data_XPATH", "No");

		commonLib.scrollDownToElement("Intake_Risk_Of_Contamination_XPATH", "Button");
		commonLib.click("Intake_Risk_Of_Contamination_XPATH");
		commonLib.clickWithDynamicValue("Intake_Risk_Of_Contamination_Data_XPATH", "No");

		// commonLib.scrollDownToElement("Intake_Risk_Of_Contamination_XPATH",
		// "Button");
		// commonLib.click("Intake_Risk_Of_Contamination_XPATH");
		// commonLib.clickWithDynamicValue("Intake_Risk_Of_Contamination_Data_XPATH",
		// "No");

		commonLib.scrollDownToElement("Intake_Pest_Control_XPATH", "Button");
		commonLib.click("Intake_Pest_Control_XPATH");
		commonLib.clickWithDynamicValue("Intake_Pest_Control_Data_XPATH", "No");
		if (!Country.equalsIgnoreCase("India")) {
			commonLib.scrollDownToElement("Intake_Multipack_Product_XPATH", "Button");
			commonLib.click("Intake_Multipack_Product_XPATH");
			commonLib.clickWithDynamicValue("Intake_Multipack_Product_Data_XPATH", "No");
		}

		commonLib.clickbyjavascript("Intake_Storage_Of_Products_XPATH");
		Thread.sleep(1000);

		/******* Traceability ***/

		commonLib.scrollDownToElement("Intake_Traceability_XPATH", "Button");
		commonLib.click("Intake_Traceability_XPATH");
		Thread.sleep(1000);

		commonLib.click("Intake_Manage_Product_XPATH");
		commonLib.clickWithDynamicValue("Intake_Manage_Product_Data_XPATH", "No");

		commonLib.click("Intake_Traceable_Oubounds_XPATH");
		commonLib.clickWithDynamicValue("Intake_Traceable_Oubounds_Data_XPATH", "No");

		commonLib.scrollDownToElement("Intake_Scrapped_Product_XPATH", "Input");
		commonLib.click("Intake_Scrapped_Product_XPATH");
		commonLib.clickWithDynamicValue("Intake_Scrapped_Product_Data_XPATH", "No");

		commonLib.click("Intake_Control_Product_Traceabability_XPATH");
		commonLib.clickWithDynamicValue("Intake_Control_Product_Traceabability_Data_XPATH", "No");

		commonLib.scrollDownToElement("Intake_Lot_Serial_Number_XPATH", "Input");
		commonLib.click("Intake_Lot_Serial_Number_XPATH");
		commonLib.clickWithDynamicValue("Intake_Lot_Serial_Number_Data_XPATH", "No");

		commonLib.clickbyjavascript("Intake_Traceability_XPATH");
		Thread.sleep(1000);

		/******* Post Surveillance ***/

		commonLib.scrollDownToElement("Intake_Post_Market_Surveillance_XPATH", "Button");
		commonLib.click("Intake_Post_Market_Surveillance_XPATH");
		Thread.sleep(1000);

		commonLib.click("Intake_Event_Recall_XPATH");
		commonLib.clickWithDynamicValue("Intake_Event_Recall_Data_XPATH", "No");

		commonLib.click("Intake_Notification_Communication_XPATH");
		commonLib.clickWithDynamicValue("Intake_Notification_Communication_Data_XPATH", "No");

		commonLib.scrollDownToElement("Intake_Product_Return_XPATH", "Input");
		commonLib.click("Intake_Product_Return_XPATH");
		commonLib.clickWithDynamicValue("Intake_Product_Return_Data_XPATH", "No");

		commonLib.click("Intake_Field_Safety_XPATH");
		commonLib.clickWithDynamicValue("Intake_Field_Safety_Data_XPATH", "No");

		commonLib.scrollDownToElement("Intake_Retention_Accessibility_PCA_XPATH", "Input");
		commonLib.click("Intake_Retention_Accessibility_PCA_XPATH");
		commonLib.clickWithDynamicValue("Intake_Retention_Accessibility_PCA_Data_XPATH", "No");

		commonLib.log(LogStatus.PASS, "Step 5: Completed Mandatory Fields for 'Post Market Surveillance' Section.");
		commonLib.clickbyjavascript("Intake_Post_Market_Surveillance_XPATH");
		Thread.sleep(1000);

		/******* Product Complaints ***/

		commonLib.scrollDownToElement("Intake_Product_Complaints_XPATH", "Button");
		commonLib.click("Intake_Product_Complaints_XPATH");
		Thread.sleep(1000);

		commonLib.click("Intake_Complaints_Manufacturer_XPATH");
		commonLib.clickWithDynamicValue("Intake_Complaints_Manufacturer_Data_XPATH", "No");

		commonLib.scrollDownToElement("Intake_Manufacturing_Representative_XPATH", "Input");
		commonLib.click("Intake_Manufacturing_Representative_XPATH");
		commonLib.clickWithDynamicValue("Intake_Manufacturing_Representative_Data_XPATH", "No");

		commonLib.click("Intake_Retention_Accessibility_XPATH");
		commonLib.clickWithDynamicValue("Intake_Retention_Accessibility_Data_XPATH", "No");

		commonLib.log(LogStatus.PASS, "Step 6: Completed Mandatory Fields for 'Product Complaints' Section.");

		commonLib.clickbyjavascript("Intake_Product_Complaints_XPATH");
		Thread.sleep(1000);

		/******* QMS ***/

		commonLib.scrollDownToElement("Intake_Quality_Management_Sys_XPATH", "Button");
		commonLib.click("Intake_Quality_Management_Sys_XPATH");

		commonLib.click("Intake_QMS_Conformity_XPATH");
		commonLib.clickWithDynamicValue("Intake_QMS_Conformity_Data_XPATH", "No");

		commonLib.scrollDownToElement("Intake_Conformity_Plan_XPATH", "Button");
		commonLib.click("Intake_Conformity_Plan_XPATH");
		commonLib.clickWithDynamicValue("Intake_Conformity_Plan_Data_XPATH", "No");

		commonLib.scrollDownToElement("Intake_Quality_Manual_XPATH", "Button");
		commonLib.click("Intake_Quality_Manual_XPATH");
		commonLib.clickWithDynamicValue("Intake_Quality_Manual_Data_XPATH", "No");
		if (!Country.equalsIgnoreCase("India")) {
			commonLib.click("Intake_Management_Representative_XPATH");
			commonLib.clickWithDynamicValue("Intake_Management_Representative_Data_XPATH", "No");

			commonLib.scrollDownToElement("Intake_Org_Chart_XPATH", "Input");
			commonLib.click("Intake_Org_Chart_XPATH");
			commonLib.clickWithDynamicValue("Intake_Org_Chart_Data_XPATH", "No");

			uploadFileDirectly("Intake_Upload_MasterList_XPATH", filepath);
			commonLib.waitforElement("Contract_Upload_Done_XPATH");
			commonLib.click("Contract_Upload_Done_XPATH");
			Thread.sleep(3000);
			// commonLib.scrollDownToElement("Intake_Upload_Annual_Training_Plan_XPATH",
			// "button");
			commonLib.scrollDownToElement("Intake_Upload_Annual_Training_Plan_XPATH", "Input");
			// commonLib.click("Intake_Upload_Annual_Training_Plan_XPATH");
			uploadFileDirectly("Intake_Upload_Annual_Training_Plan_XPATH", filepath);
			commonLib.waitforElement("Contract_Upload_Done_XPATH");
			commonLib.click("Contract_Upload_Done_XPATH");
			Thread.sleep(1000);
			commonLib.clickbyjavascript("Intake_Quality_Management_Sys_XPATH");
			Thread.sleep(1000);
		}

		commonLib.log(LogStatus.PASS, "Step 7: Completed Mandatory Fields for 'Quality Management System' Section.");

		/******* Surgical ***/

		commonLib.scrollDownToElement("Intake_Surgical_Sets_XPATH", "Button");
		commonLib.click("Intake_Surgical_Sets_XPATH");
		commonLib.click("Intake_Reusable_Instruments_XPATH");
		commonLib.clickWithDynamicValue("Intake_Reusable_Instruments_Data_XPATH", "No");

		commonLib.scrollDownToElement("Intake_Inspection_Surgical_Sets_XPATH", "Input");
		commonLib.click("Intake_Inspection_Surgical_Sets_XPATH");
		commonLib.clickWithDynamicValue("Intake_Inspection_Surgical_Sets_Data_XPATH", "No");

		commonLib.log(LogStatus.PASS,
				"Step 8: Completed Mandatory Fields for 'Surgical Set process, cleaning and disinfection' Section.");
		commonLib.scrollDownToElement("Intake_Save_For_Later_XPATH", "Button");
		commonLib.clickbyjavascript("Intake_Save_For_Later_XPATH");
		commonLib.waitForVisibilityOf("Intake_Form_Edit_XPATH");
		Thread.sleep(2000);
		commonLib.log(LogStatus.PASS, "Step 9: Form filled and Saved for later");
		commonLib.getScreenshot();
		Thread.sleep(1000);
		commonLib.refreshPage();
		commonLib.waitforElement("Intake_Form_Edit_XPATH");
		commonLib.click("Intake_Form_Edit_XPATH");
		commonLib.waitForElementToBeClickable("Intake_Review_Checkbox_XPATH");
		Thread.sleep(3000);
		commonLib.clickbyjavascript("Intake_Review_Checkbox_XPATH");
		Thread.sleep(2000);
		commonLib.clickbyjavascript("Submit_Review_XPATH");
		Thread.sleep(9000);
		commonLib.waitForPageToLoad();
		// commonLib.waitforInvisibilityOfElement("Submit_Review_XPATH");
		commonLib.waitForVisibilityOf("Form_Status_XPATH");

		if (commonLib.getText("Form_Status_XPATH").equalsIgnoreCase("Submitted")) {
			commonLib.log(LogStatus.PASS, "Step 10: RAQA Form Submitted for Review");
			commonLib.getScreenshot();
		} else {
			commonLib.log(LogStatus.FAIL, "Step 10: RAQA Form is not submitted for review");
			commonLib.getScreenshot();
		}

		// sfdcLib.writeToCell(workBook, workSheet, 1,lstRowData.size(), RAQAForm);
		// sfdcLib.writeToCell(workBook, workSheet, 1,
		// sfdcLib.getColumnNumberFromList(headerRow, "RAQA Form"), RAQAForm);

		commonLib.waitForElementToBeClickable("Intake_Customer_User_XPATH");
		commonLib.clickbyjavascript("Intake_Customer_User_XPATH");
		Thread.sleep(5000);
		commonLib.click("Intake_Customer_User_Logout_XPATH");
		commonLib.waitForPageToLoad();
		Thread.sleep(5000);
	}
	public void ByVisibleElement(String element) {
      
        //WebDriver driver = new FirefoxDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement Element = commonLib.findElement(element);
        // Scrolling down the page till the element is found		
        js.executeScript("arguments[0].scrollIntoView();", Element);
    }


	public String[] setTestInfo(String workSheet)
	{
		String[] testInfo = new String[3];
		
		
		
		String packageName = Opportunities.class.getPackage().toString();
		testInfo[1]=packageName;
		
		System.out.println("Package name value is " + packageName);
		System.out.println("Worksheet name value is " + workSheet);
		String workBook = getWorkbookNameWithEnvironment();
		testInfo[2]=workBook;
		System.out.println("Workbook value is " + workBook);
		
		return testInfo;

	}
	
	/**anshumans
	click
	@param field
	void
	Jan 4, 2022
	*/
	public void click(String field) {
		
		try {
			commonLib.waitForPresenceOfElementLocated(field);
			commonLib.waitForVisibilityOf(field);
			commonLib.waitForElementToBeClickable(field);
			commonLib.findElement(field).click();			
		}
		catch (ElementClickInterceptedException e) {
			System.out.println(e); 
			commonLib.clickbyjavascript(field);	
			}	
		
		commonLib.log(LogStatus.INFO, field + " clicked");
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
		try {
		
			commonLib.WaitforPresenceofElement_Dynamic_Xpath(field, key);
			//waitForElementToBeClickable(field);
			commonLib.findElementWithDynamicXPath(field,key).click();
			commonLib.syso("Clicked on element: " + field);
		} 
		catch (ElementClickInterceptedException e1) {
			System.out.println(e1); 
			try {
			commonLib.clickbyjavascriptWithDynamicValue(field, key);
			}
				catch (Exception e) {

					e.printStackTrace();
					commonLib.reportUtil.log(LogStatus.FAIL, "Not able to click on element"+e);
				}
			}
		
		commonLib.log(LogStatus.INFO, field + "clicked");
		commonLib.getScreenshot();
	}
	
	public void waitforInvisibilityOfWE(String field) {
		try {
			if (commonLib.isDisplayed(field)) {
				WebDriverWait wait = new WebDriverWait(commonLib.getDriver(), 90);
				wait.until(ExpectedConditions.invisibilityOf(commonLib.findElement(field)));
			}

		} catch (NoSuchElementException e) {
			//e.printStackTrace();
		}
	}
}
