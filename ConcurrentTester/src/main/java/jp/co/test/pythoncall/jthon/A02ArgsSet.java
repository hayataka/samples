package jp.co.test.pythoncall.jthon;

import java.util.Properties;

import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyUnicode;
import org.python.util.PythonInterpreter;

public class A02ArgsSet {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("python.console.encoding", "UTF-8");

		// integerはうまくいくことを確認
		PythonInterpreter.initialize(System.getProperties(), props, new String[0]);
		try (PythonInterpreter interp = new PythonInterpreter()) {
			PyInteger x = new PyInteger(10);
			interp.set("x", x);// 10をxに代入
			interp.exec("a = x + 2");

			PyObject a = interp.get("a");// aの結果を取得

			System.out.println(a);
			System.out.println(a.getClass());
		}

		// String かつ、英数字
		try (PythonInterpreter interp = new PythonInterpreter()) {
			PyString x = new PyString("hello23");
			interp.set("x", x);// 10をxに代入
			interp.exec("a = x + '2'");

			PyObject a = interp.get("a");// aの結果を取得

			System.out.println(a);
			System.out.println(a.getClass());
		}

		// こちらはUnicodeにしていてうまくいく
        try (PythonInterpreter interp = new PythonInterpreter()) {
            PyUnicode s = new PyUnicode("あいうえお");
            interp.set("s", s);
            interp.exec("a = s * 10");

            PyObject a = interp.get("a");

            System.out.println(a);
            System.out.println(a.getClass());
        }
		
		
		// JythonはPython2系のサポート。Python3系のコードは動作しません
		try (PythonInterpreter interp = new PythonInterpreter()) {
			PyString x = new PyString("エラーになります");
			
			/**
			 * Exception in thread "main" java.lang.IllegalArgumentException: Cannot create PyString with non-byte value
			 * 	at org.python.core.PyString.<init>(PyString.java:64)
			 * 	at org.python.core.PyString.<init>(PyString.java:70)
			 * 	at jp.co.test.concurrent.jthon.ArgsSet.main(ArgsSet.java:43)
			 */
			
			
			interp.set("x", x);// 10をxに代入
			interp.exec("a = x + '2'");

			PyObject a = interp.get("a");// aの結果を取得

			System.out.println(a);
			System.out.println(a.getClass());
		}
	
	}

}
