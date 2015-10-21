package com.github.hayataka.selenium.howto.case03;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Settings {

	/**
	 * browserに対する設定を割り当てる
	 * ・スクリーンショットの取得
	 * ・Webページ上でカスタムjavascriptの実行
	 * ・ブラウザセッションからのwindowAlertとのやり取りの有効化等
	 */
	@Test
	public void introduceSettings() {

		Map<String, Object> settings = new HashMap<>();
		settings.put("takeScreenShot", true);		// 初期設定がそもそもtrueなので、特にしなくても問題ない

		// takeScreenShot
		// handlesAlert
		// cssSelectorsEnabled
		// javascriptEnabled
		// acceptSSLCerts
		// webStorageEnabled
		// org.openqa.selenium.remote.CapabilityType参照のこと
		// http://www.programcreek.com/java-api-examples/index.php?api=org.openqa.selenium.remote.CapabilityType

		DesiredCapabilities capabilities = new DesiredCapabilities(settings);
		FirefoxDriver driver = new FirefoxDriver(capabilities);
		driver.get("https://www.google.co.jp");
		driver.quit();
	}

	/**
	 * 画面ショット取得は、使用するブラウザによって
	 * ・ページ全体					→firefox
	 * ・現在のウィンドウ
	 * ・現在のフレームの表示されている部分	→chrome
	 * ・ブラウザを含むディスプレイ全体のスクリーンショット
	 * のいづれかに決まる
	 */
	@Test
	public void screenShot() {

		FirefoxDriver driver = new FirefoxDriver();
		driver.get("https://www.google.co.jp");


		File file = driver.getScreenshotAs(OutputType.FILE);
		System.out.println("一時フォルダ(jvm起動終了時に消えるパス）：" + file.getAbsolutePath());
		//binary (byte[]) /  base64String (String)
		// 永続化する場合には別のパスにコピーする必要あり


	}
}
