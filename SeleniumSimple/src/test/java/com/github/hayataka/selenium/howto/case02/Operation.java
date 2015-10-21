package com.github.hayataka.selenium.howto.case02;

import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class Operation {

	/**
	 * Ctrlキーを押しながら、複数の操作を一括して行うようなものはActionsを用いる
	 * @param args
	 */
	@Test
	public void click() {

		WebDriver driver = new FirefoxDriver();
		try {

			driver.get("http://masaboo.cside.com/new_html1/ht_61.htm");
			// 複数選択リストボックスのサンプルURL
			//WebElement multipleSelect = driver.findElement(By.xpath("html/body/div[1]/table[3]/tbody/tr/td[1]/table/tbody/tr[3]/td/div/form/select"));
			WebElement multipleSelect = driver.findElement(By.name("ryouri2"));
			// WebElement sel1 = multipleSelect.findElement(By.xpath("option[1]"));
			WebElement sel2 = multipleSelect.findElement(By.xpath("option[2]"));
			// WebElement sel3 = multipleSelect.findElement(By.xpath("option[3]"));
			WebElement sel4 = multipleSelect.findElement(By.xpath("option[4]"));
			Actions builder = new Actions(driver);
			builder.keyDown(Keys.CONTROL).click(sel2).click(sel4).keyUp(Keys.CONTROL);
			Action compositeAction = builder.build();
			compositeAction.perform();	// perform時に内部でbuildをしているので、実質的にはbuilder.perform()で構わない
			System.out.println("ここにブレークポイントを置いて、マウス操作結果を確認する");

		} finally {
			driver.quit();
		}

	}

	@Test
	public void ngWhenHtml5DragDrop() {

		WebDriver driver = new FirefoxDriver();
		try {
			driver.get("http://www.atmarkit.co.jp/ait/files/20110913/dnd1.htm");
			Actions builder = new Actions(driver);
			WebElement src = driver.findElement(By.id("cat1pic"));
			WebElement target = driver.findElement(By.id("drop"));
			//  builder.clickAndHold(src).release(target).perform();
			// https://code.google.com/p/selenium/issues/detail?id=3604
			// HTML5のDrag&Drop機能のものに対してうまく機能していない？
			// TODO 時間ができたら確認する

			builder.dragAndDrop(src, target).perform();
			System.out.println("操作結果を確認する");

		} finally {
			driver.quit();
		}
	}


	@Test
	public void dragDrop() {

		WebDriver driver = new FirefoxDriver();
		try {
			driver.get("http://jqueryui.com/droppable/");

			System.out.println("このサイトはiframeで区切られているためframeを移動する");
			WebElement frameElement = driver.findElement(By.className("demo-frame"));
			driver.switchTo().frame(frameElement);

			Actions builder = new Actions(driver);
			WebElement src = driver.findElement(By.id("draggable"));
			WebElement target = driver.findElement(By.id("droppable"));
			//builder.clickAndHold(src).release(target).perform();
			builder.dragAndDrop(src, target).perform();
			System.out.println("操作結果を確認する");

		} finally {
			driver.quit();
		}
	}

	@Test
	public void click_alert_navigate() {

		WebDriver driver = new FirefoxDriver();
		try {
			driver.get("http://www.ajaxtower.jp/js/event/sample2_1.html");
			WebElement btn = driver.findElement(By.name("yahoo"));
			Actions builder = new Actions(driver);
			builder.click(btn).perform();		// builderで作成可能
			Alert alert = driver.switchTo().alert();	// ブラウザのalert（モーダルダイアログ）に対する操作
			alert.accept();

			driver.navigate().back();		// ブラウザの戻るボタン
			//driver.navigate().forward()
			// driver.navigate().refresh()

		} finally {
			driver.quit();
		}
	}

}
