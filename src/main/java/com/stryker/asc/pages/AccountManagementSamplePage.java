package com.stryker.asc.pages;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.openqa.selenium.ElementNotVisibleException;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.stryker.salesforceLibrary.SfdcLibrary;
import com.stryker.star.CommonLibrary;

/**
 * @author sanjayd7
 *
 */
public class AccountManagementSamplePage {

	private CommonLibrary commonLib;
	Date date = new Date();
	long ts = date.getTime();
	Timestamp timeStamp = new Timestamp(date.getTime());
	String Subject_Name = "Automation Task " + timeStamp.toString();
	SfdcLibrary sfdcLib = new SfdcLibrary(commonLib);
	ExtentTest parentTest;

	public AccountManagementSamplePage(CommonLibrary commonLib) {
		this.commonLib = commonLib;
		this.sfdcLib = new SfdcLibrary(commonLib);
	}

	/**
	 * sanjayd7
	 * 
	 * @return boolean Dec 1, 2021
	 */
	public boolean verifyFlexLogo() {
		commonLib.waitForPresenceOfElementLocated("Flex_Financial_logo_XPATH");
		commonLib.getScreenshot();
		return commonLib.waitForPresenceOfElementLocated("Flex_Financial_logo_XPATH");

	}

	/**
	 * sanjayd7
	 * 
	 * @param value void Dec 1, 2021
	 */
	public void selectViewFromDropdown(String value) {
		commonLib.selectDropdownVisibleText("FF_AccountPage_View_DD_XPATH", value);
		commonLib.log(LogStatus.INFO, "Text " + value + " selected from the dropdown");
		commonLib.getScreenshot();

	}

	/**
	 * sanjayd7
	 * 
	 * @param value void Dec 1, 2021
	 */
	public void selectContactRecordType(String value) {
		commonLib.selectDropdownVisibleText("FF_ContactRecordType_DD_XPATH", value);
		commonLib.log(LogStatus.INFO, "Text " + value + " selected from the dropdown");
		commonLib.getScreenshot();

	}

	/**
	 * sanjayd7
	 * 
	 * @throws InterruptedException
	 * @throws IOException          void Dec 1, 2021
	 */
	public void mouseHoverContactsLink() throws InterruptedException, IOException {
		commonLib.scroll_view("FF_AccountPage_Contacts_Link_XPATH");
		commonLib.performHover("FF_AccountPage_Contacts_Link_XPATH");
		Thread.sleep(1000);
		commonLib.getScreenshot();
	}

	/**
	 * sanjayd7
	 * 
	 * @throws InterruptedException
	 * @throws IOException          void Dec 1, 2021
	 */
	public void clickNewContactButton() throws InterruptedException, IOException {
		commonLib.switchtoFrame("FF_AccountPage_NewContact_Frame_XPATH");
		commonLib.click("FF_AccountPage_NewContact_Button_XPATH");
		commonLib.switchToDefaultContent();
		commonLib.log(LogStatus.INFO, "Clicked on new contact button");
		commonLib.getScreenshot();
	}

	/**
	 * sanjayd7
	 * 
	 * void Dec 1, 2021
	 */
	public void clickOnContinueButton() {

		commonLib.click("FF_AccountPage_NewContact_ContinueBtn_XPATH");
		commonLib.log(LogStatus.INFO, "Clicked on the continue button");
		commonLib.getScreenshot();

	}

	/**
	 * sanjayd7
	 * 
	 * @param firstName void Dec 1, 2021
	 */
	public void enterFirstName(String firstName) {

		commonLib.sendKeys("FF_AccountPage_NewContact_Firstname_Input_XPATH", firstName);
		commonLib.log(LogStatus.INFO, "Firstname " + firstName + " entered");
		commonLib.getScreenshot();
	}

	/**
	 * sanjayd7
	 * 
	 * @param string void Dec 1, 2021
	 */
	public void enterLastName(String lastName) {

		commonLib.sendKeys("FF_AccountPage_NewContact_Lastname_Input_XPATH", lastName);
		commonLib.log(LogStatus.INFO, "Lastname " + lastName + " entered");
		commonLib.getScreenshot();

	}

	/**
	 * sanjayd7 void Dec 1, 2021
	 */
	public void clickOnSaveBtn() {

		commonLib.click("FF_AccountPage_NewContact_SaveBtn_XPATH");
		commonLib.log(LogStatus.INFO, "Clicked on Save Button");
		commonLib.getScreenshot();
	}

	/**
	 * sanjayd7 void Dec 1, 2021
	 */
	public void clickOnShowFilters() {

		commonLib.click("FF_SearchResult_ShowFilters_Link_XPATH");
		commonLib.log(LogStatus.INFO, "Clicked on Show Filters");
		commonLib.getScreenshot();

	}

	/**
	 * sanjayd7
	 * 
	 * @param string void Dec 1, 2021
	 */
	public void enterAccountNumber(String accountNumber) {

		commonLib.sendKeys("FF_ShowFilters_AccountNumber_Input_XPATH", accountNumber);
		commonLib.log(LogStatus.INFO, "Account number " + accountNumber + " entered");
		commonLib.getScreenshot();

	}

	/**
	 * sanjayd7
	 * 
	 * void Dec 1, 2021
	 */
	public void clickOnApplyFilterButton() {

		commonLib.click("FF_Account_ApplyFilterBtn_XPATH");
		commonLib.log(LogStatus.INFO, "Clicked on the apply filter button");
		commonLib.getScreenshot();

	}

	/**
	 * sanjayd7
	 * 
	 * @param string void Dec 1, 2021
	 * @throws InterruptedException
	 */
	public boolean verifyAccountNumber(String accountNumber) throws InterruptedException {

		Thread.sleep(5000);
		commonLib.waitForVisibilityOf_DynamicXpath("FF_Account_ApplyFilter_AccountNumber_Label_XPATH", accountNumber);
		commonLib.getScreenshot();
		return commonLib.WaitforPresenceofElement_Dynamic_Xpath("FF_Account_ApplyFilter_AccountNumber_Label_XPATH",
				accountNumber);
	}

	/**
	 * sanjayd7
	 * 
	 * @param string void Dec 1, 2021
	 * @return
	 */
	public boolean verifyContactName(String name) {
		commonLib.getScreenshot();
		return commonLib.WaitforPresenceofElement_Dynamic_Xpath("FF_AccountPage_NewContact_Label_XPATH", name);

	}

	/**
	 * Method to click on Account Name
	 * 
	 * @param accountName
	 */
	public void clickOnAccountNameLink(String accountName) {
		try {
			commonLib.waitForVisibilityOf_DynamicXpath("SF_ASCSales_AccountName_Link_XPATH", accountName);
			commonLib.getScreenshot();
			commonLib.log(LogStatus.INFO, "Account Name " + accountName + " is visible in the Accounts Page");
			commonLib.clickWithDynamicValue("SF_ASCSales_AccountName_Link_XPATH", accountName);
			commonLib.waitForPageToLoad();
			commonLib.waitForVisibilityOf_DynamicXpath("SF_ASCSales_Account_Title_XPATH", accountName);
		} catch (ElementNotVisibleException e) {
			commonLib.getScreenshot();
			commonLib.log(LogStatus.FAIL, "Account Name " + accountName + " is not visible in the Accounts Page");
		}
	}
}
