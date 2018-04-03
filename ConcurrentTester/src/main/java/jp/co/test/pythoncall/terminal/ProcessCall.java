package jp.co.test.pythoncall.terminal;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class ProcessCall {

	public static void main(String[] args) throws IOException {
		String word = "abc";
		
		File f = new File("./");
		System.out.println(f.getAbsolutePath());
		
		// memo この方法だと、pyに実行ビットを立てていないと、「ファイルが存在しない」メッセージが出力されるので注意
		ProcessBuilder processBuilder = new ProcessBuilder(f.getAbsolutePath() + "/src/main/resources/echo.py");
		// TODO pathはあとで調整する必要あり。jar実行
		Process process = processBuilder.start();
		BufferedReader reader = 
				new BufferedReader(new InputStreamReader(process.getInputStream()));
		BufferedWriter writer = 
				new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		
		writer.write(word);
		writer.newLine();
		writer.flush();
		
		String ret = reader.readLine();
		System.out.println(ret);		

	}
}
