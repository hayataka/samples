package com.github.hayataka.selenium.howto.case01;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Seleniumの最低限の説明用.
 * <ul>
 * <li>Seleniumには、Selenium1(Selenium RC{Remote Control}）とSelenium2（Selenium WebDriver)がある。</li>
 * <li>ここではSelenium2について説明する</li>
 * </ul>
 *
 */
public class SimplePattern {

	/**
	 * 一番シンプルな形での使用方法の説明。
	 * 事前条件：Firefoxがインストールされていること.
	 * googleの検索画面でのテキストボックスのIDは変更になる可能性あり。
	 * newおよびquit、最低限のwebElementの説明用
	 */
	@Test
	public void sample() {
		WebDriver driver = new FirefoxDriver();								// 起動して
		try {
			driver.get("https://www.google.co.jp");							// URLアクセスして
			WebElement searchText = driver.findElement(By.id("lst-ib"));	// google検索のテキストボックス
			searchText.sendKeys("test");
			searchText.clear();
			searchText.sendKeys("yahoo");
			System.out.println("class value is:" + searchText.getAttribute("class"));			// cssの属性値を取得可能
			System.out.println("padding of cssValue is:" + searchText.getCssValue("padding"));
			Point location = searchText.getLocation();
			System.out.println("左上からの相対位置：");
			System.out.println(location);

			WebElement searchBtn = driver.findElement(By.name("btnK"));
			System.out.println("検索ボタンのテキスト：" + searchBtn.getText());	//該当要素のinnerTextの値、無い場合には空の文字列
			System.out.println(searchBtn.getTagName());
			System.out.println(searchBtn.isDisplayed());
			System.out.println(searchBtn.isEnabled());
			System.out.println(searchBtn.isSelected());
			System.out.println(searchText.getSize());
			searchText.submit(); 											// textboxでのEnterと同様。 webelement取れれば、値の取得・操作ができる
		} finally {
			driver.quit();													//closeとの差異は、（途中で新しく開いた等の）関連するwindowもすべて閉じる
		}
	}



	/**
	 * findElenment：elementが1件もない場合にはエラーが、複数件の場合は最初の1件のみ取得
	 * findElements:戻り値は0件＝空のList（size=0） 1件以上＝Listの中にあり。
	 */
	@Test
	public void findElements() {

		WebDriver driver = new FirefoxDriver();
		driver.get("https://jin-an.jp/");
		List<WebElement> elements = driver.findElements(By.className("project-content"));
		for(WebElement el : elements) {
			System.out.println(el.findElement(By.className("project-title")).getText());
			System.out.println(el.findElement(By.className("project-desc")).getText());
			System.out.println(el.findElement(By.className("price-numeric")).getText());
			System.out.println(el.findElement(By.className("work-time")).getText());
			// cssのclassName属性からで行っているもののみの例示だが
			// By.cssSelector(selector)
			// By.id(id)
			// By.xpath(xpathExpression)  等があるので、複数の指定方法がある。
			// →xpath は、Firefoxにfirebug・firepathのアドオンを入れて確認する方法を推奨
		}

		driver.quit();
	}




}
