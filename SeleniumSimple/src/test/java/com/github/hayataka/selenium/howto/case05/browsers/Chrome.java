package com.github.hayataka.selenium.howto.case05.browsers;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Chrome {

	@Test
	public void chrome() {

		System.out.println("*********************************************");
		System.out.println("Chromeを動かすためには、事前の準備が幾つか必要です");
		System.out.println("http://chromedriver.storage.googleapis.com/index.html");
		System.out.println("から、基本的には最新バージョンのディレクトリの、利用OSごとのchromedriver_OSごとに異なる箇所.zip");
		System.out.println("をダウンロード・unzip・実行してください。");
		System.out.println("実行すると、コマンドプロンプトおよび、待ち受けポートが表示されます ");
		System.out.println("*********************************************");

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\hogehoge\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.yahoo.co.jp");


		driver.quit();

	}

}
