package com.github.hayataka.selenium.howto.case05.browsers;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class IE {

	@Test
	public void ie() {

		System.out.println("*********************************************");
		System.out.println("IEを動かすためには、事前の準備が幾つか必要です");
		System.out.println("http://selenium-release.storage.googleapis.com/index.html?path=2.48/");
		System.out.println("IEDriverServer_Win32_2.48.0.zip");
		System.out.println("をダウンロード・unzip・実行してください。（IEは32bit版でここでは話を進めます");
		// 前バージョンだと上記フォルダに、 IEDriverServer_Win64・・・があったのだが、2.48には32bitしかない。なぜ？
		System.out.println("実行すると、コマンドプロンプトおよび、待ち受けポートが表示されます （5555等）");
		System.out.println("ブラウザから、http://localhost:5555/status　（5555は上記サーバのプロンプトを参照）にて起動状況表示");
		System.out.println("*********************************************");


		// systemPropertyに、IEDriverのパスを指定する
		System.setProperty("webdriver.ie.driver", "C:\\Users\\hogehoge\\IEDriverServer.exe");
		// propertyの全リストは、
		//http://selenium.googlecode.com/git/docs/api/java/constant-values.html#org.openqa.selenium.ie.InternetExplorerDriver.NATIVE_EVENTS



		// IEDriver
		WebDriver driver = new InternetExplorerDriver();
		try {
			driver.get("https://www.google.co.jp");
		}finally {
			driver.quit();
		}
	}

}
