package com.stryker.common.modules;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.stryker.salesforceLibrary.SfdcLibrary;
import com.stryker.star.CommonLibrary;

public class LoginSFDC {

	private CommonLibrary commonLib;
	Date date = new Date();
	long ts = date.getTime();
	Timestamp timeStamp = new Timestamp(date.getTime());
	String Subject_Name = "Automation Task " + timeStamp.toString();
	SfdcLibrary sfdcLib = new SfdcLibrary(commonLib);
	ExtentTest parentTest;

	public LoginSFDC(CommonLibrary commonLib) {
		this.commonLib = commonLib;
		this.sfdcLib = new SfdcLibrary(commonLib);
	}

	public void loginToSFDC_Classic(String workbookName) {
		try {

			// Fetching Login Testdata
			List<String> loginData = commonLib.read_Test_Data(workbookName, "Users", 1, 1, 2, 3);
			
			commonLib.log(LogStatus.INFO, "Login with user: " + loginData.get(0));
			
			// Login with admin credentials
			sfdcLib.login(loginData.get(0), loginData.get(1));
			commonLib.waitForPageToLoad();
			commonLib.getScreenshot();
			commonLib.log(LogStatus.INFO,
					"Successfully login to " + commonLib.getConfigValue("environment").toUpperCase() + " environment.");
			sfdcLib.switchToClassicVersion();

		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		commonLib.waitForPageToLoad();
	}
}
