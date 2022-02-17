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

	public void navigationToCPQPage() {
		commonLib.scrollDownToElement("SF_Oppty_Quote_Expander_Button_XPATH", "Expander");
		commonLib.waitForElementToBeClickable("SF_Oppty_Quote_Expander_Button_XPATH");
		commonLib.click("SF_Oppty_Quote_Expander_Button_XPATH");
		commonLib.waitForPresenceOfElementLocated("SF_New_Quote_Button_XPATH");
		commonLib.click("SF_New_Quote_Button_XPATH");
		sfdcLib.waitforInvisibilityOfWE("SVMX_Loading_Spinner_XPATH");
	}

	public void updateQuoteDescription(String quoteDescription) {
		commonLib.waitforFramenadSwitch("SF_External_WebPage_Frame_XPATH");
		commonLib.waitforFramenadSwitch("SF_Canvas_Frame_XPATH");
		commonLib.waitforFramenadSwitch("SF_Oracle_CPQ_Cloud_XPATH");
		commonLib.waitForVisibilityOf("SF_Quote_Description_TextBox_XPATH");
		commonLib.clear("SF_Quote_Description_TextBox_XPATH");
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
		commonLib.clickWithDynamicValue("SF_Add_To_Construct_Field_Dropdown_XPATH", fieldName);
		commonLib.waitForPageToLoad();
		commonLib.clickWithDynamicValue("SF_Add_To_Contract_Dropdown_Values_XPATH", fieldValue);
		commonLib.waitForPageToLoad();
		commonLib.log(LogStatus.INFO, fieldValue + " selcted successfully under: " + fieldName);
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
	 * @param buttonName
	 */
	public void clickOnCPQQuoteOperationsButton(String buttonName) {
		try {
			boolean bol = commonLib.waitForVisibilityOf_DynamicXpath("SF_CP_Quote_Operation_Button_XPATH", buttonName);
			if (bol) {
				commonLib.getScreenshot();
				commonLib.clickWithDynamicValue("SF_CP_Quote_Operation_Button_XPATH", buttonName);
				commonLib.log(LogStatus.PASS, "Clicked successfully on "+buttonName +" button");
				commonLib.waitForPageToLoad();
				Thread.sleep(9000);
			} else {
				commonLib.getScreenshot();
				commonLib.log(LogStatus.FAIL, buttonName+" is not visible");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void cickOnEditQuote(String quoteNumber) {
		try {
			commonLib.scroll_view_Dynamic("SF_Edit_Quote_Expander_Button_XPATH", quoteNumber);
		//	commonLib.scrollDownToElement("SF_Oppty_Quote_Expander_Button_XPATH", "Expander");
			commonLib.waitForElementToBeClickable_Dynamic("SF_Edit_Quote_Expander_Button_XPATH", quoteNumber);
			commonLib.clickWithDynamicValue("SF_Edit_Quote_Expander_Button_XPATH", quoteNumber);
			Thread.sleep(3000);				
				boolean bol = commonLib.waitForVisibilityOf("SF_Edit_Quote_Button_XPATH");
				if(bol) {
					commonLib.getScreenshot();
					commonLib.click("SF_Edit_Quote_Button_XPATH");
					commonLib.log(LogStatus.INFO, "Clicked successfully on Edit button");
					commonLib.waitForPageToLoad();
					Thread.sleep(7000);
				}else {
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
	 * @return
	 */
	public String fetchQuoteDescriptiontValue() {
		String descriptionValue = commonLib.getAttribute("SF_Quote_Description_TextValue_XPATH", "value");
		commonLib.getScreenshot();
		return descriptionValue;
	}

}
