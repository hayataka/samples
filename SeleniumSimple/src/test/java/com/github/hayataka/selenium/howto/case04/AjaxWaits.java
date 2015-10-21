package com.github.hayataka.selenium.howto.case04;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AjaxWaits {

	@Test
	public void ajax() {

		WebDriver driver = new FirefoxDriver();
		try {
			driver.get("http://javatechnology.net/demo/ajax-biginer-start-up/ajax2.html");

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	// 全体設定

			WebElement btn = driver.findElement(By.id("ajax-button"));
			btn.click();
			WebElement result = new WebDriverWait(driver, 8).until(new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver d) {
					return d.findElement(By.id("sample-result"));
				}
			});

			System.out.println(result.getText());

		} finally {
			driver.quit();
		}
	}

}
