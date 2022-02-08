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

	/////////// Ansh


}

