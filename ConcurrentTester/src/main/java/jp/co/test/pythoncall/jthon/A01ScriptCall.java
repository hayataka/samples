package jp.co.test.pythoncall.jthon;

import java.util.Properties;
import org.python.util.PythonInterpreter;

public class A01ScriptCall {

	//pom.xml にてjython実行環境
	// 一番シンプルなパターン、直接、pythonのコマンドを記述して実行　渡すもの一切なし
	// https://qiita.com/ota-meshi/items/76f4a65e9bd2fe0e2f68

	//jythonはpython2環境で作成したスクリプトにしか適用できないので、そこ次第でそもそもNG
	
	public static void main(String[] args) {

		Properties props = new Properties();
        props.put("python.console.encoding", "UTF-8");

        PythonInterpreter.initialize(System.getProperties(), props, new String[0]);
        try (PythonInterpreter interp = new PythonInterpreter()) {

            interp.exec("a = 1 + 2");
            interp.exec("print(a)");
        }
	}

}
