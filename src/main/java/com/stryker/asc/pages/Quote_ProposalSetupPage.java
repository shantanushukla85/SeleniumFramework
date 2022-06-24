package com.stryker.asc.pages;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * @author SHANTANUS5
 *
 */

public class Quote_ProposalSetupPage {

	private CommonLibrary commonLib;
	Date date = new Date();
	long ts = date.getTime();
	Timestamp timeStamp = new Timestamp(date.getTime());
	String Subject_Name = "Automation Task " + timeStamp.toString();
	SfdcLibrary sfdcLib = new SfdcLibrary(commonLib);
	ExtentTest parentTest;
	AccountAndTerritoriesPage accountAndTerr = new AccountAndTerritoriesPage(commonLib);
	OpportunitiesPage oppor = new OpportunitiesPage(commonLib);

	public Quote_ProposalSetupPage(CommonLibrary commonLib) {
		this.commonLib = commonLib;
		this.sfdcLib = new SfdcLibrary(commonLib);
		this.accountAndTerr = new AccountAndTerritoriesPage(commonLib);
		this.oppor = new OpportunitiesPage(commonLib);
	}

	/**SHANTANUS5
	navigationToQuoteCreationPage
	@throws InterruptedException
	@throws IOException
	void
	Jun 24, 2022
	*/
	
	public void navigationToQuoteCreationPage() throws InterruptedException, IOException {
		commonLib.scrollDownToElement("SF_Oppty_Quote_Expander_Button_XPATH", "Expander");
		commonLib.waitForElementToBeClickable("SF_Oppty_Quote_Expander_Button_XPATH");
		for (int i = 0; i < 5; i++) {
			commonLib.scroll(0, 300);
			if (commonLib.waitForVisibilityOf_DynamicXpath("SF_Oppty_Quote_Expander_Button_XPATH", "button")) {
				break;
			}
			Thread.sleep(2000);
		}
		commonLib.performHover("SF_Oppty_Quote_Expander_Button_XPATH");
		Thread.sleep(3000);
		commonLib.click("SF_Oppty_Quote_Expander_Button_XPATH");
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "New Quote button is displayed");
		commonLib.waitForPresenceOfElementLocated("SF_New_Quote_Button_XPATH");
		commonLib.click("SF_New_Quote_Button_XPATH");
		sfdcLib.waitforInvisibilityOfWE("SVMX_Loading_Spinner_XPATH");
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "CPQ Quote screen is displayed");
		commonLib.waitForPageToLoad();

	}

	
	/**SHANTANUS5
	clickOnCreateQuote: Method to click on Create Quote screen
	void
	Jun 24, 2022
	*/
	
	public void clickOnCreateQuote() {
		commonLib.waitForVisibilityOf("SF_Create_Quote_Button_XPATH");
		commonLib.click("SF_Create_Quote_Button_XPATH");
		sfdcLib.waitforInvisibilityOfWE("SVMX_Loading_Spinner_XPATH");
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "CPQ Quote screen is displayed");
		commonLib.waitForPageToLoad();
	}

	/**
	 * 
	 */
	
	/**SHANTANUS5
	clickOnEditQuoteButton: Method to click on Edit button under Quote Details screen
	void
	Jun 24, 2022
	*/
	
	public void clickOnEditQuoteButton() {
		commonLib.waitForVisibilityOf("SF_Edit_Button_XPATH");
		commonLib.click("SF_Edit_Button_XPATH");
		sfdcLib.waitforInvisibilityOfWE("SVMX_Loading_Spinner_XPATH");
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "Edit CPQ Quote screen is displayed");
		commonLib.waitForPageToLoad();

	}
	
	/**SHANTANUS5
	clickOnQuoteNumberUnderOpportunityPage: Method to click on Quote Number under Realted List Screen
	@throws InterruptedException
	void
	Jun 24, 2022
	*/
	
	public void clickOnQuoteNumberUnderOpportunityPage() throws InterruptedException {
		commonLib.clickbyjavascript("SF_Quote_Link_Under_Oppty_Page_XPATH");
		commonLib.getScreenshot();
		Thread.sleep(1000);
	}

	/**SHANTANUS5
	switchtoFrameUnderCPQScreen
	void
	Jun 24, 2022
	*/
	
	public void switchtoFrameUnderCPQScreen() {
		commonLib.waitforFramenadSwitch("SF_Accessibility_Frame_XPATH");
		commonLib.waitforFramenadSwitch("SF_Canvas_Frame_XPATH");
		commonLib.waitforFramenadSwitch("SF_Oracle_CPQ_Cloud_XPATH");

	}

	/**SHANTANUS5
	updateQuoteDescription
	@param quoteDescription
	void
	Jun 24, 2022
	*/
	
	public void updateQuoteDescription(String quoteDescription) {
		commonLib.waitForElementToBeClickable("SF_Quote_Description_TextBox_XPATH");
		commonLib.click("SF_Quote_Description_TextBox_XPATH");
		commonLib.sendKeys("SF_Quote_Description_TextBox_XPATH", quoteDescription);
		commonLib.waitForPageToLoad();
		commonLib.log(LogStatus.INFO, "Quote Description Value is updated successfully");
		commonLib.getScreenshot();

	}

	/**SHANTANUS5
	clickOnAddConstructButton
	void
	Jun 24, 2022
	*/
	
	public void clickOnAddConstructButton() {
		try {
			commonLib.click("SF_Add_To_Constructs_Button_XPATH");
			Thread.sleep(5000);
			commonLib.waitForPageToLoad();
			commonLib.log(LogStatus.INFO, "Clicked successfully on Add to Construct button");
			commonLib.getScreenshot();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**SHANTANUS5
	selectDropdownValueUnderAddToConstruct
	@param fieldName
	@param fieldValue
	void
	Jun 24, 2022
	*/
	
	public void selectDropdownValueUnderAddToConstruct(String fieldName, String fieldValue) {
		try {
			commonLib.waitForElementToBeClickable_Dynamic("SF_Add_To_Construct_Field_Dropdown_XPATH", fieldName);
			commonLib.clickWithDynamicValue("SF_Add_To_Construct_Field_Dropdown_XPATH", fieldName);
			Thread.sleep(2000);
			commonLib.clickWithDynamicValue("SF_Add_To_Contract_Dropdown_Values_XPATH", fieldValue);
			commonLib.waitForPageToLoad();
			Thread.sleep(2000);
			commonLib.log(LogStatus.INFO, fieldValue + " selcted successfully under: " + fieldName);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**SHANTANUS5
	clickOnAddToQuote
	void
	Jun 24, 2022
	*/
	
	public void clickOnAddToQuote() {
		try {
			commonLib.waitForElementToBeClickable("SF_Add_To_Quote_Button_XPATH");
			Thread.sleep(5000);
			commonLib.clickbyjavascript("SF_Add_To_Quote_Button_XPATH");
			sfdcLib.waitforInvisibilityOfWE("SVMX_Loading_Spinner_XPATH");
			commonLib.waitForPageToLoad();
			commonLib.log(LogStatus.INFO, "Clicked successfully on Add to Quote button");
			commonLib.getScreenshot();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**SHANTANUS5
	clickOnUpdateQuote
	@throws IOException
	void
	Jun 24, 2022
	*/
	
	public void clickOnUpdateQuote() throws IOException {
		try {
			commonLib.waitForPresenceOfElementLocated("SF_Update_Quote_Button_ID");
			commonLib.waitForElementToBeClickable("SF_Update_Quote_Button_ID");
			Thread.sleep(5000);
			commonLib.click("SF_Update_Quote_Button_ID");
			Thread.sleep(5000);
			commonLib.waitForPageToLoad();
			commonLib.log(LogStatus.INFO, "Clicked successfully on Update Quote button");
			commonLib.getScreenshot();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**SHANTANUS5
	clickOnLoadButtonUnderAddConstruct
	void
	Jun 24, 2022
	*/
	
	public void clickOnLoadButtonUnderAddConstruct() {
		commonLib.click("SF_Load_Quote_Button_XPATH");
		commonLib.log(LogStatus.INFO, "Clicked successfully on Load Quote button");
		commonLib.getScreenshot();
	}

	/**SHANTANUS5
	updateProcedurePrice
	@param price
	void
	Jun 24, 2022
	*/
	
	public void updateProcedurePrice(String price) {
		commonLib.waitForVisibilityOf("SF_Manual_Procedure_Price_XPATH");
		commonLib.clear("SF_Manual_Procedure_Price_XPATH");
		commonLib.sendKeys("SF_Manual_Procedure_Price_XPATH", price);
		commonLib.log(LogStatus.INFO, "Successfully entered price: " + price + " under Manual Procedure Price field");
		commonLib.getScreenshot();
	}

	/**SHANTANUS5
	updateAddConstructTextboxField
	@param fieldName
	@param fieldValue
	void
	Jun 24, 2022
	*/
	
	public void updateAddConstructTextboxField(String fieldName, String fieldValue) {
		commonLib.clear_DynamicValue("SF_Year_Textbox_XPATH", fieldName);
		commonLib.sendKeys_DynamicValue("SF_Year_Textbox_XPATH", fieldName, fieldValue);
		commonLib.log(LogStatus.INFO, "Successfully entered year: " + fieldValue + " under field " + fieldName);

	}

	/**SHANTANUS5
	clickOnCreateQuoteButton
	void
	Jun 24, 2022
	*/
	
	public void clickOnCreateQuoteButton() {
		commonLib.waitForElementToBeClickable("SF_Create_Quote_Button_XPATH");
		commonLib.click("SF_Create_Quote_Button_XPATH");
		commonLib.log(LogStatus.INFO, "Clicked successfully on Create Quote button");
		commonLib.getScreenshot();
	}


	/**SHANTANUS5
	clickOnQuoteNumber
	@param quoteNumber
	void
	Jun 24, 2022
	*/
	public void clickOnQuoteNumber(String quoteNumber) {
		try {
			commonLib.waitForPageToLoad();
			commonLib.scrollDownToElement("SF_Member_Role_XPATH", "div");
			Thread.sleep(2000);
			commonLib.clickbyjavascriptWithDynamicValue("SF_Quote_Number_Link_XPATH", quoteNumber);
			commonLib.log(LogStatus.PASS, "Clicked successfully on Quote Number Link");
		} catch (Exception e) {
			commonLib.getScreenshot();
			commonLib.log(LogStatus.FAIL, "Quote Number: " + quoteNumber + " is not available");
		}

	}

	/**SHANTANUS5
	clickOnCloneQuote
	void
	Jun 24, 2022
	*/
	
	public void clickOnCloneQuote() {
		try {
			boolean bol = commonLib.waitForPresenceOfElementLocated("SF_Clone_Quote_Button_XPATH");
			if (bol) {
				commonLib.getScreenshot();
				commonLib.waitForElementToBeClickable("SF_Clone_Quote_Button_XPATH");
				commonLib.click("SF_Clone_Quote_Button_XPATH");
				commonLib.log(LogStatus.PASS, "Clicked successfully on Clone Quote button");
				commonLib.waitForPageToLoad();
				Thread.sleep(18000);
			} else {
				commonLib.log(LogStatus.FAIL, "Clone Quote button is not available");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**SHANTANUS5
	clickOnCPQQuoteOperationsButton: Method to click on Save, Return to Opportunity and Rerun Pricing buttons
	@param buttonName
	void
	Jun 24, 2022
	*/
	public void clickOnCPQQuoteOperationsButton(String buttonName) {
		try {
			boolean bol = commonLib.waitForVisibilityOf_DynamicXpath("SF_CP_Quote_Operation_Button_XPATH", buttonName);
			if (bol) {
				commonLib.getScreenshot();
				commonLib.clickWithDynamicValue("SF_CP_Quote_Operation_Button_XPATH", buttonName);
				commonLib.log(LogStatus.PASS, "Clicked successfully on " + buttonName + " button");
				Thread.sleep(1000);
				commonLib.waitForVisibilityOf("SF_Quote_Processing_Scroll_XPATH");
				commonLib.waitForPageToLoad();

			} else {
				commonLib.getScreenshot();
				commonLib.log(LogStatus.FAIL, buttonName + " is not visible");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**SHANTANUS5
	verifyPresenceOfOperationButton
	@param actionName
	void
	Jun 24, 2022
	*/
	
	public void verifyPresenceOfOperationButton(String actionName) {
		try {
			commonLib.KeyPress_pageUp();

			boolean bol = commonLib.waitForVisibilityOf_DynamicXpath("SF_CP_Quote_Operation_Button_XPATH", actionName);
			if (bol) {
				commonLib.log(LogStatus.PASS, actionName + "  is available to the user");

			} else {
				commonLib.log(LogStatus.FAIL, actionName + "  is not available to the user");
			}
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**SHANTANUS5
	cickOnEditQuote
	@param quoteNumber
	void
	Jun 24, 2022
	*/
	
	public void cickOnEditQuote(String quoteNumber) {
		try {
			commonLib.scroll_view_Dynamic("SF_Edit_Quote_Expander_Button_XPATH", quoteNumber);
			commonLib.waitForElementToBeClickable_Dynamic("SF_Edit_Quote_Expander_Button_XPATH", quoteNumber);
			commonLib.clickWithDynamicValue("SF_Edit_Quote_Expander_Button_XPATH", quoteNumber);
			Thread.sleep(3000);
			boolean bol = commonLib.waitForVisibilityOf("SF_Edit_Quote_Button_XPATH");
			if (bol) {
				commonLib.getScreenshot();
				commonLib.click("SF_Edit_Quote_Button_XPATH");
				commonLib.log(LogStatus.INFO, "Clicked successfully on Edit button");
				commonLib.waitForPageToLoad();
				Thread.sleep(7000);
			} else {
				commonLib.getScreenshot();
				commonLib.log(LogStatus.INFO, "Edit button is not visible");
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**SHANTANUS5
	fetchQuoteDescriptiontValue: Method to fetch quote description field value
	@return
	String
	Jun 24, 2022
	*/
	public String fetchQuoteDescriptiontValue() {
		String descriptionValue = commonLib.getAttribute("SF_Quote_Description_TextValue_XPATH", "value");
		commonLib.getScreenshot();
		return descriptionValue;
	}

	/**SHANTANUS5
	addQuoteLine
	@param partNumber
	void
	Jun 24, 2022
	*/
	
	public void addQuoteLine(String partNumber) {
		try {
			commonLib.scrollDownToElement("SF_Quotes_Quick_Add_Button_XPATH", "Button");
			commonLib.waitForElementToBeClickable("SF_Quotes_Quick_Add_Button_XPATH");
			commonLib.click("SF_Quotes_Quick_Add_Button_XPATH");
			Thread.sleep(2000);
			commonLib.click("SF_Part_Number_Textbox_XPATH");
			commonLib.sendKeys("SF_Part_Number_Textbox_XPATH", partNumber);
			commonLib.click("SF_Part_Number_Lookup_Button_XPATH");
			commonLib.log(LogStatus.INFO, "Part Number value got entered");
			commonLib.getScreenshot();
			commonLib.click("SF_Quick_Add_Ok_Button_XPATH");
			commonLib.waitForPageToLoad();
			Thread.sleep(5000);
			commonLib.waitForVisibilityOf("SF_Quote_Line_View_Button_XPATH");
			commonLib.scrollDownToElement("SF_Quote_Line_View_Button_XPATH", "button");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**SHANTANUS5
	verifyPartNumberProducts
	@return
	int
	Jun 24, 2022
	*/
	
	public int verifyPartNumberProducts() {
		int parts = 0;
		try {
			commonLib.clickbyjavascript("SF_Quotes_Tab_XPATH");
			commonLib.waitForPageToLoad();
			commonLib.waitForElementToBeClickable("SF_Quote_Number_LinkText_XPATH");
			commonLib.click("SF_Quote_Number_LinkText_XPATH");
			commonLib.waitForPageToLoad();
			commonLib.waitForElementToBeClickable("SF_Related_Tab_XPATH");
			commonLib.click("SF_Related_Tab_XPATH");
			Thread.sleep(2000);
			List<WebElement> partNumbers = commonLib.findElements("SF_Part_Number_Links_XPATH");
			parts = partNumbers.size();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parts;

	}

	/**SHANTANUS5
	verifyProposedPriceIsNonEditable
	void
	Jun 24, 2022
	*/
	
	public void verifyProposedPriceIsNonEditable() {
		List<WebElement> proposedPrice = commonLib.findElements("SF_List_Price_Elements_XPATH");
		for (int i = 0; i <= proposedPrice.size(); i++) {
			String attributeValue = proposedPrice.get(i).getAttribute("class");
			commonLib.getScreenshot();
			if (attributeValue.equalsIgnoreCase("table-read-only-value")) {
				commonLib.log(LogStatus.PASS, "Prosped Price is Read Only for row: " + i);

			} else {
				commonLib.log(LogStatus.FAIL, "Prosped Price is editable for row: " + i);
			}
		}

	}

	/**SHANTANUS5
	updateRebateFieldValue
	@param rebatePercentage
	@param rebateAmount
	void
	Jun 24, 2022
	*/
	
	public void updateRebateFieldValue(String rebatePercentage, String rebateAmount) {
		commonLib.scrollDownToElement("SF_Mass_Update_Expander_Button_XPATH", "Button");
		commonLib.clickbyjavascript("SF_Mass_Update_Expander_Button_XPATH");
		commonLib.waitForPresenceOfElementLocated("SF_Mass_Update_RebatePercenatge_Textbox_XPATH");
		commonLib.clear("SF_Mass_Update_RebatePercenatge_Textbox_XPATH");
		commonLib.sendKeys("SF_Mass_Update_RebatePercenatge_Textbox_XPATH", rebatePercentage);
		commonLib.log(LogStatus.INFO, "Rebate Percenatge value is entered");
		commonLib.waitForPageToLoad();
		commonLib.clear("SF_Mass_Update_RebateAmount_Textbox_XPATH");
		commonLib.sendKeys("SF_Mass_Update_RebateAmount_Textbox_XPATH", rebateAmount);
		commonLib.log(LogStatus.INFO, "Rebate Amount value is entered");
		commonLib.getScreenshot();
		commonLib.click("SF_Quote_Update_All_Button_XPATH");
		sfdcLib.waitforInvisibilityOfWE("SVMX_Loading_Spinner_XPATH");
	}

	/**SHANTANUS5
	clickOnRequestFinancing
	void
	Jun 24, 2022
	*/
	
	public void clickOnRequestFinancing() {
		try {
			commonLib.KeyPress_pageUp();
			commonLib.waitForElementToBeClickable("SF_Request_Finance_Checkbox_XPATH");
			commonLib.click("SF_Request_Finance_Checkbox_XPATH");
			commonLib.getScreenshot();
			commonLib.log(LogStatus.INFO, "Clicked successfully on Request Financing Checkbox");
			commonLib.softAssertThat(
					commonLib.waitForElementToBeClickable_Dynamic("SF_Quote_Transaction_Link_XPATH",
							"Financing Options"),
					"Financing Options link is displayed", "Financing Options link is not displayed");
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**SHANTANUS5
	clickQuoteTransactionLink
	@param transactionName
	void
	Jun 24, 2022
	*/
	
	public void clickQuoteTransactionLink(String transactionName) {
		try {
			for (int i = 0; i <= 5; i++) {
				commonLib.KeyPress_pageUp();
				commonLib.scroll("Up");
				JavascriptExecutor jse = (JavascriptExecutor) commonLib.getDriver();
				jse.executeScript("window.scrollBy(0,-350)", "");
				if (commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Transaction_Link_XPATH", transactionName)) {
					commonLib.log(LogStatus.INFO, transactionName + " field is visible");
					break;

				}

			}
			commonLib.clickbyjavascriptWithDynamicValue("SF_Quote_Transaction_Link_XPATH", transactionName);
			commonLib.waitForPageToLoad();
			Thread.sleep(2000);
			commonLib.getScreenshot();
			commonLib.log(LogStatus.INFO,
					"Clicked successfully on Link: " + transactionName + " under CPQ Quote screen");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**SHANTANUS5
	updateFlexQuestionaireDropdownValues
	@param fieldName
	@param fieldValue
	void
	Jun 24, 2022
	*/
	
	public void updateFlexQuestionaireDropdownValues(String fieldName, String fieldValue) {
		try {
			// SF_Finance_Flex_Dropdown_Link_XPATH
			commonLib.waitForElementToBeClickable_Dynamic("SF_Finance_Flex_Dropdown_Link_XPATH", fieldName);
			commonLib.clickWithDynamicValue("SF_Finance_Flex_Dropdown_Link_XPATH", fieldName);
			Thread.sleep(2000);
			commonLib.clickWithDynamicValue("SF_Finance_Flex_Dropdown_Value_XPATH", fieldValue);
			commonLib.log(LogStatus.INFO, "Successfully selected :" + fieldValue + "against" + " field " + fieldName);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**SHANTANUS5
	clickOnFinanceFlexRadioButton
	@param fieldName
	void
	Jun 24, 2022
	*/
	
	public void clickOnFinanceFlexRadioButton(String fieldName) {
		try {
			commonLib.waitForElementToBeClickable_Dynamic("SF_Finance_Flex_RadioButton_XPATH", fieldName);
			commonLib.clickWithDynamicValue("SF_Finance_Flex_RadioButton_XPATH", fieldName);
			Thread.sleep(2000);
			commonLib.log(LogStatus.INFO, "clicked successfully on radiobutton :" + fieldName);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**SHANTANUS5
	updateFlexComment
	@param flexComment
	void
	Jun 24, 2022
	*/
	
	public void updateFlexComment(String flexComment) {
		commonLib.waitForVisibilityOf("SF_Comments_Flex_Textbox_XPATH");
		commonLib.click("SF_Comments_Flex_Textbox_XPATH");
		commonLib.clear("SF_Comments_Flex_Textbox_XPATH");
		commonLib.sendKeys("SF_Comments_Flex_Textbox_XPATH", flexComment);
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "Successfully entered: " + flexComment + " under Comments to Flex field");
	}

	/**SHANTANUS5
	updateFinanceFlexFacilityType
	@param fieldName
	void
	Jun 24, 2022
	*/
	
	public void updateFinanceFlexFacilityType(String fieldName) {
		try {
			commonLib.waitForElementToBeClickable_Dynamic("SF_Finance_Flex_Facility_Type_Radio_XPATH", fieldName);
			commonLib.clickWithDynamicValue("SF_Finance_Flex_Facility_Type_Radio_XPATH", fieldName);
			Thread.sleep(2000);
			commonLib.log(LogStatus.INFO,
					"Clicked successfully on radiobutton :" + fieldName + " under Facility Type Radio Field");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**SHANTANUS5
	clickOnSendToFlexButton
	void
	Jun 24, 2022
	*/
	
	public void clickOnSendToFlexButton() {
		try {
			commonLib.KeyPress_pageUp();
			boolean bol = commonLib.waitForVisibilityOf("SF_Send_to_Flex_Button_XPATH");
			if (bol) {
				commonLib.getScreenshot();
				commonLib.log(LogStatus.PASS, "Send to Flex button is visible");
				commonLib.click("SF_Send_to_Flex_Button_XPATH");
				sfdcLib.waitforInvisibilityOfWE("SVMX_Loading_Spinner_XPATH");
			} else {
				commonLib.getScreenshot();
				commonLib.log(LogStatus.FAIL, "Send to Flex button is not visible");
			}

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**SHANTANUS5
	verfiyQuoteStatusField
	@param fieldName
	@return
	String
	Jun 24, 2022
	*/
	
	public String verfiyQuoteStatusField(String fieldName) {
		String fieldText = null;
		try {
			commonLib.KeyPress_pageUp();
			fieldText = commonLib.findElementWithDynamicXPath("SF_Quote_Transaction_Field_Text_XPATH", fieldName)
					.getAttribute("value");
			commonLib.getScreenshot();
			commonLib.log(LogStatus.INFO, fieldName + " field value is : " + fieldText);

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fieldText;

	}	

	/**SHANTANUS5
	verifyASPPriceValue
	@return
	@throws AWTException
	String
	Jun 24, 2022
	*/
	public String verifyASPPriceValue() throws AWTException {

		for (int i = 0; i < 8; i++) {
			commonLib.scroll(0, 200);
			if (commonLib.waitForVisibilityOf_DynamicXpath("SF_ASP_Price_Value_XPATH", "span")) {
				break;
			}
		}
		String ASPtext = commonLib.getText("SF_ASP_Price_Value_XPATH");
		if (!ASPtext.equalsIgnoreCase(null) && ASPtext.equalsIgnoreCase("Business ASP Price")) {
			commonLib.log(LogStatus.PASS, "ASP Price Value is :" + ASPtext);
			commonLib.getScreenshot();
		} else {
			commonLib.log(LogStatus.FAIL, "ASP Price Value is displaying as null");
			commonLib.getScreenshot();
		}
		return ASPtext;
	}

	/**SHANTANUS5
	verifyQuoteDetailsFieldValues
	@param fieldName
	void
	Jun 24, 2022
	*/
	
	public void verifyQuoteDetailsFieldValues(String fieldName) {
		boolean field = commonLib.findElementWithDynamicXPath("SF_Quote_Details_Field_Name_XPATH", fieldName)
				.isDisplayed();

		if (field) {
			commonLib.log(LogStatus.PASS, "Field Name: '" + fieldName + "' is displayed under Quote Details screen");

		} else {
			commonLib.log(LogStatus.FAIL,
					"Field Name: '" + fieldName + "' is not displayed under Quote Details screen");
			commonLib.getScreenshot();
		}
	}
		
	/**SHANTANUS5
	enterYearValueforLineItem
	@param yearNum
	@param value
	void
	Jun 24, 2022
	*/
	public void enterYearValueforLineItem(String yearNum, String value) {
		try {
			for (int i = 0; i <= 5; i++) {
				commonLib.scroll(550, 0);
				commonLib.scrollDownToElement("SF_Quote_Year7_Header_XPATH", "div");
				commonLib.scroll_view_Dynamic("SF_Quote_Year_Cell_XPATH", yearNum);
				boolean bol = commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Year_Cell_XPATH", yearNum);
				if (bol) {
					break;
				}
			}
			commonLib.clickbyjavascriptWithDynamicValue("SF_Quote_Year_Cell_XPATH", yearNum);
			commonLib.clickWithDynamicValue("SF_Quote_Year_Cell_XPATH", yearNum);
			Thread.sleep(2000);
			commonLib.sendKeys_DynamicValue("SF_Quote_Year_Input_XPATH", yearNum, value);

		} catch (Exception e) {
			commonLib.getScreenshot();
			e.printStackTrace();
		}
	}

	
	/**SHANTANUS5
	expandMassUpdatedSection
	void
	Jun 24, 2022
	*/
	
	public void expandMassUpdatedSection() {
		try {
			Thread.sleep(1000);
			commonLib.waitForVisibilityOf("SF_Quote_Mass_Update_Section_XPATH");
			commonLib.scrollDownToElement("SF_Quote_Mass_Update_Section_XPATH", "header");
			commonLib.click("SF_Quote_Mass_Update_Section_XPATH");
			commonLib.waitForPageToLoad();
			commonLib.scroll_view_Dynamic("SF_Quote_Field_Label_Name_XPATH", "Sell or Place");
			commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Field_Label_Name_XPATH", "Sell or Place");
			commonLib.getScreenshot();
			commonLib.log(LogStatus.INFO, "Mass Updates Section is Expanded");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**SHANTANUS5
	expandSectionUnderQuoteScreen: Method to expand any Section Name under Quote screen
	@param sectionname
	void
	Jun 24, 2022
	*/
	public void expandSectionUnderQuoteScreen(String sectionname) {
		commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Section_Name_XPATH", sectionname);
		commonLib.scroll_view_Dynamic("SF_Quote_Section_Name_XPATH", sectionname);
		commonLib.clickWithDynamicValue("SF_Quote_Section_Name_XPATH", sectionname);
		commonLib.waitForPageToLoad();
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, sectionname + " : is expanded successfully");
	}

	/**SHANTANUS5
	verifySectionNameAvailablity
	@param sectionname
	void
	Jun 24, 2022
	*/
	
	public void verifySectionNameAvailablity(String sectionname) {
		commonLib.scroll_view_Dynamic("SF_Quote_Section_Name_XPATH", sectionname);
		boolean bol = commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Section_Name_XPATH", sectionname);
		if (bol) {
			commonLib.log(LogStatus.PASS, sectionname + "  is visible to the user");
			commonLib.getScreenshot();
		} else {
			commonLib.log(LogStatus.PASS, sectionname + "  is visible to the user");
			commonLib.getScreenshot();
		}
	}

	/**SHANTANUS5
	scrolltoSectionNameUnderQuoteScreen
	@param sectionname
	@throws AWTException
	void
	Jun 24, 2022
	*/
	
	public void scrolltoSectionNameUnderQuoteScreen(String sectionname) throws AWTException {
		commonLib.KeyPress_pageUp();
		commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Section_Name_XPATH", sectionname);
		commonLib.scroll_view_Dynamic("SF_Quote_Section_Name_XPATH", sectionname);
		commonLib.scroll("Up");
		commonLib.waitForPageToLoad();
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, sectionname + " : is expanded successfully");
	}
	
	/**SHANTANUS5
	navigateToTabUnderQuoteSection
	@param tabName
	void
	Jun 24, 2022
	*/
	public void navigateToTabUnderQuoteSection(String tabName) {
		try {
			commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Tab_Name_XPATH", tabName);
			Thread.sleep(1000);
			commonLib.clickWithDynamicValue("SF_Quote_Tab_Name_XPATH", tabName);
			commonLib.waitForPageToLoad();
			commonLib.getScreenshot();
			commonLib.log(LogStatus.INFO, "Successfully navigate to tab :" + tabName);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**SHANTANUS5
	verifyServiceSummaryValues
	@param columnName
	void
	Jun 24, 2022
	*/
	public void verifyServiceSummaryValues(String columnName) {
		commonLib.scroll_view_Dynamic("SF_Quote_Summary_Service_Header_XPATH", columnName);
		boolean bol = commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Summary_Service_Header_XPATH", columnName);
		if (bol) {
			commonLib.log(LogStatus.PASS, columnName + " is visible in the system");
		} else {
			commonLib.log(LogStatus.FAIL, columnName + " is not visible in the system");
			commonLib.getScreenshot();
		}

	}

	/**SHANTANUS5
	selectFlexInfoReviewCheckox
	void
	Jun 24, 2022
	*/
	
	public void selectFlexInfoReviewCheckox() {
		commonLib.waitForVisibilityOf("SF_Quote_Flex_Info_Reviewed_Checkbox_XPATH");
		commonLib.click("SF_Quote_Flex_Info_Reviewed_Checkbox_XPATH");
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "Flex Deal Info Reviewed checkbox is selected");
	}
	
	/**SHANTANUS5
	verifyBaseSummaryValues
	@param columnName
	void
	Jun 24, 2022
	*/
	public void verifyBaseSummaryValues(String columnName) {
		commonLib.scroll_view_Dynamic("SF_Quote_Summary_Base_Header_XPATH", columnName);
		boolean bol = commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Summary_Base_Header_XPATH", columnName);
		if (bol) {
			commonLib.log(LogStatus.PASS, columnName + " is visible in the system");
		} else {
			commonLib.log(LogStatus.FAIL, columnName + " is not visible in the system");
			commonLib.getScreenshot();
		}
	}

	/**SHANTANUS5
	verifyBusinessUnitEditableStatusUnderPricingSection
	@param buName
	@return
	@throws AWTException
	String
	Jun 24, 2022
	*/
	
	public String verifyBusinessUnitEditableStatusUnderPricingSection(String buName) throws AWTException {
		scrolltoSectionNameUnderQuoteScreen("Pricing");
		commonLib.scroll("Up");
		commonLib.getScreenshot();
		String attrName = sfdcLib.getAttributeDynamic("SF_Quote_Business_Unit_XPATH", buName, "class");
		return attrName;

	}
	
	/**SHANTANUS5
	saveFinancialModel
	@param tabName
	@param viewName
	@throws InterruptedException
	void
	Jun 24, 2022
	*/
	public void saveFinancialModel(String tabName, String viewName) throws InterruptedException {

		if (tabName.equalsIgnoreCase("Capital Inputs")) {
			commonLib.click("SF_Quote_Capital_Input_XPATH");
			commonLib.clickWithDynamicValue("SF_Quote_Business_Unit_XPATH", viewName);
			commonLib.click("SF_Quote_Capital_Save_Button_XPATH");
		}
		if (tabName.equalsIgnoreCase("Consumables Input")) {
			commonLib.click("SF_Quote_Consumable_Input_XPATH");
			commonLib.clickWithDynamicValue("SF_Quote_Business_Unit_XPATH", viewName);
			commonLib.click("SF_Quote_Consumable_Save_Button_XPATH");
		}
		if (tabName.equalsIgnoreCase("Services Input")) {
			commonLib.click("SF_Quote_Service_Input_XPATH");
			commonLib.clickWithDynamicValue("SF_Quote_Business_Unit_XPATH", viewName);
			commonLib.click("SF_Quote_Service_Save_Button_XPATH");
		}
		Thread.sleep(1000);
		commonLib.waitForVisibilityOf("SF_Quote_Processing_Scroll_XPATH");
		commonLib.waitForPageToLoad();
	}

	/**SHANTANUS5
	verifyFieldLabelNames
	@param fieldName
	@return
	String
	Jun 24, 2022
	*/
	public String verifyFieldLabelNames(String fieldName) {
		String labelName = commonLib.getText("SF_Quote_Field_Label_Name_XPATH", fieldName);
		return labelName;
	}
	
	/**SHANTANUS5
	verifyQuoteStatusValue: Method to fetch quote status value
	@return
	@throws Exception
	String
	Jun 24, 2022
	*/
	public String verifyQuoteStatusValue() throws Exception {
		// commonLib.scroll("UP");
		commonLib.scrollDownToElement("SF_Quote_Status_XPATH", "input-text");
		String value = commonLib.getAttribute("SF_Quote_Status_XPATH", "value");
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "Quote Status Value is: " + value);
		return value;
	}
	
	/**SHANTANUS5
	selectIDINValue
	@param idinvalue
	void
	Jun 24, 2022
	*/
	public void selectIDINValue(String idinvalue) {
		try {
			commonLib.scrollDownToElement("SF_Quote_Deal_Comments_XPATH", "label");
			Thread.sleep(1000);
			commonLib.performHoverandClick("SF_Quote_Select_IDN_Dropdown_Arrow_XPATH");
			Thread.sleep(1000);
			commonLib.clickWithDynamicValue("SF_Quote_IDIN_Value_XPATH", idinvalue);
			Thread.sleep(3000);
			commonLib.click("SF_Quote_Update_IDIN_XPATH");
			commonLib.waitForVisibilityOf("SF_Quote_Processing_Scroll_XPATH");
			commonLib.waitForPageToLoad();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**SHANTANUS5
	selectGPOPricelistValue
	@param gpovalue
	void
	Jun 24, 2022
	*/
	public void selectGPOPricelistValue(String gpovalue) {
		try {
			commonLib.scroll("Up");
			Thread.sleep(1000);
			commonLib.performHoverandClick("SF_Quote_GPO_Pricelist_Dropdown_Arrow_XPATH");
			Thread.sleep(1000);
			commonLib.clickWithDynamicValue("SF_Quote_IDIN_Value_XPATH", gpovalue);
			Thread.sleep(3000);
			commonLib.click("SF_Quote_Update_GPO_XPATH");
			commonLib.waitForVisibilityOf("SF_Quote_Processing_Scroll_XPATH");
			commonLib.waitForPageToLoad();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**SHANTANUS5
	fetchSecionNamesUnderQuoteScreen
	@return
	List<String>
	Jun 24, 2022
	*/
	@SuppressWarnings("null")
	public List<String> fetchSecionNamesUnderQuoteScreen() {
		List<String> elements = new ArrayList<String>();
		List<WebElement> sectionNameElements = commonLib.findElements("SF_Quote_Section_Name_List_XPATH");

		for (int i = 0; i <= sectionNameElements.size() - 1; i++) {
			String text = sectionNameElements.get(i).getText();
			elements.add(text);
		}

		return elements;
	}

	/**SHANTANUS5
	fetchColumnHeaderUnderFinancialRollSection
	@return
	List<String>
	Jun 24, 2022
	*/
	@SuppressWarnings("null")
	public List<String> fetchColumnHeaderUnderFinancialRollSection() {
		List<String> elements = new ArrayList<String>();
		List<WebElement> sectionNameElements = commonLib.findElements("SF_Quote_Financial_Model_Column_Header_XPATH");
		for (int i = 0; i <= sectionNameElements.size() - 1; i++) {
			String text = sectionNameElements.get(i).getText();
			elements.add(text);
		}

		return elements;
	}

	/**SHANTANUS5
	clickOnFinancialModelTabLink
	@param tabName
	void
	Jun 24, 2022
	*/
	
	public void clickOnFinancialModelTabLink(String tabName) {
		commonLib.scroll_view_Dynamic("SF_Quote_Finanacial_Model_Tab_XPATH", tabName);
		commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Finanacial_Model_Tab_XPATH", tabName);
		commonLib.clickWithDynamicValue("SF_Quote_Finanacial_Model_Tab_XPATH", tabName);
		commonLib.waitForPageToLoad();
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "Successfully navigated to Financial Model Tab: " + tabName);
	}

	/**SHANTANUS5
	fetchServiceInputValues
	@return
	String
	Jun 24, 2022
	*/
	
	public String fetchServiceInputValues() {
		String str = commonLib.getAttribute("SF_Quote_Service_Input_View_XPATH", "value");
		return str;
	}

	/**SHANTANUS5
	clikOnUploadCSVFile
	void
	Jun 24, 2022
	*/
	
	public void clikOnUploadCSVFile() {
		commonLib.waitForElementToBeClickable("SF_Quote_Upload_CSV_Button_XPATH");
		commonLib.click("SF_Quote_CSV_Part_File_XPATH");
		commonLib.waitForPageToLoad();
		commonLib.log(LogStatus.INFO, "Clicked successfully on Upload CSV File button");
	}
	
	/**SHANTANUS5
	fetchQuoteDetailLabelValue
	@param labelName
	@return
	String
	Jun 24, 2022
	*/
	public String fetchQuoteDetailLabelValue(String labelName) {
		String labelText = commonLib.getText("SF_Quote_Details_Label_Value_XPATH", labelName);
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, labelName + " is populated with value: " + labelText);
		return labelText;
	}

	/**SHANTANUS5
	verifyMakoSalesCheckboxStatus
	@return
	String
	Jun 24, 2022
	*/
	public String verifyMakoSalesCheckboxStatus() {
		String attibuteName = commonLib.getAttribute("SF_Includes_Mako_Checkbox_XPATH", "checked");
		System.out.println("Attribute Name is: " + attibuteName);
		commonLib.getScreenshot();
		return attibuteName;
	}
	
	/**SHANTANUS5
	selectProposalTemplateValue
	@param templateName
	void
	Jun 24, 2022
	*/
	public void selectProposalTemplateValue(String templateName) {
		try {
			commonLib.waitForVisibilityOf("SF_Quote_Proposal_Template_Type_Dropdown_XPATH");
			commonLib.performHoverandClick("SF_Quote_Proposal_Template_Type_Dropdown_XPATH");
			Thread.sleep(2000);
			commonLib.clickWithDynamicValue("SF_Quote_IDIN_Value_XPATH", templateName);
			commonLib.waitForVisibilityOf("SF_Quote_Processing_Scroll_XPATH");
			commonLib.waitForPageToLoad();
			commonLib.getScreenshot();
			commonLib.log(LogStatus.INFO, "Proposal Template Type: " + templateName + "  is selected");

		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**SHANTANUS5
	selectRadioButtonUnderProposal
	@param radioButtonName
	void
	Jun 24, 2022
	*/
	public void selectRadioButtonUnderProposal(String radioButtonName) {
		try {
			commonLib.clickWithDynamicValue("SF_Quote_ProposalTab_RadioButton_XPATH", radioButtonName);
			Thread.sleep(3000);
			commonLib.getScreenshot();
			commonLib.log(LogStatus.INFO, "Radio Button: " + radioButtonName + "  is selected");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**SHANTANUS5
	clickOnChooseFileUnderAddionalAttachments
	void
	Jun 24, 2022
	*/
	public void clickOnChooseFileUnderAddionalAttachments() {
		commonLib.waitForVisibilityOf("SF_Quote_Select_File_XPATH");
		commonLib.clickbyjavascript("SF_Quote_Select_File_XPATH");
		commonLib.waitForPageToLoad();
		commonLib.getScreenshot();
	}

	/**SHANTANUS5
	performPrintProposal
	@throws IOException
	@throws InterruptedException
	void
	Jun 24, 2022
	*/
	public void performPrintProposal() throws IOException, InterruptedException {
		commonLib.waitForVisibilityOf("SF_Quote_Proposal_Print_Button_XPATH");
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "Print Proposal button is displayed");
		commonLib.click("SF_Quote_Proposal_Print_Button_XPATH");
		commonLib.waitForVisibilityOf("SF_Quote_Processing_Scroll_XPATH");
		Thread.sleep(2000);
		commonLib.waitForPageToLoad();
		commonLib.getScreenshot();
		commonLib.click("SF_Quote_Proposal_Print_Confirm_Button_XPATH");
		commonLib.log(LogStatus.PASS, "Proposal has been generated");
	}
	
	/**SHANTANUS5
	attachPDFToOpporunity
	@throws IOException
	@throws InterruptedException
	void
	Jun 24, 2022
	*/
	public void attachPDFToOpporunity() throws IOException, InterruptedException {
		commonLib.waitForVisibilityOf("SF_Quote_Attach_PDF_Opportunity_XPATH");
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "Attach PDF to Opporunity button is displayed");
		commonLib.click("SF_Quote_Attach_PDF_Opportunity_XPATH");
		commonLib.waitForVisibilityOf("SF_Quote_Processing_Scroll_XPATH");
		commonLib.waitForPageToLoad();
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "PDF is attached to Opportunity");
	}
	
	/**SHANTANUS5
	verifyProposalPDFGenerationUnderQuotesPage
	void
	Jun 24, 2022
	*/
	public void verifyProposalPDFGenerationUnderQuotesPage() {
		try {
			commonLib.scrollDownToElement("SF_Quotes_Header_Under_Oppty_XPATH", "span");
			commonLib.assertThat(commonLib.waitForVisibilityOf("SF_Quote_Notes_And_Attachment_PDF_XPATH"),
					"PDF file generated is attached and available under Notes & Attachments section",
					"PDF file is not generated under Notes & Attachments section");
			commonLib.scrollDownToElement("SF_Quote_Stage_History_Header_XPATH", "span");

			Thread.sleep(2000);

			commonLib.scrollDownToElement("SF_Quote_Divisonal_Opportunities_Header_XPATH", "span");
			Thread.sleep(2000);
			commonLib.scrollDownToElement("SF_Quote_Files_Header_XPATH", "span");
			commonLib.assertThat(commonLib.waitForPresenceOfElementLocated("SF_Quote_Files_PDF_XPATH"),
					"PDF file generated is attached and available under Files section",
					"PDF file is not generated under Files section");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
