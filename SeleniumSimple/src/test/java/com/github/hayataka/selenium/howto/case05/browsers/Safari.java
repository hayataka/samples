package com.github.hayataka.selenium.howto.case05.browsers;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class Safari {

	@Test
	public void safari() {

		System.out.println("https://github.com/SeleniumHQ/selenium/wiki/SafariDriver");
		System.out.println("に記載の通り、SafariDriver.safariextzをダウンロードし、実行（Safariブラウザに機能拡張を入れる");
		
//		System.setProperty("SafariDefaultPath", "/Applications/Safari.app");
		WebDriver driver = new SafariDriver();
		driver.get("https://www.google.co.jp");
		
		driver.quit();
	}
	
}
