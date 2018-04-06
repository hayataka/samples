package jp.co.test.pythoncall.terminal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ProcessCall {
//http://techtipshoge.blogspot.jp/2015/09/java.html
	
	//memo python3 2008年にリリース
	//     python2 は、2020年まではメンテするとの話（そこで終わり）
	//　https://www.sejuku.net/blog/8988
	
	public static void main(String[] args) throws IOException {
		String word = "abc";

		File f = new File("./");
		System.out.println(f.getAbsolutePath());

		// memo この方法だと、pyに実行ビットを立てていないと、「ファイルが存在しない」メッセージが出力されるので注意
		ProcessBuilder processBuilder = new ProcessBuilder(f.getAbsolutePath() + "/src/main/resources/echo.py");
		// pathはあとで調整する必要あり。jar実行
		Process process = processBuilder.start();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));) {

			// 標準入力に渡す
			writer.write(word);
			writer.newLine();
			writer.flush();

			// 標準出力から受け取る
			String ret = reader.readLine();
			System.out.println(ret);
		}

	}
}
