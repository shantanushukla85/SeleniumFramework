package com.stryker.asc.pages;

import static org.testng.Assert.assertEquals;

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
	//	commonLib.clickbyjavascript("SF_Oppty_Quote_Expander_Button_XPATH");
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

	public void switchtoFrameUnderCPQScreen() {
		if (commonLib.waitForPresenceOfElementLocated("SF_External_WebPage_Frame_XPATH")) {
			commonLib.waitforFramenadSwitch("SF_External_WebPage_Frame_XPATH");
		} else {
			commonLib.waitforFramenadSwitch("SF_Accessibility_Frame_XPATH");

		}
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

	public void clickOnQuoteNumber(String quoteNumber) {
		boolean bol = commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Number_Link_XPATH", quoteNumber);
		if (bol) {
			commonLib.getScreenshot();
			commonLib.clickWithDynamicValue("SF_Quote_Number_Link_XPATH", quoteNumber);
			commonLib.log(LogStatus.PASS, "Clicked successfully on Quote Number Link");
		} else {
			commonLib.log(LogStatus.FAIL, "Quote Number is not available");
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
				commonLib.waitForPageToLoad();
				Thread.sleep(9000);
			} else {
				commonLib.getScreenshot();
				commonLib.log(LogStatus.FAIL, buttonName + " is not visible");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void cickOnEditQuote(String quoteNumber) {
		try {
			commonLib.scroll_view_Dynamic("SF_Edit_Quote_Expander_Button_XPATH", quoteNumber);
			// commonLib.scrollDownToElement("SF_Oppty_Quote_Expander_Button_XPATH",
			// "Expander");
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
			// commonLib.scrollDownToElement("SF_Quotes_Quick_Add_Button_XPATH", "Button");
			commonLib.waitForElementToBeClickable("SF_Quotes_Quick_Add_Button_XPATH");
			commonLib.click("SF_Quotes_Quick_Add_Button_XPATH");
			Thread.sleep(2000);
			commonLib.click("SF_Part_Number_Textbox_XPATH");
			commonLib.sendKeys("SF_Part_Number_Textbox_XPATH", partNumber);
			commonLib.click("SF_Part_Number_Lookup_Button_XPATH");
			commonLib.log(LogStatus.INFO, "Part Number value got entered");
			commonLib.getScreenshot();
			commonLib.click("SF_Quick_Add_Ok_Button_XPATH");
			sfdcLib.waitforInvisibilityOfWE("SVMX_Loading_Spinner_XPATH");
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
			commonLib.KeyPress_pageUp();
			boolean bol = commonLib.waitForVisibilityOf_DynamicXpath("SF_Quote_Transaction_Link_XPATH",
					transactionName);
			if (bol) {
				commonLib.log(LogStatus.INFO, transactionName + " field is visible");
			} else {
				commonLib.click("SF_Transaction_Link_XPATH");
				Thread.sleep(1000);
			}
			commonLib.clickWithDynamicValue("SF_Quote_Transaction_Link_XPATH", transactionName);
			commonLib.waitForPageToLoad();
			Thread.sleep(2000);
			commonLib.getScreenshot();
			commonLib.log(LogStatus.INFO,
					"Clicked successfully on Link: " + transactionName + " under CPQ Quote screen");
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
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

}
