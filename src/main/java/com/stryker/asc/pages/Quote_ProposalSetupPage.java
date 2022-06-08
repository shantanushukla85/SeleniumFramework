package com.stryker.asc.pages;

import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
/**
 * @author SHANTANUS5
 *
 */
/**
 * @author SHANTANUS5
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
		// commonLib.clickbyjavascript("SF_Oppty_Quote_Expander_Button_XPATH");
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

	/**
	 * Method to click on Create Quote screen
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
	 * Method to click on Edit button under Quote Details screen
	 */
	public void clickOnEditQuoteButton() {
		commonLib.waitForVisibilityOf("SF_Edit_Button_XPATH");
		commonLib.click("SF_Edit_Button_XPATH");
		sfdcLib.waitforInvisibilityOfWE("SVMX_Loading_Spinner_XPATH");
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "Edit CPQ Quote screen is displayed");
		commonLib.waitForPageToLoad();

	}

	/**
	 * Method to click on Quote Number under Realted List Screen
	 * 
	 * @param quoteNumber
	 * @throws InterruptedException
	 */
	public void clickOnQuoteNumberUnderOpportunityPage() throws InterruptedException {
		// commonLib.waitForPresenceOfElementLocated("SF_Quote_Link_Under_Oppty_Page_XPATH");

		commonLib.clickbyjavascript("SF_Quote_Link_Under_Oppty_Page_XPATH");
		commonLib.getScreenshot();
		Thread.sleep(1000);
//		if(commonLib.waitForVisibilityOf("SF_Quote_Link_Under_Oppty_Page_XPATH")) {
//			commonLib.clickbyjavascript("SF_Quote_Link_Under_Oppty_Page_XPATH");	
//			commonLib.getScreenshot();
//			commonLib.log(LogStatus.INFO, "Clicked successfully on the quote under Related List screen");
//		}else {
//			commonLib.getScreenshot();
//			commonLib.log(LogStatus.INFO, "Quotes are not available under Related List screen");
//		}
//		

//		try {
//			for (int i = 0; i < 5; i++) {
//				commonLib.scroll(0, 450);				
//				if (commonLib.waitForVisibilityOf("SF_Quotes_Header_Under_Oppty_XPATH")){
//					break;
//				}
//			}
//			// commonLib.scrollDownToElement("SF_Quotes_Header_Under_Oppty_XPATH", "h2");
//			Thread.sleep(2000);
//			commonLib.log(LogStatus.INFO, "Quotes Related List is displayed");
//			boolean bol = commonLib.waitForVisibilityOf("SF_Quote_Link_Under_Oppty_Page_XPATH");
//
//			if (bol) {
//				commonLib.log(LogStatus.INFO, "Quotes are available under Related List screen");
//				commonLib.getScreenshot();
//				commonLib.click("SF_Quote_Link_Under_Oppty_Page_XPATH");
//				commonLib.waitForPageToLoad();
//			} else {
//				commonLib.log(LogStatus.INFO, "Quotes are not available");
//			}
//
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	public void switchtoFrameUnderCPQScreen() {
//		if (commonLib.waitForPresenceOfElementLocated("SF_External_WebPage_Frame_XPATH")) {
//			commonLib.waitforFramenadSwitch("SF_External_WebPage_Frame_XPATH");
//		} else {
//			commonLib.waitforFramenadSwitch("SF_Accessibility_Frame_XPATH");
//
//		}
		commonLib.waitforFramenadSwitch("SF_Accessibility_Frame_XPATH");
		// commonLib.waitforFramenadSwitch("SF_Session_Frame_XPATH");
		commonLib.waitforFramenadSwitch("SF_Canvas_Frame_XPATH");
		commonLib.waitforFramenadSwitch("SF_Oracle_CPQ_Cloud_XPATH");

	}

	public void updateQuoteDescription(String quoteDescription) {
		commonLib.waitForElementToBeClickable("SF_Quote_Description_TextBox_XPATH");
		commonLib.click("SF_Quote_Description_TextBox_XPATH");
		// commonLib.clear("SF_Quote_Description_TextBox_XPATH");
		commonLib.sendKeys("SF_Quote_Description_TextBox_XPATH", quoteDescription);
		commonLib.waitForPageToLoad();
		commonLib.log(LogStatus.INFO, "Quote Description Value is updated successfully");
		commonLib.getScreenshot();

	}

	public void clickOnAddConstructButton() {
		try {
			commonLib.click("SF_Add_To_Constructs_Button_XPATH");
			Thread.sleep(3000);
			commonLib.waitForPageToLoad();
			commonLib.log(LogStatus.INFO, "Clicked successfully on Add to Construct button");
			commonLib.getScreenshot();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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

	public void clickOnLoadButtonUnderAddConstruct() {
		commonLib.click("SF_Load_Quote_Button_XPATH");
		commonLib.log(LogStatus.INFO, "Clicked successfully on Load Quote button");
		commonLib.getScreenshot();
	}

	public void updateProcedurePrice(String price) {
		commonLib.waitForVisibilityOf("SF_Manual_Procedure_Price_XPATH");
		commonLib.clear("SF_Manual_Procedure_Price_XPATH");
		commonLib.sendKeys("SF_Manual_Procedure_Price_XPATH", price);
		commonLib.log(LogStatus.INFO, "Successfully entered price: " + price + " under Manual Procedure Price field");
		commonLib.getScreenshot();
	}

	public void updateAddConstructTextboxField(String fieldName, String fieldValue) {
		commonLib.clear_DynamicValue("SF_Year_Textbox_XPATH", fieldName);
		commonLib.sendKeys_DynamicValue("SF_Year_Textbox_XPATH", fieldName, fieldValue);
		commonLib.log(LogStatus.INFO, "Successfully entered year: " + fieldValue + " under field " + fieldName);

	}

	public void clickOnCreateQuoteButton() {
		commonLib.waitForElementToBeClickable("SF_Create_Quote_Button_XPATH");
		commonLib.click("SF_Create_Quote_Button_XPATH");
		commonLib.log(LogStatus.INFO, "Clicked successfully on Create Quote button");
		commonLib.getScreenshot();
	}

//	public void clickOnQuoteNumber(String quoteNumber) {
//		commonLib.waitForPageToLoad();
//		JavascriptExecutor jse = (JavascriptExecutor)commonLib.getDriver();
//		jse.executeScript("window.scrollBy(0,350)", "");
//		//commonLib.scrollDownToElement("SF_Quotes_Header_Under_Oppty_XPATH", "a");
//		commonLib.scroll_view_Dynamic("SF_Quote_Number_Link_XPATH", quoteNumber);
//		boolean bol = commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Number_Link_XPATH", quoteNumber);
//		if (bol) {
//			commonLib.getScreenshot();
//			commonLib.clickbyjavascriptWithDynamicValue("SF_Quote_Number_Link_XPATH", quoteNumber);
//			//commonLib.clickWithDynamicValue("SF_Quote_Number_Link_XPATH", quoteNumber);
//			commonLib.log(LogStatus.PASS, "Clicked successfully on Quote Number Link");
//		} else {
//			commonLib.log(LogStatus.FAIL, "Quote Number is not available");
//		}
//	}

	public void clickOnQuoteNumber(String quoteNumber) {
		try {
			commonLib.waitForPageToLoad();
			commonLib.scroll_view_Dynamic("SF_Quote_Number_Link_XPATH", quoteNumber);
			commonLib.clickbyjavascriptWithDynamicValue("SF_Quote_Number_Link_XPATH", quoteNumber);
			commonLib.log(LogStatus.PASS, "Clicked successfully on Quote Number Link");
		} catch (Exception e) {
			commonLib.getScreenshot();
			commonLib.log(LogStatus.FAIL, "Quote Number: " + quoteNumber + " is not available");
		}

	}

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

	/**
	 * Method to click on Save, Return to Opportunity and Rerun Pricing buttons
	 * 
	 * @param buttonName
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

	/**
	 * Method to fetch quote description field value
	 * 
	 * @return
	 */
	public String fetchQuoteDescriptiontValue() {
		String descriptionValue = commonLib.getAttribute("SF_Quote_Description_TextValue_XPATH", "value");
		commonLib.getScreenshot();
		return descriptionValue;
	}

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

	public int verifyPartNumberProducts() {
		int parts = 0;
		try {
			// commonLib.waitForElementToBeClickable("SF_Quotes_Tab_XPATH");
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

	public void clickOnRequestFinancing() {
		try {
			commonLib.KeyPress_pageUp();
			// commonLib.scrollDownToElement("SF_Request_Finance_Checkbox_XPATH",
			// "Checkbox");
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
			// commonLib.click("SF_Quote_Other_Info_Link_XPATH");
			commonLib.clickbyjavascriptWithDynamicValue("SF_Quote_Transaction_Link_XPATH", transactionName);
			// commonLib.clickWithDynamicValue("SF_Quote_Transaction_Link_XPATH",
			// transactionName);
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

	public void updateFlexComment(String flexComment) {
		commonLib.waitForVisibilityOf("SF_Comments_Flex_Textbox_XPATH");
		commonLib.click("SF_Comments_Flex_Textbox_XPATH");
		commonLib.clear("SF_Comments_Flex_Textbox_XPATH");
		commonLib.sendKeys("SF_Comments_Flex_Textbox_XPATH", flexComment);
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "Successfully entered: " + flexComment + " under Comments to Flex field");
	}

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

	/**
	 * @param fieldName
	 * @return
	 */
	public String verfiyQuoteStatusField(String fieldName) {
		String fieldText = null;
		try {
			commonLib.KeyPress_pageUp();
			// String
			// fieldName2=commonLib.findElementPresence_Dynamic("SF_Quote_Transaction_Field_Text_XPATH",
			// fieldName);
			fieldText = commonLib.findElementWithDynamicXPath("SF_Quote_Transaction_Field_Text_XPATH", fieldName)
					.getAttribute("value");
			// fieldText = commonLib.getText("SF_Quote_Transaction_Field_Text_XPATH",
			// fieldName);
			commonLib.getScreenshot();
			commonLib.log(LogStatus.INFO, fieldName + " field value is : " + fieldText);

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fieldText;

	}

	/**
	 * Method to fetch ASP Price Value
	 * 
	 * @return
	 * @throws AWTException
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

	/**
	 * Method to enter Year Values under various Years Columns of Parts table
	 * 
	 * @param yearNum
	 * @param value
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
			// commonLib.performHoverandClickDynamic("SF_Quote_Year_Input_XPATH", yearNum);
			commonLib.clickbyjavascriptWithDynamicValue("SF_Quote_Year_Cell_XPATH", yearNum);
			commonLib.clickWithDynamicValue("SF_Quote_Year_Cell_XPATH", yearNum);
			Thread.sleep(2000);
			// commonLib.clear_DynamicValue("SF_Quote_Year_Input_XPATH", yearNum);
			commonLib.sendKeys_DynamicValue("SF_Quote_Year_Input_XPATH", yearNum, value);

		} catch (Exception e) {
			commonLib.getScreenshot();
			e.printStackTrace();
		}
	}

	/**
	 * Method to expand mass update section
	 * 
	 * @param yearNum
	 * @param value
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

	/**
	 * Method to expand any Section Name under Quote screen
	 * 
	 * @param sectionname
	 */
	public void expandSectionUnderQuoteScreen(String sectionname) {
		commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Section_Name_XPATH", sectionname);
		commonLib.scroll_view_Dynamic("SF_Quote_Section_Name_XPATH", sectionname);
		commonLib.clickWithDynamicValue("SF_Quote_Section_Name_XPATH", sectionname);
		commonLib.waitForPageToLoad();
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, sectionname + " : is expanded successfully");
	}

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

	public void scrolltoSectionNameUnderQuoteScreen(String sectionname) throws AWTException {
		commonLib.KeyPress_pageUp();
		commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Section_Name_XPATH", sectionname);
		commonLib.scroll_view_Dynamic("SF_Quote_Section_Name_XPATH", sectionname);
		commonLib.scroll("Up");
		commonLib.waitForPageToLoad();
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, sectionname + " : is expanded successfully");
	}

	/**
	 * Method to navigate to a particular tab under Quote Section
	 * 
	 * @param tabName
	 */
	public void navigateToTabUnderQuoteSection(String tabName) {
		try {
			commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Tab_Name_XPATH", tabName);
//			commonLib.scroll_view_Dynamic("SF_Quote_Tab_Name_XPATH", tabName);
//			commonLib.scroll("Up");
			Thread.sleep(1000);
			// commonLib.clickbyjavascriptWithDynamicValue("SF_Quote_Tab_Name_XPATH",
			// tabName);
			commonLib.clickWithDynamicValue("SF_Quote_Tab_Name_XPATH", tabName);
			commonLib.waitForPageToLoad();
			commonLib.getScreenshot();
			commonLib.log(LogStatus.INFO, "Successfully navigate to tab :" + tabName);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method to verify visibility of columns under Service Section of Summary
	 * Rollup
	 * 
	 * @param columnName
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
	
	public void selectFlexInfoReviewCheckox() {
		commonLib.waitForVisibilityOf("SF_Quote_Flex_Info_Reviewed_Checkbox_XPATH");
		commonLib.click("SF_Quote_Flex_Info_Reviewed_Checkbox_XPATH");
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "Flex Deal Info Reviewed checkbox is selected");
	}

	/**
	 * Method to verify visibility of columns under Service Section of Summary
	 * Rollup
	 * 
	 * @param columnName
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

	public String verifyBusinessUnitEditableStatusUnderPricingSection(String buName) throws AWTException {
		scrolltoSectionNameUnderQuoteScreen("Pricing");
		commonLib.scroll("Up");
		commonLib.getScreenshot();
		// getAttributeDynamicSF_Quote_Business_Unit_XPATH
		String attrName = sfdcLib.getAttributeDynamic("SF_Quote_Business_Unit_XPATH", buName, "class");
		return attrName;

	}

	/**
	 * @param tabName  = Capital Inputs, Consumables Input , Services Input
	 * @param viewName = Pricing, Finance
	 * @throws InterruptedException
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

	/**
	 * Method to verify field names under Quote screen
	 * 
	 * @param fieldName
	 * @return
	 */
	public String verifyFieldLabelNames(String fieldName) {
		String labelName = commonLib.getText("SF_Quote_Field_Label_Name_XPATH", fieldName);
		return labelName;
	}

	/**
	 * Method to fetch quote status value
	 * 
	 * @return
	 * @throws Exception
	 */
	public String verifyQuoteStatusValue() throws Exception {
		// commonLib.scroll("UP");
		commonLib.scrollDownToElement("SF_Quote_Status_XPATH", "input-text");
		String value = commonLib.getAttribute("SF_Quote_Status_XPATH", "value");
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "Quote Status Value is: " + value);
		return value;
	}

	/**
	 * Method to selct IDIN value
	 * 
	 * @param idinvalue
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

	/**
	 * Method to update GPO Pricelist Value
	 * 
	 * @param gpovalue
	 */

	public void selectGPOPricelistValue(String gpovalue) {
		try {
			commonLib.scroll("Up");
//			commonLib.scrollDownToElement("SF_Quote_Mass_Update_Section_XPATH", "h3");
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

	public void clickOnFinancialModelTabLink(String tabName) {
		commonLib.scroll_view_Dynamic("SF_Quote_Finanacial_Model_Tab_XPATH", tabName);
		commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Finanacial_Model_Tab_XPATH", tabName);
		commonLib.clickWithDynamicValue("SF_Quote_Finanacial_Model_Tab_XPATH", tabName);
		commonLib.waitForPageToLoad();
		commonLib.getScreenshot();
		commonLib.log(LogStatus.INFO, "Successfully navigated to Financial Model Tab: " + tabName);
	}

	public String fetchServiceInputValues() {
		String str = commonLib.getAttribute("SF_Quote_Service_Input_View_XPATH", "value");
		return str;
	}

}
